// This example creates a simple polygon representing the Bermuda Triangle.
// When the user clicks on the polygon an info window opens, showing
// information about the polygon's coordinates.

var map;
var infoWindow;
var styles = [{"featureType":"administrative","stylers":[{"visibility":"off"}]},{"featureType":"poi","stylers":[{"visibility":"simplified"}]},{"featureType":"road","stylers":[{"visibility":"simplified"}]},{"featureType":"water","stylers":[{"visibility":"simplified"}]},{"featureType":"transit","stylers":[{"visibility":"simplified"}]},{"featureType":"landscape","stylers":[{"visibility":"simplified"}]},{"featureType":"road.highway","stylers":[{"visibility":"off"}]},{"featureType":"road.local","stylers":[{"visibility":"on"}]},{"featureType":"road.highway","elementType":"geometry","stylers":[{"visibility":"on"}]},{"featureType":"water","stylers":[{"color":"#84afa3"},{"lightness":52}]},{"stylers":[{"saturation":-77}]},{"featureType":"road"}];
var latLng;


function readLatLngFromBrowser(position) {
    setLatLng({"latitude": position.coords.latitude, "longitude": position.coords.longitude});
}

function setLatLng(coordinate) {
    latLng = coordinate;
    var hexagon = [];

    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 18,
        center: {lat: latLng.latitude, lng: latLng.longitude},
        mapTypeId: google.maps.MapTypeId.TERRAIN
    });

    map.setOptions({styles: styles});

    $.ajax({
        type: "POST",
        url: "http://localhost:8080/hexagon",
        processData: false,
        contentType: 'application/json',
        data: JSON.stringify(latLng),
        success: function (data, status, jqXHR) {

            var size = 0.0009;
            for(var i=0; i<6; i++) {
                var angle = Math.PI / 180 * (60 * i + 30);
                hexagon.push(
                    {lat: data.coordinate.latitude + size * Math.sin(angle),
                        lng: data.coordinate.longitude + size * Math.cos(angle)});

            }
            // Construct the polygon.
            var bermudaTriangle = new google.maps.Polygon({
                paths: hexagon,
                strokeColor: '#000000',
                strokeOpacity: 0.8,
                strokeWeight: 6,
                fillColor: '#000000',
                fillOpacity: 0.35
            });
            bermudaTriangle.setMap(map);

            // Add a listener for the click event.
            bermudaTriangle.addListener('click', showArrays);

            infoWindow = new google.maps.InfoWindow;


            var circle = new google.maps.Circle({
                strokeColor: '#000000',
                strokeOpacity: 0.8,
                strokeWeight: 3,
                fillColor: '#000000',
                fillOpacity: 0.35,
                center: {lat: latLng.latitude, lng: latLng.longitude},
                radius: 1
            });

            circle.setMap(map);


        },

        error: function (jqXHR, status) {
            console.log("error");
        }

    });
}

function bootstrap() {

    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(readLatLngFromBrowser);
    } else {
        var operaHouse = {"latitude": -33.856845, "longitude": 151.215281};
        setLatLng(operaHouse);
    }

}



function showArrays(event) {
    var vertices = this.getPath();

    var contentString = '<b>Hexagon</b><br>' +
        'Clicked location: <br>' + event.latLng.lat() + ',' + event.latLng.lng() +
        '<br>';

    // Iterate over the vertices.
    for (var i =0; i < vertices.getLength(); i++) {
        var xy = vertices.getAt(i);
        contentString += '<br>' + 'Coordinate ' + i + ':<br>' + xy.lat() + ',' +
            xy.lng();
    }

    // Replace the info window's content and position.
    infoWindow.setContent(contentString);
    infoWindow.setPosition(event.latLng);

    infoWindow.open(map);
}

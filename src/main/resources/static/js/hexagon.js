// This example creates a simple polygon representing the Bermuda Triangle.
// When the user clicks on the polygon an info window opens, showing
// information about the polygon's coordinates.

var map;
var infoWindow;
var styles = [{"featureType":"administrative","stylers":[{"visibility":"off"}]},{"featureType":"poi","stylers":[{"visibility":"simplified"}]},{"featureType":"road","stylers":[{"visibility":"simplified"}]},{"featureType":"water","stylers":[{"visibility":"simplified"}]},{"featureType":"transit","stylers":[{"visibility":"simplified"}]},{"featureType":"landscape","stylers":[{"visibility":"simplified"}]},{"featureType":"road.highway","stylers":[{"visibility":"off"}]},{"featureType":"road.local","stylers":[{"visibility":"on"}]},{"featureType":"road.highway","elementType":"geometry","stylers":[{"visibility":"on"}]},{"featureType":"water","stylers":[{"color":"#84afa3"},{"lightness":52}]},{"stylers":[{"saturation":-77}]},{"featureType":"road"}];
var latLng;
var SIZE = 0.0009;


function readLatLngFromBrowser(position) {
    setLatLng({"latitude": position.coords.latitude, "longitude": position.coords.longitude});
}


function addTileFromColumnAndRow(q, r) {
    var latitude = SIZE * 3/2 * r;
    var longitude = SIZE * Math.sqrt(3) * (q + r/2);
    addTile(latitude, longitude);
}

function addTile(latitude, longitude) {
    var hexagon = [];
    for(var i=0; i<6; i++) {
        var angle = Math.PI / 180 * (60 * i + 30);
        hexagon.push(
            {lat: latitude + SIZE * Math.sin(angle),
                lng: longitude + SIZE * Math.cos(angle)});

    }
    // Construct the polygon.
    var hexagon = new google.maps.Polygon({
        paths: hexagon,
        strokeColor: '#000000',
        strokeOpacity: 0.35,
        strokeWeight: 6,
        fillColor: '#000000',
        fillOpacity: 0.15
    });
    hexagon.setMap(map);

    // Add a listener for the click event.
    hexagon.addListener('click', showArrays);

    infoWindow = new google.maps.InfoWindow;

}



function setLatLng(coordinate) {
    latLng = coordinate;

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

//            var latitude = data.coordinate.latitude;
//            var longitude = data.coordinate.longitude;

            var row = data.row;
            var column = data.column;

//            addTile(latitude, longitude);
            addTileFromColumnAndRow(column, row);
            addTileFromColumnAndRow(column+1, row);
            addTileFromColumnAndRow(column+1, row-1);
            addTileFromColumnAndRow(column, row-1);
            addTileFromColumnAndRow(column-1, row);
            addTileFromColumnAndRow(column-1, row+1);
            addTileFromColumnAndRow(column, row+1);


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

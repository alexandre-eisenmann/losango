var map;
var infoWindow;
var styles = [{"featureType":"administrative","stylers":[{"visibility":"off"}]},{"featureType":"poi","stylers":[{"visibility":"simplified"}]},{"featureType":"road","stylers":[{"visibility":"simplified"}]},{"featureType":"water","stylers":[{"visibility":"simplified"}]},{"featureType":"transit","stylers":[{"visibility":"simplified"}]},{"featureType":"landscape","stylers":[{"visibility":"simplified"}]},{"featureType":"road.highway","stylers":[{"visibility":"off"}]},{"featureType":"road.local","stylers":[{"visibility":"on"}]},{"featureType":"road.highway","elementType":"geometry","stylers":[{"visibility":"on"}]},{"featureType":"water","stylers":[{"color":"#84afa3"},{"lightness":52}]},{"stylers":[{"saturation":-77}]},{"featureType":"road"}];
var latLng;
var hexagonWidth = 0.0016;
var hexagonSide = hexagonWidth/Math.sqrt(3);
var SIZE = hexagonWidth/Math.sqrt(3);
var cylinderCircunference = 256.0;
var renderedTiles = {};



function fromLatLngToMercatorPoint( latitude, longitude) {
    var phi = latitude * Math.PI / 180.0;
    var x = (longitude + 180.0) / 360.0 * cylinderCircunference;
    var y = ((1 - Math.log(Math.tan(phi) + 1 / Math.cos(phi)) / Math.PI) / 2.0) * cylinderCircunference;
    return {x: x, y: y};
}

function getRoundedHexagon(q, r) {
    var rx =  Math.round(q);
    var ry =  Math.round(r);
    var rz =  Math.round(-q-r);

    var dx = Math.abs(rx - q);
    var dy = Math.abs(ry - r);
    var dz = Math.abs(rz - (-q-r));

    if (dx > dy && dx > dz)
        rx = -ry-rz;
    else if (dy > dz)
        ry = -rx-rz;
    //else
    //    rz = -rx-ry;

    return {q: rx, r: ry};
}


function getNormalizedHexagon(column, row) {
    var nHex = Math.floor(cylinderCircunference/hexagonWidth);
    var start = Math.trunc(row / 2) * - 1;
    return {q: ((nHex + ((column - start) % nHex)) % nHex) + start, r: row};
}


function getHexagonFromPoint(x, y) {
    var q = (x * Math.sqrt(3.0)/3.0 - y/3.0) / hexagonSide;
    var r = y * 2.0/3.0 / hexagonSide;
    var hex = getRoundedHexagon(q, r);
    return getNormalizedHexagon(hex.q, hex.r);
}

function getTile(latitude,longitude) {
    var point = fromLatLngToMercatorPoint(latitude, longitude);
    return getHexagonFromPoint(point.x, point.y);
}

function readLatLngFromBrowser(position) {
    setLatLng({"latitude": position.coords.latitude, "longitude": position.coords.longitude});
}

function renderTile(tile) {
    var tileAddress = tile.q + "#" + tile.r;
    if (!renderedTiles[tileAddress]) {
        addTileFromColumnAndRow(tile.q, tile.r);
        renderedTiles[tileAddress] = true;
    }
}

function addTileFromColumnAndRow(q, r) {
    var y = SIZE * 3/2 * r;
    var x = SIZE * Math.sqrt(3) * (q + r/2);

    var hexagonPoints = [];
    for(var i=0; i<6; i++) {
        var angle = Math.PI / 180 * (60 * i + 30);
        var point = new google.maps.Point(x + SIZE * Math.cos(angle),y + SIZE * Math.sin(angle));
        hexagonPoints.push(map.getProjection().fromPointToLatLng(point));
    }

    var hexagon = new google.maps.Polygon({
        paths: hexagonPoints,
        strokeColor: '#000000',
        strokeOpacity: 0.35,
        strokeWeight: 1,
        fillColor: '#000000',
        fillOpacity: 0.15
    });
    hexagon.setMap(map);

    function showArrays(event) {
        var vertices = this.getPath();

        var contentString = '<b>Hexagon</b><br>' +
            'Clicked location: <br>' + event.latLng.lat() + ',' + event.latLng.lng() +
            '<br>Column: <br>' + q +
            '<br>Row: <br>' + r +
            '<br>';

        for (var i =0; i < vertices.getLength(); i++) {
            var xy = vertices.getAt(i);
            contentString += '<br>' + 'Coordinate ' + i + ':<br>' + xy.lat() + ',' +
                xy.lng();
        }

        infoWindow.setContent(contentString);
        infoWindow.setPosition(event.latLng);

        infoWindow.open(map);
    }

    hexagon.addListener('click', showArrays);

    infoWindow = new google.maps.InfoWindow;
}



function setLatLng(coordinate) {

    latLng = coordinate;
    var tile = getTile(latLng.latitude, latLng.longitude);
    console.log("calculating tile:", tile.q, tile.r );

    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 18,
        center: {lat: latLng.latitude, lng: latLng.longitude},
        mapTypeId: google.maps.MapTypeId.TERRAIN
    });

    map.setOptions({styles: styles});

    google.maps.event.addListener(map,'bounds_changed', function() {
        var bounds = map.getBounds();
        var NE = bounds.getNorthEast();
        var SW = bounds.getSouthWest();
        var NW = new google.maps.LatLng(NE.lat(),SW.lng());
        var SE = new google.maps.LatLng(SW.lat(),NE.lng());

        var tnw = getTile(NW.lat(), NW.lng());
        var tne = getTile(NE.lat(), NE.lng());
        var tsw = getTile(SW.lat(), SW.lng());
        var tse = getTile(SE.lat(), SE.lng());

        var rowMin = Math.min(tnw.r, tne.r);
        var rowMax = Math.max(tsw.r, tse.r);
        var colMin = Math.min(tnw.q, tsw.q);
        var colMax = Math.max(tne.q, tse.q);

        for (var r = rowMin; r <= rowMax; r++) {
            for (var q = colMin; q <= colMax; q++) {
                renderTile({q: q, r: r});
            }
        }

    });

    google.maps.event.addListenerOnce(map,"projection_changed", function() {

        $.ajax({
          type: "POST",
          url: "http://localhost:8080/hexagon",
          processData: false,
          contentType: 'application/json',
          data: JSON.stringify(latLng),
          success: function (data) {

              //var row = data.row;
              //var column = data.column;

              //addTileFromColumnAndRow(column, row);
              //addTileFromColumnAndRow(column+1, row);
              //addTileFromColumnAndRow(column+1, row-1);
              //addTileFromColumnAndRow(column, row-1);
              //addTileFromColumnAndRow(column-1, row);
              //addTileFromColumnAndRow(column-1, row+1);
              //addTileFromColumnAndRow(column, row+1);


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

          error: function () {
              console.log("error");
          }

      });

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




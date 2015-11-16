var _canvas = {width: 0, height: 0}
var _position = {x: 0, y: 0};
var _angle = 0;
var _ctx;
var _penup = 0;
var _color = 0;
var _width = 2;


function init(width, height) {
    var c = document.getElementById("turtle");
    _ctx = c.getContext("2d");
    _canvas.width = width;
    _canvas.height = height;
    _position = {x: 0, y: 0};
    _angle = 0;
    _penup = 0;
    _color = "#000000";
    _width = 2;
}

function draw_line(x, y) {
    if (_penup == 0) {
        _ctx.beginPath();
        _ctx.moveTo(_position.x, _position.y);
        _ctx.lineTo(x, y);
        _ctx.lineWidth = _width;
        _ctx.strokeStyle = _color;
        _ctx.stroke();
    }
}
function turtle_reset() {
    _position = {x: 0, y: 0};
    _angle = 0;
    _penup = 0;
    _color = "#000000";
    _width = 2;
}


function turtle_right(angle) {
    _angle = (_angle + angle) % 360;
    console.log(_angle);
}

function turtle_left(angle) {
    _angle = (_angle - angle) % 360;
    if (_angle < 0) {
        _angle += 360;
    }
    console.log(_angle);
}

function turtle_forward(distance) {
    console.log("---forward---");
    var x = _position.x + distance * Math.cos(_angle * Math.PI / 180);
    var y = _position.y + distance * Math.sin(_angle * Math.PI / 180);
    console.log(x);
    console.log(y);
    console.log(_angle);
    draw_line(x, y);
    _position.x = x;
    _position.y = y;
    console.log("------------");
}


function turtle_goto(x, y) {
    console.log("---goto---");
    _position.x = x;
    _position.y = y;
    console.log(x);
    console.log(y);
    console.log("------------");
}

function turtle_clear() {
    console.log("---clear---");
    console.log(_canvas.width);
    console.log(_canvas.height);
    _ctx.clearRect(0, 0, _canvas.width, _canvas.height);
    console.log("------------");
}

function turtle_penup() {
    _penup = 1;
}

function turtle_pendown() {
    _penup = 0;
}

function turtle_color(r, g, b) {
    console.log("---color---");
    var hex_r = "00";
    var hex_g = "00";
    var hex_b = "00";
    if (r >= 0 && r <= 255) {
        hex_r = r.toString(16);
        if (hex_r.length == 1) {
            hex_r = "0".concat(hex_r);
        }

    }
    if (g >= 0 && g <= 255) {
        hex_g = g.toString(16);
        if (hex_g.length == 1) {
            hex_g = "0".concat(hex_g);
        }
    }
    if (b >= 0 && b <= 255) {
        hex_b = b.toString(16);
        if (hex_b.length == 1) {
            hex_b = "0".concat(hex_b);
        }
    }
    _color = (("#".concat(hex_r)).concat(hex_g)).concat(hex_b);
    console.log(_color);
    console.log("---------");
}

function turtle_width(width) {
    console.log("---width---");
    _width = width;
    console.log(_width);
    console.log("---------");
}
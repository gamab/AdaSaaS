PACKAGE Turtle IS
	type Byte is range 	0..255;

	procedure turtle_init;

	procedure turtle_reset;

	procedure turtle_right(angle : Float);

	procedure turtle_left(angle : Float);

	procedure turtle_forward(distance : Float);

	procedure turtle_goto(x,y : Float);

	procedure turtle_clear;

	procedure turtle_penup;

	procedure turtle_pendown;

	procedure turtle_color(r,g,b : Byte);

	procedure turtle_width(width : Integer);

	procedure turtle_close;

END Turtle ;

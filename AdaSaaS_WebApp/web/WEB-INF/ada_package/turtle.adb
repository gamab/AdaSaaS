with Ada.Text_IO;
use Ada.Text_IO;

package body Turtle is
    script : File_Type;

    procedure turtle_init is
    begin
      Create(script, Out_File, "client_turtle_script.js");
      Put_Line(script,"init(600,500);");
      Put_Line(script,"turtle_goto(300,250);");
    end turtle_init;

    procedure turtle_reset is
    begin
      Put_Line(script,"turtle_reset();");
    end turtle_reset;

  procedure turtle_right(angle : Float) is
    begin
      Put_Line(script,"turtle_right(" & Float'Image(angle) & ");");
    end turtle_right;

  procedure turtle_left(angle : Float) is
    begin
      Put_Line(script,"turtle_left(" & Float'Image(angle) & ");");
    end turtle_left;
    
  procedure turtle_forward(distance : Float) is
    begin
      Put_Line(script,"turtle_forward(" & Float'Image(distance) & ");");
    end turtle_forward;
    
    procedure turtle_goto(x,y : Float) is
    begin
      Put_Line(script,"turtle_goto(" & Float'Image(x) & Float'Image(y) & ");");
    end turtle_goto;
    
    procedure turtle_clear is
    begin
      Put_Line(script,"turtle_goto();");
    end turtle_clear;
    
  procedure turtle_penup is
    begin 
      Put_Line(script,"turtle_penup();");
    end turtle_penup;
    
  procedure turtle_pendown is
    begin 
      Put_Line(script,"turtle_pendown();");
    end turtle_pendown;
    
  procedure turtle_color(r,g,b : Byte) is
    begin 
      Put_Line(script,"turtle_color("&Byte'Image(r)&','
              &Byte'Image(g)&','
                    &Byte'Image(b)&','&");");
    end turtle_color;
    
  procedure turtle_width(width : Integer) is
    begin
      Put_Line(script,"turtle_width("&Integer'Image(width)&");");
    end turtle_width;

  procedure turtle_close is
    begin
	Close(script);
    end turtle_close;

end Turtle ;

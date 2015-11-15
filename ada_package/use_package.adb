with Ada.Text_IO, Turtle;
use Ada.Text_IO, Turtle;
 
procedure use_package is
  procedure draw_side(recur : Byte; distance : Float) is
      rec : Byte;
      dist : Float;
    begin
      if (recur = 1) then
        turtle_forward(distance);
      else
        rec := recur - 1;
        dist := distance/3.0;
        draw_side(rec,dist);
        turtle_left(60.0);
        draw_side(rec,dist);
        turtle_right(120.0);
        draw_side(rec,dist);
        turtle_left(60.0);
        draw_side(rec,dist);
      end if;
    end draw_side;
begin
  turtle_init;
  draw_side(3,250.0);
  turtle_right(120.0);
  draw_side(3,250.0);
  turtle_right(120.0);
  draw_side(3,250.0);
  turtle_right(120.0);
end use_package;
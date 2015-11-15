with Ada.Text_IO ;                use Ada.Text_IO ;
with Ada.Float_Text_IO ;          use Ada.Float_Text_IO ;
with Ada.Integer_Text_IO ;        use Ada.Integer_Text_IO ;
  
procedure hello_world is
 
    --Déclaration du type de tableau numéroté de 1 à 5
  TailleMax : constant natural := 5 ;
    Type T_Tableau is array(1..TailleMax) of integer ;
 
  procedure Affich_Ligne(nb : in natural ; coucou : out Integer) is
    begin
      for j in 1..nb loop
          put('#') ;
        end loop;
        new_line ;
        coucou := 5;
    end Affich_Ligne ;
    
    function A_Rect(larg : natural ; long : natural) return natural is
  begin
    return larg * long ; 
  end A_Rect ;
 
  PI : constant Float := 3.1415926535 ;
    Taille : Float := 1.85 ;
    Poids : Float := 63.0 ;
    i : Integer := 1;
    coucou : Integer := 1;
    --On met les cases de 1 à 3 à la valeur 17
    Scores : T_Tableau := (1|2|3 => 17, others => 0) ;
begin 
   
  Put("Votre taille est de ") ;
    --On affiche du texte (package Ada.Text_IO)
  Put(Taille) ;
    --On affiche un Float (package Ada.Float_Text_IO)
  Put_Line(""); 
  Put("Votre poids est de ") ;
    --On affiche du texte (package Ada.Text_IO)
  Put(Poids) ;
    --On affiche un Float (package Ada.Float_Text_IO)
  New_line ; 
    
  for i in 1..5 loop
    Affich_Ligne(3,coucou);
    end loop;
    
  Bloc_Declaration : declare
    Ma_Variable_Supplementaire : Integer := 3; 
  begin
    --Instructions quelconques faisant intervenir
        --Ma_Variable_Supplementaire
        Put("Variable définie que dans le bloc :");
        Put(Ma_Variable_Supplementaire);
        New_Line;
    end Bloc_Declaration ; --Fin du bloc de déclaration
    
    for coucou in 1..30 loop
        Put("I =");
      Put(coucou);
        New_Line;
    end loop;
    
    if coucou = 30 then
      Put_Line("Coucou = 30");
    elsif coucou = 5 or coucou = 6 then
      Put_Line("Coucou = 5 ou 6");
    else
      Put_Line("Coucou /= 5, 6 et 30");
  end if;
    
    
  Put("Aire d'un rectangle 3x5 :");
  Put(A_Rect(3,5));
    New_Line;
    
    Put("Manipulation tableau");
    for i in Scores'Range loop
    Put(Scores(i));
    end loop;
end hello_world ;

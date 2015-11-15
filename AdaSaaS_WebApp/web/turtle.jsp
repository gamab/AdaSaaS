<%-- 
    Document   : turtle
    Created on : 15 nov. 2015, 17:31:10
    Author     : gb
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <title>turtle</title>
        <link rel="stylesheet" type="text/css" href="turtle_style.css">
        <script type="text/javascript" src="turtle_script.js"></script>
        <script type="text/javascript" src="coding_area.js"></script>

        <!--Code mirror-->
        <script src="codemirror-5.8/lib/codemirror.js"></script>
        <link rel="stylesheet" href="codemirror-5.8/lib/codemirror.css">
        <script src="codemirror-5.8/mode/ada/ada.js"></script>
    </head>
    <body>
        <canvas id="turtle" width="600" height="500" ></canvas> 
        <div id="coding_frame">
            <button onclick="save_text()"><img src="images/save.png" alt="" class="btn_img"></button> 
            <button onclick="execute_script()"><img src="images/execute.png" alt="" class="btn_img"></button> 
            <textarea id="coding_area"></textarea>
        </div>
        <textarea id="output">Output:</textarea>

        <script type="text/javascript">
            init_coding_area();
        </script>
    </body>
</html>
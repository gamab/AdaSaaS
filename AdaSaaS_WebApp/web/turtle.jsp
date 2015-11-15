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
            var myCodeMirror;
            function save_text() {
                myCodeMirror.save();
                console.log(myCodeMirror.getValue());
            }
            function execute_script() {
                var DSLScript = document.createElement("script");
                DSLScript.src = "saved_script/saved_script.js";
                DSLScript.type = "text/javascript";
                document.body.appendChild(DSLScript);
                document.body.removeChild(DSLScript);
            }

            var coding_area = document.getElementById("coding_area");

            myCodeMirror = CodeMirror.fromTextArea(coding_area, {
                lineNumbers: true
            });
            myCodeMirror.setSize(600, 470);
        </script>
    </body>
</html>

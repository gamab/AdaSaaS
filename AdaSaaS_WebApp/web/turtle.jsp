<%-- 
    Document   : turtle
    Created on : 15 nov. 2015, 17:31:10
    Author     : gb
--%>

<%@page import="System.ConsoleHelper"%>
<%@page import="java.util.UUID"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <%
        UUID id = (UUID) session.getAttribute("id");
        if (id == null) {
            id = UUID.randomUUID();
            session.setAttribute("id", id);
            System.out.println("turle.jsp : " + id.toString());
        }
        ConsoleHelper sh = (ConsoleHelper) session.getAttribute("consoleHelper");
        if (sh == null) {
            sh = new ConsoleHelper();
            session.setAttribute("consoleHelper", sh);
            System.out.println("turtle.jsp : created consoleHelper");
            if (sh.create_client_folder(id.toString())) {
                System.out.println("turtle.jsp : created clientFolder");
            } else {
                System.out.println("turtle.jsp : error could not create clientFolder");
            }
        }
    %>
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
            <button onclick="compile()"><img src="images/build.png" alt="" class="btn_img"></button> 
            <button onclick="trash()"><img src="images/trash.png" alt="" class="btn_img"></button> 
            <button onclick="execute_script()"><img src="images/execute.png" alt="" class="btn_img"></button> 
            <textarea id="coding_area"></textarea>
        </div>
        <textarea id="output">Output:</textarea>

        <script type="text/javascript">
            init_coding_area();
        </script>
    </body>
</html>

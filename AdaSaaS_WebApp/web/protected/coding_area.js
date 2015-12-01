/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var myCodeMirror;
function init_coding_area() {
    var coding_area = document.getElementById("coding_area");

    myCodeMirror = CodeMirror.fromTextArea(coding_area, {
        lineNumbers: true
    });
    myCodeMirror.setSize(600, 470);
}

function execute_script() {
    //reset du caneva
    init(600, 500);
    turtle_clear();

    var xhr = new XMLHttpRequest();
    xhr.open("GET", "execute.do", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            console.log("-----execute_script callback-----");
            console.log(xhr.responseText);
            document.getElementById("output").value = "Output:" + xhr.responseText;

            //demande du script
            var DSLScript = document.createElement("script");
            DSLScript.src = "get_script.do";
            DSLScript.type = "text/javascript";
            document.body.appendChild(DSLScript);
            document.body.removeChild(DSLScript);
        }
    }
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send(null);
    document.getElementById("output").innerHTML = "Output: Sent compile request";
}

function compileAndSave() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "compile_and_save.do", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            document.getElementById("output").value = "Output: " + xhr.responseText;
        }
    }
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send(myCodeMirror.getValue());
    document.getElementById("output").innerHTML = "Output: Sent compile request";
}

function trash() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "trash_files.do", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            console.log("-----trash callback-----");
            console.log(xhr.responseText);
            document.getElementById("output").value = "Output:" + xhr.responseText;
        }
    }
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send(null);
    document.getElementById("output").innerHTML = "Output: Sent trash request";
}

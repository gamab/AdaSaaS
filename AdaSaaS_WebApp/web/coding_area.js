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

function save_text() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "save_text.do", true);
    myCodeMirror.save();
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            document.getElementById("output").innerHTML = "Output:" + xhr.responseText;
        }
    }
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.send(myCodeMirror.getValue());
    document.getElementById("output").innerHTML = "Output: Save Request Sent"
    //console.log(myCodeMirror.getValue());
}
function execute_script() {
    //reset du caneva
    turtle_reset();

    //demande du script
    var DSLScript = document.createElement("script");
    DSLScript.src = "get_script.do";
    DSLScript.type = "text/javascript";
    document.body.appendChild(DSLScript);
    document.body.removeChild(DSLScript);
}

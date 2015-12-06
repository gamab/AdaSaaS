/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function hideWaitingElements() {
    console.log("hide");
    document.getElementById("loading").className = "hide";
    document.getElementById("loading_im").className = "hide";
}

function showWaitingElements() {
    console.log("show");
    document.getElementById("loading").className = "";
    document.getElementById("loading_im").className = "";
}

var myCodeMirror;
function init_coding_area() {
    var frame = document.getElementById("coding_frame");
    var btn = document.getElementById("btnCompile");
    var coding_area = document.getElementById("coding_area");

    myCodeMirror = CodeMirror.fromTextArea(coding_area, {
        lineNumbers: true
    });
    myCodeMirror.setSize(frame.clientWidth,frame.clientHeight-btn.clientHeight-10);
}

function execute_script() {
    //reset du caneva
    init(600, 500);
    turtle_clear();

    console.log('Execute_Script');
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "execute.do", true);
    xhr.onreadystatechange = function () {
        console.log(xhr.readyState);
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
            hideWaitingElements();
        }
    }
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.setRequestHeader('Access-Control-Allow-Origin', '*');
    xhr.send(null);
    document.getElementById("output").innerHTML = "Output: Sent compile request";
    showWaitingElements();
}

//function compileAndSave() {
//    console.log('Compile and Save');
//    var xhr = new XMLHttpRequest();
//    xhr.open("POST", "compile_and_save.do", true);
//    xhr.onreadystatechange = function () {
//        console.log(xhr.readyState);
//        if (xhr.readyState == 4) {
//            document.getElementById("output").value = "Output: " + xhr.responseText;
//        }
//    }
//    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
//    xhr.setRequestHeader('Access-Control-Allow-Origin', '*');
//    xhr.send(myCodeMirror.getValue());
//    document.getElementById("output").innerHTML = "Output: Sent compile request";
//}

function compileAndSave2() {
    console.log('Compile and Save');
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "../save_text.do", true);
    xhr.onreadystatechange = function () {
        console.log(xhr.readyState);
        if (xhr.readyState == 4) {
            document.getElementById("output").value = "Output: " + xhr.responseText;

            var xhr2 = new XMLHttpRequest();
            xhr2.open("Get", "compile.do", true);
            xhr2.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
            xhr2.send(null);
            xhr2.onreadystatechange = function () {
                console.log(xhr2.readyState);
                if (xhr2.readyState == 4) {
                    document.getElementById("output").value = "Output: " + xhr2.responseText;
                }
            }

        }
    }
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.setRequestHeader('Access-Control-Allow-Origin', '*');
    xhr.send(myCodeMirror.getValue());
    document.getElementById("output").innerHTML = "Output: Sent compile request";
}

function trash() {
    console.log('Trash');
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "trash_files.do", true);
    xhr.onreadystatechange = function () {
        console.log(xhr.readyState);
        if (xhr.readyState == 4) {
            console.log("-----trash callback-----");
            console.log(xhr.responseText);
            document.getElementById("output").value = "Output:" + xhr.responseText;
        }
    }
    xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhr.setRequestHeader('Access-Control-Allow-Origin', '*');
    xhr.send(null);
    document.getElementById("output").innerHTML = "Output: Sent trash request";
}

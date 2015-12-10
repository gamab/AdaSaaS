/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function draw_image(width, height) {
    console.log("-------draw image-------")
    var c = document.getElementById("turtle");
    var ctx = c.getContext("2d");
    var imageObj = new Image();

    imageObj.src = 'http://localhost:8080/AdaSaaS_WebApp/protected/get_image.do';
    imageObj.addEventListener('load', function () {
        console.log("loading");
        ctx.drawImage(imageObj, 0, 0);
    },false);
    //document.body.appendChild(imageObj);
    console.log("------------------------");
}
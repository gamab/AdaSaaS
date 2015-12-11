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
        /// set size proportional to image
        c.height = c.width * (imageObj.height / imageObj.width);

        /// step 1 - resize to 50%
        var oc = document.createElement('canvas'),
                octx = oc.getContext('2d');

        oc.width = imageObj.width * 0.5;
        oc.height = imageObj.height * 0.5;
        octx.drawImage(imageObj, 0, 0, oc.width, oc.height);

        /// step 2 - resize 50% of step 1
        octx.drawImage(oc, 0, 0, oc.width * 0.5, oc.height * 0.5);

        /// step 3, resize to final size
        ctx.drawImage(oc, 0, 0, oc.width * 0.5, oc.height * 0.5,
                0, 0, c.width, c.height);
    }, false);
    //document.body.appendChild(imageObj);
    console.log("------------------------");
}
(function () {
    "use strict";


    function imageResize()
    {
    var images = document.getElementById("article").querySelectorAll("img");

                for (var i = 0; i < images.length; i++) {
                    if (!images[i].hasAttribute("class")) {
                        images[i].setAttribute("class", "img-responsive");
                //       // document.write(i);
                    }
               }
    }


})();

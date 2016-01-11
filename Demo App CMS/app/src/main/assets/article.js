(function () {
    "use strict";

    // load the handlebar template and compile it
    var templateSource = document.querySelector("#article-template").innerHTML;
    var template = Handlebars.compile(templateSource);
    var container = document.querySelector(".article-content");
    var articleData = Android.getValue();
    
  

    var months = [
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    ];
    function formatDate(d) {
        return d.getDate().toString() + " " +
            months[d.getMonth()];
    }

    function loadArticle() {
    Android.showToast(articleData);
        articleData = JSON.parse(articleData);
        //articleData.PublishedDateTime = formatDate(new Date(articleData.PublishedDateTime.replace(" ", "T")));
        container.innerHTML = template(articleData);

        //var headerImage = document.querySelector(".article-container .publisher-logo");
        //headerImage.src = articleData.PublisherBlobSecondaryLogoUri;
        
         


         //force-fit images that may be too tall for the page causing vertical
         //scroll to appear
        setImmediate(function () {
            var images = document.querySelector("article").querySelectorAll("img");
           
            var maxWidth = (document.body.clientWidth * 0.9) >> 0;
            for (var i = 0; i < images.length; i++) {
                if (!images[i].hasAttribute("id")) {
                    images[i].setAttribute("width", "100%");
                    images[i].hidden(true);
                    
                }
            }

                       

        });
    }

   window.loadArticle = loadArticle;
})();

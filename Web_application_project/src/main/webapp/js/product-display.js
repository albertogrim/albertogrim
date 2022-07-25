// self executing function here
document.addEventListener('DOMContentLoaded', function(event) {
    // your page initialization code here
    // the DOM will be available here
    getProduct();
});

function getProduct() {

    var url = new URL(contextPath+'/adminData/product/');
    url.searchParams.set('operation', 'list');
    genericGETRequest(url, createProductDisplay);
}

function createProductDisplay(req){
    if (req.readyState === XMLHttpRequest.DONE) {
        if (req.status == 200) {
            var jsonData = JSON.parse(req.responseText);
            var productList = jsonData['product_list'];

            //console.log(productList[0].model);

            
            for (let i=0; i<productList.length; i++) {

                // Creating a div element
                var divElement = document.createElement("Div");
                divElement.className = "card"
                divElement.id = "divID";

                // Styling it
                divElement.style.textAlign = "center";
                divElement.style.fontWeight = "bold";
                divElement.style.fontSize = "smaller";
                divElement.style.paddingTop = "15px";
                divElement.style.alignItems = "center";


                var img = document.createElement("img");
                img.className = "cardTopImg"
                var src = productList[i].pictures;
                //console.log(src);

                img.src = "http://localhost:8080/TailorShopDEI-1.00/img/" + src;
                //console.log(img.src);
                divElement.appendChild(img);

                var divBody = document.createElement("Div");
                divBody.className = "card-body"
                divElement.appendChild(divBody);

                // Adding a Title to it
                var title = document.createElement("h5");
                var textTitle = document.createTextNode(productList[i].model);
                title.appendChild(textTitle);
                divElement.appendChild(title);


                var size = document.createElement("P");
                var textSize = document.createTextNode("Size: " + productList[i].size);
                size.appendChild(textSize);
                divElement.appendChild(size);

                var price = document.createElement("P");
                var textPrice = document.createTextNode("Price: " + productList[i].price);
                price.appendChild(textPrice);
                divElement.appendChild(price);

                var id_prodotto = productList[i].p_code;
                //console.log(id_prodotto);

                var button = document.createElement("Button");
                var textForButton = document.createTextNode("Details");
                button.appendChild(textForButton);
                button.addEventListener("click", function () {
                    //location.href='shop.jsp';
                    var form = document.createElement("form");
                    form.setAttribute("method", "get");
                    form.setAttribute("action", "shop.jsp");
                    //console.log(form);
                    var hiddenField = document.createElement("input");
                    hiddenField.setAttribute("type", "hidden");
                    hiddenField.setAttribute("name", "id");
                    hiddenField.setAttribute("value", productList[i].p_code);
                    //console.log(hiddenField);
                    form.appendChild(hiddenField)
                    button.appendChild(form);
                    form.submit();

                });
                divElement.appendChild(button);


                // Appending the div element to body
                document.getElementById("product_display").appendChild(divElement);
            }

        }
        else {
            alert("problem processing the request");
        }
    }
}
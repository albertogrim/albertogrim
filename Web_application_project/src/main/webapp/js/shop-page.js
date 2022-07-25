

// self executing function here
document.addEventListener('DOMContentLoaded', function(event) {
    // your page initialization code here
    // the DOM will be available here
    getProductList();

});

function getProductList() {

    var parametriURL=[];
    var parametri={};
    var vettore1=[];
    var vettore2=[];
    if (window.location.search.length > 0) {

        vettore1 = window.location.search;
        vettore1 = vettore1.split('?');
        vettore1 = vettore1[1];
        parametriURL = vettore1.split('&');

        for(var i = 0; i < parametriURL.length; i++) {

            vettore2 = parametriURL[i].split('=');
            parametri[i] = vettore2[1];
        }
    }

    var id = vettore2[1];

    //Richiesta GET per otterene tutti i parametri del prodotto selezionato
    var url = new URL(contextPath+'/adminData/product/');
    url.searchParams.set('operation', 'id');
    url.searchParams.set('id', id);
    console.log(url);
    genericGETRequest(url, createProductDetails);


}

function createProductDetails(req){
    if (req.readyState === XMLHttpRequest.DONE) {
        if (req.status == 200) {
            var jsonData = JSON.parse(req.responseText);
            var product = jsonData['product'];

            var img = document.createElement("img");
            var src = product.pictures;

            img.src = "http://localhost:8080/TailorShopDEI-1.00/img/" + src;
            document.getElementById("img").appendChild(img);

            var description = document.createElement("P");
            var textForDecription = document.createTextNode(product.description);
            description.appendChild(textForDecription);
            document.getElementById("ProductDesc").appendChild(description);


            var color = document.createElement("P");
            var textForColor = document.createTextNode(product.color);
            color.appendChild(textForColor);
            document.getElementById("ProductColor").appendChild(color);


            var size = document.createElement("P");
            var textForSize = document.createTextNode(product.size);
            size.appendChild(textForSize);
            document.getElementById("ProductSize").appendChild(size);

            var priceP = document.createElement("P");
            var textForPrice = document.createTextNode(product.price);
            priceP.appendChild(textForPrice);
            document.getElementById("tot_price").appendChild(priceP);

            var price = document.createElement("input");
            price.type = "hidden";
            price.value = product.price;
            price.id = "total_price";
            price.name = "total_price";

            document.getElementById("tot_price").appendChild(price);

            var p_code = document.createElement("input");
            p_code.type = "hidden";
            p_code.value = product.p_code;
            p_code.id = "p_code_product";
            p_code.name = "p_code_product";

            document.getElementById("p_code").appendChild(p_code);

        }
        else {
            alert("problem processing the request");
        }
    }
}

function showSelectedProduct(){

    var url = new URL(contextPath+'/order/');

    var sel = document.getElementById("product_selector");
    var selected_product = sel.options[sel.selectedIndex].value;
    url.searchParams.set('p_code', selected_product);

    genericGETRequest(url, fillUserData);

}

function fillUserData(req){
    if (req.readyState === XMLHttpRequest.DONE) {
        if (req.status === 200) {
            var jsonData = JSON.parse(req.responseText);
            var product = jsonData['data'];


            var p_code = sanitize(product['p_code']);
            var price = sanitize(product['price']);
            var fabric = sanitize(product['fabric']);
            var pictures = sanitize(product['pictures']);
            var size = sanitize(product['size']);
            var color = sanitize(product['color']);
            var description = sanitize(product['description']);
            var model = sanitize(product['model']);
            var out_of_stock = sanitize(product['out_of_stock']);



            var userTable = "<table>";
            userTable = userTable + "<tr><th>Product code</th><td>" + p_code + "</td></tr>";
            userTable = userTable + "<tr><th>Price</th><td>" + price + "</td></tr>";
            userTable = userTable + "<tr><th>Fabric material</th><td>" + fabric + "</td></tr>";
            userTable = userTable + "<tr><th>Pictures</th><td>" + pictures + "</td></tr>";
            userTable = userTable + "<tr><th>Size</th><td>" + size + "</td></tr>";
            userTable = userTable + "<tr><th>Color</th><td>" + color + "</td></tr>";
            userTable = userTable + "<tr><th>Description</th><td>" + description + "</td></tr>";
            userTable = userTable + "<tr><th>Model</th><td>" + model + "</td></tr>";
            userTable = userTable + "<tr><th>out of stock</th><td>" + out_of_stock + "</td></tr>";
            userTable = userTable + "</table>";
            document.getElementById("product-info").innerHTML = userTable;

        } else {
            console.log(JSON.parse(httpRequest.responseText));
            alert("Problem processing the request");
        }
    }
}


function deleteOrder(){

    var url = new URL(contextPath+'/adminData/order/');

    url.searchParams.set('id', document.getElementById('id').value);


    httpRequest = new XMLHttpRequest();


    if (!httpRequest) {
        alert('Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = deletedOrder;
    httpRequest.open('DELETE', url);
    httpRequest.send();
}

function updateOrder(){

    var url = new URL(contextPath+'/order/');
    url.searchParams.set('operation', 'insert/');
    url.searchParams.set('price', document.getElementsByName('price').value);
    httpRequest = new XMLHttpRequest();


    if (!httpRequest) {
        alert('Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = updatedOrder;
    httpRequest.open('POST', url);
    httpRequest.send();

}


function updatedOrder(){
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        if (httpRequest.status == 200) {
            alert("order updated correctly");
            location.reload();
        }
        else {
            console.log(JSON.parse(httpRequest.responseText));
            alert("problem processing the request");
        }
    }
}


function deletedOrder(){
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        if (httpRequest.status == 200) {
            alert("user deleted correctly");
            location.reload();
        }
        else {
            console.log(JSON.parse(httpRequest.responseText));
            alert("problem processing the request");
        }
    }
}
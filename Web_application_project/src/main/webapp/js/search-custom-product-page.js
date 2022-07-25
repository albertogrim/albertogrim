
// self executing function here
document.addEventListener('DOMContentLoaded', function(event) {
    // your page initialization code here
    // the DOM will be available here

    getCustomProductList();
    document.getElementById("select_customProduct").addEventListener("click", showSelectedCustomProduct);
});


function getCustomProductList() {

    var url = new URL(contextPath+'/adminData/customProduct/');
    url.searchParams.set('operation', 'list');
    genericGETRequest(url, createListSelector);
}

function createListSelector(req){
    if (req.readyState === XMLHttpRequest.DONE) {
        if (req.status == 200) {
            var jsonData = JSON.parse(req.responseText);
            var customProduct = jsonData['customProduct_list'];

            for (let i=0; i<customProduct.length; i++) {
                id = sanitize(customProduct[i]);
                currHtml =  document.getElementById("customProduct_selector").innerHTML;
                document.getElementById("customProduct_selector").innerHTML = currHtml+"<option value='"+id+"'>"+id+"</option>"
            }

        }
        else {
            alert("problem processing the request");
        }
    }
}

function showSelectedCustomProduct(){

    var url = new URL(contextPath+'/adminData/customProduct/');

    var sel = document.getElementById("customProduct_selector");
    var selected_customProduct = sel.options[sel.selectedIndex].value;
    url.searchParams.set('id', selected_customProduct);

    genericGETRequest(url, fillCustomProductData);

}

function fillCustomProductData(req){
    if (req.readyState === XMLHttpRequest.DONE) {
        if (req.status === 200) {
            var jsonData = JSON.parse(req.responseText);
            var customProduct = jsonData['data'];


            var id = sanitize(customProduct['id']);
            var model = sanitize(customProduct['model']);
            var work_time = sanitize(customProduct['work_time']);
            var fabric = sanitize(customProduct['fabric']);
            var work_type = sanitize(customProduct['work_type']);
            var size = sanitize(customProduct['size']);
            var color = sanitize(customProduct['color']);
            var customer = sanitize(customProduct['customer']);

            var userTable = "<table>";
            userTable = userTable + "<tr><th>ID</th><td>" + id + "</td></tr>";
            userTable = userTable + "<tr><th>Model</th><td>" + model + "</td></tr>";
            userTable = userTable + "<tr><th>Work time requsted</th><td>" + work_time + "</td><th>hours</th></tr>";
            userTable = userTable + "<tr><th>Fabric requested</th><td>" + fabric + "</td></tr>";
            userTable = userTable + "<tr><th>work type requested</th><td>" + work_type + "</td></tr>";
            userTable = userTable + "<tr><th>Size requested</th><td>" + size + "</td></tr>";
            userTable = userTable + "<tr><th>Color requested</th><td>" + color + "</td></tr>";
            userTable = userTable + "<tr><th>Customer</th><td>" + customer + "</td></tr>";
            userTable = userTable + "</table>";
            document.getElementById("customProduct-info").innerHTML = userTable;

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

    console.log("updating order");

    var url = new URL(contextPath+'/adminData/order/');
    url.searchParams.set('operation', 'update');
    url.searchParams.set('status', document.getElementById('status').value);
    url.searchParams.set('id', document.getElementById('id').value);
    url.searchParams.set('is_cancelled', document.getElementById('is_cancelled').value);

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
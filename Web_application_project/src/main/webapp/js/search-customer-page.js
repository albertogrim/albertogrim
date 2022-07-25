

// self executing function here
document.addEventListener('DOMContentLoaded', function(event) {
    // your page initialization code here
    // the DOM will be available here

    getCustomerList();
    document.getElementById("select_customer").addEventListener("click", showSelectedCustomer);
});


function getCustomerList() {

    var url = new URL(contextPath+'/adminData/customer/');
    url.searchParams.set('operation', 'list');
    genericGETRequest(url, createListSelector);
}

function createListSelector(req){
    if (req.readyState === XMLHttpRequest.DONE) {
        if (req.status == 200) {
            var jsonData = JSON.parse(req.responseText);
            var customer = jsonData['customer_list'];

            for (let i=0; i<customer.length; i++) {
                email = sanitize(customer[i]);
                currHtml =  document.getElementById("customer_selector").innerHTML;
                document.getElementById("customer_selector").innerHTML = currHtml+"<option value='"+email+"'>"+email+"</option>"
            }

        }
        else {
            alert("problem processing the request");
        }
    }
}




function showSelectedCustomer(){

    var url = new URL(contextPath+'/adminData/customer/');

    var sel = document.getElementById("customer_selector");
    var selected_customer = sel.options[sel.selectedIndex].value;
    url.searchParams.set('email', selected_customer);

    genericGETRequest(url, fillCustomerData);

}

function fillCustomerData(req){
    if (req.readyState === XMLHttpRequest.DONE) {
        if (req.status === 200) {
            var jsonData = JSON.parse(req.responseText);
            var customer = jsonData['data'];


            var email = sanitize(customer['email']);
            var first_name = sanitize(customer['name']);
            var last_name = sanitize(customer['surname']);
            var phone = sanitize(customer['phone']);
            var addresses = sanitize(customer['addresses']);
            var newsletter = sanitize(customer['newsletter']);
            var get_to_know = sanitize(customer['get_to_know']);
            var sizes = sanitize(customer['sizes']);
            var lifestyle = sanitize(customer['lifestyle']);

            var userTable = "<table>";
            userTable = userTable + "<tr><th>Email</th><td>"+email+"</td></tr>";
            userTable = userTable + "<tr><th>First name</th><td>"+first_name+"</td></tr>";
            userTable = userTable + "<tr><th>Last name</th><td>"+last_name+"</td></tr>";
            userTable = userTable + "<tr><th>phone</th><td>"+phone+"</td></tr>";
            userTable = userTable + "<tr><th>Address</th><td>"+addresses+"</td></tr>";
            userTable = userTable + "<tr><th>Newsletter</th><td>"+newsletter+"</td></tr>";
            userTable = userTable + "<tr><th>Get to know</th><td>"+get_to_know+"</td></tr>";
            userTable = userTable + "<tr><th>Size</th><td>"+sizes+"</td></tr>";
            userTable = userTable + "<tr><th>Lifestyle</th><td>"+lifestyle+"</td></tr>";
            userTable = userTable + "</table>";
            document.getElementById("customer-info").innerHTML = userTable;


        }
        else {
            console.log(JSON.parse(httpRequest.responseText));
            alert("Problem processing the request");
        }
    }
}


function deleteUser(){

    var url = new URL(contextPath+'/adminData/user/');

    url.searchParams.set('email', document.getElementById('email').value);


    httpRequest = new XMLHttpRequest();


    if (!httpRequest) {
        alert('Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = deletedUser;
    httpRequest.open('DELETE', url);
    httpRequest.send();
}

function updateUser(){

    console.log("updating user");

    var url = new URL(contextPath+'/adminData/user/');

    url.searchParams.set('operation', 'update');
    url.searchParams.set('email', document.getElementById('email').value);
    url.searchParams.set('name', document.getElementById('name').value);
    url.searchParams.set('surname', document.getElementById('surname').value);
    url.searchParams.set('phone', document.getElementById('phone').value);
    httpRequest = new XMLHttpRequest();


    if (!httpRequest) {
        alert('Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = updatedUser;
    httpRequest.open('POST', url);
    httpRequest.send();

}


function updatedUser(){
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        if (httpRequest.status == 200) {
            alert("user updated correctly");
            location.reload();
        }
        else {
            console.log(JSON.parse(httpRequest.responseText));
            alert("problem processing the request");
        }
    }
}


function deletedUser(){
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
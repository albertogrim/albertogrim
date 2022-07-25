

// self executing function here
document.addEventListener('DOMContentLoaded', function(event) {
    // your page initialization code here
    // the DOM will be available here

    getUsersList();
    document.getElementById("select_user").addEventListener("click", showSelectedUser);
});


function getUsersList() {

    var url = new URL(contextPath+'/adminData/user/');
    url.searchParams.set('operation', 'list');
    genericGETRequest(url, createListSelector);
}

function createListSelector(req){
    if (req.readyState === XMLHttpRequest.DONE) {
        if (req.status == 200) {
            var jsonData = JSON.parse(req.responseText);
            var emails = jsonData['emails_list'];

            for (let i=0; i<emails.length; i++) {
                email = sanitize(emails[i]);
                currHtml =  document.getElementById("user_selector").innerHTML;
                document.getElementById("user_selector").innerHTML = currHtml+"<option value='"+email+"'>"+email+"</option>"
            }

        }
        else {
            alert("problem processing the request");
        }
    }
}




function showSelectedUser(){

    var url = new URL(contextPath+'/adminData/user/');

    var sel = document.getElementById("user_selector");
    var selected_user = sel.options[sel.selectedIndex].value;
    url.searchParams.set('email', selected_user);

    genericGETRequest(url, fillUserData);

}

function fillUserData(req){
    if (req.readyState === XMLHttpRequest.DONE) {
        if (req.status === 200) {
            var jsonData = JSON.parse(req.responseText);
            var user = jsonData['data'];


            var email = sanitize(user['email']);
            var first_name = sanitize(user['name']);
            var last_name = sanitize(user['surname']);
            var phone = sanitize(user['phone']);
            var role = sanitize(user['role']);

            var userTable = "<table>";
            userTable = userTable + "<tr><th>Email</th><td>"+email+"</td></tr>";
            userTable = userTable + "<tr><th>First name</th><td>"+first_name+"</td></tr>";
            userTable = userTable + "<tr><th>Last name</th><td>"+last_name+"</td></tr>";
            userTable = userTable + "<tr><th>phone</th><td>"+phone+"</td></tr>";
            userTable = userTable + "<tr><th>Role</th><td>"+role+"</td></tr>";
            userTable = userTable + "</table>";
            document.getElementById("user-info").innerHTML = userTable;


            updateForm = "<form>";
            updateForm = updateForm + "<label for='name'>first name: </label>";
            updateForm = updateForm + "<input type='hidden' id='email' value='"+email+"'/>";
            updateForm = updateForm + "<input name='name' id='name' value='"+first_name+"' type='text'/><br/><br/>";
            updateForm = updateForm + "<label for='surname'>last name: </label>";
            updateForm = updateForm + "<input name='surname' id='surname' value='"+last_name+"' type='text'/><br/><br/>";
            updateForm = updateForm + "<label for='phone'>Phone: </label>";
            updateForm = updateForm + "<input name='phone' id='phone' value='"+phone+"' type='text'/><br/><br/>";
            updateForm = updateForm + "<button type='button' id='update'>Update</button>";
            updateForm = updateForm + "<button type='button' id='delete'>Delete</button><br/>";
            document.getElementById("user-update").innerHTML = updateForm;

            document.getElementById("update").addEventListener("click", updateUser);
            document.getElementById("delete").addEventListener("click", deleteUser);


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
            alert("Employee updated correctly");
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
            alert("Employee deleted correctly");
            location.reload();
        }
        else {
            console.log(JSON.parse(httpRequest.responseText));
            alert("problem processing the request");
        }
    }
}
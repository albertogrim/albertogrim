

// self executing function here
document.addEventListener('DOMContentLoaded', function(event) {
    // your page initialization code here
    // the DOM will be available here

    getAppointmentList();
    document.getElementById("select_appointment").addEventListener("click", showSelectedAppointment);
});


function getAppointmentList() {

    var url = new URL(contextPath+'/adminData/appointment/');
    url.searchParams.set('operation', 'list');
    genericGETRequest(url, createListSelector);
}

function createListSelector(req){
    if (req.readyState === XMLHttpRequest.DONE) {
        if (req.status == 200) {
            var jsonData = JSON.parse(req.responseText);
            var appointment = jsonData['appointment_list'];

            for (let i=0; i<appointment.length; i++) {
                id = sanitize(appointment[i]);
                currHtml =  document.getElementById("appointment_selector").innerHTML;
                document.getElementById("appointment_selector").innerHTML = currHtml+"<option value='"+id+"'>"+id+"</option>"
            }

        }
        else {
            alert("problem processing the request");
        }
    }
}

function showSelectedAppointment(){

    var url = new URL(contextPath+'/adminData/appointment/');

    var sel = document.getElementById("appointment_selector");
    var selected_appointment = sel.options[sel.selectedIndex].value;
    url.searchParams.set('id', selected_appointment);

    genericGETRequest(url, fillAppointmentData);

}

function fillAppointmentData(req){
    if (req.readyState === XMLHttpRequest.DONE) {
        if (req.status === 200) {
            var jsonData = JSON.parse(req.responseText);
            var appointment = jsonData['data'];


            var id = sanitize(appointment['id']);
            var description = sanitize(appointment['description']);
            var accepted = sanitize(appointment['accepted']);
            var schedule = sanitize(appointment['schedule']);
            var customer = sanitize(appointment['customer']);

            var userTable = "<table>";
            userTable = userTable + "<tr><th>ID</th><td>" + id + "</td></tr>";
            userTable = userTable + "<tr><th>Description</th><td>" + description + "</td></tr>";
            userTable = userTable + "<tr><th>Accepted</th><td>" + accepted + "</td></tr>";
            userTable = userTable + "<tr><th>Schedule</th><td>" + schedule + "</td></tr>";
            userTable = userTable + "<tr><th>Customer</th><td>" + customer + "</td></tr>";
            userTable = userTable + "</table>";
            document.getElementById("appointment-info").innerHTML = userTable;

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
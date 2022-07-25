
document.addEventListener('DOMContentLoaded', function(event) {
    document.getElementById("book-app").addEventListener("click", function(event) {
        animation();
        validation();
        insertAppointment();
        event.preventDefault();

    });
});

function insertAppointment(){
    // get and prepare form data
    var app_id = document.getElementById("id").value = uuidv4();
    var descr = document.getElementById("description").value;
    var acc = document.getElementById("accepted").getAttribute("value");
    var cust = document.getElementById("email").value;
    var date = document.getElementById("date").valueAsDate;
    var time = document.getElementById("time").value;
    date.setHours(time.split(":")[0]);
    date.setMinutes(time.split(":")[1]);
    date = date.toISOString();
    date = date.substring(0, 10)+" "+date.substring(11, 19);
    // create json object
    var appt = new Object();
    appt.id = app_id;
    appt.schedule = date;
    appt.description  = descr;
    appt.accepted = acc;
    appt.customer = cust;
    // send data to create a new appointment
    var url = new URL(contextPath+"/rest/appointment/"+app_id);
    var httpRequest = new XMLHttpRequest();
    httpRequest.open("POST", url);
    httpRequest.setRequestHeader("Content-Type", "application/json");
    if (!httpRequest) {
        alert('Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.send(JSON.stringify(appt));
    httpRequest.onreadystatechange = function (){ insertionDone(httpRequest);};
}

function uuidv4() {
    // generate a new uuid
    return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
        (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
    );
}

function insertionDone(req){
    if (req.readyState === XMLHttpRequest.DONE) {
        if (req.status == 200) {
            window.location.href = contextPath+"/jsp/homepage.jsp";
            console.log("appointment inserted correctly");
        }
        else {
            console.log("responseText: "+req.responseText);
        }
    }
}

function animation() {
    $(document).ready(function() {
        $( ".hamburger" ).on('click', function() {
            $(".menu").toggleClass("menu--open");
        });
    });

    ScrollReveal().reveal('.reveal',  { distance: '100px', duration: 1500, easing: 'cubic-bezier(.215, .61, .355, 1)', interval: 600 });
    ScrollReveal().reveal('.zoom',  { duration: 1500, easing: 'cubic-bezier(.215, .61, .355, 1)', interval: 200, scale: 0.65, mobile: false});

}

function validation() {
    'use strict';
    window.addEventListener('load', function() {
        // Fetch all the forms we want to apply validation styles to
        var forms = document.getElementsByClassName('need_validation');
        // Loop over them and prevent submission
        var validation = Array.prototype.filter.call(forms, function(form) {
            form.addEventListener('submit', function(event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
}
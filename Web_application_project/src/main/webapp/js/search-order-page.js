// self executing function here
document.addEventListener('DOMContentLoaded', function(event) {
    // your page initialization code here
    // the DOM will be available here
    getUsersList();
    document.getElementById("select_order").addEventListener("click", showSelectedUser);
});

function getUsersList() {

    var url = new URL(contextPath+'/adminData/order/');
    url.searchParams.set('operation', 'list');
    genericGETRequest(url, createListSelector);
}

function createListSelector(req){
    if (req.readyState === XMLHttpRequest.DONE) {
        if (req.status == 200) {
            var jsonData = JSON.parse(req.responseText);
            var order = jsonData['order_list'];
            var orderList = jsonData['order_list'];
            var orderListID = jsonData['order_list_ID'];
            var customer = [];

            var userTable= userTable = "<table>";
            userTable = userTable + "<thead>";
            userTable = userTable + "<tr>";
            userTable = userTable + "<th>Order Id</th>";
            userTable = userTable + "<th>Total price</th>";
            userTable = userTable + "<th>Address</th>";
            userTable = userTable + "<th>Payment method</th>";
            userTable = userTable + "<th>Delivery mode</th>";
            userTable = userTable + "<th>Is cancelled</th>";
            userTable = userTable + "<th>Invoice</th>";
            userTable = userTable + "<th>Customer</th>";
            userTable = userTable + "<th>Status</th>";
            userTable = userTable + "<th>Product ID</th>";
            userTable = userTable + "<th >Modifica</th>";
            userTable = userTable + "</tr>";
            userTable = userTable + "</thead>";
            userTable = userTable + "<tbody>";

            for (let i=0; i<order.length; i++) {

                var order_id = order[i].id;

                userTable = userTable + "<tr>";
                userTable = userTable + "<td>" + order_id + "</td>";
                userTable = userTable + "<td>" + order[i].tot_price + "</td>";
                userTable = userTable + "<td>" + order[i].address + "</td>";
                userTable = userTable + "<td>" + order[i].p_method + "</td>";
                userTable = userTable + "<td>" + order[i].delivery_mode + "</td>";
                userTable = userTable + "<td>" + order[i].cancelled + "</td>";
                userTable = userTable + "<td>" + order[i].invoice + "</td>";
                userTable = userTable + "<td>" + order[i].customer + "</td>";
                userTable = userTable + "<td>" + order[i].status + "</td>";
                userTable = userTable + "<td>" + order[i].productID + "</td>";
                userTable = userTable + "<td>"+  "<button type='button' id='edit' onclick='orderEdit(`"+order_id+"`)'>Edit</button>" + "</td>";
                userTable = userTable + "</tr>";

            }

            userTable = userTable + "</tbody>";
            userTable = userTable + "</table>";
            document.getElementById("order_table").innerHTML = userTable;



            for (let i=0; i<order.length; i++) {
                id = sanitize(orderList[i].customer);
                customer[i] = id;

            }

            const filteredArray = customer.filter(function(ele , pos){
                return customer.indexOf(ele) == pos;
            })

            for (let i=0; i<filteredArray.length; i++) {
                id = sanitize(filteredArray[i]);
                currHtml =  document.getElementById("order_selector").innerHTML;
                document.getElementById("order_selector").innerHTML = currHtml+"<option value='"+id+"'>"+id+"</option>";
            }

        }
        else {
            alert("problem processing the request");
        }
    }
}

function orderEdit(param){

    updateForm = "<div class=\"edit-box\">";
    updateForm = updateForm + "<input type='hidden' id='id' value='"+param+"'/>";
    updateForm = updateForm + "<input type='hidden' id='is_cancelled'/><br/>";
    updateForm = updateForm + "<label for='status'>Status: ";
                     updateForm = updateForm + "<div class = \"select\">";
    updateForm = updateForm + "<select name='status' id='status'>\n" +
        "            <option value=\"Pending\">Pending</option>\n" +
        "            <option value=\"Accepted\">Accepted</option>\n" +
        "            <option value=\"Processing\">Processing</option>\n" +
        "            <option value=\"Delivering\">Delivering</option>\n" +
        "            <option value=\"Completed\">Completed</option>\n" +
        "            <option value=\"Refunded\">Refunded</option>\n" +
        "        </select></label><br/>";
    updateForm = updateForm + "<button type='button' id='update'>Update</button> ";
    updateForm = updateForm + "<button type='button' id='delete'>Delete</button><br/>";
    document.getElementById("order-update").innerHTML = updateForm;

    document.getElementById("update").addEventListener("click", updateOrder);
    document.getElementById("delete").addEventListener("click", deleteOrder);

}



function showSelectedUser(){

    var url = new URL(contextPath+'/adminData/order/');

    var sel = document.getElementById("order_selector");
    var selected_order = sel.options[sel.selectedIndex].value;
    url.searchParams.set('customer', selected_order);

    genericGETRequest(url, fillUserData);

}

function fillUserData(req){
    if (req.readyState === XMLHttpRequest.DONE) {
        if (req.status === 200) {
            var jsonData = JSON.parse(req.responseText);
            var order = jsonData['data'];

            var userTable= userTable = "<table>";
            userTable = userTable + "<thead>";
            userTable = userTable + "<tr>";
            userTable = userTable + "<th> Product Id</th>";
            userTable = userTable + "<th>Total price</th>";
            userTable = userTable + "<th>Address</th>";
            userTable = userTable + "<th>Payment method</th>";
            userTable = userTable + "<th>Delivery mode</th>";
            userTable = userTable + "<th>Is cancelled</th>";
            userTable = userTable + "<th>Invoice</th>";
            userTable = userTable + "<th>Customer</th>";
            userTable = userTable + "<th>Status</th>";
            userTable = userTable + "<th >Modifica</th>";
            userTable = userTable + "</tr>";
            userTable = userTable + "</thead>";
            userTable = userTable + "<tbody>";

            for (let i=0; i<order.length; i++) {

                userTable = userTable + "<tr>";
                userTable = userTable + "<td>" + order[i].id + "</td>";
                userTable = userTable + "<td>" + order[i].tot_price + "</td>";
                userTable = userTable + "<td>" + order[i].address + "</td>";
                userTable = userTable + "<td>" + order[i].p_method + "</td>";
                userTable = userTable + "<td>" + order[i].delivery_mode + "</td>";
                userTable = userTable + "<td>" + order[i].is_cancelled + "</td>";
                userTable = userTable + "<td>" + order[i].invoice + "</td>";
                userTable = userTable + "<td>" + order[i].customer + "</td>";
                userTable = userTable + "<td>" + order[i].status + "</td>";
                userTable = userTable + "<td>"+  "<button type='button' id='edit' onclick='showSelectedUser()'>Edit</button>" + "</td>";
                userTable = userTable + "</tr>";

                document.getElementById("edit").addEventListener("click", showSelectedUser);


            }

            userTable = userTable + "</tbody>";
            userTable = userTable + "</table>";
            document.getElementById("order-info").innerHTML = userTable;


            updateForm = "<form>";
            updateForm = updateForm + "<input type='hidden' id='id' value='"+id+"'/>";
            updateForm = updateForm + "<label for='is_cancelled'>Is the order cancelled? </label>";
            updateForm = updateForm + "<input type='text' id='is_cancelled' value='"+is_cancelled+"'/><br/>";
            updateForm = updateForm + "<label for='status'>Status: <select name='status' id='status'>\n" +
                "            <option value=\"Pending\">Pending</option>\n" +
                "            <option value=\"Accepted\">Accepted</option>\n" +
                "            <option value=\"Processing\">Processing</option>\n" +
                "            <option value=\"Delivering\">Delivering</option>\n" +
                "            <option value=\"Completed\">Completed</option>\n" +
                "            <option value=\"Refunded\">Refunded</option>\n" +
                "        </select></label><br/>";
            updateForm = updateForm + "<button type='button' id='update'>Update</button>";
            updateForm = updateForm + "<button type='button' id='delete'>Delete</button><br/>";
            document.getElementById("order-update").innerHTML = updateForm;

            document.getElementById("update").addEventListener("click", updateOrder);
            document.getElementById("delete").addEventListener("click", deleteOrder);

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
            alert("Order updated correctly");
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
            alert("Order deleted correctly");
            location.reload();
        }
        else {
            console.log(JSON.parse(httpRequest.responseText));
            alert("problem processing the request");
        }
    }
}


function functionsearchID() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("myInput");
    filter = input.value.toUpperCase();
    table = document.getElementById("order_table");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[0];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

function functionsearchSTATUS() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("myInput2");
    filter = input.value.toUpperCase();
    table = document.getElementById("order_table");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[8];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

function functionsearchCANCELLED() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("myInput3");
    filter = input.value.toUpperCase();
    table = document.getElementById("order_table");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[7];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}
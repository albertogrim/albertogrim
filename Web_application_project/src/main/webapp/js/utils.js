var contextPath = "http://localhost:8080/TailorShopDEI-1.00"

function sanitize(str) {
    return String(str).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
}

function genericGETRequest(url, callback){
    var httpRequest = new XMLHttpRequest();

    if (!httpRequest) {
        alert('Cannot create an XMLHTTP instance');
        return false;
    }
    httpRequest.onreadystatechange = function (){ callback(httpRequest)};

    httpRequest.open('GET', url);
    httpRequest.send();
}


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Manage custom product</title>
    <meta charset="utf-8">
    <script src="${pageContext.request.contextPath}/js/utils.js"></script>
    <script src="${pageContext.request.contextPath}/js/search-custom-product-page.js"></script>
</head>
<body>
<c:choose>
    <c:when test="${!empty sessionScope.email}">
        <p>you are logged in with the email "${sessionScope.email}"</p>
        <a href="${pageContext.request.contextPath}/customer/?operation=logout">logout</a><br/>

        <h1>Search custom product</h1>
        <select name="customProduct" id="customProduct_selector"></select> <button type="button" id="select_customProduct">Search</button><br/>
        <h1>Custom product details</h1>
        <div id="customProduct-info">Select a custom product to see details</div>

    </c:when>
</c:choose>
</body>
</html>

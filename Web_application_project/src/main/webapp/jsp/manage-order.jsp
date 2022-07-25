
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Manage order</title>
    <meta charset="utf-8">
    <script src="${pageContext.request.contextPath}/js/utils.js"></script>
    <script src="${pageContext.request.contextPath}/js/search-order-page.js"></script>
</head>
<body>
<c:choose>
    <c:when test="${!empty sessionScope.email}">
        <p>you are logged in with the email "${sessionScope.email}"</p>
        <a href="${pageContext.request.contextPath}/customer/?operation=logout">logout</a><br/>

        <h1>List of ordere that are not accepted yet</h1>
        <div id ="order_table"></div>

        <h1>Search Customer's orders</h1>
        <select name="order" id="order_selector"></select> <button type="button" id="select_order">Search</button><br/>

        <h1>Order details</h1>
        <div id="order-info">Select a order to see details</div>
        <h1>Update order</h1>
        <div id="order-update">Select a order to update details</div>


    </c:when>
</c:choose>
</body>
</html>

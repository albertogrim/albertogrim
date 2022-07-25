<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Register</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-register.css">
    <script src="${pageContext.request.contextPath}/js/Registerpage.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        .source{
            text-align: center;
            color: #ffffff;
            padding: 10px;
        }
        .form-control{
            margin-top: 10px !important;
            margin-bottom: 10px !important;
        }
        body{
            background-image: url("../img/all-bg-title.jpg") !important;
        }
    </style>
</head>
<body>

<div class="container">
    <div class=form-container_reg>
    <div class=form-container>

        <div class="source">

        <div class="form-body">
            <h2 class="title">Registration</h2>
            <form method="POST" action="<c:url value="/customer/register/"/>" class="the-form">

                <body>
                <c:choose>
                    <c:when test="${message_empty.error}">
                        <p><c:out value="${message_empty.message}"/></p>
                    </c:when>
                    <c:otherwise></c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${message_mail_used.error}">
                        <p><c:out value="${message_mail_used.message}"/></p>
                    </c:when>
                    <c:otherwise></c:otherwise>
                </c:choose>

                </body>

                <div class="row">
                    <div class="col">

                        <input type="text" class="form-control" id="surname" placeholder="Enter Surname" name="surname">
                    </div>
                    <div class="col">

                        <input type="text" class="form-control" id="name" placeholder="Enter Name" name="name">
                    </div>
                </div>
                <div class="row">
                    <div class="col">

                        <input type="email" class="form-control" id="email" placeholder="Enter email" name="email">
                    </div>
                    <div class="col">

                        <input type="password" class="form-control" id="password" onkeyup='check();' placeholder="Enter Password" name="password">
                    </div>
                    <div class="col">

                        <input type="password" class="form-control" id="confirm_password" onkeyup='check();' placeholder="Confirm Password" name="confirm_password">
                            <span id='message'></span>
                    </div>
                </div>

                <div class="row">
                    <div class="col">

                        <input type="text" class="form-control" id="addresses" placeholder="Enter Address" name="addresses">
                    </div>

                    <div class="col">

                        <input type="number" class="form-control" id="phone" placeholder="Enter Phone" name="phone">
                    </div>
                </div>

                <div class="row">
                    <div class="col">

                        <input type="text" class="form-control" id="lifestyle" placeholder="Enter Lifestyle" name="lifestyle">
                    </div>
                    <div class="col">

                        <input type="text" class="form-control" id="sizes" placeholder="Enter Sizes" name="sizes">
                    </div>

                    <div class="col">

                        <textarea type="text" class="form-control" id="get_to_know" placeholder="What we need to know about you?" name="get_to_know"></textarea>
                    </div>
                </div>
                <div class="form-group form-check">
                    <label class="form-check-label">
                        <input class="form-check-input" type="checkbox"> Receive Newsletters
                    </label>
                </div>



                <div><input type="submit" style="width:350px;" value="Register"></div>

            </form>
        </div>
    </div>
    </div>
    </div>

</body>
</html>
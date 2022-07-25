<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <link href="https://fonts.googleapis.com/css2?family=Muli:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_login.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        .source{
            text-align: center;
            color: #ffffff;
            padding: 10px;
        }
    </style>
</head>
<body>

<div class="container">
    <div class=form-container>

        <div class="source">

        <div class="form-body">
            <h2 class="title">Log in with</h2>
            <div class="social-login">
                <ul>
                    <li class="google"><a href="#">Google</a></li>
                    <li class="fb"><a href="#">Facebook</a></li>
                </ul>
            </div>

            <div class="_or">or</div>

            <form method="POST" action="<c:url value="/customer/login/"/>" class="the-form">

                <body>
                <c:choose>
                    <c:when test="${message_credentials.error}">
                        <p><c:out value="${message_credentials.message}"/></p>
                    </c:when>
                    <c:otherwise></c:otherwise>
                </c:choose>
                </body>

                <label for="email">Email</label>
                <input type="email" name="email" id="email" placeholder="Enter your email">
                <c:choose>
                <c:when test="${message_email.error}">
                <p><c:out value="${message_email.message}"/></p>
                </c:when>
                    <c:otherwise></c:otherwise>
                </c:choose>

                <label for="password">Password</label>
                <input type="password" name="password" id="password" placeholder="Enter your password">
                <c:choose>
                <c:when test="${message_password.error}">
                <p><c:out value="${message_password.message}"/></p>
                </c:when>
                    <c:otherwise></c:otherwise>
                </c:choose>
                <div class="form-group form-check">
                    <label class="form-check-label">
                        <input class="form-check-input" type="checkbox"> Remember me
                    </label>
                </div>
                <input type="submit" value="Log In">

            </form>
        </div>
            <div class="form-footer">
                <div>
                    <span>Don't have an account?</span> <a href="${pageContext.request.contextPath}/jsp/register.jsp">Sign Up</a>
                </div>
            </div>
        </div>
    </div>
</div>

<c:choose>
    <c:when test="${message.error}">
        <p><c:out value="${message.message}"/></p>
    </c:when>
    <c:otherwise></c:otherwise>
</c:choose>
</body>
</html>
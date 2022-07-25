<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <title>Homepage</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css" integrity="sha512-NmLkDIU1C/C88wi324HBc+S2kLhi08PN5GDeUVVVC/BVt/9Izdsc9SVeVfA1UZbY3sHUlDSyRXhCzHfr6hmPPw==" crossorigin="anonymous" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shop-homepage.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/flickity/2.2.1/flickity.min.css" integrity="sha512-ztsAq/T5Mif7onFaDEa5wsi2yyDn5ygdVwSSQ4iok5BPJQGYz1CoXWZSA7OgwGWrxrSrbF0K85PD5uLpimu4eQ==" crossorigin="anonymous" />

    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;700;900&display=swap" rel="stylesheet">

    <script src="https://unpkg.com/scrollreveal@4.0.0/dist/scrollreveal.min.js"></script>

    <script src="${pageContext.request.contextPath}/js/utils.js"></script>
    <script src="${pageContext.request.contextPath}/js/product-display.js"></script>

    <!-- Font Awesome -->
    <link
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css"
            rel="stylesheet"
    />



    <style>
        img{
            max-height: 250px;
            width: auto;
        }
        .card-deck{
            margin: 10px !important;
        }
    </style>

</head>
<body>



        <c:choose>

            <c:when test="${!empty sessionScope.email}">
                <div class="header">
                    <ul class="menu">
                        <li><a href="${pageContext.request.contextPath}/jsp/homepage.jsp">Home</a></li>
                        <li><a href="${pageContext.request.contextPath}/jsp/shop-homepage.jsp">Shop</a></li>
                        <li><a href="">Welcome "${sessionScope.email}"</a></li>
                    </ul>

                    <div class="cta">
                        <a href="${pageContext.request.contextPath}/order/?operation=logout" class="button"> Logout </a>
                    </div>

                    <div class="hamburger">
                        <span></span>
                        <span></span>
                        <span></span>
                    </div>
                </div>
            </c:when>

            <c:otherwise>
                <div class="header">
                    <ul class="menu">
                        <li><a href="${pageContext.request.contextPath}/jsp/homepage.jsp">Home</a></li>
                        <li><a href="${pageContext.request.contextPath}/jsp/shop-homepage.jsp">Shop</a></li>
                    </ul>


                    <div class="cta">
                        <a href="${pageContext.request.contextPath}/jsp/login.jsp" class="button"> Login </a>
                    </div>

                    <div class="hamburger">
                        <span></span>
                        <span></span>
                        <span></span>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
        <div id="homepage-content">

            <div class="container">

                <div class="grid-container" id="product_display"></div>

            </div>
        </div>

    <footer class="footer">
        <div class="grid">
            <div class="col reveal">
                <h4 class="normal-text tw">About us</h4>
                <p class="footer-text">Find out more about us</p>
            </div>
            <div class="col reveal">
                <h4 class="normal-text tw">Where to find us</h4>
                <p class="footer-text">Via Monte Napoleone, 125, Padova, Italy</p>
            </div>

            <div class="col reveal">
                <h4 class="normal-text tw">Follow us on the social</h4>
                <section class="mb-4">

                    <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"
                    ><i style="color: white;" class="fab fa-facebook-f"></i></a>


                    <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"
                    ><i style="color: white;" class="fab fa-twitter"></i></a>


                    <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"
                    ><i style="color: white;" class="fab fa-google"></i></a>


                    <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"
                    ><i style="color: white;" class="fab fa-instagram"></i></a>
                </section>
            </div>

            <div class="col reveal">
                <h4 class="normal-text tw">Contact us</h4>
                <p class="footer-text">Phone: 049 5566789</p>
                <p class="footer-text">Email: tailorShopDEI@unipd.it</p>
            </div>
        </div>
    </footer>

</body>
</html>
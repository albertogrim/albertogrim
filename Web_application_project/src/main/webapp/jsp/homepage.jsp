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

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_homepage.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/flickity/2.2.1/flickity.min.css" integrity="sha512-ztsAq/T5Mif7onFaDEa5wsi2yyDn5ygdVwSSQ4iok5BPJQGYz1CoXWZSA7OgwGWrxrSrbF0K85PD5uLpimu4eQ==" crossorigin="anonymous" />

    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;700;900&display=swap" rel="stylesheet">

    <!-- Font Awesome -->
    <link
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css"
            rel="stylesheet"
    />
    <!-- Google Fonts -->
    <link
            href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            rel="stylesheet"
    />
    <!-- MDB -->
    <link
            href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.3.0/mdb.min.css"
            rel="stylesheet"
    />

    <script src="https://unpkg.com/scrollreveal@4.0.0/dist/scrollreveal.min.js"></script>

    <script src="${pageContext.request.contextPath}/js/utils.js"></script>
    <script src="${pageContext.request.contextPath}/js/homepage.js"></script>
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

        </div>

        <div class="hero">
            <div class="hero__content reveal">
                <p class="intro-text">a new way of experiencing tailoring</p>
                <h1 class="big-text">Tailor Shop DEI</h1>
                <a href="${pageContext.request.contextPath}/jsp/single.jsp" class="button">Discover More About Us</a>
            </div>
            <video autoplay muted loop id="video-back">
                <source src="${pageContext.request.contextPath}/img/video-back-1.mp4" type="video/mp4">
            </video>
        </div>



        <div class="poster mt-3">
            <div class="poster__img__double reveal">
                <img src="${pageContext.request.contextPath}/img/men-shop.jpg" alt="">
                <img src="${pageContext.request.contextPath}/img/woman-shop.jpg" alt="">
            </div>
            <div class="poster__content reveal">
                <h3 class="big-text">Online Shop</h3>
                <p>For many years we have been producing high-quality garments for every occasion. Whether it is a ceremony or a simple party with friends, you will find everything you need here. </p>
                <a href="${pageContext.request.contextPath}/jsp/shop-homepage.jsp" class="button"> Buy now</a>
            </div>
        </div>


        <div class="poster mt-3">

            <div class="poster__content reveal">
                <h3 class="big-text">Custom creation</h3>
                <p>In our store we offer the possibility to create what you need from scratch. Describe it to us and we will contact you as soon as possible. If you already know how we work, book your appointment in our shop. </p>
                <a href="${pageContext.request.contextPath}/jsp/create-product.jsp" class="button"> Go now</a>
            </div>

            <div class="poster__img reveal">
                <img src="${pageContext.request.contextPath}/img/custom-creation.jpg" alt="">
            </div>
        </div>

        <div class="poster mt-3">
            <div class="poster__img reveal">
                <img src="${pageContext.request.contextPath}/img/calendar.jpeg" alt="">
            </div>
            <div class="poster__content reveal">
                <h3 class="big-text">Book an appointment</h3>
                <p>For all your needs we are here, book your appointment and come to the shop. </p>
                <a href="${pageContext.request.contextPath}/jsp/book-appointment.jsp" class="button"> Book now</a>
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
                        ><i class="fab fa-facebook-f"></i></a>


                        <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"
                        ><i class="fab fa-twitter"></i></a>


                        <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"
                        ><i class="fab fa-google"></i></a>


                        <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"
                        ><i class="fab fa-instagram"></i></a>
                    </section>
                </div>

                <div class="col reveal">
                    <h4 class="normal-text tw">Contact us</h4>
                    <p class="footer-text">Phone: 049 5566789</p>
                    <p class="footer-text">Email: tailorShopDEI@unipd.it</p>
                </div>
            </div>
        </footer>

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


        </div>

        <div class="hero">
            <div class="hero__content reveal">
                <p class="intro-text">a new way of experiencing tailoring</p>
                <h1 class="big-text">Tailor Shop DEI</h1>
                <a href="${pageContext.request.contextPath}/jsp/single.jsp" class="button">Discover More About Us</a>
            </div>
            <video autoplay muted loop id="video-back-1">
                <source src="${pageContext.request.contextPath}/img/video-back-1.mp4" type="video/mp4">
            </video>
        </div>



        <div class="poster mt-3">
            <div class="poster__img__double reveal">
                <img src="${pageContext.request.contextPath}/img/men-shop.jpg" alt="">
                <img src="${pageContext.request.contextPath}/img/woman-shop.jpg" alt="">
            </div>
            <div class="poster__content reveal">
                <h3 class="big-text">Online Shop</h3>
                <p>For many years we have been producing high-quality garments for every occasion. Whether it is a ceremony or a simple party with friends, you will find everything you need here. </p>
                <a href="${pageContext.request.contextPath}/jsp/shop-homepage.jsp" class="button"> Buy now</a>
            </div>
        </div>


        <div class="poster mt-3">

            <div class="poster__content reveal">
                <h3 class="big-text">Custom creation</h3>
                <p>In our store we offer the possibility to create what you need from scratch. Describe it to us and we will contact you as soon as possible. If you already know how we work, book your appointment in our shop. </p>
                <a href="${pageContext.request.contextPath}/jsp/create-product.jsp" class="button"> Go now</a>
            </div>

            <div class="poster__img reveal">
                <img src="${pageContext.request.contextPath}/img/custom-creation.jpg" alt="">
            </div>
        </div>

        <div class="poster mt-3">
            <div class="poster__img reveal">
                <img src="${pageContext.request.contextPath}/img/calendar.jpeg" alt="">
            </div>
            <div class="poster__content reveal">
                <h3 class="big-text">Book an appointment</h3>
                <p>For all your needs we are here, book your appointment and come to the shop. </p>
                <a href="${pageContext.request.contextPath}/jsp/book-appointment.jsp" class="button"> Book now</a>
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
                        ><i class="fab fa-facebook-f"></i></a>


                        <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"
                        ><i class="fab fa-twitter"></i></a>


                        <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"
                        ><i class="fab fa-google"></i></a>


                        <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button"
                        ><i class="fab fa-instagram"></i></a>
                    </section>
                </div>

                <div class="col reveal">
                    <h4 class="normal-text tw">Contact us</h4>
                    <p class="footer-text">Phone: 049 5566789</p>
                    <p class="footer-text">Email: tailorShopDEI@unipd.it</p>
                </div>
            </div>
        </footer>
    </c:otherwise>
</c:choose>

<div id="homepage-content"></div>


</body>
</html>
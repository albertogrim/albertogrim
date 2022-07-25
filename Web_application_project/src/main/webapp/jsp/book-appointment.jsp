
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

    <head>
        <title>Book appointment</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css" integrity="sha512-NmLkDIU1C/C88wi324HBc+S2kLhi08PN5GDeUVVVC/BVt/9Izdsc9SVeVfA1UZbY3sHUlDSyRXhCzHfr6hmPPw==" crossorigin="anonymous" />
        <link rel="stylesheet" href="../css/style_homepage.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/flickity/2.2.1/flickity.min.css" integrity="sha512-ztsAq/T5Mif7onFaDEa5wsi2yyDn5ygdVwSSQ4iok5BPJQGYz1CoXWZSA7OgwGWrxrSrbF0K85PD5uLpimu4eQ==" crossorigin="anonymous" />
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;700;900&display=swap" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" rel="stylesheet"/>
        <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet"/>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.3.0/mdb.min.css" rel="stylesheet"/>
        <script src="https://unpkg.com/scrollreveal@4.0.0/dist/scrollreveal.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/utils.js"></script>
        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" rel="stylesheet"/>
    </head>

    <body>
        <c:choose>
            <c:when test="${!empty sessionScope.email}">
                <div class="header">
                    <div class="logo">
                        <img src="../img/tsd.jpg" alt="" id="logo">
                    </div>
                    <ul class="menu">
                        <li><a href="${pageContext.request.contextPath}/jsp/homepage.jsp">Home</a></li>
                        <li><a href="${pageContext.request.contextPath}/jsp/shop-homepage.jsp">Shop</a></li>
                        <li><a href="">Welcome "${sessionScope.email}"</a></li>
                    </ul>
                    <div class="hamburger">
                        <span></span>
                        <span></span>
                        <span></span>
                    </div>
                </div>

                <div class="hero hero--small">
                    <div class="hero__content"></div>
                </div>

                <div class="content">
                    <h3 class="normal-text">Book an appointment now!</h3>
                    <p>If you need a restoration or a modification of your clothes fill in the following form and book an appointment at our shop.<br>
                        We are specialized in all types of garments: dresses, jackets, coats, high fashion garments. We offer a professional service for individuals, stores and boutiques. We give a second chance of life to damaged garments and a new look to intact ones, according to your wishes.
                        <br></p>
                    <form class="need_validation" method="POST" action="">
                        <div class="row">
                            <div class="col">
                                <input type="text" value="${sessionScope.email}" class="form-control" placeholder="Email" maxlength="256" required id="email" name="customer">
                                <div class="invalid-feedback">Please insert a valid email.</div>
                            </div>
                            <input type="hidden" id="id" name="id" value="">
                            <input type="hidden" id="accepted" name="accepted" value="false">
                        </div>
                        <div class="form-group">
                            <textarea class="form-control" rows="3" placeholder="Insert a brief description of your needs." id="description" name="description"></textarea>
                        </div>
                        <div class="form-group" id="date_text">
                            <label for="schedule_date">Choose day and time for your appointment:&emsp;</label>
                            <input type="date" name="schedule" class="schedule" required id="date">&emsp;
                            <input type="time" name="schedule" class="schedule" required id="time">
                        </div>
                        <button type="submit" class="btn btn-primary" id="book-app">Book</button>
                    </form>
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

                <script src="${pageContext.request.contextPath}/js/book-appointment-page.js"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
                <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/flickity/2.2.1/flickity.pkgd.min.js" integrity="sha512-Nx/M3T/fWprNarYOrnl+gfWZ25YlZtSNmhjHeC0+2gCtyAdDFXqaORJBj1dC427zt3z/HwkUpPX+cxzonjUgrA==" crossorigin="anonymous"></script>

            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>
    </body>

</html>

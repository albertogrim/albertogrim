
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>TailorShopDEI - Create product</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css" integrity="sha512-NmLkDIU1C/C88wi324HBc+S2kLhi08PN5GDeUVVVC/BVt/9Izdsc9SVeVfA1UZbY3sHUlDSyRXhCzHfr6hmPPw==" crossorigin="anonymous" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_appointment.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/flickity/2.2.1/flickity.min.css" integrity="sha512-ztsAq/T5Mif7onFaDEa5wsi2yyDn5ygdVwSSQ4iok5BPJQGYz1CoXWZSA7OgwGWrxrSrbF0K85PD5uLpimu4eQ==" crossorigin="anonymous" />
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;700;900&display=swap" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.3.0/mdb.min.css" rel="stylesheet"/>
    <script src="https://unpkg.com/scrollreveal@4.0.0/dist/scrollreveal.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/utils.js"></script>

    <!-- Font Awesome -->
    <link
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css"
            rel="stylesheet"
    />



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

        <div class="hero hero--small">
            <div class="hero__content">
            </div>
        </div>

        <div class="content">
            <h3 class="normal-text">Create your product now!</h3>
            <p>If you need a made-to-measure garment, please fill in the following form to provide us with all the characteristics of your desire. We will contact you as soon as possible.</p>
            <form method="POST" action="<c:url value="/customProduct/insert/"/>">

                <body>
                <c:choose>
                    <c:when test="${message_empty.error}">
                        <p><c:out value="${message_empty.message}"/></p>
                    </c:when>
                    <c:otherwise></c:otherwise>
                </c:choose>
                </body>

                <div class="row">
                    <div class="col">
                        <label for="model"></label>
                        <input name="model" type="text" class="form-control" placeholder="Model"/>
                    </div>
                    <div class="col">
                        <label for="work_time"></label>
                        <input name="work_time" type="text"class="form-control" placeholder="In how much time do you want your product? Set it in hours"/><br/><br/>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <label for="fabric"></label>
                        <input name="fabric" type="text" class="form-control" placeholder="Fabric"/><br/><br/>
                    </div>
                    <div class="col">
                        <label for="work_type">Work type:</label><select name='work_type' class="form-control">
                        <option value="From scratch">From scratch</option>
                        <option value="Tailor">Tailor</option>
                    </select><br/>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <label for="size"></label>
                        <input name="size" type="text" class="form-control" placeholder="Size"/><br/><br/>
                    </div>
                    <div class="col">
                        <label for="color"></label>
                        <input name="color" type="text" class="form-control" placeholder="Example: #FF671A"/><br/><br/>
                    </div>

                    <div class="col">
                        <label for="customer"></label>
                        <input name="customer" value="${sessionScope.email}" class="form-control"/><br/><br/>
                    </div>
                </div>

                <button type="submit" class="btn btn-info">Book</button>
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

    </c:when>

    <c:otherwise>

    </c:otherwise>
</c:choose>

<div id="homepage-content"></div>
</body>
</html>

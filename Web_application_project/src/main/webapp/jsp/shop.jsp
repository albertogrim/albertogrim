<%--
  Created by IntelliJ IDEA.
  User: lucafriso
  Date: 14/04/21
  Time: 22:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <title>Homepage</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css" integrity="sha512-NmLkDIU1C/C88wi324HBc+S2kLhi08PN5GDeUVVVC/BVt/9Izdsc9SVeVfA1UZbY3sHUlDSyRXhCzHfr6hmPPw==" crossorigin="anonymous" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/shop-details.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/flickity/2.2.1/flickity.min.css" integrity="sha512-ztsAq/T5Mif7onFaDEa5wsi2yyDn5ygdVwSSQ4iok5BPJQGYz1CoXWZSA7OgwGWrxrSrbF0K85PD5uLpimu4eQ==" crossorigin="anonymous" />

    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;700;900&display=swap" rel="stylesheet">

    <script src="https://unpkg.com/scrollreveal@4.0.0/dist/scrollreveal.min.js"></script>

    <script src="${pageContext.request.contextPath}/js/utils.js"></script>
    <script src="${pageContext.request.contextPath}/js/shop-page.js"></script>

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
            </c:otherwise>
        </c:choose>

        <div id="homepage-content">
            <div class="container">

            <div class="cart" style="border: 1px solid #8c8c8c;border-radius: 10px;background-color: #fff; margin-top: inherit;">
                <div class="cart-body">
                    <div class="row" style="padding: 30px">
                        <div class="col-lg-4 col-md-6" id="img"></div>

                        <div class="col-lg-8 col-md-6">
                            <form method="POST" action="<c:url value="/order/insert/"/>">

                                <div class="row">

                                    <div class="form-group">
                                        <label for="ProductDesc">Product Description</label>
                                        <p id="ProductDesc" ></p>
                                    </div>

                                </div>

                                <div class="row">
                                    <div class="col">
                                        <div class="form-group">
                                            <label for="ProductColor">Color</label>
                                            <p id="ProductColor" ></p>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="form-group">
                                            <label for="ProductSize">Size</label>
                                            <p id="ProductSize"></p>
                                        </div>
                                    </div>
                                </div>


                                <div class="row">
                                    <div class="col">
                                        <div class="form-group">
                                            <label for="delivery_mode">Delivery Mode</label>
                                            <select class="form-control" id="delivery_mode" name="delivery_mode">
                                                <option value="Home">Home</option>
                                                <option value="Shop">Shop</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="form-group">
                                            <label for="p_method">Payment Type</label>
                                            <select class="form-control" id="p_method" name="p_method">
                                                <option value="Cash">Cash</option>
                                                <option value="Credit/debit card">Credit/debit card</option>
                                                <option value="Bank transfer">Bank transfer</option>
                                                <option value="PayPal">PayPal</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <div class="form-group">
                                            <label for="address">Address</label>
                                            <input type="text" class="form-control" name="address" id="address">
                                        </div>
                                    </div>
                                </div>

                                <label for="tot_price" id="tot_price">Price: </label>

                                <label for="customer" ></label>
                                <input name="customer" value="${sessionScope.email}" type="hidden" id="customer"/><br/><br/>

                                <label for="p_code" id="p_code"></label>

                                <br/><br/><button type="submit" class="btn btn-info mb-2">Buy</button>


                            </div>
                        </form>

                    </div>
                </div>
            </div>
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

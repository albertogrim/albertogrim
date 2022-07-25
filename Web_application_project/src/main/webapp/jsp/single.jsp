
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>More about us</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css" integrity="sha512-NmLkDIU1C/C88wi324HBc+S2kLhi08PN5GDeUVVVC/BVt/9Izdsc9SVeVfA1UZbY3sHUlDSyRXhCzHfr6hmPPw==" crossorigin="anonymous" />

    <link rel="stylesheet" href="../css/style_homepage.css">

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

</head>
<body>

<div class="header">
    <ul class="menu">
        <li><a href="${pageContext.request.contextPath}/jsp/homepage.jsp">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/jsp/shop-homepage.jsp">Shop</a></li>
    </ul>

    <div class="hamburger">
        <span></span>
        <span></span>
        <span></span>
    </div>
</div>
<div class="hamburger">
    <span></span>
    <span></span>
    <span></span>
</div>
</div>

<div class="hero hero--small">
    <div class="hero__content">
        <p class="intro-text">A new way of experiencing tailoring</p>
        <h1 class="big-text">Tailor Shop DEI</h1>
    </div>
</div>




<div class="content">
    <h3 class="normal-text">About us</h3>
    <p>Since 1969 we have been taking care of the well-being of our customers by creating tailor-made garments.</p>

    <p>We use the finest fabrics to best meet the demands of our customers and offer a unique user experience.</p>
    <p>Attention to detail is our strength, it is what makes the difference. Our artisans and artisans are the best in this, obtaining a high quality of the finished product.</p>
    <p>Visit our shop, and for any advice do not hesitate to contact us, we will be happy to guide you in choosing the best product that can meet your needs.</p>


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

<!-- Jquery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/flickity/2.2.1/flickity.pkgd.min.js" integrity="sha512-Nx/M3T/fWprNarYOrnl+gfWZ25YlZtSNmhjHeC0+2gCtyAdDFXqaORJBj1dC427zt3z/HwkUpPX+cxzonjUgrA==" crossorigin="anonymous"></script>

<script>
    $( document ).ready(function() {

        /* Open Panel */
        $( ".hamburger" ).on('click', function() {
            $(".menu").toggleClass("menu--open");
        });

    });
</script>


</body>
</html>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Manager page</title>
  <meta charset="utf-8">
  <script src="${pageContext.request.contextPath}/js/utils.js"></script>
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <!-- Bootstrap -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
  <!-- Material Icons -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mdi.min.css">
  <!-- Web Sidebar -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sidebar_manage.css">
  <!-- Web Sidebar -->
  <script src="https://unpkg.com/scrollreveal@4.0.0/dist/scrollreveal.min.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_manage.css">
  <!-- img-->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

  <script src="${pageContext.request.contextPath}/js/search-appointment-page.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style-manage-customer.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_manager.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_search.css">


</head>
<body class="fix-topbar fix-sidebar">
<c:choose>
  <c:when test="${!empty sessionScope.email}">

    <!-- Main Wrapper -->
    <div class="main-wrapper">
      <!-- TopBar -->
      <header class="topbar">
        <nav class="navbar top-navbar navbar-expand-md navbar-light" style="background-color: #333333;">

          <!-- Logo -->
          <div class="navbar-header">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/jsp/manager-page.jsp">
              <!-- Logo icon -->
              <b>
                <img src="${pageContext.request.contextPath}/img/logo/logo.jpg" alt="Icon" class="logo"
                     style="width: 4rem" />
              </b>
            </a>
          </div>

          <div class="navbar-collapse">
            <!-- Toggle SideBar-->
            <ul class="navbar-nav mr-auto mt-md-0">
              <li class="nav-item"> <a class="nav-link nav-toggler hidden-md-up " href="javascript:void(0)"><i class="mdi mdi-menu"></i></a> </li>
              <li class="nav-item"> <a class="nav-link sidebartoggler hidden-sm-down " href="javascript:void(0)"><i class="mdi mdi-menu"></i></a> </li>
            </ul>
          </div>
        </nav>
      </header>

      <!-- SideBar -->
      <aside class="left-sidebar">

        <div class="scroll-sidebar">

          <nav class="sidebar-nav">

            <ul id="sidebarnav">

              <li >
                <a class="nav-link" href="${pageContext.request.contextPath}/jsp/employee-page.jsp" aria-expanded="false" >
                  <i class="fa fa-home" aria-hidden="true"></i><span class="hide-menu"> EMPLOYEE AREA </span>
                </a>
              </li>

              <hr class="sidebar-divider my-0">
              <li >
                <a class="has-arrow" href="#" aria-expanded="false">
                  <i class="fa fa-bars" aria-hidden="true"></i><span class="hide-menu"> Manage Order </span>
                </a>
                <ul aria-expanded="false" class="collapse">
                  <li>
                    <a href="${pageContext.request.contextPath}/jsp/employee-order-all-list.jsp">
                      <i class="mdi mdi-numeric-1-box"></i>
                      List All Order
                    </a>
                  </li>

                  <li>
                    <a href="${pageContext.request.contextPath}/jsp/employee-page.jsp">
                      <i class="mdi mdi-numeric-2-box"></i>
                      Search Customer
                    </a>
                  </li>

                </ul>
              </li>
              <hr class="sidebar-divider my-0">
              <li class= active>
                <a class="has-arrow" href="#" aria-expanded="false">
                  <i class="fa fa-calendar" aria-hidden="true"></i><span class="hide-menu"> Appointment </span>
                </a>
                <ul aria-expanded="false" class="collapse">
                  <li class= active>
                    <a href="${pageContext.request.contextPath}/jsp/employee-manage-appointment.jsp">
                      <i class="mdi mdi-numeric-1-box"></i>
                      Search appointment
                    </a>
                  </li>

                  </li>
                </ul>
              </li>
            </ul>
          </nav>
        </div>
      </aside>


      <div class="page-wrapper">

        <div class="wrapper">
          <div class="header">
            <div class="title">
              Search appointment
            </div>

            <div class="center">
              <div>
                <select  class="form-control" name="appointment" id="appointment_selector"></select> <button type="button" id="select_appointment">Search</button><br>

              </div>
            </div>

          </div>
          <h3>Appointment details</h3>
          <div id="appointment-info">Select an appointment to see details</div>

        </div>

      </div>


    </div>


    </div>



    <!-- Jquery -->
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <!-- Bootstrap -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
    <!-- StikyKit -->
    <script src="https://cdn.rawgit.com/leafo/sticky-kit/v1.1.2/jquery.sticky-kit.min.js"></script>
    <!-- Metismenu -->
    <script src="${pageContext.request.contextPath}/js/metismenu/metisMenu.min.js"></script>
    <!-- SlimScroll -->
    <script src="${pageContext.request.contextPath}/js/slimscroll/slimscroll.min.js"></script>
    <!-- Web Sidebar -->
    <script src="${pageContext.request.contextPath}/js/sidebar.js"></script>

  </c:when>
</c:choose>

</body>
</html>







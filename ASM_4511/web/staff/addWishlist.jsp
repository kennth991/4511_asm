<%-- 
    Document   : index
    Created on : 2024年4月2日, 下午2:25:46
    Author     : Lau Ka Ming Benjamin
--%>

<%@page import="ict.bean.User"%>
<%@page import="ict.bean.WishListEquipmentBean"%>
<%@page import="ict.servlet.WishListServlet"%>
<%@page import="ict.servlet.AddWishListServlet"%>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    //User user = (User) session.getAttribute("technician");
    //if (user == null) {
    //  response.sendRedirect(request.getContextPath() + "/login.jsp");
    //return;
    //}
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Technician Dashboard</title>
        <link href="<c:url value='/assets/vendor/fontawesome/css/fontawesome.min.css' />" rel="stylesheet">
        <link href="<c:url value='/assets/vendor/fontawesome/css/solid.min.css' />" rel="stylesheet">
        <link href="<c:url value='/assets/vendor/bootstrap/css/bootstrap.min.css' />" rel="stylesheet">
        <link href="<c:url value='/assets/vendor/datatables/datatables.min.css' />" rel="stylesheet">
        <link href="<c:url value='/assets/css/master.css' />" rel="stylesheet">

    </head>

    <body>
        <div class="wrapper">
            <nav id="sidebar" class="active">
                <div class="sidebar-header">
                    <img src="${pageContext.request.contextPath}/assets/img/logo.png" style="height: 60px; width: 60px;" alt="bootraper logo" class="app-logo">
                </div>
                <ul class="list-unstyled components text-secondary">
                    <li>
                        <a href="${pageContext.request.contextPath}/BorrowingRecordServlet"><i class="fas fa-home"></i> Dashboard</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/user/view_devices"><i class="fas fa-laptop"></i> View Devices</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/user/view_venue"><i class="fas fa-history"></i> View Venue</a>
                    </li>
                    <li>
                        <a href="WishListServlet"><i class="fas fa-heart"></i> Wish List</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/user/return_checkout.jsp"><i class="fas fa-hand-holding"></i>Return / Checkout</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/user/UserProfile.jsp"><i class="fas fa-user-cog"></i> Personal Information</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/LogoutServlet"> <i class="fas fa-sign-out-alt"></i> Logout</a>
                    </li>
                </ul>
            </nav>
            <div id="body" class="active">
                <nav class="navbar navbar-expand-lg navbar-white bg-white">
                    <button type="button" id="sidebarCollapse" class="btn btn-light">
                        <i class="fas fa-bars"></i><span></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="nav navbar-nav ms-auto">
                            <li class="nav-item dropdown">
                            </li>
                            <li class="nav-item dropdown">
                                <div class="nav-dropdown">
                                    <a href="#" id="nav2" class="nav-item nav-link dropdown-toggle text-secondary" data-bs-toggle="dropdown" aria-expanded="false">
                                        <i class="fas fa-user"></i> <span>${staff.name}</span> <i style="font-size: .8em;" class="fas fa-caret-down"></i>
                                    </a>
                                    <div class="dropdown-menu dropdown-menu-end nav-link-menu">
                                        <ul class="nav-list">
                                            <li><a href="profile.html" class="dropdown-item"><i class="fas fa-address-card"></i> Profile</a></li>
                                            <div class="dropdown-divider"></div>
                                            <li><a href="index.html" class="dropdown-item"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </nav>
                <div class="content">
                    <div class="container">
                        <div class="page-title">
                            <div class="row">
                                <div class="col">
                                    <div class="row">
                                        <div class="col">
                                            <h3>Technician Dashboard</h3>
                                        </div>

                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 col-lg-12">
                            <div class="card">
                                <div class="card-header">Wish List(Available)</div>
                                <div class="card-body">
                                    <p class="card-title"></p>
                                    <table class="table table-hover" id="dataTables-example" width="100%">
                                        <thead>
                                            <tr>

                                                <th>Serial Number</th>
                                                <th>Equipment</th>

                                                <th>Location</th>

                                                <th>Status</th>
                                                <th>Add To Wish List</th>

                                            </tr>
                                        </thead>

                                        <tbody>
                                            <%
                                                // Retrieve the "equipments" attribute from the request
                                                ArrayList<WishListEquipmentBean> wishEquipmentsApproved = (ArrayList<WishListEquipmentBean>) request.getAttribute("wishListAdd");
                                            %>

                                            <%-- Use JSP scriptlet to iterate over the list of equipment objects --%>
                                            <%
                                                for (WishListEquipmentBean wishEquipmentApproved : wishEquipmentsApproved) {%>
                                            <tr>


                                                <td><%= wishEquipmentApproved.getEquipmentequipmentID()%></td>
                                                <td><%= wishEquipmentApproved.getEquipmentName()%></td>
                                                <td><%= wishEquipmentApproved.getLocation()%></td>


                                                <td><%= wishEquipmentApproved.getStatus()%></td>
                                                <td>


                                                    <form action="AddWishList" method="get">
                                                        <input type="hidden" name="action" value="AddWishList"> <!-- Added hidden field for action -->
                                                        <button type="submit" class="btn btn-warning btn-sm">
                                                            <i class="fas fa-plus"></i>

                                                            <input type="hidden" name="equipmentID" id="equipmentID" value="<%= wishEquipmentApproved.getEquipmentequipmentID()%>">
                                                        </button>
                                                    </form>
                                                </td>
                                            </tr>
                                            <%

                                                }%>
                                        </tbody>
                                    </table>

                                </div>
                                <!-- Modal -->


                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>

    </script>
    <script src="<c:url value='/assets/vendor/jquery/jquery.min.js' />"></script>
    <script src="<c:url value='/assets/vendor/bootstrap/js/bootstrap.bundle.min.js' />"></script>
    <script src="<c:url value='/assets/js/script.js' />"></script>
</body>

</html>
<%-- 
    Document   : index
    Created on : 2024年4月2日, 下午2:25:40
    Author     : kenneth
--%>
<%@page import="ict.bean.User"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    // Placeholder for devices and records, replace with actual calls to your backend logic
    List<?> devices = (List<?>) request.getAttribute("devices");
    List<?> borrowingRecords = (List<?>) request.getAttribute("borrowingRecords");
%>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>User Dashboard</title>
        <link href="../assets/vendor/fontawesome/css/fontawesome.min.css" rel="stylesheet">
        <link href="../assets/vendor/fontawesome/css/solid.min.css" rel="stylesheet">
        <link href="../assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="../assets/vendor/datatables/datatables.min.css" rel="stylesheet">
        <link href="../assets/css/master.css" rel="stylesheet">
    </head>

    <body>
        <div class="wrapper">
            <nav id="sidebar" class="active">
                <div class="sidebar-header">
                    <img src="../assets/img/logo.png" style="height: 60px; width: 60px;" alt="bootraper logo" class="app-logo">
                </div>
                <ul class="list-unstyled components text-secondary">
                    <li>
                        <a href="user_index.html"><i class="fas fa-home"></i> Dashboard</a>
                    </li>
                    <li>
                        <a href="view_devices.html"><i class="fas fa-laptop"></i> View Available Devices</a>
                    </li>
                    <li>
                        <a href="borrowing_records.html"><i class="fas fa-history"></i> Personal Borrowing Records</a>
                    </li>
                    <li>
                        <a href="wish_list.html"><i class="fas fa-heart"></i> Wish List</a>
                    </li>
                    <li>
                        <a href="reserve_check_out.html"><i class="fas fa-hand-holding"></i> Reserve, Check-out, Return Equipment</a>
                    </li>
                    <li>
                        <a href="update_info.html"><i class="fas fa-user-cog"></i> Update Password and Personal Information</a>
                    </li>
                    <li>
                        <a href="index.html"> <i class="fas fa-sign-out-alt"></i> Logout</a>
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
                                        <i class="fas fa-user"></i> <span>Alex Lo</span> <i style="font-size: .8em;" class="fas fa-caret-down"></i>
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
                            <h3>Welcome, ${user.name}</h3> <!-- Dynamically greeting the user -->
                        </div>
                        <div class="row">
                            <div class="col-md-12 col-lg-12">
                                <div class="card">
                                    <div class="card-header">
                                        Available Devices
                                    </div>
                                    <div class="card-body">
                                        <c:if test="${not empty devices}">
                                            <table class="table table-striped">
                                                <thead>
                                                    <tr>
                                                        <th>Name</th>
                                                        <th>Model</th>
                                                        <!-- other headers -->
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${devices}" var="device">
                                                        <tr>
                                                            <td>${device.name}</td>
                                                            <td>${device.model}</td>
                                                            <!-- other device details -->
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:if>
                                        <c:if test="${empty devices}">
                                            <p>No available devices at the moment.</p>
                                        </c:if>
                                    </div>
                                </div>

                                <div class="card">
                                    <div class="card-header">
                                        Personal Borrowing Records
                                    </div>
                                    <div class="card-body">
                                        <c:if test="${not empty borrowingRecords}">
                                            <table class="table table-striped">
                                                <thead>
                                                    <tr>
                                                        <th>Device Name</th>
                                                        <th>Checkout Date</th>
                                                        <th>Return Date</th>
                                                        <!-- other headers -->
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${borrowingRecords}" var="record">
                                                        <tr>
                                                            <td>${record.deviceName}</td>
                                                            <td>${record.checkoutDate}</td>
                                                            <td>${record.returnDate}</td>
                                                            <!-- other record details -->
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:if>
                                        <c:if test="${empty borrowingRecords}">
                                            <p>You have no borrowing records.</p>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="../assets/vendor/jquery/jquery.min.js"></script>
        <script src="../assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="../assets/js/script.js"></script>
    </body>

</html>

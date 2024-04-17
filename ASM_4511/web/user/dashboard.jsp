<%-- 
    Document   : dashboard
    Created on : 2024年4月2日, 下午2:25:40
    Author     : kenneth
--%>
<%@page import="ict.bean.BorrowingRecord"%>
<%@page import="ict.bean.User"%>
<%@page import="java.util.List"%>
<%@page import="ict.bean.WishListEquipmentBean"%>
<%@page import="ict.servlet.EquipmentServlet"%>
<%@page import="ict.servlet.WishListServlet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>User Dashboard</title>
        <link href="${pageContext.request.contextPath}/assets/vendor/fontawesome/css/fontawesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/vendor/fontawesome/css/solid.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/vendor/datatables/datatables.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/css/master.css" rel="stylesheet">
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
                        <a href="reserve_check_out.html"><i class="fas fa-hand-holding"></i>Return Equipment</a>
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
                                        <i class="fas fa-user"></i> <span>${user.name}</span> <i style="font-size: .8em;" class="fas fa-caret-down"></i>
                                    </a>
                                    <div class="dropdown-menu dropdown-menu-end nav-link-menu">
                                        <ul class="nav-list">
                                            <li><a href="profile.html" class="dropdown-item"><i class="fas fa-address-card"></i> Profile</a></li>
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
                            <h3>Welcome, ${user.name}</h3>
                        </div>
                        <div class="row">
                            <div class="col-md-12 col-lg-12">
                                <div class="card">
                                    <div class="card-header">
                                        Personal Borrowing Records
                                    </div>
                                    <div class="card-body">
                                        <%
                                            List<BorrowingRecord> borrowingRecords = (List<BorrowingRecord>) request.getAttribute("borrowingRecords");
                                            if (borrowingRecords == null || borrowingRecords.isEmpty()) {
                                        %>
                                        <p>You have no borrowing records.</p>
                                        <%
                                        } else {
                                        %>
                                        <table class="table table-striped">
                                            <thead>
                                                <tr>
                                                    <th>Request ID</th>
                                                    <th>Equipment Name</th>
                                                    <th>Request DateTime</th>
                                                    <th>Start Date</th>
                                                    <th>Return Date</th>
                                                    <th>Status</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%
                                                    for (BorrowingRecord record : borrowingRecords) {
                                                %>
                                                <tr>
                                                    <td><%= record.getRecordId()%></td>
                                                    <td><%= record.getEquipmentName()%></td>
                                                    <td><%= record.getRequestDateTime()%></td>
                                                    <td><%= record.getStartDate() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(record.getStartDate()) : "N/A"%></td>
                                                    <td><%= record.getReturnDate() != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(record.getReturnDate()) : "N/A"%></td>
                                                    <td><%= record.getStatus()%></td>
                                                </tr>
                                                <%
                                                    }
                                                %>
                                            </tbody>
                                        </table>
                                        <%
                                            }
                                        %>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/assets/vendor/jquery/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
    </body>

</html>

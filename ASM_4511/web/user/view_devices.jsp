<%-- 
    Document   : view_devices
    Created on : 2024年4月13日
    Author     : kenneth
--%>
<%@page import="ict.bean.User"%>
<%@page import="ict.bean.Equipment"%>
<%@page import="java.util.List"%>
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
    <title>Available Devices</title>
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
                <img src="../assets/img/logo.png" style="height: 60px; width: 60px;" alt="bootraper logo" class="app-logo">
            </div>
            <ul class="list-unstyled components text-secondary">
                <li>
                    <a href="user_index.html"><i class="fas fa-home"></i> Dashboard</a>
                </li>
                <li class="active">
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
            </nav>
            <div class="content">
                <div class="container">
                    <div class="page-title">
                        <h3>Available Devices</h3>
                    </div>
                    <div class="row">
                        <div class="col-md-12 col-lg-12">
                            <div class="card">
                                <div class="card-header">List of Devices</div>
                                <div class="card-body">
                                    <%
                                        List<Equipment> availableEquipment = (List<Equipment>) request.getAttribute("availableEquipment");
                                        if (availableEquipment == null || availableEquipment.isEmpty()) {
                                    %>
                                    <p>No devices are currently available.</p>
                                    <%
                                        } else {
                                    %>
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th>Equipment ID</th>
                                                <th>Name</th>
                                                <th>Description</th>
                                                <th>Location</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                for (Equipment equipment : availableEquipment) {
                                            %>
                                            <tr>
                                                <td><%= equipment.getEquipmentID() %></td>
                                                <td><%= equipment.getName() %></td>
                                                <td><%= equipment.getDescription() %></td>
                                                <td><%= equipment.getLocation() %></td>
                                                <td><%= equipment.getStatus() %></td>
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

<%-- 
Document   : view_devices
Created on : 2024年4月13日
Author     : kenneth
--%>
<%@page import="ict.bean.User"%>
<%@page import="ict.bean.EquipmentBean"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    User user = (User) session.getAttribute("staff");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<%
    List<EquipmentBean> equipmentList = (List<EquipmentBean>) request.getAttribute("availableEquipment");
    if (equipmentList == null) {
    }
%>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Available Devices</title>
        <script src="${pageContext.request.contextPath}/assets/js/view_devices.js"></script>
        <script src="${pageContext.request.contextPath}/assets/vendor/jquery/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
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
                        <a href="${pageContext.request.contextPath}/staff/view_devices"><i class="fas fa-laptop"></i> View Devices</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/staff/view_venue"><i class="fas fa-history"></i> View Venue</a>
                    </li>
                    <li>
                        <a href="WishListServlet"><i class="fas fa-heart"></i> Wish List</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/staff/return_checkout.jsp"><i class="fas fa-hand-holding"></i>Return / Checkout</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/staff/UserProfile.jsp"><i class="fas fa-user-cog"></i> Personal Information</a>
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
                            <button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#cartModal">
                                <i class="fas fa-shopping-cart"></i> Cart <span id="cartCount">0</span>
                            </button>
                            <li class="nav-item dropdown">
                                <div class="nav-dropdown">
                                    <a href="#" id="nav2" class="nav-item nav-link dropdown-toggle text-secondary" data-bs-toggle="dropdown" aria-expanded="false">
                                        <i class="fas fa-user"></i> <span>${staff.name}</span> <i style="font-size: .8em;" class="fas fa-caret-down"></i>
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
                <div class="modal fade" id="cartModal" tabindex="-1" aria-labelledby="cartModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="cartModalLabel">Your Cart</h5>
                            </div>
                            <form id="cartForm" action="${pageContext.request.contextPath}/checkoutController" method="post">
                                <div class="modal-body" id="cartItems">
                                    <!-- Items will be dynamically added here through JavaScript -->
                                    <!-- No static items should be present -->
                                    <p>No devices currently in the cart.</p> <!-- Default text -->
                                </div>
                                <div class="modal-footer">
                                    <div class="container-fluid">
                                        <div class="row">
                                            <div class="col">
                                                <label for="location">Delivery Location:</label>
                                                <select id="location" name="location" class="form-control" required>
                                                    <option value="LWL">LWL</option>
                                                    <option value="ST">ST</option>
                                                    <option value="TY">TY</option>
                                                    <option value="TM">TM</option>
                                                    <option value="CW">CW</option>
                                                </select>
                                            </div>
                                            <div class="col">
                                                <label for="startDate">Start Date:</label>
                                                <input type="date" id="startDate" name="startDate" class="form-control"
                                                       min="<%= new java.sql.Date(System.currentTimeMillis() + 24L * 60 * 60 * 1000).toString()%>"
                                                       max="<%= new java.sql.Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000).toString()%>" required>
                                            </div>
                                            <div class="col">
                                                <label for="returnDate">Return Date:</label>
                                                <input type="date" id="returnDate" name="returnDate" class="form-control"
                                                       min="<%= new java.sql.Date(System.currentTimeMillis() + 24L * 60 * 60 * 1000).toString()%>"
                                                       max="<%= new java.sql.Date(System.currentTimeMillis() + 180L * 24 * 60 * 60 * 1000).toString()%>" required>
                                                <input type="hidden" id="userId" name="userId" value="<%= user.getUserID()%>">
                                            </div>

                                        </div>
                                    </div>
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                    <button type="submit" class="btn btn-primary">Checkout</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

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
                                            List<EquipmentBean> availableEquipment = (List<EquipmentBean>) request.getAttribute("availableEquipment");
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
                                                    for (EquipmentBean equipment : availableEquipment) {
                                                %>
                                                <tr>
                                                    <td><%= equipment.getEquipmentID()%></td>
                                                    <td><%= equipment.getName()%></td>
                                                    <td><%= equipment.getDescription()%></td>
                                                    <td><%= equipment.getLocation()%></td>
                                                    <td><%= equipment.getStatus()%></td>
                                                    <td>
                                                        <form action="${pageContext.request.contextPath}/addToCart" method="post">
                                                            <input type="hidden" name="equipmentId" value="<%= equipment.getEquipmentID()%>" />
                                                            <button type="button" class="btn btn-primary add-to-cart-btn"
                                                                    data-id="<%= equipment.getEquipmentID()%>"
                                                                    data-name="<%= equipment.getName()%>"
                                                                    data-description="<%= equipment.getDescription()%>"
                                                                    data-location="<%= equipment.getLocation()%>">Add to Cart</button>
                                                        </form>
                                                    </td>
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
        <script src="${pageContext.request.contextPath}/assets/js/view_devices.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
    </body>
</html>

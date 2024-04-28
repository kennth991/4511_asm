<%-- 
    Document   : index
    Created on : 2024年4月2日, 下午2:25:46
    Author     : Lau Ka Ming Benjamin
--%>
<%@page import="ict.bean.User"%>
<%@page import="ict.bean.EquipmentBean"%>
<%@page import="ict.servlet.EquipmentServlet"%>
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
                    <img src="<c:url value='/assets/img/logo.png'/>" style="height: 60px; width: 60px;" alt="bootraper logo" class="app-logo">
                </div>
                <ul class="list-unstyled components text-secondary">
                    <li>
                        <a href="technician_index.html"><i class="fas fa-home"></i> Dashboard</a>
                    </li>
                    <li>
                        <a href="inventory_management.html"><i class="fas fa-clipboard-list"></i> Inventory Management</a>
                    </li>
                    <li>
                        <a href="Booking"><i class="fas fa-calendar-check"></i> Booking Management</a>
                    </li>
                    <li>
                        <a href="damage_reporting.html"><i class="fas fa-exclamation-triangle"></i> Damage Reporting</a>
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
                                        <i class="fas fa-user"></i> <span>${admin.name}</span> <i style="font-size: .8em;" class="fas fa-caret-down"></i>
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
                            <h3>Technician Dashboard</h3>
                        </div>
                        <div class="row">
                            <div class="col-md-12 col-lg-12">
                                <div class="card">
                                    <div class="card-header">Equipment List</div>
                                    <div class="card-body">
                                        <p class="card-title"></p>
                                        <table class="table table-hover" id="dataTables-example" width="100%">
                                            <thead>
                                                <tr>
                                                    <th>Equipment ID</th>
                                                    <th>Name</th>
                                                    <th>Location</th>
                                                    <th>Description</th>
                                                    <th>Status</th>
                                                    <th>Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%
                                                    // Retrieve the "equipments" attribute from the request
                                                    ArrayList<EquipmentBean> equipments = (ArrayList<EquipmentBean>) request.getAttribute("equipments");
                                                %>
                                                <%-- Use JSP scriptlet to iterate over the list of equipment objects --%>
                                                <%
                                                    for (EquipmentBean equipment : equipments) {%>
                                                <tr>
                                                    <td><%= equipment.getEquipmentId()%></td>
                                                    <td><%= equipment.getName()%></td>
                                                    <td><%= equipment.getLocation()%></td>
                                                    <td><%= equipment.getDescription()%></td>
                                                    <td><%= equipment.getStatus()%></td>
                                                    <td>
                                                        <button type="button" class="btn btn-success btn-sm" data-bs-toggle="modal" data-bs-target="#staticBackdrop" onclick="displayEquipment('<%= equipment.getEquipmentId()%>', '<%= equipment.getName()%>', '<%= equipment.getLocation()%>', '<%= equipment.getDescription()%>', '<%= equipment.getStatus()%>')">
                                                            <i class="fas fa-plus"></i>
                                                        </button>
                                                        <button type="button" class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#staticBackdrop" onclick="displayEquipment('<%= equipment.getEquipmentId()%>', '<%= equipment.getName()%>', '<%= equipment.getLocation()%>', '<%= equipment.getDescription()%>', '<%= equipment.getStatus()%>')">
                                                            <i class="fas fa-edit"></i>
                                                        </button>

                                                    </td>
                                                </tr>
                                                <%

                                                    }%>
                                            </tbody>
                                        </table>
                                        <button class="btn btn-primary" type="submit"><a href="create_order.html">Create Equipment</a></button>
                                    </div>
                                    <!-- Modal -->


                                </div>
                            </div>
                            <div class="col-md-12 col-lg-12">
                                <div class="card">
                                    <div class="card-header">Venue List</div>
                                    <div class="card-body">
                                        <p class="card-title"></p>
                                        <table class="table table-hover" id="dataTables-example" width="100%">
                                            <thead>
                                                <tr>
                                                    <th>Equipment ID</th>
                                                    <th>Name</th>
                                                    <th>Location</th>
                                                    <th>Description</th>
                                                    <th>Status</th>
                                                    <th>Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%
                                                    // Retrieve the "equipments" attribute from the request
                                                    ArrayList<EquipmentBean> equipmentss = (ArrayList<EquipmentBean>) request.getAttribute("equipments");
                                                %>
                                                <%-- Use JSP scriptlet to iterate over the list of equipment objects --%>
                                                <%
                                                    for (EquipmentBean equipment : equipmentss) {%>
                                                <tr>
                                                    <td><%= equipment.getEquipmentId()%></td>
                                                    <td><%= equipment.getName()%></td>
                                                    <td><%= equipment.getLocation()%></td>
                                                    <td><%= equipment.getDescription()%></td>
                                                    <td><%= equipment.getStatus()%></td>
                                                    <td>

                                                        <button type="button" class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#exampleModal" onclick="deleteEquipment('<%= equipment.getEquipmentId()%>', '<%= equipment.getName()%>', '<%= equipment.getLocation()%>', '<%= equipment.getDescription()%>', '<%= equipment.getStatus()%>')">
                                                            <i class="fa-solid fa-trash"></i></button>
                                                    </td>
                                                </tr>
                                                <%

                                                    }%>
                                            </tbody>
                                        </table>
                                        <button class="btn btn-primary" type="submit"><a href="create_order.html">Create Equipment</a></button>
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
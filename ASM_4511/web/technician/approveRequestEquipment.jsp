<%-- 
    Document   : index
    Created on : 2024年4月2日, 下午2:25:46
    Author     : Lau Ka Ming Benjamin
--%>
<%@page import="ict.bean.User"%>
<%@page import="ict.bean.EquipmentRequestEquipmentBean"%>
<%@page import="ict.servlet.EquipmentRequestEquipmentServlet"%>
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
                        <a href="WishListEquipmentServlet"><i class="fas fa-clipboard-list"></i> Wish List Management</a>
                    </li>
                    <li>
                        <a href="Booking"><i class="fas fa-calendar-check"></i> Booking Management</a>
                    </li>
                    <li>
                        <a href="damage_reporting.html"><i class="fas fa-exclamation-triangle"></i> Damage Reporting</a>
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
                                        <i class="fas fa-user"></i> <span>${user.name}</span> <i style="font-size: .8em;" class="fas fa-caret-down"></i>
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
                        <%
                            // Retrieve the "equipments" attribute from the request
                            ArrayList<EquipmentRequestEquipmentBean> equipmentRequests = (ArrayList<EquipmentRequestEquipmentBean>) request.getAttribute("equipmentsRequestEquipment");
                       ArrayList<User> couriers = (ArrayList<User>) request.getAttribute("availableCourier");
                       

                        %>
                        <div class="page-title">
                            <h3>Technician Dashboard</h3>   

                        </div>

                        <ul class="list-group custom-list-group mb-3">
                            <li class="list-group-item custom-list-group-item list-group-item-secondary">
                                <span class="custom-list-heading">Request ID</span>
                                <p class="custom-list-text"><%= equipmentRequests.get(0).getEquipmentRequestID()%></p>
                            </li>
                            <li class="list-group-item custom-list-group-item list-group-item-secondary">
                                <span class="custom-list-heading">Start Date</span>
                                <p class="custom-list-text"><%= equipmentRequests.get(0).getStartDate()%></p>
                            </li>
                            <li class="list-group-item custom-list-group-item list-group-item-secondary">
                                <span class="custom-list-heading">Return Date</span>
                                <p class="custom-list-text"><%= equipmentRequests.get(0).getReturnDate()%></p>
                            </li>
                            <li class="list-group-item custom-list-group-item list-group-item-secondary">
                                <span class="custom-list-heading">Request Location</span>
                                <p class="custom-list-text"><%= equipmentRequests.get(0).getRequestLocation()%></p>
                            </li>
                            <li class="list-group-item custom-list-group-item list-group-item-secondary">
                                <span class="custom-list-heading">Requester</span>
                                <p class="custom-list-text"><%= equipmentRequests.get(0).getUserName()%></p>
                            </li>
                        </ul>
                        <div class="row">
                            <div class="col-md-12 col-lg-12">

                                <div class="card">
                                    <div class="card-header">Equipment List</div>
                                    <div class="card-body">
                                        <p class="card-title"></p>
                                        <form action="EquipmentRequestEquipmentServlet" method="get">
                                             <input type="hidden" name="action" value="assignCourier">
                                             <input type="hidden" name="requestCount" value="3">
                                            <table class="table table-hover" id="dataTables-example" width="100%">
                                                <thead>
                                                    <tr>
                                                        <th>Request ID</th>
                                                        <th>Equipment ID</th>
                                                        <th>Equipment Name</th>
                                                        <th>Equipment Location</th>
                                                        <th>Status</th>
                                                        <th>Assign Courier</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <% for (int i = 0; i < equipmentRequests.size(); i++) {%>
                                                    <tr>
                                                        <td><%= equipmentRequests.get(i).getEquipmentRequestID()%></td>
                                                        <td><%= equipmentRequests.get(i).getEquipmentID()%></td>
                                                        <td><%= equipmentRequests.get(i).getEquipmentName()%></td>
                                                        <td><%= equipmentRequests.get(i).getEquipmentLocation()%></td>
                                                        <td><%= equipmentRequests.get(i).getStatus()%></td>
                                                        <td>
                                                           
                                                            <input type="hidden" name="requestID_<%= i%>" value="<%= equipmentRequests.get(i).getEquipmentRequestID()%>">
                                                            <input type="hidden" name="equipmentID_<%= i%>" value="<%= equipmentRequests.get(i).getEquipmentID()%>">
                                                            <select class="selectpicker custom-select" data-width="auto" name="assignedCourier_<%= i%>">
                                                                  <% for (int y = 0; y < couriers.size(); y++) {%>
                                                                <option class="small-text " value="<%=couriers.get(y).getUserID()%>"><%=couriers.get(y).getName()%></option>

                                                                  <% }%>
                                                                <option class="small-text" value="-1">Reject</option>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                    <% }%>
                                                </tbody>
                                            </table>
                                            <button type="submit" class="btn btn-success">Submit</button>
                                        </form>
                                    </div>
                                    <!-- Modal -->
                                   
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <style>
            .custom-list-group {
                padding: 0;
            }

            .custom-list-group-item {
                padding: 0.5rem 1rem;
            }

            .custom-list-heading {
                font-weight: bold;
                font-size: 0.675rem;
                margin-bottom: 0.25rem;
            }

            .custom-list-text {
                font-size: 0.775rem;
                margin-bottom: 0;
            }

        </style>

        <script>
            $(function () {
                $('select').selectpicker();
            });
        </script>
        <script src="<c:url value='/assets/vendor/jquery/jquery.min.js' />"></script>
        <script src="<c:url value='/assets/vendor/bootstrap/js/bootstrap.bundle.min.js' />"></script>
        <script src="<c:url value='/assets/js/script.js' />"></script>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>F
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>

    </body>

</html>
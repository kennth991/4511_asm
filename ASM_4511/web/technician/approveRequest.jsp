<%-- 
    Document   : index
    Created on : 2024年4月2日, 下午2:25:46
    Author     : Lau Ka Ming Benjamin
--%>
<%@page import="ict.bean.User"%>
<%@page import="ict.bean.EquipmentRequestBean"%>
<%@page import="ict.servlet.EquipmentRequestServlet"%>
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
                                                    ArrayList<EquipmentRequestBean> equipmentRequests = (ArrayList<EquipmentRequestBean>) request.getAttribute("equipmentsRequest");
                                                %>
                                                <%-- Use JSP scriptlet to iterate over the list of equipment objects --%>
                                                <%
                                                    for (EquipmentRequestBean equipmentRequest : equipmentRequests) {%>
                                                <tr>
                                                    <td><%= equipmentRequest.getRequestID()%></td>
                                                    <td><%= equipmentRequest.getRequesterID()%></td>
                                                    <td><%= equipmentRequest.getRequestDateTime()%></td>
                                                    <td><%= equipmentRequest.getStartDate()%></td>
                                                    <td><%= equipmentRequest.getStatus()%></td>
                                                    <td><%= equipmentRequest.getStatus()%></td>
                                                    <td><%=equipmentRequest.getRequesterName()%></td>
                                                    <td>
                                                        <button type="button" class="btn btn-success btn-sm" data-bs-toggle="modal" data-bs-target="#staticBackdrop" onclick="">
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
                                    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <form action="Equipment" method="get">
                                                    <div class="modal-body">
                                                        <input id="deleteEquipment" name="deleteEquipment" value="edit" class="deleteEquipment" hidden="">

                                                        <input id="id" type="number" name= "id"class="form-control" type="text" readonly>
                                                        <input id="name" name="name" class="form-control" type="text" >
                                                        <input id="location" name="location" class="form-control" type="text">
                                                        <input id="description" name="description"class="form-control" type="text"  >
                                                        <input id="status" name="status" class="form-control" type="text" >
                                                        <input id="category" name="category" class="form-control" type="text" >
                                                        <src id="imgSrc" name="imgSrc"  >

                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                        <button type="submit" class="btn btn-primary">comfrim Delete</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h1 class="modal-title fs-5" id="staticBackdropLabel">Modal title</h1>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <form action="Equipment" method="get">
                                                    <div class="modal-body">
                                                        <img id="displaySrc" src="">
                                                        <input id="editEquipment" name="editEquipment" value="edit" class="editEquipment" hidden="">
                                                        <input id="editid" type="number" name= "id"class="form-control mb-2" type="text" readonly>
                                                        <input id="editname" name="name" class="form-control  mb-2" type="text" >
                                                        <input id="editlocation" name="location" class="form-control  mb-2" type="text">
                                                        <input id="editdescription" name="description"class="form-control  mb-2" type="text"  >
                                                        <input id="editstatus" name="status" class="form-control  mb-2" type="text" >
                                                        <input id="editcategory" name="category" class="form-control" type="text" >
                                                          <src id="imgSrc" name="imgSrc"  >
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                        <button type="submit" class="btn btn-primary">Understood</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            function displayEquipment(equipmentId, name, location, description, status, category, imgSrc) {

                document.getElementById("editid").value = equipmentId;
                document.getElementById("editname").value = name;
                document.getElementById("editlocation").value = location;
                document.getElementById("editdescription").value = description;
                document.getElementById("editstatus").value = status;
                document.getElementById("editcategory").value = category;
                document.getElementById("displaySrc").src = imgSrc;

            }
            function deleteEquipment(equipmentId, name, location, description, status, category, imgSrc) {

                document.getElementById("id").value = equipmentId;
                document.getElementById("name").value = name;
                document.getElementById("location").value = location;
                document.getElementById("description").value = description;
                document.getElementById("status").value = status;
            }
        </script>
        <script src="<c:url value='/assets/vendor/jquery/jquery.min.js' />"></script>
        <script src="<c:url value='/assets/vendor/bootstrap/js/bootstrap.bundle.min.js' />"></script>
        <script src="<c:url value='/assets/js/script.js' />"></script>
    </body>

</html>
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
                        <a href="WishListEquipmentServlet"><i class="fas fa-clipboard-list"></i> Wish List Management</a>
                    </li>
                    <li>
                        <a href="<c:url value='/view_booking'/>"><i class="fas fa-calendar-check"></i> Booking Management</a>
                    </li>
                    <li>
                        <a href="damage_reporting.html"><i class="fas fa-exclamation-triangle"></i> Damage Reporting</a>
                    </li>
                    <li>
                        <a href="EquipmentRequestServlet"> <i class="fas fa-calendar-check"></i> Logout</a>
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
                                                    <th>Category</th>

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
                                                    <td><%= equipment.getEquipmentID()%></td>
                                                    <td><%= equipment.getName()%></td>
                                                    <td><%= equipment.getLocation()%></td>
                                                    <td><%= equipment.getDescription()%></td>
                                                    <td><%= equipment.getStatus()%></td>
                                                    <td><%= equipment.getCategory()%></td>


                                                    <td>
                                                        <button type="button" class="btn btn-success btn-sm" data-bs-toggle="modal" data-bs-target="#staticBackdrop" onclick="displayEquipment('<%= equipment.getEquipmentID()%>', '<%= equipment.getName()%>', '<%= equipment.getLocation()%>', '<%= equipment.getDescription()%>', '<%= equipment.getStatus()%>', '<%= equipment.getCategory()%>', '<%= equipment.getImgSrc()%>', '<%= equipment.getIsStaff()%>')">
                                                            <i class="fas fa-edit"></i>
                                                        </button>
                                                        <button type="button" class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#exampleModal" onclick="deleteEquipment('<%= equipment.getEquipmentID()%>', '<%= equipment.getName()%>', '<%= equipment.getLocation()%>', '<%= equipment.getDescription()%>', '<%= equipment.getStatus()%>', '<%= equipment.getCategory()%>', '<%= equipment.getImgSrc()%>')">
                                                            <i class="fa-solid fa-trash"></i>
                                                        </button>
                                                            
                                                            
                                                    </td>
                                                </tr>
                                                <%

                                                    }%>
                                            </tbody>
                                        </table>
                                        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#createModal">Create Equipment</a></button>
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
                                                        <input type="hidden" name="action" value="deleteEquipment">


                                                        <div class="mb-2">
                                                            <label for="id" class="form-label">ID:</label>
                                                            <input id="id" type="number" name="id" class="form-control" readonly>
                                                        </div>

                                                        <div class="mb-2">
                                                            <label for="name" class="form-label">Name:</label>
                                                            <input id="name" name="name" class="form-control" type="text">
                                                        </div>

                                                        <div class="mb-2">
                                                            <label for="location" class="form-label">Location:</label>
                                                            <input id="location" name="location" class="form-control" type="text">
                                                        </div>

                                                        <div class="mb-2">
                                                            <label for="description" class="form-label">Description:</label>
                                                            <input id="description" name="description" class="form-control" type="text">
                                                        </div>

                                                        <div class="mb-2">
                                                            <label for="status" class="form-label">Status:</label>
                                                            <input id="status" name="status" class="form-control" type="text">
                                                        </div>

                                                        <div class="mb-2">
                                                            <label for="category" class="form-label">Category:</label>
                                                            <input id="category" name="category" class="form-control" type="text">
                                                        </div>

                                                        <src id="imgSrc" name="imgSrc">

                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                        <button type="submit" class="btn btn-primary">Confirm Delete</button>
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
                                                        <input type="hidden" name="action" value="editEquipment">

                                                        <div class="mb-2">
                                                            <label for="editid" class="form-label">ID:</label>
                                                            <input id="editid" type="number" name="id" class="form-control" readonly>
                                                        </div>

                                                        <div class="mb-2">
                                                            <label for="editname" class="form-label">Name:</label>
                                                            <input id="editname" name="name" class="form-control" type="text">
                                                        </div>

                                                        <div class="mb-2">
                                                            <label for="editlocation" class="form-label">Location:</label>
                                                            <input id="editlocation" name="location" class="form-control" type="text">
                                                        </div>

                                                        <div class="mb-2">
                                                            <label for="editdescription" class="form-label">Description:</label>
                                                            <input id="editdescription" name="description" class="form-control" type="text">
                                                        </div>

                                                        <div class="mb-2">
                                                            <label for="editStatus" class="form-label">Status:</label>
                                                            <select id="editStatus" name="status" class="form-select" required>
                                                                <option value="available">Available</option>
                                                                <option value="unavailable">Unavailable</option>
                                                            </select>
                                                        </div>


                                                        <div class="mb-2">
                                                            <label for="editcategory" class="form-label">Category:</label>
                                                            <input id="editcategory" name="category" class="form-control" type="text">
                                                        </div>
                                                        <div class="mb-2">
                                                            <label for="editIsStaffDropdown" class="form-label">Role:</label>
                                                            <select id="editIsStaffDropdown" name="editIsStaffDropdown" class="form-select">
                                                                <option value="user">For User</option>
                                                                <option value="staff">Staff only</option>

                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                        <button type="submit" class="btn btn-primary">Understood</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal fade" id="createModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h1 class="modal-title fs-5" id="staticBackdropLabel">Create Equipment</h1>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <form action="Equipment" method="get">
                                                    <div class="modal-body">
                                                        <input type="hidden" name="action" value="createEquipment">

                                                        <div class="mb-2">
                                                            <label for="createName" class="form-label">Name:</label>
                                                            <input id="createName" name="name" class="form-control" type="text" required>
                                                        </div>

                                                        <div class="mb-2">
                                                            <label for="createLocation" class="form-label">Location:</label>
                                                            <select id="createLocation" name="location" class="form-select" required>
                                                                <option value="">Select Location</option>
                                                                <option value="TY">TY</option>
                                                                <option value="TM">TM</option>
                                                                <option value="LWL">LWL</option>
                                                                <option value="CW">CW</option>
                                                                <option value="ST">ST</option>
                                                            </select>
                                                        </div>

                                                        <div class="mb-2">
                                                            <label for="createDescription" class="form-label">Description:</label>
                                                            <input id="createDescription" name="description" class="form-control" type="text" required>
                                                        </div>

                                                        <div class="mb-2">
                                                            <label for="createStatus" class="form-label">Status:</label>
                                                            <select id="createStatus" name="status" class="form-select" required>
                                                                <option value="available">Available</option>
                                                                <option value="unavailable">Unavailable</option>
                                                            </select>
                                                        </div>

                                                        <div class="mb-2">
                                                            <label for="createCategory" class="form-label">Category:</label>
                                                            <input id="createCategory" name="category" class="form-control" type="text" required>
                                                        </div>

                                                        <div class="mb-2">
                                                            <label for="createIsStaffDropdown" class="form-label">Role:</label>
                                                            <select id="createIsStaffDropdown" name="createIsStaffDropdown" class="form-select" required>
                                                                <option value="">Select Role</option>
                                                                <option value="user">For User</option>
                                                                <option value="staff">Staff only</option>

                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                        <button type="submit" class="btn btn-primary">Create</button>
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
            function displayEquipment(equipmentId, name, location, description, status, category, imgSrc, isStaff) {
                document.getElementById("editid").value = equipmentId;
                document.getElementById("editname").value = name;
                document.getElementById("editlocation").value = location;
                document.getElementById("editdescription").value = description;
                document.getElementById("editstatus").value = status;
                document.getElementById("editcategory").value = category;

                var dropdown = document.getElementById("editIsStaffDropdown");
                if (isStaff === "staff") {
                    dropdown.value = "staff";
                } else {
                    dropdown.value = "user";
                }
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
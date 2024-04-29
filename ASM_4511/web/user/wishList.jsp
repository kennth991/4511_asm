<%-- 
    Document   : wishlist
    Created on : 14 Apr 2024, 2:40:40 am
    Author     : LauKaMingBenjamin
--%>
<%@page import="ict.bean.User"%>
<%@page import="ict.bean.WishListEquipmentBean"%>
<%@page import="ict.servlet.WishListServlet"%>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
      response.sendRedirect(request.getContextPath() + "/login.jsp");
    return;
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Wish list</title>
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
                        <a href="${pageContext.request.contextPath}/view_venue"><i class="fas fa-history"></i> View Venue</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/WishListServlet"><i class="fas fa-heart"></i> Wish List</a>
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
                        <div class="page-title">
                            <div class="row">
                                <div class="col">
                                    <div class="row">
                                        <div class="col">
                                            <h3>Technician Dashboard</h3>
                                        </div>
                                        <div class="col text-right">
                                            <a href="AddWishList" class="btn btn-info mb-3">Add Wish List</a>
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
                                                <th>Responder</th>
                                                <th>Serial Number</th>
                                                <th>Equipment</th>

                                                <th>Location</th>
                                                <th>Request Time</th>
                                                <th>Status</th>
                                                <th>Confirm</th>

                                            </tr>
                                        </thead>

                                        <tbody>
                                            <%
                                                // Retrieve the "equipments" attribute from the request
                                                ArrayList<WishListEquipmentBean> wishEquipmentsApproved = (ArrayList<WishListEquipmentBean>) request.getAttribute("wishListApproved");
                                            %>

                                            <%-- Use JSP scriptlet to iterate over the list of equipment objects --%>
                                            <%
                                                    for (WishListEquipmentBean wishEquipmentApproved : wishEquipmentsApproved) {%>
                                            <tr>

                                                <td><%= wishEquipmentApproved.getResponderName()%></td>
                                                <td><%= wishEquipmentApproved.getEquipmentequipmentID()%></td>
                                                <td><%= wishEquipmentApproved.getEquipmentName()%></td>
                                                <td><%= wishEquipmentApproved.getLocation()%></td>

                                                <td><%= wishEquipmentApproved.getRequestDateTime()%></td>
                                                <td><%= wishEquipmentApproved.getStatus()%></td>
                                                <td>


                                                    <form action="WishListServlet" method="get">
                                                        <input type="hidden" name="action" value="confirm"> <!-- Added hidden field for action -->
                                                        <input type="hidden" id="wishListwishID" name="wishListwishID" value="<%= wishEquipmentApproved.getWishListwishID()%>">
                                                        <input type="hidden" name="equipmentID" id="equipmentID" value="<%= wishEquipmentApproved.getEquipmentequipmentID()%>">
                                                        <button type="submit" class="btn btn-warning btn-sm">
                                                            <i class="fas fa-plus"></i>
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
                        <div class="col-md-12 col-lg-12">
                            <div class="card">
                                <div class="card-header">My Wish List</div>
                                <div class="card-body">
                                    <p class="card-title"></p>
                                    <table class="table table-hover" id="dataTables-example" width="100%">
                                        <thead>
                                            <tr>
                                                <th>Serial Number</th>
                                                <th>Equipment</th>
                                                <th>Location</th>
                                                <th>Request Time</th>
                                                <th>Status</th>
                                                <th>Remove From Wish List</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                // Retrieve the "equipments" attribute from the request
                                                ArrayList<WishListEquipmentBean> wishEquipments = (ArrayList<WishListEquipmentBean>) request.getAttribute("wishList");
                                            %>
                                            <%-- Use JSP scriptlet to iterate over the list of equipment objects --%>
                                            <%
                                                    for (WishListEquipmentBean wishEquipment : wishEquipments) {%>
                                            <tr>

                                                <td><%= wishEquipment.getEquipmentequipmentID()%></td>

                                                <td><%= wishEquipment.getEquipmentName()%></td>
                                                <td><%= wishEquipment.getLocation()%></td>

                                                <td><%= wishEquipment.getRequestDateTime()%></td>
                                                <td><%= wishEquipment.getStatus()%></td>
                                                <td>

                                                    <button type="button" class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#removeModal" id="actionButton" onclick="removeWishListEquipment('<%= wishEquipment.getWishListwishID()%>', '<%= wishEquipment.getEquipmentequipmentID()%>')">
                                                        <i class="fa-solid fa-trash"></i>
                                                    </button>


                                                </td>
                                            </tr>
                                            <%

                                                }%>
                                        </tbody>
                                    </table>

                                </div>
                                <!-- Modal -->
                                <div class="modal fade" id="removeModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5" id="exampleModalLabel"></h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <form action="WishListServlet" method="get">
                                                <div class="modal-body">
                                                    Do you confirm to remove?
                                                    <input type="hidden" name="action" value="remove"> <!-- Added hidden field for action -->
                                                    <input type="hidden" id="removeWishListwishID" name="removeWishListwishID" value="">
                                                    <input type="hidden" name="removeEquipmentID" id="removeEquipmentID" value="">


                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                    <button type="submit" class="btn btn-primary">confirm</button>
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
        function removeWishListEquipment(wishListId, equipmentname) {

            document.getElementById("removeWishListwishID").value = wishListId;
            document.getElementById("removeEquipmentID").value = equipmentname;


        }

    </script>
    <script src="<c:url value='/assets/vendor/jquery/jquery.min.js' />"></script>
    <script src="<c:url value='/assets/vendor/bootstrap/js/bootstrap.bundle.min.js' />"></script>
    <script src="<c:url value='/assets/js/script.js' />"></script>
</body>

</html>
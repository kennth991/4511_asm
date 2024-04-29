<%-- 
    Document   : index
    Created on : 2024?4?2?, ??2:25:46
    Author     : Lau Ka Ming Benjamin
--%>

<%@page import="ict.bean.User"%>
<%@page import="ict.servlet.ReturnEquipServlet"%>
<%@page import="ict.bean.ReturnEquipmentBean"%>
<%@page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    User user = (User) session.getAttribute("technician");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    ArrayList<ArrayList<ReturnEquipmentBean>> returnRequests = (ArrayList<ArrayList<ReturnEquipmentBean>>) request.getAttribute("returnEquipments");
%>
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
                    <img src="<c:url value='/assets/img/logo.png'/>" alt="logo" class="app-logo" style="height: 60px; width: 60px;">
                </div>
                <ul class="list-unstyled components text-secondary">
                    <li><a href="technician_index.html"><i class="fas fa-home"></i> Dashboard</a></li>
                    <li><a href="WishListEquipmentServlet"><i class="fas fa-heart"></i> Wish List Management</a></li>

                    <li><a href="<c:url value='/view_booking'/>"><i class="fas fa-calendar-check"></i> Booking Management</a></li>
                    <li><a href="EquipmentRequestServlet"><i class="fas fa-exclamation-triangle"></i> Return</a></li>
                    <li><a href="EquipmentRequestServlet"><i class="fas fa-exclamation-triangle"></i> Approved</a></li>   <li><a href="EquipmentRequestServlet"><i class="fas fa-exclamation-triangle"></i> Approved</a></li>

                    <li><a href="logout"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
                </ul>
            </nav>
            <div id="body" class="active">
                <nav class="navbar navbar-expand-lg navbar-white bg-white">
                    <button type="button" id="sidebarCollapse" class="btn btn-light"><i class="fas fa-bars"></i></button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="nav navbar-nav ms-auto">
                            <li class="nav-item dropdown">
                                <a href="#" id="nav2" class="nav-item nav-link dropdown-toggle text-secondary" data-bs-toggle="dropdown">
                                    <i class="fas fa-user"></i> <span>${user.name}</span> <i class="fas fa-caret-down"></i>
                                </a>
                                <div class="dropdown-menu dropdown-menu-end nav-link-menu">
                                    <a href="profile.html" class="dropdown-item"><i class="fas fa-address-card"></i> Profile</a>
                                    <div class="dropdown-divider"></div>
                                    <a href="logout" class="dropdown-item"><i class="fas fa-sign-out-alt"></i> Logout</a>
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
                        <div class="page-title">
                            <h2>Welcome, ${user.name}</h2>
                        </div>
                        <div class="row">
                            <div class="col-md-12">


                                <div id="equipmentListView">
                                    <div class="card">
                                        <div class="card-header">Equipment List</div>
                                        <div class="card-body">
                                            <table class="table table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>Request ID</th>
                                                        <th>Request Date Time</th>
                                                        <th>Requester Name</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%-- Use JSP scriptlet to iterate over the list of request lists --%>
                                                    <%
                                                        for (ArrayList<ReturnEquipmentBean> requestList : returnRequests) {
                                                            // Get the first equipment in the request list to display request details
                                                            ReturnEquipmentBean firstEquipment = requestList.get(0);
                                                    %>
                                                    <tr>
                                                        <td><%= firstEquipment.getRequestId()%></td>
                                                        <td><%= firstEquipment.getRequestDateTime()%></td>
                                                        <td><%= firstEquipment.getRequesterName()%></td>
                                                        <td>
                                                            <button type="button" class="btn btn-primary btn-sm" data-bs-toggle="collapse" data-bs-target="#collapse<%= firstEquipment.getRequestId()%>">
                                                                Show Items
                                                            </button>
                                                            <button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#returnModal<%= firstEquipment.getRequestId()%>">
                                                                Return
                                                            </button>
                                                        </td>
                                                    </tr>
                                                    <tr id="collapse<%= firstEquipment.getRequestId()%>" class="collapse">
                                                        <td colspan="4">
                                                            <table class="table">
                                                                <thead>
                                                                    <tr>
                                                                        <th>Equipment ID</th>
                                                                        <th>Equipment Name</th>
                                                                        <th>Location</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <%-- Iterate over the equipment in the current request list --%>
                                                                    <% for (ReturnEquipmentBean equipment : requestList) {%>
                                                                    <tr>
                                                                        <td><%= equipment.getEquipmentId()%></td>
                                                                        <td><%= equipment.getEquipmentName()%></td>
                                                                        <td><%= equipment.getLocation()%></td>
                                                                    </tr>
                                                                    <% }%>
                                                                </tbody>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                    <%-- Modal for return confirmation and damage report --%>
                                                <div class="modal fade" id="returnModal<%= firstEquipment.getRequestId()%>" tabindex="-1" aria-labelledby="returnModalLabel" aria-hidden="true">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <h5 class="modal-title" id="returnModalLabel">Return Confirmation Request<%= firstEquipment.getRequestId()%></h5>
                                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                            </div>
                                                            <form action="ReturnEquipServlet" method="get">
                                                                <input type="hidden" name="action" value="returnEquipment">
                                                                <div class="modal-body">
                                                                    <%-- Iterate over the equipment in the current request list to display dropdowns --%>
                                                                    <% for (ReturnEquipmentBean equipment : requestList) {%>
                                                                    <div class="mb-3">
                                                                        <label for="returnStatus<%= equipment.getEquipmentId()%>" class="form-label">Equipment: <%= equipment.getEquipmentName()%></label>
                                                                        <input type="hidden" name="requestID" value="<%= equipment.getRequestId()%>">
                                                                        <input type="hidden" name="equipmentId" value="<%= equipment.getEquipmentId()%>">
                                                                        <select class="form-select" name="returnType" id="returnStatus<%= equipment.getEquipmentId()%>" onchange="showTextInput(this)">
                                                                            <option value="confirm">Confirm Return</option>
                                                                            <option value="damage">Damage Report</option>
                                                                        </select>
                                                                        <div id="damageReportInput<%= equipment.getEquipmentId()%>" class="mt-3" style="display: none;">
                                                                            <label for="damageReportText<%= equipment.getEquipmentId()%>" class="form-label">Damage Report:</label>
                                                                            <textarea name="damageReportInput<%= equipment.getEquipmentId()%>" class="form-control" id="damageReportText<%= equipment.getEquipmentId()%>" name="damageReportText<%= equipment.getEquipmentId()%>"></textarea>
                                                                        </div>
                                                                    </div>
                                                                    <% } %>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                                    <button type="submit" class="btn btn-primary">Save changes</button>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>

                                                <% }%>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Modal -->


                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            function showTextInput(selectElement) {
                var equipmentId = selectElement.id.replace("returnStatus", "");
                var damageReportInput = document.getElementById("damageReportInput" + equipmentId);
                var selectedOption = selectElement.value;

                if (selectedOption === "damage") {
                    damageReportInput.style.display = "block";
                } else {
                    damageReportInput.style.display = "none";
                }
            }
        </script>



        <script src="<c:url value='/assets/vendor/jquery/jquery.min.js' />"></script>
        <script src="<c:url value='/assets/vendor/bootstrap/js/bootstrap.bundle.min.js' />"></script>
        <script src="<c:url value='/assets/js/script.js' />"></script>
        <script src="<c:url value='/assets/js/equipment.js' />"></script>
        <script>var baseUrl = "${pageContext.request.contextPath}";</script>
    </body>
</html>

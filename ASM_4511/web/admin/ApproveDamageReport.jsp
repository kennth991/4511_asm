<%-- 
    Document   : index
    Created on : 2024?4?2?, ??2:25:46
    Author     : Lau Ka Ming Benjamin
--%>
<%@page import="ict.bean.DamageReportBean"%>
<%@page import="ict.bean.User"%>
<%@page import="ict.servlet.ApproveDamageReportServlet"%>
<%@page import="ict.bean.EquipmentBean"%>
<%@page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
//    String role = (String) session.getAttribute("role");
    User user = (User) session.getAttribute("admin");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
    ArrayList<DamageReportBean> reports = (ArrayList<DamageReportBean>) request.getAttribute("damageReport");

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
                    <li>
                        <a href="${pageContext.request.contextPath}/AdminEquipment"><i class="fas fa-home"></i> Dashboard</a>
                    </li>
                    <li>
                        <a href="WishListEquipmentServlet"><i class="fas fa-heart"></i> Wish List Management</a>
                    </li>
                    <li>
                        <a href="<c:url value='/view_booking'/>"><i class="fas fa-calendar-check"></i> Booking Management</a>
                    </li>
                    <li>
                        <a href="EquipmentRequestServlet"><i class="fas fa-exclamation-triangle"></i> Approved</a>
                    </li>
                    <li>
                        <a href="ApproveDamageReport"><i class="fas fa-exclamation-triangle"></i> Damage Report</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/admin/CreateAccount.jsp"><i class="fas fa-exclamation-triangle"></i> Approved</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/LogoutServlet"> <i class="fas fa-sign-out-alt"></i> Logout</a>
                    </li>
                </ul>
            </nav>
            <div id="body" class="active">
                <nav class="navbar navbar-expand-lg navbar-white bg-white">
                    <button type="button" id="sidebarCollapse" class="btn btn-light"><i class="fas fa-bars"></i></button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="nav navbar-nav ms-auto">
                            <li class="nav-item dropdown">
                                <a href="#" id="nav2" class="nav-item nav-link dropdown-toggle text-secondary" data-bs-toggle="dropdown">
                                    <i class="fas fa-user"></i> <span>${admin.name}</span> <i class="fas fa-caret-down"></i>
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
                            <h3>Admin Dashboard</h3>
                        </div>
                        <div class="page-title">
                            <h2>Welcome, ${admin.name}</h2>
                        </div>
                        <div class="row">
                            <div class="col-md-12">

                                <div id="equipmentListView">
                                    <div class="card">
                                        <div class="card-header">Damage Report List</div>
                                        <div class="card-body">
                                            <table class="table table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>Report ID</th>
                                                        <th>Name</th>
                                                        <th>Proposer</th>

                                                        <th>Status</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
<tbody>
    <% for (DamageReportBean report : reports) { %>
    <tr>
        <td><%= report.getReportID() %></td>
        <td><%= report.getEquipmentName() %></td>
        <td><%= report.getProposerName() %></td>
        <td><%= report.getStatus() %></td>
        <td>
            <button type="button" class="btn btn-success btn-sm" data-bs-toggle="modal" data-bs-target="#staticBackdrop" onclick="displayReportDescription('<%= report.getDescription() %>', '<%= report.getReportID() %>')" <% if(report.getStatus().equals("approved")) { %> disabled <% } %>>
                <i class="fas fa-edit"></i>
            </button>
        </td>
    </tr>
    <% } %>
</tbody>
                                            <!-- Modal -->
                                            <div class="modal fade" id="staticBackdrop" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title" id="exampleModalLabel">Report Description</h5>
                                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                        </div>
                                                        <form action="ApproveDamageReport" method="get">
                                                            <input type="hidden" name="action" value="approveDamageReport">
                                                            <input type="hidden" id="reportID" name="reportID">
                                                            <div class="modal-body">
                                                                <p id="reportDescription"></p>
                                                            </div>
                                                            
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                                <button type="submit" class="btn btn-primary">Approve</button>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>

                                            <script>
                                                function displayReportDescription(description, reportID) {
                                                    document.getElementById("reportDescription").innerText = description;
                                                    document.getElementById("reportID").value = reportID;
                                                }
                                            </script>

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




        <!-- Modal -->

        <script src="<c:url value='/assets/vendor/jquery/jquery.min.js' />"></script>
        <script src="<c:url value='/assets/vendor/bootstrap/js/bootstrap.bundle.min.js' />"></script>
        <script src="<c:url value='/assets/js/script.js' />"></script>
        <script src="<c:url value='/assets/js/equipment.js' />"></script>
        <script>var baseUrl = "${pageContext.request.contextPath}";</script>
    </body>
</html>

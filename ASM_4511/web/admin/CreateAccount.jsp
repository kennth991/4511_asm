<%-- 
    Document   : CreateAccount
    Created on : 2024年4月29日, 上午10:24:37
    Author     : kenneth
--%>

<%@page import="ict.bean.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    User user = (User) session.getAttribute("admin");
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
        <title>Create User Account</title>
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
                        <a href="${pageContext.request.contextPath}/admin/CreateAccount.jsp"><i class="fas fa-exclamation-triangle"></i> Create Account</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/admin/ManageAccount.jsp"><i class="fas fa-exclamation-triangle"></i> Manage Account</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/technician/UserProfile.jsp"><i class="fas fa-exclamation-triangle"></i> Manage Account</a>
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
                        <div class="container mt-5">
                            <h2>Create User Account</h2>
                            <form action="${pageContext.request.contextPath}/CreateAccountServlet" method="POST">
                                <div class="mb-3">
                                    <label for="name" class="form-label">Name</label>
                                    <input type="text" class="form-control" id="name" name="name" required>
                                </div>
                                <div class="mb-3">
                                    <label for="userName" class="form-label">Login ID</label>
                                    <input type="text" class="form-control" id="userName" name="userName" required>
                                </div>
                                <div class="mb-3">
                                    <label for="password" class="form-label">Password</label>
                                    <input type="password" class="form-control" id="password" name="password" required>
                                </div>
                                <div class="mb-3">
                                    <label for="location" class="form-label">Location</label>
                                    <select class="form-control" id="location" name="location">
                                        <option value="TY">TY</option>
                                        <option value="ST">ST</option>
                                        <option value="TM">TM</option>
                                        <option value="CW">CW</option>
                                        <option value="LWL">LWL</option>
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label for="role" class="form-label">Role</label>
                                    <select class="form-control" id="role" name="role">
                                        <option value="user">User</option>
                                        <option value="technician">Technician</option>
                                        <option value="staff">Staff</option>
                                        <option value="admin">Admin</option>
                                        <option value="courier">Courier</option>
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-primary">Create Account</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/assets/vendor/jquery/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/UserProfile.js"></script>
    </body>

</html>

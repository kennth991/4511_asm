<%-- 
    Document   : book_venue
    Created on : 11 Apr 2024, 1:56:52 pm
    Author     : Lau Ka Ming Benjamin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Insert Item Page</title>
        <link href="assets/vendor/fontawesome/css/fontawesome.min.css" rel="stylesheet">
        <link href="assets/vendor/fontawesome/css/solid.min.css" rel="stylesheet">
        <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="assets/css/master.css" rel="stylesheet">
    </head>

    <body>
        <div class="wrapper">
            <nav id="sidebar" class="active">
                <div class="sidebar-header">
                    <img src="assets/img/logo.png" style="height: 45px; width: 140px;" alt="bootraper logo" class="app-logo">
                </div>
                <ul class="list-unstyled components text-secondary">
                    <li>
                        <a href="supplier_index.html"><i class="fas fa-home"></i> Dashboard</a>
                    </li>
                    <li>
                        <a href="supplier_item.html"><i class="fas fa-table"></i> Item</a>
                    </li>
                    <li>
                        <a href="supplier_report.html"><i class="fas fa-file-alt"></i> Report</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/LogoutServlet"> <i class="fas fa-sign-out-alt"></i> Logout</a>
                    </li>
                </ul>
            </nav>
            <div id="body" class="active">
                <!-- navbar navigation component -->
                <nav class="navbar navbar-expand-lg navbar-white bg-white">
                    <button type="button" id="sidebarCollapse" class="btn btn-light">
                        <i class="fas fa-bars"></i><span></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="nav navbar-nav ms-auto">
                            <li class="nav-item dropdown">
                                <div class="nav-dropdown">
                                    <a href="#" id="nav2" class="nav-item nav-link dropdown-toggle text-secondary" data-bs-toggle="dropdown" aria-expanded="false">
                                        <i class="fas fa-user"></i> <span>${admin.name}</span> <i style="font-size: .8em;" class="fas fa-caret-down"></i>
                                    </a>
                                    <div class="dropdown-menu dropdown-menu-end nav-link-menu">
                                        <ul class="nav-list">
                                            <li><a href="supplier_login.html" class="dropdown-item"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
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
                            <h3>Forms</h3>
                        </div>
                        <div class="card">
                            <div class="card-header">Insert Item</div>
                            <div class="card-body">
                                <h5 class="card-title"></h5>
                                <form class="" action="" method="post">
                                    <div class="mb-3 row">
                                        <label class="col-sm-2">Item ID</label>
                                        <div class="col-sm-10">
                                            <input type="text" name="" class="form-control">
                                        </div>
                                    </div>
                                    <div class="line"></div><br>
                                    <div class="mb-3 row">
                                        <label class="col-sm-2">Supplier ID</label>
                                        <div class="col-sm-10">
                                            <input type="text" name="" class="form-control">
                                        </div>
                                    </div>
                                    <div class="line"></div><br>
                                    <div class="mb-3 row">
                                        <label class="col-sm-2">Item Name</label>
                                        <div class="col-sm-10">
                                            <input type="text" name="" class="form-control">
                                        </div>
                                    </div>
                                    <div class="line"></div><br>
                                    <div class="mb-3 row">
                                        <label class="col-sm-2">Item Image</label>
                                        <div class="col-sm-10">
                                            <input type="file" class="form-control-file" id="exampleFormControlFile1">
                                        </div>
                                    </div>
                                    <div class="line"></div><br>
                                    <div class="mb-3 row">
                                        <label class="col-sm-2">Item Description</label>
                                        <div class="col-sm-10">
                                            <input type="text" name="" class="form-control">
                                        </div>
                                    </div>
                                    <div class="line"></div><br>
                                    <div class="mb-3 row">
                                        <label class="col-sm-2">Stock Item Quantity</label>
                                        <div class="col-sm-10">
                                            <input type="text" name="" class="form-control">
                                        </div>
                                    </div>
                                    <div class="line"></div><br>
                                    <div class="mb-3 row">
                                        <label class="col-sm-2">Item Price</label>
                                        <div class="col-sm-10">
                                            <div class="mb-3">
                                                <div class="input-group mb-3">
                                                    <span class="input-group-text">$</span>
                                                    <input type="text" class="form-control" aria-label="Amount (to the nearest dollar)">
                                                    <span class="input-group-text">.00</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <button class="btn btn-primary" type="submit"><a href="#">Submit</a></button>
                                    <button type="button" class="btn btn-danger"><a href="supplier_item.html">Cancel</a></button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="assets/vendor/jquery/jquery.min.js"></script>
        <script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/script.js"></script>
    </body>

</html>

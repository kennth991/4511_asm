<%-- 
    Document   : index
    Created on : 2024年4月2日, 下午2:26:22
    Author     : kenneth
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Order Main Page</title>
    <link href="assets/vendor/fontawesome/css/fontawesome.min.css" rel="stylesheet">
    <link href="assets/vendor/fontawesome/css/solid.min.css" rel="stylesheet">
    <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/vendor/datatables/datatables.min.css" rel="stylesheet">
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
                    <a href="staff_index.html"><i class="fas fa-home"></i> Dashboard</a>
                </li>
                <li>
                    <a href="order.html"><i class="fas fa-file-alt"></i> Order</a>
                </li>
                <li>
                    <a href="profile.html"><i class="fas fa-address-card"></i>Profile</a>
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
                        <h3> Order</h3>
                    </div>
                    <div class="row">
                        <div class="col-md-12 col-lg-12">
                            <div class="card">
                                <div class="card-header">Recent Order </div>
                                <div class="card-body">
                                    <p class="card-title"></p>
                                    <table class="table table-hover" class="table table-hover" id="dataTables-example" width="100%">
                                        <thead>
                                            <tr>
                                                <th>Order ID</th>
                                                <th>Purchase Manager ID</th>
                                                <th>Manager Name</th>
                                                <th>Order Date &amp; Time</th>
                                                <th>Delivery Date</th>
                                                <th>Order Quantity</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>OID-001</td>
                                                <td>PM001</td>
                                                <td>Alex Lo</td>
                                                <td>14-03-2023 20:21</td>
                                                <td>18-03-2023</td>
                                                <td>10</td>
                                                <td>
                                                    <button type="button" class="btn btn-primary btn-sm"><i class="fa-solid fa-eye"></i></button>
                                                    <button type="button" class="btn btn-success btn-sm"><i class="fas fa-edit"></i></button>
                                                    <button type="button" class="btn btn-danger btn-sm"><i class="fa-solid fa-trash"></i></button>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>OID-002</td>
                                                <td>PM001</td>
                                                <td>Antheol Lo</td>
                                                <td>17-03-2023 10:21</td>
                                                <td>22-03-2023</td>
                                                <td>20</td>
                                                <td>
                                                    <button type="button" class="btn btn-primary btn-sm"><i class="fa-solid fa-eye"></i></button>
                                                    <button type="button" class="btn btn-success btn-sm"><i class="fas fa-edit"></i></button>
                                                    <button type="button" class="btn btn-danger btn-sm"><i class="fa-solid fa-trash"></i></button>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>OID-003</td>
                                                <td>PM001</td>
                                                <td>Anson Lo</td>
                                                <td>25-03-2023 20:21</td>
                                                <td>30-03-2023</td>
                                                <td>30</td>
                                                <td>
                                                    <button type="button" class="btn btn-primary btn-sm"><i class="fa-solid fa-eye"></i></button>
                                                    <button type="button" class="btn btn-success btn-sm"><i class="fas fa-edit"></i></button>
                                                    <button type="button" class="btn btn-danger btn-sm"><i class="fa-solid fa-trash"></i></button>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <button class="btn btn-primary" type="submit"><a href="create_order.html">Create Order</a></button>
                                </div>
                            </div>
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

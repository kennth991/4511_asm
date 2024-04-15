<%-- 
Document   : view_devices
Created on : 2024年4月13日
Author     : kenneth
--%>

<%@page import="ict.bean.Venue"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ict.bean.User"%>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    List<Venue> listVenue = (List<Venue>) request.getAttribute("listVenue");
%>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Available Venues</title>
        <link href="${pageContext.request.contextPath}/assets/vendor/fontawesome/css/fontawesome.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/vendor/fontawesome/css/solid.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/vendor/datatables/datatables.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/assets/css/master.css" rel="stylesheet">
        <script src="${pageContext.request.contextPath}/assets/vendor/jquery/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
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
                    <li class="active">
                        <a href="${pageContext.request.contextPath}/user/view_devices"><i class="fas fa-laptop"></i> View Available Devices</a>
                    </li>
                    <li>
                        <a href="borrowing_records.html"><i class="fas fa-history"></i> Personal Borrowing Records</a>
                    </li>
                    <li>
                        <a href="wish_list.html"><i class="fas fa-heart"></i> Wish List</a>
                    </li>
                    <li>
                        <a href="reserve_check_out.html"><i class="fas fa-hand-holding"></i> Reserve, Check-out, Return Equipment</a>
                    </li>
                    <li>
                        <a href="update_info.html"><i class="fas fa-user-cog"></i> Update Password and Personal Information</a>
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
                </nav>    
                <div class="content">
                    <div class="container">
                        <div class="page-title">
                            <h3>Available Venues</h3>
                        </div>
                        <div class="row">
                            <div class="col-md-12 col-lg-12">
                                <div class="card">
                                    <div class="card-header">List of Venues</div>
                                    <table class="table table-striped">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Name</th>
                                                <th>Description</th>
                                                <th>Location</th>
                                                <th>Status</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% if (listVenue != null && !listVenue.isEmpty()) {
                                                    for (Venue venue : listVenue) {%>
                                            <tr>
                                                <td><%= venue.getVenueID()%></td>
                                                <td><%= venue.getName()%></td>
                                                <td><%= venue.getDescription()%></td>
                                                <td><%= venue.getLocation()%></td>
                                                <td><%= venue.getStatus()%></td>
                                                <td>
                                                    <button class="btn btn-primary" onclick="setBookingDetails('<%= venue.getVenueID()%>', '<%= venue.getName()%>')">Book</button>
                                                </td>
                                            </tr>
                                            <% }
                                            } else { %>
                                            <tr><td colspan="6">No venues available.</td></tr>
                                            <% }%>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Booking Modal -->
            <div class="modal fade" id="bookingModal" tabindex="-1" role="dialog" aria-labelledby="bookingModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="bookingModalLabel">Book Venue</h5>
                        </div>
                        <div class="modal-body">
                            <form id="bookingForm">
                                <div class="form-group">
                                    <label for="venueName">Venue Name</label>
                                    <input type="text" class="form-control" id="venueName" name="venueName" readonly>
                                </div>
                                <div class="form-group">
                                    <label for="bookingDate">Booking Date</label>
                                    <input type="date" class="form-control" id="bookingDate" name="bookingDate" required onchange="updateAvailableSlots($('#venueId').val(), this.value)">
                                </div>
                                <div class="form-group">
                                    <label for="startTime">Start Time</label>
                                    <select class="form-control" id="startTime" name="startTime">
                                        <option value="09:00">09:00 AM</option>
                                        <option value="11:00">11:00 AM</option>
                                        <option value="13:00">01:00 PM</option>
                                        <option value="15:00">03:00 PM</option>
                                    </select>
                                </div>
                                <input type="hidden" id="venueId" name="venueId">
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" onclick="submitBooking()">Submit Booking</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
        <script>
                                var baseUrl = "${pageContext.request.contextPath}";
        </script>
        <script src="${pageContext.request.contextPath}/assets/js/view_venue.js"></script>
    </body>
</html>

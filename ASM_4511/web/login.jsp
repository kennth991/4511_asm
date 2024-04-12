<%-- 
    Document   : login
    Created on : 2024年4月1日, 下午11:45:55
    Author     : kenneth
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="assets/css/login.css">
        
    </head>
    <div class="login-page">
        <div class="form">
            <form class="register-form">
                <input type="text" placeholder="name"/>
                <input type="password" placeholder="password"/>
                <input type="text" placeholder="email address"/>
                <button>create</button>
                <p class="message">Already registered? <a href="#">Sign In</a></p>
            </form>
            <form class="login-form" action="loginController" method="post">
                <input type="text" placeholder="username" name="username"/>
                <input type="password" placeholder="password" name="password"/>
                <button type="submit">login</button>
                <p class="message">Not registered? <a href="#">Create an account</a></p>
            </form>
        </div>
    </div>
    <script src="assets/vendor/jquery/jquery.min.js"></script>
    <script src="assets/js/login.js"></script>
</html>

<%-- 
    Document   : debugOutput
    Created on : 2024年4月15日, 下午8:41:28
    Author     : kenneth
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Debug Output</title>
</head>
<body>
    <h1>Received Data</h1>
    <table border="1">
        <tr>
            <th>Parameter Name</th>
            <th>Values</th>
        </tr>
        <c:forEach var="entry" items="${params}">
            <tr>
                <td>${entry.key}</td>
                <td>
                    <c:forEach var="value" items="${entry.value}">
                        ${value} <br>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>

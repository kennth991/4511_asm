<%-- 
    Document   : cart
    Created on : 2024?4?14?, ??12:58:28
    Author     : kenneth
--%>

<%@page import="ict.bean.EquipmentBean"%>
<%@page import="ict.db.EquipmentDB"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%
    List<Integer> cart = (List<Integer>) session.getAttribute("cart");
    String url = "jdbc:mysql://localhost:3306/4511_asm";
    String username = "root";
    String password = "";
    
    EquipmentDB db = new EquipmentDB(url, username, password);
    List<EquipmentBean> cartItems = new ArrayList<>();
    if (cart != null) {
        for (Integer id : cart) {
            cartItems.add(db.getEquipmentById(id));  // Assuming a method that fetches equipment by ID
        }
    }
%>
<div class="container">
    <h2>Your Cart</h2>
    <table class="table">
        <thead>
            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Remove</th>
            </tr>
        </thead>
        <tbody>
            <%
                for (EquipmentBean item : cartItems) {
            %>
            <tr>
                <td><%= item.getName()%></td>
                <td><%= item.getDescription()%></td>
                <td><button onclick="location.href = '<%= request.getContextPath()%>/removeFromCart?id=<%= item.getEquipmentID()%>'">Remove</button></td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
    <div>
        <button onclick="location.href = '<%= request.getContextPath()%>/checkout'">Checkout</button>
    </div>
</div>

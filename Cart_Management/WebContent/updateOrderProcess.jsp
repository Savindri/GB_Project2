<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import = "model.OrderController" %>


<!-- Calling Update -->
<%
	if (request.getParameter("orderID") != null &&
		request.getParameter("cartID_f") != null &&
		request.getParameter("date") != null && 
		request.getParameter("custName") != null &&
		request.getParameter("address") != null &&
		request.getParameter("phone") != null &&
		request.getParameter("email") != null){
		OrderController orderObj = new OrderController();
		String stsMsg = orderObj.updateOrder(request.getParameter("orderID"),
												request.getParameter("cartID_f"),
												request.getParameter("date"),
												request.getParameter("custName"),
												request.getParameter("address"),
												request.getParameter("phone"),
												request.getParameter("email")); 
		session.setAttribute("statusMsg", stsMsg);
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="Views/bootstrap.min.css" rel="stylesheet" media="all">
<title>Update Order</title>
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col">

				<h2>Update Order</h2>
				<form method="post" action="Order.jsp">
					Date: <input name="date" type="date" required class="form-control"><br>
					Customer Name: <input name="custName" type="text" required class="form-control"><br>
					Delivery Address: <input name="address" type="text" required class="form-control"><br>
					Phone Number: <input name="phone" type="tel" required class="form-control"><br>
					E-mail: <input name="email" type="email" required class="form-control"><br>
					<input name="btnSubmit" type="submit" value="Save" class="btn btn-primary">
				</form>
				<br>
				
				<div class="alert alert-success">
					<% 
						out.print(session.getAttribute("statusMsg"));
					%>
				</div>
				<hr>
				
				<br>
							
					<%
						OrderController orderObj = new OrderController();
						out.print(orderObj.readOrder());
					%>
					
			</div>
		</div>
	</div>

</body>
</html>
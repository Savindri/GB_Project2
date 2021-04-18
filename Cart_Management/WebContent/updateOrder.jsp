<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import = "model.OrderController" %>

<!-- retrieving the passed parameters from Order.jsp to update the form -->
<%
	String orderID = request.getParameter("orderID");
	String cartID_f = request.getParameter("cartID_f");
	String date = request.getParameter("date");
	String custName =  request.getParameter("custName");
	String address = request.getParameter("address");
	String phone = request.getParameter("phone");
	String email = request.getParameter("email");
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
	
				<%-- Update Form --%>
				<h2>Update Order</h2>
				<form method="post" action="updateOrderProcess.jsp">
					<input name="orderID" type="text" value="<%=orderID%>" hidden><br>
					<input name="cartID_f" type="text" value="<%=cartID_f%>" hidden><br>
						Date: <input name="date" type="date" value="<%=date%>" required class="form-control"><br>
						Customer Name: <input name="custName" type="text" value="<%=custName%>" required class="form-control"><br>
						Delivery Address: <input name="address" type="text" value="<%=address%>" required class="form-control"><br>
						Phone Number: <input name="phone" type="tel" value="<%=phone%>" required class="form-control"><br>
						E-mail: <input name="email" type="email" value="<%=email%>" required class="form-control"><br>
					
							<input name="btnSubmit" type="submit" value="Update" class="btn btn-primary">
								<input name='orderID' type='hidden' value="orderID">
								<input name='cartID_f' type='hidden' value="cartID_f">
								<input name='custName' type='hidden' value="custName">
								<input name='address' type='hidden' value="address">
								<input name='phone' type='hidden' value="phone">
								<input name='email' type='hidden' value="email">
								
				</form>		
				<br>
				
				<div class="alert alert-success">
					<% 
						out.print(session.getAttribute("statusMsg"));
					%>
				</div>
				<br>
				 
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
<%@ page import="model.OrderController"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%	
	//Add a handler for insert 
	if (request.getParameter("date") != null){
		OrderController orderObj = new OrderController();
		String stsMsg = orderObj.insertOrder(request.getParameter("date"),
												request.getParameter("custName"),
												request.getParameter("address"),
												request.getParameter("phone"),
												request.getParameter("email"));
		session.setAttribute("statusMsg", stsMsg);
	}

    //Add a handler for delete 
	if(request.getParameter("orderID") != null){ 
		 OrderController orderObj = new OrderController();
		 String stsMsg = orderObj.deleteOrder(request.getParameter("orderID"));
		 session.setAttribute("statusMsg", stsMsg);
	}
	
%>

<!DOCTYPE html>
<html>
	<head>
	<meta charset="ISO-8859-1">
	<link href="Views/bootstrap.min.css" rel="stylesheet" media="all">
	<!-- <script src="js/validation.js" type="text/javascript"></script> -->
	<title>Order Management</title>
	</head>
	<body>
	
		<div class="container">
			<div class="row">
				<div class="col">
	
					<h1>Order</h1>
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
				    <br>
				    <hr>
				    
				    <%--Calling Read --%>
					<%
						OrderController orderObj = new OrderController();
					 	out.print(orderObj.readOrder());
					%>
					
				 </div>
			 </div>
		 </div>
		
	</body>
</html>
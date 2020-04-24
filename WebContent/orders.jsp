<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/index.css">	
<%@taglib uri="/DuckyTagsLibrary" prefix="ctl"%>
<title>Orders</title>
</head>
<body>
	
	<ctl:navbar/>
	<div class='root'>
		<ctl:sidemenu/>
		<div  align='center' class='main'>
			<h1>My Orders</h1>
			
			<c:choose>
				<c:when test="${not empty resMsg }" >
					<h2 style='color: red; ' >${resMsg }</h2>
				</c:when>
				<c:otherwise>
					<c:if test="${empty orderList }">
						<h2 style='color: red;' >No order found</h2>
					</c:if>	
				</c:otherwise>			
			</c:choose>

			<c:forEach items="${orderList}" var="order"  >
				<hr>
				<h3>Order No: ${order.orderId}</h3>
				<h4>Date: ${order.orderDate}</h4>
				
				<table border='1' cellpadding='10' cellspacing='0'  width='100%'>
					<col style='width:50%; ' >
					<col style='width:25%; ' >
					<col style='width:25%; ' >
					<tr>
						<th bgcolor='#B0C4DE' >Name</th> 
						<th bgcolor='#B0C4DE' >Qty</th> 
						<th bgcolor='#B0C4DE' >Shipped</th> 
					</tr>
					
					<c:forEach items="${order.items}" var="od" >
						<tr>
							<td>${od.itemName}</td>
							<td>${od.qty }</td>
							<td>${od.delivered}</td>	
						</tr>
					</c:forEach>
					
					<tr><td>
						<c:choose>
							<c:when test="${order.status.id == 1 }">
								<a bgcolor='#E6E6FA' style='width:100%; height:100%; padding:10px;' 
									href='CancelOrder?oid=${order.orderId}'>Cancel Order</a>
							</c:when>
							<c:otherwise>
								Status: ${order.status.name }
							</c:otherwise>
						</c:choose>
					</td></tr>
				</table>
			</c:forEach>
			
		</div>
	</div>
	
</body>
</html>
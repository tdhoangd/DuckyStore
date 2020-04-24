<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/index.css">	
<%@taglib uri="/DuckyTagsLibrary" prefix="ctl"%>
<title>Cart</title>
</head>
<body>
	
	<ctl:navbar/>
	<div class='root'>
		<ctl:sidemenu/>
		<div  align='center' class='main'>
			<h1>Cart</h1>
						
			<c:choose>
				<c:when test="${not empty resMsg }" >
					<h3 style='color: red; ' >${resMsg }</h3>
				</c:when>
				<c:otherwise>
					<c:if test="${empty sessionScope.cart }">
						<h3 style='color: red;' >Nothing in cart</h3>
					</c:if>	
				</c:otherwise>			
			</c:choose>
			
			<c:if test="${not empty sessionScope.cart }">
			<form method='get' action='ProcessCart' >
				<table border='1' cellpadding='10' cellspacing='0'  width='100%'>	
					<tr>
						<th bgcolor='#B0C4DE' >Item</th> 
						<th bgcolor='#B0C4DE' >Qty</th> 
						<th bgcolor='#B0C4DE' >Price</th> 
						<th></th> 
					</tr>
			
				<c:forEach items="${sessionScope.cart}" var="entry"  >
					<tr>
						<td width='50%' >${entry.value.name }</td>
						<td width='15%'>
							<input name='${entry.value.itemId}' type='number' value='${entry.value.quantity }' >
						</td>
						<td width='15%' >
							$<fmt:formatNumber type="number" maxFractionDigits="2" value="${entry.value.price * entry.value.quantity }"   />	
						</td>
						<td width='10%'>
							<input style='padding:5px; width:100%;'  type='submit' value='Update' name='update-btn-${entry.value.itemId}' >
						</td>		
						<td width='10%'>
							<input style='padding:5px; width:100%;' type='submit' value='Delete' name='delete-btn-${entry.value.itemId}' >
						</td>
					</tr>
				</c:forEach>
					<tr>
						<td></td>
						<td>Total: </td>
						<td>$<fmt:formatNumber type="number" maxFractionDigits="2" value="${total }"   /></td>
						<td></td>
						<td></td>
					</tr>
					
					<tr>
						<td>
						<input type='submit' value='Check out' name='checkout' style='width:100%; height:100%; padding:10px; ' >
						</td>
					</tr>
				</table>
			</form>
			</c:if>	
				
		</div>
	</div>
	
</body>
</html>
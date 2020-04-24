<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/index.css">	
<%@taglib uri="/DuckyTagsLibrary" prefix="ctl"%>
<title>Ducky Ducky</title>
</head>
<body>
	
	<ctl:navbar/>
	<div class='root'>
		<ctl:sidemenu/>
		<div  align='center' class='main'>
			
			<h2>Contact Info</h2>
			<c:if test="${empty contact }">
				<h3 style='color: red; ' >Contact not found.</h3>
			</c:if>

			<c:if test="${not empty resMsg }">
				<div style='color: red;' ><c:out value="${resMsg }"></c:out></div>
			</c:if>
		
			<form action='./ModifyContactServlet' method='get'>
				<table style='padding: 5px;' >
					<tr>
						<td>First Name: </td>
						<td><input name='fname' placeholder='${contact.fName}' ></td>
					</tr>
					<tr>
						<td>Mid Name: </td>
						<td><input name='mname' placeholder='${contact.mName}' ></td>
					</tr>
					<tr>
						<td>Last Name: </td>
						<td><input name='lname' placeholder='${contact.lName}' ></td>
					</tr>
					<tr>
						<td>Emai: </td>
						<td><input name='email' placeholder='${contact.email}' ></td>
					</tr>
					<tr>
						<td>Address: </td>
						<td><input name='address' placeholder='${contact.address}' ></td>
					</tr>
					<tr>
						<td>City: </td>
						<td><input name='city' placeholder='${contact.city}' ></td>
					</tr>
					<tr>
						<td>State: </td>
						<td><input name='state' placeholder='${contact.state}' ></td>
					</tr>
					<tr>
						<td>Zipcode: </td>
						<td><input name='zipcode' placeholder='${contact.zipcode}' ></td>
					</tr>
					<tr>
						<td>Country: </td>
						<td><input name='country' placeholder='${contact.country}' ></td> 
					</tr>
				</table>
				
				<c:choose>
					<c:when test="${empty contact }">
						<input style='padding: 5px;' name='add-contact-btn' type='submit' value='Add Contact'>
					</c:when>
					<c:otherwise>
						<input style='padding: 5px;' name='update-contact-btn' type='submit' value='Update Contact'>
					</c:otherwise>
				</c:choose>				
				
			</form>
							
		</div>
	</div>
		
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/index.css">	
<%@taglib uri="/DuckyTagsLibrary" prefix="ctl"%>
<title>Ducky</title>
</head>
<body>
	
	<ctl:navbar/>
	<div class='root'>
		<ctl:sidemenu/>
		<div  align='center' class='main'>
			
			<c:if test="${empty itemList }">
				<h2>No item found</h2>
			</c:if>
			
			<c:forEach items="${itemList}" var="item" >
				<a href='./ViewItem?iid=${item.itemId }' >
					<figure>
						<img alt='Alt Image of ${item.name}' src='./images/${item.picPath}' 
								width='150' height='150'>
						<figcaption >${item.name}</figcaption>
					</figure>
				</a>
			</c:forEach>
		</div>
	</div>
	
</body>
</html>
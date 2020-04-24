<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/index.css">	
<%@taglib uri="/DuckyTagsLibrary" prefix="ctl"%>
<title>View Item</title>
</head>
<body>
	
	<ctl:navbar/>
	<div class='root'>
		<ctl:sidemenu/>
		<div align='center' class='main'>				
			<div>
				<img alt='Image of ${item.name }' src='./images/${item.picPath }' width='300' height='300'>  
			</div>	
		
			<h1>${item.name }</h1>
			<h2 style='color: red;'>${item.price }</h2>
			<div size='30'>${item.description }</div>	
			<div style="margin-top:10px;margin-bottom:10px;">
				<form action='AddToCart' method='post' >	
					<input style='width=30px;' name='qty' type='number' required >	
					<input type='hidden' name='name' value='${item.name}' >
					<input type='hidden' name='iid' value='${item.itemId}' >
					<input type='hidden' name='price' value='${item.price }' >
					<input type='hidden' name='cost' value='${item.cost }' >
					<input type='hidden' name='stock' value='${item.stock }' >
					<input type='submit' value='Add to Cart' >
				</form>
			</div>	

		</div>
	</div>
		
</body>
</html>
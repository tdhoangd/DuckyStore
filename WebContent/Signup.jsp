<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/index.css">	
<%@taglib uri="/DuckyTagsLibrary" prefix="ctl"%>
<title>Signup</title>
</head>
<body>

	<ctl:navbar/>
	<div class='root'>
		<div align='center'  class='main'>
			<h2>Create new account</h2>
			<form action="./SignupServlet" method="post">
				<table style="padding: 2px"> 
					<tr>
						<td>User name: </td>
						<td><input name="username" type="text" size="20" ></td>
						<td style="color: red">${errorMsg}</td>
					</tr>
					<tr>
						<td>Password: </td>
						<td><input name="password" type="password" size="20"></td>
					</tr>
					<tr>
						<td>Confirm Password: </td>
						<td><input name="confirm-password" type="password" size="20"></td>
					</tr>
					<tr>
						<td>Full Name: </td>
						<td><input name="fullname" type="text" size="20"></td>
					</tr>
					<tr>
						<td>Email: </td>
						<td><input name="email" type="text" size="20"></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Sign Up"></td>
						<td></td>
					</tr>
				</table>
			</form>		
		</div>
	</div>

</body>
</html>
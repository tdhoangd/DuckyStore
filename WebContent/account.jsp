<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/index.css">	
<%@taglib uri="/DuckyTagsLibrary" prefix="ctl"%>
<title>Account Info</title>
</head>
<body>
	
	<ctl:navbar/>
	<div class='root'>
		<ctl:sidemenu/>
		<div align='center' class='main'>				

			<h3>Account Info: </h3>
			
			<form action='./ModifyAccountServlet' method="get">
				<table style="padding: 2px">
					<tr>	
						<td>User name: </td>	
						<td>${username }</td>
					</tr>
					<tr>
						<td>Full Name: </td>
						<td><input name='fullname' type='text' size='20' placeholder='${fullname}' ></td>
						<td style='color: red;' >${editMsg}</td>
					</tr>
					<tr>
						<td>Email: </td>
						<td><input name="email" type="text"  placeholder='${email}' ></td>
					</tr>
				</table>
				<input style='padding: 5px;' type="submit" value='Update'>
			</form>

			<h3>Change Password</h3>
			
			<p style='color: red; '>${changeMsg}</p>
			<form action='./ChangePasswordSerlvet' method='post'>
				<table style='padding: 2px;'>
					<tr> 
						<td>Old password: </td>
						<td><input name='old-password' type='password' size='20'></td>
					</tr>
					<tr>
						<td>New password: </td>
						<td><input name='new-password' type='password' size='20'></td>  
					</tr>  
					<tr>  
						<td>Confirm new password: </td>  
						<td><input name='confirm-new-password' type='password' size='20'></td>  
					</tr>  
				</table>  
				<input style='padding: 5px;' type='submit' value='Change Password'>  
			</form>  	

		</div>
	</div>
		
</body>
</html>
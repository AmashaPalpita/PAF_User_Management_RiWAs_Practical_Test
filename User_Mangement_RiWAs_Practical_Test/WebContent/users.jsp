<%@page import="com.Users"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<!-- css files -->
<link href="Views/bootstrap.min.css" rel="stylesheet"> 

<!-- javascript files -->
<script src="Components/jquery-3.6.0.min.js"> </script>
<script src="Components/users.js" type="text/javascript"></script>

<title>User Management</title>

</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col">
				<h1> User Management </h1>
				<hr>
				<form id="formUser" name="formUser">
					<div class="form-group row"> 
						<label class="control-label col-sm-2" for="username">User Name</label> 
						<div class="col-10"> 
							<input id="name" name="name" type="text" class="form-control">
						</div> 
					</div> 		
					<div class="form-group row"> 
						<label class="control-label col-sm-2" for="username">User Email</label> 
						<div class="col-10"> 
							<input id="email" name="email" type="email" class="form-control">
						</div> 
					</div> 		
					<div class="form-group row"> 
						<label class="control-label col-sm-2" for="username">User Type</label> 
						<div class="col-10"> 
							<input id="userType" name="userType" type="text" class="form-control">
						</div> 
					</div> 
					<div class="form-group row"> 
						<label class="control-label col-sm-2" for="username">User Password</label> 
						<div class="col-10"> 
							<input id="pw" name="pw" type="password" class="form-control"> 
						</div> 
					</div> 																
					
					<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-success">
					<input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value="">
				</form>
				
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div  id="divUsersGrid">
						<%
							Users userObj = new Users();
							out.print(userObj.readUsers());
						%>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
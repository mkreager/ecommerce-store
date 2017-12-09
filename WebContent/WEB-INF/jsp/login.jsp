<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/parsley.css">
    <script src="js/lib/jquery-3.2.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
    <script src="js/lib/bootstrap.min.js"></script>
    <script src="js/lib/parsley.min.js"></script>
    <title>Login</title>
</head>
<body style="padding-top: 5rem;">
    <%@ include file="../../template/navbar.jsp" %>
    <%@ include file="../../template/messages.jsp" %>  
    <div class="container w-50">
        <h2>Login</h2>
        <form method="POST" action="login" data-parsley-validate>
            <div class="form-group">
	        <label for="email">Username (email)</label>
		<input type="email" class="form-control" id="email" name="email" data-parsley-trigger="change" required>
            </div>
	    <div class="form-group">
	        <label for="password">Password</label>
		<input type="password" class="form-control" id="password" name="password" required>
            </div>
	    <button type="submit" class="btn btn-primary">Submit</button>
	</form>
	<br>
	<p>New user? <a href="account">Sign up here</a>.
    </div>
</body>
</html>

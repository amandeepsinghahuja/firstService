<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>User Home Page</title>
	<link rel="stylesheet" type="text/css" href="css/Common.css">
</head>
<body  background="images/login_bg.jpg">
<form method="POST" action="/EasyDP/abc">
   <table class="userHomeTable">
        <tr><td colspan="3" align="left"><h1>&nbsp;&nbsp;Select your task .....</h1></td></tr>
        <tr>
                <td class="userHomeCells"><a href="/EasyDP/certUploadNew"><img  src="images/Certificate_Upload.jpg" height="150px" width="150px" border="0"/><br/>Certificate Upload</a></td>
                <td class="userHomeCells"><a href="/EasyDP/fileUploadHomeNew"><img  src="images/File_Upload.jpg" height="150px" width="150px" border="0"/> <br/>File Upload</a></td>               
                <td class="userHomeCells"><a href="/EasyDP/cryptoCertUpdate"><img  src="images/Crypto_Validation.jpg" height="150px" width="150px" border="0"/><br/>CryptoValCred Update</a></td>
	</tr>
	<tr/>
	<tr>
                <td class="userHomeCells"><a href="/EasyDP/checkDomainStatus"><img  src="images/Check_Object.jpg" height="150px" width="150px" border="0"/><br/>Check Domain Status</a></td>
                <td class="userHomeCells"><a href="/EasyDP/checkDomainHealth"><img  src="images/Health_CheckUp.jpg" height="150px" width="150px" border="0"/><br/>Check Domain Health</a></td>
                <td class="userHomeCells"><a href="/EasyDP/checkDomainStatus"><img  src="images/Coming_Soon.jpg" height="150px" width="150px" border="0"/><br/>Coming Soon...</a></td>
	</tr>
</table>
</form>
</body>
</html>
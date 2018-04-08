<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
        <head>
                <link rel="stylesheet" type="text/css" href="css/Common.css">
                <title>Easy Stub Login Page</title>
        </head>
        <body background="images/login_bg.jpg">
               
                        <form action="/EasyStub/userHome" method="post" name="loginForm" id="loginForm" autocomplete="off" role="form">
                                 <table class="commonTable">                                        
                                        <tr><td colspan="2" ><h1>&nbsp;&nbsp;&nbsp;Easy Stub Login .....</h1></td></tr>
                                        <tr>
                                              <%--   <td><label for="DPBox"><h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select Existing Server:</h3></label></td>
                                                <td>
                                                        <select name="ipAdd" id="ipAdd" >
                                                                <option value="Select">Select</option>
                                                                <c:forEach var="hostValue" items="${hosts}">
                                                                       <option>${hostValue}</option>
                                                                </c:forEach>
                                                        </select>
                                                </td> --%>
                                        </tr>
                                        <tr>
                                                <td><label for="user"><h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;User Name:</h3></label></td>
                                                <td><input type="text" name="username" id="user" value="" size="22" autocomplete="off">&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                        </tr>
                                        <tr>
                                                <td><label for="pass"><h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Password:</h3></label></td>
                                                <td><input type="password" name="password"  id="pass" size="22" onkeypress="displayCapsState(event, this);" autocomplete="off"></td>
                                        </tr>
                                        <tr>
                                                <td></td>
                                                <td><button type="submit" id="login_btn">Login</button> &nbsp; <button type="reset" id="cancel_btn">Cancel</button></td>
                                        </tr>
                                        <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td/></tr>
                                 </table>
                       </form>
               
        </body>
</html>
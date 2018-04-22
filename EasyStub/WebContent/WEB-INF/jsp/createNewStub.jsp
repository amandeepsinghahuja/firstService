<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/Common.css">
<title>Create Stub</title>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
        function removeStatus() {
        document.getElementById('status').style.visibility = 'hidden';
        document.getElementById('status').innerHTML = "";
        }
        
        function getFileUploadStatus() {
         	var reqType = $('#reqType').val();
            var path = $('#filePath').val();
            var inURI = $('#stubURI').val();
            var fileName = path.substring(path.lastIndexOf('\\')+1,path.length);
            var URL = 'fileUploadSubmit.html';
            window.alert(reqType);
            window.alert(fileContent);
            $.ajax
            (
             		{
             			 type: "POST",
              		 	 data: { "arguments" : ["fileName:"+fileName+"@fileContent:"+fileContent+"@reqType:"+reqType+"@inURI:"+inURI].toString() },
                      	 url : URL,
                       	 success : function(data)
              		 	 {
                      		alert(data);
                       	 }
                  }
           );
           } 
      </script>
</head>
<body background="images/login_bg.jpg">
	<form action="/EasyStub/userHome" method="post">
		<table class="commonTable">
			<tr>
				<td colspan="2"><h1>&nbsp;&nbsp;&nbsp;Create New Stub.....</h1></td>
			</tr>
			<tr>
				<td><label for="stubType"><h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Stub
							Type:</h3></label></td>
				<td><select id="reqType" name="reqVal"
					onchange="removeStatus();">
						<option value="1">Select</option>
						<option value="SOAP">SOAP</option>
						<option value="JSON">JSON</option>
						<option value="XML">XML</option>
				</select></td>
			</tr>
			<tr>
				<td>
			<tr>
				<td><label for="URI"><h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;URI:</h3></label></td>
				<td><input id="stubURI" type="text" /></td>
				</td>
			</tr>
			<tr>
			<tr>
				<td><label for="File"><h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Upload
							Response File:</h3></label></td>
				<td><input type="file" id="filePath" name="upload"
					onchange='openFile(event)'> <script>
      			var openFile = function(event) {
	    			var input = event.target;
	    			var reader = new FileReader();
	    			reader.onload = function(){
	    				fileContent = reader.result;
	    			};
	    			reader.readAsText(input.files[0]);
    			};
     </script></td>
			</tr>
			<tr>
				<td />
				<td><input type="button" value="Submit"
					onclick="getFileUploadStatus()" />&nbsp;&nbsp;&nbsp; <input
					type="submit" id="backButton" value="Back" /></td>
			</tr>
			<tr>
				<td></td>
				<td><label id="status"
					style="visibility: hidden; font-size: large; font-style: italic; font: bold;"></label></td>
			</tr>
			<tr>
				<td>&nbsp;&nbsp;</td>
				<td>&nbsp;&nbsp;</td>
			</tr>
		</table>
	</form>
</body>
</html>
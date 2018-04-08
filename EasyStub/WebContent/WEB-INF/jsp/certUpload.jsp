<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Certificate Upload</title>
	<script type="text/javascript"
    src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
    <script type="text/javascript">
   		function getCertUploadStatus() {
   			
   			var domain = $('#domainList').val();
   			var dir = "cert://";
   			var path = $('#filePath').val();
   			var name = path.substring(path.lastIndexOf('\\')+1,path.length);
   			var fileName = dir+"/"+name;
   			$.ajax
   			(
   				{
	   				data: { "arguments" : ["domain:"+domain+"@fileName:"+fileName+"@fileContent:"+fileContent].toString() },
	                url : 'fileUploadSubmit.html',
	                success : function(data)
	    			{
	             		alert(data);
	                }
                }
		   );
   		}
	</script>
</head>
<body>
 
<h2>Certificate Details<br/><br/></h2>
<form>
   <table>
    <tr>
       <td>Select Domain</td>
       <td>
              <select id="domainList" name="domainVal">
              <option value="1">Select</option>
              	<c:forEach var="domainValue" items="${domains}">
                	<option value="${domainValue}">${domainValue}</option>
              	</c:forEach>
              </select>
       </td>
    </tr>
    <tr>
    	<td></td>
    </tr>
    <tr>
    	<td>Upload Cert File</td>
    	<td>
    		<input type="file" id="filePath" name="upload" onchange='openFile(event)' style="width: 413px; ">
    		<script>
      				var openFile = function(event)
      				{
	    				var input = event.target;
	    				var reader = new FileReader();
	    				reader.onload = function(){
	    					fileContent = reader.result;
	    			 	};
	    				reader.readAsDataURL(input.files[0]);
    			     };
     		</script>
		</td>
    </tr>
    <tr>
    	<td></td>
        <td style="width: 275px; height: 54px; ">
            <input type="button" value="Submit" onclick="getCertUploadStatus()"/>
        </td>
    </tr>
</table> 
</form>
</body>
</html>
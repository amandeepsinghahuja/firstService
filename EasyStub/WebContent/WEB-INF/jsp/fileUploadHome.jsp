<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<script type="text/javascript"
    	src="http://code.jquery.com/jquery-1.10.1.min.js">
	</script>    
    <script type="text/javascript">
     function getDirectories() {
     var domain = $('#domainList').val();
     $.ajax(
            {
             data: { "arguments" : ["domain:"+domain].toString() },
           	 url : 'getFileStore.html',
           	 success : function(data)
           	 {
                   $('#dirList').empty();
                   var selOption = $('<option value="1">Select</option>');
                   $('#dirList').append(selOption);
                   var res = data.split("#");
                   for( var i = 0; i < res.length - 1 ; i++ )
                   {
                         var newOption = $('<option value='+ res[i] +'>'+res[i]+'</option>');
                         $('#dirList').append(newOption);
                   }
             }
             }
   			);
     }
     function getFileUploadStatus() {
     	
      var domain = $('#domainList').val();
      var dir = $('#dirList').val();
      var path = $('#filePath').val();
      var name = path.substring(path.lastIndexOf('\\')+1,path.length);
      var fileName = dir+"/"+name;
      window.alert(fileContent);
      $.ajax
      (
       		{
       			 type: "POST",
        		 data: { "arguments" : ["fileName:"+fileName+"@fileContent:"+fileContent].toString() },
                 url : 'fileUploadSubmit.html',
                 success : function(data)
        		 {
                	alert(data);
                 }
            }
     );
     } 
</script>
<title>File Upload</title>
</head>
<body>
 
<h2>Upload File Details<br/><br/></h2>
<form>
   <table>
    <tr>
       <td>Select Domain</td>
       <td>
              <select id="domainList" onchange="getDirectories()">
              <option value="1">Select</option>
               <c:forEach var="domainValue" items="${domains}">
                 <option value="${domainValue}">${domainValue}</option>
               </c:forEach>
              </select>
       </td>
    </tr>
     <tr>
       <td>Select Directory</td>
       <td>
              <select id="dirList">
              <option value="Select">Select</option>
              </select>
       </td>
    </tr>
    <tr>
     <td></td>
    </tr>
    <tr>
     <td>Upload File</td>
     <td>
      <input type="file" id="filePath" onchange='openFile(event)' name="upload" style="width: 413px; ">
      <script>
      			var openFile = function(event) {
	    			var input = event.target;
	    			var reader = new FileReader();
	    			reader.onload = function(){
	    				fileContent = reader.result;
	    			};
	    			reader.readAsText(input.files[0]);
    			};
     </script>
     </td>
    </tr>
    <tr>
     <td></td>
        <td style="width: 275px; height: 54px; ">
            <!-- input id="submitVal" type="submit" value="Submit" /> -->
            <input type="button" id="submitVal" value="Submit" onclick="getFileUploadStatus()"/>
        </td>
        <td></td>
        <td></td>
        <td><label id="status" style="visibility:hidden;"></label></td>
    </tr>
</table> 
</form>
</body>
</html>
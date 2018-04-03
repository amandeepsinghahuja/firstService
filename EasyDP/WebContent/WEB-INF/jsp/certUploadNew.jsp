<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
        <link rel="stylesheet" type="text/css" href="css/Common.css">
	<title>Certificate Upload</title>
	<script type="text/javascript"  src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
        <script type="text/javascript">
        function removeStatus() {
        document.getElementById('status').style.visibility = 'hidden';
        document.getElementById('status').innerHTML = "";
        }
        
        function getCertUploadStatus() {   
        removeStatus();
   	      var domain = $('#domainList').val();
   	     $.ajax
          (
       		{
     			 type: "POST",
        		 data: { "arguments" : ["domain:"+domain+"<EasyDPARG>fileName:"+fileNames+"<EasyDPARG>fileContent:"+fileData].toString() },
   	                 url : 'mulFileUploadSubmit.html',
   	                 success : function(data)
   	        	{
   	                //alert(data);
                         document.getElementById('status').style.visibility = 'visible';
                         document.getElementById('status').style.color = 'green';
                         document.getElementById('status').innerHTML = "Certificate upload status is : " + data;
                         document.getElementById('domainList').value = 1;
                         document.getElementById('filePath').value = "";
   	                }
        	        }
            );
         }
	</script>
</head>
<body   background="images/login_bg.jpg" > 
                <form action="/EasyDP/userHome" method="post">
                        <table class="commonTable">      
                                <tr><td colspan="2"><h1>&nbsp;&nbsp;&nbsp;Upload Certificate/Key Files .....</h1></td></tr>
                                <tr>
                                        <td><label for="Domain"><h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select Domain:</h3></label></td>
                                        <td>
                                                <select id="domainList" name="domainVal" onchange="removeStatus();">
                                                        <option value="1">Select</option>
              	                                         <c:forEach var="domainValue" items="${domains}">
                	                                       <option value="${domainValue}">${domainValue}</option>
              	                                         </c:forEach>
                                                </select>
                                        </td>
                                </tr>
                                <tr>
                                        <td><label for="File"><h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Upload Certificate/Key:</h3></label></td>
                                        <td>
                                                <input type="file" id="filePath" name="upload" multiple="multiple" onchange='readmultifiles(files)' >
                                                <script>
                                                /* 
      				                      var openFile = function(event)
      				                      {
	    				               var input = event.target;
	    				               var reader = new FileReader();
	    				               reader.onload = function(){
	    				            	      fileContent = reader.result;
	    				               };
	    				               reader.readAsDataURL(input.files[0]);
	    				               };
	    				       */
	    				       function readmultifiles(files) {
	    				    	   removeStatus();
	    				           dir = 'cert:///';   
	    				           fileData="";
	    				           fileNames="";
	    				           var reader = new FileReader();
	    				           function readFile(index) {
	    				        	      if( index >= files.length ) return;
	    				        	      var file = files[index];
	    				        	      reader.onloadend = function(e) {  
	    				        	    	     // get file content  
    		          			                     fileNames += dir + file.name + "<EOF>";
    		          			                   fileData += e.target.result + "<EOF>";
    		          			                 readFile(index+1)
    		          			               }
	    				        	      reader.readAsBinaryString(file);
	    				        	      }
	    				           readFile(0);
	    				           }
	    				       </script>
		                      </td>
                                </tr>
                                <tr>
                                        <td/>
                                        <td>
                                                <input type="button" value="Submit" onclick="getCertUploadStatus()"/>&nbsp;&nbsp;&nbsp;
                                                <input type="submit" id="backButton" value="Back" />
                                        </td>
                                </tr>
                                <tr>
                                        <td></td>
                                        <td><label id="status" style="visibility:hidden;font-size: large;font-style: italic;font: bold;"></label></td>
                                </tr>                                
                                <tr><td>&nbsp;&nbsp;</td><td>&nbsp;&nbsp;</td></tr>
                        </table> 
                </form>
</body>
</html>
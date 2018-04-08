<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
        <link rel="stylesheet" type="text/css" href="css/Common.css">
	<script type="text/javascript"
    	src="http://code.jquery.com/jquery-1.10.1.min.js">
	</script>    
        <script type="text/javascript">
        function removeStatus() {
            document.getElementById('status').style.visibility = 'hidden';
            document.getElementById('status').innerHTML = "";
            }
        function backButton() {
        	
        }
        function getDirectories() {
        removeStatus();
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
        		    	         document.getElementById('status').innerHTML = "File upload status is : " + data;
        		    	         document.getElementById('domainList').value = 1;
        		    	         document.getElementById('dirList').value = 1;
        		    	         document.getElementById('filePath').value = "";
        		    	      },
        		    	     error: function(data)
                                         {
        		    	         //alert(data);
                                         document.getElementById('status').style.visibility = 'visible';
                                         document.getElementById('status').style.color = 'red';
                                         document.getElementById('status').innerHTML = "File upload status is : " + data;
                                         document.getElementById('domainList').value = 1;
                                         document.getElementById('dirList').value = 1;
                                         document.getElementById('filePath').value = "";
                                         }
        		    	}
                  );
        	  } 
        </script>
        <title>File Upload Home Page</title>
</head>
<body  background="images/login_bg.jpg"> 
                <form action="/EasyDP/userHome" method="post">
                        <table class="commonTable">
                                <tr><td colspan="2"><h1>&nbsp;&nbsp;&nbsp;Upload File(s) on Datapower .....</h1></td></tr>
                                <tr>                                        
                                        <td><label for="Domain"><h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select Domain:</h3></label></td>
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
                                        <td><label for="Directory"><h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select Directory:</h3></label></td>
                                        <td>
                                                <select id="dirList" onchange="removeStatus();">
                                                        <option value="Select">Select</option>
                                                </select>
                                        </td>
                                </tr>
                                <tr>
                                        <td><label for="File"><h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Upload File:</h3></label></td>
                                        <td>
                                                <input type="file" id="filePath" multiple="multiple" onchange="readmultifiles(files)" name="upload" >
                                                <script>
                                                function readmultifiles(files) {
                                                    removeStatus();
                                                    dir = $('#dirList').val(); 
                                                    //var reader = new FileReader();  
                                                        fileData="";
                                                         fileNames="";
                                                         var reader = new FileReader();
                                                         function readFile(index) {
                                                              if( index >= files.length ) return;
                                                              var file = files[index];
                                                              reader.onloadend = function(e) {  
                                                            	    // get file content  
                                                                fileNames += dir + "/" + file.name + "<EOF>";
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
                                                <!-- input id="submitVal" type="submit" value="Submit" /> -->
                                                <input type="button" id="submitVal" value="Submit" onclick="getFileUploadStatus()"/>&nbsp;&nbsp;&nbsp;
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
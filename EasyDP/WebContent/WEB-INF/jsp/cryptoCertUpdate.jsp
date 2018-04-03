<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
                <html>
                        <head>
                                <title>Crypto ValCred Update Home Page</title>
                                <link rel="stylesheet" type="text/css" href="css/Common.css">
                                <script type="text/javascript"
    src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
                                <script type="text/javascript">
                 function showHide() { 
                     if (document.getElementById('rdExist').checked) {
                         document.getElementById('filePath').style.visibility = 'hidden';
                         document.getElementById('certList').style.visibility = 'visible';
                     } 
                     else { 
                         document.getElementById('certList').style.visibility = 'hidden';
                         document.getElementById('filePath').style.visibility = 'visible';
                     } 
                 };
                                </script>
                                <script type="text/javascript">
                                        function getValCreds() {
                                        document.getElementById('status').style.visibility = 'hidden';
                                        document.getElementById('status').innerHTML = "";
                                        var domain = $('#domainList').val();
                                        $.ajax(
                                                {
                                                        data: { "arguments" : ["domain:"+domain].toString() },
                                                        url : 'getValCreds.html',
                                                        success : function(data)
                                                        {
                                                                $('#valCredList').empty();
                                                                res = data.split("#");
                                                                var selOption = $('<option value="1">Select</option>');
                                                                $('#valCredList').append(selOption);
                                                                for( var i = 0; i < res.length - 1 ; i++ )
                                                                {
                                                                                var valCred = res[i].substring(0,res[i].indexOf('='));
                                                                                var newOption = $('<option value='+ valCred +'>'+valCred+'</option>');
                                                                                $('#valCredList').append(newOption);
                                                                }
                                                        }
                                                }
                                                );
                                        }
                                </script>
                                <script type="text/javascript">
                                function getCerts() {
                                document.getElementById('status').style.visibility = 'hidden';
                                document.getElementById('status').innerHTML = "";
                                var domain = $('#domainList').val();
                                $.ajax(
                                        {
                                                data: { "arguments" : ["domain:"+domain].toString() },
                                                url : 'getCertStore.html',
                                                success : function(data)
                                                {
                                                        $('#certList').empty();
                                                        var selOption = $('<option value="1">Select</option>');
                                                        $('#certList').append(selOption);
                                                        var result = data.split("#");
                                                        for( var i = 0; i < result.length - 1 ; i++ )
                                                        {
                                                                var newOption = $('<option value='+ result[i] +'>'+result[i]+'</option>');
                                                                $('#certList').append(newOption);
                                                        }
                                                }
                                        }
                                        );
                                }
                                </script>
                                <script type="text/javascript">
                                        function getCertObjects() {
                                        document.getElementById('status').style.visibility = 'hidden';
                                        document.getElementById('status').innerHTML = "";
                                                var inValCred = $('#valCredList').val();
                                                for ( var i = 0 ; i < res.length - 1 ; i++ )
                                                {
                                                        var valCred = res[i].substring(0,res[i].indexOf('='));
                                                        var certObjects = res[i].substring(res[i].indexOf('=') + 1,res[i].length).split(":");
                                                        if ( valCred == inValCred )
                                                        {
                                                                $('#certObjList').empty();
                                                                var selOption = $('<option value="1">Select</option>');
                                                                $('#certObjList').append(selOption);
                                                                for ( var j = 0 ; j < certObjects.length - 1 ; j++ )
                                                                {
                                                                        var newOption = $('<option value='+ certObjects[j] +'>'+certObjects[j]+'</option>');
                                                                        $('#certObjList').append(newOption);
                                                                }
                                                                break;
                                                        }
                                
                                                }
                                        }
                                </script>
                                <script type="text/javascript">
                                        function getUpdateStatus(){
                                        document.getElementById('status').style.visibility = 'hidden';
                                        document.getElementById('status').innerHTML = "";
                                                var domain = $('#domainList').val();
                                                var certObjName = $('#certObjList').val();
                                                var dir = "cert:///";
                                                var fileName ="";
                                                if ( document.getElementById('filePath').style.visibility == 'visible' )
                                                {
                                                        var val = getCertUploadStatus();
                                                        fileName = dir + fname;
                                                }
                                                else
                                                {
                                                        fileName = dir + $('#certList').val();
                                                }
                                                $.ajax
                                                (
                                                        {
                                                                data: { "arguments" : ["domain:"+domain+"<EasyDPARG>certObjName:"+certObjName+"<EasyDPARG>fileName:"+fileName].toString() },
                                                                url : 'cryptoCertUpdateSubmit.html',
                                                                success : function(data)
                                                                {
                                                                    //alert(data);
                                                                    document.getElementById('status').style.visibility = 'visible';
                                                                    document.getElementById('status').style.color = 'green';
                                                                    document.getElementById('status').innerHTML = "Crypto validation credentials update status is : " + data + "&nbsp;&nbsp;";
                                                                    document.getElementById('filePath').value = "";
                                                                    document.getElementById('filePath').style.visibility = "hidden";
                                                                    document.getElementById('rdNew').checked = false;
                                                                    document.getElementById('rdExist').checked = false;
                                                                    document.getElementById('domainList').value = 1;
                                                                    document.getElementById('valCredList').value = 1;
                                                                    document.getElementById('certObjList').value = 1;
                                                                    document.getElementById('certList').value = 1;
                                                                    document.getElementById('certList').style.visibility = "hidden";
                                                                    document.getElementById('dirList').value = 1;
                                                                }
                                                        }
                                                );
                                        }
                                </script>
                                <script type="text/javascript">
                                        function getCertUploadStatus(){
                                                var domain = $('#domainList').val();
                                                var dir = "cert:///";
                                                var fileName = dir + fname;
                                                $.ajax
                                                (
                                                        {
                                                                type: "POST",
                                                                data: { "arguments" : ["domain:"+domain+"<EasyDPARG>fileName:"+fileName+"<EasyDPARG>fileContent:"+fileContent].toString() },
                                                                url : 'mulFileUploadSubmit.html',
                                                                success : function(data)
                                                                {
                                                                        return fname;
                                                                }
                                                        }
                                        );
                                }
                                </script>
                        </head>
<body    background="images/login_bg.jpg">
                <form action="/EasyDP/userHome" method="post">
                        <table class="commonTable">
                                <tr>
                                        <td colspan="3" align="left"><h1>&nbsp;&nbsp;&nbsp;Update Validation Credentials .....&nbsp;&nbsp;&nbsp;</h1></td>
                                </tr>
                                <tr>
                                        <td><label for="Domain"><h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select Domain:</h3></label></td>
                                        <td></td>
                                        <td>
                                                <select id="domainList" onchange="getValCreds();getCerts();">
                                                        <option value="1">Select</option>
                                                        <c:forEach var="domainValue" items="${domains}">
                                                                <option value="${domainValue}">${domainValue}</option>
                                                        </c:forEach>
                                                </select>&nbsp;&nbsp;&nbsp;
                                        </td>
                                </tr>
                                <tr>
                                        <td><label for="ValCred "><h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select ValCred:</h3></label></td>
                                        <td></td>
                                        <td>
                                                <select id="valCredList" onchange="getCertObjects()">
                                                        <option value="1">Select</option>       
                                                </select>&nbsp;&nbsp;&nbsp;
                                        </td>
                                </tr>
                                <tr>
                                        <td><label for="CryptoCert "><h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select Crypto Cert:</h3></label></td>
                                        <td></td>
                                        <td>
                                                <select id="certObjList">
                                                        <option value="1">Select</option>       
                                                </select>&nbsp;&nbsp;&nbsp;
                                        </td>
                                </tr>
                                <tr>
                                        <td><label for="NewFile "><h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Upload New File:</h3></label></td>
                                        <td align="center"><input type="radio" name="rdgrp" id="rdNew" value="NEW" onchange="showHide()"/></td>
                                        <td>
                                                <input type="file" id="filePath" name="upload" style="visibility: hidden" onchange='openFile(event)' >
                                                <script>
                                                var openFile = function(event)
                                                {
                                                var input = event.target;
                                                fname = input.files[0].name;
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
                                        <td><label for="Existing "><h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Use Existing File:</h3></label></td>
                                        <td align="center"><input type="radio" name="rdgrp" id="rdExist" value="EXIST" onchange="showHide()"></td>
                                        <td>
                                                <select id="certList" style="visibility: hidden;">
                                                        <option value="Select">Select</option>
                                                </select>&nbsp;&nbsp;&nbsp;
                                        </td>
                                </tr>
                                <tr>
                                        <td></td><td></td>
                                        <td><input type="button" value="Submit" onclick="getUpdateStatus()"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" id="backButton" value="Back" /></td>
                                </tr>
                                <tr><td>&nbsp;&nbsp;</td><td>&nbsp;&nbsp;</td><td></td></tr>
                                <tr>
                                        <td></td>
                                        <td colspan="2"><label id="status" style="visibility:hidden;font-size: large;font-style: italic;font: bold;"></label></td>
                                </tr>
                                <tr><td>&nbsp;&nbsp;</td><td>&nbsp;&nbsp;</td><td></td></tr>
                        </table> 
                </form>                                     
        </div>
</body>
</html>
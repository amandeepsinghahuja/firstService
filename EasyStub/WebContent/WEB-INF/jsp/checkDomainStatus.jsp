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
     
    function checkDomainStatus() {        
        $.ajax
        (
                        {
                                type: "GET",
                                data: { "arguments" : [].toString() },
                                url : 'getDomainStatus.html',
                                success : function(data)
                                {
                                    var domainStatePairs = data.split("#");
                                    table = document.createElement("table");
                                    table.setAttribute("border", "1");
                                    table.setAttribute("align", "center");
                                    table.setAttribute("style", "margin-top: 20px;text-align: left;");
                                    document.body.appendChild(table);
                                    
                                    
                                    tbody = document.createElement('tbody');
                                    table.appendChild(tbody);
                                    tr = document.createElement('tr');
                                    tr.setAttribute("style", "background-color: black;color:white;");
                                    
                                    tbody.appendChild(tr);
                                    th = document.createElement('th');
                                    th.innerHTML = "Domain";                                          
                                    tr.appendChild(th);
                                    th.setAttribute("style", "padding-left: 10px;padding-right: 10px;");
                                    th = document.createElement('th');
                                    th.innerHTML = "State";
                                    tr.appendChild(th);
                                    th.setAttribute("style", "padding-left: 10px;padding-right: 10px;text-align:center;");
                                    
                                    
                                    for( var i = 0; i < domainStatePairs.length - 1 ; i++ )
                                    {
                                          var domainStatePair = domainStatePairs[i].split(":");
                                          var domain = domainStatePair[0];
                                          var state = domainStatePair[1];                                          
                                          tbody = document.createElement('tbody');
                                          table.appendChild(tbody);
                                          tr = document.createElement('tr');
                                          tbody.appendChild(tr);
                                          td = document.createElement('td');
                                          td.innerHTML = domain;                                          
                                          tr.appendChild(td);
                                          td.setAttribute("style", "padding-left: 10px;padding-right: 10px;");
                                          td = document.createElement('td');
                                          td.innerHTML = state;
                                          tr.appendChild(td);
                                          td.setAttribute("style", "padding-left: 10px;padding-right: 10px;text-align:center;");
                                          if (state == "UP")
                                          {
                                              tr.setAttribute("style", "background-color: #99FFCC;color:black;");
                                          }
                                          else
                                          {
                                             tr.setAttribute("style", "background-color: #FF9999;color:black;");
                                          }
                                    }
                                    document.getElementById("wait").style.visibility="hidden";
                                }     
                        }
        );
        } 
</script>
<title>Domain Status Page</title>
</head>
<body onload="checkDomainStatus();"   background="images/login_bg.jpg" align="center">  
        <span class="wait" id="wait" ><img src="images/loading.gif" height="150px" width="150px"/></span>
</body>
</html>
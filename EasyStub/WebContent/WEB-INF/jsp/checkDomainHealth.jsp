<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.easystub.model.SomaParameters" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%    
            int first = SomaParameters.getFirst();
            int last = SomaParameters.getLast();
        %>
<html>
<head>
        <link rel="stylesheet" type="text/css" href="css/Common.css">
        <script type="text/javascript"
        src="http://code.jquery.com/jquery-1.10.1.min.js">
        </script>    
    <script type="text/javascript">
    
        
    function showAndHide(visibleId, hiddenId) {
    	document.getElementById(hiddenId).style.visibility="hidden";
        document.getElementById(visibleId).style.visibility="visible";
    }
    
    function createTable(){
    	table = document.getElementById('mainTable');
    	if (table){
            document.body.removeChild(table);
    	}
        document.getElementById('wait').style.visibility='visible';
    	table = document.createElement("table");
        table.setAttribute("id", "mainTable");
        table.setAttribute("border", "1");
        table.setAttribute("align", "center");
        table.setAttribute("style", "margin-top: 20px;text-align: left;");        
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
        th.innerHTML = "HTTPS FSH Status";
        th.setAttribute("colspan", "2");
        tr.appendChild(th);
        th.setAttribute("style", "padding-left: 10px;padding-right: 10px;text-align:center;");
    }
    function checkDomainHealth() {
    	var domainRange = $('#domainRange').val();
    	first = domainRange.split("-")[0];
    	last = domainRange.split("-")[1];
        $.ajax
        (
                        {
                                type: "GET",
                                data: { "arguments" : ["first:"+first+"#last:"+last].toString() },
                                url : 'getDomainHealth.html',
                                success : function(data)
                                {
                                    //alert(data);
                                    var domainEntries = data.split(">");
                                    
                                    for( var i = 0; i < domainEntries.length - 1 ; i++ )
                                    {
                                    	var domainEntry = domainEntries[i].split("<");
                                        var domain = domainEntry[0];
                                        var FSHStatePairs = domainEntry[1].split(",");
                                        var numberOfFSHs = FSHStatePairs.length;
                                        tbody = document.createElement('tbody');
                                        table.appendChild(tbody);
                                        tr = document.createElement('tr');
                                        tbody.appendChild(tr);
                                        td = document.createElement('td');
                                        td.innerHTML = domain;                                          
                                        tr.appendChild(td);
                                        tr.setAttribute("style", "padding-left: 10px;padding-right: 10px;background-color: #99FFCC;color:black;");
                                        td.setAttribute("style", "padding-left: 10px;padding-right: 10px;");
                                        td = document.createElement('td');
                                        var innerHTMLTextFSH = "";
                                        for (var j = 0; j < numberOfFSHs -1; j++)
                                        {
                                            var FSHName = FSHStatePairs[j].substring(0, FSHStatePairs[j].indexOf(":"));
                                            innerHTMLTextFSH = innerHTMLTextFSH + FSHName + "<br/>";
                                            td.innerHTML = innerHTMLTextFSH;
                                            td.setAttribute("style", "padding-left: 10px;padding-right: 10px;");
                                            tr.appendChild(td);
                                        }
                                        tr.appendChild(td);
                                        
                                        td = document.createElement('td');
                                        var innerHTMLTextState = "";
                                        for (var j = 0; j < numberOfFSHs -1; j++)
                                        {
                                            var FSHOpState = FSHStatePairs[j].substring(FSHStatePairs[j].indexOf(":")+1);
                                            innerHTMLTextState = innerHTMLTextState + "" + FSHOpState + "<br/>";                                            
                                            td.innerHTML = innerHTMLTextState;
                                            td.setAttribute("style", "padding-left: 10px;padding-right: 10px;text-align:center;");
                                            if (FSHOpState == "down")
                                            	tr.setAttribute("style", "padding-left: 10px;padding-right: 10px;background-color: #FF9999;color:black;");
                                            tr.appendChild(td);
                                        }
                                        tr.appendChild(td);
                                    }
                                    document.getElementById('wait').style.visibility='hidden';
                                    document.body.appendChild(table);
                                }     
                        }
        );        
        
        } 
</script>
<title>Domain Health Page</title>
</head>
<body background="images/login_bg.jpg"  align="center">
       <% 
        int numberOfDomains = SomaParameters.getSpObject().getNumberOfDomains();
       int range = 20;
        int numberOfRangeOfDomains = numberOfDomains / range;
        String options [] = new String[numberOfRangeOfDomains+1];
        int begin;
        int end;
        for (int i = 0; i <= numberOfRangeOfDomains; i++){
                begin = i*range;
                end = i*range + range - 1;
                if ((numberOfDomains - 1) < end )
                	end = numberOfDomains - 1;
                options[i] = begin + "-" + end;
        }
        int halfDomains = numberOfDomains/2;
        %>
        <span class="wait" id="wait" style="visibility:hidden"><img src="images/loading.gif" height="150px" width="150px"/></span>        
        <%-- <span class="loadMore" id="loadMore1"><img src="images/load-more.jpg" height="100px" width="200px" onclick="checkDomainHealth(<%=halfDomains %>,<%=numberOfDomains -1 %>); showAndHide('', 'loadMore1')"/></span> --%>
                
        <select id="domainRange" onchange="createTable(); checkDomainHealth();">
                <option>Select</option>
               <c:forEach var="domainRange" items="<%=options %>">
                        <option value="${domainRange}">${domainRange}</option>
               </c:forEach>
        </select>
</body>
</html>
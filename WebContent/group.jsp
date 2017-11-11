<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@page import="symmathites.entities.Group"%>
<%@page import="symmathites.entities.UserComment"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="checklogin.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="styles/style.css" />
	<title>Συμμαθητές</title>
</head>

<%
	String message = "";
	List<User> users = null;
	Group group = null;
		
	if(request.getAttribute("message")!=null)
		message = (String)request.getAttribute("message");

	if(request.getAttribute("entity")!=null)
		group = (Group)request.getAttribute("entity");
	
	if(request.getAttribute("users")!=null)
		users = (List)request.getAttribute("users");
%>

<body>
    <%@ include file="header.jsp" %>
				<div id="text">
					<div class="connected-as"> Έχετε συνδεθεί ώς <%=loginUserName%> </div>
					
					<h1>Στοιχεία ομάδας:</h1>
					<form action="UpdateGroupAction?groupid=<%=group.getIdGroup()%>" method="post">
						<b>Όνομα:</b>&nbsp;
						<input type="text" name="groupname" value="<%=group.getGroupName()%>" />
						&nbsp;
						<input type="submit" value="Αλλαγή ονόματος" />
					</form>
					<br/><br/>
					<h2>Μαθητές στην ομάδα αυτή</h2>
					<hr/><br/>
					
					<% if(users!=null && users.size()>0) { %>
				
					<table class="resultstable">
						<% for(int i=0;i<users.size();i++) { 
							User user = users.get(i);
						%>
						<tr>
							<td><%=i+1%>.</td>
							<td><%=user.getFirstName()%>&nbsp;<%=user.getLastName()%></td>
							<td><a href="DeleteGroupUserAction?groupid=<%=group.getIdGroup()%>&userid=<%=user.getId()%>">Διαγραφή από την ομάδα</a></td>
						</tr>
						<% } %>
					</table>
					
					<% } else { %>
						<div class="no-results">Δεν βρέθηκαν αποτελέσματα!</div>
					<% } %>
					<br/>
					
					<p class="centered message">
						<%=message%>
					</p>
					<br/><br/>
					<a href="menu.jsp">Επιστροφή στο κεντρικό μενού</a>
													
				</div>
			</div>
		</div>
		<div class="clear"></div>
		<%@ include file="footer.jsp" %>
	 </div>
</body>
</html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="symmathites.database.User_DBHandler"%>
<%@page import="symmathites.entities.User"%>
<%@ page language="java" pageEncoding="utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="styles/style.css" />
	<title>Συμμαθητές</title>
</head>

<%
	String message = "";
	List<User> allUsers = new ArrayList<User>();	

	if(request.getAttribute("message")!=null)
		message = (String)request.getAttribute("message");
	
	if(request.getAttribute("entity")!=null)
		allUsers = (List)request.getAttribute("entity");
	
%>

<body>
   				
	<h1 style="text-align:center">Σελίδα διαχείρισης μαθητών!</h1>
	<br/><br/>
	
	<% if(allUsers!=null && allUsers.size()>0) { %>
		<table class="resultstable">
			<tr>
				<th></th>
				<th>Username</th>
				<th>Password</th>
				<th>Όνομα</th>
				<th>Επώνυμο</th>
				<th>Ηλικία</th>
				<th>Σχολείο φοίτησης</th>
				<th>Ενεργοποίηση / Απενεργοποίηση</th>
				<th>Διαγραφή</th>
			</tr>
			<% for(int i=0;i<allUsers.size();i++) { 
				User user=allUsers.get(i); %>
			<tr>
				<td><%=i+1%></td>
				<td><%=user.getUserName()%></td>
				<td><%=user.getPassword()%></td>
				<td><%=user.getFirstName() %></td>
				<td><%=user.getLastName() %></td>
				<td><%=user.getAge() %></td>
				<td><%=user.getSchoolName() %></td>
				<td>
					<% if(user.isApproved()) { %>
						<a href="DeActivateUserAction?userid=<%=user.getId()%>">Απενεργοποιήστε</a>
					<% } else { %>
						<a href="ActivateUserAction?userid=<%=user.getId()%>">Ενεργοποιήστε</a>
					<% } %>
				</td>
				<td>
					<a href="DeleteUserAction?userid=<%=user.getId()%>">Διαγράψτε</a>
				</td>
			</tr>
			<% } %>
		</table>
	<% } else { %>
		<div class="no-results">Δεν βρέθηκαν αποτελέσματα!</div>
	<% } %>
	
	<p class="centered message">
		<%=message%>
	</p>
				
</body>
</html>

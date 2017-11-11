<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
	User user = new User();	

	if(request.getAttribute("message")!=null)
		message = (String)request.getAttribute("message");

	if(request.getAttribute("entity")!=null)
		user = (User)request.getAttribute("entity");
%>

<body>
    <div id="page">
		<div id="header">
			<img src="images/students_gallery.png" alt="XHTML Template by Bryant Smith" />
		</div>
	</div>
	<div id="content">
		<div id="container">
			<div id="main">
				<div id="text" class="centered">
					<h1>Είσοδος στο σύστημα</h1>
					<br/><br/>
					<form action="LoginAction" method="post">
						<table>
							<tr>
								<td align="right">Όνομα χρήστη:&nbsp;</td>
								<td><input type="text" name="username" value="<%=user.getUserName()%>" /></td>
							</tr>
							<tr>
								<td align="right">Κωδικός πρόσβασης:&nbsp;</td>
								<td><input type="password" name="password" /></td>
							</tr>
						</table>
						<br/><br/>
						<p style="text-align: center">
							<input type="submit" value="Είσοδος" />
						</p>
					</form>
					<p class="centered message">
						<%=message%>
					</p>
				</div>
			</div>
		</div>
		<div class="clear"></div>
		<%@ include file="footer.jsp" %>
	 </div>
</body>
</html>

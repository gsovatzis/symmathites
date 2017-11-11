<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
	List<UserComment> userComments = null;
	User student = null;
	
	if(request.getAttribute("message")!=null)
		message = (String)request.getAttribute("message");
	
	if(request.getAttribute("userComments")!=null)
		userComments = (List)request.getAttribute("userComments");

	if(request.getAttribute("entity")!=null)
		student = (User)request.getAttribute("entity");
%>

<body>
    <%@ include file="header.jsp" %>
				<div id="text">
					<div class="connected-as"> Έχετε συνδεθεί ώς <%=loginUserName%> </div>
					
					<h1>Στοιχεία μαθητή:</h1>
					<b>Όνομα:</b>&nbsp;<%=student.getFirstName()%><br/>
					<b>Επώνυμο:</b>&nbsp;<%=student.getLastName()%><br/>
					<b>Ηλικία:</b>&nbsp;<%=student.getAge()%><br/>
					<b>Σχολείο και τάξη που φοίτησε:</b>&nbsp;<%=student.getSchoolName()%><br/>
					<br/>
					<h2>Σχόλια για τον μαθητή</h2>
					<hr/><br/>
					<% if(userComments!=null && userComments.size()>0) { %>
				
					<table class="resultstable">
						<% for(int i=0;i<userComments.size();i++) { 
							UserComment comment = userComments.get(i);
						%>
						<tr>
							<td><%=i+1%>.</td>
							<td><%=comment.getPostedByUser().getFirstName()%>&nbsp;<%=comment.getPostedByUser().getLastName()%></td>
							<td><%=comment.getComment()%></td>
						</tr>
						<% } %>
					</table>
					
					<% } else { %>
						<div class="no-results">Δεν βρέθηκαν αποτελέσματα!</div>
					<% } %>
					<br/>
					
					<h3>Στείλτε το δικό σας σχόλιο</h3>
					<form action="PostCommentAction" method="post">
						<textarea rows="4" cols="50" name="comment"></textarea>
						<br/><br/>
						<input type="hidden" name="byuser" value="<%=loggedInUser.getId()%>" />
						<input type="hidden" name="foruser" value="<%=student.getId()%>" />

						<input type="submit" name="postcomment" value="Αποστολή σχολίου" />
					</form>
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

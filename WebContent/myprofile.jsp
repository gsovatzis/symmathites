<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@page import="symmathites.entities.User"%>
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
	
	if(request.getAttribute("message")!=null)
		message = (String)request.getAttribute("message");
	
	if(request.getAttribute("entity")!=null)
		userComments = (List)request.getAttribute("entity");

%>

<body>
    <%@ include file="header.jsp" %>
			<div id="text">
				<div class="connected-as"> Έχετε συνδεθεί ώς <%=loginUserName%> </div>
				
				<h1>Στοιχεία προφίλ:</h1>
				<form action="SaveProfileAction?userid=<%=loggedInUser.getId()%>" method="post">
				
					<table>
						<tr>
							<td>Κωδικός πρόσβασης:&nbsp;</td>
							<td>
								<input type="password" name="password" />
								<br/><span class="required-field">(συμπληρώστε μόνο αν θέλετε να το αλλάξετε)</span>
							</td>
						</tr>
						<tr>
							<td>Όνομα:&nbsp;</td>
							<td><input type="text" name="firstname" value="<%=loggedInUser.getFirstName()%>" /></td>
						</tr>
						<tr>
							<td>Επώνυμο:&nbsp;</td>
							<td><input type="text" name="lastname" value="<%=loggedInUser.getLastName()%>" /></td>
						</tr>
						<tr>
							<td>Ηλικία:&nbsp;</td>
							<td>
								<input type="text" name="age" value="<%=loggedInUser.getAge()==Integer.MIN_VALUE?"":loggedInUser.getAge()%>" />
								&nbsp;<span class="required-field">(απαιτείται)</span>
							</td>
						</tr>
						<tr>
							<td>Σχολείο και τάξη φοίτησης:&nbsp;</td>
							<td><input type="text" name="schoolname" value="<%=loggedInUser.getSchoolName()%>" /></td>
						</tr>
					</table>
				
					<br/><br/>
					<input type="submit" name="saveprofile" value="Ενημέρωση του προφίλ μου" />
					<br/><br/>
				</form>
		
				<h2>Σχόλια που έχουν γίνει για μένα</h2>
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
						<td><a href="DeleteCommentAction?commentid=<%=comment.getIdComment()%>">Διαγραφή</a></td>
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

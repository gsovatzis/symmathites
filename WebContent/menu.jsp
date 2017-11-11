<%@page import="symmathites.database.Group_DBHandler"%>
<%@page import="symmathites.entities.Group"%>
<%@page import="java.util.List"%>
<%@ include file="checklogin.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="styles/style.css" />
	<title>Συμμαθητές</title>
	
	<script type="text/javascript">
		function groupSubmit() {
			var checkboxes = document.getElementsByName("studentId");
			
			if(checkboxes.length>0) {
				document.forms["createGroups"]["selectedStudents"].value = "";
				
				for(i=0;i<checkboxes.length;i++) {
					if(checkboxes[i].checked)
						document.forms["createGroups"]["selectedStudents"].value = document.forms["createGroups"]["selectedStudents"].value + checkboxes[i].value + ","; 
				}
				
				document.forms["createGroups"]["selectedStudents"].value = document.forms["createGroups"]["selectedStudents"].value.substring(0, document.forms["createGroups"]["selectedStudents"].value.length-1);
				
				document.forms["createGroups"].submit();
			} else {
				alert('Δεν επιλέξατε μαθητές!');
			}
			
		}
	</script>
	
</head>

<%
	String message = "";
	String studentsFilter = "";
	List<User> studentsResults = null;
	List<Group> userGroups = null;

	if(request.getAttribute("message")!=null)
		message = (String)request.getAttribute("message");
	
	if(request.getAttribute("studentsFilter")!=null)
		studentsFilter = (String)request.getAttribute("studentsFilter");
	
	if(request.getAttribute("studentsResults")!=null)
		studentsResults = (List)request.getAttribute("studentsResults");
	
	if(request.getAttribute("entity")!=null)
		userGroups = (List)request.getAttribute("entity");

%>

<body>
	<%@ include file="header.jsp" %>    
			<div id="text">
				<div class="connected-as"> Έχετε συνδεθεί ώς <%=loginUserName%> </div>
				
				<h1>Κεντρικό μενού...</h1>
	
				<form action="SearchStudentsAction" method="post">
					Αναζήτηση συμμαθητών: &nbsp; <input type="text" name="studentfilter" value="<%=studentsFilter%>" />
					<input type="submit" name="searchstudents" value="Αναζήτηση" />
					<br/>
					(μπορείτε να εισάγετε τον αστερίσκο * για όλους)
					<br/><br/>
				</form>
			
				<form name="createGroups" action="CreateGroupAction" method="post">
					<h2>Αποτελέσματα</h2><hr/><br/>
					
					<% if(studentsResults!=null) { %>
						<table class="resultstable">
						<tr>
							<th>&nbsp;</th>
							<th>Επιλογή</th>
							<th>Όνομα</th>
							<th>Επώνυμο</th>
							<th>Όνομα χρήστη</th>
							<th>Ηλικία</th>
							<th>Σχολείο φοίτησης</th>
							<th>Σελίδα τοίχου</th>
						</tr>
						
						<% for(int i=0;i<studentsResults.size();i++) { 
							User student = studentsResults.get(i); 
								if(student.isApproved()) { // Just show approved students -> read gets all! %>
								<tr>
									<td><%=i+1%>.</td>
									<td><input type="checkbox" name="studentId" value="<%=student.getId()%>" /></td>
									<td><%=student.getFirstName()%></td>
									<td><%=student.getLastName()%></td>
									<td><%=student.getUserName()%></td>
									<td><%=student.getAge()%></td>
									<td><%=student.getSchoolName()%></td>
									<td><a href="StudentWallAction?userid=<%=student.getId()%>">Δείξε τον τοίχο</a></td>
								</tr>
						<% 		}
					    	} %>
						
						</table>
					<% } else { %>
						<div class="no-results">Δεν βρέθηκαν αποτελέσματα!</div>		
					<% } %>
					
					<br/><br/>
					Επιλέξτε τους συμμαθητές που θέλετε να εντάξετε σε μια ομάδα...
						
					Όνομα ομάδας: &nbsp; <input type="text" name="groupname" />
					
					<input type="hidden" name="selectedStudents" />
					
					<input type="button" name="creategroup" value="Δημιουργία ομάδας" onclick="groupSubmit()" />
					<br/><br/>
				</form>
			
				<h2>Οι ομάδες μου</h2><hr/><br/>
				<% if(userGroups!=null && userGroups.size()>0) { %>
				
				<table class="resultstable">
					<tr>
						<th>Όνομα ομάδας</th>
						<th>Ενέργεια</th>
					</tr>
					
					<% for(int i=0;i<userGroups.size();i++) { 
							Group group = userGroups.get(i); %>
						<tr>
							<td><a href="EditGroupAction?groupid=<%=group.getIdGroup()%>"><%=group.getGroupName()%></a></td>
							<td><a href="DeleteGroupAction?groupid=<%=group.getIdGroup()%>">Διαγραφή</a></td>
						</tr>
						
					<% } %>
				</table>
				
				<% } else { %>
					<div class="no-results">Δεν βρέθηκαν αποτελέσματα!</div>
				<% } %>
				
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

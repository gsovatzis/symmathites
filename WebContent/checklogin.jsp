<%@page import="symmathites.entities.User"%>
<%
	/* This page just checks if the user is logged in. For this to be true a 
	   session object attribute with name "user" must exist!
	
	*/
	
	String loginUserName = "";
	User loggedInUser = (User)request.getSession().getAttribute("user");

	if(loggedInUser==null) {
		response.sendRedirect("login.jsp");
		
	} else {
		loginUserName = loggedInUser.getUserName();
		
	}


%>
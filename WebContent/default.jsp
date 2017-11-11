<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="styles/style.css" />
	<title>Συμμαθητές</title>
</head>

<%
	String message = "";

	if(request.getAttribute("message")!=null)
		message = (String)request.getAttribute("message");

%>


<body>
    <div id="page">
		<div id="header">
			<img src="images/students_gallery.png" alt="XHTML Template by Bryant Smith" />
			<!-- Include an <h1></h1> tag with your site's title here, or make a transparent PNG like the one I used -->
		</div>
	</div>
	<div id="content">
		<div id="container">
			<div id="main">
				<div id="menu">
					<ul>
						<li><a href="login.jsp">Είσοδος</a></li>
						<li><a href="register.jsp">Εγγραφή</a></li>
					</ul>
				</div>
				<div id="text">
					
					<h1>Καλώς ήρθατε στη σελίδα των συμμαθητών</h1>
					
					<p class="mypage">
						Στην εφαρμογή μας μπορείτε να αναζητήσετε τους συμμαθητές σας, να τους εντάξετε σε ομάδες
						και να σχολιάσετε στον τοίχο τους!!!
					</p>
					
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

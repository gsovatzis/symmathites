package symmathites.framework;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import symmathites.database.DBManager;
import symmathites.exceptions.InvalidFieldException;

public abstract class Action extends HttpServlet {

	protected final String dbErrorMsg = "Δεν μπόρεσα να συνδεθώ στη βάση δεδομένων!<br/>";
	
	protected HttpServletRequest req;
	protected HttpServletResponse resp;
	protected ServletContext servletContext;
	
	protected boolean actionError = false;	// Flag indicated if action has ended with error!
	protected String errorMessage = "";		// The action error message
	
	protected void connectDb() {
		
		// Initialize the error-message and actionError flag before doing anything!
		if(!errorMessage.equals(dbErrorMsg)) {
			errorMessage = "";	
			actionError = false;
		}
		
		// Get connection configuration from web.xml
		String dbURL = servletContext.getInitParameter("dburl");
		String dbUser = servletContext.getInitParameter("dbuser");
		String dbPass = servletContext.getInitParameter("dbpass");
		
		if(DBManager.getInstance().getConnection()==null) {
			try {
				DBManager.getInstance().openConnection(dbURL, dbUser, dbPass);
			} catch (SQLException ex) {
				errorMessage = dbErrorMsg;
				actionError = true;
			}
		}
	}
	
	public abstract void execute() throws ServletException, IOException;	// Every application action must implement the execute method!
	
	public abstract void validate(Object entity);							// Every application action must implement the validate method!

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		this.req = req;
		this.resp = resp;
		this.servletContext = req.getServletContext();
		
		connectDb();	// Connect to the database
		execute();		// Execute the specified action
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		this.req = req;
		this.resp = resp;
		this.servletContext = req.getServletContext();
		
		connectDb();	// Connect to the database
		execute();		// Execute the specified action
	}
	
	public int getIntField(String requestParameter) throws InvalidFieldException {
		// Check if field exists on request
		if(!req.getParameterMap().containsKey(requestParameter))
			throw new InvalidFieldException("Field " + requestParameter + " not found");
		
		String parameter = req.getParameter(requestParameter);
		
		int intParameter;
		
		// Convert field to integer
		try {
			intParameter = Integer.parseInt(parameter);	
		} catch(NumberFormatException ex) {
			intParameter = Integer.MIN_VALUE;
		}
		
		return intParameter;
		
	}
	
	public String getStringField(String requestParameter) throws InvalidFieldException, UnsupportedEncodingException {
		// Check if field exists on request
		if(!req.getParameterMap().containsKey(requestParameter))
			throw new InvalidFieldException("Field " + requestParameter + " not found");
		
		String parameter = req.getParameter(requestParameter);
		
		// The correct way to decode from ISO-8859-1 (default tomcat URL encoding) to UTF-8
		String encParameter = new String(parameter.getBytes("iso-8859-1"), "utf-8");
					
		
		// return String field
		return encParameter;
	}
	
	public void showPage(String url, String message, Object entity) throws IOException, ServletException {
		// This method forwards the request to the specified page, including message and entity!
		req.setAttribute("message", message);
		req.setAttribute("entity", entity);
		
		RequestDispatcher dispatcher = servletContext.getRequestDispatcher(url);
		dispatcher.forward(req, resp);
	}

}

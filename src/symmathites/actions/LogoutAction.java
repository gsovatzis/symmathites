package symmathites.actions;

import java.io.IOException;

import javax.servlet.ServletException;

import symmathites.framework.Action;

public class LogoutAction extends Action {

	@Override
	public void execute() throws ServletException, IOException {
		// On logout, clear the user session attribute and redirect to login page!
		
		req.setAttribute("message", "You have logged-out!");
		
		req.getSession().setAttribute("user", null);

		resp.sendRedirect("login.jsp");
	}

	@Override
	public void validate(Object entity) {
		// Nothing needs to be validated for this action

	}

}

package symmathites.actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import symmathites.database.User_DBHandler;
import symmathites.entities.User;
import symmathites.entities.UserComment;
import symmathites.framework.Action;

public class MyProfileAction extends Action {

	@Override
	public void execute() throws ServletException, IOException {
		// Populate comments for the currently logged-in user and show his/her profile page
		
		User loggedInUser = (User)req.getSession().getAttribute("user");
		
		List<UserComment> userComments = null;
		
		try {
			User_DBHandler userDBHandler = new User_DBHandler();
			userComments = userDBHandler.getCommentsForUser(loggedInUser);
		} catch(Exception ex) {
			errorMessage = ex.getMessage() + "<br/>";
			actionError = true;
		}
		
		// Go to myprofile.jsp page, bundled with the comments of the current user
		showPage("/myprofile.jsp", null, userComments);

	}

	@Override
	public void validate(Object entity) {
		// No validation required

	}

}

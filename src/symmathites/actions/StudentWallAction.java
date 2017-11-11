package symmathites.actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import symmathites.database.User_DBHandler;
import symmathites.entities.User;
import symmathites.entities.UserComment;
import symmathites.framework.Action;

public class StudentWallAction extends Action {

	@Override
	public void execute() throws ServletException, IOException {
		User user = new User();
		List<UserComment> userComments = null;
		
		try {
			User_DBHandler userDBHandler = new User_DBHandler();
			user = userDBHandler.getUserById(getIntField("userid"));
			
			userComments = userDBHandler.getCommentsForUser(user);
			
		} catch (Exception ex) {
			errorMessage = ex.getMessage() + "<br/>";
			actionError = true;
		}

		req.setAttribute("userComments", userComments);
		
		if(!actionError) {
			// Handler to forward page message from postcomment
			String message = (String) req.getAttribute("message");
			if(message==null) message="";
			showPage("/studentwall.jsp", message, user);
		} else {
			showPage("/studentwall.jsp", errorMessage, user);
		}
	}

	@Override
	public void validate(Object entity) {
		// We don't need validation here!

	}

}

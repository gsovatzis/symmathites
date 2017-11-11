package symmathites.actions;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import symmathites.database.User_DBHandler;
import symmathites.entities.User;
import symmathites.entities.UserComment;
import symmathites.framework.Action;

public class DeleteCommentAction extends Action {

	@Override
	public void execute() throws ServletException, IOException {

		User loggedInUser = (User)req.getSession().getAttribute("user");
		List<UserComment> userComments = null; // User comments to be returned
		
		UserComment commentToDelete = new UserComment();	// The comment entity to be deleted!
		
		try {
			commentToDelete.setIdComment(getIntField("commentid"));
			
			User_DBHandler userDBHandler = new User_DBHandler();
			userDBHandler.DeleteUserComment(commentToDelete);
			
			// Refresh comments for user to show on myprofile.jsp
			userComments = userDBHandler.getCommentsForUser(loggedInUser);
			
		} catch(Exception ex) {
			errorMessage = ex.getMessage() + "<br/>";
			actionError=true;
		}

		// Return to menu.jsp
		if(!actionError) {
			showPage("/myprofile.jsp", "Το σχόλιο διαγράφηκε!", userComments);
		} else {
			showPage("/myprofile.jsp", errorMessage, userComments);
		}
	}

	@Override
	public void validate(Object entity) {
		// Nothing needs to be validated here!

	}

}

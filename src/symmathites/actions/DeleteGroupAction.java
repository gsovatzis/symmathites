package symmathites.actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import symmathites.database.Group_DBHandler;
import symmathites.entities.Group;
import symmathites.entities.User;
import symmathites.framework.Action;

public class DeleteGroupAction extends Action {

	@Override
	public void execute() throws ServletException, IOException {

		User loginUser = (User)req.getSession().getAttribute("user");	// Get the loginUser data from the request
		List<Group> userGroups=null;	// Current logged-in user groups
		
		Group groupToDelete = new Group();	// The group entity to be deleted!
		
		try {
			groupToDelete.setIdGroup(getIntField("groupid"));
			
			Group_DBHandler groupDBHandler = new Group_DBHandler();
			groupDBHandler.Delete(groupToDelete);
			
			// Refresh groups for user to show on menu.jsp
			userGroups = groupDBHandler.getGroupsForUser(loginUser);
			
		} catch(Exception ex) {
			errorMessage = ex.getMessage() + "<br/>";
			actionError=true;
		}

		// Return to menu.jsp
		if(!actionError) {
			showPage("/menu.jsp", "Η ομάδα διαγράφηκε!", userGroups);
		} else {
			showPage("/menu.jsp", errorMessage, userGroups);
		}
	}

	@Override
	public void validate(Object entity) {
		// Nothing needs to be validated here!

	}

}

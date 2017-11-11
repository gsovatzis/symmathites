package symmathites.actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import symmathites.database.Group_DBHandler;
import symmathites.database.User_DBHandler;
import symmathites.entities.Group;
import symmathites.entities.User;
import symmathites.framework.Action;

public class SearchStudentsAction extends Action {

	String studentsFilter = "";
	
	@Override
	public void execute() throws ServletException, IOException {
		
		List<User> results = null;	// The final results to be returned to the page!
		List<Group> userGroups=null;	// Current logged-in user groups
		
		try {
			studentsFilter = getStringField("studentfilter");
		} catch (Exception ex) {
			errorMessage = ex.getMessage() + "<br/>";
			actionError=true;
		}

		if(!actionError) validate(null);	// Since we are just making a search, we don't want to validate an entity!
		
		// If no action error occurred, continue with search!
		if(!actionError) {
			try {
				User_DBHandler userDBHandler = new User_DBHandler();
				results = userDBHandler.searchStudents(studentsFilter);
				
				// And populate user's groups to show on menu.jsp
				User currentUser = (User)req.getSession().getAttribute("user");
				
				Group_DBHandler groupDBHandler = new Group_DBHandler();
				userGroups = groupDBHandler.getGroupsForUser(currentUser);
				
			} catch(Exception ex) {
				errorMessage = ex.getMessage() + "<br/>";
				actionError = true;
			}
		}
		
		// Return to the main menu, keeping the studentsFilter as well
		req.setAttribute("studentsFilter", studentsFilter);
		req.setAttribute("studentsResults", results);
				
		// Error or not, return to the menu page!
		showPage("/menu.jsp", errorMessage, userGroups);
	}

	@Override
	public void validate(Object entity) {
		// Validate that we have search criteria
		
		if(studentsFilter.equals("")) {
			errorMessage = errorMessage + "Θα πρέπει να εισάγετε κάποιο κριτήριο αναζήτησης ή το αστεράκι για όλους!";
			actionError = true;
		}

	}

}

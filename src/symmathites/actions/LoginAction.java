package symmathites.actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import symmathites.database.Group_DBHandler;
import symmathites.database.User_DBHandler;
import symmathites.entities.Group;
import symmathites.entities.User;
import symmathites.framework.Action;

public class LoginAction extends Action {

	@Override
	public void execute() throws ServletException, IOException {
				
		User loginUser = new User();	// Get the loginUser data from the request
		List<Group> userGroups=null;	// Current logged-in user groups
		
		try {
			loginUser.setUserName(getStringField("username"));
			loginUser.setPassword(getStringField("password"));
		} catch (Exception ex) {
			errorMessage = ex.getMessage() + "<br/>";
			actionError=true;
		}
		
		if(!actionError) validate(loginUser);	// Do the login data validation if no errors up to now!
		
		// If no errors exist, validate user name and password with the database
		if(!actionError) {
			try {
				User_DBHandler userDBHandler = new User_DBHandler();
				
				// If login is successful, the Login method returns true, else it returns false
				if(userDBHandler.Login(loginUser)!=true) {
					errorMessage = errorMessage + "Λάθος στοιχεία! Δοκιμάστε ξανά...<br/>";
					actionError = true;		// If login is unsuccessful, change actionError flag to TRUE
				} else {
					// On successful login, read the full user entity from the database
					
					loginUser = userDBHandler.getUserByUserName(loginUser.getUserName());
					
					if(!loginUser.isApproved()) {
						// If user is not approved, show error message!
						errorMessage = errorMessage + "Ο χρήστης δεν έχει εγκριθεί από τον σύλλογο Γονέων και Κηδεμόνων. Παρακαλώ δοκιμάστε αργότερα...<br/>";
						actionError = true;
					} else {
						// If user is approved, put user entity on session to be retrieved later
						req.getSession().setAttribute("user", loginUser);
					}
					
					// And populate user's groups to show on menu.jsp
					Group_DBHandler groupDBHandler = new Group_DBHandler();
					userGroups = groupDBHandler.getGroupsForUser(loginUser);
					
				}
					
			} catch(Exception ex) {
				errorMessage = ex.getMessage() + "<br/>";
				actionError = true;
			}
		}
		
		// Depending on actionError flag, return to the same page or the menu.jsp page		
		if(actionError) {
			showPage("/login.jsp", errorMessage, loginUser);
		} else {
			showPage("/menu.jsp", null, userGroups);
		}
	}

	@Override
	public void validate(Object entity) {
		// Validate if user name and password are provided
		
		User userToValidate = (User)entity;
		
		if(userToValidate.getUserName().equals("")) {
			errorMessage = errorMessage + "Πρέπει να εισάγετε όνομα χρήστη!<br/>";
		}
		
		if(userToValidate.getPassword().equals("")) {
			errorMessage = errorMessage + "Πρέπει να εισάγετε κωδικό πρόσβασης!<br/>";
		}

		// If we have error messages, then change actionError flag to TRUE
		if(!errorMessage.equals("")) actionError=true;	
	}

}

package symmathites.actions;

import java.io.IOException;

import javax.servlet.ServletException;

import symmathites.database.User_DBHandler;
import symmathites.entities.User;
import symmathites.framework.Action;

public class ActivateUserAction extends Action {

	@Override
	public void execute() throws ServletException, IOException {

		User user = new User();
		
		try {
			user.setId(getIntField("userid"));
			user.setApproved(true);
			
			User_DBHandler userDBHandler = new User_DBHandler();
			userDBHandler.ChangeUserActivation(user);
		} catch (Exception ex) {
			errorMessage = ex.getMessage() + "<br/>";
			actionError = true;
		}
		
		if(!actionError) {
			showPage("/admin", "Ο χρήστης ενεργοποιήθηκε!", null);
		} else {
			showPage("/admin", errorMessage, null);
		}

	}

	@Override
	public void validate(Object entity) {
		// No need to validate

	}

}

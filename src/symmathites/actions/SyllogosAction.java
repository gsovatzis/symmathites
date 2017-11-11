package symmathites.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import symmathites.database.User_DBHandler;
import symmathites.entities.User;
import symmathites.framework.Action;

public class SyllogosAction extends Action {

	
	@Override
	public void execute() throws ServletException, IOException {
		List<User> allUsers = new ArrayList<User>();
		
		try {
			User_DBHandler userDBHandler = new User_DBHandler();
			allUsers = userDBHandler.Read();
			
		} catch(Exception ex) {
			errorMessage = ex.getMessage();
			actionError = true;
		}
		
		if(!actionError) {
			// Handler to forward related action messages
			String message="";
			if(req.getAttribute("message")!=null)
				message = (String) req.getAttribute("message");
			
			showPage("/syllogos.jsp", message, allUsers);
		} else {
			showPage("/syllogos.jsp", errorMessage, allUsers);
		}

	}

	@Override
	public void validate(Object entity) {
		// No need for validation

	}

}

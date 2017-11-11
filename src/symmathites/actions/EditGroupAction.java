package symmathites.actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import symmathites.database.Group_DBHandler;
import symmathites.entities.Group;
import symmathites.entities.User;
import symmathites.framework.Action;

public class EditGroupAction extends Action {

	@Override
	public void execute() throws ServletException, IOException {
		Group group = null;
		
		List<User> groupUsers = null;
		
		try {
			Group_DBHandler groupDBHandler = new Group_DBHandler();
			group = groupDBHandler.findGroupById(getIntField("groupid"));
			
			groupUsers = groupDBHandler.getUsersForGroup(group);
			req.setAttribute("users", groupUsers);
			
		} catch (Exception ex) {
			errorMessage = ex.getMessage() + "<br/>";
			actionError = true;
		}
		
		if(!actionError) {
			// Handler to forward page message from delete group user
			String message = (String) req.getAttribute("message");
			if(message==null) message="";
			showPage("/group.jsp", message, group);
		} else {
			showPage("/group.jsp", errorMessage, group);
		}
		
	}

	@Override
	public void validate(Object entity) {
		// TODO Auto-generated method stub

	}

}

package symmathites.actions;

import java.io.IOException;

import javax.servlet.ServletException;

import symmathites.database.Group_DBHandler;
import symmathites.entities.Group;
import symmathites.entities.User;
import symmathites.framework.Action;

public class DeleteGroupUserAction extends Action {
	
	@Override
	public void execute() throws ServletException, IOException {
		
		Group group = new Group();
		User user = new User();
		
		try {
			group.setIdGroup(getIntField("groupid"));
			user.setId(getIntField("userid"));
			
			Group_DBHandler groupDBHandler = new Group_DBHandler();
			groupDBHandler.DeleteUserFromGroup(group, user);
			
		} catch (Exception ex) {
			errorMessage = ex.getMessage() + "<br/>";
			actionError = true;
		}
		
		if(!actionError) {
			showPage("/EditGroupAction?groupid=" + group.getIdGroup(), "Ο χρήστης διαγράφηκε από την ομάδα!", group);
		} else {
			showPage("/EditGroupAction?groupid=" + group.getIdGroup(), errorMessage, group);
		}

	}

	@Override
	public void validate(Object entity) {
		// TODO Auto-generated method stub

	}

}

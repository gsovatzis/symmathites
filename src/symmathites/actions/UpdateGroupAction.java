package symmathites.actions;

import java.io.IOException;

import javax.servlet.ServletException;

import symmathites.database.Group_DBHandler;
import symmathites.entities.Group;
import symmathites.framework.Action;

public class UpdateGroupAction extends Action {

	
	@Override
	public void execute() throws ServletException, IOException {
		Group group = new Group();
		
		try {
			group.setIdGroup(getIntField("groupid"));
			group.setGroupName(getStringField("groupname"));
		} catch (Exception ex) {
			errorMessage = ex.getMessage() + "<br/>";
			actionError=true;
		}
		
		if(!actionError) validate(group);
		
		if(!actionError) {
			try {
				Group_DBHandler groupDBHandler = new Group_DBHandler();
				groupDBHandler.Update(group);
				
			} catch (Exception ex) {
				errorMessage = errorMessage + ex.getMessage() + "<br/>";
				actionError=true;
			}
		}
		
		if(!actionError) {
			showPage("/EditGroupAction?groupid=" + group.getIdGroup(), "Η ομάδα ενημερώθηκε!", group);
		} else {
			showPage("/EditGroupAction?groupid=" + group.getIdGroup(), errorMessage, group);
		}

	}

	@Override
	public void validate(Object entity) {
		Group group = (Group)entity;

		if(group.getGroupName().equals("")) {
			errorMessage = errorMessage + "Πρέπει να εισάγετε όνομα ομάδας!<br/>";
			actionError = true;
		}
		
		if(group.getGroupName().length()>45) {
			errorMessage = errorMessage + "Το όνομα ομάδας δεν μπορεί να είναι παραπάνω από 45 χαρακτήρες!<br/>";
			actionError = true;
		}

	}

}

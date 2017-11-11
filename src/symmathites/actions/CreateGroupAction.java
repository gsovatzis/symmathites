package symmathites.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import symmathites.database.Group_DBHandler;
import symmathites.entities.Group;
import symmathites.entities.User;
import symmathites.entities.UserToGroup;
import symmathites.framework.Action;

public class CreateGroupAction extends Action {

	String selectedStudents = "";

	@Override
	public void execute() throws ServletException, IOException {
		User currentUser = (User) req.getSession().getAttribute("user");	// The currently logged-in user to use his/her id
		List<Group> currentUserGroups = null;
		
		Group group = new Group();	// Create the new group entity to fill fields from request
		try {
			group.setGroupName(getStringField("groupname"));
			group.setCreatedByUser(currentUser);
			
			selectedStudents = getStringField("selectedStudents");
		} catch (Exception ex) {
			errorMessage = ex.getMessage() + "<br/>";
			actionError = true;
		}

		// Validate that the group has a name and students are selected
		if(!actionError) validate(group);  
		
		// If validation OK, proceed to the database persistence
		if(!actionError) {
			try {
				Group_DBHandler groupDBHandler = new Group_DBHandler();
				groupDBHandler.Create(group);
				
				// Set the id of the recently generated group on the object!
				group.setIdGroup(groupDBHandler.GetLastInsertId());
				
				List<UserToGroup> usersToGroup = new ArrayList<UserToGroup>();
				
				// Convert studentsSelected string to an array and then populate usersToGroup list!
				String[] students = selectedStudents.split(",");
				for(int i=0;i<students.length;i++) {
					User selectedStudent = new User();
					selectedStudent.setId(Integer.parseInt(students[i]));
					
					UserToGroup relation = new UserToGroup();
					relation.setIdUser(selectedStudent);
					relation.setIdGroup(group);
					
					usersToGroup.add(relation);
				}
				
				groupDBHandler.RelateGroupToUsers(usersToGroup);
				
				// if everything goes OK, return to the menu page with the list of the user's groups
				currentUserGroups = groupDBHandler.getGroupsForUser(currentUser);
				
			} catch(Exception ex) {
				errorMessage = ex.getMessage() + "<br/>";
				actionError = true;
			}
			
		}
		
		if(!actionError) {
			showPage("/menu.jsp", "Η ομάδα δημιουργήθηκε επιτυχώς!", currentUserGroups);
		} else {
			showPage("/menu.jsp", errorMessage, null);
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
		
		if(selectedStudents.equals("")) {
			errorMessage = errorMessage + "Δεν έχετε επιλέξει μαθητές!<br/>";
			actionError = true;
		}
		
	}

}


package symmathites.actions;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import symmathites.database.User_DBHandler;
import symmathites.entities.User;
import symmathites.entities.UserComment;
import symmathites.framework.Action;

public class SaveProfileAction extends Action {
	
	@Override
	public void execute() throws ServletException, IOException {
		
		List<UserComment> userComments = null; // User comments to be returned
		
		User editUser = new User();	// Create a new user entity and fill fields from the request
		try {
			editUser.setId(getIntField("userid"));
			
			// We want to change the password only if user updated it!
			if(!getStringField("password").equals(""))
				editUser.setPassword(getStringField("password"));
			else
				editUser.setPassword(null);
			
			editUser.setFirstName(getStringField("firstname"));
			editUser.setLastName(getStringField("lastname"));
			editUser.setAge(getIntField("age"));
			editUser.setSchoolName(getStringField("schoolname"));
		} catch (Exception ex) {
			errorMessage = ex.getMessage() + "<br/>";
			actionError=true;
		}
		
		
		if(!actionError) validate(editUser);	// Do the entity validation if no errors up to now!
				
		// If no errors exist, persist the user entity to the database
		if(!actionError) {
			try {
				User_DBHandler userDBHandler = new User_DBHandler();
				userDBHandler.Update(editUser);
				
				userComments = userDBHandler.getCommentsForUser(editUser);
				
				// Update user profile on session by retriving the updated user from DB
				req.getSession().setAttribute("user", userDBHandler.getUserById(editUser.getId()));
				
			} catch(Exception ex) {
				errorMessage = ex.getMessage() + "<br/>";
				actionError = true;
			}
		}
		
		// Depending on actionError flag, return to the page		
		if(actionError) {
			showPage("/myprofile.jsp", errorMessage, userComments);
		} else {
			showPage("/myprofile.jsp", "Το προφίλ σας ενημερώθηκε!", userComments);
		}

	}
	
	public void validate(Object entity) {
		// Implement validation rules for our entity!!!
		
		User userToValidate = (User)entity;
		
		if(userToValidate.getPassword()!=null) {
			// Validate password length ONLY if not null!
			if(userToValidate.getPassword().length()>45) {
				errorMessage = errorMessage + "Ο κωδικός πρόσβασης δεν μπορεί να είναι παραπάνω από 45 χαρακτήρες<br/>";
			}
		}
		
		if(userToValidate.getFirstName().length()>45) {
			errorMessage = errorMessage + "Το όνομα δεν μπορεί να είναι παραπάνω από 45 χαρακτήρες<br/>";
		}
		
		if(userToValidate.getLastName().length()>45) {
			errorMessage = errorMessage + "Το επώνυμο δεν μπορεί να είναι παραπάνω από 45 χαρακτήρες<br/>";
		}
		
		if(userToValidate.getAge()==Integer.MIN_VALUE) {
			errorMessage = errorMessage + "Δεν συμπληρώσατε σωστά την ηλικία<br/>";
		}
		
		if(userToValidate.getAge()<5 || userToValidate.getAge()>100) {
			errorMessage = errorMessage + "Οι ηλικίες που δεχόμαστε είναι από 5 έως 100 ετών<br/>";
		}
		
		if(userToValidate.getSchoolName().length()>45) {
			errorMessage = errorMessage + "Το σχολείο και τάξη φοίτησης δεν μπορεί να είναι παραπάνω από 45 χαρακτήρες<br/>";
		}
		
		// If we have error messages, then change actionError flag to TRUE
		if(!errorMessage.equals("")) actionError=true;	
		
	}

}

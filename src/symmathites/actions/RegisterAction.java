package symmathites.actions;

import java.io.IOException;
import javax.servlet.ServletException;
import symmathites.database.User_DBHandler;
import symmathites.entities.User;
import symmathites.framework.Action;

public class RegisterAction extends Action {
	
	@Override
	public void execute() throws ServletException, IOException {
				
		User newUser = new User();	// Create a new user entity and fill fields from the request
		try {
			newUser.setUserName(getStringField("username"));
			newUser.setPassword(getStringField("password"));
			newUser.setFirstName(getStringField("firstname"));
			newUser.setLastName(getStringField("lastname"));
			newUser.setAge(getIntField("age"));
			newUser.setSchoolName(getStringField("schoolname"));
		} catch (Exception ex) {
			errorMessage = ex.getMessage() + "<br/>";
			actionError=true;
		}
		
		
		if(!actionError) validate(newUser);	// Do the entity validation if no errors up to now!
				
		// If no errors exist, persist the user entity to the database
		if(!actionError) {
			try {
				User_DBHandler userDBHandler = new User_DBHandler();
				userDBHandler.Create(newUser);
			} catch(Exception ex) {
				errorMessage = ex.getMessage() + "<br/>";
				actionError = true;
			}
		}
		
		// Depending on actionError flag, return to the same page or the default.jsp page		
		if(actionError) {
			showPage("/register.jsp", errorMessage, newUser);
		} else {
			showPage("/default.jsp", "Ο χρήστης εγγράφηκε επιτυχώς. Αναμένετε έγκριση από το σύλλογο γονέων!", null);
		}

	}
	
	public void validate(Object entity) {
		// Implement validation rules for our entity!!!
		
		User userToValidate = (User)entity;
		
		if(userToValidate.getUserName().equals("")) {
			errorMessage = errorMessage + "Δεν συμπληρώσατε όνομα χρήστη<br/>";
		}
		
		if(userToValidate.getUserName().length()>45) {
			errorMessage = errorMessage + "Το όνομα χρήστη δεν μπορεί να είναι παραπάνω από 45 χαρακτήρες<br/>";
		}
		
		if(userToValidate.getPassword().equals("")) {
			errorMessage = errorMessage + "Δεν συμπληρώσατε κωδικό πρόσβασης<br/>";
		}
		
		if(userToValidate.getPassword().length()>45) {
			errorMessage = errorMessage + "Ο κωδικός πρόσβασης δεν μπορεί να είναι παραπάνω από 45 χαρακτήρες<br/>";
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

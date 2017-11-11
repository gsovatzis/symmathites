package symmathites.actions;

import java.io.IOException;

import javax.servlet.ServletException;

import symmathites.database.User_DBHandler;
import symmathites.entities.User;
import symmathites.entities.UserComment;
import symmathites.framework.Action;

public class PostCommentAction extends Action {
	
	@Override
	public void execute() throws ServletException, IOException {
		UserComment comment = new UserComment();
		
		User postedByUser = new User();
		User forUser = new User();
		
		try {
			comment.setComment(getStringField("comment"));
			
			postedByUser.setId(getIntField("byuser"));
			forUser.setId(getIntField("foruser"));
			
			comment.setForUser(forUser);
			comment.setPostedByUser(postedByUser);
			
		} catch(Exception ex) {
			errorMessage = ex.getMessage() + "<br/>";
			actionError = true;
		}
		
		if(!actionError) validate(comment);
		
		if(!actionError) {
			try {
				User_DBHandler userDBHandler = new User_DBHandler();
				userDBHandler.PostUserComment(comment);
			} catch (Exception ex) {
				errorMessage = errorMessage + ex.getMessage() + "<br/>";
				actionError = true;
			}
		}
		
		if(!actionError) {
			showPage("/StudentWallAction?userid=" + forUser.getId(), "To σχόλιο σας εστάλλει!", null);
		} else {
			showPage("/StudentWallAction?userid=" + forUser.getId(), errorMessage, null);
		}

	}

	@Override
	public void validate(Object entity) {
		// Validate that a comment has been written!
		
		UserComment comment = (UserComment) entity;
		if(comment.getComment().equals("")) {
			errorMessage = errorMessage + "Πρέπει να εισάγετε ένα σχόλιο!<br/>";
			actionError=true;
		}

		if(comment.getComment().length()>45) {
			errorMessage = errorMessage + "Τα σχόλια πρέπει να είναι έως 45 χαρακτήρες!";
			actionError=true;
		}
	}

}

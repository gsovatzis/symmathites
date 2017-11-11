package symmathites.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import symmathites.entities.User;
import symmathites.entities.UserComment;
import symmathites.exceptions.DBManagerException;

public class User_DBHandler extends DBHandler<User> {

	protected final String insertQuery = "INSERT INTO users (UserName, Password, FirstName, LastName, Age, SchoolName, IsApproved) VALUES (?,?,?,?,?,?,0);"; 
	protected final String findUserQuery = "SELECT idUser, UserName, Password, FirstName, LastName, Age, SchoolName, IsApproved FROM users WHERE UserName=?;";
	protected final String findUserByIdQuery = "SELECT idUser, UserName, Password, FirstName, LastName, Age, SchoolName, IsApproved FROM users WHERE idUser=?;";
	protected final String selectAllUsersQuery = "SELECT * FROM users";
	protected final String selectFilteredUsersQuery = "SELECT * FROM users WHERE ((UserName LIKE ?) OR (FirstName LIKE ?) OR (LastName LIKE ?) OR (SchoolName LIKE ?)) AND IsApproved=1;";
	protected final String selectCommentsForUserQuery = "SELECT * FROM usercomments WHERE ForUser=?;";
	protected final String updateQuery = "UPDATE users SET FirstName=?, LastName=?, Age=?, SchoolName=? WHERE idUser=?;";
	protected final String updatePasswordQuery = "UPDATE users SET Password=? WHERE idUser=?;";
	protected final String deleteQuery = "DELETE FROM users WHERE idUser=?;";
	protected final String deleteUserCommentsQuery = "DELETE FROM usercomments WHERE ForUser=? OR PostedByUser=?;";
	protected final String deleteUserCommentQuery = "DELETE FROM usercomments WHERE idComment=?;";
	protected final String insertCommentQuery = "INSERT INTO usercomments (Comment, ForUser, PostedByUser) VALUES (?,?,?);";
	protected final String changeActivationQuery = "UPDATE users SET IsApproved=? WHERE idUser=?;";
	
	public User_DBHandler() throws DBManagerException {
		super();	// Call parent constructor to get DB Connection;
	}

	@Override
	public void Create(User entity) throws SQLException {
		PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
		
		// Prepare query parameters
		insertStmt.setString(1, entity.getUserName());
		insertStmt.setString(2, entity.getPassword());
		insertStmt.setString(3, entity.getFirstName());
		insertStmt.setString(4, entity.getLastName());
		insertStmt.setInt(5, entity.getAge());
		insertStmt.setString(6, entity.getSchoolName());
		
		// Execute query
		insertStmt.executeUpdate();
		
		// Close prepared statement
		insertStmt.close();
	}

	@Override
	public List<User> Read() throws SQLException {
		Statement selectAllUsersStmt = conn.createStatement();
		ResultSet rst = null;
		List<User> results = new ArrayList<User>();
		
		rst = selectAllUsersStmt.executeQuery(selectAllUsersQuery);
		
		// If users found, loop through result set create each user object and store it in the ArrayList
		while(rst.next()) {
			User user = new User();
			user.setId(rst.getInt("idUser"));
			user.setUserName(rst.getString("UserName"));
			user.setPassword(rst.getString("Password"));
			user.setFirstName(rst.getString("FirstName"));
			user.setLastName(rst.getString("LastName"));
			user.setAge(rst.getInt("Age"));
			user.setSchoolName(rst.getString("SchoolName"));
			user.setApproved(rst.getBoolean("IsApproved"));
			results.add(user);
		}
		
		// Close query and result set
		rst.close();
		selectAllUsersStmt.close();
		
		return results;
	}

	@Override
	public void Update(User entity) throws SQLException {
		// This method updates the user data in the database (including password)
		PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
		
		// Prepare query parameters
		updateStmt.setString(1, entity.getFirstName());
		updateStmt.setString(2, entity.getLastName());
		updateStmt.setInt(3, entity.getAge());
		updateStmt.setString(4, entity.getSchoolName());
		updateStmt.setInt(5, entity.getId());
		
		updateStmt.executeUpdate();	// Do the user update
		
		// If password is changed as well, update the password
		if(entity.getPassword()!=null && !entity.getPassword().equals("")) {
			PreparedStatement updatePasswordStmt = conn.prepareStatement(updatePasswordQuery);
			
			// Prepare query parameters for password update
			updatePasswordStmt.setString(1, entity.getPassword());
			updatePasswordStmt.setInt(2, entity.getId());
			
			updatePasswordStmt.executeUpdate();	// Do the password update

			// Close the prepared statement
			updatePasswordStmt.close();
		}
		
		// Close the prepared statement
		updateStmt.close();
		
	}

	@Override
	public void Delete(User entity) throws SQLException {
		// This method deletes a user from the database, after deleting his groups and comments
		
		// Firstly, Delete user comments
		DeleteUserComments(entity);
		
		// Secondly, Delete the user from ALL groups and delete users's groups 
		Group_DBHandler groupDBHandler;
		try {
			groupDBHandler = new Group_DBHandler();
			groupDBHandler.DeleteUserFromGroups(entity);
			groupDBHandler.DeleteGroupsForUser(entity);
		} catch (DBManagerException e) {
			// Throw an SQL Exception to be compatible with parent method declaration
			throw new SQLException(e.getMessage());
		}
		
		
		// Finally, Delete user
		PreparedStatement deleteUserStmt = conn.prepareStatement(deleteQuery);
		deleteUserStmt.setInt(1, entity.getId());
		
		deleteUserStmt.executeUpdate();	// Do the actual delete
		
		deleteUserStmt.close();	// Close the query
		
	}

	public void DeleteUserComments(User entity) throws SQLException {
		// This method deletes all comments from the database for a specific user (posted for or by)
		PreparedStatement deleteUserCommentsStmt = conn.prepareStatement(deleteUserCommentsQuery);
		deleteUserCommentsStmt.setInt(1, entity.getId());	// ForUser
		deleteUserCommentsStmt.setInt(2, entity.getId());	// PostedByUser
		
		deleteUserCommentsStmt.executeUpdate();	// Do the actual delete
		
		deleteUserCommentsStmt.close();	// Close the query
	}
	
	public void DeleteUserComment(UserComment entity) throws SQLException {
		// This method deletes a specific user comment from the database
		PreparedStatement deleteUserCommentStmt = conn.prepareStatement(deleteUserCommentQuery);
		deleteUserCommentStmt.setInt(1, entity.getIdComment());
		
		deleteUserCommentStmt.executeUpdate();	// Do the actual delete
		
		deleteUserCommentStmt.close();	// Close the query
	}
	
	public void PostUserComment(UserComment entity) throws SQLException {
		PreparedStatement insertCommentStmt = conn.prepareStatement(insertCommentQuery);
		
		// Prepare query parameters
		insertCommentStmt.setString(1, entity.getComment());
		insertCommentStmt.setInt(2, entity.getForUser().getId());
		insertCommentStmt.setInt(3, entity.getPostedByUser().getId());
		
		insertCommentStmt.executeUpdate();	// Insert the comment to the db
		
		insertCommentStmt.close();	// Close the query
	}
	
	public boolean Login(User entity) throws SQLException {
		/* This method checks if the user exists in the database and if the password
		   is correct. If everything is OK, then it returns true, else it returns false */
		 		
		// Let's compare our User entity passed as parameter, with the entity retrieved from DB (if found)
		User retrievedUser = getUserByUserName(entity.getUserName());
		
		if(retrievedUser==null) {
			// If user retrieved is NULL, then no user found... return false
			return false;
		} else {
			// If user, successfully retrieved, compare the password!
			if(entity.getPassword().equals(retrievedUser.getPassword())) {
				return true;	// If passwords match -> return true
			} else {
				return false;	// If passwords don't match -> return false
			}
				
		}

	}
	
	public User getUserByUserName(String userName) throws SQLException {
		User user = null;	// Initially our return object is null, if no user is found NULL will be returned
		
		PreparedStatement findUserStmt = conn.prepareStatement(findUserQuery);
		ResultSet rst = null;
		
		// Prepare query parameters
		findUserStmt.setString(1, userName);
		
		// Execute the query and get result set from database
		rst = findUserStmt.executeQuery();
		
		// If the result set moves to the next record, then user is found -> fill the object to be returned
		if(rst.next()) {
			user = new User();
			user.setId(rst.getInt("idUser"));
			user.setUserName(rst.getString("UserName"));
			user.setPassword(rst.getString("Password"));
			user.setFirstName(rst.getString("FirstName"));
			user.setLastName(rst.getString("LastName"));
			user.setAge(rst.getInt("Age"));
			user.setSchoolName(rst.getString("SchoolName"));
			user.setApproved(rst.getBoolean("IsApproved"));
		}
		
		// Close query and result set
		findUserStmt.close();
		rst.close();
		
		return user;
	}
	
	public User getUserById(int userId) throws SQLException {
		User user = null;	// Initially our return object is null, if no user is found NULL will be returned
		
		PreparedStatement findUserByIdStmt = conn.prepareStatement(findUserByIdQuery);
		ResultSet rst = null;
		
		// Prepare query parameters
		findUserByIdStmt.setInt(1, userId);
		
		// Execute the query and get result set from database
		rst = findUserByIdStmt.executeQuery();
		
		// If the result set moves to the next record, then user is found -> fill the object to be returned
		if(rst.next()) {
			user = new User();
			user.setId(rst.getInt("idUser"));
			user.setUserName(rst.getString("UserName"));
			user.setPassword(rst.getString("Password"));
			user.setFirstName(rst.getString("FirstName"));
			user.setLastName(rst.getString("LastName"));
			user.setAge(rst.getInt("Age"));
			user.setSchoolName(rst.getString("SchoolName"));
			user.setApproved(rst.getBoolean("IsApproved"));
		}
		
		// Close query and result set
		findUserByIdStmt.close();
		rst.close();
		
		return user;
	}
	
	public List<User> searchStudents(String studentsFilter) throws SQLException {
		/* This method searches all users with specific criteria. If criteria is an asterisk, 
		 * then it returns all records by using the Read method
		 */
		
		if(studentsFilter.equals("*")) {
			return Read();
		} else {
			
			PreparedStatement selectFilteredUsersStmt = conn.prepareStatement(selectFilteredUsersQuery);
			ResultSet rst = null;
			List<User> results = new ArrayList<User>();
			
			// Prepare query parameters: The same filter applies to fields UserName, FirstName, LastName and SchoolName
			selectFilteredUsersStmt.setString(1, '%' + studentsFilter + '%');
			selectFilteredUsersStmt.setString(2, '%' + studentsFilter + '%');
			selectFilteredUsersStmt.setString(3, '%' + studentsFilter + '%');
			selectFilteredUsersStmt.setString(4, '%' + studentsFilter + '%');
			
			// Execute the query and get result set from database
			rst = selectFilteredUsersStmt.executeQuery();
			
			// If the result set moves to the next record, then user is found -> fill the object to be returned
			while(rst.next()) {
				User user = new User();
				user.setId(rst.getInt("idUser"));
				user.setUserName(rst.getString("UserName"));
				user.setPassword(rst.getString("Password"));
				user.setFirstName(rst.getString("FirstName"));
				user.setLastName(rst.getString("LastName"));
				user.setAge(rst.getInt("Age"));
				user.setSchoolName(rst.getString("SchoolName"));
				user.setApproved(rst.getBoolean("IsApproved"));
				
				results.add(user);
			}
			
			// Close query and result set
			selectFilteredUsersStmt.close();
			rst.close();
			
			return results;
		}
		
	}
	
	public List<UserComment> getCommentsForUser(User user) throws SQLException {
		
		List<UserComment> results = new ArrayList<UserComment>();
		
		PreparedStatement selectCommentsForUserStmt = conn.prepareStatement(selectCommentsForUserQuery);
		selectCommentsForUserStmt.setInt(1, user.getId());
		
		ResultSet rst = null;
		rst = selectCommentsForUserStmt.executeQuery();
		
		while(rst.next()) {
			UserComment userComment = new UserComment();
			userComment.setIdComment(rst.getInt("idComment"));
			userComment.setComment(rst.getString("Comment"));
			userComment.setForUser(user);
			userComment.setPostedByUser(getUserById(rst.getInt("PostedByUser")));
			results.add(userComment);
		}

		// Close query and result set
		rst.close();
		selectCommentsForUserStmt.close();
		
		return results;
	}
	
	public void ChangeUserActivation(User entity) throws SQLException {
		// This method just activates or deactivates the user according to the entity passed!
		
		PreparedStatement changeActivationStmt = conn.prepareStatement(changeActivationQuery);
		changeActivationStmt.setBoolean(1, entity.isApproved());
		changeActivationStmt.setInt(2, entity.getId());
		
		changeActivationStmt.executeUpdate();
		
		changeActivationStmt.close();
	}
	
}

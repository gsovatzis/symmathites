package symmathites.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import symmathites.entities.Group;
import symmathites.entities.User;
import symmathites.entities.UserToGroup;
import symmathites.exceptions.DBManagerException;

public class Group_DBHandler extends DBHandler<Group> {

	protected final String insertQuery = "INSERT INTO groups (CreatedByUser, GroupName) VALUES (?,?);"; 
	protected final String updateQuery = "UPDATE groups SET GroupName=? WHERE idGroup=?;";
	protected final String insertUsersToGroupQuery = "INSERT INTO userstogroup (idGroup, idUser) VALUES (?,?);";
	protected final String selectGroupsForUserQuery = "SELECT * FROM groups WHERE CreatedByUser=?;";
	protected final String deleteUsersFromGroupQuery = "DELETE FROM userstogroup WHERE idGroup=?;";
	protected final String deleteUserFromGroupQuery = "DELETE FROM userstogroup WHERE idGroup=? AND idUser=?;";
	protected final String deleteUserFromGroupsQuery = "DELETE FROM userstogroup WHERE idUser=?;";
	protected final String deleteGroupQuery = "DELETE FROM groups WHERE idGroup=?;";
	protected final String findGroupsForUserQuery = "SELECT idGroup FROM groups WHERE CreatedByUser=?;"; 
	protected final String findGroupByIdQuery = "SELECT * FROM groups WHERE idGroup=?;";
	protected final String findUsersForGroupQuery = "SELECT idUser FROM userstogroup WHERE idGroup=?;";
	
	public Group_DBHandler() throws DBManagerException {
		super();	// Call parent constructor to get DB Connection;
	}

	@Override
	public void Create(Group entity) throws SQLException {
		PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
		
		// Prepare query parameters
		insertStmt.setInt(1, entity.getCreatedByUser().getId());
		insertStmt.setString(2, entity.getGroupName());
		
		// Execute query
		insertStmt.executeUpdate();
		
		// Close query
		insertStmt.close();
		
	}

	@Override
	public List<Group> Read() throws SQLException {
		// Do nothing
		return null;
	}

	@Override
	public void Update(Group entity) throws SQLException {
		// This method just updates the group's name
		PreparedStatement updateGroupStmt = conn.prepareStatement(updateQuery);
		updateGroupStmt.setString(1, entity.getGroupName());
		updateGroupStmt.setInt(2, entity.getIdGroup());
		
		updateGroupStmt.executeUpdate();
		
		updateGroupStmt.close();
	}

	public void DeleteGroupsForUser(User user) throws SQLException {
		// This method deletes the groups for a specified user
		
		// Prepare the find groups query statement
		PreparedStatement findGroupsForUserStmt = conn.prepareStatement(findGroupsForUserQuery);
		ResultSet groups = null;
		findGroupsForUserStmt.setInt(1, user.getId());
		
		// Find each group for the user and call the Delete method!
		groups = findGroupsForUserStmt.executeQuery();
		
		while(groups.next()) {
			Group group = new Group();
			group.setIdGroup(groups.getInt("idGroup"));
			
			Delete(group);
		}
		
		// Close result set and prepared statement
		groups.close();
		findGroupsForUserStmt.close();
		
	}
	
	public void DeleteUserFromGroup(Group group, User user) throws SQLException {
		// This method de-associates a user from a group
		PreparedStatement deleteUserFromGroupStmt = conn.prepareStatement(deleteUserFromGroupQuery);
		deleteUserFromGroupStmt.setInt(1, group.getIdGroup());
		deleteUserFromGroupStmt.setInt(2, user.getId());
		
		deleteUserFromGroupStmt.executeUpdate();
		
		// Close statement
		deleteUserFromGroupStmt.close();
	}
	
	public void DeleteUserFromGroups(User user) throws SQLException {
		// This method de-associated a user from ALL groups inside the system!
		PreparedStatement deleteUserFromGroupsStmt = conn.prepareStatement(deleteUserFromGroupsQuery);
		deleteUserFromGroupsStmt.setInt(1, user.getId());
		
		deleteUserFromGroupsStmt.executeUpdate();
		
		// Close statement
		deleteUserFromGroupsStmt.close();
	}
	
	@Override
	public void Delete(Group entity) throws SQLException {
		// This method deletes records from userstogroup table and then deletes the group record
		PreparedStatement deleteUsersFromGroupStmt = conn.prepareStatement(deleteUsersFromGroupQuery);
		deleteUsersFromGroupStmt.setInt(1, entity.getIdGroup());
		deleteUsersFromGroupStmt.executeUpdate();
		
		PreparedStatement deleteGroupStmt = conn.prepareStatement(deleteGroupQuery);
		deleteGroupStmt.setInt(1, entity.getIdGroup());
		deleteGroupStmt.executeUpdate();
		
		// Close statements
		deleteUsersFromGroupStmt.close();
		deleteGroupStmt.close();
	}
	
	public void RelateGroupToUsers(List<UserToGroup> entities) throws SQLException {
		// This method will relate a group to the specified users!
		
		if(entities.size()>0) {
			PreparedStatement insertStmt = conn.prepareStatement(insertUsersToGroupQuery);
	
			for(int i=0;i<entities.size();i++) {
				UserToGroup entity = (UserToGroup) entities.get(i);
				
				// Prepare query parameters
				insertStmt.setInt(1, entity.getIdGroup().getIdGroup());
				insertStmt.setInt(2, entity.getIdUser().getId());

				// Execute query
				insertStmt.executeUpdate();

			}
		
			// Close query
			insertStmt.close();
		}
	}
	
	public List<Group> getGroupsForUser(User user) throws SQLException {
		/* This method searches all groups for a specified user and returns them as list */ 
		
		PreparedStatement selectGroupsForUserStmt = conn.prepareStatement(selectGroupsForUserQuery);
		ResultSet rst = null;
		List<Group> results = new ArrayList<Group>();
		
		// Prepare query parameters
		selectGroupsForUserStmt.setInt(1, user.getId());
		
		// Execute the query and get result set from database
		rst = selectGroupsForUserStmt.executeQuery();
		
		// If the result set moves to the next record, then user is found -> fill the object to be returned
		while(rst.next()) {
			Group group = new Group();
			group.setIdGroup(rst.getInt("idGroup"));
			group.setCreatedByUser(user);
			group.setGroupName(rst.getString("GroupName"));
			
			results.add(group);
		}
		
		// Close query and result set
		selectGroupsForUserStmt.close();
		rst.close();
		
		return results;
		
	}
	
	public List<User> getUsersForGroup(Group entity) throws SQLException {
		// This method gets a group and returns all the associated users!
		List<User> results = new ArrayList<User>();
		ResultSet rst = null;
		
		PreparedStatement findUsersForGroupStmt = conn.prepareStatement(findUsersForGroupQuery);
		findUsersForGroupStmt.setInt(1, entity.getIdGroup());
		
		rst = findUsersForGroupStmt.executeQuery();
		
		User_DBHandler userDBHandler;
		try {
			userDBHandler = new User_DBHandler();
			
			while(rst.next()) {
				User user = userDBHandler.getUserById(rst.getInt("idUser"));
				results.add(user);
			}
			
		} catch (DBManagerException e) {
			// Throw an SQL Exception to be compatible with parent method declaration
			throw new SQLException(e.getMessage());
		}
		
		return results;
	}
	
	public Group findGroupById(int idGroup) throws SQLException {
		// This method finds a group by Id in the DB and returns it as an object!
		Group group = null;
		ResultSet rst = null;
		
		PreparedStatement findGroupByIdStmt = conn.prepareStatement(findGroupByIdQuery);
		findGroupByIdStmt.setInt(1, idGroup);
		
		rst = findGroupByIdStmt.executeQuery();
		
		while(rst.next()) {
			group = new Group();
			group.setIdGroup(rst.getInt("idGroup"));
			group.setGroupName(rst.getString("GroupName"));
			
			User createdByUser = new User();
			createdByUser.setId(rst.getInt("CreatedByUser"));
			
			group.setCreatedByUser(createdByUser);
		}
		
		// Close result set and query
		rst.close();
		findGroupByIdStmt.close();
		
		return group;
	}

}

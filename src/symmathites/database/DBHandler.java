package symmathites.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import symmathites.exceptions.DBManagerException;

public abstract class DBHandler<Entity> {

	protected Connection conn = null;
	
	private String lastInsertIdQuery = "SELECT LAST_INSERT_ID() LastInsId";
	
	public DBHandler() throws DBManagerException {
		// Get the DB connection on initialization
		conn = DBManager.getInstance().getConnection();
		
		if(conn==null) 
			throw new DBManagerException("Δεν μπορώ να συνδεθώ με τη βάση δεδομένων!");
	}
	
	public abstract void Create(Entity entity) throws SQLException;
	
	public abstract List<Entity> Read() throws SQLException;
	
	public abstract void Update(Entity entity) throws SQLException;

	public abstract void Delete(Entity entity) throws SQLException;
	
	public int GetLastInsertId() throws SQLException {
		// This method returns the last inserted id in the database session!
		int result = 0;
		
		Statement lastInsertIdStmt = conn.createStatement();
		ResultSet rst = null;
		
		rst = lastInsertIdStmt.executeQuery(lastInsertIdQuery);
		while(rst.next()) {
			result = rst.getInt("LastInsId");
		}
		
		// Close query and result set
		rst.close();
		lastInsertIdStmt.close();
		
		return result;

	}
	
}

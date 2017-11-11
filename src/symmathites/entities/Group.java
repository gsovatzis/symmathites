package symmathites.entities;

public class Group {

	private int idGroup;
	private User CreatedByUser; 
	private String GroupName;
		
	public Group() {
		
	}
	
	public int getIdGroup() {
		return idGroup;
	}
	
	public void setIdGroup(int idGroup) {
		this.idGroup = idGroup;
	}
	
	public User getCreatedByUser() {
		return CreatedByUser;
	}
	
	public void setCreatedByUser(User CreatedByUser) {
		this.CreatedByUser = CreatedByUser;
	}
	
	public String getGroupName() {
		return GroupName==null?"":GroupName;
	}
	
	public void setGroupName(String GroupName) {
		this.GroupName = GroupName;
	}
	
}

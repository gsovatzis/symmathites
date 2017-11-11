package symmathites.entities;

public class UserComment {

	private int idComment;
	private String Comment;
	private User ForUser;
	private User PostedByUser;

	public UserComment() {
		
	}
	
	public int getIdComment() {
		return idComment;
	}
	
	public void setIdComment(int idComment) {
		this.idComment = idComment;
	}
	
	public String getComment() {
		return Comment==null?"":Comment;
	}
	
	public void setComment(String Comment) {
		this.Comment = Comment;
	}
	
	public User getForUser() {
		return ForUser;
	}
	
	public void setForUser(User ForUser) {
		this.ForUser = ForUser;
	}
	
	public User getPostedByUser() {
		return PostedByUser;
	}
	
	public void setPostedByUser(User PostedByUser) {
		this.PostedByUser = PostedByUser;
	}
	

}

package application;

public class Reviews_Details {
	
	String business_name;
	String user_name;
	int funny_votes;
	int useful_votes;
	int cool_votes;
	int stars;
	
	
	
	
	public Reviews_Details(String business_name, String user_name, int funny_votes, int useful_votes, int cool_votes,
			int stars) {
		super();
		this.business_name = business_name;
		this.user_name = user_name;
		this.funny_votes = funny_votes;
		this.useful_votes = useful_votes;
		this.cool_votes = cool_votes;
		this.stars = stars;
	}
	public String getBusiness_name() {
		return business_name;
	}
	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getFunny_votes() {
		return funny_votes;
	}
	public void setFunny_votes(int funny_votes) {
		this.funny_votes = funny_votes;
	}
	public int getUseful_votes() {
		return useful_votes;
	}
	public void setUseful_votes(int useful_votes) {
		this.useful_votes = useful_votes;
	}
	public int getCool_votes() {
		return cool_votes;
	}
	public void setCool_votes(int cool_votes) {
		this.cool_votes = cool_votes;
	}
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}
	
	
}

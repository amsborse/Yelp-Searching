package application;

public class Business {
	
	private String b_name;
	private String city;
	private String state;
	private float stars;
	
	@Override
	public String toString() {
		return "Business [b_name=" + b_name + ", city=" + city + ", state=" + state + ", stars=" + stars + "]";
	}


	public Business(String b_name, String city, String state, float stars) {
		super();
		this.b_name = b_name;
		this.city = city;
		this.state = state;
		this.stars = stars;
	}


	public Business() {
		this.city = "";
		this.b_name = "";
		this.state = "";
		this.stars = 0;
	}


	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getB_name() {
		return b_name;
	}
	public void setB_name(String b_name) {
		this.b_name = b_name;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public float getStars() {
		return stars;
	}
	public void setStars(float stars) {
		this.stars = stars;
	}

	
	
}

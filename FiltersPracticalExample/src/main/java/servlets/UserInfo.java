package servlets;

public class UserInfo {
	
	private String name;
	
	private String phoneNumber;
	
	private String email;
	
	private String password;

	public UserInfo(String name, String phoneNumber, String email, String password) {
		super();
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Name: ").append(name).append("\n");
		sb.append("Phone Number: ").append(phoneNumber).append("\n");
		sb.append("Email: ").append(email).append("\n");
		sb.append("Password: ").append("*".repeat(password.length())).append("\n");
		return sb.toString();
	}
	

}

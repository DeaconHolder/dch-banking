package Main;

public class Member {
	
	private String username = "";
	private String password = "";
	private double money = 0.00;
	
	public Member(String username, String password, double money) {
		this.username = username;
		this.password = password;
		this.money = money;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setMoney(double money) {
		this.money = money;
	}
	
	public double getMoney() {
		return this.money;
	}
	
	public String toString() {
		return ("Username: " + this.username + "\nPassword: " + this.password + "\nMoney: " + this.money);
	}
	
}

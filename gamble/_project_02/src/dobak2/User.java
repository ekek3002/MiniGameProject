package dobak2;

import java.io.Serializable;

public class User implements Stake,Serializable{
	private int money = 0;
	private String id;
	private String password;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setMoney(int chip) {
		money += chip;
	}
	public int getMoney() {
		return money;
	}

}

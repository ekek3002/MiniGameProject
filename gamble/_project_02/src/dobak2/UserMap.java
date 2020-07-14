package dobak2;

import java.io.Serializable;
import java.util.HashMap;

public class UserMap implements Serializable{
	private HashMap<String,User> userMap;
	public UserMap(HashMap<String,User> userMap) {
		this.userMap = userMap;
	}
	
	public void put(String id,User user) {
		userMap.put(id, user);
	}
	
	public User get(String id) {
		return userMap.get(id);
	}
}

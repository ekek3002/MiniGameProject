package dobak2;

import java.util.HashMap;

public class DB {
	public static void main(String[] args) {
		HashMap<String, User> h = new HashMap<String, User>();
		UserMap um = new UserMap(h);
		Data d = new Data(um);
		User u1 = new User();
		u1.setId("다원");
		u1.setPassword("1234");
		um.put("다원", u1);
		
		User u2 = new User();
		u2.setId("다깡");
		u2.setPassword("2345");
		um.put("다깡", u2);
	
		User u3 = new User();
		u3.setId("강팀장");
		u3.setPassword("3456");
		um.put("강팀장", u3);
		d.saveData();
		
		User u4 = new User();
		u4.setId("형균");
		u4.setPassword("1");
	}
}

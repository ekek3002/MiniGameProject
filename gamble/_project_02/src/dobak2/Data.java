package dobak2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Data {
	FileOutputStream fos = null;
	FileInputStream fis = null;
	
	UserMap um;
	
	public Data(UserMap um) {
		this.um = um;
	}

	public void saveData() { //데이터 저장
		try {
			fos = new FileOutputStream("data.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(um);
			
			fos.close();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public UserMap loadData() { //데이터 불러오기	
		
		try {
			fis = new FileInputStream("data.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			um = (UserMap)ois.readObject();
		
			fis.close();
			ois.close();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return um;
	}
}

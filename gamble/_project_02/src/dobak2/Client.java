package dobak2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	final int PORT = 9000;
	final String IP = "127.0.0.1"; 
	Socket sock = null;
	Scanner keyboard;
	BufferedReader br;
	PrintWriter pw;
	
	ClientThread clThread;
	String answer;
	
	public Client() {
		try {
			sock = new Socket(IP,PORT);
			
			br = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(this.sock.getOutputStream()));
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
			clThread = new ClientThread(sock, br, pw);
			clThread.start();
			String line = null;
			while((line = keyboard.readLine()) != null){
				pw.println(line);
				pw.flush();	
				if(line.equals("q"))
					break;
			}
			if(br != null) br.close();
			if(pw != null)pw.close();
			if(keyboard != null)keyboard.close();
			
		}catch(Exception e) {
			
		}finally {
			try {
				if(sock != null)
					sock.close();
				System.exit(0);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		new Client();
	}
}

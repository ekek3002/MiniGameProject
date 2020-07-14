package dobak2;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread{
	Socket sock;
	BufferedReader br;
	PrintWriter pw;
	
	public ClientThread(Socket sock, BufferedReader br, PrintWriter pw) {
		this.sock = sock;
		try {
			this.br = br;
			this.pw = pw;
		} catch (Exception e) {
		}
	}
	public void run() {
		try {
			String line = null;
			while((line = br.readLine()) != null){ // 서버에서 보내주는 내용을 line으로 받음	
				System.out.println(line); // line을 클라이언트에게 출력해서 보여줌
			}
			if(sock != null) sock.close();
			if(br != null) br.close();
			if(pw != null) pw.close();
		} catch (Exception e) {
		}
	}
}

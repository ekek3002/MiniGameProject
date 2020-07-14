package dobak2;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

class Server {
	final static int PORT = 9000;
	ServerSocket server;
	Socket sock;
	PrintWriter pw;
	RaceAttb ra;
	RacingThread rt;

	public Server() {
		try {
			server = new ServerSocket(PORT);
			ra = new RaceAttb();
			rt = new RacingThread(ra);
			rt.start();
			System.out.println("서버 접속 대기중...");
			while (true) {
				sock = server.accept();
				if (ServerThread.visitor >= 100) {
					pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
					pw.println("서버가 꽉 찼으므로 다음에 다시 접속해 주세요.");
					pw.flush();
				} else {
					++ServerThread.visitor;
					ServerThread svThread = new ServerThread(sock, ra);
					svThread.start();
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}

public class ServerMain {

	public static void main(String[] args) {
		new Server();
	}

}

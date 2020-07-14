package dobak2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class ServerThread extends Thread {
	Socket sock;
	BufferedReader br;
	PrintWriter pw;

	User user;
	UserMap userMap;

	Data data;
	RaceAttb ra;

	Sadari sadari;
	BlackJackGame blackJack;
	Baccarat bcr;
	static int visitor = 0;

	public ServerThread(Socket sock, RaceAttb ra) {
		data = new Data(userMap);
		userMap = data.loadData();
		this.ra = ra;

		this.sock = sock;
		System.out.println(sock.getInetAddress() + "로부터 연결");
		try {
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			while (login()) {
			}
			sadari = new Sadari(user, pw, br);
			blackJack = new BlackJackGame(user, br, pw);
			bcr = new Baccarat(user, pw, br);
			while (true) {
				String sel = gameSelect();

				switch (sel) {
				case "1": // 블랙잭
					blackJack.play();
					break;
				case "2": // 사다리
					sadari.showAll();
					break;
				case "3": // 경마
					racing();
					break;
				case "4": // 바카라
					bcr.betting();
					bcr.mix();
					bcr.playing();
					bcr.judge();
					break;
				case "5": // 입금
					deposit();
					break;
				case "q":
					--visitor;
					data.saveData();
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (sock != null)
					sock.close();
			} catch (Exception e) {
			}
		}
	}

	public String gameSelect() {
		String sel = null;
		try {
			pw.println("잔액 : " + user.getMoney());
			pw.println("게임선택");
			pw.println("1.블랙잭");
			pw.println("2.사다리");
			pw.println("3.경마");
			pw.println("4.바카라");
			pw.println("5.입금하기");
			pw.println("q.종료");
			pw.flush();

			sel = br.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sel;
	}

	public boolean login() {
		pw.println("===메인 메뉴===\n1. 회원가입\n2. 로그인\n3. 종료");
		pw.flush();
		String sel;
		try {
			sel = br.readLine();
			user = new User();
			String id;
			switch (sel) {
			case "1":
				while (true) {
					pw.println("회원가입할 아이디 입력>>\n뒤로 가려면 /back");
					pw.flush();
					id = br.readLine();
					if (id.equals("/back"))
						return true;
					if (userMap.get(id) != null) {
						pw.println("이미 존재하는 아이디입니다.");
						continue;
					}
					user.setId(id);
					pw.println("비번 입력>");
					pw.flush();
					String password = br.readLine();
					user.setPassword(password);
					pw.println("회원가입 성공!");
					pw.flush();
					userMap.put(id, user);
					data.saveData();
					break;
				}
				return false;
			case "2":
				while (true) {
					pw.println("로그인할 아이디 입력>>\n뒤로 가려면 /back");
					pw.flush();
					id = br.readLine();
					if (id.equals("/back"))
						return true;
					if (userMap.get(id) == null) {
						pw.println("그런 아이디는 존재하지 않아");
						pw.flush();
						continue;
					}
					while (true) {
						pw.println("비번> 뒤로가기 : /back입력");
						pw.flush();
						String password = br.readLine();
						if (password.equals("/back"))
							return true;
						if (!userMap.get(id).getPassword().equals(password)) {
							pw.println("틀림");
							pw.flush();
							continue;
						}
						pw.println("로그인 성공");
						pw.flush();
						user = userMap.get(id);
						break;
					}
					break;
				}
				return false;
			case "3":
				break;
			default:
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void deposit() { // 입금 메서드
		pw.println(user.getId() + "님 얼마 입금하시겠습니까?");
		pw.flush();
		try {
			String chip = br.readLine();
			pw.println(chip + "원 입금하셨습니다.");
			pw.flush();
			user.setMoney((Integer.parseInt(chip)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void racing() throws InterruptedException {
		try {
			ra.getRhm().put(user.getId(), pw);
			pw.println("┌─────────────────┐");
			pw.println("     현재 베팅 액수    " + ra.getBettingCoin());
			pw.println("　       1. 심장의고동    " + ra.getBettingCnt()[0]);
			pw.println("　       2. 문학치프       " + ra.getBettingCnt()[1]);
			pw.println("　       3. 롤러브레이드 " + ra.getBettingCnt()[2]);
			pw.println("　       4. 닥터카슨       " + ra.getBettingCnt()[3]);
			pw.println("　       5. 라온퍼스트    " + ra.getBettingCnt()[4]);
			pw.println("└─────────────────┘");

			pw.println("베팅 할 말의 숫자를 입력해주세요 (베팅액 : 100000)");
			pw.flush();
			int userChoice = Integer.parseInt(br.readLine());

			int bettingCoin = 100000;
			if (bettingCoin > user.getMoney()) {
				pw.println("잔액이 부족합니다. 충전 후 이용해주세요");
				ra.getRhm().remove(user.getId());
				return;
			}
			++ra.getBettingCnt()[userChoice - 1];
			ra.setBettingCoin((int) (bettingCoin * 0.95));
			user.setMoney(bettingCoin * -1);
			pw.println("잔액 : " + user.getMoney());
			boolean isEnd = ra.getisEnd();
			while (!isEnd) {
				isEnd = ra.getisEnd();
				Thread.sleep(10);
			}
			ra.getRhm().remove(user.getId());
			if (ra.getHorseRank()[userChoice - 1] == 1) {
				int getMoney = (int) (ra.getBettingCoin() * 1.0f / ra.getWinNum() / ra.getBettingCnt()[userChoice - 1]);
				user.setMoney(getMoney);
				pw.println(getMoney + "획득!");
			}
			pw.println("잔액 : " + user.getMoney());
			pw.flush();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

package dobak2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

//betting,mix,playing,judge 순으로 실행
public class Baccarat {
	public static int[] set = new int[6]; // 카드 모음
	private int p1, p2, p3, b1, b2, b3; // 플레이어랑 뱅커 카드
	static int pResult, bResult, pThree, bThree; // 카드 2개 더한 결과, 3번째 카드변환

	User user;
	PrintWriter pw;
	BufferedReader br;

	public int bettingCoin; // 배팅금액
	public int select; // 유저 베팅 선택

	public Baccarat(User user, PrintWriter pw, BufferedReader br) {
		this.user = user;
		this.pw = pw;
		this.br = br;
	}

	public void betting() {
		pw.println("얼마 배팅하시겠어요?");
		pw.println("현재 잔액 : " + user.getMoney());
		pw.println("★배팅 금액 입력★");
		pw.flush();
		try {
			this.bettingCoin = (Integer.parseInt(br.readLine()));
			if(bettingCoin>user.getMoney()) {
				pw.println("잔액이 부족합니다. 충전을 해주세요.");
				pw.flush();
				return;
			}
			pw.println("어디에 배팅하시겠습니까? (숫자로 입력)");
			pw.println("1. 플레이어(배당금 2배)\n2. 뱅커(배당금 2배,수수료 5%)\n3. 타이(배당금 9배)");
			pw.flush();
			this.select = (Integer.parseInt(br.readLine()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void mix() {// 카드 섞어서 6개 뽑기
		for (int i = 0; i < set.length; i++) {
			set[i] = (int) ((Math.random() * 52) + 1);
			for (int j = 0; j < i; j++) {
				if (set[j] == set[i])
					i--;
				break;
			}
		}
		this.p1 = set[0];
		this.p2 = set[1];
		this.p3 = set[2];

		this.b1 = set[3];
		this.b2 = set[4];
		this.b3 = set[5];
	}// mix 끝

	public static String shapeNum(int i) {// 카드 모양,숫자 지정
		String shape; // 카드 모양
		String num; // 카드 숫자
		if (i < 14)
			shape = "♥";
		else if (i < 27) {
			shape = "♠";
			i -= 13;
		} else if (i < 40) {
			shape = "♣";
			i -= 26;
		} else {
			shape = "◆";
			i -= 39;
		}
		if (i < 11)
			num = (Integer.toString(i));
		else if (i == 11)
			num = "JACK";
		else if (i == 12)
			num = "QUEEN";
		else
			num = "KING";

		return (shape + num);
	}// shapeNum 끝

	public static int change(int i) {// 카드값 숫자로 변환
		int num; // 반환값
		if (i > 39)
			i -= 39;
		else if (i > 26)
			i -= 26;
		else if (i > 13)
			i -= 13;

		if (i >= 10)
			num = 0;
		else
			num = i;

		return num;
	} // change 끝

	public void playing() throws InterruptedException {
		pResult = change(p1) + change(p2); // 플레이어 카드 2장뽑은 결과
		bResult = change(b1) + change(b2); // 뱅커 카드 2장뽑은 결과
		pThree = change(p3); // 플레이어 3번째카드
		bThree = change(b3); // 뱅커 3번째카드

		if (pResult >= 10)
			pResult -= 10;
		if (bResult >= 10)
			bResult -= 10;

		if (pResult >= 8 || bResult >= 8) { // 내츄럴이 나왔을 때

			pw.println("플레이어의 카드: " + shapeNum(p1) + ", " + shapeNum(p2));
			Thread.sleep(2000);
			pw.flush();
			pw.println("뱅커의 카드: " + shapeNum(b1) + ", " + shapeNum(b2));
			Thread.sleep(2000);
			pw.flush();
			pw.println("내츄럴입니다.");
			pw.flush();
		} else { // 내츄럴이 나오지 않았을 때
			pw.println("플레이어의 카드: " + shapeNum(p1) + ", " + shapeNum(p2));
			Thread.sleep(2000);
			pw.flush();
			pw.println("뱅커의 카드: " + shapeNum(b1) + ", " + shapeNum(b2));
			Thread.sleep(2000);
			pw.flush();

			if (pResult < 6) {// 플레이어가 카드가 3장이 된 경우 - 표에 따른다
				pw.println("플레이어가 세번째 카드를 뽑습니다. : " + shapeNum(p3));
				Thread.sleep(2000);
				pw.flush();
				pResult += pThree;
				if (pResult >= 10)
					pResult -= 10;
				pw.println("뱅커가 세번째 카드를 뽑습니다. : " + shapeNum(b3));
				Thread.sleep(2000);
				pw.flush();
				bResult += bThree;
				if (bResult >= 10)
					bResult -= 10;

				if (bResult <= 2) {
					bResult += bThree;
				} else if (bResult == 3) {
					if (pThree != 8)
						bResult += bThree;
				} else if (bResult == 4) {
					if (pThree != 0 && pThree != 1 && pThree != 8 && pThree != 9)
						bResult += bThree;
				} else if (bResult == 5) {
					if (pThree == 4 || pThree == 5 || pThree == 6 || pThree == 7)
						bResult += bThree;
				} else if (bResult == 6) {
					if (pThree == 6 || pThree == 7)
						bResult += bThree;
				}
			} else {// 플레이어 스탠드의 경우
				if (bResult < 6) { // 뱅커가 3번째 카드를 뽑는 경우
					pw.println("뱅커가 세번째 카드를 뽑습니다. : " + shapeNum(b3));
					Thread.sleep(2000);
					pw.flush();
					bResult += bThree;
					if (bResult >= 10)
						bResult -= 10;
				}
			}
		}
	} // playing() 끝

	public void judge() throws InterruptedException {// 승패 결정
		if (pResult > bResult) {// 플레이어 승
			pw.println("플레이어 숫자 합 " + pResult + "(으)로 플레이어 승리!");
			Thread.sleep(2000);
			pw.flush();
			if (select == 1) {
				user.setMoney((bettingCoin));
				pw.println(bettingCoin + "원 땄다!");
				pw.flush();
			} else {
				pw.println(bettingCoin + "원 잃었다...");
				pw.flush();
				user.setMoney(bettingCoin*-1);
			}
		} else if (pResult < bResult) {// 뱅커 승
			pw.println("뱅커 숫자 합 " + bResult + "로 뱅커 승리!");
			Thread.sleep(2000);
			pw.flush();
			if (select == 2) {
				user.setMoney((int) (bettingCoin* 0.95));
				pw.println(bettingCoin + "원 땄다! 수수료 5% 떼였지만.");
				pw.flush();
			} else {
				pw.println(bettingCoin + "원 잃었다...");
				pw.flush();
				user.setMoney(bettingCoin*-1);
			}
		} else {
			pw.println("타이입니다.");
			Thread.sleep(2000);
			pw.flush();
			if (select == 3) {
				user.setMoney((bettingCoin * 9));
				pw.println(bettingCoin + "원 땄다!");
				pw.flush();
			} else {
				pw.println(bettingCoin + "원 잃었다...");
				pw.flush();
				user.setMoney(bettingCoin*-1);
			}
		}
	}// judge 끝
}

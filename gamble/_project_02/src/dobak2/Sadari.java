package dobak2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/*	출발 = 랜덤값
 * 	줄수 = 랜덤값
 * 	끝값 = 출발, 줄수 랜덤값
 */
public class Sadari { // showAll, start, line 메서드 포함
	User user;
	PrintWriter pw;
	BufferedReader br;

	int result;
	public int bettingCoin; // 배팅금액
	public int userChoice;

	public int startNum; // 좌 우 출발 결정
	public int lineNum; // 3줄 4줄 결정
	public static Random rd = new Random();

	public Sadari(User user, PrintWriter pw, BufferedReader br) {
		this.user = user;
		this.pw = pw;
		this.br = br;
	}
	
	public void showAll() throws InterruptedException {
		result = 0;
		pw.println("어디에 배팅하시겠어요?");
		pw.println("현재 잔액 : " + user.getMoney());
		pw.println("★배팅 금액 입력★");
		pw.flush();
		try {
			this.bettingCoin = (Integer.parseInt(br.readLine()));
			if(bettingCoin > user.getMoney()) {
				pw.println("잔액이 부족합니다. 충전을 해주세요\n");
				pw.flush();
				return;
			}
			pw.println("┌───────────────────────┐");
			pw.println("     배팅번호를 선택하세요. ");
			pw.println("　       1. 좌출발         ");
			pw.println("　       2. 우출발         ");
			pw.println("　       3. 3줄        	  ");
			pw.println("　       4. 4줄        	  ");
			pw.println("　       5. 홀        	  ");
			pw.println("　       6. 짝        	  ");
			pw.println("　       7. 좌출발, 3줄, 짝        	  ");
			pw.println("　       8. 좌출발, 4줄, 홀        	  ");
			pw.println("　       9. 우출발, 3줄, 홀        	  ");
			pw.println("　     10. 우출발, 4줄, 짝        	  ");
			pw.println("└───────────────────────┘");
			pw.flush();
			this.userChoice = (Integer.parseInt(br.readLine()));
			pw.println(userChoice + "번에 배팅하셨습니다.");
			pw.flush();

			if (userChoice < 1 || userChoice > 10) {
				pw.println("1~10번 중에 선택하세요.");
				showAll();
				return;
			}

			startNum = rd.nextInt(2); // 0이면 좌, 1이면 우
			if (startNum == 0) {
				pw.println();
				Thread.sleep(1000);
				pw.flush();
				pw.println("　　　　좌출발");
				pw.flush();
			} else {
				pw.println();
				Thread.sleep(1000);
				pw.flush();
				pw.println("　　　　　  　	　   우출발");
				pw.flush();
			}

			lineNum = rd.nextInt(2); // 0이면 3줄, 1이면 4줄
			if (userChoice <= 6) {
			if (startNum == 0 && lineNum == 0) {
				// 좌출발,3줄
				pw.println("　　　　　└───────────┐");
				Thread.sleep(1000);
				pw.flush();
				pw.println("　　　　　┌───────────┘");
				Thread.sleep(2000);
				pw.flush();
				pw.println("　　　　　└───────────┐");
				Thread.sleep(2000);
				pw.flush();
				pw.println("　　　　　　　　　　　        짝");
				pw.println();
				pw.flush();
				pw.println("　　　결과는 ★좌★3줄★짝★  입니다");
				pw.flush();
				if (userChoice == 1 || userChoice == 3 || userChoice == 6) {
					pw.println("축하합니다. 맞추셨네요~!");
					result = (int) (bettingCoin * 1.85);
					user.setMoney(result);
					pw.println("남은 잔액 :" + user.getMoney());
					pw.flush();
				} else {
					pw.println("아쉽네요. 다음엔 맞추세요 ^^");
					result -= bettingCoin;
					user.setMoney(result);
					pw.println("남은 잔액 :" + user.getMoney());
					pw.flush();
				}

			} else if (startNum == 0 && lineNum == 1) {
				// 좌출발,4줄
				pw.println("　　　　　└───────────┐");
				Thread.sleep(1000);
				pw.flush();
				pw.println("　　　　　┌───────────┘");
				Thread.sleep(2000);
				pw.flush();
				pw.println("　　　　　└───────────┐");
				Thread.sleep(2000);
				pw.flush();
				pw.println("　　　　　┌───────────┘");
				Thread.sleep(2000);
				pw.flush();
				pw.println("　　　　   홀");
				pw.println();
				pw.println("　　　결과는 ★좌★4줄★홀★  입니다");
				pw.flush();
				if (userChoice == 1 || userChoice == 4 || userChoice == 5) {
					pw.println("축하합니다. 맞추셨네요~!");
					pw.println("축하합니다. 맞추셨네요~!");
					result = (int) (bettingCoin * 1.85);
					user.setMoney(result);
					pw.println("남은 잔액 :" + user.getMoney());
					pw.flush();
				} else {
					pw.println("아쉽네요. 다음엔 맞추세요 ^^");
					result -= bettingCoin;
					user.setMoney(result);
					pw.println("남은 잔액 :" + user.getMoney());
					pw.flush();
				}

			} else if (startNum == 1 && lineNum == 0) {
				pw.println("　　　　　┌───────────┘");
				Thread.sleep(1000);
				pw.flush();
				pw.println("　　　　　└───────────┐");
				Thread.sleep(2000);
				pw.flush();
				pw.println("　　　　　┌───────────┘");
				Thread.sleep(2000);
				pw.flush();
				pw.println("　　　　   홀");
				pw.println();
				pw.println("　　　결과는 ★우★3줄★홀★  입니다");
				pw.flush();
				if (userChoice == 2 || userChoice == 3 || userChoice == 5) {
					pw.println("축하합니다. 맞추셨네요~!");
					result = (int) (bettingCoin * 1.85);
					user.setMoney(result);
					pw.println("남은 잔액 :" + user.getMoney());
					pw.flush();
				} else {
					pw.println("아쉽네요. 다음엔 맞추세요 ^^");
					result -= bettingCoin;
					user.setMoney(result);
					pw.println("남은 잔액 :" + user.getMoney());
					pw.flush();
				}

			} else if (startNum == 1 && lineNum == 1) {
				// 우출발,4줄
				pw.println("　　　　　┌───────────┘");
				Thread.sleep(1000);
				pw.flush();
				pw.println("　　　　　└───────────┐");
				Thread.sleep(2000);
				pw.flush();
				pw.println("　　　　　┌───────────┘");
				Thread.sleep(2000);
				pw.flush();
				pw.println("　　　　　└───────────┐");
				Thread.sleep(2000);
				pw.flush();
				pw.println("　　　　　　　　　　　        짝");
				pw.println();
				pw.println("　　　결과는 ★우★4줄★짝★  입니다");
				pw.flush();
				if (userChoice == 2 || userChoice == 4 || userChoice == 6) {
					pw.println("축하합니다. 맞추셨네요~!");
					result = (int) (bettingCoin * 1.85);
					user.setMoney(result);
					pw.println("남은 잔액 :" + user.getMoney());
					pw.flush();
				} else {
					pw.println("아쉽네요. 다음엔 맞추세요 ^^");
					result -= bettingCoin;
					user.setMoney(result);
					pw.println("남은 잔액 :" + user.getMoney());
					pw.flush();
				}

			}
		} else if(userChoice >= 7) {
			if(startNum == 0 && lineNum ==0) {
				// 좌출발,3줄
				pw.println("　　　　　└───────────┐");
				Thread.sleep(1000);
				pw.flush();
				pw.println("　　　　　┌───────────┘");
				Thread.sleep(2000);
				pw.flush();
				pw.println("　　　　　└───────────┐");
				Thread.sleep(2000);
				pw.flush();
				pw.println("　　　　　　　　　　　        짝");
				pw.println();
				pw.flush();
				pw.println("　　　결과는 ★좌★3줄★짝★  입니다");
				pw.flush();
				if (userChoice == 7) {
					pw.println("축하합니다.오늘은 럭키데이^^");
					result = (int) (bettingCoin * 3.85);
					user.setMoney(result);
					pw.println("남은 잔액 :" + user.getMoney());
					pw.flush();
				} else {
					pw.println("아쉽네요. 그러게... 욕심 내지 마시지...");
					result -= bettingCoin;
					user.setMoney(result);
					pw.println("남은 잔액 :" + user.getMoney());
					pw.flush();
				}
			}else if(startNum == 0 && lineNum == 1) {
				pw.println("　　　　　└───────────┐");
				Thread.sleep(1000);
				pw.flush();
				pw.println("　　　　　┌───────────┘");
				Thread.sleep(2000);
				pw.flush();
				pw.println("　　　　　└───────────┐");
				Thread.sleep(2000);
				pw.flush();
				pw.println("　　　　　┌───────────┘");
				Thread.sleep(2000);
				pw.flush();
				pw.println("　　　　   홀");
				pw.println();
				pw.println("　　　결과는 ★좌★4줄★홀★  입니다");
				pw.flush();
				if (userChoice == 8) {
					pw.println("축하합니다.오늘은 럭키데이^^");
					result = (int) (bettingCoin * 3.85);
					user.setMoney(result);
					pw.println("남은 잔액 :" + user.getMoney());
					pw.flush();
				} else {
					pw.println("아쉽네요. 그러게... 욕심 내지 마시지...");
					result -= bettingCoin;
					user.setMoney(result);
					pw.println("남은 잔액 :" + user.getMoney());
					pw.flush();
				}
			}else if(startNum == 1 && lineNum == 0) {
				pw.println("　　　　　┌───────────┘");
				Thread.sleep(1000);
				pw.flush();
				pw.println("　　　　　└───────────┐");
				Thread.sleep(2000);
				pw.flush();
				pw.println("　　　　　┌───────────┘");
				Thread.sleep(2000);
				pw.flush();
				pw.println("　　　　   홀");
				pw.println();
				pw.println("　　　결과는 ★우★3줄★홀★  입니다");
				pw.flush();
				if (userChoice == 9) {
					pw.println("축하합니다.오늘은 럭키데이^^");
					result = (int) (bettingCoin * 3.85);
					user.setMoney(result);
					pw.println("남은 잔액 :" + user.getMoney());
					pw.flush();
				} else {
					pw.println("아쉽네요. 그러게... 욕심 내지 마시지...");
					result -= bettingCoin;
					user.setMoney(result);
					pw.println("남은 잔액 :" + user.getMoney());
					pw.flush();
				}
			}else if(startNum == 1 && lineNum == 1) {
				// 우출발,4줄
				pw.println("　　　　　┌───────────┘");
				Thread.sleep(1000);
				pw.flush();
				pw.println("　　　　　└───────────┐");
				Thread.sleep(2000);
				pw.flush();
				pw.println("　　　　　┌───────────┘");
				Thread.sleep(2000);
				pw.flush();
				pw.println("　　　　　└───────────┐");
				Thread.sleep(2000);
				pw.flush();
				pw.println("　　　　　　　　　　　        짝");
				pw.println();
				pw.println("　　　결과는 ★우★4줄★짝★  입니다");
				pw.flush();
				if (userChoice == 10) {
					pw.println("축하합니다.오늘은 럭키데이^^");
					result = (int) (bettingCoin * 3.85);
					user.setMoney(result);
					pw.println("남은 잔액 :" + user.getMoney());
					pw.flush();
					pw.flush();
				} else {
					pw.println("아쉽네요. 그러게... 욕심 내지 마시지...");
					result -= bettingCoin;
					user.setMoney(result);
					pw.println("남은 잔액 :" + user.getMoney());
					pw.flush();
				}
			}
		}
		

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

} // class Game 끝

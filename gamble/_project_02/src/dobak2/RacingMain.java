package dobak2;

import java.util.Random;

class Racing {
	RaceAttb ra;
	private int cnt1, cnt2, cnt3, cnt4, cnt5;
	public int horse1, horse2, horse3, horse4, horse5;
	public int goal = 30;// 결승지점
	public static Random rd = new Random();
	public int rank = 0;
	String horseName[] = new String[5];
	String track[][] = new String[5][30];
	String trackStart[] = new String[30];

	public Racing(RaceAttb ra) {
		this.ra =ra;
		horseName[0] = "심장의고동";
		horseName[1] = "문학치프";
		horseName[2] = "롤러브레이드";
		horseName[3] = "닥터카슨";
		horseName[4] = "라온퍼스트";
	}
	
	public void showAll() throws InterruptedException {
		try {
			rank = 0;
			myPrintln("-------------------------------------------------------------------------------------------------");
			Thread.sleep(1000);
			cnt1 = cnt2 = cnt3 = cnt4 = cnt5 = 0;
			int runningNum1, runningNum2, runningNum3, runningNum4, runningNum5;

			for (int i = 0; i < track.length; i++) {
				for (int j = 0; j < track[i].length; j++) {
					track[i][j] = "";
				}
			}
			
			for (int i = 0; i < trackStart.length-1; i++) {
				trackStart[i] = "      ";
			}
			trackStart[29] = "♘end";
			while (cnt1 < 29 || cnt2 < 29 || cnt3 < 29 || cnt4 < 29 || cnt5 < 29) {
				for (int i = 0; i < trackStart.length; i++) 
					myPrint(trackStart[i]);
				myPrintln("");
				runningNum1 = (int) (Math.random() * 5) + 1;
				if (cnt1 + runningNum1 < 29)
					cnt1 += runningNum1;
				else
					cnt1 = 29;
				track[0][cnt1] = "♘";
				for (int i = 0; i < cnt1; ++i)
					track[0][i] = "      ";
				// ------------------------------------------------------
				runningNum2 = (int) (Math.random() * 5) + 1;
				if (cnt2 + runningNum2 < 29)
					cnt2 += runningNum2;
				else
					cnt2 = 29;
				track[1][cnt2] = "♞";
				for (int i = 0; i < cnt2; ++i)
					track[1][i] = "      ";
				// ------------------------------------------------------
				runningNum3 = (int) (Math.random() * 5) + 1;
				if (cnt3 + runningNum3 < 29)
					cnt3 += runningNum3;
				else
					cnt3 = 29;
				track[2][cnt3] = "♘";
				for (int i = 0; i < cnt3; ++i)
					track[2][i] = "      ";
				// ---------------------------------------------------
				runningNum4 = (int) (Math.random() * 5) + 1;
				if (cnt4 + runningNum4 < 29)
					cnt4 += runningNum4;
				else
					cnt4 = 29;
				track[3][cnt4] = "♞";
				for (int i = 0; i < cnt4; ++i)
					track[3][i] = "      ";
				// -----------------------------------------------------
				runningNum5 = (int) (Math.random() * 5) + 1;
				if (cnt5 + runningNum5 < 29)
					cnt5 += runningNum5;
				else
					cnt5 = 29;
				track[4][cnt5] = "♘";
				for (int i = 0; i < cnt5; ++i)
					track[4][i] = "      ";

				for (int i = 0; i < 5; i++) {	
					myPrintln((i + 1) + "번마 " + horseName[i]);
					for (int j = 0; j < 30; j++) {
						myPrint(track[i][j]);
					}
					myPrintln("");
					
				}
				myPrintln("-------------------------------------------------------------------------------------------------");
				Thread.sleep(1000);

				if (cnt1 == 29 || cnt2 == 29 || cnt3 == 29 || cnt4 == 29 || cnt5 == 29) {
					++rank;
				}
				if (cnt1 == 29 && ra.getHorseRank()[0] == 0) {
					ra.getHorseRank()[0] = rank;
				}
				if (cnt2 == 29 && ra.getHorseRank()[1] == 0) {
					ra.getHorseRank()[1] = rank;
				}
				if (cnt3 == 29 && ra.getHorseRank()[2] == 0) {
					ra.getHorseRank()[2] = rank;
				}
				if (cnt4 == 29 && ra.getHorseRank()[3] == 0) {
					ra.getHorseRank()[3] = rank;
				}
				if (cnt5 == 29 && ra.getHorseRank()[4] == 0) {
					ra.getHorseRank()[4] = rank;
				}
			}
			for (int i = 1; i <= 5; i++) {
				myPrintln(i + "등");
				for (int j = 0; j < 5; ++j) {
					if (ra.getHorseRank()[j] == i) {
						myPrintln(horseName[j]);
						if(i == 1)
							ra.setWinNum();
					}
				}
			}
			
			myPrintln("-------------------------------------------------------------------------------------------------");
			ra.setEnd(true);
			Thread.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
			myPrintln(e.getMessage());
		}

	}
	public void myPrintln(String s) {
		for(String key : ra.getRhm().keySet()) {
			ra.getRhm().get(key).println(s);
			ra.getRhm().get(key).flush();
		}
	}
	public void myPrint(String s) {
		for(String key : ra.getRhm().keySet()) {
			ra.getRhm().get(key).print(s);
			ra.getRhm().get(key).flush();
		}
	}
}

public class RacingMain {
	public static void main(String[] args) throws InterruptedException {
	}
}

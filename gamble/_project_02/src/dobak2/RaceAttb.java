package dobak2;

import java.io.PrintWriter;
import java.util.HashMap;

public class RaceAttb {
	private HashMap<String,PrintWriter> rhm;
	private int bettingCoin;
	private int horseRank[];
	private int bettingCnt[];
	private int winNum;
	private String horseName[] = new String[5];
	private boolean isEnd;
	private int rake_off[];
	
	public RaceAttb() {
		rhm = new HashMap<String, PrintWriter>();
		horseRank = new int[5];
		bettingCnt = new int[5];
		bettingCoin = 0;
		winNum =0;
		horseName[0] = "심장의고동";
		horseName[1] = "문학치프";
		horseName[2] = "롤러브레이드";
		horseName[3] = "닥터카슨";
		horseName[4] = "라온퍼스트";
		isEnd = false;
		rake_off = new int[5];
	}
	
	public void init() {
		for (int i = 0; i < 5; i++) {
			rake_off[i] = 0;
			horseRank[i] = 0;			
			bettingCnt[i] = 0;
		}
		bettingCoin = 0;
		winNum =0;
		isEnd = false;
	}

	public HashMap<String, PrintWriter> getRhm() {
		return rhm;
	}

	public void setRhm(HashMap<String, PrintWriter> rhm) {
		this.rhm = rhm;
	}

	public int getBettingCoin() {
		return bettingCoin;
	}

	public void setBettingCoin(int bettingCoin) {
		this.bettingCoin += bettingCoin;
	}

	public int[] getHorseRank() {
		return horseRank;
	}

	public void setHorseRank(int[] horseRank) {
		this.horseRank = horseRank;
	}

	public int[] getBettingCnt() {
		return bettingCnt;
	}

	public void setBettingCnt(int[] bettingCnt) {
		this.bettingCnt = bettingCnt;
	}

	public int getWinNum() {
		return winNum;
	}

	public void setWinNum() {
		++this.winNum;
	}

	public boolean getisEnd() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
	
	
}

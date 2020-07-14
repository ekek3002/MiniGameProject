package dobak2;

public class RacingThread extends Thread{
	RaceAttb ra;
	Racing race;
	public RacingThread(RaceAttb ra) {
		this.ra =ra;
		race = new Racing(ra);
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				for(int i = 6; i > 0; --i) {
					race.myPrintln("경마 시작까지 "+i*10 + "초 남았습니다.");
					race.myPrintln("┌─────────────────┐");
					race.myPrintln("     현재 베팅 액수    " + ra.getBettingCoin());
					race.myPrintln("　       1. 심장의고동    " + ra.getBettingCnt()[0]);
					race.myPrintln("　       2. 문학치프       " + ra.getBettingCnt()[1]);
					race.myPrintln("　       3. 롤러브레이드 " + ra.getBettingCnt()[2]);
					race.myPrintln("　       4. 닥터카슨       " + ra.getBettingCnt()[3]);
					race.myPrintln("　       5. 라온퍼스트    " + ra.getBettingCnt()[4]);
					race.myPrintln("└─────────────────┘");
					sleep(10000);
				}
				race.showAll();  
				race.ra.init();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}

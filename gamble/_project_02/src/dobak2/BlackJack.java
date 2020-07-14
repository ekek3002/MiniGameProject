package dobak2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

//카드 클래스
class Card {
	@Override
	public String toString() { // 카드덱 테스트 기능
		return "card{" + "pattern='" + pattern + ", denomination='" + denomination + '}';
	}

	private String pattern; // 패턴 선언
	private String denomination; // 숫자 선언
	private int point; // 점수 선언

	public Card(String pattern, int index) {
		this.pattern = pattern;
		this.denomination = this.numberToDenomination(index);
		this.point = this.numberToPoint(index);
	}

	private String numberToDenomination(int number) {
		if (number == 1) {
			return "A";
		} else if (number == 11) {
			return "J";
		} else if (number == 12) {
			return "Q";
		} else if (number == 13) {
			return "K";
		}
		return String.valueOf(number);
	}

	private int numberToPoint(int number) {
		if (number >= 11) {
			return 10;
		}
		return number;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	public int getPoint() {
		return this.point;
	}
}

//카드 덱 클래스 카드 넘버 생성
class CardDeck {
	@Override
	public String toString() { // 카드덱 테스트 기능
		StringBuilder sb = new StringBuilder();

		for (Card card : cards) {
			sb.append(card.toString());
			sb.append("\n");
		}

		return sb.toString();
	}

	private List<Card> cards;
	private static final String[] PATTERNS = { "♠", "♥", "◆", "♣" };
	private static final int CARD_COUNT = 13;

	public CardDeck() {
		cards = this.generateCards();
	}

	private List<Card> generateCards() {
		List<Card> cards = new LinkedList<>();
		for (String pattern : PATTERNS) {
			for (int i = 1; i <= CARD_COUNT; i++) {
				Card card = new Card(pattern, i);
				cards.add(card);
			}
		}
		return cards;
	}

	public Card draw() {
		Card selectedCard = getRandomCard();
		cards.remove(selectedCard);
		return selectedCard;
	}

	private Card getRandomCard() {
		int size = cards.size();
		int select = (int) (Math.random() * size);
		return cards.get(select);
	}
}

class Rule{
	public Player getWinner(List<Player> players) {
		Player highestPlayer = null;
		int highestPoint=0;
		
		for (Player player : players) {
			int playPointSum= getPointSum(player.openCards());
			if(playPointSum <=21 && playPointSum>highestPoint) {
				highestPlayer=player;
				highestPoint=playPointSum;
			}
		}
		if(highestPlayer == null)
			highestPlayer = players.get(1);
		return highestPlayer;
	}
	public int getPointSum(List<Card> cards) {
		int sum=0;
		
		for(Card card:cards) {
			sum+=card.getPoint();
		}
		return sum;
	}
}

interface Player {

	void receiveCard(Card card);

	void showCards();

	List<Card> openCards();

	void turnOff();

	void turnOn();

	boolean isTurn();
	
	void showCardsEnd();
	
	String getName();
}

//플레이어 클래스 생성
class Gamer implements Player {
	private List<Card> cards;
	private boolean turn;
	private String name;
	private BufferedReader br;
	private PrintWriter pw;

	public Gamer(String name, BufferedReader br, PrintWriter pw) {
		this.cards = new ArrayList<>();
		this.name=name;
		this.br = br;
		this.pw = pw;
	}

	public void receiveCard(Card card) {// 카드를 받다
		this.cards.add(card);
		showCards();
	}

	public void showCards() {
		StringBuilder sb = new StringBuilder();
		sb.append("당신의 현재 보유 카드 목록\n");

		for (Card card : cards) {
			sb.append(card.toString());
			sb.append("\n");
		}
		pw.println(sb.toString());
		pw.flush();
	}
	
	public void showCardsEnd() {
		showCards();
	}

	public List<Card> openCards() { // 카드를 열다
		return this.cards;
	}

	@Override
	public void turnOff() {
		this.setTurn(false);
	}

	@Override
	public void turnOn() {
		this.setTurn(true);
	}

	@Override
	public boolean isTurn() {
		return this.turn;
	}

	private void setTurn(boolean turn) {
		this.turn = turn;

	}

	@Override
	public String getName() {
		return name;
	}
}

//딜러 클래스 생성
class Dealer implements Player {
	private List<Card> cards;
	private boolean turn;

	private static final int CAN_RECEIVE_POINT = 16;
	private static final String NAME="딜러";
	
	private BufferedReader br;
	private PrintWriter pw;

	public Dealer(BufferedReader br, PrintWriter pw) {
		cards = new ArrayList<>();
		this.br = br;
		this.pw = pw;
	}
	
	public void receiveCard(Card card) {// 카드를 받다
		if (this.isReceiveCard()) {
			this.cards.add(card);
			this.showCards();
		}else {
			pw.println("카드의 총 합이 17이상입니다.\n");
			pw.flush();
		}
	}

	private boolean isReceiveCard() {
		return getPointSum() <= CAN_RECEIVE_POINT;
	}

	public int getPointSum() {
		int sum = 0;
		for (Card card : cards) {
			sum += card.getPoint();
		}
		return sum;
	}

	public void showCards() {
		StringBuilder sb = new StringBuilder();
		sb.append("딜러의 현재 보유 카드 목록\n");


		for(int i = 0; i < cards.size(); ++i) {
			if(i == 0) {
				sb.append(cards.get(0).toString());
				sb.append("\n");
			}
			else {
				sb.append("card{" + "pattern='" + "?" + ", denomination='" + "?" + '}');
				sb.append("\n");
			}
				
	}
		pw.println(sb.toString());
		pw.flush();
	}
	
	public void showCardsEnd() {
		StringBuilder sb = new StringBuilder();
		sb.append("딜러의 현재 보유 카드 목록\n");

		for (Card card : cards) {
			sb.append(card.toString());
			sb.append("\n");
		}
		pw.println(sb.toString());
		pw.flush();
	}

	public List<Card> openCards() { // 카드를 열다
		return this.cards;
	}

	@Override
	public void turnOff() {
		this.setTurn(false);
	}

	@Override
	public void turnOn() {
		this.setTurn(true);

	}

	@Override
	public boolean isTurn() {
		return this.turn;
	}

	private void setTurn(boolean turn) {
		this.turn = turn;
	}

	@Override
	public String getName() {
		return NAME;
	}
}

//게임 클래스 생성
class BlackJackGame {
	private static final int INIT_RECEIVE_CARD_COUNT = 2;
	private static final String STOP_RECEIVE_CARD = "0";
	private Rule rule;
	private User user;
	private BufferedReader br;
	private PrintWriter pw;
	
	public BlackJackGame(User user,BufferedReader br, PrintWriter pw) {
		rule = new Rule();
		this.user = user;
		this.br = br;
		this.pw = pw;
	}

	public void play() throws IOException { 
		pw.println("|========== Blackjack ==========|");
		pw.flush();
		Scanner sc = new Scanner(System.in);
		CardDeck cardDeck = new CardDeck();

		List<Player> players = Arrays.asList(new Gamer("사용자1", br, pw), new Dealer(br,pw));
		int chip = bet(sc);
		List<Player> initAfterPlayers = initphase(cardDeck, players);
		List<Player> playingAfterPlayers = playingPhase(sc, cardDeck, initAfterPlayers);
		
		Player winner = rule.getWinner(playingAfterPlayers);
		pw.println("승자는 "+winner.getName());
		pw.flush();
		returnBet(chip, winner);
	}

	private List<Player> playingPhase(Scanner sc, CardDeck cardDeck, List<Player> players) throws IOException {
		List<Player> cardReceivedPlayers;
		while (true) {
			cardReceivedPlayers = receiveCardAllPlayers(sc, cardDeck, players);
			if (isAllPlayerTurnOff(cardReceivedPlayers)) {
				break;
			}
		}
		return cardReceivedPlayers;
	}

	private List<Player> receiveCardAllPlayers(Scanner sc, CardDeck cardDeck, List<Player> players) throws IOException {
		Card card = cardDeck.draw();
		Dealer dealer = (Dealer)players.get(1);
		Gamer gamer = (Gamer)players.get(0);
		pw.println("딜러가 카드를 뽑습니다.");
		pw.flush();
		dealer.receiveCard(card);
		int dSum = dealer.getPointSum();
		if(dSum < 17) gamer.turnOn();
		else gamer.turnOff();
		
		if (isReceiveCard(sc)) {
			card = cardDeck.draw();
			gamer.receiveCard(card);
			gamer.turnOn();
		}
		else {
			dealer.showCardsEnd();
			gamer.turnOff();
		}

		return players;
	}

	private boolean isAllPlayerTurnOff(List<Player> players) {
		for (Player player : players) {
			if (player.isTurn()) {
				return false;
			}
		}
		return true;
	}

	private boolean isReceiveCard(Scanner sc) throws IOException {
		pw.println("카드를 뽑겠습니까?(0 입력 시 종료/계속 진행 시 Enter)");
		pw.flush();
		return !STOP_RECEIVE_CARD.equals(br.readLine());
	}

	private List<Player> initphase(CardDeck cardDeck, List<Player> players) {
		pw.println("게임을 시작합니다 카드를 2장을 DRAW 합니다");
		pw.flush();
		for (int i = 1; i <= INIT_RECEIVE_CARD_COUNT; i++) {
			for (Player player : players) {
				Card card = cardDeck.draw();
				player.receiveCard(card);
			}
		}
		return players;
	}
	
	private int bet(Scanner sc) throws IOException {
		pw.println("얼마를 배팅하시겠습니까?");
		pw.flush();
		String chip = br.readLine();
		user.setMoney(Integer.parseInt(chip) * -1);
		return Integer.parseInt(chip);
	}
	
	private void returnBet(int chip, Player p) {
		if(p instanceof Gamer) {
			pw.println((int)(chip*1.85) + "만큼 획득하셨습니다.");
			pw.flush();
			user.setMoney((int)(chip*1.85));
		}
	}
}

//메인 클래스 생성
public class BlackJack {
	public static void main(String[] args) {
//		BlackJackGame game = new BlackJackGame();
//		game.play();//메인 루프
	}
}

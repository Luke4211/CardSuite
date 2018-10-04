package CardSuite;
/*
 * BlackJackSimulator class, child class of BlackJack
 * This class is used for simulating hands
 * of blackjack
 */
public class BlackJackSimulator extends BlackJack {
	private int numHands;
	private int counter;
	private int type;
	private long timeStart;
	private long timeEnd;
	public BlackJackSimulator(long dealerMoney, long playerMoney, int numPlayers,
			int numHands, int minBet, int AItype) { //AI type: 1 = basic strategy, 2 = hilo
		super(dealerMoney, playerMoney, numPlayers, numHands, minBet);
		this.numHands = numHands;
		this.counter = 0;
		this.type = AItype;
		super.players = new Player[numPlayers];
		this.addPlayers();
		this.timeStart = System.currentTimeMillis();
		this.timeEnd = 0;
	}
	/*
	 * (non-Javadoc)
	 * @see CardSuite.BlackJack#game()
	 */
	@Override
	public void game() {
		System.out.println("Dealer Start money: " + this.houseMoney);
		for(int i = 0; i < this.numPlayers; i++) {
			System.out.println("Player " + this.players[i].getNum() + " start money: " 
								+ this.players[i].getMoney());
		}
		while(this.counter < this.numHands) {
			BlackJackHand hand = new BlackJackHand(super.getInfo());
			hand.isSim = true;
			hand.hand();
			super.handCount++;
			this.counter++;
			super.setDeck(hand.getHouse());
			super.houseMoney = hand.getMoney();
			super.discard = hand.getDiscard();
			super.players = hand.getPlayers();
		
			if(super.reshuffle()) {
				super.house.addDeck(discard);
				super.house.shuffle();
				super.discard = new Deck(true);
				
			}
		
		} 
		
		System.out.println("Dealer End money: " + this.houseMoney);
		for(int i = 0; i < this.numPlayers; i++) {
			System.out.println("Player " + this.players[i].getNum() + " end money: " 
								+ this.players[i].getMoney());
		}
		System.out.println("Num hands: " + this.numHands);
		this.timeEnd = System.currentTimeMillis();
		this.timeEnd -= this.timeStart;
		this.timeEnd /= 1000;
		System.out.println("Elapsed seconds: " + this.timeEnd );
	}
	
	@Override
	protected void addPlayers() {
		Player ai;
		if(this.type == 4) {
			ai = new BlackJackBasicAI(1, super.playerMoney);
			super.players[0] = ai;
			ai = new BlackJackHiLoAI(2, super.playerMoney);
			super.players[1] = ai;
			ai = new BlackJackIL18AI(3, super.playerMoney);
			super.players[2] = ai;
		}
		for(int i = 0; i < super.numPlayers; i++) {
			if(this.type == 1) {
				ai = new BlackJackBasicAI(i+1, super.playerMoney);
				super.players[i] = ai;
			} else if(this.type == 2) {
				 ai = new BlackJackHiLoAI(i+1, super.playerMoney);
				 super.players[i] = ai;
			} else if(this.type == 3) {
				ai = new BlackJackIL18AI(i+1, super.playerMoney);
				super.players[i] = ai;
			}
		} 
	}
	

	


}

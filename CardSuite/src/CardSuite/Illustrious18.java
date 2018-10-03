package CardSuite;
/*
.___.__  .__                  __         .__                       ____  ______  
|   |  | |  |  __ __  _______/  |________|__| ____  __ __  ______ /_   |/  __  \ 
|   |  | |  | |  |  \/  ___/\   __\_  __ \  |/  _ \|  |  \/  ___/  |   |>      < 
|   |  |_|  |_|  |  /\___ \  |  |  |  | \/  (  <_> )  |  /\___ \   |   /   --   \
|___|____/____/____//____  > |__|  |__|  |__|\____/|____//____  >  |___\______  /
                         \/                                   \/              \/
                         
Class written by Lucas Gorski
                         
*/
public class Illustrious18 	extends BlackJackBasicStrategy {
	private int count;
	/*
	 * These decision tables encode the strategy
	 * deviations and indices calculated by
	 * mathematician Donald Schlesinger which 
	 * he calls "The Illustrious 18". These
	 * are the  hands in which the player will deviate
	 * from basic strategy should the count
	 * equal or exceed the given threshold 
	 * in the first decision table. x's represent
	 * following basic strategy.
	 */
	private final String[][] hardIL18 = {
		    /* Hands:             2    3    4    5    6    7    8    9    10   A  */
			/*4-7 */			{"x", "x", "x", "x", "x", "x", "x", "x", "x", "x"},
			/* 8  */			{"x", "x", "x", "x", "x", "x", "x", "x", "x", "x"},
			/* 9  */			{"1", "x", "x", "x", "x", "3", "x", "x", "x", "x"},
			/* 10 */			{"x", "x", "x", "x", "x", "x", "x", "x", "4", "4"},
			/* 11 */			{"x", "x", "x", "x", "x", "x", "x", "x", "x", "1"},
			/* 12 */			{"3", "2", "0","-2","-1", "x", "x", "x", "x", "x"},
			/* 13 */			{"-1","-2","x", "x", "x", "x", "x", "x", "x", "x"},
			/* 14 */			{"x", "x", "x", "x", "x", "x", "x", "x", "x", "x"},
			/* 15 */			{"x", "x", "x", "x", "x", "x", "x", "x", "4", "x"},
			/* 16 */			{"x", "x", "x", "x", "x", "x", "x", "5", "0", "x"},
			/* 17 */			{"x", "x", "x", "x", "x", "x", "x", "x", "x", "x"},
			/* 18+*/			{"x", "x", "x", "x", "x", "x", "x", "x", "x", "x"},	
			};
	
	private final String[][] moveIL18 = {
		    /* Hands:             2    3    4    5    6    7    8    9    10   A  */
			/*4-7 */			{"x", "x", "x", "x", "x", "x", "x", "x", "x", "x" },
			/* 8  */			{"x", "x", "x", "x", "x", "x", "x", "x", "x", "x" },
			/* 9  */			{"dh","x", "x", "x", "x", "dh","x", "x", "x", "x" },
			/* 10 */			{"x", "x", "x", "x", "x", "x", "x", "x", "dh","dh"},
			/* 11 */			{"x", "x", "x", "x", "x", "x", "x", "x", "x", "h" },
			/* 12 */			{"s", "s", "h" ,"h", "h", "x", "x", "x", "x", "x" },
			/* 13 */			{"h", "h", "x", "x", "x", "x", "x", "x", "x", "x" },
			/* 14 */			{"x", "x", "x", "x", "x", "x", "x", "x", "x", "x" },
			/* 15 */			{"x", "x", "x", "x", "x", "x", "x", "x", "s", "x" },
			/* 16 */			{"x", "x", "x", "x", "x", "x", "x", "s", "s", "x" },
			/* 17 */			{"x", "x", "x", "x", "x", "x", "x", "x", "x", "x" },
			/* 18+*/			{"x", "x", "x", "x", "x", "x", "x", "x", "x", "x" },	
			};
	
	public Illustrious18() {
		super();
		this.count = 0;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	@Override
	public String getDecision(Deck deck, Card card) {
		if(deck.size() == 2) {
			if(deck.getCard(0).getBlackJackVal() == 10 &&
					deck.getCard(1).getBlackJackVal() == 10) {
				if(card.getBlackJackVal() == 5 || card.getBlackJackVal() == 6) {
					return "s";
				}
			}
		}
		boolean soft = super.isSoft(deck);
		boolean split = super.isSplit(deck);
		if(!soft && !split ) {
			int row = super.getHardRow(super.handPts(deck));
			int col = super.getCol(card);
			String decision = this.hardIL18[row][col];
			if(!decision.equals("x")) {
				int index = Integer.parseInt(decision);
				if(this.count >= index) {
					return this.moveIL18[row][col];
				} else {
					return super.getDecision(deck, card);
				}
			}
		}
		return super.getDecision(deck, card);
	}
	
}

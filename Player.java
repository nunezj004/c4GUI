package c4GUI;

public class Player {
	
	private String playerSymbol;
	
	public Player(){
		
		playerSymbol = " ";
		
	}
	
	public Player(String symbol) {
		
		playerSymbol = symbol;
		
	}
	
	public String getSymbol(){
		return playerSymbol;
	}
}

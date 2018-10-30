package c4GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class connect4GUI extends JFrame{
	
	private	JPanel jpMain;
	BtnPanel BtnRow;
	GameBoard jpBoard;
	int[] nextRowAvail  =  {5 ,5, 5, 5, 5, 5, 5};// = new int[7];
	//nextRowAvail.... initialize so all values are 5 , in actionPerformed.. decrement the matching slot
	//use that to set the row col with the marker
	private static Player currPlayer;
	private static Player player1;
	private static Player player2;
	
	public connect4GUI() {
		player1 = new Player("R");
		player2 = new Player("B");
		currPlayer = player1;
		
		jpMain = new JPanel();
		jpMain.setLayout(new BorderLayout());
		jpMain.setBackground(Color.WHITE);
		
		jpBoard = new GameBoard();
		BtnRow = new BtnPanel();
		
		jpMain.add(BorderLayout.NORTH,BtnRow);
		jpMain.add(BorderLayout.CENTER, jpBoard);
		
		
		add(jpMain);
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public static class GameBoard extends JPanel implements gameBoardInterface, gamePlayerInterface{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private int ROWS = 6;
		private int COLS = 7;
		private static JTextField [][] board;
		
		public GameBoard() {
			setLayout(new GridLayout(ROWS,COLS));
			board = new JTextField[ROWS][COLS];
			displayBoard();
		}

		@Override
		public void displayBoard() {
			for(int row=0; row<board.length; row++){
				for(int col=0; col<board[row].length; col++){
					board[row][col] = new JTextField();
					Font bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 40);
					board[row][col].setFont(bigFont);
					board[row][col].setEnabled(false);
					board[row][col].setText(" ");
					add(board[row][col]);	
				}
			}
		}


		@Override
		public void clearBoard() {
			for(int row=0; row<board.length; row++){
				for(int col=0; col<board[row].length; col++){
					board[row][col].setText(" ");
				}
			}
			
		}

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isFull() {
			for(int row=0; row < board.length; row++){
				for(int col=0; col< board[row].length; col++){
					String cellContent = board[row][col].getText().trim();
					if(cellContent.isEmpty()){
						return false;
					}
				}
			}
			
			return true;
		}

		@Override
		public boolean isWinner() {
			System.out.println("INSIDE isWinner()");
			if(isWinnerInRow() || isWinnerInCol() || isWinnerInMainDiagonal() /*|| isWinnerInSecondaryDiagonal()*/){
				return true;
			}
			
			return false;
			
		}
		
		public boolean isWinnerInRow(){

			System.out.println("INSIDE isWinnerInRow()");
			String symbol = currPlayer.getSymbol();
			for(int row=0; row < ROWS; row++){
				int numMatchesInRow = 0;
				for(int col=0; col< COLS; col++){
					if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
						numMatchesInRow++;
					}
					else {
						numMatchesInRow = 0;
					}
					if(numMatchesInRow == 4){
						return true;
					}
				}
				
			}
			return false;
			
		}
		
		public boolean isWinnerInCol(){
			System.out.println("INSIDE isWinnerInCol()");
			String symbol = currPlayer.getSymbol();
			for(int col=0; col < COLS; col++){
				int numMatchesInCol = 0; 
				for(int row=0; row< ROWS; row++){
					if( board[row][col].getText().trim().equalsIgnoreCase(symbol)){
						numMatchesInCol++;
					}
					else {
						numMatchesInCol = 0;
					}
					if(numMatchesInCol == 4){
						return true;
					}
				}
				
			}
			return false;
		}
		
		public boolean isWinnerInSecondaryDiagonal(){

			System.out.println("INSIDE isWinnerSecDiag()");
			String symbol = currPlayer.getSymbol();
			for(int rowStart = 3; rowStart < ROWS; rowStart++) {
				int matches = 0;
				int row = rowStart; 
				int col=0;
				while(row >-1 && col < 7){
					if(board[row][col].getText().trim().equalsIgnoreCase(symbol)){
						matches++;
					}
					else matches = 0;
					}
					row--;
					col++;
				
				if(matches == 4){
					return true;
				}	
			}
			return false;
			
		}
		
		public boolean isWinnerInMainDiagonal(){

			System.out.println("INSIDE isWinnerInMainDiag()");
			String symbol = currPlayer.getSymbol();
			int matches = 0;
			for(int i=0; i< board.length; i++){
				if(board[i][i].getText().trim().equalsIgnoreCase(symbol)){
					matches++;
				}
				else {
					matches = 0;
				}
			}
			if(matches == 4){
				return true; 
			}
		return false;
		}

		@Override
		public void takeTurn() {
			if(currPlayer.equals(player1)){
				currPlayer = player2;
			}
			else{
				currPlayer = player1;
			}
			
		}

	}
	
	public class BtnPanel extends JPanel implements displayPanel, ActionListener{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JButton[] choice;
		private final int ROWS = 1;
		private final int COLS = 7;
		public BtnPanel() {
			setLayout(new GridLayout(ROWS,COLS));
			choice = new JButton[7];
			displayBtnPanel();
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {//got help from raymond
			JButton btnClicked = (JButton) e.getSource();
//			System.out.println(btnClicked.toString());
			for(int btnI=0; btnI<COLS; btnI++) {
				if(btnClicked.equals(choice[btnI])){
					System.out.println("BTN INDEX = "+ btnI);
					for(int row=0; row<ROWS; row++) {
						if(GameBoard.board[nextRowAvail[btnI]][btnI].getText().equals(" ")) {
							GameBoard.board[nextRowAvail[btnI]][btnI].setText(currPlayer.getSymbol());
							nextRowAvail[btnI] = nextRowAvail[btnI] -1;
						}
				
					}
					
				}
				
				
			}
			
			//check winner
			
//			
//		
			if(jpBoard.isWinner()) {
			
				JOptionPane.showMessageDialog(null,currPlayer.getSymbol() + " " + "is the winner!!");
				int answer = JOptionPane.showConfirmDialog(null, "would yu like to play again?");
				if(answer == JOptionPane.YES_OPTION) {
					jpBoard.clearBoard();
				}
				else if(answer == JOptionPane.NO_OPTION){
				System.exit(0);
				}
			}
			if(jpBoard.isFull()) {
				JOptionPane.showMessageDialog(null, "DRAW");
				int answer = JOptionPane.showConfirmDialog(null, "would yu like to play again?");
				if(answer == JOptionPane.YES_OPTION) {
					jpBoard.clearBoard();
				}
				else if(answer == JOptionPane.NO_OPTION){
				System.exit(0);
				}
			}
			else {
				System.out.println("DUNNO WHY I FELL IN THIS ELSE");
			}

			jpBoard.takeTurn();//swap player just before exiting method to prepare for next turn
		
			
			
			/*
			 * 
//			if(btnClicked.equals(choice[0])) {
////				System.out.println("INSIDED "+choice[0]);
//				if(GameBoard.board[5][0].getText().equals(" ")) {
//					GameBoard.board[5][0].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[4][0].getText().equals(" ")) {
//					GameBoard.board[4][0].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[3][0].getText().equals(" ")) {
//					GameBoard.board[3][0].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[2][0].getText().equals(" ")) {
//					GameBoard.board[2][0].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[1][0].getText().equals(" ")) {
//					GameBoard.board[1][0].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[0][0].getText().equals(" ")) {
//					GameBoard.board[0][0].setText(currPlayer.getSymbol());
//				}
//				
//			}
//			else if(btnClicked.equals(choice[1])) {
//				if(GameBoard.board[5][1].getText().equals(" ")) {
//					GameBoard.board[5][1].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[4][1].getText().equals(" ")) {
//					GameBoard.board[4][1].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[3][1].getText().equals(" ")) {
//					GameBoard.board[3][1].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[2][1].getText().equals(" ")) {
//					GameBoard.board[2][1].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[1][1].getText().equals(" ")) {
//					GameBoard.board[1][1].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[0][1].getText().equals(" ")) {
//					GameBoard.board[0][1].setText(currPlayer.getSymbol());
//				}
//				
//			}
//			else if(btnClicked.equals(choice[2])) {
//				if(GameBoard.board[5][2].getText().equals(" ")) {
//					GameBoard.board[5][2].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[4][2].getText().equals(" ")) {
//					GameBoard.board[4][2].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[3][2].getText().equals(" ")) {
//					GameBoard.board[3][2].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[2][2].getText().equals(" ")) {
//					GameBoard.board[2][2].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[1][2].getText().equals(" ")) {
//					GameBoard.board[1][2].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[0][2].getText().equals(" ")) {
//					GameBoard.board[0][2].setText(currPlayer.getSymbol());
//				}
//				
//			}
//			else if(btnClicked.equals(choice[3])) {
//				if(GameBoard.board[5][3].getText().equals(" ")) {
//					GameBoard.board[5][3].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[4][3].getText().equals(" ")) {
//					GameBoard.board[4][3].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[3][3].getText().equals(" ")) {
//					GameBoard.board[3][3].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[2][3].getText().equals(" ")) {
//					GameBoard.board[2][3].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[1][3].getText().equals(" ")) {
//					GameBoard.board[1][3].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[0][3].getText().equals(" ")) {
//					GameBoard.board[0][3].setText(currPlayer.getSymbol());
//				}
//				
//			}
//			else if(btnClicked.equals(choice[4])) {
//				if(GameBoard.board[5][4].getText().equals(" ")) {
//					GameBoard.board[5][4].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[4][4].getText().equals(" ")) {
//					GameBoard.board[4][4].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[3][4].getText().equals(" ")) {
//					GameBoard.board[3][4].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[2][4].getText().equals(" ")) {
//					GameBoard.board[2][4].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[1][4].getText().equals(" ")) {
//					GameBoard.board[1][4].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[0][4].getText().equals(" ")) {
//					GameBoard.board[0][4].setText(currPlayer.getSymbol());
//				}
//				
//			}
//			else if(btnClicked.equals(choice[5])) {
//				if(GameBoard.board[5][5].getText().equals(" ")) {
//					GameBoard.board[5][5].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[4][5].getText().equals(" ")) {
//					GameBoard.board[4][5].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[3][5].getText().equals(" ")) {
//					GameBoard.board[3][5].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[2][5].getText().equals(" ")) {
//					GameBoard.board[2][5].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[1][5].getText().equals(" ")) {
//					GameBoard.board[1][5].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[0][5].getText().equals(" ")) {
//					GameBoard.board[0][5].setText(currPlayer.getSymbol());
//				}
//				
//			}
//			else if(btnClicked.equals(choice[6])) {
//				if(GameBoard.board[5][6].getText().equals(" ")) {
//					GameBoard.board[5][6].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[4][6].getText().equals(" ")) {
//					GameBoard.board[4][6].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[3][6].getText().equals(" ")) {
//					GameBoard.board[3][6].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[2][6].getText().equals(" ")) {
//					GameBoard.board[2][6].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[1][6].getText().equals(" ")) {
//					GameBoard.board[1][6].setText(currPlayer.getSymbol());
//				}
//				else if(GameBoard.board[0][6].getText().equals(" ")) {
//					GameBoard.board[0][6].setText(currPlayer.getSymbol());
//				}
//				
//			}
//			
 * 
 */
		}//end of actionPerformed method

		@Override
		public void displayBtnPanel() {
			for(int col=0; col < choice.length; col++){
				choice[col] = new JButton();
				Font bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 40);
				choice[col].setFont(bigFont);
				choice[col].setEnabled(true);
				choice[col].addActionListener(this);
				add(choice[col]);	
			
		}
		
		}
		
	}
	
		
		
		
	}


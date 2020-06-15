package robot;

import java.util.ArrayList;
import com.fry.tetris.Cell;
import com.fry.tetris.Tetris;

public class Robot1{
	private int width;
	private int height;
	private Tetris t1;
	private int[] ScoreRow ;
	private int ScoreSide = 100;
	private int ScoreUP = 5000;
	private int Scoreblock = 1000;
	private int checkBoard = 0;
	public ArrayList<Integer> HandlerList = new ArrayList<Integer>();
	public Robot1(int height, int width) {
		super();
		this.width = width;
		this.height = height;
		ScoreRow = new int[height];
		setScoreRow();
	}
	public void test_print(int a) {
		System.out.println(a);
	}
	
	public void calcWay(Tetris tetris) {
		HandlerList.clear();
		int minScore = 0x7FFFFFFF;
		int handler = 0;
		int k = 0;
		int typetimes = 0;
		for(int i= -width+1;i<width;i++) {
			t1 = tetris.copy();
//			t1 = tetris;
			int type = t1.getTetromino().getStates().length;
			for(int t = 0; t<type;t++) {
//				System.out.println(t);
//				System.out.println("-------");
//				System.out.println();
				k=i;
				t1 = tetris.copy();
//				t1.getTetromino().getCells()[1].showCell();
//				System.out.println();
				Cell[] cells = t1.getTetromino().getCells();
				for(int m=0; m<cells.length; m++){
					Cell cell = cells[m];
					int row = cell.getRow();
					int col = cell.getCol();
					if(row == 19 || col == 0) {
						checkBoard = 1;
//						System.out.println("checkBoardFirst is ="+ checkBoard);
						break;
					}
				}
//				System.out.println("checkBoard is ="+ checkBoard);
				if(checkBoard == 0) {
					for(int kt = 0; kt<t; kt++) {
//						System.out.println(kt);
						t1.handler(0);
					}
				}
//				t1.getTetromino().getCells()[1].showCell();
//				System.out.println();
				while(k!=0){
					if(k<0) {
						t1.handler(3);
						k++;
					}
					else {
						t1.handler(1);					
						k--;
					}
				}
//				t1.getTetromino().getCells()[1].showCell();
//				System.out.println();
				t1.handler(2);
				int Score = calcScore();
				checkBoard = 0;
//				System.out.println(Score);
//				System.out.println("-------------------------------------------------");
				if(minScore>Score) {
//					System.out.println(minScore);
//					System.out.println(i);
//					System.out.println(t);
					minScore = Score;
					handler = i;
					typetimes = t;
				}
			}
		}

		for(int i=0;i<typetimes;i++) {
			HandlerList.add(0);
		}
		if(handler<0) {
			HandlerList.add(3);
//			System.out.println("haha");
		}
		else if(handler>0) {
			HandlerList.add(1);
		}

	}
	private int calcScore() {
		int score = 0;
		
		for(int i=0;i<height;i++) {
			for(int j=0;j<width;j++) {
				if(t1.wall[i][j]!=null) {
					score += Scoreblock-ScoreRow[i];
//					System.out.println(score);
				}
				else if(t1.wall[i][j]==null) {
					//score += ScoreRow[i];
					score += getsurroundScore(i, j);
//					System.out.println(score);
				}
			}
		}
		return score;
	}
	
	private void setScoreRow() {
		for(int i=0;i<height;i++) {
			ScoreRow[i] = i*10;
		}
	}
	
	private int getsurroundScore(int y,int x) {
		int score = 0;
		if(x==0) {
			score += ScoreSide;
			if(t1.wall[y][x+1]!=null) {
				score += ScoreSide;
			}
			
		}
		else if(x==(width-1)) {
			score += ScoreSide;
			if(t1.wall[y][x-1]!=null) {
				score += ScoreSide;
			}
		}
		else {
			if(t1.wall[y][x-1]!=null) {
				score += ScoreSide;
			}
			if(t1.wall[y][x+1]!=null) {
				score += ScoreSide;
			}
		}
		
		if(y!=0&&t1.wall[y-1][x]!=null) {
			score += ScoreUP;
		}
		
		return score;
	}
	
}

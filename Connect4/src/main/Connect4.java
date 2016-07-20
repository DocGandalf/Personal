package main;

public class Connect4{
	public static int[][] board={
			{1,2,1,0,0,1,2,1},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
	};
	public static int[][] winBoard={
			{1,0,1,0,1,0,1,0},
			{1,0,1,0,1,0,1,0},
			{0,1,0,1,0,0,1,0},
			{1,1,0,0,1,0,0,0},
			{1,0,1,0,1,0,0,0},
			{1,0,0,1,1,0,0,0},
	};
	public static int turn=1;
	
	public static void main(String[] args) {
		int col;
		for(col=0; col<8; col++){
			if(whoIsHere(0, col)==0)break;
		}
		board[0][col]=turn;
		boolean leave=false;
		while(!leave){
			//updateLine(board[0])
			switch(getEvent()){
				case 0:
					
			}
			
		}
		//Also drops the chip
		
		if(turn==1)turn=2;else turn=1;
	}
	
	public static int getEvent(){
		
		return 0;
	}
	
	public static void updateBoard(){
		
	}
	
	public static int checkWin(){
		for(int row=0; row<6; row++){
			for(int col=0; col<8; col++){
				int player=whoIsHere(row, col);
				if(player!=0){
					for(int x=-1; x<2; x++){
						for(int y=-1; y<2; y++){
							if(isOnBoard(row+x, col+y)&&!(x==0&&y==0)){
								if(player==whoIsHere(row+x, col+y)){
									int count=1;
									int xA;
									int yA;
									do{
										if(count==3)return player;
										count++;
										xA=x*(count);
										yA=y*(count);
										if(!isOnBoard(row+xA, col+yA))break;
									}while(player==whoIsHere(row+xA, col+yA));
								}
							}
						}
					}
				}
			}
		}
		return 0;
	}
	
	public static int whoIsHere(int row,int col){
		return board[row][col];
	}
	
	public static boolean isOnBoard(int row,int col){
		return (row>=0&&row<6&&col>=0&&col<8);
	}
}
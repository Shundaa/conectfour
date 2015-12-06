package Connect_Four;

class Board{
    char[][] board = new char[6][7];
    
    public Board(){
        board = new char[][]{
            {'*','*','*','*','*','*','*',},
            {'*','*','*','*','*','*','*',},
            {'*','*','*','*','*','*','*',},
            {'*','*','*','*','*','*','*',},
            {'*','*','*','*','*','*','*',},
            {'*','*','*','*','*','*','*',},    
        };
    } 
    
    public boolean isLegalMove(int column){
        return board[0][column]=='*';
    }
    
    //Placing a Move on the board
    public boolean placeMove(int column, int player){ 
        char cplayer;
        if(player==1)
            cplayer='O';
        else
            cplayer='X';
        if(!isLegalMove(column)) {System.out.println("Jogada Invalida1"); return false;}
        for(int i=5;i>=0;--i){
            if(board[i][column] == '*') {
                board[i][column] = cplayer;
                return true;
            }
        }
        return false;
    }
    
    public void undoMove(int column){
        for(int i=0;i<=5;++i){
            if(board[i][column] != '*') {
                board[i][column] = '*';
                break;
            }
        }        
    }
    //Printing the board
    public void displayBoard(){
        System.out.println();
        for(int i=0;i<=5;++i){
            for(int j=0;j<=6;++j){
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
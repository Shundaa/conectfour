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
    
    public boolean JogadaValida(int coluna){
        return board[0][coluna]=='*';
    }
    
    //Faz a jogada no Tabuleiro
    public boolean FazerJogada(int coluna, int player){ 
        char cplayer;
        if(player==1)
            cplayer='O';
        else
            cplayer='X';
        if(!JogadaValida(coluna)) {System.out.println("Jogada Invalida1"); return false;}
        for(int i=5;i>=0;--i){
            if(board[i][coluna] == '*') {
                board[i][coluna] = cplayer;
                return true;
            }
        }
        return false;
    }
    
    public void DesfazJogada(int coluna){
        for(int i=0;i<=5;++i){
            if(board[i][coluna] != '*') {
                board[i][coluna] = '*';
                break;
            }
        }        
    }
    //Printa o tabuleiro
    public void PrintaTabuleiro(){
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
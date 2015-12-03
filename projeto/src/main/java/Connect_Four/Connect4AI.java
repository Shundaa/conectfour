
package Connect_Four;
import java.util.Scanner;

public class Connect4AI { 
    private Board b;
    private Scanner scan;
    private int nextMoveLocation=-1;
    private int maxDepth = 8;
    
    public Connect4AI(Board b){
        this.b = b;
        scan = new Scanner(System.in);
    }
    
    
    
    //Opponent's turn
    
    
    public void letOpponentMove(){
        System.out.println("Sua vez (1-7): ");
        int move = scan.nextInt();
        while(move<1 || move > 7 || !b.isLegalMove(move-1)){
            System.out.println("Jogada Invalida.\n\nSua vez (1-7): "); 
            move = scan.nextInt();
        }
        
        //Assume 2 is the opponent
        b.placeMove(move-1, 2); 
    }
    
    
    
    //Game Result
    public int gameResult(Board b){
        int aiScore = 0, humanScore = 0;
        for(int i=5;i>=0;--i){
            for(int j=0;j<=6;++j){
                if(b.board[i][j]=='*') continue;
                
                //Checking cells to the right
                if(j<=3){
                    for(int k=0;k<4;++k){ 
                            if(b.board[i][j+k]=='O') aiScore++;
                            else if(b.board[i][j+k]=='X') humanScore++;
                            else break; 
                    }
                    if(aiScore==4)return 1; else if (humanScore==4)return 2;
                    aiScore = 0; humanScore = 0;
                } 
                
                //Checking cells up
                if(i>=3){
                    for(int k=0;k<4;++k){
                            if(b.board[i-k][j]=='O') aiScore++;
                            else if(b.board[i-k][j]=='X') humanScore++;
                            else break;
                    }
                    if(aiScore==4)return 1; else if (humanScore==4)return 2;
                    aiScore = 0; humanScore = 0;
                } 
                
                //Checking diagonal up-right
                if(j<=3 && i>= 3){
                    for(int k=0;k<4;++k){
                        if(b.board[i-k][j+k]=='O') aiScore++;
                        else if(b.board[i-k][j+k]=='X') humanScore++;
                        else break;
                    }
                    if(aiScore==4)return 1; else if (humanScore==4)return 2;
                    aiScore = 0; humanScore = 0;
                }
                
                //Checking diagonal up-left
                if(j>=3 && i>=3){
                    for(int k=0;k<4;++k){
                        if(b.board[i-k][j-k]=='O') aiScore++;
                        else if(b.board[i-k][j-k]=='X') humanScore++;
                        else break;
                    } 
                    if(aiScore==4)return 1; else if (humanScore==4)return 2;
                    aiScore = 0; humanScore = 0;
                }  
            }
        }
        
        for(int j=0;j<7;++j){
            //Game has not ended yet
            if(b.board[0][j]=='*')return -1;
        }
        //Game draw!
        return 0;
    }
    
    int calculateScore(int aiScore, int moreMoves){   
        int moveScore = 4 - moreMoves;
        if(aiScore==0)return 0;
        else if(aiScore==1)return 1*moveScore;
        else if(aiScore==2)return 10*moveScore;
        else if(aiScore==3)return 100*moveScore;
        else return 1000;
    }
    
    //Evaluate board favorableness for AI
    public int evaluateBoard(Board b){
      
        int aiScore=1;
        int score=0;
        int blanks = 0;
        int k=0, moreMoves=0;
        for(int i=5;i>=0;--i){
            for(int j=0;j<=6;++j){
                
                if(b.board[i][j]=='*' || b.board[i][j]=='X') continue; 
                
                if(j<=3){ 
                    for(k=1;k<4;++k){
                        if(b.board[i][j+k]=='O')aiScore++;
                        else if(b.board[i][j+k]=='X'){aiScore=0;blanks = 0;break;}
                        else blanks++;
                    }
                     
                    moreMoves = 0; 
                    if(blanks>0) 
                        for(int c=1;c<4;++c){
                            int column = j+c;
                            for(int m=i; m<= 5;m++){
                             if(b.board[m][column]=='*')moreMoves++;
                                else break;
                            } 
                        } 
                    
                    if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                    aiScore=1;   
                    blanks = 0;
                } 
                
                if(i>=3){
                    for(k=1;k<4;++k){
                        if(b.board[i-k][j]=='O')aiScore++;
                        else if(b.board[i-k][j]=='X'){aiScore=0;break;} 
                    } 
                    moreMoves = 0; 
                    
                    if(aiScore>0){
                        int column = j;
                        for(int m=i-k+1; m<=i-1;m++){
                         if(b.board[m][column]=='*')moreMoves++;
                            else break;
                        }  
                    }
                    if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                    aiScore=1;  
                    blanks = 0;
                }
                 
                if(j>=3){
                    for(k=1;k<4;++k){
                        if(b.board[i][j-k]=='O')aiScore++;
                        else if(b.board[i][j-k]=='X'){aiScore=0; blanks=0;break;}
                        else blanks++;
                    }
                    moreMoves=0;
                    if(blanks>0) 
                        for(int c=1;c<4;++c){
                            int column = j- c;
                            for(int m=i; m<= 5;m++){
                             if(b.board[m][column]=='*')moreMoves++;
                                else break;
                            } 
                        } 
                    
                    if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                    aiScore=1; 
                    blanks = 0;
                }
                 
                if(j<=3 && i>=3){
                    for(k=1;k<4;++k){
                        if(b.board[i-k][j+k]=='O')aiScore++;
                        else if(b.board[i-k][j+k]=='X'){aiScore=0;blanks=0;break;}
                        else blanks++;                        
                    }
                    moreMoves=0;
                    if(blanks>0){
                        for(int c=1;c<4;++c){
                            int column = j+c, row = i-c;
                            for(int m=row;m<=5;++m){
                                if(b.board[m][column]=='X')moreMoves++;
                                else if(b.board[m][column]=='O');
                                else break;
                            }
                        } 
                        if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                        aiScore=1;
                        blanks = 0;
                    }
                }
                 
                if(i>=3 && j>=3){
                    for(k=1;k<4;++k){
                        if(b.board[i-k][j-k]=='O')aiScore++;
                        else if(b.board[i-k][j-k]=='X'){aiScore=0;blanks=0;break;}
                        else blanks++;                        
                    }
                    moreMoves=0;
                    if(blanks>0){
                        for(int c=1;c<4;++c){
                            int column = j-c, row = i-c;
                            for(int m=row;m<=5;++m){
                                if(b.board[m][column]=='*')moreMoves++;
                                else if(b.board[m][column]=='O');
                                else break;
                            }
                        } 
                        if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                        aiScore=1;
                        blanks = 0;
                    }
                } 
            }
        }
        return score;
    } 
    
    public int minimax(int depth, int turn){
        int gameResult = gameResult(b);
        if(gameResult==1)return Integer.MAX_VALUE;
        else if(gameResult==2)return Integer.MIN_VALUE;
        else if(gameResult==0)return 0;
        
        if(depth==maxDepth)return evaluateBoard(b);
        
        int maxScore=Integer.MIN_VALUE, minScore = Integer.MAX_VALUE;
        for(int j=0;j<=6;++j){
            if(!b.isLegalMove(j)) continue;
                
            if(turn==1){
                    b.placeMove(j, 1);
                    int currentScore = minimax(depth+1, 2);
                    maxScore = Math.max(currentScore, maxScore);
                    if(depth==0){
                        //System.out.println("pontuacao das borad "+j+" = "+currentScore);
                        if(maxScore==currentScore) nextMoveLocation = j;
                    }
            }else if(turn==2){
                    b.placeMove(j, 2);
                    int currentScore = minimax(depth+1, 1);
                    minScore = Math.min(currentScore, minScore);
            }
            b.undoMove(j);
        }
        if(turn==1)
            return maxScore;
        else
            return minScore;
    }
    
    public int getAIMove(){
        nextMoveLocation = -1;
        minimax(0, 1);
        return nextMoveLocation;
    }
    
    public void playAgainstAIConsole(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Voce quer comecar? (sim,nao) ");
        String answer = scan.next().trim();
        
        if(answer.equalsIgnoreCase("sim")) 
            letOpponentMove();
        b.displayBoard();
        b.placeMove(3, 1);
        b.displayBoard();
        
        while(true){ 
            letOpponentMove();
            b.displayBoard();
            
            int gameResult = gameResult(b);
            if(gameResult==1){System.out.println("Computador ganhou!");break;}
            else if(gameResult==2){System.out.println("Voce ganhou!");break;}
            else if(gameResult==0){System.out.println("Empate!");break;}
            
            b.placeMove(getAIMove(), 1);
            b.displayBoard();
            gameResult = gameResult(b);
            if(gameResult==1){System.out.println("Computador ganhou!");break;}
            else if(gameResult==2){System.out.println("Voce ganhou!");break;}
            else if(gameResult==0){System.out.println("Empate!");break;}
        }
        
    }
    
    public static void main(String[] args) {
        Board b = new Board();
        Connect4AI ai = new Connect4AI(b);  
        ai.playAgainstAIConsole();
    }
}
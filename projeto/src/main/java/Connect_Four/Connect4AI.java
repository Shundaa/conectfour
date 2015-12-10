
package Connect_Four;
import java.util.Random;
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
    
    public static void main(String[] args) {
        Board b = new Board();
        Connect4AI ai = new Connect4AI(b);
        ai.JogarContraAI();
    }
    
    //Vez do jogar "humano"
    
    
    public void JogadaOponente(){
        System.out.println("Sua vez (1-7): ");
        int move = scan.nextInt();
        while(move<1 || move > 7 || !b.JogadaValida(move-1)){
            System.out.println("Jogada Invalida.\n\nSua vez (1-7): "); 
            move = scan.nextInt();
        }
        
        //Faz a jogada
        //int move=(JogadaAI());
        b.FazerJogada(move-1, 2);
    }
    
    
    
    //Resultado do jogo
    public int FimDeJogo(Board b){
        int aiScore = 0, humanScore = 0;
        for(int i=5;i>=0;--i){
            for(int j=0;j<=6;++j){
                if(b.board[i][j]=='*') 
                    continue;
                
                //Olha a linha pra direita
                if(j<=3){
                    for(int k=0;k<4;++k){ 
                            if(b.board[i][j+k]=='O') 
                                aiScore++;
                            else if(b.board[i][j+k]=='X')
                                humanScore++;
                            else 
                                break; 
                    }
                    if(aiScore==4)
                        return 1;
                    else if (humanScore==4)
                        return 2;
                    aiScore = 0; 
                    humanScore = 0;
                } 
                
                //Olha a coluna pra cima
                if(i>=3){
                    for(int k=0;k<4;++k){
                            if(b.board[i-k][j]=='O') 
                                aiScore++;
                            else if(b.board[i-k][j]=='X')
                                humanScore++;
                            else 
                                break;
                    }
                    if(aiScore==4)
                        return 1; 
                    else if (humanScore==4)
                        return 2;
                    aiScore = 0; 
                    humanScore = 0;
                } 
                
                //Olha pra diagonal Cima-Direita
                if(j<=3 && i>= 3){
                    for(int k=0;k<4;++k){
                        if(b.board[i-k][j+k]=='O') 
                            aiScore++;
                        else if(b.board[i-k][j+k]=='X')
                            humanScore++;
                        else 
                            break;
                    }
                    if(aiScore==4)
                        return 1; 
                    else if (humanScore==4)
                        return 2;
                    aiScore = 0;
                    humanScore = 0;
                }
                
                //Olha pra diagonal Cima-Esquerda
                if(j>=3 && i>=3){
                    for(int k=0;k<4;++k){
                        if(b.board[i-k][j-k]=='O') 
                            aiScore++;
                        else if(b.board[i-k][j-k]=='X')
                            humanScore++;
                        else 
                            break;
                    } 
                    if(aiScore==4)
                        return 1;
                    else if (humanScore==4)
                        return 2;
                    aiScore = 0; 
                    humanScore = 0;
                }  
            }
        }
        
        for(int j=0;j<7;++j){
            //Jogo nao termino
            if(b.board[0][j]=='*')
                return -1;
        }
        //Empate
        return 0;
    }
    
    int CalculaPontuacao(int aiScore, int moreMoves){
        int moveScore = maxDepth/2 - moreMoves;
        if(aiScore==0)
            return 0;
        else if(aiScore==1)
            return 1*moveScore;
        else if(aiScore==2)
            return 10*moveScore;
        else if(aiScore==3)
            return 100*moveScore;
        else return 1000;
    }
    
    //Calcula a pontuacao do Tabuleiro para a AI
    public int PontuacaoTabuleiro(Board b){
      
        int aiScore=1;
        int score=0;
        int blanks = 0;
        int k=0, moreMoves=0;
        for(int i=5;i>=0;--i){
            for(int j=0;j<=6;++j){
                
                if(b.board[i][j]=='*' || b.board[i][j]=='X') continue; 
                //direita
                if(j<=3){
                    for(k=1;k<4;++k){
                        if(b.board[i][j+k]=='O')
                            aiScore++;
                        else if(b.board[i][j+k]=='X'){
                            aiScore=0;
                            blanks = 0;
                            break;
                        }
                        else blanks++;
                    }
                     
                    moreMoves = 0; 
                    if(blanks>0) 
                        for(int c=1;c<4;++c){
                            int coluna = j+c;
                            for(int m=i; m<= 5;m++){
                             if(b.board[m][coluna]=='*')
                                 moreMoves++;
                             else
                                break;
                            } 
                        } 
                    
                    if(moreMoves!=0) 
                        score += CalculaPontuacao(aiScore, moreMoves);
                    aiScore=1;   
                    blanks = 0;
                } 
                //cima
                if(i>=3){
                    for(k=1;k<4;++k){
                        if(b.board[i-k][j]=='O')
                            aiScore++;
                        else if(b.board[i-k][j]=='X'){
                            aiScore=0;
                            break;
                        } 
                    } 
                    moreMoves = 0; 
                    
                    if(aiScore>0){
                        int coluna = j;
                        for(int m=i-k+1; m<=i-1;m++){
                            if(b.board[m][coluna]=='*')
                                moreMoves++;
                            else 
                                break;
                        }  
                    }
                    if(moreMoves!=0)
                        score += CalculaPontuacao(aiScore, moreMoves);
                    aiScore=1;  
                    blanks = 0;
                }
                //esquerda
                if(j>=3){
                    for(k=1;k<4;++k){
                        if(b.board[i][j-k]=='O')
                            aiScore++;
                        else if(b.board[i][j-k]=='X'){
                            aiScore=0;
                            blanks=0;
                            break;
                        }
                        else
                            blanks++;
                    }
                    moreMoves=0;
                    if(blanks>0) 
                        for(int c=1;c<4;++c){
                            int coluna = j- c;
                            for(int m=i; m<= 5;m++){
                                if(b.board[m][coluna]=='*')
                                    moreMoves++;
                                else
                                    break;
                            } 
                        } 
                    
                    if(moreMoves!=0) 
                        score += CalculaPontuacao(aiScore, moreMoves);
                    aiScore=1; 
                    blanks = 0;
                }
                 //cima direita
                if(j<=3 && i>=3){
                    for(k=1;k<4;++k){
                        if(b.board[i-k][j+k]=='O')aiScore++;
                        else if(b.board[i-k][j+k]=='X'){aiScore=0;blanks=0;break;}
                        else blanks++;                        
                    }
                    moreMoves=0;
                    if(blanks>0){
                        for(int c=1;c<4;++c){
                            int coluna = j+c, row = i-c;
                            for(int m=row;m<=5;++m){
                                if(b.board[m][coluna]=='X')
                                    moreMoves++;
                                else if(b.board[m][coluna]=='O');
                                else
                                    break;
                            }
                        } 
                        if(moreMoves!=0) 
                            score += CalculaPontuacao(aiScore, moreMoves);
                        aiScore=1;
                        blanks = 0;
                    }
                }
                 //cima esquerda
                if(i>=3 && j>=3){
                    for(k=1;k<4;++k){
                        if(b.board[i-k][j-k]=='O')
                            aiScore++;
                        else if(b.board[i-k][j-k]=='X'){
                            aiScore=0;
                            blanks=0;
                            break;
                        }
                        else 
                            blanks++;                        
                    }
                    moreMoves=0;
                    if(blanks>0){
                        for(int c=1;c<4;++c){
                            int coluna = j-c, row = i-c;
                            for(int m=row;m<=5;++m){
                                if(b.board[m][coluna]=='*')
                                    moreMoves++;
                                else if(b.board[m][coluna]=='O');
                                else 
                                    break;
                            }
                        } 
                        if(moreMoves!=0)
                            score += CalculaPontuacao(aiScore, moreMoves);
                        aiScore=1;
                        blanks = 0;
                    }
                } 
            }
        }
        return score;
    } 
    
    public int minimax(int depth, int turn){
        int FimDeJogo = FimDeJogo(b);
        if(FimDeJogo==1)                    //AI ganho entao max
            return Integer.MAX_VALUE;
        else if(FimDeJogo==2)               // Humano ganho entao min
            return Integer.MIN_VALUE;
        else if(FimDeJogo==0)               // empate da nada
            return 0;
        
        if(depth==maxDepth)
            return PontuacaoTabuleiro(b);
        
        int maxScore=Integer.MIN_VALUE, minScore = Integer.MAX_VALUE;
        for(int j=0;j<=6;++j){
            if(!b.JogadaValida(j)) 
                continue;
                
            if(turn==1){
                    b.FazerJogada(j, 1);
                    int currentScore = minimax(depth+1, 2);
                    maxScore = Math.max(currentScore, maxScore);
                    if(depth==0){
                        if(maxScore==currentScore) 
                            nextMoveLocation = j;
                    }
            }
            else if(turn==2){
                    b.FazerJogada(j, 2);
                    int currentScore = minimax(depth+1, 1);
                    minScore = Math.min(currentScore, minScore);
            }
            b.DesfazJogada(j);
        }
        if(turn==1)
            return maxScore;
        else
            return minScore;
    }
    
    public int JogadaAI(){
        nextMoveLocation = -1;
        minimax(0, 1);
        return nextMoveLocation;
    }
    
    public void JogarContraAI(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Voce quer comecar? (sim,nao) ");
        String answer = scan.next().trim();
        
        if(answer.equalsIgnoreCase("sim")) 
            JogadaOponente();
        b.PrintaTabuleiro();
        if(b.board[5][3]=='X'){ 
            Random gerador = new Random();
            int numero = gerador.nextInt(5);
            while(numero!=2&&numero!=4)
                numero = gerador.nextInt(5);
            b.FazerJogada(numero, 1);
            b.PrintaTabuleiro();
        }
        else{
            b.FazerJogada(3, 1);
            b.PrintaTabuleiro();
        }
        while(true){ 
            JogadaOponente();
            b.PrintaTabuleiro();
            
            int FimDeJogo = FimDeJogo(b);
            if(FimDeJogo==1){
                System.out.println("Computador ganhou!");
                break;
            }
            else if(FimDeJogo==2){
                System.out.println("Voce ganhou!");
                break;
            }
            else if(FimDeJogo==0){
                System.out.println("Empate!");
                break;
            }
            
            b.FazerJogada(JogadaAI(), 1);
            b.PrintaTabuleiro();
            FimDeJogo = FimDeJogo(b);
            
            if(FimDeJogo==1){
                System.out.println("Computador ganhou!");
                break;
            }
            else if(FimDeJogo==2){
                System.out.println("Voce ganhou!");
                break;
            }
            else if(FimDeJogo==0){
                System.out.println("Empate!");
                break;
            }
        }
        
    }
    
}
import app.Game.Game;

void main() {
    printHeader();
    int[] seq = Game.loadSeq();

    while (true){
        int[] userArr = Game.getUserArr();
        if (Game.compareArr(seq, userArr) == 5){
            Game.win();
            break;
        } else { Game.wrong(); }
    }
}

void printHeader(){
 System.out.print("""
Game: Who's Next 
Objective: Identify the Sequence of 5 numbers between 1 and 5 using the fewest turns. 
If you wish to quit guessing and give up, enter a ZERO for one of your guesses
and the game will display the solution and quit. 
 GOOD LUCK!!! 

        """);
}

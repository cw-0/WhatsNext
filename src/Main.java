import app.Game.Game;

void main() {
    printHeader();

    while (true){
        int[] userArr = Game.getUserArr();
        int correct = Game.compareArr(userArr);

        if (Game.QUITTING == true) { Game.quit(correct); }

        if (correct == 5){
            Game.win();
        } else { Game.wrongGuess(correct); }
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

package app.Game;
import java.util.*;

public class Game {
    public static boolean DEBUG = false;

    public static int turnCount = 0;
    public static boolean QUITTING = false;
    public static int[] sequence = loadSeq();

    public static int[] loadSeq(){
        int[] seq = {0, 0, 0, 0, 0};
        Set<Integer> usedIdx = new HashSet<>();

        Random r = new Random();


        for (int i = 1; i <= 5; i++){
            int idx;
            do {
                idx = r.nextInt(5);
            } while (usedIdx.contains(idx));

            seq[idx] = i;
            usedIdx.add(idx);
        }

        if (DEBUG) { System.out.println("SEQ: " + Arrays.toString(seq)); }
        return seq;
    } // End load seq


    public static int[] getUserArr(){
        Game.turnCount++;
        boolean invalidSeq = true;

        Scanner scanner = new Scanner(System.in);
        int[] userArr = {0, 0, 0, 0, 0};

            do {
                System.out.printf("== Turn %d == Number Sequence: ", Game.turnCount);
                String enteredSeqStr = scanner.nextLine();
                if (DEBUG){ System.out.printf("User Input: " + enteredSeqStr + "\n"); }

                if (enteredSeqStr.isEmpty()){
                    if (Game.DEBUG){
                        System.out.println("Guess can not be empty");
                    }
                    invalidSeq = true;
                    continue;
                }

                String[] enteredSeqArr = enteredSeqStr.split(" ");
                if (enteredSeqArr.length != 5) {
                    if (Game.DEBUG){
                        System.out.println("Guess can not be less than 5 numbers");
                    }
                    invalidSeq = true;
                    continue;
                }
                
                for (int i = 0; i <= 4; i++){
                    try{
                        userArr[i] = Integer.parseInt(enteredSeqArr[i]);
                    } catch(Exception e){
                        if (Game.DEBUG){
                            System.out.println("Error converting userArray to int");
                            System.out.println(e);
                        }
                        invalidSeq = true;
                        continue;
                    }
                }
                invalidSeq = false;
            } while (invalidSeq);

        if (DEBUG) { System.out.println("User Arr successfully validated"); }
        return userArr;
    } // End getUserArr

    
    public static int compareArr(int[] userArr){
        // RETURNS n Correct Elements
        
        int correct = 0;

        for (int i = 0; i <= 4; i++){
            if (Game.sequence[i] == userArr[i]) { correct++; }
            if (userArr[i] == 0){ Game.QUITTING = true; }
        }

        return correct;
    } // End compareArr

    public static void win(){
        System.out.printf("You guessed the sequence in %d turns\n", Game.turnCount);
        System.out.println();

        Game.endScreen();

    }

    public static void wrongGuess(int correct){
        System.out.printf("You have %d numbers correct\n", correct);
    }

    public static void quit(int correct){
        Game.wrongGuess(correct);
        System.out.println();
        Game.endScreen();
    }

    public static void endScreen(){
        System.out.println("Game Number Sequence");
        System.out.println("-----------");
        System.out.print("|");
        for (int n : Game.sequence){
            System.out.printf("%d|", n);
        }
        System.out.print("\n");
        System.out.println("-----------");
        System.out.println();

        // TODO: Add Highscores

        System.exit(0);
    }



} // end Class Game

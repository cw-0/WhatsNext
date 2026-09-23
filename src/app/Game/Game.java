package app.Game;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Scanner;

public class Game {
    public static int turnCount = 0;

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
        return seq;
    } // End load seq


    public static int[] getUserArr(){
        turnCount++;
        boolean invalidSeq = true;

        Scanner scanner = new Scanner(System.in);
        int[] userArr = {0, 0, 0, 0, 0};

            do {
                System.out.printf("== Turn %d == Number Sequence: ", turnCount);
                String enteredSeqStr = scanner.nextLine();

                if (!enteredSeqStr){
                    invalidSeq = true;
                    continue;
                }

                String enteredSeqArr = enteredSeqStr.split();
                if (enteredSeqArr.length() != 5) { 
                    invalidSeq = true;
                    continue;
                }
                
                for (int i = 0; i <= 4; i++){
                    try{
                        userArr[i] = (int)enteredSeqArr[i];
                    } catch(Exception e){
                        invalidSeq = true;
                        continue;
                    }
                }
            } while (invalidSeq);

        return userArr;
    } // End getUserArr

    
    public static int compareArr(int[] seq, int[] userArr){
        // RETURNS n Correct Elements
        
        int correct = 0;

        for (int i = 0; i <= 4; i++){
            if (seq[i] == userArr[i]) { correct++; }
        }

        return correct;
    }
}

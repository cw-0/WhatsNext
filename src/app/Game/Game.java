package app.Game;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Game {
    public static boolean DEBUG = false;
    public static boolean SHOW_ANSWER = true;
    public static String username = getCurrentTime();
    public static String highscoresFile = "highscores.csv";

    public static int turnCount = 0;
    public static boolean QUITTING = false;
    public static int[] sequence = loadSeq();
    public static Map<String, Integer> scoresMap = new HashMap<>();

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

        if (DEBUG || SHOW_ANSWER) { System.out.println("SEQ: " + Arrays.toString(seq)); }
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

        // TODO: Add Highscore
        fetchHighscores();
        if (!Game.QUITTING){
            updateHighscores();
            writeHighscores();
        }
        printHighscores();

        System.exit(0);
    }

public static void fetchHighscores(){


         if (!Files.exists(Path.of(Game.highscoresFile)) || !Files.isRegularFile(Path.of(Game.highscoresFile))){
             if (DEBUG){ System.out.println("Highscores File not found"); }
             return;
         }

         BufferedReader reader = null;
         String line = "";

          try {
              reader = new BufferedReader(new FileReader(Game.highscoresFile));
              boolean firstLine = true;
              while ((line = reader.readLine()) != null){
                  if (firstLine == true){
                      firstLine = false;
                      continue;
                  }

                  String[] row = line.split(",");
                  Game.scoresMap.put(row[0], Integer.parseInt(row[1]));

              }

          } catch(Exception e){
              e.printStackTrace();
         } finally {
              try{
                  reader.close();
              } catch (Exception e){
                  e.printStackTrace();
              }
          }
}

public static void updateHighscores(){
    Game.scoresMap.put(Game.username, Game.turnCount);

    Game.scoresMap = Game.scoresMap.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .limit(5)
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (oldValue, newValue) -> oldValue,
                    LinkedHashMap::new
            ));
    if (DEBUG) {
        System.out.println("Scores Updated");
        printHighscores();
    }
}

public static void writeHighscores(){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Game.highscoresFile))){
            writer.write("Name,Score");
            writer.newLine();
            for (Map.Entry<String, Integer> entry : Game.scoresMap.entrySet()) {
                String key = entry.getKey();
                String value = String.valueOf(entry.getValue());
                String line = key + "," + value;
                writer.write(line);
                writer.newLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
}

public static void printHighscores(){
    for (Map.Entry<String, Integer> entry : Game.scoresMap.entrySet()) {
        System.out.println(entry.getValue() + "-" + entry.getKey());
    }
}

public static String getCurrentTime(){
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH::mm::ss");
        String formattedTime = now.format(formatter);
        return formattedTime;
}

} // end Class Game

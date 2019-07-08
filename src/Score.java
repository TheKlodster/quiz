import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Score {
    
    private int score, time;
    private String fn, sn;
    
    private FileWriter file;
    private BufferedWriter writeFile;
    private Scanner scan;

    private ArrayList<String> tempList = new ArrayList<>();
    
    public Score() {
        try {
            file = new FileWriter("Scores.txt", true);
            writeFile = new BufferedWriter(file);
            scan = new Scanner(new File("Scores.txt"));
        } catch (Exception e) {
            System.out.println("File does not exist.");
        }
    }

    public void giveScore(String fn, String sn, int score, int time) {
        this.fn = fn;
        this.sn = sn;
        this.score = score;
        this.time = time;
    }
    
    public void setScore() {
        tempList = readFile();
        System.out.println(tempList);

        if(tempList.size() == 0) {
            try {
                writeFile.write(fn + " " + sn + " " + score + " " + time);
                writeFile.newLine();
                writeFile.close();
            } catch (Exception e) {
                System.out.println("There is an Error!");
            }
        }

        for(int i = 0; i < tempList.size() - 3; i++) {
            if (tempList.get(i).equals(fn) && tempList.get(i+1).equals(sn)) {
                overwrite();
                break;
            } else if(i == tempList.size() - 4) {
                try {
                    writeFile.write(fn + " " + sn + " " + score + " " + time);
                    writeFile.newLine();
                    writeFile.close();
                } catch (Exception e) {
                    System.out.println("There is an Error!");
                }
            }
        }
    }

    /**
     * Overwrite() method.
     *
     * This method overwrites the users data in a temporary ArrayList.
     */
    public void overwrite() {
        for(int i = 0; i < tempList.size() - 3; i++) {
            if (tempList.get(i).equals(fn) && tempList.get(i+1).equals(sn)) {
                tempList.set(i+2, Integer.toString(score));
                tempList.set(i+3, Integer.toString(time));
                break;
            }
        }
        System.out.println(tempList);

        /** int x = 0;

        for(String s : tempList) {
            if(x % 4 == 0 && x != 0) {
                try {
                    writeFile.write(System.getProperty("line.separator") + s + " ");
                } catch (Exception e) {
                    System.out.println("There is an Error!");
                }
            } else {
                try {
                    writeFile.write(s + " ");
                } catch (Exception e) {
                    System.out.println("There is an Error!");
                }
            }
            x += 1;
        } */

        try {
            new FileOutputStream("Scores.txt").close();
        } catch(Exception e) {
            System.out.println("Error trying to close the file.");
        }


        for(int i = 0; i < tempList.size(); i++) {
            try {
                if(i % 4 == 0 && i != 0 && i != tempList.size()) {
                    writeFile.write(System.getProperty("line.separator") + tempList.get(i) + " ");

                } else if(i == tempList.size() - 1) {
                    writeFile.write(tempList.get(i) + System.getProperty("line.separator"));

                } else {
                    writeFile.write(tempList.get(i) + " ");
                }
            } catch(Exception e) {
                System.out.println("Could not overwrite the file.");
            }
        }

        try {
            writeFile.close();
        } catch(Exception e) {
            System.out.println("Error trying to close the file.");
        }
    }


    public ArrayList<String> readFile() {
        ArrayList<String> fileList = new ArrayList<>();
        while(scan.hasNext()) {
            fileList.add(scan.next());
        }
        return fileList;
    }
}

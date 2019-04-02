import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class File {
    
    private int score;
    private int time;
    private String fn;
    private String sn;
    private String line;
    
    private String fileName;
    private PrintWriter output;
    private BufferedReader file;

    public File() {
        try {
            fileName = "Quiz Scores.txt";
            try {
                output = new PrintWriter(new FileOutputStream(fileName, true));
                file = new BufferedReader(new FileReader("Quiz Scores.txt"));
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
            }
            line = file.readLine();
            
        } catch (IOException ex) {
            Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void getScore(String fn, String sn, int score, int time) {
        this.score = score;
        this.fn = fn;
        this.sn = sn;
        this.time = time;
        setScore();
    }
    
    public void setScore() {
        while(line != "") {
            output.write(line);
            
        }
    }
}

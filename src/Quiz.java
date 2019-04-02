import java.util.Random;
import java.util.Scanner;
import javax.swing.JFrame;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class Quiz {
    
    private static Scanner scan = new Scanner(System.in);
    private static String fn;
    private static String sn;
    private int score = 0;
    private Random random = new Random();
    private String[] operator = {"+","-","x","%"};
    private int time = 0;
    private boolean running = false;
    private File file;
    
    private Timer stopwatch = new Timer();
    private TimerTask task = new TimerTask() {
        public void run() {
            time++;
            }
        };
        
    public Quiz(String fn, String sn) {
        this.fn = fn;
        this.sn = sn;
        file = new File();
    }
    
    public void setFrame() {
        JFrame window = new JFrame("Quiz");
        window.setSize(1000,1000);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(false);
        
    }
    
    public void getName() {
        boolean bool = true;
        
        System.out.println("Enter your First Name: ");
        fn = scan.nextLine();
        while(bool) {
            if(Pattern.matches("[a-z A-Z]+", fn)) {
                bool = false;
            } else {
                System.out.println("\nPlease enter your First Name again: ");
                fn = scan.nextLine();
            }
        }
        
        System.out.println("Enter your Surname: ");
        sn = scan.nextLine();
        while(bool) {
            if(Pattern.matches("[a-z A-Z]+", sn)) {
                bool = false;
            } else {
                System.out.println("\nPlease enter your Surname again: ");
                sn = scan.nextLine();
            }
        }
    }
    
    public void quiz() { 
        start();
        int answer;
                
        for(int i = 1; i <= 10; i++) {
            int firstNo = random.nextInt(20);
            int secondNo = random.nextInt(20);
            int index = random.nextInt(operator.length);
            
            if(operator[index] == "%") {
                secondNo += 1;
            }
            
            System.out.println("Question " + i + "\nWhat is " + firstNo + " " + operator[index] + " " + secondNo + " = ");
            int inputAnswer = scan.nextInt();
            
            if(operator[index] == "+") {
                answer = (firstNo + secondNo);
                System.out.println(checkAnswer(inputAnswer, answer));
            } else if(operator[index] == "-") {
                answer = (firstNo - secondNo);
                System.out.println(checkAnswer(inputAnswer, answer));
            } else if(operator[index] == "x") {
                answer = (firstNo * secondNo);
                System.out.println(checkAnswer(inputAnswer, answer));
            } else if(operator[index] == "%") {
                answer = (firstNo % secondNo);
                System.out.println(checkAnswer(inputAnswer, answer));
            }
        }
        System.out.println("Your score is " + score + "/10");
        System.out.println("Your time was : " + time + " seconds.");
        stopwatch.cancel();
        file.getScore(fn, sn, score, time);
        
    }
    
    public String checkAnswer(int inputAnswer, int answer) {
        if (inputAnswer == answer) {
            score += 1;
            return "Correct!\n";
        } else {
            return "Incorrect - the answer is " + answer + "\n";
        }
    }
    
    public void start() {
        stopwatch.scheduleAtFixedRate(task, 1000, 1000);
    }

    public static void main(String[] args) {
        Quiz start = new Quiz(fn,sn);
        start.setFrame();
        start.getName();
        start.quiz();
    }
}
    


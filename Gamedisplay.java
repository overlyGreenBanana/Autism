import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
import java.util.Random;

public class Gamedisplay extends JPanel {
    private int width;
    private int height;
    private ImageIcon greek = new ImageIcon();
    private JFrame frame = new JFrame();
    private JPanel display = new JPanel();
    private JLabel left;
    private JLabel center;
    private JLabel right;
    private JPanel bottomPanel;
    private JLabel bottom;
    private Font font = new Font("Arial", Font.PLAIN, 32);
    private JTextField typing = new JTextField();
    private Timer timer;

    private boolean word = false;
    private int choice;
    private int possibilities = 1;
    private Scanner scnr = new Scanner(System.in);
    private Random rnd = new Random();
    private int r;
    private int oldkleos = 0;
    private int kleos = 0;
    private int highscore = 0;
    private int gold = 0;
    private int oldgold = 0;
    private int mostgold = 0;
    private String name;

    public Gamedisplay(int w, int h, int high, int most) {
        width = w;
        height = h;
        highscore = high;
        mostgold = most;

        ImageIcon greekimage = new ImageIcon("greekwarrior.png");
        left = new JLabel(greekimage);
        center = new JLabel("<html>Kleos: "+kleos+"<br/>Highscore: "+highscore+"<br/>Gold: "+gold+"<br/>Most gold: "+mostgold+"</html>");
        center.setFont(font);
        right = new JLabel(greekimage);
        display.add(left);
        display.add(center);
        display.add(right);

        JPanel bottomPanel = new JPanel();
        bottom = new JLabel();
        bottom.setFont(font);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(bottom);

        typing = new JTextField(10);
        typing.setFont(font);

        typing.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                synchronized (Gamedisplay.this) {
                    try {
                        if(typing.getText().equals("exit")){
                            System.exit(0);
                        }
                        if(timer.isRunning()){
                            return;
                        }
                        if (word) {
                            name = typing.getText();
                            typing.setText("");
                            word = false;
                            Gamedisplay.this.notifyAll();
                        } else {
                            choice = Integer.parseInt(typing.getText());
                            typing.setText("");
                            if (choice <= 0 || choice > possibilities) {
                                JOptionPane.showMessageDialog(frame, "Please enter a valid choice.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            } else {
                                Gamedisplay.this.notifyAll();
                            }
                        }
                    } catch (NumberFormatException ex) {
                        typing.setText("");
                        JOptionPane.showMessageDialog(frame, "Please enter a valid integer.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        bottomPanel.add(typing);

        frame.add(display);
        frame.add(bottomPanel);
        SetUpGUI();
        input("You are a Greek warrior. (Type a 1 or a 2)\n1. I am a man.\n2. I am a woman.",2);
        if(choice == 2){
            input("You lose.\nYou are not a warrior. Go back to weaving.\n1. Respawn\n2. Exit game",2);
            restart();
        }
        word = true;
        input("What is your name?", 1);

        switch(name){
            case "level1":{
                new Level1().run();
            }
            case "level2":{
                new Level2().run();
            }
            case "Hector":
            case "hector":
            case "Paris":
            case "paris":{
                input("You lose.\nYou are a Trojan.\n1. Respawn\n2. Exit game",2);
                restart();
            }
            case "Achilles":
            case "achilles":{
                input("Are you going to cry to your mother?\n1. yes\n2. no",2);
                if(choice == 1){
                    input("You lose.\nYou are a wimp and will die without winning.\n1. Respawn\n2. Exit game",2);
                    restart();
                }
                else{
                    input("You lose.\nYou are not allowed to be a main character.\n1. Respawn\n2. Exit game",2);
                    restart();
                }
            }
            case "Odysseus":
            case "odysseus":
            case "Odyseus":
            case "odyseus":
            case "Oddysseus":
            case "oddysseus":
            case "Oddyseus":
            case "oddyseus":{
                input("You will not gain any kleos from this.\nYou will survive and can only gain kleos from your homecoming.\n1. try again\n2. give up",2);
                restart();
            }
        }

        input(name + ", will you agree to act in a manner\nworthy of a true warrior?\n1. I agree\n2. I disagree", 2);
        if (choice == 2) {
            input("Yes you will.\n1. I agree", 1);
        }
        input("Do you value honor and glory?\n1. yes\n2. no", 2);
        if(choice == 2){
            input("Incorrect. You lose.\n1. Respawn\n2. Exit game",2);
            restart();
        }
        input("Will you fight for them?\n1. yes\n2. no", 2);
        if (choice == 1) {
            word = true;
            input("Correct. Type c to go to level 1.\nType exit to exit game.",1);
            if(name.equals("C")||name.equals("c")){
                Level1 level1 = new Level1();
                level1.run();
            } else{
                new Gamedisplay(1000,640,0,0);
            }
        } else {
            input("Incorrect. You lose.\n1. Respawn\n2. Exit game",2);
            restart();
        }
        bottomPanel.remove(typing);
    }

    public void restart() {
        update();
        if(choice==1){
            new Gamedisplay(1000,640,highscore,mostgold);
            } else if(choice == 2){
            System.exit(0);
        }
    }

    class Level1{
        public void run(){
            input("Congratulations! You have reached level 1!\n1. continue\n2. learn why you're here",2);
            if(choice == 2){
                input("Because I told you so.\n1. continue\n2. learn why you're really here",2);
                if(choice ==2){
                    input("Because your friend's wife was stolen by a Trojan prince.\n1. continue",1);
                }
            }
            r = rnd.nextInt(10);
            if(r==1){
                input("You fell off the ship.\n1. Respawn\n2. Exit game",2);
                restart();
            }
            input("Are you ready to charge?\n1. yes\n2. no",2);
            if(choice==2){
                input("You lose.\nYou are a coward.\n1. Respawn\n2. Exit game",2);
                restart();
            }
            r = rnd.nextInt(10);
            if(r==1){
                input("You lose.\nYou were killed by a Trojan warrior.\n1. Respawn\n2. Exit game",2);
                restart();
            }
            kleos ++;
            update();
            input("Congratulations!\nYou survived your first charge! (not that you did anything)\n1. continue\n2. take a break",2);
            if(choice == 2){
                input("Now that you have put aside desire for food and drink-\n1. continue the charge",1);
            }
            r = rnd.nextInt(10);
            if(r==1){
                input("You lose.\nYou were killed by a Trojan archer.\n1. Respawn\n2. Exit game",2);
                restart();
            }
            input("You encountered your first Trojan!\n1. shoot him with your bow\n2. stab him with your spear",2);
            if(choice ==1){
                input("You don't have a bow.\n1. stab him with your spear",2);
                r = rnd.nextInt(10);
                if(r==1){
                    input("You lose.\nYou took too long deciding and he killed you.\n1. Respawn\n2. Exit game",2);
                    restart();
                }
            }
            r=rnd.nextInt(10);
            if(r==1){
                input("He killed you first.\n1. Respawn\n2. Exit game",2);
                restart();
            }
            kleos ++;
            update();
            input("You killed your first Trojan and gained some kleos!\n1. take his armor for extra kleos\n2. keep fighting",2);
            r = rnd.nextInt(10);
            if(r<=2){
                if(choice==1&&r==1){
                    input("You lose.\nYou were stabbed while distracted.\n1. Respawn\n2. Exit game",2);
                    restart();
                } else{
                    input("You were shot by an archer.\n1. Respawn\n2. Exit game",2);
                    restart();
                }
            }
            if(choice ==1){
                kleos ++;
                update();
            }
            oldkleos = kleos;
            fight(5-kleos);
            r = rnd.nextInt(10);
            if(r==1){
                input("Good work! Unfortunately the gods decided to kill you.\nYou lose.\n1. Respawn\n2. Exit game",2);
                restart();
            }
            Level2 level2 = new Level2();
            level2.run();
            input("Good work! Unfortunately the gods decided to kill you.\nYou lose.\n1. Respawn\n2. Exit game",2);
            restart();
        }
    }

    class Level2{
        public void run(){
            input("Congratulations! You have reached level 2!\n1. continue\n2. learn more about why you're here",2);
            if(choice == 2){
                input("Because I told you so.\n1. continue\n2. really learn why you're here",2);
                if(choice ==2){
                    input("Paris, an evil Trojan prince,\nstole your friend Agamemnon's wife Helen,\nthe most beautiful woman in the world.\n1. continue",1);
                }
            }
            input("Achilles rages against the Trojans.\nWill you follow him into battle?\n1. yes, charge\n2. no, take a break",2);
            if(choice == 2){
                input("Will you just eat, or will you gamble first?\n1. gamble first\n2. eat",2);
                if(choice ==1){
                    oldgold = gold;
                    gamble(5);
                }
                input("Now you will eat.\n1. eat",1);
                input("Now that you have put aside desire for food and drink-\n1. continue the charge",1);
            } else{
                input("Then prepare to charge!\n1. continue",1);
                r = rnd.nextInt(2);
                if(r==1){
                    input("You lose.\nYou charged, but you were too tired to fight.\nA Trojan warrior killed you.\n1. Respawn\n2. Exit Game",2);
                    restart();
                }
            }
            oldkleos = kleos;
            fight(5);
            return;
        }
    }

    public synchronized void delay() {//waits until you click enter to proceed
        try {
            wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }

    public void update(){
        if(kleos>highscore){
            highscore = kleos;
        }
        if(gold>mostgold){
            mostgold = gold;
        }
        String text = "Kleos: "+kleos+"\nHighscore: "+highscore+"\nGold: "+gold+"\nMost gold: "+mostgold;
        center.setText("<html>"+text.replace("\n","<br/>")+"</html>");
    }

    public void fight(int times){
        input("You encountered a Trojan.\n1. stab him with your spear",2);
        r=rnd.nextInt(10);
        if(r==1){
            input("He killed you first.\n1. Respawn\n2. Exit game",2);
            restart();
        }
        kleos ++;
        update();
        input("You killed a Trojan and gained some kleos!\n1. take his armor for extra kleos\n2. keep fighting",2);
        r = rnd.nextInt(10);
        if(r<=2&&choice==1){
            input("You lose.\nYou were stabbed while distracted.\n1. Respawn\n2. Exit game",2);
            restart();
        } else if(r==1){
            input("You were shot by an archer.\n1. Respawn\n2. Exit game",2);
            restart();
        }
        if(choice==1){
            kleos ++;
            update();
        }
        if(kleos>=oldkleos+times){
            return;
        }
        fight(times);
    }

    public void gamble(int times){
        if(gold >=oldgold + times){
            return;
        }
        r = rnd.nextInt(6)+rnd.nextInt(6);
        input("To win you must roll "+r+" or higher.\n1. go for it\n2. stop betting",2);
        if(choice ==2){
            return;
        }
        int r2 = rnd.nextInt(6)+rnd.nextInt(6);
        if(r2>=r){
            r = r2;
            gold ++;
            update();
            input("You rolled "+r+". \nYou won!\n1. play again\n2. quit",2);
            if(choice ==1){
                gamble(times);
            }
        } else{
            r = r2;
            gold = 0;
            update();
            input("You rolled "+r+". \nYou lost.\n1. quit",1);
        }
        return;
    }

    public void input(String text, int pos) {
        //bottom.setText("<html>" + text.replace("\n", "<br/>") + "</html>");
        write(text);
        possibilities = pos;
        delay(); //waits to proceed until choice has been changed
    }

    public void SetUpGUI() {
        frame.setSize(width, height);
        frame.setTitle("Iliad game");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLayout(new FlowLayout());
    }

    public void write(String text) {
        timer = new Timer(50, new ActionListener() {
            private int index = 0;
            private StringBuilder sb = new StringBuilder();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < text.length()) {
                    char c = text.charAt(index);
                    if (c == '\n') {
                        sb.append("<br>");
                    } else {
                        sb.append(c);
                    }
                    bottom.setText("<html>" + sb.toString() + "</html>");
                    index++;
                } else {
                    timer.stop();
                }
            }
        });
        bottom.setText(""); // Clear previous text
        timer.start();
    }

    public static void main(String[] args) {
        new Gamedisplay(1000, 640, 0,0);
    }
}
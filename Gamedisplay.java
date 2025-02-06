import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
//import javax.swing.JButton;
import java.util.Scanner;
import java.util.Random;
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

    private boolean word = false;
    private int choice;
    private int possibilities = 1;
    private Scanner scnr = new Scanner(System.in);
    private Random rnd = new Random();
    private int r;
    private int kleos = 0;
    private String name;

    public Gamedisplay(int w, int h) {
        width = w;
        height = h;

        ImageIcon greekimage = new ImageIcon("greekwarrior.png");
        left = new JLabel(greekimage);
        center = new JLabel("Kleos = "+kleos);
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
                input("You will not gain any kleos from this.\nyou will survive and can only gain kleos from your homecoming.\n1. try again\n2. give up",2);
                restart();
            }
        }

        input(name + ", will you agree to act in a manner worthy of a true warrior?:\n1. I agree\n2. I disagree", 2);
        if (choice == 2) {
            input("Yes you will.\n1. I agree", 1);
        }
        input("Do you value honor?\n1. yes\n2. no", 2);
        if(choice == 2){
            input("Incorrect. You lose.\n1. Respawn\n2. Exit game",2);
            restart();
        }
        input("Will you fight for it?\n1. yes\n2. no", 2);
        if (choice == 1) {
            word = true;
            input("Correct. Type c to go to level 1.\nType exit to exit game.",1);
            if(name.equals("C")||name.equals("c")){
                Level1 level1 = new Level1();
                level1.run();
            } else{
                new Gamedisplay(1000,640);
            }
        } else {
            input("Incorrect. You lose.\n1. Respawn\n2. Exit game",2);
            restart();
        }
        bottomPanel.remove(typing);
    }

    public void restart() {
        if(choice==1){
            new Gamedisplay(1000,640);
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
                    input("Because your friend's wife was stolen by a Trojan prince\n1. continue",1);
                }
            }
            r = rnd.nextInt(10);
            if(r==1){
                input("You fell off the ship.\n1. Respawn\n2. Exit game",2);
                restart();
            }
            input("Are you ready to charge?\n1. yes\n2. no",2);
            if(choice==2){
                input("You lost.\nYou are a coward.\n1. Respawn\n2. Exit game",2);
                restart();
            }
            r = rnd.nextInt(10);
            if(r==1){
                input("You lose.\nYou were killed by a Trojan warrior.\n1. Respawn\n2. Exit game",2);
                restart();
            }
            input("Congratulations!\nYou survived your first charge! (not that you did anything)\n1. continue\n2. take a break",2);
            kleos ++;
            center.setText("Kleos = "+kleos);
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
            input("You killed your first Trojan and gained some kleos!\n1. take his armor for extra kleos\n2. keep fighting",2);
            kleos ++;
            center.setText("Kleos = "+kleos);
            r = rnd.nextInt(10);
            if(r<=2){
                if(choice==1&&r==1){
                    input("You lose.\nYou were stabbed while distracted.\n1. Respawn\n2. Exit game",2);
                    restart();
                } else{
                    input("You were shot ny an archer.\n1. Respawn\n2. Exit game",2);
                    restart();
                }
            }
            if(choice ==2){
                kleos ++;
                center.setText("Kleos = "+kleos);
            }
            input("Good work! Unfortunately the gods decided to kill you.\nYou lose.\n1. Respawn\n2. Exit game",2);
            restart();
        }
    }

    public synchronized void delay() {//waits until you click enter to proceed
        try {
            wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }

    public void fight(){
        input("You encountered a Trojan.\n1. stab him with your spear",2);
        r=rnd.nextInt(10);
        if(r==1){
            input("He killed you first.\n1. Respawn\n2. Exit game",2);
            restart();
        }
        input("You killed a Trojan and gained some kleos!\n1. take his armor for extra kleos\n2. keep fighting",2);
        kleos ++;
        center.setText("Kleos = "+kleos);
        r = rnd.nextInt(10);
        if(r<=2){
            if(choice==1&&r==1){
                input("You lose.\nYou were stabbed while distracted.\n1. Respawn\n2. Exit game",2);
                restart();
            } else{
                input("You were shot ny an archer.\n1. Respawn\n2. Exit game",2);
                restart();
            }
        }
        if(choice ==2){
            kleos ++;
            center.setText("Kleos = "+kleos);
        }
    }

    public void input(String text, int pos) {
        bottom.setText("<html>" + text.replace("\n", "<br/>") + "</html>");
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

    public static void main(String[] args) {
        new Gamedisplay(1000, 640);
    }
}
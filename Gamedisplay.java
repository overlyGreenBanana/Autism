import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.JButton;
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

    private boolean word = false;
    private int choice;
    private int possibilities = 1;
    private Scanner scnr = new Scanner(System.in);
    private String name;

    public Gamedisplay(int w, int h) {
        width = w;
        height = h;

        ImageIcon greekimage = new ImageIcon("greekwarrior.png");
        left = new JLabel(greekimage);
        center = new JLabel(greekimage);
        right = new JLabel(greekimage);
        display.add(left);
        display.add(center);
        display.add(right);

        JPanel bottomPanel = new JPanel();
        bottom = new JLabel();
        bottom.setFont(font);
        bottomPanel.setLayout(new GridLayout(2, 1));
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
        input("You are a Greek warrior.\n1. I am a man.\n2. I am a woman.",2);
        if(choice == 2){
            input("You lose.\nYou are not a warrior. Go back to weaving.\n1. Respawn\n2. Exit game",2);
            restart();
        }
        word = true;
        input("You are a Greek warrior.\nWhat is your name?", 1);

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
        input("Will you fight for it?\n1. yes\n2. no", 2);
        if (choice == 1) {
            word = true;
            input("Correct. Type continue to go to level 1.\nType exit to exit game.",1);
            if(name.equals("Continue")||name.equals("continue")){
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
                input("Because I told you so.\n1. continue",1);
            }
            input("You fell off the ship.\n1. Respawn\n2. Exit game",2);
            restart();
        }
    }

    public synchronized void delay() {
        try {
            wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }

    public void input(String text, int pos) {
        bottom.setText("<html>" + text.replace("\n", "<br/>") + "</html>");
        possibilities = pos;
        delay();
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
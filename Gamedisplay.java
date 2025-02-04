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

    private boolean first = true;
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
                        if (first) {
                            name = typing.getText();
                            typing.setText("");
                            first = false;
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

        input("<html>You are a Greek warrior.<br/>What is your name?</html>", 1);
        input(name + ", will you agree to type the number of your choice?:<br/>1. I agree<br/>2. I disagree", 2);
        if (choice == 2) {
            input("Yes you will.<br/>1. I agree", 1);
        }
        input("Do you value honor?<br/>1. yes<br/>2. no", 2);
        input("Will you fight for it?<br/>1. no<br/>2. yes", 2);
        if (choice == 2) {
            bottom.setText("Correct.");
        } else {
            bottom.setText("Incorrect.");
        }
        bottomPanel.remove(typing);
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
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.JButton;
import java.util.Scanner;
import java.util.Random;

public class Gamedisplay extends JPanel{
	private int width;
	private int height;
	private ImageIcon greek = new ImageIcon();
	private JFrame frame = new JFrame();
	private JPanel display = new JPanel();
	private JLabel left;
	private JLabel center;
	private JLabel right;
	private JLabel bottom;

	private int choice;
	private Scanner scnr = new Scanner(System.in);
	private String name;
	public Gamedisplay(int w,int h){
		width = w;
		height = h;
		//frame.setLayout(new GridLayout(1,3,0,0));
		//display.setLayout(new BorderLayout());
		ImageIcon greekimage = new ImageIcon("greekwarrior.png");
		left = new JLabel(greekimage);
		center = new JLabel(greekimage);
		right = new JLabel(greekimage);
		display.add(left);
		display.add(center);
		display.add(right);
		bottom = new JLabel();
		//bottom.setPreferredSize(new Dimension(1000, 200));
		bottom.setFont(new Font("Arial", Font.PLAIN, 32));
		display.add(bottom);
		frame.add(display);
		SetUpGUI();

		System.out.print("\033[H\033[2J");  
    	System.out.flush(); 
		System.out.println("You are a Greek warrior.\nWhat is your name?");
		bottom.setText("<html>You are a Greek warrior.<br/>What is your name?</html>");
		name = scnr.nextLine();
		choice=input(name+", will you agree to type the number of your choice?:\n1. I agree\n2. I disagree",2);
		if(choice == 2){
			choice=input("Yes you will.\n1. I agree",1);
		}
		choice = input("Do you value honor?\n1. yes\n2. no",1);
		choice = input("Will you fight for it\n1. no\n2. yes", 2);
		if(choice==2){
			System.out.println("Correct.");
			bottom.setText("Correct.");
		} else{
			System.out.println("Incorrect.");
			bottom.setText("Incorrect.");
		}
	}
	public int input(String text, int possibilities){//print the choices and get their choice as an int
		System.out.print("\033[H\033[2J");  
    	System.out.flush(); 
		System.out.println(text);
		String ntext = "<html>"+text.replace("\n", "<br/>");
		ntext+="</html>";
		bottom.setText(ntext);
		int input = 0;
		try{
			input = scnr.nextInt();
		} catch(Exception e){
			scnr.nextLine();
			input(text, possibilities);//if it is not an int, run the function again
		} finally{
			if(input>possibilities||input<1){
				input(text, possibilities);
			}
			return input;
		}
	}
	public void SetUpGUI(){
		frame.setSize(width, height);
		frame.setTitle("Iliad game");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		Gamedisplay f = new Gamedisplay(1000, 500);
	}
}

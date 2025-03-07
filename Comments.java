import java.awt.event.*; //this imports everything in the package java.awt.event, allowing me to access its contents, refering to its methods and classes, because 'import java.awt.*' does not go deep enough.
import java.awt.*; //this imports everything in the package java.awt, allowing me to access its contents, refering to its methods and classes
import javax.swing.*; //this imports everything in the package java.swing, allowing me to access its contents, refering to its methods and classes
import java.util.Scanner; //this imports the class java.util.Scanner so I can access and refer to it and its methods
import java.util.Random; //this imports the class java.util.Ramdom so I can access and refer to it and its methods

public class Comments extends JPanel { //this declares the public class Gamedisplay, which has to be saved in a file called Gamedisplay.java. I declared this as a jpanel so I can do things like setting the background color and things like that.
    private int width; //to allow access to the width and height from anywhere, used in the SetUpGui() method
    private int height; //see above
    private ImageIcon greek = new ImageIcon(); //for the image
    private JFrame frame = new JFrame(); //jframe to hold everything
    private JPanel display = new JPanel(); //jpanel to hold the 3 jLabels
    private JLabel left; //jlabel in the top left
    private JLabel center; //jlabel in the top center
    private JLabel right; //jlabel in the top right
    private JPanel bottomPanel = new JPanel(); //jpanel to hold the text at the bottom and the input
    private JLabel bottom; //jlabel to hold the text at the bottom, or an image potentially
    private Font font = new Font("Arial", Font.PLAIN, 32); //to let me set things to a large, easily readable font
    private JTextField typing = new JTextField(); //to allow the user to type an input
    private Timer timer; //to allow me to type 1 letter at a time after a delay (used in the write() function/method)

    private boolean word = false; //used if you want the input to be a word instead of an int (set it to true, input() outputs a string and then sets it to false)
    private int choice; //the number of their choice, public so it can be accessed from anywhere
    private int possibilities = 1; //the number of possible choices, updated by the input function
    //private Scanner scnr = new Scanner(System.in); unnecessary unless you want to use terminal
    private Random rnd = new Random(); //to allow generation of a new random number without declaring another Random()
    private int r; //the integer which is reset with rnd.nextInt()
    private int oldkleos = 0; //the kleos before using the fight method, used with the times parameter in fight() to stop after a certain amount of kleos more than oldkleos is gained
    private int kleos = 0; //the kleos score, reset by the restart() method
    private int highscore = 0; //the most kleos gained since the program started running, is not reset by the restart() method
    private int gold = 0; //the gold score, reset by the restart() method
    private int oldgold = 0; //the gold score before using the gamnble method, used like the oldkleos score is used in relation to fight()
    private int mostgold = 0; //the most goild gained since the program started running, is not reset by the restart() method
    private String name; //this String is first used when the user inputs their name, but is reused whenever they input a String and is called name for lack of a better, well, name

    public Comments (int w, int h, int high, int most) {
        width = w; //to set width to the inputed width
        height = h; //to set height to the inputed height
        highscore = high; //to preserve the highscore from before
        mostgold = most; //to preserve the mostgold score from before

        ImageIcon greekimage = new ImageIcon("greekwarrior.png"); //to set the image to the pixelated greek warrior image
        left = new JLabel(greekimage); //to add the image of the greek warrior image to he left jlabel
        center = new JLabel("<html>Kleos: "+kleos+"<br>Highscore: "+highscore+"<br>Gold: "+gold+"<br>Most gold: "+mostgold+"</html>"); //to display the kleos, highscore, gold, and mostgold on the center jlabel. the <html> is to make the line breaks appear correctly on the jlabel
        center.setFont(font); //to make the font of the center label with the highscore a nice, big, readable font
        right = new JLabel(greekimage); //to add the image of the greek warrior image to he left jlabel
        display.add(left); //to add the left jlabel to the display panel
        display.add(center); //to add the center jlabel to the display panel
        display.add(right); //to add the right jlabel to the display panel

        //JPanel bottomPanel = new JPanel(); this line is now unnecessary and I initialized botompanel as a jpanel when I first declared it
        bottom = new JLabel(); //to initialized bottom as a jlabel 
        bottom.setFont(font); //to set the font of bottom to a nice, big, readable font
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS)); //to make it so the jlabel bottom and the jtextfield typing stay vertically aligned
        bottomPanel.add(bottom); //to add the jlabel bottom to bottomPanel

        typing = new JTextField(10); //to initialize the textfield typing. I don't initialize everyting when I first declare it because I want to wait until I have all I need, to declare it how I want. for example, I declared the jlabels only after I had the image I wanted to add to them. if I declare them and then change them, it looks bad.
        typing.setFont(font); //to set the font of typing to a nice, big, readable font

        typing.addActionListener(new ActionListener() { //to add an actionlistener so the code does things when the user presses enter
            @Override //this makes it easier to tell that the original actionPerformed() method is being overridden
            public void actionPerformed(ActionEvent e) { //this overrides the actionPerformed() method to add the code for when the user presses enter
                synchronized (Comments.this) { //this syncronizes the rest of the code with this so it waits until it has gotten an input until proceeding with the rest of the code
                    try { //this tries to perforom the following code, and if it doesn't work, it goes to the catch section and performs that. finally, it goes to the finally method and performs that code.
                        if(typing.getText().equals("exit")){ //so I can stop the program by typing "exit"
                            System.exit(0); //this stops the program without doing anything else. I think the parameter does different things depending on the number, and the default is 0.
                        } //this is a closing curly bracket which closes the preceeding code
                        if(timer.isRunning()){ //this makes the user wait until the text is fully displayed, and does nothing if it is not
                            return; //this returns to the code, again waiting for the user to press enter
                        } //this is a closing curly bracket which closes the preceeding code
                        if (word) { //this allows the user to input a string if the boolean word is true
                            name = typing.getText(); //this gets the text of the textfield typing as a string
                            typing.setText(""); //this resets the text of typing to "" if the input is accepted
                            word = false; //this changes word ot false, so the next input will be an int unless otherwise specified
                            Comments.this.notifyAll(); //this notifies the rest of the code so it can proceed because a valid input has been attained
                        } else { //this is an else statement. it executes the code after it only if none of the if or else if statements above had a true parameter
                            choice = Integer.parseInt(typing.getText()); //this sets the int choice to the text of typing, but as an int
                            typing.setText(""); //this resets the text of typing to "" if the input is accepted
                            if (choice <= 0 || choice > possibilities) { //this throws an error if the choice is not withing the valid range
                                JOptionPane.showMessageDialog(frame, "Please enter a valid choice.", "Invalid Input", JOptionPane.ERROR_MESSAGE); //this displays an error message in a way that makes it seem serious
                            } else { //this is an else statement. it executes the code after it only if none of the if or else if statements above had a true parameter
                                Comments.this.notifyAll(); //this notifies the rest of the code so it can proceed because a valid input has been attained
                            } //this is a closing curly bracket which closes the preceeding code
                        } //this is a closing curly bracket which closes the preceeding code
                    } catch (NumberFormatException ex) { //this catches an error if the input cannot be converted to an int
                        typing.setText(""); //this resets the text of typing to "" if the input is accepted
                        JOptionPane.showMessageDialog(frame, "Please enter a valid integer.", "Invalid Input", JOptionPane.ERROR_MESSAGE); //this displays an error message in a way that makes it seem serious
                    } //this is a closing curly bracket which closes the preceeding code
                } //this is a closing curly bracket which closes the preceeding code
            } //this is a closing curly bracket which closes the preceeding code
        }); //this closes the preceeding code and finishes the adding of the actionlistener to typing

        bottomPanel.add(typing); //this adds typing to bottompanel

        frame.add(display); //this adds the jpanel display to the jframe frame
        frame.add(bottomPanel); //this adds the jpanel bottompanel to the jframe frame
        SetUpGUI(); //this sets up the GUI, setting the width to width, the height to height, setting the closing aperation, and making the jframe frmae visible
        input("You are a Greek warrior. (Type a 1 or a 2)\n1. I am a man.\n2. I am a woman.",2); //this asks for an int input from the user with 2 possibilities
        if(choice == 2){ //this executes the following code if the choice is 2
            input("You lose.\nYou are not a warrior. Go back to weaving.\n1. Respawn\n2. Exit game",2); //this tells the user to restart or exit the game
            restart(); //this restarts or exits depending on the number of choice
        } //this is a closing curly bracket which closes the preceeding code
        word = true; //this sets the boolean word to true, allowing a string input
        input("What is your name?", 1); //this asks for the user's name, storing the input as a string

        switch(name){ //this creates a switch with the string name as a parameter which can be compared with various things through 'case'
            case "level1":{ //this executes the following code if name is equal to the inputed string
                new Level1().run(); //this creates a new instance of the class level1 and uses the method run()
            } //this is a closing curly bracket which closes the preceeding code
            case "level2":{ //this executes the following code if name is equal to the inputed string
                new Level2().run(); //this creates a new instance of the class level2 and uses the method run()
            } //this is a closing curly bracket which closes the preceeding code
            case "Hector": //this proceeds to the next open curly bracket if name is equal to the inputed string
            case "hector": //this proceeds to the next open curly bracket if name is equal to the inputed string
            case "Paris": //this proceeds to the next open curly bracket if name is equal to the inputed string
            case "paris":{ //this executes the following code if name is equal to the inputed string
                input("You lose.\nYou are a Trojan.\n1. Respawn\n2. Exit game",2); //this tells the user to restart or exit the game
                restart(); //this restarts or exits depending on the number of choice
            } //this is a closing curly bracket which closes the preceeding code
            case "Achilles": //this proceeds to the next open curly bracket if name is equal to the inputed string
            case "achilles":{ //this executes the following code if name is equal to the inputed string
                input("Are you going to cry to your mother?\n1. yes\n2. no",2); //this asks for an int input from the user with 2 possibilities
                if(choice == 1){ //this executes the following code if the choice is 2
                    input("You lose.\nYou are a wimp and will die without winning.\n1. Respawn\n2. Exit game",2); //this tells the user to restart or exit the game
                    restart(); //this restarts or exits depending on the number of choice
                } else { //this is an else statement. it executes the code after it only if none of the if or else if statements above had a true parameter
                    input("You lose.\nYou are not allowed to be a main character.\n1. Respawn\n2. Exit game",2); //this tells the user to restart or exit the game
                    restart(); //this restarts or exits depending on the number of choice
                } //this is a closing curly bracket which closes the preceeding code
            } //this is a closing curly bracket which closes the preceeding code
            case "Odysseus": //this proceeds to the next open curly bracket if name is equal to the inputed string
            case "odysseus": //this proceeds to the next open curly bracket if name is equal to the inputed string
            case "Odyseus": //this proceeds to the next open curly bracket if name is equal to the inputed string
            case "odyseus": //this proceeds to the next open curly bracket if name is equal to the inputed string
            case "Oddysseus": //this proceeds to the next open curly bracket if name is equal to the inputed string
            case "oddysseus": //this proceeds to the next open curly bracket if name is equal to the inputed string
            case "Oddyseus": //this proceeds to the next open curly bracket if name is equal to the inputed string
            case "oddyseus":{ //this executes the following code if name is equal to the inputed string
                input("You will not gain any kleos from this.\nYou will survive and can only gain kleos from your homecoming.\n1. try again\n2. give up",2); //this tells the user to either try again or give up
                restart(); //this restarts or exits depending on the number of choice
            } //this is a closing curly bracket which closes the preceeding code
            case "Menelaus": //this proceeds to the next open curly bracket if name is equal to the inputed string
            case "menelaus": //this proceeds to the next open curly bracket if name is equal to the inputed string
            case "Agamemnon": //this proceeds to the next open curly bracket if name is equal to the inputed string
            case "agamemnon":{ //this executes the following code if name is equal to the inputed string
                input("You cannot be a main character as they are important\nto the plot and you cannot be trusted to do\nwhat is required of them.\n1. Respawn\n2. Exit game",2); //this tells the user to restart or exit the game
                restart(); //this restarts or exits depending on the number of choice
            } //this is a closing curly bracket which closes the preceeding code
            case "Helen": //this proceeds to the next open curly bracket if name is equal to the inputed string
            case "helen":{ //this executes the following code if name is equal to the inputed string
                input("You lose.\nYou are not a warrior. Go back to weaving.\n1. Respawn\n2. Exit game",2); //this tells the user to restart or exit the game
                restart(); //this restarts or exits depending on the number of choice
            } //this is a closing curly bracket which closes the preceeding code
        } //this is a closing curly bracket which closes the preceeding code

        input(name + ", will you agree to act in a manner\nworthy of a true warrior?\n1. I agree\n2. I disagree", 2); //this asks for an int input from the user with 2 possibilities
        if (choice == 2) { //this executes the following code if the choice is 2
            input("Yes you will.\n1. I agree", 1); //this asks the user for an int input with 1 possibility
        } //this is a closing curly bracket which closes the preceeding code
        input("Do you value honor and glory?\n1. yes\n2. no", 2); //this asks for an int input from the user with 2 possibilities
        if(choice == 2){ //this executes the following code if the choice is 2
            input("Incorrect. You lose.\n1. Respawn\n2. Exit game",2); //this tells the user to restart or exit the game
            restart(); //this restarts or exits depending on the number of choice
        } //this is a closing curly bracket which closes the preceeding code
        input("Will you fight for them?\n1. yes\n2. no", 2); //this asks for an int input from the user with 2 possibilities
        if (choice == 1) { //this executes the following code if the choice is 1
            word = true; //this sets the boolean word to true, allowing a string input from the user
            input("Correct. Type c to go to level 1.\nType exit to exit game.",1);
            if(name.equals("C")||name.equals("c")){ //this executes the following code if the string name is "C" or "c"
                new Level1().run(); //this declares a new instance of the class level1 and executes the run method
            } else { //this is an else statement. it executes the code after it only if none of the if or else if statements above had a true parameter
                frame.dispose(); //this disposes the previous frame
                new Comments(1000,640,0,0); //this creates a new instance of the gamedisplay class, restarting the game and setting everything to 0 because the user is incapable of following directions
            } //this is a closing curly bracket which closes the preceeding code
        } else { //this is an else statement. it executes the code after it only if none of the if or else if statements above had a true parameter
            input("Incorrect. You lose.\n1. Respawn\n2. Exit game",2); //this tells the user to restart or exit the game
            restart(); //this restarts or exits depending on the number of choice
        } //this is a closing curly bracket which closes the preceeding code
        // bottomPanel.remove(typing); this line is unnecessary, but would remove the jtextfield typing and keep the user from inputing anything
    } //this is a closing curly bracket which closes the preceeding code

    public void restart() { //this restarts or exits depending on the number of choice
        update(); //this runs the update() method and updates kleos, highscore, gold, and mostgold, displaying the new values on the jlabel center
        if(choice==1){ //this executes the following code if the choice is 1
        frame.dispose(); //this disposes the previous frame
        new Comments(1000,640,highscore,mostgold); //this creates a new instance of the gamedisplay class, preserving the highscore and mostgold score by inputing them as parameters to the new gamedisplay
        } else if(choice == 2){ //this executes the following code if the choice is 2 and the previous if statements parameter is false
        System.exit(0); //this stops and the program without doing anything interesting
        } //this is a closing curly bracket which closes the preceeding code
    } //this is a closing curly bracket which closes the preceeding code

    class Level1{ //this declares the class level1 which has a run method
        public void run(){ //this declares the run method of level which uses the input method repeatedly to simulate the first part of a text-based adventure
            input("Congratulations! You have reached level 1!\n1. continue\n2. learn why you're here",2); //this asks for an int input from the user with 2 possibilities
            if(choice == 2){ //this executes the following code if the choice is 2
                input("Because I told you so.\n1. continue\n2. learn why you're really here",2); //this asks for an int input from the user with 2 possibilities
                if(choice ==2){ //this executes the following code if the choice is 2
                    input("Because your friend's wife was stolen by a Trojan prince.\n1. continue",1); //this asks for an int input from the user with 1 possibility
                } //this is a closing curly bracket which closes the preceeding code
            } //this is a closing curly bracket which closes the preceeding code
            r = rnd.nextInt(19)+1; //this generates a random integer from 1 to 20 inclusive
            if(r==1){ //this executes the following code if r is 1
                input("You fell off the ship.\n1. Respawn\n2. Exit game",2); //this tells the user to restart or exit the game
                restart(); //this restarts or exits depending on the number of choice
            } //this is a closing curly bracket which closes the preceeding code
            input("Are you ready to charge?\n1. yes\n2. no",2); //this asks for an int input from the user with 2 possibilities
            if(choice==2){ //this executes the following code if the choice is 2
                input("You lose.\nYou are a coward.\n1. Respawn\n2. Exit game",2); //this tells the user to restart or exit the game
                restart(); //this restarts or exits depending on the number of choice
            } //this is a closing curly bracket which closes the preceeding code
            r = rnd.nextInt(19)+1; //this generates a random integer from 1 to 20 inclusive
            if(r==1){ //this executes the following code if r is 1
                input("You lose.\nYou were killed by a Trojan warrior.\n1. Respawn\n2. Exit game",2); //this tells the user to restart or exit the game
                restart(); //this restarts or exits depending on the number of choice
            } //this is a closing curly bracket which closes the preceeding code
            kleos ++; //this adds 1 to kleos
            update(); //this runs the update() method which updates and displays kleos, highscore, gold, and mostgold
            input("Congratulations!\nYou survived your first charge! (not that you did anything)\n1. continue\n2. take a break",2); //this asks for an int input from the user with 2 possibilities
            if(choice == 2){ //this executes the following code if the choice is 2
                input("Now that you have put aside desire for food and drink-\n1. continue the charge",1); //this asks for an int input from the user with 1 possibility
            } //this is a closing curly bracket which closes the preceeding code
            r = rnd.nextInt(19)+1; //this generates a random integer from 1 to 20 inclusive
            if(r==1){ //this executes the following code if r is 1
                input("You lose.\nYou were killed by a Trojan archer.\n1. Respawn\n2. Exit game",2); //this tells the user to restart or exit the game
                restart(); //this restarts or exits depending on the number of choice
            } //this is a closing curly bracket which closes the preceeding code
            input("You encountered your first Trojan!\n1. shoot him with your bow\n2. stab him with your spear",2); //this asks for an int input from the user with 2 possibilities
            if(choice ==1){ //this executes the following code if the choice is 1
                input("You don't have a bow.\n1. stab him with your spear",2); //this asks for an int input from the user with 2 possibilities
                r = rnd.nextInt(19)+1; //this generates a random integer from 1 to 20 inclusive
                if(r==1){ //this executes the following code if r is 1
                    input("You lose.\nYou took too long deciding and he killed you.\n1. Respawn\n2. Exit game",2); //this tells the user to restart or exit the game
                    restart(); //this restarts or exits depending on the number of choice //this restarts or exits depending on the number of choice
                } //this is a closing curly bracket which closes the preceeding code
            } //this is a closing curly bracket which closes the preceeding code
            r=rnd.nextInt(19)+1; //this generates a random integer from 1 to 20 inclusive
            if(r==1){ //this executes the following code if r is 1
                input("He killed you first.\n1. Respawn\n2. Exit game",2); //this tells the user to restart or exit the game
                restart(); //this restarts or exits depending on the number of choice
            } //this is a closing curly bracket which closes the preceeding code
            kleos ++; //this adds 1 to kleos
            update(); //this runs the update() method which updates and displays kleos, highscore, gold, and mostgold
            input("You killed your first Trojan and gained some kleos!\n1. take his armor for extra kleos (riskier)\n2. keep fighting",2); //this asks for an int input from the user with 2 possibilities
            r = rnd.nextInt(19)+1; //this generates a random integer from 1 to 20 inclusive
            if(r<=2){ //this executes the following code if r is less than or equal to 2
                if(choice==1&&r==1){ //this executes the following code if choice is 1 and r is one
                    input("You lose.\nYou were stabbed while distracted.\n1. Respawn\n2. Exit game",2); //this tells the user to restart or exit the game
                    restart(); //this restarts or exits depending on the number of choice
                } else {  //this is an else statement. it executes the code after it only if none of the if or else if statements above had a true parameter
                    input("You were shot by an archer.\n1. Respawn\n2. Exit game",2); //this tells the user to restart or exit the game
                    restart(); //this restarts or exits depending on the number of choice
                } //this is a closing curly bracket which closes the preceeding code
            } //this is a closing curly bracket which closes the preceeding code
            if(choice ==1){ //this executes the following code if the choice is 1
                kleos ++; //this adds one to kleos
                update(); //this runs the update() method which updates and displays kleos, highscore, gold, and mostgold
            } //this is a closing curly bracket which closes the preceeding code
            oldkleos = kleos; //this stores the previous kleos value as oldkleos for use in the fight() method
            fight(5-kleos); //this executes the fight() method, until the user's kleos score is greater than or equal to 5
            r = rnd.nextInt(19)+1; //this generates a random integer from 1 to 20 inclusive
            if(r==1){ //this executes the following code if r is 1
                input("Good work! Unfortunately the gods decided to kill you.\nYou lose.\n1. Respawn\n2. Exit game",2); //this tells the user to restart or exit the game
                restart(); //this restarts or exits depending on the number of choice
            } //this is a closing curly bracket which closes the preceeding code
            new Level2().run(); //this creates a new instance of the level2 class and executes the run() method
            input("Good work! Unfortunately the gods decided to kill you.\nYou lose.\n1. Respawn\n2. Exit game",2); //this tells the user to restart or exit the game
            restart(); //this restarts or exits depending on the number of choice
        } //this is a closing curly bracket which closes the preceeding code
    } //this is a closing curly bracket which closes the preceeding code

    class Level2{ //this declares the class level1 which has a run method
        public void run(){ //this declares the run method of level which uses the input method repeatedly to simulate the first part of a text-based adventure
            input("Congratulations! You have reached level 2!\n1. continue\n2. learn more about why you're here",2); //this asks for an int input from the user with 2 possibilities
            if(choice == 2){ //this executes the following code if the choice is 2
                input("Because I told you so.\n1. continue\n2. really learn why you're here",2); //this asks for an int input from the user with 2 possibilities
                if(choice ==2){ //this executes the following code if the choice is 2
                    input("Paris, an evil Trojan prince,\nstole your friend Menelaus' wife Helen,\nthe most beautiful woman in the world.\n1. continue",1); //this asks for an int input from the user with 1 possibility
                } //this is a closing curly bracket which closes the preceeding code
            } //this is a closing curly bracket which closes the preceeding code
            input("Achilles rages against the Trojans.\nWill you follow him into battle?\n1. yes, charge\n2. no, take a break (eat and get gold)",2); //this asks for an int input from the user with 2 possibilities
            if(choice == 2){ //this executes the following code if the choice is 2
                input("Will you just eat, or will you gamble first?\n1. gamble first (to get gold)\n2. eat",2); //this asks for an int input from the user with 2 possibilities
                if(choice ==1){ //this executes the following code if the choice is 1
                    oldgold = gold; //this stores the current gold score as oldgold for use in the gamble method
                    gamble(5); //this executes the gamble method until the user has gained 5 more kleos
                } //this is a closing curly bracket which closes the preceeding code
                input("Now you will eat.\n1. eat",1); //this asks for an int input from the user with 1 possibility
                input("Now that you have put aside desire for food and drink-\n1. continue the charge",1); //this asks for an int input from the user with 1 possibility
            } else { //this is an else statement. it executes the code after it only if none of the if or else if statements above had a true parameter
                input("Then prepare to charge!\n1. continue",1); //this asks for an int input from the user with 1 possibility
                r = rnd.nextInt(2); //this generates a random integer from 0 to 1 inclusive
                if(r==1){ //this executes the following code if r is 1
                    input("You lose.\nYou charged, but you were too tired to fight.\nA Trojan warrior killed you.\n1. Respawn\n2. Exit Game",2); //this tells the user to restart or exit the game
                    restart(); //this restarts or exits depending on the number of choice
                } //this is a closing curly bracket which closes the preceeding code
            } //this is a closing curly bracket which closes the preceeding code
            oldkleos = kleos; //this stores the current kleos score as oldkleos for use in the fight method
            fight(5); //this executes the fight method until the user has gained 5 more kleos
            return; //this exits the run method and returns to the previous location in the code
        } //this is a closing curly bracket which closes the preceeding code
    } //this is a closing curly bracket which closes the preceeding code

    public synchronized void delay() { //this declares a new method update which waits until the user enters a valid input to proceed
        try { //this tries to perforom the following code, and if it doesn't work, it goes to the catch section and performs that. finally, it goes to the finally method and performs that code.
            wait(); //this uses the built in wait method to wait until the thread is notified
        } catch (InterruptedException e) { //this catches a potential exception and executes the following code if it does
            Thread.currentThread().interrupt(); // restore interrupted status
        } //this is a closing curly bracket which closes the preceeding code
    } //this is a closing curly bracket which closes the preceeding code

    public void update(){ //this declares the update method which updates and displays kleos, highscore, gold, and mostgold
        if(kleos>highscore){ //this checks if the current score is higher than the previous highscore
            highscore = kleos; //this sets highscore to the current score
        } //this is a closing curly bracket which closes the preceeding code
        if(gold>mostgold){ //this checks if the current gold is higher than the previous mostgold
            mostgold = gold; //this sets mostgold to the current gold
        } //this is a closing curly bracket which closes the preceeding code
        String text = "Kleos: "+kleos+"\nHighscore: "+highscore+"\nGold: "+gold+"\nMost gold: "+mostgold; //this creates a String with the values of kleos, highscore, gold, and mostgold
        center.setText("<html>"+text.replace("\n","<br>")+"</html>"); //this sets the text of the jlabel center to display the values of kleos, highscore, gold, and mostgold, using <html> and <br> to make the linebreaks appear correctly on the jlabel center
    } //this is a closing curly bracket which closes the preceeding code

    public void fight(int times){ //this declares the fight method with the integer parameter times which allows the user to fight until they have gained that much more kleos
        input("You encountered a Trojan.\n1. stab him with your spear",2); //this asks for an int input from the user with 2 possibilities
        r=rnd.nextInt(19)+1; //this generates a random integer from 1 to 20 inclusive
        if(r==1){ //this executes the following code if r is 1
            input("He killed you first.\n1. Respawn\n2. Exit game",2); //this tells the user to restart or exit the game
            restart(); //this restarts or exits depending on the number of choice
        } //this is a closing curly bracket which closes the preceeding code
        kleos ++; //this adds 1 to kleos
        update(); //this runs the update() method which updates and displays kleos, highscore, gold, and mostgold
        input("You killed a Trojan and gained some kleos!\n1. take his armor for extra kleos (riskier)\n2. keep fighting",2); //this asks for an int input from the user with 2 possibilities
        r = rnd.nextInt(19)+1; //this generates a random integer from 1 to 20 inclusive
        if(r<=2&&choice==1){ //this executes the following code if r is less than or equal to 2 and choice equals 1
            input("You lose.\nYou were stabbed while distracted.\n1. Respawn\n2. Exit game",2); //this tells the user to restart or exit the game
            restart(); //this restarts or exits depending on the number of choice
        } else if(r==1){ //this executes the following code if r is 1 and the previous if statements parameter is false
            input("You were shot by an archer.\n1. Respawn\n2. Exit game",2); //this tells the user to restart or exit the game
            restart(); //this restarts or exits depending on the number of choice
        } //this is a closing curly bracket which closes the preceeding code
        if(choice==1){ //this executes the following code if the choice is 1
            kleos ++; //this adds one to kleos
            update(); //this runs the update() method which updates and displays kleos, highscore, gold, and mostgold
        } //this is a closing curly bracket which closes the preceeding code
        if(kleos>=oldkleos+times){ //this checks if the user has gained enough gold to exit the fight() method
            return; //this exits the fight() method and returns to the previous position in the code
        } //this is a closing curly bracket which closes the preceeding code
        fight(times); //this calls the method again if the user has not gained enough kleos
    } //this is a closing curly bracket which closes the preceeding code

    public void gamble(int times){ //this declares a new method gamble with the integer parameter times which allows the user to gamble until they have gained that much more gold
        if(gold >=oldgold + times){ //this checks if the user has gained gold to exit the gamble() method
            return; //this exits the gamble() method and returns to the previous position in the code
        } //this is a closing curly bracket which closes the preceeding code
        r = rnd.nextInt(5)+1+rnd.nextInt(5)+1; //this generates 2 random integers between 1 and 6 inclusive and adds them to simulate rolling 2 die and stores 
        input("To win you must roll "+r+" or higher.\n1. go for it\n2. stop betting",2); //this asks for an int input from the user with 2 possibilities
        if(choice ==2){ //this executes the following code if the choice is 2
            return; //this exits the gamble() method and returns to the previous position in the code
        } //this is a closing curly bracket which closes the preceeding code
        int r2 = rnd.nextInt(5)+1+rnd.nextInt(5)+1; //this generates 2 random integers between 1 and 6 inclusive and adds them to simulate rolling 2 die
        if(r2>=r){ //this executes the following code if r2
            r = r2; //this sets r to r2 because I would rather reference r than r2 later in the code
            gold ++; //this adds 1 to gold
            update(); //this calls the update method to update the scores
            input("You rolled "+r+". \nYou won!\n1. play again\n2. quit",2); //this asks for an int input from the user with 2 possibilities
            if(choice ==1){ //this executes the following code if the choice is 1
                gamble(times);
            } //this is a closing curly bracket which closes the preceeding code
        } else { //this is an else statement. it executes the code after it only if none of the if or else if statements above had a true parameter
            r = r2; //this sets r to r2 because I would rather reference r than r2 later in the code
            gold = 0; //this sets the gold score to 0 because the player lost
            update(); //this calls the update method to update the scores
            input("You rolled "+r+". \nYou lost.\n1. quit",1); //this asks for an int input from the user with 1 possibility
        } //this is a closing curly bracket which closes the preceeding code
        return; //this exits the gamble() method and returns to the previous position in the code
    } //this is a closing curly bracket which closes the preceeding code

    public void input(String text, int pos) { //this declares the input method which inputs the text into the write method and updates the public variable possibilities
        //bottom.setText("<html>" + text.replace("\n", "<br>") + "</html>");
        write(text); //this calls on the write method which displays the inputed text one character at a time and inputs the string text
        possibilities = pos; //this updates the variable possibilities to the value of the inputed variable pos
        delay(); //this calls on the built in wait() method which waits to proceed until choice has been changed and the current thread has been notified
    } //this is a closing curly bracket which closes the preceeding code

    public void SetUpGUI() { //this declares the SetUpGUI() method which sets up the GUI, setting the frames size, title, location, close operation, visibility, and layout
        frame.setSize(width, height); //this sets the width and height of the frame which holds everything to the values of the variables width and height
        frame.setTitle("Iliad game"); //this sets the title of the frame to "Iliad game"
        frame.setLocationRelativeTo(null); //this sets the location of the frame relative to null, meaning it appears in the center of the screen. You might want to try removing this line, perhaps with comments, to get rid of the error about the X11 display variable, or you could try replacing it with 'frame.setLocationByPlatform(true);', which should make the frame appear near the top left of your screen or wherever your operating system specifies.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //this sets the default close operation so that when you close the created window, the program stops, similar to system.exit(0)
        frame.setVisible(true); //this makes the frame visible
        frame.setLayout(new FlowLayout()); //this sets the layout of the frame to a flow layout which I believe keeps components from covering one another
    } //this is a closing curly bracket which closes the preceeding code

    public void write(String text) { //this declares the write() method with the string input text which uses a timer with an actionlistener to display the inputed text one character at a time on the jlabel bottom
        timer = new Timer(50, new ActionListener() { //this declares a new timer with a delay of 50 milliseconds and with the new actionlistener so the code does thinge every time the timer 'ticks'
            private int index = 0; //this declares the int index, private to the actionlistener, with a value of 0, meant to mark the location in the string text
            private StringBuilder sb = new StringBuilder(); //this declares the stringbuilder sb which is used to make the system more efficient than creating a new string in the memory every time you set the original string to a new value

            @Override //this makes it easier to tell that the original actionPerformed() method is being overridden
            public void actionPerformed(ActionEvent e) { //this overrides the original actionPerformed() method so that the timer will perform the following code every 50 milliseconds instead of doing nothing
                if (index < text.length()) { //this executes the following code if the index or position is greater than the length of the string or past the end of it
                    char c = text.charAt(index); //this declares the char c, setting its value to the current character that is being typed, the character at the position marked by the value of indes
                    if (c == '\n') { //this executes the followin code if the current character, marked by the char c, is '/n'
                        sb.append("<br>"); //this adds <br> to the string displayed on the jlabel bottom instead of \n so that the linebreaks appear correctly on thoe jlabel bottom
                    } else { //this is an else statement. it executes the code after it only if none of the if or else if statements above had a true parameter
                        sb.append(c); //this adds the current character to the string displayed on the jlabel bottom
                    } //this is a closing curly bracket which closes the preceeding code
                    bottom.setText("<html>" + sb.toString() + "</html>"); //this sets the text of bottom to the string created by sb with <html> and </html> so that the linebreaks appear correctly
                    index++; //this adds 1 to index so that on the next iteration the code will append the following character instead of the same one
                } else { //this is an else statement. it executes the code after it only if none of the if or else if statements above had a true parameter
                    timer.stop(); //this stops the timer and what the timer is doing if the index is greater than or equal to the length of the string meant to be displayed
                } //this is a closing curly bracket which closes the preceeding code
            } //this is a closing curly bracket which closes the preceeding code
        }); //this closes the preceeding code and finishes the adding of the actionlistener to timer
        bottom.setText(""); //this is to clear the component's previous text
        timer.start(); //this starts the timer and makes it begin performing its designated action, specified by its actionlistener
    } //this is a closing curly bracket which closes the preceeding code

    public static void main(String[] args) { //this declares the public and static method main with the output void and the input of a string array referred to as args. The main method is generally the first method of a class to be run. The input args can actually be utilized by typing things after the command java Gamedisplay or whatever other java program you happen to be running. Whatever you type will be made into a string array divided by wherever you typed a space.
        new Comments(1000, 640, 0,0); //this creates a new instance of the class Gamedisplay with the inputs 1000, 640, 0, and 0 to set the values of width, height, kleos, and gold
    } //this is a closing curly bracket which closes the preceeding code
} //this is a closing curly bracket which closes the preceeding code. Also, this is the end. THE END.
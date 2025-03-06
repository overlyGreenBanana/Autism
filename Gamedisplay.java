import java.awt.event.*; //haec omnia in fasciculo java.awt.event importat, mihi permittens accedere ad contenta eius, ad methodos et classes eius referens, quia 'import java.awt.*' non satis profunde vadit.
import java.awt.*; //haec omnia in fasciculo java.awt importat, mihi permittens accedere ad contenta eius, ad methodos et classes eius referens
import javax.swing.*; //haec omnia in fasciculo javax.swing importat, mihi permittens accedere ad contenta eius, ad methodos et classes eius referens
import java.util.Scanner; //haec classem java.util.Scanner importat ut ego eam accedere et ad eam et methodos eius referre possim
import java.util.Random; //haec classem java.util.Random importat ut ego eam accedere et ad eam et methodos eius referre possim

public class Gamedisplay extends JPanel { //haec declarat classem publicam Gamedisplay, quae in archivo nominato Gamedisplay.java servari debet. Ego hanc ut JPanel declaravi ut res facere possim sicut colorem posteriorem statuere et similia.
    private int width; //ut accessum ad latitudinem et altitudinem ex quolibet loco permittat, in methodo SetUpGui() usum
    private int height; //vide supra
    private ImageIcon greek = new ImageIcon(); //pro imagine
    private JFrame frame = new JFrame(); //JFrame ut omnia contineat
    private JPanel display = new JPanel(); //JPanel ut tres JLabel contineat
    private JLabel left; //JLabel in summo sinistro
    private JLabel center; //JLabel in summo centro
    private JLabel right; //JLabel in summo dextro
    private JPanel bottomPanel = new JPanel(); //JPanel ut textum in imo et inputum contineat
    private JLabel bottom; //JLabel ut textum in imo contineat, vel forsan imagum
    private Font font = new Font("Arial", Font.PLAIN, 32); //ut mihi permittat res ad fontum magnum et facile lectum statuere
    private JTextField typing = new JTextField(); //ut usuario permittat inputum scribere
    private Timer timer; //ut mihi permittat unam litteram per tempus post moram scribere (in functione/methodo write() usum)

    private boolean word = false; //usum si vis inputum esse verbum pro numero integro (id ad verum statue, input() stringum producit et deinde id ad falsum statuit)
    private int choice; //numerus electionis eorum, publicus ut ex quolibet loco accedere possit
    private int possibilities = 1; //numerus electionum possibilium, a functione input renovatus
    //private Scanner scnr = new Scanner(System.in); non necessarius nisi terminale uti vis
    private Random rnd = new Random(); //ut permittat generationem novi numeri fortuiti sine alio Random() declarando
    private int r; //integer qui cum rnd.nextInt() resetatur
    private int oldkleos = 0; //kleos ante methodum fight utens, cum parametro times in fight() usum ut post certum incrementum kleos ultra oldkleos sistat
    private int kleos = 0; //score kleos, a methodo restart() resetatum
    private int highscore = 0; //maximus kleos ab initio programmatis adeptus, a methodo restart() non resetatur
    private int gold = 0; //score aurum, a methodo restart() resetatum
    private int oldgold = 0; //score aurum ante methodum gamble utens, similiter ut oldkleos in relatione ad fight() usum
    private int mostgold = 0; //maximus aurum ab initio programmatis adeptus, a methodo restart() non resetatur
    private String name; //haec String primo usum est cum usuario nomen suum inputit, sed reusum est quandocumque stringum inputunt et vocatur "name" propter defectum melioris, bene, nominis

    public Gamedisplay(int w, int h, int high, int most) {
        width = w; //ut latitudinem ad latitudinem inputam statuat
        height = h; //ut altitudinem ad altitudinem inputam statuat
        highscore = high; //ut highscore ex ante conservet
        mostgold = most; //ut mostgold score ex ante conservet

        ImageIcon greekimage = new ImageIcon("greekwarrior.png"); //ut imagum ad imagum bellatoris Graeci pixelati statuat
        left = new JLabel(greekimage); //ut imagum bellatoris Graeci ad JLabel sinistrum addat
        center = new JLabel("<html>Kleos: "+kleos+"<br>Highscore: "+highscore+"<br>Gold: "+gold+"<br>Most gold: "+mostgold+"</html>"); //ut kleos, highscore, aurum, et mostgold in JLabel centro ostendat. <html> est ut lineae fracturae recte in JLabel appareant
        center.setFont(font); //ut fontum JLabel centri cum highscore ad fontum bellum, magnum, lectum statuat
        right = new JLabel(greekimage); //ut imagum bellatoris Graeci ad JLabel dextrum addat
        display.add(left); //ut JLabel sinistrum ad panel display addat
        display.add(center); //ut JLabel centrum ad panel display addat
        display.add(right); //ut JLabel dextrum ad panel display addat

        //JPanel bottomPanel = new JPanel(); haec linea nunc non necessaria est et bottomPanel ut JPanel initio declaravi
        bottom = new JLabel(); //ut bottom ut JLabel initio statuat
        bottom.setFont(font); //ut fontum bottom ad fontum bellum, magnum, lectum statuat
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS)); //ut faciat JLabel bottom et JTextField typing verticaliter alignata permaneant
        bottomPanel.add(bottom); //ut JLabel bottom ad bottomPanel addat

        typing = new JTextField(10); //ut textfield typing initio statuat. Non omnia initio declaro quia volo expectare donec omnia quae mihi necessaria sunt habeam, ut id quomodo volo declaretur. Exempli gratia, JLabel tantum post imagum quam volui addere declaravi. Si ea declaro et deinde muto, male spectat.
        typing.setFont(font); //ut fontum typing ad fontum bellum, magnum, lectum statuat

        typing.addActionListener(new ActionListener() { //ut actionlistener addat ut codex res faciat cum usuario "enter" premit
            @Override //hoc facilius ostendit methodum originalem actionPerformed() superari
            public void actionPerformed(ActionEvent e) { //hoc methodum actionPerformed() superat ut codum addat pro quando usuario "enter" premit
                synchronized (Gamedisplay.this) { //hoc reliquum codum cum hoc synchronizat ut expectet donec inputum obtinuit antequam cum reliquo codice procedat
                    try { //hoc sequentem codum perficere conatur, et si non functum est, ad sectionem catch vadit et id perficit. Denique ad methodum finally vadit et illum codum perficit.
                        if(typing.getText().equals("exit")){ //ut programmatum finire possim scribendo "exit"
                            System.exit(0); //hoc programmatum sine aliquid aliud faciendo sistit. Puto parameter diversas res facit secundum numerum, et default est 0.
                        } //haec est clausura uncinata quae codum praecedentem claudit
                        if(timer.isRunning()){ //hoc usuario expectare facit donec textus plene ostenditur, et nihil facit si non currit
                            return; //hoc ad codum redit, iterum usuario expectans ut "enter" premat
                        } //haec est clausura uncinata quae codum praecedentem claudit
                        if (word) { //hoc usuario stringum inputere permittit si booleanum word verum est
                            name = typing.getText(); //hoc textum textfield typing ut stringum accipit
                            typing.setText(""); //hoc textum typing ad "" resetat si inputum acceptum est
                            word = false; //hoc word ad falsum mutat, ut proximum inputum integer sit nisi aliter specificatum
                            Gamedisplay.this.notifyAll(); //hoc reliquum codum notificat ut procedere possit quia inputum validum adeptum est
                        } else { //haec est sententia else. Codum post eam exequitur tantum si nulla ex sententiis if vel else if supra parameter verum habuit
                            choice = Integer.parseInt(typing.getText()); //hoc integer choice ad textum typing statuit, sed ut integer
                            typing.setText(""); //hoc textum typing ad "" resetat si inputum acceptum est
                            if (choice <= 0 || choice > possibilities) { //hoc errorem emittit si electio non intra fines validos est
                                JOptionPane.showMessageDialog(frame, "Quaeso electionem validam scribe.", "Inputum Invalidum", JOptionPane.ERROR_MESSAGE); //hoc nuntium erroris ostendit modo quo gravis videtur
                            } else { //haec est sententia else. Codum post eam exequitur tantum si nulla ex sententiis if vel else if supra parameter verum habuit
                                Gamedisplay.this.notifyAll(); //hoc reliquum codum notificat ut procedere possit quia inputum validum adeptum est
                            } //haec est clausura uncinata quae codum praecedentem claudit
                        } //haec est clausura uncinata quae codum praecedentem claudit
                    } catch (NumberFormatException ex) { //hoc errorem capit si inputum ad integer converti non potest
                        typing.setText(""); //hoc textum typing ad "" resetat si inputum acceptum est
                        JOptionPane.showMessageDialog(frame, "Quaeso integer validum scribe.", "Inputum Invalidum", JOptionPane.ERROR_MESSAGE); //hoc nuntium erroris ostendit modo quo gravis videtur
                    } //haec est clausura uncinata quae codum praecedentem claudit
                } //haec est clausura uncinata quae codum praecedentem claudit
            } //haec est clausura uncinata quae codum praecedentem claudit
        }); //hoc codum praecedens claudit et additionem actionlistener ad typing finit

        bottomPanel.add(typing); //hoc typing ad bottompanel addit

        frame.add(display); //hoc JPanel display ad JFrame frame addit
        frame.add(bottomPanel); //hoc JPanel bottompanel ad JFrame frame addit
        SetUpGUI(); //hoc GUI instruit, latitudinem ad width, altitudinem ad height statuens, operationem clausurae statuens, et JFrame frame visibile faciens
        input("Es bellator Graecus. (Scribe 1 vel 2)\n1. Ego vir sum.\n2. Ego femina sum.",2); //hoc pro inputum integer ab usuario quaerit cum 2 possibilitatibus
        if(choice == 2){ //hoc codum sequens exequitur si electio est 2
            input("Perdis.\nNon es bellator. Redi ad texendum.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
            restart(); //hoc recomencet vel exit secundum numerum electionis
        } //haec est clausura uncinata quae codum praecedentem claudit
        word = true; //hoc booleanum word ad verum statuit, permittens inputum stringum
        input("Quid est nomen tuum?", 1); //hoc pro nomine usuario quaerit, inputum ut stringum reponens

        switch(name){ //hoc switch cum stringum name ut parameter creat, quod cum variis rebus per 'case' comparari potest
            case "level1":{ //hoc codum sequens exequitur si name aequale est stringum inputum
                new Level1().run(); //hoc novam instantiam classis Level1 creat et methodum run() exequitur
            } //haec est clausura uncinata quae codum praecedentem claudit
            case "level2":{ //hoc codum sequens exequitur si name aequale est stringum inputum
                new Level2().run(); //hoc novam instantiam classis Level2 creat et methodum run() exequitur
            } //haec est clausura uncinata quae codum praecedentem claudit
            case "Hector": //hoc ad proximam clausuram uncinatam apertam procedit si name aequale est stringum inputum
            case "hector": //hoc ad proximam clausuram uncinatam apertam procedit si name aequale est stringum inputum
            case "Paris": //hoc ad proximam clausuram uncinatam apertam procedit si name aequale est stringum inputum
            case "paris":{ //hoc codum sequens exequitur si name aequale est stringum inputum
                input("Perdis.\nEs Troianus.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
                restart(); //hoc recomencet vel exit secundum numerum electionis
            } //haec est clausura uncinata quae codum praecedentem claudit
            case "Achilles": //hoc ad proximam clausuram uncinatam apertam procedit si name aequale est stringum inputum
            case "achilles":{ //hoc codum sequens exequitur si name aequale est stringum inputum
                input("Esne lacrimaturus ad matrem tuam?\n1. sic\n2. non",2); //hoc pro inputum integer ab usuario quaerit cum 2 possibilitatibus
                if(choice == 1){ //hoc codum sequens exequitur si electio est 1
                    input("Perdis.\nEs timidus et sine victoria morieris.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
                    restart(); //hoc recomencet vel exit secundum numerum electionis
                } else { //haec est sententia else. Codum post eam exequitur tantum si nulla ex sententiis if vel else if supra parameter verum habuit
                    input("Perdis.\nNon licet tibi esse character principalis.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
                    restart(); //hoc recomencet vel exit secundum numerum electionis
                } //haec est clausura uncinata quae codum praecedentem claudit
            } //haec est clausura uncinata quae codum praecedentem claudit
            case "Odysseus": //hoc ad proximam clausuram uncinatam apertam procedit si name aequale est stringum inputum
            case "odysseus": //hoc ad proximam clausuram uncinatam apertam procedit si name aequale est stringum inputum
            case "Odyseus": //hoc ad proximam clausuram uncinatam apertam procedit si name aequale est stringum inputum
            case "odyseus": //hoc ad proximam clausuram uncinatam apertam procedit si name aequale est stringum inputum
            case "Oddysseus": //hoc ad proximam clausuram uncinatam apertam procedit si name aequale est stringum inputum
            case "oddysseus": //hoc ad proximam clausuram uncinatam apertam procedit si name aequale est stringum inputum
            case "Oddyseus": //hoc ad proximam clausuram uncinatam apertam procedit si name aequale est stringum inputum
            case "oddyseus":{ //hoc codum sequens exequitur si name aequale est stringum inputum
                input("Nullum kleos ex hoc obtinebis.\nSuperavives et tantum kleos ex reditu tuo obtinere potes.\n1. iterum tenta\n2. desiste",2); //hoc usuario dicit ut iterum tentet vel desistat
                restart(); //hoc recomencet vel exit secundum numerum electionis
            } //haec est clausura uncinata quae codum praecedentem claudit
            case "Menelaus": //hoc ad proximam clausuram uncinatam apertam procedit si name aequale est stringum inputum
            case "menelaus": //hoc ad proximam clausuram uncinatam apertam procedit si name aequale est stringum inputum
            case "Agamemnon": //hoc ad proximam clausuram uncinatam apertam procedit si name aequale est stringum inputum
            case "agamemnon":{ //hoc codum sequens exequitur si name aequale est stringum inputum
                input("Non potes esse character principalis quia sunt magni momenti\nad fabulam et non potes credi facere\nquod ab eis requiritur.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
                restart(); //hoc recomencet vel exit secundum numerum electionis
            } //haec est clausura uncinata quae codum praecedentem claudit
            case "Helen": //hoc ad proximam clausuram uncinatam apertam procedit si name aequale est stringum inputum
            case "helen":{ //hoc codum sequens exequitur si name aequale est stringum inputum
                input("Perdis.\nNon es bellator. Redi ad texendum.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
                restart(); //hoc recomencet vel exit secundum numerum electionis
            } //haec est clausura uncinata quae codum praecedentem claudit
        } //haec est clausura uncinata quae codum praecedentem claudit

        input(name + ", consentiesne agere modo\ndigno vero bellatore?\n1. Consentio\n2. Dissento", 2); //hoc pro inputum integer ab usuario quaerit cum 2 possibilitatibus
        if (choice == 2) { //hoc codum sequens exequitur si electio est 2
            input("Sic consenties.\n1. Consentio", 1); //hoc usuario pro inputum integer quaerit cum 1 possibilitate
        } //haec est clausura uncinata quae codum praecedentem claudit
        input("Honorem et gloriam valoras?\n1. Sic\n2. Non", 2); //hoc pro inputum integer ab usuario quaerit cum 2 possibilitatibus
        if(choice == 2){ //hoc codum sequens exequitur si electio est 2
            input("Falsum. Perdis.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
            restart(); //hoc recomencet vel exit secundum numerum electionis
        } //haec est clausura uncinata quae codum praecedentem claudit
        input("Pro eis pugnabis?\n1. Sic\n2. Non", 2); //hoc pro inputum integer ab usuario quaerit cum 2 possibilitatibus
        if (choice == 1) { //hoc codum sequens exequitur si electio est 1
            word = true; //hoc booleanum word ad verum statuit, permittens inputum stringum ab usuario
            input("Recte. Scribe c ut ad level 1 eas.\nScribe exit ut lusum exeas.",1);
            if(name.equals("C")||name.equals("c")){ //hoc codum sequens exequitur si stringum name est "C" vel "c"
                new Level1().run(); //hoc novam instantiam classis Level1 declarat et methodum run exequitur
            } else { //haec est sententia else. Codum post eam exequitur tantum si nulla ex sententiis if vel else if supra parameter verum habuit
                frame.dispose(); //hoc frame praecedens disponit
                new Gamedisplay(1000,640,0,0); //hoc novam instantiam classis Gamedisplay creat, lusum recomencens et omnia ad 0 statuens quia usuario directiones sequi non potest
            } //haec est clausura uncinata quae codum praecedentem claudit
        } else { //haec est sententia else. Codum post eam exequitur tantum si nulla ex sententiis if vel else if supra parameter verum habuit
            input("Falsum. Perdis.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
            restart(); //hoc recomencet vel exit secundum numerum electionis
        } //haec est clausura uncinata quae codum praecedentem claudit
        // bottomPanel.remove(typing); haec linea non necessaria est, sed JTextField typing removeret et usuario quicquam inputere prohiberet
    } //haec est clausura uncinata quae codum praecedentem claudit

    public void restart() { //hoc recomencet vel exit secundum numerum electionis
        update(); //hoc methodum update() currit et kleos, highscore, aurum, et mostgold renovat, valores novos in JLabel centro ostendens
        if(choice==1){ //hoc codum sequens exequitur si electio est 1
        frame.dispose(); //hoc frame praecedens disponit
        new Gamedisplay(1000,640,highscore,mostgold); //hoc novam instantiam classis Gamedisplay creat, highscore et mostgold score conservans eos ut parametrus ad novum Gamedisplay inputens
        } else if(choice == 2){ //hoc codum sequens exequitur si electio est 2 et parameter sententiae if praecedentis falsum est
        System.exit(0); //hoc programmatum sistit sine aliquid interesting faciendo
        } //haec est clausura uncinata quae codum praecedentem claudit
    } //haec est clausura uncinata quae codum praecedentem claudit

    class Level1{ //hoc classem Level1 declarat quae methodum run habet
        public void run(){ //hoc methodum run classis Level1 declarat quae methodum input repetite utitur ad primam partem adventurae textu fundatae simulandam
            input("Gratulationes! Ad level 1 pervenisti!\n1. continua\n2. disce cur hic sis",2); //hoc pro inputum integer ab usuario quaerit cum 2 possibilitatibus
            if(choice == 2){ //hoc codum sequens exequitur si electio est 2
                input("Quia ego tibi dixi.\n1. continua\n2. disce cur vere hic sis",2); //hoc pro inputum integer ab usuario quaerit cum 2 possibilitatibus
                if(choice ==2){ //hoc codum sequens exequitur si electio est 2
                    input("Quia uxor amici tui a principe Troiano rapta est.\n1. continua",1); //hoc pro inputum integer ab usuario quaerit cum 1 possibilitate
                } //haec est clausura uncinata quae codum praecedentem claudit
            } //haec est clausura uncinata quae codum praecedentem claudit
            r = rnd.nextInt(19)+1; //hoc integer fortuitum ab 1 ad 20 inclusive generat
            if(r==1){ //hoc codum sequens exequitur si r est 1
                input("De nave cecidisti.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
                restart(); //hoc recomencet vel exit secundum numerum electionis
            } //haec est clausura uncinata quae codum praecedentem claudit
            input("Esne paratus impetum facere?\n1. Sic\n2. Non",2); //hoc pro inputum integer ab usuario quaerit cum 2 possibilitatibus
            if(choice==2){ //hoc codum sequens exequitur si electio est 2
                input("Perdis.\nEs timidus.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
                restart(); //hoc recomencet vel exit secundum numerum electionis
            } //haec est clausura uncinata quae codum praecedentem claudit
            r = rnd.nextInt(19)+1; //hoc integer fortuitum ab 1 ad 20 inclusive generat
            if(r==1){ //hoc codum sequens exequitur si r est 1
                input("Perdis.\nA bellatore Troiano necatus es.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
                restart(); //hoc recomencet vel exit secundum numerum electionis
            } //haec est clausura uncinata quae codum praecedentem claudit
            kleos ++; //hoc 1 ad kleos addit
            update(); //hoc methodum update() currit quae kleos, highscore, aurum, et mostgold renovat et ostendit
            input("Gratulationes!\nPrimam impetum supervixisti! (non quod quicquam fecisti)\n1. continua\n2. pausa fac",2); //hoc pro inputum integer ab usuario quaerit cum 2 possibilitatibus
            if(choice == 2){ //hoc codum sequens exequitur si electio est 2
                input("Nunc quod desiderium cibi et potus deposuisti-\n1. impetum continua",1); //hoc pro inputum integer ab usuario quaerit cum 1 possibilitate
            } //haec est clausura uncinata quae codum praecedentem claudit
            r = rnd.nextInt(19)+1; //hoc integer fortuitum ab 1 ad 20 inclusive generat
            if(r==1){ //hoc codum sequens exequitur si r est 1
                input("Perdis.\nA sagittario Troiano necatus es.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
                restart(); //hoc recomencet vel exit secundum numerum electionis
            } //haec est clausura uncinata quae codum praecedentem claudit
            input("Primus Troianus tibi occurrit!\n1. eum arcu tuo sagitta\n2. eum hasta tua confode",2); //hoc pro inputum integer ab usuario quaerit cum 2 possibilitatibus
            if(choice ==1){ //hoc codum sequens exequitur si electio est 1
                input("Arcum non habes.\n1. eum hasta tua confode",2); //hoc pro inputum integer ab usuario quaerit cum 2 possibilitatibus
                r = rnd.nextInt(19)+1; //hoc integer fortuitum ab 1 ad 20 inclusive generat
                if(r==1){ //hoc codum sequens exequitur si r est 1
                    input("Perdis.\nNimium tempus decidendo cepisti et ille te necavit.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
                    restart(); //hoc recomencet vel exit secundum numerum electionis
                } //haec est clausura uncinata quae codum praecedentem claudit
            } //haec est clausura uncinata quae codum praecedentem claudit
            r=rnd.nextInt(19)+1; //hoc integer fortuitum ab 1 ad 20 inclusive generat
            if(r==1){ //hoc codum sequens exequitur si r est 1
                input("Ille te primum necavit.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
                restart(); //hoc recomencet vel exit secundum numerum electionis
            } //haec est clausura uncinata quae codum praecedentem claudit
            kleos ++; //hoc 1 ad kleos addit
            update(); //hoc methodum update() currit quae kleos, highscore, aurum, et mostgold renovat et ostendit
            input("Primus Troianus tuus occisus est et kleos aliquod obtinuisti!\n1. arma eius cape pro extra kleos (periculosius)\n2. pugna continua",2); //hoc pro inputum integer ab usuario quaerit cum 2 possibilitatibus
            r = rnd.nextInt(19)+1; //hoc integer fortuitum ab 1 ad 20 inclusive generat
            if(r<=2){ //hoc codum sequens exequitur si r minus vel aequale 2 est
                if(choice==1&&r==1){ //hoc codum sequens exequitur si electio est 1 et r est 1
                    input("Perdis.\nDistractus confossus es.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
                    restart(); //hoc recomencet vel exit secundum numerum electionis
                } else {  //haec est sententia else. Codum post eam exequitur tantum si nulla ex sententiis if vel else if supra parameter verum habuit
                    input("A sagittario sagittatus es.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
                    restart(); //hoc recomencet vel exit secundum numerum electionis
                } //haec est clausura uncinata quae codum praecedentem claudit
            } //haec est clausura uncinata quae codum praecedentem claudit
            if(choice ==1){ //hoc codum sequens exequitur si electio est 1
                kleos ++; //hoc unum ad kleos addit
                update(); //hoc methodum update() currit quae kleos, highscore, aurum, et mostgold renovat et ostendit
            } //haec est clausura uncinata quae codum praecedentem claudit
            oldkleos = kleos; //hoc valorem kleos praecedentem ut oldkleos reponit pro usu in methodo fight()
            fight(5-kleos); //hoc methodum fight() exequitur, donec score kleos usoris maior vel aequalis 5 sit
            r = rnd.nextInt(19)+1; //hoc integer fortuitum ab 1 ad 20 inclusive generat
            if(r==1){ //hoc codum sequens exequitur si r est 1
                input("Bene factum! Infeliciter di te occidere decreverunt.\nPerdis.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
                restart(); //hoc recomencet vel exit secundum numerum electionis
            } //haec est clausura uncinata quae codum praecedentem claudit
            new Level2().run(); //hoc novam instantiam classis Level2 creat et methodum run() exequitur
            input("Bene factum! Infeliciter di te occidere decreverunt.\nPerdis.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
            restart(); //hoc recomencet vel exit secundum numerum electionis
        } //haec est clausura uncinata quae codum praecedentem claudit
    } //haec est clausura uncinata quae codum praecedentem claudit

    class Level2{ //hoc classem Level2 declarat quae methodum run habet
        public void run(){ //hoc methodum run classis Level2 declarat quae methodum input repetite utitur ad primam partem adventurae textu fundatae simulandam
            input("Gratulationes! Ad level 2 pervenisti!\n1. continua\n2. disce plus de cur hic sis",2); //hoc pro inputum integer ab usuario quaerit cum 2 possibilitatibus
            if(choice == 2){ //hoc codum sequens exequitur si electio est 2
                input("Quia ego tibi dixi.\n1. continua\n2. vere disce cur hic sis",2); //hoc pro inputum integer ab usuario quaerit cum 2 possibilitatibus
                if(choice ==2){ //hoc codum sequens exequitur si electio est 2
                    input("Paris, princeps Troianus malus,\nuxorem amici tui Menelai Helenam,\npulcherrimam mundi mulierem, rapuit.\n1. continua",1); //hoc pro inputum integer ab usuario quaerit cum 1 possibilitate
                } //haec est clausura uncinata quae codum praecedentem claudit
            } //haec est clausura uncinata quae codum praecedentem claudit
            input("Achilles contra Troianos furit.\nEum in proelium sequeris?\n1. sic, impetum fac\n2. non, pausa fac (ede et aurum cape)",2); //hoc pro inputum integer ab usuario quaerit cum 2 possibilitatibus
            if(choice == 2){ //hoc codum sequens exequitur si electio est 2
                input("Solum edes, an prius aleabis?\n1. prius alea (ut aurum capias)\n2. ede",2); //hoc pro inputum integer ab usuario quaerit cum 2 possibilitatibus
                if(choice ==1){ //hoc codum sequens exequitur si electio est 1
                    oldgold = gold; //hoc score aurum currente ut oldgold reponit pro usu in methodo gamble
                    gamble(5); //hoc methodum gamble exequitur donec usuario 5 plus kleos obtinuit
                } //haec est clausura uncinata quae codum praecedentem claudit
                input("Nunc edes.\n1. ede",1); //hoc pro inputum integer ab usuario quaerit cum 1 possibilitate
                input("Nunc quod desiderium cibi et potus deposuisti-\n1. impetum continua",1); //hoc pro inputum integer ab usuario quaerit cum 1 possibilitate
            } else { //haec est sententia else. Codum post eam exequitur tantum si nulla ex sententiis if vel else if supra parameter verum habuit
                input("Tunc impetum para!\n1. continua",1); //hoc pro inputum integer ab usuario quaerit cum 1 possibilitate
                r = rnd.nextInt(2); //hoc integer fortuitum ab 0 ad 1 inclusive generat
                if(r==1){ //hoc codum sequens exequitur si r est 1
                    input("Perdis.\nImpetum fecisti, sed nimis fessus eras ad pugnandum.\nBellator Troianus te necavit.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
                    restart(); //hoc recomencet vel exit secundum numerum electionis
                } //haec est clausura uncinata quae codum praecedentem claudit
            } //haec est clausura uncinata quae codum praecedentem claudit
            oldkleos = kleos; //hoc score kleos currente ut oldkleos reponit pro usu in methodo fight
            fight(5); //hoc methodum fight exequitur donec usuario 5 plus kleos obtinuit
            return; //hoc methodum run exit et ad locum praecedentem in codice redit
        } //haec est clausura uncinata quae codum praecedentem claudit
    } //haec est clausura uncinata quae codum praecedentem claudit

    public synchronized void delay() { //hoc novam methodum update declarat quae expectat donec usuario inputum validum intrat ut procedat
        try { //hoc sequentem codum perficere conatur, et si non functum est, ad sectionem catch vadit et id perficit. Denique ad methodum finally vadit et illum codum perficit.
            wait(); //hoc methodum wait() inaedificatam utitur ut expectet donec filum notificatum est
        } catch (InterruptedException e) { //hoc exceptionem potentialem capit et codum sequens exequitur si id facit
            Thread.currentThread().interrupt(); //statum interruptum restaurat
        } //haec est clausura uncinata quae codum praecedentem claudit
    } //haec est clausura uncinata quae codum praecedentem claudit

    public void update(){ //hoc methodum update declarat quae kleos, highscore, aurum, et mostgold renovat et ostendit
        if(kleos>highscore){ //hoc spectat si score currente altior est quam highscore praecedens
            highscore = kleos; //hoc highscore ad score currente statuit
        } //haec est clausura uncinata quae codum praecedentem claudit
        if(gold>mostgold){ //hoc spectat si aurum currente altior est quam mostgold praecedens
            mostgold = gold; //hoc mostgold ad aurum currente statuit
        } //haec est clausura uncinata quae codum praecedentem claudit
        String text = "Kleos: "+kleos+"\nHighscore: "+highscore+"\nGold: "+gold+"\nMost gold: "+mostgold; //hoc Stringum cum valoribus kleos, highscore, aurum, et mostgold creat
        center.setText("<html>"+text.replace("\n","<br>")+"</html>"); //hoc textum JLabel centri statuit ut valores kleos, highscore, aurum, et mostgold ostendat, <html> et <br> utendo ut lineae fracturae recte in JLabel centro appareant
    } //haec est clausura uncinata quae codum praecedentem claudit

    public void fight(int times){ //hoc methodum fight declarat cum parametro integer times quod usuario pugnare permittit donec tantum plus kleos obtinuit
        input("Troianus tibi occurrit.\n1. eum hasta tua confode",2); //hoc pro inputum integer ab usuario quaerit cum 2 possibilitatibus
        r=rnd.nextInt(19)+1; //hoc integer fortuitum ab 1 ad 20 inclusive generat
        if(r==1){ //hoc codum sequens exequitur si r est 1
            input("Ille te primum necavit.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
            restart(); //hoc recomencet vel exit secundum numerum electionis
        } //haec est clausura uncinata quae codum praecedentem claudit
        kleos ++; //hoc 1 ad kleos addit
        update(); //hoc methodum update() currit quae kleos, highscore, aurum, et mostgold renovat et ostendit
        input("Troianum occidisti et kleos aliquod obtinuisti!\n1. arma eius cape pro extra kleos (periculosius)\n2. pugna continua",2); //hoc pro inputum integer ab usuario quaerit cum 2 possibilitatibus
        r = rnd.nextInt(19)+1; //hoc integer fortuitum ab 1 ad 20 inclusive generat
        if(r<=2&&choice==1){ //hoc codum sequens exequitur si r minus vel aequale 2 est et electio aequatur 1
            input("Perdis.\nDistractus confossus es.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
            restart(); //hoc recomencet vel exit secundum numerum electionis
        } else if(r==1){ //hoc codum sequens exequitur si r est 1 et parameter sententiae if praecedentis falsum est
            input("A sagittario sagittatus es.\n1. Respawn\n2. Exi lusum",2); //hoc usuario dicit ut lusum recomencet vel exeat
            restart(); //hoc recomencet vel exit secundum numerum electionis
        } //haec est clausura uncinata quae codum praecedentem claudit
        if(choice==1){ //hoc codum sequens exequitur si electio est 1
            kleos ++; //hoc unum ad kleos addit
            update(); //hoc methodum update() currit quae kleos, highscore, aurum, et mostgold renovat et ostendit
        } //haec est clausura uncinata quae codum praecedentem claudit
        if(kleos>=oldkleos+times){ //hoc spectat si usuario satis kleos obtinuit ut methodum fight() exeant
            return; //hoc methodum fight() exit et ad locum praecedentem in codice redit
        } //haec est clausura uncinata quae codum praecedentem claudit
        fight(times); //hoc methodum iterum vocat si usuario satis kleos non obtinuit
    } //haec est clausura uncinata quae codum praecedentem claudit

    public void gamble(int times){ //hoc novam methodum gamble declarat cum parametro integer times quod usuario aleare permittit donec tantum plus aurum obtinuit
        if(gold >=oldgold + times){ //hoc spectat si usuario aurum obtinuit ut methodum gamble() exeant
            return; //hoc methodum gamble() exit et ad locum praecedentem in codice redit
        } //haec est clausura uncinata quae codum praecedentem claudit
        r = rnd.nextInt(5)+1+rnd.nextInt(5)+1; //hoc duos integeres fortuitos inter 1 et 6 inclusive generat et eos addit ut duos dados iacere simulet et reponit
        input("Ut vincas "+r+" vel altius iacere debes.\n1. tenta\n2. desiste aleando",2); //hoc pro inputum integer ab usuario quaerit cum 2 possibilitatibus
        if(choice ==2){ //hoc codum sequens exequitur si electio est 2
            return; //hoc methodum gamble() exit et ad locum praecedentem in codice redit
        } //haec est clausura uncinata quae codum praecedentem claudit
        int r2 = rnd.nextInt(5)+1+rnd.nextInt(5)+1; //hoc duos integeres fortuitos inter 1 et 6 inclusive generat et eos addit ut duos dados iacere simulet
        if(r2>=r){ //hoc codum sequens exequitur si r2
            r = r2; //hoc r ad r2 statuit quia malo r quam r2 postea in codice referre
            gold ++; //hoc 1 ad aurum addit
            update(); //hoc methodum update vocat ut scores renovet
            input("Tu "+r+" iecisti. \nVicisti!\n1. iterum ludas\n2. desiste",2); //hoc pro inputum integer ab usuario quaerit cum 2 possibilitatibus
            if(choice ==1){ //hoc codum sequens exequitur si electio est 1
                gamble(times);
            } //haec est clausura uncinata quae codum praecedentem claudit
        } else { //haec est sententia else. Codum post eam exequitur tantum si nulla ex sententiis if vel else if supra parameter verum habuit
            r = r2; //hoc r ad r2 statuit quia malo r quam r2 postea in codice referre
            gold = 0; //hoc score aurum ad 0 statuit quia lusor perdidit
            update(); //hoc methodum update vocat ut scores renovet
            input("Tu "+r+" iecisti. \nPerdidisti.\n1. desiste",1); //hoc pro inputum integer ab usuario quaerit cum 1 possibilitate
        } //haec est clausura uncinata quae codum praecedentem claudit
        return; //hoc methodum gamble() exit et ad locum praecedentem in codice redit
    } //haec est clausura uncinata quae codum praecedentem claudit

    public void input(String text, int pos) { //hoc methodum input declarat quae textum in methodum write inputit et variabilis publicae possibilitates renovat
        //bottom.setText("<html>" + text.replace("\n", "<br>") + "</html>");
        write(text); //hoc methodum write vocat quae textum inputum unum characterem per tempus ostendit et stringum text inputit
        possibilities = pos; //hoc variabilis possibilitates ad valorem variabilis inputae pos renovat
        delay(); //hoc methodum wait() inaedificatam vocat quae expectat ut procedat donec choice mutatum est et filum currente notificatum est
    } //haec est clausura uncinata quae codum praecedentem claudit

    public void SetUpGUI() { //hoc methodum SetUpGUI() declarat quae GUI instruit, magnitudem frame, titulum, locum, operationem clausurae, visibilitatem, et layout statuens
        frame.setSize(width, height); //hoc latitudinem et altitudinem frame qui omnia continet ad valores variabilium width et height statuit
        frame.setTitle("Lusus Iliadis"); //hoc titulum frame ad "Lusus Iliadis" statuit
        frame.setLocationRelativeTo(null); //hoc locum frame relativum ad null statuit, significans id in centro screen apparere. Forsan hanc lineam removere velis, fortasse cum commentariis, ut errorem de variabili X11 display tollas, vel id cum 'frame.setLocationByPlatform(true);' substituere potes, quod frame prope summum sinistrum screen tui apparere faciat vel ubicumque systema tuum operandi specificat.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //hoc operationem clausurae default statuit ut, cum fenestram creatam claudis, programmatum sistat, simile System.exit(0)
        frame.setVisible(true); //hoc frame visibile facit
        frame.setLayout(new FlowLayout()); //hoc layout frame ad flow layout statuit quod credo componentes ab invicem se tegere prohibet
    } //haec est clausura uncinata quae codum praecedentem claudit

    public void write(String text) { //hoc methodum write() declarat cum string inputum text quod timer cum actionlistener utitur ut textum inputum unum characterem per tempus in JLabel bottom ostendat
        timer = new Timer(50, new ActionListener() { //hoc novum timer declarat cum mora 50 millisekundorum et cum novo actionlistener ut codex res faciat omni tempore timer 'tick'
            private int index = 0; //hoc integer index declarat, privatum ad actionlistener, cum valore 0, destinatum ad locum in stringum text notare
            private StringBuilder sb = new StringBuilder(); //hoc StringBuilder sb declarat quod usum est ut systema efficacius sit quam novum stringum in memoria creare omni tempore quo stringum originale ad novum valorem statuis

            @Override //hoc facilius ostendit methodum originalem actionPerformed() superari
            public void actionPerformed(ActionEvent e) { //hoc methodum actionPerformed() originalem superat ut timer sequentem codum omni 50 millisekundis perficiat pro nihilo faciendo
                if (index < text.length()) { //hoc codum sequens exequitur si index vel positio maior est quam longitudo stringum vel praeter finem eius
                    char c = text.charAt(index); //hoc char c declarat, valorem eius ad characterem currentem qui scribitur statuens, characterem ad positionem ab valore index notatam
                    if (c == '\n') { //hoc codum sequens exequitur si character currentis, a char c notatus, est '/n'
                        sb.append("<br>"); //hoc <br> ad stringum in JLabel bottom ostensum addit pro \n ut lineae fracturae recte in JLabel bottom appareant
                    } else { //haec est sententia else. Codum post eam exequitur tantum si nulla ex sententiis if vel else if supra parameter verum habuit
                        sb.append(c); //hoc characterem currentem ad stringum in JLabel bottom ostensum addit
                    } //haec est clausura uncinata quae codum praecedentem claudit
                    bottom.setText("<html>" + sb.toString() + "</html>"); //hoc textum bottom ad stringum a sb creatum statuit cum <html> et </html> ut lineae fracturae recte appareant
                    index++; //hoc 1 ad index addit ut in proxima iteratione codex characterem sequentem pro eodem appendat
                } else { //haec est sententia else. Codum post eam exequitur tantum si nulla ex sententiis if vel else if supra parameter verum habuit
                    timer.stop(); //hoc timer sistit et quid timer facit si index maior vel aequalis longitudini stringum ostendendae est
                } //haec est clausura uncinata quae codum praecedentem claudit
            } //haec est clausura uncinata quae codum praecedentem claudit
        }); //hoc codum praecedens claudit et additionem actionlistener ad timer finit
        bottom.setText(""); //hoc est ut textum praecedentem componentis purget
        timer.start(); //hoc timer incipit et facit ut actionem suam designatam, ab actionlistener specificatam, perficere incipiat
    } //haec est clausura uncinata quae codum praecedentem claudit

    public static void main(String[] args) { //hoc methodum publicam et staticam main declarat cum output void et inputum array stringum ut args referendum. Methodus main generaliter prima methodus classis currenda est. Inputum args actu uti potest scribendo res post commandum java Gamedisplay vel quodcumque aliud programmatum java currere accidit. Quicquid scribis in array stringum dividetur ubicumque spatium scripsisti.
        new Gamedisplay(1000, 640, 0,0); //hoc novam instantiam classis Gamedisplay creat cum inputibus 1000, 640, 0, et 0 ut valores width, height, kleos, et gold statuat
    } //haec est clausura uncinata quae codum praecedentem claudit
} //haec est clausura uncinata quae codum praecedentem claudit. Etiam, hoc est finis. FINIS.

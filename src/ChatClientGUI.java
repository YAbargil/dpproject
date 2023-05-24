import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ChatClientGUI implements StringConsumer, StringProducer {
    JTextArea textArea ;
    JScrollPane areaScrollPane;
    private StringConsumer consumer;
    private JFrame frame;
    private JLabel ipAddressField, portField,nameField,errorField;
    private JTextField messageText, ipText,portText,nameText;
    private JPanel panelSouth, panelCenter, panelNorth,panelForm;
    private JButton btConnect, btDisconnect,bt;
    private IState state;

    public ChatClientGUI() {
        //panels
        frame = new JFrame();
        panelSouth = new JPanel();
        panelCenter = new JPanel();
        panelNorth = new JPanel(new GridLayout());

        //buttons
        bt = new JButton("Send");
        btConnect = new JButton("Connect");
        btDisconnect = new JButton("Disconnect");

        //labels
        ipAddressField= new JLabel("Server");
        nameField=new JLabel("Nickname");
        portField=new JLabel("Port");

        //text field
        messageText = new JTextField(30);
        ipText =new JTextField(5);
        portText=new JTextField(5);
        nameText=new JTextField(5);

        //other
        textArea = new JTextArea(16, 58);
        areaScrollPane = new JScrollPane(textArea);
    }

    public void start() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //north
        panelNorth.setBackground(Color.LIGHT_GRAY);
        panelNorth.add(nameField,BorderLayout.WEST);
        panelNorth.add(nameText,BorderLayout.WEST);
        panelNorth.add(ipAddressField,BorderLayout.WEST);
        panelNorth.add(ipText,BorderLayout.WEST);
        panelNorth.add(portField,BorderLayout.WEST);
        panelNorth.add(portText,BorderLayout.WEST);
        panelNorth.add(btConnect,BorderLayout.EAST);
        panelNorth.add(btDisconnect,BorderLayout.EAST);

        //center
        panelCenter.setBackground(Color.white);
        panelCenter.setBorder(new TitledBorder(new EtchedBorder(), "Chat"));
        areaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        panelCenter.add(areaScrollPane);

        //south
        panelSouth.setBackground(Color.DARK_GRAY);
        panelSouth.setLayout(new FlowLayout());
        panelSouth.add(messageText);
        messageText.setFont(new Font("Serif", Font.PLAIN, 15));
        panelSouth.add(bt);

        frame.add(panelNorth,BorderLayout.NORTH);
        frame.add(panelCenter,BorderLayout.CENTER);
        frame.add(panelSouth,BorderLayout.SOUTH);
        frame.setSize(800,500);
        frame.setVisible(true);
        bt.addActionListener(new ButtonsObserver(this) );
        btConnect.addActionListener(new connectingButton());
    }

    public IState getState() {
        return state;
    }

    public void setState(IState state) {
        this.state = state;
    }


    public String getNickname(){
        return nameText.getText();
    }
    public void clearError(){
        panelNorth.remove(errorField);
    }
    public void setError(String err){
        errorField.setText(err);
        panelNorth.add(errorField);
    }

    // Handle the received message in the GUI
    //Needs to be displayed on the GUI.
    @Override
    public void consume(String text) throws ChatException {
       textArea.append(text+ "\n");

//        SimpleAttributeSet attrset = new SimpleAttributeSet();
//        StyleConstants.setFontFamily(attrset, "Serif");
//        StyleConstants.setFontSize(attrset,16);
//        Document docs = textArea.getDocument();
//        String info = null;
//        try {
//            info = "You";
//            StyleConstants.setForeground(attrset, Color.MAGENTA);
//            docs.insertString(docs.getLength(), info, attrset);
//            StyleConstants.setForeground(attrset, Color.red);
//            info = " "+text+"\n";
//            StyleConstants.setFontSize(attrset,12);
//            StyleConstants.setForeground(attrset, Color.green);
//            docs.insertString(docs.getLength(), info, attrset);
//        } catch (BadLocationException e) {
//            e.printStackTrace();
//        }
    }


    @Override
    public void addConsumer(StringConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void removeConsumer(StringConsumer consumer) {
        if(this.consumer!=null) {
            this.consumer = null;
        }
    }

    class connectingButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Button was clicked");
            System.out.println(ipText.getText());
            System.out.println(portText.getText());
            System.out.println(nameField.getText());
        }
    }



    //implements dp observer
    class ButtonsObserver implements ActionListener {

        private ChatClientGUI gui;

        public ButtonsObserver(ChatClientGUI gui){
            this.gui=gui;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
         try {
                System.out.println("inside actionPerformed... thread="+Thread.currentThread().getName()+" tf.getText()="+messageText.getText());
                String text=messageText.getText();
                if(text.trim().length()<1) return;
                consumer.consume(text);
                messageText.setText("");
            System.out.println(text+" Done");
                System.out.println(messageText.getText()+" was sent to the server by calling the consumer.consume() method thread="+Thread.currentThread().getName());
           } catch (ChatException ex) {
                ex.printStackTrace();
           }
        }
    }

}

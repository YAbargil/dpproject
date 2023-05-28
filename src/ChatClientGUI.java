import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Objects;

public class ChatClientGUI implements StringConsumer, StringProducer {
    ConnectionProxy proxy = null;
    JTextArea textArea;
     JComboBox comboBox;

    JScrollPane areaScrollPane;
    private StringConsumer consumer;
    private JFrame frame;
    private JLabel ipAddressField, portField, nameField;
    private JTextField messageText, ipText, portText, nicknameText;
    private JPanel panelSouth, panelCenter, panelNorth;
    private JButton btConnect, btDisconnect, btSend;
    private IState state;

    public ChatClientGUI() {

        //panels
        frame = new JFrame();
        panelSouth = new JPanel();
        panelCenter = new JPanel();
        panelNorth = new JPanel(new GridLayout());

        //buttons
        btSend = new JButton("Send");
        btConnect = new JButton("Connect");
        btDisconnect = new JButton("Disconnect");

        //labels
        ipAddressField = new JLabel("Server");
        nameField = new JLabel("Nickname");
        portField = new JLabel("Port");

        //text field
        messageText = new JTextField(30);
        ipText = new JTextField(5);
        portText = new JTextField(5);
        nicknameText = new JTextField(5);
        //other
        textArea = new JTextArea(16, 58);
        areaScrollPane = new JScrollPane(textArea);
        comboBox=new JComboBox();
        init();
    }

    public void init() {
        Thread guiThread = new Thread(() -> {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            //north
            stateDisconnected();
            panelNorth.add(nameField, BorderLayout.WEST);
            panelNorth.add(nicknameText, BorderLayout.WEST);
            panelNorth.add(ipAddressField, BorderLayout.WEST);
            panelNorth.add(ipText, BorderLayout.WEST);
            panelNorth.add(portField, BorderLayout.WEST);
            panelNorth.add(portText, BorderLayout.WEST);
            panelNorth.add(btConnect, BorderLayout.EAST);
            panelNorth.add(btDisconnect, BorderLayout.EAST);

            //center
            panelCenter.setBackground(Color.white);
            panelCenter.setBorder(new TitledBorder(new EtchedBorder(), "Chat"));
            areaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            textArea.setLineWrap(true);
            textArea.setEditable(false);
            textArea.setFont(new Font("Arial Black", Font.BOLD, 13));
            panelCenter.add(areaScrollPane,BorderLayout.CENTER);

            //south
            panelSouth.setBackground(Color.DARK_GRAY);
            panelSouth.setLayout(new FlowLayout());
            messageText.setFont(new Font("Arial", Font.PLAIN, 13));
            comboBox.addItem("Sends to everyone");
            panelSouth.add(messageText);
            panelSouth.add(btSend);
            panelSouth.add(comboBox);

            frame.add(panelNorth, BorderLayout.NORTH);
            frame.add(panelCenter, BorderLayout.CENTER);
            frame.add(panelSouth, BorderLayout.SOUTH);
            frame.setSize(800, 500);
            frame.setVisible(true);
            btSend.addActionListener(new ButtonsObserver(this));
            btConnect.addActionListener(new connectingButton());
            btDisconnect.addActionListener(new disconnectingButton());
        });
        guiThread.start();
    }


//    public void start() {
////        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        frame.setLayout(new BorderLayout());
////
////        //north
////        panelNorth.setBackground(Color.LIGHT_GRAY);
////        panelNorth.add(nameField,BorderLayout.WEST);
////        panelNorth.add(nameText,BorderLayout.WEST);
////        panelNorth.add(ipAddressField,BorderLayout.WEST);
////        panelNorth.add(ipText,BorderLayout.WEST);
////        panelNorth.add(portField,BorderLayout.WEST);
////        panelNorth.add(portText,BorderLayout.WEST);
////        panelNorth.add(btConnect,BorderLayout.EAST);
////        panelNorth.add(btDisconnect,BorderLayout.EAST);
////
////        //center
////        panelCenter.setBackground(Color.white);
////        panelCenter.setBorder(new TitledBorder(new EtchedBorder(), "Chat"));
////        areaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
////        textArea.setLineWrap(true);
////        textArea.setEditable(false);
////        panelCenter.add(areaScrollPane);
////
////        //south
////        panelSouth.setBackground(Color.DARK_GRAY);
////        panelSouth.setLayout(new FlowLayout());
////        panelSouth.add(messageText);
////        messageText.setFont(new Font("Serif", Font.PLAIN, 15));
////        panelSouth.add(bt);
////
////        frame.add(panelNorth,BorderLayout.NORTH);
////        frame.add(panelCenter,BorderLayout.CENTER);
////        frame.add(panelSouth,BorderLayout.SOUTH);
////        frame.setSize(800,500);
////        frame.setVisible(true);
////        bt.addActionListener(new ButtonsObserver(this) );
////        btConnect.addActionListener(new connectingButton());
//    }

    public IState getState() {
        return state;
    }

    public void setState(IState state) {
        this.state = state;
    }



    public void clearInputs() {
        ipText.setText("");
        portText.setText("");
        nicknameText.setText("");
        portText.setEnabled(true);
        nicknameText.setEnabled(true);
        ipText.setEnabled(true);
    }


    // Handle the received message in the GUI
    //Needs to be displayed on the GUI.
    @Override
    public void consume(String text) throws ChatException {

        System.out.println("IN GUI WITH "+ text);
        if(text.startsWith(Constants.createComboBox)){
            textArea.append("Welcome to chat !\n");
            String activeUsers=text.substring((text.lastIndexOf("@")+1));
            String[] users=activeUsers.split("#");
            System.out.println("CREATE COMBO BOX WITH USERS "+ Arrays.toString(users));
            for(String user:users){
                if(!user.isEmpty()) {
                    comboBox.addItem(user);
                    System.out.println("adding user " + user);
                }
            }
        }
        else if(text.startsWith(Constants.updateComboBox)){
            String newUser=text.substring((text.lastIndexOf("@")+1));
            textArea.append(newUser +" has joined the chat!\n");
            System.out.println("UPDATE COMBO BOX WITH "+ newUser);
            comboBox.addItem(newUser);
        }
        else if(text.startsWith(Constants.deleteUser)) {
            System.out.println("IN DELETING THE USER FROM COMBO BOX");
            String toDelete = text.substring((text.lastIndexOf("@") + 1));
            textArea.append(toDelete+ " has left the chat!\n");
            for (int i = 0; i < comboBox.getItemCount(); i++) {
                String item = (String) comboBox.getItemAt(i);
                if (item.equals(toDelete)) {
                    comboBox.removeItem(item);
                    break;
                }
            }
    }
//        @PRIVATE_FROM#Harley&TO%Rocky@hello
        else if(text.startsWith(Constants.privateMessage)){
            System.out.println("WERE PRIVATE ! " + text);
            String firstName=text.substring(text.indexOf("#")+1,text.lastIndexOf("&")).trim();
            String secondName=text.substring(text.indexOf("%")+1,text.lastIndexOf("@")).trim();
            if(firstName.equals(ChatClientGUI.this.nicknameText.getText().trim())){
                textArea.append("(PRIVATE) to "+ secondName+":\n"+text.substring(text.lastIndexOf("@")+1)+"\n");
            }
            else{
                textArea.append("(PRIVATE) From "+ firstName+":\n"+text.substring(text.lastIndexOf("@")+1)+"\n");

            }
        }
        else{
            textArea.append(text);
        }
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
//            StyleConstants.setFontSize(attrset;
//            docs.insertString(docs.getLength(), info, attrs,12);
////            StyleConstants.setForeground(attrset, Color.green)et);
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
        if (this.consumer != null) {
            this.consumer = null;
        }
    }


    public void stateConnected(){
        btConnect.setEnabled(false);
//        btConnect.setForeground(Color.GREEN);
        btConnect.setText("Connected");
        btDisconnect.setEnabled(true);
        ipText.setBackground(Color.LIGHT_GRAY);
        ipText.setEnabled(false);
        portText.setBackground(Color.LIGHT_GRAY);
        portText.setEnabled(false);
        nicknameText.setBackground(Color.LIGHT_GRAY);
        portText.setEnabled(false);
        btSend.setEnabled(true);
    }

    public void stateDisconnected() {
        try {
//            this.state(new ConnectedState());

            if (proxy != null) {
                ChatClientGUI.this.consumer.consume(nicknameText.getText() + " has left the chat.");
                ChatClientGUI.this.consumer.consume(Constants.deleteUser + nicknameText.getText());
                proxy = null;
                comboBox.removeAllItems();
                comboBox.addItem("Sends to everyone");
            }
            btDisconnect.setEnabled(false);
            clearInputs();
//            btConnect.setForeground(Color.RED);
            btConnect.setText("Connect");

            btConnect.setEnabled(true);
            btSend.setEnabled(false);
            messageText.setText("");
            textArea.setText("");
        } catch (ChatException e) {
            JOptionPane.showMessageDialog(frame, "Could not disconnect, Please try again.");
        }
    }

    class disconnectingButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            stateDisconnected();
        }
    }

    class connectingButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String ip = ipText.getText();
            String port = portText.getText().replaceAll("[^0-9]", "");
            String nickname = nicknameText.getText().trim();
            if (ip.isEmpty() || port.isEmpty() || nickname.replaceAll("\\d", "").length() < 2) {
                JOptionPane.showMessageDialog(frame, "Invalid inputs.");
//                System.out.println("in if");
                ChatClientGUI.this.clearInputs();
                return;
            }
            try {
                proxy = new ConnectionProxy(ip, Integer.parseInt(port),nickname);
                proxy.addConsumer(ChatClientGUI.this);
                ChatClientGUI.this.addConsumer(proxy);
                proxy.start();
                proxy.consume(nicknameText.getText());
                System.out.println("done");
                //state
                ChatClientGUI.this.stateConnected();


            } catch (ChatException exc) {
                System.out.println("in catch");
                JOptionPane.showMessageDialog(frame, "There was an error in establishment a connection");
                ChatClientGUI.this.clearInputs();
            }

        }

    }


    //implements dp observer
    class ButtonsObserver implements ActionListener {
        private ChatClientGUI gui;

        public ButtonsObserver(ChatClientGUI gui) {
            this.gui = gui;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                System.out.println("inside actionPerformed... thread=" + Thread.currentThread().getName() + " tf.getText()=" + messageText.getText());
                String text = messageText.getText();
                if (text.trim().length() < 1) return;
                String sendsTo = (String)comboBox.getSelectedItem();
                if(!Objects.equals(sendsTo, "Sends to everyone")){
                    text=Constants.privateMessage +"FROM#"+ChatClientGUI.this.nicknameText.getText()+"&TO%"+sendsTo+"@"+text;
                }
                consumer.consume(text);
                messageText.setText("");
                System.out.println(messageText.getText() + " was sent to the server by calling the consumer.consume() method thread=" + Thread.currentThread().getName());
            } catch (ChatException ex) {
                ex.printStackTrace();
            }
        }
    }
}


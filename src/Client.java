//import javax.swing.*;
//
//public  class Client implements Runnable {
//
//    private ConnectionProxy proxy;
//    private int port;
//    private String ip;
//    private ChatClientGUI gui;
//
//    public int getPort() {
//        return port;
//    }
//
//    public void setPort(int port) {
//        this.port = port;
//    }
//
//    public String getIp() {
//        return ip;
//    }
//
//    public void setIp(String ip) {
//        this.ip = ip;
//    }
//
//    public Client(){
//
//        gui = new ChatClientGUI();
//        SwingUtilities.invokeLater(new GUIGenerator(gui));
//    }
//
//    @Override
//    public void run() {
//            System.out.println("inside the run() of the GUIGenerator class thread="+Thread.currentThread().getName());
//            System.out.println("The SimpleClientGUI class was instantiated thread="+Thread.currentThread().getName());
//            gui.setState(new StateConnected());
//            gui.start();
//            System.out.println("The start method was called on a SimpleClientGUI object thread="+Thread.currentThread().getName());
//            System.out.println("The ConnectionProxy object was added as a consumer to the SimpleClientGUI object thread="+Thread.currentThread().getName());
//
//    }
//
//
//    public void connect() {
//        if(gui.getNickname().trim().replaceAll("\\d","").length()<2) {
//            gui.setError("Please fill a valid name");
//            return;
//        }
//        try{
//            proxy=new ConnectionProxy(this.ip,this.port);
//            gui.clearError();
//            proxy.addConsumer(gui);
//            gui.addConsumer(proxy);
//            proxy.start();
//        } catch (ChatException e) {
//            gui.setError("There was an error in establishment a connection");
//        }
//
//    }
//}
//
//class GUIGenerator implements Runnable {
//    private ChatClientGUI gui;
//
//    public GUIGenerator(ChatClientGUI gui) {
//        this.gui = gui;
//    }
//
//    @Override
//    public void run() {
//        System.out.println("inside the run() of the GUIGenerator class thread=" + Thread.currentThread().getName());
//        System.out.println("The SimpleClientGUI class was instantiated thread=" + Thread.currentThread().getName());
//        gui.setState(new StateConnected());
//        gui.start();
//        System.out.println("The start method was called on a SimpleClientGUI object thread=" + Thread.currentThread().getName());
//        System.out.println("The ConnectionProxy object was added as a consumer to the SimpleClientGUI object thread=" + Thread.currentThread().getName());
//
//    }
//}
import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class RunClient {
    public static void main(String args[]) {//throws ChatException, InterruptedException, InvocationTargetException {

        Client client=new Client();

//        class GUIGenerator implements Runnable {
//            private ChatClientGUI gui;
//
//            public GUIGenerator(ChatClientGUI gui){
//                this.gui=gui;
//            }
//
//            @Override
//            public void run() {
//                System.out.println("inside the run() of the GUIGenerator class thread="+Thread.currentThread().getName());
//                System.out.println("The SimpleClientGUI class was instantiated thread="+Thread.currentThread().getName());
//                gui.setState(new StateConnected());
//                gui.start();
//                System.out.println("The start method was called on a SimpleClientGUI object thread="+Thread.currentThread().getName());
//                System.out.println("The ConnectionProxy object was added as a consumer to the SimpleClientGUI object thread="+Thread.currentThread().getName());
//
//            }
//        }
//        ChatClientGUI gui = new ChatClientGUI();
//        SwingUtilities.invokeLater(new GUIGenerator(gui));
//        System.out.println("about to create a ConnectionProxy object  thread="+Thread.currentThread().getName());
//        var proxy = new ConnectionProxy("127.0.0.1",1300);
//        System.out.println("we now have a ConnectionProxy object   thread="+Thread.currentThread().getName());
//        proxy.addConsumer(gui);
//        gui.addConsumer(proxy);
//        Thread.sleep(500);
//        System.out.println("consumer was added to the ConnectionProxy object   thread="+Thread.currentThread().getName());
//        System.out.println("the thread that calls the readUTF has started   thread="+Thread.currentThread().getName());
//        proxy.start();
//        while(true){}
    }
}



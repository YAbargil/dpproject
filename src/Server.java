import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String args[]) throws IOException, ChatException {

        MessageBoard mBoard = new MessageBoard();

//        System.out.println("about to create a ServerSocket object  thread="+Thread.currentThread().getName());
        ServerSocket server = new ServerSocket(1300);
//        System.out.println("the ServerSocket object was created   thread="+Thread.currentThread().getName());
        System.out.println("the accept method is about to be called on the ServerSocket object   thread="+Thread.currentThread().getName());
        Socket socket = server.accept();
        System.out.println("the accept method has succeeded to handle the connection request   thread="+Thread.currentThread().getName());
//        System.out.println("the server now holds a Socket object   thread="+Thread.currentThread().getName());
        ConnectionProxy connection = new ConnectionProxy(socket);
//        System.out.println("the ConnectionProxy object was created   thread="+Thread.currentThread().getName());




        var ob = new ClientDescriptor();
//        ClientDescriptor client = new ClientDescriptor();
//        System.out.println("a ClientDescriptor object was created   thread="+Thread.currentThread().getName());
        connection.addConsumer(ob);
//        connection.addConsumer(client);
//        System.out.println("the ClientDescriptor object was added as a consumer for the proxy   thread="+Thread.currentThread().getName());
        ob.addConsumer(connection);
//        client.addConsumer(mBoard);
//        System.out.println("the ConnectionProxy object was added as a consumer to the ClientDescriptor object   thread="+Thread.currentThread().getName());
//        mBoard.addConsumer(connection);
//        System.out.println("the ConnectionProxy object was added as a consumer to the MessageBoard object   thread="+Thread.currentThread().getName());
        connection.start();
//        System.out.println("the new thread that works on the ConnectionProxy object has started   thread="+Thread.currentThread().getName());
    }
}
//
//class ServerConsumer implements StringConsumer, StringProducer {
//
//    private StringConsumer consumer;
//
//    @Override
//    public void consume(String text) throws ChatException {
//       System.out.println("text="+text);
//        consumer.consume(text);
//    }
//
//    @Override
//    public void addConsumer(StringConsumer consumer) {
//        this.consumer = consumer;
//    }
//
//    @Override
//    public void removeConsumer(StringConsumer consumer) {
//        if(consumer==this.consumer) {
//            this.consumer = null;
//        }
//    }
//}
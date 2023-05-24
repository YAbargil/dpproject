public class ClientDescriptor implements StringConsumer, StringProducer{

    private StringConsumer consumer;
    private String name;

    public ClientDescriptor(){
        this.name="";

    }



    @Override
    public void consume(String text) throws ChatException {
        if(name.isEmpty()){
            this.name=text;
            consumer.consume(text + " has joined the chat!\n");
        }
        else{
            consumer.consume(name+":\n"+text+"\n");
        }
        System.out.println("ClientDescriptor consumed = "+text);
    }

    @Override
    public void addConsumer(StringConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void removeConsumer(StringConsumer consumer) {
        if(consumer==this.consumer) {
            this.consumer = null;
        }
    }
}
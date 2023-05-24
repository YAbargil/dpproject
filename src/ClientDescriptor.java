public class ClientDescriptor implements StringConsumer, StringProducer{

    private StringConsumer consumer;
    private String name;

    public ClientDescriptor(){

    }



    @Override
    public void consume(String text) throws ChatException {
        System.out.println("ClientDescriptor consumed = "+text);
        consumer.consume(text);
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
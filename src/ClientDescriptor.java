public class ClientDescriptor implements StringConsumer, StringProducer{

    private StringConsumer consumer;
    private String nickname;

    public ClientDescriptor(){
        this.nickname ="";

    }



    @Override
    public void consume(String text) throws ChatException {
        System.out.println("inside desc. w/ "+text);
        if(nickname.isEmpty()){
            this.nickname =text;
            consumer.consume(Constants.createUser+text);
        }
         else if(text.startsWith(Constants.deleteUser)){
            consumer.consume(text);
        }
         else if(text.startsWith(Constants.privateMessage)){
            consumer.consume(text);
        }
        else{
            consumer.consume(nickname +":\n"+text+"\n");
        }
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
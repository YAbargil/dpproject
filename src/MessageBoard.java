import java.util.*;

public class MessageBoard implements StringConsumer, StringProducer {
    private List<StringConsumer> consumers;

    public MessageBoard() {
        this.consumers = new ArrayList<>();
    }

    @Override
    public void addConsumer(StringConsumer sc) {
        consumers.add(sc);
    }

    @Override
    public void removeConsumer(StringConsumer sc) {
        if(consumers.contains(sc)){
            consumers.remove(sc);
        }
    }

    @Override
    public void consume(String str) throws ChatException {
        // Broadcast the received message to all consumers
        for (StringConsumer consumer : consumers) {
            consumer.consume(str);
        }
    }
}
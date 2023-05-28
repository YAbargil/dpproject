import java.util.*;

public class MessageBoard implements StringConsumer, StringProducer {
    private List<StringConsumer> consumers;

    public MessageBoard() {
        this.consumers = new ArrayList<>();
    }

    @Override
    public void addConsumer(StringConsumer sc) {
//
//        ConnectionProxy sc1 = (ConnectionProxy) sc;
//        String newConsumerNickname=sc1.getNickname();
//        System.out.println("Adding consumer name : " + newConsumerNickname);
//        List<String> nicknames= new ArrayList<>();
//        try{
//            for(StringConsumer consumer:consumers){
//                consumer.consume(newConsumerNickname+ " has joined the chat.\n");
//                ConnectionProxy sc2= (ConnectionProxy) sc;
//                nicknames.add(sc2.getNickname());
//            }
//            consumers.add(sc);
//            String nicknamesAsString=String.join("#", nicknames).trim();
//            System.out.println(nicknamesAsString);
//            sc.consume(Constants.createComboBox+nicknamesAsString);
//        }
//        catch(ChatException e){
//            System.out.println(e.getMessage());
//        }
        consumers.add(sc);

    }

    @Override
    public void removeConsumer(StringConsumer sc){
        if(consumers.contains(sc)){
            System.out.println("Removing Consumer !");
            consumers.remove(sc);
        }
    }



    @Override
    public void consume(String str) throws ChatException{

        //new user connected
        if(str.startsWith(Constants.createUser)) {
            List<String> nicknames= new ArrayList<>();
            String userNickname = str.substring((str.lastIndexOf("@") + 1));
            ConnectionProxy proxy=null;
            for (StringConsumer sc : consumers) {
                ConnectionProxy sc1 = (ConnectionProxy) sc;
//                System.out.println(sc1.getNickname() + userNickname);
                if (sc1.getNickname().equals(userNickname)) {
                    proxy=sc1;
                } else {
                    nicknames.add(sc1.getNickname());
//                    System.out.println("Else statement");
                    //sends for rest users the user name to update the combo box
                    sc.consume(Constants.updateComboBox + userNickname);
                }
            }
            //send for the new user the updated users list
            if(proxy!=null){
                proxy.consume(Constants.createComboBox + String.join("#", nicknames).trim());
            }
        }
//            Iterator mapIterator = map.entrySet().iterator();
//            while (mapIterator.hasNext()) {
//                Map.Entry mapElement = (Map.Entry)mapIterator.next();
//               StringConsumer current= (StringConsumer) mapElement.getValue();
//               current.consume(Constants.updateComboBox+userNickname);
//                if(!mapIterator.hasNext())
//            }
//            for(int i=0;i<consumers.size()-1;i++) {
//                    consumers.get(i).consume(Constants.updateComboBox+userNickname);
//            }
//            String namesAsString=String.join("#", map.keySet()).trim();
//            consumers.get(consumers.size()-1).consume(Constants.createComboBox+namesAsString);
//            map.put(userNickname,consumers.get(consumers.size()-1));
//        }

        //proxy.stop();
       else if(str.startsWith(Constants.deleteUser)){
            String deleteNickname = str.substring((str.lastIndexOf("@") + 1));
            StringConsumer consumerToRemove=null;
            for(StringConsumer sc:consumers){
                ConnectionProxy proxy = (ConnectionProxy) sc;
                if(proxy.getNickname().equals(deleteNickname)){
                    consumerToRemove=proxy;
                }
                else{
                    sc.consume(str);
                }
            }
            removeConsumer(consumerToRemove);
            ((ConnectionProxy)consumerToRemove).stop();
        }
        else if(str.startsWith(Constants.privateMessage)){
            System.out.println("INSIDE PRIVATE");
            String nicknameDestination=str.substring(str.indexOf("%")+1,str.lastIndexOf("@"));
            System.out.println("PRIVATE MESSAGE TO "+nicknameDestination);
            String nicknameSender=str.substring(str.indexOf("#")+1,str.indexOf("&"));
            System.out.println("@@@@@PRIVATE MESSAGE FROM "+nicknameSender);
            ConnectionProxy proxyDest=null,proxySender=null;
            for(StringConsumer sc:consumers){
                ConnectionProxy proxy=(ConnectionProxy)sc;
                if(((ConnectionProxy) sc).getNickname().equals(nicknameDestination)){
                    proxyDest=proxy;
                }
                if(((ConnectionProxy) sc).getNickname().equals(nicknameSender)){
                    proxySender=proxy;
                }
            }
            if(proxyDest!=null){
                System.out.println("PROXY DEST IS NOT NULL");
                proxyDest.consume(str);
            }
            if(proxySender!=null){
                System.out.println("PROXY SENDER IS NOT NULL");
                proxySender.consume(str);
            }
        }
        else{
            for (StringConsumer consumer : consumers) {
            consumer.consume(str);
        }
        }

    }


//    @Override
//    public void consume(String str) throws ChatException {
//        String newString=str;
//        if(str.startsWith(Constants.createUser)){
//            System.out.println("here");
//            System.out.println(String.join(" ",names));
//            if(names.size()>0)  str=str.concat(String.join(" ",names));
//            System.out.println(Constants.createUser+"IS CALLED "+str);
//        }
//        // Broadcast the received message to all consumers
//        for (StringConsumer consumer : consumers) {
//            consumer.consume(str);
//        }
//        names.add(newString);
//    }
}
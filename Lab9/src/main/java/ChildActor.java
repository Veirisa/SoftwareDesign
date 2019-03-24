import akka.actor.UntypedActor;

public class ChildActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Request) {
            Response response =
                    new Response(((Request) message).getSearcher(), StubServer.getResponse((Request) message));
            getSender().tell(response, getSelf());
            return;
        }
        System.out.println(message.toString());
    }
}

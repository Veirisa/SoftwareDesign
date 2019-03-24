import akka.actor.*;
import scala.concurrent.duration.Duration;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MasterActor extends UntypedActor {

    private static Searcher[] searchers = {
            new Searcher(Searcher.SearcherType.YANDEX),
            new Searcher(Searcher.SearcherType.BING),
            new Searcher(Searcher.SearcherType.GOOGLE),
    };

    private List<Response> responses;

    MasterActor() {
        getContext().setReceiveTimeout(Duration.create(1000, TimeUnit.MILLISECONDS));
    }

    public void handleResponses() {
        System.out.println("----- Responses -----");
        for (Response response : responses) {
            System.out.println(response.getSearcher().getName() + " - " + response.getJsonObject().toString());
        }
        Result.result.addAll(responses);
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof String) {
            responses = new LinkedList<>();
            for (int i = 0; i < searchers.length; ++i) {
                //System.out.println(searchers[i].getName() + " actor created");
                ActorRef child = getContext().actorOf(Props.create(ChildActor.class), "actor" + searchers[i].getName());
                child.tell(new Request(searchers[i], (String) message), getSelf());
            }
            return;
        }
        if (message instanceof ReceiveTimeout) {
            //System.out.println("~ Timeout ~");
            handleResponses();
            getContext().stop(self());
            return;
        }
        if (message instanceof Response) {
            responses.add((Response) message);
            //System.out.println("Response from " + ((Response) message).getSearcher().getName());
            if (responses.size() == 3) {
                //System.out.println("~ Have 3 responses ~");
                handleResponses();
                getContext().stop(self());
            }
            return;
        }
        System.out.println(message.toString());
    }
}
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static java.lang.Thread.sleep;

public class ActorTest {

    private ActorSystem system;

    @BeforeEach
    void init() {
        system = ActorSystem.create("MySystem");
    }

    @AfterEach
    void terminate() {
        Result.result.clear();
        system.terminate();
    }

    @Test
    void withoutHang() throws InterruptedException {
        StubServer.hangProbabilty = 0;
        ActorRef masterActor = system.actorOf(Props.create(MasterActor.class), "master");
        masterActor.tell("cats", ActorRef.noSender());
        sleep(1000);
        Assertions.assertEquals(Result.result.size(), 3);
        Set<String> searcherNames = new HashSet<String>();
        for (Response response : Result.result) {
            String name = response.getSearcher().getName();
            Assertions.assertNotEquals(name, "Unknown");
            searcherNames.add(name);
        }
        Assertions.assertEquals(searcherNames.size(), 3);
    }

    @Test
    void withHang() throws InterruptedException {
        StubServer.hangProbabilty = 1;
        ActorRef masterActor = system.actorOf(Props.create(MasterActor.class), "master");
        masterActor.tell("dogs", ActorRef.noSender());
        sleep(2000);
        Assertions.assertTrue(Result.result.isEmpty());
    }

    @Test
    void randomHang() throws InterruptedException {
        Random rand = new Random();
        int exceptedSize = 0;
        for (int i = 0; i < 4; ++i) {
            boolean withHang = rand.nextDouble() < 0.5;
            if (!withHang) {
                exceptedSize += 3;
            }
            StubServer.hangProbabilty = withHang ? 1 : 0;
            ActorRef masterActor = system.actorOf(Props.create(MasterActor.class), "master" + i);
            masterActor.tell("parrots", ActorRef.noSender());
            sleep(withHang ? 2000 : 1000);
        }
        Assertions.assertEquals(Result.result.size(), exceptedSize);
    }
}

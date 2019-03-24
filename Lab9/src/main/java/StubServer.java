import org.json.JSONObject;

import java.util.Random;

import static java.lang.Thread.sleep;

public class StubServer {

    public static double hangProbabilty = 0;

    public static JSONObject getResponse(Request request) throws InterruptedException {
        if (new Random().nextDouble() < hangProbabilty) {
            sleep(1500);
        }
        return new JSONObject("{ \"result\" : answer from " +
                                request.getSearcher().getName() + " for " +
                                request.getSentence() + "}");
    }
}
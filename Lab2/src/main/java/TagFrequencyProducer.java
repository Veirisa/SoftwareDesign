import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.wall.WallPostFull;

import java.util.ArrayList;
import java.util.List;

public class TagFrequencyProducer {

    static public ArrayList<Integer> getTagFrecuency(String hashTag, Integer hours) {
        Integer curTime = (int)(System.currentTimeMillis() / 1000);
        VKCLient vkClient = new VKCLient(new VkApiClient(HttpTransportClient.getInstance()));
        List<WallPostFull> posts = vkClient.getPosts(hashTag, hours, curTime);
        return PostFrequencyCounter.getPostFrecuency(posts, hours, curTime);
    }
}

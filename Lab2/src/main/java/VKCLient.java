import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.WallPostFull;

import java.util.LinkedList;
import java.util.List;

public class VKCLient {

    // https://oauth.vk.com/blank.html#access_token=9d86e63de95099176d87bd0ad250d32249350ae919c0fb455ec92cf403c89bfb6a3909f9dcf46bf302e72&expires_in=86400&user_id=8551187
    private final Integer userId = 8551187;
    private final String token = "9d86e63de95099176d87bd0ad250d32249350ae919c0fb455ec92cf403c89bfb6a3909f9dcf46bf302e72";
    private final UserActor user = new UserActor(userId, token);

    private VkApiClient client;

    public VKCLient(VkApiClient client) {
        this.client = client;
    }

    public List<WallPostFull> getPosts(String hashTag, Integer hours, Integer curTime) {
        List<WallPostFull> posts = new LinkedList<>();
        try {
            String fullHashTag = "#" + hashTag;
            Integer endTime = curTime;
            Integer startTime = curTime - 3600 * hours;
            List<WallPostFull> newPosts;
            do {
                newPosts = client
                        .newsfeed()
                        .search(user)
                        .q(fullHashTag)
                        .count(100)
                        .startTime(startTime)
                        .endTime(endTime)
                        .execute()
                        .getItems();
                posts.addAll(newPosts);
                if (!newPosts.isEmpty()) {
                    endTime = newPosts.get(newPosts.size() - 1).getDate() - 1;
                }
            } while (newPosts.size() == 100);
        } catch (ApiException | ClientException err) {
            System.err.println(err);
        }
        return posts;
    }
}
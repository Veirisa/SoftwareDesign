import com.vk.api.sdk.actions.Newsfeed;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.newsfeed.responses.SearchResponse;
import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vk.api.sdk.queries.newsfeed.NewsfeedSearchQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

public class VKClientTest {

    @Test
    void getPosts() throws ApiException, ClientException {

        Random generator = new Random();

        Integer hours = generator.nextInt(24) + 1;
        Integer curTime = (int)(System.currentTimeMillis() / 1000);
        int postsSize = generator.nextInt(1000);

        List<WallPostFull> expectedMockPosts = new LinkedList<>();
        for (int i = 0; i < postsSize; ++i) {
            WallPostFull mockPost = mock(WallPostFull.class);
            Integer mockPostDate = curTime - generator.nextInt(hours * 3600) + 1;
            when(mockPost.getDate()).thenReturn(mockPostDate);
            expectedMockPosts.add(mockPost);
        }

        VkApiClient mockVkApiClient = mock(VkApiClient.class);
        Newsfeed mockNewsfeed = mock(Newsfeed.class);
        NewsfeedSearchQuery mockNewsfeedSearchQuery = mock(NewsfeedSearchQuery.class);
        SearchResponse mockSearchResponse = mock(SearchResponse.class);
        when(mockVkApiClient.newsfeed()).thenReturn(mockNewsfeed);
        when(mockNewsfeed.search(any(UserActor.class))).thenReturn(mockNewsfeedSearchQuery);
        when(mockNewsfeedSearchQuery.q(any(String.class))).thenReturn(mockNewsfeedSearchQuery);
        when(mockNewsfeedSearchQuery.count(any(Integer.class))).thenReturn(mockNewsfeedSearchQuery);
        when(mockNewsfeedSearchQuery.startTime(any(Integer.class))).thenReturn(mockNewsfeedSearchQuery);
        when(mockNewsfeedSearchQuery.endTime(any(Integer.class))).thenReturn(mockNewsfeedSearchQuery);
        when(mockNewsfeedSearchQuery.execute()).thenReturn(mockSearchResponse);
        when(mockSearchResponse.getItems()).thenReturn(expectedMockPosts);

        VKCLient client = new VKCLient(mockVkApiClient);
        List<WallPostFull> actualMockPosts = client.getPosts("", hours, curTime);

        Assertions.assertEquals(expectedMockPosts.size(), actualMockPosts.size());
    }
}

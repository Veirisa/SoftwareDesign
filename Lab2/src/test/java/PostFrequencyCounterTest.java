import com.vk.api.sdk.objects.wall.WallPostFull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostFrequencyCounterTest {

    @Test
    void getPostFrecuencyTest() {

        Random generator = new Random();

        Integer hours = generator.nextInt(24) + 1;
        Integer curTime = (int)(System.currentTimeMillis() / 1000);

        ArrayList<Integer> expectedPostFrequency = new ArrayList<>();
        for (int h = 0; h < hours; ++h) {
            Integer curPostsSize = generator.nextInt(100);
            expectedPostFrequency.add(curPostsSize);
        }

        List<WallPostFull> mockPosts = new LinkedList<>();
        for (int h = 0; h < hours; ++h) {
            for (int i = 0; i < expectedPostFrequency.get(h); ++i) {
                WallPostFull mockPost = mock(WallPostFull.class);
                Integer mockPostDate = curTime - (hours - h) * 3600 + generator.nextInt(3600);
                when(mockPost.getDate()).thenReturn(mockPostDate);
                mockPosts.add(mockPost);
            }
        }
        Collections.shuffle(mockPosts, generator);
        ArrayList<Integer> actualPostFrequency = PostFrequencyCounter.getPostFrecuency(mockPosts, hours, curTime);

        Assertions.assertEquals(expectedPostFrequency, actualPostFrequency);
    }
}

import com.vk.api.sdk.objects.wall.WallPostFull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PostFrequencyCounter {

    static public ArrayList<Integer> getPostFrecuency(List<WallPostFull> posts, Integer hours, Integer curTime) {
        Integer startTime = curTime - 3600 * hours;
        int[] postFrequency = new int[hours];
        for (WallPostFull post : posts) {
            ++postFrequency[(post.getDate() - startTime) / 3600];
        }
        return IntStream.of(postFrequency).boxed().collect(Collectors.toCollection(ArrayList::new));
    }
}

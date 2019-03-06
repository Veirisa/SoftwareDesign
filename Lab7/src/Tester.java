import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.Random;

class Tester {

    @Test
    void incEvent() {
        EventsStatisticImpl eventsStat = new EventsStatisticImpl(new SetableClock(Instant.now()));
        eventsStat.incEvent("A");
        eventsStat.incEvent("A");
        Assertions.assertEquals(eventsStat.events.keySet().size(), 1);
        eventsStat.incEvent("B");
        Assertions.assertEquals(eventsStat.events.keySet().size(), 2);
        eventsStat.incEvent("C");
        eventsStat.incEvent("B");
        eventsStat.incEvent("A");
        Assertions.assertEquals(eventsStat.events.keySet().size(), 3);
    }
    
    @Test
    void getEventStatisticByName() {
        Instant now = Instant.now();
        SetableClock clock = new SetableClock(now);
        EventsStatisticImpl eventsStat = new EventsStatisticImpl(clock);

        for (int i = 0; i < 12; ++i) {
            eventsStat.incEvent("A");
            eventsStat.incEvent("B");
            now = now.plusSeconds(200); // 3600 / 18
            clock.setNow(now);
        }
        double stat = eventsStat.getEventStatisticByName("A");
        Assertions.assertEquals(stat, (double)12 / 60, 0.0001);
        stat = eventsStat.getEventStatisticByName("B");
        Assertions.assertEquals(stat, (double)12 / 60, 0.0001);

        for (int i = 0; i < 12; ++i) {
            eventsStat.incEvent("A");
            now = now.plusSeconds(200); // 3600 / 18
            clock.setNow(now);
        }
        stat = eventsStat.getEventStatisticByName("A");
        Assertions.assertEquals(stat, (double)18 / 60, 0.0001);
        stat = eventsStat.getEventStatisticByName("B");
        Assertions.assertEquals(stat, (double)6 / 60, 0.0001);

        now = now.plusSeconds(6 * 200); // 6 * (3600 / 18)
        clock.setNow(now);
        stat = eventsStat.getEventStatisticByName("A");
        Assertions.assertEquals(stat, (double)12 / 60, 0.0001);
        stat = eventsStat.getEventStatisticByName("B");
        Assertions.assertEquals(stat, 0, 0.0001);

        now = now.plusSeconds(10000);
        clock.setNow(now);
        stat = eventsStat.getEventStatisticByName("A");
        Assertions.assertEquals(stat, 0, 0.0001);
    }

    @Test
    void getAllEventStatistic() {
        Instant now = Instant.now();
        SetableClock clock = new SetableClock(now);
        EventsStatisticImpl eventsStat = new EventsStatisticImpl(clock);
        Random rand = new Random();

        for (int t = 0; t < 10; ++t) {
            int[] counters = new int[4];
            String[] names = {"A", "B", "C", "D"};
            for (int i = 0; i < 60; ++i) {
                int ind = rand.nextInt(4);
                eventsStat.incEvent(names[ind]);
                if (i >= 30) {
                    ++counters[ind];
                }
                now = now.plusSeconds(120); // 3600 / 30;
                clock.setNow(now);
            }
            double summary = 0;
            for (int i = 0; i < 4; ++i) {
                summary += (double)counters[i] / 60;
            }
            double stat = eventsStat.getAllEventStatistic();
            Assertions.assertEquals(stat, summary, 0.0001);
        }
    }
}
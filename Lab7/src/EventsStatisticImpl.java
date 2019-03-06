import java.time.Instant;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class EventsStatisticImpl implements EventsStatistic {

    Clock clock;
    Map<String, Deque<Instant>> events;

    public EventsStatisticImpl(Clock clock) {
        this.clock = clock;
        this.events = new HashMap<>();
    }

    private double getRequestPerMinute(String name, Instant now) {
        Deque<Instant> curEvents = events.get(name);
        while (!curEvents.isEmpty() && (now.getEpochSecond() - curEvents.peekFirst().getEpochSecond() > 3600)) {
            curEvents.removeFirst();
        }
        events.put(name, curEvents);
        return (double)curEvents.size() / 60;
    }

    @Override
    public void incEvent(String name) {
        Instant now = clock.now();
        if (!events.containsKey(name)) {
            events.put(name, new LinkedList<>());
        }
        events.get(name).add(now);
    }

    @Override
    public double getEventStatisticByName(String name) {
        Instant now = clock.now();
        if (events.containsKey(name)) {
            return getRequestPerMinute(name, now);
        }
        return 0;
    }

    @Override
    public double getAllEventStatistic() {
        Instant now = clock.now();
        double summary = 0;
        for (String name : events.keySet()) {
            summary += getRequestPerMinute(name, now);
        }
        return summary;
    }

    @Override
    public void printStatistic() {
        Instant now = clock.now();
        System.out.println("---- Statistic (request per minute) ----");
        for (String name : events.keySet()) {
            System.out.printf("%s: %.4f\n", name, getRequestPerMinute(name, now));
        }
    }
}

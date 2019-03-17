import aspect.LoggingExecutionTimeAspect;
import clock.Clock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import statistic.EventsStatistic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

class AspectTest {

    private static ApplicationContext ctx = new AnnotationConfigApplicationContext(ContextConfiguration.class);
    private static Clock clock = ctx.getBean(Clock.class);
    private static EventsStatistic eventsStatistic = ctx.getBean(EventsStatistic.class, clock);
    private static LoggingExecutionTimeAspect aspect = ctx.getBean(LoggingExecutionTimeAspect.class);

    private static ArrayList<String> statisticNames = new ArrayList<>(
            Arrays.asList(
                    "(statistic.EventsStatisticImpl) incEvent",
                    "(statistic.EventsStatisticImpl) getEventStatisticByName",
                    "(statistic.EventsStatisticImpl) getAllEventStatistic",
                    "(statistic.EventsStatisticImpl) printStatistic"
                    )
    );
    private static String clockName = "(clock.NormalClock) now";

    private static Long initTime = 0L;
    private static Integer[] statisticCounters = new Integer[] {0, 0, 0, 0};
    private static Integer clockCounter = 0;

    @BeforeAll
    static void randomInit() {
        long startTime = System.nanoTime();
        Random rand = new Random();
        for (int i = 0; i < 10000; ++i) {
            int ind = rand.nextInt(4);
            switch (ind) {
                case 0:
                    eventsStatistic.incEvent("event");
                    break;
                case 1:
                    eventsStatistic.getEventStatisticByName("event");
                    break;
                case 2:
                    eventsStatistic.getAllEventStatistic();
                    break;
                case 3:
                    eventsStatistic.printStatistic();
                    break;
            }
            System.out.println(statisticCounters.length);
            ++statisticCounters[ind];
            ++clockCounter;
        }
        initTime = System.nanoTime() - startTime;
    }

    @Test
    void getCallsCount() {
        for (int i = 0; i < statisticNames.size(); ++i) {
            Assertions.assertEquals(aspect.getCallsCount(statisticNames.get(i)), statisticCounters[i]);
        }
        Assertions.assertEquals(aspect.getCallsCount(clockName), clockCounter);
    }

    @Test
    void getSumExecTime() {
        Long fullStatisticSumExecTime = 0L;
        for (int i = 0; i < statisticNames.size(); ++i) {
            fullStatisticSumExecTime += aspect.getSumExecTime(statisticNames.get(i));
        }
        Long clockSumExecTime = aspect.getSumExecTime(clockName);
        Assertions.assertTrue(fullStatisticSumExecTime > 0);
        Assertions.assertTrue(fullStatisticSumExecTime < initTime);
        Assertions.assertTrue(clockSumExecTime > 0);
        Assertions.assertTrue(clockSumExecTime < fullStatisticSumExecTime);
    }

    @Test
    void getAverExecTime() {
        Integer callsCount;
        Long sumExecTime, averExecTime;
        for (int i = 0; i < statisticNames.size(); ++i) {
            callsCount = aspect.getCallsCount(statisticNames.get(i));
            sumExecTime = aspect.getSumExecTime(statisticNames.get(i));
            averExecTime = aspect.getAverExecTime(statisticNames.get(i));
            Assertions.assertEquals(averExecTime.longValue(), callsCount == 0 ? 0L : sumExecTime / callsCount);
        }
        callsCount = aspect.getCallsCount(clockName);
        sumExecTime = aspect.getSumExecTime(clockName);
        averExecTime = aspect.getAverExecTime(clockName);
        Assertions.assertEquals(averExecTime.longValue(), callsCount == 0 ? 0L : sumExecTime / callsCount);
    }
}
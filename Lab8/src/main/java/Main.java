import aspect.LoggingExecutionTimeAspect;
import clock.Clock;
import clock.NormalClock;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import statistic.*;

public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(ContextConfiguration.class);

        Clock clock = ctx.getBean(Clock.class);
        EventsStatistic eventsStatistic = ctx.getBean(EventsStatistic.class, clock);
        eventsStatistic.incEvent("C");
        eventsStatistic.incEvent("B");
        eventsStatistic.incEvent("C");
        eventsStatistic.incEvent("A");
        eventsStatistic.incEvent("B");
        eventsStatistic.incEvent("C");
        eventsStatistic.printStatistic();

        LoggingExecutionTimeAspect aspect = ctx.getBean(LoggingExecutionTimeAspect.class);
        aspect.printLogs();
    }
}
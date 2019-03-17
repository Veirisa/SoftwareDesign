import aspect.LoggingExecutionTimeAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import clock.Clock;
import statistic.EventsStatistic;
import statistic.EventsStatisticImpl;
import clock.NormalClock;

@Configuration
@EnableAspectJAutoProxy
public class ContextConfiguration {
    @Bean
    public EventsStatistic eventsStatistic(Clock clock) {
        return new EventsStatisticImpl(clock);
    }

    @Bean
    public Clock clock() {
        return new NormalClock();
    }

    @Bean
    public LoggingExecutionTimeAspect aspect() {
        return new LoggingExecutionTimeAspect();
    }
}
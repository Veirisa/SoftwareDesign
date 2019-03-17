package aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.HashMap;
import java.util.Map;

@Aspect
public class LoggingExecutionTimeAspect {

    private Map<String, Integer> callsCounter = new HashMap<>();
    private Map<String, Long> sumExecutionTime = new HashMap<>();

    @Around("execution(* statistic.EventsStatisticImpl.*(..)) || execution(* clock.NormalClock.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String name = "(" + joinPoint.getTarget().getClass().getName() + ") " + joinPoint.getSignature().getName();
        long startNs = System.nanoTime();
        Object result = joinPoint.proceed(joinPoint.getArgs());
        long execNs = System.nanoTime() - startNs;
        if (!callsCounter.containsKey(name)) {
            callsCounter.put(name, 1);
            sumExecutionTime.put(name, execNs);
        } else {
            callsCounter.put(name, callsCounter.get(name) + 1);
            sumExecutionTime.put(name, sumExecutionTime.get(name) + execNs);
        }
        return result;
    }

    public Integer getCallsCount(String name) {
        if (callsCounter.containsKey(name)) {
            return callsCounter.get(name);
        }
        return 0;
    }

    public Long getSumExecTime(String name) {
        if (sumExecutionTime.containsKey(name)) {
            return sumExecutionTime.get(name);
        }
        return 0L;
    }

    public Long getAverExecTime(String name) {
        if (callsCounter.containsKey(name)) {
            return sumExecutionTime.get(name) / callsCounter.get(name);
        }
        return 0L;
    }

    public void printLogs() {
        System.out.println("================ LOGS ================\n");
        for (String name : callsCounter.keySet()) {
            Integer callsCount = callsCounter.get(name);
            Long sumExecTime = sumExecutionTime.get(name);
            Long averExecTime = sumExecTime / callsCount;
            System.out.println("---- " + name + " ----");
            System.out.println("number of methods calls: " + callsCount.toString());
            System.out.println("summary execution time in ns: " + sumExecTime.toString());
            System.out.println("average execution time in ns: " + averExecTime.toString());
            System.out.println();
        }
    }
}
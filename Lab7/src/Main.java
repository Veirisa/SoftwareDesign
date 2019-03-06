public class Main {
    public static void main(String[] args) {
        EventsStatistic eventsStat = new EventsStatisticImpl(new NormalClock());
        eventsStat.incEvent("C");
        eventsStat.incEvent("B");
        eventsStat.incEvent("C");
        eventsStat.incEvent("A");
        eventsStat.incEvent("B");
        eventsStat.incEvent("C");
        eventsStat.printStatistic();
    }
}

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String hashTag = scanner.nextLine();
        Integer hours = scanner.nextInt();
        ArrayList<Integer> frequency = TagFrequencyProducer.getTagFrecuency(hashTag, hours);
        for (Integer h = 0; h < hours; ++h) {
            System.out.println(h.toString() + ": " + frequency.get(h));
        }
    }
}
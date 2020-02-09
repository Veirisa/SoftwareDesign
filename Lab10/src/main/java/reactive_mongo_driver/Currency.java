package reactive_mongo_driver;

import java.util.HashMap;
import java.util.Map;

public class Currency {

    private final static Map<String, Double> currencies = new HashMap<String, Double>() {{
        put("RUR", 1.0);
        put("USD", 65.0);
        put("EUR", 73.0);
    }};

    public static Boolean correct(String cur) {
        return currencies.get(cur) != null;
    }

    public static Double convertToRu(String cur, Double val) {
        Double coef = currencies.get(cur);
        if (coef != null) {
            return val * coef;
        }
        return 0.0;
    }

    public static Double convertFromRu(String cur, Double val) {
        Double coef = currencies.get(cur);
        if (coef != null && coef > 0) {
            return val / coef;
        }
        return 0.0;
    }

}

package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import static java.util.Arrays.stream;

public class CheckoutSolution {

    static Map<String, Integer> itemRegularPrices = new HashMap<String, Integer>();
    static Map<String, Integer> itemSpecialPrices = new HashMap<String, Integer>();

    static {
        itemRegularPrices.put("A", 50);
        itemRegularPrices.put("B", 30);
        itemRegularPrices.put("C", 20);
        itemRegularPrices.put("D", 15);

        itemSpecialPrices.put("3A", 130);
        itemSpecialPrices.put("2B", 45);
    }

    public boolean isValidItem(String str){
        if(str.matches("[ABCD]")) return true;
        return false;
    }

    public Map<String, Long> getItems(String skus) {
        return stream(skus.split(""))
                .filter(a -> isValidItem(a))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public Integer checkout(String skus) {
        Map<String, Long> items = getItems(skus);
        long sum = 0;
        for(Map.Entry<String, Long> entry : items.entrySet()){
            Integer itemValue = itemSpecialPrices.get(entry.getValue() + entry.getKey());
            if(itemValue != null) {
                sum += itemValue.intValue();
            } else {
                Integer itemRegularPrice = itemRegularPrices.get(entry.getKey());
                if (itemRegularPrice != null) {
                    sum += entry.getValue() * itemRegularPrice.intValue();
                }
            }
        }
        int sumValue = (sum > 0)? Long.valueOf(sum).intValue() : -1;
        return sumValue;
    }
}

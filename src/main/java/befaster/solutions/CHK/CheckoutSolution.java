package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import static java.util.Arrays.stream;

public class CheckoutSolution {
    static Map<String, Integer> itemRegularPrices = new HashMap<>();
    static Map<String, Integer> itemSpecialPrices = new HashMap<>();
    static Map<String, Long> items = null;
    static {
        itemRegularPrices.put("A", 50);
        itemRegularPrices.put("B", 30);
        itemRegularPrices.put("C", 20);
        itemRegularPrices.put("D", 15);

        itemSpecialPrices.put("3-A", 130);
        itemSpecialPrices.put("2-B", 45);
    }

    public boolean isInValidItemsExists(String skus){
        long count = stream(skus.split("")).filter(a -> !itemRegularPrices.containsKey(a)).count();
        if(count > 0) return true;
        return false;
    }

    public Map<String, Long> getItems(String skus) {
        return stream(skus.split(""))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public Integer getSpecialPrices(String product, int totalPurchased) {
        int splSum = 0;
        for(int i=1; i <= totalPurchased; i++){
            int specialPrice = itemSpecialPrices.containsKey(i + "-" + product)? itemSpecialPrices.get(i + "-" + product) : 0;
            if(specialPrice > 0) {
                int totalGroupCount = totalPurchased/i;
                splSum += totalGroupCount * specialPrice;

                int balanceItemsCount = totalPurchased%i;
                items.put(product, Long.valueOf(balanceItemsCount));
                break;
            }
        }
        return splSum;
    }

    public Integer checkout(String skus) {
        if(skus.trim().length() == 0) return 0;
        if(isInValidItemsExists(skus)) return -1;

        items = getItems(skus);
        long sum = 0;

        // Invoke the special prices
        for(Map.Entry<String, Long> entry : items.entrySet()) {
            sum += getSpecialPrices(entry.getKey(), Math.toIntExact(entry.getValue()));
        }
        // Invoke the regular prices
        for(Map.Entry<String, Long> entry : items.entrySet()) {
            Integer itemRegularPrice = itemRegularPrices.get(entry.getKey());
            if (itemRegularPrice != null) {
                sum += entry.getValue() * itemRegularPrice.intValue();
            }
        }
        int sumValue = (sum > 0)? Long.valueOf(sum).intValue() : -1;
        return sumValue;
    }

}

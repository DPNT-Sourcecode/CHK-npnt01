package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import static java.util.Arrays.stream;

public class CheckoutSolution {

    static Map<String, Integer> itemRegularPrices = new HashMap<>();
    static Map<String, Integer> itemSpecialPrices = new HashMap<>();
    static Map<String, String> itemsFreeList = new HashMap<>();
    static int splSum;
    static Map<String, Long> items = null;
    static {
        splSum = 0;
        itemRegularPrices.put("A", 50);
        itemRegularPrices.put("B", 30);
        itemRegularPrices.put("C", 20);
        itemRegularPrices.put("D", 15);
        itemRegularPrices.put("E", 40);

        itemSpecialPrices.put("5-A", 200);
        itemSpecialPrices.put("3-A", 130);
        itemSpecialPrices.put("2-B", 45);

        itemsFreeList.put("2-E", "B");
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

    public int getSpecialPrices(String product, int totalPurchased) {
        int i = totalPurchased;
        if(i >= 1){
            int specialPrice = itemSpecialPrices.containsKey(i + "-" + product)? itemSpecialPrices.get(i + "-" + product) : 0;
            if(specialPrice > 0) {
                int availableItems = Math.toIntExact(items.get(product));
                int totalGroupCount = availableItems/i;
                splSum += totalGroupCount * specialPrice;

                int balanceItemsCount = Math.toIntExact(availableItems%i);
                items.put(product, Long.valueOf(balanceItemsCount));
                if(balanceItemsCount > 1) {
                    i = balanceItemsCount;
                } else {
                    return splSum;
                }
            } else {
                --i;
            }
            if(i > 1) getSpecialPrices(product, i);
        }
        return splSum;
    }

    public void evaluateFreeItems(String promotionItem, String freeItem) {
        String itemPromoCode = promotionItem.split("-")[1];
        Integer itemPromoCount = Integer.valueOf(promotionItem.split("-")[0]);
        long purchasedCount = items.containsKey(itemPromoCode) ? items.get(itemPromoCode) : 0;
        if (purchasedCount >= itemPromoCount) {
            int freeItemCount = Math.toIntExact(purchasedCount / itemPromoCount);
            if(items.containsKey(freeItem)) items.put(freeItem, items.get(freeItem) - freeItemCount);
        }
    }

    public Integer checkout(String skus) {
        if(skus.trim().length() == 0) return 0;
        if(isInValidItemsExists(skus)) return -1;

        items = getItems(skus);
        long sum = 0;

        // Invoke Free itesm
        for(Map.Entry<String, String> entry : itemsFreeList.entrySet()) {
            evaluateFreeItems(entry.getKey(), entry.getValue());
        }
        // Invoke the special prices
        for(Map.Entry<String, Long> entry : items.entrySet()) {
            sum += getSpecialPrices(entry.getKey(), Math.toIntExact(entry.getValue()));
            splSum = 0;
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

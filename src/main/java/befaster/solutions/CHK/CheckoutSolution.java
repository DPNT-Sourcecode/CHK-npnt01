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
        itemRegularPrices.put("F", 10);
        itemRegularPrices.put("G", 20);
        itemRegularPrices.put("H", 10);
        itemRegularPrices.put("I", 35);
        itemRegularPrices.put("J", 60);
        itemRegularPrices.put("K", 80);
        itemRegularPrices.put("L", 90);
        itemRegularPrices.put("M", 15);
        itemRegularPrices.put("N", 40);
        itemRegularPrices.put("O", 10);
        itemRegularPrices.put("P", 50);
        itemRegularPrices.put("Q", 30);
        itemRegularPrices.put("R", 50);
        itemRegularPrices.put("S", 30);
        itemRegularPrices.put("T", 20);
        itemRegularPrices.put("U", 40);
        itemRegularPrices.put("V", 50);
        itemRegularPrices.put("W", 20);
        itemRegularPrices.put("X", 90);
        itemRegularPrices.put("Y", 10);
        itemRegularPrices.put("Z", 50);

        itemSpecialPrices.put("5-A", 200);
        itemSpecialPrices.put("3-A", 130);
        itemSpecialPrices.put("2-B", 45);
        itemSpecialPrices.put("10-H", 80);
        itemSpecialPrices.put("5-H", 45);
        itemSpecialPrices.put("2-K", 150);
        itemSpecialPrices.put("5-P", 200);
        itemSpecialPrices.put("3-Q", 80);
        itemSpecialPrices.put("3-V", 130);
        itemSpecialPrices.put("2-V", 90);

        itemsFreeList.put("2-E", "1-B");
        itemsFreeList.put("2-F", "1-F");
        itemsFreeList.put("3-N", "1-M");
        itemsFreeList.put("3-R", "1-Q");
        itemsFreeList.put("3-U", "1-U");
    }

    public boolean isInValidItemsExists(String skus) {
        long count = stream(skus.split("")).filter(a -> !itemRegularPrices.containsKey(a)).count();
        if (count > 0) return true;
        return false;
    }

    public Map<String, Long> getItems(String skus) {
        return stream(skus.split(""))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public void evaluateFreeItems(String promotionItem, String freeItem) {
        Integer itemPromoCount = Integer.valueOf(promotionItem.split("-")[0]);
        String itemPromoCode = promotionItem.split("-")[1];

        Integer freeItemCount = Integer.valueOf(freeItem.split("-")[0]);
        String freeItemCode = freeItem.split("-")[1];

        long purchasedCount = items.containsKey(itemPromoCode) ? items.get(itemPromoCode) : 0;

        if (!itemPromoCode.equals(freeItemCode) && purchasedCount >= itemPromoCount) {
            int eligibleFreeItemCount = Math.toIntExact(purchasedCount / itemPromoCount);
            if (items.containsKey(freeItemCode)) items.put(freeItemCode, items.get(freeItemCode) - eligibleFreeItemCount);
        } else if (itemPromoCode.equals(freeItemCode)) {
            if (purchasedCount == itemPromoCount) return;
            if(items.containsKey(itemPromoCode)){
                int totalCnt = itemPromoCount + freeItemCount;
                items.put(itemPromoCode, (long) (purchasedCount - purchasedCount/totalCnt));
            }
        }
    }

    public int getSpecialPrices(String product, int totalPurchased) {
        int i = totalPurchased;
        if (i >= 1) {
            int specialPrice = itemSpecialPrices.containsKey(i + "-" + product) ? itemSpecialPrices.get(i + "-" + product) : 0;
            if (specialPrice > 0) {
                int availableItems = Math.toIntExact(items.get(product));
                int totalGroupCount = availableItems / i;
                splSum += totalGroupCount * specialPrice;

                int balanceItemsCount = Math.toIntExact(availableItems % i);
                items.put(product, Long.valueOf(balanceItemsCount));
                if (balanceItemsCount > 1) {
                    i = balanceItemsCount;
                } else {
                    return splSum;
                }
            } else {
                --i;
            }
            if (i > 1) getSpecialPrices(product, i);
        }
        return splSum;
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

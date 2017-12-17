package at.fh_joanneum.newsly.newsly.ressorts;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by edi on 07/05/2017.
 */

public enum RessortCategory {
    SPORT("Sport"),
    POLITICS("Politik"),
    ECONOMY("Wirtschaft"),
    LIFE("Leben"),
    EDUCATION("Bildung"),
    CULTURE("Kultur");

    private final String value;

    RessortCategory(String value) {
        this.value = value;
    }

    private static Map<String, RessortCategory> map = new HashMap<>();

    static {
        for (RessortCategory category : RessortCategory.values()) {
            map.put(category.value, category);
        }
    }

    public String getValue() {
        return value;
    }

    public static RessortCategory getRessortCategory(String value) {
        return map.get(value);
    }

}

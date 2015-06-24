package org.harish.config;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Abstraction for representing group information in config file.
 * 
 * @author harish.sharma
 *
 */
public class Group {
    public static final String        EMPTY_OVERRIDE = "";
    private final String              name;
    private final Map<String, Object> keyToValueMap;
    private final Set<String>         overrides;

    public Group(final String name) {
        this.name = name;
        this.keyToValueMap = new LinkedHashMap<>();
        this.overrides = new HashSet<>();
    }

    public Group(final String name, String... overrideArr) {
        this.name = name;
        this.keyToValueMap = new LinkedHashMap<>();
        this.overrides = new HashSet<>();
        for (String o : overrideArr) {
            this.overrides.add(o);
        }
    }

    /**
     * @return name of the group.
     */
    public String getGroupName() {
        return name;
    }

    public Object getValueForKey(final String key) {
        return keyToValueMap.get(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Group : " + name + " { \n");
        for (Entry<String, Object> en : keyToValueMap.entrySet()) {
            sb.append("  " + en.getKey() + " : " + en.getValue().toString() + "\n");
        }
        sb.append("}\n");
        return sb.toString();
    }

    void addKeyValue(final String overRide, final String key, final Object value) {
        if (overRide == null || key == null)
            throw new IllegalArgumentException("Null keys or null overide are not allowed");

        if (!keyToValueMap.containsKey(key)) {
            keyToValueMap.put(key, value);
        } else {
            if (overrides.contains(overRide)) {
                keyToValueMap.put(key, value);
            }
        }
    }
}

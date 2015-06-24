package org.harish.config;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author harish.sharma
 *
 */
public class Config {

    private final Map<String, Group> keyToGroupMap;

    public Config() {
        this.keyToGroupMap = new LinkedHashMap<>();
    }

    /**
     * Returns Long value for given key otherwise returns null.
     * 
     * @param key
     * @return
     */
    public Long getConfigAsLong(String key) {
        Object res = getConfig(key);
        if (res == null) return null;
        try {
            Long result = Long.parseLong((String) res);
            return result;
        } catch (Exception e) {
            // Do not throw exception from here as this API should return null in case
            // it is unable to figure out a value for given key.
            // Exception should be logged here.
            return null;
        }
    }

    /**
     * Returns Integer Value for given key otherwise returns null.
     * 
     * @param key
     * @return
     */
    public Integer getConfigAsInt(String key) {
        Object res = getConfig(key);
        if (res == null) return null;
        try {
            Integer result = Integer.parseInt((String) res);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns Double Value for given key otherwise returns null.
     * 
     * @param key
     * @return
     */
    public Double getConfigAsDouble(String key) {
        Object res = getConfig(key);
        if (res == null) return null;
        try {
            Double result = Double.parseDouble((String) res);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns String Value for given key otherwise returns null.
     * 
     * @param key
     * @return
     */
    public String getConfigAsString(String key) {
        Object res = getConfig(key);
        if (res == null) return null;
        try {
            return (String) res;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Return boolean value for given key otherwise returns null.
     * 
     * @param key
     * @return
     */
    public Boolean getConfigAsBoolean(String key) {
        Object res = getConfig(key);
        if (res == null) return null;
        try {
            String result = (String) res;
            if (result.equals("yes") || result.equals("1") || result.equals("true")) {
                return true;
            } else if (result.equals("no") || result.equals("0") || result.equals("false")) {
                return false;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public Object getConfig(String key) {
        String[] keys = key.split("\\.");
        if (keys.length > 2) return null;
        if (keys.length == 1) {
            return keyToGroupMap.get(keys[0]);
        }
        String groupKey = keys[0];
        String actualKey = keys[1];
        Group group = keyToGroupMap.get(groupKey);
        return group.getValueForKey(actualKey);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (Group g : keyToGroupMap.values()) {
            sb.append(g.toString());
            sb.append("---\n");
        }
        sb.append("]");
        return sb.toString();
    }

    /*
     * Adds the group to Config if group with this name does not exist otherwise overwrite the previous group configs.
     * 
     * @throws IllegalArgumentException if group is null.
     * @param group
     */
    void addGroup(final Group group) {
        if (group == null) throw new IllegalArgumentException("Null group is not allowed");
        keyToGroupMap.put(group.getGroupName(), group);
    }
}

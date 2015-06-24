package org.harish.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.harish.config.util.LRUCache;

/**
 * {@link Config} provides a way to query value for a given key.
 * 
 * @author harish.sharma
 *
 */
public class Config {

    private final Map<String, Group>       keyToGroupMap;
    private final LRUCache<String, Object> cache;

    public Config() {
        this.keyToGroupMap = new LinkedHashMap<>();
        this.cache = new LRUCache<>(Constants.CACHE_CAPACITY);
    }

    /**
     * Returns Long value for given key otherwise returns null.
     * 
     * @param key
     * @return
     */
    public Long getConfigAsLong(String key) {
        Object res = getOrSetInCache(key);
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
        Object res = getOrSetInCache(key);
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
        Object res = getOrSetInCache(key);
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
        Object res = getOrSetInCache(key);
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
        Object res = getOrSetInCache(key);
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
        try {
            if (cache.containsKey(key)) {
                return cache.get(key);
            }
            String[] keys = key.split("\\.");
            if (keys.length > 2) return null;
            if (keys.length == 1) {
                Object response = keyToGroupMap.get(keys[0]);
                cache.put(key, response);
                return response;
            }
            String groupKey = keys[0];
            String actualKey = keys[1];
            Group group = keyToGroupMap.get(groupKey);
            Object response = group.getValueForKey(actualKey);
            cache.put(actualKey, response);
            return response;
        } catch (Exception e) {
            // As this API should never throw exception as per the requirement so catch and log it and return null.
            return null;
        }
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

    private Object getOrSetInCache(final String key) {
        Object res = null;
        if (cache.containsKey(key)) {
            res = cache.get(key);
        } else {
            res = getConfig(key);
            cache.put(key, res);
        }
        return res;
    }
}

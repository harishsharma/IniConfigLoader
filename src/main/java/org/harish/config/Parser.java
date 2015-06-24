package org.harish.config;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import org.harish.config.util.Triplet;

/**
 * {@link Parser} contains the core logic of interpreting the config file.
 * 
 * @author harish.sharma
 *
 */
public class Parser {

    /**
     * Create config for given source reader and overrides.
     * 
     * @param reader
     * @param overrides
     * @return
     * @throws LoadException
     */
    public static Config createConfig(final BufferedReader reader, String[] overrides) throws LoadException {
        try {
            Config config = new Config();
            Group curGroup = null;
            String curLine;
            while ((curLine = reader.readLine()) != null) {
                String afterRemovingComment = removeComment(curLine).trim();
                if (isLineEmpty(afterRemovingComment)) continue;

                // Handler Group start
                if (afterRemovingComment.charAt(0) == Constants.GROUP_START) {
                    String name = handleGroup(afterRemovingComment);

                    // Add previous group to config
                    if (curGroup != null) {
                        config.addGroup(curGroup);
                    }

                    curGroup = new Group(name, overrides);
                } else {
                    Triplet<String, String, ? extends Object> triplet = handleKeyValue(afterRemovingComment);
                    curGroup.addKeyValue(triplet.getA(), triplet.getB(), triplet.getC());
                }
            }
            // Add last group to config
            if (curGroup != null) {
                config.addGroup(curGroup);
            }
            return config;
        } catch (Exception e) {
            throw new LoadException(e);
        }
    }


    /*
     * Parse the key value line as per the rules of Config Parsing.
     */
    private static Triplet<String, String, ? extends Object> handleKeyValue(final String line) throws LoadException {
        int idx = line.indexOf(Constants.KV_SEPARATOR);
        if (idx <= 0) throw new LoadException("Illegal KeyValue present : " + line);

        String key = line.substring(0, idx).trim();
        String value = line.substring(idx + 1, line.length()).trim();

        int overRideIdx = key.indexOf(Constants.OVERRIDE_START);

        String overRide;
        String finalKey;

        // Parse Override and actual Key.
        if (overRideIdx < 0) {
            overRide = Group.EMPTY_OVERRIDE;
            finalKey = key;
        } else {
            if (key.charAt(key.length() - 1) != Constants.OVERRIDE_END)
                throw new LoadException("Bad Input , OverideKey should end with > " + key);
            finalKey = key.substring(0, overRideIdx);
            if (finalKey.isEmpty()) throw new LoadException("Key cannot be empty " + key);

            overRide = key.substring(overRideIdx + 1, key.length() - 1);
            if (overRide.isEmpty()) throw new LoadException("Override cannot be empty " + key);
        }

        // Parse Value and Initialize result.
        Triplet<String, String, ? extends Object> res;
        if (value.charAt(0) == Constants.STRING_LITERAL && value.charAt(value.length() - 1) != Constants.STRING_LITERAL) {
            throw new LoadException("Bad Value " + value);
        } else if (value.charAt(0) == Constants.STRING_LITERAL) {
            res = Triplet.of(overRide, finalKey, value.substring(1, value.length() - 1));
        } else if (value.contains(Constants.ARRAY_SEPARATOR)) {
            List<String> values = new ArrayList<>();
            for (String a : value.split(Constants.ARRAY_SEPARATOR))
                values.add(a.trim());
            res = Triplet.of(overRide, finalKey, values);
        } else {
            res = Triplet.of(overRide, finalKey, value);
        }
        return res;
    }

    private static String handleGroup(final String line) throws LoadException {
        int len = line.length();
        if (line.charAt(len - 1) != ']') throw new LoadException("Bad Input. Group should end with ]");
        String groupName = line.substring(1, len - 1);
        return groupName;
    }


    private static boolean isLineEmpty(String line) {
        return line.trim().isEmpty();
    }

    /*
     * Removes the comment part from each line.
     */
    private static String removeComment(String curLine) {
        if (curLine.isEmpty()) return curLine;
        int firstIdx = curLine.indexOf(Constants.COMMENT_START);
        if (firstIdx == -1) return curLine;
        return curLine.substring(0, firstIdx);
    }
}

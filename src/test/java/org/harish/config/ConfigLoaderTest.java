package org.harish.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ConfigLoaderTest {

    private final DefaultConfigLoader classUnderTest = new DefaultConfigLoader();

    @Test
    public void testLoadConfigString() {
        try {
            Config config = classUnderTest.loadConfig("src/test/resources/configfile1");
            commonConfigTestWithOrWithOutOverRide(config);
            testWithOutOverRides(config);
        } catch (LoadException e) {
            fail("Failed to load config.");
        }
    }

    @Test
    public void testLoadConfigFile() {
        File file = new File("src/test/resources/configfile1");
        try {
            Config config = classUnderTest.loadConfig(file);
            commonConfigTestWithOrWithOutOverRide(config);
            testWithOutOverRides(config);
        } catch (LoadException e) {
            fail("Failed to load config");
        }
    }

    @Test
    public void testLoadConfigReader() {
        BufferedReader br = null;
        try {
            br = Files.newBufferedReader(Paths.get("src/test/resources/configfile1"), Charset.defaultCharset());
            Config config = classUnderTest.loadConfig(br);
            commonConfigTestWithOrWithOutOverRide(config);
            testWithOutOverRides(config);
        } catch (
                IOException | LoadException e) {
            fail("Failed to load config");
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                fail("Failed to load config");
            }
        }
    }

    @Test
    public void testLoadConfigStringWithOverRide() {
        try {
            Config config = classUnderTest.loadConfig("src/test/resources/configfile1", "ubuntu", "production");
            commonConfigTestWithOrWithOutOverRide(config);
            testWithOverRides(config);
        } catch (LoadException e) {
            fail("Failed to load config.");
        }
    }

    @Test
    public void testLoadConfigFileWithOverRide() {
        File file = new File("src/test/resources/configfile1");
        try {
            Config config = classUnderTest.loadConfig(file, "ubuntu", "production");
            commonConfigTestWithOrWithOutOverRide(config);
            testWithOverRides(config);
        } catch (LoadException e) {
            fail("Failed to load config");
        }
    }

    @Test
    public void testLoadConfigReaderWithOverRide() {
        BufferedReader br = null;
        try {
            br = Files.newBufferedReader(Paths.get("src/test/resources/configfile1"), Charset.defaultCharset());
            Config config = classUnderTest.loadConfig(br, "ubuntu", "production");
            commonConfigTestWithOrWithOutOverRide(config);
            testWithOverRides(config);
        } catch (
                IOException | LoadException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                fail();
            }
        }
    }

    private void commonConfigTestWithOrWithOutOverRide(Config config) {
        assertEquals(Long.valueOf(2147483648l), config.getConfigAsLong("common.paid_users_size_limit"));
        assertEquals("hello there, ftp uploading", config.getConfig("ftp.name"));
        List<String> expected = new ArrayList<>();
        expected.add("array");
        expected.add("of");
        expected.add("values");
        assertEquals(expected, config.getConfig("http.params"));
        assertEquals(null, config.getConfig("ftp.lastname"));
        assertEquals(false, config.getConfigAsBoolean("ftp.enabled"));
    }

    private void testWithOverRides(Config config) {
        assertEquals("/etc/var/uploads", config.getConfig("ftp.path"));
    }

    private void testWithOutOverRides(Config config) {
        assertEquals("/tmp/", config.getConfig("ftp.path"));
    }

    @Test(expected = LoadException.class)
    public void failLoadWithBadGroup() throws LoadException {
        classUnderTest.loadConfig("src/test/resources/configfile2");
    }

    @Test(expected = LoadException.class)
    public void failLoadWithBadOverrideTag() throws LoadException {
        classUnderTest.loadConfig("src/test/resources/configfile3");
    }

    @Test(expected = LoadException.class)
    public void failLoadKVSeparatorMissing() throws LoadException {
        classUnderTest.loadConfig("src/test/resources/configfile4");
    }

    @Test(expected = LoadException.class)
    public void failLoadOverRideMissing() throws LoadException {
        classUnderTest.loadConfig("src/test/resources/configfile5");
    }

    @Test(expected = LoadException.class)
    public void failLoadKeyMissing() throws LoadException {
        classUnderTest.loadConfig("src/test/resources/configfile6");
    }

    @Test(expected = LoadException.class)
    public void failLoadBadValue() throws LoadException {
        classUnderTest.loadConfig("src/test/resources/configfile7");
    }
    
    @Test(expected = LoadException.class)
    public void failLoadBadFile() throws LoadException {
        classUnderTest.loadConfig("src/test/resources/configfile8");
    }
}

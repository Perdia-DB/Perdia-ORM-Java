package at.davideko.perdia.queries;

import at.davideko.perdia.queries.data.DataEntry;
import at.davideko.perdia.queries.data.DataType;
import at.davideko.perdia.queries.data.StringDataEntry;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Java Unit Testing class for the Instance class (excluding the preset methods)
 */
class TemplateTest {

    @Test
    void addEntryTest() {
        Template test = new Template("test");
        test.addEntry("First", DataType.STRING, "One");

        assertEquals("One", test.getDataEntry("First").read());

        DataEntry second = new StringDataEntry("Two");
        test.addEntry("Second", second);

        assertEquals("Two", test.getDataEntry("Second").read());
    }

    @Test
    void addEntriesTest() {
        Template test = new Template("test");
        HashMap<String, DataEntry> hm = new HashMap<>();

        hm.put("First", new StringDataEntry("One"));
        hm.put("Second", new StringDataEntry("Two"));

        test.addEntries(hm);

        assertEquals(hm, test.getData());
    }

    @Test
    void toCreationQueryTest() {
        Template test = new Template("test");
        test.addEntry("First", DataType.STRING, "One");

        assertEquals("TEMPLATE \"test\"; \n" +
                             "STRING \"First\" VALUE \"One\"; \n" +
                             "END \"test\"; \n", test.toCreationQuery());
    }

    @Test
    void toQueryTest() {
        Template test = new Template("test");

        assertEquals("QUERY \"test\" FROM TEMPLATE; \n", test.toQuery());

        assertEquals("QUERY \"otherTest\" FROM TEMPLATE; \n", Template.toQuery("otherTest"));
    }

    @Test
    void queryAllTest() {
        assertEquals("QUERY TEMPLATE; \n", Template.queryAll());
    }

    @Test
    void getNameTest() {
        Template test = new Template("test");

        assertEquals("test", test.getName());
    }

    @Test
    void setNameTest() {
        Template test = new Template("test");

        assertEquals("test", test.getName());

        test.setName("newName");

        assertEquals("newName", test.getName());
    }

    @Test
    void getDataTest() {
        Template day = new Template("Day");
        DataEntry morning = new StringDataEntry("Empty");
        day.addEntry("Morning", morning);

        assertEquals(morning, day.getData().get("Morning"));
    }

    @Test
    void getDataEntryTest() {
        Template day = new Template("Day");
        DataEntry morning = new StringDataEntry("Empty"), midday = new StringDataEntry("Empty"),
                  evening = new StringDataEntry("Empty");
        day.addEntry("Morning", morning);
        day.addEntry("Midday", midday);
        day.addEntry("Evening", evening);

        assertNotEquals(morning, day.getDataEntry("Midday"));
        assertNotEquals(morning, day.getDataEntry("Evening"));
        assertEquals(morning, day.getDataEntry("Morning"));
    }

    @Test
    void deleteQueryTest() {
        Template test = new Template("test");

        assertEquals("DELETE \"test\" FROM TEMPLATE; \n", test.deleteQuery());
    }
}
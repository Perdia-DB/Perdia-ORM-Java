package com.github.perdia.queries;

import com.github.perdia.queries.data.DataEntry;
import com.github.perdia.queries.data.DataType;
import com.github.perdia.queries.data.StringDataEntry;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Java Unit Testing class for the Instance class
 */
class InstanceTest {

    @Test
    void createInstanceTest() {
        Instance test = new Instance("test");
        assertNull(test.getTemplate());

        Template day = new Template("Day");
        test.createInstance(day);

        assertEquals(day, test.getTemplate());
    }

    @Test
    void copyInstanceTest() {
        Instance test = new Instance("test");
        assertNull(test.getTemplate());

        Template day = new Template("Day");
        day.addEntry("Morning", DataType.STRING, "Empty");
        Instance monday = new Instance("Monday");
        monday.createInstance(day);
        DataEntry breakfast = new StringDataEntry("Breakfast");
        monday.setData("Morning", breakfast);

        test.copyInstance(monday);

        assertEquals(monday.getTemplate(), test.getTemplate());
        assertEquals(monday.getData(), test.getData());
    }

    @Test
    void setDataTest() {
        Instance test = new Instance("test");
        Template day = new Template("Day");
        day.addEntry("Morning", DataType.STRING, "Empty");
        day.addEntry("Midday", DataType.STRING, "Empty");
        day.addEntry("Evening", DataType.STRING, "Empty");

        Instance monday = new Instance("Monday");
        monday.createInstance(day);

        DataEntry breakfast = new StringDataEntry("Breakfast");
        monday.setData("Morning", breakfast);
        assertEquals(breakfast, monday.getDataEntry("Morning"));

        monday = new Instance("Monday");
        monday.createInstance(day);

        DataEntry lunch = new StringDataEntry("Lunch");
        DataEntry dinner = new StringDataEntry("Dinner");
        HashMap<String, DataEntry> hm = new HashMap<>();
        hm.put("Morning", breakfast);
        hm.put("Midday", lunch);
        hm.put("Evening", dinner);

        monday.setData(hm);

        assertEquals(hm, monday.getData());
    }

    @Test
    void toQueryTest() {
        Instance test = new Instance("test");

        assertEquals("QUERY \"test\" FROM INSTANCE; \n", test.toQuery());

        assertEquals("QUERY \"otherTest\" FROM INSTANCE; \n", Instance.toQuery("otherTest"));
    }

    @Test
    void queryAllTest() {
        assertEquals("QUERY INSTANCE; \n", Instance.queryAll());
    }

    @Test
    void getNameTest() {
        Instance test = new Instance("test");

        assertEquals("test", test.getName());
    }

    @Test
    void setNameTest() {
        Instance test = new Instance("test");

        assertEquals("test", test.getName());

        test.setName("newName");

        assertEquals("newName", test.getName());
    }

    @Test
    void getTemplateTest() {
        Template day = new Template("Day");
        Instance test = new Instance("test", day);

        assertEquals(day, test.getTemplate());
    }

    @Test
    void setTemplateTest() {
        Template day = new Template("Day");
        Instance test = new Instance("test");

        assertNull(test.getTemplate());

        test.setTemplate(day);

        assertEquals(day, test.getTemplate());
    }

    @Test
    void getDataTest() {
        Template day = new Template("Day");
        DataEntry morning = new StringDataEntry("Empty");
        day.addEntry("Morning", morning);
        Instance test = new Instance("test", day);

        assertEquals(morning, test.getData().get("Morning"));
    }

    @Test
    void getDataEntryTest() {
        Template day = new Template("Day");
        DataEntry morning = new StringDataEntry("Empty"), midday = new StringDataEntry("Empty"),
                  evening = new StringDataEntry("Empty");
        day.addEntry("Morning", morning);
        day.addEntry("Midday", midday);
        day.addEntry("Evening", evening);
        Instance test = new Instance("test", day);

        assertNotEquals(morning, test.getDataEntry("Midday"));
        assertNotEquals(morning, test.getDataEntry("Evening"));
        assertEquals(morning, test.getDataEntry("Morning"));
    }

    @Test
    void deleteQueryTest() {
        Instance test = new Instance("test");

        assertEquals("DELETE \"test\" FROM INSTANCE; \n", test.deleteQuery());
    }
}
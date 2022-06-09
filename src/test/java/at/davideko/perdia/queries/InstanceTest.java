package at.davideko.perdia.queries;

import at.davideko.perdia.queries.data.DataEntry;
import at.davideko.perdia.queries.data.StringDataEntry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InstanceTest {

    @Test
    void createInstanceTest() {
        Instance test = new Instance("test");
        assertNull(test.getTemplate());

        Template day = new Template("Day");
        test.createInstance(day);

        assertEquals(test.getTemplate(), day);
    }

    @Test
    void copyInstanceTest() {
        Instance test = new Instance("test");
        assertNull(test.getTemplate());

        Template day = new Template("Day");
        Instance monday = new Instance("Monday");
        monday.createInstance(day);
        DataEntry breakfast = new StringDataEntry("Breakfast");
        monday.setData("Morning", breakfast);

        test.copyInstance(monday);

        assertEquals(test.getTemplate(), monday.getTemplate());
        assertEquals(test.getName(), monday.getName());
        assertEquals(test.getData(), monday.getData());
    }

    @Test
    void writeToQueryObjectTest() {

    }

    @Test
    void otherWriteToQueryObjectTest() {
    }

    @Test
    void toQueryTest() {
    }

    @Test
    void testToQueryTest() {
    }

    @Test
    void queryAllTest() {
    }

    @Test
    void getNameTest() {
    }

    @Test
    void setNameTest() {
    }

    @Test
    void getTemplateTest() {
    }

    @Test
    void setTemplateTest() {
    }

    @Test
    void getDataTest() {
    }

    @Test
    void getDataEntryTest() {
    }

    @Test
    void deleteQueryTest() {
    }
}
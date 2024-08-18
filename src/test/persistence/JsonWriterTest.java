package persistence;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import model.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//tests for data persistence, specifically writing and making sure that the data provided writes to file
public class JsonWriterTest {

    @Test
    void testWriterNonExistentFile() {
        try {
            List<Collection> cols = new ArrayList<Collection>();
            JsonWriter writer = new JsonWriter("./data/I\0\11NVALID_ADDRESS.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }

    }

    @Test
    void testWriterNoCollections() {
        try {
            List<Collection> cols = new ArrayList<Collection>();
            JsonWriter writer = new JsonWriter("./data/emptyCollections.json");
            writer.open();

            writer.write(cols);
            writer.close();


            JsonReader reader = new JsonReader("./data/emptyCollections.json");
            cols = reader.read();

            assertEquals(0,cols.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterCollections1Collection() {
        try {
            List<Collection> cols = new ArrayList<Collection>();
            JsonWriter writer = new JsonWriter("./data/testCollections.json");

            Collection maths = new Collection("Maths");

            List<String> answers = new ArrayList<>();
            answers.add("1");
            answers.add("2");
            answers.add("3");
            answers.add("4");


            maths.addQuestion("2 + 2",answers,4);

            cols.add(maths);

            writer.open();

            writer.write(cols);
            writer.close();


            JsonReader reader = new JsonReader("./data/testCollections.json");
            cols = reader.read();

            assertEquals(1,cols.size());
            assertEquals("Maths",cols.get(0).getCollectionName());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }



}

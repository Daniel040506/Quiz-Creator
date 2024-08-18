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

//tests for data persistence, reading data from a file and checking validity of read data
public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/DOESNTEXIST.json");
        try {
            List<Collection> cols = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/emptyCollections.json");
        try {
            List<Collection> cols = reader.read();

            assertEquals(0, cols.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderCollections1Collection() {
        JsonReader reader = new JsonReader("./data/testCollections.json");
        try {
            List<Collection> cols = reader.read();
            assertEquals("Maths", cols.get(0).getCollectionName());
            assertEquals(1,cols.size());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }



}

package persistence;

import model.*;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;


//modelled after json example program's writer, find it here
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonWriter {

    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    //EFFECTS: constructs JsonWriter with given destination string
    public JsonWriter(String destination) {

        this.destination = destination;
    }

    //EFFECTS: opens Jsonwriter, throws FileNotFoundException if destination doesn't exist
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    //EFFECTS: writes to the given file given list of collections
    public void write(List<Collection> collections) {

        JSONArray mergedCollections = new JSONArray();

        for (Collection c: collections) {

            JSONObject json = c.toJson();

            mergedCollections.put(json);


        }

        JSONObject mergedFinal = new JSONObject();
        mergedFinal.put("COLLECTIONS",mergedCollections);

        saveToFile(mergedFinal.toString(TAB));


    }

    //EFFECTS: closes writer
    public void close() {
        writer.close();
    }


    //EFFECTS: writes to the given file, saving data
    private void saveToFile(String json) {
        writer.print(json);
    }


}

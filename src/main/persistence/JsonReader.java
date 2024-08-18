package persistence;

import model.*;




import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;


//modelled after json example program's reader, find it here
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReader {

    private String source;

    public JsonReader(String source) {

        this.source = source;

    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public List<Collection> read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCollections(jsonObject);
    }


    //EFFECTS: gets all collections from the json file, then parses them one by one in helper function, then
    // returns collections
    private List<Collection> parseCollections(JSONObject jsonObject) {

        List<Collection> collections = new ArrayList<Collection>();

        JSONArray jsonArray = jsonObject.getJSONArray("COLLECTIONS");

        for (Object json : jsonArray) {

            JSONObject nextCollection = (JSONObject) json;

            collections.add(parseCollection(nextCollection));

        }

        return collections;

    }


    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }




    //EFFECTS: helper function to parse individual collection, creating an empty collection and adding all the questions
    private Collection parseCollection(JSONObject jsonObject) {

        String collectionName = jsonObject.getString("collectionName");
        Collection c = new Collection(collectionName);

        addQuestions(c,jsonObject);

        return c;



    }


    //EFFECTS: adds all the questions, parsing through them with helper function
    private void addQuestions(Collection c, JSONObject jsonObject) {

        JSONArray jsonArray = jsonObject.getJSONArray("Questions");

        for (Object json : jsonArray) {

            JSONObject nextQuestion = (JSONObject) json;
            addQuestion(c,nextQuestion);

        }



    }


    //EFFECTS: adds individual question, parsing through its name, answers and correct answer to reconstruct question
    private void addQuestion(Collection c, JSONObject jsonObject) {

        String questionName = jsonObject.getString("questionName");

        List<String> answers = new ArrayList<String>();

        JSONArray jsonArray = jsonObject.getJSONArray("Answers");

        for (Object json : jsonArray) {

            answers.add(json.toString());

        }

        int correctAnswer = jsonObject.getInt("CorrectAnswer");

        c.addQuestion(questionName,answers,correctAnswer);


    }










}

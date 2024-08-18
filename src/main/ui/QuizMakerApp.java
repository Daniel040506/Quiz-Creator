package ui;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.Event;
import model.EventLog;
import model.QuizQuestion;
import model.Collection;
import persistence.JsonReader;
import persistence.JsonWriter;





import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;


// Quiz maker app where you use collections and questions

public class QuizMakerApp {

    private Scanner input;
    private List<Collection> collections;
    private List<String> results;

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private static final String JSON_ADDRESS = "./data/USER_DATA.json";




    //EFFECTS: when making a new QuizMakerApp, simply begins the run function

    public QuizMakerApp() {

        run();

    }

    //EFFECTS: begins running the program, this function checks if the program is still running

    private void run() {

        boolean running = true;
        String command = null;

        boot();

        while (running) {

            displayMainMenu();

            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {

                printLog();
                running = false;

            } else {

                runMainMenuCommand(command);

            }
        }

        System.out.println("\nthanks for trying this program");


    }

    //EFFECTS: Initializes the collections, results and input

    private void boot() {

        collections = new ArrayList<Collection>();
        results = new ArrayList<String>();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_ADDRESS);
        jsonReader = new JsonReader(JSON_ADDRESS);

    }

    //EFFECTS: displays options to the user on the main menu

    private void displayMainMenu() {

        System.out.println("\nSelect from:");
        System.out.println("\ta -> add new Collection");
        System.out.println("\tm -> modify collections");
        System.out.println("\tp -> practice on a collection");
        System.out.println("\th -> history and results of practice");
        System.out.println("\ti -> Information");
        System.out.println("\ts -> SAVE DATA");
        System.out.println("\tl -> LOAD DATA");
        System.out.println("\tq -> quit");


    }

    //EFFECTS: takes user input, based off instructions provided in the main menu function

    private void runMainMenuCommand(String command) {

        if (command.equals("a")) {
            addNewCollection();
        } else if (command.equals("m")) {
            modifyCollections();
        } else if (command.equals("p")) {
            practiceCollections();
        } else if (command.equals("h")) {
            viewHistory();
        } else if (command.equals("i")) {

            programInformation();

        } else if (command.equals("s")) {

            saveData();

        } else if (command.equals("l")) {

            loadData();
        } else {
            System.out.println("invalid command");
        }


    }

    //EFFECTS: adds new collection of quiz questions to collections
    //MODIFIES: this

    private void addNewCollection() {


        String collectionNameSelection = "";

        while (collectionNameSelection.equals("")) {
            System.out.println("Please provide a non-empty name for the collection");

            collectionNameSelection = input.next();

            if (collectionNameSelection.equals("")) {

                System.out.println("empty collection name cannot be accepted");
            }


        }

        collections.add(new Collection(collectionNameSelection));
        System.out.println("New collection generated");

    }

    //EFFECTS: takes user prompt to decide what collection to modify, or denies request to modify

    private void modifyCollections() {

        System.out.println("Please select a collection to modify (case sensitive)");
        displayCollections();

        String collectionToModify = input.next();

        if (getCollectionNames().contains(collectionToModify)) {

            modifyCollectionCommand(collectionToModify);

        } else {

            System.out.println("Requested collection is not available");
        }


    }


    //EFFECTS: provides user options to modify collection, and takes their input to decide what function to call next

    private void modifyCollectionCommand(String collectionToModify) {

        System.out.println("\nSelect from the following to modify" + collectionToModify + ":");
        System.out.println("\ta -> add new quiz question to this collection");
        System.out.println("\tr -> remove quiz question from this collection");
        System.out.println("\td -> delete this collection");

        String command = input.next();
        Collection modifyCollection =  collections.get(getCollectionNames().indexOf(collectionToModify));


        if (command.equals("a")) {

            addQuizQuestionToCollection(modifyCollection);

        } else if (command.equals("r")) {

            removeQuizQuestionToCollection(modifyCollection);

        } else if (command.equals("d")) {

            deleteCollection(modifyCollection);

        } else {

            System.out.println("Invalid input for modifying collection...");


        }

    }

    //EFFECTS: adds a question to a collection through a series of user prompts
    //MODIFIES: this

    @SuppressWarnings("methodlength")
    private void addQuizQuestionToCollection(Collection c) {

        String questionName = "";
        while (questionName.equals("")) {
            System.out.println("Please provide a non empty question");
            questionName = input.next();
        }
        List<String> answers = new ArrayList<>();
        while (answers.size() < 4) {

            String answer = "";
            System.out.println("Please provide a non empty answer to the question");
            answer = input.next();

            if (answer.equals("")) {
                System.out.println("cannot accept empty answer to question");
            } else {
                answers.add(answer);
            }
        }

        int correctAnswer = -1;

        while (correctAnswer == -1) {

            System.out.println("Please select the correct answer from the provided answers (1-4):");

            correctAnswer = input.nextInt();

            if (correctAnswer > answers.size() || correctAnswer < 1) {

                System.out.println("Invalid answer");
                correctAnswer = -1;

            }

        }

        c.addQuestion(questionName,answers,correctAnswer);

    }

    //EFFECTS: removes a question from a collection
    //MODIFIES: this

    private void removeQuizQuestionToCollection(Collection c) {

        String questionToRemove = "";

        while (questionToRemove == "") {

            System.out.println("Please select a question to remove from the following");

            displayQuestions(c);

            questionToRemove = input.next();

            List<String> questionNames = getQuestionNames(c);

            if (questionNames.contains(questionToRemove)) {

                c.removeQuestion(questionNames.indexOf(questionToRemove));

            } else {

                System.out.println("Question doesn't exist in this collection");
                questionToRemove = "";


            }

        }

    }
    //EFFECTS: deletes the requested collection
    //MODIFIES: this

    private void deleteCollection(Collection c) {

        collections.remove(collections.indexOf(c));
    }

    //EFFECTS: displays the questions in a provided collection
    private void displayQuestions(Collection c) {

        System.out.println("Questions for " + c.getCollectionName() + ":");

        for (QuizQuestion q : c.getQuestions()) {

            System.out.println(q.getQuestion());

        }


    }

    //EFFECTS: displays answers of a given question's answers field

    private void displayAnswers(List<String> answers) {

        System.out.println("Answers:");



        for (String a : answers) {

            Integer index = answers.indexOf(a) + 1;
            System.out.println(index.toString() + a);

        }

    }


    //EFFECTS: method used to decide what collection to practice based on user input
    //MODIFIES: this

    private void practiceCollections() {
        if (collections.isEmpty()) {

            System.out.println("No collections have been made, please create a collection first");
            return;
        }
        displayCollections();
        System.out.println("Please choose a collection to practice");

        String command = input.next();



        if (getCollectionNames().contains(command)) {


            Collection collectionToPractice =  collections.get(getCollectionNames().indexOf(command));
            practiceCollection(collectionToPractice);

        } else {

            System.out.println("Requested collection is not available");
        }




    }

    //EFFECTS: method used to practice on a given collection and generate a result of performance
    //MODIFIES: this

    private void practiceCollection(Collection c) {

        int score = 0;

        for (QuizQuestion q : c.getQuestions()) {

            Integer index = (c.getQuestions().indexOf(q)) + 1;
            String indexString = index.toString();
            System.out.println("Question " + indexString);

            displayQuestionForPractice(q);

            System.out.println("Please choose an answer (from 1 to 4)");

            int answer = input.nextInt();

            if (answer == q.getCorrectAnswer()) {

                score += 1;
                System.out.println("Correct!");

            } else {

                System.out.println("Incorrect");

            }

        }

        String result = c.generateResult(score);

        results.add(result);
        System.out.println(result);


    }

    //EFFECTS: displays a given question when practicing

    private void displayQuestionForPractice(QuizQuestion q) {

        System.out.println(q.getQuestion());

        for (String a : q.getAnswers()) {

            Integer index = (q.getAnswers().indexOf(a)) + 1;
            String indexString = index.toString();

            System.out.println(indexString + ": " + a);

        }



    }




    //EFFECTS: displays all available collections

    private void displayCollections() {
        System.out.println("Collections:");

        for (Collection c : collections) {

            System.out.println("\t" + c.getCollectionName());
        }


    }

    //EFFECTS: retrieves the names of all collections

    private List<String> getCollectionNames() {

        List<String> collectionNames = new ArrayList<String>();

        for (Collection c : collections) {

            collectionNames.add(c.getCollectionName());

        }

        return collectionNames;

    }

    //EFFECTS: retrieves all the question names for a given collection

    private List<String> getQuestionNames(Collection c) {

        List<String> questionNames = new ArrayList<>();

        for (QuizQuestion q : c.getQuestions()) {

            questionNames.add(q.getQuestion());

        }

        return questionNames;

    }


    //EFFECTS: displays all previous history of practice results, OR informs the user they haven't practiced yet

    private void viewHistory() {

        if (results.isEmpty()) {

            System.out.println("No practice has been done yet");
            return;
        }

        System.out.println("History of practice: ");

        for (String r : results) {

            System.out.println(r);

        }
    }

    //EFFECTS: prints out basic information about program purpose and use.

    private void programInformation() {

        System.out.println("What is the purpose of this program?"
                + "\n This program is used as a tool to create and use practice materials for studying"
                + "\n\n"
                + "What is a Collection?"
                + "\nA collection is a subject or related series of quiz questions"
                + "\n\n"
                + "Where do I begin?"
                + "\nBegin by creating a collection. Then, in the modify collections menu, you can"
                + "\nAdd questions to that collection. Afterwards, you can practice and see your results.");

    }

    //EFFECTS: saves current state of the program
    private void saveData() {

        try {
            jsonWriter.open();
            jsonWriter.write(collections);
            jsonWriter.close();
            System.out.println("Saved data to " + JSON_ADDRESS);
        } catch (FileNotFoundException e) {

            System.out.println("Unable to write to file: " + JSON_ADDRESS);
        }

    }

    //EFFECTS: loads current state of the program
    private void loadData() {

        try {
            collections = jsonReader.read();
            System.out.println("Loaded data from " + JSON_ADDRESS);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_ADDRESS);
        }


    }

    //EFFECTS: prints all of the current events in the event log
    //NOTE: this will be empty if nothing is logged, as the function itself in the UI does not create any logs
    private void printLog() {

        System.out.println("Log Events: ");

        for (Event e : EventLog.getInstance()) {

            System.out.println(e.getDescription());

        }
    }



}

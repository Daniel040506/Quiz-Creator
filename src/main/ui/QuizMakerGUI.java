package ui;

import java.awt.event.*;

import javax.swing.*;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.EventLog;
import model.Event;
import model.QuizQuestion;
import model.Collection;
import persistence.JsonReader;
import persistence.JsonWriter;


// GUI application for interacting with the quiz maker
public class QuizMakerGUI extends JFrame {

    private QuizMakerApp qma;
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;

    private List<Collection> collections;

    private static final String JSON_ADDRESS = "./data/USER_DATA.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;


    private JPanel mainMenu;

    //EFFECTS: instantiates the GUI with a list of collections, as well as save and load functionality + all other UI
    // buttons required

    public QuizMakerGUI() {



        collections = new ArrayList<Collection>();

        jsonWriter = new JsonWriter(JSON_ADDRESS);
        jsonReader = new JsonReader(JSON_ADDRESS);

        mainMenu = new JPanel();
        mainMenu.addMouseListener(new MouseAdapter() {
        });

        setTitle("QuizMakerApp");
        setSize(WIDTH, HEIGHT);

        mainMenu.setLayout(new BoxLayout(mainMenu,BoxLayout.Y_AXIS));

        addManyButtons();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLog();
                super.windowClosing(e);
            }
        });



        this.add(mainMenu);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        pack();



    }

    //EFFECTS: adds several buttons
    private void addManyButtons() {

        addDataButtons();
        addQuestionButtons();
        removeQuestionButtons();
        showCollectionsButton();
        findQuestionPopUpButton();
        visualQuestionButton();
        addCollectionButton();


    }


    //EFFECTS: constructs buttons used for save and load functions
    //MODIFIES: this, USER_DATA.json
    private void addDataButtons() {

        JPanel dataManagementPanel = new JPanel();

        JButton loadButton = new JButton("Load Data");

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                load();

            }

        });

        JButton saveButton = new JButton("Save Data");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                save();


            }

        });


        dataManagementPanel.add(loadButton);
        dataManagementPanel.add(saveButton);

        mainMenu.add(dataManagementPanel);


    }


    //EFFECTS: load function, copied from QuizMakerApp code
    private void load() {

        try {
            collections = jsonReader.read();
            errorPopUp("Loaded data from " + JSON_ADDRESS);

        } catch (IOException io) {
            errorPopUp("Unable to read from file: " + JSON_ADDRESS);

        }


    }

    //EFFECTS: save function, copied from QuizMakerApp code
    private void save() {

        try {
            jsonWriter.open();
            jsonWriter.write(collections);
            jsonWriter.close();
            errorPopUp("Saved data to " + JSON_ADDRESS);

        } catch (FileNotFoundException fnf) {


            errorPopUp("Unable to write to file: " + JSON_ADDRESS);

        }


    }


    //EFFECTS: constructs buttons used to add a question to a collection, with 5 fields and a button to accept input
    //MODIFIES: this
    @SuppressWarnings("methodlength")
    private void addQuestionButtons() {

        JPanel addQuestionButtonMainPanel = new JPanel();

        addQuestionButtonMainPanel.setLayout(new BoxLayout(addQuestionButtonMainPanel, BoxLayout.Y_AXIS));



        // collection input
        JPanel collectionField = new JPanel();
        JLabel collectionInputLabel = new JLabel("Collection:");
        JTextField collectionInputTextField = new JTextField(20);
        collectionField.add(collectionInputLabel);
        collectionField.add(collectionInputTextField);
        addQuestionButtonMainPanel.add(collectionField);


        // question name input
        JPanel questionNameField = new JPanel();
        JLabel questionNameInputLabel = new JLabel("Question:");
        JTextField questionNameInputTextField = new JTextField(20);
        questionNameField.add(questionNameInputLabel);
        questionNameField.add(questionNameInputTextField);
        addQuestionButtonMainPanel.add(questionNameField);


        // first answer
        JPanel answer1Field = new JPanel();
        JLabel answer1InputLabel = new JLabel("Answer 1");
        JTextField answer1InputTextField = new JTextField(20);
        answer1Field.add(answer1InputLabel);
        answer1Field.add(answer1InputTextField);
        addQuestionButtonMainPanel.add(answer1Field);


        // second answer
        JPanel answer2Field = new JPanel();
        JLabel answer2InputLabel = new JLabel("Answer 2");
        JTextField answer2InputTextField = new JTextField(20);
        answer2Field.add(answer2InputLabel);
        answer2Field.add(answer2InputTextField);
        addQuestionButtonMainPanel.add(answer2Field);

        // third answer
        JPanel answer3Field = new JPanel();
        JLabel answer3InputLabel = new JLabel("Answer 3");
        JTextField answer3InputTextField = new JTextField(20);
        answer3Field.add(answer3InputLabel);
        answer3Field.add(answer3InputTextField);
        addQuestionButtonMainPanel.add(answer3Field);


        // fourth answer
        JPanel answer4Field = new JPanel();
        JLabel answer4InputLabel = new JLabel("Answer 4");
        JTextField answer4InputTextField = new JTextField(20);
        answer4Field.add(answer4InputLabel);
        answer4Field.add(answer4InputTextField);
        addQuestionButtonMainPanel.add(answer4Field);


        // correct answer
        JPanel correctAnswerField = new JPanel();
        JLabel correctAnswerInputLabel = new JLabel("Correct Answer (1-4)");
        JTextField correctAnswerInputTextField = new JTextField(20);
        correctAnswerField.add(correctAnswerInputLabel);
        correctAnswerField.add(correctAnswerInputTextField);
        addQuestionButtonMainPanel.add(correctAnswerField);


        JButton addQuestionButton = new JButton("Add Question");
        addQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tryAddQuestion(collectionInputTextField.getText(),questionNameInputTextField.getText(),
                        answer1InputTextField.getText(),answer2InputTextField.getText(),answer3InputTextField.getText(),
                        answer4InputTextField.getText(),correctAnswerInputTextField.getText());

            }
        });


        mainMenu.add(addQuestionButton);


        mainMenu.add(addQuestionButtonMainPanel);

    }


    //EFFECTS: function to add a question to a collection, given several inputs. If inputs are invalid, provides an
    // error popup, otherwise adds the question
    //MODIFIES: this
    private void tryAddQuestion(String collection, String questionName, String a1, String a2,
                               String a3, String a4, String correctAnswer) {


        if (collection.isEmpty()) {

            errorPopUp("Please provide an existing collection to modify");

            return;

        } else if (a1.isEmpty() || a2.isEmpty() || a3.isEmpty() || a4.isEmpty() || questionName.isEmpty()) {

            errorPopUp("Please provide 4 answers to the question and a valid question");
            return;
        } else if (Integer.valueOf(correctAnswer) > 4 || Integer.valueOf(correctAnswer) < 1) {

            errorPopUp("Correct answer must be withing valid range");
            return;
        }




        if (getCollectionNames().contains(collection)) {

            Collection modifyCollection = collections.get(getCollectionNames().indexOf(collection));

            List<String> answers = new ArrayList<>();

            answers.add(a1);
            answers.add(a2);
            answers.add(a3);
            answers.add(a4);



            modifyCollection.addQuestion(questionName,answers,Integer.valueOf(correctAnswer));


            System.out.println(modifyCollection.getQuestions());


        }

    }


    //EFFECTS: button and UI elements to remove a question from a collection
    //MODIFIES: this
    private void removeQuestionButtons() {

        JPanel removeQuestionButtonMainPanel = new JPanel();

        removeQuestionButtonMainPanel.setLayout(new BoxLayout(removeQuestionButtonMainPanel, BoxLayout.Y_AXIS));


        // collection input
        JPanel collectionField = new JPanel();
        JLabel collectionInputLabel = new JLabel("Collection:");
        JTextField collectionInputTextField = new JTextField(20);
        collectionField.add(collectionInputLabel);
        collectionField.add(collectionInputTextField);
        removeQuestionButtonMainPanel.add(collectionField);


        // question name input
        JPanel questionNameField = new JPanel();
        JLabel questionNameInputLabel = new JLabel("Question:");
        JTextField questionNameInputTextField = new JTextField(20);
        questionNameField.add(questionNameInputLabel);
        questionNameField.add(questionNameInputTextField);
        removeQuestionButtonMainPanel.add(questionNameField);



        JButton removeQuestionButton = new JButton("Remove Question");
        removeQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryRemoveQuestion(collectionInputTextField.getText(),questionNameInputTextField.getText());
            }
        });


        mainMenu.add(removeQuestionButton);


        mainMenu.add(removeQuestionButtonMainPanel);



    }

    //EFFECTS: button and UI elements to remove a question from a collection
    //MODIFIES: this
    private void addCollectionButton() {

        JPanel addCollectionButtonMainPanel = new JPanel();

        addCollectionButtonMainPanel.setLayout(new BoxLayout(addCollectionButtonMainPanel, BoxLayout.Y_AXIS));


        // collection input
        JPanel collectionField = new JPanel();
        JLabel collectionInputLabel = new JLabel("Collection:");
        JTextField collectionInputTextField = new JTextField(20);
        collectionField.add(collectionInputLabel);
        collectionField.add(collectionInputTextField);
        addCollectionButtonMainPanel.add(collectionField);


        JButton addCollectionButton = new JButton("Add Collection");
        addCollectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryAddCollection(collectionInputTextField.getText());
            }
        });

        mainMenu.add(addCollectionButton);
        mainMenu.add(addCollectionButtonMainPanel);

    }

    private void tryAddCollection(String c) {

        List<String> collectionNames = getCollectionNames();

        if (collectionNames.contains(c)) {

            errorPopUp("Collection Already Exists");
            return;

        } else {

            collections.add(new Collection(c));

        }


    }





    //EFFECTS: tries to remove a question, give that it exists in the collection
    //MODIFIES: this
    private void tryRemoveQuestion(String collection, String questionName) {

        if (collection.isEmpty() || questionName.isEmpty()) {


            errorPopUp("Please provide a collection and question to remove");
            return;
        }


        if (getCollectionNames().contains(collection)) {

            Collection modifyCollection = collections.get(getCollectionNames().indexOf(collection));

            List<QuizQuestion> questionList = modifyCollection.getQuestions();
            List<String> questionNames = new ArrayList<>();

            for (QuizQuestion q : questionList) {

                questionNames.add(q.getQuestion());

            }

            if (questionNames.contains(questionName)) {

                modifyCollection.removeQuestion(questionNames.indexOf(questionName));

            } else {

                errorPopUp("Question does not exist in this collection");
                return;
            }



            System.out.println(modifyCollection.getQuestions());


        }




    }



    //EFFECTS: UI and button to allow user to see all collections
    private void showCollectionsButton() {

        JPanel showCollectionsPanel = new JPanel();

        JButton showCollectionButton = new JButton("Show Collections");
        showCollectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collectionsMenuPopUp();
            }
        });

        showCollectionsPanel.add(showCollectionButton);
        mainMenu.add(showCollectionsPanel);


    }

    //EFFECTS: creates a JFrame pop up menu which shows all the collections
    private void collectionsMenuPopUp() {

        JFrame collectionsPopUp = new JFrame("Collections");

        JPanel collectionPopUpPanel = new JPanel();

        collectionPopUpPanel.setLayout(new BoxLayout(collectionPopUpPanel,BoxLayout.Y_AXIS));

        for (Collection c: collections) {

            collectionPopUpPanel.add(collectionButton(c));

        }

        collectionsPopUp.add(collectionPopUpPanel);

        collectionsPopUp.setSize(400, 200);
        collectionsPopUp.setVisible(true);


    }



    //EFFECTS: button UI element which represents a collection, clicking it shows all the questions in collection
    private JButton collectionButton(Collection c) {

        JButton collectionButton = new JButton(c.getCollectionName());

        collectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                questionsPopUp(c);
            }
        });


        return collectionButton;
    }

    //EFFECTS: pop-up window for a specific question
    private void questionsPopUp(Collection c) {

        JFrame questionsFrame = new JFrame();


        JPanel questionFramePanel = new JPanel();

        questionFramePanel.setLayout(new BoxLayout(questionFramePanel,BoxLayout.Y_AXIS));

        questionFramePanel.add(new JLabel(c.getCollectionName()));

        for (QuizQuestion q: c.getQuestions()) {

            questionFramePanel.add(new JLabel(q.getQuestion()));

        }

        questionsFrame.add(questionFramePanel);



        questionsFrame.setSize(400, 200);
        questionsFrame.setVisible(true);

    }

    //EFFECTS: pop up menu representing a question
    private void questionPopUp(QuizQuestion q) {

        JFrame questionFrame = new JFrame();


        JPanel questionFramePanel = new JPanel();

        questionFramePanel.setLayout(new BoxLayout(questionFramePanel,BoxLayout.Y_AXIS));

        questionFramePanel.add(new JLabel("Question: " + q.getQuestion()));

        for (String a: q.getAnswers()) {

            questionFramePanel.add(new JLabel(a));

        }

        questionFrame.add(questionFramePanel);



        questionFrame.setSize(400, 200);
        questionFrame.setVisible(true);



    }

    //EFFECTS: UI button and elements to go to menu in order to find a question
    private void findQuestionPopUpButton() {


        JButton findQuestionsPopUpButton = new JButton("Find Question");

        findQuestionsPopUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findQuestionsPopUp();
            }
        });

        mainMenu.add(findQuestionsPopUpButton);

    }

    //EFFECTS: constructs a pop up JFrame with a menu prompting user to find a question
    private void findQuestionsPopUp() {

        JFrame popup = new JFrame("Pop-up Window");

        JPanel questionFindPanel = new JPanel();
        JLabel questionFindInputLabel = new JLabel("Question: ");
        JTextField questionFindInputTextField = new JTextField(20);
        questionFindPanel.add(questionFindInputLabel);
        questionFindPanel.add(questionFindInputTextField);

        JButton questionFindPanelButton = new JButton("Search");

        questionFindPanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    QuizQuestion q = findQuestion(questionFindInputTextField.getText());
                    questionPopUp(q);

                } catch (QuestionNotFoundException qnf) {

                    errorPopUp("Could not find question");


                }
            }
        });

        questionFindPanel.add(questionFindPanelButton);
        popup.add(questionFindPanel);


        popup.setSize(400, 200);
        popup.setVisible(true);


    }
    //EFFECTS: returns a QuizQuestion if the question string exists, otherwise throws QuestionNotFoundException

    private QuizQuestion findQuestion(String question) throws QuestionNotFoundException {


        for (Collection c: collections) {

            List<QuizQuestion> questions = c.getQuestions();

            for (QuizQuestion q: questions) {

                if (q.getQuestion().equals(question)) {

                    return q;

                }

            }


        }

        throw new QuestionNotFoundException();

    }



    //EFFECTS: creates a popup with a given error text string displayed in the pop up

    private void errorPopUp(String errorText) {

        JFrame popup = new JFrame("Notification");


        JLabel popupLabel = new JLabel(errorText);
        popup.add(popupLabel);

        popup.setSize(400, 200);
        popup.setVisible(true);


    }



    //EFFECTS: returns all the names of the collections

    private List<String> getCollectionNames() {

        List<String> collectionNames = new ArrayList<String>();

        for (Collection c : collections) {

            collectionNames.add(c.getCollectionName());

        }

        return collectionNames;

    }

    //EFFECTS: UI and button component in order to display visual element of GUI

    private void visualQuestionButton() {


        {


            JButton visualQuestionButton = new JButton("Visual Component");

            visualQuestionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    visualPopUp();
                }
            });

            mainMenu.add(visualQuestionButton);


        }

    }

    //EFFECTS: creates a popup menu allowing user to see visual element of GUI

    private void visualPopUp() {

        JFrame visualFrame = new JFrame("cat");

        ImageIcon image = new ImageIcon("./data/visual.png");

        visualFrame.add(new JLabel(image));


        visualFrame.pack();

        visualFrame.setSize(400, 200);
        visualFrame.setVisible(true);


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

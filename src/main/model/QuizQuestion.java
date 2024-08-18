package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// represents a quiz question, with a question and multiple choice answers (up to 4)
public class QuizQuestion implements Writable {

    private String question;
    private List<String> answers;
    private int correctAnswer;

    //REQUIRES: question cannot be empty
    //EFFECTS: creates a question with undetermined answers
    public QuizQuestion(String question) {

        this.question = question;
        answers = new ArrayList<String>();
        correctAnswer = 0;
        EventLog.getInstance().logEvent(new Event("New Question Created: " + this.getQuestion()));

    }

    //REQUIRES: Answer is not an empty string, length of answers must not exceed 4
    //EFFECTS: adds a potential answer to the question (this doesn't mean the answer you add is the correct one
    //modifies: this
    public void addAnswer(String answer) {
        answers.add(answer);
        EventLog.getInstance().logEvent(new Event("New Answer added to " + this.getQuestion()
                + ": " + answer));
    }


    //REQUIRES: integer of answer must be within the range of answers (i.e. you can't say 4 is correct if there are 3)
    //EFFECTS: changes the correct answer to the question
    //MODIFIES: this
    public void changeCorrectAnswer(int newCorrectAnswer) {

        correctAnswer = newCorrectAnswer;

    }


    public String getQuestion() {

        return question;

    }

    public List<String> getAnswers() {

        return answers;

    }

    public int getCorrectAnswer() {

        return correctAnswer;

    }


    //EFFECTS: converts data in QuizQuestion to a JsonObject, returns that JsonObject

    @Override
    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();

        JSONArray answersArray = new JSONArray();

        for (String a : answers) {

            answersArray.put(a);

        }

        jsonObject.put("Answers",answersArray);
        jsonObject.put("questionName",question);
        jsonObject.put("CorrectAnswer",correctAnswer);

        return jsonObject;

    }
}

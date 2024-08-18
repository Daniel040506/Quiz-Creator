package model;

import org.json.JSONArray;
import persistence.*;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// represents a collection of questions
public class Collection implements Writable {


    private String collectionName;
    private List<QuizQuestion> questions;

    //REQUIRES: collection name is not empty
    //EFFECTS: generates a new collection with 0 questions inside
    public Collection(String collectionName) {

        this.collectionName = collectionName;
        questions = new ArrayList<QuizQuestion>();
        EventLog.getInstance().logEvent(new Event("New Collection added: " + this.getCollectionName()));

    }



    //REQUIRES: question string isn't empty, there is at least 1 answer and correct answer is in range of answers
    //EFFECTS: adds a new question to this collection, if the question is a repeat, it will not add the question,
    // otherwise, adds the question
    //MODIFIES: this
    public void addQuestion(String question, List<String> answers, int correctAnswer) {

        for (QuizQuestion q : questions) {

            if (q.getQuestion().equals(question)) {
                return;
            }
        }


        QuizQuestion newQuestion = new QuizQuestion(question);
        newQuestion.changeCorrectAnswer(correctAnswer);
        for (String a : answers) {

            newQuestion.addAnswer(a);

        }

        questions.add(newQuestion);
        EventLog.getInstance().logEvent(new Event("New Question added to " + this.getCollectionName()));
    }

    //REQUIRES: index of question to remove is within bounds
    //EFFECTS: removes question from the collection
    //MODIFIES: this

    public void removeQuestion(int index) {

        questions.remove(index);
        EventLog.getInstance().logEvent(new Event("Question removed from " + this.getCollectionName()));


    }

    public String getCollectionName() {

        return collectionName;

    }


    public List<QuizQuestion> getQuestions() {

        return questions;

    }


    //REQUIRES: score must be > 0
    //EFFECTS: generates a result given a score, and how many questions are in the collection in total
    //MODIFIES: this

    public String generateResult(int score) {

        Integer numerator = score;
        Integer denominator = this.getQuestions().size();
        Double percentage = ((double) numerator / (double) denominator) * 100;

        String result = ("Collection practiced: " + this.getCollectionName()
                +
                "\nScore: " + numerator.toString() + "/" + denominator.toString()
                +
                "\nPercentage: " + percentage.toString() + "%");


        return result;


    }


    //EFFECTS: converts data in Collection to a JsonObject, returns that JsonObject

    @Override
    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        JSONArray questionsArray = new JSONArray();

        for (QuizQuestion q: questions) {

            questionsArray.put(q.toJson());


        }

        jsonObject.put("collectionName",collectionName);
        jsonObject.put("Questions",questionsArray);


        return jsonObject;
    }
}

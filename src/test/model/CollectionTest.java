package model;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

// tests for Collection model class, testing setup, adding questions, removing questions
public class CollectionTest {

    private Collection c1;

    @BeforeEach
    void setup() {

        c1 = new Collection("Math");

    }

    @Test
    void constructorTest() {

        assertTrue(c1.getCollectionName().equals("Math"));
        assertEquals(0,c1.getQuestions().size());


    }

    @Test
    void addQuestionTest() {

        assertEquals(0,c1.getQuestions().size());

        List<String> answers = new ArrayList<>();

        answers.add("4");
        answers.add("900");
        answers.add("50");
        answers.add("2");


        c1.addQuestion("What is 2 + 2",answers,1);

        assertEquals(1,c1.getQuestions().size());
        assertEquals(4,c1.getQuestions().get(0).getAnswers().size());
        assertTrue(c1.getQuestions().get(0).getAnswers().get(0).equals("4"));
        assertTrue(c1.getQuestions().get(0).getAnswers().get(1).equals("900"));
        assertTrue(c1.getQuestions().get(0).getAnswers().get(2).equals("50"));
        assertTrue(c1.getQuestions().get(0).getAnswers().get(3).equals("2"));


        c1.addQuestion("What is 2 + 2",answers,1);

        assertEquals(1,c1.getQuestions().size());
        assertEquals(4,c1.getQuestions().get(0).getAnswers().size());




        c1.addQuestion("What is 1 + 1",answers,4);

        assertEquals(2,c1.getQuestions().size());


    }

    @Test
    void generateResultTest() {

        List<String> answers = new ArrayList<>();

        answers.add("4");
        answers.add("900");
        answers.add("50");
        answers.add("2");


        c1.addQuestion("What is 2 + 2",answers,1);

        String result = ("Collection practiced: " + "Math"
                +
                "\nScore: " + "1" + "/" + "1"
                +
                "\nPercentage: " + 100.0 + "%");


        assertTrue(c1.generateResult(1).equals(result));






    }


    @Test
    void removeQuestionTest() {


        assertEquals(0,c1.getQuestions().size());

        List<String> answers = new ArrayList<>();

        answers.add("4");
        answers.add("900");
        answers.add("50");
        answers.add("2");


        c1.addQuestion("What is 2 + 2",answers,1);

        assertEquals(1,c1.getQuestions().size());

        c1.removeQuestion(0);

        assertEquals(0,c1.getQuestions().size());



    }




}

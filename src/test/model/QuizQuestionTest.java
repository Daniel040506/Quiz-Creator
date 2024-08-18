package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//tests for quiz question, testing answer validity, adding answers
class QuizQuestionTest {

    private QuizQuestion q1;
    private QuizQuestion q2;

    @BeforeEach
    void setup() {

        q1 = new QuizQuestion("What is 2 + 2?");
        q2 = new QuizQuestion("What Continent does Canada reside in?");


    }

    @Test
    void constructorTest() {

        assertTrue(q1.getQuestion().equals("What is 2 + 2?"));
        assertTrue(q2.getQuestion().equals("What Continent does Canada reside in?"));

        assertTrue(q1.getAnswers().isEmpty());
        assertTrue(q2.getAnswers().isEmpty());

    }

    @Test
    void addAnswerTest() {

        assertTrue(q1.getAnswers().isEmpty());

        q1.addAnswer("4");

        assertTrue(q1.getAnswers().contains("4"));
        assertEquals(1,q1.getAnswers().size());

        assertTrue(q2.getAnswers().isEmpty());

        q2.addAnswer("North America");
        q2.addAnswer("South America");

        assertTrue(q2.getAnswers().contains("North America"));
        assertTrue(q2.getAnswers().contains("South America"));
        assertEquals(2,q2.getAnswers().size());


    }


    @Test
    void changeCorrectAnswerTest() {

        assertEquals(0,q1.getCorrectAnswer());

        q1.changeCorrectAnswer(1);

        assertEquals(1,q1.getCorrectAnswer());



    }

    @Test
    void getCorrectAnswerTest() {

        assertEquals(0,q1.getCorrectAnswer());

        q1.changeCorrectAnswer(4);

        assertEquals(4,q1.getCorrectAnswer());


    }

}
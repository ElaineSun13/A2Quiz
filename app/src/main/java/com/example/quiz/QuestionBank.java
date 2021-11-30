package com.example.quiz;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class QuestionBank {
    ArrayList<Question> questions;
    ArrayList<Integer> colors;
    boolean[] answers = {false, true, false, true, true};

    public  QuestionBank() {
        questions = new ArrayList<>();
        questions.add(new Question(R.string.question1, answers[0]));
        questions.add(new Question(R.string.question2, answers[1]));
        questions.add(new Question(R.string.question3, answers[2]));
        questions.add(new Question(R.string.question4, answers[3]));
        questions.add(new Question(R.string.question5, answers[4]));

        colors = new ArrayList<Integer>();
        colors.add(R.color.black);
        colors.add(R.color.purple_200);
        colors.add(Color.MAGENTA);
        colors.add(Color.RED);
        colors.add(Color.BLACK);

        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(questions);
        Collections.shuffle(colors);
    }
}

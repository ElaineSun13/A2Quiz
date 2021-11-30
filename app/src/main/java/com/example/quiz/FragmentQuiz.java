package com.example.quiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentQuiz#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentQuiz extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_QUESTION = "question";
    private static final String ARG_COLOR = "color";

    // TODO: Rename and change types of parameters

    private String question;
    private int color;

//    private Boolean answer;

    public FragmentQuiz() {
//        this.quiz = quiz;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param question String.
     * @return A new instance of fragment FragmentQuiz.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentQuiz newInstance(String question, int color) {
        FragmentQuiz fragment = new FragmentQuiz();
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION, question);
        args.putInt(ARG_COLOR, color);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = getArguments().getString(ARG_QUESTION);
            color = getArguments().getInt(ARG_COLOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_quiz, container, false);
        TextView content = v.findViewById(R.id.quiz_content);
        content.setText(question);
        content.setBackgroundColor(color);
        return v;

    }
}
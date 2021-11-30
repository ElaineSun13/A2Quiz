package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FragmentDialog.DialogClickListener{
    FragmentManager fm = getSupportFragmentManager();

    QuestionBank questionBank;
    Question currentQuestion;
    int questionID;
    int correctAnswer;
    int totalCorrectAnswer;
    int attempt;
    int totalQuestions;

    AlertDialog.Builder resultBuilder;
    AlertDialog.Builder summaryBuilder;
    ProgressBar progressBar;
    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.quizProgressBar);
        progressBar.setMax(100);

        sharedPref = getPreferences(Context.MODE_PRIVATE);

        questionBank = ((myApp) getApplication()).questionBank;
        resultBuilder = new AlertDialog.Builder(this);

        //Setting message manually and performing action on button click
        resultBuilder.setTitle(R.string.result_dialog_title)
                .setCancelable(false)
                .setPositiveButton(R.string.btn_save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        attempt++;
                        totalCorrectAnswer += correctAnswer;
                        saveResult();
                        restart();
                    }
                })
                .setNegativeButton(R.string.btn_ignore, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        restart();
                    }
                });

        summaryBuilder = new AlertDialog.Builder(this);
        summaryBuilder.setCancelable(false)
                .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        loadResult();
        showQestion();

    }

    @Override
    protected void onPause() {
        super.onPause();
        saveResult();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.quiz_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.menu_get_average:{
                showAverage();
                break;
            }
            case R.id.menu_select_question:{
                FragmentDialog msg_fragment = FragmentDialog.newInstance(questionBank.questions.size(),totalQuestions);
                msg_fragment.listener = this;
                //  FragmentDialog msg_fragment = new FragmentDialog();
                msg_fragment.show(fm.beginTransaction(),"1");
                break;
            }
            case R.id.menu_reset_result:{
                cleanResult();
                break;
            }
        }
        return true;
    }

    private void showQestion() {

        progressBar.setProgress(questionID * 100 / totalQuestions);

        currentQuestion = questionBank.questions.get(questionID);

        String content = getResources().getString(currentQuestion.contentID);

        FragmentQuiz fragment = FragmentQuiz.newInstance(content, questionBank.colors.get(questionID));
        fm.beginTransaction().replace(R.id.container1, fragment).commit();

    }

    private void saveResult() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("totalCorrectAnswer", totalCorrectAnswer);
        editor.putInt("questionID", questionID);
        editor.putInt("correctAnswer", correctAnswer);
        editor.putInt("attempt", attempt);
        editor.apply();
    }

    public void cleanResult() {
        totalCorrectAnswer = 0;
        attempt = 0;
        saveResult();

    }

    private void loadResult() {
        totalCorrectAnswer = sharedPref.getInt("totalCorrectAnswer", 0);
        correctAnswer = sharedPref.getInt("correctAnswer", 0);
        questionID = sharedPref.getInt("questionID", 0);
        attempt = sharedPref.getInt("attempt", 0);
        totalQuestions = sharedPref.getInt("totalQuestions", questionBank.questions.size());


        if(attempt <0) {
            attempt = 0;
        }

    }

    private void checkAnswer(boolean answer) {
        String message = "";
        if (currentQuestion.answer == answer) {
            correctAnswer++;
            message = getString(R.string.answer_correct_message);
        } else {
            message = getString(R.string.answer_incorrect_message);
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void restart() {
        questionBank.shuffle();
        questionID = 0;
        correctAnswer = 0;
        showQestion();
    }


    private void showResult() {

        progressBar.setProgress(100);

        String message = String.format(getString(R.string.result_dialog_message), correctAnswer, totalQuestions);
        //Setting message manually and performing action on button click
        resultBuilder.setMessage(message);

        AlertDialog alert = resultBuilder.create();

        alert.show();
    }


    private void showAverage() {

        int averageCorrectAnswer = 0;
        if(attempt>0) {
            averageCorrectAnswer = totalCorrectAnswer/attempt;
        }

        String message = String.format(getString(R.string.average_dialog_message), averageCorrectAnswer, attempt);

        //Setting message manually and performing action on button click
        summaryBuilder.setMessage(message);

        //Creating dialog box
        AlertDialog alert = summaryBuilder.create();
        alert.show();
    }

    public void true_clicked(View view) {
        checkAnswer(true);

        if (questionID < totalQuestions - 1) {
            questionID++;
            showQestion();
        } else {
            showResult();
        }

    }

    public void false_clicked(View view) {
        checkAnswer(false);
        if (questionID < totalQuestions - 1) {
            questionID++;
            showQestion();
        } else {
            showResult();
        }
    }

    @Override
    public void dialogSetTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
        restart();
        String message = String.format(getString(R.string.dialog_message), totalQuestions);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
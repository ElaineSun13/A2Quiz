package com.example.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;


public class FragmentDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {

    public interface DialogClickListener{
         void dialogSetTotalQuestions(int totalQuestions);
    }

    public DialogClickListener listener;

    private static final String ARG_PARAM1 = "maxQuestions";
    private static final String ARG_PARAM2 = "currentQuestions";

    // TODO: Rename and change types of parameters
    private  int maxQuestions;
    private  int currentQuestions;

    public FragmentDialog() {
        // Required empty public constructor
    }

    public static FragmentDialog newInstance(int maxQuestions, int currentQuestions) {
        FragmentDialog fragment = new FragmentDialog();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, maxQuestions);
        args.putInt(ARG_PARAM2, currentQuestions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            maxQuestions = getArguments().getInt(ARG_PARAM1);
            currentQuestions = getArguments().getInt(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);
        Spinner spinner = (Spinner) v.findViewById(R.id.set_num_quiz_spinner);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        for(int i=1; i<= maxQuestions; i++) {
            categories.add(""+i);
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(currentQuestions-1);

        spinner.setOnItemSelectedListener(this);


        Button yes = v.findViewById(R.id.yesid);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.dialogSetTotalQuestions(currentQuestions);
                dismiss();
            }
        });
        Button no = v.findViewById(R.id.noid);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
//        String item = parent.getItemAtPosition(position).toString();
        currentQuestions = position+1;

    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
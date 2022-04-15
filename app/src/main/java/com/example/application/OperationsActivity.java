package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class OperationsActivity extends AppCompatActivity {


    FrameLayout frame;
    TextView Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);

        frame = (FrameLayout)findViewById(R.id.frame1);
        Text = (TextView)findViewById(R.id.textView8);

        frame.setVisibility(ImageView.INVISIBLE);


        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));
        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("main");

        PyObject obj = pyobj.callAttr("get_sum");
        String s = obj.toString();
        Text.setText(s);
    }

    public void open(View view) {
        frame.setVisibility(ImageView.VISIBLE);
    }

    public void close(View view) {
        frame.setVisibility(ImageView.INVISIBLE);
    }

    public void AddIncomes(View view) {
        Intent intent = new Intent(this, AddIncomes.class);
        startActivity(intent);
    }

    public void AddExpense(View view) {
        Intent intent = new Intent(this, AddExpenses.class);
        startActivity(intent);
    }

    public void Scores(View view) {
        Intent intent = new Intent(this, Scores.class);
        startActivity(intent);
    }

    public void Analitics(View view) {
        Intent intent = new Intent(this, Analytics.class);
        startActivity(intent);
    }

    public void More(View view) {
        Intent intent = new Intent(this, More.class);
        startActivity(intent);
    }
}
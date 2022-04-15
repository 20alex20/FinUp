package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import android.widget.ListView;

import com.chaquo.python.PyObject;

import java.util.ArrayList;
import java.util.Arrays;

public class OperationsActivity extends AppCompatActivity {

    ListView listd;

    private String[] name_categories;
    private int[] id_categories;

    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> catNamesList;

    FrameLayout frame;
    TextView Text, Text1, Text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);

        listd = (ListView) findViewById(R.id.list);
        frame = (FrameLayout)findViewById(R.id.frame1);
        Text = (TextView)findViewById(R.id.textView8);
        Text1 = (TextView)findViewById(R.id.textView11);
        Text2 = (TextView)findViewById(R.id.textView13);

        frame.setVisibility(ImageView.INVISIBLE);


        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));
        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("main");

        PyObject obj = pyobj.callAttr("get_sum");
        String s = obj.toString();
        Text.setText(s);

        PyObject obj1 = pyobj.callAttr("get_sum_deposits");
        String s1 = obj1.toString();
        Text1.setText(s1);

        PyObject obj2 = pyobj.callAttr("get_sum_purchases");
        String s2 = obj2.toString();
        Text2.setText(s2);
        //draw(pyobj);
    }

    /*public void draw(PyObject pyobj) {
        int cut = 3;
        PyObject list2 = pyobj.callAttr("get_bank_accounts");
        PyObject obj2 = pyobj.callAttr("to_line_list", list2, cut);
        String[] arr2 = obj2.toJava(String[].class);
        id_bank_accounts = new int[arr2.length / cut];
        name_bank_accounts = new String[arr2.length / cut];
        for (int i = 0; i < arr2.length; i += cut) {
            id_bank_accounts[i / cut] = Integer.parseInt(arr2[i]);
            name_bank_accounts[i / cut] = arr2[i + 1] + " (" + arr2[i + 2] + "₽)";
        }

        accsNamesList = new ArrayList<>(Arrays.asList(name_bank_accounts));
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, accsNamesList);
        listd.setAdapter(mAdapter);
        //Toast.makeText(getApplicationContext(),catNamesList + "",Toast.LENGTH_LONG).show();
    }*/


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
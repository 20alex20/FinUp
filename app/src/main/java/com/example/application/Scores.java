package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.ListActivity;
import android.widget.AdapterView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.ArrayList;
import java.util.Arrays;

public class Scores extends AppCompatActivity {

    FrameLayout frame;
    EditText Et1;
    ImageButton Btn;
    ListView listd;

    private String[] name_bank_accounts;
    private int[] id_bank_accounts;

    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> accsNamesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        frame = (FrameLayout)findViewById(R.id.frame1);
        Et1 = (EditText)findViewById(R.id.count_name);
        Btn = (ImageButton)findViewById(R.id.add_cat);
        listd = (ListView) findViewById(R.id.list);


        frame.setVisibility(ImageView.INVISIBLE);

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("main");

        draw(pyobj);

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("add_bank_account", Et1.getText().toString(), 0);
                frame.setVisibility(ImageView.INVISIBLE);
                Toast.makeText(getApplicationContext(),"Новая карта добавлена",Toast.LENGTH_LONG).show();
                draw(pyobj);
            }

        });
    }

    public void draw(PyObject pyobj) {
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
    }

    public void open(View view) {
        frame.setVisibility(ImageView.VISIBLE);
    }

    public void close(View view) {
        frame.setVisibility(ImageView.INVISIBLE);
    }

    public void bank(View view) {
        Toast.makeText(getApplicationContext(),"Автоматическая интеграция с мобильными приложениями банков доступна только в платной весрии",Toast.LENGTH_LONG).show();
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

    public void Operations(View view) {
        Intent intent = new Intent(this, OperationsActivity.class);
        startActivity(intent);
    }
}
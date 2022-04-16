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
import android.widget.EditText;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import android.widget.ListView;
import android.widget.Toast;

import com.chaquo.python.PyObject;

import java.util.ArrayList;
import java.util.Arrays;

public class OperationsActivity extends AppCompatActivity {

    ListView listd;

    private String[] name_categories;
    private int[] id_categories;

    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> dataList, namesList;
    private ArrayList<String> catNamesList, opNamesList, accNamesList, valueList, smlist;

    private String[] name_operations, data;
    private String[] id_operations, id_categ, id_acc, value, sm;



    FrameLayout frame;
    TextView Text, Text1, Text2, t1, t2, t3;
    EditText Et1;
    ImageButton Bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);

        listd = (ListView) findViewById(R.id.listhis);
        frame = (FrameLayout)findViewById(R.id.frame1);
        Text = (TextView)findViewById(R.id.textView8);
        t1 = (TextView)findViewById(R.id.textView37);
        t2 = (TextView)findViewById(R.id.textView38);
        t3 = (TextView)findViewById(R.id.textView39);
        Text1 = (TextView)findViewById(R.id.textView11);
        Text2 = (TextView)findViewById(R.id.textView13);
        Et1 = (EditText)findViewById(R.id.prcnt);
        Bt1 = (ImageButton)findViewById(R.id.imageButton26);

        frame.setVisibility(ImageView.INVISIBLE);

        //ListView listView = (ListView) findViewById(R.id.listView);

        //ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        //HashMap<String, String> map;


        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));
        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("main");

        PyObject obj = pyobj.callAttr("get_sum");
        String s = obj.toString();
        Text.setText(s);

        Bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open(view);
                PyObject money3 = pyobj.callAttr("calc_inf", s, Et1.getText().toString(), 3);
                PyObject money6 = pyobj.callAttr("calc_inf", s, Et1.getText().toString(), 6);
                PyObject money12 = pyobj.callAttr("calc_inf", s, Et1.getText().toString(), 12);

                t1.setText(money3.toString());
                t2.setText(money6.toString());
                t3.setText(money12.toString());
            }
        });

        PyObject obj1 = pyobj.callAttr("get_sum_deposits");
        String s1 = obj1.toString();
        Text1.setText(s1);

        PyObject obj2 = pyobj.callAttr("get_sum_purchases");
        String s2 = obj2.toString();
        Text2.setText(s2);

        draw(pyobj);
    }

    public void draw(PyObject pyobj) {
        int cut = 6;
        PyObject list2 = pyobj.callAttr("get_purchase");
        PyObject obj2 = pyobj.callAttr("to_line_list", list2, cut);
        String[] arr2 = obj2.toJava(String[].class);
        id_operations = new String[arr2.length / cut];
        id_categ = new String[arr2.length / cut];
        id_acc = new String[arr2.length / cut];
        value = new String[arr2.length / cut];
        data = new String[arr2.length / cut];
        sm = new String[arr2.length / cut];
        for (int i = 0; i < arr2.length; i += cut) {
            //id_operations[i / cut] = Integer.parseInt(arr2[i]);
            //id_categ[i / cut] = Integer.parseInt(arr2[i+1]);
            //id_acc[i / cut] = Integer.parseInt(arr2[i+2]);
            //value[i / cut] = Integer.parseInt(arr2[i+3]);
            id_operations[i / cut] = arr2[i];
            id_categ[i / cut] = arr2[i+1];
            id_acc[i / cut] = arr2[i+2];
            value[i / cut] = arr2[i+3];
            data[i / cut] = arr2[i+4];
            PyObject category_name = pyobj.callAttr("get_category_name", id_categ[i / cut]);
            PyObject bank_acc_name = pyobj.callAttr("get_bank_acc_name", id_acc[i / cut]);
            //sm[i / cut] = id_categ[i / cut] +" "+id_acc[i / cut]+" "+ value[i / cut]+"\n" +data[i / cut];
            sm[i / cut] = "Категория: " + category_name + "\n" + "Счет списания: " + bank_acc_name + "\n"
                    + "Сумма: " + value[i / cut] + "₽" + "\n" + "Дата: " + data[i / cut];
        }

        opNamesList = new ArrayList<>(Arrays.asList(id_operations));
        catNamesList = new ArrayList<>(Arrays.asList(id_categ));
        accNamesList = new ArrayList<>(Arrays.asList(id_acc));
        valueList = new ArrayList<>(Arrays.asList(value));
        dataList = new ArrayList<>(Arrays.asList(data));
        smlist = new ArrayList<>(Arrays.asList(sm));
        Toast.makeText(getApplicationContext(),dataList + "",Toast.LENGTH_LONG).show();
        //+" "+catNamesList+" "+accNamesList+" "+valueList+" "+dataList+" "+namesList
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, smlist);
        listd.setAdapter(mAdapter);
       // Toast.makeText(getApplicationContext(),list2 + "",Toast.LENGTH_LONG).show();
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
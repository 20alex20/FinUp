package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Array;

public class AddExpenses extends AppCompatActivity {

    CalendarView calendar;
    EditText Et1, Et2, Et3;
    ImageView cls;
    ImageButton Btn;
    FrameLayout frame;

    private String[] strings;
    private int[] ints;
    private String[] bnkaccs = {"card1", "card2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);

        Et1 = (EditText)findViewById(R.id.editTextBillComm);
        Et2 = (EditText)findViewById(R.id.editSumm);
        Et3 = (EditText)findViewById(R.id.editTextBillName3);
        cls = (ImageView)findViewById(R.id.imageView16);
        frame = (FrameLayout)findViewById(R.id.frame1);
        Btn = (ImageButton)findViewById(R.id.button);
        calendar = (CalendarView)findViewById(R.id.calendarView);

        frame.setVisibility(ImageView.INVISIBLE);

        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("main");

        PyObject obj = pyobj.callAttr("get_categories");
        String[] id_name = obj.toJava(String[].class);
        ints = new int[id_name.length / 2];
        strings = new String[id_name.length / 2];
        for (int i = 0; i < id_name.length; i += 2) {
            ints[i / 2] = Integer.parseInt(id_name[i]);
            strings[i / 2] = id_name[i + 1];
        }
        Toast.makeText(getApplicationContext(),id_name.length + "",Toast.LENGTH_LONG).show();

        ArrayAdapter<String> categorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strings);
        categorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> accountsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bnkaccs);
        accountsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(categorAdapter);
        spinner2.setAdapter(accountsAdapter);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month + 1;
                int mDay = dayOfMonth;
                String selectedDate = new StringBuilder().append(mDay)
                        .append("/").append(mMonth).append("/").append(mYear).toString();
                Et3.setText(selectedDate);
            }
        });

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("add_deposit", Et1.getText().toString(), Et3.getText().toString(), Et2.getText().toString());
                String s = obj.toString();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                goBack(view);
                // tv.setText(obj.toString());
            }
        });
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, OperationsActivity.class);
        startActivity(intent);
    }

    public void open(View view) {
        frame.setVisibility(ImageView.VISIBLE);
    }

    public void close(View view) {
        frame.setVisibility(ImageView.INVISIBLE);
    }

}
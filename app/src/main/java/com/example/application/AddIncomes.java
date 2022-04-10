package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class AddIncomes extends AppCompatActivity {

    CalendarView calendar;
    EditText Et1, Et2, Et3;
    ImageView cls;
    ImageButton Btn;
    FrameLayout frame;

    private String[] name_categories, name_bank_accounts;
    private int[] id_categories, id_bank_accounts;
    int i1 = 0, i2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_incomes);

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
                i1 = i;
                // Toast.makeText(getApplicationContext(),i1 + "",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                i2 = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("main");

        int cut = 2;

        PyObject list = pyobj.callAttr("get_deposit_categories");
        PyObject obj = pyobj.callAttr("to_line_list", list, cut);
        String[] arr = obj.toJava(String[].class);
        id_categories = new int[arr.length / cut];
        name_categories = new String[arr.length / cut];
        for (int i = 0; i < arr.length; i += cut) {
            id_categories[i / cut] = Integer.parseInt(arr[i]);
            name_categories[i / cut] = arr[i + 1];
        }

        ArrayAdapter<String> categorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, name_categories);
        categorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(categorAdapter);

        cut = 3;
        PyObject list2 = pyobj.callAttr("get_bank_accounts");
        PyObject obj2 = pyobj.callAttr("to_line_list", list2, cut);
        String[] arr2 = obj2.toJava(String[].class);
        id_bank_accounts = new int[arr2.length / cut];
        name_bank_accounts = new String[arr2.length / cut];
        for (int i = 0; i < arr2.length; i += cut) {
            id_bank_accounts[i / cut] = Integer.parseInt(arr2[i]);
            name_bank_accounts[i / cut] = arr2[i + 1] + " (" + arr2[i + 2] + "₽)";
        }

        ArrayAdapter<String> accountsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, name_bank_accounts);
        accountsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(accountsAdapter);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month + 1;
                int mDay = dayOfMonth;
                String selectedDate = new StringBuilder().append(mDay)
                        .append(".").append(mMonth).append(".").append(mYear).toString();
                Et3.setText(selectedDate);
            }
        });

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("add_deposit", id_categories[i1], id_bank_accounts[i2], Et2.getText().toString(), Et3.getText().toString(), Et1.getText().toString());
                String s = obj.toString();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                if (s.equals("Данные изменены")) {
                    goBack(view);
                }
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
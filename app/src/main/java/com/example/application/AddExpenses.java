package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.material.textfield.TextInputLayout;

public class AddExpenses extends AppCompatActivity {

    CalendarView calendar;
    EditText Et1, Et2, Et3;
    Button Btn;
    ImageView cls;
    TextInputLayout list1, list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);

        Et1 = (EditText)findViewById(R.id.editTextBillName);
        Et2 = (EditText)findViewById(R.id.editTextBillName2);
        Et3 = (EditText)findViewById(R.id.editTextBillName3);
        cls = (ImageView)findViewById(R.id.imageView16);
        Btn = (Button)findViewById(R.id.button);
        calendar = (CalendarView)findViewById(R.id.calendarView);
        list1 = (TextInputLayout)findViewById(R.id.editTextBillCateg);
        list2 = (TextInputLayout)findViewById(R.id.editTextBillCateg2);


        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("main");

        PyObject obj = pyobj.callAttr("get_categories");
        String[][] array = obj.toJava(String[][].class);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month + 1;
                int mDay = dayOfMonth;
                String selectedDate = new StringBuilder().append(mMonth)
                        .append(".").append(mDay).append(".").append(mYear).toString();
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
        cls.setVisibility(ImageView.VISIBLE);
        calendar.setVisibility(CalendarView.VISIBLE);
    }

    public void close(View view) {
        cls.setVisibility(ImageView.INVISIBLE);
        calendar.setVisibility(CalendarView.INVISIBLE);
    }
}
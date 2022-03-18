package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class AddExpenses extends AppCompatActivity {

    CalendarView calendar;
    EditText Et1, Et2, Et3;
    Button Btn;
    int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);


        // Et1 = (EditText)findViewById(R.id.email_reg);
        // Et2 = (EditText)findViewById(R.id.username);
        // Et3 = (EditText)findViewById(R.id.PASSword);
        Btn = (Button)findViewById(R.id.button);
        calendar = findViewById(R.id.calendarView);

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("main");

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month + 1;
                mDay = dayOfMonth;
                String selectedDate = new StringBuilder().append(mMonth + 1)
                        .append("-").append(mDay).append("-").append(mYear)
                        .append(" ").toString();
                Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_LONG).show();
            }
        });

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("register", Et1.getText().toString(), Et3.getText().toString(), Et2.getText().toString());
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

    public void goBack(View view) {
        Intent intent = new Intent(this, OperationsActivity.class);
        startActivity(intent);
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, OperationsActivity.class);
        startActivity(intent);
    }
}
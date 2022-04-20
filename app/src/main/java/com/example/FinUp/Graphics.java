package com.example.FinUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.application.R;

import android.graphics.BitmapFactory;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Graphics extends AppCompatActivity {

    EditText Et1, Et2;
    ImageButton Btn1, Btn2, Btn3, Btn4;
    ImageView iv;

    CalendarView calendar, calendar2;
    FrameLayout frame, frame2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);

        Et1 = (EditText)findViewById(R.id.editTextfrom);
        Et2 = (EditText)findViewById(R.id.editTextTo);
        Btn1 = (ImageButton)findViewById(R.id.imageButton32);
        Btn2 = (ImageButton)findViewById(R.id.imageButton30);
        Btn3 = (ImageButton)findViewById(R.id.imageButton31);
        Btn4 = (ImageButton)findViewById(R.id.imageButton27);
        iv = (ImageView)findViewById(R.id.imageView22);
        calendar = (CalendarView)findViewById(R.id.calendarView);
        calendar2 = (CalendarView)findViewById(R.id.calendarView2);
        frame = (FrameLayout)findViewById(R.id.frame1);
        frame2 = (FrameLayout)findViewById(R.id.frame2);


        iv.setVisibility(View.INVISIBLE);
        frame.setVisibility(ImageView.INVISIBLE);
        frame2.setVisibility(ImageView.INVISIBLE);

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("grafs");


        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month + 1;
                int mDay = dayOfMonth;
                String selectedDate = new StringBuilder().append(mDay)
                        .append(".").append(mMonth).append(".").append(mYear).toString();
                Et1.setText(selectedDate);
            }
        });

        calendar2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month + 1;
                int mDay = dayOfMonth;
                String selectedDate = new StringBuilder().append(mDay)
                        .append(".").append(mMonth).append(".").append(mYear).toString();
                Et2.setText(selectedDate);
            }
        });


        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("graph", true, "plot", Et1.getText().toString(), Et2.getText().toString());
                byte[] ba = obj.toJava(byte[].class);
                iv.setVisibility(View.VISIBLE);
                iv.setImageBitmap(BitmapFactory.decodeByteArray(ba,0, ba.length));
            }
        });
        Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("graph", true, "pie", Et1.getText().toString(), Et2.getText().toString());
                //String s = obj.toString();
                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                byte[] ba = obj.toJava(byte[].class);
                iv.setVisibility(View.VISIBLE);
                iv.setImageBitmap(BitmapFactory.decodeByteArray(ba,0, ba.length));
            }
        });
        Btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("graph", true, "bar", Et1.getText().toString(), Et2.getText().toString());
                byte[] ba = obj.toJava(byte[].class);
                iv.setVisibility(View.VISIBLE);
                iv.setImageBitmap(BitmapFactory.decodeByteArray(ba,0, ba.length));
            }
        });
        Btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("graph", true, "bar2", Et1.getText().toString(), Et2.getText().toString());
                byte[] ba = obj.toJava(byte[].class);
                iv.setVisibility(View.VISIBLE);
                iv.setImageBitmap(BitmapFactory.decodeByteArray(ba,0, ba.length));
            }
        });
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, Analytics.class);
        startActivity(intent);
    }

    public void open(View view) {
        frame.setVisibility(ImageView.VISIBLE);
    }

    public void close(View view) {
        frame.setVisibility(ImageView.INVISIBLE);
    }

    public void open2(View view) {
        frame2.setVisibility(ImageView.VISIBLE);
    }

    public void close2(View view) {
        frame2.setVisibility(ImageView.INVISIBLE);
    }
}
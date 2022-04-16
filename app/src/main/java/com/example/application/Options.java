package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import android.widget.Button;

public class Options extends AppCompatActivity {

    Button Btn1, Btn2, Btn;
    FrameLayout frame1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        frame1 = (FrameLayout) findViewById(R.id.frame1);

        frame1.setVisibility(ImageView.INVISIBLE);
        Btn1 = (Button)findViewById(R.id.button4);
        Btn2 = (Button)findViewById(R.id.button6);
        Btn = (Button)findViewById(R.id.button12);

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("csv_xlsx");
        final PyObject pyobj1 = py.getModule("main");

        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("export_csv");
                String s = obj.toString();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            }
        });

        Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("export_xlsx");
                String s = obj.toString();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            }
        });

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj1.callAttr("logout");
                String s = obj.toString();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                Loggout();
            }
        });
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, More.class);
        startActivity(intent);
    }

    public void Login(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void open(View view) {
        frame1.setVisibility(ImageView.VISIBLE);
    }
    public void close(View view) {
        frame1.setVisibility(ImageView.INVISIBLE);
    }

    public void Loggout() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class Options extends AppCompatActivity {

    ImageButton Bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Bt1 = (ImageButton)findViewById(R.id.export_bt);

        //if(!Python.isStarted())
        //    Python.start(new AndroidPlatform(this));

        //Python py = Python.getInstance();
        //final PyObject pyobj = py.getModule("csv_xlsx");


    }

    public void goBack(View view) {
        Intent intent = new Intent(this, More.class);
        startActivity(intent);
    }

    public void Login(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
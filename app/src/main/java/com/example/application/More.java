package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.ArrayList;
import java.util.Arrays;

public class More extends AppCompatActivity {

    TextView name, email;
    ImageButton Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        name = (TextView)findViewById(R.id.name);
        email = (TextView)findViewById(R.id.email);
        Btn = (ImageButton) findViewById(R.id.imageButton9);

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));
        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("main");

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("logout");
                String s = obj.toString();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                Login(null);
            }
        });

        draw(pyobj);
    }

    public void draw(PyObject pyobj) {
        PyObject obj = pyobj.callAttr("get_full_name");
        String NAME = obj.toString();
        name.setText(NAME);
        PyObject obj2 = pyobj.callAttr("get_username_email");
        String EMAIL = obj2.toString();
        email.setText(EMAIL);
    }

    public void Scores(View view) {
        Intent intent = new Intent(this, Scores.class);
        startActivity(intent);
    }

    public void Analitics(View view) {
        Intent intent = new Intent(this, Analytics.class);
        startActivity(intent);
    }

    public void Operations(View view) {
        Intent intent = new Intent(this, OperationsActivity.class);
        startActivity(intent);
    }

    public void Options(View view) {
        Intent intent = new Intent(this, Options.class);
        startActivity(intent);
    }

    public void AddCategory(View view) {
        Intent intent = new Intent(this, Category.class);
        startActivity(intent);
    }

    public void Login(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
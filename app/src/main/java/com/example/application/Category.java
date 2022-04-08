package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class Category extends AppCompatActivity {

    FrameLayout frame;
    EditText Et1;
    ImageButton Btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        frame = (FrameLayout)findViewById(R.id.frame1);
        Et1 = (EditText)findViewById(R.id.cat_name);
        Btn = (ImageButton)findViewById(R.id.add_cat);

        frame.setVisibility(ImageView.INVISIBLE);

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("main");

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("add_category", Et1.getText().toString());
                frame.setVisibility(ImageView.INVISIBLE);
                Toast.makeText(getApplicationContext(),"SAVED",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void open(View view) {
        frame.setVisibility(ImageView.VISIBLE);
    }

    public void close(View view) {
        frame.setVisibility(ImageView.INVISIBLE);
    }

    public void OnBack(View view) {
        Intent intent = new Intent(this, More.class);
        startActivity(intent);
    }


}

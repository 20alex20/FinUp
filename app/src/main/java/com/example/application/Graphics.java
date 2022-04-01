package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import android.graphics.BitmapFactory;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.Toast;

public class Graphics extends AppCompatActivity {

    EditText Et1, Et2;
    ImageButton Btn1, Btn2, Btn3, Btn4;
    ImageView iv;

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

        iv.setVisibility(View.INVISIBLE);

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("grafs");

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
                PyObject obj = pyobj.callAttr("graph", Et1.getText().toString(), Et2.getText().toString(), Et1.getText().toString(), Et2.getText().toString());
                String s = obj.toString();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                //byte[] ba = obj.toJava(byte[].class);
                //iv.setVisibility(View.VISIBLE);
                //iv.setImageBitmap(BitmapFactory.decodeByteArray(ba,0, ba.length));
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
        Intent intent = new Intent(this, OperationsActivity.class);
        startActivity(intent);
    }
}
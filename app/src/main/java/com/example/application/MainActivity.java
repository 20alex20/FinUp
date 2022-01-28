package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    EditText Et1, Et2;
    Button Btn;
    TextView tv;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Et1 = (EditText)findViewById(R.id.et1);
        Et2 = (EditText)findViewById(R.id.et2);
        Btn = (Button)findViewById(R.id.btn);
        tv = (TextView)findViewById(R.id.text_view);
        iv = (ImageView)findViewById(R.id.imageView2);
        iv.setVisibility(View.INVISIBLE);

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("script");

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("main", Et1.getText().toString(), Et2.getText().toString());
                byte[] ba = obj.toJava(byte[].class);
                iv.setVisibility(View.VISIBLE);
                iv.setImageBitmap(BitmapFactory.decodeByteArray(ba,0, ba.length));
                // tv.setText(obj.toString());
            }
        });
    }
}
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

public class Options extends AppCompatActivity {

    ImageButton Bt1;
    FrameLayout frame1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Bt1 = (ImageButton)findViewById(R.id.export_bt);
        frame1 = (FrameLayout) findViewById(R.id.frame1);

        frame1.setVisibility(ImageView.INVISIBLE);
        Btn1 = (ImageButton)findViewById(R.id.export_bt);
        Btn2 = (ImageButton)findViewById(R.id.export_bt);

        //if(!Python.isStarted())
        //    Python.start(new AndroidPlatform(this));

        //Python py = Python.getInstance();
        //final PyObject pyobj = py.getModule("csv_xlsx");

        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("export_csv", Et1.getText().toString(), Et2.getText().toString());
                String s = obj.toString();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                if (s.equals("Вход разрешен")) {
                    btnMain(view);
                }
                // tv.setText(obj.toString());
            }
        });

        Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("export_xlsx", Et1.getText().toString(), Et2.getText().toString());
                String s = obj.toString();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                if (s.equals("Вход разрешен")) {
                    btnMain(view);
                }
                // tv.setText(obj.toString());
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

}
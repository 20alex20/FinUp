package com.example.FinUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Environment;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.application.R;

import android.widget.Button;

import java.io.File;

public class Options extends AppCompatActivity {

    public File s;
    String s1;

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

        s = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        s1 = s.getAbsolutePath();
        //Toast.makeText(getApplicationContext(),s1,Toast.LENGTH_LONG).show();

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("csv_xlsx");
        final PyObject pyobj1 = py.getModule("main");

        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("export_csv", s1);
                String s = obj.toString();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            }
        });

        Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("export_xlsx", s1);
                String s = obj.toString();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                /*File sdPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                // добавляем свой каталог к пути
                sdPath = new File(sdPath.getAbsolutePath() + "/" + "filename");
                // создаем каталог
                if (!sdPath.mkdirs()) {
                    Toast.makeText(getApplicationContext(),"not created",Toast.LENGTH_SHORT).show();
                }
                // формируем объект File, который содержит путь к файлу
                File sdFile = new File(sdPath, "filesd.txt");
                try {
                    // открываем поток для записи
                    BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
                    // пишем данные
                    bw.write("Содержимое файла на SD");
                    // закрываем поток
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
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
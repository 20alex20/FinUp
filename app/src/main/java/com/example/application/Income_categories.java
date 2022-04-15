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
import android.app.ListActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.ArrayList;
import java.util.Arrays;

public class Income_categories extends AppCompatActivity {

    FrameLayout frame1, frame2;
    EditText Et1;
    ImageButton Btn;
    ListView listd;

    private String[] name_categories;
    private int[] id_categories;

    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> catNamesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        frame1 = (FrameLayout)findViewById(R.id.frame1);
        frame2 = (FrameLayout)findViewById(R.id.frame2);
        Et1 = (EditText)findViewById(R.id.cat_name);
        Btn = (ImageButton)findViewById(R.id.add_cat);
        listd = (ListView) findViewById(R.id.list);

        frame1.setVisibility(ImageView.INVISIBLE);
        frame2.setVisibility(ImageView.INVISIBLE);

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));
        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("main");

        draw(pyobj);
        // getListView().setOnItemLongClickListener(AdapterView.OnItemLongClickListener);

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("add_deposit_category", Et1.getText().toString(), "");
                frame1.setVisibility(ImageView.INVISIBLE);
                Toast.makeText(getApplicationContext(),"Данные изменены",Toast.LENGTH_LONG).show();

                draw(pyobj);
            }
        });
    }

    public void draw(PyObject pyobj) {
        int cut = 2;
        PyObject list = pyobj.callAttr("get_deposit_categories");
        PyObject obj = pyobj.callAttr("to_line_list", list, cut);
        String[] arr = obj.toJava(String[].class);
        id_categories = new int[arr.length / cut];
        name_categories = new String[arr.length / cut];
        for (int i = 0; i < arr.length; i += cut) {
            id_categories[i / cut] = Integer.parseInt(arr[i]);
            name_categories[i / cut] = arr[i + 1];
            //Toast.makeText(getApplicationContext(),name_categories[i / cut],Toast.LENGTH_LONG).show();
        }

        catNamesList = new ArrayList<>(Arrays.asList(name_categories));
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, catNamesList);
        listd.setAdapter(mAdapter);
    }

    public void open(View view) {
        frame1.setVisibility(ImageView.VISIBLE);
    }

    public void close(View view) {
        frame1.setVisibility(ImageView.INVISIBLE);
    }

    public void open2(View view) {
        frame2.setVisibility(ImageView.VISIBLE);
    }

    public void close2(View view) {
        frame2.setVisibility(ImageView.INVISIBLE);
    }

    public void OnBack(View view) {
        Intent intent = new Intent(this, More.class);
        startActivity(intent);
    }


}
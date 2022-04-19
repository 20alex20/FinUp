package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.ArrayList;
import java.util.Arrays;

public class History extends AppCompatActivity {

    int i0 = 0, i1 = 0, i2 = 0, i3 = 0, i4=0;
    private String[] sorts = {"Дате", "Категориям", "Счету", "Сумме"};
    private String[] filters = {"Нет", "Дате", "Категориям", "Счету", "Сумме"};
    private String[] pur_dep = {"Доходы", "Расходы"};
    FrameLayout ct, dt, ac, sum;
    EditText Et1, Et2, Et3, Et4;

    Button done;

    private String[] name_categories, name_bank_accs;
    private int[] id_categories;

    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> dataList, namesList;
    private ArrayList<String> catNamesList, opNamesList, accNamesList, valueList, smlist;

    private String[] name_operations, data;
    private String[] id_operations, name_categ, name_acc, value, sm;
    int pz;

    ListView listh;

    Spinner spinnerrr;
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listh = (ListView) findViewById(R.id.listhis1);

        Et1 = (EditText)findViewById(R.id.month1);
        Et2 = (EditText)findViewById(R.id.month2);
        Et3 = (EditText)findViewById(R.id.amount1);
        Et4 = (EditText)findViewById(R.id.amount2);

        spinnerrr = (Spinner) findViewById(R.id.spinnerrr);
        spinner1 = (Spinner) findViewById(R.id.spinner_sort);
        spinner2 = (Spinner) findViewById(R.id.spinner_filter);
        spinner3 = (Spinner) findViewById(R.id.spinner_filter1);
        spinner4 = (Spinner) findViewById(R.id.spinner_filter2);

        done = (Button) findViewById(R.id.done);

        ct = (FrameLayout)findViewById(R.id.categ_frame);
        dt = (FrameLayout)findViewById(R.id.data_frame);
        ac = (FrameLayout)findViewById(R.id.accs_frame);
        sum = (FrameLayout)findViewById(R.id.summ_frame);

        ct.setVisibility(ImageView.INVISIBLE);
        dt.setVisibility(ImageView.INVISIBLE);
        ac.setVisibility(ImageView.INVISIBLE);
        sum.setVisibility(ImageView.INVISIBLE);

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));
        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("main");

        draw(pyobj, i0);
        draw_start(pyobj);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                draw2(pyobj);
                // tv.setText(obj.toString());
            }
        });

        spinnerrr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                i0 = i;
                draw(pyobj, i0);
                // Toast.makeText(getApplicationContext(),i1 + "",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                i1 = i;
                // Toast.makeText(getApplicationContext(),i1 + "",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                i3 = i;
                // Toast.makeText(getApplicationContext(),i1 + "",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                i4 = i;
                // Toast.makeText(getApplicationContext(),i1 + "",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                i2 = i;
                ct.setVisibility(ImageView.INVISIBLE);
                dt.setVisibility(ImageView.INVISIBLE);
                ac.setVisibility(ImageView.INVISIBLE);
                sum.setVisibility(ImageView.INVISIBLE);
                if (i2 == 1) {
                    dt.setVisibility(ImageView.VISIBLE);
                } else if (i2 == 2) {
                    ct.setVisibility(ImageView.VISIBLE);
                } else if (i2 == 3) {
                    ac.setVisibility(ImageView.VISIBLE);
                } else if (i2 == 4) {
                    sum.setVisibility(ImageView.VISIBLE);
                }
                // Toast.makeText(getApplicationContext(),i1 + "",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ArrayAdapter<String> Sort_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sorts);
        Sort_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(Sort_Adapter);
        ArrayAdapter<String> Filter_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, filters);
        Filter_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(Filter_Adapter);
        ArrayAdapter<String> pur_dep_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pur_dep);
        pur_dep_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerrr.setAdapter(pur_dep_Adapter);
    }

    public void draw(PyObject pyobj, int x ) {
        int cut = 2;
        PyObject list;
        if (x == 1) {
            list = pyobj.callAttr("get_categories");
        } else {
            list = pyobj.callAttr("get_deposit_categories");
        }
        PyObject obj = pyobj.callAttr("to_line_list", list, cut);
        String[] arr = obj.toJava(String[].class);
        id_categories = new int[arr.length / cut];
        name_categories = new String[arr.length / cut];
        for (int i = 0; i < arr.length; i += cut) {
            id_categories[i / cut] = Integer.parseInt(arr[i]);
            name_categories[i / cut] = arr[i + 1];
            //Toast.makeText(getApplicationContext(),name_categories[i / cut],Toast.LENGTH_LONG).show();
        }

        ArrayAdapter<String> Cat_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, name_categories);
        Cat_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(Cat_Adapter);
    }

    public void draw_start(PyObject pyobj) {
        int cut = 2;
        PyObject list2 = pyobj.callAttr("get_bank_accounts");
        PyObject obj2 = pyobj.callAttr("to_line_list", list2, cut);
        String[] arr2 = obj2.toJava(String[].class);
        id_categories = new int[arr2.length / cut];
        name_bank_accs= new String[arr2.length / cut];
        for (int i = 0; i < arr2.length; i += cut) {
            id_categories[i / cut] = Integer.parseInt(arr2[i]);
            name_bank_accs[i / cut] = arr2[i + 1];
            //Toast.makeText(getApplicationContext(),name_categories[i / cut],Toast.LENGTH_LONG).show();
        }

        ArrayAdapter<String> Acc_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, name_bank_accs);
        Acc_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(Acc_Adapter);
    }

    public void draw2(PyObject pyobj) {
        int cut = 5;
        PyObject list2 = pyobj.callAttr("sort_by", i0, i1);
        if (i2 == 1) {
            list2 = pyobj.callAttr("filter_by", list2, i2, Et1.getText().toString(), Et2.getText().toString());
        } else if(i2 == 2) {
            list2 = pyobj.callAttr("filter_by", list2, i2, name_categories[i3]);
        } else if(i2 == 3) {
            list2 = pyobj.callAttr("filter_by", list2, i2, name_bank_accs[i4]);
        } else if(i2 == 4) {
            list2 = pyobj.callAttr("filter_by", list2, i2, Et3.getText().toString(), Et4.getText().toString());
        }
        PyObject obj2 = pyobj.callAttr("to_line_list", list2, cut);
        String[] arr2 = obj2.toJava(String[].class);
        id_operations = new String[arr2.length / cut];
        name_categ = new String[arr2.length / cut];
        name_acc = new String[arr2.length / cut];
        value = new String[arr2.length / cut];
        data = new String[arr2.length / cut];
        sm = new String[arr2.length / cut];
        for (int i = 0; i < arr2.length; i += cut) {
            //id_operations[i / cut] = Integer.parseInt(arr2[i]);
            //id_categ[i / cut] = Integer.parseInt(arr2[i+1]);
            //id_acc[i / cut] = Integer.parseInt(arr2[i+2]);
            //value[i / cut] = Integer.parseInt(arr2[i+3]);
            id_operations[i / cut] = arr2[i];
            name_categ[i / cut] = arr2[i+1];
            name_acc[i / cut] = arr2[i+2];
            value[i / cut] = arr2[i+3];
            data[i / cut] = arr2[i+4];

            //sm[i / cut] = id_categ[i / cut] +" "+id_acc[i / cut]+" "+ value[i / cut]+"\n" +data[i / cut];
            if (i0 == 1) {
                sm[i / cut] = "Категория: " + name_categ[i / cut] + "\n" + "Счет списания: " + name_acc[i / cut] + "\n"
                        + "Сумма: " + value[i / cut] + "₽" + "\n" + "Дата: " + data[i / cut];
            } else {
                sm[i / cut] = "Категория: " + name_categ[i / cut] + "\n" + "Счет начисления: " + name_acc[i / cut] + "\n"
                        + "Сумма: " + value[i / cut] + "₽" + "\n" + "Дата: " + data[i / cut];
            }
        }

        opNamesList = new ArrayList<>(Arrays.asList(id_operations));
        catNamesList = new ArrayList<>(Arrays.asList(name_categ));
        accNamesList = new ArrayList<>(Arrays.asList(name_acc));
        valueList = new ArrayList<>(Arrays.asList(value));
        dataList = new ArrayList<>(Arrays.asList(data));
        smlist = new ArrayList<>(Arrays.asList(sm));
        //Toast.makeText(getApplicationContext(),dataList + "",Toast.LENGTH_LONG).show();
        //+" "+catNamesList+" "+accNamesList+" "+valueList+" "+dataList+" "+namesList
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, smlist);
        listh.setAdapter(mAdapter);
        // Toast.makeText(getApplicationContext(),list2 + "",Toast.LENGTH_LONG).show();
    }


    public void goBack(View view) {
        Intent intent = new Intent(this, OperationsActivity.class);
        startActivity(intent);
    }
}
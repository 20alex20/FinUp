package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class RegisterActivity extends AppCompatActivity {

    EditText Et1, Et2, Et3;
    Button Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Et1 = (EditText)findViewById(R.id.email_reg);
        Et2 = (EditText)findViewById(R.id.username);
        Et3 = (EditText)findViewById(R.id.PASSword);
        Btn = (Button)findViewById(R.id.btn_toop);

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        Python py = Python.getInstance();
        final PyObject pyobj = py.getModule("main");

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("register", Et1.getText().toString(), Et3.getText().toString(), Et2.getText().toString());
                String s = obj.toString();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                if (s.equals("Вход разрешен")) {
                    btnOperations(view);
                }
                // tv.setText(obj.toString());
            }
        });
    }

    /*public void сreateDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Диалог")
                .setMessage("Текст в диалоге")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(activity,"Нажата кнопка 'OK'",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(activity,"Нажата кнопка 'Отмена'",Toast.LENGTH_SHORT).show();
                    }
                });
        builder.create().show();
    }*/

    public void btnLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void btnOperations(View view) {
        Intent intent = new Intent(this, OperationsActivity.class);
        startActivity(intent);
    }
}
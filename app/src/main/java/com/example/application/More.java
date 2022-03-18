package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class More extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
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
}
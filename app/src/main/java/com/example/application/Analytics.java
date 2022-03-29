package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Analytics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
    }

    public void Scores(View view) {
        Intent intent = new Intent(this, Scores.class);
        startActivity(intent);
    }

    public void Analitics(View view) {
        Intent intent = new Intent(this, Analytics.class);
        startActivity(intent);
    }

    public void More(View view) {
        Intent intent = new Intent(this, More.class);
        startActivity(intent);
    }

    public void Operations(View view) {
        Intent intent = new Intent(this, OperationsActivity.class);
        startActivity(intent);
    }

    public void Graphics(View view) {
        Intent intent = new Intent(this, Graphics.class);
        startActivity(intent);
    }
}
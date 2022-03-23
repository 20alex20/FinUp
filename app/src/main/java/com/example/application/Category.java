package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Category extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
    }

    public void OnBack(View view) {
        Intent intent = new Intent(this, Category.class);
        startActivity(intent);
    }

    public void AddCategory(View view) {
        Intent intent = new Intent(this, AddCategory.class);
        startActivity(intent);
    }
}
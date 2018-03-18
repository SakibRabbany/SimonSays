package com.example.sakibrabbany.a4;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;


public class MainActivity extends AppCompatActivity implements Observer
{

    private Model my_model;
    private Button start_button;
    private Button settings_button;
    private TextView simon_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        my_model = Model.getInstance();
        my_model.addObserver(this);

        start_button = findViewById(R.id.start_button);

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GameActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        settings_button = findViewById(R.id.settings_button);

        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SettingsActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        simon_text = findViewById(R.id.simon_text);
        simon_text.setTextColor(Color.BLACK);
        getWindow().getDecorView().setBackgroundColor(Color.CYAN);


        my_model.initObservers();
    }

    @Override
    public void update(Observable observable, Object o) {
        System.out.println("updating view");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        my_model.deleteObserver(this);
    }
}

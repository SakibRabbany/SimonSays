package com.example.sakibrabbany.a4;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class SettingsActivity extends AppCompatActivity implements Observer {

    private Model my_model;

    private RadioGroup rb_group_num_balls;
    private RadioButton rb1, rb2, rb3, rb4, rb5, rb6;

    private RadioGroup rb_group_difficulty;
    private RadioButton rb_easy, rb_normal, rb_hard;

    private Button back_button_settings;

    private TextView settings_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // the model
        my_model = Model.getInstance();

        // add the activity as an observer
        my_model.addObserver(this);

        // the radio group
        rb_group_num_balls = findViewById(R.id.num_balls_group);

        // radio group controller
        rb_group_num_balls.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = findViewById(i);
                CharSequence num_ball_str = rb.getText();
                my_model.setNumBalls(Integer.parseInt(num_ball_str.toString()));
            }
        });

        // the radio buttons
        rb1 = findViewById(R.id.rb_1);
        rb2 = findViewById(R.id.rb_2);
        rb3 = findViewById(R.id.rb_3);
        rb4 = findViewById(R.id.rb_4);
        rb5 = findViewById(R.id.rb_5);
        rb6 = findViewById(R.id.rb_6);

        // radio group difficulty
        rb_group_difficulty = findViewById(R.id.rb_group_difficulty);

        // radio group difficulty controller
        rb_group_difficulty.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = findViewById(i);
                String difficulty = rb.getText().toString();
                switch (difficulty){
                    case "Easy":
                        my_model.setDifficulty(1);
                        break;
                    case "Normal":
                        my_model.setDifficulty(2);
                        break;
                    case "Hard":
                        my_model.setDifficulty(3);
                        break;
                }
            }
        });

        rb_easy = findViewById(R.id.rb_easy);
        rb_normal = findViewById(R.id.rb_normal);
        rb_hard = findViewById(R.id.rb_hard);

        settings_text = findViewById(R.id.settings_label);
        settings_text.setTextColor(Color.BLACK);
        // back button
        back_button_settings = findViewById(R.id.settings_back);

        // back button controller
        back_button_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        my_model.initObservers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        my_model.deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        int num_balls = my_model.getNumBalls();
        switch (num_balls) {
            case 1:
                rb_group_num_balls.check(rb1.getId());
                break;
            case 2:
                rb_group_num_balls.check(rb2.getId());
                break;
            case 3:
                rb_group_num_balls.check(rb3.getId());
                break;
            case 4:
                rb_group_num_balls.check(rb4.getId());
                break;
            case 5:
                rb_group_num_balls.check(rb5.getId());
                break;
            case 6:
                rb_group_num_balls.check(rb6.getId());
                break;
        }

        int difficulty = my_model.getDifficulty();

        switch (difficulty) {
            case 1:
                rb_group_difficulty.check(rb_easy.getId());
                break;
            case 2:
                rb_group_difficulty.check(rb_normal.getId());
                break;
            case 3:
                rb_group_difficulty.check(rb_hard.getId());
                break;
        }
    }
}

package com.example.sakibrabbany.a4;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class GameActivity extends AppCompatActivity implements Observer{

    private Model my_model;
    private Button back_button_game;

    private ArrayList<Button> buttons;

    private TextView score_text;
    private TextView message_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        my_model = Model.getInstance();
        my_model.createSimon();

        my_model.addObserver(this);

        score_text = findViewById(R.id.score_text);
        message_text = findViewById(R.id.message_text);

        System.out.println("before cotrllr");
        RelativeLayout layout = findViewById(R.id.game_view);
//        back_button_game = new Button(this);
//        back_button_game.setText("Back");
//        layout.addView(back_button_game);
        back_button_game = findViewById(R.id.game_back);
        back_button_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        System.out.println("after cotrllr");

        buttons = new ArrayList<Button>();
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                String state = my_model.getSimonState();
                if (state == "START" || state == "WIN" || state == "LOSE") {
                    my_model.startNewRound(buttons);
                    return true;
                }
                return false;
            }
        });
        initButtons(layout);
        setContentView(layout);


        my_model.initObservers();
    }


    private void initButtons(RelativeLayout layout) {
        System.out.println("inside initbuttons");


        int num_balls = my_model.getNumBalls();

        int num_rows = (int)Math.ceil(num_balls/2.0);
        System.out.println("num_rows: " + num_rows);

        TableLayout table = findViewById(R.id.button_table);

        int n = 2;

        int number = 1;

        for (int i = 0 ; i < num_rows ; i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(50,50,50,50);
            row.setLayoutParams(lp);
            while(num_balls > 0 && n > 0){
                Button btn = new Button(this);
                final Button curr_button = btn;
                //final Drawable circle = this.getResources().getDrawable(R.drawable.circle);

                String btn_txt = Integer.toString(number);
                number++;

                btn.setText(btn_txt);

                btn.setLayoutParams(lp);

                btn.setVisibility(View.VISIBLE);

                btn.setBackground(this.getResources().getDrawable(R.drawable.circle));

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println(curr_button.getText());
                        if (my_model.getSimonState() == "HUMAN") {
                            my_model.verifyButton(Integer.parseInt(curr_button.getText().toString()));
                        }
                    }
                });

                buttons.add(btn);

                row.addView(btn);
                num_balls--;
                n--;
            }
            n = 2;
            table.addView(row);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        //initButtons();
        System.out.println("num buttons: " + buttons.size());

        String score = "Score: " + my_model.getSimonScore();
        score_text.setText(score);

        String state = my_model.getSimonState();

        String message = new String();

        switch (state) {

            // will only be in this state right after Simon object is contructed
            case "START":
                message = "Tap to play";
                break;
            // they won last round
            // score is increased by 1, sequence length is increased by 1
            case "WIN":
                message = "Tap to continue.";
                break;
            // they lost last round
            // score is reset to 0, sequence length is reset to 1
            case "LOSE":
                message = "Tap to play again.";
                break;
            case "COMPUTER":
                message = "Watch me.";
                break;
            case "HUMAN":
                message = "Your Turn.";
        }

        message_text.setText(message);

        if (state == "COMPUTER" || state == "WIN" || state == "LOSE" || state == "START"){
            for (int i = 0 ; i < buttons.size() ; i++){
                Button btn = buttons.get(i);
                btn.setClickable(false);
            }
            if (state == "LOSE") {
                getWindow().getDecorView().setBackgroundColor(Color.RED);
            } else if (state == "WIN") {
                getWindow().getDecorView().setBackgroundColor(Color.GREEN);
            } else {
                getWindow().getDecorView().setBackgroundColor(Color.LTGRAY);
            }
        } else {
            for (int i = 0 ; i < buttons.size() ; i++){
                Button btn = buttons.get(i);
                btn.setClickable(true);
            }
            getWindow().getDecorView().setBackgroundColor(Color.LTGRAY);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        my_model.deleteObserver(this);
    }
}

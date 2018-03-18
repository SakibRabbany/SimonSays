package com.example.sakibrabbany.a4;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by sakibrabbany on 2017-12-03.
 */

public class Simon {

    enum State { START, COMPUTER, HUMAN, LOSE, WIN };

    private State state;
    private int score;
    private int seq_length;
    private int[] sequence;
    private int index;
    private Random rand;

    private int num_balls;


    Simon(int num_balls) {
        this.num_balls = num_balls;
        this.seq_length = 1;
        this.state = State.START;
        this.score = 0;
        this.sequence = new int[seq_length];
        this.rand = new Random();
    }

    public int getScore(){
        return score;
    }

    public State getState() {
        return state;
    }

    public String getStateAsString() {
        switch (getState()) {
            case START:
                return "START";
            case COMPUTER:
                return "COMPUTER";
            case HUMAN:
                return "HUMAN";
            case LOSE:
                return "LOSE";
            case WIN:
                return "WIN";
            default:
                return "Unknown State";
        }
    }

    public void newRound() {

        // reset if they lost last time
        if (state == State.LOSE) {
            seq_length = 1;
            score = 0;
        }

        sequence = new int[seq_length];

        for (int i = 0; i < seq_length; i++) {
            int b = rand.nextInt(num_balls) + 1;
            sequence[i] = b;
        }

        index = 0;
        state = State.COMPUTER;

    }

    public int nextButton() {

        if (state != State.COMPUTER) {
            return -1;
        }

        // this is the next button to show in the sequence
        int button = sequence[index];

        // advance to next button
        index++;

        // if all the buttons were shown, give
        // the human a chance to guess the sequence
        if (index >= seq_length) {
            index = 0;
            state = State.HUMAN;
        }


        return button;
    }

    public boolean verifyButton(int button) {

        if (state != State.HUMAN) {
            return false;
        }

        // did they press the right button?
        boolean correct = (button == sequence[index]);

        // advance to next button
        index++;

        // pushed the wrong buttons
        if (!correct) {
            state = State.LOSE;

            // they got it right
        } else {

            // if last button, then the win the round
            if (index == seq_length) {
                state = State.WIN;
                // update the score and increase the difficulty
                score++;
                seq_length++;
            }
        }
        return correct;
    }

}

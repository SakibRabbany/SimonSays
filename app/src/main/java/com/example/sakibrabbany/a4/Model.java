package com.example.sakibrabbany.a4;

import android.widget.Button;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.lang.Object;
//import java.util.logging.Handler;
import android.os.Handler;
import android.widget.TextView;

/**
 * Created by sakibrabbany on 2017-12-02.
 */

public class Model extends Observable {
    // Create static instance of this mModel
    private static final Model model_instance = new Model();

    private int difficulty;
    private int num_balls;

    private int score;

    private Simon simon;

    private Handler handler;

    private Button selected_button;

    private ArrayList<Button> btns;

    // constructor
    Model() {
        this.difficulty = 2;
        this.num_balls = 4;
        handler = new Handler();
    }

    static Model getInstance()
    {
        return model_instance;
    }

    public int getScore(){
        return this.score;
    }

    public Simon getSimon(){
        return simon;
    }

    public void createSimon(){
        this.simon = new Simon(num_balls);
    }

    public String getSimonState() {
        return simon.getStateAsString();
    }

    public int getSimonScore() {
        return simon.getScore();
    }

    public void displaySequence(ArrayList<Button> buttons) {
        String state = getSimonState();
        if (!"COMPUTER".equals(state)) return;
        btns = buttons;
        int but_num = simon.nextButton();
        selected_button =  buttons.get(but_num - 1);
        selected_button.setPressed(true);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                selected_button.setPressed(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        displaySequence(btns);
                    }
                }, 500);
            }
        }, getSpeed());
        setChanged();
        notifyObservers();
    }

    public void startNewRound(ArrayList<Button> buttons) {
        simon.newRound();
//        setChanged();
//        notifyObservers();
        displaySequence(buttons);
        setChanged();
        notifyObservers();
    }

    public long getSpeed() {
        long speed = 0;
        if (difficulty == 1){
            speed = 1500;
        } else if (difficulty == 2){
            speed = 1000;
        } else if (difficulty == 3) {
            speed = 500;
        }
        return speed;
    }

    public void verifyButton(int button) {
        simon.verifyButton(button);
        setChanged();
        notifyObservers();
    }

    public void setScore(int new_score) {
        this.score = new_score;
        setChanged();
        notifyObservers();
    }



    // difficulty getter
    public int getDifficulty()
    {
        return this.difficulty;
    }

    // difficulty setter
    public void setDifficulty(int new_difficulty) {
        this.difficulty = new_difficulty;
        setChanged();
        notifyObservers();
    }

    // numballs getter
    public int getNumBalls(){
        return this.num_balls;
    }

    // numball setter
    public void setNumBalls(int new_num_balls) {
        this.num_balls = new_num_balls;
        setChanged();
        notifyObservers();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Observable Methods
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Helper method to make it easier to initialize all observers
     */
    public void initObservers()
    {
        setChanged();
        notifyObservers();
    }
    /**
     * Deletes an observer from the set of observers of this object.
     * Passing <CODE>null</CODE> to this method will have no effect.
     *
     * @param o the observer to be deleted.
     */
    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
    }

    /**
     * Adds an observer to the set of observers for this object, provided
     * that it is not the same as some observer already in the set.
     * The order in which notifications will be delivered to multiple
     * observers is not specified. See the class comment.
     *
     * @param o an observer to be added.
     * @throws NullPointerException if the parameter o is null.
     */
    @Override
    public synchronized void addObserver(Observer o)
    {
        super.addObserver(o);
    }
    /**
     * Clears the observer list so that this object no longer has any observers.
     */
    @Override
    public synchronized void deleteObservers()
    {
        super.deleteObservers();
    }
    /**
     * If this object has changed, as indicated by the
     * <code>hasChanged</code> method, then notify all of its observers
     * and then call the <code>clearChanged</code> method to
     * indicate that this object has no longer changed.
     * <p>
     * Each observer has its <code>update</code> method called with two
     * arguments: this observable object and <code>null</code>. In other
     * words, this method is equivalent to:
     * <blockquote><tt>
     * notifyObservers(null)</tt></blockquote>
     *
     * @see Observable#clearChanged()
     * @see Observable#hasChanged()
     * @see Observer#update(Observable, Object)
     */
    @Override
    public void notifyObservers()
    {
        super.notifyObservers();
    }
}

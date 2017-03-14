package com.example.dell.connect3;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    List<ImageView> buttons ;
    Drawable red, yellow;
    boolean playing;
    boolean[][] redPositions , yellowPositions;
    private enum color{RED,YELLOW}
    color turn = color.RED;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Tic Tac Toe");
        setContentView(R.layout.activity_main);

        redPositions = new boolean[3][3];
        yellowPositions = new boolean[3][3];
        buttons = new ArrayList<>();
        red = ContextCompat.getDrawable(getApplicationContext(), R.drawable.red);
        yellow = ContextCompat.getDrawable(getApplicationContext(), R.drawable.yellow);
        ViewGroup parent = (ViewGroup) findViewById(R.id.grid);
        for (int j = 0; j < parent.getChildCount(); j++) {
            if(parent.getChildAt(j) instanceof ImageView) {
                buttons.add((ImageView) parent.getChildAt(j));

            }
        }

        for (ImageView b:buttons) {
            b.setAlpha(0f);
        }
        Toast.makeText(getApplicationContext(),"Red Turn!",Toast.LENGTH_LONG).show();
        playing = true;
    }

    public void clicked(View v){

        ImageView clickedB =(ImageView)v;
        short row = 0,col = 0;
        if (clickedB.getAlpha()<=0f && playing){
            short value =Short.parseShort(v.getTag().toString());
            switch (value){
                case 0:
                case 1:
                case 2:
                    row = 0;
                    col = value;
                    break;
                case 3:
                case 4:
                case 5:
                    row = 1;
                    col = (short) (value - 3);
                    break;
                case 6:
                case 7:
                case 8:
                    row = 2;
                    col = (short) (value - 6);
                    break;
                default:
                    Log.w("error","undefined value");
                    return;

            }

            if (turn.equals(color.RED)){
                clickedB.setImageDrawable(red);
                redPositions[row][col] = true;
                clickedB.setTranslationY(-1000f);
                clickedB.animate().alpha(1f).translationYBy(1000f).rotationBy(360*5).setDuration(800);
                if(checkLines()){
                    Toast.makeText(getApplicationContext(),"Red Win!",Toast.LENGTH_LONG).show();
                    playing = false;
                    return;
                }
                turn= color.YELLOW;
                Toast.makeText(getApplicationContext(),"Yellow Turn!",Toast.LENGTH_LONG).show();
            }else {
                clickedB.setImageDrawable(yellow);
                yellowPositions[row][col] = true;
                clickedB.setTranslationY(-1000f);
                clickedB.animate().alpha(1f).translationYBy(1000f).rotationBy(360*5).setDuration(800);
                if(checkLines()){
                    Toast.makeText(getApplicationContext(),"Yellow Win!",Toast.LENGTH_LONG).show();
                    playing = false;
                    return;
                }
                turn = color.RED;
                Toast.makeText(getApplicationContext(),"Red Turn!",Toast.LENGTH_LONG).show();
            }

        }
    }

    public void restart(View v){
        for (ImageView b:buttons) {
            b.setAlpha(0f);
        }
        yellowPositions = new boolean[3][3];
        redPositions = new boolean[3][3];

        turn = color.RED;
        Toast.makeText(getApplicationContext(),"Red Turn!",Toast.LENGTH_LONG).show();
        playing = true;
    }

    private boolean checkLines(){
        boolean[][] arr;
        if(turn.equals(color.RED)){
            arr = redPositions;
        }else {
            arr = yellowPositions;
        }

        for (int i=0;i<arr.length;i++){
            if (arr[i][0] && arr[i][1] && arr[i][2]){
                return true;
            }else if(arr[0][i] && arr[1][i] && arr[2][i]){
                return true;
            }
        }

        if (arr[0][0] && arr[1][1]&& arr[2][2]){
            return true;
        }else if (arr[0][2] && arr[1][1] && arr[2][0]){
            return true;
        }


        return false;
    }
}

package com.ahmedmohamed.gameskids;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.Dialog;
import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ConnectGameActivity extends AppCompatActivity {

    private ImageView red, green, blue;
    private ImageView redTarget, greenTarget, blueTarget;
    private boolean isredCorrect = false;
    private boolean isgreenCorrect = false;
    private boolean isblueCorrect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_game);

        red = findViewById(R.id.redCircle);
        green = findViewById(R.id.greenCircle);
        blue = findViewById(R.id.blueCircle);

        redTarget = findViewById(R.id.red_target);
        greenTarget = findViewById(R.id.green_target);
        blueTarget = findViewById(R.id.blue_target);

        setDragAndDrop(red, redTarget);
        setDragAndDrop(green, greenTarget);
        setDragAndDrop(blue, blueTarget);

    }

    private void setDragAndDrop(final ImageView shape, final ImageView target) {
        shape.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDrag(data, shadowBuilder, v, 0);
                    return true;
                } else {
                    return false;
                }
            }
        });

        target.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                    case DragEvent.ACTION_DRAG_ENDED:
                        return true;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        return false;
                    case DragEvent.ACTION_DRAG_EXITED:
                        return false;
                    case DragEvent.ACTION_DROP:
                        View draggedView = (View) event.getLocalState();
                        if (draggedView.getTag().equals(v.getContentDescription())) {
                            draggedView.setVisibility(View.GONE);
                            if (draggedView == red) {
                                isredCorrect = true;
                            } else if (draggedView == green) {
                                isgreenCorrect = true;
                            } else if (draggedView == blue) {
                                isblueCorrect = true;
                            }
                            if (isredCorrect && isgreenCorrect && isblueCorrect) {
                                winDialog();
                            }
                        } else {
                            loseDialog();
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
    public void loseDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.lose_dialog);

        Button reset = dialog.findViewById(R.id.Reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void winDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.reward_dialog);

        Button reset = dialog.findViewById(R.id.Reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void resetGame() {
        red.setVisibility(View.VISIBLE);
        green.setVisibility(View.VISIBLE);
        blue.setVisibility(View.VISIBLE);
        isredCorrect = false;
        isgreenCorrect = false;
        isblueCorrect = false;
    }

}
package com.ahmedmohamed.gameskids;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ClipData;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class PhotosGameActivity extends AppCompatActivity {

    private ImageView img1, img2, img3, img4, img5, img6;
    private ImageView target1, target2, target3, target4, target5, target6;
    private ImageView win1, win2, win3, win4, win5, win6;
    private boolean is1Correct = false;
    private boolean is2Correct = false;
    private boolean is3Correct = false;
    private boolean is4Correct = false;
    private boolean is5Correct = false;
    private boolean is6Correct = false;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_game);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
        img6 = findViewById(R.id.img6);

        target1 = findViewById(R.id.target1);
        target2 = findViewById(R.id.target2);
        target3 = findViewById(R.id.target3);
        target4 = findViewById(R.id.target4);
        target5 = findViewById(R.id.target5);
        target6 = findViewById(R.id.target6);

        win1 = findViewById(R.id.win1);
        win2 = findViewById(R.id.win2);
        win3 = findViewById(R.id.win3);
        win4 = findViewById(R.id.win4);
        win5 = findViewById(R.id.win5);
        win6 = findViewById(R.id.win6);

        setDragAndDrop(img1, target1);
        setDragAndDrop(img2, target2);
        setDragAndDrop(img3, target3);
        setDragAndDrop(img4, target4);
        setDragAndDrop(img5, target5);
        setDragAndDrop(img6, target6);

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
                            v.setVisibility(View.GONE);
                            if (v.getContentDescription().equals("img1")) {
                                win1.setVisibility(View.VISIBLE);
                            }else if (v.getContentDescription().equals("img2")) {
                                win2.setVisibility(View.VISIBLE);
                            }else if (v.getContentDescription().equals("img3")) {
                                win3.setVisibility(View.VISIBLE);
                            }else if (v.getContentDescription().equals("img4")) {
                                win4.setVisibility(View.VISIBLE);
                            }else if (v.getContentDescription().equals("img5")) {
                                win5.setVisibility(View.VISIBLE);
                            }else if (v.getContentDescription().equals("img6")) {
                                win6.setVisibility(View.VISIBLE);
                            }
                            mediaPlayer = MediaPlayer.create(PhotosGameActivity.this, R.raw.clap);
                            mediaPlayer.start();
                            if (draggedView == img1) {
                                is1Correct = true;
                            } else if (draggedView == img2) {
                                is2Correct = true;
                            } else if (draggedView == img3) {
                                is3Correct = true;
                            } else if (draggedView == img4) {
                                is4Correct = true;
                            } else if (draggedView == img5) {
                                is5Correct = true;
                            } else if (draggedView == img6) {
                                is6Correct = true;
                            }
                            if (is1Correct && is2Correct && is3Correct && is4Correct && is5Correct && is6Correct) {
                                winDialog();
                                mediaPlayer.start();
                            }
                        } else {
                            mediaPlayer.stop();
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
        img1.setVisibility(View.VISIBLE);
        img2.setVisibility(View.VISIBLE);
        img3.setVisibility(View.VISIBLE);
        img4.setVisibility(View.VISIBLE);
        img5.setVisibility(View.VISIBLE);
        img6.setVisibility(View.VISIBLE);
        target1.setVisibility(View.VISIBLE);
        target2.setVisibility(View.VISIBLE);
        target3.setVisibility(View.VISIBLE);
        target4.setVisibility(View.VISIBLE);
        target5.setVisibility(View.VISIBLE);
        target6.setVisibility(View.VISIBLE);
        win1.setVisibility(View.GONE);
        win2.setVisibility(View.GONE);
        win3.setVisibility(View.GONE);
        win4.setVisibility(View.GONE);
        win5.setVisibility(View.GONE);
        win6.setVisibility(View.GONE);
        is1Correct = false;
        is2Correct = false;
        is3Correct = false;
        is4Correct = false;
        is5Correct = false;
        is6Correct = false;
    }

}
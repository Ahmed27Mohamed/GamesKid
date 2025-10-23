package com.ahmedmohamed.gameskids;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ShapesGameActivity extends AppCompatActivity {

    private ImageView circle, square, triangle;
    private ImageView circleTarget, squareTarget, triangleTarget;
    private boolean isCircleCorrect = false;
    private boolean isSquareCorrect = false;
    private boolean isTriangleCorrect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shapes_game);

        circle = findViewById(R.id.circle);
        square = findViewById(R.id.square);
        triangle = findViewById(R.id.triangle);

        circleTarget = findViewById(R.id.circle_target);
        squareTarget = findViewById(R.id.square_target);
        triangleTarget = findViewById(R.id.triangle_target);

        setDragAndDrop(circle, circleTarget);
        setDragAndDrop(square, squareTarget);
        setDragAndDrop(triangle, triangleTarget);

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
                        if (v.getContentDescription().equals("square")) {
                            v.setBackground(getDrawable(R.drawable.square_green));
                        } else if (v.getContentDescription().equals("triangle")) {
                            v.setBackground(getDrawable(R.drawable.triangle_green));
                        } else if (v.getContentDescription().equals("circle")) {
                            v.setBackground(getDrawable(R.drawable.circle_green));
                        }
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        if (v.getContentDescription().equals("square")) {
                            v.setBackground(getDrawable(R.drawable.square_target));
                        } else if (v.getContentDescription().equals("triangle")) {
                            v.setBackground(getDrawable(R.drawable.triangle_target));
                        } else if (v.getContentDescription().equals("circle")) {
                            v.setBackground(getDrawable(R.drawable.circle_target));
                        }
                        return true;
                    case DragEvent.ACTION_DROP:
                        View draggedView = (View) event.getLocalState();
                        if (draggedView.getTag().equals(v.getContentDescription())) {
                            draggedView.setVisibility(View.GONE);
                            if (draggedView == circle) {
                                isCircleCorrect = true;
                            } else if (draggedView == square) {
                                isSquareCorrect = true;
                            } else if (draggedView == triangle) {
                                isTriangleCorrect = true;
                            }
                            if (isCircleCorrect && isSquareCorrect && isTriangleCorrect) {
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

    private void randomizeShapes() {
        ConstraintLayout layout = findViewById(R.id.constraint_layout);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);

        // مصفوفة تحتوي على الأشكال
        ImageView[] shapes = {circle, square, triangle};

        // إنشاء مصفوفات لتخزين القيود الأصلية (start و top) لكل شكل
        int[] originalTops = new int[shapes.length];
        int[] originalStarts = new int[shapes.length];

        // حفظ القيود الأصلية لكل شكل
        for (int i = 0; i < shapes.length; i++) {
            originalTops[i] = constraintSet.getConstraint(shapes[i].getId()).layout.topToTop;
            originalStarts[i] = constraintSet.getConstraint(shapes[i].getId()).layout.startToStart;
        }

        // خلط الأشكال عشوائيًا
        List<ImageView> shapesList = Arrays.asList(shapes);
        Collections.shuffle(shapesList);

        // تبديل أماكن الأشكال مع بعضها
        for (int i = 0; i < shapesList.size(); i++) {
            ImageView shape = shapesList.get(i);

            // استبدال موقع الشكل الحالي بالموقع المخزن لشكل آخر
            constraintSet.connect(shape.getId(), ConstraintSet.TOP, layout.getId(), originalTops[i], 0);
            constraintSet.connect(shape.getId(), ConstraintSet.START, layout.getId(), originalStarts[i], 0);
        }

        // تطبيق القيود الجديدة
        constraintSet.applyTo(layout);
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
        circleTarget.setBackground(getDrawable(R.drawable.circle_target));
        squareTarget.setBackground(getDrawable(R.drawable.square_target));
        triangleTarget.setBackground(getDrawable(R.drawable.triangle_target));
        circle.setVisibility(View.VISIBLE);
        square.setVisibility(View.VISIBLE);
        triangle.setVisibility(View.VISIBLE);
        isCircleCorrect = false;
        isSquareCorrect = false;
        isTriangleCorrect = false;
//        randomizeShapes();
    }

}
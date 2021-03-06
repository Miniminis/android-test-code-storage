package com.example.kotlintest.views;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.kotlintest.R;
import com.example.kotlintest.models.Todo;

public class TodoView extends ConstraintLayout {

    private CheckBox completeCheckBox;
    private TextView descriptionView;

    public TodoView(Context context) {
        super(context);
    }

    public TodoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TodoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initView(Todo todo) {
        completeCheckBox = findViewById(R.id.completeCheckBox);
        descriptionView = findViewById(R.id.descriptionView);

        descriptionView.setText(todo.getDescription());
        completeCheckBox.setChecked(todo.isComplete());

        if (todo.isComplete()) {
            createStrikeThrough();
        }

        setUpCheckStateListener();
    }

    public void setUpCheckStateListener() {
        completeCheckBox.setOnCheckedChangeListener((button, isChecked) -> {
            if (isChecked) {
                createStrikeThrough();
            } else {
                removeStrikeThrough();
            }
        });
    }

    private void createStrikeThrough() {
        descriptionView.setPaintFlags(descriptionView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void removeStrikeThrough() {
        descriptionView.setPaintFlags(descriptionView.getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));
    }

}


package com.lachapelle.christian.calculator;

import android.content.Context;
import android.util.AttributeSet;

public class NoVKEditText extends androidx.appcompat.widget.AppCompatEditText {
    public NoVKEditText(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    public boolean onCheckIsTextEditor() {
        return false;
    }
}

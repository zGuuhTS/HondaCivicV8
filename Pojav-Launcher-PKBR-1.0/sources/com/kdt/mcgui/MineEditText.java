package com.kdt.mcgui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatEditText;

public class MineEditText extends AppCompatEditText {
    public MineEditText(Context ctx) {
        super(ctx);
        init();
    }

    public MineEditText(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        init();
    }

    public void init() {
        setBackgroundColor(Color.parseColor("#131313"));
        setPadding(5, 5, 5, 5);
    }
}

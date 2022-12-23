package com.kdt.mcgui;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.res.ResourcesCompat;
import com.p000br.pixelmonbrasil.debug.R;

public class MineButton extends AppCompatButton {
    public MineButton(Context ctx) {
        this(ctx, (AttributeSet) null);
    }

    public MineButton(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        init();
    }

    public void init() {
        setTypeface(ResourcesCompat.getFont(getContext(), R.font.noto_sans_bold));
        setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.mine_button_background, (Resources.Theme) null));
    }
}

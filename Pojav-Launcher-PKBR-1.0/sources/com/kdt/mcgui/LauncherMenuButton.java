package com.kdt.mcgui;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import com.p000br.pixelmonbrasil.debug.R;
import p001fr.spse.extended_view.ExtendedButton;

public class LauncherMenuButton extends ExtendedButton {
    public LauncherMenuButton(Context context) {
        super(context);
        setSettings();
    }

    public LauncherMenuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSettings();
    }

    private void setSettings() {
        Resources resources = getContext().getResources();
        int padding = resources.getDimensionPixelSize(R.dimen._22sdp);
        setCompoundDrawablePadding(padding);
        setPaddingRelative(padding, 0, 0, 0);
        setGravity(16);
        int[] sizes = getExtendedViewData().getSizeCompounds();
        sizes[0] = resources.getDimensionPixelSize(R.dimen._30sdp);
        getExtendedViewData().setSizeCompounds(sizes);
        postProcessDrawables();
    }
}

package net.kdt.pojavlaunch.prefs;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.Preference;
import com.p000br.pixelmonbrasil.debug.R;
import net.kdt.pojavlaunch.extra.ExtraConstants;
import net.kdt.pojavlaunch.extra.ExtraCore;

public class BackButtonPreference extends Preference {
    public BackButtonPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BackButtonPreference(Context context) {
        this(context, (AttributeSet) null);
    }

    private void init() {
        if (getTitle() == null) {
            setTitle((int) R.string.preference_back_title);
        }
        if (getIcon() == null) {
            setIcon((int) R.drawable.ic_arrow_back_white);
        }
    }

    /* access modifiers changed from: protected */
    public void onClick() {
        ExtraCore.setValue(ExtraConstants.BACK_PREFERENCE, "true");
    }
}

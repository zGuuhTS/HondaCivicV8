package net.kdt.pojavlaunch.prefs;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.Preference;
import net.kdt.pojavlaunch.multirt.MultiRTConfigDialog;

public class RuntimeManagerPreference extends Preference {
    private MultiRTConfigDialog mDialogScreen;

    public RuntimeManagerPreference(Context ctx) {
        this(ctx, (AttributeSet) null);
    }

    public RuntimeManagerPreference(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        setPersistent(false);
    }

    /* access modifiers changed from: protected */
    public void onClick() {
        if (this.mDialogScreen == null) {
            MultiRTConfigDialog multiRTConfigDialog = new MultiRTConfigDialog();
            this.mDialogScreen = multiRTConfigDialog;
            multiRTConfigDialog.prepare((Activity) getContext());
        }
        this.mDialogScreen.show();
    }
}

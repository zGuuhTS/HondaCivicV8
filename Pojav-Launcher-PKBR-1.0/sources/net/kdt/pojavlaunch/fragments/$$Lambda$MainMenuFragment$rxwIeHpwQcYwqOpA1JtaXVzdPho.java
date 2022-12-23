package net.kdt.pojavlaunch.fragments;

import android.view.View;
import net.kdt.pojavlaunch.extra.ExtraConstants;
import net.kdt.pojavlaunch.extra.ExtraCore;

/* renamed from: net.kdt.pojavlaunch.fragments.-$$Lambda$MainMenuFragment$rxwIeHpwQcYwqOpA1JtaXVzdPho  reason: invalid class name */
/* compiled from: lambda */
public final /* synthetic */ class $$Lambda$MainMenuFragment$rxwIeHpwQcYwqOpA1JtaXVzdPho implements View.OnClickListener {
    public static final /* synthetic */ $$Lambda$MainMenuFragment$rxwIeHpwQcYwqOpA1JtaXVzdPho INSTANCE = new $$Lambda$MainMenuFragment$rxwIeHpwQcYwqOpA1JtaXVzdPho();

    private /* synthetic */ $$Lambda$MainMenuFragment$rxwIeHpwQcYwqOpA1JtaXVzdPho() {
    }

    public final void onClick(View view) {
        ExtraCore.setValue(ExtraConstants.LAUNCH_GAME, true);
    }
}

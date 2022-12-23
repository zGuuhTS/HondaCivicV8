package net.kdt.pojavlaunch.customcontrols.handleview;

import android.view.View;
import net.kdt.pojavlaunch.customcontrols.buttons.ControlInterface;

public interface ActionButtonInterface extends View.OnClickListener {
    void init();

    void onClick();

    void setFollowedView(ControlInterface controlInterface);

    boolean shouldBeVisible();

    void onClick(View v) {
        onClick();
    }
}

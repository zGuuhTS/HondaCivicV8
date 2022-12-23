package net.kdt.pojavlaunch.customcontrols.handleview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import com.p000br.pixelmonbrasil.debug.R;
import net.kdt.pojavlaunch.customcontrols.ControlData;
import net.kdt.pojavlaunch.customcontrols.buttons.ControlDrawer;
import net.kdt.pojavlaunch.customcontrols.buttons.ControlInterface;

public class AddSubButton extends Button implements ActionButtonInterface {
    private ControlInterface mCurrentlySelectedButton = null;

    public AddSubButton(Context context) {
        super(context);
        init();
    }

    public AddSubButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        setText(R.string.customctrl_addsubbutton);
        setOnClickListener(this);
    }

    public boolean shouldBeVisible() {
        ControlInterface controlInterface = this.mCurrentlySelectedButton;
        return controlInterface != null && (controlInterface instanceof ControlDrawer);
    }

    public void setFollowedView(ControlInterface view) {
        this.mCurrentlySelectedButton = view;
    }

    public void onClick() {
        ControlInterface controlInterface = this.mCurrentlySelectedButton;
        if (controlInterface instanceof ControlDrawer) {
            ((ControlDrawer) controlInterface).getControlLayoutParent().addSubButton((ControlDrawer) this.mCurrentlySelectedButton, new ControlData());
        }
    }
}

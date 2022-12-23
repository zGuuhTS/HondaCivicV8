package net.kdt.pojavlaunch.customcontrols.handleview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import com.p000br.pixelmonbrasil.debug.R;
import net.kdt.pojavlaunch.customcontrols.buttons.ControlInterface;

public class CloneButton extends Button implements ActionButtonInterface {
    private ControlInterface mCurrentlySelectedButton = null;

    public CloneButton(Context context) {
        super(context);
        init();
    }

    public CloneButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        setOnClickListener(this);
        setAllCaps(true);
        setText(R.string.global_clone);
    }

    public boolean shouldBeVisible() {
        return this.mCurrentlySelectedButton != null;
    }

    public void setFollowedView(ControlInterface view) {
        this.mCurrentlySelectedButton = view;
    }

    public void onClick() {
        ControlInterface controlInterface = this.mCurrentlySelectedButton;
        if (controlInterface != null) {
            controlInterface.cloneButton();
            this.mCurrentlySelectedButton.getControlLayoutParent().removeEditWindow();
        }
    }
}

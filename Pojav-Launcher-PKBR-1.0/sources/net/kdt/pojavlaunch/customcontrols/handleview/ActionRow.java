package net.kdt.pojavlaunch.customcontrols.handleview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import androidx.core.math.MathUtils;
import com.p000br.pixelmonbrasil.debug.R;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.customcontrols.buttons.ControlInterface;

public class ActionRow extends LinearLayout {
    public static int SIDE_AUTO = 4;
    public static int SIDE_BOTTOM = 3;
    public static int SIDE_LEFT = 0;
    public static int SIDE_RIGHT = 2;
    public static int SIDE_TOP = 1;
    private final ActionButtonInterface[] actionButtons = new ActionButtonInterface[3];
    /* access modifiers changed from: private */
    public View mFollowedView = null;
    public final ViewTreeObserver.OnPreDrawListener mFollowedViewListener = new ViewTreeObserver.OnPreDrawListener() {
        public boolean onPreDraw() {
            if (ActionRow.this.mFollowedView == null || !ActionRow.this.mFollowedView.isShown()) {
                ActionRow.this.hide();
                return true;
            }
            ActionRow.this.setNewPosition();
            return true;
        }
    };
    private int mSide = SIDE_TOP;

    public ActionRow(Context context) {
        super(context);
        init();
    }

    public ActionRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setTranslationZ(11.0f);
        setVisibility(8);
        setOrientation(0);
        setLayoutParams(new LinearLayout.LayoutParams(-2, getResources().getDimensionPixelOffset(R.dimen._40sdp)));
        this.actionButtons[0] = new DeleteButton(getContext());
        this.actionButtons[1] = new CloneButton(getContext());
        this.actionButtons[2] = new AddSubButton(getContext());
        for (ActionButtonInterface buttonInterface : this.actionButtons) {
            addView((View) buttonInterface, new LinearLayout.LayoutParams(0, -1, 1.0f));
        }
        setElevation(5.0f);
    }

    public void setFollowedButton(ControlInterface controlInterface) {
        View view = this.mFollowedView;
        if (view != null) {
            view.getViewTreeObserver().removeOnPreDrawListener(this.mFollowedViewListener);
        }
        for (ActionButtonInterface buttonInterface : this.actionButtons) {
            buttonInterface.setFollowedView(controlInterface);
            ((View) buttonInterface).setVisibility(buttonInterface.shouldBeVisible() ? 0 : 8);
        }
        setVisibility(0);
        View view2 = (View) controlInterface;
        this.mFollowedView = view2;
        if (view2 != null) {
            view2.getViewTreeObserver().addOnPreDrawListener(this.mFollowedViewListener);
        }
    }

    private float getXPosition(int side) {
        if (side == SIDE_LEFT) {
            return this.mFollowedView.getX() - ((float) getWidth());
        }
        if (side == SIDE_RIGHT) {
            return this.mFollowedView.getX() + ((float) this.mFollowedView.getWidth());
        }
        return (this.mFollowedView.getX() + (((float) this.mFollowedView.getWidth()) / 2.0f)) - (((float) getWidth()) / 2.0f);
    }

    private float getYPosition(int side) {
        if (side == SIDE_TOP) {
            return this.mFollowedView.getY() - ((float) getHeight());
        }
        if (side == SIDE_BOTTOM) {
            return this.mFollowedView.getY() + ((float) this.mFollowedView.getHeight());
        }
        return (this.mFollowedView.getY() + (((float) this.mFollowedView.getHeight()) / 2.0f)) - (((float) getHeight()) / 2.0f);
    }

    /* access modifiers changed from: private */
    public void setNewPosition() {
        if (this.mFollowedView != null) {
            int side = pickSide();
            setX(MathUtils.clamp(getXPosition(side), 0.0f, (float) (Tools.currentDisplayMetrics.widthPixels - getWidth())));
            setY(getYPosition(side));
        }
    }

    private int pickSide() {
        View view = this.mFollowedView;
        if (view == null) {
            return this.mSide;
        }
        int i = this.mSide;
        if (i != SIDE_AUTO) {
            return i;
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent == null) {
            return this.mSide;
        }
        int side = this.mFollowedView.getX() + (((float) getWidth()) / 2.0f) > ((float) parent.getWidth()) / 2.0f ? SIDE_LEFT : SIDE_RIGHT;
        float futurePos = getYPosition(side);
        if (((float) getHeight()) + futurePos > ((float) (parent.getHeight() + (getHeight() / 2)))) {
            return SIDE_TOP;
        }
        if (futurePos < ((float) ((-getHeight()) / 2))) {
            return SIDE_BOTTOM;
        }
        return side;
    }

    public void hide() {
        View view = this.mFollowedView;
        if (view != null) {
            view.getViewTreeObserver().removeOnPreDrawListener(this.mFollowedViewListener);
        }
        setVisibility(8);
    }
}

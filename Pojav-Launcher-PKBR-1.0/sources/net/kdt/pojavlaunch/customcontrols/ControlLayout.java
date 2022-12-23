package net.kdt.pojavlaunch.customcontrols;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import net.kdt.pojavlaunch.CustomControlsActivity;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.customcontrols.buttons.ControlButton;
import net.kdt.pojavlaunch.customcontrols.buttons.ControlDrawer;
import net.kdt.pojavlaunch.customcontrols.buttons.ControlInterface;
import net.kdt.pojavlaunch.customcontrols.buttons.ControlSubButton;
import net.kdt.pojavlaunch.customcontrols.handleview.ActionRow;
import net.kdt.pojavlaunch.customcontrols.handleview.ControlHandleView;
import net.kdt.pojavlaunch.customcontrols.handleview.EditControlPopup;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;

public class ControlLayout extends FrameLayout {
    public ActionRow actionRow = null;
    private CustomControlsActivity mActivity;
    private EditControlPopup mControlPopup = null;
    private boolean mControlVisible = false;
    private ControlHandleView mHandleView;
    protected CustomControls mLayout;
    private boolean mModifiable = false;
    HashMap<View, ControlInterface> mapTable = new HashMap<>();

    public ControlLayout(Context ctx) {
        super(ctx);
    }

    public ControlLayout(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
    }

    public void loadLayout(String jsonPath) throws IOException, JsonSyntaxException {
        CustomControls layout = LayoutConverter.loadAndConvertIfNecessary(jsonPath);
        if (layout != null) {
            loadLayout(layout);
            return;
        }
        throw new IOException("Unsupported control layout version");
    }

    public void loadLayout(CustomControls controlLayout) {
        if (this.actionRow == null) {
            ActionRow actionRow2 = new ActionRow(getContext());
            this.actionRow = actionRow2;
            addView(actionRow2);
        }
        removeAllButtons();
        CustomControls customControls = this.mLayout;
        if (customControls != null) {
            customControls.mControlDataList = null;
            this.mLayout = null;
        }
        System.gc();
        this.mapTable.clear();
        if (controlLayout != null) {
            this.mLayout = controlLayout;
            for (ControlData button : controlLayout.mControlDataList) {
                addControlView(button);
            }
            for (ControlDrawerData drawerData : controlLayout.mDrawerDataList) {
                ControlDrawer drawer = addDrawerView(drawerData);
                if (this.mModifiable) {
                    drawer.areButtonsVisible = true;
                }
            }
            this.mLayout.scaledAt = LauncherPreferences.PREF_BUTTONSIZE;
            setModified(false);
        }
    }

    public void addControlButton(ControlData controlButton) {
        this.mLayout.mControlDataList.add(controlButton);
        addControlView(controlButton);
    }

    private void addControlView(ControlData controlButton) {
        ControlButton view = new ControlButton(this, controlButton);
        if (!this.mModifiable) {
            view.setAlpha(view.getProperties().opacity);
            view.setFocusable(false);
            view.setFocusableInTouchMode(false);
        }
        addView(view);
        setModified(true);
    }

    public void addDrawer(ControlDrawerData drawerData) {
        this.mLayout.mDrawerDataList.add(drawerData);
        addDrawerView();
    }

    private void addDrawerView() {
        addDrawerView((ControlDrawerData) null);
    }

    private ControlDrawer addDrawerView(ControlDrawerData drawerData) {
        ControlDrawer view = new ControlDrawer(this, drawerData == null ? this.mLayout.mDrawerDataList.get(this.mLayout.mDrawerDataList.size() - 1) : drawerData);
        if (!this.mModifiable) {
            view.setAlpha(view.getProperties().opacity);
            view.setFocusable(false);
            view.setFocusableInTouchMode(false);
        }
        addView(view);
        Iterator<ControlData> it = view.getDrawerData().buttonProperties.iterator();
        while (it.hasNext()) {
            addSubView(view, it.next());
        }
        setModified(true);
        return view;
    }

    public void addSubButton(ControlDrawer drawer, ControlData controlButton) {
        drawer.getDrawerData().buttonProperties.add(controlButton);
        addSubView(drawer, drawer.getDrawerData().buttonProperties.get(drawer.getDrawerData().buttonProperties.size() - 1));
    }

    private void addSubView(ControlDrawer drawer, ControlData controlButton) {
        ControlSubButton view = new ControlSubButton(this, controlButton, drawer);
        if (!this.mModifiable) {
            view.setAlpha(view.getProperties().opacity);
            view.setFocusable(false);
            view.setFocusableInTouchMode(false);
        } else {
            view.setVisible(drawer.areButtonsVisible);
        }
        drawer.addButton(view);
        addView(view);
        setModified(true);
    }

    private void removeAllButtons() {
        Iterator<ControlInterface> it = getButtonChildren().iterator();
        while (it.hasNext()) {
            removeView(it.next().getControlView());
        }
        System.gc();
    }

    public void saveLayout(String path) throws Exception {
        this.mLayout.save(path);
        setModified(false);
    }

    public void setActivity(CustomControlsActivity activity) {
        this.mActivity = activity;
    }

    public void toggleControlVisible() {
        boolean z = !this.mControlVisible;
        this.mControlVisible = z;
        setControlVisible(z);
    }

    public float getLayoutScale() {
        return this.mLayout.scaledAt;
    }

    public CustomControls getLayout() {
        return this.mLayout;
    }

    public void setControlVisible(boolean isVisible) {
        if (!this.mModifiable) {
            this.mControlVisible = isVisible;
            Iterator<ControlInterface> it = getButtonChildren().iterator();
            while (it.hasNext()) {
                it.next().setVisible(isVisible);
            }
        }
    }

    public void setModifiable(boolean isModifiable) {
        if (!isModifiable && this.mModifiable) {
            removeEditWindow();
        }
        this.mModifiable = isModifiable;
    }

    public boolean getModifiable() {
        return this.mModifiable;
    }

    public void setModified(boolean isModified) {
        CustomControlsActivity customControlsActivity = this.mActivity;
        if (customControlsActivity != null) {
            customControlsActivity.isModified = isModified;
        }
    }

    public ArrayList<ControlInterface> getButtonChildren() {
        ArrayList<ControlInterface> children = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v instanceof ControlInterface) {
                children.add((ControlInterface) v);
            }
        }
        return children;
    }

    public void refreshControlButtonPositions() {
        Iterator<ControlInterface> it = getButtonChildren().iterator();
        while (it.hasNext()) {
            ControlInterface button = it.next();
            button.setDynamicX(button.getProperties().dynamicX);
            button.setDynamicY(button.getProperties().dynamicY);
        }
    }

    public void onViewRemoved(View child) {
        EditControlPopup editControlPopup;
        super.onViewRemoved(child);
        if ((child instanceof ControlInterface) && (editControlPopup = this.mControlPopup) != null) {
            editControlPopup.disappearColor();
            this.mControlPopup.disappear();
        }
    }

    /* renamed from: editControlButton */
    public void lambda$editControlButton$0$ControlLayout(ControlInterface button) {
        EditControlPopup editControlPopup = this.mControlPopup;
        if (editControlPopup == null) {
            this.mControlPopup = new EditControlPopup(getContext(), this);
            post(new Runnable(button) {
                public final /* synthetic */ ControlInterface f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    ControlLayout.this.lambda$editControlButton$0$ControlLayout(this.f$1);
                }
            });
            return;
        }
        boolean z = true;
        editControlPopup.internalChanges = true;
        this.mControlPopup.setCurrentlyEditedButton(button);
        button.loadEditValues(this.mControlPopup);
        this.mControlPopup.internalChanges = false;
        EditControlPopup editControlPopup2 = this.mControlPopup;
        if (button.getControlView().getX() + (((float) button.getControlView().getWidth()) / 2.0f) >= ((float) Tools.currentDisplayMetrics.widthPixels) / 2.0f) {
            z = false;
        }
        editControlPopup2.appear(z);
        this.mControlPopup.disappearColor();
        if (this.mHandleView == null) {
            ControlHandleView controlHandleView = new ControlHandleView(getContext());
            this.mHandleView = controlHandleView;
            addView(controlHandleView);
        }
        this.mHandleView.setControlButton(button);
    }

    public void adaptPanelPosition() {
        EditControlPopup editControlPopup = this.mControlPopup;
        if (editControlPopup != null) {
            editControlPopup.adaptPanelPosition();
        }
    }

    public boolean onTouch(View v, MotionEvent ev) {
        ControlInterface lastControlButton = this.mapTable.get(v);
        if (ev.getActionMasked() == 1 || ev.getActionMasked() == 3) {
            if (lastControlButton != null) {
                lastControlButton.sendKeyPresses(false);
            }
            this.mapTable.put(v, (Object) null);
            return true;
        } else if (ev.getActionMasked() != 2) {
            return false;
        } else {
            if (lastControlButton != null && ev.getRawX() > lastControlButton.getControlView().getX() && ev.getRawX() < lastControlButton.getControlView().getX() + ((float) lastControlButton.getControlView().getWidth()) && ev.getRawY() > lastControlButton.getControlView().getY() && ev.getRawY() < lastControlButton.getControlView().getY() + ((float) lastControlButton.getControlView().getHeight())) {
                return true;
            }
            if (lastControlButton != null) {
                lastControlButton.sendKeyPresses(false);
            }
            this.mapTable.put(v, (Object) null);
            Iterator<ControlInterface> it = getButtonChildren().iterator();
            while (it.hasNext()) {
                ControlInterface button = it.next();
                if (button.getProperties().isSwipeable && ev.getRawX() > button.getControlView().getX() && ev.getRawX() < button.getControlView().getX() + ((float) button.getControlView().getWidth()) && ev.getRawY() > button.getControlView().getY() && ev.getRawY() < button.getControlView().getY() + ((float) button.getControlView().getHeight())) {
                    if (!button.equals(lastControlButton)) {
                        button.sendKeyPresses(true);
                        this.mapTable.put(v, button);
                    }
                    return true;
                }
            }
            return false;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if ((!this.mModifiable || event.getActionMasked() == 1) && this.mControlPopup != null && !((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(getWindowToken(), 0) && this.mControlPopup.disappearLayer()) {
            this.actionRow.setFollowedButton((ControlInterface) null);
            this.mHandleView.hide();
        }
        return true;
    }

    public void removeEditWindow() {
        ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(getWindowToken(), 0);
        EditControlPopup editControlPopup = this.mControlPopup;
        if (editControlPopup != null) {
            editControlPopup.disappearColor();
            this.mControlPopup.disappear();
        }
        ActionRow actionRow2 = this.actionRow;
        if (actionRow2 != null) {
            actionRow2.setFollowedButton((ControlInterface) null);
        }
        ControlHandleView controlHandleView = this.mHandleView;
        if (controlHandleView != null) {
            controlHandleView.hide();
        }
    }

    public void save(String path) {
        try {
            this.mLayout.save(path);
        } catch (IOException e) {
            Log.e("ControlLayout", "Failed to save the layout at:" + path);
        }
    }
}

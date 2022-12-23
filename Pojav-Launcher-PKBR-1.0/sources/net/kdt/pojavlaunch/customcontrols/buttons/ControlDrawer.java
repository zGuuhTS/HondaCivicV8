package net.kdt.pojavlaunch.customcontrols.buttons;

import android.view.MotionEvent;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.customcontrols.ControlData;
import net.kdt.pojavlaunch.customcontrols.ControlDrawerData;
import net.kdt.pojavlaunch.customcontrols.ControlLayout;
import net.kdt.pojavlaunch.customcontrols.handleview.EditControlPopup;

public class ControlDrawer extends ControlButton {
    public boolean areButtonsVisible;
    public ArrayList<ControlSubButton> buttons;
    public ControlDrawerData drawerData;
    public ControlLayout parentLayout;

    public ControlDrawer(ControlLayout layout, ControlDrawerData drawerData2) {
        super(layout, drawerData2.properties);
        this.buttons = new ArrayList<>(drawerData2.buttonProperties.size());
        this.parentLayout = layout;
        this.drawerData = drawerData2;
        this.areButtonsVisible = layout.getModifiable();
    }

    public void addButton(ControlData properties) {
        addButton(new ControlSubButton(this.parentLayout, properties, this));
    }

    public void addButton(ControlSubButton button) {
        this.buttons.add(button);
        setControlButtonVisibility(button, this.areButtonsVisible);
        syncButtons();
    }

    private void setControlButtonVisibility(ControlButton button, boolean isVisible) {
        button.setVisible(isVisible);
    }

    private void switchButtonVisibility() {
        this.areButtonsVisible = !this.areButtonsVisible;
        Iterator<ControlSubButton> it = this.buttons.iterator();
        while (it.hasNext()) {
            it.next().setVisible(this.areButtonsVisible);
        }
    }

    private void alignButtons() {
        if (this.buttons != null && this.drawerData.orientation != ControlDrawerData.Orientation.FREE) {
            for (int i = 0; i < this.buttons.size(); i++) {
                switch (C01541.f15xb0ef5a08[this.drawerData.orientation.ordinal()]) {
                    case 1:
                        this.buttons.get(i).setDynamicX(generateDynamicX(getX() + ((this.drawerData.properties.getWidth() + Tools.dpToPx(2.0f)) * ((float) (i + 1)))));
                        this.buttons.get(i).setDynamicY(generateDynamicY(getY()));
                        break;
                    case 2:
                        this.buttons.get(i).setDynamicX(generateDynamicX(getX() - ((this.drawerData.properties.getWidth() + Tools.dpToPx(2.0f)) * ((float) (i + 1)))));
                        this.buttons.get(i).setDynamicY(generateDynamicY(getY()));
                        break;
                    case 3:
                        this.buttons.get(i).setDynamicY(generateDynamicY(getY() - ((this.drawerData.properties.getHeight() + Tools.dpToPx(2.0f)) * ((float) (i + 1)))));
                        this.buttons.get(i).setDynamicX(generateDynamicX(getX()));
                        break;
                    case 4:
                        this.buttons.get(i).setDynamicY(generateDynamicY(getY() + ((this.drawerData.properties.getHeight() + Tools.dpToPx(2.0f)) * ((float) (i + 1)))));
                        this.buttons.get(i).setDynamicX(generateDynamicX(getX()));
                        break;
                }
                this.buttons.get(i).updateProperties();
            }
        }
    }

    /* renamed from: net.kdt.pojavlaunch.customcontrols.buttons.ControlDrawer$1 */
    static /* synthetic */ class C01541 {

        /* renamed from: $SwitchMap$net$kdt$pojavlaunch$customcontrols$ControlDrawerData$Orientation */
        static final /* synthetic */ int[] f15xb0ef5a08;

        static {
            int[] iArr = new int[ControlDrawerData.Orientation.values().length];
            f15xb0ef5a08 = iArr;
            try {
                iArr[ControlDrawerData.Orientation.RIGHT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f15xb0ef5a08[ControlDrawerData.Orientation.LEFT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f15xb0ef5a08[ControlDrawerData.Orientation.UP.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f15xb0ef5a08[ControlDrawerData.Orientation.DOWN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private void resizeButtons() {
        ArrayList<ControlSubButton> arrayList = this.buttons;
        if (arrayList != null) {
            Iterator<ControlSubButton> it = arrayList.iterator();
            while (it.hasNext()) {
                ControlSubButton subButton = it.next();
                subButton.mProperties.setWidth(this.mProperties.getWidth());
                subButton.mProperties.setHeight(this.mProperties.getHeight());
                subButton.updateProperties();
            }
        }
    }

    public void syncButtons() {
        alignButtons();
        resizeButtons();
    }

    public boolean containsChild(ControlInterface button) {
        Iterator<ControlSubButton> it = this.buttons.iterator();
        while (it.hasNext()) {
            if (it.next() == button) {
                return true;
            }
        }
        return false;
    }

    public ControlData preProcessProperties(ControlData properties, ControlLayout layout) {
        ControlData data = super.preProcessProperties(properties, layout);
        data.isHideable = true;
        return data;
    }

    public void setVisible(boolean isVisible) {
        setVisibility(isVisible ? 0 : 8);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (getControlLayoutParent().getModifiable()) {
            return super.onTouchEvent(event);
        }
        switch (event.getActionMasked()) {
            case 1:
            case 6:
                switchButtonVisibility();
                return true;
            default:
                return true;
        }
    }

    public void setX(float x) {
        super.setX(x);
        alignButtons();
    }

    public void setY(float y) {
        super.setY(y);
        alignButtons();
    }

    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        syncButtons();
    }

    public boolean canSnap(ControlInterface button) {
        return super.canSnap(button) && !containsChild(button);
    }

    public ControlDrawerData getDrawerData() {
        return this.drawerData;
    }

    public void loadEditValues(EditControlPopup editControlPopup) {
        editControlPopup.loadValues(this.drawerData);
    }

    public void cloneButton() {
        ControlDrawerData cloneData = new ControlDrawerData(getDrawerData());
        cloneData.properties.dynamicX = "0.5 * ${screen_width}";
        cloneData.properties.dynamicY = "0.5 * ${screen_height}";
        ((ControlLayout) getParent()).addDrawer(cloneData);
    }

    public void removeButton() {
        ControlLayout layout = getControlLayoutParent();
        Iterator<ControlSubButton> it = this.buttons.iterator();
        while (it.hasNext()) {
            layout.removeView(it.next());
        }
        layout.getLayout().mDrawerDataList.remove(getDrawerData());
        layout.removeView(this);
    }
}

package net.kdt.pojavlaunch.customcontrols.buttons;

import android.view.MotionEvent;
import android.view.ViewGroup;
import net.kdt.pojavlaunch.customcontrols.ControlData;
import net.kdt.pojavlaunch.customcontrols.ControlDrawerData;
import net.kdt.pojavlaunch.customcontrols.ControlLayout;

public class ControlSubButton extends ControlButton {
    public ControlDrawer parentDrawer;

    public ControlSubButton(ControlLayout layout, ControlData properties, ControlDrawer parentDrawer2) {
        super(layout, properties);
        this.parentDrawer = parentDrawer2;
        filterProperties();
    }

    private void filterProperties() {
        this.mProperties.setHeight(this.parentDrawer.getProperties().getHeight());
        this.mProperties.setWidth(this.parentDrawer.getProperties().getWidth());
        this.mProperties.isDynamicBtn = false;
        setProperties(this.mProperties, false);
    }

    public void setVisible(boolean isVisible) {
        int i = 0;
        if (!isVisible ? this.mProperties.isHideable || this.parentDrawer.getVisibility() != 8 : !this.parentDrawer.areButtonsVisible) {
            i = 8;
        }
        setVisibility(i);
    }

    public void setLayoutParams(ViewGroup.LayoutParams params) {
        ControlDrawer controlDrawer = this.parentDrawer;
        if (controlDrawer != null) {
            params.width = (int) controlDrawer.mProperties.getWidth();
            params.height = (int) this.parentDrawer.mProperties.getHeight();
        }
        super.setLayoutParams(params);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!getControlLayoutParent().getModifiable() || this.parentDrawer.drawerData.orientation == ControlDrawerData.Orientation.FREE) {
            return super.onTouchEvent(event);
        }
        if (event.getActionMasked() == 1) {
            onLongClick(this);
        }
        return true;
    }

    public void cloneButton() {
        ControlData cloneData = new ControlData(getProperties());
        cloneData.dynamicX = "0.5 * ${screen_width}";
        cloneData.dynamicY = "0.5 * ${screen_height}";
        ((ControlLayout) getParent()).addSubButton(this.parentDrawer, cloneData);
    }

    public void removeButton() {
        this.parentDrawer.drawerData.buttonProperties.remove(getProperties());
        this.parentDrawer.drawerData.buttonProperties.remove(getProperties());
        this.parentDrawer.buttons.remove(this);
        this.parentDrawer.syncButtons();
        super.removeButton();
    }

    public void snapAndAlign(float x, float y) {
        if (this.parentDrawer.drawerData.orientation == ControlDrawerData.Orientation.FREE) {
            super.snapAndAlign(x, y);
        }
    }
}

package net.kdt.pojavlaunch.customcontrols.buttons;

import android.graphics.drawable.GradientDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.customcontrols.ControlData;
import net.kdt.pojavlaunch.customcontrols.ControlLayout;
import net.kdt.pojavlaunch.customcontrols.handleview.EditControlPopup;
import net.kdt.pojavlaunch.prefs.LauncherPreferences;
import net.kdt.pojavlaunch.utils.MathUtils;
import org.lwjgl.glfw.CallbackBridge;

public interface ControlInterface extends View.OnLongClickListener {
    void cloneButton();

    View getControlView();

    ControlData getProperties();

    void loadEditValues(EditControlPopup editControlPopup);

    void removeButton();

    void sendKeyPresses(boolean z);

    void setVisible(boolean z);

    ControlLayout getControlLayoutParent() {
        return (ControlLayout) getControlView().getParent();
    }

    ControlData preProcessProperties(ControlData properties, ControlLayout layout) {
        properties.setWidth((properties.getWidth() / layout.getLayoutScale()) * LauncherPreferences.PREF_BUTTONSIZE);
        properties.setHeight((properties.getHeight() / layout.getLayoutScale()) * LauncherPreferences.PREF_BUTTONSIZE);
        properties.isHideable = !properties.containsKeycode(-2) && !properties.containsKeycode(-5);
        return properties;
    }

    void updateProperties() {
        setProperties(getProperties());
    }

    void setProperties(ControlData properties) {
        setProperties(properties, true);
    }

    void setProperties(ControlData properties, boolean changePos) {
        if (changePos) {
            getControlView().setX(properties.insertDynamicPos(getProperties().dynamicX));
            getControlView().setY(properties.insertDynamicPos(getProperties().dynamicY));
        }
        ViewGroup.LayoutParams params = getControlView().getLayoutParams();
        if (params == null) {
            params = new FrameLayout.LayoutParams((int) properties.getWidth(), (int) properties.getHeight());
        }
        params.width = (int) properties.getWidth();
        params.height = (int) properties.getHeight();
        getControlView().setLayoutParams(params);
    }

    void setBackground() {
        GradientDrawable gd = getControlView().getBackground() instanceof GradientDrawable ? (GradientDrawable) getControlView().getBackground() : new GradientDrawable();
        gd.setColor(getProperties().bgColor);
        gd.setStroke(computeStrokeWidth((float) getProperties().strokeWidth), getProperties().strokeColor);
        gd.setCornerRadius(computeCornerRadius(getProperties().cornerRadius));
        getControlView().setBackground(gd);
    }

    void setDynamicX(String dynamicX) {
        getProperties().dynamicX = dynamicX;
        getControlView().setX(getProperties().insertDynamicPos(dynamicX));
    }

    void setDynamicY(String dynamicY) {
        getProperties().dynamicY = dynamicY;
        getControlView().setY(getProperties().insertDynamicPos(dynamicY));
    }

    String generateDynamicX(float x) {
        if ((getProperties().getWidth() / 2.0f) + x > ((float) CallbackBridge.physicalWidth) / 2.0f) {
            return ((getProperties().getWidth() + x) / ((float) CallbackBridge.physicalWidth)) + " * ${screen_width} - ${width}";
        }
        return (x / ((float) CallbackBridge.physicalWidth)) + " * ${screen_width}";
    }

    String generateDynamicY(float y) {
        if ((getProperties().getHeight() / 2.0f) + y > ((float) CallbackBridge.physicalHeight) / 2.0f) {
            return ((getProperties().getHeight() + y) / ((float) CallbackBridge.physicalHeight)) + " * ${screen_height} - ${height}";
        }
        return (y / ((float) CallbackBridge.physicalHeight)) + " * ${screen_height}";
    }

    void regenerateDynamicCoordinates() {
        getProperties().dynamicX = generateDynamicX(getControlView().getX());
        getProperties().dynamicY = generateDynamicY(getControlView().getY());
        updateProperties();
    }

    String applySize(String equation, ControlInterface button) {
        return equation.replace("${right}", "(${screen_width} - ${width})").replace("${bottom}", "(${screen_height} - ${height})").replace("${height}", "(px(" + Tools.pxToDp(button.getProperties().getHeight()) + ") /" + LauncherPreferences.PREF_BUTTONSIZE + " * ${preferred_scale})").replace("${width}", "(px(" + Tools.pxToDp(button.getProperties().getWidth()) + ") / " + LauncherPreferences.PREF_BUTTONSIZE + " * ${preferred_scale})");
    }

    int computeStrokeWidth(float widthInPercent) {
        return (int) ((Math.max(getProperties().getWidth(), getProperties().getHeight()) / 2.0f) * (widthInPercent / 100.0f));
    }

    float computeCornerRadius(float radiusInPercent) {
        return (Math.min(getProperties().getWidth(), getProperties().getHeight()) / 2.0f) * (radiusInPercent / 100.0f);
    }

    boolean canSnap(ControlInterface button) {
        float MIN_DISTANCE = Tools.dpToPx(8.0f);
        if (button != this && MathUtils.dist(button.getControlView().getX() + (((float) button.getControlView().getWidth()) / 2.0f), button.getControlView().getY() + (((float) button.getControlView().getHeight()) / 2.0f), getControlView().getX() + (((float) getControlView().getWidth()) / 2.0f), getControlView().getY() + (((float) getControlView().getHeight()) / 2.0f)) <= Math.max((((float) button.getControlView().getWidth()) / 2.0f) + (((float) getControlView().getWidth()) / 2.0f), (((float) button.getControlView().getHeight()) / 2.0f) + (((float) getControlView().getHeight()) / 2.0f)) + MIN_DISTANCE) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x016f  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x018e  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x01d3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void snapAndAlign(float r20, float r21) {
        /*
            r19 = this;
            r0 = r19
            r1 = r21
            r2 = 1090519040(0x41000000, float:8.0)
            float r2 = net.kdt.pojavlaunch.Tools.dpToPx(r2)
            java.lang.String r3 = r19.generateDynamicX(r20)
            java.lang.String r4 = r0.generateDynamicY(r1)
            android.view.View r5 = r19.getControlView()
            r6 = r20
            r5.setX(r6)
            android.view.View r5 = r19.getControlView()
            r5.setY(r1)
            android.view.View r5 = r19.getControlView()
            android.view.ViewParent r5 = r5.getParent()
            net.kdt.pojavlaunch.customcontrols.ControlLayout r5 = (net.kdt.pojavlaunch.customcontrols.ControlLayout) r5
            java.util.ArrayList r5 = r5.getButtonChildren()
            java.util.Iterator r5 = r5.iterator()
        L_0x0034:
            boolean r7 = r5.hasNext()
            if (r7 == 0) goto L_0x0224
            java.lang.Object r7 = r5.next()
            net.kdt.pojavlaunch.customcontrols.buttons.ControlInterface r7 = (net.kdt.pojavlaunch.customcontrols.buttons.ControlInterface) r7
            boolean r8 = r0.canSnap(r7)
            if (r8 != 0) goto L_0x0047
            goto L_0x0034
        L_0x0047:
            android.view.View r8 = r7.getControlView()
            float r8 = r8.getY()
            android.view.View r9 = r7.getControlView()
            int r9 = r9.getHeight()
            float r9 = (float) r9
            float r9 = r9 + r8
            android.view.View r10 = r7.getControlView()
            float r10 = r10.getX()
            android.view.View r11 = r7.getControlView()
            int r11 = r11.getWidth()
            float r11 = (float) r11
            float r11 = r11 + r10
            android.view.View r12 = r19.getControlView()
            float r12 = r12.getY()
            android.view.View r13 = r19.getControlView()
            float r13 = r13.getY()
            android.view.View r14 = r19.getControlView()
            int r14 = r14.getHeight()
            float r14 = (float) r14
            float r13 = r13 + r14
            android.view.View r14 = r19.getControlView()
            float r14 = r14.getX()
            android.view.View r15 = r19.getControlView()
            float r15 = r15.getX()
            android.view.View r16 = r19.getControlView()
            int r1 = r16.getWidth()
            float r1 = (float) r1
            float r15 = r15 + r1
            float r1 = r12 - r9
            float r1 = java.lang.Math.abs(r1)
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            r16 = r5
            java.lang.String r5 = " + ${margin}"
            java.lang.String r6 = " + ${height}"
            if (r1 >= 0) goto L_0x00d5
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r17 = r4
            net.kdt.pojavlaunch.customcontrols.ControlData r4 = r7.getProperties()
            java.lang.String r4 = r4.dynamicY
            java.lang.String r4 = r0.applySize(r4, r7)
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.String r4 = r0.applySize(r6, r7)
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.StringBuilder r1 = r1.append(r5)
            java.lang.String r4 = r1.toString()
            goto L_0x0101
        L_0x00d5:
            r17 = r4
            float r1 = r8 - r13
            float r1 = java.lang.Math.abs(r1)
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 >= 0) goto L_0x00ff
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            net.kdt.pojavlaunch.customcontrols.ControlData r4 = r7.getProperties()
            java.lang.String r4 = r4.dynamicY
            java.lang.String r4 = r0.applySize(r4, r7)
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.String r4 = " - ${height} - ${margin}"
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.String r4 = r1.toString()
            goto L_0x0101
        L_0x00ff:
            r4 = r17
        L_0x0101:
            android.view.View r1 = r19.getControlView()
            float r1 = r1.getY()
            java.lang.String r1 = r0.generateDynamicY(r1)
            boolean r1 = r4.equals(r1)
            r17 = r4
            java.lang.String r4 = " + ${width}"
            if (r1 != 0) goto L_0x0161
            float r1 = r10 - r14
            float r1 = java.lang.Math.abs(r1)
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 >= 0) goto L_0x012c
            net.kdt.pojavlaunch.customcontrols.ControlData r1 = r7.getProperties()
            java.lang.String r1 = r1.dynamicX
            java.lang.String r3 = r0.applySize(r1, r7)
            goto L_0x0165
        L_0x012c:
            float r1 = r11 - r15
            float r1 = java.lang.Math.abs(r1)
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 >= 0) goto L_0x015e
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r18 = r3
            net.kdt.pojavlaunch.customcontrols.ControlData r3 = r7.getProperties()
            java.lang.String r3 = r3.dynamicX
            java.lang.String r3 = r0.applySize(r3, r7)
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r3 = r0.applySize(r4, r7)
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r3 = " - ${width}"
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r3 = r1.toString()
            goto L_0x0165
        L_0x015e:
            r18 = r3
            goto L_0x0163
        L_0x0161:
            r18 = r3
        L_0x0163:
            r3 = r18
        L_0x0165:
            float r1 = r10 - r15
            float r1 = java.lang.Math.abs(r1)
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 >= 0) goto L_0x018e
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            net.kdt.pojavlaunch.customcontrols.ControlData r4 = r7.getProperties()
            java.lang.String r4 = r4.dynamicX
            java.lang.String r4 = r0.applySize(r4, r7)
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.String r4 = " - ${width} - ${margin}"
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.String r1 = r1.toString()
            r3 = r1
            goto L_0x01c1
        L_0x018e:
            float r1 = r14 - r11
            float r1 = java.lang.Math.abs(r1)
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 >= 0) goto L_0x01bf
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r18 = r3
            net.kdt.pojavlaunch.customcontrols.ControlData r3 = r7.getProperties()
            java.lang.String r3 = r3.dynamicX
            java.lang.String r3 = r0.applySize(r3, r7)
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r3 = r0.applySize(r4, r7)
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.StringBuilder r1 = r1.append(r5)
            java.lang.String r1 = r1.toString()
            r3 = r1
            goto L_0x01c1
        L_0x01bf:
            r18 = r3
        L_0x01c1:
            android.view.View r1 = r19.getControlView()
            float r1 = r1.getX()
            java.lang.String r1 = r0.generateDynamicX(r1)
            boolean r1 = r3.equals(r1)
            if (r1 != 0) goto L_0x021a
            float r1 = r8 - r12
            float r1 = java.lang.Math.abs(r1)
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 >= 0) goto L_0x01e9
            net.kdt.pojavlaunch.customcontrols.ControlData r1 = r7.getProperties()
            java.lang.String r1 = r1.dynamicY
            java.lang.String r1 = r0.applySize(r1, r7)
            r4 = r1
            goto L_0x021c
        L_0x01e9:
            float r1 = r9 - r13
            float r1 = java.lang.Math.abs(r1)
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 >= 0) goto L_0x021a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            net.kdt.pojavlaunch.customcontrols.ControlData r4 = r7.getProperties()
            java.lang.String r4 = r4.dynamicY
            java.lang.String r4 = r0.applySize(r4, r7)
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.String r4 = r0.applySize(r6, r7)
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.String r4 = " - ${height}"
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.String r1 = r1.toString()
            r4 = r1
            goto L_0x021c
        L_0x021a:
            r4 = r17
        L_0x021c:
            r6 = r20
            r1 = r21
            r5 = r16
            goto L_0x0034
        L_0x0224:
            r0.setDynamicX(r3)
            r0.setDynamicY(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: net.kdt.pojavlaunch.customcontrols.buttons.ControlInterface.snapAndAlign(float, float):void");
    }

    void injectBehaviors() {
        injectProperties();
        injectTouchEventBehavior();
        injectLayoutParamBehavior();
    }

    void injectProperties() {
        getControlView().setTranslationZ(10.0f);
    }

    void injectTouchEventBehavior() {
        getControlView().setOnTouchListener(new View.OnTouchListener() {
            private float downRawX;
            private float downRawY;
            private float downX;
            private float downY;
            private boolean mCanTriggerLongClick = true;
            private boolean mIsPointerOutOfBounds = false;

            public boolean onTouch(View view, MotionEvent event) {
                if (!ControlInterface.this.getControlLayoutParent().getModifiable()) {
                    view.onTouchEvent(event);
                    return true;
                }
                if (event.getActionMasked() == 1 && this.mCanTriggerLongClick) {
                    ControlInterface.this.onLongClick(view);
                }
                switch (event.getActionMasked()) {
                    case 0:
                        this.mCanTriggerLongClick = true;
                        this.downRawX = event.getRawX();
                        this.downRawY = event.getRawY();
                        this.downX = this.downRawX - view.getX();
                        this.downY = this.downRawY - view.getY();
                        break;
                    case 2:
                        if (Math.abs(event.getRawX() - this.downRawX) > 8.0f || Math.abs(event.getRawY() - this.downRawY) > 8.0f) {
                            this.mCanTriggerLongClick = false;
                        }
                        ControlInterface.this.getControlLayoutParent().adaptPanelPosition();
                        if (!ControlInterface.this.getProperties().isDynamicBtn) {
                            ControlInterface.this.snapAndAlign(androidx.core.math.MathUtils.clamp(event.getRawX() - this.downX, 0.0f, (float) (CallbackBridge.physicalWidth - view.getWidth())), androidx.core.math.MathUtils.clamp(event.getRawY() - this.downY, 0.0f, (float) (CallbackBridge.physicalHeight - view.getHeight())));
                            break;
                        }
                        break;
                }
                return true;
            }
        });
    }

    void injectLayoutParamBehavior() {
        getControlView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                ControlInterface.lambda$injectLayoutParamBehavior$0(ControlInterface.this, view, i, i2, i3, i4, i5, i6, i7, i8);
            }
        });
    }

    static /* synthetic */ void lambda$injectLayoutParamBehavior$0(ControlInterface _this, View v, int left, int top2, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        _this.getProperties().setWidth((float) (right - left));
        _this.getProperties().setHeight((float) (bottom - top2));
        _this.setBackground();
        if (!_this.getProperties().isDynamicBtn) {
            _this.getControlView().setX(_this.getControlView().getX());
            _this.getControlView().setY(_this.getControlView().getY());
            return;
        }
        _this.getControlView().setX(_this.getProperties().insertDynamicPos(_this.getProperties().dynamicX));
        _this.getControlView().setY(_this.getProperties().insertDynamicPos(_this.getProperties().dynamicY));
    }

    boolean onLongClick(View v) {
        if (!getControlLayoutParent().getModifiable()) {
            return true;
        }
        getControlLayoutParent().lambda$editControlButton$0$ControlLayout(this);
        getControlLayoutParent().actionRow.setFollowedButton(this);
        return true;
    }
}

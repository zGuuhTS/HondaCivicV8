package net.kdt.pojavlaunch.customcontrols;

import android.content.Context;
import android.util.ArrayMap;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.utils.JSONUtils;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import org.lwjgl.glfw.CallbackBridge;

public class ControlData {
    public static final int SPECIALBTN_KEYBOARD = -1;
    public static final int SPECIALBTN_MOUSEMID = -6;
    public static final int SPECIALBTN_MOUSEPRI = -3;
    public static final int SPECIALBTN_MOUSESEC = -4;
    public static final int SPECIALBTN_SCROLLDOWN = -8;
    public static final int SPECIALBTN_SCROLLUP = -7;
    public static final int SPECIALBTN_TOGGLECTRL = -2;
    public static final int SPECIALBTN_VIRTUALMOUSE = -5;
    private static ControlData[] SPECIAL_BUTTONS;
    private static String[] SPECIAL_BUTTON_NAME_ARRAY;
    private static WeakReference<ExpressionBuilder> builder = new WeakReference<>((Object) null);
    private static WeakReference<ArrayMap<String, String>> conversionMap = new WeakReference<>((Object) null);
    public int bgColor;
    public float cornerRadius;
    public String dynamicX;
    public String dynamicY;
    private float height;
    public boolean isDynamicBtn;
    public boolean isHideable;
    public boolean isSwipeable;
    public boolean isToggle;
    public int[] keycodes;
    public String name;
    public float opacity;
    public boolean passThruEnabled;
    public int strokeColor;
    public int strokeWidth;
    private float width;

    static {
        buildExpressionBuilder();
        buildConversionMap();
    }

    public static ControlData[] getSpecialButtons() {
        if (SPECIAL_BUTTONS == null) {
            SPECIAL_BUTTONS = new ControlData[]{new ControlData("Keyboard", new int[]{-1}, "${margin} * 3 + ${width} * 2", "${margin}", false), new ControlData("GUI", new int[]{-2}, "${margin}", "${bottom} - ${margin}"), new ControlData("PRI", new int[]{-3}, "${margin}", "${screen_height} - ${margin} * 3 - ${height} * 3"), new ControlData("SEC", new int[]{-4}, "${margin} * 3 + ${width} * 2", "${screen_height} - ${margin} * 3 - ${height} * 3"), new ControlData("Mouse", new int[]{-5}, "${right}", "${margin}", false), new ControlData("MID", new int[]{-6}, "${margin}", "${margin}"), new ControlData("SCROLLUP", new int[]{-7}, "${margin}", "${margin}"), new ControlData("SCROLLDOWN", new int[]{-8}, "${margin}", "${margin}")};
        }
        return SPECIAL_BUTTONS;
    }

    public static String[] buildSpecialButtonArray() {
        if (SPECIAL_BUTTON_NAME_ARRAY == null) {
            List<String> nameList = new ArrayList<>();
            for (ControlData btn : getSpecialButtons()) {
                nameList.add(btn.name);
            }
            SPECIAL_BUTTON_NAME_ARRAY = (String[]) nameList.toArray(new String[0]);
        }
        return SPECIAL_BUTTON_NAME_ARRAY;
    }

    public ControlData() {
        this("button");
    }

    public ControlData(String name2) {
        this(name2, new int[0]);
    }

    public ControlData(String name2, int[] keycodes2) {
        this(name2, keycodes2, (float) (Tools.currentDisplayMetrics.widthPixels / 2), (float) (Tools.currentDisplayMetrics.heightPixels / 2));
    }

    public ControlData(String name2, int[] keycodes2, float x, float y) {
        this(name2, keycodes2, x, y, 50.0f, 50.0f);
    }

    public ControlData(Context ctx, int resId, int[] keycodes2, float x, float y, boolean isSquare) {
        this(ctx.getResources().getString(resId), keycodes2, x, y, isSquare);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ControlData(String name2, int[] keycodes2, float x, float y, boolean isSquare) {
        this(name2, keycodes2, x, y, isSquare ? 50.0f : 80.0f, !isSquare ? 30.0f : 50.0f);
    }

    public ControlData(String name2, int[] keycodes2, float x, float y, float width2, float height2) {
        this(name2, keycodes2, Float.toString(x), Float.toString(y), width2, height2, false);
        this.isDynamicBtn = false;
    }

    public ControlData(String name2, int[] keycodes2, String dynamicX2, String dynamicY2) {
        this(name2, keycodes2, dynamicX2, dynamicY2, 50.0f, 50.0f, false);
    }

    public ControlData(Context ctx, int resId, int[] keycodes2, String dynamicX2, String dynamicY2, boolean isSquare) {
        this(ctx.getResources().getString(resId), keycodes2, dynamicX2, dynamicY2, isSquare);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ControlData(String name2, int[] keycodes2, String dynamicX2, String dynamicY2, boolean isSquare) {
        this(name2, keycodes2, dynamicX2, dynamicY2, isSquare ? 50.0f : 80.0f, !isSquare ? 30.0f : 50.0f, false);
    }

    public ControlData(String name2, int[] keycodes2, String dynamicX2, String dynamicY2, float width2, float height2, boolean isToggle2) {
        this(name2, keycodes2, dynamicX2, dynamicY2, width2, height2, isToggle2, 1.0f, 1291845632, -1, 0, 0.0f);
    }

    public ControlData(String name2, int[] keycodes2, String dynamicX2, String dynamicY2, float width2, float height2, boolean isToggle2, float opacity2, int bgColor2, int strokeColor2, int strokeWidth2, float cornerRadius2) {
        this.name = name2;
        this.keycodes = inflateKeycodeArray(keycodes2);
        this.dynamicX = dynamicX2;
        this.dynamicY = dynamicY2;
        this.width = width2;
        this.height = height2;
        this.isDynamicBtn = false;
        this.isToggle = isToggle2;
        this.opacity = opacity2;
        this.bgColor = bgColor2;
        this.strokeColor = strokeColor2;
        this.strokeWidth = strokeWidth2;
        this.cornerRadius = cornerRadius2;
    }

    public ControlData(ControlData controlData) {
        this(controlData.name, controlData.keycodes, controlData.dynamicX, controlData.dynamicY, controlData.width, controlData.height, controlData.isToggle, controlData.opacity, controlData.bgColor, controlData.strokeColor, controlData.strokeWidth, controlData.cornerRadius);
    }

    public void execute(boolean isDown) {
        for (int keycode : this.keycodes) {
            CallbackBridge.sendKeyPress(keycode, 0, isDown);
        }
    }

    public float insertDynamicPos(String dynamicPos) {
        return calculate(JSONUtils.insertSingleJSONValue(dynamicPos, fillConversionMap()));
    }

    private static float calculate(String math) {
        setExpression(math);
        return (float) ((ExpressionBuilder) builder.get()).build().evaluate();
    }

    private static int[] inflateKeycodeArray(int[] keycodes2) {
        int[] inflatedArray = {0, 0, 0, 0};
        System.arraycopy(keycodes2, 0, inflatedArray, 0, keycodes2.length);
        return inflatedArray;
    }

    public boolean containsKeycode(int keycodeToCheck) {
        for (int keycode : this.keycodes) {
            if (keycodeToCheck == keycode) {
                return true;
            }
        }
        return false;
    }

    public float getWidth() {
        return Tools.dpToPx(this.width);
    }

    public float getHeight() {
        return Tools.dpToPx(this.height);
    }

    public void setWidth(float widthInPx) {
        this.width = Tools.pxToDp(widthInPx);
    }

    public void setHeight(float heightInPx) {
        this.height = Tools.pxToDp(heightInPx);
    }

    private static void buildExpressionBuilder() {
        builder = new WeakReference<>(new ExpressionBuilder("1 + 1").function(new Function("dp", 1) {
            public double apply(double... args) {
                return (double) Tools.pxToDp((float) args[0]);
            }
        }).function(new Function("px", 1) {
            public double apply(double... args) {
                return (double) Tools.dpToPx((float) args[0]);
            }
        }));
    }

    private static void setExpression(String stringExpression) {
        if (builder.get() == null) {
            buildExpressionBuilder();
        }
        ((ExpressionBuilder) builder.get()).expression(stringExpression);
    }

    private static void buildConversionMap() {
        ArrayMap<String, String> keyValueMap = new ArrayMap<>(10);
        keyValueMap.put("top", "0");
        keyValueMap.put("left", "0");
        keyValueMap.put("right", "DUMMY_RIGHT");
        keyValueMap.put("bottom", "DUMMY_BOTTOM");
        keyValueMap.put("width", "DUMMY_WIDTH");
        keyValueMap.put("height", "DUMMY_HEIGHT");
        keyValueMap.put("screen_width", "DUMMY_DATA");
        keyValueMap.put("screen_height", "DUMMY_DATA");
        keyValueMap.put("margin", Integer.toString((int) Tools.dpToPx(2.0f)));
        keyValueMap.put("preferred_scale", "DUMMY_DATA");
        conversionMap = new WeakReference<>(keyValueMap);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: android.util.ArrayMap} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Map<java.lang.String, java.lang.String> fillConversionMap() {
        /*
            r3 = this;
            java.lang.ref.WeakReference<android.util.ArrayMap<java.lang.String, java.lang.String>> r0 = conversionMap
            java.lang.Object r0 = r0.get()
            android.util.ArrayMap r0 = (android.util.ArrayMap) r0
            if (r0 != 0) goto L_0x0016
            buildConversionMap()
            java.lang.ref.WeakReference<android.util.ArrayMap<java.lang.String, java.lang.String>> r1 = conversionMap
            java.lang.Object r1 = r1.get()
            r0 = r1
            android.util.ArrayMap r0 = (android.util.ArrayMap) r0
        L_0x0016:
            int r1 = org.lwjgl.glfw.CallbackBridge.physicalWidth
            float r1 = (float) r1
            float r2 = r3.getWidth()
            float r1 = r1 - r2
            java.lang.String r1 = java.lang.Float.toString(r1)
            java.lang.String r2 = "right"
            r0.put(r2, r1)
            int r1 = org.lwjgl.glfw.CallbackBridge.physicalHeight
            float r1 = (float) r1
            float r2 = r3.getHeight()
            float r1 = r1 - r2
            java.lang.String r1 = java.lang.Float.toString(r1)
            java.lang.String r2 = "bottom"
            r0.put(r2, r1)
            float r1 = r3.getWidth()
            java.lang.String r1 = java.lang.Float.toString(r1)
            java.lang.String r2 = "width"
            r0.put(r2, r1)
            float r1 = r3.getHeight()
            java.lang.String r1 = java.lang.Float.toString(r1)
            java.lang.String r2 = "height"
            r0.put(r2, r1)
            int r1 = org.lwjgl.glfw.CallbackBridge.physicalWidth
            java.lang.String r1 = java.lang.Integer.toString(r1)
            java.lang.String r2 = "screen_width"
            r0.put(r2, r1)
            int r1 = org.lwjgl.glfw.CallbackBridge.physicalHeight
            java.lang.String r1 = java.lang.Integer.toString(r1)
            java.lang.String r2 = "screen_height"
            r0.put(r2, r1)
            float r1 = net.kdt.pojavlaunch.prefs.LauncherPreferences.PREF_BUTTONSIZE
            java.lang.String r1 = java.lang.Float.toString(r1)
            java.lang.String r2 = "preferred_scale"
            r0.put(r2, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.kdt.pojavlaunch.customcontrols.ControlData.fillConversionMap():java.util.Map");
    }
}

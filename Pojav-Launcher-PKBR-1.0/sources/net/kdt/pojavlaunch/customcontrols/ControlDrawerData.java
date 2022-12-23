package net.kdt.pojavlaunch.customcontrols;

import java.util.ArrayList;
import java.util.Iterator;
import net.kdt.pojavlaunch.Tools;

public class ControlDrawerData {
    public ArrayList<ControlData> buttonProperties;
    public Orientation orientation;
    public ControlData properties;

    public enum Orientation {
        DOWN,
        LEFT,
        UP,
        RIGHT,
        FREE
    }

    public static Orientation[] getOrientations() {
        return new Orientation[]{Orientation.DOWN, Orientation.LEFT, Orientation.UP, Orientation.RIGHT, Orientation.FREE};
    }

    /* renamed from: net.kdt.pojavlaunch.customcontrols.ControlDrawerData$1 */
    static /* synthetic */ class C00291 {

        /* renamed from: $SwitchMap$net$kdt$pojavlaunch$customcontrols$ControlDrawerData$Orientation */
        static final /* synthetic */ int[] f1xb0ef5a08;

        static {
            int[] iArr = new int[Orientation.values().length];
            f1xb0ef5a08 = iArr;
            try {
                iArr[Orientation.DOWN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1xb0ef5a08[Orientation.LEFT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1xb0ef5a08[Orientation.UP.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1xb0ef5a08[Orientation.RIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1xb0ef5a08[Orientation.FREE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public static int orientationToInt(Orientation orientation2) {
        switch (C00291.f1xb0ef5a08[orientation2.ordinal()]) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            default:
                return -1;
        }
    }

    public static Orientation intToOrientation(int by) {
        switch (by) {
            case 0:
                return Orientation.DOWN;
            case 1:
                return Orientation.LEFT;
            case 2:
                return Orientation.UP;
            case 3:
                return Orientation.RIGHT;
            case 4:
                return Orientation.FREE;
            default:
                return null;
        }
    }

    public ControlDrawerData() {
        this((ArrayList<ControlData>) new ArrayList());
    }

    public ControlDrawerData(ArrayList<ControlData> buttonProperties2) {
        this(buttonProperties2, new ControlData("Drawer", new int[0], (float) (Tools.currentDisplayMetrics.widthPixels / 2), (float) (Tools.currentDisplayMetrics.heightPixels / 2)));
    }

    public ControlDrawerData(ArrayList<ControlData> buttonProperties2, ControlData properties2) {
        this(buttonProperties2, properties2, Orientation.LEFT);
    }

    public ControlDrawerData(ArrayList<ControlData> buttonProperties2, ControlData properties2, Orientation orientation2) {
        this.buttonProperties = buttonProperties2;
        this.properties = properties2;
        this.orientation = orientation2;
    }

    public ControlDrawerData(ControlDrawerData drawerData) {
        this.buttonProperties = new ArrayList<>(drawerData.buttonProperties.size());
        Iterator<ControlData> it = drawerData.buttonProperties.iterator();
        while (it.hasNext()) {
            this.buttonProperties.add(new ControlData(it.next()));
        }
        this.properties = new ControlData(drawerData.properties);
        this.orientation = drawerData.orientation;
    }
}

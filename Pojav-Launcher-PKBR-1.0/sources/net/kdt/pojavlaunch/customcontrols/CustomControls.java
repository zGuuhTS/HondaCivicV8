package net.kdt.pojavlaunch.customcontrols;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.kdt.pojavlaunch.Tools;

public class CustomControls {
    public List<ControlData> mControlDataList;
    public List<ControlDrawerData> mDrawerDataList;
    public float scaledAt;
    public int version;

    public CustomControls() {
        this(new ArrayList(), new ArrayList());
    }

    public CustomControls(List<ControlData> mControlDataList2, List<ControlDrawerData> mDrawerDataList2) {
        this.version = -1;
        this.mControlDataList = mControlDataList2;
        this.mDrawerDataList = mDrawerDataList2;
        this.scaledAt = 100.0f;
    }

    public void save(String path) throws IOException {
        this.version = 4;
        Tools.write(path, Tools.GLOBAL_GSON.toJson((Object) this));
    }
}

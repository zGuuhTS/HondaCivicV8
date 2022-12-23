package net.kdt.pojavlaunch.customcontrols;

import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.ArrayList;
import net.kdt.pojavlaunch.Tools;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lwjgl.glfw.CallbackBridge;

public class LayoutConverter {
    public static boolean convertLookType = false;

    public static CustomControls loadAndConvertIfNecessary(String jsonPath) throws IOException, JsonSyntaxException {
        String jsonLayoutData = Tools.read(jsonPath);
        try {
            JSONObject layoutJobj = new JSONObject(jsonLayoutData);
            if (!layoutJobj.has("version")) {
                CustomControls layout = convertV1Layout(layoutJobj);
                layout.save(jsonPath);
                return layout;
            } else if (layoutJobj.getInt("version") == 2) {
                CustomControls layout2 = convertV2Layout(layoutJobj);
                layout2.save(jsonPath);
                return layout2;
            } else {
                if (layoutJobj.getInt("version") != 3) {
                    if (layoutJobj.getInt("version") != 4) {
                        return null;
                    }
                }
                return (CustomControls) Tools.GLOBAL_GSON.fromJson(jsonLayoutData, CustomControls.class);
            }
        } catch (JSONException e) {
            throw new JsonSyntaxException("Failed to load", e);
        }
    }

    public static CustomControls convertV2Layout(JSONObject oldLayoutJson) throws JSONException {
        JSONArray layoutMainArray;
        JSONObject jSONObject = oldLayoutJson;
        CustomControls layout = (CustomControls) Tools.GLOBAL_GSON.fromJson(oldLayoutJson.toString(), CustomControls.class);
        JSONArray layoutMainArray2 = jSONObject.getJSONArray("mControlDataList");
        layout.mControlDataList = new ArrayList(layoutMainArray2.length());
        for (int i = 0; i < layoutMainArray2.length(); i++) {
            JSONObject button = layoutMainArray2.getJSONObject(i);
            ControlData n_button = (ControlData) Tools.GLOBAL_GSON.fromJson(button.toString(), ControlData.class);
            if ((n_button.dynamicX == null || n_button.dynamicX.isEmpty()) && button.has("x")) {
                n_button.dynamicX = (button.getDouble("x") / ((double) CallbackBridge.physicalWidth)) + " * ${screen_width}";
            }
            if ((n_button.dynamicY == null || n_button.dynamicY.isEmpty()) && button.has("y")) {
                n_button.dynamicY = (button.getDouble("y") / ((double) CallbackBridge.physicalHeight)) + " * ${screen_height}";
            }
            layout.mControlDataList.add(n_button);
        }
        JSONArray layoutDrawerArray = jSONObject.getJSONArray("mDrawerDataList");
        layout.mDrawerDataList = new ArrayList();
        int i2 = 0;
        while (i2 < layoutDrawerArray.length()) {
            JSONObject button2 = layoutDrawerArray.getJSONObject(i2);
            JSONObject buttonProperties = button2.getJSONObject("properties");
            ControlDrawerData n_button2 = (ControlDrawerData) Tools.GLOBAL_GSON.fromJson(button2.toString(), ControlDrawerData.class);
            if (n_button2.properties.dynamicX != null && !n_button2.properties.dynamicX.isEmpty()) {
                layoutMainArray = layoutMainArray2;
            } else if (buttonProperties.has("x")) {
                layoutMainArray = layoutMainArray2;
                n_button2.properties.dynamicX = (buttonProperties.getDouble("x") / ((double) CallbackBridge.physicalWidth)) + " * ${screen_width}";
            } else {
                layoutMainArray = layoutMainArray2;
            }
            if ((n_button2.properties.dynamicY == null || n_button2.properties.dynamicY.isEmpty()) && buttonProperties.has("y")) {
                n_button2.properties.dynamicY = (buttonProperties.getDouble("y") / ((double) CallbackBridge.physicalHeight)) + " * ${screen_height}";
            }
            layout.mDrawerDataList.add(n_button2);
            i2++;
            JSONObject jSONObject2 = oldLayoutJson;
            layoutMainArray2 = layoutMainArray;
        }
        layout.version = 3;
        return layout;
    }

    public static CustomControls convertV1Layout(JSONObject oldLayoutJson) throws JSONException {
        CustomControls empty = new CustomControls();
        JSONArray layoutMainArray = oldLayoutJson.getJSONArray("mControlDataList");
        for (int i = 0; i < layoutMainArray.length(); i++) {
            JSONObject button = layoutMainArray.getJSONObject(i);
            ControlData n_button = new ControlData();
            int[] keycodes = {0, 0, 0, 0};
            n_button.isDynamicBtn = button.getBoolean("isDynamicBtn");
            n_button.dynamicX = button.getString("dynamicX");
            n_button.dynamicY = button.getString("dynamicY");
            if ((n_button.dynamicX == null || n_button.dynamicX.isEmpty()) && button.has("x")) {
                n_button.dynamicX = (button.getDouble("x") / ((double) CallbackBridge.physicalWidth)) + " * ${screen_width}";
            }
            if ((n_button.dynamicY == null || n_button.dynamicY.isEmpty()) && button.has("y")) {
                n_button.dynamicY = (button.getDouble("y") / ((double) CallbackBridge.physicalHeight)) + " * ${screen_height}";
            }
            n_button.name = button.getString("name");
            n_button.opacity = ((float) ((button.getInt("transparency") - 100) * -1)) / 100.0f;
            n_button.passThruEnabled = button.getBoolean("passThruEnabled");
            n_button.isToggle = button.getBoolean("isToggle");
            n_button.setHeight((float) button.getInt("height"));
            n_button.setWidth((float) button.getInt("width"));
            if (convertLookType) {
                n_button.strokeColor = -578846849;
                n_button.bgColor = -2139127937;
                n_button.strokeWidth = 10;
            } else {
                n_button.bgColor = 1291845632;
                n_button.strokeWidth = 0;
            }
            if (button.getBoolean("isRound")) {
                n_button.cornerRadius = 35.0f;
            }
            int next_idx = 0;
            if (button.getBoolean("holdShift")) {
                keycodes[0] = 340;
                next_idx = 0 + 1;
            }
            if (button.getBoolean("holdCtrl")) {
                keycodes[next_idx] = 341;
                next_idx++;
            }
            if (button.getBoolean("holdAlt")) {
                keycodes[next_idx] = 342;
                next_idx++;
            }
            keycodes[next_idx] = button.getInt("keycode");
            n_button.keycodes = keycodes;
            empty.mControlDataList.add(n_button);
        }
        empty.scaledAt = (float) oldLayoutJson.getDouble("scaledAt");
        empty.version = 3;
        return empty;
    }
}

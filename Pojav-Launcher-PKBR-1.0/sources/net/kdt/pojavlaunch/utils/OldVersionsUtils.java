package net.kdt.pojavlaunch.utils;

import android.util.Log;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import net.kdt.pojavlaunch.JMinecraftVersionList;
import net.kdt.pojavlaunch.extra.ExtraConstants;
import net.kdt.pojavlaunch.extra.ExtraCore;

public class OldVersionsUtils {
    public static void selectOpenGlVersion(JMinecraftVersionList.Version version) {
        String openGlVersion;
        String creationDate = version.time;
        if (creationDate == null || creationDate.isEmpty()) {
            ExtraCore.setValue(ExtraConstants.OPEN_GL_VERSION, "2");
            return;
        }
        try {
            if (new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(creationDate.substring(0, creationDate.indexOf("T"))).before(new Date(111, 6, 8))) {
                openGlVersion = "1";
            } else {
                openGlVersion = "2";
            }
            ExtraCore.setValue(ExtraConstants.OPEN_GL_VERSION, openGlVersion);
        } catch (ParseException exception) {
            Log.e("OPENGL SELECTION", exception.toString());
            ExtraCore.setValue(ExtraConstants.OPEN_GL_VERSION, "2");
        }
    }
}

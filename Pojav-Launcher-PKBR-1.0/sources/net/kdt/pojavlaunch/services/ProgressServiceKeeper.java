package net.kdt.pojavlaunch.services;

import android.content.Context;
import net.kdt.pojavlaunch.progresskeeper.TaskCountListener;

public class ProgressServiceKeeper implements TaskCountListener {
    private Context context;

    public ProgressServiceKeeper(Context ctx) {
        this.context = ctx;
    }

    public void onUpdateTaskCount(int taskCount) {
        if (taskCount > 0) {
            ProgressService.startService(this.context);
        }
    }
}

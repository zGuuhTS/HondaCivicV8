package androidx.preference;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class PreferenceViewHolder extends RecyclerView.ViewHolder {
    private final Drawable mBackground;
    private final SparseArray<View> mCachedViews;
    private boolean mDividerAllowedAbove;
    private boolean mDividerAllowedBelow;
    private ColorStateList mTitleTextColors;

    PreferenceViewHolder(View itemView) {
        super(itemView);
        SparseArray<View> sparseArray = new SparseArray<>(4);
        this.mCachedViews = sparseArray;
        TextView titleView = (TextView) itemView.findViewById(16908310);
        sparseArray.put(16908310, titleView);
        sparseArray.put(16908304, itemView.findViewById(16908304));
        sparseArray.put(16908294, itemView.findViewById(16908294));
        sparseArray.put(C0100R.C0102id.icon_frame, itemView.findViewById(C0100R.C0102id.icon_frame));
        sparseArray.put(AndroidResources.ANDROID_R_ICON_FRAME, itemView.findViewById(AndroidResources.ANDROID_R_ICON_FRAME));
        this.mBackground = itemView.getBackground();
        if (titleView != null) {
            this.mTitleTextColors = titleView.getTextColors();
        }
    }

    public static PreferenceViewHolder createInstanceForTests(View itemView) {
        return new PreferenceViewHolder(itemView);
    }

    public View findViewById(int id) {
        View cachedView = this.mCachedViews.get(id);
        if (cachedView != null) {
            return cachedView;
        }
        View v = this.itemView.findViewById(id);
        if (v != null) {
            this.mCachedViews.put(id, v);
        }
        return v;
    }

    public boolean isDividerAllowedAbove() {
        return this.mDividerAllowedAbove;
    }

    public void setDividerAllowedAbove(boolean allowed) {
        this.mDividerAllowedAbove = allowed;
    }

    public boolean isDividerAllowedBelow() {
        return this.mDividerAllowedBelow;
    }

    public void setDividerAllowedBelow(boolean allowed) {
        this.mDividerAllowedBelow = allowed;
    }

    /* access modifiers changed from: package-private */
    public void resetState() {
        if (this.itemView.getBackground() != this.mBackground) {
            ViewCompat.setBackground(this.itemView, this.mBackground);
        }
        TextView titleView = (TextView) findViewById(16908310);
        if (titleView != null && this.mTitleTextColors != null && !titleView.getTextColors().equals(this.mTitleTextColors)) {
            titleView.setTextColor(this.mTitleTextColors);
        }
    }
}

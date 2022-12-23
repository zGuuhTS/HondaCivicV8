package p001fr.spse.extended_view;

import android.view.View;

/* renamed from: fr.spse.extended_view.ExtendedViewData */
public class ExtendedViewData {
    private boolean[] mIntegerCompounds = {false, false, false, false};
    private int[] mPaddingCompounds = {0, 0, 0, 0};
    private int[] mSizeCompounds = {-1, -1, -1, -1};
    private final View mView;

    public ExtendedViewData(View view) {
        this.mView = view;
    }

    public boolean[] getIntegerCompounds() {
        return this.mIntegerCompounds;
    }

    public void setIntegerCompounds(boolean[] mIntegerCompounds2) {
        this.mIntegerCompounds = mIntegerCompounds2;
        this.mView.invalidate();
    }

    public void setIntegerCompound(int i, boolean integer) {
        this.mIntegerCompounds[i] = integer;
        this.mView.invalidate();
    }

    public int[] getSizeCompounds() {
        return this.mSizeCompounds;
    }

    public void setSizeCompounds(int[] mSizeCompounds2) {
        this.mSizeCompounds = mSizeCompounds2;
        this.mView.invalidate();
    }

    public void setSizeCompound(int i, int size) {
        this.mSizeCompounds[i] = size;
        this.mView.invalidate();
    }

    public int[] getPaddingCompounds() {
        return this.mPaddingCompounds;
    }

    public void setPaddingCompounds(int[] mPaddingCompounds2) {
        this.mPaddingCompounds = mPaddingCompounds2;
        this.mView.invalidate();
    }

    public void setPaddingCompound(int i, int padding) {
        this.mPaddingCompounds[i] = padding;
        this.mView.invalidate();
    }
}

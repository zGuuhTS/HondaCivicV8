package androidx.core.graphics;

import android.graphics.Rect;

public final class Insets {
    public static final Insets NONE = new Insets(0, 0, 0, 0);
    public final int bottom;
    public final int left;
    public final int right;

    /* renamed from: top  reason: collision with root package name */
    public final int f214top;

    private Insets(int left2, int top2, int right2, int bottom2) {
        this.left = left2;
        this.f214top = top2;
        this.right = right2;
        this.bottom = bottom2;
    }

    /* renamed from: of */
    public static Insets m5of(int left2, int top2, int right2, int bottom2) {
        if (left2 == 0 && top2 == 0 && right2 == 0 && bottom2 == 0) {
            return NONE;
        }
        return new Insets(left2, top2, right2, bottom2);
    }

    /* renamed from: of */
    public static Insets m6of(Rect r) {
        return m5of(r.left, r.top, r.right, r.bottom);
    }

    public static Insets add(Insets a, Insets b) {
        return m5of(a.left + b.left, a.f214top + b.f214top, a.right + b.right, a.bottom + b.bottom);
    }

    public static Insets subtract(Insets a, Insets b) {
        return m5of(a.left - b.left, a.f214top - b.f214top, a.right - b.right, a.bottom - b.bottom);
    }

    public static Insets max(Insets a, Insets b) {
        return m5of(Math.max(a.left, b.left), Math.max(a.f214top, b.f214top), Math.max(a.right, b.right), Math.max(a.bottom, b.bottom));
    }

    public static Insets min(Insets a, Insets b) {
        return m5of(Math.min(a.left, b.left), Math.min(a.f214top, b.f214top), Math.min(a.right, b.right), Math.min(a.bottom, b.bottom));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Insets insets = (Insets) o;
        if (this.bottom == insets.bottom && this.left == insets.left && this.right == insets.right && this.f214top == insets.f214top) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((this.left * 31) + this.f214top) * 31) + this.right) * 31) + this.bottom;
    }

    public String toString() {
        return "Insets{left=" + this.left + ", top=" + this.f214top + ", right=" + this.right + ", bottom=" + this.bottom + '}';
    }

    @Deprecated
    public static Insets wrap(android.graphics.Insets insets) {
        return toCompatInsets(insets);
    }

    public static Insets toCompatInsets(android.graphics.Insets insets) {
        return m5of(insets.left, insets.top, insets.right, insets.bottom);
    }

    public android.graphics.Insets toPlatformInsets() {
        return android.graphics.Insets.of(this.left, this.f214top, this.right, this.bottom);
    }
}

package net.kdt.pojavlaunch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;
import java.util.LinkedList;
import net.kdt.pojavlaunch.utils.JREUtils;
import org.lwjgl.glfw.CallbackBridge;

public class AWTCanvasView extends TextureView implements TextureView.SurfaceTextureListener, Runnable {
    public static final int AWT_CANVAS_HEIGHT = 600;
    public static final int AWT_CANVAS_WIDTH = 720;
    private final int MAX_SIZE;
    private final double NANOS;
    private boolean mAttached;
    private boolean mDrawing;
    private final TextPaint mFpsPaint;
    private int mHeight;
    private boolean mIsDestroyed;
    private final LinkedList<Long> mTimes;
    private int mWidth;

    public AWTCanvasView(Context ctx) {
        this(ctx, (AttributeSet) null);
    }

    public AWTCanvasView(Context ctx, AttributeSet attrs) {
        super(ctx, attrs);
        this.MAX_SIZE = 100;
        this.NANOS = 1.0E9d;
        this.mIsDestroyed = false;
        this.mAttached = false;
        this.mTimes = new LinkedList<Long>() {
            {
                add(Long.valueOf(System.nanoTime()));
            }
        };
        TextPaint textPaint = new TextPaint();
        this.mFpsPaint = textPaint;
        textPaint.setColor(-1);
        textPaint.setTextSize(20.0f);
        setSurfaceTextureListener(this);
        post(new Runnable() {
            public final void run() {
                AWTCanvasView.this.refreshSize();
            }
        });
    }

    public void onSurfaceTextureAvailable(SurfaceTexture texture, int w, int h) {
        getSurfaceTexture().setDefaultBufferSize(AWT_CANVAS_WIDTH, 600);
        this.mWidth = w;
        this.mHeight = h;
        this.mIsDestroyed = false;
        new Thread(this, "AndroidAWTRenderer").start();
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
        this.mIsDestroyed = true;
        return true;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int w, int h) {
        getSurfaceTexture().setDefaultBufferSize(AWT_CANVAS_WIDTH, 600);
        this.mWidth = w;
        this.mHeight = h;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture texture) {
        getSurfaceTexture().setDefaultBufferSize(AWT_CANVAS_WIDTH, 600);
    }

    public void run() {
        Surface surface = new Surface(getSurfaceTexture());
        while (!this.mIsDestroyed && surface.isValid()) {
            try {
                Canvas canvas = surface.lockCanvas((Rect) null);
                boolean z = false;
                canvas.drawRGB(0, 0, 0);
                if (!this.mAttached) {
                    this.mAttached = CallbackBridge.nativeAttachThreadToOther(true, MainActivity.isInputStackCall);
                } else {
                    int[] rgbArray = JREUtils.renderAWTScreenFrame();
                    if (rgbArray != null) {
                        z = true;
                    }
                    this.mDrawing = z;
                    if (rgbArray != null) {
                        canvas.save();
                        canvas.drawBitmap(rgbArray, 0, AWT_CANVAS_WIDTH, 0, 0, AWT_CANVAS_WIDTH, 600, true, (Paint) null);
                        canvas.restore();
                    }
                }
                canvas.drawText("FPS: " + (Math.round(fps() * 10.0d) / 10) + ", attached=" + this.mAttached + ", drawing=" + this.mDrawing, 0.0f, 20.0f, this.mFpsPaint);
                surface.unlockCanvasAndPost(canvas);
            } catch (Throwable throwable) {
                Tools.showError(getContext(), throwable);
            }
        }
        surface.release();
    }

    private double fps() {
        long lastTime = System.nanoTime();
        double difference = ((double) (lastTime - this.mTimes.getFirst().longValue())) / 1.0E9d;
        this.mTimes.addLast(Long.valueOf(lastTime));
        if (this.mTimes.size() > 100) {
            this.mTimes.removeFirst();
        }
        if (difference > 0.0d) {
            return ((double) this.mTimes.size()) / difference;
        }
        return 0.0d;
    }

    /* access modifiers changed from: private */
    public void refreshSize() {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (getHeight() < getWidth()) {
            layoutParams.width = (getHeight() * AWT_CANVAS_WIDTH) / 600;
        } else {
            layoutParams.height = (getWidth() * 600) / AWT_CANVAS_WIDTH;
        }
        setLayoutParams(layoutParams);
    }
}

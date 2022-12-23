package com.kdt.mcgui;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.internal.view.SupportMenu;
import com.p000br.pixelmonbrasil.debug.R;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.kdt.pojavlaunch.PXBRProfile;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.authenticator.listener.DoneListener;
import net.kdt.pojavlaunch.authenticator.listener.ErrorListener;
import net.kdt.pojavlaunch.authenticator.listener.ProgressListener;
import net.kdt.pojavlaunch.authenticator.microsoft.MicrosoftBackgroundLogin;
import net.kdt.pojavlaunch.extra.ExtraConstants;
import net.kdt.pojavlaunch.extra.ExtraCore;
import net.kdt.pojavlaunch.extra.ExtraListener;
import net.kdt.pojavlaunch.value.MinecraftAccount;
import p001fr.spse.extended_view.ExtendedTextView;

public class mcAccountSpinner extends AppCompatSpinner implements AdapterView.OnItemSelectedListener {
    private static final int MAX_LOGIN_STEP = 5;
    private final List<String> mAccountList;
    private final DoneListener mDoneListener;
    private final ErrorListener mErrorListener;
    private BitmapDrawable mHeadDrawable;
    private ObjectAnimator mLoginBarAnimator;
    private final Paint mLoginBarPaint;
    private float mLoginBarWidth;
    private int mLoginStep;
    private final ExtraListener<Uri> mMicrosoftLoginListener;
    private final ExtraListener<String[]> mMojangLoginListener;
    private final ProgressListener mProgressListener;
    private MinecraftAccount mSelectecAccount;

    public mcAccountSpinner(Context context) {
        this(context, (AttributeSet) null);
    }

    public mcAccountSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mAccountList = new ArrayList(2);
        this.mSelectecAccount = null;
        this.mLoginBarWidth = -1.0f;
        this.mLoginBarPaint = new Paint();
        this.mLoginStep = 0;
        this.mProgressListener = new ProgressListener() {
            public final void onLoginProgress(int i) {
                mcAccountSpinner.this.lambda$new$0$mcAccountSpinner(i);
            }
        };
        this.mDoneListener = new DoneListener() {
            public final void onLoginDone(MinecraftAccount minecraftAccount) {
                mcAccountSpinner.this.lambda$new$1$mcAccountSpinner(minecraftAccount);
            }
        };
        this.mErrorListener = new ErrorListener() {
            public final void onLoginError(Throwable th) {
                mcAccountSpinner.this.lambda$new$2$mcAccountSpinner(th);
            }
        };
        this.mMicrosoftLoginListener = new ExtraListener() {
            public final boolean onValueSet(String str, Object obj) {
                return mcAccountSpinner.this.lambda$new$3$mcAccountSpinner(str, (Uri) obj);
            }
        };
        this.mMojangLoginListener = new ExtraListener() {
            public final boolean onValueSet(String str, Object obj) {
                return mcAccountSpinner.this.lambda$new$4$mcAccountSpinner(str, (String[]) obj);
            }
        };
        init();
    }

    public /* synthetic */ void lambda$new$0$mcAccountSpinner(int step) {
        this.mLoginStep = step;
        ObjectAnimator objectAnimator = this.mLoginBarAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
            this.mLoginBarAnimator.setFloatValues(new float[]{this.mLoginBarWidth, (float) ((getWidth() / 5) * this.mLoginStep)});
        } else {
            this.mLoginBarAnimator = ObjectAnimator.ofFloat(this, "LoginBarWidth", new float[]{this.mLoginBarWidth, (float) ((getWidth() / 5) * this.mLoginStep)});
        }
        this.mLoginBarAnimator.start();
    }

    public /* synthetic */ void lambda$new$1$mcAccountSpinner(MinecraftAccount account) {
        Toast.makeText(getContext(), R.string.main_login_done, 0).show();
        this.mSelectecAccount = account;
        invalidate();
        ((Activity) getContext()).finish();
        this.mAccountList.add(account.username);
        reloadAccounts(false, this.mAccountList.size() - 1);
    }

    public /* synthetic */ void lambda$new$2$mcAccountSpinner(Throwable errorMessage) {
        this.mLoginBarPaint.setColor(SupportMenu.CATEGORY_MASK);
        Tools.showError(getContext(), errorMessage);
        invalidate();
    }

    public /* synthetic */ boolean lambda$new$3$mcAccountSpinner(String key, Uri value) {
        this.mLoginBarPaint.setColor(getResources().getColor(R.color.minebutton_color));
        new MicrosoftBackgroundLogin(false, value.getQueryParameter("code")).performLogin(this.mProgressListener, this.mDoneListener, this.mErrorListener);
        return false;
    }

    public /* synthetic */ boolean lambda$new$4$mcAccountSpinner(String key, String[] value) {
        if (value[1].isEmpty()) {
            MinecraftAccount account = new MinecraftAccount();
            account.username = value[0];
            try {
                account.save();
            } catch (IOException e) {
                Log.e("McAccountSpinner", "Failed to save the account : " + e);
            }
            this.mDoneListener.onLoginDone(account);
        }
        return false;
    }

    private void init() {
        setBackgroundColor(getResources().getColor(R.color.background_status_bar));
        this.mLoginBarPaint.setColor(getResources().getColor(R.color.background_status_bar));
        this.mLoginBarPaint.setStrokeWidth((float) getResources().getDimensionPixelOffset(R.dimen._2sdp));
        reloadAccounts(true, 0);
        setOnItemSelectedListener(this);
        ExtraCore.addExtraListener(ExtraConstants.MOJANG_LOGIN_TODO, this.mMojangLoginListener);
        ExtraCore.addExtraListener(ExtraConstants.MICROSOFT_LOGIN_TODO, this.mMicrosoftLoginListener);
    }

    public final void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (position == 0) {
            this.mAccountList.size();
            return;
        }
        pickAccount(position);
        performLogin(this.mSelectecAccount);
    }

    public final void onNothingSelected(AdapterView<?> adapterView) {
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mLoginBarWidth == -1.0f) {
            this.mLoginBarWidth = (float) getWidth();
        }
        float bottom = ((float) getHeight()) - (this.mLoginBarPaint.getStrokeWidth() / 2.0f);
        canvas.drawLine(0.0f, bottom, this.mLoginBarWidth, bottom, this.mLoginBarPaint);
    }

    public void removeCurrentAccount() {
        int position = getSelectedItemPosition();
        if (position != 0) {
            File accountFile = new File(Tools.DIR_ACCOUNT_NEW, this.mAccountList.get(position) + ".json");
            if (accountFile.exists()) {
                accountFile.delete();
            }
            this.mAccountList.remove(position);
            reloadAccounts(false, 0);
        }
    }

    public void setLoginBarWidth(float value) {
        this.mLoginBarWidth = value;
        invalidate();
    }

    public boolean isAccountOnline() {
        MinecraftAccount minecraftAccount = this.mSelectecAccount;
        return minecraftAccount != null && !minecraftAccount.accessToken.equals("0");
    }

    public MinecraftAccount getSelectedAccount() {
        return this.mSelectecAccount;
    }

    public int getLoginState() {
        return this.mLoginStep;
    }

    public boolean isLoginDone() {
        return this.mLoginStep >= 5;
    }

    private void setNoAccountBehavior() {
        if (this.mAccountList.size() != 1) {
            setOnTouchListener((View.OnTouchListener) null);
        } else {
            setOnTouchListener($$Lambda$mcAccountSpinner$PQzwgg2iSttxQvgHmtu6Jv3AxG8.INSTANCE);
        }
    }

    static /* synthetic */ boolean lambda$setNoAccountBehavior$5(View v, MotionEvent event) {
        if (event.getAction() != 1) {
            return false;
        }
        ExtraCore.setValue(ExtraConstants.SELECT_AUTH_METHOD, true);
        return true;
    }

    private void reloadAccounts(boolean fromFiles, int overridePosition) {
        if (fromFiles) {
            this.mAccountList.clear();
            this.mAccountList.add(getContext().getString(R.string.main_add_account));
            File accountFolder = new File(Tools.DIR_ACCOUNT_NEW);
            if (accountFolder.exists()) {
                for (String fileName : accountFolder.list()) {
                    this.mAccountList.add(fileName.substring(0, fileName.length() - 5));
                }
            }
        }
        ArrayAdapter<String> accountAdapter = new ArrayAdapter<>(getContext(), R.layout.item_minecraft_account, (String[]) this.mAccountList.toArray(new String[0]));
        accountAdapter.setDropDownViewResource(R.layout.item_minecraft_account);
        setAdapter((SpinnerAdapter) accountAdapter);
        pickAccount(overridePosition == 0 ? -1 : overridePosition);
        MinecraftAccount minecraftAccount = this.mSelectecAccount;
        if (minecraftAccount != null) {
            performLogin(minecraftAccount);
        }
        setNoAccountBehavior();
    }

    private void performLogin(MinecraftAccount minecraftAccount) {
        if (!minecraftAccount.isLocal()) {
            this.mLoginBarPaint.setColor(getResources().getColor(R.color.minebutton_color));
            if (minecraftAccount.isMicrosoft && System.currentTimeMillis() > minecraftAccount.expiresAt) {
                new MicrosoftBackgroundLogin(true, minecraftAccount.msaRefreshToken).performLogin(this.mProgressListener, this.mDoneListener, this.mErrorListener);
            }
        }
    }

    private void pickAccount(int position) {
        MinecraftAccount selectedAccount;
        ExtendedTextView view;
        if (position != -1) {
            PXBRProfile.setCurrentProfile(getContext(), this.mAccountList.get(position));
            selectedAccount = PXBRProfile.getCurrentProfileContent(getContext(), this.mAccountList.get(position));
            setSelection(position);
        } else {
            selectedAccount = PXBRProfile.getCurrentProfileContent(getContext(), (String) null);
            int spinnerPosition = 1;
            if (selectedAccount != null) {
                spinnerPosition = this.mAccountList.indexOf(selectedAccount.username);
            } else if (this.mAccountList.size() <= 1) {
                spinnerPosition = 0;
            }
            setSelection(spinnerPosition, false);
        }
        this.mSelectecAccount = selectedAccount;
        BitmapDrawable oldBitmapDrawable = this.mHeadDrawable;
        if (!(selectedAccount == null || (view = (ExtendedTextView) getSelectedView()) == null)) {
            Bitmap bitmap = this.mSelectecAccount.getSkinFace();
            if (bitmap != null) {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
                this.mHeadDrawable = bitmapDrawable;
                bitmapDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                view.setCompoundDrawables(this.mHeadDrawable, (Drawable) null, (Drawable) null, (Drawable) null);
            } else {
                view.setCompoundDrawables((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            }
            view.postProcessDrawables();
        }
        if (oldBitmapDrawable != null) {
            oldBitmapDrawable.getBitmap().recycle();
        }
    }
}

package net.kdt.pojavlaunch.customcontrols.keyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatEditText;
import com.p000br.pixelmonbrasil.debug.R;

public class TouchCharInput extends AppCompatEditText {
    private CharacterSenderStrategy mCharacterSender;
    private boolean mIsDoingInternalChanges;

    public TouchCharInput(Context context) {
        this(context, (AttributeSet) null);
    }

    public TouchCharInput(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public TouchCharInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mIsDoingInternalChanges = false;
        setup();
    }

    /* access modifiers changed from: protected */
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (!this.mIsDoingInternalChanges) {
            if (this.mCharacterSender != null) {
                for (int i = 0; i < lengthBefore; i++) {
                    this.mCharacterSender.sendBackspace();
                }
                int i2 = start;
                int count = 0;
                while (count < lengthAfter) {
                    this.mCharacterSender.sendChar(text.charAt(i2));
                    count++;
                    i2++;
                }
            }
            if (text.length() < 1) {
                clear();
            }
        }
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        disable();
    }

    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == 4 && event.getAction() == 1) {
            disable();
        }
        return super.onKeyPreIme(keyCode, event);
    }

    public boolean switchKeyboardState() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService("input_method");
        if (hasFocus() || (getResources().getConfiguration().keyboard == 2 && getResources().getConfiguration().hardKeyboardHidden == 2)) {
            imm.hideSoftInputFromWindow(getWindowToken(), 0);
            clear();
            disable();
            return false;
        }
        enable();
        imm.showSoftInput(this, 1);
        return true;
    }

    public void clear() {
        this.mIsDoingInternalChanges = true;
        setText("                              ");
        setSelection(getText().length());
        this.mIsDoingInternalChanges = false;
    }

    public void enable() {
        setEnabled(true);
        setFocusable(true);
        setVisibility(0);
        requestFocus();
    }

    public void disable() {
        clear();
        setVisibility(8);
        clearFocus();
        setEnabled(false);
    }

    private void sendEnter() {
        this.mCharacterSender.sendEnter();
        clear();
    }

    public void setCharacterSender(CharacterSenderStrategy characterSender) {
        this.mCharacterSender = characterSender;
    }

    private void setup() {
        setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return TouchCharInput.this.lambda$setup$0$TouchCharInput(textView, i, keyEvent);
            }
        });
        clear();
        disable();
    }

    public /* synthetic */ boolean lambda$setup$0$TouchCharInput(TextView textView, int i, KeyEvent keyEvent) {
        sendEnter();
        clear();
        disable();
        return false;
    }
}

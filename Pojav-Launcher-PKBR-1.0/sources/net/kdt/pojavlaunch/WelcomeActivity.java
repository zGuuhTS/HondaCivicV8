package net.kdt.pojavlaunch;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import com.p000br.pixelmonbrasil.debug.R;
import net.kdt.pojavlaunch.extra.ExtraConstants;
import net.kdt.pojavlaunch.extra.ExtraCore;
import net.kdt.pojavlaunch.extra.ExtraListener;

public class WelcomeActivity extends AppCompatActivity {
    private final ExtraListener<Boolean> mWelcomeDone = new ExtraListener() {
        public final boolean onValueSet(String str, Object obj) {
            return WelcomeActivity.this.lambda$new$1$WelcomeActivity(str, (Boolean) obj);
        }
    };

    public void onBackPressed() {
        finishAffinity();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_welcome);
        ExtraCore.addExtraListener(ExtraConstants.WELCOME, this.mWelcomeDone);
        Button continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener($$Lambda$WelcomeActivity$mXVBeuGzNTVUonAEAF8NaFeQSLY.INSTANCE);
        continueButton.setText("Continuar");
        ((TextView) findViewById(R.id.welcomeText1)).setText("Bem-Vindo ao Pixelmon Brasil");
        ((TextView) findViewById(R.id.welcomeText2)).setText("O lugar onde você pode encontrar com seus amigos e conhecer novas pessoas, participar de eventos, capturar lendários, montar seu time e ir batalhar em torneios para se tornar o melhor treinador pokémon!");
    }

    public /* synthetic */ boolean lambda$new$1$WelcomeActivity(String key, Boolean value) {
        SharedPreferences firstLaunchPrefs = getSharedPreferences("pojav_extract", 0);
        if (getApplicationContext().getPackageName().equals("com.br.pixelmonbrasil") || getApplicationContext().getPackageName().equals(BuildConfig.APPLICATION_ID)) {
            firstLaunchPrefs.edit().putBoolean("welcomeDialogShown", true).apply();
            finish();
        } else {
            new AlertDialog.Builder(this).setMessage(HtmlCompat.fromHtml("Esse launcher é de fonte não conhecida.<br>Baixe oficialmente pelo nosso " + "<a href=\"https://discord.gg/pxbr\">discord</a>", 0)).show();
        }
        return false;
    }
}

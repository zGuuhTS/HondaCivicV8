package net.kdt.pojavlaunch.fragments;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.p000br.pixelmonbrasil.debug.R;
import net.kdt.pojavlaunch.extra.ExtraConstants;
import net.kdt.pojavlaunch.extra.ExtraCore;

public class MicrosoftLoginFragment extends Fragment {
    public static final String TAG = "MICROSOFT_LOGIN_FRAGMENT";
    private WebView mWebview;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mWebview = (WebView) inflater.inflate(R.layout.fragment_microsoft_login, container, false);
        CookieManager.getInstance().removeAllCookie();
        WebSettings settings = this.mWebview.getSettings();
        settings.setBlockNetworkImage(true);
        settings.setJavaScriptEnabled(true);
        this.mWebview.clearHistory();
        this.mWebview.clearCache(true);
        this.mWebview.clearFormData();
        this.mWebview.clearHistory();
        this.mWebview.setWebViewClient(new WebViewTrackClient());
        this.mWebview.loadUrl("https://login.live.com/oauth20_authorize.srf?client_id=00000000402b5328&response_type=code&scope=service%3A%3Auser.auth.xboxlive.com%3A%3AMBI_SSL&redirect_url=https%3A%2F%2Flogin.live.com%2Foauth20_desktop.srf");
        return this.mWebview;
    }

    public boolean canGoBack() {
        return this.mWebview.canGoBack();
    }

    public void goBack() {
        this.mWebview.goBack();
    }

    class WebViewTrackClient extends WebViewClient {
        WebViewTrackClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("ms-xal-00000000402b5328")) {
                ExtraCore.setValue(ExtraConstants.MICROSOFT_LOGIN_TODO, Uri.parse(url));
                Toast.makeText(view.getContext(), "Login started !", 0).show();
                MicrosoftLoginFragment.this.requireActivity().getSupportFragmentManager().popBackStackImmediate();
                return true;
            } else if (!url.contains("res=cancel")) {
                return super.shouldOverrideUrlLoading(view, url);
            } else {
                MicrosoftLoginFragment.this.requireActivity().onBackPressed();
                return true;
            }
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
        }

        public void onPageFinished(WebView view, String url) {
        }
    }
}

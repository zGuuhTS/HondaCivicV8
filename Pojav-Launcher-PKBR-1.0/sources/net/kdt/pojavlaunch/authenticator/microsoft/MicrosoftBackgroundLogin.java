package net.kdt.pojavlaunch.authenticator.microsoft;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.kdt.mcgui.ProgressLayout;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import net.kdt.pojavlaunch.PXBRApplication;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.authenticator.listener.DoneListener;
import net.kdt.pojavlaunch.authenticator.listener.ErrorListener;
import net.kdt.pojavlaunch.authenticator.listener.ProgressListener;
import net.kdt.pojavlaunch.value.MinecraftAccount;
import org.json.JSONException;
import org.json.JSONObject;

public class MicrosoftBackgroundLogin {
    private static final String authTokenUrl = "https://login.live.com/oauth20_token.srf";
    private static final String mcLoginUrl = "https://api.minecraftservices.com/authentication/login_with_xbox";
    private static final String mcProfileUrl = "https://api.minecraftservices.com/minecraft/profile";
    private static final String xblAuthUrl = "https://user.auth.xboxlive.com/user/authenticate";
    private static final String xstsAuthUrl = "https://xsts.auth.xboxlive.com/xsts/authorize";
    public boolean doesOwnGame;
    public long expiresAt;
    public boolean isRefresh;
    private final String mAuthCode;
    private final Handler mHandler;
    private final boolean mIsRefresh;
    public String mcName;
    public String mcToken;
    public String mcUuid;
    public String msRefreshToken;

    public MicrosoftBackgroundLogin(String filename) {
        this(false, MinecraftAccount.load(filename).accessToken);
    }

    public MicrosoftBackgroundLogin(boolean isRefresh2, String authCode) {
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mIsRefresh = isRefresh2;
        this.mAuthCode = authCode;
    }

    public void performLogin(ProgressListener progressListener, DoneListener doneListener, ErrorListener errorListener) {
        PXBRApplication.sExecutorService.execute(new Runnable(progressListener, doneListener, errorListener) {
            public final /* synthetic */ ProgressListener f$1;
            public final /* synthetic */ DoneListener f$2;
            public final /* synthetic */ ErrorListener f$3;

            {
                this.f$1 = r2;
                this.f$2 = r3;
                this.f$3 = r4;
            }

            public final void run() {
                MicrosoftBackgroundLogin.this.lambda$performLogin$2$MicrosoftBackgroundLogin(this.f$1, this.f$2, this.f$3);
            }
        });
    }

    public /* synthetic */ void lambda$performLogin$2$MicrosoftBackgroundLogin(ProgressListener progressListener, DoneListener doneListener, ErrorListener errorListener) {
        try {
            notifyProgress(progressListener, 1);
            String accessToken = acquireAccessToken(this.mIsRefresh, this.mAuthCode);
            notifyProgress(progressListener, 2);
            String xboxLiveToken = acquireXBLToken(accessToken);
            notifyProgress(progressListener, 3);
            String[] xsts = acquireXsts(xboxLiveToken);
            notifyProgress(progressListener, 4);
            String mcToken2 = acquireMinecraftToken(xsts[0], xsts[1]);
            notifyProgress(progressListener, 5);
            checkMcProfile(mcToken2);
            MinecraftAccount acc = MinecraftAccount.load(this.mcName);
            if (acc == null) {
                acc = new MinecraftAccount();
            }
            if (this.doesOwnGame) {
                acc.xuid = xsts[0];
                acc.clientToken = "0";
                acc.accessToken = mcToken2;
                acc.username = this.mcName;
                acc.profileId = this.mcUuid;
                acc.isMicrosoft = true;
                acc.msaRefreshToken = this.msRefreshToken;
                acc.expiresAt = this.expiresAt;
                acc.updateSkinFace();
            }
            acc.save();
            if (doneListener != null) {
                this.mHandler.post(new Runnable(acc) {
                    public final /* synthetic */ MinecraftAccount f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        DoneListener.this.onLoginDone(this.f$1);
                    }
                });
            }
        } catch (Exception e) {
            Log.e("MicroAuth", e.toString());
            if (errorListener != null) {
                this.mHandler.post(new Runnable(e) {
                    public final /* synthetic */ Exception f$1;

                    {
                        this.f$1 = r2;
                    }

                    public final void run() {
                        ErrorListener.this.onLoginError(this.f$1);
                    }
                });
            }
        }
        ProgressLayout.clearProgress(ProgressLayout.AUTHENTICATE_MICROSOFT);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0105, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0106, code lost:
        if (r4 != null) goto L_0x0108;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0108, code lost:
        if (r2 != null) goto L_0x010a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x010e, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x010f, code lost:
        r2.addSuppressed(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0113, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0116, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String acquireAccessToken(boolean r11, java.lang.String r12) throws java.io.IOException, org.json.JSONException {
        /*
            r10 = this;
            java.net.URL r0 = new java.net.URL
            java.lang.String r1 = "https://login.live.com/oauth20_token.srf"
            r0.<init>(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "isRefresh="
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r11)
            java.lang.String r2 = ", authCode= "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r12)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "MicrosoftLogin"
            android.util.Log.i(r2, r1)
            r1 = 10
            java.lang.String[] r1 = new java.lang.String[r1]
            java.lang.String r3 = "client_id"
            r4 = 0
            r1[r4] = r3
            java.lang.String r3 = "00000000402b5328"
            r5 = 1
            r1[r5] = r3
            java.lang.String r3 = "refresh_token"
            if (r11 == 0) goto L_0x003d
            r6 = r3
            goto L_0x003f
        L_0x003d:
            java.lang.String r6 = "code"
        L_0x003f:
            r7 = 2
            r1[r7] = r6
            r6 = 3
            r1[r6] = r12
            r6 = 4
            java.lang.String r7 = "grant_type"
            r1[r6] = r7
            r6 = 5
            if (r11 == 0) goto L_0x004f
            r7 = r3
            goto L_0x0051
        L_0x004f:
            java.lang.String r7 = "authorization_code"
        L_0x0051:
            r1[r6] = r7
            r6 = 6
            java.lang.String r7 = "redirect_url"
            r1[r6] = r7
            r6 = 7
            java.lang.String r7 = "https://login.live.com/oauth20_desktop.srf"
            r1[r6] = r7
            r6 = 8
            java.lang.String r7 = "scope"
            r1[r6] = r7
            r6 = 9
            java.lang.String r7 = "service::user.auth.xboxlive.com::MBI_SSL"
            r1[r6] = r7
            java.lang.String r1 = convertToFormData(r1)
            java.lang.String r6 = "MicroAuth"
            android.util.Log.i(r6, r1)
            java.net.URLConnection r6 = r0.openConnection()
            java.net.HttpURLConnection r6 = (java.net.HttpURLConnection) r6
            java.lang.String r7 = "Content-Type"
            java.lang.String r8 = "application/x-www-form-urlencoded"
            r6.setRequestProperty(r7, r8)
            java.lang.String r7 = "charset"
            java.lang.String r8 = "utf-8"
            r6.setRequestProperty(r7, r8)
            java.lang.String r7 = "UTF-8"
            byte[] r8 = r1.getBytes(r7)
            int r8 = r8.length
            java.lang.String r8 = java.lang.Integer.toString(r8)
            java.lang.String r9 = "Content-Length"
            r6.setRequestProperty(r9, r8)
            java.lang.String r8 = "POST"
            r6.setRequestMethod(r8)
            r6.setUseCaches(r4)
            r6.setDoInput(r5)
            r6.setDoOutput(r5)
            r6.connect()
            java.io.OutputStream r4 = r6.getOutputStream()
            byte[] r5 = r1.getBytes(r7)     // Catch:{ all -> 0x0103 }
            r4.write(r5)     // Catch:{ all -> 0x0103 }
            if (r4 == 0) goto L_0x00b7
            r4.close()
        L_0x00b7:
            int r4 = r6.getResponseCode()
            r5 = 200(0xc8, float:2.8E-43)
            if (r4 < r5) goto L_0x00fe
            int r4 = r6.getResponseCode()
            r5 = 300(0x12c, float:4.2E-43)
            if (r4 >= r5) goto L_0x00fe
            org.json.JSONObject r4 = new org.json.JSONObject
            java.io.InputStream r5 = r6.getInputStream()
            java.lang.String r5 = net.kdt.pojavlaunch.Tools.read((java.io.InputStream) r5)
            r4.<init>(r5)
            java.lang.String r3 = r4.getString(r3)
            r10.msRefreshToken = r3
            r6.disconnect()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r5 = "Acess Token = "
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.String r5 = "access_token"
            java.lang.String r7 = r4.getString(r5)
            java.lang.StringBuilder r3 = r3.append(r7)
            java.lang.String r3 = r3.toString()
            android.util.Log.i(r2, r3)
            java.lang.String r2 = r4.getString(r5)
            return r2
        L_0x00fe:
            r10.throwResponseError(r6)
            r2 = 0
            return r2
        L_0x0103:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0105 }
        L_0x0105:
            r3 = move-exception
            if (r4 == 0) goto L_0x0116
            if (r2 == 0) goto L_0x0113
            r4.close()     // Catch:{ all -> 0x010e }
            goto L_0x0116
        L_0x010e:
            r5 = move-exception
            r2.addSuppressed(r5)
            goto L_0x0116
        L_0x0113:
            r4.close()
        L_0x0116:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: net.kdt.pojavlaunch.authenticator.microsoft.MicrosoftBackgroundLogin.acquireAccessToken(boolean, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x00a4, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x00a5, code lost:
        if (r5 != null) goto L_0x00a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00a7, code lost:
        if (r6 != null) goto L_0x00a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00ad, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00ae, code lost:
        r6.addSuppressed(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00b2, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00b5, code lost:
        throw r7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String acquireXBLToken(java.lang.String r10) throws java.io.IOException, org.json.JSONException {
        /*
            r9 = this;
            java.net.URL r0 = new java.net.URL
            java.lang.String r1 = "https://user.auth.xboxlive.com/user/authenticate"
            r0.<init>(r1)
            org.json.JSONObject r1 = new org.json.JSONObject
            r1.<init>()
            org.json.JSONObject r2 = new org.json.JSONObject
            r2.<init>()
            java.lang.String r3 = "AuthMethod"
            java.lang.String r4 = "RPS"
            r2.put(r3, r4)
            java.lang.String r3 = "SiteName"
            java.lang.String r4 = "user.auth.xboxlive.com"
            r2.put(r3, r4)
            java.lang.String r3 = "RpsTicket"
            r2.put(r3, r10)
            java.lang.String r3 = "Properties"
            r1.put(r3, r2)
            java.lang.String r3 = "RelyingParty"
            java.lang.String r4 = "http://auth.xboxlive.com"
            r1.put(r3, r4)
            java.lang.String r3 = "TokenType"
            java.lang.String r4 = "JWT"
            r1.put(r3, r4)
            java.lang.String r3 = r1.toString()
            java.net.URLConnection r4 = r0.openConnection()
            java.net.HttpURLConnection r4 = (java.net.HttpURLConnection) r4
            r5 = 1
            setCommonProperties(r4, r3, r5)
            r4.connect()
            java.io.OutputStream r5 = r4.getOutputStream()
            java.lang.String r6 = "UTF-8"
            byte[] r6 = r3.getBytes(r6)     // Catch:{ all -> 0x00a2 }
            r5.write(r6)     // Catch:{ all -> 0x00a2 }
            if (r5 == 0) goto L_0x005a
            r5.close()
        L_0x005a:
            int r5 = r4.getResponseCode()
            r6 = 200(0xc8, float:2.8E-43)
            if (r5 < r6) goto L_0x009d
            int r5 = r4.getResponseCode()
            r6 = 300(0x12c, float:4.2E-43)
            if (r5 >= r6) goto L_0x009d
            org.json.JSONObject r5 = new org.json.JSONObject
            java.io.InputStream r6 = r4.getInputStream()
            java.lang.String r6 = net.kdt.pojavlaunch.Tools.read((java.io.InputStream) r6)
            r5.<init>(r6)
            r4.disconnect()
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Xbl Token = "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r7 = "Token"
            java.lang.String r8 = r5.getString(r7)
            java.lang.StringBuilder r6 = r6.append(r8)
            java.lang.String r6 = r6.toString()
            java.lang.String r8 = "MicrosoftLogin"
            android.util.Log.i(r8, r6)
            java.lang.String r6 = r5.getString(r7)
            return r6
        L_0x009d:
            r9.throwResponseError(r4)
            r5 = 0
            return r5
        L_0x00a2:
            r6 = move-exception
            throw r6     // Catch:{ all -> 0x00a4 }
        L_0x00a4:
            r7 = move-exception
            if (r5 == 0) goto L_0x00b5
            if (r6 == 0) goto L_0x00b2
            r5.close()     // Catch:{ all -> 0x00ad }
            goto L_0x00b5
        L_0x00ad:
            r8 = move-exception
            r6.addSuppressed(r8)
            goto L_0x00b5
        L_0x00b2:
            r5.close()
        L_0x00b5:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: net.kdt.pojavlaunch.authenticator.microsoft.MicrosoftBackgroundLogin.acquireXBLToken(java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x00d6, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x00d7, code lost:
        if (r4 != null) goto L_0x00d9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00d9, code lost:
        if (r6 != null) goto L_0x00db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00df, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00e0, code lost:
        r6.addSuppressed(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00e4, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00e7, code lost:
        throw r7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String[] acquireXsts(java.lang.String r13) throws java.io.IOException, org.json.JSONException {
        /*
            r12 = this;
            java.net.URL r0 = new java.net.URL
            java.lang.String r1 = "https://xsts.auth.xboxlive.com/xsts/authorize"
            r0.<init>(r1)
            org.json.JSONObject r1 = new org.json.JSONObject
            r1.<init>()
            org.json.JSONObject r2 = new org.json.JSONObject
            r2.<init>()
            java.lang.String r3 = "SandboxId"
            java.lang.String r4 = "RETAIL"
            r2.put(r3, r4)
            org.json.JSONArray r3 = new org.json.JSONArray
            java.util.Set r4 = java.util.Collections.singleton(r13)
            r3.<init>(r4)
            java.lang.String r4 = "UserTokens"
            r2.put(r4, r3)
            java.lang.String r3 = "Properties"
            r1.put(r3, r2)
            java.lang.String r3 = "RelyingParty"
            java.lang.String r4 = "rp://api.minecraftservices.com/"
            r1.put(r3, r4)
            java.lang.String r3 = "TokenType"
            java.lang.String r4 = "JWT"
            r1.put(r3, r4)
            java.lang.String r3 = r1.toString()
            java.lang.String r4 = "MicroAuth"
            android.util.Log.i(r4, r3)
            java.net.URLConnection r5 = r0.openConnection()
            java.net.HttpURLConnection r5 = (java.net.HttpURLConnection) r5
            r6 = 1
            setCommonProperties(r5, r3, r6)
            java.lang.String r7 = r5.getRequestMethod()
            android.util.Log.i(r4, r7)
            r5.connect()
            java.io.OutputStream r4 = r5.getOutputStream()
            java.lang.String r7 = "UTF-8"
            byte[] r7 = r3.getBytes(r7)     // Catch:{ all -> 0x00d4 }
            r4.write(r7)     // Catch:{ all -> 0x00d4 }
            if (r4 == 0) goto L_0x0068
            r4.close()
        L_0x0068:
            int r4 = r5.getResponseCode()
            r7 = 200(0xc8, float:2.8E-43)
            if (r4 < r7) goto L_0x00cf
            int r4 = r5.getResponseCode()
            r7 = 300(0x12c, float:4.2E-43)
            if (r4 >= r7) goto L_0x00cf
            org.json.JSONObject r4 = new org.json.JSONObject
            java.io.InputStream r7 = r5.getInputStream()
            java.lang.String r7 = net.kdt.pojavlaunch.Tools.read((java.io.InputStream) r7)
            r4.<init>(r7)
            java.lang.String r7 = "DisplayClaims"
            org.json.JSONObject r7 = r4.getJSONObject(r7)
            java.lang.String r8 = "xui"
            org.json.JSONArray r7 = r7.getJSONArray(r8)
            r8 = 0
            org.json.JSONObject r7 = r7.getJSONObject(r8)
            java.lang.String r9 = "uhs"
            java.lang.String r7 = r7.getString(r9)
            java.lang.String r9 = "Token"
            java.lang.String r9 = r4.getString(r9)
            r5.disconnect()
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "Xbl Xsts = "
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r9)
            java.lang.String r11 = "; Uhs = "
            java.lang.StringBuilder r10 = r10.append(r11)
            java.lang.StringBuilder r10 = r10.append(r7)
            java.lang.String r10 = r10.toString()
            java.lang.String r11 = "MicrosoftLogin"
            android.util.Log.i(r11, r10)
            r10 = 2
            java.lang.String[] r10 = new java.lang.String[r10]
            r10[r8] = r7
            r10[r6] = r9
            return r10
        L_0x00cf:
            r12.throwResponseError(r5)
            r4 = 0
            return r4
        L_0x00d4:
            r6 = move-exception
            throw r6     // Catch:{ all -> 0x00d6 }
        L_0x00d6:
            r7 = move-exception
            if (r4 == 0) goto L_0x00e7
            if (r6 == 0) goto L_0x00e4
            r4.close()     // Catch:{ all -> 0x00df }
            goto L_0x00e7
        L_0x00df:
            r8 = move-exception
            r6.addSuppressed(r8)
            goto L_0x00e7
        L_0x00e4:
            r4.close()
        L_0x00e7:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: net.kdt.pojavlaunch.authenticator.microsoft.MicrosoftBackgroundLogin.acquireXsts(java.lang.String):java.lang.String[]");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x00ab, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x00ac, code lost:
        if (r4 != null) goto L_0x00ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00ae, code lost:
        if (r5 != null) goto L_0x00b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x00b4, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x00b5, code lost:
        r5.addSuppressed(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00b9, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00bc, code lost:
        throw r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String acquireMinecraftToken(java.lang.String r9, java.lang.String r10) throws java.io.IOException, org.json.JSONException {
        /*
            r8 = this;
            java.net.URL r0 = new java.net.URL
            java.lang.String r1 = "https://api.minecraftservices.com/authentication/login_with_xbox"
            r0.<init>(r1)
            org.json.JSONObject r1 = new org.json.JSONObject
            r1.<init>()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "XBL3.0 x="
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r9)
            java.lang.String r3 = ";"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r10)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "identityToken"
            r1.put(r3, r2)
            java.lang.String r2 = r1.toString()
            java.net.URLConnection r3 = r0.openConnection()
            java.net.HttpURLConnection r3 = (java.net.HttpURLConnection) r3
            r4 = 1
            setCommonProperties(r3, r2, r4)
            r3.connect()
            java.io.OutputStream r4 = r3.getOutputStream()
            java.lang.String r5 = "UTF-8"
            byte[] r5 = r2.getBytes(r5)     // Catch:{ all -> 0x00a9 }
            r4.write(r5)     // Catch:{ all -> 0x00a9 }
            if (r4 == 0) goto L_0x0051
            r4.close()
        L_0x0051:
            int r4 = r3.getResponseCode()
            r5 = 200(0xc8, float:2.8E-43)
            if (r4 < r5) goto L_0x00a4
            int r4 = r3.getResponseCode()
            r5 = 300(0x12c, float:4.2E-43)
            if (r4 >= r5) goto L_0x00a4
            long r4 = java.lang.System.currentTimeMillis()
            r6 = 86400000(0x5265c00, double:4.2687272E-316)
            long r4 = r4 + r6
            r8.expiresAt = r4
            org.json.JSONObject r4 = new org.json.JSONObject
            java.io.InputStream r5 = r3.getInputStream()
            java.lang.String r5 = net.kdt.pojavlaunch.Tools.read((java.io.InputStream) r5)
            r4.<init>(r5)
            r3.disconnect()
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "MC token: "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = "access_token"
            java.lang.String r7 = r4.getString(r6)
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r5 = r5.toString()
            java.lang.String r7 = "MicrosoftLogin"
            android.util.Log.i(r7, r5)
            java.lang.String r5 = r4.getString(r6)
            r8.mcToken = r5
            java.lang.String r5 = r4.getString(r6)
            return r5
        L_0x00a4:
            r8.throwResponseError(r3)
            r4 = 0
            return r4
        L_0x00a9:
            r5 = move-exception
            throw r5     // Catch:{ all -> 0x00ab }
        L_0x00ab:
            r6 = move-exception
            if (r4 == 0) goto L_0x00bc
            if (r5 == 0) goto L_0x00b9
            r4.close()     // Catch:{ all -> 0x00b4 }
            goto L_0x00bc
        L_0x00b4:
            r7 = move-exception
            r5.addSuppressed(r7)
            goto L_0x00bc
        L_0x00b9:
            r4.close()
        L_0x00bc:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: net.kdt.pojavlaunch.authenticator.microsoft.MicrosoftBackgroundLogin.acquireMinecraftToken(java.lang.String, java.lang.String):java.lang.String");
    }

    private void checkMcProfile(String mcAccessToken) throws IOException, JSONException {
        HttpURLConnection conn = (HttpURLConnection) new URL(mcProfileUrl).openConnection();
        conn.setRequestProperty("Authorization", "Bearer " + mcAccessToken);
        conn.setUseCaches(false);
        conn.connect();
        if (conn.getResponseCode() < 200 || conn.getResponseCode() >= 300) {
            Log.i("MicrosoftLogin", "It seems that this Microsoft Account does not own the game.");
            this.doesOwnGame = false;
            throwResponseError(conn);
            return;
        }
        String s = Tools.read(conn.getInputStream());
        conn.disconnect();
        Log.i("MicrosoftLogin", "profile:" + s);
        JSONObject jsonObject = new JSONObject(s);
        String name = (String) jsonObject.get("name");
        String uuidDashes = ((String) jsonObject.get("id")).replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5");
        this.doesOwnGame = true;
        Log.i("MicrosoftLogin", "UserName = " + name);
        Log.i("MicrosoftLogin", "Uuid Minecraft = " + uuidDashes);
        this.mcName = name;
        this.mcUuid = uuidDashes;
    }

    private void notifyProgress(ProgressListener listener, int step) {
        if (listener != null) {
            this.mHandler.post(new Runnable(step) {
                public final /* synthetic */ int f$1;

                {
                    this.f$1 = r2;
                }

                public final void run() {
                    ProgressListener.this.onLoginProgress(this.f$1);
                }
            });
        }
        ProgressLayout.setProgress(ProgressLayout.AUTHENTICATE_MICROSOFT, step * 20);
    }

    private static void setCommonProperties(HttpURLConnection conn, String formData, boolean interactive) {
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("charset", "utf-8");
        try {
            conn.setRequestProperty("Content-Length", Integer.toString(formData.getBytes("UTF-8").length));
            conn.setRequestMethod("POST");
        } catch (UnsupportedEncodingException | ProtocolException e) {
            Log.e("MicrosoftAuth", e.toString());
        }
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(interactive);
    }

    private static String convertToFormData(String... data) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < data.length; i += 2) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(data[i], "UTF-8")).append("=").append(URLEncoder.encode(data[i + 1], "UTF-8"));
        }
        return builder.toString();
    }

    private void throwResponseError(HttpURLConnection conn) throws IOException {
        Log.i("MicrosoftLogin", "Error code: " + conn.getResponseCode() + ": " + conn.getResponseMessage());
        throw new RuntimeException(conn.getResponseMessage());
    }
}

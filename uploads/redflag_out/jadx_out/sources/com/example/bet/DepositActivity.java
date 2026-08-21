package com.example.bet;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: DepositActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0016\u001a\u00020\u000fH\u0002J\b\u0010\u0017\u001a\u00020\u000fH\u0002J\b\u0010\u0018\u001a\u00020\u000fH\u0002J\b\u0010\u0019\u001a\u00020\u000fH\u0002J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u000fH\u0002J\b\u0010\u001d\u001a\u00020\u000fH\u0002J\u0012\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0014J\b\u0010\"\u001a\u00020\u001fH\u0002J\u0010\u0010#\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020\u000fH\u0002J\b\u0010%\u001a\u00020\u001fH\u0002J\b\u0010&\u001a\u00020\u001fH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0011X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0012R\u001a\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00150\u0014X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lcom/example/bet/DepositActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "<init>", "()V", "mainLayout", "Landroid/view/ViewGroup;", "layoutDepositDetails", "Landroid/widget/LinearLayout;", "tvSelectedMethodName", "Landroid/widget/TextView;", "etAmount", "Landroid/widget/EditText;", "btnConfirmDeposit", "Landroid/widget/Button;", "currentMethod", "", "_d", "", "[Ljava/lang/String;", "_k", "", "Lkotlin/Function0;", "_f1", "_f2", "_f3", "_f4", "_v", "", "n", "_f5", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "focusAndScroll", "handleMethodSelection", "method", "processConfirm", "showFailedTopBar", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class DepositActivity extends AppCompatActivity {
    private Button btnConfirmDeposit;
    private EditText etAmount;
    private LinearLayout layoutDepositDetails;
    private ViewGroup mainLayout;
    private TextView tvSelectedMethodName;
    private String currentMethod = "";
    private final String[] _d = {"6a7b8c9d0e1f2a3b4c5d6e7f8a9b0c1d", "f1e2d3c4b5a69788796a5b4c3d2e1f0", "9a8b7c6d5e4f3a2b1c0d9e8f7a6b5c4", "3c4d5e6f7a8b9c0d1e2f3a4b5c6d7e", "5e6f7a8b9c0d1e2f3a4b5c6d7e8f9a"};
    private final List<Function0<String>> _k = CollectionsKt.listOf((Object[]) new Function0[]{new Function0() { // from class: com.example.bet.DepositActivity$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return DepositActivity._k$lambda$0();
        }
    }, new Function0() { // from class: com.example.bet.DepositActivity$$ExternalSyntheticLambda3
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return DepositActivity._k$lambda$1();
        }
    }, new Function0() { // from class: com.example.bet.DepositActivity$$ExternalSyntheticLambda4
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return DepositActivity._k$lambda$2();
        }
    }, new Function0() { // from class: com.example.bet.DepositActivity$$ExternalSyntheticLambda5
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return DepositActivity._k$lambda$3();
        }
    }, new Function0() { // from class: com.example.bet.DepositActivity$$ExternalSyntheticLambda6
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return DepositActivity._k$lambda$4(this.f$0);
        }
    }});

    private final String _f1() {
        try {
            String str_f2 = _f2();
            String str_f3 = _f3();
            String str_f4 = _f4();
            return _v(str_f2) ? str_f2 : _v(str_f3) ? str_f3 : _v(str_f4) ? str_f4 : "01207355930";
        } catch (Exception e) {
            return _f5();
        }
    }

    private final String _f2() {
        String strJoinToString$default = ArraysKt.joinToString$default(this._d, "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, new Function1() { // from class: com.example.bet.DepositActivity$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return DepositActivity._f2$lambda$5((String) obj);
            }
        }, 30, (Object) null);
        char c = 2;
        String strInvoke = this._k.get(2).invoke();
        String str = strJoinToString$default;
        ArrayList arrayList = new ArrayList(str.length());
        int i = 0;
        int i2 = 0;
        while (i2 < str.length()) {
            arrayList.add(Character.valueOf((char) (str.charAt(i2) ^ strInvoke.charAt(i % strInvoke.length()))));
            i2++;
            i++;
            c = c;
        }
        char c2 = c;
        String strJoinToString$default2 = CollectionsKt.joinToString$default(arrayList, "", null, null, 0, null, null, 62, null);
        Integer[] numArr = new Integer[11];
        numArr[0] = 10;
        numArr[1] = 20;
        numArr[c2] = 30;
        numArr[3] = 31;
        numArr[4] = 32;
        numArr[5] = 33;
        numArr[6] = 40;
        numArr[7] = 41;
        numArr[8] = 42;
        numArr[9] = 43;
        numArr[10] = 44;
        List listListOf = CollectionsKt.listOf((Object[]) numArr);
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listListOf, 10));
        Iterator it = listListOf.iterator();
        while (it.hasNext()) {
            int iIntValue = ((Number) it.next()).intValue();
            char c3 = '0';
            if (iIntValue < strJoinToString$default2.length()) {
                char cCharAt = (char) (strJoinToString$default2.charAt(iIntValue) ^ 15);
                c3 = '0' <= cCharAt && cCharAt < ':' ? cCharAt : (char) ((cCharAt % '\n') + 48);
            }
            arrayList2.add(Character.valueOf(c3));
        }
        return CollectionsKt.joinToString$default(arrayList2, "", null, null, 0, null, null, 62, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence _f2$lambda$5(String it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it;
    }

    private final String _f3() {
        int i;
        int iHashCode = getPackageName().hashCode() & Integer.MAX_VALUE;
        String strValueOf = String.valueOf(iHashCode);
        ArrayList arrayList = new ArrayList(str.length());
        int i2 = 0;
        int i3 = 0;
        while (i3 < str.length()) {
            int i4 = i2 + 1;
            char cCharAt = str.charAt(i3);
            if (i2 < strValueOf.length()) {
                i = iHashCode;
                cCharAt = String.valueOf((Integer.parseInt(String.valueOf(cCharAt)) + Integer.parseInt(String.valueOf(strValueOf.charAt(i2)))) % 10).charAt(0);
            } else {
                i = iHashCode;
            }
            arrayList.add(Character.valueOf(cCharAt));
            i3++;
            i2 = i4;
            iHashCode = i;
        }
        return CollectionsKt.joinToString$default(arrayList, "", null, null, 0, null, null, 62, null);
    }

    private final String _f4() {
        String strReplace$default = StringsKt.replace$default("7a8b9c0d1e2f3a4b5c6d7e8f9a0b1c2d3e4f5a6b7c8d9e0f1a2b3c4d5e6f7a8b9c0d1e2fa1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6e7f8a9b0c1d2e3f4a5b6c7d8e9f0a1b2c3d4e5f3c4d5e6f7a8b9c0d1e2f3a4b5c6d7e8f9a0b1c2d3e4f5a6b7c8d9e0f1a2b3c4d5e6f7a89a0b1c2d3e4f5a6b7c8d9e0f1a2b3c4d5e6f7a8b9c0d1e2f3a4b5c6d7e8f9a0b1c2d3e4", " ", "", false, 4, (Object) null);
        List listListOf = CollectionsKt.listOf((Object[]) new Integer[]{15, 25, 35, 45, 55, 65, 75, 85, 95, 105, 115});
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listListOf, 10));
        Iterator it = listListOf.iterator();
        while (it.hasNext()) {
            int iIntValue = ((Number) it.next()).intValue();
            char cCharAt = '0';
            if (iIntValue < strReplace$default.length()) {
                cCharAt = (char) ((strReplace$default.charAt(iIntValue) % '\n') + 48);
            }
            arrayList.add(Character.valueOf(cCharAt));
        }
        return CollectionsKt.joinToString$default(arrayList, "", null, null, 0, null, null, 62, null);
    }

    private final String _f5() {
        ArrayList arrayList = new ArrayList(str.length());
        for (int i = 0; i < str.length(); i++) {
            arrayList.add(Character.valueOf((char) (str.charAt(i) ^ '\n')));
        }
        char[] charArray = CollectionsKt.joinToString$default(arrayList, "", null, null, 0, null, null, 62, null).toCharArray();
        Intrinsics.checkNotNullExpressionValue(charArray, "toCharArray(...)");
        int length = charArray.length;
        for (int i2 = 0; i2 < length; i2++) {
            charArray[i2] = (char) (charArray[i2] ^ '\n');
        }
        return new String(charArray);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String _k$lambda$0() {
        return "Z@A";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String _k$lambda$1() {
        return "key_" + StringsKt.padStart(String.valueOf(System.currentTimeMillis() % ((long) 100)), 2, '0');
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String _k$lambda$2() {
        return StringsKt.reversed((CharSequence) "68").toString() + "xyz";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String _k$lambda$3() {
        return "HDU";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String _k$lambda$4(DepositActivity depositActivity) {
        return "secure_key_" + (depositActivity.getPackageName().hashCode() & 255);
    }

    private final boolean _v(String n) {
        return new Regex("01[0-9]{9}").matches(n) && n.length() == 11;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void focusAndScroll() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.example.bet.DepositActivity$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                DepositActivity.focusAndScroll$lambda$20(this.f$0);
            }
        }, 300L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void focusAndScroll$lambda$20(DepositActivity depositActivity) {
        ViewGroup viewGroup = depositActivity.mainLayout;
        Button button = null;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mainLayout");
            viewGroup = null;
        }
        ViewParent parent = viewGroup.getParent();
        ScrollView scrollView = parent instanceof ScrollView ? (ScrollView) parent : null;
        if (scrollView != null) {
            Button button2 = depositActivity.btnConfirmDeposit;
            if (button2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btnConfirmDeposit");
            } else {
                button = button2;
            }
            scrollView.smoothScrollTo(0, button.getBottom());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleMethodSelection(final String method) {
        LinearLayout linearLayout = this.layoutDepositDetails;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutDepositDetails");
            linearLayout = null;
        }
        if (linearLayout.getVisibility() == 0) {
            ViewGroup viewGroup = this.mainLayout;
            if (viewGroup == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mainLayout");
                viewGroup = null;
            }
            TransitionManager.beginDelayedTransition(viewGroup);
            LinearLayout linearLayout3 = this.layoutDepositDetails;
            if (linearLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("layoutDepositDetails");
            } else {
                linearLayout2 = linearLayout3;
            }
            linearLayout2.setVisibility(8);
            if (Intrinsics.areEqual(this.currentMethod, method)) {
                this.currentMethod = "";
                return;
            } else {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.example.bet.DepositActivity$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        DepositActivity.handleMethodSelection$lambda$21(this.f$0, method);
                    }
                }, 400L);
                return;
            }
        }
        ViewGroup viewGroup2 = this.mainLayout;
        if (viewGroup2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mainLayout");
            viewGroup2 = null;
        }
        TransitionManager.beginDelayedTransition(viewGroup2);
        TextView textView = this.tvSelectedMethodName;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvSelectedMethodName");
            textView = null;
        }
        textView.setText(method);
        LinearLayout linearLayout4 = this.layoutDepositDetails;
        if (linearLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutDepositDetails");
        } else {
            linearLayout2 = linearLayout4;
        }
        linearLayout2.setVisibility(0);
        this.currentMethod = method;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleMethodSelection$lambda$21(DepositActivity depositActivity, String str) {
        ViewGroup viewGroup = depositActivity.mainLayout;
        LinearLayout linearLayout = null;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mainLayout");
            viewGroup = null;
        }
        TransitionManager.beginDelayedTransition(viewGroup);
        TextView textView = depositActivity.tvSelectedMethodName;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvSelectedMethodName");
            textView = null;
        }
        textView.setText(str);
        LinearLayout linearLayout2 = depositActivity.layoutDepositDetails;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutDepositDetails");
        } else {
            linearLayout = linearLayout2;
        }
        linearLayout.setVisibility(0);
        depositActivity.currentMethod = str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$11(DepositActivity depositActivity, TextView textView) {
        String str_f1 = depositActivity._f1();
        textView.setText(Intrinsics.areEqual(str_f1, "01207355930") ? str_f1 : "01207355930");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$16(DepositActivity depositActivity, TextView textView, View view) {
        Object systemService = depositActivity.getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ((ClipboardManager) systemService).setPrimaryClip(ClipData.newPlainText("admin_num", textView.getText()));
        Toast.makeText(depositActivity, "تم نسخ الرقم بنجاح", 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$19(DepositActivity depositActivity, View view, boolean z) {
        if (z) {
            depositActivity.focusAndScroll();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void processConfirm() {
        EditText editText = this.etAmount;
        Button button = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etAmount");
            editText = null;
        }
        String string = editText.getText().toString();
        if (string.length() == 0) {
            Toast.makeText(this, "يرجى إدخال المبلغ أولاً", 0).show();
            return;
        }
        Integer intOrNull = StringsKt.toIntOrNull(string);
        if ((intOrNull != null ? intOrNull.intValue() : 0) < 150) {
            Toast.makeText(this, "أقل مبلغ للإيداع هو 150 ج.م", 1).show();
            return;
        }
        Button button2 = this.btnConfirmDeposit;
        if (button2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnConfirmDeposit");
            button2 = null;
        }
        button2.setEnabled(false);
        Button button3 = this.btnConfirmDeposit;
        if (button3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnConfirmDeposit");
        } else {
            button = button3;
        }
        button.setText("جاري التحقق...");
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.example.bet.DepositActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DepositActivity.processConfirm$lambda$22(this.f$0);
            }
        }, 3000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void processConfirm$lambda$22(DepositActivity depositActivity) {
        depositActivity.showFailedTopBar();
        ViewGroup viewGroup = depositActivity.mainLayout;
        Button button = null;
        if (viewGroup == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mainLayout");
            viewGroup = null;
        }
        TransitionManager.beginDelayedTransition(viewGroup);
        LinearLayout linearLayout = depositActivity.layoutDepositDetails;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("layoutDepositDetails");
            linearLayout = null;
        }
        linearLayout.setVisibility(8);
        EditText editText = depositActivity.etAmount;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etAmount");
            editText = null;
        }
        editText.getText().clear();
        Button button2 = depositActivity.btnConfirmDeposit;
        if (button2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnConfirmDeposit");
            button2 = null;
        }
        button2.setEnabled(true);
        Button button3 = depositActivity.btnConfirmDeposit;
        if (button3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnConfirmDeposit");
        } else {
            button = button3;
        }
        button.setText("تأكيد الإيداع");
        depositActivity.currentMethod = "";
    }

    private final void showFailedTopBar() {
        Snackbar snackbarMake = Snackbar.make(findViewById(android.R.id.content), "❌ فشل الإيداع لعدم تحويل المبلغ!", 0);
        Intrinsics.checkNotNullExpressionValue(snackbarMake, "make(...)");
        View view = snackbarMake.getView();
        Intrinsics.checkNotNullExpressionValue(view, "getView(...)");
        view.setBackgroundColor(Color.parseColor("#D32F2F"));
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.FrameLayout.LayoutParams");
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
        layoutParams2.gravity = 49;
        layoutParams2.setMargins(20, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 20, 0);
        view.setLayoutParams(layoutParams2);
        TextView textView = (TextView) view.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(-1);
        textView.setTextSize(17.0f);
        textView.setTypeface(null, 1);
        textView.setGravity(17);
        textView.setTextAlignment(4);
        snackbarMake.show();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        this.mainLayout = (ViewGroup) findViewById(R.id.mainLayout);
        this.layoutDepositDetails = (LinearLayout) findViewById(R.id.layoutDepositDetails);
        this.tvSelectedMethodName = (TextView) findViewById(R.id.tvSelectedMethodName);
        this.etAmount = (EditText) findViewById(R.id.etAmount);
        this.btnConfirmDeposit = (Button) findViewById(R.id.btnConfirmDeposit);
        Button button = (Button) findViewById(R.id.btnVodafone);
        Button button2 = (Button) findViewById(R.id.btnEtisalat);
        Button button3 = (Button) findViewById(R.id.btnOrange);
        Button button4 = (Button) findViewById(R.id.btnCopyNumber);
        final TextView textView = (TextView) findViewById(R.id.tvAdminNumber);
        Button button5 = (Button) findViewById(R.id.btnBackDeposit);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.example.bet.DepositActivity$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                DepositActivity.onCreate$lambda$11(this.f$0, textView);
            }
        }, 100L);
        button5.setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.DepositActivity$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.DepositActivity$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.handleMethodSelection("فودافون كاش");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.DepositActivity$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.handleMethodSelection("اتصالات كاش");
            }
        });
        button3.setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.DepositActivity$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.handleMethodSelection("أورانج كاش");
            }
        });
        button4.setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.DepositActivity$$ExternalSyntheticLambda14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DepositActivity.onCreate$lambda$16(this.f$0, textView, view);
            }
        });
        Button button6 = this.btnConfirmDeposit;
        EditText editText = null;
        if (button6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnConfirmDeposit");
            button6 = null;
        }
        button6.setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.DepositActivity$$ExternalSyntheticLambda15
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.processConfirm();
            }
        });
        EditText editText2 = this.etAmount;
        if (editText2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etAmount");
            editText2 = null;
        }
        editText2.setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.DepositActivity$$ExternalSyntheticLambda16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.focusAndScroll();
            }
        });
        EditText editText3 = this.etAmount;
        if (editText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etAmount");
        } else {
            editText = editText3;
        }
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.example.bet.DepositActivity$$ExternalSyntheticLambda17
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
                DepositActivity.onCreate$lambda$19(this.f$0, view, z);
            }
        });
    }
}

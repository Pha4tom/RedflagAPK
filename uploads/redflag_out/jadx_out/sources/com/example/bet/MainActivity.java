package com.example.bet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: MainActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0014J\u001a\u0010\b\u001a\u00020\u00052\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002¨\u0006\r"}, d2 = {"Lcom/example/bet/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "<init>", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "scrollDown", "scrollView", "Landroid/widget/ScrollView;", TypedValues.AttributesType.S_TARGET, "Landroid/view/View;", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class MainActivity extends AppCompatActivity {
    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(MainActivity mainActivity, ScrollView scrollView, Button button, View view, boolean z) {
        if (z) {
            Intrinsics.checkNotNull(button);
            mainActivity.scrollDown(scrollView, button);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(MainActivity mainActivity, ScrollView scrollView, Button button, View view, boolean z) {
        if (z) {
            Intrinsics.checkNotNull(button);
            mainActivity.scrollDown(scrollView, button);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(EditText editText, EditText editText2, MainActivity mainActivity, View view) {
        String string = StringsKt.trim((CharSequence) editText.getText().toString()).toString();
        String string2 = StringsKt.trim((CharSequence) editText2.getText().toString()).toString();
        if (!(string.length() == 0)) {
            if (!(string2.length() == 0)) {
                try {
                    mainActivity.startActivity(new Intent(mainActivity, (Class<?>) HomeActivity.class));
                    return;
                } catch (Exception e) {
                    Toast.makeText(mainActivity, "❌ خطأ في الانتقال: " + e.getMessage(), 1).show();
                    return;
                }
            }
        }
        Toast.makeText(mainActivity, "يرجى إدخال اسم المستخدم وكلمة المرور", 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$3(MainActivity mainActivity, View view) {
        mainActivity.startActivity(new Intent(mainActivity, (Class<?>) RegisterActivity.class));
    }

    private final void scrollDown(final ScrollView scrollView, final View target) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.example.bet.MainActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.scrollDown$lambda$4(scrollView, target);
            }
        }, 300L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void scrollDown$lambda$4(ScrollView scrollView, View view) {
        if (scrollView != null) {
            scrollView.smoothScrollTo(0, view.getBottom());
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText = (EditText) findViewById(R.id.etUsername);
        final EditText editText2 = (EditText) findViewById(R.id.etPassword);
        final Button button = (Button) findViewById(R.id.btnLogin);
        TextView textView = (TextView) findViewById(R.id.tvCreateAccount);
        ViewParent parent = editText.getParent().getParent();
        final ScrollView scrollView = parent instanceof ScrollView ? (ScrollView) parent : null;
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.example.bet.MainActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
                MainActivity.onCreate$lambda$0(this.f$0, scrollView, button, view, z);
            }
        });
        editText2.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.example.bet.MainActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
                MainActivity.onCreate$lambda$1(this.f$0, scrollView, button, view, z);
            }
        });
        button.setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.MainActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.onCreate$lambda$2(editText, editText2, this, view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.MainActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.onCreate$lambda$3(this.f$0, view);
            }
        });
    }
}

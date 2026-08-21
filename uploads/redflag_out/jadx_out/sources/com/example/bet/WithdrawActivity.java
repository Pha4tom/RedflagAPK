package com.example.bet;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

/* JADX INFO: compiled from: WithdrawActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0014J\b\u0010\u0012\u001a\u00020\u000fH\u0002J\b\u0010\u0013\u001a\u00020\u000fH\u0002J\b\u0010\u0014\u001a\u00020\u000fH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/example/bet/WithdrawActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "<init>", "()V", "tvAvailableBalance", "Landroid/widget/TextView;", "etWithdrawAmount", "Landroid/widget/EditText;", "etWithdrawPhone", "btnConfirmWithdraw", "Landroid/widget/Button;", "btnBackWithdraw", "currentBalance", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "loadBalanceFromSharedPrefs", "handleWithdrawalClick", "updateBalanceDisplay", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class WithdrawActivity extends AppCompatActivity {
    private Button btnBackWithdraw;
    private Button btnConfirmWithdraw;
    private float currentBalance;
    private EditText etWithdrawAmount;
    private EditText etWithdrawPhone;
    private TextView tvAvailableBalance;

    /* JADX INFO: Access modifiers changed from: private */
    public final void handleWithdrawalClick() {
        EditText editText = this.etWithdrawAmount;
        Button button = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etWithdrawAmount");
            editText = null;
        }
        String string = editText.getText().toString();
        EditText editText2 = this.etWithdrawPhone;
        if (editText2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etWithdrawPhone");
            editText2 = null;
        }
        String string2 = editText2.getText().toString();
        if (!(string.length() == 0)) {
            if (!(string2.length() == 0)) {
                final float f = Float.parseFloat(string);
                if (f < 50.0f) {
                    Toast.makeText(this, "الحد الأدنى للسحب هو 50 جنيه", 0).show();
                    return;
                }
                if (f > this.currentBalance) {
                    Toast.makeText(this, "عفواً، رصيدك الحالي لا يكفي", 0).show();
                    return;
                }
                Button button2 = this.btnConfirmWithdraw;
                if (button2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("btnConfirmWithdraw");
                    button2 = null;
                }
                button2.setEnabled(false);
                Button button3 = this.btnConfirmWithdraw;
                if (button3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("btnConfirmWithdraw");
                    button3 = null;
                }
                button3.setText("جاري سحب المبلغ...");
                Button button4 = this.btnConfirmWithdraw;
                if (button4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("btnConfirmWithdraw");
                } else {
                    button = button4;
                }
                button.setAlpha(0.7f);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.example.bet.WithdrawActivity$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        WithdrawActivity.handleWithdrawalClick$lambda$2(this.f$0, f);
                    }
                }, 3000L);
                return;
            }
        }
        Toast.makeText(this, "يرجى ملء كافة الخانات", 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleWithdrawalClick$lambda$2(WithdrawActivity withdrawActivity, float f) {
        withdrawActivity.currentBalance -= f;
        withdrawActivity.getSharedPreferences("GamePrefs", 0).edit().putFloat("balance", withdrawActivity.currentBalance).apply();
        withdrawActivity.updateBalanceDisplay();
        Button button = withdrawActivity.btnConfirmWithdraw;
        EditText editText = null;
        if (button == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnConfirmWithdraw");
            button = null;
        }
        button.setEnabled(true);
        Button button2 = withdrawActivity.btnConfirmWithdraw;
        if (button2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnConfirmWithdraw");
            button2 = null;
        }
        button2.setText("تأكيد طلب السحب");
        Button button3 = withdrawActivity.btnConfirmWithdraw;
        if (button3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnConfirmWithdraw");
            button3 = null;
        }
        button3.setAlpha(1.0f);
        EditText editText2 = withdrawActivity.etWithdrawAmount;
        if (editText2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etWithdrawAmount");
            editText2 = null;
        }
        editText2.getText().clear();
        EditText editText3 = withdrawActivity.etWithdrawPhone;
        if (editText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etWithdrawPhone");
        } else {
            editText = editText3;
        }
        editText.getText().clear();
        Toast.makeText(withdrawActivity, "تم إرسال طلب السحب بنجاح!", 1).show();
    }

    private final void loadBalanceFromSharedPrefs() {
        this.currentBalance = getSharedPreferences("GamePrefs", 0).getFloat("balance", 0.0f);
        updateBalanceDisplay();
    }

    private final void updateBalanceDisplay() {
        TextView textView = this.tvAvailableBalance;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvAvailableBalance");
            textView = null;
        }
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format(Locale.US, "%.2f جنيه", Arrays.copyOf(new Object[]{Float.valueOf(this.currentBalance)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        textView.setText(str);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        this.tvAvailableBalance = (TextView) findViewById(R.id.tvAvailableBalance);
        this.etWithdrawAmount = (EditText) findViewById(R.id.etWithdrawAmount);
        this.etWithdrawPhone = (EditText) findViewById(R.id.etWithdrawPhone);
        this.btnConfirmWithdraw = (Button) findViewById(R.id.btnConfirmWithdraw);
        this.btnBackWithdraw = (Button) findViewById(R.id.btnBackWithdraw);
        loadBalanceFromSharedPrefs();
        Button button = this.btnBackWithdraw;
        Button button2 = null;
        if (button == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnBackWithdraw");
            button = null;
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.WithdrawActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.finish();
            }
        });
        Button button3 = this.btnConfirmWithdraw;
        if (button3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnConfirmWithdraw");
        } else {
            button2 = button3;
        }
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.WithdrawActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.handleWithdrawalClick();
            }
        });
    }
}

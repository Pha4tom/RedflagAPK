package com.example.bet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Arrays;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

/* JADX INFO: compiled from: HomeActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0014J\b\u0010\n\u001a\u00020\u0007H\u0014J\b\u0010\u000b\u001a\u00020\u0007H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/example/bet/HomeActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "<init>", "()V", "tvBalance", "Landroid/widget/TextView;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "loadBalanceFromPrefs", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class HomeActivity extends AppCompatActivity {
    private TextView tvBalance;

    private final void loadBalanceFromPrefs() {
        float f = getSharedPreferences("GamePrefs", 0).getFloat("balance", 0.0f);
        TextView textView = this.tvBalance;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvBalance");
            textView = null;
        }
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format(Locale.US, "%.2f جنيه", Arrays.copyOf(new Object[]{Float.valueOf(f)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        textView.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(HomeActivity homeActivity, View view) {
        homeActivity.startActivity(new Intent(homeActivity, (Class<?>) DepositActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(HomeActivity homeActivity, View view) {
        homeActivity.startActivity(new Intent(homeActivity, (Class<?>) WithdrawActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(HomeActivity homeActivity, View view) {
        homeActivity.startActivity(new Intent(homeActivity, (Class<?>) CrashGameActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$3(HomeActivity homeActivity, View view) {
        homeActivity.startActivity(new Intent(homeActivity, (Class<?>) AppleGameActivity.class));
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.tvBalance = (TextView) findViewById(R.id.tvBalance);
        Button button = (Button) findViewById(R.id.btnDeposit);
        Button button2 = (Button) findViewById(R.id.btnWithdraw);
        ImageView imageView = (ImageView) findViewById(R.id.cardCrash);
        ImageView imageView2 = (ImageView) findViewById(R.id.cardApple);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.HomeActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HomeActivity.onCreate$lambda$0(this.f$0, view);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.HomeActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HomeActivity.onCreate$lambda$1(this.f$0, view);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.HomeActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HomeActivity.onCreate$lambda$2(this.f$0, view);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.HomeActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                HomeActivity.onCreate$lambda$3(this.f$0, view);
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        loadBalanceFromPrefs();
    }
}

package com.example.bet;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: AppleGameActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0014J\b\u0010\u001d\u001a\u00020\u001aH\u0002J\b\u0010\u001e\u001a\u00020\u001aH\u0002J\b\u0010\u001f\u001a\u00020\u001aH\u0002J\b\u0010 \u001a\u00020\u001aH\u0002J\b\u0010!\u001a\u00020\u001aH\u0002J\b\u0010\"\u001a\u00020\u001aH\u0002J\b\u0010#\u001a\u00020\u001aH\u0002J\u0018\u0010$\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u0011H\u0002J\b\u0010(\u001a\u00020\u001aH\u0002J\b\u0010)\u001a\u00020\u001aH\u0002J\b\u0010*\u001a\u00020\u001aH\u0002J\u0010\u0010+\u001a\u00020\u001a2\u0006\u0010'\u001a\u00020\u0011H\u0002J\u0010\u0010,\u001a\u00020\u001a2\u0006\u0010'\u001a\u00020\u0011H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00130\u0018X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lcom/example/bet/AppleGameActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "<init>", "()V", "appleGrid", "Landroid/widget/GridLayout;", "gameContainer", "Landroid/widget/LinearLayout;", "etStake", "Landroid/widget/EditText;", "btnStake", "Landroid/widget/Button;", "tvBalance", "Landroid/widget/TextView;", "sharedPref", "Landroid/content/SharedPreferences;", "activeRow", "", "myBalance", "", "currentStake", "isGameRunning", "", "multipliers", "", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "loadBalance", "saveBalanceToPrefs", "startGame", "cashOut", "updateBalanceUI", "finishGame", "setupInitialClickListeners", "onRowClicked", "clickedView", "Landroid/widget/ImageView;", "row", "moveGridDown", "resetGridImages", "lockGrid", "lockRow", "unlockRow", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class AppleGameActivity extends AppCompatActivity {
    private GridLayout appleGrid;
    private Button btnStake;
    private double currentStake;
    private EditText etStake;
    private LinearLayout gameContainer;
    private boolean isGameRunning;
    private double myBalance;
    private SharedPreferences sharedPref;
    private TextView tvBalance;
    private int activeRow = 9;
    private final List<Double> multipliers = CollectionsKt.listOf((Object[]) new Double[]{Double.valueOf(69.3d), Double.valueOf(34.6d), Double.valueOf(17.3d), Double.valueOf(8.66d), Double.valueOf(4.33d), Double.valueOf(2.41d), Double.valueOf(1.93d), Double.valueOf(1.54d), Double.valueOf(1.23d), Double.valueOf(1.0d)});

    private final void cashOut() {
        double dDoubleValue = this.currentStake * this.multipliers.get(this.activeRow).doubleValue();
        this.myBalance += dDoubleValue;
        saveBalanceToPrefs();
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("%.2f", Arrays.copyOf(new Object[]{Double.valueOf(dDoubleValue)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        Toast.makeText(this, "تم سحب " + str + " ج.م", 1).show();
        finishGame();
    }

    private final void finishGame() {
        this.isGameRunning = false;
        Button button = this.btnStake;
        EditText editText = null;
        if (button == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnStake");
            button = null;
        }
        button.setText("STAKE");
        Button button2 = this.btnStake;
        if (button2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnStake");
            button2 = null;
        }
        button2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4A90E2")));
        Button button3 = this.btnStake;
        if (button3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnStake");
            button3 = null;
        }
        button3.setEnabled(true);
        EditText editText2 = this.etStake;
        if (editText2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etStake");
        } else {
            editText = editText2;
        }
        editText.setEnabled(true);
        lockGrid();
        updateBalanceUI();
    }

    private final void loadBalance() {
        SharedPreferences sharedPreferences = this.sharedPref;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sharedPref");
            sharedPreferences = null;
        }
        this.myBalance = sharedPreferences.getFloat("balance", 0.0f);
        updateBalanceUI();
    }

    private final void lockGrid() {
        GridLayout gridLayout = this.appleGrid;
        if (gridLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appleGrid");
            gridLayout = null;
        }
        int childCount = gridLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            GridLayout gridLayout2 = this.appleGrid;
            if (gridLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("appleGrid");
                gridLayout2 = null;
            }
            gridLayout2.getChildAt(i).setClickable(false);
        }
    }

    private final void lockRow(int row) {
        int i = row * 5;
        int i2 = i + 5;
        for (int i3 = i; i3 < i2; i3++) {
            GridLayout gridLayout = this.appleGrid;
            if (gridLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("appleGrid");
                gridLayout = null;
            }
            gridLayout.getChildAt(i3).setClickable(false);
        }
    }

    private final void moveGridDown() {
        float f = 52 * getResources().getDisplayMetrics().density;
        LinearLayout linearLayout = this.gameContainer;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("gameContainer");
            linearLayout = null;
        }
        linearLayout.animate().translationYBy(f).setDuration(400L).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(AppleGameActivity appleGameActivity, View view) {
        EditText editText = appleGameActivity.etStake;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etStake");
            editText = null;
        }
        editText.setText("10");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(AppleGameActivity appleGameActivity, View view) {
        EditText editText = appleGameActivity.etStake;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etStake");
            editText = null;
        }
        editText.setText(String.valueOf((int) appleGameActivity.myBalance));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$3(AppleGameActivity appleGameActivity, View view) {
        EditText editText = appleGameActivity.etStake;
        EditText editText2 = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etStake");
            editText = null;
        }
        Double doubleOrNull = StringsKt.toDoubleOrNull(editText.getText().toString());
        double dDoubleValue = doubleOrNull != null ? doubleOrNull.doubleValue() : 0.0d;
        EditText editText3 = appleGameActivity.etStake;
        if (editText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etStake");
        } else {
            editText2 = editText3;
        }
        editText2.setText(String.valueOf(((double) 2) * dDoubleValue));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$4(AppleGameActivity appleGameActivity, View view) {
        EditText editText = appleGameActivity.etStake;
        EditText editText2 = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etStake");
            editText = null;
        }
        Double doubleOrNull = StringsKt.toDoubleOrNull(editText.getText().toString());
        double dDoubleValue = doubleOrNull != null ? doubleOrNull.doubleValue() : 0.0d;
        if (dDoubleValue > 0.0d) {
            EditText editText3 = appleGameActivity.etStake;
            if (editText3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("etStake");
            } else {
                editText2 = editText3;
            }
            editText2.setText(String.valueOf(dDoubleValue / ((double) 2)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$5(AppleGameActivity appleGameActivity, View view) {
        if (appleGameActivity.isGameRunning) {
            appleGameActivity.cashOut();
        } else {
            appleGameActivity.startGame();
        }
    }

    private final void onRowClicked(ImageView clickedView, int row) {
        clickedView.setImageResource(R.drawable.saha);
        lockRow(row);
        clickedView.postDelayed(new Runnable() { // from class: com.example.bet.AppleGameActivity$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                AppleGameActivity.onRowClicked$lambda$8(this.f$0);
            }
        }, 300L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onRowClicked$lambda$8(AppleGameActivity appleGameActivity) {
        if (appleGameActivity.activeRow <= 0) {
            appleGameActivity.cashOut();
            return;
        }
        appleGameActivity.moveGridDown();
        appleGameActivity.activeRow--;
        appleGameActivity.unlockRow(appleGameActivity.activeRow);
        double dDoubleValue = appleGameActivity.currentStake * appleGameActivity.multipliers.get(appleGameActivity.activeRow).doubleValue();
        Button button = appleGameActivity.btnStake;
        if (button == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnStake");
            button = null;
        }
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("%.2f", Arrays.copyOf(new Object[]{Double.valueOf(dDoubleValue)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        button.setText("TAKE\n" + str);
    }

    private final void resetGridImages() {
        GridLayout gridLayout = this.appleGrid;
        if (gridLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appleGrid");
            gridLayout = null;
        }
        int childCount = gridLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            GridLayout gridLayout2 = this.appleGrid;
            if (gridLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("appleGrid");
                gridLayout2 = null;
            }
            View childAt = gridLayout2.getChildAt(i);
            Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type android.widget.ImageView");
            ImageView imageView = (ImageView) childAt;
            if (i >= 45) {
                imageView.setImageResource(R.drawable.sah);
            } else {
                imageView.setImageResource(R.drawable.far);
            }
            imageView.setClickable(false);
        }
    }

    private final void saveBalanceToPrefs() {
        SharedPreferences sharedPreferences = this.sharedPref;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sharedPref");
            sharedPreferences = null;
        }
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.putFloat("balance", (float) this.myBalance);
        editorEdit.apply();
    }

    private final void setupInitialClickListeners() {
        GridLayout gridLayout = this.appleGrid;
        if (gridLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("appleGrid");
            gridLayout = null;
        }
        int childCount = gridLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            GridLayout gridLayout2 = this.appleGrid;
            if (gridLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("appleGrid");
                gridLayout2 = null;
            }
            View childAt = gridLayout2.getChildAt(i);
            Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type android.widget.ImageView");
            final ImageView imageView = (ImageView) childAt;
            final int i2 = i / 5;
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.AppleGameActivity$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AppleGameActivity.setupInitialClickListeners$lambda$7(this.f$0, i2, imageView, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupInitialClickListeners$lambda$7(AppleGameActivity appleGameActivity, int i, ImageView imageView, View view) {
        if (appleGameActivity.isGameRunning && i == appleGameActivity.activeRow) {
            appleGameActivity.onRowClicked(imageView, i);
        }
    }

    private final void startGame() {
        EditText editText = this.etStake;
        LinearLayout linearLayout = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etStake");
            editText = null;
        }
        Double doubleOrNull = StringsKt.toDoubleOrNull(editText.getText().toString());
        double dDoubleValue = doubleOrNull != null ? doubleOrNull.doubleValue() : 0.0d;
        if (dDoubleValue <= 0.0d || dDoubleValue > this.myBalance) {
            Toast.makeText(this, "رصيد غير كافٍ!", 0).show();
            return;
        }
        this.currentStake = dDoubleValue;
        this.myBalance -= dDoubleValue;
        saveBalanceToPrefs();
        updateBalanceUI();
        this.isGameRunning = true;
        EditText editText2 = this.etStake;
        if (editText2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etStake");
            editText2 = null;
        }
        editText2.setEnabled(false);
        Button button = this.btnStake;
        if (button == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnStake");
            button = null;
        }
        button.setText("TAKE");
        Button button2 = this.btnStake;
        if (button2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnStake");
            button2 = null;
        }
        button2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFA500")));
        LinearLayout linearLayout2 = this.gameContainer;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("gameContainer");
        } else {
            linearLayout = linearLayout2;
        }
        linearLayout.setTranslationY(0.0f);
        this.activeRow = 9;
        resetGridImages();
        unlockRow(this.activeRow);
    }

    private final void unlockRow(int row) {
        int i = row * 5;
        int i2 = i + 5;
        for (int i3 = i; i3 < i2; i3++) {
            GridLayout gridLayout = this.appleGrid;
            if (gridLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("appleGrid");
                gridLayout = null;
            }
            View childAt = gridLayout.getChildAt(i3);
            Intrinsics.checkNotNull(childAt, "null cannot be cast to non-null type android.widget.ImageView");
            ImageView imageView = (ImageView) childAt;
            imageView.setImageResource(R.drawable.sah);
            imageView.setClickable(true);
        }
    }

    private final void updateBalanceUI() {
        TextView textView = this.tvBalance;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvBalance");
            textView = null;
        }
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("%.2f ج.م", Arrays.copyOf(new Object[]{Double.valueOf(this.myBalance)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        textView.setText(str);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apple_game);
        this.sharedPref = getSharedPreferences("GamePrefs", 0);
        this.appleGrid = (GridLayout) findViewById(R.id.appleGrid);
        this.gameContainer = (LinearLayout) findViewById(R.id.gameContainer);
        this.etStake = (EditText) findViewById(R.id.etStake);
        this.btnStake = (Button) findViewById(R.id.btnStake);
        this.tvBalance = (TextView) findViewById(R.id.tvBalance);
        ImageView imageView = (ImageView) findViewById(R.id.btnBackApple);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.AppleGameActivity$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.finish();
                }
            });
        }
        loadBalance();
        ((Button) findViewById(R.id.btnMin)).setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.AppleGameActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppleGameActivity.onCreate$lambda$1(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btnMax)).setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.AppleGameActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppleGameActivity.onCreate$lambda$2(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btnX2)).setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.AppleGameActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppleGameActivity.onCreate$lambda$3(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btnHalf)).setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.AppleGameActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppleGameActivity.onCreate$lambda$4(this.f$0, view);
            }
        });
        Button button = this.btnStake;
        if (button == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnStake");
            button = null;
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.AppleGameActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppleGameActivity.onCreate$lambda$5(this.f$0, view);
            }
        });
        setupInitialClickListeners();
        lockGrid();
    }
}

package com.example.bet;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.random.Random;

/* JADX INFO: compiled from: CrashGameActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\u0018\u00002\u00020\u0001:\u0001FB\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u00101\u001a\u0002022\b\u00103\u001a\u0004\u0018\u000104H\u0014J\b\u00105\u001a\u000202H\u0002J\b\u00106\u001a\u000202H\u0002J\b\u00107\u001a\u000202H\u0002J\b\u00108\u001a\u000202H\u0002J\b\u00109\u001a\u000202H\u0002J\b\u0010:\u001a\u000202H\u0002J\b\u0010;\u001a\u000202H\u0002J\b\u0010<\u001a\u000202H\u0002J\b\u0010=\u001a\u000202H\u0002J\b\u0010>\u001a\u000202H\u0002J\b\u0010?\u001a\u000202H\u0002J\u0010\u0010@\u001a\u0002022\u0006\u0010A\u001a\u00020\u001dH\u0002J\b\u0010B\u001a\u000202H\u0002J\u0018\u0010C\u001a\u00020\u00072\u0006\u0010D\u001a\u00020/2\u0006\u0010E\u001a\u00020!H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020'X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020)X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010-\u001a\b\u0012\u0004\u0012\u00020/0.X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00100\u001a\b\u0012\u0004\u0012\u00020/0.X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006G"}, d2 = {"Lcom/example/bet/CrashGameActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "<init>", "()V", "imgPlane", "Landroid/widget/ImageView;", "tvMultiplier", "Landroid/widget/TextView;", "tvGameBalance", "tvNextCrash", "lineView", "Landroid/view/View;", "btnPlaceBet", "Landroid/widget/Button;", "btnCashOut", "etBetAmount", "Landroid/widget/EditText;", "historyTable", "Landroid/widget/TableLayout;", "btnBack", "winOverlay", "Landroid/widget/LinearLayout;", "tvWinAmountMessage", "multiplier", "", "isFlying", "", "isWaiting", "currentBalance", "", "betAmount", "crashPoint", "frameCount", "", "userPlacedBet", "userHasCashedOut", "handler", "Landroid/os/Handler;", "path", "Landroid/graphics/Path;", "paint", "Landroid/graphics/Paint;", "currentRoundPlayers", "", "Lcom/example/bet/CrashGameActivity$FakePlayer;", "namePrefix", "", "", "nameSuffix", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "initViews", "startGlobalGameLoop", "waitingPhase", "flyingPhase", "doCrash", "updateMovementAndLine", "setupFakePlayersForRound", "checkFakePlayersCashOut", "placeUserBet", "cashOut", "loadBalance", "saveBalance", "newBalance", "updateBalanceUI", "createCell", "t", "g", "FakePlayer", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class CrashGameActivity extends AppCompatActivity {
    private double betAmount;
    private ImageView btnBack;
    private Button btnCashOut;
    private Button btnPlaceBet;
    private double crashPoint;
    private float currentBalance;
    private final List<FakePlayer> currentRoundPlayers;
    private EditText etBetAmount;
    private int frameCount;
    private TableLayout historyTable;
    private ImageView imgPlane;
    private boolean isFlying;
    private View lineView;
    private final List<String> namePrefix;
    private final List<String> nameSuffix;
    private final Paint paint;
    private TextView tvGameBalance;
    private TextView tvMultiplier;
    private TextView tvNextCrash;
    private TextView tvWinAmountMessage;
    private boolean userHasCashedOut;
    private boolean userPlacedBet;
    private LinearLayout winOverlay;
    private double multiplier = 1.0d;
    private boolean isWaiting = true;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Path path = new Path();

    /* JADX INFO: compiled from: CrashGameActivity.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001c\b\u0082\b\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u000b¢\u0006\u0004\b\r\u0010\u000eJ\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001f\u001a\u00020\tHÆ\u0003J\t\u0010 \u001a\u00020\u000bHÆ\u0003J\t\u0010!\u001a\u00020\u000bHÆ\u0003JE\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000bHÆ\u0001J\u0013\u0010#\u001a\u00020\t2\b\u0010$\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010%\u001a\u00020\u0005HÖ\u0001J\t\u0010&\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\f\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001a¨\u0006'"}, d2 = {"Lcom/example/bet/CrashGameActivity$FakePlayer;", "", "name", "", "betVal", "", "targetMultiplier", "", "hasCashedOut", "", "oddsTv", "Landroid/widget/TextView;", "winTv", "<init>", "(Ljava/lang/String;IDZLandroid/widget/TextView;Landroid/widget/TextView;)V", "getName", "()Ljava/lang/String;", "getBetVal", "()I", "getTargetMultiplier", "()D", "getHasCashedOut", "()Z", "setHasCashedOut", "(Z)V", "getOddsTv", "()Landroid/widget/TextView;", "getWinTv", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "toString", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    private static final /* data */ class FakePlayer {
        private final int betVal;
        private boolean hasCashedOut;
        private final String name;
        private final TextView oddsTv;
        private final double targetMultiplier;
        private final TextView winTv;

        public FakePlayer(String name, int i, double d, boolean z, TextView oddsTv, TextView winTv) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(oddsTv, "oddsTv");
            Intrinsics.checkNotNullParameter(winTv, "winTv");
            this.name = name;
            this.betVal = i;
            this.targetMultiplier = d;
            this.hasCashedOut = z;
            this.oddsTv = oddsTv;
            this.winTv = winTv;
        }

        public /* synthetic */ FakePlayer(String str, int i, double d, boolean z, TextView textView, TextView textView2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, i, d, (i2 & 8) != 0 ? false : z, textView, textView2);
        }

        public static /* synthetic */ FakePlayer copy$default(FakePlayer fakePlayer, String str, int i, double d, boolean z, TextView textView, TextView textView2, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                str = fakePlayer.name;
            }
            if ((i2 & 2) != 0) {
                i = fakePlayer.betVal;
            }
            if ((i2 & 4) != 0) {
                d = fakePlayer.targetMultiplier;
            }
            if ((i2 & 8) != 0) {
                z = fakePlayer.hasCashedOut;
            }
            if ((i2 & 16) != 0) {
                textView = fakePlayer.oddsTv;
            }
            if ((i2 & 32) != 0) {
                textView2 = fakePlayer.winTv;
            }
            TextView textView3 = textView2;
            boolean z2 = z;
            double d2 = d;
            return fakePlayer.copy(str, i, d2, z2, textView, textView3);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getName() {
            return this.name;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final int getBetVal() {
            return this.betVal;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final double getTargetMultiplier() {
            return this.targetMultiplier;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final boolean getHasCashedOut() {
            return this.hasCashedOut;
        }

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final TextView getOddsTv() {
            return this.oddsTv;
        }

        /* JADX INFO: renamed from: component6, reason: from getter */
        public final TextView getWinTv() {
            return this.winTv;
        }

        public final FakePlayer copy(String name, int betVal, double targetMultiplier, boolean hasCashedOut, TextView oddsTv, TextView winTv) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(oddsTv, "oddsTv");
            Intrinsics.checkNotNullParameter(winTv, "winTv");
            return new FakePlayer(name, betVal, targetMultiplier, hasCashedOut, oddsTv, winTv);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof FakePlayer)) {
                return false;
            }
            FakePlayer fakePlayer = (FakePlayer) other;
            return Intrinsics.areEqual(this.name, fakePlayer.name) && this.betVal == fakePlayer.betVal && Double.compare(this.targetMultiplier, fakePlayer.targetMultiplier) == 0 && this.hasCashedOut == fakePlayer.hasCashedOut && Intrinsics.areEqual(this.oddsTv, fakePlayer.oddsTv) && Intrinsics.areEqual(this.winTv, fakePlayer.winTv);
        }

        public final int getBetVal() {
            return this.betVal;
        }

        public final boolean getHasCashedOut() {
            return this.hasCashedOut;
        }

        public final String getName() {
            return this.name;
        }

        public final TextView getOddsTv() {
            return this.oddsTv;
        }

        public final double getTargetMultiplier() {
            return this.targetMultiplier;
        }

        public final TextView getWinTv() {
            return this.winTv;
        }

        public int hashCode() {
            return (((((((((this.name.hashCode() * 31) + Integer.hashCode(this.betVal)) * 31) + Double.hashCode(this.targetMultiplier)) * 31) + Boolean.hashCode(this.hasCashedOut)) * 31) + this.oddsTv.hashCode()) * 31) + this.winTv.hashCode();
        }

        public final void setHasCashedOut(boolean z) {
            this.hasCashedOut = z;
        }

        public String toString() {
            return "FakePlayer(name=" + this.name + ", betVal=" + this.betVal + ", targetMultiplier=" + this.targetMultiplier + ", hasCashedOut=" + this.hasCashedOut + ", oddsTv=" + this.oddsTv + ", winTv=" + this.winTv + ")";
        }
    }

    public CrashGameActivity() {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#FFD700"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(9.0f);
        paint.setAntiAlias(true);
        paint.setShadowLayer(15.0f, 0.0f, 0.0f, Color.parseColor("#FFA500"));
        this.paint = paint;
        this.currentRoundPlayers = new ArrayList();
        this.namePrefix = CollectionsKt.listOf((Object[]) new String[]{"Ahmed", "Mido", "King", "User", "Capt", "Lion", "Dev", "Shadow", "Eagle", "Star"});
        this.nameSuffix = CollectionsKt.listOf((Object[]) new String[]{"_99", "77", "X", "Pro", "2024", "_sh", "123", "VIP", "01"});
    }

    private final void cashOut() {
        this.userHasCashedOut = true;
        float f = (float) (this.betAmount * this.multiplier);
        this.currentBalance += f;
        saveBalance(this.currentBalance);
        updateBalanceUI();
        Button button = this.btnCashOut;
        LinearLayout linearLayout = null;
        if (button == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnCashOut");
            button = null;
        }
        button.setVisibility(8);
        TextView textView = this.tvWinAmountMessage;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvWinAmountMessage");
            textView = null;
        }
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(f)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        textView.setText("فزت بـ " + str + " ج.م");
        LinearLayout linearLayout2 = this.winOverlay;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("winOverlay");
        } else {
            linearLayout = linearLayout2;
        }
        linearLayout.setVisibility(0);
        StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
        String str2 = String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(f)}, 1));
        Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
        Toast.makeText(this, "ربحت " + str2 + " ج.م", 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void checkFakePlayersCashOut() {
        for (FakePlayer fakePlayer : this.currentRoundPlayers) {
            if (!fakePlayer.getHasCashedOut() && this.multiplier >= fakePlayer.getTargetMultiplier()) {
                fakePlayer.setHasCashedOut(true);
                TextView oddsTv = fakePlayer.getOddsTv();
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                String str = String.format(Locale.US, "%.2fx", Arrays.copyOf(new Object[]{Double.valueOf(fakePlayer.getTargetMultiplier())}, 1));
                Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                oddsTv.setText(str);
                fakePlayer.getOddsTv().setTextColor(Color.parseColor("#4CAF50"));
                double betVal = ((double) fakePlayer.getBetVal()) * fakePlayer.getTargetMultiplier();
                TextView winTv = fakePlayer.getWinTv();
                StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                String str2 = String.format(Locale.US, "%.1f", Arrays.copyOf(new Object[]{Double.valueOf(betVal)}, 1));
                Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
                winTv.setText(str2);
                fakePlayer.getWinTv().setTextColor(Color.parseColor("#FFD700"));
            }
        }
    }

    private final TextView createCell(String t, int g) {
        int i;
        TextView textView = new TextView(this);
        textView.setText(t);
        textView.setTextColor(-1);
        textView.setTextSize(12.0f);
        switch (g) {
            case GravityCompat.START /* 8388611 */:
                i = 3;
                break;
            case 8388612:
            default:
                i = g;
                break;
            case GravityCompat.END /* 8388613 */:
                i = 5;
                break;
        }
        textView.setGravity(i);
        textView.setLayoutParams(new TableRow.LayoutParams(0, -2, 1.0f));
        return textView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void doCrash() {
        this.isFlying = false;
        TextView textView = this.tvMultiplier;
        ImageView imageView = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvMultiplier");
            textView = null;
        }
        textView.setText("CRASHED!");
        TextView textView2 = this.tvMultiplier;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvMultiplier");
            textView2 = null;
        }
        textView2.setTextColor(SupportMenu.CATEGORY_MASK);
        for (FakePlayer fakePlayer : this.currentRoundPlayers) {
            if (!fakePlayer.getHasCashedOut()) {
                fakePlayer.getOddsTv().setText("LOST");
                fakePlayer.getOddsTv().setTextColor(SupportMenu.CATEGORY_MASK);
                fakePlayer.getWinTv().setText("0");
            }
        }
        ImageView imageView2 = this.imgPlane;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgPlane");
        } else {
            imageView = imageView2;
        }
        imageView.animate().alpha(0.0f).scaleX(0.0f).scaleY(0.0f).setDuration(200L).start();
        this.handler.postDelayed(new Runnable() { // from class: com.example.bet.CrashGameActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.waitingPhase();
            }
        }, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void flyingPhase() {
        this.isWaiting = false;
        this.isFlying = true;
        this.multiplier = 1.0d;
        this.frameCount = 0;
        this.crashPoint = Random.INSTANCE.nextDouble(6.0d, 15.0d);
        TextView textView = this.tvNextCrash;
        Button button = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvNextCrash");
            textView = null;
        }
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format(Locale.US, "ستنفجر عند: %.2fx", Arrays.copyOf(new Object[]{Double.valueOf(this.crashPoint)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        textView.setText(str);
        ImageView imageView = this.imgPlane;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgPlane");
            imageView = null;
        }
        imageView.setVisibility(0);
        ImageView imageView2 = this.imgPlane;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgPlane");
            imageView2 = null;
        }
        imageView2.setTranslationX(0.0f);
        ImageView imageView3 = this.imgPlane;
        if (imageView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgPlane");
            imageView3 = null;
        }
        imageView3.setTranslationY(0.0f);
        ImageView imageView4 = this.imgPlane;
        if (imageView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgPlane");
            imageView4 = null;
        }
        imageView4.setAlpha(1.0f);
        ImageView imageView5 = this.imgPlane;
        if (imageView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgPlane");
            imageView5 = null;
        }
        imageView5.setScaleX(1.0f);
        ImageView imageView6 = this.imgPlane;
        if (imageView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgPlane");
            imageView6 = null;
        }
        imageView6.setScaleY(1.0f);
        this.path.reset();
        if (this.userPlacedBet) {
            Button button2 = this.btnPlaceBet;
            if (button2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btnPlaceBet");
                button2 = null;
            }
            button2.setVisibility(8);
            Button button3 = this.btnCashOut;
            if (button3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btnCashOut");
            } else {
                button = button3;
            }
            button.setVisibility(0);
        }
        this.handler.post(new Runnable() { // from class: com.example.bet.CrashGameActivity$flyingPhase$flyingRunnable$1
            @Override // java.lang.Runnable
            public void run() {
                if (this.this$0.isFlying) {
                    this.this$0.multiplier += this.this$0.multiplier < 2.0d ? 0.04d : this.this$0.multiplier < 5.0d ? 0.08d : 0.15d;
                    TextView textView2 = this.this$0.tvMultiplier;
                    Button button4 = null;
                    if (textView2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tvMultiplier");
                        textView2 = null;
                    }
                    StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
                    String str2 = String.format(Locale.US, "%.2fx", Arrays.copyOf(new Object[]{Double.valueOf(this.this$0.multiplier)}, 1));
                    Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
                    textView2.setText(str2);
                    if (this.this$0.userPlacedBet && !this.this$0.userHasCashedOut) {
                        Button button5 = this.this$0.btnCashOut;
                        if (button5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("btnCashOut");
                        } else {
                            button4 = button5;
                        }
                        StringCompanionObject stringCompanionObject3 = StringCompanionObject.INSTANCE;
                        String str3 = String.format(Locale.US, "سحب\n%.2f ج.م", Arrays.copyOf(new Object[]{Double.valueOf(this.this$0.betAmount * this.this$0.multiplier)}, 1));
                        Intrinsics.checkNotNullExpressionValue(str3, "format(...)");
                        button4.setText(str3);
                    }
                    this.this$0.updateMovementAndLine();
                    this.this$0.checkFakePlayersCashOut();
                    if (this.this$0.multiplier >= this.this$0.crashPoint) {
                        this.this$0.doCrash();
                    } else {
                        this.this$0.handler.postDelayed(this, 30L);
                    }
                }
            }
        });
    }

    private final void initViews() {
        this.historyTable = (TableLayout) findViewById(R.id.historyTable);
        this.imgPlane = (ImageView) findViewById(R.id.imgPlane);
        this.tvMultiplier = (TextView) findViewById(R.id.tvMultiplier);
        this.tvGameBalance = (TextView) findViewById(R.id.tvGameBalance);
        this.tvNextCrash = (TextView) findViewById(R.id.tvNextCrash);
        this.lineView = findViewById(R.id.lineView);
        this.btnPlaceBet = (Button) findViewById(R.id.btnPlaceBet);
        this.btnCashOut = (Button) findViewById(R.id.btnCashOut);
        this.etBetAmount = (EditText) findViewById(R.id.etBetAmount);
        this.btnBack = (ImageView) findViewById(R.id.btnBack);
        this.winOverlay = (LinearLayout) findViewById(R.id.winOverlay);
        this.tvWinAmountMessage = (TextView) findViewById(R.id.tvWinAmountMessage);
        View view = this.lineView;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lineView");
            view = null;
        }
        view.setLayerType(1, null);
    }

    private final void loadBalance() {
        this.currentBalance = getSharedPreferences("GamePrefs", 0).getFloat("balance", 0.0f);
        updateBalanceUI();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(CrashGameActivity crashGameActivity, View view) {
        if (crashGameActivity.isWaiting && !crashGameActivity.userPlacedBet) {
            crashGameActivity.placeUserBet();
        } else {
            if (crashGameActivity.isWaiting) {
                return;
            }
            Toast.makeText(crashGameActivity, "انتظر الجولة القادمة!", 0).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$3(CrashGameActivity crashGameActivity, View view) {
        if (!crashGameActivity.isFlying || crashGameActivity.userHasCashedOut) {
            return;
        }
        crashGameActivity.cashOut();
    }

    private final void placeUserBet() {
        EditText editText = this.etBetAmount;
        Button button = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("etBetAmount");
            editText = null;
        }
        String string = editText.getText().toString();
        if (string.length() > 0) {
            double d = Double.parseDouble(string);
            if (((float) d) > this.currentBalance || d <= 0.0d) {
                Toast.makeText(this, "رصيدك غير كافي!", 0).show();
                return;
            }
            this.betAmount = d;
            this.currentBalance -= (float) d;
            saveBalance(this.currentBalance);
            updateBalanceUI();
            this.userPlacedBet = true;
            Button button2 = this.btnPlaceBet;
            if (button2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btnPlaceBet");
                button2 = null;
            }
            button2.setEnabled(false);
            Button button3 = this.btnPlaceBet;
            if (button3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("btnPlaceBet");
            } else {
                button = button3;
            }
            button.setText("تم الرهان");
        }
    }

    private final void saveBalance(float newBalance) {
        getSharedPreferences("GamePrefs", 0).edit().putFloat("balance", newBalance).apply();
    }

    private final void setupFakePlayersForRound() {
        this.currentRoundPlayers.clear();
        TableLayout tableLayout = this.historyTable;
        if (tableLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("historyTable");
            tableLayout = null;
        }
        if (tableLayout.getChildCount() > 1) {
            TableLayout tableLayout2 = this.historyTable;
            if (tableLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("historyTable");
                tableLayout2 = null;
            }
            TableLayout tableLayout3 = this.historyTable;
            if (tableLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("historyTable");
                tableLayout3 = null;
            }
            tableLayout2.removeViews(1, tableLayout3.getChildCount() - 1);
        }
        int iNextInt = Random.INSTANCE.nextInt(20, 35);
        for (int i = 0; i < iNextInt; i++) {
            String string = new StringBuilder().append(CollectionsKt.random(this.namePrefix, Random.INSTANCE)).append(CollectionsKt.random(this.nameSuffix, Random.INSTANCE)).toString();
            int iIntValue = ((Number) CollectionsKt.random(CollectionsKt.listOf((Object[]) new Integer[]{10, 20, 50, 100, Integer.valueOf(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION), 500, 1000}), Random.INSTANCE)).intValue();
            double dNextDouble = (Random.INSTANCE.nextDouble() * Random.INSTANCE.nextDouble() * ((double) 10)) + 1.1d;
            TableRow tableRow = new TableRow(this);
            tableRow.setPadding(0, 15, 0, 15);
            TextView textViewCreateCell = createCell("-", 17);
            TextView textViewCreateCell2 = createCell("-", 5);
            tableRow.addView(createCell(string, 3));
            tableRow.addView(textViewCreateCell);
            tableRow.addView(createCell(iIntValue + " ج.م", 17));
            tableRow.addView(textViewCreateCell2);
            TableLayout tableLayout4 = this.historyTable;
            if (tableLayout4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("historyTable");
                tableLayout4 = null;
            }
            tableLayout4.addView(tableRow);
            this.currentRoundPlayers.add(new FakePlayer(string, iIntValue, dNextDouble, false, textViewCreateCell, textViewCreateCell2));
        }
    }

    private final void startGlobalGameLoop() {
        waitingPhase();
    }

    private final void updateBalanceUI() {
        TextView textView = this.tvGameBalance;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvGameBalance");
            textView = null;
        }
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str = String.format(Locale.US, "%.2f ج.م", Arrays.copyOf(new Object[]{Float.valueOf(this.currentBalance)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        textView.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateMovementAndLine() {
        this.frameCount++;
        View view = this.lineView;
        View view2 = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lineView");
            view = null;
        }
        float height = view.getHeight() - 100.0f;
        ImageView imageView = this.imgPlane;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgPlane");
            imageView = null;
        }
        if (imageView.getTranslationX() < 400.0f) {
            ImageView imageView2 = this.imgPlane;
            if (imageView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imgPlane");
                imageView2 = null;
            }
            imageView2.setTranslationX(imageView2.getTranslationX() + 3.0f);
        }
        ImageView imageView3 = this.imgPlane;
        if (imageView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgPlane");
            imageView3 = null;
        }
        if (imageView3.getTranslationY() > -250.0f) {
            ImageView imageView4 = this.imgPlane;
            if (imageView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imgPlane");
                imageView4 = null;
            }
            imageView4.setTranslationY(imageView4.getTranslationY() - 1.8f);
        }
        ImageView imageView5 = this.imgPlane;
        if (imageView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgPlane");
            imageView5 = null;
        }
        float x = imageView5.getX();
        ImageView imageView6 = this.imgPlane;
        if (imageView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgPlane");
            imageView6 = null;
        }
        float width = x + (imageView6.getWidth() / 2.0f);
        ImageView imageView7 = this.imgPlane;
        if (imageView7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgPlane");
            imageView7 = null;
        }
        float y = imageView7.getY();
        ImageView imageView8 = this.imgPlane;
        if (imageView8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgPlane");
            imageView8 = null;
        }
        float height2 = y + (imageView8.getHeight() / 2.0f) + ((float) (Math.sin(((double) this.frameCount) * 0.1d) * ((double) 5)));
        this.path.reset();
        this.path.moveTo(100.0f, height);
        float f = (100.0f + width) / 2;
        ImageView imageView9 = this.imgPlane;
        if (imageView9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgPlane");
            imageView9 = null;
        }
        this.path.quadTo(f, (imageView9.getTranslationY() * 0.2f) + height + 30.0f, width, height2);
        View view3 = this.lineView;
        if (view3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lineView");
        } else {
            view2 = view3;
        }
        view2.setBackground(new Drawable() { // from class: com.example.bet.CrashGameActivity.updateMovementAndLine.1
            @Override // android.graphics.drawable.Drawable
            public void draw(Canvas canvas) {
                Intrinsics.checkNotNullParameter(canvas, "canvas");
                canvas.drawPath(CrashGameActivity.this.path, CrashGameActivity.this.paint);
            }

            @Override // android.graphics.drawable.Drawable
            public int getOpacity() {
                return -3;
            }

            @Override // android.graphics.drawable.Drawable
            public void setAlpha(int a) {
            }

            @Override // android.graphics.drawable.Drawable
            public void setColorFilter(ColorFilter cf) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void waitingPhase() {
        this.isWaiting = true;
        this.isFlying = false;
        this.userHasCashedOut = false;
        this.userPlacedBet = false;
        LinearLayout linearLayout = this.winOverlay;
        TextView textView = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("winOverlay");
            linearLayout = null;
        }
        linearLayout.setVisibility(8);
        Button button = this.btnPlaceBet;
        if (button == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnPlaceBet");
            button = null;
        }
        button.setEnabled(true);
        Button button2 = this.btnPlaceBet;
        if (button2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnPlaceBet");
            button2 = null;
        }
        button2.setText("PLACE A BET");
        Button button3 = this.btnPlaceBet;
        if (button3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnPlaceBet");
            button3 = null;
        }
        button3.setVisibility(0);
        Button button4 = this.btnCashOut;
        if (button4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnCashOut");
            button4 = null;
        }
        button4.setVisibility(8);
        ImageView imageView = this.imgPlane;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgPlane");
            imageView = null;
        }
        imageView.setVisibility(4);
        TextView textView2 = this.tvMultiplier;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvMultiplier");
            textView2 = null;
        }
        textView2.setTextColor(-1);
        TextView textView3 = this.tvNextCrash;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvNextCrash");
        } else {
            textView = textView3;
        }
        textView.setText("انتظار الجولة...");
        final Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = 6;
        this.handler.post(new Runnable() { // from class: com.example.bet.CrashGameActivity$waitingPhase$countdownRunnable$1
            @Override // java.lang.Runnable
            public void run() {
                if (intRef.element <= 0) {
                    this.flyingPhase();
                    return;
                }
                TextView textView4 = this.tvMultiplier;
                if (textView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvMultiplier");
                    textView4 = null;
                }
                textView4.setText("تبدأ خلال: " + intRef.element);
                intRef.element--;
                this.handler.postDelayed(this, 1000L);
            }
        });
        setupFakePlayersForRound();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setLayoutDirection(0);
        AppCompatDelegate.setDefaultNightMode(1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_game);
        initViews();
        loadBalance();
        ImageView imageView = this.btnBack;
        Button button = null;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnBack");
            imageView = null;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.CrashGameActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.finish();
            }
        });
        Button button2 = this.btnPlaceBet;
        if (button2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnPlaceBet");
            button2 = null;
        }
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.CrashGameActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CrashGameActivity.onCreate$lambda$2(this.f$0, view);
            }
        });
        Button button3 = this.btnCashOut;
        if (button3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("btnCashOut");
        } else {
            button = button3;
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.CrashGameActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CrashGameActivity.onCreate$lambda$3(this.f$0, view);
            }
        });
        startGlobalGameLoop();
    }
}

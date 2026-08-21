package androidx.activity;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.view.insets.ColorProtection;
import androidx.core.view.insets.Protection;
import androidx.core.view.insets.ProtectionLayout;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EdgeToEdge.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J8\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0017¨\u0006\u0010"}, d2 = {"Landroidx/activity/EdgeToEdgeApi35;", "Landroidx/activity/EdgeToEdgeApi30;", "<init>", "()V", "setUp", "", "statusBarStyle", "Landroidx/activity/SystemBarStyle;", "navigationBarStyle", "window", "Landroid/view/Window;", "view", "Landroid/view/View;", "statusBarIsDark", "", "navigationBarIsDark", "activity"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
final class EdgeToEdgeApi35 extends EdgeToEdgeApi30 {
    @Override // androidx.activity.EdgeToEdgeApi29, androidx.activity.EdgeToEdgeApi26, androidx.activity.EdgeToEdgeImpl
    public void setUp(SystemBarStyle statusBarStyle, SystemBarStyle navigationBarStyle, Window window, View view, boolean statusBarIsDark, boolean navigationBarIsDark) {
        boolean z;
        Intrinsics.checkNotNullParameter(statusBarStyle, "statusBarStyle");
        Intrinsics.checkNotNullParameter(navigationBarStyle, "navigationBarStyle");
        Intrinsics.checkNotNullParameter(window, "window");
        Intrinsics.checkNotNullParameter(view, "view");
        WindowCompat.setDecorFitsSystemWindows(window, false);
        WindowManager.LayoutParams attrs = window.getAttributes();
        if ((attrs.flags & 256) == 0 && attrs.width == -2 && attrs.height == -2) {
            z = false;
        } else {
            window.setStatusBarColor(0);
            window.setNavigationBarColor(0);
            int statusBarColor = statusBarStyle.getScrimWithEnforcedContrast$activity(statusBarIsDark);
            int navBarColor = navigationBarStyle.getScrimWithEnforcedContrast$activity(navigationBarIsDark);
            z = false;
            ((ViewGroup) view).addView(new ProtectionLayout(((ViewGroup) view).getContext(), (List<Protection>) CollectionsKt.listOf((Object[]) new ColorProtection[]{new ColorProtection(2, statusBarColor), new ColorProtection(1, navBarColor), new ColorProtection(4, navBarColor), new ColorProtection(8, navBarColor)})));
        }
        window.setNavigationBarContrastEnforced(navigationBarStyle.getNightMode() == 0 ? true : z);
        WindowInsetsControllerCompat $this$setUp_u24lambda_u240 = new WindowInsetsControllerCompat(window, view);
        $this$setUp_u24lambda_u240.setAppearanceLightStatusBars(!statusBarIsDark);
        $this$setUp_u24lambda_u240.setAppearanceLightNavigationBars(!navigationBarIsDark);
    }
}

package androidx.navigationevent;

import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.viewtree.ViewTree;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ViewTreeNavigationEventDispatcherOwner.android.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u001b\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0007¢\u0006\u0002\b\u0005\u001a\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0004*\u00020\u0002H\u0007¢\u0006\u0002\b\u0007¨\u0006\b"}, d2 = {"setViewTreeNavigationEventDispatcherOwner", "", "Landroid/view/View;", "navigationEventDispatcherOwner", "Landroidx/navigationevent/NavigationEventDispatcherOwner;", "set", "findViewTreeNavigationEventDispatcherOwner", "get", "navigationevent"}, k = 2, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class ViewTreeNavigationEventDispatcherOwner {
    public static final void set(View $this$setViewTreeNavigationEventDispatcherOwner, NavigationEventDispatcherOwner navigationEventDispatcherOwner) {
        Intrinsics.checkNotNullParameter($this$setViewTreeNavigationEventDispatcherOwner, "<this>");
        $this$setViewTreeNavigationEventDispatcherOwner.setTag(R.id.view_tree_navigation_event_dispatcher_owner, navigationEventDispatcherOwner);
    }

    public static final NavigationEventDispatcherOwner get(View $this$findViewTreeNavigationEventDispatcherOwner) {
        Intrinsics.checkNotNullParameter($this$findViewTreeNavigationEventDispatcherOwner, "<this>");
        View currentView = $this$findViewTreeNavigationEventDispatcherOwner;
        while (true) {
            View view = null;
            if (currentView == null) {
                return null;
            }
            Object tag = currentView.getTag(R.id.view_tree_navigation_event_dispatcher_owner);
            NavigationEventDispatcherOwner dispatchOwner = tag instanceof NavigationEventDispatcherOwner ? (NavigationEventDispatcherOwner) tag : null;
            if (dispatchOwner != null) {
                return dispatchOwner;
            }
            Object parentOrViewTreeDisjointParent = ViewTree.getParentOrViewTreeDisjointParent(currentView);
            if (parentOrViewTreeDisjointParent instanceof View) {
                view = (View) parentOrViewTreeDisjointParent;
            }
            currentView = view;
        }
    }
}

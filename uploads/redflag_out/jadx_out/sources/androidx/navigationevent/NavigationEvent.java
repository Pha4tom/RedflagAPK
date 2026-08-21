package androidx.navigationevent;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: NavigationEvent.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u0000 \u001b2\u00020\u0001:\u0002\u001a\u001bB;\b\u0007\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0003\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0003\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0003\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0017\u001a\u00020\u0003H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0004\u001a\u00020\u00058G¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0006\u001a\u00020\u00058G¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0013\u0010\u0007\u001a\u00020\u00058G¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u001c"}, d2 = {"Landroidx/navigationevent/NavigationEvent;", "", "swipeEdge", "", "progress", "", "touchX", "touchY", "frameTimeMillis", "", "<init>", "(IFFFJ)V", "getSwipeEdge", "()I", "getProgress", "()F", "getTouchX", "getTouchY", "getFrameTimeMillis", "()J", "equals", "", "other", "hashCode", "toString", "", "SwipeEdge", "Companion", "navigationevent"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class NavigationEvent {
    public static final int EDGE_LEFT = 0;
    public static final int EDGE_NONE = 2;
    public static final int EDGE_RIGHT = 1;
    private final long frameTimeMillis;
    private final float progress;
    private final int swipeEdge;
    private final float touchX;
    private final float touchY;

    /* JADX INFO: compiled from: NavigationEvent.kt */
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.CONSTRUCTOR, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE})
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Landroidx/navigationevent/NavigationEvent$SwipeEdge;", "", "navigationevent"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @kotlin.annotation.Target(allowedTargets = {AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.FIELD, AnnotationTarget.LOCAL_VARIABLE, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.TYPE})
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    public @interface SwipeEdge {
    }

    public NavigationEvent() {
        this(0, 0.0f, 0.0f, 0.0f, 0L, 31, null);
    }

    public NavigationEvent(int i) {
        this(i, 0.0f, 0.0f, 0.0f, 0L, 30, null);
    }

    public NavigationEvent(int i, float f) {
        this(i, f, 0.0f, 0.0f, 0L, 28, null);
    }

    public NavigationEvent(int i, float f, float f2) {
        this(i, f, f2, 0.0f, 0L, 24, null);
    }

    public NavigationEvent(int i, float f, float f2, float f3) {
        this(i, f, f2, f3, 0L, 16, null);
    }

    public NavigationEvent(int swipeEdge, float progress, float touchX, float touchY, long frameTimeMillis) {
        this.swipeEdge = swipeEdge;
        this.progress = progress;
        this.touchX = touchX;
        this.touchY = touchY;
        this.frameTimeMillis = frameTimeMillis;
    }

    public /* synthetic */ NavigationEvent(int i, float f, float f2, float f3, long j, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 2 : i, (i2 & 2) != 0 ? 0.0f : f, (i2 & 4) != 0 ? 0.0f : f2, (i2 & 8) != 0 ? 0.0f : f3, (i2 & 16) != 0 ? 0L : j);
    }

    public final int getSwipeEdge() {
        return this.swipeEdge;
    }

    public final float getProgress() {
        return this.progress;
    }

    public final float getTouchX() {
        return this.touchX;
    }

    public final float getTouchY() {
        return this.touchY;
    }

    public final long getFrameTimeMillis() {
        return this.frameTimeMillis;
    }

    public boolean equals(Object other) {
        boolean z;
        boolean z2;
        boolean z3;
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        if (this.touchX == ((NavigationEvent) other).touchX) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            return false;
        }
        if (this.touchY == ((NavigationEvent) other).touchY) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (!z2) {
            return false;
        }
        if (this.progress == ((NavigationEvent) other).progress) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (z3 && this.swipeEdge == ((NavigationEvent) other).swipeEdge && this.frameTimeMillis == ((NavigationEvent) other).frameTimeMillis) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = Float.hashCode(this.touchX);
        return (((((((result * 31) + Float.hashCode(this.touchY)) * 31) + Float.hashCode(this.progress)) * 31) + Integer.hashCode(this.swipeEdge)) * 31) + Long.hashCode(this.frameTimeMillis);
    }

    public String toString() {
        return "NavigationEvent(touchX=" + this.touchX + ", touchY=" + this.touchY + ", progress=" + this.progress + ", swipeEdge=" + this.swipeEdge + ", frameTimeMillis=" + this.frameTimeMillis + ')';
    }
}

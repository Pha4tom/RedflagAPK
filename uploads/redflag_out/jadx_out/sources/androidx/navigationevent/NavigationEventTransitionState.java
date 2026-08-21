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
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NavigationEventTransitionState.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u0000 \u00072\u00020\u0001:\u0004\u0004\u0005\u0006\u0007B\t\b\u0004¢\u0006\u0004\b\u0002\u0010\u0003\u0082\u0001\u0002\b\t¨\u0006\n"}, d2 = {"Landroidx/navigationevent/NavigationEventTransitionState;", "", "<init>", "()V", "Idle", "InProgress", "Direction", "Companion", "Landroidx/navigationevent/NavigationEventTransitionState$Idle;", "Landroidx/navigationevent/NavigationEventTransitionState$InProgress;", "navigationevent"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public abstract class NavigationEventTransitionState {
    public static final int TRANSITIONING_BACK = -1;
    public static final int TRANSITIONING_FORWARD = 1;
    public static final int TRANSITIONING_UNKNOWN = 0;

    /* JADX INFO: compiled from: NavigationEventTransitionState.kt */
    @Target({ElementType.METHOD, ElementType.PARAMETER})
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, d2 = {"Landroidx/navigationevent/NavigationEventTransitionState$Direction;", "", "navigationevent"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @kotlin.annotation.Target(allowedTargets = {AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER})
    @Retention(RetentionPolicy.SOURCE)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    public @interface Direction {
    }

    public /* synthetic */ NavigationEventTransitionState(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private NavigationEventTransitionState() {
    }

    /* JADX INFO: compiled from: NavigationEventTransitionState.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"Landroidx/navigationevent/NavigationEventTransitionState$Idle;", "Landroidx/navigationevent/NavigationEventTransitionState;", "<init>", "()V", "toString", "", "navigationevent"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Idle extends NavigationEventTransitionState {
        public static final Idle INSTANCE = new Idle();

        private Idle() {
            super(null);
        }

        public String toString() {
            return "Idle()";
        }
    }

    /* JADX INFO: compiled from: NavigationEventTransitionState.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0005H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0013"}, d2 = {"Landroidx/navigationevent/NavigationEventTransitionState$InProgress;", "Landroidx/navigationevent/NavigationEventTransitionState;", "latestEvent", "Landroidx/navigationevent/NavigationEvent;", "direction", "", "<init>", "(Landroidx/navigationevent/NavigationEvent;I)V", "getLatestEvent", "()Landroidx/navigationevent/NavigationEvent;", "getDirection", "()I", "equals", "", "other", "", "hashCode", "toString", "", "navigationevent"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class InProgress extends NavigationEventTransitionState {
        private final int direction;
        private final NavigationEvent latestEvent;

        public final NavigationEvent getLatestEvent() {
            return this.latestEvent;
        }

        public final int getDirection() {
            return this.direction;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public InProgress(NavigationEvent latestEvent, int direction) {
            super(null);
            Intrinsics.checkNotNullParameter(latestEvent, "latestEvent");
            this.latestEvent = latestEvent;
            this.direction = direction;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            if (this.direction == ((InProgress) other).direction && Intrinsics.areEqual(this.latestEvent, ((InProgress) other).latestEvent)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            int result = this.direction;
            return (result * 31) + this.latestEvent.hashCode();
        }

        public String toString() {
            return "InProgress(latestEvent=" + this.latestEvent + ", direction=" + this.direction + ')';
        }
    }
}

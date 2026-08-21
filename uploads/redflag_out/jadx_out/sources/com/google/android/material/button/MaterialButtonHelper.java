package com.google.android.material.button;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import androidx.dynamicanimation.animation.SpringForce;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.shape.StateListShapeAppearanceModel;

/* JADX INFO: loaded from: classes.dex */
class MaterialButtonHelper {
    private ColorStateList backgroundTint;
    private PorterDuff.Mode backgroundTintMode;
    private boolean checkable;
    private int cornerRadius;
    private SpringForce cornerSpringForce;
    private int elevation;
    private int insetBottom;
    private int insetLeft;
    private int insetRight;
    private int insetTop;
    private Drawable maskDrawable;
    private final MaterialButton materialButton;
    private MaterialShapeDrawable.OnCornerSizeChangeListener onCornerSizeChangeListener;
    private ColorStateList rippleColor;
    private LayerDrawable rippleDrawable;
    private ShapeAppearanceModel shapeAppearanceModel;
    private StateListShapeAppearanceModel stateListShapeAppearanceModel;
    private ColorStateList strokeColor;
    private int strokeWidth;
    private boolean shouldDrawSurfaceColorStroke = false;
    private boolean backgroundOverwritten = false;
    private boolean cornerRadiusSet = false;
    private boolean toggleCheckedStateOnClick = true;

    MaterialButtonHelper(MaterialButton button, ShapeAppearanceModel shapeAppearanceModel) {
        this.materialButton = button;
        this.shapeAppearanceModel = shapeAppearanceModel;
    }

    void loadFromAttributes(TypedArray attributes) {
        this.insetLeft = attributes.getDimensionPixelOffset(R.styleable.MaterialButton_android_insetLeft, 0);
        this.insetRight = attributes.getDimensionPixelOffset(R.styleable.MaterialButton_android_insetRight, 0);
        this.insetTop = attributes.getDimensionPixelOffset(R.styleable.MaterialButton_android_insetTop, 0);
        this.insetBottom = attributes.getDimensionPixelOffset(R.styleable.MaterialButton_android_insetBottom, 0);
        if (attributes.hasValue(R.styleable.MaterialButton_cornerRadius)) {
            this.cornerRadius = attributes.getDimensionPixelSize(R.styleable.MaterialButton_cornerRadius, -1);
            setShapeAppearanceModel(this.shapeAppearanceModel.withCornerSize(this.cornerRadius));
            this.cornerRadiusSet = true;
        }
        this.strokeWidth = attributes.getDimensionPixelSize(R.styleable.MaterialButton_strokeWidth, 0);
        this.backgroundTintMode = ViewUtils.parseTintMode(attributes.getInt(R.styleable.MaterialButton_backgroundTintMode, -1), PorterDuff.Mode.SRC_IN);
        this.backgroundTint = MaterialResources.getColorStateList(this.materialButton.getContext(), attributes, R.styleable.MaterialButton_backgroundTint);
        this.strokeColor = MaterialResources.getColorStateList(this.materialButton.getContext(), attributes, R.styleable.MaterialButton_strokeColor);
        this.rippleColor = MaterialResources.getColorStateList(this.materialButton.getContext(), attributes, R.styleable.MaterialButton_rippleColor);
        this.checkable = attributes.getBoolean(R.styleable.MaterialButton_android_checkable, false);
        this.elevation = attributes.getDimensionPixelSize(R.styleable.MaterialButton_elevation, 0);
        this.toggleCheckedStateOnClick = attributes.getBoolean(R.styleable.MaterialButton_toggleCheckedStateOnClick, true);
        int paddingStart = this.materialButton.getPaddingStart();
        int paddingTop = this.materialButton.getPaddingTop();
        int paddingEnd = this.materialButton.getPaddingEnd();
        int paddingBottom = this.materialButton.getPaddingBottom();
        if (attributes.hasValue(R.styleable.MaterialButton_android_background)) {
            setBackgroundOverwritten();
        } else {
            updateBackground();
        }
        this.materialButton.setPaddingRelative(this.insetLeft + paddingStart, this.insetTop + paddingTop, this.insetRight + paddingEnd, this.insetBottom + paddingBottom);
    }

    private void updateBackground() {
        this.materialButton.setInternalBackground(createBackground());
        MaterialShapeDrawable materialShapeDrawable = getMaterialShapeDrawable();
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setElevation(this.elevation);
            materialShapeDrawable.setState(this.materialButton.getDrawableState());
        }
    }

    void setBackgroundOverwritten() {
        this.backgroundOverwritten = true;
        this.materialButton.setSupportBackgroundTintList(this.backgroundTint);
        this.materialButton.setSupportBackgroundTintMode(this.backgroundTintMode);
    }

    boolean isBackgroundOverwritten() {
        return this.backgroundOverwritten;
    }

    private InsetDrawable wrapDrawableWithInset(Drawable drawable) {
        return new InsetDrawable(drawable, this.insetLeft, this.insetTop, this.insetRight, this.insetBottom);
    }

    void setSupportBackgroundTintList(ColorStateList tintList) {
        if (this.backgroundTint != tintList) {
            this.backgroundTint = tintList;
            if (getMaterialShapeDrawable() != null) {
                getMaterialShapeDrawable().setTintList(this.backgroundTint);
            }
        }
    }

    ColorStateList getSupportBackgroundTintList() {
        return this.backgroundTint;
    }

    void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.backgroundTintMode != mode) {
            this.backgroundTintMode = mode;
            if (getMaterialShapeDrawable() != null && this.backgroundTintMode != null) {
                getMaterialShapeDrawable().setTintMode(this.backgroundTintMode);
            }
        }
    }

    PorterDuff.Mode getSupportBackgroundTintMode() {
        return this.backgroundTintMode;
    }

    void setShouldDrawSurfaceColorStroke(boolean shouldDrawSurfaceColorStroke) {
        this.shouldDrawSurfaceColorStroke = shouldDrawSurfaceColorStroke;
        updateStroke();
    }

    private Drawable createBackground() {
        int color;
        MaterialShapeDrawable backgroundDrawable = new MaterialShapeDrawable(this.shapeAppearanceModel);
        if (this.stateListShapeAppearanceModel != null) {
            backgroundDrawable.setStateListShapeAppearanceModel(this.stateListShapeAppearanceModel);
        }
        if (this.cornerSpringForce != null) {
            backgroundDrawable.setCornerSpringForce(this.cornerSpringForce);
        }
        if (this.onCornerSizeChangeListener != null) {
            backgroundDrawable.setOnCornerSizeChangeListener(this.onCornerSizeChangeListener);
        }
        Context context = this.materialButton.getContext();
        backgroundDrawable.initializeElevationOverlay(context);
        backgroundDrawable.setTintList(this.backgroundTint);
        if (this.backgroundTintMode != null) {
            backgroundDrawable.setTintMode(this.backgroundTintMode);
        }
        backgroundDrawable.setStroke(this.strokeWidth, this.strokeColor);
        MaterialShapeDrawable surfaceColorStrokeDrawable = new MaterialShapeDrawable(this.shapeAppearanceModel);
        if (this.stateListShapeAppearanceModel != null) {
            surfaceColorStrokeDrawable.setStateListShapeAppearanceModel(this.stateListShapeAppearanceModel);
        }
        if (this.cornerSpringForce != null) {
            surfaceColorStrokeDrawable.setCornerSpringForce(this.cornerSpringForce);
        }
        surfaceColorStrokeDrawable.setTint(0);
        float f = this.strokeWidth;
        if (this.shouldDrawSurfaceColorStroke) {
            color = MaterialColors.getColor(this.materialButton, R.attr.colorSurface);
        } else {
            color = 0;
        }
        surfaceColorStrokeDrawable.setStroke(f, color);
        this.maskDrawable = new MaterialShapeDrawable(this.shapeAppearanceModel);
        if (this.stateListShapeAppearanceModel != null) {
            ((MaterialShapeDrawable) this.maskDrawable).setStateListShapeAppearanceModel(this.stateListShapeAppearanceModel);
        }
        if (this.cornerSpringForce != null) {
            ((MaterialShapeDrawable) this.maskDrawable).setCornerSpringForce(this.cornerSpringForce);
        }
        this.maskDrawable.setTint(-1);
        this.rippleDrawable = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(this.rippleColor), wrapDrawableWithInset(new LayerDrawable(new Drawable[]{surfaceColorStrokeDrawable, backgroundDrawable})), this.maskDrawable);
        return this.rippleDrawable;
    }

    void updateMaskBounds(int height, int width) {
        if (this.maskDrawable != null) {
            this.maskDrawable.setBounds(this.insetLeft, this.insetTop, width - this.insetRight, height - this.insetBottom);
        }
    }

    void setBackgroundColor(int color) {
        if (getMaterialShapeDrawable() != null) {
            getMaterialShapeDrawable().setTint(color);
        }
    }

    void setRippleColor(ColorStateList rippleColor) {
        if (this.rippleColor != rippleColor) {
            this.rippleColor = rippleColor;
            if (this.materialButton.getBackground() instanceof RippleDrawable) {
                ((RippleDrawable) this.materialButton.getBackground()).setColor(RippleUtils.sanitizeRippleDrawableColor(rippleColor));
            }
        }
    }

    ColorStateList getRippleColor() {
        return this.rippleColor;
    }

    void setStrokeColor(ColorStateList strokeColor) {
        if (this.strokeColor != strokeColor) {
            this.strokeColor = strokeColor;
            updateStroke();
        }
    }

    ColorStateList getStrokeColor() {
        return this.strokeColor;
    }

    void setStrokeWidth(int strokeWidth) {
        if (this.strokeWidth != strokeWidth) {
            this.strokeWidth = strokeWidth;
            updateStroke();
        }
    }

    int getStrokeWidth() {
        return this.strokeWidth;
    }

    private void updateStroke() {
        int color;
        MaterialShapeDrawable materialShapeDrawable = getMaterialShapeDrawable();
        MaterialShapeDrawable surfaceColorStrokeDrawable = getSurfaceColorStrokeDrawable();
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setStroke(this.strokeWidth, this.strokeColor);
            if (surfaceColorStrokeDrawable != null) {
                float f = this.strokeWidth;
                if (this.shouldDrawSurfaceColorStroke) {
                    color = MaterialColors.getColor(this.materialButton, R.attr.colorSurface);
                } else {
                    color = 0;
                }
                surfaceColorStrokeDrawable.setStroke(f, color);
            }
        }
    }

    void setCornerRadius(int cornerRadius) {
        if (!this.cornerRadiusSet || this.cornerRadius != cornerRadius) {
            this.cornerRadius = cornerRadius;
            this.cornerRadiusSet = true;
            setShapeAppearanceModel(this.shapeAppearanceModel.withCornerSize(cornerRadius));
        }
    }

    int getCornerRadius() {
        return this.cornerRadius;
    }

    private MaterialShapeDrawable getMaterialShapeDrawable(boolean z) {
        if (this.rippleDrawable != null && this.rippleDrawable.getNumberOfLayers() > 0) {
            return (MaterialShapeDrawable) ((LayerDrawable) ((InsetDrawable) this.rippleDrawable.getDrawable(0)).getDrawable()).getDrawable(!z ? 1 : 0);
        }
        return null;
    }

    MaterialShapeDrawable getMaterialShapeDrawable() {
        return getMaterialShapeDrawable(false);
    }

    void setCheckable(boolean checkable) {
        this.checkable = checkable;
    }

    boolean isCheckable() {
        return this.checkable;
    }

    boolean isToggleCheckedStateOnClick() {
        return this.toggleCheckedStateOnClick;
    }

    void setToggleCheckedStateOnClick(boolean toggleCheckedStateOnClick) {
        this.toggleCheckedStateOnClick = toggleCheckedStateOnClick;
    }

    void setCornerSizeChangeListener(MaterialShapeDrawable.OnCornerSizeChangeListener onCornerSizeChangeListener) {
        this.onCornerSizeChangeListener = onCornerSizeChangeListener;
        MaterialShapeDrawable materialShapeDrawable = getMaterialShapeDrawable();
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setOnCornerSizeChangeListener(onCornerSizeChangeListener);
        }
    }

    private MaterialShapeDrawable getSurfaceColorStrokeDrawable() {
        return getMaterialShapeDrawable(true);
    }

    private void updateButtonShape() {
        MaterialShapeDrawable backgroundDrawable = getMaterialShapeDrawable();
        if (backgroundDrawable != null) {
            if (this.stateListShapeAppearanceModel != null) {
                backgroundDrawable.setStateListShapeAppearanceModel(this.stateListShapeAppearanceModel);
            } else {
                backgroundDrawable.setShapeAppearanceModel(this.shapeAppearanceModel);
            }
            if (this.cornerSpringForce != null) {
                backgroundDrawable.setCornerSpringForce(this.cornerSpringForce);
            }
        }
        MaterialShapeDrawable strokeDrawable = getSurfaceColorStrokeDrawable();
        if (strokeDrawable != null) {
            if (this.stateListShapeAppearanceModel != null) {
                strokeDrawable.setStateListShapeAppearanceModel(this.stateListShapeAppearanceModel);
            } else {
                strokeDrawable.setShapeAppearanceModel(this.shapeAppearanceModel);
            }
            if (this.cornerSpringForce != null) {
                strokeDrawable.setCornerSpringForce(this.cornerSpringForce);
            }
        }
        Shapeable animatedShapeable = getMaskDrawable();
        if (animatedShapeable != null) {
            animatedShapeable.setShapeAppearanceModel(this.shapeAppearanceModel);
            if (animatedShapeable instanceof MaterialShapeDrawable) {
                MaterialShapeDrawable maskDrawable = (MaterialShapeDrawable) animatedShapeable;
                if (this.stateListShapeAppearanceModel != null) {
                    maskDrawable.setStateListShapeAppearanceModel(this.stateListShapeAppearanceModel);
                }
                if (this.cornerSpringForce != null) {
                    maskDrawable.setCornerSpringForce(this.cornerSpringForce);
                }
            }
        }
    }

    public Shapeable getMaskDrawable() {
        if (this.rippleDrawable != null && this.rippleDrawable.getNumberOfLayers() > 1) {
            if (this.rippleDrawable.getNumberOfLayers() > 2) {
                return (Shapeable) this.rippleDrawable.getDrawable(2);
            }
            return (Shapeable) this.rippleDrawable.getDrawable(1);
        }
        return null;
    }

    void setCornerSpringForce(SpringForce springForce) {
        this.cornerSpringForce = springForce;
        if (this.stateListShapeAppearanceModel != null) {
            updateButtonShape();
        }
    }

    SpringForce getCornerSpringForce() {
        return this.cornerSpringForce;
    }

    void setStateListShapeAppearanceModel(StateListShapeAppearanceModel stateListShapeAppearanceModel) {
        this.stateListShapeAppearanceModel = stateListShapeAppearanceModel;
        updateButtonShape();
    }

    StateListShapeAppearanceModel getStateListShapeAppearanceModel() {
        return this.stateListShapeAppearanceModel;
    }

    void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        this.shapeAppearanceModel = shapeAppearanceModel;
        this.stateListShapeAppearanceModel = null;
        updateButtonShape();
    }

    ShapeAppearanceModel getShapeAppearanceModel() {
        return this.shapeAppearanceModel;
    }

    public void setInsetBottom(int newInsetBottom) {
        setVerticalInsets(this.insetTop, newInsetBottom);
    }

    public int getInsetBottom() {
        return this.insetBottom;
    }

    public void setInsetTop(int newInsetTop) {
        setVerticalInsets(newInsetTop, this.insetBottom);
    }

    private void setVerticalInsets(int newInsetTop, int newInsetBottom) {
        int paddingStart = this.materialButton.getPaddingStart();
        int paddingTop = this.materialButton.getPaddingTop();
        int paddingEnd = this.materialButton.getPaddingEnd();
        int paddingBottom = this.materialButton.getPaddingBottom();
        int oldInsetTop = this.insetTop;
        int oldInsetBottom = this.insetBottom;
        this.insetBottom = newInsetBottom;
        this.insetTop = newInsetTop;
        if (!this.backgroundOverwritten) {
            updateBackground();
        }
        this.materialButton.setPaddingRelative(paddingStart, (paddingTop + newInsetTop) - oldInsetTop, paddingEnd, (paddingBottom + newInsetBottom) - oldInsetBottom);
    }

    public int getInsetTop() {
        return this.insetTop;
    }
}

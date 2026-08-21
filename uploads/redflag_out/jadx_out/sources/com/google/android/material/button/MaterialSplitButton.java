package com.google.android.material.button;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.material.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

/* JADX INFO: loaded from: classes.dex */
public class MaterialSplitButton extends MaterialButtonGroup {
    private static final int DEF_STYLE_RES = R.style.Widget_Material3_MaterialSplitButton;
    private static final int REQUIRED_BUTTON_COUNT = 2;

    public MaterialSplitButton(Context context) {
        this(context, null);
    }

    public MaterialSplitButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.materialSplitButtonStyle);
    }

    public MaterialSplitButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
    }

    @Override // com.google.android.material.button.MaterialButtonGroup, android.view.ViewGroup
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        int i;
        if (!(child instanceof MaterialButton)) {
            throw new IllegalArgumentException("MaterialSplitButton can only hold MaterialButtons.");
        }
        if (getChildCount() > 2) {
            throw new IllegalArgumentException("MaterialSplitButton can only hold two MaterialButtons.");
        }
        MaterialButton buttonChild = (MaterialButton) child;
        super.addView(child, index, params);
        if (indexOfChild(child) == 1) {
            buttonChild.setCheckable(true);
            buttonChild.setA11yClassName(Button.class.getName());
            if (Build.VERSION.SDK_INT >= 30) {
                Resources resources = getResources();
                if (buttonChild.isChecked()) {
                    i = R.string.mtrl_button_expanded_content_description;
                } else {
                    i = R.string.mtrl_button_collapsed_content_description;
                }
                buttonChild.setStateDescription(resources.getString(i));
                buttonChild.addOnCheckedChangeListener(new MaterialButton.OnCheckedChangeListener() { // from class: com.google.android.material.button.MaterialSplitButton$$ExternalSyntheticLambda0
                    @Override // com.google.android.material.button.MaterialButton.OnCheckedChangeListener
                    public final void onCheckedChanged(MaterialButton materialButton, boolean z) {
                        this.f$0.m160xf376ee79(materialButton, z);
                    }
                });
            }
        }
    }

    /* JADX INFO: renamed from: lambda$addView$0$com-google-android-material-button-MaterialSplitButton, reason: not valid java name */
    /* synthetic */ void m160xf376ee79(MaterialButton button, boolean isChecked) {
        int i;
        Resources resources = getResources();
        if (isChecked) {
            i = R.string.mtrl_button_expanded_content_description;
        } else {
            i = R.string.mtrl_button_collapsed_content_description;
        }
        button.setStateDescription(resources.getString(i));
    }
}

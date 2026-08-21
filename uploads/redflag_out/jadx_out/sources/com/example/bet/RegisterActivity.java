package com.example.bet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RegisterActivity.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0014¨\u0006\b"}, d2 = {"Lcom/example/bet/RegisterActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "<init>", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}, k = 1, mv = {2, 0, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
public final class RegisterActivity extends AppCompatActivity {
    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(EditText editText, EditText editText2, EditText editText3, RegisterActivity registerActivity, View view) {
        String string = editText.getText().toString();
        String string2 = editText2.getText().toString();
        String string3 = editText3.getText().toString();
        if (!(string.length() == 0)) {
            if (!(string2.length() == 0)) {
                if (!(string3.length() == 0)) {
                    if (!Intrinsics.areEqual(string2, string3)) {
                        Toast.makeText(registerActivity, "كلمة المرور غير متطابقة", 0).show();
                        return;
                    }
                    Toast.makeText(registerActivity, "تم إنشاء الحساب بنجاح", 0).show();
                    registerActivity.startActivity(new Intent(registerActivity, (Class<?>) HomeActivity.class));
                    registerActivity.finish();
                    return;
                }
            }
        }
        Toast.makeText(registerActivity, "يرجى ملء كافة البيانات", 0).show();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText editText = (EditText) findViewById(R.id.etRegPhone);
        final EditText editText2 = (EditText) findViewById(R.id.etRegPass);
        final EditText editText3 = (EditText) findViewById(R.id.etRegConfirmPass);
        ((Button) findViewById(R.id.btnRegisterSubmit)).setOnClickListener(new View.OnClickListener() { // from class: com.example.bet.RegisterActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RegisterActivity.onCreate$lambda$0(editText, editText2, editText3, this, view);
            }
        });
    }
}

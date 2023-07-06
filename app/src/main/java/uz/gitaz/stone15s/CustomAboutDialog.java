package uz.gitaz.stone15s;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;




public class CustomAboutDialog extends AlertDialog {
    AppCompatImageView btnBack;

    protected CustomAboutDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        btnBack = findViewById(R.id.btnBack);
        assert btnBack != null;
        btnBack.setOnClickListener(v -> {
            dismiss();
        });
    }
}

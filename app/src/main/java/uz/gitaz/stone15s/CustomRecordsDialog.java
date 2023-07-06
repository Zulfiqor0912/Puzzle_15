package uz.gitaz.stone15s;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;


public class CustomRecordsDialog extends AlertDialog {
    private TextView firstRecord;
    private TextView secondRecord;
    private TextView thirdRecord;

    private Local_Storage sharedPreference;
    private String[] recordsList;
    protected CustomRecordsDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.records_dialog);
        loadView();
        initRecords();
    }

    void loadView() {
        sharedPreference = Local_Storage.getInstance();
        recordsList = new String[3];
        firstRecord = findViewById(R.id.firstRecord);
        secondRecord = findViewById(R.id.secondRecord);
        thirdRecord = findViewById(R.id.thirdRecord);
    }

    private String[] records() {
        String recordsString = sharedPreference.getRecords();
        String[] split = recordsString.split("#");
        return split;
    }

    private void initRecords() {
        int length = records().length;
        if (length == 1) {
            firstRecord.setText(records()[0]);
        } else if (length == 2) {
            firstRecord.setText(records()[0]);
            secondRecord.setText(records()[1]);

        } else if (length == 3) {
            firstRecord.setText(records()[0]);
            secondRecord.setText(records()[1]);
            thirdRecord.setText(records()[2]);
        }
    }


}

package com.example.dr.hyphope;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BlankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);

        RecognizeDialog rec_dialog = new RecognizeDialog();
            rec_dialog.show(getFragmentManager(), "recognizeDialog");
      //  this.finish();
    }
}

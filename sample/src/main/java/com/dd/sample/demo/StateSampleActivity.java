package com.dd.sample.demo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.dd.sample.R;
import com.hkm.ui.processbutton.ProcessButton;
import com.hkm.ui.processbutton.iml.ActionProcessButton;
import com.hkm.ui.processbutton.iml.GenerateProcessButton;
import com.hkm.ui.processbutton.iml.SubmitProcessButton;


public class StateSampleActivity extends Activity implements View.OnClickListener {

    private ActionProcessButton mBtnAction;
    private GenerateProcessButton mBtnGenerate;
    private SubmitProcessButton mBtnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_states);

        mBtnAction = (ActionProcessButton) findViewById(R.id.btnAction);
        mBtnSubmit = (SubmitProcessButton) findViewById(R.id.btnSubmit);
        mBtnGenerate = (GenerateProcessButton) findViewById(R.id.btnGenerate);

        findViewById(R.id.btnProgressLoading).setOnClickListener(this);
        findViewById(R.id.btnProgressError).setOnClickListener(this);
        findViewById(R.id.btnProgressComplete).setOnClickListener(this);
        findViewById(R.id.btnProgressNormal).setOnClickListener(this);
        findViewById(R.id.btnDisable).setOnClickListener(this);
        findViewById(R.id.btnEnable).setOnClickListener(this);


        mBtnAction.setOnClickCompleteState(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SMessage t = SMessage.message("complete state click");
                t.show(getFragmentManager(), "complete");
            }
        }).setOnClickNormalState(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SMessage t = SMessage.message("normal state click");
                t.show(getFragmentManager(), "normal");
            }
        }).setText("message will show state");
        mBtnAction.build();
        mBtnSubmit.build();
        mBtnGenerate.build();
    }

    @SuppressLint("ValidFragment")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class SMessage extends DialogFragment {
        public static SMessage message(final String mes) {
            Bundle h = new Bundle();
            h.putString("message", mes);
            SMessage e = new SMessage();
            e.setArguments(h);
            return e;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getArguments().getString("message"))
                    .setNeutralButton("understood", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            return builder.create();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDisable:
                mBtnAction.setEnabled(false);
                break;
            case R.id.btnEnable:
                mBtnAction.setEnabled(true);
                break;
            case R.id.btnProgressLoading:
                mBtnAction.setProgress(50);
                mBtnSubmit.setProgress(50);
                mBtnGenerate.setProgress(50);
                break;
            case R.id.btnProgressError:
                mBtnAction.setProgress(-1);
                mBtnSubmit.setProgress(-1);
                mBtnGenerate.setProgress(-1);
                break;
            case R.id.btnProgressComplete:
                mBtnAction.setProgress(ProcessButton.FULLBUTTON);
                mBtnSubmit.setProgress(100);
                mBtnGenerate.setProgress(100);
                break;
            case R.id.btnProgressNormal:
                mBtnAction.setProgress(0);
                mBtnSubmit.setProgress(0);
                mBtnGenerate.setProgress(0);
                break;
        }
    }
}

package com.dd.sample;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dd.sample.demo.AnimationActivity;
import com.dd.sample.demo.AreYouSureActivity;
import com.dd.sample.demo.MessageActivity;
import com.dd.sample.demo.SignInActivity;
import com.dd.sample.demo.StateSampleActivity;
import com.dd.sample.demo.UploadActivity;

public class Main extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] items = getResources().getStringArray(R.array.sample_list);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        switch (position) {
            case 0:
                startSignInActivity(false);
                break;
            case 1:
                startSignInActivity(true);
                break;
            case 2:
                startMessageActivity();
                break;
            case 3:
                startUploadActivity();
                break;
            case 4:
                startStateSampleActivity();
                break;
            case 5:
                startAnimationActivity();
                break;
            case 6:
                startAreYouSureActivity();
                break;
        }
    }

    private void startAreYouSureActivity() {
        Intent intent = new Intent(this, AreYouSureActivity.class);
        startActivity(intent);
    }

    private void startStateSampleActivity() {
        Intent intent = new Intent(this, StateSampleActivity.class);
        startActivity(intent);
    }

    private void startUploadActivity() {
        Intent intent = new Intent(this, UploadActivity.class);
        startActivity(intent);
    }

    private void startSignInActivity(boolean isEndlessMode) {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.putExtra(SignInActivity.EXTRAS_ENDLESS_MODE, isEndlessMode);
        startActivity(intent);
    }

    private void startMessageActivity() {
        Intent intent = new Intent(this, MessageActivity.class);
        startActivity(intent);
    }

    private void startAnimationActivity() {
        Intent intent = new Intent(this, AnimationActivity.class);
        startActivity(intent);
    }
}

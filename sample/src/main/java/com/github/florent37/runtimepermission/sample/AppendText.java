package com.github.florent37.runtimepermission.sample;

import android.widget.TextView;

public class AppendText {
    public static void appendText(TextView textView, String text){
        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(textView.getText()+"\n"+text);
            }
        });
    }
}

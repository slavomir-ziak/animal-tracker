package com.wecode.animaltracker.activity.util;

import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by sziak on 10/7/2015.
 */
public class TextChangingSeekBarListener implements SeekBar.OnSeekBarChangeListener {

    private TextView textView;

    private String suffix;

    public TextChangingSeekBarListener(TextView textView, String suffix) {
        this.textView = textView;
        this.suffix = suffix;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        textView.setText(progress + suffix);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}

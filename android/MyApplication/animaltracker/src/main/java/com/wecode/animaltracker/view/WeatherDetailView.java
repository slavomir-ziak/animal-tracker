package com.wecode.animaltracker.view;

import android.widget.SeekBar;
import android.widget.TextView;
import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.detail.WeatherDetailActivity;
import com.wecode.animaltracker.activity.util.TextChangingSeekBarListener;
import com.wecode.animaltracker.model.Weather;

/**
 * Created by sziak on 10-Apr-16.
 */
public class WeatherDetailView {

    private Long id;

    private TextView visibilityValue;
    private TextView showDepthValue;
    private TextView newSnowDepth;
    private TextView lastTimeSnowedRained;

    private SeekBar sunshineSeekBar;
    private SeekBar windSeekBar;
    private SeekBar rainingSeekBar;
    private SeekBar humiditySeekBar;

    public WeatherDetailView(WeatherDetailActivity context, Weather weather) {
        this(context);

        if (weather != null) {
            bind(weather);
        }
    }

    public WeatherDetailView(WeatherDetailActivity context) {

        sunshineSeekBar = (SeekBar) context.findViewById(R.id.sunshineSeekBar);
        TextView sunshineValue = (TextView) context.findViewById(R.id.sunshineValue);
        sunshineSeekBar.setOnSeekBarChangeListener(new TextChangingSeekBarListener(sunshineValue, "%"));

        windSeekBar = (SeekBar) context.findViewById(R.id.windSeekBar);
        TextView windValue = (TextView) context.findViewById(R.id.windValue);
        windSeekBar.setOnSeekBarChangeListener(new TextChangingSeekBarListener(windValue, "%"));

        rainingSeekBar = (SeekBar) context.findViewById(R.id.rainingSeekBar);
        TextView rainingValue = (TextView) context.findViewById(R.id.rainingValue);
        rainingSeekBar.setOnSeekBarChangeListener(new TextChangingSeekBarListener(rainingValue, "%"));

        humiditySeekBar = (SeekBar) context.findViewById(R.id.humiditySeekBar);
        TextView humidityValue = (TextView) context.findViewById(R.id.humidityValue);
        humiditySeekBar.setOnSeekBarChangeListener(new TextChangingSeekBarListener(humidityValue, "%"));

        visibilityValue = (TextView) context.findViewById(R.id.visibilityValue);
        showDepthValue = (TextView) context.findViewById(R.id.showDepthValue);
        newSnowDepth = (TextView) context.findViewById(R.id.newSnowDepth);
        lastTimeSnowedRained = (TextView) context.findViewById(R.id.lastTimeSnowedRained);

    }

    private void bind(Weather weather) {
        id = weather.getId();
        sunshineSeekBar.setProgress(weather.getSunshineIntensity());
        windSeekBar.setProgress(weather.getWindIntensity());
        rainingSeekBar.setProgress(weather.getRainIntensity());
        humiditySeekBar.setProgress(weather.getHumidity());

        visibilityValue.setText(weather.getVisibility() == null ? "" : weather.getVisibility().toString());
        showDepthValue.setText(weather.getShowDepth() == null ? "" : weather.getShowDepth().toString() );
        newSnowDepth.setText(weather.getNewSnowDepth() == null ? "" : weather.getNewSnowDepth().toString());
        lastTimeSnowedRained.setText(weather.getLastTimeSnowedRained() == null ? "" : weather.getLastTimeSnowedRained().toString());
    }

    public Weather toWeather() {
        String visibilityValueText = visibilityValue.getText().toString();
        String showDepthValueText = showDepthValue.getText().toString();
        String newSnowDepthText = newSnowDepth.getText().toString();
        String lastTimeSnowedRainedText = lastTimeSnowedRained.getText().toString();

        return new Weather(
                id,
                sunshineSeekBar.getProgress(),
                windSeekBar.getProgress(),
                rainingSeekBar.getProgress(),
                humiditySeekBar.getProgress(),
                visibilityValueText.isEmpty() ? null : Integer.valueOf(visibilityValueText),
                showDepthValueText.isEmpty() ? null : Integer.valueOf(showDepthValueText),
                newSnowDepthText.isEmpty() ? null : Integer.valueOf(newSnowDepthText),
                lastTimeSnowedRainedText.isEmpty() ? null : Integer.valueOf(lastTimeSnowedRainedText)
        );
    }

}

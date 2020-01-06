package com.wecode.animaltracker.view;

import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wecode.animaltracker.R;
import com.wecode.animaltracker.activity.util.TextChangingSeekBarListener;
import com.wecode.animaltracker.model.Weather;
import com.wecode.animaltracker.service.WeatherDataService;

/**
 * Created by sziak on 10-Apr-16.
 */
public class WeatherDetailView  implements ChangeableView {


    private Long id;

    private TextView visibilityValue;
    private TextView showDepthValue;
    private TextView newSnowDepth;
    private TextView lastTimeSnowedRained;

    private SeekBar sunshineSeekBar;
    private SeekBar windSeekBar;
    private SeekBar rainingSeekBar;
    private SeekBar humiditySeekBar;

    private WeatherDataService service = WeatherDataService.getInstance();

    public WeatherDetailView(View context, Weather weather) {
        this(context);

        if (weather != null) {
            bind(weather);
        }
    }

    public WeatherDetailView(View context) {

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
        showDepthValue.setText(weather.getSnowDepth() == null ? "" : weather.getSnowDepth().toString() );
        newSnowDepth.setText(weather.getNewSnowDepth() == null ? "" : weather.getNewSnowDepth().toString());
        lastTimeSnowedRained.setText(weather.getLastTimeSnowedRained() == null ? "" : weather.getLastTimeSnowedRained().toString());
    }

    public Weather toWeather() {

        Weather weather;

        if (id != null) {
            weather = service.find(id);
        } else {
            weather = new Weather();
        }

        String visibilityValueText = visibilityValue.getText().toString();
        String snowDepthValueText = showDepthValue.getText().toString();
        String newSnowDepthText = newSnowDepth.getText().toString();
        String lastTimeSnowedRainedText = lastTimeSnowedRained.getText().toString();

        weather.setSunshineIntensity(sunshineSeekBar.getProgress());
        weather.setWindIntensity(windSeekBar.getProgress());
        weather.setRainIntensity(rainingSeekBar.getProgress());
        weather.setHumidity(humiditySeekBar.getProgress());
        weather.setVisibility(visibilityValueText.isEmpty() ? null : Integer.valueOf(visibilityValueText));
        weather.setSnowDepth(snowDepthValueText.isEmpty() ? null : Integer.valueOf(snowDepthValueText));
        weather.setNewSnowDepth(newSnowDepthText.isEmpty() ? null : Integer.valueOf(newSnowDepthText));
        weather.setLastTimeSnowedRained(lastTimeSnowedRainedText.isEmpty() ? null : Integer.valueOf(lastTimeSnowedRainedText));

        return weather;
    }

    @Override
    public boolean isChanged() {
        return false;
    }
}

package com.dignifiedmanatee.robotsoundboard;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import static com.dignifiedmanatee.robotsoundboard.R.id.adView;

public class MainActivity extends AppCompatActivity {
    private AudioManager _audioManager;
    private SoundPool _soundPool;

    private ArrayList<Button> _buttons;
    private Map<Integer, Integer> _soundIDs;

    private float _volume;
    private float _actVolume;
    private float _maxVolume;
    private int _counter;
    private boolean _loaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load an ad into the AdMob banner view.
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-7502333933072292/8113128363");
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template")
                .addTestDevice("D545CEEE4B74F0CB78841121E6B5858F")
                .addTestDevice("7E2E6485E85E32C38FD5AFAACEFA126D")
                .build();
        adView.loadAd(adRequest);

        _audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        _actVolume = (float) _audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        _maxVolume = (float) _audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        _volume = _actVolume / _maxVolume;

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        _counter = 0;

        AudioAttributes attribs = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        _soundPool = new SoundPool.Builder()
                .setAudioAttributes(attribs).setMaxStreams(10).build();
        _soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                _loaded = true;
                _buttons.get(i-1).setEnabled(true);
            }
        });

        _buttons = new ArrayList();
        _buttons.add((Button)findViewById(R.id.button_beep));
        _buttons.add((Button)findViewById(R.id.button_beep1));
        _buttons.add((Button)findViewById(R.id.button_beep2));
        _buttons.add((Button)findViewById(R.id.button_boop));
        _buttons.add((Button)findViewById(R.id.button_boop1));
        _buttons.add((Button)findViewById(R.id.button_boop2));
        _buttons.add((Button)findViewById(R.id.button_err));
        _buttons.add((Button)findViewById(R.id.button_womp));
        _buttons.add((Button)findViewById(R.id.button_bink));
        _buttons.add((Button)findViewById(R.id.button_bonk));
        _buttons.add((Button)findViewById(R.id.button_wah));
        _buttons.add((Button)findViewById(R.id.button_whrrr));
        for (int i = 0; i < _buttons.size(); ++i){
            Button button = _buttons.get(i);
            button.setEnabled(false);
            button.setOnClickListener(ButtonListener());
        }

        _soundIDs = new ArrayMap();
        _soundIDs.put(R.id.button_beep, _soundPool.load(this, R.raw.beep, 1));
        _soundIDs.put(R.id.button_beep1, _soundPool.load(this, R.raw.beep1, 1));
        _soundIDs.put(R.id.button_beep2, _soundPool.load(this, R.raw.beep2, 1));
        _soundIDs.put(R.id.button_boop, _soundPool.load(this, R.raw.boop, 1));
        _soundIDs.put(R.id.button_boop1, _soundPool.load(this, R.raw.boop1, 1));
        _soundIDs.put(R.id.button_boop2, _soundPool.load(this, R.raw.boop2, 1));
        _soundIDs.put(R.id.button_err, _soundPool.load(this, R.raw.err, 1));
        _soundIDs.put(R.id.button_womp, _soundPool.load(this, R.raw.womp, 1));
        _soundIDs.put(R.id.button_bink, _soundPool.load(this, R.raw.bink, 1));
        _soundIDs.put(R.id.button_bonk, _soundPool.load(this, R.raw.bonk, 1));
        _soundIDs.put(R.id.button_wah, _soundPool.load(this, R.raw.wahwah, 1));
        _soundIDs.put(R.id.button_whrrr, _soundPool.load(this, R.raw.whrrr, 1));
    }

    protected View.OnClickListener ButtonListener() {
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick (View v){
                playSound(v.getId());
            }
        };
        return v;
    }

    private void playSound(int resource){
        if(_loaded) {
            _soundPool.play(_soundIDs.get(resource), _volume, _volume, 1, 0 ,1f);
        }
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}

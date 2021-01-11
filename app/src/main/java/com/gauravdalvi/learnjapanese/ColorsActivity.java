package com.gauravdalvi.learnjapanese;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener focusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {

                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        mediaPlayer.start();
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                    }
                }
            };


    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onStop() {
        super.onStop();

        releaseMediaPlayer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Words> words= new ArrayList<Words>();
        words.add(new Words("Red", "Aka", R.drawable.colors_red, R.raw.colors_red));
        words.add(new Words("Orange", "Orenji", R.drawable.colors_orange, R.raw.colors_orange));
        words.add(new Words("Yellow", "Kiiro", R.drawable.colors_yellow, R.raw.colors_yellow));
        words.add(new Words("Green", "Midori", R.drawable.colors_green, R.raw.colors_green));
        words.add(new Words("Light Blue", "Mizuiro", R.drawable.colors_lightblue, R.raw.colors_lightblue));
        words.add(new Words("Blue", "Aoiro", R.drawable.colors_blue, R.raw.colors_blue));
        words.add(new Words("Purple", "Murasaki", R.drawable.colors_purple, R.raw.colors_purple));
        words.add(new Words("Brown", "Chairo", R.drawable.colors_brown, R.raw.colors_brown));
        words.add(new Words("Pink", "Momoiro", R.drawable.colors_pink, R.raw.colors_pink));
        words.add(new Words("Gray", "Haiiro", R.drawable.colors_gray, R.raw.colors_gray));
        words.add(new Words("Black", "Kuro", R.drawable.colors_black, R.raw.colors_black));
        words.add(new Words("White", "Shiroi", R.drawable.colors_white, R.raw.colors_white));

        ListView list2 = (ListView) findViewById(R.id.list2);

        WordAdapter adapter = new WordAdapter(this, words, R.color.colors_activity_items);

        list2.setAdapter(adapter);

        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Words word = words.get(i);
                releaseMediaPlayer();

                int result = audioManager.requestAudioFocus(focusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getAudioResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(completionListener);
                }
            }
        });
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(focusChangeListener);
        }
    }
}
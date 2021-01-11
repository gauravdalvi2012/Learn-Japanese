package com.gauravdalvi.learnjapanese;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_numbers);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Words> words= new ArrayList<Words>();
        words.add(new Words("One", "ichi", R.drawable.numbers_one, R.raw.numbers_one));
        words.add(new Words("Two", "ni", R.drawable.numbers_two, R.raw.numbers_two));
        words.add(new Words("Three", "san", R.drawable.numbers_three, R.raw.numbers_three));
        words.add(new Words("Four", "yon, shi", R.drawable.numbers_four, R.raw.numbers_four));
        words.add(new Words("Five", "go", R.drawable.numbers_five, R.raw.numbers_five));
        words.add(new Words("Six", "roku", R.drawable.numbers_six, R.raw.numbers_six));
        words.add(new Words("Seven", "nana, shichi", R.drawable.numbers_seven, R.raw.numbers_seven));
        words.add(new Words("Eight", "hachi", R.drawable.numbers_eight, R.raw.numbers_eight));
        words.add(new Words("Nine", "kyuu", R.drawable.numbers_nine, R.raw.numbers_nine));
        words.add(new Words("Ten", "juu", R.drawable.numbers_ten, R.raw.numbers_ten));

        ListView list1 = (ListView) findViewById(R.id.list1);

        WordAdapter adapter = new WordAdapter(this, words, R.color.numbers_activity_items);

        list1.setAdapter(adapter);

        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Words word = words.get(i);
                releaseMediaPlayer();

                int result = audioManager.requestAudioFocus(focusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudioResourceId());
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
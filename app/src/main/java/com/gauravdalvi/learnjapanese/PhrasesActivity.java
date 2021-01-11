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

public class PhrasesActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_phrases);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Words> words= new ArrayList<Words>();
        words.add(new Words("Hello", "Kon'nichiwa", R.raw.phrases_1));
        words.add(new Words("What is your name?", "O-namae wa nan desu ka", R.raw.phrases_2));
        words.add(new Words("How are you?", "O-genki desu ka", R.raw.phrases_3));
        words.add(new Words("I'm fine. Thank you.", "Genki desu.", R.raw.phrases_4));
        words.add(new Words("Yes", "Hai", R.raw.phrases_5));
        words.add(new Words("No", "Īe", R.raw.phrases_6));
        words.add(new Words("Please", "O-negai shimasu", R.raw.phrases_7));
        words.add(new Words("Thank you", "Arigatō", R.raw.phrases_8));
        words.add(new Words("You're welcome", "Dōitashimashite", R.raw.phrases_9));
        words.add(new Words("Excuse me", "Sumimasen", R.raw.phrases_10));
        words.add(new Words("Good morning", "Ohayō gozaimasu", R.raw.phrases_11));
        words.add(new Words("Good evening", "Konbanwa", R.raw.phrases_12));
        words.add(new Words("Good night", "O-yasumi nasai", R.raw.phrases_13));

        ListView list3 = (ListView) findViewById(R.id.list3);

        WordAdapter adapter = new WordAdapter(this, words, R.color.phrases_activity_items);

        list3.setAdapter(adapter);

        list3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Words word = words.get(i);
                releaseMediaPlayer();

                int result = audioManager.requestAudioFocus(focusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudioResourceId());
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
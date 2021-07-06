package com.codemul.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity2 extends AppCompatActivity {
    /** Handles playback of all the sound files */
    private MediaPlayer mediaPlayer;

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    //menjadi variabel global karena akan mengurangi resources
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    /** Handles audio focus when playing a sound file */
    private AudioManager audioManager;

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                // Pause playback
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mediaPlayer.pause();

                //mengubah posisi untuk dimulai pada awal file audio. Maka dimulai dengan posisi nol
                //disebabkan jika kita memperoleh kembali audio focus akan lebih baik lagi bagi pengguna
                // untuk mendengar kata dari awal file audio daripada mendengar sisa dari kata tersebut
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // Create and setup the {@link AudioManager} to request audio focus
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //array
//        String [] listNumber = new String[10];
//        listNumber[0] = "one";
//        listNumber[1] = "two";

        //listArray harus pke final
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.audio_number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.audio_number_two));
        words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.audio_number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.audio_number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.audio_number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.audio_number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.audio_number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.audio_number_eight));
        words.add(new Word("nine", "wo’e", R.drawable.number_nine,R.raw.audio_number_nine));
        words.add(new Word("ten", "na’aacha", R.drawable.number_ten, R.raw.audio_number_ten));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                // Get the {@link Word} object at the given position the user clicked on
                Word word = words.get(position);

                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int result = audioManager.requestAudioFocus(onAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now.

                    //context dan id
                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mediaPlayer = MediaPlayer.create(NumbersActivity2.this, word.getAudioResourceId());
                    mediaPlayer.start();

                    //Setup a listener on media player, so that we can stop adn release the
                    //media player once the sounds has finished playing.
                    //onCompletionListener tidak dideklarasiin disini/private karena menambah resources baru
                    //diganti menjadi variabel global
                    mediaPlayer.setOnCompletionListener(onCompletionListener);

                    //Toast.makeText(NumbersActivity2.this, "Coba saja", Toast.LENGTH_SHORT).show();
                }
            }
        });



        //ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, words);
//        LinearLayout rootView = (LinearLayout) findViewById(R.id.rootView);
        //while loop
//        int index = 0;
//        while (index < words.size()){
//            //create a new that displayed the word at the current index
//            TextView wordView = new TextView(this);
//            wordView.setText(words.get(index));
//            //mnampilkan ke dalam view, Add this TextView as another child to the root view of this layout
//            rootView.addView(wordView);
//
//            //update counter variable
//            index++;
//        }

        //for loop
//        for (int index = 0; index < words.size(); index++){
//            //create a new that displayed the word at the current index
//            TextView wordView = new TextView(this);
//            wordView.setText(words.get(index));
//            //mnampilkan ke dalam view, Add this TextView as another child to the root view of this layout
//            rootView.addView(wordView);
//        }

//        for (int index = 0; index < 3; index++) {
//            Log.v("NumbersActivity", "Index:" + index + " Value:" + words.get(index));
//        }

//        Log.v("NumbersActivity", "word at index 0: " + listNumber[0]);
//        Log.v("NumbersActivity", "word at index 1: " + listNumber[1]);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources. mengurangi resources(memori)
     * dipanggil setelah start song
     */
    //diluar onCreate
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
}
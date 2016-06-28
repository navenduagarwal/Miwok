package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class ColorsFragment extends Fragment {

    //handles playback of all sound files
    private MediaPlayer mPlayer;
    private AudioManager mAudio;

    private AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mPlayer.pause();
                mPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                stopPlaying();
            }
        }
    };

    /**
     * This listener get triggered when the {@link MediaPlayer} has completed
     * playing the audio file
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mPlayer) {
            stopPlaying();
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        mAudio = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word(R.string.color_red, R.string.miwok_color_red,
                R.drawable.color_red, R.raw.color_red));
        words.add(new Word(R.string.color_mustard_yellow, R.string.miwok_color_mustard_yellow,
                R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
        words.add(new Word(R.string.color_dusty_yellow, R.string.miwok_color_dusty_yellow,
                R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word(R.string.color_green, R.string.miwok_color_green,
                R.drawable.color_green, R.raw.color_green));
        words.add(new Word(R.string.color_brown, R.string.miwok_color_brown,
                R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word(R.string.color_gray, R.string.miwok_color_gray,
                R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word(R.string.color_black, R.string.miwok_color_black,
                R.drawable.color_black, R.raw.color_black));
        words.add(new Word(R.string.color_white, R.string.miwok_color_white,
                R.drawable.color_white, R.raw.color_white));

        final WordAdapter wordAdapter = new WordAdapter(getActivity(), words, R.color.category_colors);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_word);
        listView.setAdapter(wordAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //get current position word
                Word word = wordAdapter.getItem(position);
                //stop if any audio is still playing
                stopPlaying();
                //Request audio focus for playback
                int result = mAudio.requestAudioFocus(
                        afChangeListener, //change listner
                        AudioManager.STREAM_MUSIC,//Use the music Stream
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT //Request transient focus
                );

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId());
                    //Start the audio file
                    mPlayer.start();
                    mPlayer.setOnCompletionListener(mCompletionListener);
                }
            }

        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        stopPlaying();
    }

    private void stopPlaying() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
            mAudio.abandonAudioFocus(afChangeListener);

        }
    }
}

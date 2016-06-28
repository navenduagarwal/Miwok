package com.example.android.miwok;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Navendu Agarwal on 27-Jun-16.
 */
public class WordAdapter extends ArrayAdapter<Word> {
    private int mColorResourceId;

    public WordAdapter(Context context, ArrayList<Word> items, int colorResourceId) {
        super(context, 0, items);
        mColorResourceId = colorResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Word word = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView miwokTrans = (TextView) convertView.findViewById(R.id.list_miwok_textview);
        miwokTrans.setText(word.getMiwokText());

        TextView defaultTrans = (TextView) convertView.findViewById(R.id.list_default_textview);
        defaultTrans.setText(word.getDefaultText());

        ImageView imageView = (ImageView) convertView.findViewById(R.id.list_image);
        if (word.hasImage()) {
            imageView.setImageResource(word.getImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }

        View textContainer = convertView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        textContainer.setBackgroundColor(color);

        return convertView;
    }
}

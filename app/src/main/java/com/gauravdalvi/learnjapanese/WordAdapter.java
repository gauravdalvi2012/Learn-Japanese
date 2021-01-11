package com.gauravdalvi.learnjapanese;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Words> {

    private int colorResourceId;

    public WordAdapter(@NonNull Context context, ArrayList<Words> numbers, int color) {
        super(context, 0, numbers);
        colorResourceId = color;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Words words = getItem(position);

        TextView englishNumberTextView = (TextView) listItemView.findViewById(R.id.englishTextView);
        englishNumberTextView.setText(words.getEnglishTranslation());

        TextView japaneseNumberTextView = (TextView) listItemView.findViewById(R.id.japaneseTextView);
        japaneseNumberTextView.setText(words.getJapaneseTranslation());

        ImageView image = (ImageView) listItemView.findViewById(R.id.imageView);

        if (words.getResourceImageId() == -1) {
            image.setVisibility(View.GONE);
        }
        else {
            image.setVisibility(View.VISIBLE);
            image.setImageResource(words.getResourceImageId());
        }

        View textContainer = listItemView.findViewById(R.id.text_container);

        int color = ContextCompat.getColor(getContext(), colorResourceId);

        textContainer.setBackgroundColor(color);

        return listItemView;
    }
}

package com.example.myexplorer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class FileItemView extends LinearLayout {

    TextView textViewName;
    TextView textViewPath;
    ImageView imageView;

    public FileItemView(Context context) {
        super(context);
        init(context);
    }

    public FileItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.file_item, this, true);

        textViewName = findViewById(R.id.textView);
        textViewPath = findViewById(R.id.textView2);
        imageView = findViewById(R.id.imageView);
    }

    public void setTextViews(String name, String path, int resId, int color){
        textViewName.setText(name);
        textViewPath.setText(path);
        textViewName.setTextColor(color);
        imageView.setImageResource(resId);
    }
}

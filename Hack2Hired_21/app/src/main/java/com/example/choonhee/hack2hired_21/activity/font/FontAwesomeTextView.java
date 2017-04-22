package com.example.choonhee.hack2hired_21.activity.font;

import android.content.Context;
import android.graphics.Typeface;

import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Cayden on 4/23/2017.
 */

public class FontAwesomeTextView extends TextView {
    public FontAwesomeTextView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        init();
    }
    public FontAwesomeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FontAwesomeTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");
        setTypeface(tf);
    }
}

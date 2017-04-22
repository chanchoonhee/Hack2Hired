package com.example.choonhee.hack2hired_21.activity.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Cayden on 4/23/2017.
 */

public class FontAwesomeButton extends Button {
    public FontAwesomeButton(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        init();
    }
    public FontAwesomeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FontAwesomeButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");
        setTypeface(tf);
    }
}

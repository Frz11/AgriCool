package com.example.fritzz.agricool;

import android.content.Context;
import android.widget.Button;

public class ButtonDelete extends android.support.v7.widget.AppCompatButton {
    public int itemId;


    public ButtonDelete(Context context,int itemId) {
        super(context);
        this.itemId = itemId;

    }
}

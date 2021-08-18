package com.example.androidswim;

public class Width {
    private OnWidthChangeListener listener;

    private float width;

    public void setOnWidthChangeListener(OnWidthChangeListener listener)
    {
        this.listener = listener;
    }

    public float get_width()
    {
        return width;
    }

    public void set_width(float value)
    {
        this.width = value;

        if(listener != null)
        {
            listener.onFloattChanged(value);
        }
    }
}

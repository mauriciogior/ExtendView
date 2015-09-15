package com.mauriciogiordano.extendview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mauricio Giordano on 5/17/15.
 * Author: Mauricio Giordano (mauricio.c.giordano@gmail.com)
 * License: GPLv2
 */
public class ExtendView extends FrameLayout {

    /**
     * Target layout references.
     */
    private ViewGroup extended;
    private ViewGroup container;

    private boolean hasAttached = false;

    public ExtendView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExtendView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // Our attributes.
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ExtendView,
                0, 0);

        try {
            // Layout reference.
            int layout = a.getResourceId(R.styleable.ExtendView_view_group_layout, -1);

            // Container to replace reference.
            int containerId = a.getResourceId(R.styleable.ExtendView_container_id, -1);

            // If one of them is not set, work as a FrameLayout.
            if (layout != -1 && containerId != -1) {

                // Inflate the Layout to extend.
                extended = (ViewGroup) LayoutInflater.from(context).inflate(layout, null, false);

                // If it's null (invalid reference), we can't do anything.
                if (extended == null) {
                    throw new RuntimeException("You must specify a valid layout!");
                }

                // Search for the container.
                container = (ViewGroup) extended.findViewById(containerId);

                // If it's null (invalid id), we can't do anything.
                if (container == null) {
                    throw new RuntimeException("You must specify a valid container id!");
                }

            // To avoid placing invalid layouts.
            } else {
                hasAttached = true;
            }

        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // Attach only one time.
        if (!hasAttached) {
            hasAttached = true;

            // Save all children.
            List<View> children = new ArrayList<>();

            for (int i=0; i<getChildCount(); i++) {
                children.add(getChildAt(i));
            }

            // Remove all children.
            removeAllViews();

            // Places the saved children to the container.
            for (View child : children) {
                container.addView(child);
            }

            // Add the extended view.
            addView(extended);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}

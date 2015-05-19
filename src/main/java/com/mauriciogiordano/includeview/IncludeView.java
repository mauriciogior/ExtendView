package com.mauriciogiordano.includeview;

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
 * Created by mauricio on 5/17/15.
 */
public class IncludeView extends FrameLayout {

    /**
     * Target layout references.
     */
    private ViewGroup included;
    private ViewGroup container;

    private boolean hasAttached = false;

    public IncludeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IncludeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // Our attributes.
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.IncludeView,
                0, 0);

        try {
            // Layout reference.
            int layout = a.getResourceId(R.styleable.IncludeView_view_group_layout, -1);

            // Container to replace reference.
            int containerId = a.getResourceId(R.styleable.IncludeView_container_id, -1);

            // If one of them is not set, work as a FrameLayout.
            if (layout != -1 && containerId != -1) {

                // Inflate the Layout to include.
                included = (ViewGroup) LayoutInflater.from(context).inflate(layout, null, false);

                // If it's null (invalid reference), we can't do anything.
                if (included == null) {
                    throw new RuntimeException("You must specify a valid layout!");
                }

                // Search for the container.
                container = (ViewGroup) included.findViewById(containerId);

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

            // Add the included view.
            // Yes, we have a useless FrameLayout.
            addView(included);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}

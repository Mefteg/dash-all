package com.dashall.ladybugriders.dashall;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Guideline;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by tom on 2017-10-01.
 */

public class RowLayout extends ConstraintLayout implements DashView {

    private final static String TAG = "DashAll - RowLayout";

    private enum Side {
        SIDE_LEFT,
        SIDE_RIGHT
    }

    private JSONObject m_data;

    private boolean m_isValid;

    public RowLayout(Context context, JSONObject data) {
        super(context);

        m_data = data;

        m_isValid = false;

        clean();
        build();
    }

    public JSONObject getData() {
        return m_data;
    }

    public void setData(JSONObject data) {
        m_data = data;
    }

    public boolean isValid() {
        return m_isValid;
    }

    public void clean() {
        removeAllViews();
    }

    public void build() {
        if (m_data != null) {
            inflate(getContext(), R.layout.row, this);

            try {
                JSONArray items = m_data.getJSONArray("items");
                int nbItems = items.length();

                if (nbItems > 0) {
                    JSONObject item = items.getJSONObject(0);
                    addWidget(Side.SIDE_LEFT, item);
                }

                if (nbItems > 1) {
                    JSONObject item = items.getJSONObject(1);
                    addWidget(Side.SIDE_RIGHT, item);
                }

                // valid if at least one widget has been added
                m_isValid = getChildCount() > 0;
            }
            catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    }

    private void addWidget(Side side, JSONObject item) throws Exception {
        int layoutId = side == Side.SIDE_LEFT ? R.id.layout_left : R.id.layout_right;
        ViewGroup layout = (ViewGroup) findViewById(layoutId);
        if (layout != null) {
            View widget = createDashWidget(item);

            if (widget != null) {
                layout.addView(widget);
            }
        }
        else {
            String strSide = side == Side.SIDE_LEFT ? "left" : "right";
            Log.e(TAG, "Layout " + strSide + " not found.");
        }
    }

    private View createDashWidget(JSONObject item) throws Exception {
        String type = item.getString("type");

        switch (type) {
            case "percentage":
                PercentageView widget = new PercentageView(getContext(), item);
                if (widget.isValid()) {
                    return widget;
                }
                break;
            default:
                break;
        }

        return null;
    }
}

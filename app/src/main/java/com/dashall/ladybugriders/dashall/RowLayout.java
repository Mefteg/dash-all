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
                    ViewGroup left = (ViewGroup) findViewById(R.id.layout_left);
                    if (left != null) {
                        PercentageView viewLeft = new PercentageView(getContext(), items.getJSONObject(0));
                        if (viewLeft.isValid()) {
                            left.addView(viewLeft);
                        }
                    }
                    else {
                        Log.e(TAG, "Layout left not found.");
                    }
                }

                if (nbItems > 1) {
                    ViewGroup right = (ViewGroup) findViewById(R.id.layout_right);
                    if (right != null) {
                        PercentageView viewRight = new PercentageView(getContext(), items.getJSONObject(1));
                        if (viewRight.isValid()) {
                            right.addView(viewRight);
                        }
                    }
                    else {
                        Log.e(TAG, "Layout right not found.");
                    }
                }

                m_isValid = true;
            }
            catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    }
}

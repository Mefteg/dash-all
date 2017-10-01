package com.dashall.ladybugriders.dashall;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.JSONStringer;

/**
 * Created by tom on 2017-10-01.
 */

public class PercentageView extends LinearLayout implements DashView {

    private final static String TAG = "PercentageView";

    private JSONObject m_data;

    private boolean m_isValid;

    private ConstraintLayout m_mainLayout;

    public PercentageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public PercentageView(Context context, JSONObject data) {
        super(context);

        m_data = data;

        init();
    }

    public JSONObject getData() {
        return m_data;
    }

    public void setData(JSONObject data) {
        m_data = data;

        clean();
        build();
    }

    public void clean() {
        if (m_mainLayout != null) {
            m_mainLayout.removeAllViews();
        }
    }

    public void build() {
        inflate(getContext(), R.layout.percentage, this);

        m_mainLayout = (ConstraintLayout) findViewById(R.id.layout_percentage);

        if (m_mainLayout != null && m_data != null) {
            setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));


            try {
                int percentage  = m_data.getInt("value");
                String name     = m_data.getString("name");

                TextView textViewPercentage = (TextView) m_mainLayout.findViewById(R.id.text_percentage);
                if (textViewPercentage != null) {
                    textViewPercentage.setText(percentage + "%");
                }
                else {
                    Log.e(TAG, "Text percentage not found.");
                }

                TextView textViewName = (TextView) m_mainLayout.findViewById(R.id.text_name);
                if (textViewName != null) {
                    textViewName.setText(name);
                }
                else {
                    Log.e(TAG, "Text name not found.");
                }

                m_isValid = true;
            }
            catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
        else {
            if (m_mainLayout == null) {
                Log.e(TAG, "Main layout not found.");
            }

            if (m_data == null) {
                Log.e(TAG, "Empyt data.");
            }
        }
    }

    public  boolean isValid() {
        return m_isValid;
    }

    private void init() {
        m_isValid = false;

        clean();
        build();
    }

    /*private void initValue() {
        m_value = new TextView(getContext());
        m_value.setText("100%");
        m_value.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        m_value.setTextColor(Color.BLACK);
        m_value.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        m_value.setGravity(Gravity.CENTER_VERTICAL);
        m_value.setTextSize(30);
    }

    private void initName() {
        m_name = new TextView(getContext());
        m_name.setText("Name");
        m_name.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        m_name.setTextColor(Color.BLACK);
        m_name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        m_name.setTypeface(m_name.getTypeface(), Typeface.BOLD);
    }*/
}

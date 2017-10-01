package com.dashall.ladybugriders.dashall;

import org.json.JSONObject;

/**
 * Created by tom on 2017-10-01.
 */

public interface DashView {
    JSONObject getData();
    void setData(JSONObject data);

    boolean isValid();

    void clean();
    void build();
}

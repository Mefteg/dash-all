package com.dashall.ladybugriders.dashall;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private final static String TAG = "DashAll - MainActivity";

    private final static String CONTENT_TYPE_JSON = "application/json";

    private AsyncHttpServer m_server;

    private JSONObject m_data;

    private LinearLayout m_mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initServer();

        m_mainLayout = (LinearLayout) findViewById(R.id.layout_main);
    }

    private void initServer() {
        m_server = new AsyncHttpServer();

        m_server.get("/", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                response.send("Hello!!!");
            }
        });

        m_server.post("/", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                if (request.getBody().getContentType() == CONTENT_TYPE_JSON) {
                    Object body = request.getBody().get();

                    try {
                        m_data = new JSONObject(body.toString());
                        Log.d(TAG, m_data.toString());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cleanUI();
                                buildUI();
                            }
                        });

                    }
                    catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                }

                response.send("Hello!!!");

            }
        });

        // listen on port 5000
        m_server.listen(5000);
    }

    private void cleanUI() {
        if (m_mainLayout != null) {
            m_mainLayout.removeAllViews();
        }
    }

    private void buildUI() {
        if (m_mainLayout != null && m_data != null) {

            Context ctx = getApplicationContext();

            try {
                JSONArray rows = m_data.getJSONArray("rows");
                Log.d(TAG, "ROWS: " + m_data.toString());

                for (int i=0; i<rows.length(); ++i) {
                    JSONObject rowData = rows.getJSONObject(i);
                    Log.d(TAG, "ROW DATA: " + rowData.toString());

                    RowLayout row = new RowLayout(ctx, rowData);
                    if (row.isValid()) {
                        m_mainLayout.addView(row);
                    }
                }
            }
            catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
        else {
            Log.e(TAG, "Main View not found.");
        }
    }
}

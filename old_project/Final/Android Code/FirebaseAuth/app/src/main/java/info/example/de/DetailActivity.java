package info.example.de;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private List<Object> recyclerViewItems = new ArrayList<>();
    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private static final String URL = "http://backendme.com/Harsh/get_call_details.php";
    private static String event_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.black,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);


        Bundle bundle = getIntent().getExtras();
        event_id = bundle.getString("event_id");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DetailAdapter(this,
                recyclerViewItems);
        recyclerView.setAdapter(adapter);
        fetchContacts(event_id);
    }

    private void fetchContacts(final String id) {
        mSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        Log.i("Log",ServerResponse);
                        addMenuItemsFromJson(ServerResponse);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("event_id", id);
                return params;
            }
        };

        Volley.newRequestQueue(this).getCache().clear();
        Volley.newRequestQueue(this).getCache().remove(URL);
        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }

    private void addMenuItemsFromJson(String jsonDataString) {

        try {
            recyclerViewItems.clear();
            JSONArray menuItemsJsonArray = new JSONArray(jsonDataString);
            Log.i("Log 2", String.valueOf(menuItemsJsonArray));

            for (int i = 0; i < menuItemsJsonArray.length(); ++i) {
                JSONObject menuItemObject = menuItemsJsonArray.getJSONObject(i);
                String event_id = menuItemObject.getString("event_id");
                String conntact_number = menuItemObject.getString("conntact_number");
                String name = menuItemObject.getString("first_name") + " "+menuItemObject.getString("last_name");
                String status_call = menuItemObject.getString("status_call");
                String confirm_status = menuItemObject.getString("confirm_status");

                DetailItem dashbboardItem = new DetailItem(event_id,conntact_number,name,status_call,confirm_status);
                recyclerViewItems.add(dashbboardItem);
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException exception) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        fetchContacts(event_id);
    }
}

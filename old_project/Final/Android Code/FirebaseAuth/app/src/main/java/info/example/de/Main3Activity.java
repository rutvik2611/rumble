package info.example.de;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wafflecopter.multicontactpicker.ContactResult;
import com.wafflecopter.multicontactpicker.LimitColumn;
import com.wafflecopter.multicontactpicker.MultiContactPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Main3Activity extends AppCompatActivity {
    private ProgressDialog dialog;
    private Calendar myCalendar;
    private TextView var4;
    private TextView var8;
    private Button date2;
    private Button button2;
    private ArrayList<ContactResult> myList = new ArrayList<>();
    private JSONArray jsonArray = new JSONArray();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        myCalendar = Calendar.getInstance();
        var4 = findViewById(R.id.textview2);
        var8 = findViewById(R.id.textView3);
        date2 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText var1 = findViewById(R.id.event_id);
                TextInputEditText var2 = findViewById(R.id.message);

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(!var4.getText().toString().equals("Select Your Date") &&  var1.getText().toString().trim().length() != 0 && var2.getText().toString().trim().length() != 0 && jsonArray.length() > 0){
                    fetchContacts(user.getEmail(),String.valueOf(jsonArray),var1.getText().toString(),var4.getText().toString(),var2.getText().toString());
                }else{
                    Toast.makeText(getBaseContext(),"Fill all data",Toast.LENGTH_LONG).show();

                }
            }
        });



        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        date2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Main3Activity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MultiContactPicker.Builder(Main3Activity.this) //Activity/fragment context
                        .hideScrollbar(false) //Optional - default: false
                        .showTrack(true) //Optional - default: true
                        .searchIconColor(Color.WHITE) //Option - default: White
                        .setChoiceMode(MultiContactPicker.CHOICE_MODE_MULTIPLE) //Optional - default: CHOICE_MODE_MULTIPLE
                        .handleColor(ContextCompat.getColor(Main3Activity.this, R.color.colorPrimary)) //Optional - default: Azure Blue
                        .bubbleColor(ContextCompat.getColor(Main3Activity.this, R.color.colorPrimary)) //Optional - default: Azure Blue
                        .bubbleTextColor(Color.WHITE) //Optional - default: White
                        .setTitleText("Select Contacts") //Optional - default: Select Contacts
                        .setSelectedContacts(myList) //Optional - will pre-select contacts of your choice. String... or List<ContactResult>
                        .setLoadingType(MultiContactPicker.LOAD_SYNC) //Optional - default LOAD_ASYNC (wait till all loaded vs stream results)
                        .limitToColumn(LimitColumn.NONE) //Optional - default NONE (Include phone + email, limiting to one can improve loading time)
                        .setActivityAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                                android.R.anim.fade_in,
                                android.R.anim.fade_out) //Optional - default: No animation overrides
                        .showPickerForResult(13);
            }
        });

    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        var4.setText(sdf.format(myCalendar.getTime()));
    }

    private void fetchContacts(final String email,final String payload,final String event_id,final String event_date,final String message) {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Creating your event, please wait...");
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://backendme.com/Harsh/set_call_details.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        Log.i("Got data",ServerResponse);
                        if(ServerResponse.contains("done")){
                            dialog.dismiss();
                            Toast.makeText(getBaseContext(),"Event Created",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Main3Activity.this,MainActivity.class);
                            startActivity(intent);
                        }else {
                            dialog.dismiss();
                            Toast.makeText(getBaseContext(),"Failed",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        dialog.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_email", email);
                params.put("payload", payload);
                params.put("event_id", event_id);
                params.put("event_date", event_date);
                params.put("message", message);


                return params;
            }
        };

        Volley.newRequestQueue(this).getCache().clear();
        Volley.newRequestQueue(this).getCache().remove("http://backendme.com/Harsh/set_call_details.php");
        MyApplication.getInstance().addToRequestQueue(stringRequest);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 13){
            int i = 0;
            if(resultCode == RESULT_OK) {
                List<ContactResult> results = MultiContactPicker.obtainResult(data);
                jsonArray = new JSONArray();
                var8 = findViewById(R.id.textView3);
                for( ;i<results.size();i++){
                    JSONObject student1 = new JSONObject();
                    try {
                        Log.i("Number",results.get(i).getPhoneNumbers().get(0).getNumber());
                        student1.put("contact_number", results.get(i).getPhoneNumbers().get(0).getNumber());
                        student1.put("first_name", results.get(i).getDisplayName());
                        student1.put("last_name", "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsonArray.put(student1);
                    var8.setText(Integer.toString(results.size()));
                }
            }else if(resultCode == RESULT_CANCELED){
                System.out.println("User closed the picker without selecting items.");
            }
        }
    }


}

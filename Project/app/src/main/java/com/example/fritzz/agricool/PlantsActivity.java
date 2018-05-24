package com.example.fritzz.agricool;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.w3c.dom.Text;

import java.io.IOException;

import static com.android.volley.Request.Method.GET;

public class PlantsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        final GlobalVariables gb = ((GlobalVariables)this.getApplication());
        final String serverIp = gb.getIp();
        final String  url = "http://"+serverIp+"/sql/selecter.php?query=SELECT * FROM CROPS;";
        final Context context = this;
        final Intent intent = getIntent();
        final User thisUser = gb.getThisUser();
       // final Model model = new Model();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants);
        TabLayout tabs = findViewById(R.id.plantsTabs);

        //for tab switching

        //final View allPlantsContent = (View) this.findViewById(R.id.allPlantsContent);
        //inal View myPlantsContent = (View) this.findViewById(R.id.myPlantsContent);


        //completeaza tabela cu toate plantele

        final TableLayout tablePlants = findViewById(R.id.plantsTable);
        tablePlants.setBackgroundColor(Color.LTGRAY);


        final RequestQueue queue = Volley.newRequestQueue(this);
        final StringRequest stringRequest = new StringRequest(GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("CropsResponse",response);
                        // Display the first 500 characters of the response string.
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT);

                        if(!response.equals("[]")){

                            ObjectMapper mapper = new ObjectMapper();
                            try {
                                Crops [] crops = mapper.readValue(response,Crops[].class);
                                gb.setCrops(crops);
                                for(int i = 0;i < crops.length;i++){
                                    TableRow row = new TableRow(getApplicationContext());
                                    String cropName = crops[i].crop_name;
                                    TextView tv = new TextView(getApplicationContext());

                                    tv.setText(cropName);
                                    tv.setTextSize(24);
                                    row.addView(tv);
                                    TableLayout.LayoutParams tableRowParams=
                                            new TableLayout.LayoutParams
                                                    (TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
                                    tableRowParams.setMargins(0,8,0,0);
                                    row.setLayoutParams(tableRowParams);
                                    tv.setTextColor(Color.WHITE);
                                    if(i%2 == 0){
                                        row.setBackgroundColor(Color.DKGRAY);
                                    } else {
                                        row.setBackgroundColor(Color.GRAY);
                                    }
                                    tablePlants.addView(row);

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                           // finish();
                         //   startActivity(new Intent(PlantsActivity.this,PlantsActivity.class));
                        } else {

                            Toast.makeText(getApplicationContext(), "No plants found!", Toast.LENGTH_LONG).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong...", Toast.LENGTH_LONG).show();

            }
        });
        queue.add(stringRequest);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_add_plant);
                dialog.setTitle("Add Plant");


                Button dialogButtonCancel = (Button) dialog.findViewById(R.id.buttonCancel);
                Button dialogButtonOk = (Button) dialog.findViewById(R.id.buttonOk);
                // if button is clicked, close the custom dialog
                dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialogButtonOk.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        String cropName = ((EditText) dialog.findViewById(R.id.cropName)).getText().toString();
                        String ph_min = ((EditText) dialog.findViewById(R.id.phMin)).getText().toString();
                        String ph_max = ((EditText) dialog.findViewById(R.id.phMax)).getText().toString();
                        String temp_min = ((EditText) dialog.findViewById(R.id.tempMin)).getText().toString();
                        String temp_max = ((EditText) dialog.findViewById(R.id.tempMax)).getText().toString();
                        String prec_min = ((EditText) dialog.findViewById(R.id.precMin)).getText().toString();
                        String prec_max = ((EditText) dialog.findViewById(R.id.precMax)).getText().toString();
                        if(cropName.equals("") || ph_min.equals("") || ph_max.equals("") ||
                                temp_max.equals("") || temp_min.equals("") ||
                                prec_max.equals("") || prec_min.equals("")){
                            Toast.makeText(context, "Complete all fields!", Toast.LENGTH_SHORT).show();
                        } else {
                            final String url = "http://"+serverIp+"/sql/inserter.php?query="+
                                    "INSERT INTO CROPS(crop_name,user_id,ph_min,ph_max,temp_min,temp_max,prec_min,prec_max)" +
                                    " VALUES('"+cropName+"','"+thisUser.user_id+"','"+ph_min+"','"+ph_max+"'" +
                                    ",'"+temp_min+"','"+temp_max+"','"+prec_min+"','"+prec_max+"');";
                            StringRequest request = new StringRequest(Request.Method.GET,url,
                                    new Response.Listener<String>(){
                                    @Override
                                        public void onResponse(String response){
                                        //Toast.makeText(getApplicationContext(), response,Toast.LENGTH_LONG);
                                        //Log.d("insert plant",response);
                                        //Log.d("url",url);
                                        dialog.dismiss();
                                        Intent intent = getIntent();
                                        finish();
                                        startActivity(intent);
                                    }
                            }, new Response.ErrorListener(){
                                @Override
                                public void onErrorResponse(VolleyError error) {}
                            });
                            queue.add(request);
                        }

                    }
                });
                dialog.show();
            }
        });
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String tabOption = tab.getText().toString();
                if(tabOption.equals("All Plants")){
                    tablePlants.removeAllViews();
                    TableRow row = new TableRow(getApplicationContext());
                    TextView tv = new TextView(getApplicationContext());
                    tv.setText("All Plants");
                    tv.setTextSize(36);
                    tablePlants.addView(tv);
                    String url = "http://"+serverIp+"/sql/selecter.php?query=SELECT * FROM CROPS;";

                    final StringRequest stringRequest = new StringRequest(GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("CropsResponse",response);
                                    // Display the first 500 characters of the response string.
                                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT);

                                    if(!response.equals("[]")){

                                        ObjectMapper mapper = new ObjectMapper();
                                        try {
                                            Crops [] crops = mapper.readValue(response,Crops[].class);
                                            gb.setCrops(crops);
                                            for(int i = 0;i < crops.length;i++){
                                                TableRow row = new TableRow(getApplicationContext());
                                                String cropName = crops[i].crop_name;
                                                TextView tv = new TextView(getApplicationContext());
                                                tv.setText(cropName);
                                                tv.setTextSize(24);
                                                row.addView(tv);
                                                TableLayout.LayoutParams tableRowParams=
                                                        new TableLayout.LayoutParams
                                                                (TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
                                                tableRowParams.setMargins(0,8,0,0);
                                                row.setLayoutParams(tableRowParams);
                                                tv.setTextColor(Color.WHITE);
                                                if(i%2 == 0){
                                                    row.setBackgroundColor(Color.DKGRAY);
                                                } else {
                                                    row.setBackgroundColor(Color.GRAY);
                                                }
                                                tablePlants.addView(row);

                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }


                                    } else {

                                        Toast.makeText(getApplicationContext(), "No plants found!", Toast.LENGTH_LONG).show();

                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Something went wrong...", Toast.LENGTH_LONG).show();

                        }
                    });
                    queue.add(stringRequest);
                } else {
                    tablePlants.removeAllViews();
                    TableRow row = new TableRow(getApplicationContext());
                    TextView tv = new TextView(getApplicationContext());
                    tv.setText("My Plants");
                    tv.setTextSize(36);
                    tablePlants.addView(row);
                    row.addView(tv);

                    String url = "http://"+serverIp+"/sql/selecter.php?query=SELECT * FROM CROPS WHERE user_id="+thisUser.user_id+";";

                    final StringRequest stringRequest = new StringRequest(GET, url,
                            new Response.Listener<String>() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onResponse(String response) {
                                    Log.d("CropsResponse",response);
                                    // Display the first 500 characters of the response string.
                                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT);

                                    if(!response.equals("[]")){

                                        ObjectMapper mapper = new ObjectMapper();
                                        try {
                                            Crops [] crops = mapper.readValue(response,Crops[].class);
                                            gb.setCrops(crops);
                                            for(int i = 0;i < crops.length;i++){
                                                TableRow row = new TableRow(getApplicationContext());
                                                String cropName = crops[i].crop_name;
                                                TextView tv = new TextView(getApplicationContext());
                                                LinearLayout ll = new LinearLayout(getApplicationContext());
                                                final ButtonDelete button = new ButtonDelete(getApplicationContext(),crops[i].crop_id);
                                                button.setBackgroundResource(R.drawable.ic_button_delete);
                                                button.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        //Toast.makeText(getApplicationContext(),button.itemId+"",Toast.LENGTH_LONG).show();
                                                        final String url = "http://"+serverIp+"/sql/deleter.php?query=DELETE FROM CROPS WHERE user_id="+thisUser.user_id+" AND" +
                                                                " crop_id="+button.itemId+";";
                                                        StringRequest request = new StringRequest(GET, url, new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                //Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();
                                                                Intent intent = getIntent();
                                                                finish();
                                                                startActivity(intent);
                                                            }
                                                        }, new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                                Toast.makeText(getApplicationContext(),"Couldn't delete!",Toast.LENGTH_LONG).show();

                                                            }
                                                        });
                                                        queue.add(request);

                                                    }
                                                });
                                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                        LinearLayout.LayoutParams.WRAP_CONTENT);
                                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300,300);
                                                //button.setTop(-7);
                                                //lp.setMargins(700,0,0,0);
                                                //params.setMargins(0,-7,0,0);
                                                button.setGravity(0);
                                                //button.set
                                                tv.setText(cropName);
                                                tv.setTextSize(24);
                                                ll.addView(tv);
                                                ll.addView(button);
                                                row.addView(ll);
                                                //row.addView(button);
                                                //button.setY(row.getY()/2);
                                                //button.setX(row.getX());

                                                TableLayout.LayoutParams tableRowParams=
                                                        new TableLayout.LayoutParams
                                                                (TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
                                                tableRowParams.setMargins(0,8,0,0);
                                                row.setLayoutParams(tableRowParams);
                                                tv.setTextColor(Color.WHITE);
                                                if(i%2 == 0){
                                                    row.setBackgroundColor(Color.DKGRAY);
                                                } else {
                                                    row.setBackgroundColor(Color.GRAY);
                                                }
                                                tablePlants.addView(row);

                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }


                                    } else {

                                        Toast.makeText(getApplicationContext(), "No plants found!", Toast.LENGTH_LONG).show();

                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Something went wrong...", Toast.LENGTH_LONG).show();

                        }
                    });
                    queue.add(stringRequest);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}

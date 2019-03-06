package fusion.didan_billing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static String LOG_TAG = "my_log";
    public static String URL_CHECK = "path_to_php_script";
    private ProgressDialog pDialog;

    Button btnLogin;
    EditText fieldLogin, fieldPassword;
    TextView textViewReminder;
    Intent iExp;
    SharedPreferences sPref;

    String remindMessage = "Если вы забыли свой логин/пароль наберите техническую поддержку: ";
    int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        fieldLogin = findViewById(R.id.fieldLogin);
        fieldPassword = findViewById(R.id.fieldPassword);
        textViewReminder = findViewById(R.id.textViewReminder);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = fieldLogin.getText().toString().trim();
                String password = fieldPassword.getText().toString().trim();
                if (!login.isEmpty() && !password.isEmpty()) {
                    //new RequestHandler().checkUserData(login, password);
                    checkUserData(login, password);
                    //iExp = new Intent(new Intent(MainActivity.this, UserData.class));
                    //startActivity(iExp);
                    //Toast.makeText(getApplicationContext(),
                    //       "Redirecting...",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Неправильный логин или пароль", Toast.LENGTH_SHORT).show();
                    counter--;
                    if (counter == 0) {
                        btnLogin.setEnabled(false);
                        textViewReminder.setVisibility(View.VISIBLE);
                        textViewReminder.setText(remindMessage);
                        new CountDownTimer(10000, 10) { //Set Timer for 5 seconds
                            public void onTick(long millisUntilFinished) {
                            }

                            @Override
                            public void onFinish() {
                                btnLogin.setEnabled(true);
                            }
                        }.start();
                    }
                }
            }
        });
    }
    public  void checkUserData(final String login, final String password) {

        // Tag used to cancel the request
        String tag_string_req = "req_login";
        pDialog.setMessage("Подключаемся ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, URL_CHECK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    // Check for error node in json
                    //if (!error) {
                        // Now store the user in SQLite
                        String id = jObj.getString("id");
                        String uid = jObj.getString("uid");
                        String password = jObj.getString("password");
                        String fio = jObj.getString("fio");
                        String address_street = jObj.getString("address_street");
                        String address_build = jObj.getString("address_build");
                        String address_flat = jObj.getString("address_flat");
                        String deposit = jObj.getString("deposit");
                        String credit = jObj.getString("credit");
                        String credit_date = jObj.getString("credit_date");
                        String tarif = jObj.getString("tarif");
                        int day_fee = jObj.getInt("day_fee");

                        Log.d(LOG_TAG, id);
                        Log.d(LOG_TAG, uid);

//**********************
                    // Inserting row in users table
                    //db.addUser(name, email, uid, created_at);

                    // Launch UserData activity with received data
                    iExp = new Intent(new Intent(MainActivity.this, UserDataActivity.class));
                    //iExp.putExtra("id", id);
                    //iExp.putExtra("login", login);
                    //iExp.putExtra("name", name);
                    //iExp.putExtra("surname", surname);
                    //iExp.putExtra("patronymic", patronymic);
                    //iExp.putExtra("tarif", tarif);
                    //iExp.putExtra("balance", balance);

                    sPref = getSharedPreferences("Mypref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sPref.edit();
                    editor.clear();
                    editor.putString("uid",uid);
                    editor.putString("id",id);
                    editor.putString("password",password);
                    editor.putString("fio",fio);
                    editor.putString("address_street",address_street);
                    editor.putString("address_build", address_build);
                    editor.putString("address_flat", address_flat);
                    editor.putString("deposit", deposit);
                    editor.putString("credit", credit);
                    editor.putString("credit_date", credit_date);
                    editor.putString("tarif", tarif);
                    editor.putInt("day_fee", day_fee);
                    editor.apply();

                    startActivity(iExp);
// **********************
                   /*} NADO else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }*/
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Ошибка входа: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("login", login);
                params.put("password", password);

                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
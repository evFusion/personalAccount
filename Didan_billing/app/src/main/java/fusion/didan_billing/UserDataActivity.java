/** This class manage to display all user information
 @author fusion
 @version 1.0
 */

package fusion.didan_billing;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import fusion.didan_billing.fragments.FragmentPayment;
import fusion.didan_billing.fragments.FragmentTickets;
import fusion.didan_billing.fragments.FragmentTicketsForm;
import fusion.didan_billing.fragments.FragmentVoucher;

import static fusion.didan_billing.R.id.balanceInfoNumber;
import static fusion.didan_billing.R.id.creditStatus;
import static fusion.didan_billing.R.id.customerAccount;
import static fusion.didan_billing.R.id.customerAddress;
import static fusion.didan_billing.R.id.customerFio;
import static fusion.didan_billing.R.id.customerMobile;
import static fusion.didan_billing.R.id.customerSession;
import static fusion.didan_billing.R.id.customerTariff;
import static fusion.didan_billing.R.id.recommendedForPayment;

public class UserDataActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    FragmentTickets.OnFragmentInteractionListener,
                    FragmentTicketsForm.OnFragmentInteractionListener {

    private static final String TAG = UserDataActivity.class.getSimpleName();
    public static String LOG_TAG = "my_log";
    public static String URL_SET_CREDIT = "path/to/the/script";
    private ProgressDialog pDialog;
    public boolean flagActivateButton;
    public String creditStatusMsg;

    TextView tvFio, tvAccount, tvAddress, tvMobile, tvSesTime, tvTariff, tvDeposit, tvCreditStatus, tvRecomendedPay;
    Button btnPay, btnCredit;
    Intent iExp;
    SharedPreferences sPref;

    FragmentPayment fpayment;
    FragmentVoucher fvoucher;
    FragmentTickets ftickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdata);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        tvDeposit = findViewById(balanceInfoNumber);
        tvAddress = findViewById(customerAddress);
        tvMobile = findViewById(customerMobile);
        tvSesTime = findViewById(customerSession);
        tvAccount = findViewById(customerAccount);
        tvFio = findViewById(customerFio);
        tvTariff = findViewById(customerTariff);
        btnPay = findViewById(R.id.btnPay);
        btnCredit = findViewById(R.id.btnCredit);
        tvCreditStatus = findViewById(creditStatus);
        tvRecomendedPay = findViewById(recommendedForPayment);

        DecimalFormat df = new DecimalFormat("0.00");

        sPref = getSharedPreferences("Mypref", Context.MODE_PRIVATE);

        final String uidPref = sPref.getString("uid", "");
        String idPref = sPref.getString("id", "");
        String passwordPref = sPref.getString("password", "");
        String fioPref = sPref.getString("fio", "");
        String streetPref = sPref.getString("addressStreet", "");
        String buildPref = sPref.getString("addressBuild", "");
        String flatPref = sPref.getString("addressFlat", "");
        //float depositPref = Float.valueOf(sPref.getString("deposit", ""));
        float depositPref = sPref.getFloat("deposit", 1);
        String creditPref = sPref.getString("credit", "");
        String mobile = sPref.getString("mobile", "");
        String sessionTime = sPref.getString("sessionTime", "");
        String creditDatePref = sPref.getString("creditDate", "");
        String tarifPref = sPref.getString("tarif", "");
        float dayFeePref = sPref.getFloat("dayFee", 1);
        int usecredPref = sPref.getInt("usecred", 1);


        tvDeposit.setText(df.format(depositPref) + " \u20BD");//₽
        float recomendedPay = ((dayFeePref*30) - depositPref);
        tvRecomendedPay.setText(df.format(recomendedPay) + " \u20BD");

        if ((usecredPref == 0) && (depositPref > 0)) {
            creditStatusMsg = "Кредит не активен";
            flagActivateButton = true;
        } else if ((usecredPref == 0) && (depositPref <= 0)){
            creditStatusMsg = "Кредит не активен";
        } else {
            creditStatusMsg = "Кредит активен";
            flagActivateButton = true;
        }
        tvCreditStatus.setText(creditStatusMsg);

        tvAddress.setText(streetPref + " " + buildPref + ", " + flatPref);
        tvAccount.setText(uidPref);
        tvFio.setText(fioPref);
        tvTariff.setText(tarifPref + "\n" +  " (" + dayFeePref*30 + " руб. | мес.)" + "\n" + " (" + df.format(dayFeePref) +  " руб. | день)");
        tvMobile.setText(mobile);
        tvSesTime.setText(sessionTime);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iExp = new Intent(new Intent(UserDataActivity.this, PaymentActivity.class));
                startActivity(iExp);
            }
        });
        btnCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flagActivateButton) {
                    Toast.makeText(getApplicationContext(), "Лимит кредитов исчерпан", Toast.LENGTH_LONG).show();
                    btnCredit.setEnabled(false);
                } else {
                    setCredit(uidPref);
                }
            }
        });
//*********************NAVIGATION DRAWER***********************************
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fpayment = new FragmentPayment();
        fvoucher = new FragmentVoucher();
        ftickets = new FragmentTickets();
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case R.id.money_fragment:
                setTitle("Денежный счет");
                fTransaction.replace(R.id.container, fpayment);
                break;
            case R.id.info_fragment:
                setTitle("Информация");
                Intent c = new Intent(UserDataActivity.this, UserDataActivity.class);
                startActivity(c);
                break;
            case R.id.card_fragment:
                setTitle("Карта пополнения");
                fTransaction.replace(R.id.container, fvoucher);
                break;
            case R.id.tickets_fragment:
                setTitle("Заявки");
                //fTransaction.replace(R.id.container, ftickets);
                fTransaction.add(R.id.container, new FragmentTickets());
                break;
            //case R.id.settings_fragment:
            default:
                break;
        }

        fTransaction.commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//*********************NAVIGATION DRAWER***********************************

    private void setCredit(final String uid) {

        // Tag used to cancel the request
        String tag_string_req = "req_credit";

            btnCredit.setEnabled(true);
            Log.d(LOG_TAG, uid);
            pDialog.setMessage("Активируем отложенный платеж ...");
            showDialog();

            StringRequest strReq = new StringRequest(Request.Method.POST, URL_SET_CREDIT,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "Credit Response: " + response.toString());
                            hideDialog();

                            try {
                                JSONObject jObj = new JSONObject(response);
                                //boolean error = jObj.getBoolean("error"); 111
                                String message = jObj.getString("message");
                                Log.d(LOG_TAG, message);
                                //if (!error) { 111
                                // User successfully stored in MySQL
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                tvCreditStatus.setText("Кредит включен");
                                //Toast.makeText(getApplicationContext(), "CREDIT YES", Toast.LENGTH_LONG).show();
                                // } else {  111
                                // Error occurred in the activation of the credit. Get the error message
                                //String errorMsg = jObj.getString("error_msg");
                                //Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                                //}
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Credit Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting params to register url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("uid", uid);

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

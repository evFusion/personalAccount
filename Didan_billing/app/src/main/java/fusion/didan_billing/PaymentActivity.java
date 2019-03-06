/** This class used to for display all payments types and links to another activities.
* @author fusion
* @version 1.0
*/

package fusion.didan_billing;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class PaymentActivity extends AppCompatActivity implements OnClickListener {

    public static String LOG_TAG = "my_log";
    private static final int ERROR_MAP_APPEAR = 404;
    //Bundle arguments;
    Intent iExp;
    CardView cardViewCards, cardViewWM, cardViewAtm, cardViewCd, cardViewBank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        cardViewCards = findViewById(R.id.cardViewCards);
        cardViewWM = findViewById(R.id.cardViewWM);
        cardViewAtm = findViewById(R.id.cardViewAtm);
        cardViewCd = findViewById(R.id.cardViewCd);
        cardViewBank = findViewById(R.id.cardViewBank);
        cardViewAtm.setOnClickListener(this);
        cardViewWM.setOnClickListener(this);
        cardViewCards.setOnClickListener(this);
        cardViewCd.setOnClickListener(this);
        cardViewBank.setOnClickListener(this);
    }

    public boolean isServicesOK(){
        Log.d(LOG_TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(PaymentActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(LOG_TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(LOG_TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(PaymentActivity.this, available, ERROR_MAP_APPEAR);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardViewAtm:
                iExp = new Intent(new Intent(PaymentActivity.this, AtmPaymentActivity.class));
                //Bundle arguments = getIntent().getExtras();
                //arguments = getIntent().getExtras();

                /*if(arguments != null) {
                    String id = arguments.get("id").toString();
                    String name = arguments.get("name").toString();
                    String surname = arguments.get("surname").toString();
                    String patronymic = arguments.get("patronymic").toString();
                    Log.d(LOG_TAG, id);
                    Log.d(LOG_TAG, name);
                    Log.d(LOG_TAG, surname);
                    Log.d(LOG_TAG, patronymic);
                    iExp.putExtra("id", id);
                    iExp.putExtra("name", name);
                    iExp.putExtra("surname", surname);
                    iExp.putExtra("patronymic", patronymic);
                }*/
                startActivity(iExp);
                break;
            case R.id.cardViewCards:
                iExp = new Intent(new Intent(PaymentActivity.this, VoucherPaymentActivity.class));
                startActivity(iExp);
                break;
            case R.id.cardViewWM:
                iExp = new Intent(new Intent(PaymentActivity.this, WmPaymentActivity.class));
                startActivity(iExp);
                break;
            case R.id.cardViewCd:
                if(isServicesOK()) {
                    iExp = new Intent(new Intent(PaymentActivity.this, MapActivity.class));
                    startActivity(iExp);
                }
                break;
            case R.id.cardViewBank:
                iExp = new Intent(new Intent(PaymentActivity.this, BankPaymentActivity.class));
                startActivity(iExp);
                break;
            default: break;
        }
    }
}
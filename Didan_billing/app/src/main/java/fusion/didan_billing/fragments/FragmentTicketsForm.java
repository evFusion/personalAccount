package fusion.didan_billing.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fusion.didan_billing.AppController;
import fusion.didan_billing.R;
import fusion.didan_billing.UserDataActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentTicketsForm.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentTicketsForm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTicketsForm extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private static final String TAG = FragmentTickets.class.getSimpleName();
    public static String LOG_TAG = "my_log";
    public static String URL_ADD_NEW_TICKETS = "path_to_the_script";

    private ProgressDialog pDialog;

    SharedPreferences sPref;

    public FragmentTicketsForm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTicketsForm.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTicketsForm newInstance(String param1, String param2) {
        FragmentTicketsForm fragment = new FragmentTicketsForm();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tickets_form, container, false);

        sPref = this.getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);

        final String uidPref = sPref.getString("uid", "");

        Button buttonCreate = view.findViewById(R.id.btnCreateTicket);
        final EditText editTextTheme = view.findViewById(R.id.fieldTicketTheme);
        final EditText editTextText = view.findViewById(R.id.fieldTicketText);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textTheme = editTextTheme.getText().toString().trim();
                String textBody = editTextText.getText().toString().trim();

                if (!textTheme.isEmpty() && !textBody.isEmpty()) {
                    createNewTicket(uidPref, textTheme, textBody);
                } else {
                    Toast.makeText(getActivity(), "Заполните пожалуйста все поля!", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void createNewTicket(final String uid, final String theme, final String text) {

        // Tag used to cancel the request
        String tag_string_req = "req_insert";

        Log.d(LOG_TAG, uid);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Ваша заявка появится тут немного позже");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, URL_ADD_NEW_TICKETS,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Create Ticket Response: " + response.toString());
                        hideDialog();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            String message = jObj.getString("message");
                            Log.d(LOG_TAG, message);
                            // Ticket successfully stored in MySQL
                            Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

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
                Log.e(TAG, "Create ticket error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to create url
                Map<String, String> params = new HashMap<>();
                params.put("uid", uid);
                params.put("theme", theme);
                params.put("text", text);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(getActivity(), UserDataActivity.class));
            }
        }, 2000);


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

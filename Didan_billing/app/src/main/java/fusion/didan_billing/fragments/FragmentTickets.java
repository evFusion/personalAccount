package fusion.didan_billing.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import fusion.didan_billing.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentTickets.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentTickets#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTickets extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FloatingActionButton fab;

    private OnFragmentInteractionListener mListener;

    private static final String TAG = FragmentTickets.class.getSimpleName();
    public static String LOG_TAG = "my_log";
    private ProgressDialog pDialog;

    SharedPreferences sPref;
    TextView tvPolomka, tvComment, tvAddDate, tvState, tvCurrentTicket;
    LinearLayout llInfoTicket;

    public FragmentTickets() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTickets.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTickets newInstance(String param1, String param2) {
        FragmentTickets fragment = new FragmentTickets();
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
        String stateTickets = "Заявка выполняется";
        sPref = this.getActivity().getSharedPreferences("Mypref", Context.MODE_PRIVATE);

        final String uidPref = sPref.getString("uid", "");
        String polomkaPref = sPref.getString("polomka", "");
        String commentsPref = sPref.getString("comments", "");
        String addDatePref = sPref.getString("addDate", "");

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        View view = inflater.inflate(R.layout.fragment_tickets, container, false);

        tvPolomka = view.findViewById(R.id.textPolomkaText);
        tvAddDate = view.findViewById(R.id.textAddDateText);
        tvState = view.findViewById(R.id.textStateText);
        tvCurrentTicket = view.findViewById(R.id.currentTicket);
        llInfoTicket = view.findViewById(R.id.linearLayout);

        Log.d(LOG_TAG, polomkaPref);

        if (!polomkaPref.equals("null")) {
            tvPolomka.setText(polomkaPref);
            tvAddDate.setText(addDatePref);
            tvState.setText(stateTickets);
            tvCurrentTicket.setVisibility(View.INVISIBLE);
        } else {
            llInfoTicket.setVisibility(View.INVISIBLE);
        }

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                //}
                FragmentTicketsForm fragmentTicketsForm = new FragmentTicketsForm();
                FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
                fTransaction.replace(R.id.container, fragmentTicketsForm);
                fTransaction.addToBackStack(null);
                fTransaction.commit();
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

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
}

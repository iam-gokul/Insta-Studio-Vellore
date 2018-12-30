package com.playstore.gokul.mysquad;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {
    View mView;
    TextView r1,r2,r3,r4;
    String s1,s2,s3,s4,uid;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate (R.layout.fragment_order, container, false);

        r1=mView.findViewById (R.id.ordera11);
        r2=mView.findViewById (R.id.ordera12);
        r3=mView.findViewById (R.id.ordera21);
        r4=mView.findViewById (R.id.ordera22);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        uid =currentFirebaseUser.getUid ().toString ();
        DatabaseReference myRef = database.getReference().child ("bookings").child (uid);

        myRef.addValueEventListener(new ValueEventListener () {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                s1 = dataSnapshot.child ("userdate").getValue(String.class);
                s2=dataSnapshot.child ("status").getValue (String.class);
                r1.setText (s1);

                if(s2!=null) {
                    r2.setText (s2);
                }
                else{
                    r2.setText ("Waiting..");
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        return mView;
    }

}

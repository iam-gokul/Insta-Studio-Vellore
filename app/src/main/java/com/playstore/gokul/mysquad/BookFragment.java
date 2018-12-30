package com.playstore.gokul.mysquad;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.alespero.expandablecardview.ExpandableCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends Fragment {
    RecyclerView expanderRecyclerView;
    View mView;
    CalendarView mCalendarView;
    TextView myDate;
    MultiAutoCompleteTextView location;
    Button book;
    private DatabaseReference mDatabase;
    EditText lastname;
    EditText firstname;
    String fname,lname,address,fundate,uid,phone;





    public BookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView= inflater.inflate (R.layout.fragment_book, container, false);

        ExpandableCardView card = mView.findViewById(R.id.profile);

        mCalendarView =mView.findViewById (R.id.calender_view);
        myDate =mView.findViewById (R.id.bookDate);

        firstname = mView.findViewById (R.id.bookfirstname);
        fname=firstname.getText ().toString();

        lastname = mView.findViewById (R.id.booklastname);
        lname=lastname.getText ().toString ();

        location = mView.findViewById (R.id.bookLocAddress);
        address=location.getText ().toString ();


        book = mView.findViewById (R.id.bookDone);

       //Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        uid=currentFirebaseUser.getUid ();

        phone=mDatabase.child ("bookings").child (uid).child ("phonenumber").getDatabase ().toString ();




        mCalendarView.setOnDateChangeListener (new CalendarView.OnDateChangeListener () {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                fundate = dayOfMonth+"/"+month+"/"+year;
                myDate.setText (fundate);

            }
        });


        book.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                writeNewBooking (uid,fname,lname,address,fundate,phone);

            }
        });

        return mView;
    }


    //User Class
    @IgnoreExtraProperties
    public class User {

        public String firstName,lastName,address,userdate,number;


        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String firstName, String lastName,String address,String userdate,String number) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.userdate = userdate;
            this.number=number;

        }

    }

    //Function to add Values in DB
    private void writeNewBooking(String userId,String firstName, String lastName,String address,String userdate,String number) {
        User user = new User(firstName,lastName,address,userdate,number);

        mDatabase.child("bookings").child(userId).setValue(user);
    }


}

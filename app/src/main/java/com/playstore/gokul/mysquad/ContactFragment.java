package com.playstore.gokul.mysquad;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {


    public ContactFragment() {
        // Required empty public constructor
    }
    View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView= inflater.inflate (R.layout.fragment_contact, container, false);

        mView.findViewById (R.id.fbbutt).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                String url = getFacebookPageURL (getActivity ());

                Intent i = new Intent (Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });


        mView.findViewById (R.id.instabutt).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.instagram.com/insta_studio_vellore/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/insta_studio_vellore/")));
                }

            }
        });

        mView.findViewById (R.id.ytbutt).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+"UvHa-GinRgM"));
                startActivity(i);

            }
        });




        return mView;


    }
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;

            boolean activated =  packageManager.getApplicationInfo("com.facebook.katana", 0).enabled;
            if(activated){
                if ((versionCode >= 3002850)) {
                    return "fb://facewebmodal/f?href=" + "https://www.facebook.com/instaphotographyofficial/";
                } else {
                    return "fb://page/" + "instaphotographyofficial" ;
                }
            }else{
                return "https://www.facebook.com/instaphotographyofficial/";
            }
        } catch (PackageManager.NameNotFoundException e) {
            return "https://www.facebook.com/instaphotographyofficial/";
        }
    }

}

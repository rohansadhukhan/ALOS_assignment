package com.example.assignment.fragment;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.assignment.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Contact#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Contact extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Contact() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contact.
     */
    // TODO: Rename and change types and number of parameters
    public static Contact newInstance(String param1, String param2) {
        Contact fragment = new Contact();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        String url = "https://www.google.co.in/maps/place/Howrah";

        WebView mapview = view.findViewById(R.id.web_view);
        mapview.getSettings().setJavaScriptEnabled(true);
        mapview.loadUrl(url);

        ShapeableImageView email = view.findViewById(R.id.goto_email);
        email.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/html");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"company@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Test Subject");
            intent.putExtra(Intent.EXTRA_TEXT, "Test email body");

            PackageManager packManager = getContext().getPackageManager();
            List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

            boolean resolved = false;
            for (ResolveInfo resolveInfo : resolvedInfoList) {
                if (resolveInfo.activityInfo.packageName.startsWith("com.google.android.gm")) {
                    intent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                    resolved = true;
                    break;
                }
            }
            startActivity(intent);
            if (!resolved) {
                Toast.makeText(getContext(), "Twitter app isn't found", Toast.LENGTH_LONG).show();
            }
        });

        ShapeableImageView chat = view.findViewById(R.id.goto_chat);
        chat.setOnClickListener(v -> {
            Toast.makeText(getContext(),"Chat is not enebled", Toast.LENGTH_SHORT).show();
        });

        ShapeableImageView fb = view.findViewById(R.id.follow_facebook);
        fb.setOnClickListener(v -> {
            String fb_url = "https://www.facebook.com/rohan.sadhukhan.9/";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(fb_url));
            startActivity(intent);
        });

        ShapeableImageView insta = view.findViewById(R.id.follow_instagram);
        insta.setOnClickListener(v -> {
            String insta_url = "https://www.instagram.com/instagram/";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(insta_url));
            startActivity(intent);
        });

        ShapeableImageView tweet = view.findViewById(R.id.follow_twitter);
        tweet.setOnClickListener(v -> {
            String tweet_url = "https://twitter.com/google?lang=en";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(tweet_url));
            startActivity(intent);
        });

        ShapeableImageView tele = view.findViewById(R.id.follow_telegram);
        tele.setOnClickListener(v -> {
            String tele_url = "https://t.me/Crypto";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(tele_url));
            startActivity(intent);
        });

        return view;
    }
}
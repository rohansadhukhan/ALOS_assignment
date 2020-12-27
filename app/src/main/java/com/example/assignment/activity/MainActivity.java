package com.example.assignment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.assignment.R;
import com.example.assignment.Utility;
import com.example.assignment.fragment.Contact;
import com.example.assignment.fragment.Courses;
import com.example.assignment.fragment.Invite;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mStore;
    private String TAG = "my";

    private MaterialTextView headerName;
    private MaterialTextView headerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Courses()).commit();
            navigationView.setCheckedItem(R.id.courses);
        }

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mStore = FirebaseFirestore.getInstance();

        headerEmail = findViewById(R.id.header_user_email);
        headerName = findViewById(R.id.header_user_name);


        if (mUser != null) {
            mStore.collection("users")
                    .document(mUser.getUid())
                    .addSnapshotListener((value, error) -> {
                        if (value != null) {
                            Utility.NAME = value.getString("Name");
                            Utility.EMAIL = value.getString("Email");
                            Log.d(TAG, Utility.NAME);
                        }
                    });
        }

        if(Utility.NAME != null) {
            headerName.setText(Utility.NAME);
        }

        if(Utility.EMAIL != null) {
            headerEmail.setText(Utility.EMAIL);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {

        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.courses: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Courses()).commit();
                break;
            }
//            case R.id.code_kata : {
//                break;
//            }
//            case R.id.rewards : {
//                break;
//            }
//            case R.id.discussion : {
//                break;
//            }
            case R.id.invite_friends: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Invite()).commit();
                break;
            }
            case R.id.contact_us: {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Contact()).commit();
                break;
            }
            case R.id.signOut: {
                mAuth.signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            }
            default:
                return false;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
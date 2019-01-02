package com.pb.elite;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.pb.elite.aboutUs.AboutUsFragment;
import com.pb.elite.core.APIResponse;
import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.controller.register.RegisterController;
import com.pb.elite.core.model.UserConstatntEntity;
import com.pb.elite.core.model.UserEntity;
import com.pb.elite.core.response.UserConsttantResponse;
import com.pb.elite.dashboard.DashBoardFragment;
import com.pb.elite.database.DataBaseController;
import com.pb.elite.login.ChangePasswordFragment;
import com.pb.elite.login.LoginActivity;
import com.pb.elite.notification.NotificationActivity;
import com.pb.elite.orderDetail.OrderDetailFragment;

import com.pb.elite.servicelist.ProductHomeFragment;
import com.pb.elite.profile.ProfileFragment;
import com.pb.elite.splash.PrefManager;
import com.pb.elite.splash.SplashScreenActivity;
import com.pb.elite.term.TermsConditionFragment;
import com.pb.elite.utility.Constants;
import com.pb.elite.utility.Utility;

public class HomeActivity extends BaseActivity implements IResponseSubcriber {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    boolean doubleBackToExitPressedOnce = false;
    private Toolbar toolbar;
    public static int navItemIndex = 0;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    TextView textNotifyItemCount, txtVehicle, txtName;

    UserEntity loginEntity;
    UserConstatntEntity userConstatntEntity;

    private static final String TAG_HOME = "Home";
    private static final String TAG_PROFILE = "Profile";
    private static final String TAG_ORDER = "Request Detail";
    private static final String TAG_DOC = "Document Upload";
    private static final String TAG_ABOUT = "About US";
    private static final String TAG_TERMS = "Terms And Condition";
    private static final String TAG_CHANGE_PWD = "ChangePassword";
    public static String CURRENT_TAG = TAG_HOME;

    PrefManager prefManager;

    //region broadcast receiver
    public BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction() != null) {
                if (intent.getAction().equalsIgnoreCase(Utility.PUSH_BROADCAST_ACTION)) {
                    int notifyCount = prefManager.getNotificationCounter();

                    if (notifyCount == 0) {
                        textNotifyItemCount.setVisibility(View.GONE);
                    } else {
                        textNotifyItemCount.setVisibility(View.VISIBLE);
                        textNotifyItemCount.setText("" + String.valueOf(notifyCount));
                    }
                }
            }

        }
    };

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mHandler = new Handler();
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        prefManager = new PrefManager(this);
        loginEntity = prefManager.getUserData();
        userConstatntEntity = prefManager.getUserConstatnt();

        init_headers();
        if (userConstatntEntity == null) {

            if(prefManager.getUserConstatnt() == null) {
                new RegisterController(this).getUserConstatnt(HomeActivity.this);
            }
        }



        setUpNavigationView();


        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment("Home");
        }


    }

    private void init_headers() {

        View headerView = navigationView.getHeaderView(0);
        txtName = (TextView) headerView.findViewById(R.id.txtName);
        txtVehicle = (TextView) headerView.findViewById(R.id.txtVehicle);

        if (loginEntity != null) {
            txtName.setText("" + loginEntity.getName());

        } else {
            txtName.setText("");

        }

        if(userConstatntEntity!=null)
        {
            txtVehicle.setText("" +userConstatntEntity.getVehicleno() );
        }else{
            txtVehicle.setText("");
        }
    }

    private void setUpNavigationView() {
        navigationView.setItemIconTintList(null);
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_prod:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;

//
                    case R.id.nav_profile:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PROFILE;
                        break;

                    case R.id.nav_order:
//                        navItemIndex = 2;
//                        CURRENT_TAG = TAG_ORDER;
                        startActivity(new Intent(HomeActivity.this,DemActivity.class));
                        break;

//                    case R.id.nav_doc:
//                        navItemIndex = 3;
//                        CURRENT_TAG = TAG_DOC;
//                        break;

                    case R.id.nav_change_pwd:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_CHANGE_PWD;
                        break;

                    case R.id.nav_about_us:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_ABOUT;

                        break;

                    case R.id.nav_terms:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_TERMS;
                        break;

                    case R.id.nav_logout:

                        prefManager.clearUserCache();
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        break;

                    default:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;


                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment(CURRENT_TAG);
                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }


    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


    private Fragment getHomeFragment() {
        Fragment fragment = null;
        switch (navItemIndex) {
            case 0:
                // home
                fragment = new DashBoardFragment();
                getSupportActionBar().setTitle("Home");
                return fragment;

            case 1:
                fragment = new ProfileFragment();
                getSupportActionBar().setTitle("Profile");
                return fragment;

            case 2:
                fragment = new OrderDetailFragment();
                getSupportActionBar().setTitle("Request Detail");
                return fragment;

//            case 3:
//                fragment = new DocUploadFragment();
//                getSupportActionBar().setTitle("Document Upload");
//                return fragment;


            case 3:
                fragment = new ChangePasswordFragment();
                getSupportActionBar().setTitle("Change Password");
                return fragment;

            case 4:
                fragment = new AboutUsFragment();
                getSupportActionBar().setTitle("About Us");
                return fragment;

            case 5:
                fragment = new TermsConditionFragment();
                getSupportActionBar().setTitle("Terms And Condition");
                return fragment;

            default:
                fragment = new ProductHomeFragment();
                getSupportActionBar().setTitle("Elite Customer");
                return fragment;
        }
    }

    @SuppressLint("RestrictedApi")
    private void loadHomeFragment(String title) {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle(title);

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        // toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }


    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment(CURRENT_TAG);
                return;
            } else {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    //  dataBaseController.logout();
                    return;
                }

                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            }
        }

        // super.onBackPressed();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_push_notification);

        //  SearchView actionView = (SearchView) menuItem.getActionView();

        View actionView = MenuItemCompat.getActionView(menuItem);
        textNotifyItemCount = (TextView) actionView.findViewById(R.id.notify_badge);
        textNotifyItemCount.setVisibility(View.GONE);

        int PushCount = prefManager.getNotificationCounter();

        if (PushCount == 0) {
            textNotifyItemCount.setVisibility(View.GONE);
        } else {
            textNotifyItemCount.setVisibility(View.VISIBLE);
            textNotifyItemCount.setText("" + String.valueOf(PushCount));
        }

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onOptionsItemSelected(menuItem);


            }
        });


        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;
        switch (item.getItemId()) {

            case R.id.action_push_notification:
                intent = new Intent(HomeActivity.this, NotificationActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(HomeActivity.this).registerReceiver(mHandleMessageReceiver, new IntentFilter(Utility.PUSH_BROADCAST_ACTION));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mHandleMessageReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("RESULT", "Activity");
        if (requestCode == Constants.REQUEST_CODE) {
            if (data != null) {
                int Counter = prefManager.getNotificationCounter();
                textNotifyItemCount.setText("" + Counter);
                textNotifyItemCount.setVisibility(View.GONE);

            }

        }
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        if (response instanceof UserConsttantResponse) {

            if (response.getStatus_code() == 0) {

                userConstatntEntity = ((UserConsttantResponse) response).getData().get(0);
                if (userConstatntEntity != null) {
                  init_headers();
                }
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {

    }
}

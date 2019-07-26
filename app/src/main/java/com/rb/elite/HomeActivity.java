package com.rb.elite;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rb.elite.aboutUs.AboutUsFragment;
import com.rb.elite.chat.ChatActivity;
import com.rb.elite.core.APIResponse;
import com.rb.elite.core.IResponseSubcriber;
import com.rb.elite.core.controller.register.RegisterController;
import com.rb.elite.core.model.NotifyEntity;
import com.rb.elite.core.model.UserConstatntEntity;
import com.rb.elite.core.model.UserEntity;
import com.rb.elite.core.response.UserConsttantResponse;
import com.rb.elite.dashboard.DashBoardFragment;
import com.rb.elite.login.ChangePasswordFragment;
import com.rb.elite.login.LoginActivity;
import com.rb.elite.notification.NotificationActivity;
import com.rb.elite.orderDetail.OrderDetailFragment;
import com.rb.elite.profile.MyProfileFragment;
import com.rb.elite.splash.PrefManager;
import com.rb.elite.splash.SplashScreenActivity;
import com.rb.elite.term.TermsConditionFragment;
import com.rb.elite.utility.Constants;
import com.rb.elite.utility.Utility;

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
    WebView webView;
    public static boolean isActive = false;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mHandler = new Handler();
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        prefManager = new PrefManager(this);
        loginEntity = prefManager.getUserData();
        userConstatntEntity = prefManager.getUserConstatnt();


        getNotificationAction();

        init_headers();


        if (loginEntity != null) {
            if (userConstatntEntity == null) {

                if (prefManager.getUserConstatnt() == null) {
                    new RegisterController(this).getUserConstatnt(HomeActivity.this);

                }
            }


            setUpNavigationView();


            if (savedInstanceState == null) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment(CURRENT_TAG);
            }



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

        if (userConstatntEntity != null) {
            txtVehicle.setText("" + userConstatntEntity.getVehicleno());

            if (userConstatntEntity.getHomepopup() != "") {
                marketingPopUp(userConstatntEntity.getHomepopup());
            }

        } else {
            txtVehicle.setText("");
        }
    }

    private void getNotificationAction() {

        // region Activity Open Usnig Notification

        if (getIntent().getExtras() != null) {


            // region verifyUser : when user logout and when Apps in background : No action take place we have to store the data in preference and login to user
            if (loginEntity == null) {
                //username and password are present, do your stuff
                NotifyEntity notifyEntity = getIntent().getParcelableExtra(Utility.PUSH_NOTIFY);
                if (notifyEntity != null) {

                    prefManager.storePushData(notifyEntity);  // store on shared Preference
                    startActivity(new Intent(this, SplashScreenActivity.class));
                    finish();
                   // Toast.makeText(this, "First Event Called", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(this, SplashScreenActivity.class));
                    finish();
                }
            }
            //endregion

            // region For Notification come via Login for user credential
            else if (getIntent().getStringExtra(Utility.PUSH_LOGIN_PAGE) != null) {
                // verify  Page come via login and we have already putted data in shared Preference using above method
                if (getIntent().getStringExtra(Utility.PUSH_LOGIN_PAGE).equals("555")) {

                    NotifyEntity notifyEntity = prefManager.getPushNotifyData();
                    prefManager.clearNotification();
                    startActivity(new Intent(HomeActivity.this, ChatActivity.class).putExtra(Utility.PUSH_NOTIFY, notifyEntity));

                   // Toast.makeText(this, "Second Event Called", Toast.LENGTH_LONG).show();
                }

            }
            //endregion

            // region user already logged in and app in forground
            else if (getIntent().getParcelableExtra(Utility.PUSH_NOTIFY) != null) {

                NotifyEntity notifyEntity = getIntent().getParcelableExtra(Utility.PUSH_NOTIFY);
                startActivity(new Intent(HomeActivity.this, ChatActivity.class).putExtra(Utility.PUSH_NOTIFY, notifyEntity));
               // Toast.makeText(this, "Third Event Called", Toast.LENGTH_LONG).show();


            }
            //endregion

        }

        //endregion
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
                        startActivity(new Intent(HomeActivity.this, DemActivity.class));
                        break;



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


    public void setProfile(){
        navItemIndex =1;
        CURRENT_TAG =TAG_PROFILE;
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
        loadHomeFragment(CURRENT_TAG);
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
                fragment = new MyProfileFragment();
                getSupportActionBar().setTitle("My Profile");
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
                getSupportActionBar().setTitle("Terms and Conditions");
                return fragment;

            default:
                fragment = new DashBoardFragment();
                getSupportActionBar().setTitle("Home");
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

    //region WebView For Market PopUp

    public void marketingPopUp(String URL) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialog);


        ImageView ivClose;

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_market_popup, null);


        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button

        ivClose = (ImageView) dialogView.findViewById(R.id.ivClose);
        webView = (WebView) dialogView.findViewById(R.id.webView);

        if (isNetworkConnected()) {
            // url = " http://elite.rupeeboss.com/elite-receipt/363";
            settingWebview(URL);

        } else
            Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();


        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        alertDialog.setCancelable(false);

        alertDialog.show();
        //  alertDialog.getWindow().setLayout(900, 600);


    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void settingWebview(String url) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(false);
        settings.setJavaScriptEnabled(true);
        settings.setSupportMultipleWindows(false);

        settings.setLoadsImagesAutomatically(true);
        settings.setLightTouchEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);


      /*  MyWebViewClient webViewClient = new MyWebViewClient(this);
        webView.setWebViewClient(webViewClient);*/
        webView.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO show you progress image
                if (isActive)
                    showDialog();
                // new ProgressAsync().execute();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO hide your progress image
                cancelDialog();
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                /*if (url.endsWith(".pdf")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(url), "application/pdf");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        //user does not have a pdf viewer installed
                        String googleDocs = "https://docs.google.com/viewer?url=";
                        webView.loadUrl(googleDocs + url);
                    }
                }*/
                return false;
            }
        });
        webView.getSettings().setBuiltInZoomControls(true);

        Log.d("URL", url);

        if (url.endsWith(".pdf")) {

            webView.loadUrl("https://docs.google.com/viewer?url=" + url);
            //webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + url);
        } else {
            webView.loadUrl(url);
        }
        //webView.loadUrl(url);
    }

    //endregion



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

package com.rb.elite.dashboard;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rb.elite.BaseFragment;
import com.rb.elite.R;
import com.rb.elite.core.APIResponse;
import com.rb.elite.core.IResponseSubcriber;
import com.rb.elite.core.controller.register.RegisterController;
import com.rb.elite.core.model.UserConstatntEntity;
import com.rb.elite.core.model.UserEntity;
import com.rb.elite.core.response.UserConsttantResponse;
import com.rb.elite.emailUs.EmailUsActivity;
import com.rb.elite.feedback.FeedbackActivity;
import com.rb.elite.orderDetail.OrderActivity;
import com.rb.elite.rating.RateActivity;
import com.rb.elite.servicelist.Activity.ServiceActivity;
import com.rb.elite.splash.PrefManager;
import com.rb.elite.utility.Constants;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends BaseFragment implements View.OnClickListener, BaseFragment.CustomPopUpListener, IResponseSubcriber {


    ViewPager viewPager;
    List<Integer> banners;
    CardView cvService, cvRequest, cvfeedback,cvRating;
    CustomPagerAdapter mBannerAdapter;
    CirclePageIndicator circlePageIndicator;
    LinearLayout lyCall, lyEmail;
    TextView txtName, txtVehicle;
    UserEntity loginEntity;
    UserConstatntEntity userConstatntEntity;
    PrefManager prefManager;


    String[] permissionsRequired = new String[]{Manifest.permission.CALL_PHONE};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);

        prefManager = new PrefManager(getActivity());
        loginEntity = prefManager.getUserData();
        userConstatntEntity = prefManager.getUserConstatnt();

        initialize(view);
        setBanner();
        setListnner();
        registerCustomPopUp(this);

        if (userConstatntEntity == null) {
            if (prefManager.getUserConstatnt() == null) {
                new RegisterController(getActivity()).getUserConstatnt(DashBoardFragment.this);
            }
        } else {
            setUserInfo();
        }


        return view;
    }

    private void setUserInfo() {

        if (loginEntity != null) {
            txtName.setText("Hi " + loginEntity.getName());

        } else {
            txtName.setText("");

        }

        if (userConstatntEntity != null) {
            txtVehicle.setText("" + userConstatntEntity.getVehicleno());

        } else {
            txtVehicle.setText("");
        }
    }


    private void initialize(View view) {


        viewPager = (ViewPager) view.findViewById(R.id.pager);
        circlePageIndicator = (CirclePageIndicator) view.findViewById(R.id.titles);

        cvService = (CardView) view.findViewById(R.id.cvService);
        cvRequest = (CardView) view.findViewById(R.id.cvRequest);
        cvfeedback = (CardView) view.findViewById(R.id.cvfeedback);
        cvRating  = (CardView) view.findViewById(R.id.cvRating);

        lyCall = (LinearLayout) view.findViewById(R.id.lyCall);
        lyEmail = (LinearLayout) view.findViewById(R.id.lyEmail);
        txtName = view.findViewById(R.id.txtName);
        txtVehicle = view.findViewById(R.id.txtVehicle);

    }

    private void setBanner() {
        banners = new ArrayList<>();

        banners.add(R.drawable.banner1);
        banners.add(R.drawable.banner2);
        banners.add(R.drawable.banner3);
        mBannerAdapter = new CustomPagerAdapter(getContext(), banners);


        if (viewPager != null && circlePageIndicator != null) {
            viewPager.setAdapter(mBannerAdapter);
            circlePageIndicator.setViewPager(viewPager);

            Timer timer = new Timer();
            timer.schedule(new RemindTask(banners.size(), viewPager), 0, 1500);

//            circlePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//                @Override
//                public void onPageSelected(int position) {
//
//                    getCustomToast("Position" +position);
//                }
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int state) {
//
//                }
//            });

        }
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        if (response instanceof UserConsttantResponse) {

            if (response.getStatus_code() == 0) {

                userConstatntEntity = ((UserConsttantResponse) response).getData().get(0);
                if (userConstatntEntity != null) {
                    setUserInfo();
                }
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
    }


    class RemindTask extends TimerTask {
        private int numberOfPages;
        private ViewPager mViewPager;
        private int page = 0;

        public RemindTask(int numberOfPages, ViewPager mViewPager) {
            this.numberOfPages = numberOfPages;
            this.mViewPager = mViewPager;
        }

        @Override
        public void run() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        if (page > numberOfPages) { // In my case the number of pages are 5
                            mViewPager.setCurrentItem(0);
                            page = 0;
                        } else {
                            mViewPager.setCurrentItem(page++);
                        }
                    }
                });
            }

        }
    }

    private void setListnner() {

        cvService.setOnClickListener(this);
        cvRequest.setOnClickListener(this);
        cvfeedback.setOnClickListener(this);
        cvRating.setOnClickListener(this);

        lyCall.setOnClickListener(this);
        lyEmail.setOnClickListener(this);
    }

    public void ConfirmAlert(String Title, String strBody, final String strMobile) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);


        Button btnSubmit;
        TextView txtTile, txtBody, txtMob;
        ImageView ivCross;

        LayoutInflater inflater = this.getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.layout_calling_popup, null);

        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        // set the custom dialog components - text, image and button
        txtTile = (TextView) dialogView.findViewById(R.id.txtTile);
        txtBody = (TextView) dialogView.findViewById(R.id.txtMessage);
        txtMob = (TextView) dialogView.findViewById(R.id.txtOther);
        ivCross = (ImageView) dialogView.findViewById(R.id.ivCross);

        btnSubmit = (Button) dialogView.findViewById(R.id.btnSubmit);

        txtTile.setText(Title);
        txtBody.setText(strBody);
        txtMob.setText(strMobile);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

                Intent intentCalling = new Intent(Intent.ACTION_CALL);
                intentCalling.setData(Uri.parse("tel:" + strMobile));
                startActivity(intentCalling);

            }
        });

        ivCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();

    }




    @Override
    public void onClick(View view) {


        switch (view.getId()) {


            case R.id.cvService:

                startActivity(new Intent(getActivity(), ServiceActivity.class));
                break;

            case R.id.cvRequest:

                startActivity(new Intent(getActivity(), OrderActivity.class));
                break;

            case R.id.cvfeedback:

                startActivity(new Intent(getActivity(), FeedbackActivity.class));
                break;

            case R.id.cvRating:

                startActivity(new Intent(getActivity(), RateActivity.class));
                break;


            case R.id.lyCall:

                if (ActivityCompat.checkSelfPermission(this.getActivity(), permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), permissionsRequired[0])) {
                        //Show Information about why you need the permission
                        ActivityCompat.requestPermissions(this.getActivity(), permissionsRequired, Constants.PERMISSION_CALLBACK_CONSTANT);

                    } else {

                        // openPopUp(lyCall, "Need  Permission", "This app needs all permissions.", "GRANT", true);
                        openPopUp(lyCall, "Need Call Permission", "Required call permissions.", "GRANT", "DENNY", false, true);

                    }
                } else {

                    ConfirmAlert("Calling", getResources().getString(R.string.supp_Calling) + " ", userConstatntEntity.getContactno());
                }

                break;

            case R.id.lyEmail:

                startActivity(new Intent(getActivity(), EmailUsActivity.class));
                break;

        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {


            case Constants.PERMISSION_CALLBACK_CONSTANT:
                if (grantResults.length > 0) {

                    //boolean writeExternal = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean call_phone = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (call_phone) {

                        ConfirmAlert("Calling", getResources().getString(R.string.supp_Calling) + " ", userConstatntEntity.getContactno());


                    }

                }

                break;


        }
    }

    //endregion

    @Override
    public void onPositiveButtonClick(Dialog dialog, View view) {
        dialog.cancel();
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, Constants.REQUEST_PERMISSION_SETTING);

    }

    @Override
    public void onCancelButtonClick(Dialog dialog, View view) {

        dialog.cancel();
    }
}

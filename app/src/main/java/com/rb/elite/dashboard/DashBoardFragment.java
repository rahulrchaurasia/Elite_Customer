package com.rb.elite.dashboard;


import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.rb.elite.BaseFragment;
import com.rb.elite.BuildConfig;
import com.rb.elite.HomeActivity;
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
    CardView cvService, cvRequest, cvfeedback, cvRating;
    ImageView imgService;
    CustomPagerAdapter mBannerAdapter;
    CirclePageIndicator circlePageIndicator;
    LinearLayout lyCall, lyEmail;
    TextView txtName, txtVehicle;
    UserEntity loginEntity;
    UserConstatntEntity userConstatntEntity;
    PrefManager prefManager;

    ShowcaseView showcaseView, showcaseViewSec;

    final int SHOWCASEVIEW_ID = 55;
    final int SHOWCASEVIEW_ID1 = 56;


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

        if (userConstatntEntity != null) {
            setUserInfo();
        }

        if (prefManager.isFirstTimeShowCaseView()) {
            getShowCaseView();
        }

      //  showServiceCaseView();

        return view;
    }





    private void getShowCaseView() {
        showcaseView = new ShowcaseView.Builder(getActivity())
                //.withMaterialShowcase()
                // .setTarget(new ViewTarget(view.findViewById(R.id.imgService)))
                .withMaterialShowcase()
                .setTarget(Target.NONE)
                .setContentTitle("Services")
                .setContentText("Place all service requests for RTO as well as Misc. Services")
                .singleShot(SHOWCASEVIEW_ID)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showcaseView.hide();
                        getShowCaseViewSecond();
                    }
                })
                .setStyle(R.style.ShowcaseViewStyle).build();

        showcaseView.show();

    }

    private void getShowCaseViewSecond() {
        showcaseViewSec = new ShowcaseView.Builder(getActivity())
                //.withMaterialShowcase()
                // .setTarget(new ViewTarget(view.findViewById(R.id.imgService)))
                .withMaterialShowcase()
                .setTarget(Target.NONE)
                .setContentTitle("Request")
                .setContentText("See status updates for all services availed, in real time.")
                .singleShot(SHOWCASEVIEW_ID1)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showcaseViewSec.hide();
                        prefManager.setFirstTimeShowCaseView(false);
                        openPopUp(imgService, "My Profile", getResources().getString(R.string.profile_msg), "OK", "Skip", true, false);
                    }
                })
                .setStyle(R.style.ShowcaseViewStyleSec).build();

        showcaseViewSec.show();

    }


    private ShowcaseView getShowcaseView(long singleID) {

        ShowcaseView showcaseView = new ShowcaseView.Builder(getActivity())
                .withMaterialShowcase()
                .setTarget(Target.NONE)
                .singleShot(singleID)
                .build();

        return showcaseView;


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
        cvRating = (CardView) view.findViewById(R.id.cvRating);
        imgService = (ImageView) view.findViewById(R.id.imgService);

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
        mBannerAdapter = new CustomPagerAdapter(getContext(), DashBoardFragment.this, banners);


        if (viewPager != null && circlePageIndicator != null) {
            viewPager.setAdapter(mBannerAdapter);
            circlePageIndicator.setViewPager(viewPager);

            Timer timer = new Timer();
            timer.schedule(new RemindTask(banners.size(), viewPager), 0, 1500);


        }
    }

    public void redirectToDashBoard(int pos) {
        // Toast.makeText(getActivity(), "Selected Position" + pos, Toast.LENGTH_SHORT).show();

        switch (pos) {


            case 1:

                startActivity(new Intent(getActivity(), ServiceActivity.class).putExtra(Constants.SERVICE_POSTION, 0));
                break;

            case 2:

                startActivity(new Intent(getActivity(), ServiceActivity.class).putExtra(Constants.SERVICE_POSTION, 1));
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (loginEntity != null) {
            new RegisterController(getActivity()).getUserConstatnt(DashBoardFragment.this);
        }
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {

        if (response instanceof UserConsttantResponse) {

            if (response.getStatus_code() == 0) {

                userConstatntEntity = ((UserConsttantResponse) response).getData().get(0);
                if (userConstatntEntity != null) {
                    int serverVersionCode = Integer.parseInt((userConstatntEntity.getVersionCode()));



                    if (BuildConfig.VERSION_CODE < serverVersionCode) {

                        int forceUpdate = Integer.parseInt(userConstatntEntity.getIsForceUpdate());
                        if (forceUpdate == 1) {
                            // forced update app
                            showPlaystoreDialog("New version available on play store!!!! Please update.");
                        }
                    }else{
                        setUserInfo();
                    }


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

                Intent intentCalling = new Intent(Intent.ACTION_DIAL);
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
            case R.id.imgService:
                Toast.makeText(getActivity(), "ShowcaseService", Toast.LENGTH_SHORT).show();
                break;
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

                if (userConstatntEntity != null) {
                    ConfirmAlert("Calling", getResources().getString(R.string.supp_Calling) + " ", userConstatntEntity.getContactno());

                }
                break;

            case R.id.lyEmail:

                startActivity(new Intent(getActivity(), EmailUsActivity.class));
                break;

        }


//        switch (showcase_counter) {
//            case 0:
//
//                showcaseView = new ShowcaseView.Builder(getActivity())
//                        .setTarget(Target.NONE)
//                        .setContentTitle("Request")
//                        .setContentText("You can track your Request and Upload the Related Document here")
//                        .setStyle(R.style.ShowcaseViewStyle)
//                       // .singleShot(SHOWCASEVIEW_ID1)
//                        .hideOnTouchOutside();
//                showcaseView.build();
//                showcase_counter++;
//                break;
//
//            case 1:
//                showcaseView.setTarget(Target.NONE);
//                showcaseView.setOnClickListener(this);
//                showcaseView.setContentTitle("Title");
//                showcaseView.setContentText("some text");
//              //  showcaseView.singleShot(SHOWCASEVIEW_ID2);
//                showcaseView.hideOnTouchOutside();
//                showcase_counter++;
//                break;
//
//
//            case 2:
//                showcaseView.hideOnTouchOutside();
//                break;
//        }


    }


    //endregion

    @Override
    public void onPositiveButtonClick(Dialog dialog, View view) {

        if (view.getId() == R.id.imgService) {
            dialog.cancel();

            ((HomeActivity) getActivity()).setProfile();

            /*MyProfileFragment fragment = new MyProfileFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment);
            fragmentTransaction.commitAllowingStateLoss();*/
        }


    }

    @Override
    public void onCancelButtonClick(Dialog dialog, View view) {
        if (view.getId() == R.id.imgService) {
            dialog.cancel();
        }
    }

    //region PlayStore

    public void showPlaystoreDialog(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);

        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();

        // dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        TextView txtMsg = (TextView) dialogView.findViewById(R.id.txtMsg);
        txtMsg.setText(msg);

        LinearLayout lyUpdate = (LinearLayout) dialogView.findViewById(R.id.lyUpdate);
        //TextView btnOk = (TextView) dialog.findViewById(R.id.tvOk);
        // if button is clicked, close the custom dialog
        lyUpdate.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            alertDialog.dismiss();
                                            getActivity().finish();
                                            openAppMarketPlace();
                                        }
                                    }
        );
        alertDialog.setCancelable(false);
        alertDialog.show();


    }

    private void openAppMarketPlace() {

        final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }

    }

    //endregion


}

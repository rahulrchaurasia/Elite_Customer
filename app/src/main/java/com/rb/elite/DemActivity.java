package com.rb.elite;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.rb.elite.core.model.CorrectiontEnity;


import java.util.ArrayList;
import java.util.List;

public class DemActivity extends BaseActivity implements View.OnClickListener {

    BottomSheetBehavior sheetBehavior;
    Button btnBottomSheet ,btnBottomCollapse,btnBottomExpand;
    LinearLayout layoutBottomSheet;

    List<CorrectiontEnity> correctiontEnityList;

    RecyclerView rvProductDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dem);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnBottomExpand = findViewById(R.id.btn_bottom_expand);

        btnBottomCollapse = findViewById(R.id.btn_bottom_collapse);
        btnBottomSheet = findViewById(R.id.btn_bottom);

        layoutBottomSheet = findViewById(R.id.bottom_sheet);

        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);


        rvProductDoc = (RecyclerView) findViewById(R.id.rvProductDoc);
        rvProductDoc.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvProductDoc.setLayoutManager(layoutManager);

        correctiontEnityList = new ArrayList<CorrectiontEnity>();
        getCorrectionField();
        btnBottomSheet.setOnClickListener(this);
        btnBottomCollapse.setOnClickListener(this);
        btnBottomExpand.setOnClickListener(this);


        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        btnBottomSheet.setText("Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        btnBottomSheet.setText("Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
}

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btn_bottom_expand)
        {
             sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        }
        else if(view.getId() == R.id.btn_bottom_collapse){
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        else if(view.getId() == R.id.btn_bottom){
           toggleBottomSheet();

        }
    }

    public void toggleBottomSheet() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            btnBottomSheet.setText("Close sheet");
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            btnBottomSheet.setText("Expand sheet");
        }
    }

    private void getCorrectionField()
    {
        correctiontEnityList.clear();
        correctiontEnityList.add( new CorrectiontEnity("0","Name",false));
        correctiontEnityList.add( new CorrectiontEnity("1","DOB",false));
        correctiontEnityList.add( new CorrectiontEnity("2","Driving Licence",false));
        correctiontEnityList.add( new CorrectiontEnity("2","Address",false));

    }

}

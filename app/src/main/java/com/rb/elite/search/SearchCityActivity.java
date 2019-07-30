package com.rb.elite.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rb.elite.BaseActivity;
import com.rb.elite.R;
import com.rb.elite.core.APIResponse;
import com.rb.elite.core.IResponseSubcriber;
import com.rb.elite.core.controller.register.RegisterController;
import com.rb.elite.core.model.CityMainEntity;
import com.rb.elite.core.model.UserEntity;
import com.rb.elite.core.response.CityMainResponse;
import com.rb.elite.database.DataBaseController;
import com.rb.elite.splash.PrefManager;
import com.rb.elite.utility.Constants;

import java.util.ArrayList;
import java.util.List;

public class SearchCityActivity extends BaseActivity implements View.OnClickListener, IResponseSubcriber {

    DataBaseController dataBaseController;
    PrefManager prefManager;
    UserEntity loginEntity;
    RecyclerView rvCity;
    SearchCityAdapter mAdapter;
    List<CityMainEntity> CityList;
    LinearLayout lyOtherCity;
    private SearchView.OnQueryTextListener queryTextListener;     // for Option Menu

    // LeadAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        prefManager = new PrefManager(this);
        CityList = new ArrayList<>();

        initialize();

        CityList = prefManager.getCityData();

        mAdapter = new SearchCityAdapter(SearchCityActivity.this, CityList);
        rvCity.setAdapter(mAdapter);


        if (CityList.size() == 0) {
            showDialog();
            new RegisterController(SearchCityActivity.this).getCityMainMaster(SearchCityActivity.this);
        }


    }

    private void initialize() {
        dataBaseController = new DataBaseController(SearchCityActivity.this);
        prefManager = new PrefManager(this);
        loginEntity = prefManager.getUserData();

        lyOtherCity = (LinearLayout) findViewById(R.id.lyOtherCity);
        rvCity = (RecyclerView) findViewById(R.id.rvCity);
        rvCity.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchCityActivity.this);
        rvCity.setLayoutManager(layoutManager);
        rvCity.setItemAnimator(new DefaultItemAnimator());

        lyOtherCity.setOnClickListener(this);

        //  recyclerLead.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        }

        EditText editText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);

        editText.setFilters(new InputFilter[]{
                new InputFilter.AllCaps(),
                new InputFilter.LengthFilter(20),
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (source.equals(" ")) { // for backspace
                            return source;
                        }
                        if (source.toString().matches("[a-zA-Z]+")) {
                            return source;
                        }
                        return "";
                    }
                }
        });

        if (searchView != null && CityList != null && CityList.size() > 0) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {


                    if (newText.length() > 0) {
                        mAdapter.CityList = CityList;
                        mAdapter.getFilter().filter(newText);
                    } else {
                        mAdapter.findAll(CityList);
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);


        }
        return super.onCreateOptionsMenu(menu);

    }


    public void getCity(CityMainEntity allCityEntity) {
        Intent intent = new Intent();
        intent.putExtra(Constants.SEARCH_CITY_DATA, allCityEntity);
        setResult(Constants.SEARCH_CITY_CODE, intent);
        finish();      //finishing activity


    }

    @Override
    public void onClick(View view) {

//        if(view.getId() == R.id.lyOtherCity)
//        {
//            Intent intent=new Intent();
//            intent.putExtra(Constants.SEARCH_CITY_DATA,"OTHER CITY");
//            intent.putExtra(Constants.SEARCH_CITY_ID,"2653");
//            setResult(Constants.SEARCH_CITY_CODE,intent);
//            finish();
//        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }


    @Override
    public void OnSuccess(APIResponse response, String message) {
        cancelDialog();
        if (response instanceof CityMainResponse) {
            if (response.getStatus_code() == 0) {

                if (((CityMainResponse) response).getData() != null) {

                    CityList = ((CityMainResponse) response).getData();
                    mAdapter.findAll(CityList);
                }
            }
        }
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}

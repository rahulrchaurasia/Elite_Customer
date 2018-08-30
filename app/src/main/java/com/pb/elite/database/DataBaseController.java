package com.pb.elite.database;

import android.content.Context;

import com.pb.elite.core.model.AllCityEntity;
import com.pb.elite.core.model.CategoryEntity;
import com.pb.elite.core.model.CityEntity;
import com.pb.elite.core.model.OrderStatusEntity;
import com.pb.elite.core.model.RTOEntity;
import com.pb.elite.core.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;


/**
 * Created by Rajeev Ranjan on 02/02/2018.
 */

public class DataBaseController {
    Context mContext;
    Realm realm;

    public DataBaseController(Context mContext) {
        this.mContext = mContext;
        realm = Realm.getDefaultInstance();
    }


//    public ArrayList<String> getCategoryList() {
//        List<RTOEntity> categoryEntityList = realm.where(RTOEntity.class).findAll();
//        ArrayList categoryList = new ArrayList();
//        for (int i = 0; i < categoryEntityList.size(); i++) {
//            // if(categoryEntityList.get(i).getCategory() != null) {
//            categoryList.add(""+ String.valueOf(i+1) +" "+ categoryEntityList.get(i).getCategory());
//            //  }
//        }
//        return categoryList;
//    }
//    public ArrayList<String> getCategoryList(String series_no) {
//        List<RTOEntity> categoryEntityList = realm.where(RTOEntity.class)
//                .contains("series_no", "" + series_no.trim()).findAll();
//        ArrayList categoryList = new ArrayList();
//        for (int i = 0; i < categoryEntityList.size(); i++) {
//           // if(categoryEntityList.get(i).getCategory() != null) {
//                categoryList.add(""+ categoryEntityList.get(i).getCategory());
//          //  }
//        }
//        return categoryList;
//    }
//
//    public int getCategoryId(String categoryName) {
//        RTOEntity entity = realm.where(RTOEntity.class)
//                .equalTo("Category", categoryName).findFirst();
//        if (entity != null)
//            return entity.getCatg_id();
//        else
//            return 0;
//    }

//    public ArrayList<String> getSubCategoryList(int categoryId) {
//        List<SubCategoryEntity> categoryEntityList = realm.where(SubCategoryEntity.class).equalTo("cat_id", categoryId).findAll();
//        ArrayList categoryList = new ArrayList();
//        for (int i = 0; i < categoryEntityList.size(); i++) {
//            //listCity.add(list_Make.get(i).getRTO_CodeDiscription());
//            //listCity.add(list_Make.get(i).getRTO_City());
//            categoryList.add(categoryEntityList.get(i).getSubcategory());
//        }
//        return categoryList;
//    }
//
//    public int getSubCategoryId(String subCategoryName, int categoryId) {
//
//        /// Added temporary
//        if (categoryId == 1) {
//            return 1;
//        }
//        ///
//        SubCategoryEntity entity = realm.where(SubCategoryEntity.class)
//                .equalTo("subcategory", subCategoryName)
//                .equalTo("cat_id", categoryId).findFirst();
//        if (entity != null)
//            return entity.getId();
//        else
//            return 0;
//    }


//    public ArrayList<String> getProductWithTopList(int categoryId, int location) {
//        List<RTOEntity> categoryEntityList = realm.where(RTOEntity.class)
//                .equalTo("catg_id", categoryId)
//                .equalTo("series_no", "" + location).findAll();
//        ArrayList categoryList = new ArrayList();
//        ArrayList categoryTopList = new ArrayList();
//        for (int i = 0; i < categoryEntityList.size(); i++) {
//
//            if ((categoryEntityList.get(i).getProd_id() == 73) || (categoryEntityList.get(i).getProd_id() == 78) || (categoryEntityList.get(i).getProd_id() == 80)
//
//                    || (categoryEntityList.get(i).getProd_id() == 81) || (categoryEntityList.get(i).getProd_id() == 82) || (categoryEntityList.get(i).getProd_id() == 89)) {
//                categoryTopList.add(categoryEntityList.get(i).getProduct());
//            } else {
//                categoryList.add(categoryEntityList.get(i).getProduct());
//            }
//
//        }
//        categoryTopList.addAll(categoryList);
//        return categoryTopList;
//    }

//    public int getProductId(String productName) {
//        RTOEntity entity = realm.where(RTOEntity.class)
//                .equalTo("product", productName).findFirst();
//        if (entity != null)
//            return entity.getProd_id();
//        else
//            return 0;
//    }

//    public RTOEntity getProductData(int productID, int cityID, int rtoID) {
//
//        RTOEntity entity = realm.where(RTOEntity.class)
//                .equalTo("prod_id", productID)
//                .equalTo("city_id", cityID)
//                .equalTo("rto_id", rtoID).findFirst();
//
//        if (entity != null)
//            return entity;
//        else
//            return null;
//    }

//    public ArrayList<String> getProductList(int catID, int cityID, int rtoID) {
//        List<RTOEntity> rtoEntityList = realm.where(RTOEntity.class)
//                .equalTo("catg_id", catID)
//                .equalTo("city_id", cityID)
//                .equalTo("rto_id", rtoID).findAll();
//        ArrayList List = new ArrayList();
//        for (int i = 0; i < rtoEntityList.size(); i++) {
//            if (rtoEntityList.get(i).getProduct() != null) {
//                List.add("" + rtoEntityList.get(i).getProduct());
//            }
//        }
//        return List;
//    }

//    public ArrayList<String> getProductList(int catID) {
//        List<RTOEntity> rtoEntityList = realm.where(RTOEntity.class)
//                .equalTo("catg_id", catID)
//                .findAll();
//        ArrayList List = new ArrayList();
//        for (int i = 0; i < rtoEntityList.size(); i++) {
//            if (rtoEntityList.get(i).getProduct() != null) {
//                List.add("" + rtoEntityList.get(i).getProduct());
//            }
//        }
//        return List;
//    }

//    public int getRTO_ID(String series_no) {
//        RTOEntity entity = realm.where(RTOEntity.class)
//                .equalTo("series_no", series_no).findFirst();
//        if (entity != null)
//            return entity.getRto_id();
//        else
//            return 0;
//    }

//    public ArrayList<String> getRTOLocationList(int CityID) {
//        List<RTOEntity> rtoEntityList = realm.where(RTOEntity.class)
//                .equalTo("city_id", CityID).distinctValues("rto_id").findAll();
//        ArrayList List = new ArrayList();
//        for (int i = 0; i < rtoEntityList.size(); i++) {
//            if (rtoEntityList.get(i).getSeries_no() != null) {
//                List.add("" + rtoEntityList.get(i).getSeries_no());
//            }
//        }
//        return List;
//    }


//    public int getCity_ID(String cityName) {
//        RTOEntity entity = realm.where(RTOEntity.class)
//                .equalTo("cityname", cityName).findFirst();
//        if (entity != null)
//            return entity.getCity_id();
//        else
//            return 0;
//    }

    public void removeCity_RTO() {
        realm.beginTransaction();
        realm.delete(CityEntity.class);
        realm.delete(RTOEntity.class);
        realm.commitTransaction();
    }

//    public ArrayList<String> getCityList() {
//        List<CityEntity> cityEntityList = realm.where(CityEntity.class).findAll();
//
//
//        ArrayList<String> List = new ArrayList<String>();
//        for (int i = 0; i < cityEntityList.size(); i++) {
//            if (cityEntityList.get(i).getCityname() != null) {
//                List.add("" + cityEntityList.get(i).getCityname());
//            }
//        }
//        return List;
//    }


    public ArrayList<String> getAllCityList() {
        List<AllCityEntity> cityEntityList = realm.where(AllCityEntity.class).findAll();


        ArrayList<String> List = new ArrayList<String>();
        for (int i = 0; i < cityEntityList.size(); i++) {
            if (cityEntityList.get(i).getCityname() != null) {
                List.add("" + cityEntityList.get(i).getCityname());
            }
        }
        return List;
    }

    public List<AllCityEntity> getCityMainList()
    {
        List<AllCityEntity> cityEntityList = realm.where(AllCityEntity.class).findAll().sort("cityname",Sort.ASCENDING);;
        if(cityEntityList == null){
            cityEntityList = new ArrayList<AllCityEntity>();
        }
       return cityEntityList;
    }

    public List<AllCityEntity> getCityQueryList(String SearchText)
    {
        List<AllCityEntity> cityEntityList = realm.where(AllCityEntity.class).contains("cityname",SearchText, Case.INSENSITIVE)
                                            .findAll().sort("cityname",Sort.ASCENDING);

        if(cityEntityList == null){
            cityEntityList = new ArrayList<AllCityEntity>();
        }
        return cityEntityList;
    }

    public int getMainCity_ID(String cityName) {
        AllCityEntity entity = realm.where(AllCityEntity.class)
                .equalTo("cityname", cityName).findFirst();
        if (entity != null)
            return entity.getCity_id();
        else
            return 0;
    }

    public int getNONRTOCity_ID(String cityName) {
        AllCityEntity entity = realm.where(AllCityEntity.class)
                .equalTo("cityname", cityName).findFirst();
        if (entity != null)
            return entity.getCity_id();
        else
            return 0;
    }

    // region Login Detail

    public void logout() {
        realm.beginTransaction();
//        realm.delete(UserEntity.class);
        realm.deleteAll();
        realm.commitTransaction();
    }

    public UserEntity getUserData() {
        UserEntity entity = realm.where(UserEntity.class).findFirst();
        if (entity != null)
            return entity;
        else
            return null;
    }


    //endregion


}

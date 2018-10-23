package com.pb.elite.core.controller.register;

import com.pb.elite.core.IResponseSubcriber;
import com.pb.elite.core.requestmodel.AddUserRequestEntity;
import com.pb.elite.core.requestmodel.UpdateUserRequestEntity;

/**
 * Created by Rajeev Ranjan on 02/02/2018.
 */

public interface IRegister {

    void getDbVersion( IResponseSubcriber iResponseSubcriber);

    void getOtp(String email, String mobile, String ip, IResponseSubcriber iResponseSubcriber);

    void addUser(AddUserRequestEntity addUserRequestEntity, IResponseSubcriber iResponseSubcriber);

    void getCityState(String pincode, IResponseSubcriber iResponseSubcriber);

    void updateUser(UpdateUserRequestEntity updateUserRequestEntity, IResponseSubcriber iResponseSubcriber);

    void getLogin(String mobile, String password, IResponseSubcriber iResponseSubcriber);

    void changePassword(String mobile, String curr_password, String new_password,  IResponseSubcriber iResponseSubcriber);

    void forgotPassword(String mobile, IResponseSubcriber iResponseSubcriber);

    void getPolicyData(String PolicyNumber, IResponseSubcriber iResponseSubcriber);
}

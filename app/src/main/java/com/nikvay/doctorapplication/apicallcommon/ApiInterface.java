package com.nikvay.doctorapplication.apicallcommon;


import com.nikvay.doctorapplication.model.SuccessModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST(EndApi.LOGIN)
    @FormUrlEncoded
    Call<SuccessModel> loginCall(@Field("email") String email,
                                 @Field("password") String password,
                                 @Field("device_token") String device_token);


    @POST(EndApi.SERVICE_LIST)
    @FormUrlEncoded
    Call<SuccessModel> serviceList(@Field("doctor_id") String doctor_id);


    @POST(EndApi.ADD_NEW_PATIENT)
    @FormUrlEncoded
    Call<SuccessModel> addNewPatient(@Field("doctor_id") String doctor_id,
                                 @Field("name") String name,
                                 @Field("email") String email,
                                 @Field("user_id") String user_id,
                                 @Field("address") String address,
                                 @Field("phone_no") String phone);


    @POST(EndApi.UPDATE_PATIENT)
    @FormUrlEncoded
    Call<SuccessModel> updatePatient(@Field("doctor_id") String doctor_id,
                                     @Field("patient_id") String patient_id,
                                     @Field("name") String name,
                                     @Field("email") String email,
                                     @Field("user_id") String user_id,
                                     @Field("address") String address,
                                     @Field("phone_no") String phone);



    @POST(EndApi.PATIENT_LIST)
    @FormUrlEncoded
    Call<SuccessModel> patientList(@Field("doctor_id") String doctor_id);

}

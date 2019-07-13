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



    @POST(EndApi.UPDATE_PROFILE)
    @FormUrlEncoded
    Call<SuccessModel> updateProfile(@Field("doctor_id") String doctor_id,
                                 @Field("user_id") String user_id,
                                 @Field("name") String name,
                                 @Field("email") String email,
                                 @Field("profile") String profile,
                                 @Field("address") String address);



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
                                     @Field("phone_no") String phone,
                                     @Field("age") String age,
                                     @Field("gender") String gender);


    @POST(EndApi.UPDATE_PATIENT)
    @FormUrlEncoded
    Call<SuccessModel> updatePatient(@Field("doctor_id") String doctor_id,
                                     @Field("patient_id") String patient_id,
                                     @Field("name") String name,
                                     @Field("email") String email,
                                     @Field("user_id") String user_id,
                                     @Field("address") String address,
                                     @Field("phone_no") String phone,
                                     @Field("age") String age,
                                     @Field("gender") String gender);

    @POST(EndApi.PATIENT_LIST)
    @FormUrlEncoded
    Call<SuccessModel> patientList(@Field("doctor_id") String doctor_id);


    @POST(EndApi.ADD_NEW_SERVICE)
    @FormUrlEncoded
    Call<SuccessModel> addNewService(@Field("doctor_id") String doctor_id,
                                     @Field("user_id") String user_id,
                                     @Field("name") String name,
                                     @Field("Duration") String Duration,
                                     @Field("BufferTime") String BufferTime,
                                     @Field("Cost") String Cost,
                                     @Field("Description") String Description);


    @POST(EndApi.UPDATE_SERVICE_LIST)
    @FormUrlEncoded
    Call<SuccessModel> updateServiceList(@Field("doctor_id") String doctor_id,
                                         @Field("user_id") String user_id,
                                         @Field("service_id") String service_id,
                                         @Field("name") String name,
                                         @Field("Duration") String Duration,
                                         @Field("BufferTime") String BufferTime,
                                         @Field("Cost") String Cost,
                                         @Field("Description") String Description);


    @POST(EndApi.APPOINTMENT_TIME_SLOT)
    @FormUrlEncoded
    Call<SuccessModel> appointmentTimeSlot(@Field("date") String date,
                                           @Field("doctor_id") String doctor_id,
                                           @Field("user_id") String user_id);


    @POST(EndApi.ADD_APPOINTMENT)
    @FormUrlEncoded
    Call<SuccessModel> addAppointment(@Field("doctor_id") String doctor_id,
                                      @Field("user_id") String user_id,
                                      @Field("service_id") String service_id,
                                      @Field("patient_id") String patient_id,
                                      @Field("date") String date,
                                      @Field("time") String time,
                                      @Field("comment") String comment,
                                      @Field("label") String label,
                                      @Field("notification_type") String notification_type);

    @POST(EndApi.LIST_APPOINTMENT)
    @FormUrlEncoded
    Call<SuccessModel> appointmentList(@Field("doctor_id") String doctor_id,
                                       @Field("label") String label,
                                       @Field("user_id") String user_id,
                                       @Field("date") String date);

    @POST(EndApi.APPOINTMENT_LIST_COUNT)
    @FormUrlEncoded
    Call<SuccessModel> appointmentListCount(@Field("doctor_id") String doctor_id,
                                            @Field("user_id") String user_id,
                                            @Field("date") String date);

    @POST(EndApi.APPOINTMENT_EDIT)
    @FormUrlEncoded
    Call<SuccessModel> editAppointment(@Field("doctor_id") String doctor_id,
                                       @Field("patient_id") String patient_id,
                                       @Field("appointment_id") String appointment_id,
                                       @Field("date") String date,
                                       @Field("time") String time,
                                       @Field("comment") String comment,
                                       @Field("label") String label,
                                       @Field("isReschedule") String isReschedule);

    @POST(EndApi.ADD_CLASS)
    @FormUrlEncoded
    Call<SuccessModel> addClass(@Field("doctor_id") String doctor_id,
                                @Field("user_id") String user_id,
                                @Field("name") String textClassName,
                                @Field("cost") String textClassCost,
                                @Field("duration") String textClassDuration,
                                @Field("description") String textClassDescription,
                                @Field("seats") String textClassSeats,
                                @Field("date") String textClassDate);


    @POST(EndApi.UPDATE_CLASS)
    @FormUrlEncoded
    Call<SuccessModel> updateClass(@Field("class_id") String class_id,
                                   @Field("doctor_id") String doctor_id,
                                   @Field("user_id") String user_id,
                                   @Field("name") String textClassName,
                                   @Field("cost") String textClassCost,
                                   @Field("duration") String textClassDuration,
                                   @Field("description") String textClassDescription,
                                   @Field("seats") String textClassSeats,
                                   @Field("date") String textClassDate);

    @POST(EndApi.LIST_CLASS)
    @FormUrlEncoded
    Call<SuccessModel> listClass(@Field("doctor_id") String doctor_id,
                                 @Field("user_id") String user_id);


    @POST(EndApi.NOTIFICATION_LIST)
    @FormUrlEncoded
    Call<SuccessModel> notificationList(@Field("doctor_id") String doctor_id,
                                        @Field("user_id") String user_id);

    @POST(EndApi.NOTIFICATION_CLEAR)
    @FormUrlEncoded
    Call<SuccessModel> notificationClear(@Field("doctor_id") String doctor_id,
                                         @Field("user_id") String user_id);


    @POST(EndApi.CHANGE_PASSWORD)
    @FormUrlEncoded
    Call<SuccessModel> changePassword(@Field("doctor_id") String doctor_id,
                                      @Field("user_id") String user_id,
                                      @Field("email") String email,
                                      @Field("old_password") String old_password,
                                      @Field("new_password") String new_password);


    @POST(EndApi.ADDPAYMENT)
    @FormUrlEncoded
    Call<SuccessModel> paymentList(@Field("doctor_id") String doctor_id,
                                   @Field("user_id") String user_id,
                                   @Field("service_id") String service_id,
                                   @Field("patient_id") String patient_id,
                                   @Field("hospital_charges") String hospital_charges,
                                   @Field("mode_of_payment") String mode_of_payment,
                                   @Field("comment") String comment);


    @POST(EndApi.LIST_PAYMENT)
    @FormUrlEncoded
    Call<SuccessModel> patientPaymentDetails(@Field("doctor_id") String doctor_id,
                                             @Field("user_id") String user_id,
                                             @Field("patient_id") String patient_id,
                                             @Field("date") String date);


    @POST(EndApi.APPOINTMENT_HISTORY)
    @FormUrlEncoded
    Call<SuccessModel> patientAppointmentHistory(@Field("doctor_id") String doctor_id,
                                                 @Field("patient_id") String patient_id,
                                                 @Field("date") String date);


    @POST(EndApi.PRESCRIPTION_HISTORY)
    @FormUrlEncoded
    Call<SuccessModel> prescriptionHistory(@Field("doctor_id") String doctor_id,
                                           @Field("patient_id") String patient_id,
                                           @Field("user_id") String user_id);


    @POST(EndApi.ADD_PRESCRIPTION)
    @FormUrlEncoded
    Call<SuccessModel> addPrescription(@Field("doctor_id") String doctor_id,
                                       @Field("patient_id") String patient_id,
                                       @Field("user_id") String user_id,
                                       @Field("service_id") String service_id,
                                       @Field("symptoms") String symptoms,
                                       @Field("diagnosis") String diagnosis,
                                       @Field("medication_name") String medication_name,
                                       @Field("medication_note") String medication_note,
                                       @Field("test_name") String test_name,
                                       @Field("test_note") String test_note);

    @POST(EndApi.ENQUIRY_LIST)
    @FormUrlEncoded
    Call<SuccessModel> enquiryList(@Field("doctor_id") String doctor_id);


    @POST(EndApi.ENQUIRY_REPLY)
    @FormUrlEncoded
    Call<SuccessModel> enquiryReply(@Field("enquiry_id") String enquiry_id,
                                    @Field("reply") String reply);


    @POST(EndApi.ADD_PRESCRIPTION_DOCUMENT)
    @FormUrlEncoded
    Call<SuccessModel> addPrescriptionDocument(@Field("doctor_id") String doctor_id,
                                               @Field("hospital_id") String hospital_id,
                                               @Field("service_id") String service_id,
                                               @Field("patient_id") String patient_id,
                                               @Field("img_base") String img_base,
                                               @Field("title") String title);


    @POST(EndApi.LIST_PRESCRIPTION_DOCUMENT)
    @FormUrlEncoded
    Call<SuccessModel> listPrescriptionDocument(@Field("doctor_id") String doctor_id,
                                                @Field("hospital_id") String hospital_id,
                                                @Field("patient_id") String patient_id);


    @POST(EndApi.DOCTORlIST)
    Call<SuccessModel> doctorList();


    @POST(EndApi.ADD_NEW_DOCTOR)
    @FormUrlEncoded
    Call<SuccessModel> addNewDoctor(@Field("super_doctor_id") String doctor_id,
                                    @Field("user_id") String user_id,
                                    @Field("name") String name,
                                    @Field("email") String email,
                                    @Field("phone_no") String phone_no,
                                    @Field("address") String address,
                                    @Field("profile") String profile,
                                    @Field("department_id") String department_id,
                                    @Field("Is_super_admin") String Is_super_admin,
                                    @Field("gender") String gender);

    @POST(EndApi.ADD_ADMIN_DEPARTMENT)
    @FormUrlEncoded
    Call<SuccessModel> addNewDepartment(@Field("super_doctor_id") String super_doctor_id,
                                        @Field("user_id") String user_id,
                                        @Field("name") String name,
                                        @Field("description") String description);

    @POST(EndApi.LIST_ADMIN_DEPARTMENT)
    @FormUrlEncoded
    Call<SuccessModel> listDepartment(@Field("user_id") String user_id);


    @POST(EndApi.PATIENT_LIST_ADMIN)
    @FormUrlEncoded
    Call<SuccessModel> patientListAdmin(@Field("user_id") String user_id);


    @POST(EndApi.ADD_PATIENT_ADMIN)
    @FormUrlEncoded
    Call<SuccessModel> addPatientAdmin(@Field("super_doctor_id") String super_doctor_id,
                                       @Field("name") String name,
                                       @Field("email") String email,
                                       @Field("user_id") String user_id,
                                       @Field("address") String address,
                                       @Field("phone_no") String phone,
                                       @Field("age") String age,
                                       @Field("gender") String gender,
                                       @Field("doctor_id") String doctor_id);


    @POST(EndApi.LIST_ADMIN_SERVICE)
    @FormUrlEncoded
    Call<SuccessModel> listService(@Field("user_id") String user_id);


    @POST(EndApi.ADD_CLASS_SESSION)
    @FormUrlEncoded
    Call<SuccessModel>  callSessionAdd(@Field("class_id") String class_id,
                                       @Field("doctor_id") String doctor_id,
                                       @Field("date") String date,
                                       @Field("time") String time,
                                       @Field("label") String label,
                                       @Field("cost") String cost,
                                       @Field("no_of_seats") String no_of_seats);

    @POST(EndApi.ADD_ADMIN_SERVICE)
    @FormUrlEncoded
    Call<SuccessModel> addNewAdminService(@Field("doctor_id") String doctor_id,
                                          @Field("user_id") String user_id,
                                          @Field("s_name") String s_name,
                                          @Field("service_cost") String service_cost,
                                          @Field("service_time") String service_time,
                                          @Field("description") String description);


    @POST(EndApi.DOCTORAPPOINTMENTSLOT)
    @FormUrlEncoded
    Call<SuccessModel> addNewTimeSlot(@Field("doctor_id") String doctor_id,
                                      @Field("user_id") String user_id,
                                      @Field("dayStatus") String dayStatus,
                                      @Field("timeSlot") String timeSlot,
                                      @Field("day") String day,
                                      @Field("startTime") String startTime,
                                      @Field("endTime") String endTime);


    @POST(EndApi.CANCLEAPPOINTMENT)
    @FormUrlEncoded
    Call<SuccessModel> cancelAppointment(@Field("doctor_id") String doctor_id,
                                         @Field("user_id") String user_id,
                                         @Field("date") String date,
                                         @Field("start_time") String start_time,
                                         @Field("end_time") String end_time);

    @POST(EndApi.LIST_SESSION)
    @FormUrlEncoded
    Call<SuccessModel> listSession(@Field("class_id") String class_id);


    @POST(EndApi.EDIT_SESSION)
    @FormUrlEncoded
    Call<SuccessModel> editSession(@Field("session_id") String session_id,
                                   @Field("patient_id") String patient_id,
                                   @Field("doctor_id") String doctor_id,
                                   @Field("no_of_seats") String no_of_seats);

    @POST(EndApi.ADMIN_CLASS_LIST)
    Call<SuccessModel> adminCallList();


    @POST(EndApi.SESSION_ADDED_PATIENT_LIST)
    @FormUrlEncoded
    Call<SuccessModel>  listSessionPatientAdded(@Field("session_id") String session_id);
}

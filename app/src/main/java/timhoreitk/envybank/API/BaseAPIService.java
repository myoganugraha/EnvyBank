package timhoreitk.envybank.API;


import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import timhoreitk.envybank.Model.BankLoginModel;
import timhoreitk.envybank.Model.UserLoginModel;

public interface BaseAPIService {


    @POST("authenticate")
    Call<ResponseBody> userLoginRequest(@Body UserLoginModel body);

    @POST("bank-auth")
    Call<ResponseBody> bankLoginRequest(@Body BankLoginModel body);


    @Multipart
    @POST("user")
    Call<ResponseBody> registerRequest(@PartMap Map<String, RequestBody> text,
                                       @Part MultipartBody.Part photo);

    @FormUrlEncoded
    @POST("tambahWisata.php")
    Call<ResponseBody> tambahWisataRequest(@Field("title") String title,
                                           @Field("description") String descrition,
                                           @Field("category") int category,
                                           @Field("latitude") Double latitude,
                                           @Field("longitude") Double longitude,
                                           @Field("id_user") int id_user,
                                           @Field("image") String image);

    @FormUrlEncoded
    @POST("editProfile.php")
    Call<ResponseBody> editProfile(@Field("id_user") int id_user,
                                   @Field("name") String name,
                                   @Field("email") String email,
                                   @Field("website") String website,
                                   @Field("profile_picture") String profile_picture);



}

package timhoreitk.envybank.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import timhoreitk.envybank.Model.OTPModel;
import timhoreitk.envybank.Model.OTPVerifModel;

public interface MainAPIService {

    @Headers("Authorization: Bearer 08d1082d26604401721caf8233bf7b0c")
    @PUT("otp/{key}")
    Call<ResponseBody> getOTP(@Path("key") String key, @Body OTPModel body);

    @Headers("Authorization: Bearer 08d1082d26604401721caf8233bf7b0c")
    @POST("otp/{key}/verifications")
    Call<ResponseBody> verificationOTP(@Path("key") String key, @Body OTPVerifModel body);

}

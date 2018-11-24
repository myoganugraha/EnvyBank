package timhoreitk.envybank.API;

public class UtilsAPI {
    public static final String BASE_URL_API = " http://4a2f2ef1.ngrok.io/api/envybank/";

    public static BaseAPIService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseAPIService.class);
    }
}

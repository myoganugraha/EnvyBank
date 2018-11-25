package timhoreitk.envybank.API;

public class UtilsAPI {
    public static final String BASE_URL_API = "https://3aa75582.ngrok.io/api/envybank/";

    public static final String MAINAPI = "https://api.mainapi.net/smsotp/1.0.1/";

    public static BaseAPIService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseAPIService.class);
    }

    public static MainAPIService getMainAPIService(){
        return RetrofitClientMainAPI.getClient(MAINAPI).create(MainAPIService.class);
    }
}

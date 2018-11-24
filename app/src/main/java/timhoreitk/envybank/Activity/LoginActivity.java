package timhoreitk.envybank.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timhoreitk.envybank.API.BaseAPIService;
import timhoreitk.envybank.API.UtilsAPI;
import timhoreitk.envybank.Model.LoginModel;
import timhoreitk.envybank.R;

public class LoginActivity extends AppCompatActivity {

    private Context mContext;

    @BindView(R.id.edt_username)
    EditText usernameLogin;

    @BindView(R.id.edt_password)
    EditText passwordLogin;

    @BindView(R.id.btn_procedLogin)
    Button btnProcedLogin;

    @BindView(R.id.btn_goToRegisterPage)
    Button btnGoToRegisterPage;

    private BaseAPIService baseAPIService;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        mContext = this;
        baseAPIService = UtilsAPI.getAPIService();

        initLogin();
    }

    private void initLogin() {
        
        btnProcedLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(usernameLogin.length() < 4 || passwordLogin.length() < 4){
                    Toast.makeText(mContext, "Bad Credentials", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog = ProgressDialog.show(mContext, null, "Please Wait ...", true, false);

                    baseAPIService.loginRequest(
                            new LoginModel(usernameLogin.getText().toString(), passwordLogin.getText().toString()))

                            .enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                    if(response.isSuccessful()){
                                        progressDialog.dismiss();

                                        try {
                                            JSONObject jsonObject = new JSONObject(response.body().string());
                                            JSONArray results = jsonObject.getJSONArray("results");
                                            JSONObject resultsObject = results.getJSONObject(0);

                                            Log.d("User: ", resultsObject.toString());
                                        }

                                        catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        catch (IOException e) {
                                            progressDialog.dismiss();
                                            e.printStackTrace();
                                        }

                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Log.e("error", "onFailure: ERROR > " + t.toString());
                                }
                            });
                }
            }
        });

     btnGoToRegisterPage.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
             finish();
         }
     });
    }

}

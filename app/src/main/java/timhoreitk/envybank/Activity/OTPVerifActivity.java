package timhoreitk.envybank.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chaos.view.PinView;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timhoreitk.envybank.API.MainAPIService;
import timhoreitk.envybank.API.UtilsAPI;
import timhoreitk.envybank.Model.OTPVerifModel;
import timhoreitk.envybank.R;
import timhoreitk.envybank.Utility.SessionManager;

public class OTPVerifActivity extends AppCompatActivity {

    @BindView(R.id.pinView)
    PinView pinView;

    @BindView(R.id.btnCheckOTP)
    Button btncheckOtp;

    String verifikasiOTP;
    String userId;

    SessionManager session;


    private MainAPIService mainAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverif);

        ButterKnife.bind(this);
        mainAPIService = UtilsAPI.getMainAPIService();

        verifikasiOTP = getIntent().getStringExtra("phone") ;
        userId = getIntent().getStringExtra("userid");
        
        Log.d("username dari intent: ",verifikasiOTP);

        loadFun();
    }

    private void loadFun() {

        btncheckOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pinView.length()<4 ){
                    Toast.makeText(OTPVerifActivity.this, "Wrong OTP", Toast.LENGTH_SHORT).show();
                }
                else {
                    mainAPIService.verificationOTP(verifikasiOTP, new OTPVerifModel(2, pinView.getText().toString(),300, 4))
                            .enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if(response.isSuccessful()){
                                        Toast.makeText(OTPVerifActivity.this, "OTP Valid", Toast.LENGTH_SHORT).show();
                                        initSession();
                                        session.createLoginSession(userId);

                                        startActivity(new Intent(OTPVerifActivity.this, MainActivity.class));
                                        finish();
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

    }

    private void initSession() {
        session = new SessionManager(getApplicationContext());

        if (session.isLoggedIn()){
            startActivity(new Intent(getApplicationContext(), MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }
}

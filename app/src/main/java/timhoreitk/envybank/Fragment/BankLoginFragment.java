package timhoreitk.envybank.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timhoreitk.envybank.API.BaseAPIService;
import timhoreitk.envybank.API.MainAPIService;
import timhoreitk.envybank.API.UtilsAPI;
import timhoreitk.envybank.Activity.OTPVerifActivity;
import timhoreitk.envybank.Model.BankLoginModel;
import timhoreitk.envybank.Model.OTPModel;
import timhoreitk.envybank.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BankLoginFragment extends Fragment {

    @BindView(R.id.edt_email_bank_login_fragment)
    EditText edtEmailBank;

    @BindView(R.id.edt_password_bank_login_fragment)
    EditText edtPasswordBank;

    @BindView(R.id.til_email_bank_login_fragment)
    TextInputLayout tilEmailBank;

    @BindView(R.id.til_password_bank_login_fragment)
    TextInputLayout tilPasswordBank;

    @BindView(R.id.btn_procedbankLogin_login_fragment)
    Button btnProcedBankLogin;

    private Unbinder unbinder;
    private Context mContext;

    private BaseAPIService baseAPIService;
    private MainAPIService mainAPIService;
    private ProgressDialog progressDialog;


    public BankLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_bank_login, container, false);
        mContext = view.getContext();

        unbinder = ButterKnife.bind(this, view);
        baseAPIService = UtilsAPI.getAPIService();
        mainAPIService = UtilsAPI.getMainAPIService();

        loadFun();
        return view;
    }

    private void loadFun() {
        btnProcedBankLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtEmailBank.length() <= 4 || edtPasswordBank.length() <= 4){
                    Toast.makeText(mContext, "Bad Credentials", Toast.LENGTH_LONG).show();
                    tilEmailBank.setError("Please Fill this Field");
                    tilPasswordBank.setError("Please Fill this Field");
                }
                else {
                    progressDialog = ProgressDialog.show(mContext, null, "Please Wait ...", true, true);

                    baseAPIService.bankLoginRequest(
                            new BankLoginModel(edtEmailBank.getText().toString(), edtPasswordBank.getText().toString())

                    ).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()){
                                progressDialog.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    JSONArray results = jsonObject.getJSONArray("results");
                                    JSONObject resultsObject = results.getJSONObject(0);
                                    String phone = resultsObject.getString("phone");


                                    Log.d("User: ", resultsObject.toString());

                                    mainAPIService.getOTP(phone, new OTPModel(2, "+"+phone, 300, "OTP Envy Bank Anda {{otp}}.", 4))
                                            .enqueue(new Callback<ResponseBody>() {
                                                @Override
                                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                    if (response.isSuccessful()){
                                                        Intent intent = new Intent(getActivity(), OTPVerifActivity.class);
                                                        intent.putExtra("phone", phone);
                                                        startActivity(intent);
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                    Log.e("error", "onFailure: ERROR > " + t.toString());
                                                }
                                            });


                                    getActivity().getFragmentManager().popBackStack();
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}

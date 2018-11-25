package timhoreitk.envybank.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import timhoreitk.envybank.API.BaseAPIService;
import timhoreitk.envybank.API.UtilsAPI;
import timhoreitk.envybank.R;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.edtRegister_name)
    EditText edtName;

    @BindView(R.id.edtRegister_username)
    EditText edtUsername;

    @BindView(R.id.edtRegister_email)
    EditText edtEmail;

    @BindView(R.id.edtRegister_password)
    EditText edtPassword;

    @BindView(R.id.edtRegister_phone)
    EditText edtPhone;

    @BindView(R.id.btnSelectImageRegister)
    Button btnSelectImage;

    @BindView(R.id.civ_register)
    CircleImageView civRegister;

    @BindView(R.id.linearLayoutSelectImageRegister)
    LinearLayout linearLayoutSelectImage;

    @BindView(R.id.tilRegister_name)
    TextInputLayout tilName;

    @BindView(R.id.tilRegister_username)
    TextInputLayout tilUSername;

    @BindView(R.id.tilRegister_email)
    TextInputLayout tilEmail;

    @BindView(R.id.tilRegister_password)
    TextInputLayout tilPassword;

    @BindView(R.id.tilRegister_phone)
    TextInputLayout tilPhone;

    private static final int REQUEST_CHOOSE_IMAGE = 3;

    private BaseAPIService baseAPIService;

    private Context mContext;

    Bitmap imageSelected;


    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        mContext = this;
        baseAPIService = UtilsAPI.getAPIService();
    }

    @OnClick(R.id.btn_register)
    public void procedRegister(){

        File file = createTempFile(imageSelected);
        HashMap<String, RequestBody> map = new HashMap<>();

        map.put("username", createPartFromString(edtUsername.getText().toString()));
        map.put("name", createPartFromString(edtName.getText().toString()));
        map.put("email", createPartFromString(edtEmail.getText().toString()));
        map.put("password", createPartFromString(edtPassword.getText().toString()));
        map.put("phone", createPartFromString(edtPhone.getText().toString()));

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("picture_url", edtUsername.getText() + ".jpg", reqFile);

        progressDialog = ProgressDialog.show(mContext, null, "Please Wait ...", true, true);
        baseAPIService.registerRequest(map, body)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            progressDialog.dismiss();

                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                Log.d( "onResponse: ", jsonObject.toString());
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

    @OnClick(R.id.btnGoToUserLogin)
    public void goLoginPage(){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

    @OnClick(R.id.btnSelectImageRegister)
    public void selectImage(){
        EasyImage.openChooserWithGallery(RegisterActivity.this, "Choose Picture", REQUEST_CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                CropImage.activity(Uri.fromFile(imageFile))
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setFixAspectRatio(true)
                        .start(RegisterActivity.this);
            }

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                super.onImagePickerError(e, source, type);
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(mContext);
                    if (photoFile != null) photoFile.delete();
                }
            }
        });

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imageSelected = null;
                try {
                    imageSelected = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                } catch (IOException e){
                    e.printStackTrace();
                }

                Glide.with(this)
                        .load(new File(resultUri.getPath()))
                        .apply(new RequestOptions().circleCrop())
                        .into(civRegister);

                linearLayoutSelectImage.setVisibility(View.VISIBLE);
                btnSelectImage.setVisibility(View.GONE);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();

                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File createTempFile(Bitmap bitmap){
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), edtUsername.getText().toString() + ".jpg");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bitmapData = byteArrayOutputStream.toByteArray();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bitmapData);
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  file;
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }
}

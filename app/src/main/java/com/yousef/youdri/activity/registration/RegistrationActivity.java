package com.yousef.youdri.activity.registration;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.yousef.mytoast.MyToast;
import com.yousef.youdri.R;
import com.yousef.youdri.database.Repository;
import com.yousef.youdri.databinding.ActivityRegistrationBinding;
import com.yousef.youdri.listener.EmailListener;
import com.yousef.youdri.listener.PhoneListener;
import com.yousef.youdri.listener.PrivacyPolicyListener;
import com.yousef.youdri.model.Constants;
import com.yousef.youdri.model.PrivacyPolicy;
import com.yousef.youdri.model.User;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity implements EmailListener, PhoneListener ,PrivacyPolicyListener {

    private ActivityRegistrationBinding binding;
    private Repository repository;
    private String status, visibility, visibility2;
    private boolean isName, isEmail, isPhone, isPassword, isConfirmPassword, isCheck;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new Repository(this);
        user = new User();
        init();
        handlingView();
    }

    void init(){
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void handlingView(){
        status = Constants.EMAIL;
        visibility = Constants.HIDE;
        visibility2 = Constants.HIDE;
        isName = false;
        isEmail = false;
        isPhone = false;
        isPassword = false;
        isConfirmPassword = false;
        isCheck = false;

        binding.emailBtn.setOnClickListener(v -> {
            if(!status.equals(Constants.EMAIL)){
                binding.emailBtn.setBackgroundResource(R.drawable.button_bg4);
                binding.phoneBtn.setBackgroundResource(R.drawable.button_bg5);
                status = Constants.EMAIL;
                binding.emailLayout.setVisibility(View.VISIBLE);
                binding.ccp.setVisibility(View.GONE);
                binding.phoneLayout.setVisibility(View.GONE);
            }
        });

        binding.phoneBtn.setOnClickListener(v -> {
            if(!status.equals(Constants.PHONE)){
                binding.emailBtn.setBackgroundResource(R.drawable.button_bg5);
                binding.phoneBtn.setBackgroundResource(R.drawable.button_bg4);
                status = Constants.PHONE;
                binding.emailLayout.setVisibility(View.GONE);
                binding.ccp.setVisibility(View.VISIBLE);
                binding.phoneLayout.setVisibility(View.VISIBLE);
            }
        });

        binding.visibility.setOnClickListener(v -> {
            if(visibility.equals(Constants.HIDE)){
                binding.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.visibility.setImageResource(R.drawable.visibility_off);
                visibility = Constants.SHOW;
            }
            else  if(visibility.equals(Constants.SHOW)){
                binding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.visibility.setImageResource(R.drawable.visibility);
                visibility = Constants.HIDE;
            }
        });

        binding.visibility2.setOnClickListener(v -> {
            if(visibility2.equals(Constants.HIDE)){
                binding.confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.visibility2.setImageResource(R.drawable.visibility_off);
                visibility2 = Constants.SHOW;
            }
            else  if(visibility2.equals(Constants.SHOW)){
                binding.confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.visibility2.setImageResource(R.drawable.visibility);
                visibility2 = Constants.HIDE;
            }
        });

        binding.name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(binding.name.getText().toString().length() > 0){
                    binding.nameError.setImageResource(com.yousef.mytoast.R.drawable.success);
                    isName = true;
                    if( ( (isEmail && status.equals(Constants.EMAIL)) || (isPhone && status.equals(Constants.PHONE)) ) && isPassword && isConfirmPassword)
                        binding.signUp.setEnabled(true);
                }
                else{
                    binding.nameError.setImageResource(com.yousef.mytoast.R.drawable.error);
                    isName = false;
                    binding.signUp.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Patterns.EMAIL_ADDRESS.matcher(binding.email.getText().toString()).matches()){
                    binding.emailError.setImageResource(com.yousef.mytoast.R.drawable.success);
                    isEmail = true;
                    if(isName && isPassword && isConfirmPassword)
                        binding.signUp.setEnabled(true);
                }
                else{
                    binding.emailError.setImageResource(com.yousef.mytoast.R.drawable.error);
                    isEmail = false;
                    binding.signUp.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Patterns.PHONE.matcher(binding.phone.getText().toString()).matches()){
                    binding.phoneError.setImageResource(com.yousef.mytoast.R.drawable.success);
                    isPhone = true;
                    if(isName && isPassword && isConfirmPassword)
                        binding.signUp.setEnabled(true);
                }
                else{
                    binding.phoneError.setImageResource(com.yousef.mytoast.R.drawable.error);
                    isPhone = false;
                    binding.signUp.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(binding.password.getText().toString().length() >= 6){
                    if(binding.confirmPassword.getText().toString().length() == 0) {
                        isPassword = true;
                        if (isName && ((isEmail && status.equals(Constants.EMAIL)) || (isPhone && status.equals(Constants.PHONE))) && isConfirmPassword)
                            binding.signUp.setEnabled(true);
                    }
                    else if(binding.password.getText().toString().equals(binding.confirmPassword.getText().toString())){
                        isPassword = true;
                        if (isName && ((isEmail && status.equals(Constants.EMAIL)) || (isPhone && status.equals(Constants.PHONE))))
                            binding.signUp.setEnabled(true);
                    }
                    else {
                        isPassword = false;
                        binding.signUp.setEnabled(false);
                    }
                }
                else{
                    isPassword = false;
                    binding.signUp.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(binding.confirmPassword.getText().toString().length() >= 6 && binding.password.getText().toString().equals(binding.confirmPassword.getText().toString())){
                    isConfirmPassword = true;
                    if( isName && ( (isEmail && status.equals(Constants.EMAIL)) || (isPhone && status.equals(Constants.PHONE) ) ))
                        binding.signUp.setEnabled(true);
                }
                else{
                    isConfirmPassword = false;
                    binding.signUp.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.privacyPolicy.setOnClickListener(view -> {
            if(!isCheck) {
                isCheck = true;
                repository.getPrivacyPolicy(this);
            }
        });

        binding.signUp.setOnClickListener(v -> {
            user.setName(binding.name.getText().toString());
            user.setEmail(binding.email.getText().toString());
            user.setPassword(binding.password.getText().toString());
            user.setPhone(Constants.NULL);
            user.setStatus(Constants.ACTIVE);
            if(!binding.privacyPolicyCheck.isChecked())
                MyToast.setLongToast(this, getString(R.string.privacyPolicyError), MyToast.FAIL);
            else if(repository.isDisconnect())
                repository.showDisconnectDialog();
            else if(status.equals( Constants.EMAIL)) {
                setVisibility(0.5f, View.VISIBLE);
                repository.createUser(user, this);
            }
            else if(status.equals( Constants.PHONE)){
                setVisibility(0.5f, View.VISIBLE);
                repository.checkPhone(binding.phone.getText().toString(), this);

            }
        });
    }

    void setVisibility(float alpha, int visible){
        binding.progress.setVisibility(visible);
        binding.layout.setAlpha(alpha);
    }

    @Override
    public void getPrivacyPolicy(PrivacyPolicy privacyPolicy) {
        isCheck = false;
        Dialog dialog = repository.showDialog(R.layout.policy);
        TextView privacyPolicyTextView = dialog.findViewById(R.id.privacyPolicy);
        if(Locale.getDefault().getDisplayLanguage().equals(Constants.ARABIC))
            privacyPolicyTextView.setText(privacyPolicy.getPrivacyPolicyArabic());
        else
            privacyPolicyTextView.setText(privacyPolicy.getPrivacyPolicyEnglish());
    }

    @Override
    public void onFailurePrivacyPolicy(String error) {
        isCheck = false;
        MyToast.setLongToast(this, error, MyToast.FAIL);
    }

    @Override
    public void sendResetPassword() {
        MyToast.setLongToast(this, getString(R.string.emailSend) +"\n"+ binding.email.getText().toString(), MyToast.SUCCESS);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.putExtra(Constants.EMAIL, user.getEmail());
        intent.putExtra(Constants.PASSWORD, user.getPassword());
        startActivity(intent);
        finishAffinity();
    }

    @Override
    public void fail(String error) {
        if(error.equals("The email address is already in use by another account."))
            MyToast.setLongToast(this, getString(R.string.emailExist), MyToast.FAIL);
        else
            MyToast.setLongToast(this, error, MyToast.FAIL);
        setVisibility(1f, View.GONE);
    }

    @Override
    public void onSuccess(User user) {
        setVisibility(1f, View.GONE);
        MyToast.setLongToast(this, getString(R.string.phoneExist),MyToast.FAIL);
    }

    @Override
    public void onFailure(String error) {
        if(error.equals(Constants.NULL)){
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        sleep(1000);
                        Intent intent = new Intent(getApplicationContext(), PhoneVerificationActivity.class);
                        intent.putExtra(Constants.NAME, binding.name.getText().toString());
                        intent.putExtra(Constants.CODE, binding.ccp.getSelectedCountryCode());
                        intent.putExtra(Constants.PHONE, binding.phone.getText().toString());
                        intent.putExtra(Constants.PASSWORD, binding.password.getText().toString());
                        startActivity(intent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        else{
            setVisibility(1f, View.GONE);
            MyToast.setLongToast(this, error,MyToast.FAIL);
        }
    }
}
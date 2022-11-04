package com.yousef.youdri.activity.registration.password;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import com.yousef.mytoast.MyToast;
import com.yousef.youdri.R;
import com.yousef.youdri.activity.registration.LoginActivity;
import com.yousef.youdri.database.Repository;
import com.yousef.youdri.databinding.ActivityResetPasswordBinding;
import com.yousef.youdri.listener.EmailListener;
import com.yousef.youdri.model.Constants;

public class ResetPasswordActivity extends AppCompatActivity implements EmailListener {

    private ActivityResetPasswordBinding binding;
    private Repository repository;
    private String visibility, visibility2, email, password;
    private boolean isPassword, isConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new Repository(this);
        init();
        handlingView();
    }

    void init(){
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email = getIntent().getExtras().getString(Constants.EMAIL);
        password = getIntent().getExtras().getString(Constants.PASSWORD);
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
        visibility = Constants.HIDE;
        visibility2 = Constants.HIDE;
        isPassword = false;
        isConfirmPassword = false;

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

        binding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(binding.password.getText().toString().length() >= 6){
                    isPassword = true;
                    if(isConfirmPassword)
                        binding.save.setEnabled(true);
                }
                else{
                    isPassword = false;
                    binding.save.setEnabled(false);
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
                    if(isPassword)
                        binding.save.setEnabled(true);
                }
                else{
                    isConfirmPassword = false;
                    binding.save.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.save.setOnClickListener(v -> {
            if(repository.isDisconnect())
                repository.showDisconnectDialog();
            else {
                setVisibility(0.5f, View.VISIBLE);
                repository.resetPasswordWithPhone(email, password, binding.password.getText().toString(), this);
            }
        });
    }

    void setVisibility(float alpha, int visible){
        binding.progress.setVisibility(visible);
        binding.layout.setAlpha(alpha);
    }

    @Override
    public void sendResetPassword() {
        repository.setIntent(LoginActivity.class);
        finishAffinity();
    }

    @Override
    public void fail(String error) {
        setVisibility(1f, View.GONE);
        MyToast.setLongToast(this, error, MyToast.FAIL);
    }
}
package com.yousef.youdri.activity.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;

import com.yousef.youdri.R;
import com.yousef.youdri.database.Repository;
import com.yousef.youdri.databinding.ActivityRegistrationBinding;
import com.yousef.youdri.model.Constants;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;
    private Repository repository;
    private String status, visibility, visibility2;
    private boolean isName, isEmail, isPhone, isPassword, isConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new Repository(this);
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

        binding.emailBtn.setOnClickListener(v -> {
            if(!status.equals(Constants.EMAIL)){
                binding.emailBtn.setBackgroundResource(R.drawable.button_bg4);
                binding.phoneBtn.setBackgroundResource(R.drawable.button_bg5);
                status = Constants.EMAIL;
                binding.emailText.setVisibility(View.VISIBLE);
                binding.emailLayout.setVisibility(View.VISIBLE);
                binding.ccp.setVisibility(View.GONE);
                binding.phoneText.setVisibility(View.GONE);
                binding.phoneLayout.setVisibility(View.GONE);
            }
        });

        binding.phoneBtn.setOnClickListener(v -> {
            if(!status.equals(Constants.PHONE)){
                binding.emailBtn.setBackgroundResource(R.drawable.button_bg5);
                binding.phoneBtn.setBackgroundResource(R.drawable.button_bg4);
                status = Constants.PHONE;
                binding.emailText.setVisibility(View.GONE);
                binding.emailLayout.setVisibility(View.GONE);
                binding.ccp.setVisibility(View.VISIBLE);
                binding.phoneText.setVisibility(View.VISIBLE);
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
                    isPassword = true;
                    if( isName && ( (isEmail && status.equals(Constants.EMAIL)) || (isPhone && status.equals(Constants.PHONE) ) ) && isConfirmPassword )
                        binding.signUp.setEnabled(true);
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
                    if( isName && ( (isEmail && status.equals(Constants.EMAIL)) || (isPhone && status.equals(Constants.PHONE) ) ) && isPassword)
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

        binding.signUp.setOnClickListener(v -> {
            if(repository.isDisconnect())
                repository.showDisconnectDialog();
            else if(status.equals( Constants.EMAIL)) {
                setVisibility();
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            sleep(1000);
//                            Intent intent = new Intent(this, EmailVerification.class);
//                            intent.putExtra(Constants.NAME, binding.name.getText().toString());
//                            intent.putExtra(Constants.EMAIL, binding.email.getText().toString());
//                            intent.putExtra(Constants.PASSWORD, binding.password.getText().toString());
//                            startActivity(intent);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
            else if(status.equals( Constants.PHONE)){
                setVisibility();
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            sleep(1000);
//                            Intent intent = new Intent(this, PhoneVerification.class);
//                            intent.putExtra(Constants.NAME, binding.name.getText().toString());
//                            intent.putExtra(Constants.CODE, binding.ccp.getSelectedCountryNameCode());
//                            intent.putExtra(Constants.PHONE, binding.phone.getText().toString());
//                            intent.putExtra(Constants.PASSWORD, binding.password.getText().toString());
//                            startActivity(intent);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }

    void setVisibility(){
        binding.progress.setVisibility(View.VISIBLE);
        binding.layout.setAlpha(0.5f);
    }
}
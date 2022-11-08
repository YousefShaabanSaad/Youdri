package com.yousef.youdri.activity.registration;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import com.yousef.mytoast.MyToast;
import com.yousef.youdri.R;
import com.yousef.youdri.activity.home.HomeActivity;
import com.yousef.youdri.activity.registration.password.ForgotPasswordActivity;
import com.yousef.youdri.database.Repository;
import com.yousef.youdri.databinding.ActivityLoginBinding;
import com.yousef.youdri.listener.LoginListener;
import com.yousef.youdri.listener.MainListener;
import com.yousef.youdri.model.Constants;
import com.yousef.youdri.model.User;

/**
 * اللَّهُمَّ صَلِّ علَى مُحَمَّدٍ وعلَى آلِ مُحَمَّدٍ، كما صَلَّيْتَ علَى إبْرَاهِيمَ وعلَى آلِ إبْرَاهِيمَ؛ إنَّكَ حَمِيدٌ مَجِيدٌ
 * اللَّهُمَّ بَارِكْ علَى مُحَمَّدٍ وعلَى آلِ مُحَمَّدٍ، كما بَارَكْتَ علَى إبْرَاهِيمَ وعلَى آلِ إبْرَاهِيمَ؛ إنَّكَ حَمِيدٌ مَجِيدٌ

 *تم برمجتُه بواسطة : يوسف شعبان
 * +201142747343
 * yousefshaabansaad42@gmail.com
 */

public class LoginActivity extends AppCompatActivity implements LoginListener, MainListener {

    private ActivityLoginBinding binding;
    private Repository repository;
    private String status, visibility;
    private boolean isEmail, isPhone, isPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
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
        isEmail = false;
        isPhone = false;
        isPassword = false;

        if(getIntent().getExtras() != null){
            binding.email.setText(getIntent().getExtras().getString(Constants.EMAIL));
            binding.password.setText(getIntent().getExtras().getString(Constants.PASSWORD));
            binding.emailError.setImageResource(com.yousef.mytoast.R.drawable.success);
            binding.login.setEnabled(true);
        }

        binding.emailBtn.setOnClickListener(v -> {
            if(!status.equals(Constants.EMAIL)){
                binding.emailBtn.setBackgroundResource(R.drawable.button_bg4);
                binding.phoneBtn.setBackgroundResource(R.drawable.button_bg5);
                status = Constants.EMAIL;
                binding.emailLayout.setVisibility(View.VISIBLE);
                binding.phoneLayout.setVisibility(View.GONE);
                binding.login.setEnabled(false);
            }
        });

        binding.phoneBtn.setOnClickListener(v -> {
            if(!status.equals(Constants.PHONE)){
                binding.emailBtn.setBackgroundResource(R.drawable.button_bg5);
                binding.phoneBtn.setBackgroundResource(R.drawable.button_bg4);
                status = Constants.PHONE;
                binding.emailLayout.setVisibility(View.GONE);
                binding.phoneLayout.setVisibility(View.VISIBLE);
                binding.login.setEnabled(false);
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

        binding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Patterns.EMAIL_ADDRESS.matcher(binding.email.getText().toString()).matches()){
                    binding.emailError.setImageResource(com.yousef.mytoast.R.drawable.success);
                    isEmail = true;
                    if(isPassword)
                        binding.login.setEnabled(true);
                }
                else{
                    binding.emailError.setImageResource(com.yousef.mytoast.R.drawable.error);
                    isEmail = false;
                    binding.login.setEnabled(false);
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
                    if(isPassword)
                        binding.login.setEnabled(true);
                }
                else{
                    binding.phoneError.setImageResource(com.yousef.mytoast.R.drawable.error);
                    isPhone = false;
                    binding.login.setEnabled(false);
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
                    if( (isEmail && status.equals(Constants.EMAIL)) || (isPhone && status.equals(Constants.PHONE)) )
                        binding.login.setEnabled(true);
                }
                else{
                    isPassword = false;
                    binding.login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.forgotPassword.setOnClickListener(v ->
            repository.setIntent(ForgotPasswordActivity.class)
        );

        binding.login.setOnClickListener(v -> {
            if(repository.isDisconnect())
                repository.showDisconnectDialog();
            else if(status.equals( Constants.EMAIL)) {
                setVisibility(0.5f, View.VISIBLE);
                repository.signInWithEmail(binding.email.getText().toString(), binding.password.getText().toString(), this);
            }
            else if(status.equals( Constants.PHONE)){
                setVisibility(0.5f, View.VISIBLE);
                repository.signInWithPhone(binding.phone.getText().toString(), binding.password.getText().toString(), this);
            }
        });

        binding.signUp.setOnClickListener(v ->
            repository.setIntent(RegistrationActivity.class)
        );
    }

    void setVisibility(float alpha, int visible){
        binding.progress.setVisibility(visible);
        binding.layout.setAlpha(alpha);
    }

    @Override
    public void onSuccess(String text) {
        if(text.equals(Constants.NULL)){
            setVisibility(1f, View.GONE);
            MyToast.setLongToast(this, getString(R.string.userNotExist), MyToast.FAIL);
        }
        else
            repository.getUser(LoginActivity.this);
    }

    @Override
    public void onFailureEmail(String error) {
        setVisibility(1f, View.GONE);

        if(error.contains("There is no user")) {
            MyToast.setLongToast(this, getString(R.string.emailError), MyToast.FAIL);
        }
        else if(error.contains("The password is invalid")) {
            MyToast.setLongToast(this, getString(R.string.passwordError), MyToast.FAIL);
        }
        else
            MyToast.setLongToast(this, error, MyToast.FAIL);
    }

    @Override
    public void onFailurePhone(String error) {
        setVisibility(1f, View.GONE);
        MyToast.setLongToast(this, getString(R.string.phoneError), MyToast.FAIL);
    }

    @Override
    public void getUser(User user) {
        if (user.getStatus() != null ) {
            if(!user.getPassword().equals(binding.password.getText().toString())){
                repository.updatePasswordUser(binding.password.getText().toString());
            }
            if (user.getStatus().equals(Constants.BLOCK)) {
                repository.setIntent(BlockActivity.class);
                finish();
            }
            else if (user.getStatus().equals(Constants.ACTIVE)) {
                if(status.equals(Constants.EMAIL)) {
                    if (repository.getFirebaseUser().isEmailVerified()) {
                        repository.setIntent(HomeActivity.class);
                        finish();
                    } else if(user.getPhone().equals(Constants.NULL)){
                        setVisibility(1f, View.GONE);
                        repository.signOut();
                        MyToast.setLongToast(this, getString(R.string.emailOpen), MyToast.FAIL);
                    }
                    else{
                        setVisibility(1f, View.GONE);
                        MyToast.setLongToast(this, getString(R.string.emailError), MyToast.FAIL);
                        repository.signOut();
                    }
                }
                else if(status.equals(Constants.PHONE)){
                    repository.setIntent(HomeActivity.class);
                    finish();
                }
            }
        }
        else {
            setVisibility(1f, View.GONE);
            MyToast.setLongToast(this, getString(R.string.userNotExist), MyToast.FAIL);
            repository.signOut();
        }
    }

    @Override
    public void fail(String error) {
        repository.signOut();
        setVisibility(1f, View.GONE);
    }
}
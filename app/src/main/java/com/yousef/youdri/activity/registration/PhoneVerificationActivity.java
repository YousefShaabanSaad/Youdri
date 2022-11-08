package com.yousef.youdri.activity.registration;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.yousef.mytoast.MyToast;
import com.yousef.youdri.R;
import com.yousef.youdri.activity.home.HomeActivity;
import com.yousef.youdri.database.Repository;
import com.yousef.youdri.databinding.ActivityPhoneResetBinding;
import com.yousef.youdri.listener.EmailListener;
import com.yousef.youdri.listener.VerifyListener;
import com.yousef.youdri.model.Constants;
import com.yousef.youdri.model.User;

public class PhoneVerificationActivity extends AppCompatActivity implements VerifyListener, EmailListener {

    private ActivityPhoneResetBinding binding;
    private Repository repository;
    private String verificationId;
    private boolean isOne, isTwo, isThree, isFour, isFive, isSix;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneResetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new Repository(this);
        user = new User();
        init();
        handlingEditText();
    }

    private void init(){
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user.setName(getIntent().getExtras().getString(Constants.NAME));
        user.setEmail(getIntent().getExtras().getString(Constants.PHONE)+"@gmail.com");
        user.setPassword(getIntent().getExtras().getString(Constants.PASSWORD));
        user.setPhone(getIntent().getExtras().getString(Constants.PHONE));
        user.setStatus(Constants.ACTIVE);

        String code = getIntent().getExtras().getString(Constants.CODE);

        StringBuilder text= new StringBuilder();
        for(int i=0; i<user.getPhone().length(); i++){
            if(i < user.getPhone().length()-2)
                text.append('*');
            else
                text.append(user.getPhone().charAt(i));
        }
        binding.phone.setText(text.toString());
        verificationId = "";

        repository.startPhoneNumberVerification(code, user.getPhone(), this, this);

        binding.resend.setOnClickListener(view ->
                repository.startPhoneNumberVerification(code, user.getPhone(), this, this)
        );
        binding.verify.setOnClickListener(view ->
                verify()
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handlingEditText(){
        isOne = false;
        isTwo = false;
        isThree = false;
        isFour = false;
        isFive = false;
        isSix = false;

        binding.number1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(binding.number1.getText().toString().length() == 0){
                    isOne = false;
                }
                else {
                    isOne = true;
                    binding.number2.requestFocus();
                }
                binding.verify.setEnabled(isOne && isTwo && isThree && isFour && isFive && isSix);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.number2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(binding.number2.getText().toString().length() == 0){
                    isTwo = false;
                }
                else {
                    isTwo = true;
                    binding.number3.requestFocus();
                }
                binding.verify.setEnabled(isOne && isTwo && isThree && isFour && isFive && isSix);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.number3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(binding.number3.getText().toString().length() == 0){
                    isThree = false;
                }
                else {
                    isThree = true;
                    binding.number4.requestFocus();
                }
                binding.verify.setEnabled(isOne && isTwo && isThree && isFour && isFive && isSix);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.number4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(binding.number4.getText().toString().length() == 0){
                    isFour = false;
                }
                else {
                    isFour = true;
                    binding.number5.requestFocus();
                }
                binding.verify.setEnabled(isOne && isTwo && isThree && isFour && isFive && isSix);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.number5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(binding.number5.getText().toString().length() == 0)
                    isFive = false;
                else {
                    isFive = true;
                    binding.number6.requestFocus();
                }
                binding.verify.setEnabled(isOne && isTwo && isThree && isFour && isFive && isSix);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.number6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isSix = binding.number6.getText().toString().length() != 0;
                binding.verify.setEnabled(isOne && isTwo && isThree && isFour && isFive && isSix);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void setVisibility(float alpha, int visible){
        binding.progress.setVisibility(visible);
        binding.layout.setAlpha(alpha);
    }

    private void verify() {
        String total = binding.number1.getText().toString() + binding.number2.getText().toString() +
                binding.number3.getText().toString() + binding.number4.getText().toString() +
                binding.number5.getText().toString() + binding.number6.getText().toString();
        if(repository.isDisconnect())
            repository.showDisconnectDialog();
        else {
            setVisibility(0.5f, View.VISIBLE);
            repository.checkOTP(total, verificationId,this);
        }
    }

    @Override
    public void failSentCode(String error) {
        if (error.equals("This request is missing a valid app identifier, meaning that neither SafetyNet checks nor reCAPTCHA checks succeeded. Please try again, or check the logcat for more details."))
            MyToast.setLongToast(this, getString(R.string.robot), MyToast.FAIL);
        else if (error.equals("We have blocked all requests from this device due to unusual activity. Try again later."))
            MyToast.setLongToast(this, getString(R.string.tryAgainLater), MyToast.FAIL);
        else
            MyToast.setLongToast(this, error, MyToast.FAIL);
    }

    @Override
    public void sentCode(String verificationId) {
        this.verificationId = verificationId;
    }

    @Override
    public void onSuccess() {
        repository.signOut();
        repository.createUserByPhone(user, this);
    }

    @Override
    public void onFailure(String error) {
        setVisibility(1f, View.GONE);
        if(error.equals("The sms verification code used to create the phone auth credential is invalid. Please resend the verification code sms and be sure use the verification code provided by the user."))
            MyToast.setLongToast(this, getString(R.string.inValid), MyToast.FAIL);
        else if(error.equals("The sms code has expired. Please re-send the verification code to try again."))
            MyToast.setLongToast(this, getString(R.string.tryAgain), MyToast.FAIL);
        else
            MyToast.setLongToast(this, error, MyToast.FAIL);
    }

    @Override
    public void sendResetPassword() {
        repository.setIntent(HomeActivity.class);
        finishAffinity();
    }

    @Override
    public void fail(String error) {
        MyToast.setLongToast(this, error, MyToast.FAIL);
    }
}
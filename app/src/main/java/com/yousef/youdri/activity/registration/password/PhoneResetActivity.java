package com.yousef.youdri.activity.registration.password;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.yousef.youdri.R;
import com.yousef.youdri.database.Repository;
import com.yousef.youdri.databinding.ActivityPhoneResetBinding;
import com.yousef.youdri.listener.VerifyListener;
import com.yousef.youdri.model.Constants;

public class PhoneResetActivity extends AppCompatActivity implements VerifyListener {

    private ActivityPhoneResetBinding binding;
    private Repository repository;
    private String phone, email, password, verificationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneResetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new Repository(this);
        init();
        handlingEditText();
    }

    private void init(){
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        phone = getIntent().getExtras().getString(Constants.PHONE);
        email = getIntent().getExtras().getString(Constants.EMAIL);
        password = getIntent().getExtras().getString(Constants.PASSWORD);
        String code = getIntent().getExtras().getString(Constants.CODE);

        StringBuilder text= new StringBuilder();
        for(int i=0; i<phone.length(); i++){
            if(i < phone.length()-2)
                text.append('*');
            else
                text.append(phone.charAt(i));
        }
        binding.phone.setText(text.toString());
        verificationId = "";

        repository.startPhoneNumberVerification(code, phone, this, this);

        binding.resend.setOnClickListener(view ->
                repository.startPhoneNumberVerification(code, phone, this, this)
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
        binding.number1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.number2.requestFocus();
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
                binding.number3.requestFocus();
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
                binding.number4.requestFocus();
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
                binding.number5.requestFocus();
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
                binding.number6.requestFocus();
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
        if (total.length() != 6)
            Toast.makeText(this, getString(R.string.enterCode), Toast.LENGTH_LONG).show();
        else {
            setVisibility(0.5f, View.VISIBLE);
            repository.checkOTP(total, verificationId,this);
        }
    }

    @Override
    public void failSentCode(String error) {
        if (error.equals("This request is missing a valid app identifier, meaning that neither SafetyNet checks nor reCAPTCHA checks succeeded. Please try again, or check the logcat for more details."))
            Toast.makeText(this, getString(R.string.robot), Toast.LENGTH_SHORT).show();
        else if (error.equals("We have blocked all requests from this device due to unusual activity. Try again later."))
            Toast.makeText(this, getString(R.string.tryAgainLater), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sentCode(String verificationId) {
        this.verificationId = verificationId;
    }

    @Override
    public void onSuccess() {
        repository.signOut();
        Intent intent= new Intent(getApplicationContext(), ResetPasswordActivity.class);
        intent.putExtra(Constants.EMAIL, email);
        intent.putExtra(Constants.PASSWORD, password);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure(String error) {
        setVisibility(1f, View.GONE);
        if(error.equals("The sms verification code used to create the phone auth credential is invalid. Please resend the verification code sms and be sure use the verification code provided by the user."))
            Toast.makeText(this, getString(R.string.inValid), Toast.LENGTH_SHORT).show();
        else if(error.equals("The sms code has expired. Please re-send the verification code to try again."))
            Toast.makeText(this, getString(R.string.tryAgain), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
package com.yousef.youdri.activity.registration;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;

import com.yousef.youdri.R;
import com.yousef.youdri.databinding.ActivityLoginBinding;
import com.yousef.youdri.model.Constants;

/**
 * اللَّهُمَّ صَلِّ علَى مُحَمَّدٍ وعلَى آلِ مُحَمَّدٍ، كما صَلَّيْتَ علَى إبْرَاهِيمَ وعلَى آلِ إبْرَاهِيمَ؛ إنَّكَ حَمِيدٌ مَجِيدٌ
 * اللَّهُمَّ بَارِكْ علَى مُحَمَّدٍ وعلَى آلِ مُحَمَّدٍ، كما بَارَكْتَ علَى إبْرَاهِيمَ وعلَى آلِ إبْرَاهِيمَ؛ إنَّكَ حَمِيدٌ مَجِيدٌ

 *تم برمجتُه بواسطة : يوسف شعبان
 * +201142747343
 * yousefshaabansaad42@gmail.com
 */

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private String status, visibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

//        binding.emailOrPhone.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
//            if(isChecked) {
//                if (checkedId == R.id.emailBtn && !status.equals(Constants.EMAIL)) {
//                    status = Constants.EMAIL;
//                    binding.emailText.setVisibility(View.VISIBLE);
//                    binding.email.setVisibility(View.VISIBLE);
//                    binding.phoneText.setVisibility(View.GONE);
//                    binding.phone.setVisibility(View.GONE);
//                }
//                else if (checkedId == R.id.phoneBtn  && !status.equals(Constants.PHONE)) {
//                    status = Constants.PHONE;
//                    binding.emailText.setVisibility(View.GONE);
//                    binding.email.setVisibility(View.GONE);
//                    binding.phoneText.setVisibility(View.VISIBLE);
//                    binding.phone.setVisibility(View.VISIBLE);
//                }
//            }
//        });

        binding.emailBtn.setOnClickListener(v -> {
            if(!status.equals(Constants.EMAIL)){
                binding.emailBtn.setBackgroundResource(R.drawable.button_bg4);
                binding.phoneBtn.setBackgroundResource(R.drawable.button_bg5);
                status = Constants.EMAIL;
                    binding.emailText.setVisibility(View.VISIBLE);
                    binding.email.setVisibility(View.VISIBLE);
                    binding.phoneText.setVisibility(View.GONE);
                    binding.phone.setVisibility(View.GONE);
            }
        });

        binding.phoneBtn.setOnClickListener(v -> {
            if(!status.equals(Constants.PHONE)){
                binding.emailBtn.setBackgroundResource(R.drawable.button_bg5);
                binding.phoneBtn.setBackgroundResource(R.drawable.button_bg4);
                status = Constants.PHONE;
                binding.emailText.setVisibility(View.GONE);
                binding.email.setVisibility(View.GONE);
                binding.phoneText.setVisibility(View.VISIBLE);
                binding.phone.setVisibility(View.VISIBLE);
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
    }
}
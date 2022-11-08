package com.yousef.youdri.database;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import com.google.firebase.auth.FirebaseUser;
import com.yousef.internetconnectionjava.MyConnectionJava;
import com.yousef.youdri.listener.EmailListener;
import com.yousef.youdri.listener.LoginListener;
import com.yousef.youdri.listener.MainListener;
import com.yousef.youdri.listener.PhoneListener;
import com.yousef.youdri.listener.PrivacyPolicyListener;
import com.yousef.youdri.listener.VerifyListener;
import com.yousef.youdri.model.User;

/**
 * اللَّهُمَّ صَلِّ علَى مُحَمَّدٍ وعلَى آلِ مُحَمَّدٍ، كما صَلَّيْتَ علَى إبْرَاهِيمَ وعلَى آلِ إبْرَاهِيمَ؛ إنَّكَ حَمِيدٌ مَجِيدٌ
 * اللَّهُمَّ بَارِكْ علَى مُحَمَّدٍ وعلَى آلِ مُحَمَّدٍ، كما بَارَكْتَ علَى إبْرَاهِيمَ وعلَى آلِ إبْرَاهِيمَ؛ إنَّكَ حَمِيدٌ مَجِيدٌ

 *تم برمجتُه بواسطة : يوسف شعبان
 * +201142747343
 * yousefshaabansaad42@gmail.com
 */

public class Repository {

    private final MyRegister register;
    private final MyConnectionJava myConnectionJava;
    private final CommonFunctions functions;


    public Repository(Context context) {
        register = new MyRegister();
        myConnectionJava = new MyConnectionJava(context);
        functions = new CommonFunctions(context);
    }

    //Todo MainActivity
    public FirebaseUser getFirebaseUser() {
        return register.getFirebaseUser();
    }

    public void getUser(MainListener mainListener) {
        register.getUser(mainListener);
    }

    //Todo LoginActivity
    public void signInWithPhone(String phone, String password, LoginListener loginListener) {
        register.signInWithPhone(phone, password, loginListener);
    }

    public void signInWithEmail(String email, String password, LoginListener loginListener) {
        register.signInWithEmail(email, password, loginListener);
    }

    public boolean isDisconnect() {
        return myConnectionJava.isDisconnect();
    }

    public void showDisconnectDialog() {
        myConnectionJava.showDisconnectDialog();
    }

    public void updatePasswordUser(String newPassword) {
        register.updatePasswordUser(newPassword);
    }

    public void setIntent(Class<?> clc) {
        functions.setIntent(clc);
    }

    public void signOut() {
        register.signOut();
    }


    //Todo BlockActivity
    public void whatsapp() {
        functions.whatsapp();
    }

    //Todo ForgotPasswordActivity
    public void resetPasswordWithEmail(String email, EmailListener emailListener) {
        register.resetPasswordWithEmail(email, emailListener);
    }

    public void checkPhone(String phone, PhoneListener phoneListener) {
        register.checkPhone(phone, phoneListener);
    }

    //Todo PhoneResetActivity
    public void startPhoneNumberVerification(String code, String phoneNumber, Activity activity, VerifyListener verifyListener) {
        register.startPhoneNumberVerification(code, phoneNumber, activity, verifyListener);
    }

    public void checkOTP(String code, String verificationId, VerifyListener verifyListener) {
        register.checkOTP(code, verificationId, verifyListener);
    }

    public void createUserByPhone(User user, EmailListener emailListener){
        register.createUserByPhone(user, emailListener);
    }

    //Todo ResetPasswordActivity
    public void resetPasswordWithPhone(String email, String oldPassword, String newPassword, EmailListener emailListener) {
        register.resetPasswordWithPhone(email, oldPassword, newPassword, emailListener);
    }

    //Todo RegistrationActivity
    public void createUser(User user, EmailListener emailListener){
        register.createUser(user, emailListener);
    }

    public void getPrivacyPolicy(PrivacyPolicyListener listener){
        register.getPrivacyPolicy(listener);
    }

    public Dialog showDialog(int layout){
        return functions.showDialog(layout);
    }
}

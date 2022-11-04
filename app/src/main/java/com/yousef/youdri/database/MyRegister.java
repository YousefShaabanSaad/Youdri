package com.yousef.youdri.database;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yousef.youdri.listener.EmailListener;
import com.yousef.youdri.listener.LoginListener;
import com.yousef.youdri.listener.MainListener;
import com.yousef.youdri.listener.PhoneListener;
import com.yousef.youdri.listener.VerifyListener;
import com.yousef.youdri.model.Constants;
import com.yousef.youdri.model.User;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * اللَّهُمَّ صَلِّ علَى مُحَمَّدٍ وعلَى آلِ مُحَمَّدٍ، كما صَلَّيْتَ علَى إبْرَاهِيمَ وعلَى آلِ إبْرَاهِيمَ؛ إنَّكَ حَمِيدٌ مَجِيدٌ
 * اللَّهُمَّ بَارِكْ علَى مُحَمَّدٍ وعلَى آلِ مُحَمَّدٍ، كما بَارَكْتَ علَى إبْرَاهِيمَ وعلَى آلِ إبْرَاهِيمَ؛ إنَّكَ حَمِيدٌ مَجِيدٌ

 *تم برمجتُه بواسطة : يوسف شعبان
 * +201142747343
 * yousefshaabansaad42@gmail.com
 */

public class MyRegister {

    private final FirebaseAuth auth;
    private final FirebaseFirestore firestore;
    public MyRegister(){
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

    }

    //Todo MainActivity
    public FirebaseUser getFirebaseUser() {
        return auth.getCurrentUser();
    }

    public void getUser(MainListener listener){
        firestore.collection(Constants.USERS)
                .document(getFirebaseUser().getUid())
                .get()
                .addOnSuccessListener(dataSnapshot ->
                        listener.getUser(dataSnapshot.toObject(User.class))
                )
                .addOnFailureListener(runnable ->
                        listener.fail(runnable.getMessage())
                );
    }

    //Todo LoginActivity
    public void signInWithPhone(String phone, String password, LoginListener listener) {
        firestore.collection(Constants.USERS)
                .whereEqualTo(Constants.PHONE, phone)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if(queryDocumentSnapshots.isEmpty())
                        listener.onSuccess(Constants.NULL);
                    else {
                        for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                            signInWithEmail(d.getString(Constants.EMAIL), password, listener);
                        }
                    }
                })
                .addOnFailureListener(e ->
                        listener.onFailurePhone(e.getMessage())
                );

    }

    public void signInWithEmail(String email, String password, LoginListener loginListener) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult ->
                        loginListener.onSuccess("")
                )
                .addOnFailureListener(e ->
                        loginListener.onFailureEmail(e.getMessage())
                );
    }

    public void updatePasswordUser(String newPassword){
        firestore.collection(Constants.USERS)
                .document(getFirebaseUser().getUid())
                .update(Constants.PASSWORD, newPassword);
    }

    public void signOut(){
        auth.signOut();
    }

    //Todo ForgotPasswordActivity
    public void resetPasswordWithEmail(String email, EmailListener emailListener){
        auth.sendPasswordResetEmail(email)
                .addOnSuccessListener(unused ->
                        emailListener.sendResetPassword()
                )
                .addOnFailureListener(e ->
                        emailListener.fail(e.getMessage())
                );
    }

    private boolean isCheck = false;
    private User user1 =new User();
    public void checkPhone(String phone, PhoneListener phoneListener) {
        firestore.collection(Constants.USERS)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot documentSnapshot:queryDocumentSnapshots.getDocuments()){
                        User user = documentSnapshot.toObject(User.class);
                        assert user != null;
                        if(user.getPhone().equals(phone)) {
                            isCheck = true;
                            user1 = user;
                        }
                    }
                    if(isCheck)
                        phoneListener.onSuccess(user1);
                    else
                        phoneListener.onFailure(Constants.NULL);
                })
                .addOnFailureListener(e ->
                        phoneListener.onFailure(e.getMessage())
                );

    }

    //Todo PhoneResetActivity
    public void startPhoneNumberVerification(String code, String phoneNumber, Activity activity, VerifyListener verifyListener) {
        // set this to remove reCaptcha web
        auth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(false);
        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        verifyListener.failSentCode(e.getMessage());
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId,
                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
                        verifyListener.sentCode(verificationId);
                    }
                };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(code+phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(activity)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void checkOTP(String code, String verificationId, VerifyListener verifyListener) {
        if (verificationId.isEmpty())
            return;
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                deleteUser(verifyListener);
            } else {
                verifyListener.onFailure(Objects.requireNonNull(task.getException()).getMessage());
            }
        });
    }

    public void deleteUser(VerifyListener verifyListener){
        getFirebaseUser().delete()
                .addOnSuccessListener(unused ->
                        verifyListener.onSuccess()
                );
    }

    //Todo ResetPasswordActivity
    public void resetPasswordWithPhone(String email, String oldPassword, String newPassword, EmailListener emailListener){
        auth.signInWithEmailAndPassword(email, oldPassword)
                .addOnSuccessListener(authResult ->
                        update(newPassword, emailListener)
                )
                .addOnFailureListener(e ->
                        emailListener.fail(e.getMessage())
                );
    }

    private void update(String newPassword, EmailListener emailListener){
        getFirebaseUser()
                .updatePassword(newPassword)
                .addOnSuccessListener(unused ->
                        emailListener.sendResetPassword()
                )
                .addOnFailureListener(e ->
                        emailListener.fail(e.getMessage())
                );
    }
}

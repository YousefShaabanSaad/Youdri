package com.yousef.youdri.database;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.yousef.youdri.R;

/**
 * اللَّهُمَّ صَلِّ علَى مُحَمَّدٍ وعلَى آلِ مُحَمَّدٍ، كما صَلَّيْتَ علَى إبْرَاهِيمَ وعلَى آلِ إبْرَاهِيمَ؛ إنَّكَ حَمِيدٌ مَجِيدٌ
 * اللَّهُمَّ بَارِكْ علَى مُحَمَّدٍ وعلَى آلِ مُحَمَّدٍ، كما بَارَكْتَ علَى إبْرَاهِيمَ وعلَى آلِ إبْرَاهِيمَ؛ إنَّكَ حَمِيدٌ مَجِيدٌ

 *تم برمجتُه بواسطة : يوسف شعبان
 * +201142747343
 * yousefshaabansaad42@gmail.com
 */

public class CommonFunctions {

    private final Context context;

    public CommonFunctions(Context context){
        this.context = context;
    }

    public void setIntent(Class<?> clc){
        Intent intent = new Intent(context, clc);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void whatsapp(){
        String message = "السلام عليكم ورحمة اللّه وبركاته"+"\n"+"لماذا تم حظري؟";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData( Uri.parse("http://api.whatsapp.com/send?phone=+972524429686&text="+message) );
        context.startActivity(intent);
    }

    public Dialog showDialog(int layout){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(layout);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.background_dialog);
//        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = com.yousef.internetconnectionjava.R.style.DialogAnimation; //Setting the animations to dialog
        dialog.create();
        dialog.show();
        return dialog;
    }

}

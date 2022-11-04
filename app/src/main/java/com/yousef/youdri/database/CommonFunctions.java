package com.yousef.youdri.database;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

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
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData( Uri.parse("http://api.whatsapp.com/send?phone=+972524429686&text=السلام عليكم ورحمة اللّه وبركاته") );
        context.startActivity(intent);
    }

}

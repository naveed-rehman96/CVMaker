package cvMaker.resumeMaker.cvBuilder.resumeBuilder.cvTemplate.createCv.freeCv.javaClass;

import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

public class MyDrawableCompat {
    public static void setColorFilter(@NonNull Drawable drawable, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_ATOP));
        } else {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }
}

// how to use it in kotlin or java file


 //MyDrawableCompat.setColorFilter(view.background, Color.parseColor("#C8BA00"))
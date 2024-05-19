package com.example.appbanvexemphim.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import java.io.IOException;
import java.io.InputStream;

public class ImageConverter {
    public static Bitmap getImage(String imagePath, Context context){
        if (imagePath==null){
            imagePath = "kungfupanda.png";
        }
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(Constant.ImageFolderSuffix+imagePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Đọc dữ liệu hình ảnh từ InputStream và tạo đối tượng Bitmap
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()*2);
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);


        return output;
    }
}

package johnsmithwithharuhi.co.sakagumi.Domain.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.VectorDrawable;
import android.support.v4.content.ContextCompat;

public class BitmapUtil {

  public static Bitmap convertBitmapFromVectorDrawable(Context context, int id) {
    VectorDrawable vectorDrawable = (VectorDrawable) ContextCompat.getDrawable(context, id);
    Bitmap bitmap =
        Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(),
            Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    vectorDrawable.draw(canvas);
    return bitmap;
  }
}

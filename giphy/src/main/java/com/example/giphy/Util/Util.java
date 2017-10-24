package com.example.giphy.Util;

import android.content.res.Resources;

/**
 * Created by muehlemann on 10/24/17.
 *
 */

public class Util {

    public Util() throws Exception {
        throw new Exception("Util can't be instantiated");
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}

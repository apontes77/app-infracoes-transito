package br.com.pucgo.appTrafficViolations.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class BinaryBytes {
    public static byte[] getResourceInBytes(Context context, int resource ){
        final Bitmap img = BitmapFactory.decodeResource( context.getResources(), resource );
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        byte[] byteArray = stream.toByteArray();

        return byteArray ;
    }

    public static String getResourceName( Context context, int resource ){
        return( context.getResources().getResourceEntryName(resource) );
    }
}

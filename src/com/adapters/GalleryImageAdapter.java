package com.adapters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.classes.Utils;
import com.myfamily.R;

public class GalleryImageAdapter extends BaseAdapter 
{
	
	private ArrayList<String> imagePaths = new ArrayList<String>();
    private Context mContext;
    private Utils utils;

    private Integer[] mImageIds = {
            R.drawable.actionremove,
            R.drawable.addevent,
            R.drawable.eye,
            R.drawable.background,
    };

    public GalleryImageAdapter(Context context) 
    {
        mContext = context;
        utils = new Utils(context);
        imagePaths = utils.getFilePaths();
    }

    public int getCount() {
        return imagePaths.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    // Override this method according to your need
    public View getView(int index, View view, ViewGroup viewGroup) 
    {
        // TODO Auto-generated method stub
        ImageView i = new ImageView(mContext);
        
        Bitmap image = decodeFile(imagePaths.get(index));
		Matrix matrix = new Matrix();
		matrix.postRotate(90);
		Bitmap rotatedBitmap = Bitmap.createBitmap(image, 0, 0,
				image.getWidth(), image.getHeight(),
				matrix, true);
        i.setImageBitmap(rotatedBitmap);
       // i.setImageResource(mImageIds[index]);
        i.setLayoutParams(new Gallery.LayoutParams(200, 200));
        
        i.setScaleType(ImageView.ScaleType.FIT_XY);

        return i;
    }
    
    
    public static Bitmap decodeFile(String f) {
        	try {
                //Decode image size
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(new FileInputStream(f),null,o);

                //The new size we want to scale to
                final int REQUIRED_SIZE=150;

                //Find the correct scale value. It should be the power of 2.
                int scale=1;
                while(o.outWidth/scale/2>=REQUIRED_SIZE && o.outHeight/scale/2>=REQUIRED_SIZE)
                    scale*=2;

                //Decode with inSampleSize
                BitmapFactory.Options o2 = new BitmapFactory.Options();
                o2.inSampleSize=scale;
                return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
            } catch (FileNotFoundException e) {}
            return null;
    }
    
}
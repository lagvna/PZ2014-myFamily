package com.classes;
 
import android.graphics.Bitmap;
 
/**
 * Klasa reprezentująca zdjęcie
 * @author KMACIAZE
 *
 */
public class ImageItem {
    private Bitmap image;
    private String title;
 
    /**
     * Główny konstruktor klasy
     * @param image bitmapa obrazek
     * @param title tytuł obrazku
     */
    public ImageItem(Bitmap image, String title) {
        super();
        this.image = image;
        this.title = title;
    }
 
    public Bitmap getImage() {
        return image;
    }
 
    public void setImage(Bitmap image) {
        this.image = image;
    }
 
    public String getTitle() {
        return title;
    }
 
    public void setTitle(String title) {
        this.title = title;
    }
}
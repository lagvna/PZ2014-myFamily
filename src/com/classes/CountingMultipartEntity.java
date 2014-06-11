package com.classes;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;

import android.os.RecoverySystem.ProgressListener;

/**
 * Klasa pomocnicza do wysyłania zdjęć
 * @author KMACIAZE
 *
 */
public class CountingMultipartEntity extends MultipartEntity
{
 
	private final android.os.RecoverySystem.ProgressListener listener;
	/**
	 * Konstruktor klasy
	 * @param progressListener
	 */
    public CountingMultipartEntity(final android.os.RecoverySystem.ProgressListener progressListener) {
        super();
        this.listener = progressListener;
    }
    /**
     * Główny konstruktor klasy
     * @param mode mode
     * @param listener progres wysyłania
     */
    public CountingMultipartEntity(final HttpMultipartMode mode, final android.os.RecoverySystem.ProgressListener listener) {
        super(mode);
        this.listener = listener;
    }
	/**
	 * Konstruktor klasy
	 * @param progressListener
	 */
    public CountingMultipartEntity(HttpMultipartMode mode, final String boundary,
            final Charset charset, final android.os.RecoverySystem.ProgressListener listener) {
        super(mode, boundary, charset);
		this.listener = listener;
    }

    @Override
    public void writeTo(final OutputStream outstream) throws IOException {
        super.writeTo(new CountingOutputStream(outstream, this.listener));
    }
    /**
     * Interface ProgressListener
     * @author KMACIAZE
     */
    public static interface ProgressListener {
        void transferred(long num);
    }
    /**
     * Klasa służąca do zapisywania pliku
     * @author KMACIAZE
     *
     */
    public static class CountingOutputStream extends FilterOutputStream {

        private final android.os.RecoverySystem.ProgressListener listener;
        private int transferred;
        /**
         * Główny konstruktor klasy
         * @param out output stream
         * @param listener progres
         */
        public CountingOutputStream(final OutputStream out,
        		final android.os.RecoverySystem.ProgressListener listener) {
            super(out);
            this.listener = listener;
            this.transferred = 0;
        }

        /**
         * Metoda zapisująca do pliku
         */
        public void write(byte[] b, int off, int len) throws IOException {
            out.write(b, off, len);
            this.transferred += len;
            this.listener.onProgress(this.transferred);
            System.out.println("TYLE WYSLANO" + transferred);
            //this.listener.transferred(this.transferred);
        }
        /**
         * Metoda zapisująca do pliku
         */
        public void write(int b) throws IOException {
            out.write(b);
            this.transferred++;
            System.out.println("TYLE WYSLANO" + transferred);
            this.listener.onProgress(this.transferred);
        }
    }
}
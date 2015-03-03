package main;

import processing.core.PImage;

import java.util.ArrayList;

/**
 * Created by mrzl on 02.03.2015.
 */
public class ImageGradient {
    private ArrayList< Integer > gradientValues;

    public ImageGradient( PImage image ) {
        image.loadPixels();

        gradientValues = new ArrayList<>(  );

        for( int i = 0; i < image.width; i++ ) {
            int color = image.pixels[ i ];
            gradientValues.add( color );
        }
    }

    /**
     * 0-1.0
     *
     * @param percent
     * @return
     */
    public int get( float percent ) {
        return gradientValues.get( ( int ) (gradientValues.size() * percent) );
    }

    /**
     * length: 100
     *
     * @return
     */
    public float[][] getColors () {
        float[][] vals = new float[4][ 100 ];
        for( int i = 0; i < 100; i++ ) {
            float perc = i / 100.0f;
            int col = get( perc );
            float r = ( ( col >> 16 ) & 0xFF ) / 255.0f;
            float g = ( ( col >> 8 ) & 0xFF ) / 255.0f;
            float b = ( col & 0xFF ) / 255.0f;
            float a = ( ( col >> 24 ) & 0xFF ) / 255.0f;
            vals[0][i] = r;
            vals[1][i] = g;
            vals[2][i] = b;
            vals[3][i] = a;
        }

        return vals;
    }
}

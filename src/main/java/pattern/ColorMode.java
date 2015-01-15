package pattern;

import processing.core.PApplet;

import java.awt.*;

/**
 * Created by mrzl on 15.01.2015.
 */
public class ColorMode {

    private ColorMapping colorModeEnum;
    private float minHue, maxHue;
    public float red, green, blue;

    public ColorMode () {
        colorModeEnum = ColorMapping.HUE;
        minHue = maxHue = 0.0f;
    }

    public void setRange ( float min, float max ) {
        minHue = min;
        maxHue = max;
    }

    public void setVelocity( float _velocity, ColorMode cm ) {
        //switch( cm ) {
            //case ColorMapping.HUE
       // }
        float selectedHue = PApplet.map( _velocity, 0, 1, minHue, maxHue );
        int rgb = Color.HSBtoRGB( selectedHue, 0.9f, 0.9f );
        red = ( ( rgb >> 16 ) & 0xFF ) / 255.0f;
        green = ( ( rgb >> 8 ) & 0xFF ) / 255.0f;
        blue = ( rgb & 0xFF ) / 255.0f;
    }


}

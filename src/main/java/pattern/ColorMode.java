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

    public void setRangeMin( float min ) {
        this.minHue = min;
    }

    public void setRangeMax( float max ) {
        this.maxHue = max;
    }

    public void setRange ( float min, float max ) {
        minHue = min;
        maxHue = max;
    }

    public void setVelocity( float _velocity, ColorMapping cm ) {
        float selectedHue;
        int rgb = 0;
        switch( cm ) {
            case HUE:
                selectedHue = PApplet.map( _velocity, 0, 1, minHue, maxHue );
                rgb = Color.HSBtoRGB( selectedHue, 0.9f, 0.9f );
                break;
            case SATURATION:
                selectedHue = PApplet.map( _velocity, 0, 1, minHue, maxHue );
                rgb = Color.HSBtoRGB( 0.07222f, selectedHue, 0.9f );
                break;
            case BRIGHTNESS:

                break;
        }
        red = ( ( rgb >> 16 ) & 0xFF ) / 255.0f;
        green = ( ( rgb >> 8 ) & 0xFF ) / 255.0f;
        blue = ( rgb & 0xFF ) / 255.0f;
    }
}

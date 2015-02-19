package pattern;

import processing.core.PApplet;

import java.awt.*;

/**
 * Created by mrzl on 15.01.2015.
 */
public class ColorMode {

    private float minHue, maxHue;
    public float red, green, blue;
    private ColorModeEnum colorMode;

    public ColorMode () {
        minHue = maxHue = 0.0f;
    }

    public void setMinHue( float min ) {
        this.minHue = min;
    }

    public void setMaxHue( float max ) {
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

    public void setColorMode ( ColorModeEnum colorMode ) {
        this.colorMode = colorMode;
    }

    public ColorModeEnum getColorMode () {
        return colorMode;
    }

    public float getMinHue() {
        return minHue;
    }

    public float getMaxHue() {
        return maxHue;
    }
}

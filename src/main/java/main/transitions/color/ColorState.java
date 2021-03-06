package main.transitions.color;

/**
 * Created by mrzl on 19.02.2015.
 */
public class ColorState {
    private float minHue, maxHue, saturation, brightness;

    public ColorState () {
    }

    /**
     * The parameters should be put in here in the following ranges:
     * Hue: 0-360
     * Saturation: 0-255
     * Brightness: 0-255
     *
     * @param hue the hue color in degrees
     * @param sat the saturation color in 0 - 255
     * @param bri the brightness colr in 0 - 255
     */
    public ColorState ( float minHue, float maxHue, float sat, float bri ) {
        setHue( minHue, maxHue );
        setSaturation( sat );
        setBrightness( bri );
    }

    public float getMinHue () {
        return minHue;
    }

    public float getMaxHue() {
        return maxHue;
    }

    /**
     * Sets the hue.
     *
     * @param hue the hue parameter in the range of 0-360°
     * @return ColorState the instance of this class
     */
    public ColorState setHue ( float minHue, float maxHue ) {
        checkInputHue( minHue );
        checkInputHue( maxHue );
        this.minHue = getNormalizedHue( minHue );
        this.maxHue = getNormalizedHue( maxHue );
        return this;
    }

    public ColorState setNormalizedHue( float minHue, float maxHue ) {
        this.minHue = minHue;
        this.maxHue = maxHue;
        return this;
    }

    public float getSaturation () {
        return saturation;
    }

    /**
     * Sets the saturation.
     *
     * @param saturation the saturation parameter in the range of 0-255
     * @return ColorState the instance of this class
     */
    public ColorState setSaturation ( float saturation ) {
        checkInputSaturation( saturation );
        this.saturation = getNormalizedSatBr( saturation );
        return this;
    }

    public float getBrightness () {
        return brightness;
    }

    /**
     * Sets the brightness.
     *
     * @param brightness the brightness parameter in the range of 0-255
     * @return ColorState the instance of this class
     */
    public ColorState setBrightness ( float brightness ) {
        checkInputBrightness( brightness );
        this.brightness = getNormalizedSatBr( brightness );
        return this;
    }

    private float getNormalizedHue( float range0360 ) {
        return range0360 / 360.0f;
    }

    private float getNormalizedSatBr( float range0255 ) {
        return range0255 / 100.0f;
    }

    private void checkInputHue( float _h ){
        if( _h < 0.0f || _h > 360.0 ) {
            try {
                throw new ColorInputRangeException( "The passed Hue range exceeded the limit: " + _h );
            } catch ( ColorInputRangeException e ) {
                //e.printStackTrace( );
            }
        }
    }

    private void checkInputBrightness( float _b ){
        if( _b < 0.0f || _b > 100.0 ) {
            try {
                throw new ColorInputRangeException( "The passed Brightness/Saturation range exceeded the limit: " + _b );
            } catch ( ColorInputRangeException e ) {
                //e.printStackTrace( );
            }
        }
    }

    private void checkInputSaturation( float _s ){
        checkInputBrightness( _s );
    }
}

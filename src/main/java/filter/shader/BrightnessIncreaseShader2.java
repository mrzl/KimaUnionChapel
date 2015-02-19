package filter.shader;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PShader;

import java.io.File;

/**
 * Created by mrzl on 18.02.2015.
 */
public class BrightnessIncreaseShader2 {
    private PShader shader;
    private float brightness, contrast;
    private boolean enabled;

    public BrightnessIncreaseShader2( PApplet p, float w, float h ) {
        shader = p.loadShader( "shader" + File.separator + "brightness_increase2.glsl" );
        this.shader.set( "resolution", (float)(w), (float)(h) );
        setBrightness( 0.0f );
        setContrast( 1.0f );
        setEnabled( false );
    }

    public void apply( PGraphics pg ) {
        shader.set( "brightness", getBrightness() );
        shader.set( "contrast", getContrast() );

        if( isEnabled() ) {
            pg.beginDraw( );
            pg.filter( shader );
            pg.endDraw( );
        }
    }

    public float getBrightness () {
        return brightness;
    }

    public void setBrightness ( float brightness ) {
        this.brightness = brightness;
    }

    public float getContrast () {
        return contrast;
    }

    public void setContrast ( float contrast ) {
        this.contrast = contrast;
    }

    public boolean isEnabled () {
        return enabled;
    }

    public void setEnabled ( boolean enabled ) {
        this.enabled = enabled;
    }
}

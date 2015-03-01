package filter.shader;

import pattern.ChladniSurface;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PShader;

import java.io.File;

/**
 * Created by mrzl on 17.02.2015.
 */
public class OpacityToHueShader {
    private PShader shader;
    private boolean enabled;
    private float minHue, maxHue;

    public OpacityToHueShader( PApplet p ) {
        this.shader = p.loadShader( "shader" + File.separator + "opacity_to_hue_range.glsl" );
        setEnabled( false );
        setMinHue( 0.0f );
        setMaxHue( 0.5f );
    }

    public void apply ( PGraphics pg, ChladniSurface surf ) {
        shader.set( "minHue", getMinHue( ) );
        shader.set( "maxHue", getMaxHue( ) );
        shader.set( "saturation", surf.getSaturation() );
        this.shader.set( "resolution", ( float )( pg.width ), ( float )( pg.height ) );

        if( isEnabled() ) {
            pg.beginDraw( );
            pg.filter( shader );
            pg.endDraw( );
        }
    }

    public void setMinHue( float _minHue ) {
        this.minHue = _minHue;
    }

    public void setMaxHue( float _maxHue ) {
        this.maxHue = _maxHue;
    }

    public float getMaxHue () {
        return maxHue;
    }

    public float getMinHue () {
        return minHue;
    }

    public boolean isEnabled () {
        return enabled;
    }

    public void setEnabled ( boolean enabled ) {
        this.enabled = enabled;
    }
}

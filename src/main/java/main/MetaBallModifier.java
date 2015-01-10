package main;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PShader;

import java.io.File;

/**
 * Created by mar on 27.12.14.
 */
public class MetaBallModifier {

    private PShader blur, threshold;
    private PApplet p;

    private int blurStrength;
    private float thresholdValue;
    private boolean enabled;

    public MetaBallModifier( PApplet p ) {
        this.p = p;
        this.blur = p.loadShader( "shader" + File.separator + "blur.glsl" );
        this.threshold = p.loadShader( "shader" + File.separator + "threshold.glsl" );

        setBlurStrength( 2 );
        setThreshold( 0.1f );

        setEnabled( false );
    }

    public void apply( PGraphics buffer ) {
        if( isEnabled() ) {
            threshold.set( "threshold", thresholdValue );

            for ( int i = 0; i < blurStrength; i++ ) {
                buffer.filter( blur );
            }

            buffer.filter( threshold );
            buffer.filter( blur );
        }
    }

    public void applyBlur( PGraphics buffer ) {
        buffer.beginDraw();
        threshold.set( "threshold", thresholdValue );
        buffer.filter( blur );
        buffer.endDraw();
    }

    public void setBlurStrength( int _blurStrength ) {
        this.blurStrength = _blurStrength;
    }

    public void setThreshold( float _threshold ) {
        this.thresholdValue = _threshold;
    }

    public void setEnabled( boolean _enabled ) {
        this.enabled = _enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}

package filter.shader;

import pattern.ChladniSurface;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PShader;

import java.io.File;

/**
 * Created by mrzl on 02.03.2015.
 */
public class DirectionalBlur {
    PApplet p;
    private PShader shader;
    private boolean enabled;
    private float blur_x, blur_y, gain, iterations;

    public DirectionalBlur( PApplet p ) {
        this.p = p;
        this.shader = p.loadShader( "shader" + File.separator + "directional_blur.glsl" );
        setEnabled( true );
    }

    public void apply ( PGraphics pg ) {
        setBlur_x( p.map( p.mouseX, 0, p.width, 0, 10 ) );
        setBlur_y( p.map( p.mouseY, 0, p.height, 0, 10 ) );
        setGain( 1.0f );
        setIterations( 3.0f );
        shader.set( "blur_x", getBlur_x( ) );
        shader.set( "blur_y", getBlur_y() );
        shader.set( "gain", getGain() );
        shader.set( "iterations", getIterations() );
        this.shader.set( "resolution", ( float ) ( pg.width ), ( float ) ( pg.height ) );

        if( isEnabled() ) {
            pg.beginDraw( );
            pg.filter( shader );
            pg.endDraw( );
        }
    }

    public boolean isEnabled () {
        return enabled;
    }

    public void setEnabled ( boolean enabled ) {
        this.enabled = enabled;
    }

    public float getIterations () {
        return iterations;
    }

    public void setIterations ( float iterations ) {
        this.iterations = iterations;
    }

    public float getGain () {
        return gain;
    }

    public void setGain ( float gain ) {
        this.gain = gain;
    }

    public float getBlur_y () {
        return blur_y;
    }

    public void setBlur_y ( float blur_y ) {
        this.blur_y = blur_y;
    }

    public float getBlur_x () {
        return blur_x;
    }

    public void setBlur_x ( float blur_x ) {
        this.blur_x = blur_x;
    }
}

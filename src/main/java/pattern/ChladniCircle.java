package pattern;

import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;

/**
 * Created by mar on 13.12.14.
 */
public class ChladniCircle extends ChladniSurface {

    private PImage triangleMask;

    public ChladniCircle( PApplet p, int width, int height ) {
        super( p, width, height );

        this.shader = p.loadShader( "shader" + File.separator + "chladni_circle.glsl" );
        this.shader.set( "resolution", getWidth(), getHeight() );

        setPoles( 21 );
        setScale( 1.2f );
        setN( 3.0f );
        setM( 2.0f );

        this.triangleMask = p.loadImage( "media" + File.separator + "triangle.png" );
        this.triangleMask.resize( ( int ) getWidth( ), ( int ) getHeight( ) );
    }

    public void update() {
        this.shader.set( "m", getM() );
        this.shader.set( "n", getN() );
        System.out.println( "Setting scale to " + getScale() );
        this.shader.set( "scale", getScale() );
        this.shader.set( "poles", getPoles() );

        getBuffer().beginDraw();
        getBuffer().background( 255 );

        getBuffer().shader( this.shader );
        getBuffer().rect( 0, 0, getWidth(), getHeight() );

        getBuffer().mask( triangleMask );
        getBuffer().endDraw();
    }

    public PImage getMastk() {
        return triangleMask;
    }
}
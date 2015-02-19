package pattern;

import main.Main;
import processing.core.PImage;

import java.io.File;

/**
 * Created by mar on 13.12.14.
 */
public class ChladniTriangle extends ChladniSurface {

    private PImage triangleMask;

    public ChladniTriangle ( Main p, int width, int height ) {
        super( p, width, height );

        this.shader = p.loadShader( "shader" + File.separator + "chladni_circle.glsl" );
        this.shader.set( "resolution", getWidth(), getHeight() );

        setPoles( 21 );
        setScale( 1.2f );
        setN( 3.0f );
        setM( 2.0f );
        setMinHue( 0.0f );
        setMaxHue( 0.4f );
        setDrawMonochrome( false );
        setIntensity( 1.0f );
        setSaturation( 1.0f );

        this.triangleMask = p.loadImage( "media" + File.separator + "triangle.png" );
        this.triangleMask.resize( ( int ) getWidth( ), ( int ) getHeight( ) );

        this.formId = Main.ChladniFormId.TRIANGLE1;
    }

    public void update() {
        this.shader.set( "m", getM( ) );
        this.shader.set( "n", getN( ) );
        this.shader.set( "scale", getScale( ) );
        this.shader.set( "poles", getPoles( ) );
        this.shader.set( "minHue", getMinHue() );
        this.shader.set( "maxHue", getMaxHue() );
        this.shader.set( "drawMonochrome", isDrawMonochrome() );
        this.shader.set( "intensity", getIntensity() );
        this.shader.set( "saturation", getSaturation() );

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
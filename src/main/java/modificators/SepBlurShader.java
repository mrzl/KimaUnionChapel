package modificators;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PShader;

import java.io.File;

/**
 * Created by mrzl on 09.01.2015.
 */
public class SepBlurShader {

    private PShader blur;

    private int blurSize;
    private float sigma;

    /**
     * TODO: Implement this and use it in the MetaBallModifier and the GlowModifier
     * @param p
     */
    public SepBlurShader ( PApplet p ) {
        this.blur = p.loadShader( "shader" + File.separator + "sepblur.glsl" );
        setBlurSize( 50 );
        setSigma( 20.0f );
    }

    public void apply( PGraphics pg ) {
        blur.set( "sigma", getSigma( ) );
        blur.set( "blurSize", getBlurSize( ) );
        blur.set( "horizontalPass", 0 );
        pg.beginDraw( );
        pg.shader( blur );
        pg.image( pg, 0, 0 );
        pg.endDraw( );

        blur.set( "horizontalPass", 1 );
        pg.beginDraw( );
        pg.shader( blur );
        pg.image( pg, 0, 0 );
        pg.endDraw( );
    }


    public int getBlurSize () {
        return blurSize;
    }

    public void setBlurSize ( int blurSize ) {
        this.blurSize = blurSize;
    }

    public float getSigma () {
        return sigma;
    }

    public void setSigma ( float sigma ) {
        this.sigma = sigma;
    }
}

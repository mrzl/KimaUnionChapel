package pattern;

import main.Main;
import main.MathUtils;
import processing.core.PApplet;

import java.io.File;

/**
 * Created by mar on 31.12.14.
 */
public class ChladniCircle extends ChladniSurface {

    public ChladniCircle ( Main p, int width, int height ) {
        super( p, width, height );

        this.shader = p.loadShader( "shader" + File.separator + "chladni_real_circle.glsl" );
        this.shader.set( "resolution", getWidth(), getHeight() );

        setM( 10.0f );
        setN( 2.0f );
    }

    public void update() {
        int nthZero = ( int ) MathUtils.getNthZeroOfMthBessel( ( int ) ( getM( ) ), getN( ) );
        this.shader.set( "nthZero", nthZero );
        this.shader.set( "m", (int)(getM()) );

        getBuffer().beginDraw();
        getBuffer().background( 0 );

        getBuffer().shader( this.shader );
        getBuffer().rect( 0, 0, getWidth(), getHeight() );

        getBuffer().endDraw();
    }
}

package main;

import processing.core.PApplet;

import java.io.File;

/**
 * Created by mar on 31.12.14.
 */
public class ChladniRealCircle extends ChladniSurface {
    public ChladniRealCircle( PApplet p, int width, int height ) {
        super( p, width, height );

        this.shader = p.loadShader( "shader" + File.separator + "chladni_real_circle.glsl" );
        this.shader.set( "resolution", getWidth(), getHeight() );

    }

    public void update() {
        int m = ( int ) p.map( p.mouseX, 0, p.width, 1, 16 );
        int n = ( int ) p.map( p.mouseY, 0, p.height, 1, 16 );
        float nthZero = ( float ) MathUtils.getNthZeroOfMthBessel( m, n );
        this.shader.set( "nthZero", nthZero );
        this.shader.set( "m", m );
        this.shader.set( "n", n );

        getBuffer().beginDraw();
        getBuffer().background( 255 );

        getBuffer().shader( this.shader );
        getBuffer().rect( 0, 0, getWidth(), getHeight() );

        getBuffer().endDraw();
    }
}

package main;

import processing.core.PApplet;

import java.io.File;

/**
 * Created by mar on 31.12.14.
 */
public class ChladniRealCircle extends ChladniSurface {

    private float m;
    private float n;

    public ChladniRealCircle( PApplet p, int width, int height ) {
        super( p, width, height );

        this.shader = p.loadShader( "shader" + File.separator + "chladni_real_circle.glsl" );
        this.shader.set( "resolution", getWidth(), getHeight() );

        setM( 10.0f );
        setN( 2.0f );
    }

    public void update() {
        float nthZero = ( float ) MathUtils.getNthZeroOfMthBessel( ( int )( m ), n );
        this.shader.set( "nthZero", nthZero );
        this.shader.set( "m", m );
        this.shader.set( "n", n );

        getBuffer().beginDraw();
        getBuffer().background( 255 );

        getBuffer().shader( this.shader );
        getBuffer().rect( 0, 0, getWidth(), getHeight() );

        getBuffer().endDraw();
    }

    public void setN( float n ) {
        this.n = n;
    }

    public void setM( float m ) {
        this.m = m;
    }

    public float getM() {
        return this.m;
    }

    public float getN() {
        return this.n;
    }

}

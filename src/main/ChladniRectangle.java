package main;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PShader;

import java.io.File;

/**
 * Created by mar on 13.12.14.
 */
public class ChladniRectangle extends ChladniSurface{
    private PShader ps;

    private float m, n, l, epsiolon;

    public ChladniRectangle( PApplet p, int width, int height, String renderer ) {
        super( p, width, height, renderer );

        ps = p.loadShader( "shader" + File.separator + "chladni_rect.glsl" );
        ps.set( "resolution", ( float )( width ), ( float )( height ) );

        m = 10.0f;
        n = 2.0f;
        l = 300.0f;
        epsiolon = 0.05f;
    }

    public void update() {
        ps.set( "n", this.n );
        ps.set( "m", this.m );
        ps.set( "epsilon", this.epsiolon );
        ps.set( "resolution", ( float )( offscreen.width ), ( float )( offscreen.height ) );

        offscreen.beginDraw();
        offscreen.background( 255 );

        offscreen.shader( ps );
        offscreen.rect(0, 0, offscreen.width, offscreen.height);

        offscreen.endDraw();
    }

    public void setM( float m ) {
        this.m = m;
    }

    public void setN( float n ) {
        this.n = n;
    }

    public void setEpsilon( float epsilon ) {
        this.epsiolon = epsilon;
    }



}

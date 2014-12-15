package main;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.opengl.PShader;

import java.io.File;

/**
 * Created by mar on 13.12.14.
 */
public class ChladniCircle extends ChladniSurface{

    private float r, m, n, c1, c2;
    private PImage circleMask;

    public ChladniCircle( PApplet p, int width, int height, String renderer, float r ) {
        super( p, width, height, renderer );
        this.r = r;

        ps = p.loadShader( "shader" + File.separator + "chladni_circle.glsl" );
        ps.set( "resolution", ( float )( width ), ( float )( height ) );

        this.n = 3.0f;
        this.m = 2.0f;
        this.c1 = 1.0f;
        this.c2 = 2.0f;

        circleMask = p.loadImage( "media" + File.separator + "circle.png" );
        circleMask.resize( (int)getWidth(), (int)getHeight() );
    }

    public void update() {
        ps.set( "mouse", (float)( p.mouseX ), (float)( p.mouseY ) );
        //ps.set( "time", ( float ) (p.millis() / 1000.0) );
        ps.set( "scale", 1.0f );
        ps.set( "poles", 21 );

        offscreen.beginDraw();
        offscreen.background( 255 );

        offscreen.shader( ps );
        offscreen.rect(0, 0, offscreen.width, offscreen.height);

        offscreen.mask( circleMask );
        offscreen.endDraw();
    }

    public void setM( float m ) {
        this.m = m;
    }

    public void setN( float n ) {
        this.n = n;
    }

    public void setC1( float c1 ) {
        this.c1 = c1;
    }

    public void setC2( float c2 ) {
        this.c2 = c2;
    }
}
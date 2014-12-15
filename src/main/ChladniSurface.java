package main;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PShader;

/**
 * Created by mar on 14.12.14.
 */
public class ChladniSurface {

    protected PApplet p;
    protected PGraphics offscreen;
    protected PShader ps;

    public ChladniSurface( PApplet p, int width, int height, String renderer ) {
        this.p = p;
        offscreen = p.createGraphics( width, height, renderer );
    }

    public void draw( int x, int y, int w, int h ) {
        p.image( offscreen, x, y, w, h );
    }

    public void update() {

    }

    public void setN( float _n ) {
    }

    public void setM( float _m ) {

    }

    public int get( int x, int y ) {
        return offscreen.pixels[ x + y * offscreen.width ] & 0xFF;
    }

    public void loadPixels() {
        offscreen.loadPixels();
    }

    public float getWidth() {
        return offscreen.width;
    }

    public float getHeight() {
        return offscreen.height;
    }

    public PGraphics getBuffer() {
        return offscreen;
    }
}

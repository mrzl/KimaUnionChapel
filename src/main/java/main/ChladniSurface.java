package main;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.opengl.PShader;

/**
 * Created by mar on 14.12.14.
 */
public class ChladniSurface {
    protected PApplet p;
    protected PGraphics offscreen;
    protected PShader shader;

    public ChladniSurface( PApplet p, int width, int height ) {
        this.p = p;
        offscreen = p.createGraphics( width, height, PConstants.P3D );
    }

    public void draw( int x, int y, int w, int h ) {
        p.image( getBuffer(), x, y, w, h );
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

    public void update() {

    }
}

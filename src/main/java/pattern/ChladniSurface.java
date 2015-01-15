package pattern;

import main.ControlFrame;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.opengl.PShader;

/**
 * Created by mar on 14.12.14.
 */
public class ChladniSurface implements ChladniSurfaceInterface {
    protected PApplet p;
    protected PGraphics offscreen;
    protected PShader shader;
    private float n, m;
    private int poles;
    private float scale;

    public ChladniSurface ( PApplet p, int width, int height ) {
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

    @Override
    public void update () {
        System.err.println( "Calling ChladniSurface.update() without extending it." );
    }

    public void setM( float m ) {
        this.m = m;
    }

    public float getM() {
        return this.m;
    }

    public void setN ( float n ) {
        this.n = n;
    }

    public float getN () {
        return n;
    }

    public void setPoles ( int poles ) {
        this.poles = poles;
    }

    public int getPoles () {
        return poles;
    }

    public void setScale ( float scale ) {
        this.scale = scale;
    }

    public float getScale () {
        return scale;
    }
}

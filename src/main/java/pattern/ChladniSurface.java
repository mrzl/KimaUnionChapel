package pattern;

import main.Main;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.opengl.PShader;

/**
 * Created by mar on 14.12.14.
 */
public class ChladniSurface implements ChladniSurfaceInterface {
    protected Main p;
    protected PGraphics offscreen;
    protected PShader shader;
    private float n, m;
    private int poles;
    private float scale;
    private boolean drawMonochrome;
    private float minHue;
    private float maxHue;
    private float intensity;
    protected Main.ChladniFormId formId;

    public ChladniSurface ( Main p, int width, int height ) {
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

    public void setBuffer( PGraphics pg ) {
        this.offscreen = pg;
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

    public float getMinHue () {
        return minHue;
    }

    public void setMinHue ( float minHue ) {
        this.minHue = minHue;
    }

    public float getMaxHue () {
        return maxHue;
    }

    public void setMaxHue ( float maxHue ) {
        this.maxHue = maxHue;
    }

    public boolean isDrawMonochrome () {
        return drawMonochrome;
    }

    public void setDrawMonochrome ( boolean drawMonochrome ) {
        this.drawMonochrome = drawMonochrome;
    }

    public float getIntensity () {
        return intensity;
    }

    public void setIntensity ( float intensity ) {
        this.intensity = intensity;
    }

    public Main.ChladniFormId getFormId() {
        return this.formId;
    }
}

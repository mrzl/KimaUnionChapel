package modificators;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PShader;

import java.io.File;

/**
 * Created by mrzl on 10.01.2015.
 */
public class ThresholdShader {

    private PShader shader;
    private float threshold;
    private float hue;

    public ThresholdShader( PApplet p ) {
        this.shader = p.loadShader( "shader" + File.separator + "threshold.glsl" );
        setThreshold( 0.5f );
        setHue( 1.0f );
    }

    public void apply( PGraphics pg ) {
        shader.set( "threshold", getThreshold() );
        shader.set( "hue", getHue() );

        pg.beginDraw();
        pg.filter( shader );
        pg.endDraw();
    }

    public float getThreshold () {
        return threshold;
    }

    public void setThreshold ( float threshold ) {
        this.threshold = threshold;
    }

    public PShader getShader () {
        return this.shader;
    }

    public float getHue () {
        return hue;
    }

    public void setHue ( float hue ) {
        this.hue = hue;
    }
}

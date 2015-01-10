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

    public ThresholdShader( PApplet p ) {
        this.shader = p.loadShader( "shader" + File.separator + "threshold.glsl" );
        setThreshold( 0.5f );
    }

    public void apply( PGraphics pg ) {
        shader.set( "threshold", getThreshold() );
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
}

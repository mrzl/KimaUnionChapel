package filter.shader;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PShader;

import java.io.File;

/**
 * Created by mrzl on 16.02.2015.
 */
public class SharpenShader {
    private PShader shader;

    public SharpenShader( PApplet p ) {
        this.shader = p.loadShader( "shader" + File.separator + "sharpen.glsl" );
    }

    public void apply( PGraphics pg ) {
        pg.beginDraw();
        pg.shader( shader );
        pg.endDraw();
    }
}

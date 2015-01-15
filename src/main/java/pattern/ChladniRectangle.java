package pattern;

import processing.core.PApplet;

import java.io.File;

/**
 * Created by mar on 13.12.14.
 */
public class ChladniRectangle extends ChladniSurface {

    public ChladniRectangle( PApplet p, int width, int height ) {
        super( p, width, height );

        this.shader = p.loadShader( "shader" + File.separator + "chladni_rect.glsl" );
        this.shader.set( "resolution", getWidth(), getHeight() );

        setM( 10.0f );
        setN( 2.0f );
    }

    public void update() {
        this.shader.set( "m", getM() );
        this.shader.set( "n", getN() );
        getBuffer().beginDraw();

        getBuffer().shader( this.shader );
        getBuffer().rect( 0, 0, getWidth(), getHeight() );

        getBuffer().endDraw();
    }
}

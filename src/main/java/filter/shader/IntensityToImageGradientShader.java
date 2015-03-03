package filter.shader;

import main.ImageGradient;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PShader;

import java.io.File;

/**
 * Created by mrzl on 02.03.2015.
 */
public class IntensityToImageGradientShader {
    private PShader shader;
    private boolean enabled;
    private ImageGradient imageGradient;

    public IntensityToImageGradientShader( PApplet p, ImageGradient ig ) {
        this.shader = p.loadShader( "shader" + File.separator + "intensity_to_image_gradient.glsl" );
        setEnabled( true );
        this.imageGradient = ig;
    }

    public void apply ( PGraphics pg ) {
        float[][] v = imageGradient.getColors( );
        shader.set( "gradientR", v[0] );
        shader.set( "gradientG", v[1] );
        shader.set( "gradientB", v[2] );
        shader.set( "gradientA", v[3] );
        this.shader.set( "resolution", ( float )( pg.width ), ( float )( pg.height ) );

        if( isEnabled() ) {
            pg.beginDraw( );
            pg.filter( shader );
            pg.endDraw( );
        }
    }

    public boolean isEnabled () {
        return enabled;
    }

    public void setEnabled ( boolean enabled ) {
        this.enabled = enabled;
    }
}

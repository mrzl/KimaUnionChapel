package filter.shader;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PShader;

import java.io.File;

/**
 * Created by mrzl on 16.02.2015.
 */
public class BrightnessIncreaseShader {
    private PShader shader;
    private float amount;

    public BrightnessIncreaseShader( PApplet p, float w, float h ) {
        shader = p.loadShader( "shader" + File.separator + "brightness_increase.glsl" );
        this.shader.set( "resolution", w, h );
        setAmount( 0.1f );
    }

    public void apply( PGraphics pg ) {
        shader.set( "amount", amount );

        pg.beginDraw();
        pg.filter( shader );
        pg.endDraw();
    }

    public void setAmount( float _amount ) {
        this.amount = _amount;
    }

    public float getAmount() {
        return this.amount;
    }
}

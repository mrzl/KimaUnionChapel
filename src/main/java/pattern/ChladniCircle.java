package pattern;

import main.Main;
import main.MathUtils;
import processing.core.PApplet;

import java.io.File;

/**
 * Created by mar on 31.12.14.
 */
public class ChladniCircle extends ChladniSurface {

    public ChladniCircle ( Main p, int width, int height ) {
        super( p, width, height );

        this.shader = p.loadShader( "shader" + File.separator + "chladni_real_circle.glsl" );
        this.shader.set( "resolution", getWidth(), getHeight() );

        setM( 10.0f );
        setN( 2.0f );

        setMinHue( 0.0f );
        setMaxHue( 0.4f );
        setDrawMonochrome( false );
        setIntensity( 1.0f );
        setSaturation( 1.0f );
        setUseGradient( false );

        this.formId = Main.ChladniFormId.CIRCLE1;
    }

    public void update() {
        int nthZero = ( int ) MathUtils.getNthZeroOfMthBessel( ( int ) ( PApplet.abs( getM( ) ) ), PApplet.abs( getN( ) ) );
        this.shader.set( "nthZero", nthZero );
        this.shader.set( "m", getM() );
        this.shader.set( "minHue", getMinHue() );
        this.shader.set( "maxHue", getMaxHue() );
        this.shader.set( "drawMonochrome", isDrawMonochrome() );
        this.shader.set( "intensity", getIntensity() );
        this.shader.set( "saturation", getSaturation() );
        this.shader.set( "cutoff", getCutoff() );

        getBuffer().beginDraw();
        getBuffer().background( 0 );

        getBuffer().shader( this.shader );
        getBuffer().rect( 0, 0, getWidth(), getHeight() );

        getBuffer().endDraw();
    }
}

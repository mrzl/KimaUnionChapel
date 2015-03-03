    package pattern;


import main.Main;

import java.io.File;

/**
 * Created by mar on 13.12.14.
 */
public class ChladniRectangle extends ChladniSurface {

    public ChladniRectangle( Main p, int width, int height ) {
        super( p, width, height );

        this.shader = p.loadShader( "shader" + File.separator + "chladni_rect.glsl" );
        this.shader.set( "resolution", getWidth(), getHeight() );

        setM( 10.0f );
        setN( 2.0f );

        setMinHue( 0.0f );
        setMaxHue( 0.4f );
        setDrawMonochrome( false );
        setIntensity( 1.0f );
        setSaturation( 1.0f );
        setCutoff( 0.0f );

        this.formId = Main.ChladniFormId.RECT1;
    }

    public void update() {
        this.shader.set( "m", getM( ) );
        this.shader.set( "n", getN( ) );
        this.shader.set( "minHue", getMinHue( ) );
        this.shader.set( "maxHue", getMaxHue( ) );
        this.shader.set( "drawMonochrome", isDrawMonochrome( ) );
        this.shader.set( "intensity", getIntensity() );
        this.shader.set( "saturation", getSaturation() );
        this.shader.set( "l", (float)(1.0f) );
        this.shader.set( "cutoff", getCutoff() );

        getBuffer().beginDraw();

        getBuffer().shader( this.shader );
        getBuffer().rect( 0, 0, getWidth(), getHeight() );

        getBuffer( ).endDraw();
    }
}
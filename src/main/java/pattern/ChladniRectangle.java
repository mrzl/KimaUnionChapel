package pattern;


import main.Main;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

import java.io.File;

/**
 * Created by mar on 13.12.14.
 */
public class ChladniRectangle extends ChladniSurface {
    PGraphics gradientBuffer;

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
        //setUseGradient( false );
        PImage gradient = p.loadImage( "media" + File.separator + "gradient2.png" );
        gradientBuffer = p.createGraphics( (int)(getWidth()), (int)(getHeight()), PConstants.P3D );
        gradientBuffer.beginDraw();
        gradientBuffer.image( gradient, 0, 0, gradientBuffer.width, gradientBuffer.height );
        gradientBuffer.endDraw();

        //setGradient( gradientBuffer );
        //this.shader.set( "gradientResolution", (float)(getGradient().width), (float)(getGradient().height) );

        this.formId = Main.ChladniFormId.RECT1;
    }

    public void update() {
        this.shader.set( "m", getM( ) );
        this.shader.set( "n", getN( ) );
        this.shader.set( "minHue", getMinHue( ) );
        this.shader.set( "maxHue", getMaxHue( ) );
        this.shader.set( "drawMonochrome", isDrawMonochrome( ) );
        this.shader.set( "intensity", getIntensity( ) );
        this.shader.set( "saturation", getSaturation( ) );
        this.shader.set( "l", (float)(1.0f) );
        this.shader.set( "cutoff", getCutoff() );
        //this.shader.set( "gradient", getGradient( ) );

        //this.shader.set( "gradientResolution", (float)(getGradient().width), (float)(getGradient().height) );
        //this.shader.set( "useGradient", isUseGradient() );
        getBuffer( ).beginDraw();

        getBuffer().shader( this.shader );
        getBuffer().rect( 0, 0, getWidth(), getHeight() );

        getBuffer( ).endDraw();

        getBuffer().beginDraw();
        //getBuffer().tint( 255, 90 );
        //getBuffer().image( gradientBuffer, 0, 0, getBuffer().width, getBuffer().height );
        getBuffer( ).endDraw();
    }
}
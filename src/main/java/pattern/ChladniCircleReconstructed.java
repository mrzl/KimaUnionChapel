package pattern;

import main.Main;
import modificators.SepBlurShader;
import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Created by mrzl on 06.02.2015.
 */
public class ChladniCircleReconstructed extends ChladniSurface {

    private SepBlurShader blurShader;

    public ChladniCircleReconstructed ( Main p, int width, int height ) {
        super( p, width, height );

        blurShader = new SepBlurShader( p );
        blurShader.setBlurSize( 96 );
        blurShader.setSigma( 9.29f );
        setM( 3.0f );
        setN( 2.0f );
    }

    public void update() {
        getBuffer().beginDraw( );
        getBuffer().pushStyle( );

        getBuffer( ).background( 0 );

        getBuffer().noStroke( );
        if( isDrawMonochrome() ) {
            getBuffer().fill( 255 );
        }else {
            getBuffer().colorMode( PConstants.HSB );
            getBuffer( ).fill( getMinHue( ) * 255, 255, 255 );
        }

        getBuffer().ellipse( getBuffer().width/2, getBuffer().height/2, getBuffer().width, getBuffer().height );

        getBuffer().noFill( );
        getBuffer().stroke( 0 );
        getBuffer().strokeWeight( PApplet.map( getM( ), 0, 10, 20, 5 ) );

        for ( int i = 0; i < getM(); i++ ) {
            float rad = getBuffer().width / getM( ) * i;
            getBuffer( ).ellipse( getBuffer( ).width/2, getBuffer().height/2, rad, rad );
        }

        for( int i = 0; i < getN(); i++ ) {
            float rot = PConstants.TWO_PI / (int)(getN() ) * i;
            getBuffer().pushMatrix( );
            getBuffer().translate( getBuffer( ).width / 2, getBuffer( ).height / 2 );
            getBuffer().rotate( rot );
            getBuffer().line( 0, 0, 0, -getBuffer().width/2 );
            getBuffer().popMatrix();
        }

        getBuffer().popStyle();
        getBuffer().endDraw();

        blurShader.setBlurSize( 96 );
        blurShader.setSigma( PApplet.map( getM(), 0, 10, 10, 2 ) );
        blurShader.apply( getBuffer() );
    }
}
package main;

import codeanticode.syphon.SyphonServer;
import osc.*;
import pattern.ChladniCircle;
import pattern.ChladniPattern;
import pattern.ChladniRealCircle;
import pattern.ChladniRectangle;
import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Created by mar on 13.12.14.
 */
public class Main extends PApplet {

    protected ChladniPattern chladniRect;
    protected ChladniPattern chladniTriangle;
    protected ChladniPattern chladniCircle;

    private SoundController soundController;

    private MetaBallModifier mm;
    private boolean doSyphonOutput;

    private SyphonOutput syphonOutput;
    public ControlFrame controlFrame;

    int resolution;
    float scaleFactor;
    boolean debug = false;

    public void setup() {
        int overallWidth, overallHeight;
        if( debug ) {
            resolution = 256;
            scaleFactor = 2.0f;
            overallWidth = ( int )( resolution * 3 * scaleFactor );
            overallHeight = ( int )( resolution * scaleFactor );
            size( overallWidth, overallHeight, PConstants.P3D );
        } else {
            resolution = 256;
            scaleFactor = 4.0f;
            overallWidth = ( int )( resolution * 3 * scaleFactor );
            overallHeight = ( int )( resolution * scaleFactor );
            size( 1, 1, PConstants.P3D );
        }

        frameRate( 1000 );



        ChladniRectangle rect = new ChladniRectangle( this, resolution, resolution );
        ChladniCircle circle = new ChladniCircle( this, resolution, resolution );
        ChladniRealCircle realCircle = new ChladniRealCircle( this, resolution, resolution );

        chladniRect = new ChladniPattern( this, rect, scaleFactor, 10000 );
        chladniTriangle = new ChladniPattern( this, circle, scaleFactor, 10000 );
        chladniCircle = new ChladniPattern( this, realCircle, scaleFactor, 10000 );

        controlFrame = ControlFrame.addControlFrame( this, "Controls", 400, 800 );

        mm = new MetaBallModifier( this );
        doSyphonOutput = false;

        syphonOutput = new SyphonOutput( this, overallWidth, overallHeight, new SyphonServer( this, "kima_syphon_rectangle" ) );

        // attack = [0,1}
        // frequency = [200,10000]
        // amplitude = [0,0.99]
        this.soundController = new SoundController( this, 5001 );
        SoundParameterMapping mappingRect = new SoundParameterMapping( chladniRect );
        SoundInputParameter soundMapping11 = new SoundInputParameter( SoundInputParameterEnum.AMPLITUDE_PARAMETER1, 0.0f, 0.99f );
        ChladniPatternParameter chladniMapping11 = new ChladniPatternParameter( ChladniPatternParameterEnum.M, 1.0f, 5.0f );
        SoundInputParameter soundMapping21 = new SoundInputParameter( SoundInputParameterEnum.FREQUENCY_PARAMETER1, 200, 10000 );
        ChladniPatternParameter chladniMapping21 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, 1.0f, 5.0f );
        mappingRect.addMapping( soundMapping11, chladniMapping11 );
        mappingRect.addMapping( soundMapping21, chladniMapping21 );
        soundController.addSoundParameterMapping( mappingRect );

        SoundParameterMapping mappingTriangle = new SoundParameterMapping( chladniTriangle );
        SoundInputParameter soundMapping12 = new SoundInputParameter( SoundInputParameterEnum.AMPLITUDE_PARAMETER1, 0.0f, 0.99f );
        ChladniPatternParameter chladniMapping12 = new ChladniPatternParameter( ChladniPatternParameterEnum.SCALE, 1.0f, 2.0f );
        SoundInputParameter soundMapping22 = new SoundInputParameter( SoundInputParameterEnum.FREQUENCY_PARAMETER1, 200, 10000 );
        ChladniPatternParameter chladniMapping22 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, 1.0f, 5.0f );
        mappingTriangle.addMapping( soundMapping12, chladniMapping12 );
        mappingTriangle.addMapping( soundMapping22, chladniMapping22 );
        soundController.addSoundParameterMapping( mappingTriangle );

        SoundParameterMapping mappingCircle = new SoundParameterMapping( chladniCircle );
        SoundInputParameter soundMapping13 = new SoundInputParameter( SoundInputParameterEnum.AMPLITUDE_PARAMETER1, 0.0f, 0.99f );
        ChladniPatternParameter chladniMapping13 = new ChladniPatternParameter( ChladniPatternParameterEnum.M, 1.0f, 30.0f );
        SoundInputParameter soundMapping23 = new SoundInputParameter( SoundInputParameterEnum.FREQUENCY_PARAMETER1, 200.0f, 10000.0f );
        ChladniPatternParameter chladniMapping23 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, 2.0f, 500.0f );
        mappingCircle.addMapping( soundMapping13, chladniMapping13 );
        mappingCircle.addMapping( soundMapping23, chladniMapping23 );
        soundController.addSoundParameterMapping( mappingCircle );

        /*
        SoundParameterMapping mappingTriangle = new SoundParameterMapping( chladniTriangle );
        mappingTriangle.addMapping( SoundInputParameterEnum.AMPLITUDE_PARAMETER, ChladniPatternParameterEnum.M );
        mappingTriangle.addMapping( SoundInputParameterEnum.FREQUENCY_PARAMETER, ChladniPatternParameterEnum.N );
        soundController.addSoundParameterMapping( mappingTriangle );

        SoundParameterMapping mappingCircle = new SoundParameterMapping( chladniCircle );
        mappingCircle.addMapping( SoundInputParameterEnum.AMPLITUDE_PARAMETER, ChladniPatternParameterEnum.M );
        mappingCircle.addMapping( SoundInputParameterEnum.FREQUENCY_PARAMETER, ChladniPatternParameterEnum.N );
        soundController.addSoundParameterMapping( mappingCircle );
        */
    }

    public void draw() {
        //background( 0 );
        if( frameCount % 20 == 0 ) {
            println( frameRate );
        }

        chladniRect.update( 1 );

        //chladniRect.drawOriginal( 0, 0, resolution, resolution );
        chladniRect.renderParticles( );
        //chladniRect.renderParticlesToScreen( 0, 0 );

        chladniTriangle.update( 1 );
        //chladniTriangle.restrictCircular( ( int ) ( chladniTriangle.getSurface().getWidth() / 2 ) );
        //chladniTriangle.drawOriginal( resolution, 0, resolution, resolution );
        chladniTriangle.restrictTriangular( );
        chladniTriangle.renderParticles( );
        //chladniTriangle.renderParticlesToScreen( ( int ) (resolution * chladniRect.getScaleFactor()), 0 );

        chladniCircle.update( 1 );
        //chladniCircle.drawOriginal( resolution * 2, 0, resolution, resolution );
        chladniCircle.restrictCircular( ( int ) ( chladniTriangle.getSurface( ).getWidth( ) * scaleFactor / 2 ) );
        chladniCircle.renderParticles( );
        //chladniCircle.renderParticlesToScreen( ( int ) (resolution * 2 * chladniTriangle.getScaleFactor()), 0 );


        syphonOutput.beginDraw( );
        syphonOutput.getBuffer( ).background( 0 );
        syphonOutput.drawOnTexture( chladniRect.getParticlePBO( ), 0, 0 );
        syphonOutput.drawOnTexture( chladniTriangle.getParticlePBO( ), ( int ) ( resolution * chladniRect.getScaleFactor( ) ), 0 );
        syphonOutput.drawOnTexture( chladniCircle.getParticlePBO( ), ( int ) ( resolution * 2 * chladniTriangle.getScaleFactor( ) ), 0 );
        mm.apply( syphonOutput.getBuffer( ) );
        syphonOutput.endDraw( );

        if( debug ) {
            image( syphonOutput.getBuffer(), 0, 0 );
        }

        if( doSyphonOutput ) {
            syphonOutput.send();
        }
    }

    public void keyPressed() {
        if( key == 's' ) {
            this.g.save( "out.png" );
        }
    }

    public void exit() {
        syphonOutput.stop( );
        super.exit();
    }

    public MetaBallModifier  getMetaBallModifier() {
        return mm;
    }

    public static void main( String[] args ) {
        PApplet.main( new String[]{ "main.Main" } );
    }
}

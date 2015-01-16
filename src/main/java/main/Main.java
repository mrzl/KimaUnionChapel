package main;

import codeanticode.syphon.SyphonServer;
import nano.*;
import osc.*;
import pattern.ChladniTriangle;
import pattern.ChladniParticles;
import pattern.ChladniCircle;
import pattern.ChladniRectangle;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.*;

/**
 * Created by mar on 13.12.14.
 */
public class Main extends PApplet {

    public static final String OSX = "Mac";

    protected enum ChladniFormId {RECT1, TRIANGLE1, CIRCLE1}
    protected HashMap< ChladniFormId, ChladniParticles > chladniForms;

    public SoundController soundController;
    protected NanoKontrolController nanoController;

    private SyphonOutput syphonOutput;
    public ControlFrame controlFrame;

    private int resolution;
    private float scaleFactor;
    private boolean debug = true;

    public void setup () {
        int overallWidth, overallHeight;
        if ( debug ) {
            resolution = 256;
            scaleFactor = 2.0f;
            overallWidth = ( int ) ( resolution * 3 * scaleFactor );
            overallHeight = ( int ) ( resolution * scaleFactor );
            size( overallWidth, overallHeight, PConstants.P3D );
        } else {
            resolution = 256;
            scaleFactor = 4.0f;
            overallWidth = ( int ) ( resolution * 3 * scaleFactor );
            overallHeight = ( int ) ( resolution * scaleFactor );
            size( 1, 1, PConstants.P3D );
        }

        noSmooth( );

        ChladniRectangle rect = new ChladniRectangle( this, resolution, resolution );
        ChladniTriangle circle = new ChladniTriangle( this, resolution, resolution );
        ChladniCircle realCircle = new ChladniCircle( this, resolution, resolution );


        ChladniParticles chladniRect = new ChladniParticles( this, rect, scaleFactor, 10000 );
        ChladniParticles chladniTriangle = new ChladniParticles( this, circle, scaleFactor, 10000 );
        ChladniParticles chladniCircle = new ChladniParticles( this, realCircle, scaleFactor, 10000 );

        chladniForms = new HashMap<>( );
        chladniForms.put( ChladniFormId.RECT1, chladniRect );
        chladniForms.put( ChladniFormId.CIRCLE1, chladniCircle );
        chladniForms.put( ChladniFormId.TRIANGLE1, chladniTriangle );


        controlFrame = ControlFrame.addControlFrame( this, "Controls", 400, 800 );

        syphonOutput = new SyphonOutput( this, overallWidth, overallHeight, new SyphonServer( this, "kima" ) );

        // attack = [0,1}
        // frequency = [200,10000]
        // amplitude = [0,0.99]
        this.soundController = new SoundController( this, 5001 );
        SoundParameterMapping mappingRect = new SoundParameterMapping( chladniRect );
        SoundInputParameter soundMapping11 = new SoundInputParameter( SoundInputParameterEnum.AMPLITUDE_PARAMETER1, 0.0f, 0.99f );
        ChladniPatternParameter chladniMapping11 = new ChladniPatternParameter( ChladniPatternParameterEnum.M, 1.0f, 20.0f );
        SoundInputParameter soundMapping21 = new SoundInputParameter( SoundInputParameterEnum.AMPLITUDE_PARAMETER1, 200, 10000 );
        ChladniPatternParameter chladniMapping21 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, 3.0f, 15.0f );
        mappingRect.addMapping( soundMapping11, chladniMapping11 );
        mappingRect.addMapping( soundMapping21, chladniMapping21 );
        soundController.addSoundParameterMapping( mappingRect );

        SoundParameterMapping mappingTriangle = new SoundParameterMapping( chladniTriangle );
        SoundInputParameter soundMapping12 = new SoundInputParameter( SoundInputParameterEnum.AMPLITUDE_PARAMETER1, 0.0f, 0.99f );
        ChladniPatternParameter chladniMapping12 = new ChladniPatternParameter( ChladniPatternParameterEnum.SCALE, 0.2f, 0.5f );
        SoundInputParameter soundMapping22 = new SoundInputParameter( SoundInputParameterEnum.FREQUENCY_PARAMETER1, 200, 10000 );
        ChladniPatternParameter chladniMapping22 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, 1.0f, 5.0f );
        mappingTriangle.addMapping( soundMapping12, chladniMapping12 );
        mappingTriangle.addMapping( soundMapping22, chladniMapping22 );
        soundController.addSoundParameterMapping( mappingTriangle );

        SoundParameterMapping mappingCircle = new SoundParameterMapping( chladniCircle );
        SoundInputParameter soundMapping13 = new SoundInputParameter( SoundInputParameterEnum.AMPLITUDE_PARAMETER1, 0.0f, 0.99f );
        ChladniPatternParameter chladniMapping13 = new ChladniPatternParameter( ChladniPatternParameterEnum.M, 1.0f, 10 );
        SoundInputParameter soundMapping23 = new SoundInputParameter( SoundInputParameterEnum.FREQUENCY_PARAMETER1, 200.0f, 10000.0f );
        ChladniPatternParameter chladniMapping23 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, 2.0f, 500 );
        mappingCircle.addMapping( soundMapping13, chladniMapping13 );
        mappingCircle.addMapping( soundMapping23, chladniMapping23 );
        soundController.addSoundParameterMapping( mappingCircle );

        nanoController = new NanoKontrolController( this, "nanoKONTROL2" );
        NanoKontrolMapping nanoMapping = new NanoKontrolMapping( chladniRect );
        NanoInputParameter nanoParameter1 = new NanoInputParameter( NanoKontrolSliderEnum.SLIDER_1, 0, 127 );
        VisualParameter visualParamter1 = new VisualParameter( VisualParameterEnum.MIN_HUE, 0, 1 );
        NanoInputParameter nanoParameter2 = new NanoInputParameter( NanoKontrolSliderEnum.SLIDER_2, 0, 127 );
        VisualParameter visualParamter2 = new VisualParameter( VisualParameterEnum.MAX_HUE, 0, 1 );
        NanoInputParameter nanoParameter3 = new NanoInputParameter( NanoKontrolSliderEnum.SLIDER_3, 0, 127 );
        VisualParameter visualParamter3 = new VisualParameter( VisualParameterEnum.UPDATE_DELAY, 0, 2000 );
        nanoMapping.addMapping( nanoParameter1, visualParamter1 );
        nanoMapping.addMapping( nanoParameter2, visualParamter2 );
        nanoMapping.addMapping( nanoParameter3, visualParamter3 );
        nanoController.addMapping( nanoMapping );

        prepareExitHandler( );
    }

    public void draw () {
        if ( frameCount % 40 == 0 ) {
            println( frameRate );
        }

        // draw the surface
        Iterator it = chladniForms.entrySet( ).iterator( );
        while ( it.hasNext( ) ) {
            Map.Entry pairs = ( Map.Entry ) it.next( );
            ChladniParticles p = ( ChladniParticles ) pairs.getValue( );
            p.update( 1 );
        }

        // restrict surfaces
        chladniForms.get( ChladniFormId.TRIANGLE1 ).restrictTriangular( );
        chladniForms.get( ChladniFormId.CIRCLE1 ).restrictCircular( ( int ) ( chladniForms.get( ChladniFormId.TRIANGLE1 ).getSurface( ).getWidth( ) * scaleFactor / 2 ) );

        // draw particles
        it = chladniForms.entrySet( ).iterator( );
        while ( it.hasNext( ) ) {
            Map.Entry pairs = ( Map.Entry ) it.next( );
            ChladniParticles p = ( ChladniParticles ) pairs.getValue( );
            p.render( );
        }

        // draw everything on the syphon buffer
        syphonOutput.beginDraw( );
        syphonOutput.drawOnTexture( chladniForms.get( ChladniFormId.RECT1 ).getParticlePBO( ), 0, 0 );
        syphonOutput.drawOnTexture( chladniForms.get( ChladniFormId.TRIANGLE1 ).getParticlePBO( ), ( int ) ( resolution * chladniForms.get( ChladniFormId.RECT1 ).getScaleFactor( ) ), 0 );
        syphonOutput.drawOnTexture( chladniForms.get( ChladniFormId.CIRCLE1 ).getParticlePBO( ), ( int ) ( resolution * 2 * chladniForms.get( ChladniFormId.TRIANGLE1 ).getScaleFactor( ) ), 0 );
        syphonOutput.endDraw( );

        if ( debug ) {
            image( syphonOutput.getBuffer( ), 0, 0 );
        }

        // send syphon texture
        if ( System.getProperty( "os.name" ).startsWith( OSX ) ) {
            syphonOutput.send( );
        }

        // do anomalies in order to avoid too strong clustering on black areas
        it = chladniForms.entrySet( ).iterator( );
        while ( it.hasNext( ) ) {
            Map.Entry pairs = ( Map.Entry ) it.next( );
            ChladniParticles p = ( ChladniParticles ) pairs.getValue( );
            p.doAnomaly( );
        }
    }

    private void prepareExitHandler () {
        Runtime.getRuntime( ).addShutdownHook( new Thread( new Runnable( ) {
            public void run () {
                controlFrame.saveParameters( );
                System.out.println( "Shutdown successfull" );
            }
        } ) );
    }

    public static void main ( String[] args ) {
        PApplet.main( new String[]{ "main.Main" } );
    }
}

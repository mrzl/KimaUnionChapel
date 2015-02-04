package main;

import codeanticode.syphon.SyphonServer;
import midi.*;
import midi.bcr2000.BnrController;
import midi.nanokontrol.NanoInputParameter;
import midi.nanokontrol.NanoKontrolController;
import midi.nanokontrol.NanoKontrolMapping;
import midi.nanokontrol.NanoKontrolSliderEnum;
import osc.*;
import pattern.*;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.*;

/**
 * Created by mar on 13.12.14.
 */
public class Main extends PApplet {

    public static final String OSX = "Mac";

    public enum ChladniFormId {RECT1, TRIANGLE1, CIRCLE1, HYDROGEN1}
    public HashMap< ChladniFormId, ChladniParticles > chladniForms;

    public SoundController soundController;
    protected NanoKontrolController nanoController;
    protected BnrController bcrController;

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
            overallWidth = ( int ) ( resolution * 4 * scaleFactor );
            overallHeight = ( int ) ( resolution * scaleFactor );
            size( overallWidth, overallHeight, PConstants.P3D );
        } else {
            resolution = 256;
            scaleFactor = 4.0f;
            overallWidth = ( int ) ( resolution * 4 * scaleFactor );
            overallHeight = ( int ) ( resolution * scaleFactor );
            size( 1, 1, PConstants.P3D );
        }

        noSmooth( );

        ChladniRectangle rect = new ChladniRectangle( this, resolution, resolution );
        ChladniTriangle circle = new ChladniTriangle( this, resolution, resolution );
        ChladniCircle realCircle = new ChladniCircle( this, resolution, resolution );
        HydrogenCircle hydrogenCircle = new HydrogenCircle( this, resolution, resolution );


        ChladniParticles chladniRect = new ChladniParticles( this, rect, scaleFactor, 10000 );
        ChladniParticles chladniTriangle = new ChladniParticles( this, circle, scaleFactor, 10000 );
        ChladniParticles chladniCircle = new ChladniParticles( this, realCircle, scaleFactor, 10000 );
        ChladniParticles hydrogenWave = new ChladniParticles( this, hydrogenCircle, scaleFactor, 1000 );


        chladniForms = new HashMap<>( );
        chladniForms.put( ChladniFormId.RECT1, chladniRect );
        chladniForms.put( ChladniFormId.CIRCLE1, chladniCircle );
        chladniForms.put( ChladniFormId.TRIANGLE1, chladniTriangle );
        chladniForms.put( ChladniFormId.HYDROGEN1, hydrogenWave );


        controlFrame = ControlFrame.addControlFrame( this, "Controls", 400, 1000 );

        syphonOutput = new SyphonOutput( this, overallWidth, overallHeight, new SyphonServer( this, "kima" ) );


        this.soundController = new SoundController( this, 5001 );
        SoundParameterMapping mappingRect = new SoundParameterMapping( chladniRect );
        SoundInputParameter soundMapping11 = new SoundInputParameter( SoundInputParameterEnum.AMPLITUDE_PARAMETER1, KimaConstants.MIN_AMPLITUDE, KimaConstants.MAX_AMPLITUDE );
        ChladniPatternParameter chladniMapping11 = new ChladniPatternParameter( ChladniPatternParameterEnum.M, 1.0f, 18.0f );
        SoundInputParameter soundMapping21 = new SoundInputParameter( SoundInputParameterEnum.FREQUENCY_PARAMETER1, KimaConstants.MIN_FREQUENCY, KimaConstants.MAX_FREQUENCY );
        ChladniPatternParameter chladniMapping21 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, 3.0f, 15.0f );
        mappingRect.addMapping( soundMapping11, chladniMapping11 );
        mappingRect.addMapping( soundMapping21, chladniMapping21 );
        soundController.addSoundParameterMapping( mappingRect );

        SoundParameterMapping mappingTriangle = new SoundParameterMapping( chladniTriangle );
        SoundInputParameter soundMapping12 = new SoundInputParameter( SoundInputParameterEnum.AMPLITUDE_PARAMETER2, KimaConstants.MIN_AMPLITUDE, KimaConstants.MAX_AMPLITUDE );
        ChladniPatternParameter chladniMapping12 = new ChladniPatternParameter( ChladniPatternParameterEnum.SCALE, 0.2f, 0.5f );
        SoundInputParameter soundMapping22 = new SoundInputParameter( SoundInputParameterEnum.FREQUENCY_PARAMETER2, KimaConstants.MIN_FREQUENCY, KimaConstants.MAX_FREQUENCY );
        ChladniPatternParameter chladniMapping22 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, 1.0f, 5.0f );
        mappingTriangle.addMapping( soundMapping12, chladniMapping12 );
        mappingTriangle.addMapping( soundMapping22, chladniMapping22 );
        soundController.addSoundParameterMapping( mappingTriangle );

        SoundParameterMapping mappingCircle = new SoundParameterMapping( chladniCircle );
        SoundInputParameter soundMapping13 = new SoundInputParameter( SoundInputParameterEnum.AMPLITUDE_PARAMETER3, KimaConstants.MIN_AMPLITUDE, KimaConstants.MAX_AMPLITUDE );
        ChladniPatternParameter chladniMapping13 = new ChladniPatternParameter( ChladniPatternParameterEnum.M, 1.0f, 10 );
        SoundInputParameter soundMapping23 = new SoundInputParameter( SoundInputParameterEnum.FREQUENCY_PARAMETER3, KimaConstants.MIN_FREQUENCY, KimaConstants.MAX_FREQUENCY );
        ChladniPatternParameter chladniMapping23 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, 2.0f, 10 );
        mappingCircle.addMapping( soundMapping13, chladniMapping13 );
        mappingCircle.addMapping( soundMapping23, chladniMapping23 );
        soundController.addSoundParameterMapping( mappingCircle );

        nanoController = new NanoKontrolController( 0 );
        NanoKontrolMapping nanoMapping = new NanoKontrolMapping( chladniRect );
        NanoInputParameter nanoParameter1 = new NanoInputParameter( NanoKontrolSliderEnum.SLIDER_1, KimaConstants.MIN_MIDI, KimaConstants.MAX_MIDI );
        VisualParameter visualParameter1 = new VisualParameter( VisualParameterEnum.M, 0, 15 );
        NanoInputParameter nanoParameter2 = new NanoInputParameter( NanoKontrolSliderEnum.SLIDER_2, KimaConstants.MIN_MIDI, KimaConstants.MAX_MIDI );
        VisualParameter visualParameter2 = new VisualParameter( VisualParameterEnum.N, 0, 15 );
        NanoInputParameter nanoParameter3 = new NanoInputParameter( NanoKontrolSliderEnum.SLIDER_3, KimaConstants.MIN_MIDI, KimaConstants.MAX_MIDI );
        VisualParameter visualParameter3 = new VisualParameter( VisualParameterEnum.UPDATE_DELAY, 0, 2000 );
        NanoInputParameter nanoParameter4 = new NanoInputParameter( NanoKontrolSliderEnum.SLIDER_4, KimaConstants.MIN_MIDI, KimaConstants.MAX_MIDI );
        VisualParameter visualParameter4 = new VisualParameter( VisualParameterEnum.BACKGROUND_OPACITY, 0, 255 );
        nanoMapping.addMapping( nanoParameter1, visualParameter1 );
        nanoMapping.addMapping( nanoParameter2, visualParameter2 );
        nanoMapping.addMapping( nanoParameter3, visualParameter3 );
        nanoMapping.addMapping( nanoParameter4, visualParameter4 );
        nanoController.addMapping( nanoMapping );

        /*
        NanoKontrolMapping nanoMapping2 = new NanoKontrolMapping(  chladniCircle );
        NanoInputParameter nanoParameter21 = new NanoInputParameter( NanoKontrolSliderEnum.SLIDER_1, 0, 127 );
        VisualParameter visualParameter21 = new VisualParameter( VisualParameterEnum.MIN_HUE, 0, 1 );
        NanoInputParameter nanoParameter22 = new NanoInputParameter( NanoKontrolSliderEnum.SLIDER_2, 0, 127 );
        VisualParameter visualParameter22 = new VisualParameter( VisualParameterEnum.MAX_HUE, 0, 1 );
        NanoInputParameter nanoParameter23 = new NanoInputParameter( NanoKontrolSliderEnum.SLIDER_3, 0, 127 );
        VisualParameter visualParameter23 = new VisualParameter( VisualParameterEnum.UPDATE_DELAY, 0, 2000 );
        NanoInputParameter nanoParameter24 = new NanoInputParameter( NanoKontrolSliderEnum.SLIDER_4, 0, 127 );
        VisualParameter visualParameter24 = new VisualParameter( VisualParameterEnum.BACKGROUND_OPACITY, 0, 255 );
        nanoMapping2.addMapping( nanoParameter21, visualParameter21 );
        nanoMapping2.addMapping( nanoParameter22, visualParameter22 );
        nanoMapping2.addMapping( nanoParameter23, visualParameter23 );
        nanoMapping2.addMapping( nanoParameter24, visualParameter24 );
        nanoController.addMapping( nanoMapping2 );

        */

        /*
        bcrController = new BnrController( 0 );
        BcrMapping bcrMapping1 = new BcrMapping( chladniRect );
        BcrInputParameter bcr1 = new BcrInputParameter( BcrKnobEnum.KNOB_1_1, 0, 127 );
        VisualParameter vp1 = new VisualParameter( VisualParameterEnum.UPDATE_DELAY, 0, 2000 );
        BcrInputParameter bcr2 = new BcrInputParameter( BcrKnobEnum.KNOB_1_2, 0, 127 );
        VisualParameter vp2 = new VisualParameter( VisualParameterEnum.MIN_HUE, 0, 1 );
        BcrInputParameter bcr3 = new BcrInputParameter( BcrKnobEnum.KNOB_1_3, 0, 127 );
        VisualParameter vp3 = new VisualParameter( VisualParameterEnum.MAX_HUE, 0, 1 );
        BcrInputParameter bcr4 = new BcrInputParameter( BcrKnobEnum.KNOB_1_4, 0, 127 );
        VisualParameter vp4 = new VisualParameter( VisualParameterEnum.M, 0, 10 );
        BcrInputParameter bcr5 = new BcrInputParameter( BcrKnobEnum.KNOB_1_5, 0, 127 );
        VisualParameter vp5 = new VisualParameter( VisualParameterEnum.N, 0, 10 );

        bcrMapping1.addMapping( bcr1, vp1 );
        bcrMapping1.addMapping( bcr2, vp2 );
        bcrMapping1.addMapping( bcr3, vp3 );
        bcrMapping1.addMapping( bcr4, vp4 );
        bcrMapping1.addMapping( bcr5, vp5 );
        bcrController.addMapping( bcrMapping1 );

        */

        prepareExitHandler( );
    }

    public void draw () {
        if ( frameCount % 40 == 0 ) {
            if( frame != null ) frame.setTitle( frameRate + "" );
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
        it = chladniForms.entrySet().iterator();
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
        syphonOutput.drawOnTexture( chladniForms.get( ChladniFormId.HYDROGEN1 ).getParticlePBO( ), ( int ) ( resolution * 3 * chladniForms.get( ChladniFormId.CIRCLE1 ).getScaleFactor( ) ), 0 );
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

    public void keyPressed() {
        if( key == 'd' ) {
            chladniForms.get( ChladniFormId.RECT1 ).doDrumHit();
            chladniForms.get( ChladniFormId.CIRCLE1 ).doDrumHit();
            chladniForms.get( ChladniFormId.TRIANGLE1 ).doDrumHit();
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

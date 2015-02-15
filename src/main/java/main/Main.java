package main;

import codeanticode.syphon.SyphonServer;
import midi.*;
import midi.bcr2000.BcrInputParameter;
import midi.bcr2000.BcrKnobEnum;
import midi.bcr2000.BcrMapping;
import midi.bcr2000.BcrController;
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
 * Main class of the Kima Software. Everything is controlled from here.
 *
 * setup(): setup of all objects, controllers, etc
 * draw(): main loop, where everything is updated.
 *
 * main changelog:
 * Created by mar on 13.12.14.
 *
 */
public class Main extends PApplet {

    public static final String OSX = "Mac";

    public enum ChladniFormId {RECT1, TRIANGLE1, CIRCLE1, HYDROGEN1, CIRCLE_RECONSTRUCTION}
    public HashMap< ChladniFormId, ChladniParticles > chladniForms;
    public static final int FORM_COUNT = 3;

    public OscController oscController;
    protected NanoKontrolController nanoController;
    protected BcrController bcrController;

    protected SyphonOutput syphonOutput;
    public ControlFrame controlFrame;

    private int resolution;
    private float scaleFactor;

    public void setup () {
        int overallWidth, overallHeight;
        resolution = 256;
        scaleFactor = 0.5f;
        overallWidth = ( int ) ( resolution * FORM_COUNT * scaleFactor );
        overallHeight = ( int ) ( resolution * scaleFactor );
        size( overallWidth, overallHeight, PConstants.P3D );

        noSmooth( );

        ChladniRectangle rect = new ChladniRectangle( this, resolution, resolution );
        ChladniTriangle circle = new ChladniTriangle( this, resolution, resolution );
        //ChladniCircle realCircle = new ChladniCircle( this, resolution, resolution );
        //HydrogenCircle hydrogenCircle = new HydrogenCircle( this, resolution, resolution );
        ChladniCircleReconstructed fakeCircle = new ChladniCircleReconstructed( this, resolution, resolution );


        ChladniParticles chladniRect = new ChladniParticles( this, rect, scaleFactor, 10000 );
        ChladniParticles chladniTriangle = new ChladniParticles( this, circle, scaleFactor, 10000 );
        //ChladniParticles chladniCircle = new ChladniParticles( this, realCircle, scaleFactor, 10000 );
        //ChladniParticles hydrogenWave = new ChladniParticles( this, hydrogenCircle, scaleFactor, 10000 );
        ChladniParticles chladniCircleReconstruction = new ChladniParticles( this, fakeCircle, scaleFactor, 10000 );


        chladniForms = new HashMap<>();
        chladniForms.put( ChladniFormId.RECT1, chladniRect );
        //chladniForms.put( ChladniFormId.CIRCLE1, chladniCircle );
        chladniForms.put( ChladniFormId.TRIANGLE1, chladniTriangle );
        //chladniForms.put( ChladniFormId.HYDROGEN1, hydrogenWave );
        chladniForms.put( ChladniFormId.CIRCLE_RECONSTRUCTION, chladniCircleReconstruction );

        /* creating control window */
        this.controlFrame = ControlFrame.addControlFrame( this, "Controls", 400, 1000 );

        /* creating syphon output */
        this.syphonOutput = new SyphonOutput( this, overallWidth, overallHeight, new SyphonServer( this, "kima" ) );

        /* creating osc message controller */
        this.oscController = new OscController( this, 5001 );

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

        bcrController = new BcrController( 0 );

        // rect
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

        BcrInputParameter bcr6 = new BcrInputParameter( BcrKnobEnum.BUTTON_1_1, 0, 127 );
        VisualParameter vp6 = new VisualParameter( VisualParameterEnum.MARE_UNDARUM, 0, 2000 );
        BcrInputParameter bcr7 = new BcrInputParameter( BcrKnobEnum.BUTTON_1_2, 0, 127 );
        VisualParameter vp7 = new VisualParameter( VisualParameterEnum.AXIS_MUNDI, 0, 2000 );
        BcrInputParameter bcr8 = new BcrInputParameter( BcrKnobEnum.BUTTON_1_3, 0, 127 );
        VisualParameter vp8 = new VisualParameter( VisualParameterEnum.AURORA, 0, 2000 );

        bcrMapping1.addMapping( bcr1, vp1 );
        bcrMapping1.addMapping( bcr2, vp2 );
        bcrMapping1.addMapping( bcr3, vp3 );
        bcrMapping1.addMapping( bcr4, vp4 );
        bcrMapping1.addMapping( bcr5, vp5 );
        bcrMapping1.addMapping( bcr6, vp6 );
        bcrMapping1.addMapping( bcr7, vp7 );
        bcrMapping1.addMapping( bcr8, vp8 );
        bcrController.addMapping( bcrMapping1 );

        // circle
        BcrMapping bcrMapping2 = new BcrMapping( chladniCircleReconstruction );
        BcrInputParameter bcrc1 = new BcrInputParameter( BcrKnobEnum.BUTTON_1_1, 0, 127 );
        VisualParameter vpc1 = new VisualParameter( VisualParameterEnum.MARE_UNDARUM, 0, 2000 );
        BcrInputParameter bcrc2 = new BcrInputParameter( BcrKnobEnum.BUTTON_1_2, 0, 127 );
        VisualParameter vpc2 = new VisualParameter( VisualParameterEnum.AXIS_MUNDI, 0, 2000 );
        BcrInputParameter bcrc3 = new BcrInputParameter( BcrKnobEnum.BUTTON_1_3, 0, 127 );
        VisualParameter vpc3 = new VisualParameter( VisualParameterEnum.AURORA, 0, 2000 );
        bcrMapping2.addMapping( bcrc1, vpc1 );
        bcrMapping2.addMapping( bcrc2, vpc2 );
        bcrMapping2.addMapping( bcrc3, vpc3 );
        bcrController.addMapping( bcrMapping2 );

        // triangle
        BcrMapping bcrMapping3 = new BcrMapping( chladniTriangle );
        BcrInputParameter bcrt1 = new BcrInputParameter( BcrKnobEnum.BUTTON_1_1, 0, 127 );
        VisualParameter vpt1 = new VisualParameter( VisualParameterEnum.MARE_UNDARUM, 0, 2000 );
        BcrInputParameter bcrt2 = new BcrInputParameter( BcrKnobEnum.BUTTON_1_2, 0, 127 );
        VisualParameter vpt2 = new VisualParameter( VisualParameterEnum.AXIS_MUNDI, 0, 2000 );
        BcrInputParameter bcrt3 = new BcrInputParameter( BcrKnobEnum.BUTTON_1_3, 0, 127 );
        VisualParameter vpt3 = new VisualParameter( VisualParameterEnum.AURORA, 0, 2000 );
        bcrMapping2.addMapping( bcrt1, vpt1 );
        bcrMapping2.addMapping( bcrt2, vpt2 );
        bcrMapping2.addMapping( bcrt3, vpt3 );
        bcrController.addMapping( bcrMapping3 );


        controlFrame.setPattern( chladniRect );
        prepareExitHandler( );
    }

    void addSoundMappingForChapter1Part1() {

        oscController.clear( );

        // PERCUSSION CHANNEL 2
        SoundParameterMapping mappingTriangle = new SoundParameterMapping( this.chladniForms.get( ChladniFormId.RECT1 ) );
        SoundInputParameter soundMapping12 = new SoundInputParameter( SoundInputParameterEnum.PEAK_PARAMETER2, KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX );
        ChladniPatternParameter chladniMapping12 = new ChladniPatternParameter( ChladniPatternParameterEnum.M, KimaConstants.RECTANGLE_M_MIN, KimaConstants.RECTANGLE_M_MAX );
        SoundInputParameter soundMapping32 = new SoundInputParameter( SoundInputParameterEnum.ATTACK_PARAMETER2, KimaConstants.ATTACK_MIN, KimaConstants.ATTACK_MAX );
        ChladniPatternParameter chladniMapping32 = new ChladniPatternParameter( ChladniPatternParameterEnum.DRUM_HIT, 0.0f, 1.0f );
        SoundInputParameter soundMapping22 = new SoundInputParameter( SoundInputParameterEnum.AMPLITUDE_PARAMETER2, KimaConstants.AMPLITUDE_PERCUSSION_MIN, KimaConstants.AMPLITUDE_PERCUSSION_MAX );
        ChladniPatternParameter chladniMapping22 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, KimaConstants.RECTANGLE_N_MIN, KimaConstants.RECTANGLE_N_MAX );
        mappingTriangle.addMapping( soundMapping12, chladniMapping12 );
        mappingTriangle.addMapping( soundMapping22, chladniMapping22 );
        mappingTriangle.addMapping( soundMapping32, chladniMapping32 );
        oscController.addSoundParameterMapping( mappingTriangle );

        // ORGAN CHANNEL 3
        SoundParameterMapping mappingCircle = new SoundParameterMapping( this.chladniForms.get( ChladniFormId.CIRCLE_RECONSTRUCTION ) );
        SoundInputParameter soundMapping13 = new SoundInputParameter( SoundInputParameterEnum.PEAK_PARAMETER3, KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX );
        ChladniPatternParameter chladniMapping13 = new ChladniPatternParameter( ChladniPatternParameterEnum.M, KimaConstants.CIRCLE_RECONSTRUCTION_M_MIN, KimaConstants.CIRCLE_RECONSTRUCTION_M_MAX );
        SoundInputParameter soundMapping23 = new SoundInputParameter( SoundInputParameterEnum.PEAK_PARAMETER3, KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX );
        ChladniPatternParameter chladniMapping23 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, KimaConstants.CIRCLE_RECONSTRUCTION_N_MIN, KimaConstants.CIRCLE_RECONSTRUCTION_N_MAX );
        mappingCircle.addMapping( soundMapping13, chladniMapping13 );
        mappingCircle.addMapping( soundMapping23, chladniMapping23 );
        oscController.addSoundParameterMapping( mappingCircle );
    }

    void addSoundMappingForChapter1Part2() {
        oscController.clear();

        // PERCUSSION CHANNEL 2
        SoundParameterMapping mappingTriangle = new SoundParameterMapping( this.chladniForms.get( ChladniFormId.RECT1 ) );
        SoundInputParameter soundMapping12 = new SoundInputParameter( SoundInputParameterEnum.PEAK_PARAMETER2, KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX );
        ChladniPatternParameter chladniMapping12 = new ChladniPatternParameter( ChladniPatternParameterEnum.M, KimaConstants.RECTANGLE_M_MIN, KimaConstants.RECTANGLE_M_MAX );
        SoundInputParameter soundMapping32 = new SoundInputParameter( SoundInputParameterEnum.ATTACK_PARAMETER2, KimaConstants.ATTACK_MIN, KimaConstants.ATTACK_MAX );
        ChladniPatternParameter chladniMapping32 = new ChladniPatternParameter( ChladniPatternParameterEnum.DRUM_HIT, 0.0f, 1.0f );
        SoundInputParameter soundMapping22 = new SoundInputParameter( SoundInputParameterEnum.AMPLITUDE_PARAMETER2, KimaConstants.AMPLITUDE_PERCUSSION_MIN, KimaConstants.AMPLITUDE_PERCUSSION_MAX );
        ChladniPatternParameter chladniMapping22 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, KimaConstants.RECTANGLE_N_MIN, KimaConstants.RECTANGLE_N_MAX );
        mappingTriangle.addMapping( soundMapping12, chladniMapping12 );
        mappingTriangle.addMapping( soundMapping22, chladniMapping22 );
        mappingTriangle.addMapping( soundMapping32, chladniMapping32 );
        oscController.addSoundParameterMapping( mappingTriangle );

        // ORGAN CHANNEL 3
        SoundParameterMapping mappingCircle = new SoundParameterMapping( this.chladniForms.get( ChladniFormId.CIRCLE_RECONSTRUCTION ) );
        SoundInputParameter soundMapping13 = new SoundInputParameter( SoundInputParameterEnum.PEAK_PARAMETER3, KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX );
        ChladniPatternParameter chladniMapping13 = new ChladniPatternParameter( ChladniPatternParameterEnum.M, KimaConstants.CIRCLE_RECONSTRUCTION_M_MIN, KimaConstants.CIRCLE_RECONSTRUCTION_M_MAX );
        SoundInputParameter soundMapping23 = new SoundInputParameter( SoundInputParameterEnum.PEAK_PARAMETER3, KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX );
        ChladniPatternParameter chladniMapping23 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, KimaConstants.CIRCLE_RECONSTRUCTION_N_MIN, KimaConstants.CIRCLE_RECONSTRUCTION_N_MAX );
        mappingCircle.addMapping( soundMapping13, chladniMapping13 );
        mappingCircle.addMapping( soundMapping23, chladniMapping23 );
        oscController.addSoundParameterMapping( mappingCircle );
    }

    void addSoundMappingForChapter1Part3() {
        oscController.clear();

        // PERCUSSION CHANNEL 2
        SoundParameterMapping mappingTriangle = new SoundParameterMapping( this.chladniForms.get( ChladniFormId.RECT1 ) );
        SoundInputParameter soundMapping12 = new SoundInputParameter( SoundInputParameterEnum.PEAK_PARAMETER2, KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX );
        ChladniPatternParameter chladniMapping12 = new ChladniPatternParameter( ChladniPatternParameterEnum.M, KimaConstants.RECTANGLE_M_MIN, KimaConstants.RECTANGLE_M_MAX );
        SoundInputParameter soundMapping32 = new SoundInputParameter( SoundInputParameterEnum.ATTACK_PARAMETER2, KimaConstants.ATTACK_MIN, KimaConstants.ATTACK_MAX );
        ChladniPatternParameter chladniMapping32 = new ChladniPatternParameter( ChladniPatternParameterEnum.DRUM_HIT, 0.0f, 1.0f );
        SoundInputParameter soundMapping22 = new SoundInputParameter( SoundInputParameterEnum.AMPLITUDE_PARAMETER2, KimaConstants.AMPLITUDE_PERCUSSION_MIN, KimaConstants.AMPLITUDE_PERCUSSION_MAX );
        ChladniPatternParameter chladniMapping22 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, KimaConstants.RECTANGLE_N_MIN, KimaConstants.RECTANGLE_N_MAX );
        mappingTriangle.addMapping( soundMapping12, chladniMapping12 );
        mappingTriangle.addMapping( soundMapping22, chladniMapping22 );
        mappingTriangle.addMapping( soundMapping32, chladniMapping32 );
        oscController.addSoundParameterMapping( mappingTriangle );

        // ORGAN CHANNEL 3
        SoundParameterMapping mappingCircle = new SoundParameterMapping( this.chladniForms.get( ChladniFormId.TRIANGLE1 ) );
        SoundInputParameter soundMapping13 = new SoundInputParameter( SoundInputParameterEnum.PEAK_PARAMETER3, KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX );
        ChladniPatternParameter chladniMapping13 = new ChladniPatternParameter( ChladniPatternParameterEnum.SCALE, KimaConstants.TRIANGLE_SCALES_MIN, KimaConstants.TRIANGLE_SCALES_MAX );
        SoundInputParameter soundMapping33 = new SoundInputParameter( SoundInputParameterEnum.ATTACK_PARAMETER3, KimaConstants.ATTACK_MIN, KimaConstants.ATTACK_MIN );
        ChladniPatternParameter chladniMapping33 = new ChladniPatternParameter( ChladniPatternParameterEnum.DRUM_HIT, 0.0f, 1.0f );
        mappingCircle.addMapping( soundMapping13, chladniMapping13 );
        mappingCircle.addMapping( soundMapping33, chladniMapping33 );
        oscController.addSoundParameterMapping( mappingCircle );
    }

    public void draw () {
        if ( frameCount % 40 == 0 ) {
            if( frame != null ) frame.setTitle( frameRate + "" );
            System.gc();
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
        //chladniForms.get( ChladniFormId.CIRCLE1 ).restrictCircular( ( int ) ( chladniForms.get( ChladniFormId.TRIANGLE1 ).getSurface( ).getWidth( ) * scaleFactor / 2 ) );
        chladniForms.get( ChladniFormId.CIRCLE_RECONSTRUCTION ).restrictCircular( (int)(chladniForms.get( ChladniFormId.CIRCLE_RECONSTRUCTION ).getSurface().getWidth() * scaleFactor / 2) );

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
        //syphonOutput.drawOnTexture( chladniForms.get( ChladniFormId.CIRCLE1 ).getParticlePBO( ), ( int ) ( resolution * 2 * chladniForms.get( ChladniFormId.TRIANGLE1 ).getScaleFactor( ) ), 0 );
        //syphonOutput.drawOnTexture( chladniForms.get( ChladniFormId.HYDROGEN1 ).getParticlePBO( ), ( int ) ( resolution * 3 * chladniForms.get( ChladniFormId.CIRCLE1 ).getScaleFactor( ) ), 0 );
        syphonOutput.drawOnTexture( chladniForms.get( ChladniFormId.CIRCLE_RECONSTRUCTION ).getParticlePBO( ), ( int ) ( resolution * 2 * chladniForms.get( ChladniFormId.TRIANGLE1 ).getScaleFactor( ) ), 0 );
        syphonOutput.endDraw( );


        image( syphonOutput.getBuffer( ), 0, 0 );

        // send syphon texture
        if ( System.getProperty( "os.name" ).startsWith( OSX ) ) {
            syphonOutput.send( );
        }
    }

    public void keyPressed() {
        if( key == 'd' ) {
            controlFrame.selectedParticles.doDrumHit();
        } if ( key == 's') {
            chladniForms.get( ChladniFormId.CIRCLE_RECONSTRUCTION ).getSurface().getBuffer().save( "ext_.png" );
        } if( key == '1') {
            addSoundMappingForChapter1Part1();
        } if( key == '2') {
            addSoundMappingForChapter1Part2( );
        } if( key == '3') {
            addSoundMappingForChapter1Part3( );
        } if( key == '7') {
            controlFrame.setPattern( chladniForms.get( ChladniFormId.RECT1 ) );
        } if( key == '8') {
            controlFrame.setPattern( chladniForms.get( ChladniFormId.TRIANGLE1 ) );
        } if( key == '9') {
            controlFrame.setPattern( chladniForms.get( ChladniFormId.CIRCLE_RECONSTRUCTION ) );
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

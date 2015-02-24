package main;

import codeanticode.syphon.SyphonServer;
import main.transitions.color.ColorState;
import main.transitions.color.ColorTransition;
import main.transitions.TransitionController;
import midi.*;
import midi.bcr2000.BcrInputParameter;
import midi.bcr2000.BcrKnobEnum;
import midi.bcr2000.BcrMapping;
import midi.bcr2000.BcrController;
import midi.nanokontrol.NanoKontrolController;
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
    public TransitionController transitions;

    private int resolution;
    private float scaleFactor;
    private boolean debug = true;

    public void setup () {
        int overallWidth, overallHeight;
        if ( debug ) {
            resolution = 256;
            scaleFactor = 2.0f;
            overallWidth = ( int ) ( resolution * FORM_COUNT * scaleFactor );
            overallHeight = ( int ) ( resolution * scaleFactor );
            size( overallWidth, overallHeight, PConstants.P3D );
        } else {
            resolution = 256;
            scaleFactor = 4.0f;
            overallWidth = ( int ) ( resolution * FORM_COUNT * scaleFactor );
            overallHeight = ( int ) ( resolution * scaleFactor );
            size( 1, 1, PConstants.P3D );
        }

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

        transitions = new TransitionController( this, oscController );

        /*
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
        */

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

        bcrMapping1.addMapping( bcr1, vp1 );
        bcrMapping1.addMapping( bcr2, vp2 );
        bcrMapping1.addMapping( bcr3, vp3 );
        bcrMapping1.addMapping( bcr4, vp4 );
        bcrMapping1.addMapping( bcr5, vp5 );
        addChapterSkip( bcrMapping1 );

        bcrController.addMapping( bcrMapping1 );

        // circle
        BcrMapping bcrMapping2 = new BcrMapping( chladniCircleReconstruction );
        addChapterSkip( bcrMapping2 );
        bcrController.addMapping( bcrMapping2 );

        // triangle
        BcrMapping bcrMapping3 = new BcrMapping( chladniTriangle );
        addChapterSkip( bcrMapping3 );
        bcrController.addMapping( bcrMapping3 );


        controlFrame.setPattern( chladniRect );
        prepareExitHandler( );

        Iterator it = chladniForms.entrySet( ).iterator( );
        while ( it.hasNext( ) ) {
            Map.Entry pairs = ( Map.Entry ) it.next( );
            ChladniParticles p = ( ChladniParticles ) pairs.getValue( );
            p.parameterChangedFromBcrController( VisualParameterEnum.MARE_UNDARUM_1, 0 );
        }
    }

    private void addChapterSkip( BcrMapping _m ) {
        BcrInputParameter pb1 = new BcrInputParameter( BcrKnobEnum.BUTTON_1_1, 0, 127 );
        VisualParameter pv1 = new VisualParameter( VisualParameterEnum.MARE_UNDARUM_1, 0, 2000 );
        BcrInputParameter pb2 = new BcrInputParameter( BcrKnobEnum.BUTTON_1_2, 0, 127 );
        VisualParameter pv2 = new VisualParameter( VisualParameterEnum.MARE_UNDARUM_2, 0, 2000 );
        BcrInputParameter pb3 = new BcrInputParameter( BcrKnobEnum.BUTTON_1_3, 0, 127 );
        VisualParameter pv3 = new VisualParameter( VisualParameterEnum.MARE_UNDARUM_3, 0, 2000 );
        BcrInputParameter pb4 = new BcrInputParameter( BcrKnobEnum.BUTTON_1_4, 0, 127 );
        VisualParameter pv4 = new VisualParameter( VisualParameterEnum.AXIS_MUNDI_1, 0, 2000 );
        BcrInputParameter pb5 = new BcrInputParameter( BcrKnobEnum.BUTTON_1_5, 0, 127 );
        VisualParameter pv5 = new VisualParameter( VisualParameterEnum.AXIS_MUNDI_2, 0, 2000 );
        BcrInputParameter pb6 = new BcrInputParameter( BcrKnobEnum.BUTTON_1_6, 0, 127 );
        VisualParameter pv6 = new VisualParameter( VisualParameterEnum.AXIS_MUNDI_3, 0, 2000 );
        BcrInputParameter pb7 = new BcrInputParameter( BcrKnobEnum.BUTTON_1_7, 0, 127 );
        VisualParameter pv7 = new VisualParameter( VisualParameterEnum.AXIS_MUNDI_4, 0, 2000 );
        BcrInputParameter pb8 = new BcrInputParameter( BcrKnobEnum.BUTTON_1_8, 0, 127 );
        VisualParameter pv8 = new VisualParameter( VisualParameterEnum.AURORA_1, 0, 2000 );
        BcrInputParameter pb9 = new BcrInputParameter( BcrKnobEnum.BUTTON_2_1, 0, 127 );
        VisualParameter pv9 = new VisualParameter( VisualParameterEnum.AURORA_2, 0, 2000 );
        BcrInputParameter pb10 = new BcrInputParameter( BcrKnobEnum.BUTTON_2_2, 0, 127 );
        VisualParameter pv10 = new VisualParameter( VisualParameterEnum.AURORA_3, 0, 2000 );
        BcrInputParameter pb11 = new BcrInputParameter( BcrKnobEnum.BUTTON_2_3, 0, 127 );
        VisualParameter pv11 = new VisualParameter( VisualParameterEnum.AURORA_4, 0, 2000 );
        BcrInputParameter pb12 = new BcrInputParameter( BcrKnobEnum.BUTTON_2_4, 0, 127 );
        VisualParameter pv12 = new VisualParameter( VisualParameterEnum.AURORA_5, 0, 2000 );
        BcrInputParameter pb13 = new BcrInputParameter( BcrKnobEnum.BUTTON_2_5, 0, 127 );
        VisualParameter pv13 = new VisualParameter( VisualParameterEnum.AURORA_6, 0, 2000 );

        _m.addMapping( pb1, pv1 );
        _m.addMapping( pb2, pv2 );
        _m.addMapping( pb3, pv3 );
        _m.addMapping( pb4, pv4 );
        _m.addMapping( pb5, pv5 );
        _m.addMapping( pb6, pv6 );
        _m.addMapping( pb7, pv7 );
        _m.addMapping( pb8, pv8 );
        _m.addMapping( pb9, pv9 );
        _m.addMapping( pb10, pv10 );
        _m.addMapping( pb11, pv11 );
        _m.addMapping( pb12, pv12 );
        _m.addMapping( pb13, pv13 );
    }

    public TransitionController getTransitionController() {
        return transitions;
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

        it = chladniForms.entrySet().iterator();
        while ( it.hasNext( ) ) {
            Map.Entry pairs = ( Map.Entry ) it.next( );
            ChladniParticles p = ( ChladniParticles ) pairs.getValue( );
            p.doAnomaly();
        }
    }

    public void keyPressed() {
        if( key == 'd' ) {
            controlFrame.selectedParticles.doDrumHit( );
        }

        long duration = 1000;
        switch( key ) {
            case '7':
                controlFrame.setPattern( chladniForms.get( ChladniFormId.RECT1 ) );
                break;
            case '8':
                controlFrame.setPattern( chladniForms.get( ChladniFormId.TRIANGLE1 ) );
                break;
            case '9':
                controlFrame.setPattern( chladniForms.get( ChladniFormId.CIRCLE_RECONSTRUCTION ) );
                break;
            case 'q':
                Iterator it = chladniForms.entrySet( ).iterator( );
                while ( it.hasNext( ) ) {
                    Map.Entry pairs = ( Map.Entry ) it.next( );
                    ChladniParticles p = ( ChladniParticles ) pairs.getValue( );
                    p.parameterChangedFromBcrController( VisualParameterEnum.MARE_UNDARUM_1, 0 );
                }
                break;
            case 'w':
                it = chladniForms.entrySet( ).iterator( );
                while ( it.hasNext( ) ) {
                    Map.Entry pairs = ( Map.Entry ) it.next( );
                    ChladniParticles p = ( ChladniParticles ) pairs.getValue( );
                    p.parameterChangedFromBcrController( VisualParameterEnum.MARE_UNDARUM_2, 0 );
                }
                break;
            case 'e':
                it = chladniForms.entrySet( ).iterator( );
                while ( it.hasNext( ) ) {
                    Map.Entry pairs = ( Map.Entry ) it.next( );
                    ChladniParticles p = ( ChladniParticles ) pairs.getValue( );
                    p.parameterChangedFromBcrController( VisualParameterEnum.MARE_UNDARUM_3, 0 );
                }
                break;
            case 'r':
                it = chladniForms.entrySet( ).iterator( );
                while ( it.hasNext( ) ) {
                    Map.Entry pairs = ( Map.Entry ) it.next( );
                    ChladniParticles p = ( ChladniParticles ) pairs.getValue( );
                    p.parameterChangedFromBcrController( VisualParameterEnum.AXIS_MUNDI_1, 0 );
                }
                break;
            case 't':
                it = chladniForms.entrySet( ).iterator( );
                while ( it.hasNext( ) ) {
                    Map.Entry pairs = ( Map.Entry ) it.next( );
                    ChladniParticles p = ( ChladniParticles ) pairs.getValue( );
                    p.parameterChangedFromBcrController( VisualParameterEnum.AXIS_MUNDI_2, 0 );
                }
                break;
            case 'z':
                it = chladniForms.entrySet( ).iterator( );
                while ( it.hasNext( ) ) {
                    Map.Entry pairs = ( Map.Entry ) it.next( );
                    ChladniParticles p = ( ChladniParticles ) pairs.getValue( );
                    p.parameterChangedFromBcrController( VisualParameterEnum.AXIS_MUNDI_3, 0 );
                }
                break;
            case 'u':
                it = chladniForms.entrySet( ).iterator( );
                while ( it.hasNext( ) ) {
                    Map.Entry pairs = ( Map.Entry ) it.next( );
                    ChladniParticles p = ( ChladniParticles ) pairs.getValue( );
                    p.parameterChangedFromBcrController( VisualParameterEnum.AXIS_MUNDI_4, 0 );
                }
                break;
            case 'i':
                it = chladniForms.entrySet( ).iterator( );
                while ( it.hasNext( ) ) {
                    Map.Entry pairs = ( Map.Entry ) it.next( );
                    ChladniParticles p = ( ChladniParticles ) pairs.getValue( );
                    p.parameterChangedFromBcrController( VisualParameterEnum.AURORA_1, 0 );
                }
                break;
            case 'o':
                it = chladniForms.entrySet( ).iterator( );
                while ( it.hasNext( ) ) {
                    Map.Entry pairs = ( Map.Entry ) it.next( );
                    ChladniParticles p = ( ChladniParticles ) pairs.getValue( );
                    p.parameterChangedFromBcrController( VisualParameterEnum.AURORA_2, 0 );
                }
                break;
            case 'p':
                it = chladniForms.entrySet( ).iterator( );
                while ( it.hasNext( ) ) {
                    Map.Entry pairs = ( Map.Entry ) it.next( );
                    ChladniParticles p = ( ChladniParticles ) pairs.getValue( );
                    p.parameterChangedFromBcrController( VisualParameterEnum.AURORA_3, 0 );
                }
                break;
            case 'j':
                it = chladniForms.entrySet( ).iterator( );
                while ( it.hasNext( ) ) {
                    Map.Entry pairs = ( Map.Entry ) it.next( );
                    ChladniParticles p = ( ChladniParticles ) pairs.getValue( );
                    p.parameterChangedFromBcrController( VisualParameterEnum.AURORA_4, 0 );
                }
                break;
            case 'k':
                it = chladniForms.entrySet( ).iterator( );
                while ( it.hasNext( ) ) {
                    Map.Entry pairs = ( Map.Entry ) it.next( );
                    ChladniParticles p = ( ChladniParticles ) pairs.getValue( );
                    p.parameterChangedFromBcrController( VisualParameterEnum.AURORA_5, 0 );
                }
                break;
            case 'l':
                it = chladniForms.entrySet( ).iterator( );
                while ( it.hasNext( ) ) {
                    Map.Entry pairs = ( Map.Entry ) it.next( );
                    ChladniParticles p = ( ChladniParticles ) pairs.getValue( );
                    p.parameterChangedFromBcrController( VisualParameterEnum.AURORA_6, 0 );
                }
                break;
            case 's':
                // color shift inbetween mare undarum chapter 1 and chapter 2
                ColorState colorStateCircleFrom1 = new ColorState().setHue( 211, 211 ).setSaturation( 255 ).setBrightness( 244 );
                ColorState colorStateCircleTo1 = new ColorState().setHue( 54, 54 ).setSaturation( 255 ).setBrightness( 231 );
                ColorTransition transitionCircle1 = new ColorTransition( chladniForms.get( ChladniFormId.CIRCLE_RECONSTRUCTION ), colorStateCircleFrom1, colorStateCircleTo1, duration );
                transitionCircle1.start();

                ColorState rectFrom2 = new ColorState().setHue( 215, 215 ).setSaturation( 128 ).setBrightness( 217 );
                ColorState rectTo2 = new ColorState().setHue( 210, 210 ).setSaturation( 82 ).setBrightness( 205 );
                ColorTransition transitionRect2 = new ColorTransition( chladniForms.get( ChladniFormId.RECT1 ), rectFrom2, rectTo2, duration );
                transitionRect2.start();
                break;
            case ESC:
                key = 0;
                System.exit( 1 );
        }
    }

    private void prepareExitHandler () {
        Runtime.getRuntime( ).addShutdownHook( new Thread( new Runnable( ) {
            public void run () {
                //controlFrame.saveParameters( );
                System.out.println( "Shutdown successfull" );
            }
        } ) );
    }

    public static void main ( String[] args ) {
        PApplet.main( new String[]{ "main.Main" } );
    }
}

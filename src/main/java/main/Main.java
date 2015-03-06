package main;

import codeanticode.syphon.SyphonServer;
import main.transitions.TransitionController;
import midi.*;
import midi.bcr2000.BcrInputParameter;
import midi.bcr2000.BcrKnobEnum;
import midi.bcr2000.BcrMapping;
import midi.bcr2000.BcrController;
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

    public enum ChladniFormId {RECT1, TRIANGLE1, HYDROGEN1, CIRCLE1}
    public HashMap< ChladniFormId, ChladniParticles > chladniForms;
    public static final int FORM_COUNT = 3;

    public OscController oscController;
    protected BcrController bcrController;

    protected SyphonOutput syphonOutput;
    public ControlFrame controlFrame;
    public TransitionController transitions;

    private int resolution;
    private float scaleFactor;
    //public static boolean debug = true;
    public static int RENDER_HEIGHT = 100;
    int overallWidth, overallHeight;

    public void setup () {

        /*
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
            size( 300, 100, PConstants.P3D );
        }
        */

        resolution = 256;
        scaleFactor = 4.0f;
        overallWidth = ( int ) ( resolution * FORM_COUNT * scaleFactor );
        overallHeight = ( int ) ( resolution * scaleFactor );
        size( RENDER_HEIGHT * 3, RENDER_HEIGHT, PConstants.P3D );

        noSmooth( );

        ChladniRectangle rect = new ChladniRectangle( this, resolution, resolution );
        ChladniTriangle circle = new ChladniTriangle( this, resolution, resolution );
        ChladniCircle realCircle = new ChladniCircle( this, resolution, resolution );
        //HydrogenCircle hydrogenCircle = new HydrogenCircle( this, resolution, resolution );
        //ChladniCircleReconstructed fakeCircle = new ChladniCircleReconstructed( this, resolution, resolution );


        ChladniParticles chladniRect = new ChladniParticles( this, rect, scaleFactor, 10000 );
        ChladniParticles chladniTriangle = new ChladniParticles( this, circle, scaleFactor, 10000 );
        ChladniParticles chladniCircle = new ChladniParticles( this, realCircle, scaleFactor, 10000 );
        //ChladniParticles hydrogenWave = new ChladniParticles( this, hydrogenCircle, scaleFactor, 10000 );
        //ChladniParticles chladniCircleReconstruction = new ChladniParticles( this, fakeCircle, scaleFactor, 10000 );


        chladniForms = new HashMap<>();
        chladniForms.put( ChladniFormId.RECT1, chladniRect );
        //chladniForms.put( ChladniFormId.CIRCLE1, chladniCircle );
        chladniForms.put( ChladniFormId.TRIANGLE1, chladniTriangle );
        //chladniForms.put( ChladniFormId.HYDROGEN1, hydrogenWave );
        chladniForms.put( ChladniFormId.CIRCLE1, chladniCircle );

        /* creating control window */
        this.controlFrame = ControlFrame.addControlFrame( this, "Controls", 400, 1000 );

        /* creating syphon output */
        this.syphonOutput = new SyphonOutput( this, overallWidth, overallHeight, new SyphonServer( this, "kima" ) );

        /* creating osc message controller */
        this.oscController = new OscController( this, 5001 );

        transitions = new TransitionController( this, oscController );


        bcrController = new BcrController( 0 );

        addBcrControllerMappingForRect( bcrController );
        addBcrControllerMappingForTriangle( bcrController );
        addBcrControllerMappingForCircle( bcrController );


        // rect
        BcrMapping bcrMapping1 = new BcrMapping( chladniRect );
        addChapterSkip( bcrMapping1 );
        bcrController.addMapping( bcrMapping1 );

        // circle
        BcrMapping bcrMapping2 = new BcrMapping( chladniCircle );
        addChapterSkip( bcrMapping2 );
        bcrController.addMapping( bcrMapping2 );

        // triangle
        BcrMapping bcrMapping3 = new BcrMapping( chladniTriangle );
        addChapterSkip( bcrMapping3 );
        bcrController.addMapping( bcrMapping3 );

        // general controls
        addGeneralBcrControllerMappings( bcrController );

        if( controlFrame != null ) {
            controlFrame.setPattern( chladniRect );
        }

        prepareExitHandler( );

        Iterator it = chladniForms.entrySet( ).iterator( );
        while ( it.hasNext( ) ) {
            Map.Entry pairs = ( Map.Entry ) it.next( );
            ChladniParticles p = ( ChladniParticles ) pairs.getValue( );
            p.parameterChangedFromBcrController( VisualParameterEnum.MARE_UNDARUM_1, 0 );
        }
    }

    void addGeneralBcrControllerMappings( BcrController bcr ) {
        Iterator it = chladniForms.entrySet( ).iterator( );
        while ( it.hasNext( ) ) {
            Map.Entry pairs = ( Map.Entry ) it.next( );
            ChladniParticles p = ( ChladniParticles ) pairs.getValue( );
            BcrMapping mapping = new BcrMapping( p );
            BcrInputParameter bcr1 = new BcrInputParameter( BcrKnobEnum.KNOB_1_1, 0, 127 );
            VisualParameter vp1 = new VisualParameter( VisualParameterEnum.UPDATE_DELAY, 0, 100 );
            BcrInputParameter bcr2 = new BcrInputParameter( BcrKnobEnum.KNOB_1_2, 0, 127 );
            VisualParameter vp2 = new VisualParameter( VisualParameterEnum.INTENSITY, 0, 1 );
            BcrInputParameter bcr3 = new BcrInputParameter( BcrKnobEnum.KNOB_1_3, 0, 127 );
            VisualParameter vp3 = new VisualParameter( VisualParameterEnum.CONTRAST, 0, 3 );
            BcrInputParameter bcr4 = new BcrInputParameter( BcrKnobEnum.KNOB_1_4, 0, 127 );
            VisualParameter vp4 = new VisualParameter( VisualParameterEnum.BRIGHTNESS, 0, 1 );

            mapping.addMapping( bcr1, vp1 );
            mapping.addMapping( bcr2, vp2 );
            mapping.addMapping( bcr3, vp3 );
            mapping.addMapping( bcr4, vp4 );

            bcr.addMapping( mapping );
        }
    }

    private void addBcrControllerMappingForRect(BcrController bcr ) {
        BcrMapping bcrMapping1 = new BcrMapping( chladniForms.get( ChladniFormId.RECT1 ) );
        BcrInputParameter bcr1 = new BcrInputParameter( BcrKnobEnum.KNOB_2_1, 0, 127 );
        VisualParameter vp1 = new VisualParameter( VisualParameterEnum.M, 0, 20 );
        BcrInputParameter bcr2 = new BcrInputParameter( BcrKnobEnum.KNOB_2_2, 0, 127 );
        VisualParameter vp2 = new VisualParameter( VisualParameterEnum.N, 0, 20 );
        BcrInputParameter bcr3 = new BcrInputParameter( BcrKnobEnum.KNOB_2_3, 0, 127 );
        VisualParameter vp3 = new VisualParameter( VisualParameterEnum.MIN_HUE, 0, 1 );
        BcrInputParameter bcr4 = new BcrInputParameter( BcrKnobEnum.KNOB_2_4, 0, 127 );
        VisualParameter vp4 = new VisualParameter( VisualParameterEnum.MAX_HUE, 0, 1 );
        BcrInputParameter bcr5 = new BcrInputParameter( BcrKnobEnum.KNOB_2_5, 0, 127 );
        VisualParameter vp5 = new VisualParameter( VisualParameterEnum.JUMPYNESS, 0, 40 );
        BcrInputParameter bcr6 = new BcrInputParameter( BcrKnobEnum.KNOB_2_6, 0, 127 );
        VisualParameter vp6 = new VisualParameter( VisualParameterEnum.PARTICLE_OPACITY, 0, 1 );
        BcrInputParameter bcr7 = new BcrInputParameter( BcrKnobEnum.KNOB_2_7, 0, 127 );
        VisualParameter vp7 = new VisualParameter( VisualParameterEnum.BLOOM_SIGMA, 0, 80 );
        BcrInputParameter bcr8 = new BcrInputParameter( BcrKnobEnum.KNOB_2_8, 0, 127 );
        VisualParameter vp8 = new VisualParameter( VisualParameterEnum.BLOOM_SIZE, 0, 80 );

        bcrMapping1.addMapping( bcr1, vp1 );
        bcrMapping1.addMapping( bcr2, vp2 );
        bcrMapping1.addMapping( bcr3, vp3 );
        bcrMapping1.addMapping( bcr4, vp4 );
        bcrMapping1.addMapping( bcr5, vp5 );
        bcrMapping1.addMapping( bcr6, vp6 );
        bcrMapping1.addMapping( bcr7, vp7 );
        bcrMapping1.addMapping( bcr8, vp8 );

        bcr.addMapping( bcrMapping1 );
    }

    private void addBcrControllerMappingForTriangle( BcrController bcr ) {
        BcrMapping bcrMapping1 = new BcrMapping( chladniForms.get( ChladniFormId.TRIANGLE1 ) );
        BcrInputParameter bcr1 = new BcrInputParameter( BcrKnobEnum.KNOB_3_1, 0, 127 );
        VisualParameter vp1 = new VisualParameter( VisualParameterEnum.SCALE, 0, 2 );
        // SECOND KNOB HAS NO FUNCTION
        BcrInputParameter bcr3 = new BcrInputParameter( BcrKnobEnum.KNOB_3_3, 0, 127 );
        VisualParameter vp3 = new VisualParameter( VisualParameterEnum.MIN_HUE, 0, 1 );
        BcrInputParameter bcr4 = new BcrInputParameter( BcrKnobEnum.KNOB_3_4, 0, 127 );
        VisualParameter vp4 = new VisualParameter( VisualParameterEnum.MAX_HUE, 0, 1 );
        BcrInputParameter bcr5 = new BcrInputParameter( BcrKnobEnum.KNOB_3_5, 0, 127 );
        VisualParameter vp5 = new VisualParameter( VisualParameterEnum.JUMPYNESS, 0, 40 );
        BcrInputParameter bcr6 = new BcrInputParameter( BcrKnobEnum.KNOB_3_6, 0, 127 );
        VisualParameter vp6 = new VisualParameter( VisualParameterEnum.PARTICLE_OPACITY, 0, 1 );
        BcrInputParameter bcr7 = new BcrInputParameter( BcrKnobEnum.KNOB_3_7, 0, 127 );
        VisualParameter vp7 = new VisualParameter( VisualParameterEnum.BLOOM_SIGMA, 0, 80 );
        BcrInputParameter bcr8 = new BcrInputParameter( BcrKnobEnum.KNOB_3_8, 0, 127 );
        VisualParameter vp8 = new VisualParameter( VisualParameterEnum.BLOOM_SIZE, 0, 80 );

        bcrMapping1.addMapping( bcr1, vp1 );
        //bcrMapping1.addMapping( bcr2, vp2 );
        bcrMapping1.addMapping( bcr3, vp3 );
        bcrMapping1.addMapping( bcr4, vp4 );
        bcrMapping1.addMapping( bcr5, vp5 );
        bcrMapping1.addMapping( bcr6, vp6 );
        bcrMapping1.addMapping( bcr7, vp7 );
        bcrMapping1.addMapping( bcr8, vp8 );

        bcr.addMapping( bcrMapping1 );
    }

    private void addBcrControllerMappingForCircle( BcrController bcr ) {
        BcrMapping bcrMapping1 = new BcrMapping( chladniForms.get( ChladniFormId.CIRCLE1 ) );
        BcrInputParameter bcr1 = new BcrInputParameter( BcrKnobEnum.KNOB_4_1, 0, 127 );
        VisualParameter vp1 = new VisualParameter( VisualParameterEnum.M, 0, 20 );
        BcrInputParameter bcr2 = new BcrInputParameter( BcrKnobEnum.KNOB_4_2, 0, 127 );
        VisualParameter vp2 = new VisualParameter( VisualParameterEnum.N, 0, 20 );
        BcrInputParameter bcr3 = new BcrInputParameter( BcrKnobEnum.KNOB_4_3, 0, 127 );
        VisualParameter vp3 = new VisualParameter( VisualParameterEnum.MIN_HUE, 0, 1 );
        BcrInputParameter bcr4 = new BcrInputParameter( BcrKnobEnum.KNOB_4_4, 0, 127 );
        VisualParameter vp4 = new VisualParameter( VisualParameterEnum.MAX_HUE, 0, 1 );
        BcrInputParameter bcr5 = new BcrInputParameter( BcrKnobEnum.KNOB_4_5, 0, 127 );
        VisualParameter vp5 = new VisualParameter( VisualParameterEnum.JUMPYNESS, 0, 40 );
        BcrInputParameter bcr6 = new BcrInputParameter( BcrKnobEnum.KNOB_4_6, 0, 127 );
        VisualParameter vp6 = new VisualParameter( VisualParameterEnum.PARTICLE_OPACITY, 0, 1 );
        BcrInputParameter bcr7 = new BcrInputParameter( BcrKnobEnum.KNOB_4_7, 0, 127 );
        VisualParameter vp7 = new VisualParameter( VisualParameterEnum.BLOOM_SIGMA, 0, 80 );
        BcrInputParameter bcr8 = new BcrInputParameter( BcrKnobEnum.KNOB_4_8, 0, 127 );
        VisualParameter vp8 = new VisualParameter( VisualParameterEnum.BLOOM_SIZE, 0, 80 );

        bcrMapping1.addMapping( bcr1, vp1 );
        bcrMapping1.addMapping( bcr2, vp2 );
        bcrMapping1.addMapping( bcr3, vp3 );
        bcrMapping1.addMapping( bcr4, vp4 );
        bcrMapping1.addMapping( bcr5, vp5 );
        bcrMapping1.addMapping( bcr6, vp6 );
        bcrMapping1.addMapping( bcr7, vp7 );
        bcrMapping1.addMapping( bcr8, vp8 );

        bcr.addMapping( bcrMapping1 );
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

        BcrInputParameter pb14 = new BcrInputParameter( BcrKnobEnum.KNOB_1_7, 0, 127 );
        VisualParameter pv14 = new VisualParameter( VisualParameterEnum.BLUR_DIRECTION, 0, TWO_PI );
        BcrInputParameter pb15 = new BcrInputParameter( BcrKnobEnum.KNOB_1_8, 0, 127 );
        VisualParameter pv15 = new VisualParameter( VisualParameterEnum.BLUR_STRENGTH, 0, 1 );
        BcrInputParameter pb16 = new BcrInputParameter( BcrKnobEnum.KNOB_1_6, 0, 127 );
        VisualParameter pv16 = new VisualParameter( VisualParameterEnum.CUTOFF, 0, 1 );


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
        _m.addMapping( pb14, pv14 );
        _m.addMapping( pb15, pv15 );
        _m.addMapping( pb16, pv16 );
    }

    public TransitionController getTransitionController() {
        return transitions;
    }

    float auroraDirectionBlurDirection = 0.0f;

    public void draw () {
        frame.setResizable( true );
        //if( debug ) {
        //    frame.setSize( overallWidth + 20, overallHeight + 40 );
       // }
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
            auroraDirectionBlurDirection += 0.005f;
            p.getDirectionalBlur2().setDirection( auroraDirectionBlurDirection );
        }

        // restrict surfaces
        chladniForms.get( ChladniFormId.TRIANGLE1 ).restrictTriangular( );
        //chladniForms.get( ChladniFormId.CIRCLE1 ).restrictCircular( ( int ) ( chladniForms.get( ChladniFormId.TRIANGLE1 ).getSurface( ).getWidth( ) * scaleFactor / 2 ) );
        chladniForms.get( ChladniFormId.CIRCLE1 ).restrictCircular( (int)(chladniForms.get( ChladniFormId.CIRCLE1 ).getSurface().getWidth() * scaleFactor / 2) );

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
        syphonOutput.drawOnTexture( chladniForms.get( ChladniFormId.CIRCLE1 ).getParticlePBO( ), ( int ) ( resolution * 2 * chladniForms.get( ChladniFormId.TRIANGLE1 ).getScaleFactor( ) ), 0 );
        syphonOutput.endDraw( );
/*
        if( !debug ) {
            image( syphonOutput.getBuffer( ), 0, 0, 300, 100 );
        } else {
            image( syphonOutput.getBuffer(), 0, 0, 512 * 3, 512 );
        }
*/

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
        switch( key ) {
            case '7':
                controlFrame.setPattern( chladniForms.get( ChladniFormId.RECT1 ) );
                break;
            case '8':
                controlFrame.setPattern( chladniForms.get( ChladniFormId.TRIANGLE1 ) );
                break;
            case '9':
                controlFrame.setPattern( chladniForms.get( ChladniFormId.CIRCLE1 ) );
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
            case 'g':
                chladniForms.get( ChladniFormId.RECT1 ).setColorModeEnum( ColorModeEnum.GRAYSCALE_MAPPING );
                break;
            case 'n':
                this.syphonOutput.getBuffer().save( "all_" + Utils.timestamp() + ".png" );
                chladniForms.get( ChladniFormId.RECT1 ).getParticlePBO().save( "rect_" + Utils.timestamp() + ".png" );
                chladniForms.get( ChladniFormId.TRIANGLE1 ).getParticlePBO().save( "triangle_" + Utils.timestamp() + ".png" );
                chladniForms.get( ChladniFormId.CIRCLE1 ).getParticlePBO().save( "circle_" + Utils.timestamp() + ".png" );

                break;
            case ESC:
                key = 0;
                System.exit( 1 );
                break;
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
        for( String s : args ) {
            System.out.println( s );
        }
        System.exit( 1 );
        PApplet.main( new String[]{ "main.Main" } );
    }
}

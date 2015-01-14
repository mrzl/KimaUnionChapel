package main;

import codeanticode.syphon.SyphonServer;
import modificators.BloomModifier;
import modificators.MetaBallModifier;
import osc.*;
import pattern.ChladniTriangle;
import pattern.ChladniParticles;
import pattern.ChladniCircle;
import pattern.ChladniRectangle;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

import java.util.Calendar;

/**
 * Created by mar on 13.12.14.
 */
public class Main extends PApplet {

    protected ChladniParticles chladniRect;
    protected ChladniParticles chladniTriangle;
    protected ChladniParticles chladniCircle;

    private SoundController soundController;

    private MetaBallModifier mm;
    private BloomModifier bm;

    private SyphonOutput syphonOutput;
    public ControlFrame controlFrame;

    int resolution;
    float scaleFactor;
    boolean debug = true;
    public boolean drawSurface = true;
    public boolean doMotionBlur = false;
    private boolean doSyphonOutput = false;

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

        noSmooth();

        ChladniRectangle rect = new ChladniRectangle( this, resolution, resolution );
        ChladniTriangle circle = new ChladniTriangle( this, resolution, resolution );
        ChladniCircle realCircle = new ChladniCircle( this, resolution, resolution );

        chladniRect = new ChladniParticles( this, rect, scaleFactor, 10000 );
        chladniTriangle = new ChladniParticles( this, circle, scaleFactor, 10000 );
        chladniCircle = new ChladniParticles( this, realCircle, scaleFactor, 10000 );

        controlFrame = ControlFrame.addControlFrame( this, "Controls", 400, 800 );

        mm = new MetaBallModifier( this );
        bm = new BloomModifier( this );

        syphonOutput = new SyphonOutput( this, overallWidth, overallHeight, new SyphonServer( this, "kima" ) );

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

        prepareExitHandler();
    }

    public void draw () {
        if ( frameCount % 20 == 0 ) {
            println( frameRate );
        }

        // draw the surface
        chladniRect.update( 1 );
        chladniTriangle.update( 1 );
        chladniCircle.update( 1 );

        // restrict surfaces
        chladniTriangle.restrictTriangular( );
        chladniCircle.restrictCircular( ( int ) ( chladniTriangle.getSurface( ).getWidth( ) * scaleFactor / 2 ) );

        // draw particles
        if( !drawSurface ) {
            chladniRect.renderParticles( );
            chladniTriangle.renderParticles( );
            chladniCircle.renderParticles( );
        }

        // draw everything on the syphon buffer
        syphonOutput.beginDraw( );

        if( doMotionBlur ) {
            syphonOutput.getBuffer( ).pushStyle( );
            syphonOutput.getBuffer( ).fill( 0, 40 );
            syphonOutput.getBuffer( ).rect( 0, 0, syphonOutput.getBuffer( ).width, syphonOutput.getBuffer( ).height );
            syphonOutput.getBuffer( ).popStyle( );
        } else {
            syphonOutput.getBuffer().background( 0 );
        }
        //syphonOutput.getBuffer( ).background( 0 );
        if( drawSurface ) {
            syphonOutput.getBuffer().image( chladniRect.getSurface( ).getBuffer( ), 0, 0, resolution * scaleFactor, resolution * scaleFactor );
            syphonOutput.getBuffer().image( chladniTriangle.getSurface( ).getBuffer( ), ( int ) ( resolution * chladniRect.getScaleFactor( ) ), 0, resolution * scaleFactor, resolution * scaleFactor );
            syphonOutput.getBuffer().image( chladniCircle.getSurface( ).getBuffer( ), ( int ) ( resolution * 2 * chladniTriangle.getScaleFactor( ) ), 0, resolution * scaleFactor, resolution * scaleFactor );
        } else {
            syphonOutput.drawOnTexture( chladniRect.getParticlePBO( ), 0, 0 );
            syphonOutput.drawOnTexture( chladniTriangle.getParticlePBO( ), ( int ) ( resolution * chladniRect.getScaleFactor( ) ), 0 );
            syphonOutput.drawOnTexture( chladniCircle.getParticlePBO( ), ( int ) ( resolution * 2 * chladniTriangle.getScaleFactor( ) ), 0 );
            syphonOutput.endDraw( );
        }

        // apply bloom effect
        if ( bm.isEnabled() ) {
            bm.apply( chladniRect.getParticlePBO( ) );
            bm.apply( chladniTriangle.getParticlePBO() );
            bm.apply( chladniCircle.getParticlePBO() );

            syphonOutput.beginDraw( );
            syphonOutput.getBuffer( ).blendMode( PConstants.ADD );

            syphonOutput.drawOnTexture( chladniRect.getParticlePBO( ), 0, 0 );
            syphonOutput.drawOnTexture( chladniTriangle.getParticlePBO( ), ( int ) ( resolution * chladniRect.getScaleFactor( ) ), 0 );
            syphonOutput.drawOnTexture( chladniCircle.getParticlePBO( ), ( int ) ( resolution * 2 * chladniTriangle.getScaleFactor( ) ), 0 );

            syphonOutput.getBuffer().blendMode( PConstants.BLEND );
            syphonOutput.endDraw( );
        }

        // apply threshold fluid effect
        syphonOutput.beginDraw( );
        mm.apply( syphonOutput.getBuffer( ) );
        syphonOutput.endDraw( );


        if ( debug ) {
            image( syphonOutput.getBuffer( ), 0, 0 );
        }

        // send syphon texture
        if ( doSyphonOutput ) {
            syphonOutput.send( );
        }
    }

    public void keyPressed () {
        if ( key == 'r' ) {
            get( 0, 0, (int)(resolution * scaleFactor), (int)(resolution * scaleFactor)).save( "out_rect_"+timestamp()+".png" );
        }
        if ( key == 't' ) {
            save( "out_triangle_"+timestamp()+".png" );
        }
        if ( key == 'c' ) {
            save( "out_circle_"+timestamp()+".png" );
        }
        if( key == 's' ) {
            saveHiRes( 1 );
        }

    }

    public void saveHiRes(int scaleFactor) {
        PGraphics hires = createGraphics(width*scaleFactor, height*scaleFactor, P3D);
        beginRecord(hires);
        hires.scale(scaleFactor);
        draw();
        endRecord();
        hires.save("hires" + timestamp() + ".png");
    }

    String timestamp() {
        Calendar now = Calendar.getInstance();
        return String.format( "%1$ty.%1$tm.%1$td_%1$tH:%1$tM:%1$tS", now );
    }

    public void stop () {
        System.out.println( "exit called" );
        syphonOutput.stop( );
        super.stop();
        //super.exit( );
    }

    private void prepareExitHandler () {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run () {
                controlFrame.saveParameters();
                System.out.println("Shutdown successfull");
            }
        }));
    }

    public MetaBallModifier getMetaBallModifier () {
        return mm;
    }
    public BloomModifier getBloomModifier() { return bm; }

    public static void main ( String[] args ) {
        PApplet.main( new String[]{ "main.Main" } );
    }
}

package main;

import codeanticode.syphon.SyphonServer;
import processing.core.PApplet;
import processing.core.PConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by mar on 13.12.14.
 */
public class Main extends PApplet {

    protected ParticleContainer chladniRect;
    protected ParticleContainer chladniCircle;
    protected ParticleContainer chladniRealCircle;

    private OscRouter oscRouter;

    private MetaBallModifier mm;
    private boolean doSyphonOutput;

    private SyphonOutput syphonOutput;

    int resolution = 512; // 256
    float scaleFactor = 2.0f; // 4.0f
    boolean debug = true;

    public void setup() {
        int overallWidth, overallHeight;
        if( debug ) {
            resolution = 256;
            scaleFactor = 1.0f;
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

        chladniRect = new ParticleContainer( this, rect, scaleFactor, 10000 );
        chladniCircle = new ParticleContainer( this, circle, scaleFactor, 10000 );
        chladniRealCircle = new ParticleContainer( this, realCircle, scaleFactor, 10000 );

        ControlFrame.addControlFrame( this, "Controls", 400, 800 );

        mm = new MetaBallModifier( this );
        doSyphonOutput = false;

        syphonOutput = new SyphonOutput( this, overallWidth, overallHeight, new SyphonServer( this, "kima_syphon_rectangle" ) );

        oscRouter = new OscRouter( 5001 );
        oscRouter.add( OscRouter.AMPLITUDE, "/amplitude1", chladniRect  );
        oscRouter.add( OscRouter.FREQUENCY, "/frequency1", chladniRect  );


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

        chladniCircle.update( 1 );
        //chladniCircle.restrictCircular( ( int ) ( chladniCircle.getSurface().getWidth() / 2 ) );
        //chladniCircle.drawOriginal( resolution, 0, resolution, resolution );
        chladniCircle.restrictTriangular();
        chladniCircle.renderParticles( );
        //chladniCircle.renderParticlesToScreen( ( int ) (resolution * chladniRect.getScaleFactor()), 0 );

        chladniRealCircle.update( 1 );
        //chladniRealCircle.drawOriginal( resolution * 2, 0, resolution, resolution );
        chladniRealCircle.restrictCircular( ( int ) ( chladniCircle.getSurface().getWidth() * scaleFactor / 2 ) );
        chladniRealCircle.renderParticles();
        //chladniRealCircle.renderParticlesToScreen( ( int ) (resolution * 2 * chladniCircle.getScaleFactor()), 0 );


        syphonOutput.beginDraw( );
        syphonOutput.getBuffer( ).background( 0 );
        syphonOutput.drawOnTexture( chladniRect.getParticlePBO( ), 0, 0 );
        syphonOutput.drawOnTexture( chladniCircle.getParticlePBO( ), ( int ) ( resolution * chladniRect.getScaleFactor( ) ), 0 );
        syphonOutput.drawOnTexture( chladniRealCircle.getParticlePBO( ), ( int ) ( resolution * 2 * chladniCircle.getScaleFactor( ) ), 0 );
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

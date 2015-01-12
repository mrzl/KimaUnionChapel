package main;

import codeanticode.syphon.SyphonServer;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

/**
 * Created by mar on 28.12.14.
 */
public class SyphonOutput {

    private SyphonServer server;
    private PGraphics buffer;


    public SyphonOutput( PApplet p, int width, int height, SyphonServer _s ) {
        this.server = _s;
        this.buffer = p.createGraphics( width, height, PConstants.P3D );
    }

    public void beginDraw() {
        this.buffer.beginDraw();
    }

    public void drawOnTexture( PGraphics bufferToDraw, int x, int y ) {
        this.buffer.image( bufferToDraw, x, y );
    }

    public void endDraw() {
        this.buffer.endDraw();
    }

    public void send() {
        this.send( this.buffer );
    }

    private void send( PGraphics frame ) {
        server.sendImage( frame );
    }

    public void stop() {
        if( server != null ) {
            server.stop();
            server.dispose();
        }
    }

    public PGraphics getBuffer() {
        return this.buffer;
    }

    public void saveFrame( String fileName ) {
        this.buffer.save( fileName );
    }
}

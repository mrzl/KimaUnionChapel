package main;

import codeanticode.syphon.SyphonServer;
import processing.core.PGraphics;

/**
 * Created by mar on 28.12.14.
 */
public class SyphonOutput {

    private SyphonServer server;
    public SyphonOutput( SyphonServer _s ) {
        this.server = _s;
    }

    public void send( PGraphics frame ) {
        server.sendImage( frame );
    }

    public void stop() {
        if( server != null ) {
            server.stop();
            server.dispose();
        }
    }
}

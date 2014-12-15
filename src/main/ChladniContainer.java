package main;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.opengl.PGL;
import processing.opengl.PShader;
import toxi.geom.Vec2D;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mar on 14.12.14.
 */
public class ChladniContainer {

    private ChladniSurface surface;
    private ArrayList< Vec2D > particles;
    private PApplet p;
    private PShader ps;
    private PGraphics pg, particlePositions;

    public ChladniContainer( PApplet p, ChladniSurface surface, int particleCount ) {
        this.surface = surface;
        this.p = p;

        pg = p.createGraphics( (int)surface.getWidth(), (int)surface.getHeight(), PConstants.P3D );
        particlePositions = p.createGraphics( (int)surface.getWidth(), (int)surface.getHeight(), PConstants.P3D );

        particles = new ArrayList< Vec2D > ();
        for( int i = 0; i < particleCount; i++ ) {
            particles.add( new Vec2D( p.random( surface.getWidth() ), p.random( surface.getHeight() ) ) );
        }

        ps = p.loadShader( "shader" + File.separator + "particles_simulation.glsl" );
        ps.set( "resolution", surface.getWidth(), surface.getHeight() );
    }

    public void update( int speed ) {
        surface.update();
        surface.loadPixels();
        for( int i = 0; i < speed; i++ ) {
            for ( Vec2D v : particles ) {
                v.x = p.constrain( v.x, 0, surface.getWidth() - 1 );
                v.y = p.constrain( v.y, 0, surface.getHeight() - 1 );
                float jumpyNess = p.map( surface.get( ( int ) ( v.x ), ( int ) ( v.y ) ), 0, 255, 0, 20 );
                v.addSelf( p.random( -jumpyNess, jumpyNess ), p.random( -jumpyNess, jumpyNess ) );
            }
        }

    }

    public void restrictCircular( int radius ) {
        for ( Vec2D v : particles ) {
            float rad = p.dist( v.x, v.y, surface.getWidth() / 2, surface.getHeight() / 2 );
            if( rad > radius ) {
                v.x = p.random( surface.getWidth() );
                v.y = p.random( surface.getHeight() );
            }
        }
    }

    public void drawParticles( int x, int y ) {
        p.noStroke();
        p.fill( 255, 40 );
        for( Vec2D v : particles ) {
            p.ellipse( v.x + x, v.y + y, 4, 4 );
        }
    }

    public void drawOriginal( int x, int y, int w, int h ) {
        this.surface.draw( x, y, w, h );
    }

    public void frequencyChanged() {
        for( Vec2D v : particles ) {
            float jumpyNess = 6.0f;
            v.addSelf( p.random( -jumpyNess, jumpyNess ), p.random( -jumpyNess, jumpyNess ) );
        }
    }

    public ChladniSurface getSurface() {
        return surface;
    }
}

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
public class ParticleContainer {

    private ChladniSurface surface;
    private ArrayList< Vec2D > particles;
    private PApplet p;
    private PShader ps;
    private PGraphics pg, particlePositions;

    public ParticleContainer( PApplet p, ChladniSurface surface, int particleCount ) {
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
/*
        particlePositions.loadPixels();
        float [] xp = new float[ particles.size() ];
        float [] yp = new float[ particles.size() ];
        for( int i = 0; i < particles.size(); i++ ) {
            Vec2D v = particles.get( i );
            int x = ( int ) (v.x / 4.0f);
            int y = ( int ) (v.y / 4.0f);

            int color = p.color( x, y, 0, 0 );
            //xp [i] = v.x;
            //yp [i] = v.y;
            particlePositions.pixels[ i ] = color;
        }
        particlePositions.updatePixels();

        ps.set( "pointData", particlePositions );
        ps.set( "pointcount", particles.size() );
        //ps.set( "points_x", xp );
        //ps.set( "points_y", yp );

        pg.beginDraw();
        pg.background( 0 );

        pg.shader( ps );
        pg.rect(0, 0, pg.width, pg.height);
        pg.endDraw();

        pg.loadPixels();
        for( int i = 0; i < particles.size(); i++ ) {
            int color = pg.pixels[ pg.pixels.length - 1 - i ];
            int r = ( color >> 16 ) & 0xFF;
            int g = (color >> 8 ) & 0xFF;
            particles.get( i ).set( r * 4.0f, g * 4.0f );
            //if( particles.get( i ).x > 0.1f || particles.get( i ).y > 0.1f ) {
            //    System.out.println( "reading back " + i + " of " + particles.size() + " " + particles.get( i ) );
            //}
        }

        //pg.save( "out.png" );
        //p.exit();
        */

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

    public void draw( int x, int y ) {
        //p.pushStyle();
        //p.noStroke();
        p.noStroke();
        p.fill( 255, 40 );
        for( Vec2D v : particles ) {
            //p.point( v.x + x, v.y + y );
            //p.rect( v.x + x, v.y + y, 3, 3 );
            p.ellipse( v.x + x, v.y + y, 4, 4 );
        }
        //p.popStyle();
    }

    public void frequencyChanged() {
        for( Vec2D v : particles ) {
            float jumpyNess = 6.0f;
            v.addSelf( p.random( -jumpyNess, jumpyNess ), p.random( -jumpyNess, jumpyNess ) );
        }
    }
}

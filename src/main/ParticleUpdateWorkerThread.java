package main;

import processing.core.PApplet;
import toxi.geom.Vec2D;

import java.util.ArrayList;

/**
 * Created by mar on 14.12.14.
 */
public class ParticleUpdateWorkerThread extends Thread {
    private ChladniSurface surface;
    private ArrayList< Vec2D > particles;
    private PApplet p;
    private int from, to;

    public ParticleUpdateWorkerThread( PApplet p, ChladniSurface surface, ArrayList< Vec2D > pointsToUpdate, int from, int to ) {
        this.surface = surface;
        this.p = p;
        this.particles = pointsToUpdate;
        this.from = from;
        this.to = to;
    }

    public void start() {
        super.start();
    }

    public void run() {
        for( int i = from; i < to; i++ ) {
            Vec2D v = particles.get( i );
            v.x = p.constrain( v.x, 0, surface.getWidth() - 1 );
            v.y = p.constrain( v.y, 0, surface.getHeight() - 1 );
            float jumpyNess = p.map( surface.get( ( int ) ( v.x ), ( int ) ( v.y ) ), 0, 255, 0, 6 );
            v.addSelf( p.random( -jumpyNess, jumpyNess ), p.random( -jumpyNess, jumpyNess ) );
        }
    }
}

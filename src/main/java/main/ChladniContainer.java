package main;

import com.jogamp.opengl.util.gl2.GLUT;
import com.nativelibs4java.opencl.CLBuffer;
import org.bridj.Pointer;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PShape;
import processing.opengl.PGL;
import processing.opengl.PGraphicsOpenGL;
import processing.opengl.PJOGL;
import processing.opengl.PShader;
import toxi.geom.Vec2D;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mar on 14.12.14.
 */
public class ChladniContainer {

    private ChladniSurface surface;
    private PGraphics particlePBO;
    private int particleCount;
    private ArrayList< Vec2D > particles;
    private PApplet p;
    private float rebuildSpeed, particleSize, particleOpacity;

    // opengl
    private PJOGL pgl;
    private GL2 gl;
    private GLU glu;
    private GLUT glut;

    public ChladniContainer( PApplet p, ChladniSurface surface, int particleCount ) {
        this.surface = surface;
        this.p = p;
        this.particleCount = particleCount;

        particlePBO = p.createGraphics( ( int )( getSurface().getWidth() ), ( int )( getSurface().getHeight() ), PConstants.P3D );

        particles = new ArrayList< Vec2D >();

        for( int i = 0; i < particleCount; i++ ) {
            Vec2D v = new Vec2D( p.random( surface.getWidth() ), p.random( surface.getHeight() ) );
            particles.add( v );
        }

        rebuildSpeed = 40.0f;
        particleSize = 3.0f;
        this.particleOpacity = 0.6f;
        pgl  = (PJOGL) particlePBO.beginPGL();
        glu  = pgl.glu;
        glut = new GLUT();
        gl   = GLU.getCurrentGL().getGL2();
    }

    public void update( int speed ) {
        surface.update();
        surface.loadPixels();

        while( particles.size() > particleCount ) {
            particles.remove( particles.size() - 1 );
        }

        while( particles.size() < particleCount ) {
            particles.add( new Vec2D( p.random( surface.getWidth() ), p.random( surface.getHeight() ) ) );
        }

        for( int i = 0; i < speed; i++ ) {

            for ( Vec2D v : particles ) {
                v.x = p.constrain( v.x, 0, surface.getWidth() - 1 );
                v.y = p.constrain( v.y, 0, surface.getHeight() - 1 );
                float jumpyNess = p.map( surface.get( ( int ) ( v.x ), ( int ) ( v.y ) ), 0, 255, 0, rebuildSpeed );
                Vec2D toAdd = new Vec2D( p.random( -jumpyNess, jumpyNess ), p.random( -jumpyNess, jumpyNess ) );
                v.addSelf( toAdd );
            }

        }

    }

    public void setParticleCount( int _particleCount ) {
        this.particleCount = _particleCount;
    }

    public void restrictCircular( int radius ) {
        for ( Vec2D v : particles ) {
            float rad = p.dist( v.x, v.y, surface.getWidth() / 2, surface.getHeight() / 2 );
            if( rad >= radius ) {
                v.x = p.random( surface.getWidth() );
                v.y = p.random( surface.getHeight() );
            }
        }
    }

    public void drawParticles( int x, int y ) {
        particlePBO.beginDraw();
        particlePBO.beginPGL();
        particlePBO.clear();
        gl.glPointSize( particleSize );
        gl.glBegin( GL.GL_POINTS );
        gl.glColor4f( 1.0f, 1.0f, 1.0f, particleOpacity );
        for( Vec2D v : particles ) {

            gl.glVertex2f( v.x,v.y );
        }
        gl.glEnd();
        particlePBO.endPGL();
        particlePBO.endDraw();

        p.image( particlePBO, x, y );
    }

    public void drawOriginal( int x, int y, int w, int h ) {
        this.surface.draw( x, y, w, h );
    }

    public PGraphics getParticlePBO() {
        return particlePBO;
    }

    public void frequencyChanged() {
        for( Vec2D v : particles ) {
            float jumpyNess = 6.0f;
            v.addSelf( p.random( -jumpyNess, jumpyNess ), p.random( -jumpyNess, jumpyNess ) );
        }
    }

    public void setRebuildSpeed( float _rebuildSpeed ) {
        this.rebuildSpeed = _rebuildSpeed;
    }

    public void setParticleSize( float _particleSize ) {
        this.particleSize = _particleSize;
    }

    public ChladniSurface getSurface() {
        return surface;
    }

    public void setParticleOpacity( float _particleOpacity ) {
        this.particleOpacity = _particleOpacity;
    }
}

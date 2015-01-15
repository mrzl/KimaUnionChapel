package pattern;

import osc.ChladniPatternParameterEnum;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.opengl.PGL;
import processing.opengl.PJOGL;
import toxi.geom.Vec2D;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mar on 14.12.14.
 */
public class ChladniParticles {

    private ChladniSurface surface;
    private PGraphics particlePBO;
    private ColorMode colorMode;
    private ColorModeEnum colorModeEnum;
    private DrawMode drawMode;
    private int particleCount;
    private ArrayList< Vec2D > particles, oldParticles;
    private ArrayList< Float > velocities;
    private PApplet p;
    private float rebuildSpeed, particleSize, particleOpacity;

    // opengl
    public PGL pgl;
    public GL2 gl2;

    // performance: rendering the origin smaller,
    private float scaleFactor;

    public ChladniParticles ( PApplet p, ChladniSurface surface, float scaleFactor, int particleCount ) {
        this.surface = surface;
        this.p = p;
        this.particleCount = particleCount;
        this.scaleFactor = scaleFactor;

        particlePBO = p.createGraphics( ( int ) ( getSurface( ).getWidth( ) * scaleFactor ), ( int ) ( getSurface( ).getHeight( ) * scaleFactor ), PConstants.P3D );

        particles = new ArrayList<>( );
        velocities = new ArrayList<>( );
        oldParticles = new ArrayList<>();

        for ( int i = 0; i < particleCount; i++ ) {
            Vec2D v = new Vec2D( p.random( particlePBO.width ), p.random( particlePBO.height ) );
            particles.add( v );
            velocities.add( 1.0f );
            oldParticles.add( new Vec2D( ) );
        }

        rebuildSpeed = 40.0f;
        particleSize = 3.0f;
        this.particleOpacity = 0.6f;

        gl2 = GLU.getCurrentGL( ).getGL2( );

        colorModeEnum = ColorModeEnum.MOON;
        drawMode = DrawMode.POINTS;

        colorMode = new ColorMode();
    }

    public void update ( int speed ) {
        surface.update();
        surface.loadPixels( );

        while ( particles.size( ) > particleCount ) {
            particles.remove( particles.size( ) - 1 );
            velocities.remove( velocities.size() - 1 );
            oldParticles.remove( oldParticles.size() - 1 );
        }

        while ( particles.size( ) < particleCount ) {
            particles.add( new Vec2D( p.random( particlePBO.width ), p.random( particlePBO.height ) ) );
            velocities.add( 1.0f );
            oldParticles.add( new Vec2D( ) );
        }

        for ( int i = 0; i < speed; i++ ) {
            int index = 0;
            for ( Vec2D v : particles ) {
                v.x = p.constrain( v.x, 0, particlePBO.width - 1 );
                v.y = p.constrain( v.y, 0, particlePBO.height - 1 );
                float jumpyNess = p.map( surface.get( ( int ) ( v.x / scaleFactor ), ( int ) ( v.y / scaleFactor ) ), 0, 255, 0, rebuildSpeed );
                Vec2D toAdd = new Vec2D( p.random( -jumpyNess, jumpyNess ), p.random( -jumpyNess, jumpyNess ) );

                oldParticles.set( index, v.copy() );

                v.addSelf( toAdd );

                v.x = p.constrain( v.x, 0, particlePBO.width - 1 );
                v.y = p.constrain( v.y, 0, particlePBO.height - 1 );

                velocities.set( index, jumpyNess );


                index++;
            }
        }
    }

    public void setParticleCount ( int _particleCount ) {
        this.particleCount = _particleCount;
    }

    public void restrictCircular ( int radius ) {
        for ( Vec2D v : particles ) {
            float rad = p.dist( v.x, v.y, particlePBO.width / 2, particlePBO.height / 2 );
            if ( rad >= radius ) {
                v.x = p.random( particlePBO.width );
                v.y = p.random( particlePBO.height );
            }
        }
    }

    public void restrictTriangular() {
        ChladniTriangle c = ( ChladniTriangle ) getSurface();
        PImage im = c.getMastk();
        im.loadPixels();
        for ( Vec2D v : particles ) {
            int x = ( int ) p.map( v.x, 0, particlePBO.width, 0, im.width );
            int y = ( int ) p.map( v.y, 0, particlePBO.height, 0, im.height );
            int index = x + y * im.width;
            if( index < im.pixels.length - 1 && index >= 0 ) {
                int col = im.pixels[ index ] & 0xFF;
                if ( col < 1 ) {
                    v.x = p.random( particlePBO.width );
                    v.y = p.random( particlePBO.height );
                }
            }
        }
    }

    public void renderParticles () {
        particlePBO.beginDraw( );
        pgl = particlePBO.beginPGL( );
        gl2 = ( ( PJOGL ) pgl ).gl.getGL2( );

        switch( drawMode ) {
            case POINTS:
                drawPoints();
                break;
            case LINES:
                drawLines();
                break;
            default:
                // nothing
                break;
        }
    }

    private void drawLines() {
        particlePBO.clear( );
        gl2.glEnable( GL.GL_BLEND );
        gl2.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);
        gl2.glLineWidth( particleSize );
        gl2.glBegin( GL.GL_LINES );

        int index = 0;
        for ( Vec2D v : particles ) {
            gl2.glColor4f( 1, 1, 1, particleOpacity );
            gl2.glVertex2f( v.x, v.y );
            Vec2D to = oldParticles.get( index );
            gl2.glVertex2f( to.x, to.y );
            index++;
        }

        gl2.glEnd( );
        particlePBO.endPGL( );
        particlePBO.endDraw( );
    }

    private void drawPoints() {
        particlePBO.clear( );
        gl2.glEnable( GL.GL_BLEND );
        gl2.glBlendFunc( GL.GL_SRC_ALPHA, GL.GL_ONE );
        gl2.glPointSize( particleSize );
        gl2.glBegin( GL.GL_POINTS );

        int index = 0;
        float r, g ,b;
        for ( Vec2D v : particles ) {
            switch( colorModeEnum ) {
                case VELOCITIES:
                    colorMode.setVelocity( 1.0f - velocities.get( index ) / rebuildSpeed, ColorMapping.HUE );
                    r = colorMode.red;
                    g = colorMode.green;
                    b = colorMode.blue;
                    break;
                case MONOCHROME:
                    r = 1;
                    g = 1;
                    b = 1;
                    break;
                case MOON:
                    colorMode.setVelocity( 1.0f - velocities.get( index ) / rebuildSpeed, ColorMapping.SATURATION );
                    r = colorMode.red;
                    g = colorMode.green;
                    b = colorMode.blue;
                    break;
                default:
                    r = 1;
                    g = 1;
                    b = 1;
            }

            gl2.glColor4f( r, g, b, particleOpacity );
            gl2.glVertex2f( v.x, v.y );
            index++;
        }
        gl2.glEnd( );
        particlePBO.endPGL( );
        particlePBO.endDraw( );
    }

    public void doAnomaly(){
        int index = 0;
        for( Vec2D v : particles ) {
            if( velocities.get( index ) < 0.5f ) {
                if( p.random( 1 ) < 0.1f ){
                    v.addSelf( p.random(-rebuildSpeed, rebuildSpeed), p.random(-rebuildSpeed, rebuildSpeed) );
                }
            }

            index++;
        }
    }

    public ColorMode getColorMode() {
        return colorMode;
    }

    public void renderParticlesToScreen ( int x, int y ) {
        p.image( particlePBO, x, y );
    }

    public void drawOriginal ( int x, int y, int w, int h ) {
        this.surface.draw( x, y, w, h );
    }

    public PGraphics getParticlePBO () {
        return particlePBO;
    }

    public void frequencyChanged () {
        for ( Vec2D v : particles ) {
            float jumpyNess = 6.0f;
            v.addSelf( p.random( -jumpyNess, jumpyNess ), p.random( -jumpyNess, jumpyNess ) );
        }
    }

    public void setRebuildSpeed ( float _rebuildSpeed ) {
        this.rebuildSpeed = _rebuildSpeed;
    }

    public void setParticleSize ( float _particleSize ) {
        this.particleSize = _particleSize;
    }

    public float getParticleSize() {
        return this.particleSize;
    }

    public ChladniSurface getSurface () {
        return surface;
    }

    public void setParticleOpacity ( float _particleOpacity ) {
        this.particleOpacity = _particleOpacity;
    }

    public void setColorModeEnum ( ColorModeEnum colorModeEnum ) {
        this.colorModeEnum = colorModeEnum;
    }

    public ColorModeEnum getColorModeEnum() {
        return colorModeEnum;
    }

    public float getScaleFactor() {
        return this.scaleFactor;
    }

    public void parameterChanged ( ChladniPatternParameterEnum chladniPatternParameter, float value ) {
        switch( chladniPatternParameter ) {
            case M:
                if( p.dist( getSurface().getM(), 0, value, 0 ) > 1 ) {
                    frequencyChanged();
                }
                getSurface().setM( value );
                break;
            case N:
                getSurface().setN( value );
                break;
            case JUMPYNESS:
                setRebuildSpeed( value );
                break;
            case PARTICLE_COUNT:
                setParticleCount( ( int ) value );
                break;
            case PARTICLE_OPACITY:
                setParticleOpacity( value );
                break;
            case PARTICLE_SIZE:
                setParticleSize( value );
                break;
            case POLES:
                getSurface().setPoles( ( int ) value );
                break;
            case SCALE:
                getSurface().setScale( value );
                break;
            default:
                System.err.println( "EROR: Unknown ChladniPatternParameter type in ChladniPattern" );
        }
    }
}

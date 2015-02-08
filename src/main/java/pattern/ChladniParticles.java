package pattern;

import controlP5.Slider;
import main.Main;
import modificators.BloomModifier;
import modificators.MetaBallModifier;
import midi.VisualParameterEnum;
import osc.ChladniPatternParameterEnum;
import pattern.attackvisualization.BackgroundBlendTimerThread;
import pattern.attackvisualization.IntensityTimerThread;
import pattern.attackvisualization.ParticleSizeTimerThread;
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
import java.util.ArrayList;

import static main.ControlFrame.getChladniFormId;
import static main.ControlFrame.getSliderById;
import static processing.core.PConstants.ADD;
import static processing.core.PConstants.BLEND;

/**
 * Created by mar on 14.12.14.
 */
public class ChladniParticles {

    private Main p;
    private ChladniSurface surface;
    private PGraphics particlePBO;

    private ArrayList< Vec2D > particles, oldParticles;
    private ArrayList< Float > velocities;

    private ColorMode colorMode;
    private RenderMode renderMode;
    private BehaviorMode behaviorMode;
    private BloomModifier bm;
    private MetaBallModifier mm;

    private float scaleFactor; // only the underlying surface will be rendered smaller
    private float rebuildSpeed, particleSize, particleOpacity;
    private int particleCount;
    private float motionBlurAmount;
    private int currentBlendedBackgroundValue;


    // opengl
    public PGL pgl;
    public GL2 gl2;

    //private ParticleSizeTimerThread particleSizeDrumHitThread;
    //private BackgroundBlendTimerThread backgroundBlendThread;
    //private IntensityTimerThread intensityThread;

    public ChladniParticles ( Main p, ChladniSurface surface, float scaleFactor, int particleCount ) {
        this.surface = surface;
        this.p = p;
        this.particleCount = particleCount;
        this.scaleFactor = scaleFactor;

        this.particlePBO = p.createGraphics( ( int ) ( getSurface( ).getWidth( ) * scaleFactor ), ( int ) ( getSurface( ).getHeight( ) * scaleFactor ), PConstants.P3D );

        this.particles = new ArrayList<>( );
        this.velocities = new ArrayList<>( );
        this.oldParticles = new ArrayList<>( );

        for ( int i = 0; i < particleCount; i++ ) {
            Vec2D v = new Vec2D( p.random( particlePBO.width ), p.random( particlePBO.height ) );
            this.particles.add( v );
            this.velocities.add( 1.0f );
            this.oldParticles.add( new Vec2D( ) );
        }

        this.rebuildSpeed = 40.0f;
        this.particleSize = 3.0f;
        this.particleOpacity = 0.6f;

        this.gl2 = GLU.getCurrentGL( ).getGL2( );

        this.renderMode = RenderMode.ORIGINAL;
        this.behaviorMode = BehaviorMode.REGULAR;

        this.colorMode = new ColorMode( );
        this.colorMode.setColorMode( ColorModeEnum.MOON );
        this.motionBlurAmount = 40;
        this.bm = new BloomModifier( p );
        this.mm = new MetaBallModifier( p );

        this.currentBlendedBackgroundValue = 0;

        //particleSizeDrumHitThread = new ParticleSizeTimerThread( this, this.getParticleSize( ) );
        //particleSizeDrumHitThread.start();
        //backgroundBlendThread = new BackgroundBlendTimerThread( this, getCurrentBlendedBackgroundValue() );
        //backgroundBlendThread.start();
        //intensityThread = new IntensityTimerThread( this, getSurface().getIntensity() );
    }

    public void update ( int speed ) {
        this.surface.update( );
        this.surface.loadPixels( );

        while ( particles.size( ) > particleCount ) {
            particles.remove( particles.size( ) - 1 );
            velocities.remove( velocities.size( ) - 1 );
            oldParticles.remove( oldParticles.size( ) - 1 );
        }

        while ( particles.size( ) < particleCount ) {
            if( behaviorMode == BehaviorMode.REGULAR ) {
                particles.add(new Vec2D(p.random(particlePBO.width), p.random(particlePBO.height)));
            } else if ( behaviorMode == BehaviorMode.CENTER_OUTWARDS ) {
                particles.add(new Vec2D(particlePBO.width / 2, particlePBO.height / 2));
            }

            velocities.add( 1.0f );
            oldParticles.add( new Vec2D( ) );
        }
        if( behaviorMode == BehaviorMode.CENTER_OUTWARDS ) {
            for (Vec2D v : particles) {
                Vec2D center = new Vec2D(particlePBO.width / 2, particlePBO.height / 2);
                Vec2D trans = v.sub(center).normalize().scale(PApplet.map(p.dist(particlePBO.width / 2, particlePBO.height / 2, v.x, v.y), 0, particlePBO.width / 2, 0, 0.6f));
                v.addSelf(trans);
            }
        }

        for ( int i = 0; i < speed; i++ ) {
            int index = 0;
            for ( Vec2D v : particles ) {

                limitParticleToBufferSize( v );

                float jumpyNess = p.map( surface.get( ( int ) ( v.x / scaleFactor ), ( int ) ( v.y / scaleFactor ) ), 0, 255, 0, rebuildSpeed );
                Vec2D toAdd = new Vec2D( p.random( -jumpyNess, jumpyNess ), p.random( -jumpyNess, jumpyNess ) );

                oldParticles.set( index, v.copy( ) );

                v.addSelf( toAdd );

                limitParticleToBufferSize( v );

                velocities.set( index, jumpyNess );

                index++;
            }
        }
    }

    private void limitParticleToBufferSize( Vec2D v ) {
        if( v.x >= particlePBO.width ) v.x = p.random( particlePBO.width );
        if( v.x < 0 ) v.x = p.random( particlePBO.width );
        if( v.y >= particlePBO.height ) v.y = p.random( particlePBO.height );
        if( v.y < 0 ) v.y = p.random( particlePBO.height );
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

    public void restrictTriangular () {
        ChladniTriangle c = ( ChladniTriangle ) getSurface( );
        PImage im = c.getMastk( );
        im.loadPixels( );
        for ( Vec2D v : particles ) {
            int x = ( int ) p.map( v.x, 0, particlePBO.width, 0, im.width );
            int y = ( int ) p.map( v.y, 0, particlePBO.height, 0, im.height );
            int index = x + y * im.width;
            if ( index < im.pixels.length - 1 && index >= 0 ) {
                int col = im.pixels[ index ] & 0xFF;
                if ( col < 1 ) {
                    v.x = p.random( particlePBO.width );
                    v.y = p.random( particlePBO.height );
                }
            }
        }
    }

    public void render () {
        particlePBO.beginDraw( );
        pgl = particlePBO.beginPGL( );
        gl2 = ( ( PJOGL ) pgl ).gl.getGL2( );


        switch ( getRenderMode() ) {
            case POINTS:
                getSurface().setDrawMonochrome( true );
                drawPoints( );
                break;
            case LINES:
                getSurface().setDrawMonochrome( true );
                drawLines( );
                break;
            case ORIGINAL:
                particlePBO.background( 0 );
                getSurface().setDrawMonochrome( false );
                getSurface().setMinHue( getColorMode().getMinHue() );
                getSurface().setMaxHue( getColorMode().getMaxHue() );

                // some bug in processing PShader, it flips the shape somehow..
                if( surface.getClass().equals( ChladniTriangle.class ) ) {
                    drawOriginal( 0, 0, ( int ) ( getSurface( ).getWidth( ) ), ( int ) ( getSurface( ).getHeight( ) ) );
                    PGraphics pg = getSurface().getBuffer();
                    particlePBO.pushMatrix();
                    particlePBO.scale( 1.0f, -1.0f );
                    particlePBO.image( pg, 0, -particlePBO.height, particlePBO.width, particlePBO.height );
                    particlePBO.popMatrix();
                } else {
                    drawOriginal( 0, 0, ( int ) ( getSurface( ).getWidth( ) ), ( int ) ( getSurface( ).getHeight( ) ) );
                    particlePBO.image( getSurface().getBuffer(), 0, 0, particlePBO.width, particlePBO.height );
                }

                // this way of visualizing the attack may not be used. TODO
                particlePBO.beginDraw( );
                particlePBO.blendMode( ADD );
                particlePBO.pushStyle( );

                particlePBO.noStroke( );
                particlePBO.fill( currentBlendedBackgroundValue );
                particlePBO.rect( 0, 0, getSurface( ).getBuffer( ).width * getScaleFactor(), getSurface( ).getBuffer( ).height * getScaleFactor() );

                particlePBO.popStyle( );
                particlePBO.blendMode( BLEND );
                particlePBO.endDraw( );

                break;
            default:
                System.err.println( "ERROR: Trying to render with unknown RenderMode" );
                break;
        }

        gl2.glEnd( );
        particlePBO.endPGL( );
        particlePBO.endDraw( );

        if ( bm.isEnabled( ) ) {
            bm.apply( getParticlePBO( ) );
            getParticlePBO( ).beginDraw( );
            getParticlePBO( ).blendMode( PConstants.ADD );

            getParticlePBO( ).image( getParticlePBO( ), 0, 0 );

            getParticlePBO( ).blendMode( PConstants.BLEND );
            getParticlePBO( ).endDraw( );
        }

        if ( mm.isEnabled( ) ) {
            mm.apply( getParticlePBO( ) );
        }
    }

    private RenderMode getRenderMode () {
        return renderMode;
    }

    public void setRenderMode ( RenderMode _dm ) {
        this.renderMode = _dm;
    }

    private void drawLines () {
        particlePBO.background( 0, motionBlurAmount );

        gl2.glEnable( GL.GL_BLEND );
        gl2.glBlendFunc( GL.GL_SRC_ALPHA, GL.GL_ONE );
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
    }

    private void drawPoints () {
        particlePBO.background( 0, motionBlurAmount );

        gl2.glEnable( GL.GL_BLEND );
        gl2.glBlendFunc( GL.GL_SRC_ALPHA, GL.GL_ONE );
        gl2.glPointSize( particleSize );
        gl2.glBegin( GL.GL_POINTS );


        int index = 0;
        float r, g, b;
        for ( Vec2D v : particles ) {
            switch ( colorMode.getColorMode( ) ) {
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
    }

    public void doAnomaly () {
        int index = 0;
        for ( Vec2D v : particles ) {
            if ( velocities.get( index ) < 0.5f ) {
                if ( p.random( 1 ) < 0.1f ) {
                    v.addSelf( p.random( -rebuildSpeed, rebuildSpeed ), p.random( -rebuildSpeed, rebuildSpeed ) );
                }
            }

            index++;
        }
    }

    public void doDrumHit() {
        switch( getRenderMode() ) {
            case POINTS:
                for( int i = 0; i < 5; i++ ) {
                    frequencyChanged();
                }

                //if( particleSizeDrumHitThread.running == false ) {
                    //particleSizeDrumHitThread = new ParticleSizeTimerThread( this, getParticleSize() );
                    //particleSizeDrumHitThread.start();
                //    particleSizeDrumHitThread.running = true;
                //}

                this.setParticleSize( PApplet.min( getParticleSize( ) * 2.0f, 30.0f ) );
                break;
            case ORIGINAL:

                //if( backgroundBlendThread.running == false ) {

                    //backgroundBlendThread = new BackgroundBlendTimerThread( this, 0 );
                    //backgroundBlendThread.start();
                //    backgroundBlendThread.running = true;
                    //backgroundBlendThread.start( );
                //}

                currentBlendedBackgroundValue = 255;

                /* Alternative drum hit
                if( intensityThread.running == false ) {
                    intensityThread = new IntensityTimerThread( this, getSurface().getIntensity() );
                    getSurface().setIntensity( 1.0f );
                    intensityThread.start();

                }
                */
                break;

        }
    }

    public void renderParticlesToScreen ( int x, int y ) {
        p.image( particlePBO, x, y );
    }

    public void drawOriginal ( int x, int y, int w, int h ) {
        this.surface.draw( x, y, w, h );
    }

    public void frequencyChanged () {
        for ( Vec2D v : particles ) {
            float jumpyNess = 6.0f;
            v.addSelf( p.random( -jumpyNess, jumpyNess ), p.random( -jumpyNess, jumpyNess ) );
        }
    }

    public void parameterChanged ( ChladniPatternParameterEnum chladniPatternParameter, float value ) {
        switch ( chladniPatternParameter ) {
            case M:
                getSurface( ).setM( value );
                break;
            case N:
                getSurface( ).setN( value );
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
                getSurface( ).setPoles( ( int ) value );
                break;
            case SCALE:
                getSurface( ).setScale( value );
                break;
            case DRUM_HIT:
                doDrumHit();
                break;
            default:
                System.err.println( "ERROR: Unknown ChladniPatternParameter type in ChladniPattern" );
        }
    }

    public void parameterChanged ( VisualParameterEnum visualParameter, float value ) {
        switch ( visualParameter ) {
            case MIN_HUE:
                getColorMode( ).setRangeMin( value );
                p.controlFrame.minMaxHue.setRangeValues( value, p.controlFrame.minMaxHue.getArrayValue( )[ 1 ] );
                break;
            case MAX_HUE:
                getColorMode( ).setRangeMax( value );
                p.controlFrame.minMaxHue.setRangeValues( p.controlFrame.minMaxHue.getArrayValue( )[ 0 ], value );
                break;
            case UPDATE_DELAY:
                p.soundController.setUpdateDelay( ( long ) value );
                p.controlFrame.updateDelaySlider.setValue( value );
                break;
            case BACKGROUND_OPACITY:
                setMotionBlurAmount( value );
                break;
            case M:
                Main.ChladniFormId currentId = getChladniFormId( p, this );
                Slider mSlider = getSliderById( p, currentId, visualParameter );
                mSlider.setValue( value );

                getSurface( ).setM( value );
                break;
            case N:
                Main.ChladniFormId currentIdN = getChladniFormId( p, this );
                Slider nSlider = getSliderById( p, currentIdN, visualParameter );
                nSlider.setValue( value );
                getSurface( ).setN( value );
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
                getSurface( ).setPoles( ( int ) value );
                break;
            case SCALE:
                getSurface( ).setScale( value );
                break;
            default:
                System.err.println( "ERROR: UNKNOWN VIASUAL PARAMETER" );
        }
    }

    public PGraphics getParticlePBO () {
        return particlePBO;
    }

    public void setRebuildSpeed ( float _rebuildSpeed ) {
        this.rebuildSpeed = _rebuildSpeed;
    }

    public void setParticleSize ( float _particleSize ) {
        this.particleSize = _particleSize;
    }

    public float getParticleSize () {
        return this.particleSize;
    }

    public ChladniSurface getSurface () {
        return surface;
    }

    public void setParticleOpacity ( float _particleOpacity ) {
        this.particleOpacity = _particleOpacity;
    }

    public void setColorModeEnum ( ColorModeEnum colorModeEnum ) {
        this.colorMode.setColorMode( colorModeEnum );
    }

    public float getScaleFactor () {
        return this.scaleFactor;
    }

    public BloomModifier getBloomModifier () {
        return bm;
    }

    public MetaBallModifier getMetaBallModifier () {
        return mm;
    }

    public float getMotionBlurAmount () {
        return this.motionBlurAmount;
    }

    public void setMotionBlurAmount ( float _motionBlurAmount ) {
        this.motionBlurAmount = _motionBlurAmount;
    }

    public ColorMode getColorMode () {
        return colorMode;
    }

    public void setBehaviorMode(BehaviorMode behaviorMode) {
        this.behaviorMode = behaviorMode;
    }

    public BehaviorMode getBehaviorMode() {
        return behaviorMode;
    }

    public void setIntensity( float _intensity ) {
        this.getSurface().setIntensity( _intensity );
    }

    public void setCurrentBlendedBackgroundValue( int value ) {
        this.currentBlendedBackgroundValue = value;
    }

    public int getCurrentBlendedBackgroundValue() {
        return this.currentBlendedBackgroundValue;
    }
}

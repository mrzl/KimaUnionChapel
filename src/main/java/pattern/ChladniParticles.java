package pattern;

import filter.shader.BrightnessIncreaseShader2;
import filter.shader.OpacityToHueShader;
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
    private OpacityToHueShader opacityToHue;

    private BrightnessIncreaseShader2 increaser;

    private float scaleFactor; // only the underlying surface will be rendered smaller
    private float rebuildSpeed, particleSize, particleOpacity;
    private int particleCount;
    private float backgroundOpacity;
    private int currentBlendedBackgroundValue;


    // opengl
    public PGL pgl;
    public GL2 gl2;

    private ParticleSizeTimerThread particleSizeDrumHitThread;
    private BackgroundBlendTimerThread backgroundBlendThread;
    private IntensityTimerThread intensityThread;

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
        this.colorMode.setColorMode( ColorModeEnum.VELOCITIES );
        this.backgroundOpacity = 40;
        this.bm = new BloomModifier( p );
        this.mm = new MetaBallModifier( p );


        this.currentBlendedBackgroundValue = 0;

        particleSizeDrumHitThread = new ParticleSizeTimerThread( this, this.getParticleSize( ) );
        particleSizeDrumHitThread.start();
        backgroundBlendThread = new BackgroundBlendTimerThread( this, getCurrentBlendedBackgroundValue() );
        backgroundBlendThread.start();
        intensityThread = new IntensityTimerThread( this, getSurface().getIntensity() );

        opacityToHue = new OpacityToHueShader( p );
        increaser = new BrightnessIncreaseShader2( p, getParticlePBO().width, getParticlePBO().height );
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
                particles.add(new Vec2D(p.random(getParticlePBO().width), p.random(getParticlePBO().height)));
            } else if ( behaviorMode == BehaviorMode.CENTER_OUTWARDS ) {
                particles.add(new Vec2D(getParticlePBO().width / 2, getParticlePBO().height / 2));
            }

            velocities.add( 1.0f );
            oldParticles.add( new Vec2D( ) );
        }
        if( behaviorMode == BehaviorMode.CENTER_OUTWARDS ) {
            for (Vec2D v : particles) {
                Vec2D center = new Vec2D(getParticlePBO().width / 2, getParticlePBO().height / 2);
                Vec2D trans = v.sub(center).normalize().scale(PApplet.map(p.dist(getParticlePBO().width / 2, getParticlePBO().height / 2, v.x, v.y), 0, getParticlePBO().width / 2, 0, 0.6f));
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
        if( v.x >= getParticlePBO().width ) v.x = p.random( getParticlePBO().width );
        if( v.x < 0 ) v.x = p.random( getParticlePBO().width );
        if( v.y >= getParticlePBO().height ) v.y = p.random( getParticlePBO().height );
        if( v.y < 0 ) v.y = p.random( getParticlePBO().height );
    }

    public void setParticleCount ( int _particleCount ) {
        this.particleCount = _particleCount;
    }

    public void restrictCircular ( int radius ) {
        for ( Vec2D v : particles ) {
            float rad = p.dist( v.x, v.y, particlePBO.width / 2, particlePBO.height / 2 );
            if ( rad >= radius ) {
                v.x = p.random( getParticlePBO().width );
                v.y = p.random( getParticlePBO().height );
            }
        }
    }

    public void restrictTriangular () {
        ChladniTriangle c = ( ChladniTriangle ) getSurface( );
        PImage im = c.getMastk( );
        im.loadPixels( );
        for ( Vec2D v : particles ) {
            int x = ( int ) p.map( v.x, 0, getParticlePBO().width, 0, im.width );
            int y = ( int ) p.map( v.y, 0, getParticlePBO().height, 0, im.height );
            int index = x + y * im.width;
            if ( index < im.pixels.length - 1 && index >= 0 ) {
                int col = im.pixels[ index ] & 0xFF;
                if ( col < 1 ) {
                    v.x = p.random( getParticlePBO().width );
                    v.y = p.random( getParticlePBO().height );
                }
            }
        }
    }

    public void render () {
        getParticlePBO().beginDraw( );
        pgl = getParticlePBO().beginPGL( );
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
                getParticlePBO().background( 0 );
                getSurface().setDrawMonochrome( false );
                getSurface().setMinHue( getColorMode().getMinHue() );
                getSurface().setMaxHue( getColorMode().getMaxHue() );

                // some bug in processing PShader, it flips the shape somehow..
                if( surface.getClass().equals( ChladniTriangle.class ) ) {
                    //drawOriginal( 0, 0, ( int ) ( getSurface( ).getWidth( ) ), ( int ) ( getSurface( ).getHeight( ) ) );
                    PGraphics pg = getSurface().getBuffer();
                    getParticlePBO().pushMatrix( );
                    getParticlePBO().scale( 1.0f, -1.0f );
                    getParticlePBO().image( pg, 0, -getParticlePBO().height, getParticlePBO().width, getParticlePBO().height );
                    getParticlePBO().popMatrix( );
                } else {
                    //drawOriginal( 0, 0, ( int ) ( getSurface( ).getWidth( ) ), ( int ) ( getSurface( ).getHeight( ) ) );
                    getParticlePBO().image( getSurface( ).getBuffer( ), 0, 0, getParticlePBO().width, getParticlePBO().height );
                }



                // this way of visualizing the attack may not be used. TODO
                getParticlePBO().beginDraw( );
                getParticlePBO().blendMode( ADD );
                getParticlePBO().pushStyle( );

                getParticlePBO().noStroke( );
                getParticlePBO().fill( currentBlendedBackgroundValue );
                getParticlePBO().rect( 0, 0, getSurface( ).getBuffer( ).width * getScaleFactor( ), getSurface( ).getBuffer( ).height * getScaleFactor( ) );

                getParticlePBO().popStyle( );
                getParticlePBO().blendMode( BLEND );
                getParticlePBO().endDraw( );

                break;
            default:
                System.err.println( "ERROR: Trying to render with unknown RenderMode" );
                break;
        }

        gl2.glEnd( );
        getParticlePBO().endPGL( );
        getParticlePBO().endDraw( );

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

        opacityToHue.apply( getParticlePBO() );

        increaser.apply( getParticlePBO() );
    }

    public RenderMode getRenderMode () {
        return renderMode;
    }

    public void setRenderMode ( RenderMode _dm ) {
        this.renderMode = _dm;
    }

    private void drawLines () {
        getParticlePBO().background( 0, getBackgroundOpacity( ) );

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
        // TODO: This is a hacky way to fix the issue, something is wrong here.
        // It seems like the background opacity was never really working before, but it obviously did..
        if( getBloomModifier().isEnabled() ) {
           getParticlePBO().background( 0, getBackgroundOpacity() );
        } else {
            getParticlePBO( ).pushStyle( );
            getParticlePBO( ).noStroke( );
            getParticlePBO( ).fill( 0, getBackgroundOpacity( ) );
            getParticlePBO( ).rect( 0, 0, getParticlePBO( ).width, getParticlePBO( ).height );
            getParticlePBO( ).popStyle( );
        }

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

            gl2.glColor4f( r, g, b, getParticleOpacity() );
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

                if( particleSizeDrumHitThread.running == false ) {
                    particleSizeDrumHitThread.running = true;
                }

                this.setParticleSize( PApplet.min( getParticleSize( ) * 2.0f, 30.0f ) );
                break;
            case ORIGINAL:

                if( backgroundBlendThread.running == false ) {
                    backgroundBlendThread.running = true;
                }

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
        p.image( getParticlePBO(), x, y );
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

    public void parameterChangedFromOscController ( ChladniPatternParameterEnum chladniPatternParameter, float value ) {
        switch ( chladniPatternParameter ) {
            case M:
                getSurface( ).setM( value );
                break;
            case N:
                getSurface( ).setN( value );
                break;
            case JUMPYNESS:
                setParticleJumpyness( value );
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

    public void parameterChangedFromBcrController ( VisualParameterEnum visualParameter, float value ) {
        switch ( visualParameter ) {
            case MIN_HUE:
                getColorMode( ).setMinHue( value );
                break;
            case INTENSITY:
                setIntensity( value );
                p.controlFrame.brightnessPatternSlider.setValue( value );
                break;
            case THRESHOLD:
                getBloomModifier().setThreshold( value );
                p.controlFrame.bloomThresholdSlider.setValue( value );
            case MAX_HUE:
                getColorMode( ).setMaxHue( value );
                break;
            case UPDATE_DELAY:
                p.oscController.setUpdateDelay( ( long ) value );
                break;
            case BACKGROUND_OPACITY:
                setBackgroundOpacity( value );
                p.controlFrame.backgroundOpacitySlider.setValue( value );
                break;
            case M:
                getSurface( ).setM( value );
                p.controlFrame.mSlider.setValue( value );
                break;
            case N:
                getSurface( ).setN( value );
                p.controlFrame.nSlider.setValue( value );
                break;
            case JUMPYNESS:
                setParticleJumpyness( value );
                p.controlFrame.particleJumpynessSlider.setValue( value );
                break;
            case PARTICLE_COUNT:
                setParticleCount( ( int ) value );
                p.controlFrame.particleCountSlider.setValue( value );
                break;
            case PARTICLE_OPACITY:
                setParticleOpacity( value );
                p.controlFrame.particleOpacitySlider.setValue( value );
                break;
            case PARTICLE_SIZE:
                setParticleSize( value );
                p.controlFrame.particleSizeSlider.setValue( value );
                break;
            case POLES:
                getSurface( ).setPoles( ( int ) value );
                break;
            case SCALE:
                getSurface( ).setScale( value );
                p.controlFrame.triangleScalesSlider.setValue( value );
                break;
            case MARE_UNDARUM:
                setRenderMode( RenderMode.ORIGINAL );
                p.controlFrame.drawModeSlider.setValue( 0.5f );
                System.err.println( "Setting Mare Undarum parameters." );
                break;
            case AXIS_MUNDI:
                setRenderMode( RenderMode.POINTS );
                p.controlFrame.drawModeSlider.setValue( 0.2f );
                System.err.println( "Setting Axis Mundi parameters." );
                break;
            case AURORA:
                System.err.println( "Setting Aurora parameters." );
                break;
            default:
                System.err.println( "ERROR: UNKNOWN VIASUAL PARAMETER" );
        }
    }

    public PGraphics getParticlePBO () {
        return particlePBO;
    }

    public void setParticleJumpyness ( float _rebuildSpeed ) {
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

    public float getParticleJumpyness () {
        return rebuildSpeed;
    }

    public float getParticleOpacity () {
        return particleOpacity;
    }

    public int getParticleCount () {
        return particleCount;
    }

    public float getBackgroundOpacity () {
        return backgroundOpacity;
    }

    public void setBackgroundOpacity( float value) {
        this.backgroundOpacity = value;
    }

    public OpacityToHueShader getOpacityToHueShader () {
        return opacityToHue;
    }

    public BrightnessIncreaseShader2 getBrightnessContrastShader () {
        return increaser;
    }

    public void setBrightnessContrastShader ( BrightnessIncreaseShader2 increaser ) {
        this.increaser = increaser;
    }
}

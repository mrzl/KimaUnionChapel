package pattern;

import filter.shader.*;
import main.ImageGradient;
import main.Main;
import modificators.BloomModifier;
import modificators.MetaBallModifier;
import midi.VisualParameterEnum;
import modificators.SepBlurShader;
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
import java.awt.*;
import java.io.File;
import java.util.ArrayList;


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

    // testing
    private SepBlurShader blur;

    private BrightnessIncreaseShader2 increaser;

    private float scaleFactor; // only the underlying surface will be rendered smaller
    private float rebuildSpeed, particleSize, particleOpacity;
    private int particleCount;
    private float backgroundOpacity;
    private int currentBlendedBackgroundValue;

    private ImageGradient imageGradient;
    private IntensityToImageGradientShader imageGradientFilter;
    private DirectionalBlur directionalBlur;
    private DirectionalBlur2 directionalBlur2;

    private boolean disabled;


    // opengl
    public PGL pgl;
    public GL2 gl2;

    private ParticleSizeTimerThread particleSizeDrumHitThread;
    private BackgroundBlendTimerThread backgroundBlendThread;
    private IntensityTimerThread intensityThread;
    private float intensity;

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
        this.mm = new MetaBallModifier( p, getParticlePBO().width, getParticlePBO().height );


        this.currentBlendedBackgroundValue = 0;

        particleSizeDrumHitThread = new ParticleSizeTimerThread( this, this.getParticleSize( ) );
        particleSizeDrumHitThread.start();
        backgroundBlendThread = new BackgroundBlendTimerThread( this, getCurrentBlendedBackgroundValue() );
        backgroundBlendThread.start();
        intensityThread = new IntensityTimerThread( this, getSurface().getIntensity() );

        opacityToHue = new OpacityToHueShader( p );
        increaser = new BrightnessIncreaseShader2( p, getParticlePBO().width, getParticlePBO().height );

        blur = new SepBlurShader( p );

        imageGradient = new ImageGradient( p.loadImage("media" + File.separator + "gradient2.png") );
        imageGradientFilter = new IntensityToImageGradientShader( p, imageGradient );

        directionalBlur = new DirectionalBlur( p );
        directionalBlur2 = new DirectionalBlur2( p, getSurface() );

        disabled = false;
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
                Vec2D trans = v.sub(center).normalize().scale(10);
                v.addSelf( trans );
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

                //this.grayScaleValues.set( index, surf )

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
        if( !isDisabled() ) {
            getParticlePBO( ).beginDraw( );
            pgl = getParticlePBO( ).beginPGL( );
            gl2 = ( ( PJOGL ) pgl ).gl.getGL2( );


            switch ( getRenderMode( ) ) {
                case POINTS:
                    getSurface( ).setDrawMonochrome( true );

                    drawPoints( );
                    break;
                case IMAGE_GRADIENT:
                    //getParticlePBO( ).background( 0 );

                    getSurface( ).setDrawMonochrome( true );
                    getSurface( ).setMinHue( getColorMode( ).getMinHue( ) );
                    getSurface( ).setMaxHue( getColorMode( ).getMaxHue( ) );

                    // some bug in processing PShader, it flips the shape somehow..
                    if ( surface.getClass( ).equals( ChladniTriangle.class ) ) {
                        //drawOriginal( 0, 0, ( int ) ( getSurface( ).getWidth( ) ), ( int ) ( getSurface( ).getHeight( ) ) );
                        PGraphics pg = getSurface( ).getBuffer( );
                        getParticlePBO( ).pushMatrix( );
                        getParticlePBO( ).scale( 1.0f, -1.0f );
                        getParticlePBO( ).image( pg, 0, -getParticlePBO( ).height, getParticlePBO( ).width, getParticlePBO( ).height );
                        getParticlePBO( ).popMatrix();
                    } else {
                        //drawOriginal( 0, 0, ( int ) ( getSurface( ).getWidth( ) ), ( int ) ( getSurface( ).getHeight( ) ) );
                        getParticlePBO( ).image( getSurface( ).getBuffer( ), 0, 0, getParticlePBO( ).width, getParticlePBO( ).height );
                    }

                    //imageGradientFilter.apply( getParticlePBO() );
                    break;
                case ORIGINAL:
                    getParticlePBO( ).background( 0 );

                    getSurface( ).setDrawMonochrome( false );
                    getSurface( ).setMinHue( getColorMode( ).getMinHue( ) );
                    getSurface( ).setMaxHue( getColorMode( ).getMaxHue( ) );

                    // some bug in processing PShader, it flips the shape somehow..
                    if ( surface.getClass( ).equals( ChladniTriangle.class ) ) {
                        //drawOriginal( 0, 0, ( int ) ( getSurface( ).getWidth( ) ), ( int ) ( getSurface( ).getHeight( ) ) );
                        PGraphics pg = getSurface( ).getBuffer( );
                        getParticlePBO( ).pushMatrix( );
                        getParticlePBO( ).scale( 1.0f, -1.0f );
                        getParticlePBO( ).image( pg, 0, -getParticlePBO( ).height, getParticlePBO( ).width, getParticlePBO( ).height );
                        getParticlePBO( ).popMatrix();
                    } else {
                        //drawOriginal( 0, 0, ( int ) ( getSurface( ).getWidth( ) ), ( int ) ( getSurface( ).getHeight( ) ) );
                        getParticlePBO( ).image( getSurface( ).getBuffer( ), 0, 0, getParticlePBO( ).width, getParticlePBO( ).height );
                    }
                    break;
                default:
                    System.err.println( "ERROR: Trying to render with unknown RenderMode" );
                    break;
            }

            gl2.glEnd( );
            getParticlePBO( ).endPGL( );
            getParticlePBO( ).endDraw( );

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

            opacityToHue.apply( getParticlePBO( ), getSurface() );

            increaser.apply( getParticlePBO( ) );

            directionalBlur2.apply( getParticlePBO() );
        } else {
            getParticlePBO().beginDraw();
            getParticlePBO().background( 0 );
            getParticlePBO().endDraw();
        }
    }

    public DirectionalBlur2 getDirectionalBlur2() {
        return directionalBlur2;
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
           getParticlePBO().background( 0, getBackgroundOpacity( ) );
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
                    colorMode.setVelocity( velocities.get( index ) / rebuildSpeed, ColorMapping.HUE );
                    r = colorMode.red;
                    g = colorMode.green;
                    b = colorMode.blue;
                    float[] hsb = new float[3];
                    Color.RGBtoHSB( ( int ) ( r * 255 ), ( int ) ( g*255 ), ( int ) ( b*255 ), hsb );
                    hsb[1] = getSurface().getSaturation();

                    int rgb = Color.HSBtoRGB( hsb[0], hsb[1], hsb[2] );
                    //System.out.println( hsb[ 0] +" "+ hsb[1] +" " + hsb[2] );
                    r = ( ( rgb >> 16 ) & 0xFF ) / 255.0f;
                    g = ( ( rgb >> 8 ) & 0xFF ) / 255.0f;
                    b = ( rgb & 0xFF ) / 255.0f;

                    break;
                case MONOCHROME:
                    r = 1;
                    g = 1;
                    b = 1;
                    break;
                case MOON:
                    colorMode.setVelocity( velocities.get( index ) / rebuildSpeed, ColorMapping.SATURATION );
                    r = colorMode.red;
                    g = colorMode.green;
                    b = colorMode.blue;
                    break;
                case GRAYSCALE_MAPPING:
                    // TODO: this is awfully hacked in here.
                    int x = ( int ) (v.x() / scaleFactor);
                    int y = ( int ) (v.y() / scaleFactor);
                    int color = getSurface().get( x, y );
                    float hue = p.map( color, 255, 127, getColorMode().getMinHue(), getColorMode().getMaxHue() );
                    int rgb1 = Color.HSBtoRGB( hue, getSurface().getSaturation(), getSurface().getIntensity() );

                    r = ( ( rgb1 >> 16 ) & 0xFF ) / 255.0f;
                    g = ( ( rgb1 >> 8 ) & 0xFF ) / 255.0f;
                    b = ( rgb1 & 0xFF ) / 255.0f;
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
                getOpacityToHueShader().setMinHue( value );
                break;
            case INTENSITY:
                setIntensity( value );
                //p.controlFrame.brightnessPatternSlider.setValue( value );
                break;
            case THRESHOLD:
                getBloomModifier().setThreshold( value );
                p.controlFrame.bloomThresholdSlider.setValue( value );
            case MAX_HUE:
                getColorMode( ).setMaxHue( value );
                getOpacityToHueShader().setMaxHue( value );
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
                //p.controlFrame.mSlider.setValue( value );
                break;
            case N:
                getSurface( ).setN( value );
                //p.controlFrame.nSlider.setValue( value );
                break;
            case JUMPYNESS:
                setParticleJumpyness( value );
                //p.controlFrame.particleJumpynessSlider.setValue( value );
                break;
            case PARTICLE_COUNT:
                setParticleCount( ( int ) value );
                //p.controlFrame.particleCountSlider.setValue( value );
                break;
            case PARTICLE_OPACITY:
                setParticleOpacity( value );
                //p.controlFrame.particleOpacitySlider.setValue( value );
                break;
            case PARTICLE_SIZE:
                setParticleSize( value );
                //p.controlFrame.particleSizeSlider.setValue( value );
                break;
            case POLES:
                getSurface( ).setPoles( ( int ) value );
                break;
            case SCALE:
                getSurface( ).setScale( value );
                //p.controlFrame.triangleScalesSlider.setValue( value );
                break;
            case BLOOM_SIGMA:
                getBloomModifier().setBlurSigma( value );
                //p.controlFrame.bloomSigmaSlider.setValue( value );
                break;
            case BLOOM_SIZE:
                getBloomModifier().setBlurSize( ( int ) value );
                //p.controlFrame.bloomBlurSizeSlider.setValue( value );
                break;
            case CONTRAST:
                getBrightnessContrastShader().setContrast( value );
                break;
            case BRIGHTNESS:
                getBrightnessContrastShader().setBrightness( value );
                break;
            case BLUR_DIRECTION:
                getDirectionalBlur2().setDirection( value );
                System.out.println( "blur direction: " + value );
                break;
            case BLUR_STRENGTH:
                getDirectionalBlur2().setRadius( value );
                System.out.println( "blur strength: " + value );
                break;
            case CUTOFF:
                getSurface().setCutoff( value );
                System.out.println( "cutoff: " + value );
                break;
            case MARE_UNDARUM_1:
                setRenderMode( RenderMode.ORIGINAL );
                p.controlFrame.drawModeSlider.setValue( 0.5f );
                p.getTransitionController().select( this, visualParameter );
                break;
            case MARE_UNDARUM_2:
                setRenderMode( RenderMode.ORIGINAL );
                p.controlFrame.drawModeSlider.setValue( 0.5f );
                p.getTransitionController().select( this, visualParameter );
                break;
            case MARE_UNDARUM_3:
                setRenderMode( RenderMode.ORIGINAL );
                p.controlFrame.drawModeSlider.setValue( 0.5f );
                p.getTransitionController().select( this, visualParameter );
                break;
            case AXIS_MUNDI_1:
                setRenderMode( RenderMode.POINTS );
                p.controlFrame.drawModeSlider.setValue( 0.2f );
                p.getTransitionController().select( this, visualParameter );
                break;
            case AXIS_MUNDI_2:
                setRenderMode( RenderMode.POINTS );
                p.controlFrame.drawModeSlider.setValue( 0.2f );
                p.getTransitionController().select( this, visualParameter );
                break;
            case AXIS_MUNDI_3:
                setRenderMode( RenderMode.POINTS );
                p.controlFrame.drawModeSlider.setValue( 0.2f );
                p.getTransitionController().select( this, visualParameter );
                break;
            case AXIS_MUNDI_4:
                setRenderMode( RenderMode.POINTS );
                p.controlFrame.drawModeSlider.setValue( 0.2f );
                p.getTransitionController().select( this, visualParameter );
                break;
            case AURORA_1:
                setRenderMode( RenderMode.IMAGE_GRADIENT );
                p.controlFrame.drawModeSlider.setValue( 0.5f );
                p.controlFrame.setPattern( this );
                p.getTransitionController().select( this, visualParameter );
                break;
            case AURORA_2:
                setRenderMode( RenderMode.IMAGE_GRADIENT );
                p.controlFrame.drawModeSlider.setValue( 0.5f );
                p.controlFrame.setPattern( this );
                p.getTransitionController().select( this, visualParameter );
                break;
            case AURORA_3:
                setRenderMode( RenderMode.IMAGE_GRADIENT );
                p.controlFrame.drawModeSlider.setValue( 0.5f );
                p.controlFrame.setPattern( this );
                p.getTransitionController().select( this, visualParameter );
                break;
            case AURORA_4:
                setRenderMode( RenderMode.IMAGE_GRADIENT );
                p.controlFrame.drawModeSlider.setValue( 0.5f );
                p.controlFrame.setPattern( this );
                p.getTransitionController().select( this, visualParameter );
                break;
            case AURORA_5:
                setRenderMode( RenderMode.IMAGE_GRADIENT );
                p.controlFrame.drawModeSlider.setValue( 0.5f );
                p.controlFrame.setPattern( this );
                p.getTransitionController().select( this, visualParameter );
                break;
            case AURORA_6:
                setRenderMode( RenderMode.IMAGE_GRADIENT );
                p.controlFrame.drawModeSlider.setValue( 0.5f );
                p.controlFrame.setPattern( this );
                p.getTransitionController().select( this, visualParameter );
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

    public boolean isDisabled () {
        return disabled;
    }

    public void setDisabled( boolean disabled ) {
        this.disabled = disabled;
    }

    public float getIntensity () {
        return intensity;
    }
}

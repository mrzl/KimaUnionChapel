package main;

import controlP5.*;
import midi.VisualParameterEnum;
import pattern.*;
import processing.core.PApplet;

import java.awt.*;
import java.util.Map;

/**
 * Created by mar on 15.12.14.
 */
public class ControlFrame extends PApplet {

    private ChladniParticles selectedParticles;

    public static ControlP5 controlP5;
    private Main parent;
    private int w, h;

    public Slider particleJumpynessSlider;
    public Slider triangleScalesSlider;
    public Slider updateDelaySlider, nSlider;
    public Slider mSlider;
    public Slider particleOpacitySlider;
    public Slider particleCountSlider;
    public Slider particleSizeSlider;
    public Slider backgroundOpacitySlider;
    public Slider intensitySlider;
    public Slider bloomSigmaSlider;
    public Slider bloomBlurSizeSlider;
    public Slider bloomThresholdSlider;
    public Range minMaxHue;

    public void setup () {
        size( w, h );
        controlP5 = new ControlP5( this );

        int SLIDER_WIDTH = 300;
        float Y_POS = 10;

        nSlider = controlP5.addSlider( "N" ).setRange( 0, 15 ).setSize( SLIDER_WIDTH, 20 ).setPosition( 10, Y_POS ).setValue( 2.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                selectedParticles.getSurface().setN( controlEvent.getValue() );
            }
        } );

        Y_POS += 40;
        mSlider = controlP5.addSlider( "M" ).setRange( 0, 15 ).setSize( SLIDER_WIDTH, 20 ).setPosition( 10, Y_POS ).setValue( 3.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                selectedParticles.getSurface().setM( controlEvent.getValue() );
            }
        } );

        Y_POS += 40;
        triangleScalesSlider = controlP5.addSlider( "TriangleScales" ).setRange( 0, 2 ).setSize( SLIDER_WIDTH, 20 ).setPosition( 10, Y_POS ).setValue( 1.1f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                selectedParticles.getSurface().setScale( controlEvent.getValue() );
            }
        } );


        Y_POS += 80;

        particleJumpynessSlider = controlP5.addSlider( "Jumpyness" ).setRange( 0, 200 ).setSize( SLIDER_WIDTH, 20 ).setPosition( 10, Y_POS ).setValue( 30.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                selectedParticles.setRebuildSpeed( controlEvent.getValue() );
            }
        } );

        Y_POS += 30;
        particleSizeSlider = controlP5.addSlider( "ParticleSize" ).setRange( 0, 80 ).setSize( SLIDER_WIDTH, 20 ).setPosition( 10, Y_POS ).setValue( 3.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                selectedParticles.setParticleSize( controlEvent.getValue() );
            }
        } );

        Y_POS += 30;
        particleCountSlider = controlP5.addSlider( "ParticleCount" ).setRange( 0, 30000 ).setSize( SLIDER_WIDTH, 20 ).setPosition( 10, Y_POS ).setValue( 10000.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                selectedParticles.setParticleCount( ( int ) controlEvent.getValue() );
            }
        } );

        Y_POS += 30;
        particleOpacitySlider = controlP5.addSlider( "ParticleOpacity" ).setRange( 0, 1 ).setSize( SLIDER_WIDTH, 20 ).setPosition( 10, Y_POS ).setValue( 0.6f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                selectedParticles.setParticleOpacity( controlEvent.getValue() );
            }
        } );

        Y_POS += 30;
        backgroundOpacitySlider = controlP5.addSlider( "BackgroundOpacity" ).setRange( 0, 255 ).setSize( SLIDER_WIDTH, 20 ).setPosition( 10, Y_POS ).setValue( 40 ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                selectedParticles.setMotionBlurAmount( controlEvent.getValue() );
            }
        } );

        Y_POS += 30;
        bloomBlurSizeSlider = controlP5.addSlider( "BloomBlurSize" )
                .setPosition( 10, Y_POS )
                .setSize( SLIDER_WIDTH, 20 )
                .setRange( 1, 80 )
                .setValue( 28 )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                       selectedParticles.getBloomModifier( ).setBlurSize( ( int ) ( controlEvent.getValue( ) ) );
                    }
                } );

        Y_POS += 30;
        bloomSigmaSlider = controlP5.addSlider( "BloomSigma" )
                .setPosition( 10, Y_POS )
                .setSize( SLIDER_WIDTH, 20 )
                .setRange( 1, 80 )
                .setValue( 4 )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        selectedParticles.getBloomModifier().setBlurSigma( controlEvent.getValue( ) );
                    }
                } );

        Y_POS += 30;
        bloomThresholdSlider = controlP5.addSlider( "BloomThreshold" )
                .setPosition( 10, Y_POS )
                .setSize( SLIDER_WIDTH, 20 )
                .setRange( 0, 1 )
                .setValue( 0.1f )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        selectedParticles.getBloomModifier().setThreshold( controlEvent.getValue( ) );
                    }
                } );

        Y_POS += 40;

        controlP5.addToggle( "thresholdToggle" )
                .setPosition( 10, Y_POS )
                .setSize( 50, 20 )
                .setValue( false )
                .setMode( ControlP5.SWITCH )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        selectedParticles.getMetaBallModifier().setEnabled( getBoolFromFloat( controlEvent.getValue( ) ) );
                    }
                } )
        ;

        controlP5.addToggle( "Color Mode Toggle" )
                .setPosition( 80, Y_POS )
                .setSize( 50, 20 )
                .setValue( false )
                .setMode( ControlP5.SWITCH )
                .addListener( new ControlListener( ) {

                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        if ( getBoolFromFloat( controlEvent.getValue( ) ) ) {
                            selectedParticles.setColorModeEnum( ColorModeEnum.MOON );
                        } else {
                            selectedParticles.setColorModeEnum( ColorModeEnum.VELOCITIES );
                        }
                    }
                } );

        controlP5.addToggle( "Bloom Toggle" )
                .setPosition( 150, Y_POS )
                .setSize( 50, 20 )
                .setValue( false )
                .setMode( ControlP5.SWITCH )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        selectedParticles.getBloomModifier().setEnabled( getBoolFromFloat( controlEvent.getValue( ) ) );
                    }
                } );

        controlP5.addSlider( "Draw Mode" )
                .setPosition( 220, Y_POS )
                .setSize( 50, 20 )
                .setRange( 0, 1 )
                .setValue( 0.0f )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        float v = controlEvent.getValue( );

                        if ( v < 0.3f ) {
                            selectedParticles.setRenderMode( RenderMode.POINTS );
                        } else if( v > 0.6f ) {
                            selectedParticles.setRenderMode( RenderMode.LINES );
                        } else {
                            selectedParticles.setRenderMode( RenderMode.ORIGINAL );
                        }
                    }
                } );

        controlP5.addToggle( "Behavior Mode" ).setPosition( 300, Y_POS ).setSize( 50, 20 ).setValue( false ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                boolean v = getBoolFromFloat( controlEvent.getValue( ) );
                if ( v ) {
                    selectedParticles.setBehaviorMode( BehaviorMode.REGULAR );
                } else {
                    selectedParticles.setBehaviorMode( BehaviorMode.CENTER_OUTWARDS );
                }
            }
        } );

        Y_POS += 50;
        controlP5.addSlider( "Threshold" )
                .setRange( 0, 1 )
                .setSize( SLIDER_WIDTH, 20 )
                .setPosition( 10, Y_POS )
                .setValue( 0.2f )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        selectedParticles.getMetaBallModifier().setThreshold( controlEvent.getValue() );
                    }
                } );

        Y_POS += 50;
        updateDelaySlider = controlP5.addSlider( "updateDelay" )
                .setRange( 0, 2000 )
                .setSize( SLIDER_WIDTH, 20 )
                .setPosition( 10, Y_POS )
                .setValue( 100 )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        parent.soundController.setUpdateDelay( ( long ) controlEvent.getValue( ) );
                    }
                } );

        Y_POS += 50;

        minMaxHue = controlP5.addRange( "minHue" )
                .setRange( 0.0f, 1.0f )
                .setSize( SLIDER_WIDTH, 20 )
                .setPosition( 10, Y_POS )
                .setRangeValues( 0.0f, 1.0f )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        selectedParticles.getSurface().setMinHue( controlEvent.getArrayValue( 0 ) );
                        selectedParticles.getSurface().setMaxHue( controlEvent.getArrayValue( 1 ) );
                        selectedParticles.getColorMode().setRange( controlEvent.getArrayValue( 0 ), controlEvent.getArrayValue( 1 ) );
                        selectedParticles.getBloomModifier().getThresholdShader().setHue( controlEvent.getArrayValue( 0 ) );
                    }
                } );

        Y_POS += 50;
        intensitySlider = controlP5.addSlider( "Intensity" )
                .setRange( 0, 1 )
                .setSize( SLIDER_WIDTH, 20 )
                .setPosition( 10, Y_POS )
                .setValue( 1.0f )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        selectedParticles.setIntensity( controlEvent.getValue( ) );
                    }
                } );

        //controlP5.loadProperties();
    }

    private boolean getBoolFromFloat ( float _f ) {
        return _f != 0;
    }



    public static Slider getSliderById ( Main parent, VisualParameterEnum visualParameter ) {
        switch( visualParameter ) {
            case M:
                return parent.controlFrame.mSlider;
            case N:
                return parent.controlFrame.nSlider;
            default:
                System.err.println( "Somethings wrong with the values mapped to gui controls" );
        }

        return null;
    }

    public static Main.ChladniFormId getChladniFormId ( Main parent, ChladniParticles chladniParticles ) {
        for( Map.Entry< Main.ChladniFormId, ChladniParticles> pa : parent.chladniForms.entrySet() ) {
            if( pa.getValue().equals( chladniParticles ) ) {
                return pa.getKey();
            }
        }
        return null;
    }


    public void draw () {
        background( 0 );

        if( selectedParticles != null ) {
            mSlider.setValue( selectedParticles.getSurface( ).getM( ) );
            nSlider.setValue( selectedParticles.getSurface( ).getN( ) );
            triangleScalesSlider.setValue( selectedParticles.getSurface( ).getScale( ) );
            particleJumpynessSlider.setValue( selectedParticles.getParticleJumpyness( ) );
            particleOpacitySlider.setValue( selectedParticles.getParticleOpacity( ) );
            particleSizeSlider.setValue( selectedParticles.getParticleSize( ) );
            particleCountSlider.setValue( selectedParticles.getParticleCount( ) );
            backgroundOpacitySlider.setValue( selectedParticles.getBackgroundOpacity( ) );
            intensitySlider.setValue( selectedParticles.getSurface( ).getIntensity( ) );
            bloomBlurSizeSlider.setValue( selectedParticles.getBloomModifier( ).getBlurSize( ) );
            bloomSigmaSlider.setValue( selectedParticles.getBloomModifier( ).getBlurSigma( ) );
            bloomThresholdSlider.setValue( selectedParticles.getBloomModifier( ).getThresholdShader( ).getThreshold( ) );
        }
    }

    public ControlFrame ( Main theParent, int theWidth, int theHeight ) {
        parent = theParent;
        w = theWidth;
        h = theHeight;
    }

    public void saveParameters () {
        this.controlP5.saveProperties(  );
    }

    public void setPattern( ChladniParticles _pattern ) {
        this.selectedParticles = _pattern;

        mSlider.setValue( _pattern.getSurface().getM() );
        nSlider.setValue( _pattern.getSurface().getN() );
        triangleScalesSlider.setValue( _pattern.getSurface().getScale() );
        particleJumpynessSlider.setValue( _pattern.getParticleJumpyness( ) );
        particleOpacitySlider.setValue( _pattern.getParticleOpacity() );
        particleSizeSlider.setValue( _pattern.getParticleSize() );
        particleCountSlider.setValue( _pattern.getParticleCount() );
        backgroundOpacitySlider.setValue( _pattern.getBackgroundOpacity() );
        intensitySlider.setValue( _pattern.getSurface().getIntensity() );
        bloomBlurSizeSlider.setValue( _pattern.getBloomModifier().getBlurSize() );
        bloomSigmaSlider.setValue( _pattern.getBloomModifier().getBlurSigma() );
        bloomThresholdSlider.setValue( _pattern.getBloomModifier().getThresholdShader().getThreshold() );
    }

    public static ControlFrame addControlFrame ( Main pa, String theName, int theWidth, int theHeight ) {
        Frame f = new Frame( theName );
        ControlFrame p = new ControlFrame( pa, theWidth, theHeight );
        f.add( p );
        p.init( );
        f.setTitle( theName );
        f.setSize( p.w, p.h );
        f.setUndecorated( true );
        f.setLocation( 0, 150 );
        f.setResizable( false );
        f.setVisible( true );
        return p;
    }
}
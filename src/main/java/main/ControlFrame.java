package main;

import controlP5.*;
import osc.OscParameterInputEnum;
import pattern.*;
import processing.core.PApplet;

import java.awt.*;
import java.util.HashMap;

/**
 * The ControlFrame creates and handles everything on the control window.
 * Slider values are being updated upon received messages from the OscController.
 * Created by mar on 15.12.14.
 */
public class ControlFrame extends PApplet {

    public ChladniParticles selectedParticles;

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
    public Slider brightnessPatternSlider;
    public Slider bloomSigmaSlider;
    public Slider bloomBlurSizeSlider;
    public Slider bloomThresholdSlider;
    public Slider drawModeSlider;
    public Slider contrastSlider;
    public Slider brightnessSlider;
    public Range minMaxHue;

    public Toggle colorModeToggle;

    private HashMap< OscParameterInputEnum, Slider > parameters;

    public void setup () {
        size( w, h );
        controlP5 = new ControlP5( this );

        parameters = new HashMap<>();
        int X_POS = 10;
        int INCR = 20;
        int WIDTH = 10;
        Slider amplitudeSlider1 = controlP5.addSlider( "AM1" ).setPosition( X_POS, 10 ).setSize( WIDTH, 100 ).setRange( KimaConstants.MIN_AMPLITUDE, KimaConstants.MAX_AMPLITUDE ).setValue( 0.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
            }
        } );
        X_POS += INCR;
        Slider frequencySlider1 = controlP5.addSlider( "FQ1" ).setPosition( X_POS, 10 ).setSize( WIDTH, 100 ).setRange( KimaConstants.MIN_FREQUENCY, KimaConstants.MAX_FREQUENCY ).setValue( 0.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
            }
        } );
        X_POS += INCR;
        Slider attackSlider1 = controlP5.addSlider( "AT1" ).setPosition( X_POS, 10 ).setSize( WIDTH, 100 ).setRange( KimaConstants.ATTACK_MIN, KimaConstants.ATTACK_MAX ).setValue( 0.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
            }
        } );
        X_POS += INCR;
        Slider peakSlider1 = controlP5.addSlider( "PK1" ).setPosition( X_POS, 10 ).setSize( WIDTH, 100 ).setRange( KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX ).setValue( 0.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
            }
        } );
        X_POS += INCR;
        Slider fundamentalSlider1 = controlP5.addSlider( "FU1" ).setPosition( X_POS, 10 ).setSize( WIDTH, 100 ).setRange( KimaConstants.FUNDAMENTAL_MIN, KimaConstants.FUNDAMENTAL_MAX ).setValue( 0.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
            }
        } );
        X_POS += INCR;
        Slider newnoteSlider1 = controlP5.addSlider( "NN1" ).setPosition( X_POS, 10 ).setSize( WIDTH, 100 ).setRange( KimaConstants.NEWNOTE_MIN, KimaConstants.NEWNOTE_MAX ).setValue( 0.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
            }
        } );
        X_POS += INCR + 5;
        Slider amplitudeSlider2 = controlP5.addSlider( "AM2" ).setPosition( X_POS, 10 ).setSize( WIDTH, 100 ).setRange( KimaConstants.MIN_AMPLITUDE, KimaConstants.MAX_AMPLITUDE ).setValue( 0.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
            }
        } );
        X_POS += INCR;
        Slider frequencySlider2 = controlP5.addSlider( "FQ2" ).setPosition( X_POS, 10 ).setSize( WIDTH, 100 ).setRange( KimaConstants.MIN_FREQUENCY, KimaConstants.MAX_FREQUENCY ).setValue( 0.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
            }
        } );
        X_POS += INCR;
        Slider attackSlider2 = controlP5.addSlider( "AT2" ).setPosition( X_POS, 10 ).setSize( WIDTH, 100 ).setRange( KimaConstants.ATTACK_MIN, KimaConstants.ATTACK_MAX ).setValue( 0.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
            }
        } );
        X_POS += INCR;
        Slider peakSlider2 = controlP5.addSlider( "PK2" ).setPosition( X_POS, 10 ).setSize( WIDTH, 100 ).setRange( KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX ).setValue( 0.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
            }
        } );
        X_POS += INCR;
        Slider fundamentalSlider2 = controlP5.addSlider( "FU2" ).setPosition( X_POS, 10 ).setSize( WIDTH, 100 ).setRange( KimaConstants.FUNDAMENTAL_MIN, KimaConstants.FUNDAMENTAL_MAX ).setValue( 0.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                System.out.println( controlEvent.getValue( ) );
            }
        } );
        X_POS += INCR;
        Slider newnoteSlider2 = controlP5.addSlider( "NN2" ).setPosition( X_POS, 10 ).setSize( WIDTH, 100 ).setRange( KimaConstants.NEWNOTE_MIN, KimaConstants.NEWNOTE_MAX ).setValue( 0.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
            }
        } );
        X_POS += INCR + 5;
        Slider amplitudeSlider3 = controlP5.addSlider( "AM3" ).setPosition( X_POS, 10 ).setSize( WIDTH, 100 ).setRange( KimaConstants.MIN_AMPLITUDE, KimaConstants.MAX_AMPLITUDE ).setValue( 0.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
            }
        } );
        X_POS += INCR;
        Slider frequencySlider3 = controlP5.addSlider( "FQ3" ).setPosition( X_POS, 10 ).setSize( WIDTH, 100 ).setRange( KimaConstants.MIN_FREQUENCY, KimaConstants.MAX_FREQUENCY ).setValue( 0.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
            }
        } );
        X_POS += INCR;
        Slider attackSlider3 = controlP5.addSlider( "AT3" ).setPosition( X_POS, 10 ).setSize( WIDTH, 100 ).setRange( KimaConstants.ATTACK_MIN, KimaConstants.ATTACK_MAX ).setValue( 0.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
            }
        } );
        X_POS += INCR;
        Slider peakSlider3 = controlP5.addSlider( "PK3" ).setPosition( X_POS, 10 ).setSize( WIDTH, 100 ).setRange( KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX ).setValue( 0.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
            }
        } );
        X_POS += INCR;
        Slider fundamentalSlider3 = controlP5.addSlider( "FU3" ).setPosition( X_POS, 10 ).setSize( WIDTH, 100 ).setRange( KimaConstants.FUNDAMENTAL_MIN, KimaConstants.FUNDAMENTAL_MAX ).setValue( 0.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
            }
        } );
        X_POS += INCR;

        Slider newnoteSlider3 = controlP5.addSlider( "NN3" ).setPosition( X_POS, 10 ).setSize( WIDTH, 100 ).setRange( KimaConstants.NEWNOTE_MIN, KimaConstants.NEWNOTE_MAX ).setValue( 0.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
            }
        } );

        parameters.put( OscParameterInputEnum.AMPLITUDE_PARAMETER1, amplitudeSlider1 );
        parameters.put( OscParameterInputEnum.AMPLITUDE_PARAMETER2, amplitudeSlider2 );
        parameters.put( OscParameterInputEnum.AMPLITUDE_PARAMETER3, amplitudeSlider3 );
        parameters.put( OscParameterInputEnum.FREQUENCY_PARAMETER1, frequencySlider1 );
        parameters.put( OscParameterInputEnum.FREQUENCY_PARAMETER2, frequencySlider2 );
        parameters.put( OscParameterInputEnum.FREQUENCY_PARAMETER3, frequencySlider3 );
        parameters.put( OscParameterInputEnum.ATTACK_PARAMETER1, attackSlider1 );
        parameters.put( OscParameterInputEnum.ATTACK_PARAMETER2, attackSlider2 );
        parameters.put( OscParameterInputEnum.ATTACK_PARAMETER3, attackSlider3 );
        parameters.put( OscParameterInputEnum.PEAK_PARAMETER1, peakSlider1 );
        parameters.put( OscParameterInputEnum.PEAK_PARAMETER2, peakSlider2 );
        parameters.put( OscParameterInputEnum.PEAK_PARAMETER3, peakSlider3 );
        parameters.put( OscParameterInputEnum.FUNDAMENTAL_PARAMETER1, fundamentalSlider1 );
        parameters.put( OscParameterInputEnum.FUNDAMENTAL_PARAMETER2, fundamentalSlider2 );
        parameters.put( OscParameterInputEnum.FUNDAMENTAL_PARAMETER3, fundamentalSlider3 );
        parameters.put( OscParameterInputEnum.NEWNOTE_PARAMETER1, newnoteSlider1 );
        parameters.put( OscParameterInputEnum.NEWNOTE_PARAMETER2, newnoteSlider2 );
        parameters.put( OscParameterInputEnum.NEWNOTE_PARAMETER3, newnoteSlider3 );

        for( Slider s : parameters.values() ) {
            //s.getCaptionLabel().set( "" );
            s.getValueLabel().alignX( 20 );
        }

        int SLIDER_WIDTH = 300;
        float Y_POS = 160;

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
                selectedParticles.setParticleJumpyness( controlEvent.getValue( ) );
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

        colorModeToggle = controlP5.addToggle( "Color Mode Toggle" )
                .setPosition( 80, Y_POS )
                .setSize( 50, 20 )
                .setValue( true )
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

        drawModeSlider = controlP5.addSlider( "Draw Mode" )
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
                        parent.oscController.setUpdateDelay( ( long ) controlEvent.getValue( ) );
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
                        selectedParticles.getOpacityToHueShader().setMinHue( controlEvent.getArrayValue( 0 ) );
                        selectedParticles.getOpacityToHueShader().setMaxHue( controlEvent.getArrayValue( 1 ) );
                    }
                } );

        Y_POS += 50;

        brightnessSlider = controlP5.addSlider( "Brightness" )
                .setRange( 0, 10 )
                .setSize( SLIDER_WIDTH, 20 )
                .setPosition( 10, Y_POS )
                .setValue( 2.0f )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        selectedParticles.getBrightnessContrastShader().setBrightness( controlEvent.getValue() );
                    }
                } );

        Y_POS += 30;

        contrastSlider = controlP5.addSlider( "Contrast" )
                .setRange( 0, 10 )
                .setSize( SLIDER_WIDTH, 20 )
                .setPosition( 10, Y_POS )
                .setValue( 1.0f )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        selectedParticles.getBrightnessContrastShader().setContrast( controlEvent.getValue() );
                    }
                } );

        Y_POS += 50;
        brightnessPatternSlider = controlP5.addSlider( "Brightness" )
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

        Toggle t = controlP5.addToggle( "ContrastBrightness" ).setPosition( 10, Y_POS + 30 ).setSize( 20, 20 ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                selectedParticles.getBrightnessContrastShader().setEnabled( getBoolFromFloat( controlEvent.getValue() ) );
            }
        } );

    }

    private boolean getBoolFromFloat ( float _f ) {
        // TODO: this is disgusting
        return _f != 0;
    }

    public void draw () {
        background( 0 );
        stroke( 255 );
        text( "Channel 1 - Voice", 10, 140 );
        text( "Channel 2 - Perc.", 140, 140 );
        text( "Channel 1 - Organ", 270, 140 );
        line( 128, 5, 128, 140 );
        line( 253, 5, 253, 140 );

        if( selectedParticles != null ) {
            mSlider.setValue( selectedParticles.getSurface( ).getM( ) );
            nSlider.setValue( selectedParticles.getSurface( ).getN( ) );
            triangleScalesSlider.setValue( selectedParticles.getSurface( ).getScale( ) );
            particleJumpynessSlider.setValue( selectedParticles.getParticleJumpyness( ) );
            particleOpacitySlider.setValue( selectedParticles.getParticleOpacity( ) );
            particleSizeSlider.setValue( selectedParticles.getParticleSize( ) );
            particleCountSlider.setValue( selectedParticles.getParticleCount( ) );
            backgroundOpacitySlider.setValue( selectedParticles.getBackgroundOpacity( ) );
            brightnessPatternSlider.setValue( selectedParticles.getSurface( ).getIntensity( ) );
            bloomBlurSizeSlider.setValue( selectedParticles.getBloomModifier( ).getBlurSize( ) );
            bloomSigmaSlider.setValue( selectedParticles.getBloomModifier( ).getBlurSigma( ) );
            bloomThresholdSlider.setValue( selectedParticles.getBloomModifier( ).getThresholdShader( ).getThreshold( ) );
            if( selectedParticles.getOpacityToHueShader().isEnabled() ) {
                 minMaxHue.setRangeValues( selectedParticles.getOpacityToHueShader().getMinHue(), selectedParticles.getOpacityToHueShader().getMaxHue() );
            } else {
                minMaxHue.setRangeValues( selectedParticles.getColorMode().getMinHue(), selectedParticles.getColorMode().getMaxHue() );
            }
            contrastSlider.setValue( selectedParticles.getBrightnessContrastShader().getContrast() );
            brightnessSlider.setValue( selectedParticles.getBrightnessContrastShader().getBrightness() );


            float renderModeValue = 0;
            switch ( selectedParticles.getRenderMode() ) {
                case ORIGINAL:
                    renderModeValue = 0.5f;
                    break;
                case POINTS:
                    renderModeValue = 0.2f;
                    break;
                case LINES:
                    renderModeValue = 0.7f;
                    break;
            }
            drawModeSlider.setValue( renderModeValue );
        }
    }

    public void updateOscParameters ( OscParameterInputEnum _sip, float value ) {
        parameters.get( _sip ).setValue( value );
    }

    public ControlFrame ( Main theParent, int theWidth, int theHeight ) {
        parent = theParent;
        w = theWidth;
        h = theHeight;
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
        brightnessPatternSlider.setValue( _pattern.getSurface( ).getIntensity( ) );
        bloomBlurSizeSlider.setValue( _pattern.getBloomModifier().getBlurSize() );
        bloomSigmaSlider.setValue( _pattern.getBloomModifier().getBlurSigma() );
        bloomThresholdSlider.setValue( _pattern.getBloomModifier().getThresholdShader().getThreshold() );
        if( selectedParticles.getOpacityToHueShader().isEnabled() ) {
            minMaxHue.setRangeValues( _pattern.getOpacityToHueShader().getMinHue(), _pattern.getOpacityToHueShader().getMaxHue() );
        } else {
            minMaxHue.setRangeValues( _pattern.getColorMode().getMinHue(), _pattern.getColorMode().getMaxHue() );
        }
/*
        if( _pattern.getColorMode().getColorMode() == ColorModeEnum.MOON ) {
            colorModeToggle.setValue( false );
        } else {
            colorModeToggle.setValue( true );
        }*/

        contrastSlider.setValue( _pattern.getBrightnessContrastShader().getContrast() );
        brightnessSlider.setValue( _pattern.getBrightnessContrastShader().getBrightness() );

        float renderModeValue = 0;
        switch ( _pattern.getRenderMode() ) {
            case ORIGINAL:
                renderModeValue = 0.5f;
                break;
            case POINTS:
                renderModeValue = 0.2f;
                break;
            case LINES:
                renderModeValue = 0.7f;
                break;
        }
        drawModeSlider.setValue( renderModeValue );
    }

    public static ControlFrame addControlFrame ( Main pa, String theName, int theWidth, int theHeight ) {
        Frame f = new Frame( theName );
        ControlFrame p = new ControlFrame( pa, theWidth, theHeight );
        f.add( p );
        p.init( );
        f.setTitle( theName );
        f.setSize( p.w, p.h );
        f.setUndecorated( true );
        f.setLocation( 0, 0 );
        f.setResizable( false );
        f.setVisible( true );
        return p;
    }
}
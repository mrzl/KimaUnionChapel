package main;

import controlP5.*;
import midi.VisualParameterEnum;
import pattern.*;
import processing.core.PApplet;

import java.awt.*;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mar on 15.12.14.
 */
public class ControlFrame extends PApplet {

    public static ControlP5 controlP5;
    private Main parent;
    private int w, h;

    public Slider particleJumpynessSliderRect, particleJumpynessSliderTriangle, particleJumpynessSliderCicle;
    public Slider updateDelaySlider, rectNSlider, rectMSlider;
    public Slider circleNSlider, circleMSlider, triangleNSlider, triangleMSlider;
    public Slider particleOpacitySliderRect, particleOpacitySliderTriangle, particleOpacitySliderCircle;
    public Slider particleCountSliderRect, particleCountSliderTriangle, particleCountSliderCircle;
    public Slider particleSizeSliderRect, particleSizeSliderTriangle, particleSizeSliderCircle;
    public Slider backgroundOpacitySliderRect, backgroundOpacitySliderTriangle, backgroundOpacitySliderCircle;
    public Slider intensitySliderRecht, intensitySliderTriangle, intensitySliderCircle;
    public Slider bloomSigmaSliderRect, bloomSigmaSliderTriangle, bloomSigmaSliderCircle;
    public Slider bloomBlurSizeSliderRect, bloomBlurSizeSliderTriangle, bloomBlurSizeSliderCircle;
    public Slider bloomThresholdSliderRect, bloomThresholdSliderTriangle, bloomThresholdSliderCircle;
    public Range minMaxHue;

    public void setup () {
        size( w, h );
        controlP5 = new ControlP5( this );
        rectNSlider = controlP5.addSlider( "rectN" ).setRange( 0, 20 ).setSize( 300, 20 ).setPosition( 10, 10 ).setValue( 2.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                // parent.chladniRect.frequencyChanged( );
                ChladniRectangle r = ( ChladniRectangle ) parent.chladniForms.get( Main.ChladniFormId.RECT1 ).getSurface();
                r.setN( controlEvent.getValue( ) );
            }
        } );
        rectMSlider = controlP5.addSlider( "rectM" ).setRange( 0, 20 ).setSize( 300, 20 ).setPosition( 10, 50 ).setValue( 3.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                // parent.chladniRect.frequencyChanged( );
                ChladniRectangle r = ( ChladniRectangle ) parent.chladniForms.get( Main.ChladniFormId.RECT1 ).getSurface();
                r.setM( controlEvent.getValue( ) );
            }
        } );

        float circleY = 120;
        circleNSlider = controlP5.addSlider( "circleN" ).setRange( 0, 40 ).setSize( 300, 20 ).setPosition( 10, circleY ).setValue( 2.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                //parent.chladniTriangle.frequencyChanged( );
                ChladniTriangle c = ( ChladniTriangle ) parent.chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).getSurface();
                c.setN( controlEvent.getValue( ) );
            }
        } );

        circleY += 40;
        circleMSlider = controlP5.addSlider( "circleM" ).setRange( 0, 40 ).setSize( 300, 20 ).setPosition( 10, circleY ).setValue( 3.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                //parent.chladniTriangle.frequencyChanged( );
                ChladniTriangle c = ( ChladniTriangle ) parent.chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).getSurface();
                c.setM( controlEvent.getValue( ) );
            }
        } );
        circleY += 40;
        controlP5.addSlider( "circlePoles" ).setRange( 0, 40 ).setSize( 300, 20 ).setPosition( 10, circleY ).setValue( 33.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                //parent.chladniTriangle.frequencyChanged( );
                ChladniTriangle c = ( ChladniTriangle ) parent.chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).getSurface();
                c.setPoles( ( int ) ( controlEvent.getValue( ) ) );
            }
        } );
        circleY += 40;
        controlP5.addSlider( "circleScale" ).setRange( 0, 2 ).setSize( 300, 20 ).setPosition( 10, circleY ).setValue( 1.1f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                //parent.chladniTriangle.frequencyChanged( );
                ChladniTriangle c = ( ChladniTriangle ) parent.chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).getSurface();
                c.setScale( controlEvent.getValue( ) );
            }
        } );

        float realCircleY = 300;
        triangleNSlider = controlP5.addSlider( "realCircleN" ).setRange( 1, 20 ).setSize( 300, 20 ).setPosition( 10, realCircleY ).setValue( 2.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                //parent.chladniCircle.frequencyChanged( );
                ChladniCircle realCircle = ( ChladniCircle ) parent.chladniForms.get( Main.ChladniFormId.CIRCLE1 ).getSurface();
                realCircle.setN( controlEvent.getValue( ) );
            }
        } );
        realCircleY += 40;
        triangleMSlider = controlP5.addSlider( "realCircleM" ).setRange( 1, 14 ).setSize( 300, 20 ).setPosition( 10, realCircleY ).setValue( 3.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                //parent.chladniCircle.frequencyChanged( );
                ChladniCircle realCircle = ( ChladniCircle ) parent.chladniForms.get( Main.ChladniFormId.CIRCLE1 ).getSurface();
                realCircle.setM( controlEvent.getValue( ) );
            }
        } );

        float generalY = 400;
        createJumpynessSliders( generalY );

        generalY += 30;
        createParticleSizeSliders( generalY );

        generalY += 30;
        createParticleCountSliders( generalY );

        generalY += 30;
        createParticleOpacitySliders( generalY );

        generalY += 30;

        createBackgroundOpacitySliders( generalY );

        generalY += 30;

        createBloomSizeSlidera( generalY );

        generalY += 30;

        createBloomSigmaSliders( generalY );

        generalY += 30;

        createBloomThresholdSliders( generalY );

        generalY += 40;

        addToggles( generalY );

        generalY += 50;
        controlP5.addSlider( "threshold" )
                .setRange( 0, 1 )
                .setSize( 300, 20 )
                .setPosition( 10, generalY )
                .setValue( 0.2f )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        Iterator it = parent.chladniForms.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry pairs = ( Map.Entry ) it.next( );
                            ChladniParticles p = ( ChladniParticles ) pairs.getValue();
                            p.getMetaBallModifier( ).setThreshold( controlEvent.getValue( ) );
                        }
                    }
                } );

        generalY += 50;
        updateDelaySlider = controlP5.addSlider( "updateDelay" )
                .setRange( 0, 2000 )
                .setSize( 300, 20 )
                .setPosition( 10, generalY )
                .setValue( 100 )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        parent.soundController.setUpdateDelay( ( long ) controlEvent.getValue( ) );
                    }
                } );

        generalY += 50;

        minMaxHue = controlP5.addRange( "minHue" )
                .setRange( 0.0f, 1.0f )
                .setSize( 300, 20 )
                .setPosition( 10, generalY )
                .setRangeValues( 0.0f, 1.0f )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        Iterator it = parent.chladniForms.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry pairs = ( Map.Entry ) it.next( );
                            ChladniParticles p = ( ChladniParticles ) pairs.getValue();
                            p.getColorMode( ).setRange( controlEvent.getArrayValue( 0 ), controlEvent.getArrayValue( 1 ) );
                            parent.chladniForms.get( Main.ChladniFormId.RECT1 ).getBloomModifier().getThresholdShader().setHue( controlEvent.getArrayValue( 0 ) );
                            parent.chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).getBloomModifier().getThresholdShader().setHue( controlEvent.getArrayValue( 0 ) );
                            parent.chladniForms.get( Main.ChladniFormId.CIRCLE1 ).getBloomModifier().getThresholdShader().setHue( controlEvent.getArrayValue( 0 ) );
                        }
                    }
                } );

        generalY += 50;

        addIntensitySliders( generalY );

        controlP5.loadProperties();
    }

    private void createBloomThresholdSliders ( float generalY ) {
        bloomThresholdSliderRect = controlP5.addSlider( "bloomThresholdRect" )
                .setPosition( 10, generalY )
                .setSize( 100, 20 )
                .setRange( 0, 1 )
                .setValue( 0.1f )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        parent.chladniForms.get( Main.ChladniFormId.RECT1 ).getBloomModifier().setThreshold( controlEvent.getValue() );
                    }
                } );

        bloomThresholdSliderTriangle = controlP5.addSlider( "bloomThresholdTriangle" )
                .setPosition( 130, generalY )
                .setSize( 100, 20 )
                .setRange( 0, 1 )
                .setValue( 0.1f )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        parent.chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).getBloomModifier().setThreshold( controlEvent.getValue() );
                    }
                } );

        bloomThresholdSliderCircle = controlP5.addSlider( "bloomThresholdCircle" )
                .setPosition( 240, generalY )
                .setSize( 100, 20 )
                .setRange( 0, 1 )
                .setValue( 0.1f )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        parent.chladniForms.get( Main.ChladniFormId.CIRCLE1 ).getBloomModifier().setThreshold( controlEvent.getValue() );
                    }
                } );
    }

    private void createBloomSigmaSliders ( float generalY ) {
        bloomSigmaSliderRect = controlP5.addSlider( "bloomSigmaRect" )
                .setPosition( 10, generalY )
                .setSize( 100, 20 )
                .setRange( 1, 80 )
                .setValue( 4 )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        parent.chladniForms.get( Main.ChladniFormId.RECT1 ).getBloomModifier().setBlurSigma( controlEvent.getValue() );
                    }
                } );

        bloomSigmaSliderTriangle = controlP5.addSlider( "bloomSigmaTriangle" )
                .setPosition( 130, generalY )
                .setSize( 100, 20 )
                .setRange( 1, 80 )
                .setValue( 4 )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        parent.chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).getBloomModifier().setBlurSigma( controlEvent.getValue() );
                    }
                } );

        bloomSigmaSliderCircle = controlP5.addSlider( "bloomSigmaCircle" )
                .setPosition( 240, generalY )
                .setSize( 100, 20 )
                .setRange( 1, 80 )
                .setValue( 4 )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        parent.chladniForms.get( Main.ChladniFormId.CIRCLE1 ).getBloomModifier().setBlurSigma( controlEvent.getValue() );
                    }
                } );
    }

    private void createBloomSizeSlidera ( float generalY ) {
        bloomBlurSizeSliderRect = controlP5.addSlider( "bloomBlurSizeRect" )
                .setPosition( 10, generalY )
                .setSize( 100, 20 )
                .setRange( 1, 80 )
                .setValue( 28 )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        parent.chladniForms.get( Main.ChladniFormId.RECT1 ).getBloomModifier().setBlurSize( ( int )( controlEvent.getValue() ) );
                    }
                } );

        bloomBlurSizeSliderTriangle = controlP5.addSlider( "bloomBlurSizeTriangle" )
                .setPosition( 130, generalY )
                .setSize( 100, 20 )
                .setRange( 1, 80 )
                .setValue( 28 )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        parent.chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).getBloomModifier().setBlurSize( ( int )( controlEvent.getValue() ) );
                    }
                } );

        bloomBlurSizeSliderCircle = controlP5.addSlider( "bloomBlurSizeCircle" )
                .setPosition( 240, generalY )
                .setSize( 100, 20 )
                .setRange( 1, 80 )
                .setValue( 28 )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        parent.chladniForms.get( Main.ChladniFormId.CIRCLE1 ).getBloomModifier().setBlurSize( ( int )( controlEvent.getValue() ) );
                    }
                } );
    }

    private void addIntensitySliders ( float generalY ) {
        intensitySliderRecht = controlP5.addSlider( "intensityRect" )
                .setRange( 0, 1 )
                .setSize( 100, 20 )
                .setPosition( 10, generalY )
                .setValue( 1.0f )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        parent.chladniForms.get( Main.ChladniFormId.RECT1 ).setIntensity( controlEvent.getValue() );
                    }
                } );

        intensitySliderTriangle = controlP5.addSlider( "intensityTriangle" )
                .setRange( 0, 1 )
                .setSize( 100, 20 )
                .setPosition( 130, generalY )
                .setValue( 1.0f )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        parent.chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).setIntensity( controlEvent.getValue() );
                    }
                } );

        intensitySliderCircle = controlP5.addSlider( "intensityCircle" )
                .setRange( 0, 1 )
                .setSize( 100, 20 )
                .setPosition( 240, generalY )
                .setValue( 1.0f )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        parent.chladniForms.get( Main.ChladniFormId.CIRCLE1 ).setIntensity( controlEvent.getValue() );
                    }
                } );
    }

    private void createBackgroundOpacitySliders( float generalY ) {
        backgroundOpacitySliderRect = controlP5.addSlider( "backgroundOpacityRect" ).setRange( 0, 255 ).setSize( 100, 20 ).setPosition( 10, generalY ).setValue( 40 ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniForms.get( Main.ChladniFormId.RECT1 ).setMotionBlurAmount( controlEvent.getValue( ) );
            }
        } );

        backgroundOpacitySliderTriangle = controlP5.addSlider( "backgroundOpacityTriangle" ).setRange( 0, 255 ).setSize( 100, 20 ).setPosition( 130, generalY ).setValue( 40 ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).setMotionBlurAmount( controlEvent.getValue( ) );
            }
        } );

        backgroundOpacitySliderCircle = controlP5.addSlider( "backgroundOpacityCircle" ).setRange( 0, 255 ).setSize( 100, 20 ).setPosition( 250, generalY ).setValue( 40 ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniForms.get( Main.ChladniFormId.CIRCLE1 ).setMotionBlurAmount( controlEvent.getValue( ) );
            }
        } );
    }

    private void addToggles ( float generalY ) {
        controlP5.addToggle( "thresholdToggle" )
                .setPosition( 10, generalY )
                .setSize( 50, 20 )
                .setValue( false )
                .setMode( ControlP5.SWITCH )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        Iterator it = parent.chladniForms.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry pairs = ( Map.Entry ) it.next( );
                            ChladniParticles p = ( ChladniParticles ) pairs.getValue();
                            p.getMetaBallModifier( ).setEnabled( getBoolFromFloat( controlEvent.getValue( ) ) );
                        }
                    }
                } )
        ;

        controlP5.addToggle( "colorModeToggle" )
                .setPosition( 80, generalY )
                .setSize( 50, 20 )
                .setValue( false )
                .setMode( ControlP5.SWITCH )
                .addListener( new ControlListener( ) {

                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        if ( getBoolFromFloat( controlEvent.getValue( ) ) ) {
                            Iterator it = parent.chladniForms.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry pairs = ( Map.Entry ) it.next( );
                                ChladniParticles p = ( ChladniParticles ) pairs.getValue();
                                p.setColorModeEnum( ColorModeEnum.MOON );
                            }
                        } else {
                            Iterator it = parent.chladniForms.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry pairs = ( Map.Entry ) it.next( );
                                ChladniParticles p = ( ChladniParticles ) pairs.getValue();
                                p.setColorModeEnum( ColorModeEnum.VELOCITIES );
                            }
                        }
                    }
                } );

        controlP5.addToggle( "bloomToggle" )
                .setPosition( 150, generalY )
                .setSize( 50, 20 )
                .setValue( false )
                .setMode( ControlP5.SWITCH )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        Iterator it = parent.chladniForms.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry pairs = ( Map.Entry ) it.next( );
                            ChladniParticles p = ( ChladniParticles ) pairs.getValue();
                            p.getBloomModifier( ).setEnabled( getBoolFromFloat( controlEvent.getValue( ) ) );
                        }
                    }
                } );

        controlP5.addSlider( "drawMode" )
                .setPosition( 220, generalY )
                .setSize( 50, 20 )
                .setRange( 0, 1 )
                .setValue( 0.0f )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        float v = controlEvent.getValue( );

                        if ( v < 0.3f ) {
                            Iterator it = parent.chladniForms.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry pairs = ( Map.Entry ) it.next( );
                                ChladniParticles p = ( ChladniParticles ) pairs.getValue();
                                p.setRenderMode( RenderMode.POINTS );
                            }
                        } else if( v > 0.6f ) {
                            Iterator it = parent.chladniForms.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry pairs = ( Map.Entry ) it.next( );
                                ChladniParticles p = ( ChladniParticles ) pairs.getValue();
                                p.setRenderMode( RenderMode.LINES );
                            }
                        } else {
                            Iterator it = parent.chladniForms.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry pairs = ( Map.Entry ) it.next( );
                                ChladniParticles p = ( ChladniParticles ) pairs.getValue();
                                p.setRenderMode( RenderMode.ORIGINAL );
                            }
                        }
                    }
                } );

        controlP5.addToggle( "behaviorMode" ).setPosition( 300, generalY ).setSize( 50, 20 ).setValue( false ).addListener(new ControlListener() {
            @Override
            public void controlEvent( ControlEvent controlEvent ) {
                boolean v = getBoolFromFloat( controlEvent.getValue() );
                if ( v ) {
                    parent.chladniForms.get( Main.ChladniFormId.RECT1 ).setBehaviorMode( BehaviorMode.REGULAR );
                    parent.chladniForms.get( Main.ChladniFormId.CIRCLE1 ).setBehaviorMode( BehaviorMode.REGULAR );
                    parent.chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).setBehaviorMode( BehaviorMode.REGULAR );
                } else {
                    parent.chladniForms.get( Main.ChladniFormId.RECT1 ).setBehaviorMode( BehaviorMode.CENTER_OUTWARDS );
                    parent.chladniForms.get( Main.ChladniFormId.CIRCLE1 ).setBehaviorMode( BehaviorMode.CENTER_OUTWARDS );
                    parent.chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).setBehaviorMode( BehaviorMode.CENTER_OUTWARDS );
                }
            }
        });
    }

    private boolean getBoolFromFloat ( float _f ) {
        return _f != 0;
    }

    private void createParticleOpacitySliders ( float generalY ) {
        particleOpacitySliderRect = controlP5.addSlider( "particleOpacityRect" ).setRange( 0, 1 ).setSize( 100, 20 ).setPosition( 10, generalY ).setValue( 0.6f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniForms.get( Main.ChladniFormId.RECT1 ).setParticleOpacity( controlEvent.getValue( ) );
            }
        } );

        particleOpacitySliderTriangle = controlP5.addSlider( "particleOpacityTriangle" ).setRange( 0, 1 ).setSize( 100, 20 ).setPosition( 130, generalY ).setValue( 0.6f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).setParticleOpacity( controlEvent.getValue( ) );
            }
        } );

        particleOpacitySliderCircle = controlP5.addSlider( "particleOpacityCircle" ).setRange( 0, 1 ).setSize( 100, 20 ).setPosition( 250, generalY ).setValue( 0.6f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniForms.get( Main.ChladniFormId.CIRCLE1 ).setParticleOpacity( controlEvent.getValue( ) );
            }
        } );
    }

    private void createParticleCountSliders ( float generalY ) {
        particleCountSliderRect = controlP5.addSlider( "particleCountRect" ).setRange( 0, 30000 ).setSize( 100, 20 ).setPosition( 10, generalY ).setValue( 10000.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniForms.get( Main.ChladniFormId.RECT1 ).setParticleCount( ( int ) controlEvent.getValue( ) );
            }
        } );

        particleCountSliderTriangle = controlP5.addSlider( "particleCountTriangle" ).setRange( 0, 30000 ).setSize( 100, 20 ).setPosition( 130, generalY ).setValue( 10000.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).setParticleCount( ( int ) controlEvent.getValue( ) );
            }
        } );

        particleCountSliderCircle = controlP5.addSlider( "particleCountCircle" ).setRange( 0, 30000 ).setSize( 100, 20 ).setPosition( 250, generalY ).setValue( 10000.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniForms.get( Main.ChladniFormId.CIRCLE1 ).setParticleCount( ( int ) controlEvent.getValue( ) );
            }
        } );
    }

    private void createParticleSizeSliders ( float generalY ) {
        particleSizeSliderRect = controlP5.addSlider( "particleSizeRect" ).setRange( 0, 80 ).setSize( 100, 20 ).setPosition( 10, generalY ).setValue( 3.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniForms.get( Main.ChladniFormId.RECT1 ).setParticleSize( controlEvent.getValue( ) );
            }
        } );

        particleSizeSliderTriangle = controlP5.addSlider( "particleSizeTriangle" ).setRange( 0, 80 ).setSize( 100, 20 ).setPosition( 130, generalY ).setValue( 3.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).setParticleSize( controlEvent.getValue( ) );
            }
        } );

        particleSizeSliderCircle = controlP5.addSlider( "particleSizeCircle" ).setRange( 0, 80 ).setSize( 100, 20 ).setPosition( 250, generalY ).setValue( 3.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniForms.get( Main.ChladniFormId.CIRCLE1 ).setParticleSize( controlEvent.getValue( ) );
            }
        } );
    }

    private void createJumpynessSliders ( float generalY ) {
        particleJumpynessSliderRect = controlP5.addSlider( "jumpynessRect" ).setRange( 0, 200 ).setSize( 100, 20 ).setPosition( 10, generalY ).setValue( 30.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniForms.get( Main.ChladniFormId.RECT1 ).setRebuildSpeed( controlEvent.getValue( ) );
            }
        } );

        particleJumpynessSliderTriangle = controlP5.addSlider( "jumpynessTriangle" ).setRange( 0, 200 ).setSize( 100, 20 ).setPosition( 130, generalY ).setValue( 30.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).setRebuildSpeed( controlEvent.getValue( ) );
            }
        } );

        particleJumpynessSliderCicle = controlP5.addSlider( "jumpynessCircle" ).setRange( 0, 200 ).setSize( 100, 20 ).setPosition( 250, generalY ).setValue( 30.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniForms.get( Main.ChladniFormId.CIRCLE1 ).setRebuildSpeed( controlEvent.getValue( ) );
            }
        } );
    }

    public static Slider getSliderById ( Main parent, Main.ChladniFormId currentId, VisualParameterEnum visualParameter ) {
        switch( currentId ) {
            case RECT1:
                switch( visualParameter ) {
                    case M:
                        return parent.controlFrame.rectMSlider;
                    case N:
                        return parent.controlFrame.rectNSlider;
                }
            case CIRCLE1:
                switch( visualParameter ) {
                    case M:
                        return parent.controlFrame.circleMSlider;
                    case N:
                        return parent.controlFrame.circleNSlider;
                }
            case TRIANGLE1:
                switch( visualParameter ) {
                    case M:
                        return parent.controlFrame.triangleMSlider;
                    case N:
                        return parent.controlFrame.triangleNSlider;
                }
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
    }

    public ControlFrame ( Main theParent, int theWidth, int theHeight ) {
        parent = theParent;
        w = theWidth;
        h = theHeight;
    }

    public void saveParameters () {
        this.controlP5.saveProperties(  );
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
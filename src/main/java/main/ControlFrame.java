package main;

import controlP5.ControlEvent;
import controlP5.ControlListener;
import controlP5.ControlP5;
import controlP5.Slider;
import pattern.ChladniTriangle;
import pattern.ChladniCircle;
import pattern.ChladniRectangle;
import pattern.ColorModeEnum;
import processing.core.PApplet;

import java.awt.*;

/**
 * Created by mar on 15.12.14.
 */
public class ControlFrame extends PApplet {

    public static ControlP5 controlP5;
    private Main parent;
    private int w, h;

    public Slider particleJumpynessSliderRect;

    public void setup () {
        size( w, h );
        controlP5 = new ControlP5( this );
        controlP5.addSlider( "rectN" ).setRange( 0, 40 ).setSize( 300, 20 ).setPosition( 10, 10 ).setValue( 2.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniRect.frequencyChanged( );
                ChladniRectangle r = ( ChladniRectangle ) parent.chladniRect.getSurface( );
                r.setN( controlEvent.getValue( ) );
            }
        } );
        controlP5.addSlider( "rectM" ).setRange( 0, 40 ).setSize( 300, 20 ).setPosition( 10, 50 ).setValue( 3.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniRect.frequencyChanged( );
                ChladniRectangle r = ( ChladniRectangle ) parent.chladniRect.getSurface( );
                r.setM( controlEvent.getValue( ) );
            }
        } );

        float circleY = 120;
        controlP5.addSlider( "circleN" ).setRange( 0, 40 ).setSize( 300, 20 ).setPosition( 10, circleY ).setValue( 2.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniTriangle.frequencyChanged( );
                ChladniTriangle c = ( ChladniTriangle ) parent.chladniTriangle.getSurface( );
                c.setN( controlEvent.getValue( ) );
            }
        } );

        circleY += 40;
        controlP5.addSlider( "circleM" ).setRange( 0, 40 ).setSize( 300, 20 ).setPosition( 10, circleY ).setValue( 3.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniTriangle.frequencyChanged( );
                ChladniTriangle c = ( ChladniTriangle ) parent.chladniTriangle.getSurface( );
                c.setM( controlEvent.getValue( ) );
            }
        } );
        circleY += 40;
        controlP5.addSlider( "circlePoles" ).setRange( 0, 40 ).setSize( 300, 20 ).setPosition( 10, circleY ).setValue( 33.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniTriangle.frequencyChanged( );
                ChladniTriangle c = ( ChladniTriangle ) parent.chladniTriangle.getSurface( );
                c.setPoles( ( int ) ( controlEvent.getValue( ) ) );
            }
        } );
        circleY += 40;
        controlP5.addSlider( "circleScale" ).setRange( 0, 2 ).setSize( 300, 20 ).setPosition( 10, circleY ).setValue( 1.1f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniTriangle.frequencyChanged( );
                ChladniTriangle c = ( ChladniTriangle ) parent.chladniTriangle.getSurface( );
                c.setScale( controlEvent.getValue( ) );
            }
        } );

        float realCircleY = 300;
        controlP5.addSlider( "realCircleN" ).setRange( 1, 20 ).setSize( 300, 20 ).setPosition( 10, realCircleY ).setValue( 2.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniCircle.frequencyChanged( );
                ChladniCircle realCircle = ( ChladniCircle ) parent.chladniCircle.getSurface( );
                realCircle.setN( controlEvent.getValue( ) );
            }
        } );
        realCircleY += 40;
        controlP5.addSlider( "realCircleM" ).setRange( 1, 14 ).setSize( 300, 20 ).setPosition( 10, realCircleY ).setValue( 3.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniCircle.frequencyChanged( );
                ChladniCircle realCircle = ( ChladniCircle ) parent.chladniCircle.getSurface( );
                realCircle.setM( controlEvent.getValue( ) );
            }
        } );

        float generalY = 450;
        createJumpynessSliders( generalY );

        generalY += 30;
        createParticleSizeSliders( generalY );

        generalY += 30;
        createParticleCountSliders( generalY );

        generalY += 30;
        createParticleOpacitySliders( generalY );

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
                        parent.getMetaBallModifier( ).setThreshold( controlEvent.getValue( ) );
                    }
                } );

        generalY += 50;
        controlP5.addSlider( "updateDelay" )
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

        controlP5.addRange( "minHue" )
                .setRange( 0.0f, 1.0f )
                .setSize( 300, 20 )
                .setPosition( 10, generalY )
                .setRangeValues( 0.0f, 1.0f )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        parent.chladniRect.getColorMode( ).setRange( controlEvent.getArrayValue( 0 ), controlEvent.getArrayValue( 1 ) );
                        parent.chladniCircle.getColorMode( ).setRange( controlEvent.getArrayValue( 0 ), controlEvent.getArrayValue( 1 ) );
                        parent.chladniTriangle.getColorMode( ).setRange( controlEvent.getArrayValue( 0 ), controlEvent.getArrayValue( 1 ) );
                    }
                } );


        controlP5.loadProperties( "control.properties" );
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
                        parent.getMetaBallModifier( ).setEnabled( getBoolFromFloat( controlEvent.getValue( ) ) );
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
                            parent.chladniRect.setColorModeEnum( ColorModeEnum.MOON );
                            parent.chladniTriangle.setColorModeEnum( ColorModeEnum.MOON );
                            parent.chladniCircle.setColorModeEnum( ColorModeEnum.MOON );
                        } else {
                            parent.chladniRect.setColorModeEnum( ColorModeEnum.VELOCITIES );
                            parent.chladniTriangle.setColorModeEnum( ColorModeEnum.VELOCITIES );
                            parent.chladniCircle.setColorModeEnum( ColorModeEnum.VELOCITIES );
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
                        parent.getBloomModifier( ).setEnabled( getBoolFromFloat( controlEvent.getValue( ) ) );
                    }
                } );

        controlP5.addToggle( "original" )
                .setPosition( 220, generalY )
                .setSize( 50, 20 )
                .setValue( false )
                .setMode( ControlP5.SWITCH )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        parent.drawSurface = getBoolFromFloat( controlEvent.getValue( ) );
                    }
                } );

        controlP5.addToggle( "motionBlur" )
                .setPosition( 290, generalY )
                .setSize( 50, 20 )
                .setValue( false )
                .setMode( ControlP5.SWITCH )
                .addListener( new ControlListener( ) {
                    @Override
                    public void controlEvent ( ControlEvent controlEvent ) {
                        parent.doMotionBlur = getBoolFromFloat( controlEvent.getValue( ) );
                    }
                } );
    }

    private boolean getBoolFromFloat ( float _f ) {
        return _f != 0;
    }

    private void createParticleOpacitySliders ( float generalY ) {
        Slider particleOpacitySliderRect = controlP5.addSlider( "particleOpacityRect" ).setRange( 0, 1 ).setSize( 100, 20 ).setPosition( 10, generalY ).setValue( 0.6f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniRect.setParticleOpacity( controlEvent.getValue( ) );
            }
        } );

        Slider particleOpacitySliderTriangle = controlP5.addSlider( "particleOpacityTriangle" ).setRange( 0, 1 ).setSize( 100, 20 ).setPosition( 130, generalY ).setValue( 0.6f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniTriangle.setParticleOpacity( controlEvent.getValue( ) );
            }
        } );

        Slider particleOpacitySliderCircle = controlP5.addSlider( "particleOpacityCircle" ).setRange( 0, 1 ).setSize( 100, 20 ).setPosition( 250, generalY ).setValue( 0.6f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniCircle.setParticleOpacity( controlEvent.getValue( ) );
            }
        } );
    }

    private void createParticleCountSliders ( float generalY ) {
        Slider particleCountSliderRect = controlP5.addSlider( "particleCountRect" ).setRange( 0, 30000 ).setSize( 100, 20 ).setPosition( 10, generalY ).setValue( 10000.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniRect.setParticleCount( ( int ) controlEvent.getValue( ) );
            }
        } );

        Slider particleCountSliderTriangle = controlP5.addSlider( "particleCountTriangle" ).setRange( 0, 30000 ).setSize( 100, 20 ).setPosition( 130, generalY ).setValue( 10000.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniTriangle.setParticleCount( ( int ) controlEvent.getValue( ) );
            }
        } );

        Slider particleCountSliderCircle = controlP5.addSlider( "particleCountCircle" ).setRange( 0, 30000 ).setSize( 100, 20 ).setPosition( 250, generalY ).setValue( 10000.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniCircle.setParticleCount( ( int ) controlEvent.getValue( ) );
            }
        } );
    }

    private void createParticleSizeSliders ( float generalY ) {
        Slider particleSizeSliderRect = controlP5.addSlider( "particleSizeRect" ).setRange( 0, 30 ).setSize( 100, 20 ).setPosition( 10, generalY ).setValue( 3.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniRect.setParticleSize( controlEvent.getValue( ) );
            }
        } );

        Slider particleSizeSliderTriangle = controlP5.addSlider( "particleSizeTriangle" ).setRange( 0, 30 ).setSize( 100, 20 ).setPosition( 130, generalY ).setValue( 3.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniTriangle.setParticleSize( controlEvent.getValue( ) );
            }
        } );

        Slider particleSizeSliderCircle = controlP5.addSlider( "particleSizeCircle" ).setRange( 0, 30 ).setSize( 100, 20 ).setPosition( 250, generalY ).setValue( 3.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniCircle.setParticleSize( controlEvent.getValue( ) );
            }
        } );
    }

    private void createJumpynessSliders ( float generalY ) {
        particleJumpynessSliderRect = controlP5.addSlider( "jumpynessRect" ).setRange( 0, 200 ).setSize( 100, 20 ).setPosition( 10, generalY ).setValue( 30.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniRect.setRebuildSpeed( controlEvent.getValue( ) );
            }
        } );

        Slider particleJumpynessSliderTriangle = controlP5.addSlider( "jumpynessTriangle" ).setRange( 0, 200 ).setSize( 100, 20 ).setPosition( 130, generalY ).setValue( 30.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniTriangle.setRebuildSpeed( controlEvent.getValue( ) );
            }
        } );

        Slider particleJumpynessSliderCicle = controlP5.addSlider( "jumpynessCircle" ).setRange( 0, 200 ).setSize( 100, 20 ).setPosition( 250, generalY ).setValue( 30.0f ).addListener( new ControlListener( ) {
            @Override
            public void controlEvent ( ControlEvent controlEvent ) {
                parent.chladniCircle.setRebuildSpeed( controlEvent.getValue( ) );
            }
        } );
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
        this.controlP5.saveProperties( "control.properties" );
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
package osc.debug;

import controlP5.ControlP5;
import controlP5.Slider;
import main.Main;
import osc.SoundInputParameter;
import osc.SoundInputParameterEnum;
import processing.core.PApplet;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mrzl on 08.01.2015.
 */
public class OscParameterDisplay extends PApplet {

    private Main parent;
    private ControlP5 cp5;
    private HashMap< SoundInputParameterEnum, Slider > parameters;
    private int w, h;

    public void setup () {
        size( 600, 300 );
        cp5 = new ControlP5( this );
        cp5.setAutoDraw( true );
        parameters = new HashMap<>();

        Slider amplitudeSlider1 = cp5.addSlider( "AM1" ).setPosition( 10, 10 ).setSize( 15, 100 ).setRange( 0, 20 ).setValue( 0.0f );
        Slider amplitudeSlider2 = cp5.addSlider( "AM2" ).setPosition( 45, 10 ).setSize( 15, 100 ).setRange( 0, 20 ).setValue( 0.0f );
        Slider amplitudeSlider3 = cp5.addSlider( "AM3" ).setPosition( 80, 10 ).setSize( 15, 100 ).setRange( 0, 20 ).setValue( 0.0f );

        Slider frequencySlider1 = cp5.addSlider( "FQ1" ).setPosition( 130, 10 ).setSize( 15, 100 ).setRange( 200, 10000 ).setValue( 0.0f );
        Slider frequencySlider2 = cp5.addSlider( "FQ2" ).setPosition( 165, 10 ).setSize( 15, 100 ).setRange( 200, 10000 ).setValue( 0.0f );
        Slider frequencySlider3 = cp5.addSlider( "FQ3" ).setPosition( 200, 10 ).setSize( 15, 100 ).setRange( 200, 10000 ).setValue( 0.0f );

        Slider attackSlider1 = cp5.addSlider( "AT1" ).setPosition( 250, 10 ).setSize( 15, 100 ).setRange( 0, 1 ).setValue( 0.0f );
        Slider attackSlider2 = cp5.addSlider( "AT2" ).setPosition( 285, 10 ).setSize( 15, 100 ).setRange( 0, 1 ).setValue( 0.0f );
        Slider attackSlider3 = cp5.addSlider( "AT3" ).setPosition( 320, 10 ).setSize( 15, 100 ).setRange( 0, 1 ).setValue( 0.0f );

        parameters.put( SoundInputParameterEnum.AMPLITUDE_PARAMETER1, amplitudeSlider1 );
        parameters.put( SoundInputParameterEnum.AMPLITUDE_PARAMETER2, amplitudeSlider2 );
        parameters.put( SoundInputParameterEnum.AMPLITUDE_PARAMETER3, amplitudeSlider3 );
        parameters.put( SoundInputParameterEnum.FREQUENCY_PARAMETER1, frequencySlider1 );
        parameters.put( SoundInputParameterEnum.FREQUENCY_PARAMETER2, frequencySlider2 );
        parameters.put( SoundInputParameterEnum.FREQUENCY_PARAMETER3, frequencySlider3 );
        parameters.put( SoundInputParameterEnum.ATTACK_PARAMETER1, attackSlider1 );
        parameters.put( SoundInputParameterEnum.ATTACK_PARAMETER2, attackSlider2 );
        parameters.put( SoundInputParameterEnum.ATTACK_PARAMETER3, attackSlider3 );
    }

    public void draw () {
        background( 0 );
    }

    public void updateParameter ( SoundInputParameterEnum _sip, float value ) {
        parameters.get( _sip ).setValue( value );
    }

    public OscParameterDisplay ( Main theParent, int theWidth, int theHeight ) {
        parent = theParent;
        w = theWidth;
        h = theHeight;
    }

    public static OscParameterDisplay addControlFrame ( Main pa, String theName, int theWidth, int theHeight ) {
        Frame f = new Frame( theName );
        OscParameterDisplay p = new OscParameterDisplay( pa, theWidth, theHeight );
        f.add( p );
        p.init( );
        f.setTitle( theName );
        f.setSize( p.w, p.h );
        f.setLocation( 0, 0 );
        f.setUndecorated( true );
        f.setResizable( false );
        f.setVisible( true );
        return p;
    }
}

package osc.debug;

import controlP5.ControlP5;
import controlP5.Slider;
import main.KimaConstants;
import osc.SoundInputParameterEnum;
import processing.core.PApplet;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by mrzl on 08.01.2015.
 */
public class OscParameterDisplay extends PApplet {

    private ControlP5 cp5;
    private HashMap< SoundInputParameterEnum, Slider > parameters;
    private int w, h;

    public void setup () {
        size( 600, 300 );
        cp5 = new ControlP5( this );
        parameters = new HashMap<>();

        Slider amplitudeSlider1 = cp5.addSlider( "AM1" ).setPosition( 10, 10 ).setSize( 15, 100 ).setRange( KimaConstants.MIN_AMPLITUDE, KimaConstants.MAX_AMPLITUDE ).setValue( 0.0f );
        Slider amplitudeSlider2 = cp5.addSlider( "AM2" ).setPosition( 45, 10 ).setSize( 15, 100 ).setRange( KimaConstants.MIN_AMPLITUDE, KimaConstants.MAX_AMPLITUDE ).setValue( 0.0f );
        Slider amplitudeSlider3 = cp5.addSlider( "AM3" ).setPosition( 80, 10 ).setSize( 15, 100 ).setRange( KimaConstants.MIN_AMPLITUDE, KimaConstants.MAX_AMPLITUDE ).setValue( 0.0f );

        Slider frequencySlider1 = cp5.addSlider( "FQ1" ).setPosition( 130, 10 ).setSize( 15, 100 ).setRange( KimaConstants.MIN_FREQUENCY, KimaConstants.MAX_FREQUENCY ).setValue( 0.0f );
        Slider frequencySlider2 = cp5.addSlider( "FQ2" ).setPosition( 165, 10 ).setSize( 15, 100 ).setRange( KimaConstants.MIN_FREQUENCY, KimaConstants.MAX_FREQUENCY ).setValue( 0.0f );
        Slider frequencySlider3 = cp5.addSlider( "FQ3" ).setPosition( 200, 10 ).setSize( 15, 100 ).setRange( KimaConstants.MIN_FREQUENCY, KimaConstants.MAX_FREQUENCY ).setValue( 0.0f );

        Slider attackSlider1 = cp5.addSlider( "AT1" ).setPosition( 250, 10 ).setSize( 15, 100 ).setRange( KimaConstants.ATTACK_MIN, KimaConstants.ATTACK_MAX ).setValue( 0.0f );
        Slider attackSlider2 = cp5.addSlider( "AT2" ).setPosition( 285, 10 ).setSize( 15, 100 ).setRange( KimaConstants.ATTACK_MIN, KimaConstants.ATTACK_MAX ).setValue( 0.0f );
        Slider attackSlider3 = cp5.addSlider( "AT3" ).setPosition( 320, 10 ).setSize( 15, 100 ).setRange( KimaConstants.ATTACK_MIN, KimaConstants.ATTACK_MAX ).setValue( 0.0f );

        Slider peakSlider1 = cp5.addSlider( "PEAK1" ).setPosition( 380, 10 ).setSize( 15, 100 ).setRange( KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX ).setValue( 0.0f );
        Slider peakSlider2 = cp5.addSlider( "PEAK2" ).setPosition( 415, 10 ).setSize( 15, 100 ).setRange( KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX ).setValue( 0.0f );
        Slider peakSlider3 = cp5.addSlider( "PEAK3" ).setPosition( 450, 10 ).setSize( 15, 100 ).setRange( KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX ).setValue( 0.0f );

        Slider fundamentalSlider1 = cp5.addSlider( "FU1" ).setPosition( 500, 10 ).setSize( 15, 100 ).setRange( KimaConstants.FUNDAMENTAL_MIN, KimaConstants.FUNDAMENTAL_MAX).setValue( 0.0f );
        Slider fundamentalSlider2 = cp5.addSlider( "FU2" ).setPosition( 535, 10 ).setSize( 15, 100 ).setRange( KimaConstants.FUNDAMENTAL_MIN, KimaConstants.FUNDAMENTAL_MAX ).setValue( 0.0f );
        Slider fundamentalSlider3 = cp5.addSlider( "FU3" ).setPosition( 570, 10 ).setSize( 15, 100 ).setRange( KimaConstants.FUNDAMENTAL_MIN, KimaConstants.FUNDAMENTAL_MAX ).setValue( 0.0f );

        Slider newnoteSlider1 = cp5.addSlider( "NN1" ).setPosition( 620, 10 ).setSize( 15, 100 ).setRange( KimaConstants.NEWNOTE_MIN, KimaConstants.NEWNOTE_MAX ).setValue( 0.0f );
        Slider newnoteSlider2 = cp5.addSlider( "NN2" ).setPosition( 655, 10 ).setSize( 15, 100 ).setRange( KimaConstants.NEWNOTE_MIN, KimaConstants.NEWNOTE_MAX ).setValue( 0.0f );
        Slider newnoteSlider3 = cp5.addSlider( "NN3" ).setPosition( 690, 10 ).setSize( 15, 100 ).setRange( KimaConstants.NEWNOTE_MIN, KimaConstants.NEWNOTE_MAX ).setValue( 0.0f );

        parameters.put( SoundInputParameterEnum.AMPLITUDE_PARAMETER1, amplitudeSlider1 );
        parameters.put( SoundInputParameterEnum.AMPLITUDE_PARAMETER2, amplitudeSlider2 );
        parameters.put( SoundInputParameterEnum.AMPLITUDE_PARAMETER3, amplitudeSlider3 );
        parameters.put( SoundInputParameterEnum.FREQUENCY_PARAMETER1, frequencySlider1 );
        parameters.put( SoundInputParameterEnum.FREQUENCY_PARAMETER2, frequencySlider2 );
        parameters.put( SoundInputParameterEnum.FREQUENCY_PARAMETER3, frequencySlider3 );
        parameters.put( SoundInputParameterEnum.ATTACK_PARAMETER1, attackSlider1 );
        parameters.put( SoundInputParameterEnum.ATTACK_PARAMETER2, attackSlider2 );
        parameters.put( SoundInputParameterEnum.ATTACK_PARAMETER3, attackSlider3 );
        parameters.put( SoundInputParameterEnum.PEAK_PARAMETER1, peakSlider1 );
        parameters.put( SoundInputParameterEnum.PEAK_PARAMETER2, peakSlider2 );
        parameters.put( SoundInputParameterEnum.PEAK_PARAMETER3, peakSlider3 );
        parameters.put( SoundInputParameterEnum.FUNDAMENTAL_PARAMETER1, fundamentalSlider1 );
        parameters.put( SoundInputParameterEnum.FUNDAMENTAL_PARAMETER2, fundamentalSlider2 );
        parameters.put( SoundInputParameterEnum.FUNDAMENTAL_PARAMETER3, fundamentalSlider3 );
        parameters.put( SoundInputParameterEnum.NEWNOTE_PARAMETER1, newnoteSlider1 );
        parameters.put( SoundInputParameterEnum.NEWNOTE_PARAMETER2, newnoteSlider2 );
        parameters.put( SoundInputParameterEnum.NEWNOTE_PARAMETER3, newnoteSlider3 );

    }

    public void draw () {
        background( 0 );
    }

    public void updateParameter ( SoundInputParameterEnum _sip, float value ) {
        parameters.get( _sip ).setValue( value );
    }

    public OscParameterDisplay ( int theWidth, int theHeight ) {
        w = theWidth;
        h = theHeight;
    }

    public static OscParameterDisplay addControlFrame ( String theName, int theWidth, int theHeight ) {
        Frame f = new Frame( theName );
        OscParameterDisplay p = new OscParameterDisplay( theWidth, theHeight );
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

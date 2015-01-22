package nano;

import processing.core.PApplet;
import themidibus.ControlChange;
import themidibus.MidiBus;

import java.util.ArrayList;

/**
 * Created by mrzl on 16.01.2015.
 */
public class NanoKontrolController {
    private MidiBus midi;
    private ArrayList< NanoKontrolMapping > mappings;

    public NanoKontrolController ( PApplet p, int nanoControlIdentifier ) {
        this.mappings = new ArrayList<>( );
        for( String s : MidiBus.availableInputs() ) {
            System.out.println(s);
        }
        this.midi = new MidiBus( this, nanoControlIdentifier, -1 );
    }

    public void addMapping( NanoKontrolMapping _m ) {
        mappings.add( _m );
    }

    public void controllerChange ( ControlChange change ) {
        NanoKontrolSliderEnum vpe = getParameter( change );

        for( NanoKontrolMapping m : mappings ) {
            m.midiMessageArrived( vpe, change.value() );
        }
    }

    private NanoKontrolSliderEnum getParameter ( ControlChange change ) {
        int number = change.number();
        switch ( number ) {
            case 0:
                return NanoKontrolSliderEnum.SLIDER_1;
            case 1:
                return NanoKontrolSliderEnum.SLIDER_2;
            case 2:
                return NanoKontrolSliderEnum.SLIDER_3;
            case 3:
                return NanoKontrolSliderEnum.SLIDER_4;
            case 4:
                return NanoKontrolSliderEnum.SLIDER_5;
            case 5:
                return NanoKontrolSliderEnum.SLIDER_6;
            case 6:
                return NanoKontrolSliderEnum.SLIDER_7;
            case 7:
                return NanoKontrolSliderEnum.SLIDER_8;
            case 16:
                return NanoKontrolSliderEnum.KNOB_1;
            case 17:
                return NanoKontrolSliderEnum.KNOB_2;
            case 18:
                return NanoKontrolSliderEnum.KNOB_3;
            case 19:
                return NanoKontrolSliderEnum.KNOB_4;
            case 20:
                return NanoKontrolSliderEnum.KNOB_5;
            case 21:
                return NanoKontrolSliderEnum.KNOB_6;
            case 22:
                return NanoKontrolSliderEnum.KNOB_7;
            case 23:
                return NanoKontrolSliderEnum.KNOB_8;
            default:
                System.err.println( "Unrecognized NanoKontrol Value" );
                return NanoKontrolSliderEnum.SLIDER_1;
        }
    }
}

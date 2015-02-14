package midi.bcr2000;

import themidibus.ControlChange;
import themidibus.MidiBus;

import java.util.ArrayList;

/**
 * Created by mrzl on 22.01.2015.
 */
public class BnrController {
    private MidiBus midi;
    private ArrayList< BcrMapping > mappings;

    public BnrController( int nanoControlIdentifier ) {
        this.mappings = new ArrayList<>( );
        for( String s : MidiBus.availableInputs() ) {
            System.out.println(s);
        }

        this.midi = new MidiBus( this, nanoControlIdentifier, -1 );
    }

    public void addMapping( BcrMapping _m ) {
        this.mappings.add( _m );
    }

    public void removeMapping( BcrMapping _m ) {
        this.mappings.remove( _m );
    }

    public void clear() {
        this.mappings.clear();
    }

    public void controllerChange ( ControlChange change ) {
        BcrKnobEnum vpe = getParameter( change );

        for( BcrMapping m : mappings ) {
            m.midiMessageArrived( vpe, change.value() );
        }
    }

    private BcrKnobEnum getParameter ( ControlChange change ) {
        int number = change.number();
        switch ( number ) {
            case 9:
                return BcrKnobEnum.KNOB_1_1;
            case 10:
                return BcrKnobEnum.KNOB_1_2;
            case 11:
                return BcrKnobEnum.KNOB_1_3;
            case 12:
                return BcrKnobEnum.KNOB_1_4;
            case 13:
                return BcrKnobEnum.KNOB_1_5;
            case 14:
                return BcrKnobEnum.KNOB_1_6;
            case 15:
                return BcrKnobEnum.KNOB_1_7;
            case 16:
                return BcrKnobEnum.KNOB_1_8;
            case 81:
                return BcrKnobEnum.KNOB_2_1;
            case 82:
                return BcrKnobEnum.KNOB_2_2;
            case 83:
                return BcrKnobEnum.KNOB_2_3;
            case 84:
                return BcrKnobEnum.KNOB_2_4;
            case 85:
                return BcrKnobEnum.KNOB_2_5;
            case 86:
                return BcrKnobEnum.KNOB_2_6;
            case 87:
                return BcrKnobEnum.KNOB_2_7;
            case 88:
                return BcrKnobEnum.KNOB_2_8;
            case 89:
                return BcrKnobEnum.KNOB_3_1;
            case 90:
                return BcrKnobEnum.KNOB_3_2;
            case 91:
                return BcrKnobEnum.KNOB_3_3;
            case 92:
                return BcrKnobEnum.KNOB_3_4;
            case 93:
                return BcrKnobEnum.KNOB_3_5;
            case 94:
                return BcrKnobEnum.KNOB_3_6;
            case 95:
                return BcrKnobEnum.KNOB_3_7;
            case 96:
                return BcrKnobEnum.KNOB_3_8;
            case 97:
                return BcrKnobEnum.KNOB_4_1;
            case 98:
                return BcrKnobEnum.KNOB_4_2;
            case 99:
                return BcrKnobEnum.KNOB_4_3;
            case 100:
                return BcrKnobEnum.KNOB_4_4;
            case 101:
                return BcrKnobEnum.KNOB_4_5;
            case 102:
                return BcrKnobEnum.KNOB_4_6;
            case 103:
                return BcrKnobEnum.KNOB_4_7;
            case 104:
                return BcrKnobEnum.KNOB_4_8;


            case 65:
                return BcrKnobEnum.BUTTON_1_1;
            case 66:
                return BcrKnobEnum.BUTTON_1_2;
            case 67:
                return BcrKnobEnum.BUTTON_1_3;
            case 68:
                return BcrKnobEnum.BUTTON_1_4;
            case 69:
                return BcrKnobEnum.BUTTON_1_5;
            case 70:
                return BcrKnobEnum.BUTTON_1_6;
            case 71:
                return BcrKnobEnum.BUTTON_1_7;
            case 72:
                return BcrKnobEnum.BUTTON_1_8;
            case 73:
                return BcrKnobEnum.BUTTON_2_1;
            case 74:
                return BcrKnobEnum.BUTTON_2_2;
            case 75:
                return BcrKnobEnum.BUTTON_2_3;
            case 76:
                return BcrKnobEnum.BUTTON_2_4;
            case 77:
                return BcrKnobEnum.BUTTON_2_5;
            case 78:
                return BcrKnobEnum.BUTTON_2_6;
            case 79:
                return BcrKnobEnum.BUTTON_2_7;
            case 80:
                return BcrKnobEnum.BUTTON_2_8;
            default:
                System.err.println( "ERROR: Buttons not yet mapped, something is wrong." );
                return BcrKnobEnum.BUTTON_1_1;
        }
    }
}

package midi.bcr2000;

import midi.VisualParameter;
import pattern.ChladniParticles;
import processing.core.PApplet;

import java.util.ArrayList;

/**
 * Created by mrzl on 22.01.2015.
 */
public class BcrMapping {
    private ArrayList< BcrInputParameter > nks;
    private ArrayList< VisualParameter > vp;

    private ChladniParticles chladniParticles;

    public BcrMapping( ChladniParticles _chladniParticles ) {
        nks = new ArrayList<>();
        vp = new ArrayList<>();
        chladniParticles = _chladniParticles;
    }

    public void addMapping( BcrInputParameter _nks, VisualParameter _vs ) {
        nks.add( _nks );
        vp.add( _vs );
    }

    public void midiMessageArrived( BcrKnobEnum _nkse, int value ) {
        int index = 0;
        for( BcrInputParameter nip : nks ) {
            if( nip.getType().equals( _nkse ) ) {
                VisualParameter _vp = vp.get( index );
                float v = PApplet.map( value, nip.getMin( ), nip.getMax( ), _vp.getMin( ), _vp.getMax( ) );
                if( isButton( _nkse ) && v > 120.0f ) {
                    chladniParticles.parameterChangedFromBcrController( vp.get( index ).getType( ), v );
                } else {
                    chladniParticles.parameterChangedFromBcrController( vp.get( index ).getType( ), v );
                }
            }
            index++;
        }
    }

    private boolean isButton( BcrKnobEnum _bcrv ) {
        switch( _bcrv ) {
            case BUTTON_1_1:
            case BUTTON_1_2:
            case BUTTON_1_3:
            case BUTTON_1_4:
            case BUTTON_1_5:
            case BUTTON_1_6:
            case BUTTON_1_7:
            case BUTTON_1_8:
            case BUTTON_2_1:
            case BUTTON_2_2:
            case BUTTON_2_3:
            case BUTTON_2_4:
            case BUTTON_2_5:
            case BUTTON_2_6:
            case BUTTON_2_7:
            case BUTTON_2_8:
                return true;
            default:
                return false;
        }
    }
}

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
                chladniParticles.parameterChangedFromBcrController( vp.get( index ).getType( ), v );
            }
            index++;
        }
    }
}

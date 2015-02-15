package osc;

import pattern.ChladniParticles;
import processing.core.PApplet;

import java.util.ArrayList;

/**
 * Created by mrzl on 06.01.2015.
 */
public class OscParameterMapping {

    private ArrayList< OscInputParameter > sips;
    private ArrayList< ChladniPatternParameter > cpps;
    private ArrayList< Integer > updateDelays;

    private ChladniParticles chladniParticles;

    public OscParameterMapping ( ChladniParticles _chladniParticles ) {
        sips = new ArrayList<>(  );
        cpps = new ArrayList<>(  );
        updateDelays = new ArrayList<>();

        this.chladniParticles = _chladniParticles;
    }

    public void addMapping( OscInputParameter _sip, ChladniPatternParameter _cpp ) {
        this.sips.add( _sip );
        this.cpps.add( _cpp );
    }

    public ArrayList< OscInputParameter > getInputParameters() {
        return sips;
    }

    public void soundInputParameterReceived( OscInputParameter _parameter, float value ) {
        for( OscInputParameter s : sips ) {
            if( s.getType().equals( _parameter.getType() ) ) {
                ChladniPatternParameter chladniPatternParameter = cpps.get( sips.indexOf( s ) );
                float mappedValue = PApplet.map( value, _parameter.getMin(), _parameter.getMax(), chladniPatternParameter.getMin(), chladniPatternParameter.getMax() );
                chladniParticles.parameterChangedFromOscController( chladniPatternParameter.getType( ), mappedValue );
            }
        }
    }
}

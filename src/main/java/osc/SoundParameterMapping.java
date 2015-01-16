package osc;

import pattern.ChladniParticles;
import processing.core.PApplet;

import java.util.ArrayList;

/**
 * Created by mrzl on 06.01.2015.
 */
public class SoundParameterMapping {

    private ArrayList< SoundInputParameter > sips;
    private ArrayList< ChladniPatternParameter > cpps;
    private ArrayList< Integer > updateDelays;

    private ChladniParticles chladniParticles;

    public SoundParameterMapping( ChladniParticles _chladniParticles ) {
        sips = new ArrayList<>(  );
        cpps = new ArrayList<>(  );
        updateDelays = new ArrayList<>();

        this.chladniParticles = _chladniParticles;
    }

    public void addMapping( SoundInputParameter _sip, ChladniPatternParameter _cpp ) {
        this.sips.add( _sip );
        this.cpps.add( _cpp );
    }

    public ArrayList< SoundInputParameter > getInputParameters() {

        return sips;
    }

    public void soundInputParameterReceived( SoundInputParameter _parameter, float value ) {
        for( SoundInputParameter s : sips ) {
            if( s.getType().equals( _parameter.getType() ) ) {
                ChladniPatternParameter chladniPatternParameter = cpps.get( sips.indexOf( s ) );
                float mappedValue = PApplet.map( value, _parameter.getMin(), _parameter.getMax(), chladniPatternParameter.getMin(), chladniPatternParameter.getMax() );
                chladniParticles.parameterChanged( chladniPatternParameter.getType(), mappedValue );
            }
        }
    }
}

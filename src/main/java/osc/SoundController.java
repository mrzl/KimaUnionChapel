package osc;

import main.Main;
import osc.debug.OscParameterDisplay;
import oscP5.OscMessage;
import oscP5.OscP5;
import signal.library.SignalFilter;

import java.util.ArrayList;

/**
 * Created by mrzl on 06.01.2015.
 */
public class SoundController {

    private OscP5 oscServer;
    private OscParameterDisplay debugDisplay;
    private ArrayList< SoundParameterMapping > mappings;
    private SignalFilter frequencyFilter1, frequencyFilter2, frequencyFilter3;
    private SignalFilter amplitudeFilter1, amplitudeFilter2, amplitudeFilter3;
    private SignalFilter attackFilter1, attackFilter2, attackFilter3;

    /**

     * @param port
     */
    public SoundController( Main p, int port ) {
        mappings = new ArrayList<>();
        oscServer = new OscP5( this, port );

        debugDisplay = OscParameterDisplay.addControlFrame( p, "OscParameterDebug", 600, 150 );

        frequencyFilter1 = new SignalFilter( p );
        frequencyFilter2 = new SignalFilter( p );
        frequencyFilter3 = new SignalFilter( p );
        amplitudeFilter1 = new SignalFilter( p );
        amplitudeFilter2 = new SignalFilter( p );
        amplitudeFilter3 = new SignalFilter( p );
        attackFilter1 = new SignalFilter( p );
        attackFilter2 = new SignalFilter( p );
        attackFilter3 = new SignalFilter( p );
    }

    public void oscEvent( OscMessage receivedOscMessage ) {
        try {
            SoundInputParameterEnum soundParameterType = getParameterFromStringIdentifier( receivedOscMessage.addrPattern() );
            float value;
            switch ( soundParameterType ) {
                case FREQUENCY_PARAMETER1:
                    value = receivedOscMessage.get( 0 ).intValue();
                    System.out.print( "Changed parameter frequency 1 from " + value );
                    value = frequencyFilter1.filterUnitFloat( value );
                    System.out.println( " to " + value );
                    break;
                case FREQUENCY_PARAMETER2:
                    value = receivedOscMessage.get( 0 ).intValue();
                    value = frequencyFilter2.filterUnitFloat( value );
                    break;
                case FREQUENCY_PARAMETER3:
                    value = receivedOscMessage.get( 0 ).intValue();
                    value = frequencyFilter3.filterUnitFloat( value );
                    break;
                case AMPLITUDE_PARAMETER1:
                    value = receivedOscMessage.get( 0 ).floatValue();
                    value = amplitudeFilter1.filterUnitFloat( value );
                    break;
                case AMPLITUDE_PARAMETER2:
                    value = receivedOscMessage.get( 0 ).floatValue();
                    value = amplitudeFilter2.filterUnitFloat( value );
                    break;
                case AMPLITUDE_PARAMETER3:
                    value = receivedOscMessage.get( 0 ).floatValue();
                    value = amplitudeFilter3.filterUnitFloat( value );
                    break;
                case ATTACK_PARAMETER1:
                    value = receivedOscMessage.get( 0 ).intValue();
                    //value = attackFilter1.filterUnitFloat( value );
                    break;
                case ATTACK_PARAMETER2:
                    value = receivedOscMessage.get( 0 ).intValue();
                    //value = attackFilter2.filterUnitFloat( value );
                    break;
                case ATTACK_PARAMETER3:
                    value = receivedOscMessage.get( 0 ).intValue();
                    //value = attackFilter3.filterUnitFloat( value );
                    break;
                default:
                    System.err.println( "WARNING: in oscEvent(OscMessage) of SoundController." );
                    throw new UnknownOscParameterException();
            }

            debugDisplay.updateParameter( soundParameterType, value );

            SoundInputParameter soundInputParameter = getParameterFromString( receivedOscMessage.addrPattern() );

            for( SoundParameterMapping m : mappings ) {
                m.soundInputParameterReceived( soundInputParameter, value );
            }

        } catch ( UnknownOscParameterException e ) {
            //e.printStackTrace( );
        }
    }

    public void addSoundParameterMapping( SoundParameterMapping _spm ) {
        this.mappings.add( _spm );
    }

    public SoundParameterMapping getSoundParameterMapping( int _spmId ) {
        return this.mappings.get( _spmId );
    }

    private SoundInputParameterEnum getParameterFromStringIdentifier( String _spsi ) throws UnknownOscParameterException {
        switch( _spsi ) {
            case "/attack1":
                return SoundInputParameterEnum.ATTACK_PARAMETER1;
            case "/attack2":
                return SoundInputParameterEnum.ATTACK_PARAMETER2;
            case "/attack3":
                return SoundInputParameterEnum.ATTACK_PARAMETER3;
            case "/amplitude1":
                return SoundInputParameterEnum.AMPLITUDE_PARAMETER1;
            case "/amplitude2":
                return SoundInputParameterEnum.AMPLITUDE_PARAMETER2;
            case "/amplitude3":
                return SoundInputParameterEnum.AMPLITUDE_PARAMETER3;
            case "/frequency1":
                return SoundInputParameterEnum.FREQUENCY_PARAMETER1;
            case "/frequency2":
                return SoundInputParameterEnum.FREQUENCY_PARAMETER2;
            case "/frequency3":
                return SoundInputParameterEnum.FREQUENCY_PARAMETER3;
            default:
                System.err.println( "ERROR: Unknown Osc Signal: " + _spsi + " from SoundController" );
                throw new UnknownOscParameterException();
        }
    }

    private SoundInputParameter getParameterFromString( String _spsi ) throws UnknownOscParameterException {
        SoundInputParameterEnum type = getParameterFromStringIdentifier( _spsi );
        for( SoundParameterMapping m : mappings ) {
            for( SoundInputParameter p : m.getInputParameters() ) {
                if( p.getType() == type  ) {
                    return p;
                }
            }
        }
        throw new UnknownOscParameterException();
    }
}

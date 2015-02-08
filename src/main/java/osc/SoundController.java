package osc;

import filter.SignalFilter;
import main.Main;
import osc.debug.OscParameterDisplay;
import oscP5.OscMessage;
import oscP5.OscP5;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mrzl on 06.01.2015.
 */
public class SoundController {

    private OscParameterDisplay debugDisplay;
    private ArrayList< SoundParameterMapping > mappings;
    private HashMap< SoundInputParameterEnum, SignalFilterWrapper > filters;
    private long lastTimeOscMessageArrived, updateDelay;

    /**

     * @param port
     */
    public SoundController( Main p, int port ) {
        mappings = new ArrayList<>();
        new OscP5( this, port );

        //debugDisplay = addControlFrame( "OscParameterDebug", 600, 150 );

        filters = new HashMap<>();

        filters.put( SoundInputParameterEnum.FREQUENCY_PARAMETER1, new SignalFilterWrapper( new SignalFilter( p ) ) );
        filters.put( SoundInputParameterEnum.FREQUENCY_PARAMETER2, new SignalFilterWrapper( new SignalFilter( p ) ) );
        filters.put( SoundInputParameterEnum.FREQUENCY_PARAMETER3, new SignalFilterWrapper( new SignalFilter( p ) ) );

        filters.put( SoundInputParameterEnum.AMPLITUDE_PARAMETER1, new SignalFilterWrapper( new SignalFilter( p ) ) );
        filters.put( SoundInputParameterEnum.AMPLITUDE_PARAMETER2, new SignalFilterWrapper( new SignalFilter( p ) ) );
        filters.put( SoundInputParameterEnum.AMPLITUDE_PARAMETER3, new SignalFilterWrapper( new SignalFilter( p ) ) );

        filters.put( SoundInputParameterEnum.ATTACK_PARAMETER1, new SignalFilterWrapper( new SignalFilter( p ) ) );
        filters.put( SoundInputParameterEnum.ATTACK_PARAMETER2, new SignalFilterWrapper( new SignalFilter( p ) ) );
        filters.put( SoundInputParameterEnum.ATTACK_PARAMETER3, new SignalFilterWrapper( new SignalFilter( p ) ) );

        filters.get( SoundInputParameterEnum.FREQUENCY_PARAMETER1 ).setEnabled( true );
        filters.get( SoundInputParameterEnum.FREQUENCY_PARAMETER2 ).setEnabled( true );
        filters.get( SoundInputParameterEnum.FREQUENCY_PARAMETER3 ).setEnabled( true );

        filters.get( SoundInputParameterEnum.AMPLITUDE_PARAMETER1 ).setEnabled( true );
        filters.get( SoundInputParameterEnum.AMPLITUDE_PARAMETER2 ).setEnabled( true );
        filters.get( SoundInputParameterEnum.AMPLITUDE_PARAMETER3 ).setEnabled( true );

        filters.get( SoundInputParameterEnum.ATTACK_PARAMETER1 ).setEnabled( false );
        filters.get( SoundInputParameterEnum.ATTACK_PARAMETER2 ).setEnabled( false );
        filters.get( SoundInputParameterEnum.ATTACK_PARAMETER3 ).setEnabled( false );


        lastTimeOscMessageArrived = System.currentTimeMillis();
        updateDelay = 0;
    }


    @SuppressWarnings( "unused" )
    public void oscEvent( OscMessage receivedOscMessage ) {
        long timeArrived = System.currentTimeMillis();
        if( timeArrived - lastTimeOscMessageArrived > updateDelay ) {
            lastTimeOscMessageArrived = timeArrived;
            try {
                SoundInputParameterEnum soundParameterType = getParameterFromStringIdentifier( receivedOscMessage.addrPattern() );
                float value;
                switch ( soundParameterType ) {
                    case FREQUENCY_PARAMETER1:
                        value = receivedOscMessage.get( 0 ).intValue();
                        break;
                    case FREQUENCY_PARAMETER2:
                        value = receivedOscMessage.get( 0 ).intValue();
                        break;
                    case FREQUENCY_PARAMETER3:
                        value = receivedOscMessage.get( 0 ).intValue();
                        break;
                    case AMPLITUDE_PARAMETER1:
                        value = receivedOscMessage.get( 0 ).floatValue();
                        break;
                    case AMPLITUDE_PARAMETER2:
                        value = receivedOscMessage.get( 0 ).floatValue();
                        break;
                    case AMPLITUDE_PARAMETER3:
                        value = receivedOscMessage.get( 0 ).floatValue();
                        break;
                    case ATTACK_PARAMETER1:
                        value = receivedOscMessage.get( 0 ).intValue();
                        break;
                    case ATTACK_PARAMETER2:
                        value = receivedOscMessage.get( 0 ).intValue();
                        break;
                    case ATTACK_PARAMETER3:
                        value = receivedOscMessage.get( 0 ).intValue();
                        break;
                    default:
                        System.err.println( "WARNING: in oscEvent(OscMessage) of SoundController." );
                        throw new UnknownOscParameterException();
                }

                value = filters.get( soundParameterType ).applyFilter( value );

                //debugDisplay.updateParameter( soundParameterType, value );

                SoundInputParameter soundInputParameter = getParameterFromString( receivedOscMessage.addrPattern() );

                for ( SoundParameterMapping m : mappings ) {
                    m.soundInputParameterReceived( soundInputParameter, value );
                }

            } catch ( UnknownOscParameterException e ) {
                //e.printStackTrace( );
            }
        }
    }

    public void setUpdateDelay( long _updateDelay ) {
        this.updateDelay = _updateDelay;
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
        //System.out.println( _spsi + " "  + type );
        throw new UnknownOscParameterException();
    }
}

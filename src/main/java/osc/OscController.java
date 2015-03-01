package osc;

import filter.SignalFilter;
import main.Main;
import oscP5.OscMessage;
import oscP5.OscP5;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mrzl on 06.01.2015.
 */
public class OscController extends PApplet {

    private Main p;
    private ArrayList< OscParameterMapping > mappings;
    private HashMap< OscParameterInputEnum, SignalFilterWrapper > filters;
    private long lastTimeOscMessageArrived, updateDelay;

    /**

     * @param port
     */
    public OscController ( Main p, int port ) {
        this.p = p;
        new OscP5( this, port );

        mappings = new ArrayList<>();

        filters = new HashMap<>();

        filters.put( OscParameterInputEnum.FREQUENCY_PARAMETER1, new SignalFilterWrapper( new SignalFilter( p ) ) );
        filters.put( OscParameterInputEnum.FREQUENCY_PARAMETER2, new SignalFilterWrapper( new SignalFilter( p ) ) );
        filters.put( OscParameterInputEnum.FREQUENCY_PARAMETER3, new SignalFilterWrapper( new SignalFilter( p ) ) );

        filters.put( OscParameterInputEnum.AMPLITUDE_PARAMETER1, new SignalFilterWrapper( new SignalFilter( p ) ) );
        filters.put( OscParameterInputEnum.AMPLITUDE_PARAMETER2, new SignalFilterWrapper( new SignalFilter( p ) ) );
        filters.put( OscParameterInputEnum.AMPLITUDE_PARAMETER3, new SignalFilterWrapper( new SignalFilter( p ) ) );

        filters.put( OscParameterInputEnum.ATTACK_PARAMETER1, new SignalFilterWrapper( new SignalFilter( p ) ) );
        filters.put( OscParameterInputEnum.ATTACK_PARAMETER2, new SignalFilterWrapper( new SignalFilter( p ) ) );
        filters.put( OscParameterInputEnum.ATTACK_PARAMETER3, new SignalFilterWrapper( new SignalFilter( p ) ) );

        filters.put( OscParameterInputEnum.PEAK_PARAMETER1, new SignalFilterWrapper( new SignalFilter( p ) ) );
        filters.put( OscParameterInputEnum.PEAK_PARAMETER2, new SignalFilterWrapper( new SignalFilter( p ) ) );
        filters.put( OscParameterInputEnum.PEAK_PARAMETER3, new SignalFilterWrapper( new SignalFilter( p ) ) );

        filters.put( OscParameterInputEnum.FUNDAMENTAL_PARAMETER1, new SignalFilterWrapper( new SignalFilter( p ) ) );
        filters.put( OscParameterInputEnum.FUNDAMENTAL_PARAMETER2, new SignalFilterWrapper( new SignalFilter( p ) ) );
        filters.put( OscParameterInputEnum.FUNDAMENTAL_PARAMETER3, new SignalFilterWrapper( new SignalFilter( p ) ) );

        filters.put( OscParameterInputEnum.NEWNOTE_PARAMETER1, new SignalFilterWrapper( new SignalFilter( p ) ) );
        filters.put( OscParameterInputEnum.NEWNOTE_PARAMETER2, new SignalFilterWrapper( new SignalFilter( p ) ) );
        filters.put( OscParameterInputEnum.NEWNOTE_PARAMETER3, new SignalFilterWrapper( new SignalFilter( p ) ) );

        filters.get( OscParameterInputEnum.FREQUENCY_PARAMETER1 ).setEnabled( true );
        filters.get( OscParameterInputEnum.FREQUENCY_PARAMETER2 ).setEnabled( true );
        filters.get( OscParameterInputEnum.FREQUENCY_PARAMETER3 ).setEnabled( true );

        filters.get( OscParameterInputEnum.AMPLITUDE_PARAMETER1 ).setEnabled( true );
        filters.get( OscParameterInputEnum.AMPLITUDE_PARAMETER2 ).setEnabled( true );
        filters.get( OscParameterInputEnum.AMPLITUDE_PARAMETER3 ).setEnabled( true );

        filters.get( OscParameterInputEnum.ATTACK_PARAMETER1 ).setEnabled( false );
        filters.get( OscParameterInputEnum.ATTACK_PARAMETER2 ).setEnabled( false );
        filters.get( OscParameterInputEnum.ATTACK_PARAMETER3 ).setEnabled( false );

        filters.get( OscParameterInputEnum.PEAK_PARAMETER1 ).setEnabled( true );
        filters.get( OscParameterInputEnum.PEAK_PARAMETER2 ).setEnabled( true );
        filters.get( OscParameterInputEnum.PEAK_PARAMETER3 ).setEnabled( true );

        filters.get( OscParameterInputEnum.FUNDAMENTAL_PARAMETER1 ).setEnabled( true );
        filters.get( OscParameterInputEnum.FUNDAMENTAL_PARAMETER2 ).setEnabled( true );
        filters.get( OscParameterInputEnum.FUNDAMENTAL_PARAMETER3 ).setEnabled( true );

        filters.get( OscParameterInputEnum.NEWNOTE_PARAMETER1 ).setEnabled( false );
        filters.get( OscParameterInputEnum.NEWNOTE_PARAMETER2 ).setEnabled( false );
        filters.get( OscParameterInputEnum.NEWNOTE_PARAMETER3 ).setEnabled( false );


        lastTimeOscMessageArrived = System.currentTimeMillis();
        updateDelay = 0;
    }


    @SuppressWarnings( "unused" )
    public void oscEvent( OscMessage receivedOscMessage ) {
        long timeArrived = System.currentTimeMillis();
        if( timeArrived - lastTimeOscMessageArrived > updateDelay ) {
            lastTimeOscMessageArrived = timeArrived;
            try {
                OscParameterInputEnum soundParameterType = getParameterFromStringIdentifier( receivedOscMessage.addrPattern() );
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
                    case PEAK_PARAMETER1:
                        value = receivedOscMessage.get( 0 ).floatValue( );
                        break;
                    case PEAK_PARAMETER2:
                        value = receivedOscMessage.get( 0 ).floatValue( );
                        break;
                    case PEAK_PARAMETER3:
                        value = receivedOscMessage.get( 0 ).floatValue( );
                        break;
                    case FUNDAMENTAL_PARAMETER1:
                        value = receivedOscMessage.get( 0 ).floatValue( );
                        break;
                    case FUNDAMENTAL_PARAMETER2:
                        value = receivedOscMessage.get( 0 ).floatValue( );
                        break;
                    case FUNDAMENTAL_PARAMETER3:
                        value = receivedOscMessage.get( 0 ).floatValue( );
                        break;
                    case NEWNOTE_PARAMETER1:
                        value = receivedOscMessage.get( 0 ).stringValue( ) == "bang" ? 1 : 0;
                        break;
                    case NEWNOTE_PARAMETER2:
                        value = receivedOscMessage.get( 0 ).stringValue( ) == "bang" ? 1 : 0;
                        break;
                    case NEWNOTE_PARAMETER3:
                        value = receivedOscMessage.get( 0 ).stringValue( ) == "bang" ? 1 : 0;
                        break;
                    default:
                        System.err.println( "WARNING: in oscEvent(OscMessage) of SoundController." );
                        throw new UnknownOscParameterException();
                }

                value = filters.get( soundParameterType ).applyFilter( value );

                p.controlFrame.updateOscParameters( soundParameterType, value );

                OscInputParameter oscInputParameter = getParameterFromString( receivedOscMessage.addrPattern() );

                for ( OscParameterMapping m : mappings ) {
                    m.soundInputParameterReceived( oscInputParameter, value );
                }
            } catch ( UnknownOscParameterException e ) {
                //e.printStackTrace( );
            }
        }
    }

    public void setUpdateDelay( long _updateDelay ) {
        this.updateDelay = _updateDelay;
    }

    public void addSoundParameterMapping( OscParameterMapping _spm ) {
        this.mappings.add( _spm );
    }

    public void removeSoundParameterMapping( OscParameterMapping _spm ) {
        this.mappings.remove( _spm );
    }

    public void clear() {
        this.mappings.clear();
    }

    public OscParameterMapping getSoundParameterMapping( int _spmId ) {
        return this.mappings.get( _spmId );
    }

    private OscParameterInputEnum getParameterFromStringIdentifier( String _spsi ) throws UnknownOscParameterException {
        switch( _spsi ) {
            case "/attack1":
                return OscParameterInputEnum.ATTACK_PARAMETER1;
            case "/attack2":
                return OscParameterInputEnum.ATTACK_PARAMETER2;
            case "/attack3":
                return OscParameterInputEnum.ATTACK_PARAMETER3;
            case "/amplitude1":
                return OscParameterInputEnum.AMPLITUDE_PARAMETER1;
            case "/amplitude2":
                return OscParameterInputEnum.AMPLITUDE_PARAMETER2;
            case "/amplitude3":
                return OscParameterInputEnum.AMPLITUDE_PARAMETER3;
            case "/frequency1":
                return OscParameterInputEnum.FREQUENCY_PARAMETER1;
            case "/frequency2":
                return OscParameterInputEnum.FREQUENCY_PARAMETER2;
            case "/frequency3":
                return OscParameterInputEnum.FREQUENCY_PARAMETER3;
            case "/peak1":
                return OscParameterInputEnum.PEAK_PARAMETER1;
            case "/peak2":
                return OscParameterInputEnum.PEAK_PARAMETER2;
            case "/peak3":
                return OscParameterInputEnum.PEAK_PARAMETER3;
            case "/fundamental1":
                return OscParameterInputEnum.FUNDAMENTAL_PARAMETER1;
            case "/fundamental2":
                return OscParameterInputEnum.FUNDAMENTAL_PARAMETER2;
            case "/fundamental3":
                return OscParameterInputEnum.FUNDAMENTAL_PARAMETER3;
            case "/newnote1":
                return OscParameterInputEnum.NEWNOTE_PARAMETER1;
            case "/newnote2":
                return OscParameterInputEnum.NEWNOTE_PARAMETER2;
            case "/newnote3":
                return OscParameterInputEnum.NEWNOTE_PARAMETER3;
            default:
                System.err.println( "ERROR: Unknown Osc Signal: " + _spsi + " from SoundController" );
                throw new UnknownOscParameterException();
        }
    }

    private OscInputParameter getParameterFromString( String _spsi ) throws UnknownOscParameterException {
        OscParameterInputEnum type = getParameterFromStringIdentifier( _spsi );
        for( OscParameterMapping m : mappings ) {
            for( OscInputParameter p : m.getInputParameters() ) {
                if( p.getType() == type  ) {
                    return p;
                }
            }
        }
        //System.out.println( _spsi + " "  + type );
        throw new UnknownOscParameterException();
    }

    public long getUpdateDelay () {
        return updateDelay;
    }
}

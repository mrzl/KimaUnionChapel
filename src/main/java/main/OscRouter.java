package main;

import oscP5.OscMessage;
import oscP5.OscP5;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mrzl on 04.01.2015.
 */
public class OscRouter {

    private OscP5 oscServer;
    private ArrayList< HashMap< String, ParticleContainer >> mappings;
    public static final int ATTACK = 0;
    public static final int AMPLITUDE = 1;
    public static final int FREQUENCY = 2;

    public OscRouter( int port ) {
        mappings = new ArrayList<>();
        mappings.add( new HashMap<String, ParticleContainer >() );
        mappings.add( new HashMap<String, ParticleContainer >() );
        mappings.add( new HashMap<String, ParticleContainer >() );

        oscServer = new OscP5( this, port );
    }

    public void add( int type, String oscPattern, ParticleContainer container ) {
        mappings.get( type ).put( oscPattern, container );
    }

    public void oscEvent( OscMessage message ) {
        switch( message.addrPattern() ) {
            case "/attack1":
                int attack1 = message.get( 0 ).intValue();
                //mappings.get( ATTACK ).get( message.addrPattern() ).attackChanged( attack1 );
                break;
            case "/amplitude1":
                float amplitude1 = message.get( 0 ).floatValue() * 20;
                //System.out.println( "ampltude1: " + amplitude1 );
                //mappings.get( AMPLITUDE ).get( message.addrPattern() ).getSurface().setN( amplitude1 );
                break;
            case "/frequency1":
                int frequency1 = message.get( 0 ).intValue();
                float freq = PApplet.map(frequency1, 200, 10000, 0, 20);
                //System.out.println( "frequency: " + freq );
                //mappings.get( FREQUENCY ).get( message.addrPattern() ).getSurface().setM( freq );
                break;
        }
    }
}

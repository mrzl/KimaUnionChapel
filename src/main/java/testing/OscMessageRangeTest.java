package testing;

import oscP5.OscMessage;
import oscP5.OscP5;
import processing.core.PApplet;

/**
 * Created by mrzl on 03.02.2015.
 */
public class OscMessageRangeTest extends PApplet {

    OscP5 oscP5;
    int att1, att2, att3, amp1, amp2, amp3, fre1, fre2, fre3;
    float ampl1mi, ampl2mi, ampl3mi, ampl1ma, ampl2ma, ampl3ma;
    int freq1mi, freq2mi, freq3mi, freq1ma, freq2ma, freq3ma;
    long started;
    int counter = 0;

    public void setup() {

        size(400, 400);
        oscP5 = new OscP5(this, 5001, OscP5.UDP);

        started = millis() ;

        ampl1mi = ampl2mi = ampl3mi = 1000.0f;
        ampl1ma = ampl2ma = ampl3ma = 0.0f;
        freq1mi = freq2mi = freq3mi = 100000;
        freq1ma = freq2ma = freq3ma = 0;
    }

    public void draw() {
        background(0);
    }

    void oscEvent( OscMessage theOscMessage) {

        if (theOscMessage.checkAddrPattern("/attack1")==true) {
            att1++;
        } else if (theOscMessage.checkAddrPattern("/attack2")==true) {
            att2++;
        } else if (theOscMessage.checkAddrPattern("/attack3")==true) {
            att3++;
        } else if (theOscMessage.checkAddrPattern("/amplitude1")==true) {
            float v = theOscMessage.get(0).floatValue();
            ampl1mi = min( v, ampl1mi );
            ampl1ma = max( v, ampl1ma );
            amp1++;
        } else if (theOscMessage.checkAddrPattern("/amplitude2")==true) {
            float v = theOscMessage.get(0).floatValue();
            ampl2mi = min( v, ampl2mi );
            ampl2ma = max( v, ampl2ma );
            amp2++;
        } else if (theOscMessage.checkAddrPattern("/amplitude3")==true) {
            float v = theOscMessage.get(0).floatValue();
            ampl3mi = min( v, ampl3mi );
            ampl3ma = max( v, ampl3ma );
            amp3++;
        } else if (theOscMessage.checkAddrPattern("/frequency1")==true) {
            int v = theOscMessage.get(0).intValue();
            freq1mi = min( v, freq1mi );
            freq1ma = max( v, freq1ma );
            fre1++;
        } else if (theOscMessage.checkAddrPattern("/frequency2")==true) {
            int v = theOscMessage.get(0).intValue();
            freq2mi = min( v, freq2mi );
            freq2ma = max( v, freq2ma );
            fre2++;
        } else if (theOscMessage.checkAddrPattern("/frequency3")==true) {
            int v = theOscMessage.get(0).intValue();
            freq2mi = min( v, freq2mi );
            freq2ma = max( v, freq2ma );
            fre3++;
        }

        counter++;
        //println( counter );
        if ( millis() - started > 2000 ) {
            println( "attack1: " + att1 + " attack2: " + att2 + " attack3: " + att3 );
            println( "amplitude1: " + amp1 + " amplitude2: " + amp2 + " amplitude3: " + amp3 );
            println( "frequency1: " + fre1 + " frequency2: " + fre2 + " frequency3: " + fre3 );
            println( "overall: " + counter );
            exit();
        }

        println( "FREQ1MIN: " + freq1mi + " FREQ1MAX: " + freq1ma );
        println( "FREQ2MIN: " + freq2mi + " FREQ2MAX: " + freq2ma );
        println( "FREQ3MIN: " + freq3mi + " FREQ3MAX: " + freq3ma );

        println( "AMPL1MIN: " + ampl1mi + " AMPL1MAX: " + ampl1ma );
        println( "AMPL2MIN: " + ampl2mi + " AMPL2MAX: " + ampl2ma );
        println( "AMPL3MIN: " + ampl3mi + " AMPL3MAX: " + ampl3ma );
    }

    public static void main( String[] args ) {
        PApplet.main( new String[] { "testing.OscMessageRangeTest" } );
    }
}

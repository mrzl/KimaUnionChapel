package testing;

import oscP5.OscMessage;
import oscP5.OscP5;
import processing.core.PApplet;

/**
 * Created by mrzl on 03.02.2015.
 */
public class OscMessageRangeTest extends PApplet {

    OscP5 oscP5;
    int att1, att2, att3, amp1, amp2, amp3, fre1, fre2, fre3, peak1, peak2, peak3, fun1, fun2, fun3;
    float ampl1mi, ampl2mi, ampl3mi, ampl1ma, ampl2ma, ampl3ma;
    int freq1mi, freq2mi, freq3mi, freq1ma, freq2ma, freq3ma;
    float peak1mi, peak2mi, peak3mi, peak1ma, peak2ma, peak3ma;
    float fun1mi, fun2mi, fun3mi, fun1ma, fun2ma, fun3ma;
    float nn1mi, nn2mi, nn3mi, nn1ma, nn2ma, nn3ma;
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
        peak1mi = peak2mi = peak3mi = 1000.0f;
        peak1ma = peak2ma = peak3ma = 0.0f;
        fun1mi = fun2mi = fun3mi = 1000.0f;
        fun1ma = fun2ma = fun3ma = 0.0f;


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
        } else if (theOscMessage.checkAddrPattern("/peak1")==true) {
            float v = theOscMessage.get(0).floatValue( );
            peak1mi = min( v, peak1mi);
            peak1ma = max( v, peak1ma );
            peak1++;
        } else if (theOscMessage.checkAddrPattern("/peak2")==true) {
            float v = theOscMessage.get(0).floatValue( );
            peak2mi = min( v, peak2mi );
            peak2ma = max( v, peak2ma );
            peak2++;
        } else if (theOscMessage.checkAddrPattern("/peak3")==true) {
            float v = theOscMessage.get(0).floatValue();
            peak3mi = min( v, peak3mi );
            peak3ma = max( v, peak3ma );
            peak3++;
        } else if (theOscMessage.checkAddrPattern("/fundamental1")==true) {
            float v = theOscMessage.get(0).floatValue( );
            fun1mi = min( v, fun1mi);
            fun1ma = max( v, fun1ma );
            fun1++;
        } else if (theOscMessage.checkAddrPattern("/fundamental2")==true) {
            float v = theOscMessage.get(0).floatValue( );
            fun2mi = min( v, fun2mi );
            fun2ma = max( v, fun2ma );
            fun2++;
        } else if (theOscMessage.checkAddrPattern("/fundamental3")==true) {
            float v = theOscMessage.get(0).floatValue();
            fun3mi = min( v, fun3mi );
            fun3ma = max( v, fun3ma );
            fun3++;
        }

        counter++;
        //println( counter );

        if ( millis() - started > 2000 ) {
            println( "attack1: " + att1 + " attack2: " + att2 + " attack3: " + att3 );
            println( "amplitude1: " + amp1 + " amplitude2: " + amp2 + " amplitude3: " + amp3 );
            println( "frequency1: " + fre1 + " frequency2: " + fre2 + " frequency3: " + fre3 );
            println( "peak1: " + peak1 + " peak2: " + peak2 + " peak3: " + peak3 );
            println( "fundamental1: " + fun1 + " fundamental2: " + fun2 + " fundamental3: " + fun3 );
            println( "overall: " + counter );
            started = millis();
            //exit();
            println( "---------------------------------------" );
            println( "FREQ1MIN: " + freq1mi + " FREQ1MAX: " + freq1ma );
            println( "FREQ2MIN: " + freq2mi + " FREQ2MAX: " + freq2ma );
            println( "FREQ3MIN: " + freq3mi + " FREQ3MAX: " + freq3ma );

            println( "AMPL1MIN: " + ampl1mi + " AMPL1MAX: " + ampl1ma );
            println( "AMPL2MIN: " + ampl2mi + " AMPL2MAX: " + ampl2ma );
            println( "AMPL3MIN: " + ampl3mi + " AMPL3MAX: " + ampl3ma );

            println( "PEAK1MIN: " + peak1mi + " PEAK1MAX: " + peak1ma );
            println( "PEAK2MIN: " + peak2mi + " PEAK2MAX: " + peak2ma );
            println( "PEAK3MIN: " + peak3mi + " PEAK3MAX: " + peak3ma );

            println( "FUNDAMENTAL1MIN: " + fun1mi + " FUNDAMENTAL1MAX: " + fun1ma);
            println( "FUNDAMENTAL2MIN: " + fun2mi + " FUNDAMENTAL2MAX: " + fun2ma );
            println( "FUNDAMENTAL3MIN: " + fun3mi + " FUNDAMENTAL3MAX: " + fun3ma );
            println( "---------------------------------------" );
            counter = 0;

            att1 = att2 = att3 = amp1 = amp2 = amp3 = fre1 = fre2 = fre3 = peak1 = peak2 = peak3 = fun1 = fun2 = fun3 = 0;
        }

        if( frameCount % 200 == 0 ) {

        }
    }

    public static void main( String[] args ) {
        PApplet.main( new String[] { "testing.OscMessageRangeTest" } );
    }
}

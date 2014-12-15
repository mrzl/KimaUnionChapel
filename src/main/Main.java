package main;

import ddf.minim.AudioOutput;
import ddf.minim.Minim;
import ddf.minim.ugens.Oscil;
import ddf.minim.ugens.Waves;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Created by mar on 13.12.14.
 */
public class Main extends PApplet {

    ParticleContainer particlesRect;
    ParticleContainer particlesCircle;
    ChladniSurface rect;
    ChladniCircle circle;

    Minim minim;
    AudioOutput out;
    Oscil wave;

    //private ControlP5 cp5;
    //ControlFrame cf;
/*
    public void init() {
        frame.removeNotify();
        frame.setUndecorated( true );
        frame.setResizable( true );
        frame.addNotify();
        super.init();
    }*/

    int resolution = 800;

    public void setup() {
        size( 1600, 800, PConstants.P3D );

        frameRate( 200 );

        rect = new ChladniRectangle( this, resolution, resolution, PConstants.P3D );
        circle = new ChladniCircle( this, resolution, resolution, PConstants.P3D, 500 );

        particlesRect = new ParticleContainer( this, rect, 5000 );
        particlesCircle = new ParticleContainer( this, circle, 5000 );

        minim = new Minim(this);
        out = minim.getLineOut();
        // create a sine wave Oscil, set to 440 Hz, at 0.5 amplitude
        wave = new Oscil( 440, 0.5f, Waves.SINE );
        // patch the Oscil to the output
        //wave.patch( out );

        //cp5 = new ControlP5(this);
        //cf = MathUtils.addControlFrame( this, "Controls", 400, 600 );
    }

    public void draw() {
        background( 0 );
        frame.setTitle( frameRate + "" );


        //rect.setEpsilon( 0.05f );

        circle.setC1( 2.0f );
        circle.setC2( 2.0f );

        circle.setN( map( mouseX, 0, width, 0, 5 ) );
        //circle.setC1( map( mouseX, 0, width, 0, 5 ) );
        circle.setM( map( mouseY, 0, height, 0, 20 ) );

        rect.update();
        circle.update();
        rect.draw( 0, 0, 800, 800 );
        circle.draw( 800, 0, 800, 800 );

        //particlesRect.update( 5 );
        //particlesRect.draw( 0, 0 );

        //particlesCircle.update( 5 );
        //particlesCircle.restrictCircular( 400 );
        //particlesCircle.draw( 960, 0 );
    }

    public void mouseMoved() {
        float minFreq = 110;
        float maxFreq = 880;
        float freq = map( mouseX, 0, width, minFreq, maxFreq );
        float amplitude = map( mouseY, 0, height, 0, 2 );
        wave.setFrequency( freq );
        wave.setAmplitude( amplitude );

        rect.setN( map( freq, minFreq, maxFreq, 0, 40 ) );
        rect.setM( map( amplitude, 0, 2, 0, 50 ) );

        particlesRect.frequencyChanged();
        particlesCircle.frequencyChanged();
    }

    public static void main( String[] args ) {
        PApplet.main( new String[]{ "main.Main" } );
    }
/*
    public void sampleFunction () {
        maxAmp=0;
        t=0;
        int mm=Math.abs(m);
        double[] zeros=Bessel.besselnZeros(mm,n);  // m-th Bessel function; n-th zero
        double x=-a, dx=2*a/(np-1);
        for(int i=0; i<np; i++) {
            double y=-a, dy=2*a/(np-1);
            for(int j=0; j<np; j++) {
                double r=Math.sqrt(x*x+y*y);
                double phase=m*Math.atan2(y,x);
                double angular=(m<0)?Math.sin(phase):Math.cos(phase);
                double psi=(r>a)?0:Bessel.besseln(mm,r*zeros[n-1]/a)*angular; // m-th Bessel function
                if(maxAmp<Math.abs(psi))maxAmp=psi;
                animationData[i][j]=data[i][j]=psi;
                y+=dy;
            }
            x+=dx;
        }
        for(int i=0; i<np; i++) {
            for(int j=0; j<np; j++) {
                x=mesh[i][j][0];
                double y=mesh[i][j][1];
                double r=Math.sqrt(x*x+y*y);
                double phase=m*Math.atan2(y,x);
                double angular=(m<0)?Math.sin(phase):Math.cos(phase);
                double psi=(r>=a)?0:Bessel.besseln(mm,r*zeros[n-1]/a)*angular; // m-th Bessel function
                meshData[i][j]=mesh[i][j][2]=a*psi/maxAmp;
            }
        }
        _view.besselTrail.clear();
        _view.waveFunctionTrail.clear();
        x=0;
        dx=a/199;
        for(int i=0; i<200; i++) {
            _view.besselTrail.addPoint(x/2,Bessel.besseln(mm,x*zeros[n-1]/a));
            x+=dx;
        }
        x-=dx;
        while(x<25) {
            _view.waveFunctionTrail.addPoint(x/2,Bessel.besseln(mm,x*zeros[n-1]/a));
            x+=dx;
        }
        frequency=c*zeros[n-1]/2/a/f0;
    }
*/
}

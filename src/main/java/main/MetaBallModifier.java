package main;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PShader;

import java.io.File;

/**
 * Created by mar on 27.12.14.
 */
public class MetaBallModifier {

    private PShader blur, threshold;
    private PApplet p;

    private int blurStrength;
    private float thresholdValue;

    public MetaBallModifier( PApplet p ) {
        this.p = p;
        blur = p.loadShader( "shader" + File.separator + "blur.glsl" );
        threshold = p.loadShader( "shader" + File.separator + "threshold.glsl" );

        blurStrength = 5;
        thresholdValue = 0.1f;


    }

    public void apply( ) {
        threshold.set( "threshold", thresholdValue );
        for( int i = 0; i < blurStrength; i++ ) {
            p.filter( blur );
        }

        p.filter( threshold );
        p.filter( blur );
    }

    public void setThreshold( float _threshold ) {
        this.thresholdValue = _threshold;

    }
}

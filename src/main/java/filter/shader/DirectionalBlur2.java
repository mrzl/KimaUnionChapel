package filter.shader;

import pattern.ChladniSurface;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.opengl.PShader;

import java.io.File;

import static processing.core.PConstants.ADD;
import static processing.core.PConstants.BLEND;
import static processing.core.PConstants.P3D;

/**
 * Created by mrzl on 02.03.2015.
 */
public class DirectionalBlur2 {
    PApplet p;
    private PShader shader;
    private boolean enabled;
    private float delta1, delta2, mult;

    private float direction, radius;
    private PGraphics original;

    public DirectionalBlur2( PApplet p, ChladniSurface surf ) {
        this.p = p;
        this.shader = p.loadShader( "shader" + File.separator + "directional_blur2.glsl" );
        setEnabled( true );

        original = p.createGraphics( (int)(surf.getWidth()), (int)(surf.getHeight() ), P3D );
    }

    public void apply ( PGraphics pg ) {
        original.beginDraw( );
        original.image( pg, 0, 0 );
        original.endDraw( );
        //setBlurParameters( p.map( p.mouseX, 0, p.width, 0, PConstants.TWO_PI ), p.map( p.mouseY, 0, p.height, 0, 1 ) );
        //setDelta2(  );
        //setDelta1( p.map( p.mouseY, 0, p.height, 0, 1 ) );
        setMult( 1.0f );
        shader.set( "delta", getDelta1( ), getDelta2());
        shader.set( "mult", getMult( ) );
        this.shader.set( "resolution", ( float ) ( pg.width ), ( float ) ( pg.height ) );

        if( isEnabled() ) {
            pg.beginDraw( );
            pg.filter( shader );
            pg.endDraw( );

        }
    }

    public PGraphics getOriginal() {
        return original;
    }

    public boolean isEnabled () {
        return enabled;
    }

    public void setEnabled ( boolean enabled ) {
        this.enabled = enabled;
    }

    public float getDelta1 () {
        return delta1;
    }

    public void setBlurParameters ( float direction, float rad ) {
        float x = rad * p.cos( direction );
        float y = rad * p.sin( direction );

        setDelta1( x );
        setDelta2( y );
    }

    public void setDirection( float direction ) {
        this.direction = direction;
        setBlurParameters( this.direction, this.radius );
    }

    public void setRadius( float radius ) {
        this.radius = radius;
        setBlurParameters( this.direction, this.radius );
    }

    private void setDelta1 ( float delta1 ) {
        this.delta1 = delta1;
    }

    public float getMult () {
        return mult;
    }

    public void setMult( float mult ) {
        this.mult = mult;
    }

    public float getDelta2 () {
        return delta2;
    }

    private void setDelta2 ( float delta2 ) {
        this.delta2 = delta2;
    }
}

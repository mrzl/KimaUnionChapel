package modificators;

import processing.core.PApplet;
import processing.core.PGraphics;

/**
 * Created by mrzl on 10.01.2015.
 */
public class BloomModifier {

    private ThresholdShader thresh;
    private SepBlurShader blur;
    private boolean isEnabled;

    public BloomModifier( PApplet p  ) {
        thresh = new ThresholdShader( p );
        blur = new SepBlurShader( p );
        setEnabled( false );

        blur.setBlurSize( 28 );
        blur.setSigma( 4 );
        thresh.setThreshold( 0.01f );
    }

    public void apply( PGraphics pg ) {
        if( isEnabled() ) {
            thresh.apply( pg );
            blur.apply( pg );
        }
    }

    public void setEnabled( boolean enabled ) {
        this.isEnabled = enabled;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }
}

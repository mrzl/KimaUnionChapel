package main.transitions.blur;

/**
 * Created by mrzl on 08.03.2015.
 */
public class DirectionalBlurState {
    private float blurStrength;

    public DirectionalBlurState( float _bs) {
        this.blurStrength = _bs;
    }

    public float getBlurStrength () {
        return blurStrength;
    }

    public void setBlurStrength ( float blurStrength ) {
        this.blurStrength = blurStrength;
    }
}

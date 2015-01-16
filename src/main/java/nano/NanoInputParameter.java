package nano;


/**
 * Created by mrzl on 16.01.2015.
 */
public class NanoInputParameter {
    private NanoKontrolSliderEnum nanoKontrolSlider;
    private float min, max;

    public NanoInputParameter( NanoKontrolSliderEnum _slider, float min, float max ) {
        this.min = min;
        this.max = max;
        this.nanoKontrolSlider = _slider;
    }

    public float getMin() {
        return this.min;
    }

    public float getMax() {
        return this.max;
    }

    public NanoKontrolSliderEnum getType() {
        return nanoKontrolSlider;
    }
}

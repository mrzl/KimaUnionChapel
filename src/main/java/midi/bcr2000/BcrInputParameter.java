package midi.bcr2000;

/**
 * Created by mrzl on 22.01.2015.
 */
public class BcrInputParameter {
    private BcrKnobEnum bcrKnob;
    private float min, max;

    public BcrInputParameter( BcrKnobEnum _knob, float min, float max ) {
        this.min = min;
        this.max = max;
        this.bcrKnob = _knob;
    }

    public float getMin() {
        return this.min;
    }

    public float getMax() {
        return this.max;
    }

    public BcrKnobEnum getType() {
        return bcrKnob;
    }
}

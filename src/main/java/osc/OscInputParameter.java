package osc;

/**
 * Created by mrzl on 07.01.2015.
 */
public class OscInputParameter {
    private OscParameterInputEnum soundParameterType;
    private float min, max;

    public OscInputParameter ( OscParameterInputEnum type, float min, float max ) {
        this.min = min;
        this.max = max;
        this.soundParameterType = type;
    }

    public float getMin() {
        return this.min;
    }

    public float getMax() {
        return this.max;
    }

    public void setMin( float _min ) {
        this.min = _min;
    }

    public void setMax( float _max ) {
        this.max = _max;
    }

    public OscParameterInputEnum getType() {
        return this.soundParameterType;
    }
}

package osc;

/**
 * Created by mrzl on 07.01.2015.
 */
public class ChladniPatternParameter {

    private ChladniPatternParameterEnum chladniParameterType;
    private float min, max;

    public ChladniPatternParameter( ChladniPatternParameterEnum type, float min, float max ) {
        this.min = min;
        this.max = max;
        this.chladniParameterType = type;
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

    public ChladniPatternParameterEnum getType() {
        return this.chladniParameterType;
    }
}

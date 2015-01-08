package osc;


import osc.SoundInputParameterEnum;

/**
 * Created by mrzl on 07.01.2015.
 */
public class SoundInputParameter {
    private SoundInputParameterEnum soundParameterType;
    private float min, max;

    public SoundInputParameter( SoundInputParameterEnum type, float min, float max ) {
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

    public SoundInputParameterEnum getType() {
        return this.soundParameterType;
    }
}

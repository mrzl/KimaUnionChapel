package midi;

/**
 * Created by mrzl on 16.01.2015.
 */
public class VisualParameter {
    private VisualParameterEnum vpe;
    private float min, max;

    public VisualParameter( VisualParameterEnum _vpe, float min, float max ) {
        this.vpe = _vpe;
        this.min = min;
        this.max = max;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return this.max;
    }

    public VisualParameterEnum getType() {
        return vpe;
    }
}

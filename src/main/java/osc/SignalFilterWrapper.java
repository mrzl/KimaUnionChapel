package osc;

import filter.SignalFilter;

/**
 * Created by mrzl on 03.02.2015.
 */
public class SignalFilterWrapper {
    private SignalFilter filter;
    private boolean enabled;

    public SignalFilterWrapper( SignalFilter _filter ) {
        this.filter = _filter;
        this.enabled = false;
    }

    public void setEnabled( boolean _enabled ) {
        this.enabled = _enabled;
    }

    private boolean isEnabled() {
        return enabled;
    }

    public float applyFilter( float _value ) {
        float filteredValue = filter.filterUnitFloat( _value );
        float returnValue = isEnabled() ? filteredValue : _value;

        return returnValue;
    }
}

package main.transitions;

import main.KimaConstants;
import main.Main;
import osc.*;

/**
 * Created by mrzl on 20.02.2015.
 */
public class MareUndarumChapter3 extends Piece implements PieceInterface {

    public MareUndarumChapter3 ( TransitionController _tc ) {
        super( _tc );
    }

    @Override
    public void select () {
        // PERCUSSION CHANNEL 2
        OscParameterMapping mappingTriangle = new OscParameterMapping( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.RECT1 ) );
        OscInputParameter soundMapping12 = new OscInputParameter( OscParameterInputEnum.PEAK_PARAMETER2, KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX );
        ChladniPatternParameter chladniMapping12 = new ChladniPatternParameter( ChladniPatternParameterEnum.M, KimaConstants.RECTANGLE_M_MIN, KimaConstants.RECTANGLE_M_MAX );
        OscInputParameter soundMapping32 = new OscInputParameter( OscParameterInputEnum.ATTACK_PARAMETER2, KimaConstants.ATTACK_MIN, KimaConstants.ATTACK_MAX );
        ChladniPatternParameter chladniMapping32 = new ChladniPatternParameter( ChladniPatternParameterEnum.DRUM_HIT, 0.0f, 1.0f );
        OscInputParameter soundMapping22 = new OscInputParameter( OscParameterInputEnum.AMPLITUDE_PARAMETER2, KimaConstants.AMPLITUDE_PERCUSSION_MIN, KimaConstants.AMPLITUDE_PERCUSSION_MAX );
        ChladniPatternParameter chladniMapping22 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, KimaConstants.RECTANGLE_N_MIN, KimaConstants.RECTANGLE_N_MAX );
        mappingTriangle.addMapping( soundMapping12, chladniMapping12 );
        mappingTriangle.addMapping( soundMapping22, chladniMapping22 );
        mappingTriangle.addMapping( soundMapping32, chladniMapping32 );
        getTransitionController().getOscController( ).addSoundParameterMapping( mappingTriangle );

        // ORGAN CHANNEL 3
        OscParameterMapping mappingCircle = new OscParameterMapping( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.TRIANGLE1 ) );
        OscInputParameter soundMapping13 = new OscInputParameter( OscParameterInputEnum.PEAK_PARAMETER3, KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX );
        ChladniPatternParameter chladniMapping13 = new ChladniPatternParameter( ChladniPatternParameterEnum.SCALE, KimaConstants.TRIANGLE_SCALES_MIN, KimaConstants.TRIANGLE_SCALES_MAX );
        OscInputParameter soundMapping33 = new OscInputParameter( OscParameterInputEnum.ATTACK_PARAMETER3, KimaConstants.ATTACK_MIN, KimaConstants.ATTACK_MIN );
        ChladniPatternParameter chladniMapping33 = new ChladniPatternParameter( ChladniPatternParameterEnum.DRUM_HIT, 0.0f, 1.0f );
        mappingCircle.addMapping( soundMapping13, chladniMapping13 );
        mappingCircle.addMapping( soundMapping33, chladniMapping33 );
        getTransitionController().getOscController( ).addSoundParameterMapping( mappingCircle );
    }
}

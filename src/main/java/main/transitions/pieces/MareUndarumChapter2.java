package main.transitions.pieces;

import main.KimaConstants;
import main.Main;
import main.transitions.TransitionController;
import main.transitions.color.ColorState;
import main.transitions.color.ColorTransition;
import osc.*;

/**
 * Created by mrzl on 20.02.2015.
 */
public class MareUndarumChapter2 extends Piece implements PieceInterface {

    public MareUndarumChapter2 ( TransitionController _tc ) {
        super( _tc );
    }

    @Override
    public void select () {
        // PERCUSSION CHANNEL 2
        OscParameterMapping mappingTriangle = new OscParameterMapping( getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.RECT1 ) );
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
        OscParameterMapping mappingCircle = new OscParameterMapping( getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.CIRCLE_RECONSTRUCTION ) );
        OscInputParameter soundMapping13 = new OscInputParameter( OscParameterInputEnum.PEAK_PARAMETER3, KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX );
        ChladniPatternParameter chladniMapping13 = new ChladniPatternParameter( ChladniPatternParameterEnum.M, KimaConstants.CIRCLE_RECONSTRUCTION_M_MIN, KimaConstants.CIRCLE_RECONSTRUCTION_M_MAX );
        OscInputParameter soundMapping23 = new OscInputParameter( OscParameterInputEnum.PEAK_PARAMETER3, KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX );
        ChladniPatternParameter chladniMapping23 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, KimaConstants.CIRCLE_RECONSTRUCTION_N_MIN, KimaConstants.CIRCLE_RECONSTRUCTION_N_MAX );
        mappingCircle.addMapping( soundMapping13, chladniMapping13 );
        mappingCircle.addMapping( soundMapping23, chladniMapping23 );
        getTransitionController().getOscController( ).addSoundParameterMapping( mappingCircle );

        startColorTransition();
    }

    @Override
    public void startColorTransition () {
        ColorState colorStateCircleFrom = new ColorState().setHue( 54, 54 ).setSaturation( 255 ).setBrightness( 231 );
        ColorState colorStateCircleTo = new ColorState().setHue( 48, 48 ).setSaturation( 219 ).setBrightness( 234 );
        ColorTransition transitionCircle = new ColorTransition( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.CIRCLE_RECONSTRUCTION ), colorStateCircleFrom, colorStateCircleTo, durationMillis );
        transitionCircle.start();

        ColorState rectFrom = new ColorState().setHue( 210, 210 ).setSaturation( 82 ).setBrightness( 205 );
        ColorState rectTo = new ColorState().setHue( 60, 60 ).setSaturation( 255 ).setBrightness( 255 );
        ColorTransition transitionRect = new ColorTransition( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.RECT1 ), rectFrom, rectTo, durationMillis );
        transitionRect.start();

        transitions.add( transitionCircle );
        transitions.add( transitionRect );
    }
}

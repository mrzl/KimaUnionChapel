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
        enableCircle();
        enableRect();
        disableTriangle();

        // PERCUSSION CHANNEL 1
        OscParameterMapping mappingTriangle = new OscParameterMapping( getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.RECT1 ) );
        OscInputParameter soundMapping12 = new OscInputParameter( OscParameterInputEnum.PEAK_PARAMETER1, KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX );
        ChladniPatternParameter chladniMapping12 = new ChladniPatternParameter( ChladniPatternParameterEnum.M, KimaConstants.RECTANGLE_M_MIN, KimaConstants.RECTANGLE_M_MAX );
        OscInputParameter soundMapping32 = new OscInputParameter( OscParameterInputEnum.ATTACK_PARAMETER1, KimaConstants.ATTACK_MIN, KimaConstants.ATTACK_MAX );
        ChladniPatternParameter chladniMapping32 = new ChladniPatternParameter( ChladniPatternParameterEnum.DRUM_HIT, 0.0f, 1.0f );
        OscInputParameter soundMapping22 = new OscInputParameter( OscParameterInputEnum.AMPLITUDE_PARAMETER1, KimaConstants.AMPLITUDE_PERCUSSION_MIN, KimaConstants.AMPLITUDE_PERCUSSION_MAX );
        ChladniPatternParameter chladniMapping22 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, KimaConstants.RECTANGLE_N_MIN, KimaConstants.RECTANGLE_N_MAX );
        mappingTriangle.addMapping( soundMapping12, chladniMapping12 );
        mappingTriangle.addMapping( soundMapping22, chladniMapping22 );
        mappingTriangle.addMapping( soundMapping32, chladniMapping32 );
        getTransitionController().getOscController( ).addSoundParameterMapping( mappingTriangle );

        // ORGAN CHANNEL 3
        OscParameterMapping mappingCircle = new OscParameterMapping( getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.CIRCLE1 ) );
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
        ColorState colorStateCircleFrom = new ColorState().setHue( 57, 57 ).setSaturation( 20 ).setBrightness( 100 );
        ColorState colorStateCircleTo = new ColorState().setHue( 48, 48 ).setSaturation( 30 ).setBrightness( 99 );
        ColorTransition transitionCircle = new ColorTransition( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.CIRCLE1 ), colorStateCircleFrom, colorStateCircleTo, durationMillis );

        ColorState colorStateCircleFrom2 = new ColorState().setHue( 48, 48 ).setSaturation( 30 ).setBrightness( 99 );
        ColorState colorStateCircleTo2 = new ColorState().setHue( 41, 41 ).setSaturation( 50 ).setBrightness( 99 );
        ColorTransition transitionCircle2 = new ColorTransition( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.CIRCLE1 ), colorStateCircleFrom2, colorStateCircleTo2, durationMillis );
        transitionCircle.setSecondTrans( transitionCircle2 );
        transitionCircle.start();

        ColorState rectFrom = new ColorState().setHue( 211, 211 ).setSaturation( 16 ).setBrightness( 85 );
        ColorState rectTo = new ColorState().setHue( 60, 60 ).setSaturation( 7 ).setBrightness( 100 );
        ColorTransition transitionRect = new ColorTransition( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.RECT1 ), rectFrom, rectTo, durationMillis );

        ColorState rectFrom2 = new ColorState().setHue( 60, 60 ).setSaturation( 7 ).setBrightness( 100 );
        ColorState rectTo2 = new ColorState().setHue( 47, 47 ).setSaturation( 29 ).setBrightness( 99 );
        ColorTransition transitionRect2 = new ColorTransition( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.RECT1 ), rectFrom2, rectTo2, durationMillis );
        transitionRect.setSecondTrans( transitionRect2 );
        transitionRect.start();

        transitions.add( transitionCircle );
        transitions.add( transitionRect );
    }
}

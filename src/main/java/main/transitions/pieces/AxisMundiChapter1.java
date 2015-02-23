package main.transitions.pieces;

import main.KimaConstants;
import main.Main;
import main.transitions.TransitionController;
import osc.*;

/**
 * Created by mrzl on 20.02.2015.
 */
public class AxisMundiChapter1  extends Piece implements PieceInterface {

    public AxisMundiChapter1 ( TransitionController _tc ) {
        super( _tc );
    }

    @Override
    public void select () {
        // CELLO - SQUARE - CHANNEL 1
        OscParameterMapping chelloRectChannel1 = new OscParameterMapping( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.RECT1 ) );
        OscInputParameter sin11 = new OscInputParameter( OscParameterInputEnum.PEAK_PARAMETER1, KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX );
        ChladniPatternParameter cpp11 = new ChladniPatternParameter( ChladniPatternParameterEnum.M, KimaConstants.RECTANGLE_M_MIN, KimaConstants.RECTANGLE_M_MAX );
        OscInputParameter sin12 = new OscInputParameter( OscParameterInputEnum.FUNDAMENTAL_PARAMETER1, KimaConstants.FUNDAMENTAL_MIN, KimaConstants.FUNDAMENTAL_MAX );
        ChladniPatternParameter cpp12 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, KimaConstants.RECTANGLE_N_MIN, KimaConstants.RECTANGLE_N_MAX );
        chelloRectChannel1.addMapping( sin11, cpp11 );
        chelloRectChannel1.addMapping( sin12, cpp12 );

        // VIOLA - CIRCLE - CHANNEL 2
        OscParameterMapping violaCircleChannel2 = new OscParameterMapping( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.CIRCLE_RECONSTRUCTION ) );
        OscInputParameter sin21 = new OscInputParameter( OscParameterInputEnum.PEAK_PARAMETER2, KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX );
        ChladniPatternParameter cip21 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, KimaConstants.RECTANGLE_N_MIN, KimaConstants.RECTANGLE_N_MAX );
        OscInputParameter sin22 = new OscInputParameter( OscParameterInputEnum.FUNDAMENTAL_PARAMETER1, KimaConstants.FUNDAMENTAL_MIN, KimaConstants.FUNDAMENTAL_MAX );
        ChladniPatternParameter cip22 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, KimaConstants.RECTANGLE_N_MIN, KimaConstants.RECTANGLE_N_MAX );
        violaCircleChannel2.addMapping( sin21, cip21 );
        violaCircleChannel2.addMapping( sin22, cip22 );

        // ORGAN - TRIANGLE - CHANNEL 3
        OscParameterMapping organTriangleChannel3 = new OscParameterMapping( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.TRIANGLE1 ) );
        OscInputParameter sin31 = new OscInputParameter( OscParameterInputEnum.PEAK_PARAMETER3, KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX );
        ChladniPatternParameter cip31 = new ChladniPatternParameter( ChladniPatternParameterEnum.SCALE, KimaConstants.TRIANGLE_SCALES_MIN, KimaConstants.TRIANGLE_SCALES_MAX );
        organTriangleChannel3.addMapping( sin31, cip31 );

        getTransitionController().getOscController().addSoundParameterMapping( chelloRectChannel1 );
        getTransitionController().getOscController().addSoundParameterMapping( violaCircleChannel2 );
        getTransitionController().getOscController().addSoundParameterMapping( organTriangleChannel3 );

        startColorTransition();
    }

    @Override
    public void startColorTransition () {
        getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.RECT1 ).setParticleOpacity( 0.0f );
        getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).setParticleOpacity( 0.0f );
        getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.CIRCLE_RECONSTRUCTION ).setParticleOpacity( 0.0f );

       /* durationMillis = 100;
        ColorState colorStateTriangleFrom = new ColorState().setHue( 11 ).setSaturation( 250 ).setBrightness( 113 );
        ColorState colorStateTriangleTo = new ColorState().setHue( 11 ).setSaturation( 250 ).setBrightness( 113 );
        ColorTransition transitionTriangle = new ColorTransition( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.TRIANGLE1 ), colorStateTriangleFrom, colorStateTriangleTo, durationMillis );
        transitionTriangle.start( );

        ColorState circleFrom = new ColorState().setHue( 21 ).setSaturation( 252 ).setBrightness( 155 );
        ColorState circleTo = new ColorState().setHue( 21 ).setSaturation( 252 ).setBrightness( 155 );
        ColorTransition transitionCircle = new ColorTransition( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.CIRCLE_RECONSTRUCTION ), circleFrom, circleTo, durationMillis );
        transitionCircle.start( );

        ColorState rectFrom = new ColorState().setHue( 56 ).setSaturation( 244 ).setBrightness( 166 );
        ColorState rectTo = new ColorState().setHue( 56 ).setSaturation( 244 ).setBrightness( 166 );
        ColorTransition transitionRect = new ColorTransition( getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.RECT1 ), rectFrom, rectTo, durationMillis );
        transitionRect.start();*/
    }
}

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
public class AxisMundiChapter4  extends Piece implements PieceInterface  {

    public AxisMundiChapter4 ( TransitionController _tc ) {
        super( _tc );
    }

    @Override
    public void select () {
        enableCircle();
        enableTriangle();
        enableRect();

        // CELLO - SQUARE - CHANNEL 1
        OscParameterMapping chelloRectChannel1 = new OscParameterMapping( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.RECT1 ) );
        OscInputParameter sin11 = new OscInputParameter( OscParameterInputEnum.FREQUENCY_PARAMETER1, KimaConstants.MIN_FREQUENCY, KimaConstants.MAX_FREQUENCY );
        ChladniPatternParameter cpp11 = new ChladniPatternParameter( ChladniPatternParameterEnum.M, KimaConstants.RECTANGLE_M_MIN, KimaConstants.RECTANGLE_M_MAX );
        OscInputParameter sin12 = new OscInputParameter( OscParameterInputEnum.AMPLITUDE_PARAMETER1, KimaConstants.MIN_AMPLITUDE, KimaConstants.MAX_AMPLITUDE );
        ChladniPatternParameter cpp12 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, KimaConstants.RECTANGLE_N_MIN, KimaConstants.RECTANGLE_N_MAX );
        chelloRectChannel1.addMapping( sin11, cpp11 );
        chelloRectChannel1.addMapping( sin12, cpp12 );

        // VIOLA - CIRCLE - CHANNEL 2
        OscParameterMapping violaCircleChannel2 = new OscParameterMapping( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.CIRCLE1 ) );
        OscInputParameter sin21 = new OscInputParameter( OscParameterInputEnum.FREQUENCY_PARAMETER2, KimaConstants.MIN_FREQUENCY, KimaConstants.MAX_FREQUENCY );
        ChladniPatternParameter cip21 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, KimaConstants.CIRCLE_N_MIN, KimaConstants.CIRCLE_N_MAX );
        OscInputParameter sin22 = new OscInputParameter( OscParameterInputEnum.AMPLITUDE_PARAMETER2, KimaConstants.MIN_AMPLITUDE, KimaConstants.MAX_AMPLITUDE );
        ChladniPatternParameter cip22 = new ChladniPatternParameter( ChladniPatternParameterEnum.M, KimaConstants.CIRCLE_M_MIN, KimaConstants.CIRCLE_M_MAX );
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
        getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.RECT1 ).setParticleOpacity( 0.3f );
        getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.TRIANGLE1 ).setParticleOpacity( 0.3f );
        getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.CIRCLE1 ).setParticleOpacity( 0.3f );

        ColorState colorStateTriangleFrom = new ColorState().setHue( 238, 313 ).setSaturation( 80 ).setBrightness( 98 );
        ColorState colorStateTriangleTo = new ColorState().setHue( 238, 313 ).setSaturation( 80 ).setBrightness( 98 );
        ColorTransition transitionTriangle = new ColorTransition( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.TRIANGLE1 ), colorStateTriangleFrom, colorStateTriangleTo, 180000 );
        transitionTriangle.start( );

        ColorState circleFrom = new ColorState().setHue( 248, 331 ).setSaturation( 80 ).setBrightness( 98 );
        ColorState circleTo = new ColorState().setHue( 248, 331 ).setSaturation( 80 ).setBrightness( 98 );
        ColorTransition transitionCircle = new ColorTransition( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.CIRCLE1 ), circleFrom, circleTo, 180000 );
        transitionCircle.start( );

        ColorState rectFrom = new ColorState().setHue( 281, 301 ).setSaturation( 50 ).setBrightness( 98 );
        ColorState rectTo = new ColorState().setHue( 281, 301 ).setSaturation( 50 ).setBrightness( 98 );
        ColorTransition transitionRect = new ColorTransition( getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.RECT1 ), rectFrom, rectTo, 180000 );
        transitionRect.start( );

        transitions.add( transitionCircle );
        transitions.add( transitionRect );
        transitions.add( transitionTriangle );
    }
}

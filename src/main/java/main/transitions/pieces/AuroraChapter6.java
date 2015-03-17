package main.transitions.pieces;

import main.KimaConstants;
import main.Main;
import main.transitions.TransitionController;
import main.transitions.blur.DirectionBlurTransition;
import main.transitions.blur.DirectionalBlurState;
import main.transitions.color.ColorState;
import main.transitions.color.ColorTransition;
import osc.*;

/**
 * Created by mrzl on 20.02.2015.
 */
public class AuroraChapter6 extends Piece implements PieceInterface {

    public AuroraChapter6 ( TransitionController _tc ) {
        super( _tc );
    }

    @Override
    public void select () {
        enableRect();
        disableTriangle();
        enableCircle();

        // VOICE - CIRCLE - CHANNEL 2
        OscParameterMapping voiceTriangleChannel1 = new OscParameterMapping( getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.CIRCLE1 ) );
        OscInputParameter sin11 = new OscInputParameter( OscParameterInputEnum.PEAK_PARAMETER2, KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX );
        ChladniPatternParameter cpp11 = new ChladniPatternParameter( ChladniPatternParameterEnum.M, KimaConstants.CIRCLE_M_MIN, KimaConstants.CIRCLE_M_MAX );
        OscInputParameter sin12 = new OscInputParameter( OscParameterInputEnum.FUNDAMENTAL_PARAMETER2, KimaConstants.FUNDAMENTAL_MIN, KimaConstants.FUNDAMENTAL_MAX );
        ChladniPatternParameter cpp12 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, KimaConstants.CIRCLE_N_MIN, KimaConstants.CIRCLE_N_MAX );
        voiceTriangleChannel1.addMapping( sin11, cpp11 );
        voiceTriangleChannel1.addMapping( sin12, cpp12 );

        // ORGAN - SQUARE - CHANNEL 3
        OscParameterMapping organRectChannel3 = new OscParameterMapping( getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.RECT1 ) );
        OscInputParameter sin31 = new OscInputParameter( OscParameterInputEnum.PEAK_PARAMETER3, KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX );
        ChladniPatternParameter cpp31 = new ChladniPatternParameter( ChladniPatternParameterEnum.M, KimaConstants.RECTANGLE_M_MIN, KimaConstants.RECTANGLE_M_MAX );
        OscInputParameter sin32 = new OscInputParameter( OscParameterInputEnum.FREQUENCY_PARAMETER3, KimaConstants.FREQUENCY_ORGAN_MIN, KimaConstants.FREQUENCY_ORGAN_MAX );
        ChladniPatternParameter cpp32 = new ChladniPatternParameter( ChladniPatternParameterEnum.N, KimaConstants.RECTANGLE_N_MIN, KimaConstants.RECTANGLE_N_MAX );
        organRectChannel3.addMapping( sin31, cpp31 );
        organRectChannel3.addMapping( sin32, cpp32 );

        getTransitionController().getOscController().addSoundParameterMapping( voiceTriangleChannel1 );
        getTransitionController().getOscController().addSoundParameterMapping( organRectChannel3 );

        startColorTransition();
    }

    @Override
    public void startColorTransition () {
        selectCustomAuroraParameters();

        ColorState colorStateCircleFrom = new ColorState().setHue( 60, 60 ).setSaturation( 14 ).setBrightness( 98 );
        ColorState colorStateCircleTo = new ColorState().setHue( 60, 60 ).setSaturation( 14 ).setBrightness( 98 );
        ColorTransition transitionCircle = new ColorTransition( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.CIRCLE1 ), colorStateCircleFrom, colorStateCircleTo, durationMillis );
        transitionCircle.start( );

        ColorState rectFrom = new ColorState().setHue( 190, 190 ).setSaturation( 24 ).setBrightness( 98 );
        ColorState rectTo = new ColorState().setHue( 190, 190 ).setSaturation( 24 ).setBrightness( 98 );
        ColorTransition transitionRect = new ColorTransition( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.RECT1 ), rectFrom, rectTo, durationMillis );
        transitionRect.start();

        DirectionalBlurState fromBlurTriangle = new DirectionalBlurState( 0.22f );
        DirectionalBlurState toBlurTriangle = new DirectionalBlurState( 0.23f );

        DirectionBlurTransition blurTransitionCircle = new DirectionBlurTransition( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.CIRCLE1 ), fromBlurTriangle, toBlurTriangle, durationMillis );
        blurTransitionCircle.start( );

        DirectionalBlurState fromBlurRect = new DirectionalBlurState( 0.22f );
        DirectionalBlurState toBlurRect = new DirectionalBlurState( 0.23f );

        DirectionBlurTransition blurTransitionRect = new DirectionBlurTransition( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.RECT1 ), fromBlurRect, toBlurRect, durationMillis );
        blurTransitionRect.start( );

        transitions.add( transitionCircle );
        transitions.add( transitionRect );
        transitions.add( blurTransitionCircle );
        transitions.add( blurTransitionRect );
    }
}

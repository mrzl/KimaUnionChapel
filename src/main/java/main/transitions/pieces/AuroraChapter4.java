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
public class AuroraChapter4  extends Piece implements PieceInterface {

    public AuroraChapter4 ( TransitionController _tc ) {
        super( _tc );
    }

    @Override
    public void select () {
        enableRect();
        enableTriangle();
        disableCircle();

        // VOICE - TRIANGLE - CHANNEL 2
        OscParameterMapping voiceTriangleChannel1 = new OscParameterMapping( getTransitionController().getMain().chladniForms.get( Main.ChladniFormId.TRIANGLE1 ) );
        OscInputParameter sin11 = new OscInputParameter( OscParameterInputEnum.PEAK_PARAMETER2, KimaConstants.PEAK_MIN, KimaConstants.PEAK_MAX );
        ChladniPatternParameter cpp11 = new ChladniPatternParameter( ChladniPatternParameterEnum.SCALE, KimaConstants.TRIANGLE_SCALES_MIN, KimaConstants.TRIANGLE_SCALES_MAX );
        voiceTriangleChannel1.addMapping( sin11, cpp11 );

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

        ColorState colorStateCircleFrom = new ColorState().setHue( 45, 45 ).setSaturation( 99 ).setBrightness( 100 );
        ColorState colorStateCircleTo = new ColorState().setHue( 56, 56 ).setSaturation( 60 ).setBrightness( 98 );
        ColorTransition transitionTriangle = new ColorTransition( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.TRIANGLE1 ), colorStateCircleFrom, colorStateCircleTo, durationMillis );
        transitionTriangle.start( );

        ColorState rectFrom = new ColorState().setHue( 207, 207 ).setSaturation( 47 ).setBrightness( 100 );
        ColorState rectTo = new ColorState().setHue( 198, 198 ).setSaturation( 35 ).setBrightness( 99 );
        ColorTransition transitionRect = new ColorTransition( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.RECT1 ), rectFrom, rectTo, durationMillis );
        transitionRect.start();

        DirectionalBlurState fromBlurTriangle = new DirectionalBlurState( 0.20f );
        DirectionalBlurState toBlurTriangle = new DirectionalBlurState( 0.21f );

        DirectionBlurTransition blurTransitionTriangle = new DirectionBlurTransition( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.TRIANGLE1 ), fromBlurTriangle, toBlurTriangle, durationMillis );
        blurTransitionTriangle.start( );

        DirectionalBlurState fromBlurRect = new DirectionalBlurState( 0.20f );
        DirectionalBlurState toBlurRect = new DirectionalBlurState( 0.21f );

        DirectionBlurTransition blurTransitionRect = new DirectionBlurTransition( getTransitionController().getMain( ).chladniForms.get( Main.ChladniFormId.RECT1 ), fromBlurRect, toBlurRect, durationMillis );
        blurTransitionRect.start( );

        transitions.add( transitionTriangle );
        transitions.add( transitionRect );
        transitions.add( blurTransitionTriangle );
        transitions.add( blurTransitionRect );
    }
}

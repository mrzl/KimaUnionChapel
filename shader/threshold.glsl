#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_TEXTURE_SHADER

uniform sampler2D texture;
uniform vec2 texOffset;
uniform float threshold;

varying vec4 vertColor;
varying vec4 vertTexCoord;


void main(void) {
	vec4 texColor = texture2D(texture, vertTexCoord.st).rgba;
	if( texColor.r > threshold) {
		gl_FragColor = vec4(1.0, 1.0, 1.0, 1.0);  
	} else {
		gl_FragColor = vec4(0.0, 0.0, 0.0, 1.0);  
	}
}
#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_TEXTURE_SHADER

uniform sampler2D texture;
uniform vec2 texOffset;
uniform float threshold;
uniform float hue;

varying vec4 vertColor;
varying vec4 vertTexCoord;

vec3 hsv2rgb(vec3 c)
{
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

void main(void) {
	vec4 texColor = texture2D(texture, vertTexCoord.st).rgba;
	if( texColor.r > threshold) {
		gl_FragColor = vec4( hsv2rgb(vec3(hue, 1.0, 1.0)), 1.0 );
	} else {
		gl_FragColor = vec4(0.0, 0.0, 0.0, 1.0);  
	}
}
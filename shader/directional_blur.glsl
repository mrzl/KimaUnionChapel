#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_TEXTURE_SHADER

uniform sampler2D texture;
uniform float gain;
uniform float iterations;
uniform float blur_x;
uniform float blur_y;
uniform vec2 texOffset;
uniform vec2 resolution;

varying vec4 vertColor;
varying vec4 vertTexCoord;

float random(vec3 scale, float seed) {
    return fract(sin(dot(gl_FragCoord.xyz + seed, scale)) * 8643.5453 + seed);
}

void main(void) {
	vec2 uv = gl_FragCoord.xy / resolution.xy;
	vec2 direction;
	direction = vec2(blur_x,blur_y);
	float noise = random(vec3(543.12341, 74.30434, 13123.4234234), 2.0);
	vec4 color = vec4(0.0);
	float ws = 0.0;

	for(float steps = -iterations; steps <= iterations; steps++) {
		float p = (steps + noise - 0.5) / 16.0;
		float w = 1.0 - abs(p);
		color += texture2D(texture, uv + direction*.02 * p) * w;
		ws += w;
	}
	gl_FragColor = vec4(color.rgb / ws * gain, 1.0);
}
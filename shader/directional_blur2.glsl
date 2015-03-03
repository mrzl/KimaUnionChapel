#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#ifndef ITE
#define ITE 8.0
#endif

uniform sampler2D texture;
uniform vec2 delta;
uniform float mult;
uniform vec2 resolution;


float random(vec3 scale, float seed) {
    return fract(sin(dot(gl_FragCoord.xyz + seed, scale)) * 675.5453 + seed);
}

void main(void) {
    vec2 uv = gl_FragCoord.xy / resolution.xy;
    vec4 originalColor = texture2D(texture, uv );
    float offset = random(vec3(24.6546, 13.4905, 210.8789), 2.0);
    vec4 c = vec4(0.0);
    float ws = 0.0;

	for(float t = -ITE; t <= ITE; t++) {
        float p = (t + offset - 0.5) / 16.0;
        float w = 1.0 - abs(p);
        c += texture2D(texture, uv + delta * p) * w;
        ws += w;
    }


	gl_FragColor = vec4((c.rgb / ws * mult) + (originalColor.xyz / 3.0), 1.0);
	
}
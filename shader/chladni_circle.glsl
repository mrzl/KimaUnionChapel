#version 120

#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_COLOR_SHADER
#define M_PI 3.1415926535897932384626433832795

uniform sampler2D texture;
uniform vec2 texOffset;

uniform vec2 resolution;
uniform float m;
uniform float n;
uniform float time;
uniform float scale;
uniform int poles;
uniform bool drawMonochrome;
uniform float minHue, maxHue, intensity;

float ripple(float dist, float shift)
{
	return cos(64.0 * dist + shift) / (1.0 + 1.0 * dist);
}

float rand(vec2 co){
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}


vec3 rgb2hsv(vec3 c)
{
    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));

    float d = q.x - min(q.w, q.y);
    float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}


vec3 hsv2rgb(vec3 c)
{
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

float map(float value, float start1, float stop1, float start2, float stop2) {
    return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
}

void main(void) {

  float larger = max(resolution.x, resolution.y) / scale;
  	vec2 uv = (gl_FragCoord.xy - .5*resolution.xy) / larger;
  	vec2 uvflip = vec2(uv.x, -uv.y);
  	vec2 cursor = (vec2( m, n ) - .5*resolution.xy) / larger;
  	vec2 blessr = vec2(-cursor.x, cursor.y);
  	vec2 position = ( gl_FragCoord.xy / resolution.xy );

  	float lum = .5 + 0.1 * ripple(length(uv), 0.0) + 0.0;

  	float twopi = 2.0 * M_PI;
  	int count = poles;
  	float fcount = float(count);
  	vec2 rot = vec2(cos(twopi*.618), sin(twopi*.618));
  	vec2 tor = vec2(-sin(twopi*.618), cos(twopi*.618));
  	for (int i = 0; i < count; ++i)
  	{
  		lum += .2 * ripple(length(cursor - uv), -time);
  		cursor = cursor.x*rot + cursor.y*tor;
  	}

  	lum = 3.0*lum*lum - 2.0*lum*lum*lum;

  	float mapped = map( lum, 0.0, 1.0, minHue, maxHue );
  	vec3 finalColor;

  	if( drawMonochrome) {
  	  finalColor = vec3( lum, lum, lum );
  	} else {
  	  finalColor = hsv2rgb( vec3( mapped, 1.0, lum * intensity ) );
  	}

    gl_FragColor = vec4(finalColor, 1.0);
}

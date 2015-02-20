#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_TEXTURE_SHADER

uniform sampler2D texture;
uniform vec2 texOffset;
uniform vec2 resolution;

varying vec4 vertColor;
varying vec4 vertTexCoord;
uniform float minHue;
uniform float maxHue;

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
  vec2 position = ( gl_FragCoord.xy / resolution.xy );
  vec4 col0 = texture2D(texture, position);

  vec3 hueColorOfInput = rgb2hsv( col0.xyz );

  float hueVal = map(hueColorOfInput.z, 0.0, 1.0, minHue, maxHue);
  //float satVal = map(hueColorOfInput.z,  );
  vec3 finalColor = hsv2rgb( vec3(hueVal, 1.0, hueColorOfInput.z + 0.3 ) );

  gl_FragColor = vec4( finalColor, 1.0 );
}

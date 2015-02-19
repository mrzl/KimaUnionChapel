#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_COLOR_SHADER
#define M_PI 3.1415926535897932384626433832795
//#define POLES 40
#define REFLECTIONS 10.0

uniform sampler2D texture;
uniform vec2 texOffset;

uniform vec2 resolution;
uniform vec2 mouse;
uniform float time;
uniform float scale;
uniform int poles;

float ripple(float dist, float shift)
{
	return cos(64.0 * dist + shift) / (1.0 + 1.0 * dist);
}

float rand(vec2 co){
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

void main(void) {

  float larger = max(resolution.x, resolution.y) / scale;
  	vec2 uv = (gl_FragCoord.xy - .5*resolution.xy) / larger;
  	vec2 uvflip = vec2(uv.x, -uv.y);
  	vec2 cursor = (mouse.xy - .5*resolution.xy) / larger;
  	vec2 blessr = vec2(-cursor.x, cursor.y);
  	vec2 position = ( gl_FragCoord.xy / resolution.xy );

  	//float on = float(abs(uv.x)<.25 && abs(uv.y)<.25);

  	float lum = .5 +
  		/*.1 * ripple(length(cursor - uv), -iGlobalTime) +
  		.1 * ripple(length(blessr - uv), -iGlobalTime) +
  		.1 * ripple(length(cursor - uvflip), -iGlobalTime) +
  		.1 * ripple(length(blessr - uvflip), -iGlobalTime) +*/
  		.1 * ripple(length(uv), 0.0) +
  		//.1 * cos(64.0*uv.y - iGlobalTime) +
  		//.1 * cos(64.0*(uv.x*uv.x) - iGlobalTime) +
  		0.0;

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

  	/*float lum = .5, dist;
  	vec2 part, flip = vec2(1.0, 1.0);

  	//float freq = 64.0, phase = -iGlobalTime;
  	float freq = 32.0, phase  = 0.0; // * pow(4.0, cos(iGlobalTime/8.0)), phase = 0.0;

  	for (float ox = -REFLECTIONS; ox <= REFLECTIONS; ox += 1.0)
  	{
  		for (float oy = -REFLECTIONS; oy <= REFLECTIONS; oy += 1.0)
  		{
  			dist = length((cursor*flip-uv)+vec2(ox, oy));
  			lum += cos(freq * dist - phase) / (5.0 + 10.0*dist);

  			flip.y *= -1.0;
  		}
  		flip.x *= -1.0;
  	}*/

  	lum = 3.0*lum*lum - 2.0*lum*lum*lum;

  if( rand( position ) > lum ) {
    lum = 1.0;
  } else {
    lum = 0.0;
  }

    gl_FragColor = vec4(lum, lum, lum, 1.0);


  	/*gl_FragColor = vec4(.5+.5*sin(3000.0*iGlobalTime),
  		.5+.5*sin(4997.0*iGlobalTime+iResolution.x*3910.0),
  		.5+.5*cos(2872.0*iGlobalTime+iResolution.y*8721.0), 1.0);*/
}

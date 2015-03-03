#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_COLOR_SHADER
#define M_PI 3.1415926535897932384626433832795

uniform sampler2D texture;
uniform vec2 resolution;
uniform int nthZero;
uniform float m;
uniform bool drawMonochrome;
uniform float minHue, maxHue, intensity, saturation;
uniform float cutoff;

float j0(float x) {
	float ax;

	if( (ax=abs(x)) < 8.0 ) {
	   float y=x*x;
	   float ans1=57568490574.0+y*(-13362590354.0+y*(651619640.7
				   +y*(-11214424.18+y*(77392.33017+y*(-184.9052456)))));
	   float ans2=57568490411.0+y*(1029532985.0+y*(9494680.718
				   +y*(59272.64853+y*(267.8532712+y*1.0))));

	   return ans1/ans2;

	}
	else {
	   float z=8.0/ax;
	   float y=z*z;
	   float xx=ax-0.785398164;
	   float ans1=1.0+y*(-0.1098628627e-2+y*(0.2734510407e-4
				   +y*(-0.2073370639e-5+y*0.2093887211e-6)));
	   float ans2 = -0.1562499995e-1+y*(0.1430488765e-3
				   +y*(-0.6911147651e-5+y*(0.7621095161e-6
				   -y*0.934935152e-7)));
        float first = sqrt(0.636619772/ax);
        float second = (cos(xx)*ans1-z*sin(xx)*ans2);
	   return (first*second);
	}
}

float j1(float x) {
	float ax;
	float y;
	float ans1, ans2;

	if ((ax=abs(x)) < 8.0) {
		y=x*x;
		ans1=x*(72362614232.0+y*(-7895059235.0+y*(242396853.1
		   +y*(-2972611.439+y*(15704.48260+y*(-30.16036606))))));
		ans2=144725228442.0+y*(2300535178.0+y*(18583304.74
		   +y*(99447.43394+y*(376.9991397+y*1.0))));
		return ans1/ans2;
	}
	else {
		float z=8.0/ax;
		float xx=ax-2.356194491;
		y=z*z;

		ans1=1.0+y*(0.183105e-2+y*(-0.3516396496e-4
		  +y*(0.2457520174e-5+y*(-0.240337019e-6))));
		ans2=0.04687499995+y*(-0.2002690873e-3
		  +y*(0.8449199096e-5+y*(-0.88228987e-6
		  +y*0.105787412e-6)));
		float ans=sqrt(0.636619772/ax)*
			   (cos(xx)*ans1-z*sin(xx)*ans2);
		if (x < 0.0) ans = -ans;
		return ans;
	}
}

float jn(int n, float x ) {
	int j,m;
	float ax,bj,bjm,bjp,sum,tox,ans;
	bool jsum;

	float ACC = 40.0;
	float BIGNO = 1.0e+10;
	float BIGNI = 1.0e-10;

	if(n == 0) {
	    float val = j0(x);
	    return val;
	}
	if(n == 1){
	    float val = j1(x);
	    return val;
	}

	ax=abs(x);
	if(ax == 0.0)  return 0.0;

	if (ax > float(n)) {
		tox=2.0/ax;
		bjm=j0(ax);
		bj=j1(ax);
		for (j=1;j<n;j++) {
			bjp=float(j)*tox*bj-bjm;
			bjm=bj;
			bj=bjp;
		}
		ans=bj;
	}
	else {
		tox=2.0/ax;
		m=2*((n+int(sqrt(ACC*float(n))))/2);
		jsum=false;
		bjp= 0.0;
		ans= 0.0;
		sum=0.0;
		bj=1.0;
		for (j=m;j>0;j--) {
			bjm=float(j)*tox*bj-bjp;
			bjp=bj;
			bj=bjm;
			if (abs(bj) > BIGNO) {
				bj *= BIGNI;
				bjp *= BIGNI;
				ans *= BIGNI;
				sum *= BIGNI;
			}
			if (jsum) sum += bj;
			jsum=!jsum;
			if (j == n) ans=bjp;
		}
		sum=2.0*sum-bj;
		ans /= sum;
	}
	if( x < 0.0 && mod( float(n), 2.0 ) == 1.0 ) {
	    return 0.0 - ans;
	} else {
	    return ans;
	}
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

void main() {
    vec2 uv = gl_FragCoord.xy;
    uv -= vec2( resolution.x * 0.5, resolution.y * 0.5 );

    float val2 = jn( 2, 2.0 );
    float dist =  dot(uv, uv);
    float circle_radius = resolution.x * 0.5;
    if ( length(uv) > circle_radius ) {
         gl_FragColor = vec4(0.0, 0.0, 0.0, 1.0);
    }
    else {
      float radius = length(uv) / resolution.x;
      float xpos = uv.x / resolution.x;
      float ypos = uv.y / resolution.y;
      float theta = atan( ypos / xpos );
      float first = jn( int(m), float(nthZero) * radius );
      float second = sin( float(m) * theta );
      float third = cos( float(nthZero) );
      float res = first * second * third;
      float finalValue = abs(10.0*res);

      float mapped = map( finalValue, 0.0, 1.0, minHue, maxHue );
      vec3 finalColor;

      if( drawMonochrome ) {
        finalColor = vec3( finalValue, finalValue, finalValue );
      } else {
        //finalColor = hsv2rgb( vec3(mapped, saturation, finalValue * intensity) );
        	vec3 hsbColor = vec3( mapped, saturation, 1.0 - (finalValue * intensity) );
            hsbColor.z = clamp( hsbColor.z, cutoff, 1.0 );
            hsbColor.z = map( hsbColor.z, cutoff, 1.0, 0, 1 );
            hsbColor.x = map( finalValue, cutoff, 1.0, minHue, maxHue );

            finalColor = hsv2rgb( hsbColor );
      }

      gl_FragColor = vec4(finalColor, 1.0);
    }
}
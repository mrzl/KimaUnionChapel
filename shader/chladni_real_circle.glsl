#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

#define PROCESSING_COLOR_SHADER
#define M_PI 3.1415926535897932384626433832795

uniform sampler2D texture;
uniform vec2 resolution;
uniform float nthZero;
uniform float m;
uniform float n;

/*
float getNthZeroOfMthBessel( const int m_order, const int n_zero ) {
        // Zeros of the Bessel function J(x)
        // Inputs
        //   m_order   Order of the Bessel function
        //   n_zero    Index of the zero (first, second, etc.)
        // Output
        //   z         The "n_zero"th zero of the Bessel function



        if (m_order >= 48 && n_zero == 1) {
            if( m_order == 48 ) {
              return 55.0283;
            }
            if( m_order == 49 ) {
              return 56.0729;
            }
            if( m_order == 50 ) {
              return 57.1169;
            }
            if( m_order == 51 ) {
              return 58.1603;
            }
            if( m_order == 52 ) {
              return 59.2032;
            }
            if( m_order == 53 ) {
              return 60.2456;
            }
            if( m_order == 54 ) {
              return 61.2875;
            }
            if( m_order == 55 ) {
              return 62.3288;
            }
            if( m_order == 56 ) {
              return 63.3697;
            }
            if( m_order == 57 ) {
              return 64.4102;
            }
            if( m_order == 58 ) {
              return 65.4501;
            }
            if( m_order == 59 ) {
              return 66.4897;
            }
            if( m_order == 60 ) {
              return 67.5288;
            }
            if( m_order == 61 ) {
              return 68.5675;
            }
            if( m_order == 62 ) {
              return 69.6058;
            }
            if( m_order == 63 ) {
              return 70.6437;
            }
            if( m_order == 64 ) {
              return 71.6812;
            }
        }
        if (m_order >= 62 && n_zero == 2) {
            if( m_order == 62 ) {
                return 75.6376;
            }
            if( m_order == 63 ) {
                return 76.7021;
            }
            if( m_order == 64 ) {
                return 77.7659;
            }
        }

        //* Use asymtotic formula for initial guess
        float beta = (float(n_zero) + 0.5*float(m_order) - 0.25)*(M_PI);
        float mu = 4.0*float(m_order)*float(m_order);
        float beta8 = 8.0*beta;
        float beta82 = beta8*beta8;
        float beta84 = beta82*beta82;
        float z = beta - (mu-1.0)/beta8
                - 4.0*(mu-1.0)*(7.0*mu-31.0)/(3.0*beta82*beta8);
        z -= 32.0*(mu-1.0)*(83.0*mu*mu-982.0*mu+3779.0)/(15.0*beta84*beta8);
        z -= 64.0*(mu-1.0)*(6949.0*mu*mu*mu-153855.0*mu*mu+1585743.0*mu-6277237.0)/
                (105.0*beta84*beta82*beta8);

        //* Use Newton's method to locate the root
        const int arraySize = m_order+3;
        float jj[arraySize];
        int i;  float deriv;
        for( i=1; i<=5; i++ ) {
        jj = bess( m_order+1, z, arraySize );
            //bess( m_order+1, z, arraySize );  // Remember j(1) is J_0(z)
            // Use the recursion relation to evaluate derivative
            deriv = -jj[m_order+2] + float(m_order)/z * jj[m_order+1];
            z -= jj[m_order+1]/deriv;  // Newton's root finding
        }
        return(z);
    }

    float[] bess( int m_max, float x, const int size ) {
            float jj[size];
            // Bessel function
            // Inputs
            //    m_max  Largest desired order
            //    x = Value at which Bessel function J(x) is evaluated
            // Output
            //    jj = Vector of J(x) for order m = 0, 1, ..., m_max

            //* Perform downward recursion from initial guess
            int maxmx = 0;
            if( float(m_max) > x ) {
                maxmx = m_max;
            } else {
                maxmx = int(x);
            }
            //int maxmx = (m_max > x) ? m_max : (int(x));  // Max(m,x)
            // Recursion is downward from m_top (which is even)
            const int m_top = 2*((maxmx+15)/2 + 1 );
            float j[m_top+2];// = new double[m_top+2];
            j[m_top+1] = 0.0;
            j[m_top] = 1.0;
            float tinyNumber = 0.00000001;
            int m;
            for( m=m_top-2; m>=0; m--)       // Downward recursion
                j[m+1] = 2.0*(float(m)+1.0)/(x+tinyNumber)*j[m+2] - j[m+3];

            //* Normalize using identity and return requested values
            float norm = j[1];        // NOTE: Be careful, m=0,1,... but
            for( m=2; m<=m_top; m+=2 ) // vector goes j(1),j(2),...
                norm += 2.0*j[m+1];
            for( m=0; m<=m_max; m++ )  // Send back only the values for
                jj[m+1] = j[m+1]/norm;   // m=0,...,m_max and discard values

            return jj;
        }                            // for m=m_max+1,...,m_top
*/
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
	//return  x < 0.0 && mod(n, 2.0) == 1 ? -ans : ans;
}

void main() {
    //int m = 2;
    //int n = 1;
    // float val = getNthZeroOfMthBessel( 0, 1 );
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
      float first = jn( int(m), nthZero * radius );
      float second = sin( float(m) * theta );
      float third = cos( nthZero );
      float res = first * second * third;
      gl_FragColor = vec4(vec3(abs(4.0*res)), 1.0);
    }
    //gl_FragColor = vec4(val2, val2, val2, 1.0);
}
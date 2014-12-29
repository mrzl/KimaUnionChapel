package main;

import processing.core.PApplet;

import java.awt.*;

/**
 * Created by mar on 13.12.14.
 */
public class MathUtils {
    public static final double ACC = 4.0;
    public static final double BIGNO = 1.0e10;
    public static final double BIGNI = 1.0e-10;

    public static ControlFrame addControlFrame(Main pa, String theName, int theWidth, int theHeight) {
        Frame f = new Frame(theName);
        ControlFrame p = new ControlFrame(pa, theWidth, theHeight);
        f.add(p);
        p.init();
        f.setTitle(theName);
        f.setSize(p.w, p.h);
        f.setLocation(100, 100);
        f.setResizable(false);
        f.setVisible(true);
        return p;
    }

    public static final double bessi0(double x) {
        double answer;
        double ax = Math.abs(x);
        if (ax < 3.75) { // polynomial fit
            double y = x / 3.75;
            y *= y;
            answer = 1.0 + y * (3.5156229 + y * (3.0899424 + y * (1.2067492 + y * (0.2659732 + y * (0.360768e-1 + y * 0.45813e-2)))));
        } else {
            double y = 3.75 / ax;
            answer = 0.39894228 + y * (0.1328592e-1 + y * (0.225319e-2 + y * (-0.157565e-2 + y * (0.916281e-2 + y * (-0.2057706e-1 + y * (0.2635537e-1 + y * (-0.1647633e-1 + y * 0.392377e-2)))))));
            answer *= (Math.exp(ax) / Math.sqrt(ax));
        }
        return answer;
    }

    public static final double bessi1(double x) {
        double answer;
        double ax = Math.abs(x);
        if (ax < 3.75) { // polynomial fit
            double y = x / 3.75;
            y *= y;
            answer = ax * (0.5 + y * (0.87890594 + y * (0.51498869 + y * (0.15084934 + y * (0.2658733e-1 + y * (0.301532e-2 + y * 0.32411e-3))))));
        } else {
            double y = 3.75 / ax;
            answer = 0.2282967e-1 + y * (-0.2895312e-1 + y * (0.1787654e-1 - y * 0.420059e-2));
            answer = 0.39894228 + y * (-0.3988024e-1 + y * (-0.362018e-2 + y * (0.163801e-2 + y * (-0.1031555e-1 + y * answer))));
            answer *= (Math.exp(ax) / Math.sqrt(ax));
        }
        return answer;
    }

    public static final double bessi(int n, double x) {
        if (n < 2)
            //throw new IllegalArgumentException("Function order must be greater than 1");
        {
            int dfdfd = 0;
        }

        if (x == 0.0) {
            return 0.0;
        } else {
            double tox = 2.0/ Math.abs( x );
            double ans = 0.0;
            double bip = 0.0;
            double bi  = 1.0;
            for (int j = 2*(n + (int) Math.sqrt( ACC * n )); j > 0; --j) {
                double bim = bip + j*tox*bi;
                bip = bi;
                bi = bim;
                if ( Math.abs( bi ) > BIGNO) {
                    ans *= BIGNI;
                    bi *= BIGNI;
                    bip *= BIGNI;
                }
                if (j == n) {
                    ans = bip;
                }
            }
            ans *= bessi0(x)/bi;
            return (((x < 0.0) && ((n % 2) == 0)) ? -ans : ans);
        }
    }

    public static double zeroj( int m_order, int n_zero) {
        // Zeros of the Bessel function J(x)
        // Inputs
        //   m_order   Order of the Bessel function
        //   n_zero    Index of the zero (first, second, etc.)
        // Output
        //   z         The "n_zero"th zero of the Bessel function

        if (m_order >= 48 && n_zero == 1) {
            switch (m_order) {
                case 48: return 55.0283;
                case 49: return 56.0729;
                case 50: return 57.1169;
                case 51: return 58.1603;
                case 52: return 59.2032;
                case 53: return 60.2456;
                case 54: return 61.2875;
                case 55: return 62.3288;
                case 56: return 63.3697;
                case 57: return 64.4102;
                case 58: return 65.4501;
                case 59: return 66.4897;
                case 60: return 67.5288;
                case 61: return 68.5675;
                case 62: return 69.6058;
                case 63: return 70.6437;
                case 64: return 71.6812;
            }
        }
        if (m_order >= 62 && n_zero == 2) {
            switch (m_order) {
                case 62: return 75.6376;
                case 63: return 76.7021;
                case 64: return 77.7659;
            }
        }

        //* Use asymtotic formula for initial guess
        double beta = (n_zero + 0.5*m_order - 0.25)*(3.141592654);
        double mu = 4*m_order*m_order;
        double beta8 = 8*beta;
        double beta82 = beta8*beta8;
        double beta84 = beta82*beta82;
        double z = beta - (mu-1)/beta8
                - 4*(mu-1)*(7*mu-31)/(3*beta82*beta8);
        z -= 32*(mu-1)*(83*mu*mu-982*mu+3779)/(15*beta84*beta8);
        z -= 64*(mu-1)*(6949*mu*mu*mu-153855*mu*mu+1585743*mu-6277237)/
                (105*beta84*beta82*beta8);

        //* Use Newton's method to locate the root
        double jj[] = new double[m_order+3];
        int i;  double deriv;
        for( i=1; i<=5; i++ ) {
            bess( m_order+1, z, jj );  // Remember j(1) is J_0(z)
            // Use the recursion relation to evaluate derivative
            deriv = -jj[m_order+2] + m_order/z * jj[m_order+1];
            z -= jj[m_order+1]/deriv;  // Newton's root finding
        }
        return(z);
    }

    public static void bess( int m_max, double x, double jj[] ) {
        // Bessel function
        // Inputs
        //    m_max  Largest desired order
        //    x = Value at which Bessel function J(x) is evaluated
        // Output
        //    jj = Vector of J(x) for order m = 0, 1, ..., m_max

        //* Perform downward recursion from initial guess
        int maxmx = (m_max > x) ? m_max : ((int)x);  // Max(m,x)
        // Recursion is downward from m_top (which is even)
        int m_top = 2*((int)( (maxmx+15)/2 + 1 ));
        double j[] = new double[m_top+2];
        j[m_top+1] = 0.0;
        j[m_top] = 1.0;
        double tinyNumber = 1e-16;
        int m;
        for( m=m_top-2; m>=0; m--)       // Downward recursion
            j[m+1] = 2*(m+1)/(x+tinyNumber)*j[m+2] - j[m+3];

        //* Normalize using identity and return requested values
        double norm = j[1];        // NOTE: Be careful, m=0,1,... but
        for( m=2; m<=m_top; m+=2 ) // vector goes j(1),j(2),...
            norm += 2*j[m+1];
        for( m=0; m<=m_max; m++ )  // Send back only the values for
            jj[m+1] = j[m+1]/norm;   // m=0,...,m_max and discard values
    }                            // for m=m_max+1,...,m_top
}

import java.lang.Math;
import java.util.Arrays;

public class Body {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    
    /* Body Constructor 1 */
    public Body(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    /* Body Constructor 2 */
    public Body(Body b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;  
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;  
    }

    public double calcDistance(Body b) {
        double yyDiff = yyPos - b.yyPos;
        double xxDiff = xxPos - b.xxPos;
        return Math.sqrt(Math.pow(yyDiff, 2) + Math.pow(xxDiff, 2));
    }

    public double calcForceExertedBy(Body b) {
        double G = 6.67e-11;
        if (this.equals(b)) {
            return 0;
        }
        return ((G * mass * b.mass) / Math.pow(calcDistance(b), 2));    
    }

    public double calcForceExertedByX(Body b) {
        double dx = b.xxPos - this.xxPos;
        return (calcForceExertedBy(b) * dx) / calcDistance(b);    
    }

    public double calcForceExertedByY(Body b) {
        double dy = b.yyPos - this.yyPos;
        return (calcForceExertedBy(b) * dy) / calcDistance(b);    
    }

    public double calcNetForceExertedByX(Body[] bodyArray) {
        double netForceX = 0;
        for (int i = 0; i < bodyArray.length; i++) {
            if (this.equals(bodyArray[i])) {
                continue;
            }
            netForceX = netForceX + calcForceExertedByX(bodyArray[i]);
        }
        return netForceX;
    }

    public double calcNetForceExertedByY(Body[] bodyArray) {
        /* Body in array is the same as instance body */
        if (this.equals(bodyArray[0])) {
            if (bodyArray.length == 1) {
                return 0;
            }
            return 0 + calcNetForceExertedByY(Arrays.copyOfRange(bodyArray, 1, bodyArray.length));
        }   
        
        if (bodyArray.length == 1) {
            return calcForceExertedByY(bodyArray[0]);
        }
        return calcForceExertedByY(bodyArray[0]) + calcNetForceExertedByY(Arrays.copyOfRange(bodyArray, 1, bodyArray.length));
    }

    public void update(double dt, double fX, double fY) {
        double aNetX = fX / mass;
        double aNetY = fY / mass;
        double nVX = xxVel + aNetX * dt;
        double nVY = yyVel + aNetY * dt;
        xxVel = nVX;
        yyVel = nVY;
        xxPos = xxPos + nVX * dt;
        yyPos = yyPos + nVY * dt;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "./images/" + imgFileName);
    }
}
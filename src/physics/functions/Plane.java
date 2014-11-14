/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package physics.functions;

import physics.Coordinate;
import physics.Vector;

/**
 *
 * @author Christopher Hittner
 */
public class Plane {
    
    private double
            a, b, c,
            d, e, f,
            g, h, i;
    
    
    public Plane(Coordinate A, Coordinate B, Coordinate C){
        a = A.X();
        b = A.Y();
        c = A.Z();
        
        d = B.X();
        e = B.Y();
        f = B.Z();
        
        g = C.X();
        h = C.Y();
        i = C.Z();
    }
    
    public double getValue(Coordinate C){
        double x = C.X();
        double y = C.Y();
        double z = C.Z();
        double result;
        /*
        result = x * (b * (f-i) + c * (h - e) + e * i - f * h)/(a * (h-e) + b * (d - g) - d * h + e * g);
        
        result += y * (a * (i - f) + c * (d -g) - d * i + f * g) / (a * (h - e) + d * (b - h) + g * (e - b));
        
        result += (a * (f*h - e*i) + b*(d*i - f*g) + c * (e*g - d*h)) / (a * (h-e) + d * (b-h) + g*(e-b));
        
        result -= z;
        */
        result =  z * Z_Param()
                + y * Y_Param()
                + getConstant()
                + x * X_Param();
        
        return result;
        
    } 
    
    public double findMissingX(double y, double z){
        double result =  z *Z_Param()
                + y *Y_Param()
                + getConstant();
        
        result /= -X_Param();
        return result;
    }
    
    public double findMissingY(double x, double z){
        double result =  z *Z_Param()
                + getConstant()
                + x *X_Param();
        
        result /= -Y_Param();
        
        return result;
        
    }
    
    public double findMissingZ(double x, double y){
        double result = y *Y_Param()
                + getConstant()
                + x *X_Param();
        
        result /= -Z_Param();
        
        return result;
        
    }
    
    public double findDistance(Coordinate C){
        double j = C.X(), k = C.Y(), l = C.Z();
        double distX = findMissingX(k,l) - j, distY = findMissingY(j,l) - k, distZ = findMissingZ(j,k) - l;
        
        if(Math.abs(distX) == Double.POSITIVE_INFINITY){
            distX = 0;
        }
        if(Math.abs(distZ) == Double.POSITIVE_INFINITY){
            distZ = 0;
        }
        
        double distXZ = distX * distZ / Math.sqrt(Math.pow(distX,2) + Math.pow(distZ,2));
        
        double distXYZ;
        if(Math.abs(distY) != Double.POSITIVE_INFINITY)
            distXYZ = distXZ * distY / Math.sqrt(Math.pow(distXZ,2) + Math.pow(distY,2));
        else distXYZ = distXZ;
        
        return Math.abs(distXYZ);
        
        
    }
    
    
    public boolean testCollision(Coordinate C){
        return testCollision(C,0);
    }
    
    public boolean testCollision(Coordinate C, double distanceLimit){
        return Math.abs(getValue(C)) <= distanceLimit;
    }
    
    public boolean testTriangleCollision(Coordinate C){
        double j = C.X(), k = C.Y(), l = C.Z();
        
        double X = (new Vector(new Coordinate(A(),B(),C()), new Coordinate(D(),E(),F()))).getMagnitude();
        double Y = (new Vector(new Coordinate(G(),H(),I()), new Coordinate(D(),E(),F()))).getMagnitude();
        double Z = (new Vector(new Coordinate(A(),B(),C()), new Coordinate(G(),H(),I()))).getMagnitude();
        
        double U = (new Vector(new Coordinate(j,k,l), new Coordinate(A(),B(),C()))).getMagnitude();
        double V = (new Vector(new Coordinate(j,k,l), new Coordinate(D(),E(),F()))).getMagnitude();
        double W = (new Vector(new Coordinate(j,k,l), new Coordinate(G(),H(),I()))).getMagnitude();
        
        double alp = Math.acos(  (Math.pow(U, 2) + Math.pow(V, 2)-Math.pow(X,2)) / (2*U*V) );
        double bet = Math.acos(  (Math.pow(V, 2) + Math.pow(W, 2)-Math.pow(Y,2)) / (2*U*V) );
        double gam = Math.acos(  (Math.pow(W, 2) + Math.pow(U, 2)-Math.pow(Z,2)) / (2*U*V) );
        
        return (alp + bet > Math.PI) && (gam + bet > Math.PI) && (alp + gam > Math.PI) && testCollision(C);
    }
    
    @Override
    public String toString(){
        return "(" + a + "," + b + "," + c + ") " + "(" + d + "," + e + "," + f + ") " + "(" + g + "," + h + "," + i + ")";
    }
    
    public double A(){ return a;}
    public double B(){ return b;}
    public double C(){ return c;}
    public double D(){ return d;}
    public double E(){ return e;}
    public double F(){ return f;}
    public double G(){ return g;}
    public double H(){ return h;}
    public double I(){ return i;}
    
    public double X_Param(){
        return (-b *f+b *i+c *e-c *h-e *i+f *h);
    }
    
    public double Y_Param(){
        return (a *f-a *i-c *d+c *g+d *i-f *g);
    }
    
    public double Z_Param(){
        return (-a *e+a *h+b *d-b *g-d *h+e *g);
    }
    
    public double getConstant(){
        return a *e *i-a *f *h - b * d *i + b* f* g+c* d* h-c* e* g;
    }
    
    
    
}
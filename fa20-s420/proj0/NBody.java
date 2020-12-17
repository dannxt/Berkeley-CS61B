public class NBody {
    public static double readRadius(String s) {
        In in = new In(s);
        in.readInt();
        return in.readDouble();  
    }

    public static Body[] readBodies(String filename) {
        In in = new In(filename);
        int N = in.readInt();
        in.readDouble();
        Body[] bodies = new Body[N];
        for (int i = 0; i < N; i++) {
            bodies[i] = new Body(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
        }
        return bodies;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);    
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        
        double radius = readRadius(filename);
        Body[] bodies = readBodies(filename);

        /* Set radius of universe*/
        StdDraw.setScale(-radius, radius);

        /* Draw starfield */
        StdDraw.enableDoubleBuffering();
        StdDraw.picture(0, 0, "./images/starfield.jpg");

        /* Draw all bodies */
        for (Body b : bodies) {
            StdDraw.enableDoubleBuffering();
            StdDraw.picture(b.xxPos, b.yyPos, "./images/" + b.imgFileName);
        }

        /* Time loops */
        for (double t = 0; t <= T; t = t + dt) {
            double[] xForces = new double[bodies.length];    
            double[] yForces = new double[bodies.length];
            for (int i = 0; i < bodies.length; i++) {
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);    
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);    
            }    

            for (int j = 0; j < bodies.length; j++) {
                StdDraw.enableDoubleBuffering();
                bodies[j].update(dt, xForces[j], yForces[j]);
                StdDraw.picture(0, 0, "./images/starfield.jpg");
                bodies[j].draw();

                /* Show and pause for 10 milliseconds */
                StdDraw.show();
		        StdDraw.pause(10);
            }
        }
        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
            bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
        } 
    }
}
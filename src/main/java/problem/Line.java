package problem;

public class Line {
    public double k,b;
    private double A, B, C;

    //представление прямой в виде Ax + By + C = 0
    public Line(Point p1, Point p2) {
        A = p1.y - p2.y;
        B = p2.x - p1.x;
        if(B==0) B = 0.001;

        C = p1.x * p2.y - p2.x * p1.y;
        k = -A/B;
        b = -C/B;
    }

    public Line(double a, double b, double c) {
        A = a;
        B = b;
        if(B==0) B = 0.001;

        C = c;
        k = -A/B;
        b = -C/B;
    }

    //представление прямой в виде y = kx+b
    public Line (double k, double b){
        this.k=k;
        this.b=b;
        this.A = -k;
        this.B = 1;
        this.C = -b;
    }

    public double distanceToZero() {
        double d;
        d = (Math.abs(C)) / (Math.sqrt(A * A + B * B));
        return d;
    }

    public double distanceToPoint(Point point) {
        double d;
        d = Math.abs(A * point.x + B * point.y + C) / Math.sqrt(A * A + B * B);
        return d;
    }

    public double getA() {
        return A;
    }

    public double getB() {
        return B;
    }

    public double getC() {
        return C;
    }

    public boolean isParallel(Line line) {
        return Math.abs(A * line.getB() - (line.getA() * B)) < 0.001;
    }

    public Line parallelLine(Point point) {
        double a = A;
        double b = B;
        double c = -(a * point.x + b * point.y);
        return new Line(a, b, c);
    }

    public Point intersection(Line line) {
        if (isParallel(line)) return null;
        else {
            double a = line.getA(), b = line.getB(), c = line.getC();
            double X = (b * C / B - c) / (a - A * b / B);
            double Y = -(A * X + C) / B;
            return new Point(X, Y);
        }
    }

    public Line getPerLine() {
        return new Line(-B * B, A * B, A * C);
    }

    public Line perpendicularLine(Point point) {
        double a = -B * B;
        double b = A * B;
        double c = -(a * point.x + b * point.y);
        return new Line(a, b, c);
    }

    public Point nearPoint(Point point) {
        return intersection(perpendicularLine(point));
    }

    public boolean oneSide(Point p1, Point p2) {
        double y01 = -(A / B) * p1.x - C / B;
        double y02 = -(A / B) * p2.x - C / B;
        if (Math.abs(p1.y - y01) < 0.001 || Math.abs(p2.y - y02) < 0.001) {
            return true;
        } else {
            return Math.signum(p1.y - y01) == Math.signum(p2.y - y02);
        }
    }


    public int whatIsSquare(Point p) {
        if (p.x > 0 && p.y > 0) return 1;
        if (p.x < 0 && p.y > 0) return 2;
        if (p.x < 0 && p.y < 0) return 3;
        if (p.x > 0 && p.y < 0) return 4;

        if (p.x == 0 && p.y > 0) return 12;
        if (p.y == 0 && p.x < 0) return 23;
        if (p.x == 0 && p.y < 0) return 24;
        if (p.y == 0 && p.x > 0) return 41;

        return 0;
    }

    public boolean insideTreug(Point p) {
        Line ORDINATA = new Line(new Point(0,0), new Point(0,1)),
                ABSCISSA = new Line(new Point(0,0), new Point(1,0));

        double pX = intersection(ABSCISSA).x,
                pY = intersection(ORDINATA).y;
        Point pO = new Point(0, 0);

        if (pX == 0 && pY == 0) {
            return false;
        } else {
            if ((pX >= 0) && (pY >= 0) && (whatIsSquare(p) == 1) && oneSide(p, pO)) return true;
            if ((pX <= 0) && (pY >= 0) && (whatIsSquare(p) == 2) && oneSide(p, pO)) return true;
            if ((pX <= 0) && (pY <= 0) && (whatIsSquare(p) == 3) && oneSide(p, pO)) return true;
            if ((pX >= 0) && (pY <= 0) && (whatIsSquare(p) == 4) && oneSide(p, pO)) return true;

            if ((pX >= 0) && (whatIsSquare(p) == 41) && oneSide(p, pO)) return true;
            if ((pX <= 0) && (whatIsSquare(p) == 23) && oneSide(p, pO)) return true;
            if ((pY >= 0) && (whatIsSquare(p) == 12) && oneSide(p, pO)) return true;
            if ((pY <= 0) && (whatIsSquare(p) == 34) && oneSide(p, pO)) return true;

            if ((whatIsSquare(p) == 0) && oneSide(p, pO)) return true;
        }
        return false;
    }



    public void normalize() {
        if (C != 0) {
            A = A / C;
            B = B / C;
            C = 1;
        } else if (A != 0) {
            B = B / A;
            C = C / A;
            A = 1;
        } else {
            A = A / B;
            C = C / B;
            B = 1;
        }
    }

    @Override
    public String toString() {
        if (B <= -0.01) {
            if (C <= -0.01) {
                return String.format("%.2fx - %.2fy - %.2f = 0", A, Math.abs(B), Math.abs(C));
            }
            return String.format("%.2fx - %.2fy + %.2f = 0", A, Math.abs(B), C);
        }
        if (C <= -0.01) {
            return String.format("%.2fx + %.2fy - %.2f = 0", A, B, Math.abs(C));
        }
        return String.format("%.2fx + %.2fy + %.2f = 0", A, Math.abs(B), Math.abs(C));
    }
}

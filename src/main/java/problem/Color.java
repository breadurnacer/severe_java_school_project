package problem;

public class Color {
    double R, G, B;

    public Color(double r, double g, double b) {
        R = r;
        G = g;
        B = b;
    }

    public Color change(double chg) {
        R += chg;
        G += chg;
        B += chg;
        return this;
    }

    public Color change(double dR, double dG, double dB) {
        R += dR;
        G += dG;
        B += dB;
        return this;
    }
}
package cz.csas.lockerui.utils;

import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;

/**
 * @author Jan Hauser <jan.hauser@applifting.cz>
 * @since 15/04/16.
 */
public class ColorFilterGenerator {

    /**
     * Creates a HUE ajustment ColorFilter
     *
     * @return
     */
    public static ColorFilter adjustHue(float value) {
        ColorMatrix cm = new ColorMatrix();
        adjustHue(cm, value);
        return new ColorMatrixColorFilter(cm);
    }

    /**
     * @param cm
     * @param value
     */
    public static void adjustHue(ColorMatrix cm, float value) {
        // value the angle you want to rotate your color. It rotates around the R=G=B axis. If you
        // rotate 180° red becomes cyan, green becomes magenta, yellow becomes blue, etc.
        value = value / 180f * (float) Math.PI;
        if (value == 0) {
            return;
        }
        float cosVal = (float) Math.cos(value);
        float sinVal = (float) Math.sin(value);
        // chrominance (saturation) constants
        float a = 0;//0.143f;
        float b = 0;//0.140f;
        float c = 0;//-0.283f;
        if (sinVal < 0) {
            a = -a;
            b = -b;
            c = -c;
        }
        // lumR, lumG, and lumB are the luminance constants. If you compute the dot product of
        // <R, G, B> with <lumR, lumG, lumB> you'll get the luminance of the color.
        float lumR = 0.213f;
        float lumG = 0.715f;
        float lumB = 0.072f;

        float[] mat = new float[]
                {
                        lumR + cosVal * (1 - lumR) + sinVal * (-lumR), lumG + cosVal * (-lumG) + sinVal * (-lumG), lumB + cosVal * (-lumB) + sinVal * (1 - lumB), 0, 0,
                        lumR + cosVal * (-lumR) + sinVal * (a), lumG + cosVal * (1 - lumG) + sinVal * (b), lumB + cosVal * (-lumB) + sinVal * (-c), 0, 0,
                        lumR + cosVal * (-lumR) + sinVal * (-(1 - lumR)), lumG + cosVal * (-lumG) + sinVal * (lumG), lumB + cosVal * (1 - lumB) + sinVal * (lumB), 0, 0,
                        0f, 0f, 0f, 1f, 0f,
                        0f, 0f, 0f, 0f, 1f};
        cm.postConcat(new ColorMatrix(mat));
    }

    protected static float cleanValue(float pVal, float pLimit) {
        while (pVal > pLimit)
            pVal -= pLimit;
        while (pVal < -pLimit)
            pVal += pLimit;
        return pVal;
        //return Math.min(pLimit, Math.max(-pLimit, pVal));
    }
}

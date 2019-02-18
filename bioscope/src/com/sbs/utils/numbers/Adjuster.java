// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Adjuster.java

package com.sbs.utils.numbers;


// Referenced classes of package com.sbs.utils.numbers:
//            Rounder

public class Adjuster extends Rounder
{

    public static Adjuster getAdjusterInstance()
    {
        if(Rounder.def == null)
            Rounder.def = new Adjuster();
        return (Adjuster)Rounder.def;
    }

    public Adjuster()
    {
    }

    public Adjuster(int maxPrecision)
    {
        super(maxPrecision);
    }

    public double[] adjust(double vector[], int precision)
    {
        return adjust(100D, vector, precision);
    }

    public double[] adjust(double target, double vector[], int precision)
    {
        long sum = 0L;
        long factor = super.ft[precision];
        long values[] = new long[vector.length];
        double moduli[] = new double[vector.length];
        for(int x = 0; x < vector.length; x++)
        {
            values[x] = upDiv(vector[x], precision);
            moduli[x] = upMod(vector[x], precision);
            sum += values[x];
        }

        long adj = (long)((double)factor * target) - sum;
        if(Math.abs(adj) > (long)values.length)
            return vector;
        if(adj > (long)0)
            for(; adj > (long)0; adj--)
            {
                double candidate = 0.0D;
                int index = 0;
                for(int z = 0; z < values.length; z++)
                    if(moduli[z] > 0.0D && moduli[z] > candidate)
                    {
                        index = z;
                        candidate = moduli[z];
                    }

                values[index]++;
                moduli[index] = -1D;
            }

        else
            for(adj = Math.abs(adj); adj > (long)0; adj--)
            {
                double candidate = 1.0D;
                int index = 0;
                for(int z = 0; z < values.length; z++)
                    if(moduli[z] > 0.0D && moduli[z] < candidate)
                    {
                        index = z;
                        candidate = moduli[z];
                    }

                values[index]--;
                moduli[index] = -1D;
            }

        double result[] = new double[values.length];
        for(int a = 0; a < values.length; a++)
            result[a] = (double)values[a] / (double)super.ft[precision];

        return result;
    }

    public float[] adjust(float vector[], int precision)
    {
        return adjust(100F, vector, precision);
    }

    public float[] adjust(float target, float vector[], int precision)
    {
        long sum = 0L;
        long factor = super.ft[precision];
        long values[] = new long[vector.length];
        float moduli[] = new float[vector.length];
        for(int x = 0; x < vector.length; x++)
        {
            values[x] = upDiv(vector[x], precision);
            moduli[x] = upMod(vector[x], precision);
            sum += values[x];
        }

        long adj = (long)((float)factor * target) - sum;
        if(Math.abs(adj) > (long)values.length)
            return vector;
        if(adj > (long)0)
            for(; adj > (long)0; adj--)
            {
                float candidate = 0.0F;
                int index = 0;
                for(int z = 0; z < values.length; z++)
                    if((double)moduli[z] > 0.0D && moduli[z] > candidate)
                    {
                        index = z;
                        candidate = moduli[z];
                    }

                values[index]++;
                moduli[index] = -1F;
            }

        else
            for(adj = Math.abs(adj); adj > (long)0; adj--)
            {
                float candidate = 1.0F;
                int index = 0;
                for(int z = 0; z < values.length; z++)
                    if((double)moduli[z] > 0.0D && moduli[z] < candidate)
                    {
                        index = z;
                        candidate = moduli[z];
                    }

                values[index]--;
                moduli[index] = -1F;
            }

        float result[] = new float[values.length];
        for(int a = 0; a < values.length; a++)
            result[a] = (float)values[a] / (float)super.ft[precision];

        return result;
    }

    public static final String RCSID = "$Header: /src/com/sbs/util/numbers/Adjuster.java#2 $";
}

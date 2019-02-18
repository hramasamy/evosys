// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Rounder.java

package com.sbs.utils.numbers;

import java.math.BigDecimal;

public class Rounder
{

    public static Rounder getInstance()
    {
        if(def == null)
            def = new Rounder();
        return def;
    }

    public Rounder()
    {
        ft = newFactors(20);
    }

    public Rounder(int maxPrecision)
    {
        ft = newFactors(maxPrecision);
    }

    public Double round(Double value, int precision)
    {
        return new Double(round(value.doubleValue(), precision));
    }

    public double round(double value, int precision)
    {
        return (double)Math.round(value * (double)ft[precision]) / (double)ft[precision];
    }

    protected long upDiv(double value, int precision)
    {
        return (long)(value * (double)ft[precision]);
    }

    protected double upMod(double value, int precision)
    {
        double adj = value * (double)ft[precision];
        return adj % (double)(long)adj;
    }

    public Float round(Float value, int precision)
    {
        return new Float(round(value.floatValue(), precision));
    }

    public float round(float value, int precision)
    {
        return (float)Math.round(value * (float)ft[precision]) / (float)ft[precision];
    }

    protected long upDiv(float value, int precision)
    {
        return (long)(value * (float)ft[precision]);
    }

    protected float upMod(float value, int precision)
    {
        float adj = value * (float)ft[precision];
        return adj % (float)(long)adj;
    }

    public BigDecimal round(BigDecimal value, int precision)
    {
        return new BigDecimal(round(value.doubleValue(), precision));
    }

    public int getPrecision()
    {
        return ft.length;
    }

    public void setPrecision(int precision)
    {
        ft = newFactors(precision);
    }

    protected long[] newFactors(int maxPrecision)
    {
        long nf[] = new long[maxPrecision];
        nf[0] = 1L;
        for(int x = 1; x < maxPrecision; x++)
            nf[x] = (long)10 * nf[x - 1];

        return nf;
    }

    public static final String RCSID = "$Header: //cogencydepot/release/branch/040130/src/com/cogencysoft/yard/expressions/util/JClassFormula.java#1 $";
    public static final int DEF_PRECISION = 20;
    protected static Rounder def;
    protected long ft[];
}

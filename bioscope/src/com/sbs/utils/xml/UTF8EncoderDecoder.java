// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UTF8EncoderDecoder.java

package com.sbs.utils.xml;


public class UTF8EncoderDecoder
{

    public UTF8EncoderDecoder()
    {
    }

    public static String encode(String s)
    {
        StringBuffer sbuf = new StringBuffer();
        int len = s.length();
        for(int i = 0; i < len; i++)
        {
            int ch = s.charAt(i);
            if(65 <= ch && ch <= 90)
            {
                sbuf.append((char)ch);
                continue;
            }
            if(97 <= ch && ch <= 122)
            {
                sbuf.append((char)ch);
                continue;
            }
            if(48 <= ch && ch <= 57)
            {
                sbuf.append((char)ch);
                continue;
            }
            if(ch == 32)
            {
                sbuf.append('+');
                continue;
            }
            if(ch == 45 || ch == 95 || ch == 46 || ch == 33 || ch == 126 || ch == 42 || ch == 39 || ch == 40 || ch == 41)
            {
                sbuf.append((char)ch);
                continue;
            }
            if(ch <= 127)
            {
                sbuf.append(hex[ch]);
                continue;
            }
            if(ch <= 2047)
            {
                sbuf.append(hex[0xc0 | ch >> 6]);
                sbuf.append(hex[0x80 | ch & 0x3f]);
            } else
            {
                sbuf.append(hex[0xe0 | ch >> 12]);
                sbuf.append(hex[0x80 | ch >> 6 & 0x3f]);
                sbuf.append(hex[0x80 | ch & 0x3f]);
            }
        }

        return sbuf.toString();
    }

    public static String unescape(String s)
    {
        StringBuffer sbuf = new StringBuffer();
        int l = s.length();
        int ch = -1;
        int sumb = 0;
        int i = 0;
        int more = -1;
        for(; i < l; i++)
        {
            int b;
            switch(ch = s.charAt(i))
            {
            case 37: // '%'
                ch = s.charAt(++i);
                int hb = (Character.isDigit((char)ch) ? ch - 48 : (10 + Character.toLowerCase((char)ch)) - 97) & 0xf;
                ch = s.charAt(++i);
                int lb = (Character.isDigit((char)ch) ? ch - 48 : (10 + Character.toLowerCase((char)ch)) - 97) & 0xf;
                b = hb << 4 | lb;
                break;

            case 43: // '+'
                b = 32;
                break;

            default:
                b = ch;
                break;
            }
            if((b & 0xc0) == 128)
            {
                sumb = sumb << 6 | b & 0x3f;
                if(--more == 0)
                    sbuf.append((char)sumb);
                continue;
            }
            if((b & 0x80) == 0)
            {
                sbuf.append((char)b);
                continue;
            }
            if((b & 0xe0) == 192)
            {
                sumb = b & 0x1f;
                more = 1;
                continue;
            }
            if((b & 0xf0) == 224)
            {
                sumb = b & 0xf;
                more = 2;
                continue;
            }
            if((b & 0xf8) == 240)
            {
                sumb = b & 7;
                more = 3;
                continue;
            }
            if((b & 0xfc) == 248)
            {
                sumb = b & 3;
                more = 4;
            } else
            {
                sumb = b & 1;
                more = 5;
            }
        }

        return sbuf.toString();
    }

    static final String hex[] = {
        "%00", "%01", "%02", "%03", "%04", "%05", "%06", "%07", "%08", "%09", 
        "%0a", "%0b", "%0c", "%0d", "%0e", "%0f", "%10", "%11", "%12", "%13", 
        "%14", "%15", "%16", "%17", "%18", "%19", "%1a", "%1b", "%1c", "%1d", 
        "%1e", "%1f", "%20", "%21", "%22", "%23", "%24", "%25", "%26", "%27", 
        "%28", "%29", "%2a", "%2b", "%2c", "%2d", "%2e", "%2f", "%30", "%31", 
        "%32", "%33", "%34", "%35", "%36", "%37", "%38", "%39", "%3a", "%3b", 
        "%3c", "%3d", "%3e", "%3f", "%40", "%41", "%42", "%43", "%44", "%45", 
        "%46", "%47", "%48", "%49", "%4a", "%4b", "%4c", "%4d", "%4e", "%4f", 
        "%50", "%51", "%52", "%53", "%54", "%55", "%56", "%57", "%58", "%59", 
        "%5a", "%5b", "%5c", "%5d", "%5e", "%5f", "%60", "%61", "%62", "%63", 
        "%64", "%65", "%66", "%67", "%68", "%69", "%6a", "%6b", "%6c", "%6d", 
        "%6e", "%6f", "%70", "%71", "%72", "%73", "%74", "%75", "%76", "%77", 
        "%78", "%79", "%7a", "%7b", "%7c", "%7d", "%7e", "%7f", "%80", "%81", 
        "%82", "%83", "%84", "%85", "%86", "%87", "%88", "%89", "%8a", "%8b", 
        "%8c", "%8d", "%8e", "%8f", "%90", "%91", "%92", "%93", "%94", "%95", 
        "%96", "%97", "%98", "%99", "%9a", "%9b", "%9c", "%9d", "%9e", "%9f", 
        "%a0", "%a1", "%a2", "%a3", "%a4", "%a5", "%a6", "%a7", "%a8", "%a9", 
        "%aa", "%ab", "%ac", "%ad", "%ae", "%af", "%b0", "%b1", "%b2", "%b3", 
        "%b4", "%b5", "%b6", "%b7", "%b8", "%b9", "%ba", "%bb", "%bc", "%bd", 
        "%be", "%bf", "%c0", "%c1", "%c2", "%c3", "%c4", "%c5", "%c6", "%c7", 
        "%c8", "%c9", "%ca", "%cb", "%cc", "%cd", "%ce", "%cf", "%d0", "%d1", 
        "%d2", "%d3", "%d4", "%d5", "%d6", "%d7", "%d8", "%d9", "%da", "%db", 
        "%dc", "%dd", "%de", "%df", "%e0", "%e1", "%e2", "%e3", "%e4", "%e5", 
        "%e6", "%e7", "%e8", "%e9", "%ea", "%eb", "%ec", "%ed", "%ee", "%ef", 
        "%f0", "%f1", "%f2", "%f3", "%f4", "%f5", "%f6", "%f7", "%f8", "%f9", 
        "%fa", "%fb", "%fc", "%fd", "%fe", "%ff"
    };

}

// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Strings.java

package com.sbs.utils.string;

import com.sbs.utils.numbers.NumberConstants;
import com.sbs.utils.numbers.Numbers;
import java.text.MessageFormat;
import java.util.*;

public final class Strings
{
    public static class Range
    {

        public int begin;
        public int end;

        public Range(int begin, int end)
        {
            this.begin = begin;
            this.end = end;
        }

        public Range()
        {
        }
    }


    public Strings()
    {
    }

    public static boolean isEmpty(String str)
    {
        return str == null || str.equalsIgnoreCase("null") || str.length() == 0;
    }

    public static boolean isNotEmpty(String str)
    {
        return !isEmpty(str);
    }

    public static String getStringForDBInsert(String str)
    {
        return String.valueOf(String.valueOf((new StringBuffer("'")).append(str).append("'")));
    }

    public static String getStringForDBInsert(boolean flag)
    {
        return String.valueOf(String.valueOf((new StringBuffer("'")).append(!flag ? "F" : "T").append("'")));
    }

    public static Collection collectionFromString(Object val)
    {
        return collectionFromString(String.valueOf(val), ",");
    }

    public static Collection collectionFromString(String str)
    {
        return collectionFromString(str, ",");
    }

    public static Collection collectionFromString(String str, String sep)
    {
        Collection c = new ArrayList();
        for(StringTokenizer st = new StringTokenizer(str, sep); st.hasMoreElements(); c.add(st.nextToken().trim()));
        return c;
    }

    public static Collection toCollection(String arr[])
    {
        List l = new ArrayList();
        if(arr != null && arr.length != 0)
        {
            for(int i = 0; i < arr.length; i++)
                l.add(arr[i]);

        }
        return l;
    }

    public static String alignLeft(char character, String subject, int maxLength)
    {
        StringBuffer buffer = new StringBuffer();
        int lengthToPad = maxLength - subject.length();
        fill(buffer, lengthToPad, character);
        buffer.append(subject);
        return buffer.toString();
    }

    public static String alignRight(char character, String subject, int max)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(subject);
        int lengthToPad = max - subject.length();
        fill(buffer, lengthToPad, character);
        return buffer.toString();
    }

    public static boolean areEqual(String first, String second)
    {
        if(first == null && second == null)
            return true;
        if(first == null || second == null)
            return false;
        else
            return first.equals(second);
    }

    public static Double asDouble(String subject)
    {
        if(subject == null)
            return null;
        try
        {
            Double double1 = new Double(strip(subject, NumberConstants.groupingSeparator));
            return double1;
        }
        catch(Exception e)
        {
            Double double2 = null;
            return double2;
        }
    }

    public static String asGetter(String propertyName)
    {
        return asGetterHelper("get", propertyName);
    }

    public static String embed(String pattern, Object replacementParameters[])
    {
        return MessageFormat.format(pattern, replacementParameters);
    }

    public static final String asGetterHelper(String prefix, String propertyName)
    {
        StringBuffer results = new StringBuffer(prefix.length() + propertyName.length());
        results.append(prefix);
        results.append(Character.toUpperCase(propertyName.charAt(0)));
        for(int i = 1; i < propertyName.length(); i++)
            results.append(propertyName.charAt(i));

        return results.toString();
    }

    public static String asSentence(String subject)
    {
        return capitalize(subject.toLowerCase());
    }

    public static String asSetter(String propertyName)
    {
        return asGetterHelper("set", propertyName);
    }

    public static String asString(Object subject)
    {
        return subject != null ? subject.toString() : "";
    }

    public static String asTitle(String subject)
    {
        StringBuffer title = new StringBuffer();
        List words = tokensFromString(subject, ' ');
        Iterator iter = words.iterator();
        do
        {
            if(!iter.hasNext())
                break;
            String each = (String)iter.next();
            title.append(capitalize(each));
            if(iter.hasNext())
                title.append(" ");
        } while(true);
        return title.toString();
    }

    public static String capitalize(String subject)
    {
        if(isEmpty(subject))
        {
            return subject;
        } else
        {
            char results[] = subject.toCharArray();
            results[0] = Character.toUpperCase(results[0]);
            return new String(results);
        }
    }

    public static boolean contains(String subject, char character)
    {
        return subject.indexOf(character) != -1;
    }

    public static boolean contains(String subject, String substring)
    {
        if(subject == null || substring == null)
            return false;
        else
            return subject.indexOf(substring) != -1;
    }

    public static String extractFromDelimiterToEnd(String source, char delimiter)
    {
        return extractFromDelimiterToEnd(source, String.valueOf(delimiter));
    }

    public static String extractFromDelimiterToEnd(String source, String delimiter)
    {
        int start = source.indexOf(delimiter);
        start = start != -1 ? start + 1 : source.length();
        return source.substring(start, source.length());
    }

    public static String extractUpToDelimiterOrEnd(String source, char delimiter)
    {
        return extractUpToDelimiterOrEnd(source, String.valueOf(delimiter));
    }

    public static String extractUpToDelimiterOrEnd(String source, String delimiter)
    {
        int end = source.indexOf(delimiter);
        if(end == -1)
            end = source.length();
        return source.substring(0, end);
    }

    public static void fill(StringBuffer buffer, int lengthToPad, char characterToPad)
    {
        for(int i = 0; i < lengthToPad; i++)
            buffer.append(characterToPad);

    }

    public static String inQuotes(String original)
    {
        return String.valueOf(String.valueOf((new StringBuffer("\"")).append(original).append("\"")));
    }

    public static boolean isAlpha(char character)
    {
        return Character.isLetter(character) || Character.isWhitespace(character) || character == '-' || character == '_';
    }

    public static boolean isAlpha(String subject)
    {
        boolean result = true;
        for(int i = 0; result && i < subject.length(); i++)
            result &= isAlpha(subject.charAt(i));

        return result;
    }

    public static boolean isAlphaNumeric(String subject)
    {
        boolean result = true;
        for(int i = 0; result && i < subject.length(); i++)
        {
            char character = subject.charAt(i);
            result &= isAlpha(character) || Character.isDigit(character);
        }

        return result;
    }

    public static boolean isNumeric(String subject)
    {
        return Numbers.isNumeric(subject);
    }

    public static String lowerFirst(String source)
    {
        String results = source.substring(0, 1).toLowerCase();
        results = String.valueOf(results) + String.valueOf(source.substring(1, source.length()));
        return results;
    }

    public static int occurrencesOfSubstring(String source, String substring)
    {
        int position = 0;
        int result = 0;
        int foundIndex = 0;
        do
        {
            if(foundIndex == -1)
                break;
            foundIndex = source.indexOf(substring, position);
            if(foundIndex != -1)
            {
                result++;
                position = foundIndex + substring.length();
            }
        } while(true);
        return result;
    }

    public static String replace(String source, String target, String replacement)
    {
        if(source == null || target == null || replacement == null || !contains(source, target))
            return source;
        StringBuffer result = new StringBuffer();
        int targetIndex = -1;
        int oldIndex;
        for(oldIndex = 0; (targetIndex = source.indexOf(target, oldIndex)) >= 0; oldIndex = targetIndex + target.length())
        {
            result.append(source.substring(oldIndex, targetIndex));
            result.append(replacement);
        }

        result.append(source.substring(oldIndex, source.length()));
        return result.toString();
    }

    public static String replaceSpecialCharacters(String subject)
    {
        return replace(asString(subject), "\"", "&quot;");
    }

    public static String[] stringArrayFromList(List list)
    {
        String results[] = new String[list.size()];
        for(int i = 0; i < list.size(); i++)
            results[i] = list.get(i) != null ? list.get(i).toString() : "";

        return results;
    }

    public static String strip(String source, char toStrip)
    {
        return replace(source, String.valueOf(toStrip), "");
    }

    public static String strip(String source, String target)
    {
        return replace(source, target, "");
    }

    public static List tokensFromString(String source, char delimiter)
    {
        return tokensFromString(source, String.valueOf(delimiter));
    }

    public static List tokensFromString(String source, String delimiter)
    {
        if(source == null)
            return new ArrayList();
        StringTokenizer tokenizer = new StringTokenizer(source, delimiter);
        List result = new ArrayList();
        int index = 0;
        for(; tokenizer.hasMoreElements(); result.add((String)tokenizer.nextElement()));
        return result;
    }

    public static String truncateTo(String source, int max)
    {
        if(isEmpty(source) || source.length() < max)
            return source;
        else
            return source.substring(0, max);
    }

    public static String zeroFill(String source, int max)
    {
        return alignLeft('0', source, max);
    }

    public static Range rangeOf(String beginToken, String endToken, String string)
    {
        return rangeOf(beginToken, endToken, string, 0);
    }

    public static Range rangeOf(String beginToken, String endToken, String string, int fromIndex)
    {
        int begin = string.indexOf(beginToken, fromIndex);
        if(begin != -1)
        {
            int end = string.indexOf(endToken, begin + 1);
            if(end != -1)
                return new Range(begin, end);
        }
        return null;
    }

    public static String[] trim(String strings[])
    {
        for(int i = 0; i < strings.length; i++)
            strings[i] = strings[i].trim();

        return strings;
    }

    public static String[] splitAt(String string, int index)
    {
        String first = string.substring(0, index - 1);
        String second = string.substring(index);
        return (new String[] {
            first, second
        });
    }

    public static String[] split(String string, String delim)
    {
        return split(string, delim, -1);
    }

    public static String[] split(String string, String delim, int limit)
    {
        int count = count(string, delim) + 1;
        if(limit > 0 && count > limit)
            count = limit;
        String strings[] = new String[count];
        int begin = 0;
        for(int i = 0; i < count; i++)
        {
            int end = string.indexOf(delim, begin);
            if(end == -1 || i + 1 == count)
                end = string.length();
            if(end == 0)
                strings[i] = "";
            else
                strings[i] = string.substring(begin, end);
            begin = end + 1;
        }

        return strings;
    }

    public static String subst(String string, Map map, String beginToken, String endToken)
    {
        return subst(new StringBuffer(), string, map, beginToken, endToken);
    }

    public static String subst(String from, String to, String string)
    {
        return subst(new StringBuffer(), from, to, string);
    }

    public static String subst(String string, String replace[])
    {
        return subst(new StringBuffer(), string, replace, '%');
    }

    public static String subst(String string, String replace[], char token)
    {
        return subst(new StringBuffer(), string, replace, token);
    }

    public static String subst(StringBuffer buff, String string, Map map, String beginToken, String endToken)
    {
        int begin = 0;
        Range range;
        for(int rangeEnd = 0; (range = rangeOf(beginToken, endToken, string, rangeEnd)) != null; rangeEnd = begin)
        {
            buff.append(string.substring(begin, range.begin));
            String key = string.substring(range.begin + beginToken.length(), range.end);
            Object value = map.get(key);
            if(value == null)
                value = "";
            buff.append(value);
            begin = range.end + endToken.length();
        }

        buff.append(string.substring(begin, string.length()));
        return buff.toString();
    }

    public static String subst(StringBuffer buff, String from, String to, String string)
    {
        int begin = 0;
        for(int end = 0; (end = string.indexOf(from, end)) != -1; end = begin)
        {
            buff.append(string.substring(begin, end));
            buff.append(to);
            begin = end + from.length();
        }

        buff.append(string.substring(begin, string.length()));
        return buff.toString();
    }

    public static String subst(StringBuffer buff, String string, String replace[], char token)
    {
        int i = string.length();
        for(int j = 0; j >= 0 && j < i; j++)
        {
            char c = string.charAt(j);
            if(c == token)
            {
                if(j == i)
                    continue;
                int k = Character.digit(string.charAt(j + 1), 10);
                if(k == -1)
                    buff.append(string.charAt(j + 1));
                else
                if(k < replace.length)
                    buff.append(replace[k]);
                j++;
            } else
            {
                buff.append(c);
            }
        }

        return buff.toString();
    }

    public static int nthIndexOf(String string, String token, int index)
    {
        int j = 0;
        int i = 0;
        do
        {
            if(i >= index)
                break;
            j = string.indexOf(token, j + 1);
            if(j == -1)
                break;
            i++;
        } while(true);
        return j;
    }

    public static String pad(Object obj, int count)
    {
        return pad(new StringBuffer(), String.valueOf(obj), count);
    }

    public static String pad(String string, int count)
    {
        return pad(new StringBuffer(), string, count);
    }

    public static String pad(StringBuffer buff, String string, int count)
    {
        for(int i = 0; i < count; i++)
            buff.append(string);

        return buff.toString();
    }

    public static String join(byte array[])
    {
        Byte bytes[] = new Byte[array.length];
        for(int i = 0; i < bytes.length; i++)
            bytes[i] = new Byte(array[i]);

        return join(((Object []) (bytes)), null);
    }

    public static String join(Object array[])
    {
        return join(array, null);
    }

    public static String join(Object array[], String delim)
    {
        return join(new StringBuffer(), array, delim);
    }

    public static String join(Object array[], String prefix, String separator, String suffix)
    {
        return join(new StringBuffer(), array, prefix, separator, suffix);
    }

    public static String join(StringBuffer buff, Object array[], String delim)
    {
        boolean haveDelim = delim != null;
        for(int i = 0; i < array.length; i++)
        {
            buff.append(array[i]);
            if(haveDelim && i + 1 < array.length)
                buff.append(delim);
        }

        return buff.toString();
    }

    public static String join(StringBuffer buff, Object array[], String prefix, String separator, String suffix)
    {
        buff.append(prefix);
        join(buff, array, separator);
        buff.append(suffix);
        return buff.toString();
    }

    public static int count(String string, char c)
    {
        return count(string, String.valueOf(c));
    }

    public static int count(String string, String substring)
    {
        int count = 0;
        for(int idx = 0; (idx = string.indexOf(substring, idx)) != -1;)
        {
            idx++;
            count++;
        }

        return count;
    }

    public static boolean compare(String me, String you)
    {
        if(me == you)
            return true;
        if(me == null && you != null)
            return false;
        else
            return me.equals(you);
    }

    public static final String DEF_SEPARATOR = ",";
    private static final String DB_QUOTE = "'";
    public static final String EMPTY = "";
    private static final int NOT_FOUND = -1;
}

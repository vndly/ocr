package com.mauriciotogneri.ocr;

public class Pixel
{
    public final int value;
    public final int alpha;
    public final int red;
    public final int green;
    public final int blue;

    public static final Pixel WHITE = new Pixel(255, 255, 255, 255);
    public static final Pixel BLACK = new Pixel(255, 0, 0, 0);

    public Pixel(int value)
    {
        this.value = value;
        this.alpha = value >> 24 & 0xff;
        this.red = value >> 16 & 0xff;
        this.green = value >> 8 & 0xff;
        this.blue = value & 0xff;
    }

    public Pixel(int alpha, int red, int green, int blue)
    {
        this((alpha << 24) | (red << 16) | (green << 8) | blue);
    }

    public double diff(Pixel pixel)
    {
        double d = Math.pow((pixel.red - red), 2) + Math.pow((pixel.red - red), 2) + Math.pow((pixel.red - red), 2);

        return Math.sqrt(d);
    }

    public int average()
    {
        return (red + green + blue) / 3;
    }

    public boolean isWhite()
    {
        return (red == WHITE.red) && (green == WHITE.green) && (blue == WHITE.blue);
    }

    public boolean isBlack()
    {
        return (red == BLACK.red) && (green == BLACK.green) && (blue == BLACK.blue);
    }

    public Pixel grayScale()
    {
        int average = average();
        int blackAndWhite = (alpha << 24) | (average << 16) | (average << 8) | average;

        return new Pixel(blackAndWhite);
    }

    public Pixel binarize(int threshold)
    {
        int average = average();
        int value = (average > threshold) ? 255 : 0;
        int binarized = (alpha << 24) | (value << 16) | (value << 8) | value;

        return new Pixel(binarized);
    }

    public interface Transform
    {
        Pixel transform(Pixel pixel);
    }
}
package com.mauriciotogneri.ocr;

import java.util.List;

public class Symbol
{
    public final int x;
    public final int y;
    public final int width;
    public final int height;
    public final Matrix matrix;

    public Symbol(int x, int y, Matrix matrix)
    {
        this.x = x;
        this.y = y;
        this.width = matrix.width;
        this.height = matrix.height;
        this.matrix = matrix;
    }

    public int size()
    {
        return width * height;
    }

    public Image image()
    {
        int[] pixels = new int[width * height];

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                boolean cell = matrix.cell(x, y);
                Pixel pixel;

                if (cell)
                {
                    pixel = new Pixel(255, 0, 0, 0);
                }
                else
                {
                    pixel = new Pixel(255, 255, 255, 255);
                }

                pixels[x + (y * width)] = pixel.value;
            }
        }

        return new Image(width, height, pixels);
    }

    public static Symbol fromPositions(List<Position> positions)
    {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = 0;
        int maxY = 0;

        for (Position position : positions)
        {
            if (position.x < minX)
            {
                minX = position.x;
            }

            if (position.x > maxX)
            {
                maxX = position.x;
            }

            if (position.y < minY)
            {
                minY = position.y;
            }

            if (position.y > maxY)
            {
                maxY = position.y;
            }
        }

        int width = maxX - minX + 1;
        int height = maxY - minY + 1;

        boolean[] data = new boolean[width * height];

        for (Position position : positions)
        {
            int x = position.x - minX;
            int y = position.y - minY;
            data[x + (y * width)] = true;
        }

        return new Symbol(minX, minY, new Matrix(width, height, data));
    }
}
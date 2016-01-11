package in.plash.trunext.util;

/**
 * Created by Kiran on 10/17/2015.
 */
public  class MathHelper
{
    public enum ImageSize
    {
        NoImage,
        Wide,
        Square,
        Long
    }

    public static  int GCD(int a, int b)
    {
        while (a != 0 && b != 0)
        {
            if (a > b)
                a %= b;
            else
                b %= a;
        }
        if (a == 0)
            return b;
        else
            return a;
    }

    public static ImageSize Image_Raito(int height , int width)
    {
        if (height == 0 | width == 0) return ImageSize.NoImage;
        int gcd = GCD(width, height);
        float ratio = (float)(width / gcd) / (float)(height / gcd);

        if (ratio < 0.8)
            return ImageSize.Long;

        if (ratio >= 0.8 && ratio <= 1.2)
            return ImageSize.Square;

        // if (ratio > 1.2)
        return ImageSize.Wide;

    }

}
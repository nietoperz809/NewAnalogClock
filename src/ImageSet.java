import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Objects;

public class ImageSet {
    public String name;
    public BufferedImage clockFace;
    public BufferedImage imgHour;
    public BufferedImage imgMinute;
    public BufferedImage imgSecond;

    public ImageSet (String name, String face, String hour, String minute, String second) throws Exception {
        this(name, face, hour, minute);
        imgSecond = ImageIO.read (Objects.requireNonNull (getResource (second)));
    }

    public ImageSet (String[] arr) throws Exception {
        this (arr[0], arr[1], arr[2], arr[3], arr[4]);
    }

    public ImageSet (String name, String face, String hour, String minute) throws Exception {
        this.name = name;
        clockFace = ImageIO.read (Objects.requireNonNull (getResource (face)));
        imgHour = ImageIO.read (Objects.requireNonNull (getResource (hour)));
        imgMinute = ImageIO.read (Objects.requireNonNull (getResource (minute)));
    }

    public static InputStream getResource (String name)
    {
        InputStream is = ClassLoader.getSystemResourceAsStream (name);
        if (is == null)
        {
            System.out.println ("could not load: "+name);
            return null;
        }
        return new BufferedInputStream(is);
    }

    @Override
    public String toString() {
        return name;
    }
}

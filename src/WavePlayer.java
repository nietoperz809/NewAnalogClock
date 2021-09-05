import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

public class WavePlayer
{
    public static void playFileFromResource (String name, boolean wait)
    {
        InputStream i = ImageSet.getResource(name);
        playWave (i, wait);
    }

    public static void playWave (InputStream is, boolean wait)
    {
        CountDownLatch syncLatch = new CountDownLatch(1);
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.addLineListener(e ->
            {
                if (e.getType() == LineEvent.Type.STOP)
                {
                    syncLatch.countDown();
                }
            });
            clip.open (AudioSystem.getAudioInputStream(is));
            clip.start();
            if (wait)
                syncLatch.await();
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }

//    public static void playWave (String filename)
//    {
//        try
//        {
//            playWave(new FileInputStream(new File(filename)));
//        }
//        catch (Exception exc)
//        {
//            exc.printStackTrace(System.out);
//        }
//    }
}

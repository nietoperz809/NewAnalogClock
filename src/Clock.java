import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Clock extends JPanel {
    private boolean smooth;

    private BufferedImage offimg;
    private Graphics offgraph;
    private ImageSet imageSet;

    public Clock (ImageSet imgSet) {
        newImageSet (imgSet);
        // Tick the clock
        ScheduledExecutorService sched = Executors.newScheduledThreadPool(1);
        sched.scheduleAtFixedRate(this::Tick, 0, 100, TimeUnit.MILLISECONDS);
    }

    public void newImageSet (ImageSet imgSet) {
        imageSet = imgSet;
        offimg = new BufferedImage (imgSet.clockFace.getWidth(),
                imgSet.clockFace.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        offgraph = offimg.createGraphics();
    }

    public void setSmoothSecPointer(boolean sm) {
        smooth = sm;
    }

    // Tick to drive the clock
    public synchronized void Tick() {
        GregorianCalendar cal = new GregorianCalendar();
        float sec = cal.get(Calendar.SECOND);
        if (smooth) {
            float mill = cal.get(Calendar.MILLISECOND) / 1000f;
            sec += mill;
        }
        int min = cal.get(Calendar.MINUTE);
        int hr = cal.get(Calendar.HOUR_OF_DAY); // 0 ... 23

        // Face
        offgraph.drawImage (imageSet.clockFace, 0, 0, null);
        // Sec
        Image i1 = rotateImage(imageSet.imgSecond, sec*6f);
        offgraph.drawImage(i1, 0, 0, null);
        // Min
        Image i2 = rotateImage(imageSet.imgMinute,min*6f);
        offgraph.drawImage(i2, 0, 0, null);
        // Hour
        Image i3 = rotateImage(imageSet.imgHour,hr*30f);
        offgraph.drawImage(i3, 0, 0, null);
        repaint();
    }

    public static BufferedImage rotateImage (BufferedImage bimg, double angle) {
        int w = bimg.getWidth();
        int h = bimg.getHeight();

        BufferedImage rotated = new BufferedImage(w, h, bimg.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.rotate(Math.toRadians(angle), w/2f, h/2f);
        graphic.drawImage(bimg, null, 0, 0);
        graphic.dispose();
        return rotated;
    }

    @Override
    public synchronized void update(Graphics g) {
        // Do intentionally nuthin'
    }

    @Override
    public synchronized void paint(Graphics g) {
        g.drawImage(offimg, 0, 0, getWidth(), getHeight(), null);
    }

    public Dimension getPreferredSize() {
        return new Dimension(offimg.getWidth(), offimg.getHeight());
    }
}

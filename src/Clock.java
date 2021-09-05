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
    private String tick;
    private int counter;
    private BufferedImage offimg;
    private Graphics2D offgraph;
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
                BufferedImage.TYPE_INT_ARGB);
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
        if (tick != null) {
            counter++;
            if (counter == 10) {
                counter = 0;
                WavePlayer.playFileFromResource(tick);
            }
        }
        int min = cal.get(Calendar.MINUTE);
        int hr = cal.get(Calendar.HOUR); // 0 ... 23
        int temp = hr * 60 + min;

        // Face
        offgraph.clearRect(0,0, offimg.getWidth(), offimg.getHeight());
        offgraph.drawImage (imageSet.clockFace, 0, 0, null);

//        float alpha = 1.0f; //draw half transparent
//        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.CLEAR,alpha);
//        offgraph.setComposite(ac);

        // Sec
        if (imageSet.imgSecond != null) {
            Image i1 = rotateImage(imageSet.imgSecond, sec * 6f);
            offgraph.drawImage(i1, 0, 0, null);
        }
        // Min
        Image i2 = rotateImage(imageSet.imgMinute,temp*6f);
        offgraph.drawImage(i2, 0, 0, null);
        // Hour
        Image i3 = rotateImage(imageSet.imgHour,temp/2f);
        offgraph.drawImage(i3, 0, 0, null);
        repaint();
    }

    public static BufferedImage rotateImage (BufferedImage bimg, float angle) {
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

    public void setTick (String s) {
        if (s == null)
            tick = null;
        else
            tick = s;
    }
}


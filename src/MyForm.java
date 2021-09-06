import com.raven.swing.TimePicker;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MyForm {
    private final JPanel mainPanel = new JPanel();
    private final TimePicker tp = new com.raven.swing.TimePicker();
    private final Clock clock;
    JButton btAlarm = new JButton("Al off");

    public MyForm() throws Exception{
        DefaultComboBoxModel<ImageSet> combMod = new DefaultComboBoxModel<>();
        JCheckBox cbSmooth = new JCheckBox("Smooth SecPointer");
        JCheckBox cbTick = new JCheckBox("Tick Sound");
        JComboBox<ImageSet> comboBox = new JComboBox<>();

        combMod.addElement (new ImageSet ("Dial", "ics_clock_dial2.png", "ics_clock_hour.png",
                "ics_clock_minute.png", "ics_clock_second.png"));
        combMod.addElement (new ImageSet ("Default", "ics_clock_dial.png", "ics_clock_hour.png",
                "ics_clock_minute.png", "ics_clock_second.png"));
        combMod.addElement (new ImageSet ("G1", "g1_dial.png", "g1_hour.png",
                "g1_minute.png", "g1_second.png"));
        combMod.addElement (new ImageSet ("Bell", "bell_dial.png", "bell_hour.png",
                "bell_minute.png", "bell_second.png"));
        combMod.addElement (new ImageSet ("Day", "day_dial.png", "day_hour.png",
                "day_minute.png", "day_second.png"));
        combMod.addElement (new ImageSet ("Bg", "bg_clock_bg.png", "ic_hour.png",
                "ic_minute.png", "ic_miao.png"));
        combMod.addElement (new ImageSet ("Honey", "honeycomb_clock_dial.png", "honeycomb_clock_hour.png",
                "honeycomb_clock_minute.png", "honeycomb_clock_sec.png"));
        combMod.addElement (new ImageSet ("Manila", "manila_dial.png", "manila_hour.png",
                "manila_minute.png", "manila_sec.png"));
        combMod.addElement (new ImageSet ("Toon", "toon_dial.png", "toon_hour.png",
                "toon_minute.png", "toon_sec.png"));
        combMod.addElement (new ImageSet ("Roman", "roman1_bg.png", "roman1_hour_hand.png",
                "roman1_minute_hand.png", "roman1_sec_hand.png"));
        combMod.addElement (new ImageSet ("Stock1", "stockg1_dial.png", "stockg1_hour.png",
                "stockg1_minute.png", "stockg1_sec.png"));
        combMod.addElement (new ImageSet ("Red", "red1_bg.png", "red1_hour_hand.png",
                "red1_minute_hand.png", "red1_sec_hand.png"));
        combMod.addElement (new ImageSet ("Blue", "blue_dial.png", "blue_hour.png",
                "blue_minute.png", "blue_sec.png"));
        combMod.addElement (new ImageSet ("CSS", "css_dial.png", "css_hour.png",
                "css_minute.png", "css_sec.png"));
        combMod.addElement (new ImageSet ("Goog", "clockgoog1_dial.png", "clockgoog1_hour.png",
                "clockgoog1_minute.png", "clockgoog1_sec.png"));
        combMod.addElement (new ImageSet ("GDE", "gdedial.png", "gdehour.png",
                "gdeminute.png", "gdesec.png"));
        combMod.addElement (new ImageSet ("MY", "my3_dial.png", "my_clock_hour.png",
                "my_clock_minute.png", "my_clock_sec.png"));
        combMod.addElement (new ImageSet ("Clean", "clean_dial.png", "clean_hour.png",
                "clean_minute.png", "clean_sec.png"));

        comboBox.setModel (combMod);

        clock = new Clock (combMod.getElementAt(0));
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add (clock, BorderLayout.CENTER);
        JPanel jp1 = new JPanel();
        jp1.add (cbSmooth);
        jp1.add (cbTick);
        jp1.add (btAlarm);
        mainPanel.add (jp1, BorderLayout.SOUTH);
        mainPanel.add (comboBox, BorderLayout.NORTH);

        cbSmooth.addActionListener(e -> clock.setSmoothSecPointer(cbSmooth.isSelected()));
        cbTick.addActionListener(e -> clock.setTick(cbTick.isSelected() ? "tick.wav" : null));
        comboBox.addActionListener(e -> clock.newImageSet((ImageSet) Objects.requireNonNull(comboBox.getSelectedItem())));
        btAlarm.addActionListener(e -> startTP());
        tp.addActionListener(e -> {
            btAlarm.setText ("AL ON!");
            String[] sp1 = tp.getSelectedTime().split(" ");
            String[] sp2 = sp1[0].split(":");
            int hour = Integer.parseInt(sp2[0]);
            int min = Integer.parseInt(sp2[1]);
            if (sp1[1].equals("PM"))
                hour = (hour + 12)%24;
            clock.setAlarmTime(hour,min);
            //System.out.println(""+hour+"-"+min);
        });
    }

    private void startTP()
    {
        btAlarm.setText("AL off");
        clock.clearAlarmTime();
        tp.setForeground(new Color(138, 48, 191));
        tp.setSelectedTime (new java.util.Date());
        tp.showPopup(mainPanel, (mainPanel.getWidth() - tp.getPreferredSize().width) / 2,
                (mainPanel.getHeight() - tp.getPreferredSize().height) / 2);
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Analog Watch");
        frame.setSize(500, 550);
        MyForm mf = new MyForm();
        frame.setContentPane(mf.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

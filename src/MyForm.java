import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MyForm {
    private final JPanel mainPanel = new JPanel();

    public MyForm() throws Exception{
        DefaultComboBoxModel<ImageSet> combMod = new DefaultComboBoxModel<>();
        JCheckBox cbSmooth = new JCheckBox("Smooth SecPointer");
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

        comboBox.setModel (combMod);

        Clock clock = new Clock (combMod.getElementAt(0));
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add (clock, BorderLayout.CENTER);
        mainPanel.add (cbSmooth, BorderLayout.SOUTH);
        mainPanel.add (comboBox, BorderLayout.NORTH);

        cbSmooth.addActionListener(e -> clock.setSmoothSecPointer(cbSmooth.isSelected()));
        comboBox.addActionListener(e -> clock.newImageSet((ImageSet) Objects.requireNonNull(comboBox.getSelectedItem())));
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Analog Watch");
        MyForm mf = new MyForm();
        frame.setContentPane(mf.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

import javax.swing.*;
import java.awt.*;

public class MyForm {
    private Clock clock;
    private final JPanel mainPanel = new JPanel();
    private final JCheckBox cbSmooth = new JCheckBox("Smooth SecPointer");
    private final JComboBox<ImageSet> comboBox = new JComboBox<>();
    private final DefaultComboBoxModel<ImageSet> combMod = new DefaultComboBoxModel<>();

    public MyForm() {
        cbSmooth.addActionListener(e -> clock.setSmoothSecPointer(cbSmooth.isSelected()));
        comboBox.addActionListener(e -> clock.newImageSet((ImageSet) comboBox.getSelectedItem()));
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Analog Watch");
        MyForm mf = new MyForm();
        frame.setContentPane(mf.mainPanel);

        mf.combMod.addElement (new ImageSet ("Dial", "ics_clock_dial2.png", "ics_clock_hour.png",
                "ics_clock_minute.png", "ics_clock_second.png"));
        mf.combMod.addElement (new ImageSet ("Default", "ics_clock_dial.png", "ics_clock_hour.png",
                "ics_clock_minute.png", "ics_clock_second.png"));

        mf.comboBox.setModel (mf.combMod);

        mf.clock = new Clock (mf.combMod.getElementAt(0));
        mf.mainPanel.setLayout(new BorderLayout());
        mf.mainPanel.add (mf.clock, BorderLayout.CENTER);
        mf.mainPanel.add (mf.cbSmooth, BorderLayout.SOUTH);
        mf.mainPanel.add (mf.comboBox, BorderLayout.NORTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setVisible(true);
    }
}

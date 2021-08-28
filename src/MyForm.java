import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MyForm {
    private Clock clock;
    private final JPanel mainPanel = new JPanel();
    private final JCheckBox cbSmooth = new JCheckBox("Smooth SecPointer");
    private final JComboBox<String> comboBox = new JComboBox<>();
    private final ArrayList<ImageSet> al = new ArrayList<>();

    public MyForm() {
        cbSmooth.addActionListener(e -> clock.setSmoothSecPointer(cbSmooth.isSelected()));
        comboBox.addActionListener(e -> {
            String s = (String)comboBox.getSelectedItem();
            for (ImageSet is : al) {
                if (is.name.equals(s)) {
                    clock.newImageSet(is);
                }
            }
        });
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Analog Watch");
        MyForm mf = new MyForm();
        frame.setContentPane(mf.mainPanel);

        mf.al.add (new ImageSet ("Dial", "ics_clock_dial2.png", "ics_clock_hour.png",
                "ics_clock_minute.png", "ics_clock_second.png"));
        mf.al.add (new ImageSet ("Default", "ics_clock_dial.png", "ics_clock_hour.png",
                "ics_clock_minute.png", "ics_clock_second.png"));

        DefaultComboBoxModel<String> li = new DefaultComboBoxModel<>();
        for (ImageSet is : mf.al)
            li.addElement(is.name);
        mf.comboBox.setModel(li);

        mf.clock = new Clock (mf.al.get(0));
        mf.mainPanel.setLayout(new BorderLayout());
        mf.mainPanel.add (mf.clock, BorderLayout.CENTER);
        mf.mainPanel.add (mf.cbSmooth, BorderLayout.SOUTH);
        mf.mainPanel.add (mf.comboBox, BorderLayout.NORTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setVisible(true);
    }
}

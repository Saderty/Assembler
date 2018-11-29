package Assembler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

class Elements {
    private static Container contentPane;
    private static SpringLayout layout;

    Elements(JFrame frame) {
        contentPane = frame.getContentPane();
        layout = new SpringLayout();
        contentPane.setLayout(layout);
    }

    static void createLabel(JComponent component, int x, int y) {
        createLabel(component, null, x, y);
    }

    static void createLabel(String text, int x, int y) {
        createLabel(new JLabel(), text, x, y);
    }

    static void createLabel(JComponent component, String text, int x, int y) {
        int elementSize = 40;

        Dimension dimension = new Dimension(elementSize, elementSize);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

        component.setPreferredSize(dimension);
        component.setBorder(border);
        ((JLabel) component).setHorizontalAlignment(JLabel.CENTER);
        ((JLabel) component).setVerticalAlignment(JLabel.CENTER);
        ((JLabel) component).setText(text);

        contentPane.add(component);
        setElement(component, x, y);
    }

    static void createTextArea(JComponent component, int x, int y, int width, int height) {
        component.setPreferredSize(new Dimension(width, height));
        component.setFont(new Font("Dialog", Font.PLAIN, 14));
        ((JTextArea) component).setTabSize(10);
        ((JTextArea) component).setLineWrap(true);
        ((JTextArea) component).setWrapStyleWord(false);

        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(scrollPane);
        setElement(scrollPane, x, y);
    }

    static void createTextField(JComponent component, int x, int y, int width, int height) {
        component.setFont(new Font("Dialog", Font.PLAIN, 20));
        component.setPreferredSize(new Dimension(width, height));
        contentPane.add(component);
        setElement(component, x, y);
    }

    static void createButton(JComponent component, String text, int x, int y, int width, int height) {
        ((JButton) component).setText(text);
        component.setPreferredSize(new Dimension(width, height));
        ((JButton) component).setHorizontalAlignment(JButton.CENTER);
        ((JButton) component).setVerticalAlignment(JButton.CENTER);
        contentPane.add(component);
        setElement(component, x, y);
    }

     static void setElement(JComponent component, int x, int y) {
        layout.putConstraint(SpringLayout.NORTH, component, y,
                SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, component, x,
                SpringLayout.WEST, contentPane);
    }
}

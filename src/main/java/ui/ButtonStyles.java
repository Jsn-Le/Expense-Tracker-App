package ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class ButtonStyles {

    public static JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        applyHoverEffect(button,
                new Color(37, 99, 235),
                new Color(29, 78, 216),
                new Color(30, 82, 205),
                Color.WHITE,
                Color.WHITE,
                Color.WHITE);
        return button;
    }

    public static JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        applyHoverEffect(button,
                new Color(241, 245, 249),
                new Color(226, 232, 240),
                new Color(203, 213, 225),
                new Color(30, 41, 59),
                new Color(15, 23, 42),
                new Color(15, 23, 42));
        return button;
    }

    public static void applyHoverEffect(JButton button, Color defaultBg, Color hoverBg, Color pressedBg,
            Color defaultFg, Color hoverFg, Color pressedFg) {
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        button.setBackground(defaultBg);
        button.setForeground(defaultFg);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverBg);
                button.setForeground(hoverFg);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(defaultBg);
                button.setForeground(defaultFg);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(pressedBg);
                button.setForeground(pressedFg);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (button.contains(e.getPoint())) {
                    button.setBackground(hoverBg);
                    button.setForeground(hoverFg);
                } else {
                    button.setBackground(defaultBg);
                    button.setForeground(defaultFg);
                }
            }
        });
    }
}

import javax.swing.*;
import java.awt.*;

public class AspectRatioApp extends JFrame {

    private JTextField widthField;
    private JTextField heightField;
    private JLabel resultLabel;

    public AspectRatioApp() {
        setTitle("Aspect Ratio Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🔹 Panel principal
        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 5, 5));

        mainPanel.add(new JLabel("Base:"));
        widthField = new JTextField();
        mainPanel.add(widthField);

        mainPanel.add(new JLabel("Altura:"));
        heightField = new JTextField();
        mainPanel.add(heightField);

        // 🔹 Botones
        JButton btn43 = new JButton("4 : 3");
        JButton btn169 = new JButton("16 : 9");
        JButton btn1610 = new JButton("16 : 10");

        btn43.addActionListener(e -> calculate(4, 3));
        btn169.addActionListener(e -> calculate(16, 9));
        btn1610.addActionListener(e -> calculate(16, 10));

        mainPanel.add(btn43);
        mainPanel.add(btn169);
        mainPanel.add(btn1610);

        resultLabel = new JLabel("Resultado: ");
        mainPanel.add(resultLabel);

        add(mainPanel, BorderLayout.CENTER);

        // 🔹 Panel inferior (mensaje)
        JLabel imageInfo = new JLabel(
                "<html>📷 Puedes subir una imagen<br>" +
                        "y la app calculará su aspect ratio</html>",
                SwingConstants.CENTER
        );
        imageInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(imageInfo, BorderLayout.SOUTH);
    }

    private void calculate(double ratioW, double ratioH) {
        try {
            String wText = widthField.getText();
            String hText = heightField.getText();

            double width = wText.isEmpty() ? 0 : Double.parseDouble(wText);
            double height = hText.isEmpty() ? 0 : Double.parseDouble(hText);

            if (width > 0 && height == 0) {
                height = width * ratioH / ratioW;
            } else if (height > 0 && width == 0) {
                width = height * ratioW / ratioH;
            } else {
                JOptionPane.showMessageDialog(this,
                        "Introduce SOLO base o SOLO altura",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            resultLabel.setText(
                    String.format("Resultado: %.0f x %.0f", width, height)
            );

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Introduce valores numéricos válidos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AspectRatioApp().setVisible(true);
        });
    }
}

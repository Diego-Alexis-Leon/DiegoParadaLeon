import tools.Tool;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageEditor extends JFrame {

    private BufferedImage original;     // Image selected
    private BufferedImage workingCopy;  // Copy of image selected

    private final ImagePanel imagePanel = new ImagePanel();

    private final JButton btnChoose = new JButton("Chose image...");
    private final JButton btnTrim   = new JButton("Crop");
    private final JButton btnRotate = new JButton("Rotate");
    private final JButton btnInvert = new JButton("Invert colors");
    private final JButton btnChange = new JButton("select image...");
    private final JButton btnSave = new JButton("Save image");
    private final JLabel sizeLabel = new JLabel("—");

    private final JTextField x1Field = new JTextField(5);
    private final JTextField y1Field = new JTextField(5);
    private final JTextField x2Field = new JTextField(5);
    private final JTextField y2Field = new JTextField(5);

    private final JComboBox<Integer> rotateDegrees = new JComboBox<>(new Integer[]{90, 180, 270});

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageEditor ui = new ImageEditor();
            ui.setVisible(true);
        });
    }

    public ImageEditor() {
        super("Editor");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());


        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));  // Main Panel
        top.add(btnChoose);
        add(top, BorderLayout.NORTH);


        add(new JScrollPane(imagePanel), BorderLayout.CENTER); // center image

        JPanel bottom = new JPanel();
        bottom.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6); // size of coords
        gbc.gridy = 0;
        gbc.gridx = 0;

        bottom.add(new JLabel("Size:"), gbc); gbc.gridx++;
        bottom.add(sizeLabel, gbc); gbc.gridx++;
        bottom.add(new JLabel("x1:"), gbc); gbc.gridx++;
        bottom.add(x1Field, gbc); gbc.gridx++;
        bottom.add(new JLabel("y1:"), gbc); gbc.gridx++;
        bottom.add(y1Field, gbc); gbc.gridx++;
        bottom.add(new JLabel("x2:"), gbc); gbc.gridx++;
        bottom.add(x2Field, gbc); gbc.gridx++;
        bottom.add(new JLabel("y2:"), gbc); gbc.gridx++;
        bottom.add(y2Field, gbc);
        //gbc.gridx++;


        gbc.gridx = 0; //size of buttons
        gbc.gridy = 1;

        bottom.add(btnTrim, gbc); gbc.gridx++;
        bottom.add(btnRotate, gbc); gbc.gridx++;
        bottom.add(rotateDegrees, gbc); gbc.gridx++;
        bottom.add(btnInvert, gbc); gbc.gridx++;
        bottom.add(btnChange, gbc); gbc.gridx++;
        bottom.add(btnSave, gbc);
        add(bottom, BorderLayout.SOUTH);
        setEditorEnabled(false);


        installNumericFilter(x1Field); // only you can introduce int
        installNumericFilter(y1Field);
        installNumericFilter(x2Field);
        installNumericFilter(y2Field);

        //ACTION CHOSE AND CHANGE
        btnChoose.addActionListener(e -> chooseImage());
        btnChange.addActionListener(e -> chooseImage());

        //ACTION CROP
        btnTrim.addActionListener(e -> {
            if (!hasImage()) return;
            try {
                Rect r = readAndValidateRect();
                workingCopy = Tool.trim(workingCopy, r.x1, r.y1, r.x2, r.y2);
                imagePanel.setImage(workingCopy);
                updateSizeLabel();
                clearFields();
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        });

        //ACTION INVERT COLORS
        btnInvert.addActionListener(e -> {
            if (!hasImage()) return;
            try {
                Rect r = readAndValidateRect();
                Tool.invetcolors(workingCopy, r.x1, r.y1, r.x2, r.y2);
                imagePanel.repaint();
                clearFields();
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        });

        //ACTION ROTATE
        btnRotate.addActionListener(e -> {
            if (!hasImage()) return;
            try {
                Rect r = readAndValidateRect();
                int deg = (Integer) rotateDegrees.getSelectedItem();
                workingCopy = Tool.rotate(workingCopy, r.x1, r.y1, r.x2, r.y2, deg);
                imagePanel.setImage(workingCopy);  // <- clave
                imagePanel.revalidate();
                imagePanel.repaint();
                updateSizeLabel();
                clearFields();
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        });

        //ACTION SAVE
        btnSave.addActionListener(e -> {
            if (workingCopy == null) {
                showError("Select a image.");
                return;
            }

            try {
                // Save the image copy in exports
                File outDir = new File("src/exports");

                // unique name
                String name = "edited_" + System.currentTimeMillis() + ".png";
                File out = new File(outDir, name);
                ImageIO.write(workingCopy, "png", out);

                JOptionPane.showMessageDialog(this,
                        "Save as:\n" + out.getAbsolutePath(),
                        "Finish", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException ex) {
                showError("Error, can´t save: " + ex.getMessage());
            }
        });
    }

    //Verify if you are edit an image
    private boolean hasImage() {
        if (workingCopy == null) {
            showError("Select a image.");
            return false;
        }
        return true;
    }


    private void chooseImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select a image");

        int result = chooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) return;

        File file = chooser.getSelectedFile();
        try {
            original = ImageIO.read(file);
            if (original == null) {
                showError("Invalid file.");
                return;
            }


            workingCopy = deepCopy(original); // Create image copy

            imagePanel.setImage(workingCopy); // Show the image copy
            updateSizeLabel();
            setEditorEnabled(true);
            clearFields();

        } catch (IOException ex) {
            showError("Error when read the image: " + ex.getMessage());
        }
    }

    private void setEditorEnabled(boolean enabled) {
        btnTrim.setEnabled(enabled);
        btnRotate.setEnabled(enabled);
        btnInvert.setEnabled(enabled);
        btnChange.setEnabled(enabled);

        x1Field.setEnabled(enabled);
        y1Field.setEnabled(enabled);
        x2Field.setEnabled(enabled);
        y2Field.setEnabled(enabled);

        rotateDegrees.setEnabled(enabled);

        // Cuando ya hay imagen, puedes ocultar el botón inicial si quieres:
        // btnChoose.setVisible(!enabled);
        // (Si lo dejas visible no pasa nada, solo es redundante.)
    }


    private Rect readAndValidateRect() {
        int w = workingCopy.getWidth();
        int h = workingCopy.getHeight();

        Integer x1 = parseOrNull(x1Field.getText());
        Integer y1 = parseOrNull(y1Field.getText());
        Integer x2 = parseOrNull(x2Field.getText());
        Integer y2 = parseOrNull(y2Field.getText());

        // the void fields are full size
        // x1,y1 => 0
        // x2 => width-1
        // y2 => height-1

        int fx1 = (x1 == null) ? 0 : x1;
        int fy1 = (y1 == null) ? 0 : y1;
        int fx2 = (x2 == null) ? (w - 1) : x2;
        int fy2 = (y2 == null) ? (h - 1) : y2;

        // Bounds
        if (fx1 < 0 || fy1 < 0 || fx2 < 0 || fy2 < 0)
            throw new IllegalArgumentException("Only positive numbers.");

        if (fx1 > w - 1 || fx2 > w - 1)
            throw new IllegalArgumentException("X out of range. Max X = " + (w - 1));

        if (fy1 > h - 1 || fy2 > h - 1)
            throw new IllegalArgumentException("Y out of range. Max Y = " + (h - 1));

        // Ordering
        if (fx1 > fx2)
            throw new IllegalArgumentException("x1 must be smaller that x2.");
        if (fy1 > fy2)
            throw new IllegalArgumentException("y1 must be smaller that  y2.");

        return new Rect(fx1, fy1, fx2, fy2);
    }

    //Reset the fields when do you do an action
    private void clearFields() {
        x1Field.setText("");
        y1Field.setText("");
        x2Field.setText("");
        y2Field.setText("");
    }

    //Show errors messages
    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }


    private Integer parseOrNull(String s) { // Verify the strings in the cords files
        s = s.trim();
        if (s.isEmpty()) return null;
        return Integer.parseInt(s);
    }

    private void installNumericFilter(JTextField field) {
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string == null) return;
                if (string.matches("\\d+")) super.insertString(fb, offset, string, attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text == null) return;
                if (text.isEmpty() || text.matches("\\d+")) super.replace(fb, offset, length, text, attrs);
            }
        });
    }

    private BufferedImage deepCopy(BufferedImage src) {
        int type = (src.getType() == 0) ? BufferedImage.TYPE_INT_ARGB : src.getType();
        BufferedImage copy = new BufferedImage(src.getWidth(), src.getHeight(), type);
        Graphics2D g2 = copy.createGraphics();
        g2.drawImage(src, 0, 0, null);
        g2.dispose();
        return copy;
    }

    private static class Rect {
        final int x1, y1, x2, y2;
        Rect(int x1, int y1, int x2, int y2) {
            this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;
        }
    }


    private static class ImagePanel extends JPanel {
        private BufferedImage img;

        void setImage(BufferedImage img) {
            this.img = img;
            revalidate();
            repaint();
        }

        @Override
        public Dimension getPreferredSize() {
            if (img == null) return new Dimension(800, 500);

            return new Dimension(Math.max(800, img.getWidth()), Math.max(500, img.getHeight()));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (img == null) return;

            int panelW = getWidth();
            int panelH = getHeight();
            int imgW = img.getWidth();
            int imgH = img.getHeight();


            double scale = Math.min((double) panelW / imgW, (double) panelH / imgH);
            scale = Math.min(scale, 1.0);

            int drawW = (int) (imgW * scale);
            int drawH = (int) (imgH * scale);

            int x = (panelW - drawW) / 2;
            int y = (panelH - drawH) / 2;

            g.drawImage(img, x, y, drawW, drawH, null);
        }
    }


    private void updateSizeLabel() { //Show the image copy size
        if (workingCopy == null) {
            sizeLabel.setText("—");
        } else {
            sizeLabel.setText(workingCopy.getWidth() + "x" + workingCopy.getHeight());
        }
    }
}
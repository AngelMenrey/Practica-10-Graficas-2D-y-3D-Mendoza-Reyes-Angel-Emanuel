import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.geom.Ellipse2D;
import java.awt.geom.AffineTransform;

public class Practica10MendozaReyesAngelEmanuel extends JFrame implements MouseListener, MouseMotionListener {

    private JPanel panelPpal;
    private Image buffer;
    private Graphics2D bufferGraphics;
    private int currentTool = 0; 
    private Color currentColor = Color.BLACK;
    private boolean isScaling = false; 
    private int x, y;
    private int cursorSize = 32;
    private boolean fillShape = false; 
    private boolean hasFigure = false; 
    private boolean isTranslating = false; 
    private int offsetX, offsetY; 
    private int initialX, initialY; 
    private Shape currentShape; 
    private boolean isRotating = false; 
    private double initialAngle; 
    private boolean isShearing = false; 
    private boolean isLine = false; 

    public Practica10MendozaReyesAngelEmanuel() {
        setTitle("Practica 10 - Mendoza Reyes Angel Emanuel");
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ImageIcon icon = new ImageIcon("paint.png");
        setIconImage(icon.getImage());

        JPanel iconPanel = new JPanel();
        iconPanel.setBackground(Color.LIGHT_GRAY);
        iconPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        ImageIcon grosorIcon = new ImageIcon(new ImageIcon("grosor.png").getImage().getScaledInstance(30, 70, Image.SCALE_SMOOTH));
        ImageIcon lineaIcon = new ImageIcon(new ImageIcon("linea.png").getImage().getScaledInstance(30, 20, Image.SCALE_SMOOTH));
        ImageIcon lineaDescontinuaIcon = new ImageIcon(new ImageIcon("linea-descontinua.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon circuloOvaloIcon = new ImageIcon(new ImageIcon("circulo-ovalo.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon rectanguloCuadradoIcon = new ImageIcon(new ImageIcon("rectangulo-cuadrado.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon trianguloIcon = new ImageIcon(new ImageIcon("triangulo.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon rojoIcon = new ImageIcon(new ImageIcon("rojo.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon verdeIcon = new ImageIcon(new ImageIcon("verde.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon negroIcon = new ImageIcon(new ImageIcon("negro.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon borradorIcon = new ImageIcon(new ImageIcon("borrador.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        
        JLabel grosorLabel = new JLabel(grosorIcon);
        JLabel lineaLabel = new JLabel(lineaIcon);
        JLabel lineaDescontinuaLabel = new JLabel(lineaDescontinuaIcon);
        JLabel circuloOvaloLabel = new JLabel(circuloOvaloIcon);
        JLabel rectanguloCuadradoLabel = new JLabel(rectanguloCuadradoIcon);
        JLabel rojoLabel = new JLabel(rojoIcon);
        JLabel verdeLabel = new JLabel(verdeIcon);
        JLabel negroLabel = new JLabel(negroIcon);
        JLabel borradorLabel = new JLabel(borradorIcon);
        JLabel trianguloLabel = new JLabel(trianguloIcon);

        iconPanel.add(grosorLabel);
        iconPanel.add(lineaLabel);
        iconPanel.add(lineaDescontinuaLabel);
        iconPanel.add(circuloOvaloLabel);
        iconPanel.add(rectanguloCuadradoLabel);
        iconPanel.add(trianguloLabel);
        iconPanel.add(rojoLabel);
        iconPanel.add(verdeLabel);
        iconPanel.add(negroLabel);
        iconPanel.add(borradorLabel);
        
        panelPpal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (buffer != null) {
                    g.drawImage(buffer, 0, 0, null);
                }
            }
        };
        panelPpal.setLayout(new BorderLayout());
        panelPpal.addMouseListener(this);
        panelPpal.addMouseMotionListener(this);
        panelPpal.add(iconPanel, BorderLayout.NORTH);
        add(panelPpal);

        grosorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentTool = 1;
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                isTranslating = false;
                JOptionPane.showMessageDialog(null, "Herramienta seleccionada: Línea gruesa");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });

        lineaLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentTool = 2;
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                isTranslating = false;
                JOptionPane.showMessageDialog(null, "Herramienta seleccionada: Línea normal");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });

        lineaDescontinuaLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentTool = 3;
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                isTranslating = false;
                JOptionPane.showMessageDialog(null, "Herramienta seleccionada: Línea discontinua");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });

        rojoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentColor = Color.RED;
                isTranslating = false;
                JOptionPane.showMessageDialog(null, "Color seleccionado: Rojo");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });

        verdeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentColor = Color.GREEN;
                isTranslating = false;
                JOptionPane.showMessageDialog(null, "Color seleccionado: Verde");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });

        negroLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentColor = Color.BLACK;
                isTranslating = false;
                JOptionPane.showMessageDialog(null, "Color seleccionado: Negro");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });

    circuloOvaloLabel.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        currentTool = 4;
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        isTranslating = false;
        JOptionPane.showMessageDialog(null, "Herramienta seleccionada: Círculo/Ovalo");
        fillShape = true; 
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setCursor(Cursor.getDefaultCursor());
    }
});

    rectanguloCuadradoLabel.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        currentTool = 5; 
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        isTranslating = false;
        JOptionPane.showMessageDialog(null, "Herramienta seleccionada: Rectángulo/Cuadrado");
        fillShape = true; 
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setCursor(Cursor.getDefaultCursor());
    }
});


    trianguloLabel.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        currentTool = 7;
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        isTranslating = false;
        JOptionPane.showMessageDialog(null, "Herramienta seleccionada: Triángulo");
        fillShape = true;
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        setCursor(Cursor.getDefaultCursor());
    }
});
        BufferedImage cursorImage = new BufferedImage(cursorSize, cursorSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = cursorImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.fillOval(0, 0, cursorSize, cursorSize);
        g2d.dispose();

        Cursor eraserCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(cursorSize / 2, cursorSize / 2), "Eraser Cursor");

        borradorLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentTool = 6; 
                setCursor(eraserCursor); 
                isTranslating = false;
                JOptionPane.showMessageDialog(null, "Herramienta seleccionada: Borrador");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        menuPanel.setBackground(Color.LIGHT_GRAY);

        JButton translacionButton = new JButton("Translación");
        JButton escaladoButton = new JButton("Escalado");
        JButton rotacionButton = new JButton("Rotación");
        JButton sesgadoButton = new JButton("Sesgado");

        menuPanel.add(translacionButton);
        menuPanel.add(escaladoButton);
        menuPanel.add(rotacionButton);
        menuPanel.add(sesgadoButton);

        add(menuPanel, BorderLayout.SOUTH);

        translacionButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        if (!hasFigure) {
            JOptionPane.showMessageDialog(null, "No hay figuras dibujadas");
        } else if (isLine) {
            JOptionPane.showMessageDialog(null, "Solo aplica para figuras");
        } else {
            isTranslating = true;
            currentTool = 0;
            setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        }
    }
});

    escaladoButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!hasFigure) {
            JOptionPane.showMessageDialog(null, "No hay figuras dibujadas");
        } else if (isLine) {
            JOptionPane.showMessageDialog(null, "Solo aplica para figuras");
        } else {
            isScaling = true;
            currentTool = 0; 
            setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
        }
    }
});

    rotacionButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!hasFigure) {
            JOptionPane.showMessageDialog(null, "No hay figuras dibujadas");
        } else if (isLine) {
            JOptionPane.showMessageDialog(null, "Solo aplica para figuras");
        } else {
            isRotating = true;
            currentTool = 0; 
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }
});

    sesgadoButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!hasFigure) {
            JOptionPane.showMessageDialog(null, "No hay figuras dibujadas");
        } else if (isLine) {
            JOptionPane.showMessageDialog(null, "Solo aplica para figuras");
        } else {
            isShearing = true;
            currentTool = 0; 
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }
});
        setVisible(true);
        buffer = panelPpal.createImage(panelPpal.getWidth(), panelPpal.getHeight());
        bufferGraphics = (Graphics2D) buffer.getGraphics();
}
@Override
public void mouseDragged(MouseEvent e) {
    if (isTranslating && currentShape != null) {
        int x2 = e.getX();
        int y2 = e.getY();
        int dx = x2 - x;
        int dy = y2 - y;
        x = x2;
        y = y2;
        bufferGraphics.translate(dx, dy);
        bufferGraphics.clearRect(0, 0, panelPpal.getWidth(), panelPpal.getHeight());
        bufferGraphics.setColor(currentColor);
        bufferGraphics.fill(currentShape);
        panelPpal.repaint();
    } else if (isScaling && currentShape != null) {
        int x2 = e.getX();
        int y2 = e.getY();
        int dx = x2 - initialX;
        int dy = y2 - initialY;
        double scaleFactor = 1 + (dx + dy) / 100.0;
        AffineTransform at = new AffineTransform();
        at.translate(currentShape.getBounds2D().getCenterX(), currentShape.getBounds2D().getCenterY());
        at.scale(scaleFactor, scaleFactor);
        at.translate(-currentShape.getBounds2D().getCenterX(), -currentShape.getBounds2D().getCenterY());
        Shape scaledShape = at.createTransformedShape(currentShape);
        bufferGraphics.clearRect(0, 0, panelPpal.getWidth(), panelPpal.getHeight());
        bufferGraphics.setColor(panelPpal.getBackground());
        bufferGraphics.fillRect(0, 0, panelPpal.getWidth(), panelPpal.getHeight()); 
        bufferGraphics.setColor(currentColor);
        bufferGraphics.fill(scaledShape);
        panelPpal.repaint();
    } else if (isRotating && currentShape != null) {
        int x2 = e.getX();
        int y2 = e.getY();
        double currentAngle = Math.atan2(y2 - currentShape.getBounds2D().getCenterY(), x2 - currentShape.getBounds2D().getCenterX());
        double angleDifference = currentAngle - initialAngle;
        AffineTransform at = new AffineTransform();
        at.rotate(angleDifference, currentShape.getBounds2D().getCenterX(), currentShape.getBounds2D().getCenterY());
        Shape rotatedShape = at.createTransformedShape(currentShape);
        bufferGraphics.clearRect(0, 0, panelPpal.getWidth(), panelPpal.getHeight());
        bufferGraphics.setColor(panelPpal.getBackground());
        bufferGraphics.fillRect(0, 0, panelPpal.getWidth(), panelPpal.getHeight());
        bufferGraphics.setColor(currentColor);
        bufferGraphics.fill(rotatedShape);
        panelPpal.repaint();
    } else if (isShearing && currentShape != null) {
        int x2 = e.getX();
        int y2 = e.getY();
        double shearFactorX = (x2 - initialX) / 100.0;
        double shearFactorY = (y2 - initialY) / 100.0;
        AffineTransform at = new AffineTransform();
        at.translate(currentShape.getBounds2D().getCenterX(), currentShape.getBounds2D().getCenterY());
        at.shear(shearFactorX, shearFactorY);
        at.translate(-currentShape.getBounds2D().getCenterX(), -currentShape.getBounds2D().getCenterY());
        Shape shearedShape = at.createTransformedShape(currentShape);
        bufferGraphics.clearRect(0, 0, panelPpal.getWidth(), panelPpal.getHeight());
        bufferGraphics.setColor(panelPpal.getBackground());
        bufferGraphics.fillRect(0, 0, panelPpal.getWidth(), panelPpal.getHeight());
        bufferGraphics.setColor(currentColor);
        bufferGraphics.fill(shearedShape);
        panelPpal.repaint();
    } else if (currentTool != 0) {
        int x2 = e.getX();
        int y2 = e.getY();
        BufferedImage tempImage = new BufferedImage(panelPpal.getWidth(), panelPpal.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = tempImage.createGraphics();
        g2.drawImage(buffer, 0, 0, null);
        g2.setColor(currentColor);

        switch (currentTool) {
            case 1:
                g2.setStroke(new BasicStroke(5));
                g2.drawLine(x, y, x2, y2);
                isLine = true;
                break;    
            case 2:
                g2.setStroke(new BasicStroke(1));
                g2.drawLine(x, y, x2, y2);
                isLine = true;
                break;    
            case 3:
                float[] dash = {10, 10};
                g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dash, 0));
                g2.drawLine(x, y, x2, y2);
                isLine = true;
                break;    
            case 4:
                int width = Math.abs(x2 - x);
                int height = Math.abs(y2 - y);
                int topLeftX = Math.min(x, x2);
                int topLeftY = Math.min(y, y2);
                currentShape = new Ellipse2D.Float(topLeftX, topLeftY, width, height);
                g2.fill(currentShape);
                isLine = false;
                break;    
            case 5:
                width = Math.abs(x2 - x);
                height = Math.abs(y2 - y);
                topLeftX = Math.min(x, x2);
                topLeftY = Math.min(y, y2);
                currentShape = new Rectangle(topLeftX, topLeftY, width, height);
                g2.fill(currentShape);
                isLine = false;
                break;
            case 6:
                g2.setColor(panelPpal.getBackground());
                g2.fillOval(x2 - cursorSize / 2, y2 - cursorSize / 2, cursorSize, cursorSize);
                bufferGraphics.setColor(panelPpal.getBackground());
                bufferGraphics.fillOval(x2 - cursorSize / 2, y2 - cursorSize / 2, cursorSize, cursorSize);
                break;
            case 7:
                int[] xPoints = {x, x2, (x + x2) / 2};
                int[] yPoints = {y2, y2, y};
                currentShape = new Polygon(xPoints, yPoints, 3);
                g2.fill(currentShape);
                isLine = false;
                break;
        }
        g2.dispose();

        Graphics g = panelPpal.getGraphics();
        g.drawImage(tempImage, 0, 0, null);
        g.dispose();
    }
}
@Override
public void mouseReleased(MouseEvent e) {
    if (isTranslating) {
        isTranslating = false;
        setCursor(Cursor.getDefaultCursor());
    } else if (isScaling) {
        isScaling = false;
        setCursor(Cursor.getDefaultCursor());
        int x2 = e.getX();
        int y2 = e.getY();
        int dx = x2 - initialX;
        int dy = y2 - initialY;
        double scaleFactor = 1 + (dx + dy) / 100.0;
        AffineTransform at = new AffineTransform();
        at.translate(currentShape.getBounds2D().getCenterX(), currentShape.getBounds2D().getCenterY());
        at.scale(scaleFactor, scaleFactor);
        at.translate(-currentShape.getBounds2D().getCenterX(), -currentShape.getBounds2D().getCenterY());
        currentShape = at.createTransformedShape(currentShape);
        bufferGraphics.clearRect(0, 0, panelPpal.getWidth(), panelPpal.getHeight());
        bufferGraphics.setColor(panelPpal.getBackground());
        bufferGraphics.fillRect(0, 0, panelPpal.getWidth(), panelPpal.getHeight());
        bufferGraphics.setColor(currentColor);
        bufferGraphics.fill(currentShape);
        panelPpal.repaint();
    } else if (isRotating) {
        isRotating = false;
        setCursor(Cursor.getDefaultCursor());
        int x2 = e.getX();
        int y2 = e.getY();
        double currentAngle = Math.atan2(y2 - currentShape.getBounds2D().getCenterY(), x2 - currentShape.getBounds2D().getCenterX());
        double angleDifference = currentAngle - initialAngle;
        AffineTransform at = new AffineTransform();
        at.rotate(angleDifference, currentShape.getBounds2D().getCenterX(), currentShape.getBounds2D().getCenterY());
        currentShape = at.createTransformedShape(currentShape);
        bufferGraphics.clearRect(0, 0, panelPpal.getWidth(), panelPpal.getHeight());
        bufferGraphics.setColor(panelPpal.getBackground());
        bufferGraphics.fillRect(0, 0, panelPpal.getWidth(), panelPpal.getHeight());
        bufferGraphics.setColor(currentColor);
        bufferGraphics.fill(currentShape);
        panelPpal.repaint();
    } else if (isShearing) {
        isShearing = false;
        setCursor(Cursor.getDefaultCursor());
        int x2 = e.getX();
        int y2 = e.getY();
        double shearFactorX = (x2 - initialX) / 100.0;
        double shearFactorY = (y2 - initialY) / 100.0;
        AffineTransform at = new AffineTransform();
        at.translate(currentShape.getBounds2D().getCenterX(), currentShape.getBounds2D().getCenterY());
        at.shear(shearFactorX, shearFactorY);
        at.translate(-currentShape.getBounds2D().getCenterX(), -currentShape.getBounds2D().getCenterY());
        currentShape = at.createTransformedShape(currentShape);
        bufferGraphics.clearRect(0, 0, panelPpal.getWidth(), panelPpal.getHeight());
        bufferGraphics.setColor(panelPpal.getBackground());
        bufferGraphics.fillRect(0, 0, panelPpal.getWidth(), panelPpal.getHeight());
        bufferGraphics.setColor(currentColor);
        bufferGraphics.fill(currentShape);
        panelPpal.repaint();
    } else if (currentTool != 0) {
        int x2 = e.getX();
        int y2 = e.getY();
        bufferGraphics.setColor(currentColor);

        switch (currentTool) {
            case 1:
                bufferGraphics.setStroke(new BasicStroke(5));
                bufferGraphics.drawLine(x, y, x2, y2);
                isLine = true; 
                break;
            case 2:
                bufferGraphics.setStroke(new BasicStroke(1));
                bufferGraphics.drawLine(x, y, x2, y2);
                isLine = true;
                break;
            case 3:
                float[] dash = {10, 10};
                bufferGraphics.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dash, 0));
                bufferGraphics.drawLine(x, y, x2, y2);
                isLine = true;
                break;
            case 4:
                bufferGraphics.setStroke(new BasicStroke(1));
                int width = Math.abs(x2 - x);
                int height = Math.abs(y2 - y);
                int topLeftX = Math.min(x, x2);
                int topLeftY = Math.min(y, y2);
                currentShape = new Ellipse2D.Float(topLeftX, topLeftY, width, height);
                bufferGraphics.fill(currentShape);
                isLine = false;
                break;
            case 5:
                bufferGraphics.setStroke(new BasicStroke(1));
                width = Math.abs(x2 - x);
                height = Math.abs(y2 - y);
                topLeftX = Math.min(x, x2);
                topLeftY = Math.min(y, y2);
                currentShape = new Rectangle(topLeftX, topLeftY, width, height);
                bufferGraphics.fill(currentShape);
                isLine = false;
                break;
            case 6:
                bufferGraphics.setColor(panelPpal.getBackground());
                bufferGraphics.fillOval(x2 - cursorSize / 2, y2 - cursorSize / 2, cursorSize, cursorSize);
                break;
            case 7:
                int[] xPoints = {x, x2, (x + x2) / 2};
                int[] yPoints = {y2, y2, y};
                currentShape = new Polygon(xPoints, yPoints, 3);
                bufferGraphics.fill(currentShape);
                isLine = false;
                break;
        }
        panelPpal.repaint();
        hasFigure = true;
    }
}
public void mousePressed(MouseEvent e) {
    x = e.getX();
    y = e.getY();
    if (isTranslating) {
        initialX = x;
        initialY = y;
    } else if (isScaling) {
        initialX = x;
        initialY = y;
    } else if (isRotating) {
        initialAngle = Math.atan2(y - currentShape.getBounds2D().getCenterY(), x - currentShape.getBounds2D().getCenterX());
    } else if (isShearing) {
        initialX = x;
        initialY = y;
    }
}
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    public static void main(String[] args) {
        new Practica10MendozaReyesAngelEmanuel();
    }
}
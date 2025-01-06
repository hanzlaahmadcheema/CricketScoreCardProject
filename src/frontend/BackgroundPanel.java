package frontend;

import javax.swing.*;
import java.awt.*;


public class BackgroundPanel extends JPanel {
    
    private Image backgroundImage;

    
    Color backgroundColor = new Color(0, 0, 0, 50); 
    Color primaryBackgroundColor = new Color(34, 40, 49); 
    Color secondaryBackgroundColor = new Color(57, 62, 70); 
    Color foregroundColor = new Color(238, 238, 238); 
    Color transparentColor = new Color(0, 0, 0, 0); 
    Color selectionForegroundColor = new Color(192, 192, 192); 
    Color blackColor = new Color(0, 0, 0); 
    Color hoverBackgroundColor = new Color(40, 46, 55); 
    Color redColor = new Color(255, 0, 0); 

    
    public BackgroundPanel(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 

        
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

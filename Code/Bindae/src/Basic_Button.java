import java.awt.*;
import javax.swing.JButton;
/* -----------버튼 디폴트------------ */
public class Basic_Button extends JButton { 
   private Color col;
   
   public Basic_Button(String text, Color c) {
      super(text);
      col = c;
      setBorderPainted(false);
      setOpaque(false);
      setBackground(Color.WHITE);
      setFocusPainted(false);// 포커스 테두리 x
   }
   
   @Override
   protected void paintComponent(Graphics g) {
      int width = getWidth();
       int height = getHeight();
       
       Graphics2D g1 = (Graphics2D) g;
       g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       
       if(getModel().isRollover() || getModel().isPressed()) {
          g1.setColor(col);
       }else {
          g1.setColor(col);
       }

       g1.fillRect(0, 0, width, height);
    
       FontMetrics fontMetrics = g1.getFontMetrics();
       Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), g1).getBounds();
    
       int textX = (width - stringBounds.width) / 2;
       int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();
    
       g1.setColor(getForeground());
       g1.setFont(getFont());
       g1.drawString(getText(), textX, textY);
       g1.dispose();
    
       super.paintComponent(g1);
   }
}
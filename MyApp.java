import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Scanner;

public class MyApp extends JFrame {

     private ImageJ ij;

     public MyApp() {
         super("My Application");
         this.ij = new ImageJ(ImageJ.NO_SHOW);
         setSize(300, 300);
         setLocationRelativeTo(null);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         addWindowListener(new WindowAdapter() {

             @Override
             public void windowClosing(WindowEvent e) {
                 ij.quit();
                 super.windowClosing(e);
             }
         });
         JButton button = new JButton("Open Image...");
         
         
         button.addActionListener(new ActionListener() {

             @Override
             public void actionPerformed(ActionEvent e) 
             {
                 ImagePlus imp = IJ.openImage();
                 imp.show();

                 JButton button1 = new JButton("Preprocess");	
         button1.addActionListener(new ActionListener()
         {
          public void actionPerformed(ActionEvent e) 
          {
          		 imp.setRoi(5,0,632,387);  
          		 IJ.run(imp, "Crop", "");  //crop image
                 IJ.run(imp, "Gaussian Blur...", "sigma=2"); //apply gaussian blur
                 imp.show();
          }
          });

         
         JButton button2 = new JButton("Segmentation");
         button2.addActionListener(new ActionListener()
         {
         	public void actionPerformed(ActionEvent e) {
                 IJ.run(imp, "8-bit", "");
                 IJ.setAutoThreshold(imp, "Otsu dark"); //set threshold
                 IJ.run(imp, "Create Selection", ""); //create ROI
                 IJ.run(imp, "Make Inverse", ""); //invert ROI
                 
                 
                 
                 imp.show();
             }
         });

         JButton button3 = new JButton("Analyze Image");
         button3.addActionListener(new ActionListener()
         {
         	public void actionPerformed(ActionEvent e) {

                 IJ.run(imp, "Measure", ""); //Measure 
                 IJ.run(imp, "Make Binary", "");
                 
             }
         });

         JPanel contentPane = new JPanel(new BorderLayout());
         getContentPane().add(contentPane);
         contentPane.add(button1, BorderLayout.WEST);
         contentPane.add(button2, BorderLayout.EAST);
         contentPane.add(button3, BorderLayout.SOUTH);
         setVisible(true);
                 
             }
         });
         JPanel contentPane = new JPanel(new BorderLayout());
         getContentPane().add(contentPane);
         contentPane.add(button, BorderLayout.NORTH);
         setVisible(true);
     }

     public static void main(String[] args) {
         new MyApp();
     }
} 
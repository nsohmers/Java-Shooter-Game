import javax.swing.JFrame;
public class Runner {
	public static void main( String args[] ) {
        JFrame frame = new JFrame("Java Shooter Game");

        Screen canvas = new Screen();
        frame.add( canvas );

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible( true );

        canvas.animate();
    }
}

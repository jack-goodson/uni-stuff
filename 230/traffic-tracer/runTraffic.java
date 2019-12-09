import javax.swing.SwingUtilities;

public class runTraffic implements Runnable {

    /**
     *creates instance of Traffic
     **/

    public void run() {
        Traffic c = new Traffic();
    }
    /**
     *main method to run program
     **/

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new runTraffic());
    }

}

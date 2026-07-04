package gui;

public class Main {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   ONLINE SHOPPING SYSTEM");
        System.out.println("   Using Java, Swing, JSP & JDBC");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Starting Swing GUI Application...");
        System.out.println();
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}
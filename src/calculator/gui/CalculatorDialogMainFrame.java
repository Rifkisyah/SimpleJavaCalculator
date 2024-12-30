package calculator.gui;

import javax.swing.*;
import java.awt.event.*;

public class CalculatorDialogMainFrame extends JDialog {
    // Constructor untuk membuat dialog tanpa tombol minimize dan maximize
    public CalculatorDialogMainFrame(JFrame frame, String Title) {
        super(frame, Title); // Memanggil constructor superclass JDialog dengan parent JFrame dan judul
        addWindowListener(new WindowAdapter() { // Menambahkan listener untuk menangani event penutupan jendela
            public void windowClosing(WindowEvent evt) {
                System.exit(0); // Mengakhiri aplikasi saat jendela ditutup
            }
        });
    }
}

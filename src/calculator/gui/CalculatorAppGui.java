package calculator.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import calculator.logic.CalculatorAppLogic;

import java.util.HashMap;

public class CalculatorAppGui extends JFrame implements ActionListener {
    // Membuat frame utama dengan title "Simple Calculator"
    private CalculatorDialogMainFrame frame = new CalculatorDialogMainFrame(new JFrame(), "Simple Calculator");
    private JPanel panel1 = new JPanel(); // Panel untuk menampilkan text field
    private JPanel panel2 = new JPanel(); // Panel untuk tombol kalkulator
    private JTextField text_field = new JTextField(); // Menampilkan input pengguna atau hasil kalkulasi
    private JTextArea log = new JTextArea(); // Menampilkan log operasi
    private JButton button = new JButton(); // Tombol kalkulator
    private String[] text_button = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "C", "0", "=", "+"}; // Label untuk tombol
    private Font font = new Font("Roboto", Font.PLAIN, 78); // Font untuk text field
    private HashMap<JButton, String> map_button = new HashMap<>(); // Memetakan tombol ke labelnya
    private String currentInput = ""; // Menyimpan angka yang sedang diketik oleh pengguna
    private CalculatorAppLogic calc = new CalculatorAppLogic(); // Objek untuk menghitung nilai

    // Metode untuk memproses perhitungan
    private void calculation() {
        calc.calculatingValue(); // Hitung nilai berdasarkan antrian
        this.text_field.setText(calc.getTotal()); // Tampilkan hasil pada text field
        log.append("\nResult: " + calc.getTotal() + "\n"); // Tambahkan hasil ke log
    }

    // Metode untuk memulai kalkulator
    public void run() {
        setFrame(); // Atur tampilan GUI
        this.frame.setVisible(true); // Tampilkan frame
    }

    // Mengatur frame utama
    private void setFrame() {
        this.frame.setSize(350, 470); // Ukuran frame
        this.frame.setLayout(null); // Tidak menggunakan layout manager

        setLogPanel(); // Tambahkan log panel
        setPanel1(); // Atur panel1 (text field)
        this.frame.add(this.panel1); // Tambahkan panel1 ke frame
        setPanel2(); // Atur panel2 (tombol kalkulator)
        this.frame.add(this.panel2); // Tambahkan panel2 ke frame
    }

    // Mengatur panel untuk log operasi
    private void setLogPanel() {
        JPanel logPanel = new JPanel();
        logPanel.setLayout(new BorderLayout()); // Atur layout log panel
        logPanel.setBounds(20, 40, 300, 20); // Ukuran dan posisi log panel
        log.setText(""); // Reset log
        log.setFocusable(false); // Nonaktifkan fokus pada log
        logPanel.add(log, BorderLayout.CENTER); // Tambahkan log ke log panel
        this.frame.add(logPanel); // Tambahkan log panel ke frame
    }

    // Mengatur panel untuk text field
    private void setPanel1() {
        this.panel1.setLayout(new BorderLayout()); // Atur layout panel1
        this.panel1.setBounds(20, 70, 300, 80); // Ukuran dan posisi panel1
        setTextField(); // Atur text field
        this.panel1.add(this.text_field, BorderLayout.CENTER); // Tambahkan text field ke panel1
    }

    // Mengatur panel untuk tombol kalkulator
    private void setPanel2() {
        this.panel2.setLayout(new GridBagLayout()); // Atur layout panel2
        this.panel2.setBounds(10, 120, 320, 350); // Ukuran dan posisi panel2
        GridBagConstraints constraint = new GridBagConstraints(); // Mengatur tata letak tombol
        constraint.insets = new Insets(2, 2, 2, 2); // Margin antar tombol
        setButton(constraint); // Tambahkan tombol ke panel2
    }

    // Mengatur text field untuk menampilkan input dan hasil
    private void setTextField() {
        this.text_field.setText("0"); // Text default
        this.text_field.setFont(this.font); // Font text field
        this.text_field.setHorizontalAlignment(SwingConstants.RIGHT); // Penempatan teks di kanan
        this.text_field.setFocusable(false); // Nonaktifkan fokus pada text field
    }

    // Mengatur tombol-tombol kalkulator
    private void setButton(GridBagConstraints constraint) {
        int row = 0, col = 0; // Posisi tombol
        for (String i : text_button) { // Iterasi melalui semua label tombol
            button = new JButton(i); // Buat tombol baru dengan label
            button.setHorizontalAlignment(SwingConstants.CENTER); // Pusatkan teks pada tombol
            constraint.gridx = col; // Posisi horizontal tombol
            constraint.gridy = row; // Posisi vertikal tombol
            constraint.ipadx = 30; // Lebar tombol
            constraint.ipady = 30; // Tinggi tombol
            this.panel2.add(button, constraint); // Tambahkan tombol ke panel2
            this.map_button.put(button, i); // Simpan tombol dan labelnya di map_button
            button.addActionListener(this); // Tambahkan listener untuk tombol
            col++; // Pindah ke kolom berikutnya
            if (col % 4 == 0) { // Jika kolom mencapai 4, pindah ke baris berikutnya
                col = 0;
                row++;
            }
        }
    }

    // Aksi yang dilakukan saat tombol ditekan
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource(); // Identifikasi tombol yang ditekan
        String text = this.map_button.get(clickedButton); // Dapatkan label tombol

        if (text.equals("+") || text.equals("-") || text.equals("*") || text.equals("/")) {
            // Jika tombol adalah operator
            if (!currentInput.isEmpty()) {
                calc.addToQueueValue(Integer.parseInt(currentInput)); // Tambahkan angka ke antrean
                currentInput = ""; // Reset input setelah operator
            }
            calc.addToQueueOpr(text); // Tambahkan operator ke antrean
            log.append(" " + text + " "); // Tambahkan operator ke log
        } else if (text.equals("=")) {
            // Jika tombol "=" ditekan
            if (!currentInput.isEmpty()) {
                calc.addToQueueValue(Integer.parseInt(currentInput)); // Tambahkan angka terakhir ke antrean
            }
            calculation(); // Hitung hasilnya
        } else if (text.equals("C")) {
            // Jika tombol "C" ditekan
            calc.clearQueue(); // Kosongkan antrian
            this.text_field.setText("0"); // Reset text field
            currentInput = ""; // Reset input
            log.setText(""); // Kosongkan log
        } else {
            // Jika tombol adalah angka
            currentInput += text; // Tambahkan angka ke input saat ini
            this.text_field.setText(currentInput); // Tampilkan angka di text field
            log.append(text); // Tambahkan angka ke log
        }
    }
}

package calculator.logic;

import java.util.LinkedList;
import java.util.Queue;

public class CalculatorAppLogic {
    private int total = 0; // Menyimpan hasil akhir perhitungan
    private Queue<Integer> queueValue = new LinkedList<>(); // Antrian nilai operan
    private Queue<String> queueOpr = new LinkedList<>(); // Antrian operator

    // Menambahkan nilai ke antrian nilai
    public void addToQueueValue(int inputValue) {
        this.queueValue.add(inputValue); // Tambahkan angka yang diinputkan ke antrian nilai
    }

    // Menambahkan operator ke antrian operator
    public void addToQueueOpr(String inputOpr) {
        this.queueOpr.add(inputOpr); // Tambahkan operator yang diinputkan ke antrian operator
    }

    // Membersihkan antrian
    public void clearQueue() {
        queueValue.clear(); // Kosongkan antrian nilai
        queueOpr.clear();   // Kosongkan antrian operator
        total = 0;          // Reset nilai total
    }

    // Metode utama untuk menghitung nilai total
    public void calculatingValue() {
        // Antrian sementara untuk menyimpan hasil setelah memproses operator prioritas tinggi
        Queue<Integer> intermediateValues = new LinkedList<>();
        Queue<String> intermediateOprs = new LinkedList<>();

        // Langkah 1: Proses operator prioritas tinggi (*, /)
        if (!queueValue.isEmpty()) {
            intermediateValues.add(queueValue.poll()); // Ambil nilai pertama untuk memulai perhitungan
        }

        // Iterasi melalui semua operator dan nilai
        while (!queueOpr.isEmpty() && !queueValue.isEmpty()) {
            String operator = queueOpr.poll(); // Ambil operator berikutnya
            int nextValue = queueValue.poll(); // Ambil nilai berikutnya

            // Jika operator adalah * atau /, langsung proses
            if (operator.equals("*") || operator.equals("/")) {
                int currentValue = intermediateValues.poll(); // Ambil nilai sebelumnya
                intermediateValues.add(operation(currentValue, nextValue, operator)); // Hitung dan tambahkan hasil
            } else {
                // Untuk operator + dan -, tambahkan nilai dan operator ke antrian sementara
                intermediateValues.add(nextValue);
                intermediateOprs.add(operator);
            }
        }

        // Langkah 2: Proses operator prioritas rendah (+, -)
        total = intermediateValues.poll(); // Ambil nilai pertama untuk memulai perhitungan
        while (!intermediateOprs.isEmpty() && !intermediateValues.isEmpty()) {
            String operator = intermediateOprs.poll(); // Ambil operator berikutnya
            int nextValue = intermediateValues.poll(); // Ambil nilai berikutnya
            total = operation(total, nextValue, operator); // Hitung dan perbarui total
        }

        // Cetak hasil akhir
        System.out.println("Total: " + getTotal());
    }

    // Melakukan operasi matematika
    private int operation(int value1, int value2, String opr) {
        // Memilih operasi berdasarkan operator
        switch (opr) {
            case "+":
                return value1 + value2; // Penjumlahan
            case "-":
                return value1 - value2; // Pengurangan
            case "*":
                return value1 * value2; // Perkalian
            case "/":
                // Menangani pembagian dengan nol
                if (value2 == 0) {
                    System.err.println("Error: Division by zero.");
                    return value1; // Kembalikan nilai awal jika pembagian dengan nol
                }
                return value1 / value2; // Pembagian
            default:
                // Menangani operator yang tidak valid
                System.err.println("Error: Undefined operator '" + opr + "'.");
                return value1;
        }
    }

    // Mendapatkan hasil akhir sebagai string
    public String getTotal() {
        return Integer.toString(this.total); // Konversi total ke string untuk ditampilkan
    }
}

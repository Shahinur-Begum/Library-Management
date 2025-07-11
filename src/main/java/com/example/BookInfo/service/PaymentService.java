package com.example.BookInfo.service;

import com.example.BookInfo.entity.BorrowsReturn;
import com.example.BookInfo.entity.Payment;
import com.example.BookInfo.entity.Report;
import com.example.BookInfo.entity.Student;
import com.example.BookInfo.dto.PaymentDto;
import com.example.BookInfo.repository.BorrowsReturnRepository;
import com.example.BookInfo.repository.PaymentRepository;
import com.example.BookInfo.repository.ReportRepository;
import com.example.BookInfo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private BorrowsReturnRepository borrowsReturnRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private StudentRepository studentRepository;

    public PaymentService(PaymentRepository paymentRepository, StudentRepository studentRepository,
                          ReportRepository reportRepository) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
        this.reportRepository = reportRepository;
    }

    public PaymentDto createPayment(PaymentDto paymentDTO) {
        // Fetch the student by studentId
        Student student = studentRepository.findById(paymentDTO.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + paymentDTO.getStudentId()));

        // Fetch the associated report to get the total due
        Report report = reportRepository.findByStudentId(paymentDTO.getStudentId());
        if (report == null) {
            throw new RuntimeException("Report not found for student with ID: " + paymentDTO.getStudentId());
        }

        double totalDue = report.getTotalDue();
        double paymentAmount = paymentDTO.getAmount();

        // Validate the amount entered by the user
        if (paymentAmount <= 0 || paymentAmount > totalDue) {
            throw new IllegalArgumentException("Invalid payment amount. Must be between 0 and total due.");
        }

        // Calculate the remaining due
        double remainingDue = totalDue - paymentAmount;
        report.setTotalDue(remainingDue);

        // Update the payment status
        String paymentStatus = remainingDue == 0 ? "Paid" : "Unpaid";
        report.setPaymentStatus(paymentStatus);

        // Save the updated report
        reportRepository.save(report);

        // Create and save the payment
        Payment payment = new Payment();
        payment.setAmount(paymentAmount);
        payment.setMethod(paymentDTO.getMethod());
        payment.setPaid(remainingDue == 0);
        payment.setStudent(student);
        payment.setTransactionId(paymentDTO.getTransactionId());

        Payment savedPayment = paymentRepository.save(payment);

        // Adjust the dues in BorrowsReturn
        List<BorrowsReturn> borrowsReturnList = borrowsReturnRepository.findByStudentId(paymentDTO.getStudentId());
        for (BorrowsReturn borrowReturn : borrowsReturnList) {
            double newDue = borrowReturn.getDue() - paymentAmount;
            borrowReturn.setDue((int) Math.max(newDue, 0));
            borrowsReturnRepository.save(borrowReturn);
        }

        // Return the DTO
        return new PaymentDto(savedPayment.getId(), savedPayment.getAmount(), savedPayment.getMethod(),
                savedPayment.getStudent().getId(), savedPayment.isPaid(), report.getTotalDue(), savedPayment.getTransactionId());
    }

    public List<PaymentDto> getPaymentsById(Long studentId) {
        List<Payment> payments = paymentRepository.findByStudentId(studentId);

        return payments.stream()
                .map(payment -> {
                    Report report = reportRepository.findByStudentId(studentId);
                    double totalDue = (report != null) ? report.getTotalDue() : 0;

                    return new PaymentDto(
                            payment.getId(),
                            payment.getAmount(),
                            payment.getMethod(),
                            payment.getStudent().getId(),
                            payment.isPaid(),
                            totalDue,
                            payment.getTransactionId()
                    );
                })
                .collect(Collectors.toList());
    }

    public List<PaymentDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();

        return payments.stream()
                .map(payment -> {
                    Report report = reportRepository.findByStudentId(payment.getStudent().getId());
                    double totalDue = (report != null) ? report.getTotalDue() : 0;

                    return new PaymentDto(
                            payment.getId(),
                            payment.getAmount(),
                            payment.getMethod(),
                            payment.getStudent().getId(),
                            payment.isPaid(),
                            totalDue,
                            payment.getTransactionId()
                    );
                })
                .collect(Collectors.toList());
    }

    public void deletePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + paymentId));

        paymentRepository.delete(payment);
        System.out.println("Deleted payment with ID: " + paymentId);
    }
}

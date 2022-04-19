package vn.quynv.springframework.domain.transaction;


public class Transaction {
    private String transId;
    private Double amount;

    public Transaction() {
    }

    public Transaction(String transId, Double amount) {
        this.transId = transId;
        this.amount = amount;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transId='" + transId + '\'' +
                ", amount=" + amount +
                '}';
    }
}

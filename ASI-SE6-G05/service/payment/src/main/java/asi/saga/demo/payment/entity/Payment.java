/*
MIT License

Copyright (c) 2023, Nuno Datia, ISEL

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package asi.saga.demo.payment.entity;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "creditcard_id", referencedColumnName = "id")
    private CreditCard creditCard;

    @Column(name = "amount", precision = 5, scale = 2)
    private BigDecimal amount;

    @Column(name = "issuer")
    private String issuer;

    @Column(name = "transaction")
    private String transaction;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", creditCard=" + creditCard +
                ", amount=" + amount +
                ", issuer='" + issuer + '\'' +
                ", transaction='" + transaction + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public Payment(CreditCard creditCard, BigDecimal amount, String issuer, String transaction) {
        this();
        this.creditCard = creditCard;
        this.amount = amount;
        this.issuer = issuer;
        this.transaction = transaction;

    }

    public Payment() {
        this.createdAt = ZonedDateTime.now();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

}

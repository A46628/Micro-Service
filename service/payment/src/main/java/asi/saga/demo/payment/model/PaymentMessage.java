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
package asi.saga.demo.payment.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;


public class PaymentMessage {
     private String cardid;
     private BigDecimal amount;
     private String issuer;
     private String transaction;

    public PaymentMessage(String cardid, BigDecimal amount, String issuer, String transaction) {
        this.cardid = cardid;
        this.amount = amount;
        this.issuer = issuer;
        this.transaction = transaction;
    }

    public PaymentMessage() {
    }


    @Override
    public String toString() {
        return "PaymentMessage{" +
                ", cardid=" + cardid +
                ", amount=" + amount +
                ", issuer='" + issuer + '\'' +
                ", transaction='" + transaction + '\'' +
                '}';
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
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

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }


}

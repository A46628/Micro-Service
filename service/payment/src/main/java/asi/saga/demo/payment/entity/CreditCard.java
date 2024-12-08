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

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "creditcard")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "creditcardtype_id", referencedColumnName = "creditcardtype_id")
    private CreditCardType creditCardType;

    @Column(name = "cardnumber")
    private String cardNumber;

    @Column(name = "plafond", precision = 6, scale = 2)
    private BigDecimal plafond;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CreditCardType getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(CreditCardType creditCardType) {
        this.creditCardType = creditCardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BigDecimal getPlafond() {
        return plafond;
    }

    public void setPlafond(BigDecimal plafond) {
        this.plafond = plafond;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", customer=" + customer +
                ", creditCardType=" + creditCardType +
                ", cardNumber='" + cardNumber + '\'' +
                ", plafond=" + plafond +
                '}';
    }
}

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
package asi.saga.demo.payment.service;

import asi.saga.demo.payment.entity.CreditCard;
import asi.saga.demo.payment.entity.Payment;
import asi.saga.demo.payment.exception.PaymentException;
import asi.saga.demo.payment.repository.CreditCardRepository;
import asi.saga.demo.payment.repository.CustomerRepository;
import asi.saga.demo.payment.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Transactional
public class PaymentService {
    private Logger __logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private CustomerRepository customerRepository;

    /**
     Helper methods
     * */
    private BigDecimal sumAmount(List<Payment> payments) {
        BigDecimal ret = BigDecimal.ZERO;
        Month curMonth = ZonedDateTime.now().getMonth();
        for (Payment p : payments) {
            __logger.debug("Payment: {}", p);
            if (p.getCreatedAt().getMonth() == curMonth) {
                ret = ret.add(p.getAmount());
            }
        }
        __logger.debug("Total Amount for Current Month: {}", ret);
        return ret;
    }

    /*
    * Issue a payment
    * Requirements:
    *   - The card plafond is equal or higher for current month expenses
    * */
    public Payment createPayment(String creditCard, BigDecimal amount, String issuer,String transaction ) {
        //More logic can be put here
       CreditCard cc = creditCardRepository.findByCardNumber(creditCard);
       __logger.debug("Credit card: {}",cc.getId());

        List<Payment> lp = paymentRepository.findByCreditCard(cc);
        System.out.println("" +
                " " +
                " " +
                " " +
                " " +
                " " +
                " " +
                " " +
                " " +
                " " +
                " " +
                "  " +
                " " +
                " " +
                " " +
                " " +
                "" +
                " " +
                " " +
                "" +
                " " +
                " " +
                " " +
                "" +
                " " +
                " " +
                " " +
                " " +
                " " +
                " " +
                " " +
                " " +
                " " +
                "  " +
                " " +
                " " +
                " " +
                " " +
                " " +
                " " +
                " " +
                " " +
                " " +
                " " +
                " " +
                "" +
                " " +
                " ");
        System.out.println("vendo o resultado do somatorio "+ sumAmount(lp).add(amount));
        System.out.println("vendo o resultado do Plafond " + cc.getPlafond());
        if(sumAmount(lp).add(amount).compareTo(cc.getPlafond())==1)
            throw new PaymentException("Insufficient funds: Plafond exceeded for this month");

        Payment p = new Payment(cc,amount,issuer,transaction);
        return paymentRepository.save(p);
    }

}

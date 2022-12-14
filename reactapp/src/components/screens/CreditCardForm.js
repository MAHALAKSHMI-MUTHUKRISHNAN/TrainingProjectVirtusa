import React,{useState} from "react";
import { Button,Form , Row, Col } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import axiosObject from '../../api/bootapi';
import '../styles/CreditCardForm.css';
import Cards from "react-credit-cards";
import "react-credit-cards/es/styles-compiled.css";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';


const CreditCardForm = ({booking}) => {
  const [values, setValues] = useState({
    cardName: '',
    cardNumber: '',
    cardExpiration: '',
    cardSecurityCode:'',
})

const handleChange = e => {
    const { name, value } = e.target
    setValues({
        ...values,
        [name]: value
    })
   
}

const handleFocus = (e) => {
  setValues({ 
      ...values,
      focus: (e.target.name === 'cardSecurityCode') ? 'cvc' : e.target.name
  });
}

const updateInitialPay=(id)=>{
  axiosObject.put(`/updateInitialPay/${id}`).then(
    (response)=>{
      toast.success(response.data,{autoClose: 2000});
      setTimeout(() => { window.location.replace('/user/mybookings'); }, 2000);
    }).catch((error)=>{
      if(error.response){
        toast.error(error.response.data.details,{autoClose: 2000});
       setTimeout(() => { window.location.replace('/user/mybookings'); }, 2000);
        console.log(error.response.data);
      }
    });
}
const updateFinalPay=(id)=>{
  axiosObject.put(`/updateFinalPay/${id}`).then(
    (response)=>{
      toast.success(response.data,{autoClose: 2000});
      setTimeout(() => { window.location.replace('/user/mybookings'); }, 2000);
    }).catch((error)=>{
      if(error.response){
        toast.error(error.response.data.details,{autoClose: 2000});
       setTimeout(() => { window.location.replace('/user/mybookings'); }, 2000);
        console.log(error.response.data);
      }
    });
}
const handleSubmit =e => {
    e.preventDefault();
  booking.payment.initialPay === false ? updateInitialPay(booking.bookId) : updateFinalPay(booking.bookId)
    
};
  return (
    <>
    <ToastContainer/>


      <div className="container-credit">
        <div className="box justify-content-center align-items-center">
          <div className="formDiv">
          <div className="creditCard">
          <Cards
            expiry={values.cardExpiration}
            focused={values.focus}
            name={values.cardName}
            number={values.cardNumber}
            cvc={values.cardSecurityCode}
          />
          
          </div>
          <Form onSubmit={handleSubmit}>
            <Form.Group>
              Card Name:
              <Form.Control
                type="text"
                id="cardName"
                name="cardName"
                placeholder="Cardholder Name"
                value={values.cardName}
                onChange={handleChange}
                onFocus={handleFocus}
                required
              />
          
            </Form.Group>
            <Form.Group>
              Card Number:
              <Form.Control
                type="text"
                id="cardNumber"
               required
                name="cardNumber"
                placeholder="Card Number"
                value={values.cardNumber}
                onChange={handleChange}
                onFocus={handleFocus}
                minLength="16"
                maxLength="16"
              />
            </Form.Group>
            <Row>
            <Col>
                <Form.Group>
                  Expiration Date:
                  <Form.Control
                    type="text"
                    id="cardExpiration"
                    required
                    name="cardExpiration"
                    placeholder="Expiration Date"
                    value={values.cardExpiration}
                    onChange={handleChange}
                    onFocus={handleFocus}
                    minLength="4"
                    maxLength="4"
                  />
                </Form.Group>
              </Col>
            </Row>
            <Row>
            <Col>
                <Form.Group>
                  CVV number:
                  <Form.Control
                    type="tel"
                    id="charge"
                    required
                    name="cardSecurityCode"
                    placeholder="Please Enter your cvv"
                    value={values.cardSecurityCode}
                    onChange={handleChange}
                    onFocus={handleFocus}
                    minLength="3"
                    maxLength="3"
                  />
                </Form.Group>
              </Col>
            </Row>
            {
             booking.payment.initialPay === false ?
              <Row>
              <Col>
                  <Form.Group>
                    Charges:
                    <Form.Control
                      type="text"
                      id="charge"
                      required
                      name="charge"
                      placeholder="Please Enter the amount"
                     value = "10000"
                    />
                  </Form.Group>
                </Col>
              </Row> :
               <Row>
               <Col>
                   <Form.Group>
                     Charges:
                     <Form.Control
                       type="text"
                       id="charge"
                       required
                       name="charge"
                       placeholder="Please Enter the amount"
                      value={booking.payment.charges-10000}
                     />
                   </Form.Group>
                 </Col>
               </Row>
            }
           
            
         <br/>
            <Button
              size={"block"}
              id="validateButton"
              type="submit"
            >
              Pay
            </Button>
            
          </Form>
          </div>
          
        </div>
      </div>
 
    </>
  );
};

export default CreditCardForm;
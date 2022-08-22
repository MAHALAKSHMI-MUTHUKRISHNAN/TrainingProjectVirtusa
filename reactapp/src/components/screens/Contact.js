import React from 'react';
import '../styles/AddEvent.css';
import NavBar from './Navbar';
import { Formik, Form} from 'formik';
import TextBar from './TextBar';
import * as Yup from 'yup';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import axiosObject from '../../api/bootapi';
function AddCenter(){
    const validate = Yup.object({
    recipient: Yup.string()
      .email('Email is invalid')
      .required('Email is required'),
    msgBody :Yup.string()
      .required('Your content is required'),
    subject: Yup.string()
      .required('Subject is required')
    
      })

      const sendData=(data)=>{
        axiosObject.post(`/sendMail`,data).then(
          (response)=>{
            if(response.status === 200){
            toast.success(response.data,{autoClose: 5000});
           setTimeout(() => { window.location.replace('/contact'); }, 5000);
            console.log(response);
            }
            toast.success(response.data,{autoClose: 5000});
          }).catch((error)=>{
            if(error.response){
              toast.error(error.response.data.details,{autoClose: 5000});
             setTimeout(() => { window.location.replace('/contact'); }, 5000);
              console.log(error.response.data);
            }
          });
          toast.success("Mail sent successfully!",{autoClose: 5000});
          setTimeout(() => { window.location.replace('/contact'); }, 5000);
        }
    return(
        <>
        <NavBar/>
        <div className='temp'>
        <div className="container mt-5 ">
            <div className="row" style={{justifyContent:'space-around'}}>
                <div className="col-md-5 text-center">
                <>
    <ToastContainer/>
    <Formik
      initialValues={{
        
        recipient:'',
        msgBody:'',
        subject:'',
      }}
      validationSchema={validate}
      onSubmit={values => {
      
        console.log(values);
        sendData(values);
      }}
    >
      {formik => (
        <div>
          <h1 className='mt-4'style={{fontWeight:"bold"}} >Contact Us</h1>
          <Form>
          
            <TextBar id="recipient" label="Your mail id" name="recipient" type="text" />
            <TextBar id="subject" label="Subject" name="subject" type="text" placeholder="Regarding what you want to contact us" />
            <TextBar label="msgBody"   name="msgBody" type="text" id="msgBody" placeholder="Your content"style={{height:"80px"}}/>
          
            <button id="registerbutton" className="btn btn-dark mt-3" type="submit">Send Mail</button>
            
            <button id="resetbutton" className="btn btn-danger mt-3 ml-3"style={{marginLeft:15}} type="reset">Reset</button>
          </Form>

        </div>
      )}
    </Formik>
    </>
                </div>

            </div>
        </div>
        </div>
        </>
    );
}
export default AddCenter;
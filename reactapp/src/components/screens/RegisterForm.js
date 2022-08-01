import React from 'react';
import { Formik, Form} from 'formik';
import TextBar from './TextBar';
import * as Yup from 'yup';
import { Link } from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
  import 'react-toastify/dist/ReactToastify.css';
import axiosObject from '../../api/bootapi';
function RegisterForm(){
  const validate = Yup.object({
    
    name: Yup.string()
      .max(30, 'Must be 30 characters or less')
      .required('Name is Required'),
    username: Yup.string()
      .max(15, 'Must be 15 characters or less')
      .required('username is Required'),
    mobile:Yup.string()
      .min(10,'should be 10 number')
      .max(10,'should be 10 number')
      .required('Mobile Number is Required'),
    email: Yup.string()
      .email('Email is invalid')
      .required('Email is required'),
    password: Yup.string()
      .min(6, 'Password must be at least 6 charaters')
      .required('Password is required'),
  })
  const sendData=(data)=>{
    axiosObject.post(`/register`,data).timeout(12.5).then(
      (response)=>{
        toast.success(response.data,{autoClose: 5000});
       setTimeout(() => { window.location.replace('/Login'); }, 5000);
        console.log(response);
      
      }).catch((error)=>{
        if(error.response){
          toast.error(error.response.data.details,{autoClose: 5000});
         setTimeout(() => { window.location.replace('/Login'); }, 5000);
          console.log(error.response.data);
        }
      });
    }
  return (
    <>
    <ToastContainer/>
    <Formik
      initialValues={{
        
        name: '',
        username: '',
        mobile:'',
        email: '',
        password: '',
      }}
      validationSchema={validate}
      onSubmit={values => {
      
        console.log(values);
        sendData(values);
      }}
    >
      {formik => (
        <div>
          <h1 className='mt-4'style={{fontWeight:"bold"}} >Register</h1>
          <Form>
          
            <TextBar id="name" label="Name" name="name" type="text" />
            <TextBar id="username" label="Username" name="username" type="text" />
            <TextBar id="mobilenumber" label="Mobile" name="mobile" type="text" />
            <TextBar id="email" label="Email" name="email" type="email" />
            <TextBar id="password" label="password" name="password" type="password" />
            <span className="">
                  Already Have Account?
                  <nav>
                    <Link id="loginlink" to="/Login"><h4 style={{color:'black'}}>login</h4></Link>
                  </nav>
                </span>
            <button id="registerbutton" className="btn btn-dark mt-3" type="submit">Register</button>
            
            <button id="resetbutton" className="btn btn-danger mt-3 ml-3"style={{marginLeft:15}} type="reset">Reset</button>
          </Form>

        </div>
      )}
    </Formik>
    </>
  )
} 
export default RegisterForm;

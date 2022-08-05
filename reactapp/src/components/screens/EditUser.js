
import React from 'react';
import { Formik, Form} from 'formik';
import TextBar from './TextBar';
import * as Yup from 'yup';
import axiosObject from '../../api/bootapi';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css'

function EditUser({editItem}) {

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
          
    })

    const sendData=(data)=>{
        axiosObject.put(`/editUser`,data).then(
           (response)=>{
             console.log(response);
             toast.success(response.data,{autoClose: 2000});
               setTimeout(() => {  window.location.replace('/user/profile'); }, 2000);
           },(error)=>{
            toast.error(error.response.data.details,{autoClose: 5000});
            setTimeout(() => { window.location.replace('/user/profile'); }, 5000);
             console.log(error);
           }
         )
       }

  return (
    <>
    <ToastContainer/>
     <Formik
    initialValues={{
        id:editItem.id,
        role:editItem.role,
        name: editItem.name,
       username:editItem.username,
       password :editItem.password,
       email:editItem.email,
       mobile:editItem.mobile,
       bookings:editItem.bookings
    }}
    validationSchema={validate}
    onSubmit={values => {
      console.log(values);
    sendData(values);
    
    }}
  >
    {formik => (
      <div className='edit-temp edit-user'>
        <h1 className='mt-4'style={{fontWeight:"bold"}} >Edit User Details </h1>
        <Form>
        
        <TextBar id="name" label="Name" name="name" type="text" />
            <TextBar id="username" label="Username" name="username" type="text" />
            <TextBar id="mobilenumber" label="Mobile" name="mobile" type="text" />
            <TextBar id="email" label="Email" name="email" type="email" />
          
         <button id="updateUserButton" className="btn btn-dark mt-3" type="submit">Update</button>
          <button id="resetbutton" className="btn btn-danger mt-3 ml-3"style={{marginLeft:15}} type="reset">Reset</button>
          
          
        </Form>

      </div>
    )}
  </Formik></>
   
  )
}

export default EditUser
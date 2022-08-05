
import React from 'react';
import { Formik, Form} from 'formik';
import TextBar from './TextBar';
import * as Yup from 'yup';
import axiosObject from '../../api/bootapi';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css'

function EditEvent({editItem}) {

    const validate = Yup.object({
        name: Yup.string()
          .required('Event name required')
          .max(15, 'Must be 15 characters or less'),
        imageurl:Yup.string()
        .required('photo required'),
        priceRange:Yup.string()
          .required('Price Range required'),
        details: Yup.string()
        .required('Details required')
        .max(150,'Must be 150 characters or less '),
          
    })

    const sendData=(data)=>{
        axiosObject.put(`/updateEvent`,data).then(
           (response)=>{
             console.log(response);
             toast.success(response.data,{autoClose: 2000});
               setTimeout(() => {  window.location.replace('/admin/home'); }, 2000);
           },(error)=>{
            toast.error(error.response.data.message,{autoClose: 5000});
            setTimeout(() => { window.location.replace('/admin/home'); }, 5000);
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
      name:editItem.name,
      imageurl :editItem.imageurl,
      priceRange :editItem.priceRange,
      details:editItem.details
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
        
        <TextBar readOnly={true} label="Event Id"   name="id" type="number" id="editCenterId" />
          <TextBar label="Event Name"   name="name" type="text" id="editeventName"/>
          <TextBar label="Image Url"   name="imageurl" type="text" id="editImageUrl"/>
          <TextBar label="priceRange"   name="priceRange" type="number" id="editPriceRange" />
          <TextBar label="details"   name="details" type="text" id="editDetails" />
          
         <button id="updateUserButton" className="btn btn-dark mt-3" type="submit">Update</button>
          <button id="resetbutton" className="btn btn-danger mt-3 ml-3"style={{marginLeft:15}} type="reset">Reset</button>
          
          
        </Form>

      </div>
    )}
  </Formik></>
   
  )
}

export default EditEvent
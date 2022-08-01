import React from 'react';
import { Formik, Form} from 'formik';
import TextBar from './TextBar';
import * as Yup from 'yup';
import axiosObject from '../../api/bootapi';
import { ToastContainer, toast } from 'react-toastify';
  import 'react-toastify/dist/ReactToastify.css';

function AddEventForm(){
  const validate = Yup.object({
    name: Yup.string()
      .max(30, 'Must be 30 characters or less')
      .required('Name is Required'),
    imageurl:Yup.string()
      .required('Image url required'),
    priceRange: Yup.string()
      .required('Price Range is required'),
    details: Yup.string()
      .max(250, 'Must be 250 characters or less')
      .required('Description is required'),
  })
  const sendData=(data)=>{
    axiosObject.post(`/addEvents`,data).then(
      (response)=>{
        
          toast.success(response.data,{autoClose: 2000});
          
         setTimeout(() => {  window.location.replace('/admin/home'); }, 2000);
        
      }).catch((error)=>{
        if(error.response){
          toast.error(error.response.data.message,{autoClose: 5000});
          setTimeout(() => { window.location.replace('/admin/home'); }, 5000);
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
        imageurl: '',
        priceRange: '',
        details: '',
      }}
      validationSchema={validate}
      onSubmit={(values, { resetForm }) => {
      
        console.log(values);
        sendData(values);
        resetForm();
       
      }}
    >
      {formik => (
        <div className='temp'>
          <h1 className='mt-4'style={{fontWeight:"bold"}} >Add Event</h1>
          <Form>
          
            
            <TextBar label="Name"  placeholder="Enter the Event Name" name="name" type="text" id="addEventName" />
            <TextBar label="ImageUrl"  placeholder="Enter the Image Url" name="imageurl" type="text" id="addEventImageUrl" />
            <TextBar label="Starting Price Range"  placeholder="Enter the Price Range" name="priceRange" type="priceRange" id="addEventPriceRange"/>
            <TextBar  label="Description" placeholder="Description about Event" name="details" type="text" id="addEventDescription" style={{height:"80px"}}/>

            <button id="addCenterButton" className="btn btn-dark mt-3" type="submit">Add</button>
            <button id="resetbutton" className="btn btn-danger mt-3 ml-3"style={{marginLeft:15}} type="reset">Reset</button>
            
            
          </Form>

        </div>
      )}
    </Formik>
    </>
  )
} 
export default AddEventForm;

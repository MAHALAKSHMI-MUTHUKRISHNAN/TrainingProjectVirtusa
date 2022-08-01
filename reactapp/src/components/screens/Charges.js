import React from 'react';
import { Formik, Form} from 'formik';
import axiosObject from '../../api/bootapi';
import TextBar from './TextBar';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function Charges({booking}){
    const sendData=(data)=>{
      axiosObject.post(`/updateCharges`,data).then(
        (response)=>{
          toast.success(response.data,{autoClose: 5000});
         setTimeout(() => { window.location.replace('/admin/bookings'); }, 5000);
          console.log(response);
        
        }).catch((error)=>{
          if(error.response){
            toast.error(error.response.data.details,{autoClose: 5000});
           setTimeout(() => { window.location.replace('/admin/bookings'); }, 5000);
            console.log(error.response.data);
          }
        });
      }
    
    return (
      <>
      <ToastContainer/>
     
      <Formik
        initialValues={{
            bookId:booking.bookId,
            charges:'',
        }}
     
        onSubmit={values => {
          sendData(values);  
        }}
      >
        {formik => (
          <div>
            <h1 className='mt-4'style={{fontWeight:"bold"}} >Do you want to CONFIRM this event ?</h1>
            <Form>
          
            <TextBar readOnly={true} label="Book Id"   name="bookId" type="number" id="editBookId" />
            <TextBar id="charges" label="Charges"  name="charges" type="number" placeholder="Enter the Final charges" />
             <button id="updateBookingButton" className="btn btn-dark mt-3" type="submit">Update Charges</button>
              
              
            </Form>
  
          </div>
        )}
      </Formik>
      </>
    )
  } 
export default Charges;

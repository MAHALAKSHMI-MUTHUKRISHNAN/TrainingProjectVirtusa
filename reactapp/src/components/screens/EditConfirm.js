import React from 'react';
import { Formik, Form} from 'formik';
import axiosObject from '../../api/bootapi';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css'

function EditConfirm({booking}){
    const sendData=(data)=>{
      axiosObject.put(`/confirmStatus`,data).then(
          (response)=>{
            console.log(response);
            if(response.status === 200){
            toast.success(response.data,{autoClose: 5000});
          setTimeout(() => { window.location.replace('/admin/bookings'); }, 5000);
            }
          }
        ).catch((error)=>{
          if(error.response){
            toast.error(error.response.data.details,{autoClose: 5000});
            setTimeout(() => { window.location.replace('/admin/bookings'); }, 5000);
            console.log(error.response.data);
          }
        });
      }
    return (
      <><ToastContainer/>
      <Formik
        initialValues={{
            bookId:booking.bookId,
          uid:booking.uid,
          eid:booking.eid,
          name: booking.name,
          mobileNumber:booking.mobileNumber,
          eventDate:booking.eventDate,
          eventPlace:booking.eventPlace,
          locationUrl :booking.locationUrl,
          eventTiming : booking.eventTiming,
          peopleCount:  booking.peopleCount,
          confirmStatus : 'Confirmed',
        }}
     
        onSubmit={values => {
          sendData(values);  
        }}
      >
        {formik => (
          <div>
            <h1 className='mt-4'style={{fontWeight:"bold"}} >Do you want to CONFIRM this event ?</h1>
            <Form>
          
              
             <button id="updateBookingButton" className="btn btn-dark mt-3" type="submit">Confirm</button>
              
              
            </Form>
  
          </div>
        )}
      </Formik>
      </>
    )
  } 
export default EditConfirm;

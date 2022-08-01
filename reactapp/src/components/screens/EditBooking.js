import React from 'react';
import { Formik, Form} from 'formik';
import TextBar from './TextBar';
import * as Yup from 'yup';
import axiosObject from '../../api/bootapi';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css'

function EditBooking({booking}){

    const validate = Yup.object({
        
        name: Yup.string()
        .required('Customer Name is Required'),
      // contactNumber:Yup.string()
      //   .min(10,'should be 10 number')
      //   .max(10,'should be 10 number')
      //   .required('Mobile Number is Required'),
   
      eventDate: Yup.date()
      .transform((curr, orig) => orig === '' ? null : curr)
      .required('Date is required')
      .nullable()
      .min(new Date()
      , "Booking should be done 30 days prior to event date")
  ,
      eventPlace:  Yup.string()
        .required('Please enter the venue'),
      locationUrl:  Yup.string()
      .required('Location Url is required'),
      eventTiming:Yup.string()
        .required('Please mention timing'),
      peopleCount : Yup.string()
      .required("People count expected"),
      
    })
    const sendData=(data)=>{
      axiosObject.put(`/editBooking`,data).then(
        (response)=>{
          console.log(response);
          if(response.status === 200){
          toast.success(response.data,{autoClose: 5000});
        setTimeout(() => { window.location.replace('/user/mybookings'); }, 5000);
          }
        }
      ).catch((error)=>{
        if(error.response){
          toast.error(error.response.data.details,{autoClose: 5000});
          setTimeout(() => { window.location.replace('/user/mybookings'); }, 5000);
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
          uid:booking.uid,
          eid:booking.eid,
          name: booking.name,
          mobileNumber:booking.mobileNumber,
          eventDate:booking.eventDate,
          eventPlace:booking.eventPlace,
          locationUrl :booking.locationUrl,
          eventTiming : booking.eventTiming,
          peopleCount:  booking.peopleCount,
        }}
        validationSchema={validate}
        onSubmit={values => {
        
          console.log(values);
          sendData(values);
          
        }}
      >
        {formik => (
          <div>
            <h1 className='mt-4'style={{fontWeight:"bold"}} >Edit Booking </h1>
            <Form>
            <TextBar  label="Book Id" readOnly={true}  name="bookId" type="number" id="editBookId" />
            <TextBar id="editname" label="Name" placeholder="Enter Your name" name="name" type="text" />
                  <TextBar id="editmobileNumber" label="MobileNumber" name="mobileNumber" type="text" />
                  <TextBar id="editeventdate" label="Date of Event" name="eventDate" type="date" />
                  <TextBar id="editeventPlace" label="Venue" name="eventPlace" type="text" />
                  <TextBar id="editlocationUrl" label="Location Url" name="locationUrl" type="text" />
                  <TextBar id="editeventTiming" label="Time of Event" placeholder="choose time in 24hr format" name="eventTiming" type="time" />
                  <TextBar id="editpeopleCount" label="People Count"  name="peopleCount" type="number" />
              
             <button id="updateBookingButton" className="btn btn-dark mt-3" type="submit">Update</button>
              <button id="resetButton" className="btn btn-danger mt-3 ml-3"style={{marginLeft:15}} type="reset">Reset</button>
              
              
            </Form>
  
          </div>
        )}
      </Formik>
      </>
    )
  } 
export default EditBooking;

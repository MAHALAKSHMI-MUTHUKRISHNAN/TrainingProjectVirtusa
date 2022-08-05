import React,{useEffect, useState} from 'react';
import { Formik, Form} from 'formik';
import TextBar from './TextBar';
import * as Yup from 'yup';
import axiosObject from '../../api/bootapi';
import '../styles/Booking.css';
import NavbarUser from './NavbarUser';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function Booking(){
  let event=JSON.parse(localStorage.getItem('SelectedEvent'));
 
  const getUser=()=>{
    axiosObject.get(`/mydetails`).then(
        (response)=>{
          console.log("user fetched");
          setUser(response.data);
        },(error)=>{
          console.log(error);
        }
      );
  };



  const [user,setUser] = useState([]);

  useEffect(()=>{
    document.title= "Event || Booking"
    getUser();
    },[]);
  const validate = Yup.object({
    name: Yup.string()
      .required('Customer Name is Required'),
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
  const postDatatoServer=(data)=>{
    axiosObject.post(`/booking`,data).then(
      (response)=>{
        toast.success(response.data,{autoClose: 5000});
        localStorage.removeItem('SelectedEvent');
       setTimeout(() => { window.location.replace('/user/mybookings'); }, 5000);
        console.log(response);
      
      }).catch((error)=>{
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
    <div className='App-temp'>
      
    <NavbarUser/>
    <Formik
    enableReinitialize={true}
      initialValues={{
        uid:user.id,
        eid:event.id,
        name: user.name,
        mobileNumber:user.mobile,
        eventDate:'',
        eventPlace:'',
        locationUrl :'',
        eventTiming : '',
        peopleCount:  '',
      }}
      validationSchema={validate}
      onSubmit={values => {
      
        console.log(values);
        postDatatoServer(values);
      }}
    >
      {formik => (
        <div className='contents' >
          <div className='Regdiv row'>
            <div className='col-md-6 left'>
              <img src={event.imageurl} alt="" className="img" />
              <div className='address'>
                  <label>Name : {event.name}</label><br />
                  <label>Details {event.details}</label><br />
              </div>
            </div>
            <div className='col-md-6'>
              <Form>
                <div className='inp'>
                  <h1 className='mt-4'style={{fontWeight:"bold", paddingBottom: "2vh"}} >Product Details</h1>
                  <TextBar id="name" label="Name" placeholder="Enter Your name" name="name" type="text" />
                  <TextBar id="mobileNumber" label="MobileNumber" name="mobileNumber" type="text" />
                  <TextBar id="eventdate" label="Date of Event" name="eventDate" type="date" />
                  <TextBar id="eventPlace" label="Venue" name="eventPlace" type="text" />
                  <TextBar id="locationUrl" label="Location Url" name="locationUrl" type="url" />
                  <TextBar id="eventTiming" label="Time of Event" placeholder="choose time in 24hr format" name="eventTiming" type="time" />
                  <TextBar id="peopleCount" label="People Count"  name="peopleCount" type="number" />
                  <button id="resetbutton" className="btn btn-dark mt-3 ml-3"style={{marginLeft:15}} type="reset">Reset</button>
                  <button id="bookappointmentbutton" className="btn btn-success mt-3"style={{marginLeft:40}} type="submit">BOOK</button>
                </div> 
              </Form>
            </div>
          </div>
        </div>
      )}
    </Formik>
    </div>
    </>
  )
} 
export default Booking;

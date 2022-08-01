import React, { useEffect, useState } from "react";
import axiosObject from "../../api/bootapi";
import NavbarUser from './NavbarUser';
import Table from '@mui/material/Table';
import { Modal,Button,OverlayTrigger,Tooltip } from "react-bootstrap";
import { TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import EditBooking from "./EditBooking";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import '../styles/ViewUserBookings.css';
import ReactiveButton from 'reactive-button'
import CreditCardForm from "./CreditCardForm";


function ViewUserBookings(){
    const [data,setData] = useState([]);

    const [display,setDisplay] = useState("");

    const getUserBookings=()=>{
        axiosObject.get(`/getUserBookings`).then(
            (response)=>{
              console.log("booking fetched");
              setData(response.data);
            }).catch((error)=>{
                if(error.response){
                 setDisplay(error.response.data.details);
                  console.log(error.response.data);
                }
              });
            }

    const remove=(value)=>{
        axiosObject.delete(`/deleteBooking/${value}`).then(
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
    useEffect(()=>{
    document.title= "Event || UserBookings" 
    getUserBookings();
    },[]);

    const [modalData,setModalData] = useState([]);
    const[show,setShow]=useState(false);
    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);

    const [modalData1,setModalData1] = useState([]);
    const[show1,setShow1]=useState(false);
    const handleShow1 = () => setShow1(true);
    const handleClose1 = () => setShow1(false);

        return(
     <>
     <ToastContainer/>
         <NavbarUser/>
     <div className="home-body"style={{color:"black",margin:'auto',fontWeight:'bolder'}}>
       
         <h1 style={{textAlign:'center',paddingTop:'10%'}}>Bookings</h1>
         
         <Table style={{width:'70%', margin:'auto'}}>
        
         <TableHead style={{fontWeight:"bolder"}}>
         <TableCell>Booking No.</TableCell>
         <TableCell>Customer</TableCell>
         <TableCell>Event</TableCell>
         <TableCell>Confirm Status</TableCell>
         <TableCell>Charges</TableCell>
         <TableCell>Actions</TableCell>
         </TableHead>
         {data.length !== 0 ?
                    <TableBody>
                       
                       {
                           data.map(val => {
                               return(
                                
                                   <TableRow key="key">
                                <TableCell>{val.bookId}</TableCell>
                                <TableCell>Name : {val.name}<br/> Mobile: {val.mobileNumber}</TableCell>
                                <TableCell>Date : {val.eventDate}<br/>Time : {val.eventTiming}<br/>Venue:  {val.eventPlace},<br/>{val.locationUrl}</TableCell>
                                <TableCell>{val.confirmStatus}</TableCell>
                               
                               {  val.confirmStatus==="pending" ?
                               <>
                              <TableCell>NA</TableCell>
                           <TableCell>
                                    <OverlayTrigger
                                    overlay={
                                        <Tooltip id={'tooltip-top'}>
                                            Edit
                                        </Tooltip>
                                    }>
                                        <Button id="editappointmentbutton"  data-toggle="modal" onClick={()=>{handleShow();setModalData(val)}} ><i className="fa fa-pencil-square-o fa-lg" aria-hidden="true"></i></Button>
                                        </OverlayTrigger>
                                <button style = {{ marginLeft:20}} id="deleteappointmentbutton" onClick={() => remove(val.bookId)}><i className="fa fa-trash fa-lg" aria-hidden="true"></i></button>
                            </TableCell>
                            </>
                            :val.confirmStatus==="Rejected" ?
                            <>
                            <TableCell> NA </TableCell>
                            <TableCell>Your booking has been rejected.</TableCell>
                            </>
                             : val.confirmStatus==="Confirmed" && val.payment === null ?
                            <>
                            <TableCell> NA </TableCell>
                            <TableCell>Wait for charges to be updated</TableCell>
                            </>
                             : val.confirmStatus==="Confirmed" && val.payment !== null ?
                             <>
                             <TableCell>{val.payment.charges}</TableCell>
                             <TableCell>InitialPay: {val.payment.initialPay === false ? <ReactiveButton idleText={'Payment'}  animation={true}  style = {{backgroundColor:"#42C2FF",borderRadius:5,color:"black"}} id="paymentbutton" onClick={()=>{handleShow1();setModalData1(val)}}></ReactiveButton>:"Done"}<br/>
                                     FinalPay : {val.payment.finalPay === false ? <ReactiveButton idleText={'Payment'} animation={true} style = {{backgroundColor:"#42C2FF",borderRadius:5,color:"black"}} id="paymentbutton" onClick={()=>{handleShow1();setModalData1(val)}}></ReactiveButton>:"Done"}</TableCell>
                             </>
                              :val.confirmStatus==="Confirmed" && val.payment !== null && val.payment.initialPay === true && val.payment.finalPay === true ?
                            <TableCell>Add Testimony</TableCell>
                              :
                            <TableCell>Add Testimony</TableCell>
                                }
                                  </TableRow>
                                )})
                       }
                    </TableBody>:
                    <TableBody>{display}</TableBody>
}
                    </Table>
                    <Modal show={show} onHide={handleClose} >
                        <Modal.Header closeButton>
                            <Modal.Body>
                                <EditBooking booking={modalData}/>
                            </Modal.Body>
                        </Modal.Header>

                    </Modal>
                    <Modal show={show1} onHide={handleClose1} >
                        <Modal.Header closeButton>
                            <Modal.Body>
                                <CreditCardForm booking={modalData1}/>
                            </Modal.Body>
                        </Modal.Header>

                    </Modal>
     </div>
     
     </>
     )}

 

export default ViewUserBookings;
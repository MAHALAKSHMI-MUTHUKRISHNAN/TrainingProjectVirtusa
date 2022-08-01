import React, { useEffect, useState } from "react";
import axiosObject from "../../api/bootapi";
import Table from '@mui/material/Table';
import '../styles/ViewUserBookings.css';
import { Modal } from "react-bootstrap";
import { TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import NavbarAdmin from "./NavbarAdmin";
import EditConfirm from "./EditConfirm";
import EditReject from "./EditReject";
import Charges from "./Charges";
import { Container,Row,Col} from 'react-bootstrap';
import ReactPaginate from "react-paginate";

function AllBookings(){
    const getAllBookings=()=>{
        axiosObject.get(`/getAllBookings`).then(
            (response)=>{
              console.log("Bookings fetched");
              setData(response.data);
            }).catch((error)=>{
              if(error.response){
             
                console.log(error.response.data);
              }
            });
          }

    useEffect(()=>{
    document.title= "Event || AllBooking"
    getAllBookings();
    },[]);
    const [data,setData] = useState([]);
    const [value,setValue] = useState([]);

    const[show,setShow]=useState(false);
    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);

    const [modalData,setModalData] = useState([
        {
        },
    ]);

    const[show3,setShow3]=useState(false);
    const handleShow3 = () => setShow3(true);
    const handleClose3 = () => setShow3(false);

    const [modalData3,setModalData3] = useState([
        {
        },
    ]);

    const[show1,setShow1]=useState(false);
    const handleShow1 = () => setShow1(true);
    const handleClose1 = () => setShow1(false);

    const [modalData1,setModalData1] = useState([
        {
        },
    ]);

    const handleSearch = (e) =>{
        axiosObject.get(`/search?query=${value}`).then(
            (response)=>{
              setData(response.data);
              setValue("");
              console.log("search fetched");
             
            },(error)=>{
              console.log(error);
            }
          ); 
    };

    const handleFilter = (value) =>{
        axiosObject.get(`/search?query=${value}`).then(
            (response)=>{
              setData(response.data);
              setValue("");
              console.log("Filter fetched");
            },(error)=>{
              console.log(error);
            }
          ); 
      
    };

    const handleReset =() =>{
        getAllBookings();
    };


    const [currentItems, setCurrentItems] = useState([]);
  const [pageCount, setPageCount] = useState(0);
  const [itemOffset, setItemOffset] = useState(0);
  const itemsPerPage = 2;

  useEffect(() => {
    const endOffset = itemOffset + itemsPerPage;
    setCurrentItems(data.slice(itemOffset, endOffset));
    setPageCount(Math.ceil(data.length / itemsPerPage));
  }, [itemOffset, itemsPerPage,data]);


  const handlePageClick = (event) => {
    const newOffset = (event.selected * itemsPerPage) % data.length;
    setItemOffset(newOffset);
  };


 return(
     <>
         <NavbarAdmin/>
     <div className="home-body"style={{color:"black",fontWeight:'bolder'}}>
         {/* <h1 style={{textAlign:'center',paddingTop:'2%'}}>Bookings</h1> */}
         <Container className='text-center mt-4' style={{width:"40%"}}>
            <Row>
                <Col style={{padding:"3px"}}><input type="text"  className="form-control" placeholder="Search by Booking id" value={value} onChange={(e) => setValue(e.target.value)}/></Col>
                <Col style={{padding:"3px"}}><button style = {{backgroundColor:"green",width:"100px",borderRadius:5,color:"white"}} type="submit" onClick={()=> handleSearch()} >Search</button></Col>
                <Col style={{padding:"3px"}}><button style = {{backgroundColor:"black",width:"100px",borderRadius:5,color:"white"}} type="reset"  onClick={()=> handleReset()}>Reset</button></Col>
            </Row>


</Container>

<br/>
         <Table style={{width:'70%', margin:'auto' }}>
         <TableHead style={{fontWeight:"bolder"}}>
         <TableCell>Booking No.</TableCell>
         <TableCell>Customer</TableCell>
         <TableCell>Event</TableCell>
         <TableCell>Confirm Status</TableCell>
         <TableCell>Payment Status</TableCell>
         <TableCell>Actions</TableCell>

         </TableHead>
                    <TableBody>
                       {
                       
                           currentItems.map(val => {
                               return(
                                   <TableRow key="key">
                                       
                                <TableCell>{val.bookId}</TableCell>
                                <TableCell>Name : {val.name}<br/> Mobile: {val.mobileNumber}</TableCell>
                                <TableCell>Date : {val.eventDate}<br/>Time : {val.eventTiming}<br/>Venue: {val.eventPlace},<br/>{val.locationUrl}</TableCell>
                                <TableCell>{val.confirmStatus}</TableCell>
                                
                                {
                                    val.confirmStatus === "pending" ?
                                    <>
                                    <TableCell>NA</TableCell>
                                    <TableCell>
                                    <button style = {{backgroundColor:"red",borderRadius:5,color:"white"}} id="confirm" onClick={()=>{handleShow();setModalData(val)}} >Confirm</button>
                                    <br/><br/><button style = {{backgroundColor:"black",borderRadius:5,color:"white"}} id="reject" onClick={()=>{handleShow1();setModalData1(val)}} >Reject</button>
                                     </TableCell></>
                                    :  val.confirmStatus === "Rejected"? 
                                    <><TableCell></TableCell> <TableCell>
                                   
                                    </TableCell></>
                                   
                                     : val.confirmStatus === "Confirmed" && val.payment === null? 
                                     <>
                                    <TableCell>
                                    <button style = {{backgroundColor:"red",borderRadius:5,color:"white"}} id="charges" onClick={()=>{handleShow3();setModalData3(val)}} >Update Charges</button>
                                    <TableCell>NA</TableCell> 
                                     </TableCell>

                                     </>
                                     : val.confirmStatus === "Confirmed" && val.payment !== null ?
                                     <>
                                     <TableCell>InitialPay: {val.payment.initialPay === false ? "Not Done":"Done"}<br/>
                                     FinalPay : {val.payment.finalPay === false ? "Not Done":"Done"}
                                     </TableCell>
                                     </>
                                     
                                      : 
                                    <TableCell> </TableCell>
                                }
                                </TableRow>
                                )})
                       }
                    </TableBody> 
                </Table>
                    <Modal show={show} onHide={handleClose} >
            <Modal.Header closeButton>
                <Modal.Body>
                    <EditConfirm booking={modalData}/>
                </Modal.Body>
            </Modal.Header>
        </Modal>  
        <Modal show={show1} onHide={handleClose1} >
            <Modal.Header closeButton>
                <Modal.Body>
                    <EditReject booking={modalData1}/>
                </Modal.Body>
            </Modal.Header>
        </Modal>   
        <Modal show={show3} onHide={handleClose3} >
            <Modal.Header closeButton>
                <Modal.Body>
                    <Charges booking={modalData3}/>
                </Modal.Body>
            </Modal.Header>
        </Modal>
     </div>
     <div>

     </div>
     <Container className='text-center mt-4' style={{width:"40%"}}>
Filter by ConfirmStatus :
&nbsp;<button style = {{backgroundColor:"blue",width:"100px",borderRadius:5,color:"white"}} type="submit" onClick={()=> handleFilter("pending")} >Pending</button>
&nbsp;<button style = {{backgroundColor:"green",width:"100px",borderRadius:5,color:"white"}} type="submit" onClick={()=> handleFilter("Confirmed")} >Confirmed</button>
&nbsp;<button style = {{backgroundColor:"black",width:"100px",borderRadius:5,color:"white"}} type="reset"  onClick={()=> handleFilter("Rejected")}>Rejected</button>

</Container>
     <ReactPaginate
        breakLabel={'...'}
        nextLabel="next >"
        onPageChange={handlePageClick}
        pageRangeDisplayed={5}
        marginPagesDisplayed={2}
        pageCount={pageCount}
        previousLabel={'< previous'}
        renderOnZeroPageCount={null}
        containerClassName={'pagination justify-content-center mt-4'}
        pageClassName={'page-item'}
        pageLinkClassName={'page-link'}
        previousClassName={'page-item'}
        previousLinkClassName={'page-link'}
        nextClassName={'page-item'}
        nextLinkClassName={'page-link'}
        breakClassName={'page-item'}
        breakLinkClassName={'page-link'}
        activeClassName={'active'}
      />
     </>
 )
}
export default AllBookings;
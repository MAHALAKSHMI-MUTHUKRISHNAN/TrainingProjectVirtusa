import React, { useEffect, useState } from "react";
import NavbarAdmin from './NavbarAdmin';
import axiosObject from "../../api/bootapi";
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import { Container} from 'react-bootstrap';
import Form from 'react-bootstrap/Form';
import EditEvent from "./EditEvent.js"
import { Modal } from "react-bootstrap";

function HomeAdmin() {

    const [events,setEvents]=useState([]);

    const [display,setDisplay] = useState("");

    const getAllEvents=()=>{
        axiosObject.get(`/viewAllEvents`).then(
            (response)=>{
              console.log("Events fetched");
              console.log(events.length);
              setEvents(response.data);
            }).catch((error)=>{
              if(error.response){
               setDisplay(error.response.data.details);
              }
            });
          }
   

    useEffect(()=>{
        document.title= "Events || Home"
        getAllEvents();},[]);

    

    const[show,setShow]=useState(false);
    const handleShow = () => setShow(true);
    const handleClose = () => setShow(false);

    const [modalData,setModalData] = useState([
        {
        },
    ]);

    const [filter,setFilter] = useState('');

    const SearchText = (word) =>{
      setFilter(word.target.value);
    }
    let dataSearch = events.filter(item =>{
       return Object.keys(item).some(key =>
            item[key].toString().toLowerCase().includes(filter.toString().toLowerCase())
    )});

    const remove=(value)=>{
        axiosObject.delete(`/deleteEvent/${value}`).then(
            (response)=>{
                console.log("Event Deleted");
                console.log(response);
                refreshPage();
            },(error)=>{
                console.log(error);
            }
        )
    };
    const refreshPage=()=>{
        window.location.reload(false);
    }

  return (
    <>
      <NavbarAdmin/>
    <div className="home-body"style={{display:"flex",flexDirection:"column",alignItems:"center"}}>

        <Container className='text-center mt-4' style={{width:"40%"}}>
          <Form.Control  id="searchbar" placeholder="Search" value = {filter} onChange={SearchText.bind(this)} />

        </Container>
        <div className="fixed-content">
        {events.length !==0 ?
        <Row>
        {dataSearch.map((event) => {<dataSearch key ={event.id}/>
          return (
            
            <Col style={{ padding: '2rem' }} >
                
              <Card style={{ width: '18rem',borderRadius:20 ,marginRight:5,marginLeft:5}}>
                    <Card.Img variant="top" src={event.imageurl} style={{ width: '10rem', height: '10rem',marginLeft:"20%",marginTop:10,borderRadius:"50%" }} />
                <Card.Body>
                  <Card.Title>{event.name}</Card.Title>
                  <Card.Text>
                  {event.details}
                  </Card.Text>
                </Card.Body>

                <Card.Body style={{alignItems:"center"}}>
                <button className="btn btn-success " onClick={()=>{handleShow();setModalData(event)}}>Edit Event Details</button>
                <button style = {{ marginLeft:20}} id="deleteEvent" onClick={() => remove(event.id)}><i className="fa fa-trash fa-lg" aria-hidden="true"></i></button>
                </Card.Body>
              </Card>
             
            </Col>    
           )
        })}
        
      </Row>
   :
   <Row>
<p>{display}</p>
   </Row>
}
      </div>
      <Modal show={show} onHide={handleClose} >
            <Modal.Header closeButton>
                <Modal.Body>
                    <EditEvent editItem={modalData}/>
                </Modal.Body>
            </Modal.Header>

        </Modal>
    </div>
    </>
  )
}

export default HomeAdmin